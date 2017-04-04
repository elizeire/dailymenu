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
@Qualifier("nepal")
public class NepalScraper implements Scraper {
  
  private static String MENU_URL = "http://nepalbrno.cz/weekly-menu/";
  

  @Override
  @Cacheable("Nepal menus")
  public List<String> getMenu() {

    Document doc;
    List<String> menuList = new ArrayList<String>();
    
    menuList.add("::Nepal WEEK MENU::");
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
