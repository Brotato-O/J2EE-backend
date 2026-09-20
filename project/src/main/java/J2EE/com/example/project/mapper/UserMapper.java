package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.response.user.UserResponse;
import J2EE.com.example.project.entity.KhachHang;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "vaiTro.id", target = "roleId")
    @Mapping(source = "vaiTro.name", target = "roleName")
    UserResponse toResponse(KhachHang user);
}