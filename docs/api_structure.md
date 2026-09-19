# QUY CHUẨN THIẾT KẾ RESTful API

## 1. Định dạng URL (Endpoint Naming)
- Sử dụng danh từ số nhiều cho các resource, viết thường và cách nhau bằng dấu gạch ngang (kebab-case).
- **Tuyệt đối không** dùng động từ trong URL (Không dùng `/create-product`, `/delete-product`).
- **Base URL:** `/api/v1`

*Ví dụ chuẩn cho module Sản phẩm:*
- Danh mục: `/api/v1/categories`
- Thương hiệu: `/api/v1/brands`
- Sản phẩm: `/api/v1/products`

## 2. Tiêu chuẩn HTTP Methods
- `GET`: Lấy dữ liệu (List hoặc Detail).
- `POST`: Thêm mới dữ liệu.
- `PUT`: Cập nhật toàn bộ dữ liệu của 1 object.
- `PATCH`: Cập nhật 1 phần dữ liệu (vd: chỉ đổi trạng thái).
- `DELETE`: Xóa dữ liệu (Ưu tiên soft-delete - chuyển `trangthai = 1` thay vì xóa vật lý).

## 3. Quy chuẩn Truyền tham số (Parameters)
- **Path Variable:** Dùng để định danh một resource cụ thể.
  - *Ví dụ:* Lấy chi tiết 1 sản phẩm -> `GET /api/v1/products/{id}`
- **Query Parameter:** Dùng để lọc (filter), tìm kiếm (search), và phân trang (pagination).
  - *Ví dụ:* `GET /api/v1/products?page=1&size=10&brandId=2&search=ao-thun`
- **Request Body (JSON):** Bắt buộc dùng cho `POST` và `PUT`.

## 4. Quy chuẩn Phân trang (Pagination)
- Các API trả về danh sách (GET ALL) bắt buộc phải có phân trang bằng `Pageable` của Spring Data JPA.
- Data trả về phải bọc trong đối tượng chứa metadata phân trang (tổng số trang, trang hiện tại, tổng số phần tử).

## 5. Mapping CRUD cơ bản (Ví dụ với Product)
1. `GET /api/v1/products` -> Lấy danh sách sản phẩm (có phân trang/lọc).
2. `GET /api/v1/products/{id}` -> Xem chi tiết 1 sản phẩm.
3. `POST /api/v1/products` -> Thêm mới sản phẩm (Body: ProductCreateRequest).
4. `PUT /api/v1/products/{id}` -> Cập nhật sản phẩm (Body: ProductUpdateRequest).
5. `DELETE /api/v1/products/{id}` -> Xóa sản phẩm.

## 6. Quy chuẩn Request & Response Format (JSON)

### 6.1. API Trả về thông thường (Success / Error)
Tất cả các API phải bọc kết quả trả về trong class `ApiResponse<T>`. 
- Khi thành công:
```json
{
  "code": 200,
  "message": "Thành công",
  "data": { 
    "id": 1,
    "name": "Áo thun"
  }
}
- Khi thất bại (Bắt qua GlobalExceptionHandler):
{
  "code": 2001,
  "message": "Không tìm thấy sản phẩm",
  "data": null
}
6.2. Format khi Phân trang (Pagination Response)
Đối với các API GET ALL có phân trang, phần data phải được bọc trong một đối tượng chứa metadata (ví dụ: PageResponse<T>). Tuyệt đối không trả thẳng object Page<T> của Spring ra ngoài.

JSON
{
  "code": 200,
  "message": "Lấy danh sách thành công",
  "data": {
    "content": [ { "id": 1, ... }, { "id": 2, ... } ],
    "page_no": 1,
    "page_size": 10,
    "total_elements": 50,
    "total_pages": 5,
    "is_last": false
  }
}
6.3. Format khi lỗi Validation Đầu vào (Bad Request 400)
Khi dữ liệu gửi lên (POST, PUT) vi phạm các annotation @NotBlank, @Min,... phần data sẽ chứa danh sách các trường bị lỗi để Frontend dễ dàng map vào form:

JSON
{
  "code": 400,
  "message": "Dữ liệu đầu vào không hợp lệ",
  "data": {
    "proName": "Tên sản phẩm không được để trống",
    "proPrice": "Giá sản phẩm phải lớn hơn 0"
  }