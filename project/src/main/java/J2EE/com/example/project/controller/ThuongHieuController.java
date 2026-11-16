
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

import J2EE.com.example.project.dto.request.ThuongHieuRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ThuongHieuResponse;
import J2EE.com.example.project.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách thương hiệu
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<ThuongHieuResponse>>> getAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.LIST_RETRIEVED,
                        thuongHieuService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết thương hiệu
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> getById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_RETRIEVED,
                        thuongHieuService.getById(id)));
    }

    // Chỉ Admin: thêm thương hiệu
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> create(
            @Valid @RequestBody ThuongHieuRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                SuccessCode.RESOURCE_CREATED,
                                thuongHieuService.create(request)));
    }

    // Chỉ Admin: cập nhật thương hiệu
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ThuongHieuRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_UPDATED,
                        thuongHieuService.update(id, request)));
    }

    // Chỉ Admin: xóa thương hiệu
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        thuongHieuService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_DELETED,
                        null));
    }
}
