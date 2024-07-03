package com.ecommerce.desktop.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
  private String id;
  private String name;
  private String description;
  private String promoCode;
  private String startDate;
  private String endDate;
  private String discountType;
  private double discountValue;

}
