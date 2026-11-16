
package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.ColorRequest;
import J2EE.com.example.project.dto.response.ColorResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.service.ColorService;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    // Admin, Nhân Viên, Nhân Viên Kho: xem danh sách màu
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<PageResponse<ColorResponse>>> getAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.LIST_RETRIEVED,
                        colorService.getAll(pageable)));
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem chi tiết màu
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<ColorResponse>> getById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_RETRIEVED,
                        colorService.getById(id)));
    }

    // Chỉ Admin: thêm màu
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ColorResponse>> create(
            @Valid @RequestBody ColorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                SuccessCode.RESOURCE_CREATED,
                                colorService.create(request)));
    }

    // Chỉ Admin: cập nhật màu
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<ColorResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ColorRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_UPDATED,
                        colorService.update(id, request)));
    }

    // Chỉ Admin: xóa màu
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer id) {

        colorService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.RESOURCE_DELETED,
                        null));
    }
}
