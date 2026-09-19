package J2EE.com.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ColorRequest {

    @NotBlank(message = "Tên màu không được để trống")
    @Size(max = 50, message = "Tên màu không được vượt quá 50 ký tự")
    private String name;

    @NotBlank(message = "Mã màu không được để trống")
    @Size(max = 50, message = "Mã màu không được vượt quá 50 ký tự")
    private String code;
}