package com.ecommerce.desktop.Model;

import java.util.List;

import com.ecommerce.desktop.DTO.ProductList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  private String id;
  private String userId;
  private List<ProductList> products;
  private double totalPrice;
  private String createdAt;
  private String updatedAt;
  private String paymentMethod;
  private String status;

}
