package org.taskspfe.pfe.controller.product;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.taskspfe.pfe.dto.product.ProductDTO;
import org.taskspfe.pfe.service.product.ProductService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin")
    public ResponseEntity<CustomResponseEntity<ProductDTO>> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("productJson") String productJson) throws IOException {
        return productService.createProduct(image, productJson);
    }

    @GetMapping("/all/{productId}/image")
    public ResponseEntity<byte[]> downloadProductImage(@PathVariable long productId) throws IOException {
        return productService.downloadProductImage(productId);
    }

    @PutMapping("/admin/{productId}")
    public ResponseEntity<CustomResponseEntity<ProductDTO>> updateProduct(
            @PathVariable long productId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("productJson") String productJson) throws IOException {
        return productService.updateProduct(productId, image, productJson);
    }

    @GetMapping("/all/{productId}")
    public ResponseEntity<CustomResponseEntity<ProductDTO>> fetchProductById(@PathVariable long productId) {
        return productService.fetchProductById(productId);
    }

    @DeleteMapping("/admin/{productId}")
    public ResponseEntity<CustomResponseEntity<ProductDTO>> deleteProductById(@PathVariable long productId) throws IOException {
        return productService.deleteProductById(productId);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseEntity<List<ProductDTO>>> getAllProducts() {
        return productService.getAllProducts();
    }
}
