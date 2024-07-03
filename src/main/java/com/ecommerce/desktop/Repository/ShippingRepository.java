package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Shipping;

public interface ShippingRepository extends MongoRepository<Shipping, String> {

}
