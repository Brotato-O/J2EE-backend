package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.VariantRequest;
import J2EE.com.example.project.dto.response.VariantResponse;
import J2EE.com.example.project.entity.ProChitiet;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "sizeId", source = "size.id")
    @Mapping(target = "sizeName", source = "size.name")
    VariantResponse toResponse(ProChitiet entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    ProChitiet toEntity(VariantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    void updateEntity(VariantRequest request, @org.mapstruct.MappingTarget ProChitiet entity);
}