package com.brno.webScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Qualifier("aSport")
public class ASportScraper implements Scraper {
  
  private static String MENU_URL = "http://www.a-sporthotel.cz/menu/";
  
  @Override
  @Cacheable("A-sport menus")
  public List<String> getMenu() {
    Document doc;
    List<String> menuList = new ArrayList<String>();
    menuList.add("::A-Sport Hotel WEEK MENU::");
    menuList.add(MENU_URL);
    try {
      doc = Jsoup.connect(MENU_URL).get();

      Elements rows = doc.select("tr");
      
      rows.forEach(row->menuList.add(row.text()));
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    return menuList;
  }

}
