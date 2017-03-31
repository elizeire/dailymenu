package com.brno.business;

import java.util.List;

public interface DailyMenuBusiness {

  List<String> getMenu(String restaurantId);

  List<String> getMenuTranslated(String restaurantId, String languageCode);

}