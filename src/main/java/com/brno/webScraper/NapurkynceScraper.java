package com.brno.webScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Qualifier("napurkynce")
public class NapurkynceScraper implements Scraper {

  public static void main(String[] args) {
    NapurkynceScraper na = new NapurkynceScraper();
    na.getMenu();
  }

  @Override
  @Cacheable("Napurkynce menus")
  public List<String> getMenu() {

    Document doc;
    List<String> menuList = new ArrayList<String>();
    menuList.add("::NAPURKYNCE WEEK MENU::");
    try {
      doc = Jsoup.connect("http://www.napurkynce.cz/daily-menu/").get();

      String text = doc.select("pre").html();
      
      String[] rows = text.split("<br>");
      
     for (String row : rows) {
      
       menuList.add(row);
    }
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    return menuList;
  }

}
