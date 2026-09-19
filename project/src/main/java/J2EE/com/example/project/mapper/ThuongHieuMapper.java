package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.ThuongHieuRequest;
import J2EE.com.example.project.dto.response.ThuongHieuResponse;
import J2EE.com.example.project.entity.ThuongHieu;

@Mapper(componentModel = "spring")
public interface ThuongHieuMapper {

    ThuongHieuResponse toResponse(ThuongHieu entity);

    @Mapping(target = "id", ignore = true)
    ThuongHieu toEntity(ThuongHieuRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ThuongHieuRequest request, @org.mapstruct.MappingTarget ThuongHieu entity);
}