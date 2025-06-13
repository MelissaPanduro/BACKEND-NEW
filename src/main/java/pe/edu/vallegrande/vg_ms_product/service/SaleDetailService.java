package pe.edu.vallegrande.vg_ms_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail;
import pe.edu.vallegrande.vg_ms_product.repository.SaleDetailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;

    // Obtener todos los detalles (necesario para GET /sale-details)
    public Flux<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.findAll();
    }

    // Crear o actualizar un detalle
    public Mono<SaleDetail> saveSaleDetail(SaleDetail saleDetail) {
        return saleDetailRepository.save(saleDetail);
    }

    // Obtener todos los detalles de una venta específica
    public Flux<SaleDetail> getDetailsBySaleId(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    // Obtener un detalle por ID
    public Mono<SaleDetail> getDetailById(Long id) {
        return saleDetailRepository.findById(id);
    }

    // Eliminar un detalle por ID
    public Mono<Void> deleteDetail(Long id) {
        return saleDetailRepository.deleteById(id);
    }
}
