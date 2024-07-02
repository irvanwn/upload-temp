package com.ecommerce.desktop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  private int statusCode;
  private String message;
  private ProductDTO data;

}
