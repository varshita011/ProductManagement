package com.varshitha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varshitha.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{


}
