package com.brno.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brno.domain.RestaurantsCodes;
import com.brno.webScraper.Scraper;

@RestController
@RequestMapping("/menu")
public class MenuService {

  @Autowired
  @Qualifier("miki")
  private Scraper mikiScraper;

  @Autowired
  @Qualifier("napurkynce")
  private Scraper napurkynceScraper;

  @Autowired
  @Qualifier("aSport")
  private Scraper aSportScraper;

  @Autowired
  @Qualifier("rubin")
  private Scraper rubinScraper;

  @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET)
  public String getMenu(@PathVariable("restaurantId") String param) {
    String response = "";
    RestaurantsCodes code = RestaurantsCodes.valueOf(param);
    List<String> menuList = new ArrayList<String>();
    switch (code) {
    case MIKI:

      menuList.addAll(mikiScraper.getMenu());
      break;

    case NAPURKYNCE:

      menuList.addAll(napurkynceScraper.getMenu());
      break;

    case ASPORT:

      menuList.addAll(aSportScraper.getMenu());
      break;

    case RUBIN:

      menuList.addAll(rubinScraper.getMenu());
      break;

    case ALL:
      menuList.addAll(mikiScraper.getMenu());
      menuList.addAll(napurkynceScraper.getMenu());
      menuList.addAll(aSportScraper.getMenu());
      menuList.addAll(rubinScraper.getMenu());
      break;

    default:
      break;
    }

    for (String string : menuList) {
      response = response + string + "\n";
    }

    return response;
  }
  
  @RequestMapping(value = "/restaurant/html/{restaurantId}", method = RequestMethod.GET)
  public String getHtmlMenu(@PathVariable("restaurantId") String param) {
    StringBuilder response = new StringBuilder();
    RestaurantsCodes code = RestaurantsCodes.valueOf(param);
    List<String> menuList = new ArrayList<String>();
    switch (code) {
    case MIKI:

      menuList.addAll(mikiScraper.getMenu());
      break;

    case NAPURKYNCE:

      menuList.addAll(napurkynceScraper.getMenu());
      break;

    case ASPORT:

      menuList.addAll(aSportScraper.getMenu());
      break;

    case RUBIN:

      menuList.addAll(rubinScraper.getMenu());
      break;

    case ALL:
      menuList.addAll(mikiScraper.getMenu());
      menuList.addAll(napurkynceScraper.getMenu());
      menuList.addAll(aSportScraper.getMenu());
      menuList.addAll(rubinScraper.getMenu());
      break;

    default:
      break;
    }
    
    response.append("<html><body><table>");

    for (String menu : menuList) {
      if(menu.startsWith("::")){
        response.append("<tr bgcolor='#99ffe6'><td>");
      }else{
        response.append("<tr><td>");
      }
      response.append(menu);
      response.append("</tr></td>");
    }
    response.append("</table></body></html>");

    return response.toString();
  }
}