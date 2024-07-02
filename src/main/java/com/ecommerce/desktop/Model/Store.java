package com.ecommerce.desktop.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {
  private String id;
  private String storeName;
  private String description;
  private String location;
  private String telpNumber;
  private float rating;
  private String createdAt;
  private String UpdatedAt;
  List<Product> productsData;
}
