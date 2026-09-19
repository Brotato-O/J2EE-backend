package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.ColorRequest;
import J2EE.com.example.project.dto.response.ColorResponse;
import J2EE.com.example.project.entity.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorResponse toResponse(Color entity);

    @Mapping(target = "id", ignore = true)
    Color toEntity(ColorRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ColorRequest request, @org.mapstruct.MappingTarget Color entity);
}