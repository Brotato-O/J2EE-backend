package J2EE.com.example.project.dto.response.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    private String tokenType;

    private Integer userId;

    private String name;

    private String email;

    private Integer roleId;

    private String roleName;
}