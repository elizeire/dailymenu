package com.brno;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.naming.Context;

import net.minidev.json.JSONObject;

public class Test {

  private static final String DAILYMENU_API = "http://guarded-peak-75773.herokuapp.com/menu/restaurant/%s";

  public static void main(String[] args) {
    Test rf = new Test();
    System.out.println(rf.getDailyMenu(null, "MIKI"));
  }

  public static String getDailyMenu(Context context, String restaurantCode) {
    try {
      URL url = new URL(String.format(DAILYMENU_API, restaurantCode));
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));

      StringBuffer str = new StringBuffer(1024);
      String tmp = "";
      while ((tmp = reader.readLine()) != null)
        str.append(tmp).append("\n");
      reader.close();

      return str.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
