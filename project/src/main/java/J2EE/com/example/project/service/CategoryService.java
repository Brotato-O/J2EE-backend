package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.CategoryRequest;
import J2EE.com.example.project.dto.response.CategoryResponse;
import J2EE.com.example.project.dto.response.PageResponse;

public interface CategoryService {

    PageResponse<CategoryResponse> getAll(Pageable pageable);

    CategoryResponse getById(Integer id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Integer id, CategoryRequest request);

    void delete(Integer id);
}