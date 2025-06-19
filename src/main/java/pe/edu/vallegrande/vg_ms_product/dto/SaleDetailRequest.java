package pe.edu.vallegrande.vg_ms_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailRequest {
    private Long productId;
    private Integer packages;
}
