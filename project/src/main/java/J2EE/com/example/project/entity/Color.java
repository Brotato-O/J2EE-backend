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
@Table(name = "color")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Integer id;

    @Column(name = "color_name", nullable = false, length = 50)
    private String name;

    @Column(name = "color_ma", nullable = false, length = 50)
    private String code;
}