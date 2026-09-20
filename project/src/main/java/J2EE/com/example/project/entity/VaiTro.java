package J2EE.com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vaitro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaitro_id")
    private Integer id;

    @Column(name = "vaitro_name", nullable = false)
    private String name;
}