package J2EE.com.example.project;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlBuilder {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Named("toImageUrl")
    public String build(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return null;
        }
        if (publicId.startsWith("http")) {
            return publicId;
        }
        return "https://res.cloudinary.com/" + cloudName
                + "/image/upload/" + publicId;
    }
}