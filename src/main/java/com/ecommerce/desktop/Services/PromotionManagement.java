package com.ecommerce.desktop.Services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.Model.Promotion;
import com.ecommerce.desktop.Repository.PromotionRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class PromotionManagement {

  @Autowired
  private PromotionRepository promotionRepository;

  LocalDateTime now = LocalDateTime.now();

  public @ResponseBody boolean addPromotion(Promotion newPromo) {
    newPromo.setId(newPromo.getName().substring(1, 3).toUpperCase() + "-" + Randomizer.generateRandomNumber(5));
    newPromo.setPromoCode(newPromo.getName().substring(1, 3).toUpperCase() + Randomizer.generateRandomNumber(3));
    newPromo.setStartDate(now.toString());
    newPromo.setEndDate(now.plusDays(60).toString());
    promotionRepository.save(newPromo);
    return true;
  }

}
