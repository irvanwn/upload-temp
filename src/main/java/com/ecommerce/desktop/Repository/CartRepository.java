package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {
  public Cart findByUserId(String userId);

  public boolean existsByUserId(String userId);
}
