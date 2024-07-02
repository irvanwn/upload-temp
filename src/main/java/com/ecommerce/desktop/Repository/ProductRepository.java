package com.ecommerce.desktop.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

  public List<Product> findByStoreId(String id);

}
