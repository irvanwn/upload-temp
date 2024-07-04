package com.ecommerce.desktop.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.desktop.Model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
