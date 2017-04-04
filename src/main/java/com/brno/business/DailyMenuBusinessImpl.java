package com.brno.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.brno.client.translator.TranslatorClient;
import com.brno.domain.RestaurantsCodes;
import com.brno.webScraper.Scraper;

@Component
public class DailyMenuBusinessImpl implements DailyMenuBusiness {

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
  
  @Autowired
  @Qualifier("nepal")
  private Scraper nepalScraper;

  @Autowired
  @Qualifier("google")
  private TranslatorClient googleTranslator;

  /* (non-Javadoc)
   * @see com.brno.business.DailyMenuBusiness#getMenu(java.lang.String)
   */
  @Override
  public List<String> getMenu(String restaurantId) {
    RestaurantsCodes code = RestaurantsCodes.valueOf(restaurantId);
    List<String> menuList = new ArrayList<String>();
    switch (code) {
    
    case ALL:
      menuList.addAll(aSportScraper.getMenu());
      menuList.addAll(mikiScraper.getMenu());
      menuList.addAll(napurkynceScraper.getMenu());
      menuList.addAll(nepalScraper.getMenu());
      menuList.addAll(rubinScraper.getMenu());
      break;
    
    case ASPORT:
      
      menuList.addAll(aSportScraper.getMenu());
      break;

    case MIKI:

      menuList.addAll(mikiScraper.getMenu());
      break;

    case NAPURKYNCE:

      menuList.addAll(napurkynceScraper.getMenu());
      break;

    case NEPAL:

      menuList.addAll(nepalScraper.getMenu());
      break;
      
    case RUBIN:

      menuList.addAll(rubinScraper.getMenu());
      break;

    default:
      break;
    }

    return menuList;
  }

  /* (non-Javadoc)
   * @see com.brno.business.DailyMenuBusiness#getMenuTranslated(java.lang.String, java.lang.String)
   */
  @Override
  public List<String> getMenuTranslated(String restaurantId, String languageCode) {
    List<String> menuListTranslated = new ArrayList<String>();
    List<String> menuList = getMenu(restaurantId);
    menuListTranslated.addAll(googleTranslator.translate(menuList, languageCode));
    return menuListTranslated;
  }
  
  
}
