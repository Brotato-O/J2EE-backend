package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.user.UserCreateRequest;
import J2EE.com.example.project.dto.request.user.UserUpdateRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.user.UserResponse;

public interface UserService {

    PageResponse<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Integer id);

    UserResponse createUser(UserCreateRequest request);

    UserResponse updateUser(
            Integer id,
            UserUpdateRequest request);

    void deleteUser(Integer id);
}