package com.ecommerce.desktop.Model;

import com.ecommerce.desktop.DTO.Volume;

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
  private Volume volume;
  private String storeId;

}