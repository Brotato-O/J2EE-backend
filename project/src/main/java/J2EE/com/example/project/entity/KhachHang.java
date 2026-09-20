package J2EE.com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "khachhang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kh_id")
    private Integer id;

    @Column(name = "kh_name", nullable = false)
    private String name;

    @Column(name = "kh_pass", nullable = false)
    private String password;

    @Column(name = "kh_mail", nullable = false)
    private String email;

    @Column(name = "kh_tel")
    private String phone;

    @Column(name = "kh_address")
    private String address;

    @Column(name = "trangthai")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaitro_id")
    private VaiTro vaiTro;
}