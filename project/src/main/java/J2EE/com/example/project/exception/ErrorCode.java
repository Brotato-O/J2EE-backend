package J2EE.com.example.project.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SIZE_NOT_FOUND(2001, "Không tìm thấy kích thước"),
    COLOR_NOT_FOUND(2002, "Không tìm thấy màu sắc"),
    BRAND_NOT_FOUND(2003, "Không tìm thấy thương hiệu"),
    CATEGORY_NOT_FOUND(2004, "Không tìm thấy danh mục"),
    SUPPLIER_NOT_FOUND(2005, "Không tìm thấy nhà cung cấp"),
    PRODUCT_NOT_FOUND(2006, "Không tìm thấy sản phẩm"),
    VARIANT_NOT_FOUND(2007, "Không tìm thấy biến thể sản phẩm"),
    BRAND_REQUIRED(2008, "Thương hiệu là bắt buộc"),
    INVALID_REQUEST(400, "Dữ liệu đầu vào không hợp lệ"),

    EMAIL_ALREADY_EXISTS(3001, "Email đã tồn tại"),
    USER_NOT_FOUND(3002, "Không tìm thấy người dùng"),
    INVALID_CREDENTIALS(3003, "Email hoặc mật khẩu không chính xác"),
    ROLE_NOT_FOUND(3004, "Không tìm thấy vai trò"),
    ACCOUNT_DISABLED(3005, "Tài khoản đã bị khóa");

    private final int code;
    private final String message;
}