package com.varshitha.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varshitha.Product;
import com.varshitha.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
@RestController
@RequestMapping("/api/products")
public class ProductController {

	
		@Autowired
	    private ProductRepository productRepository;

		public ProductController(ProductRepository productRepository) {
		     this.productRepository = productRepository;
		}
	    // Create a new product
	    @PostMapping
	    @Operation(summary = "Update a product", description = "Update a product with it's name and price")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Product Created"),
	        @ApiResponse(responseCode = "404", description = "Product failed to create")
	    })
	    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
	        Product createdProduct = productRepository.save(product);
	        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	    }

	    // Read all products
	    @GetMapping
	    public ResponseEntity<List<Product>> getAllProducts() {
	    	List<Product> products = productRepository.findAll();
	        
	        return new ResponseEntity<>(products, HttpStatus.OK);
	    }

	    // Read a product by ID
	    @GetMapping("/{productId}")
	    @Operation(summary = "Get a product by ID", description = "Retrieve a product using its unique ID")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Product found"),
	        @ApiResponse(responseCode = "404", description = "Product not found")
	    })
	    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
	        Optional<Product> product = productRepository.findById(productId);
	        if (product.isPresent()) {
	            return new ResponseEntity<>(product.get(), HttpStatus.OK);
	        } else {
	            throw new EntityNotFoundException("Product with ID " + productId + " not found");
	        }
	    }

	    // Update a product by ID
	    @PutMapping("/{productId}")
	    @Operation(summary = "update a product by ID", description = "Update a product's price and/or name using its unique ID")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Product found"),
	        @ApiResponse(responseCode = "404", description = "Product not found")
	    })
	    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody Product updatedProduct) {
	        Optional<Product> existingProduct = productRepository.findById(productId);
	        if (existingProduct.isPresent()) {
	            updatedProduct.setId(productId); // Ensure the ID is set correctly
	            Product savedProduct = productRepository.save(updatedProduct);
	            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
	        } else {
	        	throw new EntityNotFoundException("Product with ID " + productId + " not found");
	        }
	    }

	    // Delete a product by ID
	    @DeleteMapping("/{productId}")
	    @Operation(summary = "Delete a product by ID", description = "Delete a product using its unique ID")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Product found"),
	        @ApiResponse(responseCode = "404", description = "Product not found")
	    })
	    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
	        Optional<Product> product = productRepository.findById(productId);
	        if (product.isPresent()) {
	            productRepository.deleteById(productId);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	        	throw new EntityNotFoundException("Product with ID " + productId + " not found");
	        }
	    }
}
