package com.brno.webScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Qualifier("napurkynce")
public class NapurkynceScraper implements Scraper {

  private static String MENU_URL = "http://www.napurkynce.cz/daily-menu/";
  
  @Override
  @Cacheable("Napurkynce menus")
  public List<String> getMenu() {

    Document doc;
    List<String> menuList = new ArrayList<String>();
    menuList.add("::NAPURKYNCE WEEK MENU::");
    menuList.add(MENU_URL);
    try {
      doc = Jsoup.connect(MENU_URL).get();

      String text = doc.select("pre").html();
      
      String[] rows = text.split("<br>");
      
      Arrays.asList(rows).forEach(row->menuList.add(row));
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    return menuList;
  }

}
