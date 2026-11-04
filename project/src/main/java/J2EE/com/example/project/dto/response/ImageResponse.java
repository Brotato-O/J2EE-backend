package J2EE.com.example.project.dto.response;

public record ImageResponse(
        String publicId,
        String imageUrl,
        String format,
        int width,
        int height
) {}
