
package J2EE.com.example.project.controller;

import J2EE.com.example.project.dto.response.ApiResponse;
import J2EE.com.example.project.dto.response.ImageResponse;
import J2EE.com.example.project.service.ImageService;
import com.cloudinary.Cloudinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageResponse>> getImageByPublicId(
            @PathVariable String publicId
    ) {
        ImageResponse response =
                imageService.getImageByPublicId(publicId);

        return ResponseEntity.ok(ApiResponse.success("Lấy ảnh thành công", response));
    }
}
