package pe.edu.vallegrande.vg_ms_product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("sale_detail")
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

@Column("subtotal")
private BigDecimal subtotal;

}
                                                