package org.taskspfe.pfe.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taskspfe.pfe.dto.product.ProductDTO;
import org.taskspfe.pfe.dto.product.ProductDTOMapper;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.file.FileData;
import org.taskspfe.pfe.model.product.Product;
import org.taskspfe.pfe.model.shop.CartItem;
import org.taskspfe.pfe.repository.CartItemRepository;
import org.taskspfe.pfe.repository.ProductRepository;
import org.taskspfe.pfe.service.file.FileService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;
    private final FileService fileService;
    private final CartItemRepository cartItemRepository;
    public ProductServiceImpl(ProductRepository productRepository, ProductDTOMapper productDTOMapper, FileService fileService, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.productDTOMapper = productDTOMapper;
        this.fileService = fileService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public ResponseEntity<CustomResponseEntity<ProductDTO>> createProduct(MultipartFile image, String productJson) throws IOException {
        final Product newProduct = new ObjectMapper().readValue(productJson, Product.class);
        final FileData savedImage = fileService.processUploadedFile(image);
        newProduct.setImage(savedImage);
        final ProductDTO product = productDTOMapper.apply(productRepository.save(newProduct));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.CREATED , product));
    }

    @Override
    public ResponseEntity<byte[]> downloadProductImage(long productId) throws IOException {
        final Product existingProduct = getProductById(productId);
        return fileService.downloadFile(existingProduct.getImage());
    }

    @Override
    public ResponseEntity<CustomResponseEntity<ProductDTO>> updateProduct(long productId, MultipartFile image, String productJson) throws IOException {
        final Product existingProduct = getProductById(productId);
        final Product updatedProduct = new ObjectMapper().readValue(productJson, Product.class);
        final FileData oldImage = existingProduct.getImage();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        if(image != null){
            final FileData savedImage = fileService.processUploadedFile(image);
            existingProduct.setImage(savedImage);
        }
        final ProductDTO product = productDTOMapper.apply(productRepository.save(existingProduct));
        fileService.deleteFileFromFileSystem(oldImage);
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK , product));

    }

    @Override
    public ResponseEntity<CustomResponseEntity<ProductDTO>> fetchProductById(long productId) {
        final ProductDTO product = productDTOMapper.apply(getProductById(productId));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK , product));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<ProductDTO>> deleteProductById(long productId) throws IOException {
        final Product existingProduct = getProductById(productId);
        List<CartItem> cartItems = cartItemRepository.fetchAllCartItemByProduct(existingProduct.getId());
        if(!cartItems.isEmpty()){
            cartItemRepository.deleteAll(cartItems);
        }
        productRepository.delete(existingProduct);
        fileService.deleteFileFromFileSystem(existingProduct.getImage());
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK , productDTOMapper.apply(existingProduct)));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<ProductDTO>>> getAllProducts() {
        return ResponseEntity.ok(new CustomResponseEntity<>(
                HttpStatus.OK ,
                productRepository.findAll().stream().map(productDTOMapper).toList()
                ));

    }

    public Product getProductById(long productId){
        return productRepository.fetchProductById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id "+productId+" not found")
        );
    }
}
