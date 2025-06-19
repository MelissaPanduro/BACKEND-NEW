package pe.edu.vallegrande.vg_ms_product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_product.model.ProductoModel;
import pe.edu.vallegrande.vg_ms_product.service.ProductoService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/NPH/products")
@CrossOrigin(origins = "*")
public class ProductoRest {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductoModel> createProduct(@RequestBody ProductoModel product) {
        return productoService.createProduct(product);
    }

    @GetMapping
    public Flux<ProductoModel> getAllProducts() {
        return productoService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productoService.deleteProduct(id);
    }

    @GetMapping("/active")
    public Flux<ProductoModel> getActiveProducts() {
        return productoService.getActiveProducts();
    }

    @PutMapping("/logic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> softDeleteProduct(@PathVariable Long id) {
        return productoService.softDeleteProduct(id);
    }

    @PutMapping("/restore/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> restoreProduct(@PathVariable Long id) {
        return productoService.restoreProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> updateProduct(@PathVariable Long id, @RequestBody ProductoModel productDetails) {
        return productoService.updateProduct(id, productDetails);
    }

    // PATCH para actualizar stock y estado (desde el frontend)
    @PatchMapping("/{id}/stock")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> updateStockAndStatus(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        int newStock = ((Number) body.get("stock")).intValue();
        String newStatus = (String) body.get("status");

        return productoService.getProductById(id)
                .flatMap(product -> {
                    product.setStock(newStock);
                    product.setStatus(newStatus);
                    return productoService.updateProduct(id, product);
                });
    }

    @PutMapping("/increase-stock/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
        return productoService.increaseStock(id, quantity);
    }

    @PutMapping("/adjust-stock/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> adjustStock(@PathVariable Long id, @RequestParam int quantityChange) {
        return productoService.adjustStock(id, quantityChange);
    }

    @PutMapping("/reduce-stock/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> reduceStock(@PathVariable Long id, @RequestParam int quantity) {
        return productoService.reduceStock(id, quantity);
    }
}
