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
import org.springdoc.core.annotations.ParameterObject;

import J2EE.com.example.project.dto.request.SizeRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.SizeResponse;
import J2EE.com.example.project.service.SizeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {

    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SizeResponse>>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách kích thước thành công",
                sizeService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin kích thước thành công",
                sizeService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SizeResponse>> create(@Valid @RequestBody SizeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo kích thước thành công", sizeService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> update(@PathVariable Integer id,
            @Valid @RequestBody SizeRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật kích thước thành công",
                sizeService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        sizeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa kích thước thành công", null));
    }
}