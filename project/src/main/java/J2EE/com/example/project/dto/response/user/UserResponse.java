package J2EE.com.example.project.dto.response.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private Integer status;

    private Integer roleId;

    private String roleName;
}