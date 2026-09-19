package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.SizeRequest;
import J2EE.com.example.project.dto.response.SizeResponse;
import J2EE.com.example.project.entity.Size;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    SizeResponse toResponse(Size entity);

    @Mapping(target = "id", ignore = true)
    Size toEntity(SizeRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(SizeRequest request, @org.mapstruct.MappingTarget Size entity);
}