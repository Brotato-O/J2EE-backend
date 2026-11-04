package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.response.ImageResponse;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public ImageResponse getImageByPublicId(String publicId) {
        try {
            Map result = cloudinary.api().resource(publicId, Map.of());

            return new ImageResponse(
                    (String) result.get("public_id"),
                    (String) result.get("secure_url"),
                    (String) result.get("format"),
                    ((Number) result.get("width")).intValue(),
                    ((Number) result.get("height")).intValue()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Không thể lấy thông tin ảnh: " + publicId, e
            );
        }
    }
}