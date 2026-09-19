package J2EE.com.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SizeRequest {

    @NotBlank(message = "Tên kích thước không được để trống")
    @Size(max = 50, message = "Tên kích thước không được vượt quá 50 ký tự")
    private String name;
}