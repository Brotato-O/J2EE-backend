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
@Table(name = "thuong_hieu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThuongHieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_thuong_hieu", nullable = false, length = 255)
    private String name;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String description;
}