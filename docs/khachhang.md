# CẤU TRÚC DATABASE - MODULE KHÁCH HÀNG / USER

Dưới đây là DDL mô tả các bảng liên quan đến quản lý khách hàng và vai trò người dùng.

Yêu cầu AI khi tạo Entity JPA phải map chính xác tên bảng, tên cột (bằng `@Table`, `@Column`) và thiết lập quan hệ `@ManyToOne`, `@OneToMany` dựa trên các khóa ngoại (FOREIGN KEY) trong database hiện tại.

> Lưu ý: Module User sử dụng bảng `khachhang` và `vaitro` có sẵn, không tạo bảng `users`, `roles` hoặc `quyen` mới.

```sql
-- 1. Bảng Vai trò
CREATE TABLE `vaitro` (
  `vaitro_id` int NOT NULL AUTO_INCREMENT,
  `vaitro_name` varchar(255) NOT NULL,
  PRIMARY KEY (`vaitro_id`)
);

-- 2. Bảng Khách hàng / User
CREATE TABLE `khachhang` (
  `kh_id` int NOT NULL AUTO_INCREMENT,
  `kh_name` varchar(255) NOT NULL,
  `kh_pass` varchar(255) NOT NULL,
  `kh_mail` varchar(255) NOT NULL,
  `kh_tel` varchar(20),
  `kh_address` varchar(255),
  `vaitro_id` int NOT NULL,
  `trangthai` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`kh_id`),
  CONSTRAINT `fk_khachhang_vaitro`
    FOREIGN KEY (`vaitro_id`) REFERENCES `vaitro` (`vaitro_id`)
);
```

## 3. Quan hệ giữa các bảng

Bảng `khachhang` lưu thông tin tài khoản người dùng và sử dụng `vaitro_id` để xác định vai trò.

Quan hệ:

```text
vaitro 1 ─────────── N khachhang
```

Một vai trò có thể được gán cho nhiều khách hàng/user, trong khi mỗi khách hàng/user thuộc một vai trò.

## 4. Ánh xạ Entity JPA

### VaiTro

```java
@Entity
@Table(name = "vaitro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaitro_id")
    private Integer id;

    @Column(name = "vaitro_name", nullable = false)
    private String name;
}
```

### KhachHang

```java
@Entity
@Table(name = "khachhang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kh_id")
    private Integer id;

    @Column(name = "kh_name", nullable = false)
    private String name;

    @Column(name = "kh_pass", nullable = false)
    private String password;

    @Column(name = "kh_mail", nullable = false)
    private String email;

    @Column(name = "kh_tel")
    private String phone;

    @Column(name = "kh_address")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vaitro_id")
    private VaiTro vaiTro;

    @Column(name = "trangthai")
    private Integer status;
}
```

## 5. Quy tắc sử dụng vai trò

Hệ thống phân quyền trực tiếp theo vai trò trong bảng `vaitro`, không sử dụng bảng quyền riêng.

Ví dụ các vai trò:

```text
1 - Admin
2 - User
3 - Nhân Viên
4 - Nhân Viên Kho
```

Khi đăng ký tài khoản mới, hệ thống mặc định gán vai trò khách hàng/User và client không được tự truyền `roleId`.

## 6. API liên quan đến Khách hàng / User

Base URL:

```text
/api/v1/users
```

Các API CRUD:

```text
GET     /api/v1/users
GET     /api/v1/users/{id}
POST    /api/v1/users
PUT     /api/v1/users/{id}
DELETE  /api/v1/users/{id}
```

Danh sách sử dụng `Pageable` và trả về `PageResponse<UserResponse>`.

## 7. Authentication

Module `khachhang` đồng thời được sử dụng cho đăng nhập và đăng ký:

```text
POST /api/v1/auth/register
POST /api/v1/auth/login
```

Mật khẩu được mã hóa bằng BCrypt.

Sau khi đăng nhập thành công, hệ thống tạo JWT Token. Client gửi token ở các request cần xác thực:

```http
Authorization: Bearer <token>
```

Spring Security sử dụng JWT để xác thực người dùng và kiểm tra vai trò.

## 8. Phân quyền

Phân quyền được thực hiện bằng Spring Security và `@PreAuthorize`.

Ví dụ:

```java
@PreAuthorize("hasRole('Admin')")
```

hoặc:

```java
@PreAuthorize("hasAnyRole('Admin', 'Nhân Viên')")
```

Các API không nằm trong danh sách `permitAll()` yêu cầu người dùng phải đăng nhập.

