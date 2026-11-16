package J2EE.com.example.project.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SIZE_NOT_FOUND(2001, "Không tìm thấy kích thước", HttpStatus.NOT_FOUND),
    COLOR_NOT_FOUND(2002, "Không tìm thấy màu sắc", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND(2003, "Không tìm thấy thương hiệu", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(2004, "Không tìm thấy danh mục", HttpStatus.NOT_FOUND),
    SUPPLIER_NOT_FOUND(2005, "Không tìm thấy nhà cung cấp", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(2006, "Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND),
    VARIANT_NOT_FOUND(2007, "Không tìm thấy biến thể sản phẩm", HttpStatus.NOT_FOUND),
    BRAND_REQUIRED(2008, "Thương hiệu là bắt buộc", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(2009, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(400, "Dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),

    EMAIL_ALREADY_EXISTS(3001, "Email đã tồn tại", HttpStatus.CONFLICT),
    USER_NOT_FOUND(3002, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS(3003, "Email hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(3004, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
    ACCOUNT_DISABLED(3005, "Tài khoản đã bị khóa", HttpStatus.FORBIDDEN),
    AUTHENTICATION_REQUIRED(401, "Bạn cần đăng nhập để truy cập tài nguyên này", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Bạn không có quyền truy cập tài nguyên này", HttpStatus.FORBIDDEN),
    CART_EMPTY(4001, "Giỏ hàng đang trống, không thể đặt hàng", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(4023, "Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(4024, "Không tìm thấy khách hàng", HttpStatus.NOT_FOUND),
    INVALID_ORDER_STATUS(4002, "Trạng thái đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_AMOUNT_INVALID(4003, "Số tiền đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_ALREADY_SUCCESSFUL(4004, "Đơn hàng đã được thanh toán thành công", HttpStatus.CONFLICT),
    PAYMENT_NOT_PENDING(4005, "Payment không ở trạng thái chờ thanh toán", HttpStatus.CONFLICT),
    PAYMENT_EXPIRED(4006, "Payment đã hết hạn", HttpStatus.CONFLICT),
    PAYMENT_DATA_MISMATCH(4007, "Số tiền hoặc tài khoản nhận không khớp", HttpStatus.BAD_REQUEST),
    PAYMENT_PROVIDER_MISMATCH(4008, "Payment provider không khớp", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_CALLBACK(4009, "Callback thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_GHTK_WEBHOOK(4010, "GHTK webhook hash không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_TRANSITION(4011, "Không thể chuyển trạng thái đơn hàng", HttpStatus.CONFLICT),
    SHIPMENT_CANNOT_CANCEL(4012, "Không thể hủy vận đơn", HttpStatus.CONFLICT),
    INVALID_PRODUCT_PRICE(4013, "Giá sản phẩm không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CART_QUANTITY(4014, "Số lượng giỏ hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    STOCK_EXCEEDED(4015, "Số lượng vượt quá tồn kho", HttpStatus.BAD_REQUEST),
    FULFILLMENT_NOT_FOUND(4016, "Không tìm thấy fulfillment", HttpStatus.NOT_FOUND),
    SHIPMENT_NOT_FOUND(4017, "Không tìm thấy vận đơn", HttpStatus.NOT_FOUND),
    TRANSACTION_NOT_FOUND(4018, "Không tìm thấy giao dịch", HttpStatus.NOT_FOUND),
    PAYMENT_PROVIDER_UNAVAILABLE(4019, "Payment provider hiện không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),
    PAYMENT_CONFIGURATION_INVALID(4020, "Cấu hình payment provider chưa đầy đủ", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_CHECKSUM_INVALID(4021, "Checksum thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    PAYMENT_SERIALIZATION_FAILED(4022, "Không thể tạo dữ liệu thông báo thanh toán", HttpStatus.INTERNAL_SERVER_ERROR),
    SHIPPING_PROVIDER_UNAVAILABLE(4025, "Đơn vị vận chuyển hiện không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),
    SHIPPING_RESPONSE_INVALID(4026, "Phản hồi từ đơn vị vận chuyển không hợp lệ", HttpStatus.BAD_GATEWAY),
    SHIPPING_REQUEST_FAILED(4027, "Không thể kết nối đơn vị vận chuyển", HttpStatus.BAD_GATEWAY),
    VIETQR_CONFIGURATION_INVALID(4028, "Cấu hình VietQR chưa đầy đủ", HttpStatus.INTERNAL_SERVER_ERROR),
    CONCURRENT_UPDATE(4029, "Dữ liệu đã được cập nhật bởi request khác, vui lòng tải lại và thử lại", HttpStatus.CONFLICT),
    INVALID_JSON(4030, "Dữ liệu JSON không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_TYPE(4031, "Kiểu dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),
    DATA_CONSTRAINT_VIOLATION(4032, "Dữ liệu bị trùng lặp hoặc vi phạm ràng buộc", HttpStatus.CONFLICT),
    OPERATION_CONFLICT(4033, "Không thể thực hiện thao tác ở trạng thái hiện tại", HttpStatus.CONFLICT);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}