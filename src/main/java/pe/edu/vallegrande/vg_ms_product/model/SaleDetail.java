package pe.edu.vallegrande.vg_ms_product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("sale_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetail {

    @Id
    private Long id;

    @Column("sale_id")
    private Long saleId; // FK a tabla 'sale'

    @Column("product_id")
    private Integer productId;

    @Column("weight")
    private BigDecimal weight;

    @Column("packages")
    private Integer packages;

    @Column("total_weight")
    private BigDecimal totalWeight;
}
