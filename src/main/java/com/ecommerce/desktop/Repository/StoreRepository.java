package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Store;

public interface StoreRepository extends MongoRepository<Store, String> {

}
