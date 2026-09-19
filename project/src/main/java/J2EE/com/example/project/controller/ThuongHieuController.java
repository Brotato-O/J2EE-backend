package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ThuongHieuResponse;
import J2EE.com.example.project.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ThuongHieuResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thương hiệu thành công",
                thuongHieuService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thương hiệu thành công",
                thuongHieuService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> create(
            @Valid @RequestBody ThuongHieuRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo thương hiệu thành công",
                thuongHieuService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ThuongHieuResponse>> update(@PathVariable Integer id,
            @Valid @RequestBody ThuongHieuRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thương hiệu thành công",
                thuongHieuService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        thuongHieuService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thương hiệu thành công", null));
    }
}