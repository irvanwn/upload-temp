package com.ecommerce.desktop.Services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.desktop.Model.Cart;
import com.ecommerce.desktop.Model.Transaction;
import com.ecommerce.desktop.Repository.TransactionRepository;

@Service
public class TransactionManagement {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private ShippingManagement shippingManagement;

  public boolean createTransaction(Cart cart) {
    if (cart != null) {
      Transaction newTransaction = new Transaction();
      newTransaction.setId("TRX-" + UUID.randomUUID().toString());
      newTransaction.setUserId(cart.getUserId());
      newTransaction.setProducts(cart.getProducts());
      newTransaction.setTotalPrice(cart.getTotalPrice());
      newTransaction.setPaymentMethod(null);
      newTransaction.setStatus("PENDING");
      newTransaction.setCreatedAt(new Date().toString());
      newTransaction.setUpdatedAt(new Date().toString());
      transactionRepository.save(newTransaction);
      return true;
    }
    return false;
  }

  public boolean paytransaction(String id, String paymentMethod) {
    Transaction transaction = transactionRepository.findById(id).orElse(null);
    if (transaction != null) {
      if (CodePaymentChecker(paymentMethod).equals("Invalid Payment Method")) {
        return false;
      }
      transaction.setPaymentMethod(CodePaymentChecker(paymentMethod));
      transaction.setStatus("PAID");
      transaction.setUpdatedAt(new Date().toString());
      shippingManagement.shippingData(id);
      transactionRepository.save(transaction);

      return true;
    }
    return false;
  }

  public String CodePaymentChecker(String code) {
    if (code.equals("CC")) {
      return "Credit Card";
    } else if (code.equals("DC")) {
      return "Debit Card";
    } else if (code.equals("COD")) {
      return "Cash On Delivery";
    } else {
      return "Invalid Payment Method";
    }
  }

}
