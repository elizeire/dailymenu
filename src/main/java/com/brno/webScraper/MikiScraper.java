package com.brno.webScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Qualifier("miki")
public class MikiScraper implements Scraper {

  private static String MENU_URL = "http://mikirestaurant.cz/";

  @Override
  @Cacheable("Miki menus")
  public List<String> getMenu() {

    Document doc;
    List<String> menuList = new ArrayList<String>();
    menuList.add("::MIKI TODAY`s MENU::");
    menuList.add(MENU_URL);
    try {
      doc = Jsoup.connect(MENU_URL).get();

      Elements rows = doc.select("tr");
      for (Element row : rows) {
        menuList.add(row.text());
        if(menuList.size()>6){
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return menuList;
  }

}
