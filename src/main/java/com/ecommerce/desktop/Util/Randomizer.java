package com.ecommerce.desktop.Util;

import java.util.Random;

public class Randomizer {

  private static final String NUM_DIGIT = "0123456789";
  private static final Random random = new Random();

  public static String generateRandomNumber(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(NUM_DIGIT.charAt(random.nextInt(NUM_DIGIT.length())));
    }
    return sb.toString();
  }

}
