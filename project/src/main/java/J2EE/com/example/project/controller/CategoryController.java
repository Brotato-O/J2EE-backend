
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

import J2EE.com.example.project.dto.request.CategoryRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.dto.response.CategoryResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách danh mục
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAll(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.LIST_RETRIEVED,
                        categoryService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết danh mục
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_RETRIEVED,
                        categoryService.getById(id)));
    }

    // Chỉ Admin: thêm danh mục
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                SuccessCode.RESOURCE_CREATED,
                                categoryService.create(request)));
    }

    // Chỉ Admin: cập nhật danh mục
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_UPDATED,
                        categoryService.update(id, request)));
    }

    // Chỉ Admin: xóa danh mục
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        categoryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_DELETED,
                        null));
    }
}
