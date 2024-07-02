package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

  public void findByStoreId(String id);

}
