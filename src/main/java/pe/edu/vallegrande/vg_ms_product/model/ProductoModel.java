package pe.edu.vallegrande.vg_ms_product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
public class ProductoModel {

    @Id
    private Long id;

    private String type;

    private String description;

    @Column("package_weight")
    private BigDecimal packageWeight;

    private Integer stock;

    @Column("entry_date")
    private LocalDate entryDate;

    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("type_product")
    private String typeProduct;

    @Column("price_per_kilo")
    private BigDecimal pricePerKilo;

    @Builder.Default
    private String status = "A";
}
