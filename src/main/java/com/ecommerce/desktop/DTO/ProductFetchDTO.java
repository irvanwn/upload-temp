package com.ecommerce.desktop.DTO;

import java.util.List;

import com.ecommerce.desktop.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFetchDTO {
  private int statusCode;
  private String message;
  private List<Product> data;
}
