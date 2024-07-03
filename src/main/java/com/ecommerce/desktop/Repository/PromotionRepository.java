package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Promotion;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

}
