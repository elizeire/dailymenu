package com.brno.webScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.brno.client.translator.TranslatorClient;

@Component
@Qualifier("aSport")
public class ASportScraper implements Scraper {
  
  @Autowired
  @Qualifier("google")
  private TranslatorClient googleTranslator;

  @Override
  @Cacheable("A-sport menus")
  public List<String> getMenu() {
    Document doc;
    List<String> menuList = new ArrayList<String>();
    List<String> translatedList = new ArrayList<String>();
    menuList.add("::A-Sport Hotel WEEK MENU::");
    try {
      doc = Jsoup.connect("http://www.a-sporthotel.cz/menu/").get();

      
      Elements rows = doc.select("td");
      for (Element row : rows) {
        String text = row.text();
        menuList.add(text);
        
      }
      translatedList = googleTranslator.translate(menuList, "EN"); 
      
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    return translatedList;
  }

}
