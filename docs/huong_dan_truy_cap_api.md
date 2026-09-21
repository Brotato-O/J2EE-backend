# HƯỚNG DẪN TRUY CẬP API

## 1. Tổng quan

API sử dụng JWT (JSON Web Token) để xác thực người dùng.

Quy trình truy cập API:

```text
1. Đăng ký tài khoản (nếu chưa có)
          │
          ▼
2. Đăng nhập
          │
          ▼
3. Nhận JWT Token
          │
          ▼
4. Gửi Token trong Header Authorization
          │
          ▼
5. Truy cập các API cần đăng nhập
          │
          ▼
6. Kiểm tra quyền theo vai trò (Role)
```

Base URL:

```text
http://localhost:8080/api/v1
```

---

## 2. Đăng ký tài khoản

Đăng ký không yêu cầu đăng nhập trước.

### API

```http
POST /api/v1/auth/register
```

URL đầy đủ:

```text
http://localhost:8080/api/v1/auth/register
```

### Header

```http
Content-Type: application/json
```

### Body

```json
{
  "name": "Nguyen Van A",
  "password": "123456",
  "email": "nguyenvana@gmail.com",
  "phone": "0901234567",
  "address": "TP. Ho Chi Minh"
}
```

### Lưu ý

- Không gửi `roleId` khi đăng ký.
- Tài khoản đăng ký mới được gán mặc định vai trò **User/Khách hàng**.
- Mật khẩu được mã hóa bằng BCrypt trước khi lưu vào database.
- Trạng thái tài khoản được tạo ở trạng thái hoạt động theo cấu hình hiện tại của hệ thống.

### Kết quả

Nếu đăng ký thành công, API trả về thông tin đăng nhập và JWT Token.

Ví dụ:

```json
{
  "code": 200,
  "message": "Đăng ký thành công",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9....",
    "tokenType": "Bearer",
    "userId": 60,
    "name": "Nguyen Van A",
    "email": "nguyenvana@gmail.com",
    "roleId": 2,
    "roleName": "User"
  }
}
```

---

## 3. Đăng nhập

Nếu đã có tài khoản, thực hiện đăng nhập để lấy JWT Token.

### API

```http
POST /api/v1/auth/login
```

URL đầy đủ:

```text
http://localhost:8080/api/v1/auth/login
```

### Header

```http
Content-Type: application/json
```

### Body

```json
{
  "email": "nguyenvana@gmail.com",
  "password": "123456"
}
```

### Kết quả

Ví dụ:

```json
{
  "code": 200,
  "message": "Đăng nhập thành công",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9....",
    "tokenType": "Bearer",
    "userId": 60,
    "name": "Nguyen Van A",
    "email": "nguyenvana@gmail.com",
    "roleId": 2,
    "roleName": "User"
  }
}
```

**Quan trọng:** Lấy giá trị `token` trong response để sử dụng cho các API yêu cầu đăng nhập.

---

## 4. Gửi JWT Token khi truy cập API

Các API khác ngoài:

```text
/api/v1/auth/register
/api/v1/auth/login
```

được cấu hình yêu cầu xác thực.

Khi gọi API cần đăng nhập, thêm Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Ví dụ:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9....
```

### Trong Postman

Có thể thực hiện theo cách:

```text
Authorization
→ Type: Bearer Token
→ Token: dán JWT Token vào đây
```

Không nhập chữ `Bearer` vào ô Token nếu Postman đang dùng loại **Bearer Token**, vì Postman sẽ tự thêm tiền tố này.

---

## 5. Truy cập API Users

### 5.1. Lấy danh sách users

```http
GET /api/v1/users
```

URL:

```text
http://localhost:8080/api/v1/users
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Ví dụ phân trang:

```text
GET /api/v1/users?page=0&size=10
```

---

### 5.2. Lấy user theo ID

```http
GET /api/v1/users/{id}
```

Ví dụ:

```text
GET http://localhost:8080/api/v1/users/45
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

### 5.3. Tạo user

```http
POST /api/v1/users
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

Body mẫu:

```json
{
  "name": "User Test",
  "password": "123456",
  "email": "usertest@gmail.com",
  "phone": "0901234567",
  "address": "TP. Ho Chi Minh",
  "roleId": 2,
  "status": 0
}
```

API này cần quyền phù hợp theo cấu hình `@PreAuthorize` hiện tại.

---

### 5.4. Cập nhật user

```http
PUT /api/v1/users/{id}
```

Ví dụ:

```text
PUT http://localhost:8080/api/v1/users/45
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

Body phụ thuộc vào `UserUpdateRequest` hiện tại của dự án.

---

### 5.5. Xóa user

```http
DELETE /api/v1/users/{id}
```

Ví dụ:

```text
DELETE http://localhost:8080/api/v1/users/45
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

API này cần quyền phù hợp theo cấu hình phân quyền hiện tại.

---

## 6. Phân quyền theo Role

Hệ thống sử dụng **Role**, không sử dụng bảng `quyen`.

Các vai trò hiện có:

| Role ID | Role Name | Mô tả |
|---:|---|---|
| 1 | Admin | Quản trị hệ thống |
| 2 | User | Khách hàng/người dùng |
| 3 | Nhân Viên | Nhân viên |
| 4 | Nhân Viên Kho | Nhân viên kho |

Role được lấy từ bảng:

```text
vaitro
```

và liên kết với:

```text
khachhang.vaitro_id
```

---

## 7. Quy trình kiểm tra quyền

Sau khi đăng nhập:

```text
User
 │
 │ email + password
 ▼
POST /api/v1/auth/login
 │
 ▼
AuthenticationManager
 │
 ▼
CustomUserDetailsService
 │
 ▼
Kiểm tra tài khoản trong khachhang
 │
 ▼
Lấy vai trò từ vaitro
 │
 ▼
Tạo JWT Token
 │
 ▼
Client nhận Token
```

Khi gọi API:

```text
Client
 │
 │ Authorization: Bearer <token>
 ▼
JwtAuthenticationFilter
 │
 ▼
Kiểm tra JWT
 │
 ▼
Lấy email từ Token
 │
 ▼
Tải User + Role
 │
 ▼
SecurityContext
 │
 ▼
@PreAuthorize kiểm tra Role
 │
 ├── Có quyền ──► Controller
 │
 └── Không có quyền ──► Từ chối truy cập
```

---

## 8. API nào không cần Token?

Hiện tại hai API xác thực được cấu hình `permitAll()`:

| Method | API | Token |
|---|---|---|
| POST | `/api/v1/auth/register` | Không cần |
| POST | `/api/v1/auth/login` | Không cần |

Các API khác mặc định yêu cầu xác thực vì SecurityConfig sử dụng:

```java
.anyRequest().authenticated()
```

---

## 9. Trường hợp bị 401 Unauthorized

Nếu gọi API mà nhận:

```text
401 Unauthorized
```

Kiểm tra:

1. Đã đăng nhập chưa?
2. JWT Token có được lấy từ API login không?
3. Header có đúng không?

```http
Authorization: Bearer <JWT_TOKEN>
```

4. Token có hết hạn không?
5. Token có bị sửa hoặc thiếu ký tự không?
6. Tài khoản có tồn tại trong database không?
7. Tài khoản có đang ở trạng thái cho phép đăng nhập không?

---

## 10. Trường hợp bị 403 Forbidden

Nếu nhận:

```text
403 Forbidden
```

thường có nghĩa là:

```text
Đã xác thực thành công
        ↓
Nhưng Role hiện tại
không được phép truy cập API
```

Ví dụ:

```text
User → gọi API chỉ dành cho Admin
                    ↓
                 403
```

Cần kiểm tra Role của tài khoản trong bảng:

```text
khachhang.vaitro_id
```

và tên Role trong:

```text
vaitro.vaitro_name
```

---

## 11. Thứ tự test API bằng Postman

Nên test theo thứ tự:

```text
BƯỚC 1
POST /api/v1/auth/register
        │
        ▼
BƯỚC 2
POST /api/v1/auth/login
        │
        ▼
Lấy token
        │
        ▼
BƯỚC 3
Postman → Authorization
        → Bearer Token
        → Dán token
        │
        ▼
BƯỚC 4
GET /api/v1/users
        │
        ▼
BƯỚC 5
GET /api/v1/users/{id}
        │
        ▼
BƯỚC 6
POST /api/v1/users
PUT /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

---

## 12. Tóm tắt

```text
                 ┌──────────────────┐
                 │  REGISTER        │
                 │ /auth/register   │
                 └────────┬─────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │     LOGIN        │
                 │   /auth/login    │
                 └────────┬─────────┘
                          │
                          ▼
                 ┌──────────────────┐
                 │    JWT TOKEN     │
                 └────────┬─────────┘
                          │
                          ▼
             Authorization: Bearer TOKEN
                          │
                          ▼
              ┌──────────────────────┐
              │      API khác        │
              │ /users, /products... │
              └──────────┬───────────┘
                         │
                         ▼
                  Kiểm tra JWT
                         │
                         ▼
                   Kiểm tra Role
                         │
                 ┌───────┴───────┐
                 ▼               ▼
              Có quyền       Không quyền
                 │               │
                 ▼               ▼
             200 OK             403
```

**Nguyên tắc chính:** Đăng ký/đăng nhập trước → lấy JWT → gửi JWT trong `Authorization` → hệ thống xác thực → kiểm tra Role → mới cho phép truy cập API tương ứng.
