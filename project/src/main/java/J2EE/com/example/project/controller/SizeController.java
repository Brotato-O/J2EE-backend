
package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

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
import org.springdoc.core.annotations.ParameterObject;

import J2EE.com.example.project.dto.request.SizeRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.SizeResponse;
import J2EE.com.example.project.service.SizeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {

    private final SizeService sizeService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<SizeResponse>>> getAll(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.LIST_RETRIEVED,
                        sizeService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<SizeResponse>> getById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_RETRIEVED,
                        sizeService.getById(id)));
    }

    // Chỉ Admin: thêm kích thước
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<SizeResponse>> create(
            @Valid @RequestBody SizeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                SuccessCode.RESOURCE_CREATED,
                                sizeService.create(request)));
    }

    // Chỉ Admin: cập nhật kích thước
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<SizeResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody SizeRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_UPDATED,
                        sizeService.update(id, request)));
    }

    // Chỉ Admin: xóa kích thước
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        sizeService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_DELETED,
                        null));
    }
}
