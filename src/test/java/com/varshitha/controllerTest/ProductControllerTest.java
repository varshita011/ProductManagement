package com.varshitha.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.varshitha.Product;
import com.varshitha.controller.ProductController;
import com.varshitha.repository.ProductRepository;


public class ProductControllerTest {

	private ProductController productController;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productController = new ProductController(productRepository);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(); // Create a product instance
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        verify(productRepository, times(1)).save(product);
        assert response.getStatusCode() == HttpStatus.CREATED;
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product()); // Create a list of products
        when(productRepository.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        verify(productRepository, times(1)).findAll();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null && response.getBody().size() == 2;
    }

    @Test
    public void testGetProductById() {
        Integer productId = 1;
        Product product = new Product(); // Create a product instance
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
    }

    @Test
    public void testUpdateProduct() {
        Integer productId = 1;
        Product updatedProduct = new Product(); // Create an updated product instance
        when(productRepository.findById(productId)).thenReturn(Optional.of(updatedProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, updatedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(updatedProduct);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
    }

    @Test
    public void testDeleteProduct() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
