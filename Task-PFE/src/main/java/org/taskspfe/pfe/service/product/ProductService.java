package org.taskspfe.pfe.service.product;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.taskspfe.pfe.dto.product.ProductDTO;
import org.taskspfe.pfe.model.product.Product;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseEntity<CustomResponseEntity<ProductDTO>> createProduct(MultipartFile image, String productJson) throws IOException;
    ResponseEntity<byte[]> downloadProductImage(long productId) throws IOException;
    ResponseEntity<CustomResponseEntity<ProductDTO>> updateProduct(long productId,MultipartFile image, String productJson) throws IOException;
    ResponseEntity<CustomResponseEntity<ProductDTO>> fetchProductById(long productId);
    ResponseEntity<CustomResponseEntity<ProductDTO>> deleteProductById(long productId) throws IOException;
    ResponseEntity<CustomResponseEntity<List<ProductDTO>>> getAllProducts();

    Product getProductById(final long productId);
}
