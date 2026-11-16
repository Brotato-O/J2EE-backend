package J2EE.com.example.project.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    REGISTERED(201, "Đăng ký thành công"),
    LOGGED_IN(200, "Đăng nhập thành công"),
    LIST_RETRIEVED(200, "Lấy danh sách thành công"),
    RESOURCE_RETRIEVED(200, "Lấy thông tin thành công"),
    RESOURCE_CREATED(200, "Tạo thành công"),
    RESOURCE_UPDATED(200, "Cập nhật thành công"),
    RESOURCE_DELETED(200, "Xóa thành công"),
    IMAGE_RETRIEVED(200, "Lấy ảnh thành công");

    private final int code;
    private final String message;
}
