package pe.edu.vallegrande.vg_ms_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail; // ✅ Importa el modelo correcto

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {
    private Sale sale;
    private List<SaleDetail> details; // ✅ Tipo correcto
}
