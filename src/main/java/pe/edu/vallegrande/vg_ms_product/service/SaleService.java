package pe.edu.vallegrande.vg_ms_product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_product.dto.SaleDto;
import pe.edu.vallegrande.vg_ms_product.dto.SaleDetailDto;
import pe.edu.vallegrande.vg_ms_product.dto.SaleResponse;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.repository.SaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailService saleDetailService;
    private final ProductoService productoService;

    public Mono<SaleResponse> save(SaleDto saleDto) {
        Sale sale = new Sale(null, saleDto.getSaleDate(), saleDto.getName(), saleDto.getRuc(), saleDto.getAddress(), null);
        return saleRepository.save(sale)
                .flatMap(savedSale -> {
                    List<SaleDetailDto> detailDtos = saleDto.getDetails();
                    return Flux.fromIterable(detailDtos)
                            .flatMap(detailDto -> {
                                Long productId = detailDto.getProductId();
                                int packages = detailDto.getPackages();

                                return productoService.getProductById(productId)
                                        .flatMap(product -> {
                                            BigDecimal totalWeight = product.getPackageWeight().multiply(BigDecimal.valueOf(packages));
                                            BigDecimal pricePerKg = product.getPricePerKilo();
                                            BigDecimal totalPrice = totalWeight.multiply(pricePerKg);

                                            SaleDetail detail = SaleDetail.builder()
                                                    .saleId(savedSale.getId())
                                                    .productId(productId)
                                                    .weight(product.getPackageWeight())
                                                    .packages(packages)
                                                    .totalWeight(totalWeight)
                                                    .pricePerKg(pricePerKg)
                                                    .subtotal(totalPrice)
                                                    .build();

                                            return productoService.reduceStock(productId, packages)
                                                    .then(saleDetailService.saveSaleDetail(detail));
                                        });
                            })
                            .collectList()
                            .flatMap(details -> {
                                BigDecimal total = details.stream()
                                        .map(SaleDetail::getSubtotal)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                savedSale.setTotalPrice(total);
                                return saleRepository.save(savedSale)
                                        .map(updatedSale -> new SaleResponse(updatedSale, details));
                            });
                });
    }

    public Mono<SaleResponse> update(Long id, SaleDto saleDto) {
        return saleRepository.findById(id)
                .flatMap(existingSale -> {
                    existingSale.setSaleDate(saleDto.getSaleDate());
                    existingSale.setName(saleDto.getName());
                    existingSale.setRuc(saleDto.getRuc());
                    existingSale.setAddress(saleDto.getAddress());

                    return saleRepository.save(existingSale)
                            .flatMap(savedSale -> saleDetailService.getDetailsBySaleId(savedSale.getId())
                                    .collectList()
                                    .map(details -> new SaleResponse(savedSale, details)));
                });
    }

    public Mono<Void> delete(Long id) {
        return saleRepository.deleteById(id);
    }

    public Mono<SaleResponse> getById(Long id) {
        return saleRepository.findById(id)
                .flatMap(sale -> saleDetailService.getDetailsBySaleId(sale.getId())
                        .collectList()
                        .map(details -> new SaleResponse(sale, details)));
    }

    public Flux<SaleResponse> findAll() {
        return saleRepository.findAll()
                .flatMap(sale -> saleDetailService.getDetailsBySaleId(sale.getId())
                        .collectList()
                        .map(details -> new SaleResponse(sale, details)));
    }
}
