package J2EE.com.example.project.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SIZE_NOT_FOUND(2001, "Không tìm thấy kích thước"),
    COLOR_NOT_FOUND(2002, "Không tìm thấy màu sắc"),
    BRAND_NOT_FOUND(2003, "Không tìm thấy thương hiệu"),
    INVALID_REQUEST(400, "Dữ liệu đầu vào không hợp lệ");

    private final int code;
    private final String message;
}