# CẤU TRÚC VÀ QUY TẮC CODE SPRING BOOT

## 1. Cấu trúc thư mục (Layered Architecture)
Dự án áp dụng kiến trúc chuẩn với cấu trúc package như sau:

ProjectFinish/
├── src/main/java/com/Finish/projectCV/
│   ├── common/              # Enums (StatusOrder, StatusPayment), Constants
│   ├── configuration/       # Security, JWT, Cloudinary configs
│   ├── controller/          # REST API endpoints
│   ├── dto/
│   │   ├── request/         # Request DTOs (nhận dữ liệu từ Client)
│   │   └── response/        # Response DTOs
│   │       ├── ApiResponse.java       <-- Class bọc data trả về (code, message, data)
│   │       └── ...
│   ├── entity/              # JPA entities (map với table trong Database)
│   ├── exception/           # Custom exceptions & global handler
│   │   ├── AppException.java            <-- Custom Exception chính của dự án
│   │   ├── ErrorCode.java               <-- Enum chứa toàn bộ mã lỗi
│   │   └── GlobalExceptionHandler.java  <-- @ControllerAdvice bắt lỗi tập trung
│   ├── mapper/              # MapStruct interfaces
│   ├── repository/          # JPA repositories
│   ├── service/             # Chứa Interfaces của nghiệp vụ
│   │   └── impl/            # Chứa class triển khai logic nghiệp vụ (ServiceImpl)
│   └── validator/           # Custom Bean Validation
├── src/main/resources/
│   ├── application.yaml     # Application configuration
│   └── db/migration/        # (Tùy chọn) Chứa các file SQL Flyway quản lý version DB
└── pom.xml

## 2. Quy tắc Code & Entity
- Luôn dùng Lombok: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`. Không dùng `@Data` cho Entity.
- Tên bảng/cột map chính xác bằng `snake_case` (dùng `@Table` và `@Column`).

## 3. Quy tắc Logic & DTO
- Tuyệt đối không trả Entity trực tiếp qua Controller. Luôn dùng **MapStruct** chuyển đổi sang Response DTO.
- Mọi logic nghiệp vụ nằm ở tầng `service/impl`. Controller chỉ làm nhiệm vụ nhận Request và trả Response.

## 4. Xử lý Lỗi (Exception Handling)
- Mọi lỗi nghiệp vụ phải throw `AppException` kèm theo `ErrorCode` tương ứng.
- Lỗi sẽ được `GlobalExceptionHandler` bắt và trả về format JSON chuẩn của `ApiResponse`.