package J2EE.com.example.project.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.user.UserCreateRequest;
import J2EE.com.example.project.dto.request.user.UserUpdateRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.user.UserResponse;
import J2EE.com.example.project.entity.VaiTro;
import J2EE.com.example.project.entity.KhachHang;
import J2EE.com.example.project.mapper.UserMapper;
import J2EE.com.example.project.repository.VaiTroRepository;
import J2EE.com.example.project.repository.KhachHangRepository;
import J2EE.com.example.project.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final KhachHangRepository khachHangRepository;
        private final VaiTroRepository vaiTroRepository;
        private final UserMapper userMapper;

        @Transactional(readOnly = true)
        @Override
        public PageResponse<UserResponse> getAllUsers(Pageable pageable) {

                Page<KhachHang> page = khachHangRepository.findAll(pageable);

                List<UserResponse> content = page.getContent()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();

                return PageResponse.<UserResponse>builder()
                                .content(content)
                                .pageNo(page.getNumber())
                                .pageSize(page.getSize())
                                .totalElements(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .last(page.isLast())
                                .build();
        }

        @Transactional(readOnly = true)
        @Override
        public UserResponse getUserById(Integer id) {

                KhachHang user = khachHangRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

                return userMapper.toResponse(user);
        }

        @Transactional
        @Override
        public UserResponse createUser(
                        UserCreateRequest request) {

                if (khachHangRepository.existsByEmail(
                                request.getEmail())) {

                        throw new RuntimeException(
                                        "Email đã tồn tại");
                }

                VaiTro role = vaiTroRepository.findById(
                                request.getRoleId()).orElseThrow(
                                                () -> new RuntimeException(
                                                                "Không tìm thấy vai trò"));

                KhachHang user = KhachHang.builder()
                                .name(request.getName())
                                .password(request.getPassword())
                                .email(request.getEmail())
                                .phone(request.getPhone())
                                .address(request.getAddress())
                                .status(
                                                request.getStatus() != null
                                                                ? request.getStatus()
                                                                : 0)
                                .vaiTro(role)
                                .build();

                KhachHang saved = khachHangRepository.save(user);

                return userMapper.toResponse(saved);
        }

        @Transactional
        @Override
        public UserResponse updateUser(
                        Integer id,
                        UserUpdateRequest request) {

                KhachHang user = khachHangRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Không tìm thấy user"));

                VaiTro role = vaiTroRepository.findById(
                                request.getRoleId()).orElseThrow(
                                                () -> new RuntimeException(
                                                                "Không tìm thấy vai trò"));

                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
                user.setAddress(request.getAddress());
                user.setStatus(request.getStatus());
                user.setVaiTro(role);

                KhachHang updated = khachHangRepository.save(user);

                return userMapper.toResponse(updated);
        }

        @Transactional
        @Override
        public void deleteUser(Integer id) {

                KhachHang user = khachHangRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Không tìm thấy user"));

                // Soft delete
                user.setStatus(1);

                khachHangRepository.save(user);
        }
}