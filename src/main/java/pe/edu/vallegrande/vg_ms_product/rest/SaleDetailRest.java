package pe.edu.vallegrande.vg_ms_product.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_product.model.SaleDetail;
import pe.edu.vallegrande.vg_ms_product.service.SaleDetailService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sale-details")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SaleDetailRest {

    private final SaleDetailService saleDetailService;

    // Obtener todos los detalles 
    @GetMapping
    public Flux<SaleDetail> getAllSaleDetails() {
        return saleDetailService.getAllSaleDetails();
    }

    // Crear un nuevo detalle de venta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SaleDetail> createSaleDetail(@RequestBody SaleDetail saleDetail) {
        return saleDetailService.saveSaleDetail(saleDetail);
    }

    // Obtener detalles por ID de venta
    @GetMapping("/sale/{saleId}")
    public Flux<SaleDetail> getDetailsBySaleId(@PathVariable Long saleId) {
        return saleDetailService.getDetailsBySaleId(saleId);
    }

    // Eliminar un detalle por su ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSaleDetail(@PathVariable Long id) {
        return saleDetailService.deleteDetail(id);
    }
}
