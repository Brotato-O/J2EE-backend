package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
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

import J2EE.com.example.project.dto.request.NhaCungCapRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.NhaCungCapResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class NhaCungCapController {

    private final NhaCungCapService nhaCungCapService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<NhaCungCapResponse>>> getAll(
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách nhà cung cấp thành công",
                nhaCungCapService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin nhà cung cấp thành công",
                nhaCungCapService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> create(
            @Valid @RequestBody NhaCungCapRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo nhà cung cấp thành công",
                nhaCungCapService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NhaCungCapResponse>> update(@PathVariable Integer id,
            @Valid @RequestBody NhaCungCapRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật nhà cung cấp thành công",
                nhaCungCapService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        nhaCungCapService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa nhà cung cấp thành công", null));
    }
}