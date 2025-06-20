package pe.edu.vallegrande.vg_ms_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.dto.SaleRequest;
import pe.edu.vallegrande.vg_ms_product.dto.SaleResponse;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.SaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailService saleDetailService;
    private final ProductoService productoService;

    public Mono<Sale> createSale(Sale sale) {
        sale.setId(null);
        return saleRepository.save(sale);
    }

public Mono<SaleResponse> createSaleWithDetails(SaleRequest saleRequest) {
    Sale sale = saleRequest.getSale();
    sale.setId(null); // Nuevo registro

    return saleRepository.save(sale)
        .flatMap(savedSale ->
            Flux.fromIterable(saleRequest.getDetails())
                .flatMap(detail -> {
                    Long productIdLong = detail.getProductId().longValue();
                    return productoService.getProductById(productIdLong)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Producto no encontrado con ID: " + productIdLong)))
                        .flatMap(product -> {
                            int cantidad = detail.getPackages();
                            if (product.getStock() < cantidad) {
                                return Mono.error(new IllegalArgumentException("Stock insuficiente para el producto ID: " + product.getId()));
                            }
                            return productoService.reduceStock(product.getId(), cantidad)
                                .map(updatedProduct -> {
                                    BigDecimal subtotal = updatedProduct.getPackageWeight()
                                        .multiply(BigDecimal.valueOf(cantidad))
                                        .multiply(updatedProduct.getPricePerKilo());

                                    detail.setSaleId(savedSale.getId());
                                    detail.setSubtotal(subtotal);
                                    return detail;
                                });
                        });
                })
                .flatMap(saleDetailService::saveSaleDetail)
                .collectList()
                .flatMap(savedDetails -> {
                    BigDecimal total = savedDetails.stream()
                        .map(SaleDetail::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                    savedSale.setTotalPrice(total);
                    return saleRepository.save(savedSale)
                        .map(updatedSale -> new SaleResponse(updatedSale, savedDetails));
                })
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

    public Flux<SaleResponse> getAllSalesWithDetails() {
        return saleRepository.findAll()
            .flatMap(sale ->
                saleDetailService.getDetailsBySaleId(sale.getId())
                    .collectList()
                    .map(details -> new SaleResponse(sale, details))
            );
    }

    public Mono<SaleResponse> getSaleWithDetails(Long id) {
        return saleRepository.findById(id)
            .flatMap(sale ->
                saleDetailService.getDetailsBySaleId(sale.getId())
                    .collectList()
                    .map(details -> new SaleResponse(sale, details))
            );
    }
}
