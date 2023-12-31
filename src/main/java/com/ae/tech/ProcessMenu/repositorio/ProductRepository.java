package com.ae.tech.ProcessMenu.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.product.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	List<Product> findByCategorie(String categorie);
	
	List<Product> findByTitle(String title);
}
