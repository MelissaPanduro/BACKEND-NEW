package pe.edu.vallegrande.vg_ms_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.dto.SaleRequest;
import pe.edu.vallegrande.vg_ms_product.dto.SaleResponse;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.repository.SaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailService saleDetailService;

    public Mono<Sale> createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Mono<Sale> createSaleWithDetails(SaleRequest saleRequest) {
        return saleRepository.save(saleRequest.getSale())
            .flatMap(savedSale -> Flux.fromIterable(saleRequest.getDetails())
                .map(detail -> {
                    detail.setSaleId(savedSale.getId());
                    return detail;
                })
                .flatMap(saleDetailService::saveSaleDetail)
                .then(Mono.just(savedSale))
            );
    }

    public Mono<Sale> updateSale(Long id, Sale sale) {
        return saleRepository.findById(id)
                .flatMap(existingSale -> {
                    sale.setId(id);
                    return saleRepository.save(sale);
                });
    }

    public Mono<Void> deleteSale(Long id) {
        return saleRepository.deleteById(id);
    }

    public Flux<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Mono<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Mono<Sale> getSaleByDocument(String document) {
        return saleRepository.findByRuc(document);
    }

    // ✅ Nuevo método para listar todas las ventas con sus detalles
    public Flux<SaleResponse> getAllSalesWithDetails() {
        return saleRepository.findAll()
            .flatMap(sale ->
                saleDetailService.getDetailsBySaleId(sale.getId())
                    .collectList()
                    .map(details -> new SaleResponse(sale, details))
            );
    }


    // ✅ Nuevo método: obtener venta junto con sus detalles
    public Mono<SaleResponse> getSaleWithDetails(Long id) {
        return saleRepository.findById(id)
                .flatMap(sale ->
                        saleDetailService.getDetailsBySaleId(sale.getId())
                                .collectList()
                                .map(details -> new SaleResponse(sale, details))
                );
    }
}
