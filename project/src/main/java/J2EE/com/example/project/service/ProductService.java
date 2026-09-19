package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.ProductCreationRequest;
import J2EE.com.example.project.dto.request.ProductUpdateRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ProductResponse;

public interface ProductService {

    PageResponse<ProductResponse> getAll(Pageable pageable);

    ProductResponse getById(Integer id);

    ProductResponse create(ProductCreationRequest request);

    ProductResponse update(Integer id, ProductUpdateRequest request);

    void delete(Integer id);
}