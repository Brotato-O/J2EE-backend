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

import J2EE.com.example.project.dto.request.ColorRequest;
import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.ColorResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.service.ColorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ColorResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách màu sắc thành công",
                colorService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin màu sắc thành công",
                colorService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColorResponse>> create(@Valid @RequestBody ColorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo màu sắc thành công", colorService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> update(@PathVariable Integer id,
            @Valid @RequestBody ColorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật màu sắc thành công",
                colorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        colorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa màu sắc thành công", null));
    }
}