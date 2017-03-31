package com.brno.business;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.brno.client.translator.GoogleTranslatorClient;
import com.brno.webScraper.ASportScraper;
import com.brno.webScraper.MikiScraper;
import com.brno.webScraper.NapurkynceScraper;
import com.brno.webScraper.RubinScraper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DailyMenuBusinessImpl.class, GoogleTranslatorClient.class, MikiScraper.class, NapurkynceScraper.class, RubinScraper.class, ASportScraper.class})
public class DailyMenuBusinessIntegrationTest {

  @Autowired
  private DailyMenuBusiness dailymenuBusiness;

  @Test
  public void testGetMenu() {
    List<String> allMenus = dailymenuBusiness.getMenu("ALL");
    assertTrue(allMenus != null && allMenus.size()>20);
  }

  @Test
  public void testGetMenuTranslated() {
    List<String> allMenus = dailymenuBusiness.getMenuTranslated("ALL","EN");
    assertTrue(allMenus != null && allMenus.size()>20);
  }

}
