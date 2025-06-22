package pe.edu.vallegrande.vg_ms_product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_product.dto.SaleDto;
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

    // Crear una venta básica
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Sale> createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    // Crear una venta con detalles
    @PostMapping("/full")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SaleDto> createSaleWithDetails(@RequestBody SaleDto saleDto) {
        return saleService.createSaleWithDetails(saleDto);
    }

    // Actualizar venta básica
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Sale>> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
        return saleService.updateSale(id, sale)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSale(@PathVariable Long id) {
        return saleService.deleteSale(id);
    }

    // Obtener todas las ventas (sin detalles)
    @GetMapping
    public Flux<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Sale>> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Buscar por documento
    @GetMapping("/search-by-document/{document}")
    public Mono<ResponseEntity<Sale>> getSaleByDocument(@PathVariable String document) {
        return saleService.getSaleByDocument(document)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Obtener todas las ventas con detalles
    @GetMapping("/with-details")
    public Flux<SaleDto> getAllSalesWithDetails() {
        return saleService.getAllSalesWithDetails();
    }

    // Obtener una venta con detalles por ID
    @GetMapping("/{id}/with-details")
    public Mono<ResponseEntity<SaleDto>> getSaleWithDetails(@PathVariable Long id) {
        return saleService.getSaleWithDetails(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
