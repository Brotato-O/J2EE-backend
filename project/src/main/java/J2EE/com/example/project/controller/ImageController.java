
package J2EE.com.example.project.controller;

import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.exception.SuccessCode;
import J2EE.com.example.project.dto.response.ImageResponse;
import J2EE.com.example.project.service.ImageService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Admin, Nhân Viên, Nhân Viên Kho: xem thông tin hình ảnh
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    public ResponseEntity<ApiResponse<ImageResponse>> getImageByPublicId(
            @RequestParam String publicId) {

        ImageResponse response = imageService.getImageByPublicId(publicId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.IMAGE_RETRIEVED,
                        response));
    }
}
