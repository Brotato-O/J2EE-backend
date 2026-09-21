package J2EE.com.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import J2EE.com.example.project.entity.KhachHang;
import J2EE.com.example.project.repository.KhachHangRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final KhachHangRepository khachHangRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email) throws UsernameNotFoundException {

        KhachHang user = khachHangRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy tài khoản"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" +
                                                user.getVaiTro().getName())))
                .disabled(user.getStatus() != 0)
                .build();
    }
}