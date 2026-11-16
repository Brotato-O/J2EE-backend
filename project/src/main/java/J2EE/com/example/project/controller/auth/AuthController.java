package J2EE.com.example.project.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.auth.LoginRequest;
import J2EE.com.example.project.dto.request.auth.RegisterRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.dto.response.auth.LoginResponse;
import J2EE.com.example.project.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<LoginResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ApiResponse.<LoginResponse>builder()
                .code(SuccessCode.REGISTERED.getCode())
                .message(SuccessCode.REGISTERED.getMessage())
                .data(
                        authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ApiResponse.<LoginResponse>builder()
                .code(SuccessCode.LOGGED_IN.getCode())
                .message(SuccessCode.LOGGED_IN.getMessage())
                .data(
                        authService.login(request))
                .build();
    }
}