package com.ecommerce.desktop.Model;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.desktop.DTO.ProductList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
  private String id;

  @Builder.Default
  private List<ProductList> products = new ArrayList<>();

  private double totalPrice;
  private String userId;

}
