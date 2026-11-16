
package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import J2EE.com.example.project.dto.request.ProductCreationRequest;
import J2EE.com.example.project.dto.request.ProductUpdateRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ProductResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAll(
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LIST_RETRIEVED,
                productService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_RETRIEVED,
                productService.getById(id)));
    }

    // Chỉ Admin: thêm sản phẩm
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessCode.RESOURCE_CREATED,
                        productService.create(request)));
    }

    // Chỉ Admin: cập nhật sản phẩm
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_UPDATED,
                productService.update(id, request)));
    }

    // Chỉ Admin: xóa sản phẩm
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_DELETED, null));
    }
}
