package J2EE.com.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.configuration.JwtService;
import J2EE.com.example.project.dto.request.auth.LoginRequest;
import J2EE.com.example.project.dto.request.auth.RegisterRequest;
import J2EE.com.example.project.dto.response.auth.LoginResponse;
import J2EE.com.example.project.entity.KhachHang;
import J2EE.com.example.project.entity.VaiTro;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.repository.KhachHangRepository;
import J2EE.com.example.project.repository.VaiTroRepository;
import J2EE.com.example.project.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final KhachHangRepository khachHangRepository;
        private final VaiTroRepository vaiTroRepository;

        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        private final CustomUserDetailsService userDetailsService;
        private final JwtService jwtService;

        @Override
        @Transactional
        public LoginResponse register(RegisterRequest request) {

                // 1. Kiểm tra email đã tồn tại
                if (khachHangRepository.existsByEmail(request.getEmail())) {

                        throw new AppException(
                                        ErrorCode.EMAIL_ALREADY_EXISTS);
                }

                // 2. Lấy role khách hàng mặc định
                // Role ID = 2
                VaiTro role = vaiTroRepository.findById(2)
                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

                // 3. Tạo user
                KhachHang user = KhachHang.builder()
                                .name(request.getName())
                                .password(
                                                passwordEncoder.encode(
                                                                request.getPassword()))
                                .email(request.getEmail())
                                .phone(request.getPhone())
                                .address(request.getAddress())

                                // 0 = hoạt động
                                .status(0)

                                // mặc định là khách hàng
                                .vaiTro(role)

                                .build();

                // 4. Lưu database
                KhachHang savedUser = khachHangRepository.save(user);

                // 5. Tạo UserDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                                savedUser.getEmail());

                // 6. Tạo JWT
                String token = jwtService.generateToken(
                                userDetails,
                                role.getName());

                // 7. Trả response
                return LoginResponse.builder()
                                .token(token)
                                .tokenType("Bearer")
                                .userId(savedUser.getId())
                                .name(savedUser.getName())
                                .email(savedUser.getEmail())
                                .roleId(role.getId())
                                .roleName(role.getName())
                                .build();
        }

        @Override
        public LoginResponse login(LoginRequest request) {

                System.out.println("========== LOGIN ==========");
                System.out.println("Email: " + request.getEmail());

                try {

                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));

                        System.out.println("AUTHENTICATION SUCCESS");

                } catch (Exception e) {

                        System.out.println("AUTHENTICATION ERROR: "
                                        + e.getClass().getName());

                        System.out.println("MESSAGE: "
                                        + e.getMessage());

                        throw new AppException(
                                        ErrorCode.INVALID_CREDENTIALS);
                }

                KhachHang user = khachHangRepository
                                .findByEmail(request.getEmail())
                                .orElseThrow(() -> new AppException(
                                                ErrorCode.USER_NOT_FOUND));

                if (user.getStatus() != 0) {
                        throw new AppException(
                                        ErrorCode.ACCOUNT_DISABLED);
                }

                UserDetails userDetails = userDetailsService
                                .loadUserByUsername(user.getEmail());

                String token = jwtService.generateToken(
                                userDetails,
                                user.getVaiTro().getName());

                return LoginResponse.builder()
                                .token(token)
                                .tokenType("Bearer")
                                .userId(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .roleId(user.getVaiTro().getId())
                                .roleName(user.getVaiTro().getName())
                                .build();
        }
}