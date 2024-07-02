package com.ecommerce.desktop.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private String id;
  private String name;
  private String description;
  private String category;
  private double price;
  private int stock;
  private String image;
  private String createdAt;
  private String updatedAt;
  private float rating;
  private String storeId;

}