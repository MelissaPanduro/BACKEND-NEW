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

    // Crear o actualizar un detalle
    public Mono<SaleDetail> saveSaleDetail(SaleDetail saleDetail) {
        return saleDetailRepository.save(saleDetail);
    }

    // Obtener todos los detalles de una venta
    public Flux<SaleDetail> getDetailsBySaleId(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    // Obtener detalle por id
    public Mono<SaleDetail> getDetailById(Long id) {
        return saleDetailRepository.findById(id);
    }

    // Eliminar un detalle
    public Mono<Void> deleteDetail(Long id) {
        return saleDetailRepository.deleteById(id);
    }
}
