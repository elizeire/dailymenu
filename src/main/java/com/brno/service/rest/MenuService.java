package com.brno.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brno.business.DailyMenuBusiness;

@RestController
@RequestMapping("/menu")
public class MenuService {

  @Autowired
  private DailyMenuBusiness dailyMenuBusiness;


  @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET)
  public String getMenu(@PathVariable("restaurantId") String restaurantId) {
    String response = "";
    List<String> menuListTranslated = dailyMenuBusiness.getMenuTranslated(restaurantId, "EN");

    for (String string : menuListTranslated) {
      response = response + string + "\n";
    }

    return response;
  }
  
  @RequestMapping(value = "/restaurant/html/{restaurantId}", method = RequestMethod.GET)
  public String getHtmlMenu(@PathVariable("restaurantId") String restaurantId) {
    StringBuilder response = new StringBuilder();
  
   response.append(getHtmlMenuByLanguage(restaurantId,"EN"));

    return response.toString();
  }
  
  @RequestMapping(value = "/restaurant/html/{restaurantId}/{languageCode}", method = RequestMethod.GET)
  public String getHtmlMenuByLanguage(@PathVariable("restaurantId") String restaurantId,@PathVariable("languageCode") String languageCode) {
    StringBuilder response = new StringBuilder();
  
    List<String> menuListTranslated = dailyMenuBusiness.getMenuTranslated(restaurantId, languageCode);
    
    response.append("<html><body><table>");

    for (String menu : menuListTranslated) {
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