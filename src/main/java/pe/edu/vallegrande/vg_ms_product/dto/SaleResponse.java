package pe.edu.vallegrande.vg_ms_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Sale sale;
    private List<SaleDetail> details;
}
