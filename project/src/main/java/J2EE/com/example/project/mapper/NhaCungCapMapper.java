package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.NhaCungCapRequest;
import J2EE.com.example.project.dto.response.NhaCungCapResponse;
import J2EE.com.example.project.entity.NhaCungCap;

@Mapper(componentModel = "spring")
public interface NhaCungCapMapper {

    NhaCungCapResponse toResponse(NhaCungCap entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    NhaCungCap toEntity(NhaCungCapRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(NhaCungCapRequest request, @org.mapstruct.MappingTarget NhaCungCap entity);
}