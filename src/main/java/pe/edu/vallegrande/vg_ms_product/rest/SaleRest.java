package pe.edu.vallegrande.vg_ms_product.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_product.dto.SaleRequest;
import pe.edu.vallegrande.vg_ms_product.dto.SaleResponse;
import pe.edu.vallegrande.vg_ms_product.model.Sale;
import pe.edu.vallegrande.vg_ms_product.service.SaleService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SaleRest {

    private final SaleService saleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Sale> createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PostMapping("/full")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Sale> createSaleWithDetails(@RequestBody SaleRequest saleRequest) {
        return saleService.createSaleWithDetails(saleRequest);
    }

    @PutMapping("/{id}")
    public Mono<Sale> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
        return saleService.updateSale(id, sale);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSale(@PathVariable Long id) {
        return saleService.deleteSale(id);
    }

    @GetMapping
    public Flux<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Mono<Sale> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @GetMapping("/search-by-document/{document}")
    public Mono<Sale> getSaleByDocument(@PathVariable String document) {
        return saleService.getSaleByDocument(document);
    }

    // ✅ Nuevo: listar todas las ventas con detalles
    @GetMapping("/with-details")
    public Flux<SaleResponse> getAllSalesWithDetails() {
        return saleService.getAllSalesWithDetails();
    }


    // ✅ NUEVO: Obtener venta con detalles
    @GetMapping("/with-details/{id}")
    public Mono<SaleResponse> getSaleWithDetails(@PathVariable Long id) {
        return saleService.getSaleWithDetails(id);
    }
}
