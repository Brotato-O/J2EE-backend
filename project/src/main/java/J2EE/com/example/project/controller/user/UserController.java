package J2EE.com.example.project.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.user.UserCreateRequest;
import J2EE.com.example.project.dto.request.user.UserUpdateRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.user.UserResponse;
import J2EE.com.example.project.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<PageResponse<UserResponse>> getAllUsers(
            Pageable pageable) {

        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(200)
                .message("Lấy danh sách users thành công")
                .data(userService.getAllUsers(pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable Integer id) {

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Lấy thông tin user thành công")
                .data(
                        userService.getUserById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Tạo user thành công")
                .data(
                        userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest request) {

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Cập nhật user thành công")
                .data(
                        userService.updateUser(
                                id,
                                request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(
            @PathVariable Integer id) {

        userService.deleteUser(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa user thành công")
                .build();
    }
}