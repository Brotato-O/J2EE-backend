package J2EE.com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nhacungcap")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ncc_id")
    private Integer id;

    @Column(name = "ncc_name", nullable = false, length = 255)
    private String name;

    @Column(name = "ncc_diachi", nullable = false, length = 255)
    private String address;

    @Column(name = "ncc_sdt", nullable = false, length = 20)
    private String phone;

    @Column(name = "ncc_email", nullable = false, length = 255)
    private String email;

    @Column(name = "ncc_trangthai", nullable = false)
    @Builder.Default
    private Integer status = 0;
}