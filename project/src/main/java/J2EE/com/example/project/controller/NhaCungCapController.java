
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

import J2EE.com.example.project.dto.request.NhaCungCapRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.NhaCungCapResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class NhaCungCapController {

    private final NhaCungCapService nhaCungCapService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<NhaCungCapResponse>>> getAll(
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LIST_RETRIEVED,
                nhaCungCapService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> getById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_RETRIEVED,
                nhaCungCapService.getById(id)));
    }

    // Chỉ Admin: thêm nhà cung cấp
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> create(
            @Valid @RequestBody NhaCungCapRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessCode.RESOURCE_CREATED,
                        nhaCungCapService.create(request)));
    }

    // Chỉ Admin: cập nhật nhà cung cấp
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody NhaCungCapRequest request) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_UPDATED,
                nhaCungCapService.update(id, request)));
    }

    // Chỉ Admin: xóa nhà cung cấp
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        nhaCungCapService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.RESOURCE_DELETED, null));
    }
}
