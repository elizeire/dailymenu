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
@Qualifier("miki")
public class MikiScraper implements Scraper {

  @Autowired
  @Qualifier("google")
  private TranslatorClient googleTranslator;

  @Override
  @Cacheable("Miki menus")
  public List<String> getMenu() {

    Document doc;
    List<String> menuList = new ArrayList<String>();
    List<String> translatedList = new ArrayList<String>();
    menuList.add("::MIKI TODAY`s MENU::");
    try {
      doc = Jsoup.connect("http://mikirestaurant.cz/").get();

      Elements rows = doc.select("tr");
      for (Element row : rows) {
        menuList.add(row.text());
        if(menuList.size()>6){
          break;
        }
      }
      translatedList = googleTranslator.translate(menuList, "EN"); 
    } catch (IOException e) {
      e.printStackTrace();
    }

    return translatedList;
  }

}
