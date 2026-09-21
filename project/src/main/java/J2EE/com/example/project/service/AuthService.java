package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.request.auth.LoginRequest;
import J2EE.com.example.project.dto.request.auth.RegisterRequest;
import J2EE.com.example.project.dto.response.auth.LoginResponse;

public interface AuthService {

    LoginResponse register(
            RegisterRequest request);

    LoginResponse login(
            LoginRequest request);
}