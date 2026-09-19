package J2EE.com.example.project.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Integer id;

    @Column(name = "pro_name", nullable = false, length = 255)
    private String name;

    @Column(name = "pro_img", nullable = false, length = 255)
    private String image;

    @Column(name = "pro_price", nullable = false)
    private Float price;

    @Column(name = "pro_desc", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "pro_brand", nullable = false, length = 55)
    private String brandName;

    @Column(name = "pro_stock", nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_brand", referencedColumnName = "ten_thuong_hieu", insertable = false, updatable = false)
    private ThuongHieu brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cate_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ncc_id", nullable = false)
    private NhaCungCap supplier;

    @Column(name = "trangthai", nullable = false)
    @Builder.Default
    private Integer status = 0;

    @Column(name = "pro_viewer", nullable = false)
    @Builder.Default
    private Integer viewer = 0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProChitiet> variants = new ArrayList<>();

    public void addVariant(ProChitiet variant) {
        variants.add(variant);
        variant.setProduct(this);
    }

    public void removeVariant(ProChitiet variant) {
        variants.remove(variant);
        variant.setProduct(null);
    }
}