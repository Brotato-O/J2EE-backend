# Hướng dẫn nghiệp vụ Sandbox: GHN và VNPay

Tài liệu này mô tả luồng demo đang triển khai trong backend. Dự án hiện sử dụng:

- GHN Sandbox cho tạo, hủy và theo dõi vận đơn.
- VNPay Sandbox cho tạo yêu cầu thanh toán và IPN reconciliation.
- Không còn sử dụng GHTK hoặc MoMo trong.

## 1. Cấu hình môi trường

Cấu hình database, JWT, GHN và VNPay trong `project/.env`.

```properties
GHN_ENABLED=true
GHN_BASE_URL=https://dev-online-gateway.ghn.vn
GHN_API_TOKEN=<GHN_STAGING_TOKEN>
GHN_SHOP_ID=<GHN_STAGING_SHOP_ID>
GHN_WEBHOOK_SECRET=<SECRET_TUY_THEO_WEBHOOK_CONFIG>

GHN_FROM_NAME=<TEN_NGUOI_GUI>
GHN_FROM_PHONE=<SO_DIEN_THOAI_NGUOI_GUI>
GHN_FROM_ADDRESS=<DIA_CHI_NGUOI_GUI>
GHN_FROM_WARD_NAME=<PHUONG_XA_NGUOI_GUI>
GHN_FROM_DISTRICT_NAME=<QUAN_HUYEN_NGUOI_GUI>
GHN_FROM_PROVINCE_NAME=<TINH_THANH_NGUOI_GUI>

VNPAY_ENABLED=true
VNPAY_PAYMENT_URL=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html
VNPAY_TMN_CODE=<SANDBOX_TMN_CODE>
VNPAY_HASH_SECRET=<SANDBOX_HASH_SECRET>
VNPAY_RETURN_URL=http://localhost:8080/api/v1/payments/vnpay/return
VNPAY_IPN_URL=https://<NGROK_DOMAIN>/api/v1/payments/webhooks/vnpay
VNPAY_EXPIRATION=PT15M
```

Không commit `.env`, Token, Hash Secret hoặc credential thật.

### Ngrok cho VNPay IPN

```bash
ngrok http 8080
```

Sau đó đặt `VNPAY_IPN_URL` thành:

```text
https://<domain-ngrok>/api/v1/payments/webhooks/vnpay
```

Cấu hình cùng URL trong merchant dashboard VNPay Sandbox nếu dashboard yêu cầu nhập IPN URL.

## 2. Trạng thái đơn hàng

Luồng chính:

```text
NEW
  -> CONFIRMED
  -> PACKING
  -> SHIPPING
  -> DELIVERED
```

Ngoại lệ:

```text
NEW | CONFIRMED | PACKING | SHIPPING
  -> CANCELLED

SHIPPING -> DELIVERY_FAILED -> RETURNED
SHIPPING -> RETURNED
```

Trạng thái database phải lưu bằng tên enum tiếng Anh:

```text
NEW
CONFIRMED
PACKING
SHIPPING
DELIVERED
DELIVERY_FAILED
RETURNED
CANCELLED
```

Không lưu tiếng Việt vào database. Backend vẫn đọc một số giá trị legacy để migration hoặc tương thích cũ.

## 3. Luồng đặt hàng

Đăng nhập bằng role `User`.

```http
POST /api/v1/auth/login
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

Đặt hàng:

```http
POST /api/v1/orders
Authorization: Bearer <JWT>
Content-Type: application/json
```

```json
{
  "address": "123 Đường ABC",
  "recipientProvince": "Hồ Chí Minh",
  "recipientDistrict": "Quận 1",
  "recipientWard": "Phường Bến Nghé",
  "paymentMethod": "VNPAY"
}
```

Sau `placeOrder`:

- Order: `NEW`.
- Giỏ hàng được xóa.
- Tồn kho sản phẩm đã được trừ.

## 4. Thanh toán VNPay

Tạo payment:

```http
POST /api/v1/payments/orders/{orderId}/vnpay
Authorization: Bearer <JWT>
```

Response có `paymentUrl`. Frontend mở URL này.

Sau VNPay xử lý thanh toán:

- Browser redirect tới `VNPAY_RETURN_URL`.
- VNPay server gọi IPN tới `VNPAY_IPN_URL`.
- Backend xác thực chữ ký HMAC-SHA512.
- Backend kiểm tra:
  - `vnp_TxnRef` trùng transaction reference.
  - `vnp_Amount` nhân 100 trùng amount.
  - `vnp_ResponseCode = 00`.
  - `vnp_TransactionStatus = 00`.
  - Giao dịch chưa được xử lý.
- Payment chuyển sang `SUCCESS`.
- Lịch sử payment và payment transaction được lưu.
- Order chuyển `NEW -> CONFIRMED`.

IPN endpoint:

```http
GET /api/v1/payments/webhooks/vnpay
```

Callback sample:

```text
vnp_Amount
vnp_BankCode
vnp_ResponseCode
vnp_TransactionNo
vnp_TransactionStatus
vnp_TxnRef
vnp_SecureHash
```

IPN response VNPay:

```json
{
  "RspCode": "00",
  "Message": "Confirm Success"
}
```

Browser Return không thay thế IPN. IPN mới là server-to-server confirmation.

## 5. Tạo vận đơn GHN

Điều kiện:

- Order đang `CONFIRMED`.
- Với order không COD: payment phải `SUCCESS`.
- Với COD: có thể tạo vận đơn mà không cần payment success.
- Địa chỉ recipient phải có:
  - `recipientProvince`
  - `recipientDistrict`
  - `recipientWard`

Gọi API:

```http
POST /api/v1/fulfillments/orders/{orderId}/shipment
Authorization: Bearer <staff-or-admin-JWT>
```

Chỉ role sau được phép:

```text
Admin
Nhân Viên
Nhân Viên Kho
```

Hệ thống gọi GHN staging create-order với:

- Header `Token`.
- Header `ShopId`.
- Địa chỉ người gửi lấy từ cấu hình GHN.
- Địa chỉ người nhận lấy từ order.
- `client_order_code = ORDER-{orderId}`.
- `weight`, `length`, `width`, `height` lấy cấu hình.
- `items` lấy từ order details.

GHN response `data.order_code` được lưu thành:

```text
shipments.tracking_code
shipments.provider_order_code
```

Lần gọi đầu:

```text
CONFIRMED -> PACKING -> SHIPPING
shipment: CREATED -> CONFIRMED
```

Gọi lại khi đã có `tracking_code` trả về shipment hiện tại, không tạo đơn GHN trùng.

## 6. Hủy vận đơn

Endpoint:

```http
DELETE /api/v1/fulfillments/orders/{orderId}/shipment
Authorization: Bearer <staff-or-admin-JWT>
```

Chỉ cho hủy:

- Shipment không `PICKED_UP`, `SHIPPING`, `DELIVERY_FAILED`, `DELIVERED`, `RETURNING`, `RETURNED`.
- Order đang `NEW`, `CONFIRMED` hoặc `PACKING`.

Luồng:

```text
Gọi GHN cancel
→ Cộng tồn kho
→ Shipment CANCELLED
→ Fulfillment CANCELLED
→ Order CANCELLED
→ Ghi order status history
```

Các lần hủy trùng không cộng kho lần hai vì shipment có `stock_released = true`.

Sau khi picked-up hoặc delivering, không hủy trực tiếp; dùng giao thất bại hoặc hoàn hàng.

## 7. GHN webhook

Endpoint:

```http
POST /api/v1/fulfillments/webhooks/ghn
Content-Type: application/json
X-GHN-Webhook-Secret: <secret_nếu_có>
```

`OrderCode` trong payload là mã vận đơn GHN, ví dụ `LYCGLF`, không phải `orderId`.

Payload gửi bằng PascalCase:

```json
{
  "ShopID": 123456,
  "Time": "2026-11-19T10:40:00Z",
  "OrderCode": "LYCGLF",
  "ClientOrderCode": "ORDER-7",
  "Type": "switch_status",
  "Status": "delivered",
  "Reason": "",
  "ReasonCode": ""
}
```

Mapping GHN status:

| GHN Status | Shipment | Fulfillment/Order |
| --- | --- | --- |
| `ready_to_pick` | `CONFIRMED` | `SHIPPING` |
| `picking` | `CONFIRMED` | `SHIPPING` |
| `picked` | `PICKED_UP` | `SHIPPING` |
| `delivering` | `SHIPPING` | `SHIPPING` |
| `delivered` | `DELIVERED` | `DELIVERED` |
| `delivery_fail` | `DELIVERY_FAILED` | `DELIVERY_FAILED` |
| `return` | `RETURNING` | `SHIPPING` |
| `returning` | `RETURNING` | `SHIPPING` |
| `returned` | `RETURNED` | `RETURNED` |
| `return_fail` | `DELIVERY_FAILED` | `DELIVERY_FAILED` |
| `damage` | `DELIVERY_FAILED` | `DELIVERY_FAILED` |
| `lost` | `DELIVERY_FAILED` | `DELIVERY_FAILED` |
| `cancel` | `CANCELLED` | `CANCELLED` |

Webhook:

- Dedupe bằng `OrderCode + Type + Time`.
- Lưu toàn bộ payload vào `shipping_webhook_events`.
- Chặn webhook sai thứ tự theo status rank.
- Chỉ cập nhật shipment/order khi event hợp lệ.
- Secret GHN dùng header `X-GHN-Webhook-Secret`.

## 8. Các file/legacy không còn dùng

Đã loại bỏ:

- Enum GHTK khỏi canonical `ShippingProviderType`.
- File enum trùng `entity/ShippingProviderType`.
- Mock GHTK.
- Provider GHTK.
- GHTK Properties.
- GHTK webhook/config.
- MoMo provider và MoMo properties.

Migration V15 đổi shipment legacy `GHTK -> GHN`.

Các giá trị `MOMO` có thể vẫn tồn tại trong enum để tránh lỗi deserialize dữ liệu lịch sử. Backend không còn endpoint/provider MoMo và không nên tạo payment mới bằng MoMo.
