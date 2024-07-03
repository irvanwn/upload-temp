package com.ecommerce.desktop.Model;

import java.util.List;

import com.ecommerce.desktop.DTO.Person;
import com.ecommerce.desktop.DTO.ProductList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipping {
  private String id;
  private String receipt;
  private Person sender;
  private Person receiver;
  private String shippingType;
  private double shippingCost;
  private String shippingDate;
  private String shippingStatus;
  private List<ProductList> products;
}
