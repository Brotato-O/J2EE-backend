package J2EE.com.example.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NhaCungCapResponse {

    private final Integer id;
    private final String name;
    private final String address;
    private final String phone;
    private final String email;
    private final Integer status;
}