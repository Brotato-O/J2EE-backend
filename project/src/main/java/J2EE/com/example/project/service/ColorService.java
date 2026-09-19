package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.ColorRequest;
import J2EE.com.example.project.dto.response.ColorResponse;
import J2EE.com.example.project.dto.response.PageResponse;

public interface ColorService {

    PageResponse<ColorResponse> getAll(Pageable pageable);

    ColorResponse getById(Integer id);

    ColorResponse create(ColorRequest request);

    ColorResponse update(Integer id, ColorRequest request);

    void delete(Integer id);
}