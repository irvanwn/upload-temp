package com.ecommerce.desktop.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.DTO.Person;
import com.ecommerce.desktop.DTO.ProductList;
import com.ecommerce.desktop.Model.Cart;
import com.ecommerce.desktop.Model.Shipping;
import com.ecommerce.desktop.Model.Store;
import com.ecommerce.desktop.Repository.ShippingRepository;
import com.ecommerce.desktop.Repository.StoreRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class ShippingManagement {

  @Autowired
  private ShippingRepository shippingRepository;

  @Autowired
  private StoreRepository storeRepository;

  public @ResponseBody boolean generateShippingData(Cart cart, String shippingType) {
    Shipping shipping = new Shipping();

    Store store = null;
    ProductList product = cart.getProducts().get(0);
    if (product != null) {
      store = storeRepository.findById(product.getStoreId()).orElse(null);
    }
    if (cart != null) {
      shipping.setId("shp-" + cart.getUserId() + "-" +
          Randomizer.generateRandomNumber(5));
      shipping.setSender(new Person(store.getStoreName(), store.getTelpNumber(), store.getLocation()));
      shipping.setReceiver(new Person("Dummy", "08123456789", "Jakarta"));
      shipping.setShippingType(shippingType);
      if (shippingType.equals("Standard")) {
        double baseCost = 10000.0;
        double shippingCost = baseCost * product.getQuantity();
        shipping.setReceipt("ST-" + Randomizer.generateRandomNumber(10));
        shipping.setShippingCost(shippingCost);
      } else if (shippingType.equals("Express")) {
        double baseCost = 15000.0;
        double shippingCost = baseCost * product.getQuantity();
        shipping.setReceipt("EX-" + Randomizer.generateRandomNumber(10));
        shipping.setShippingCost(shippingCost);
      } else if (shippingType.equals("Same Day")) {
        double baseCost = 20000.0;
        double shippingCost = baseCost * product.getQuantity();
        shipping.setReceipt("SD-" + Randomizer.generateRandomNumber(10));
        shipping.setShippingCost(shippingCost);
      }
      shippingRepository.save(shipping);
      return true;
    }
    return false;
  }

}
