package com.brno.webScraper;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MikiScraper.class})
public class MikiScraperIntegrationTest {

  @Autowired
  private Scraper scraper;

  @Test
  @Timed(millis=10000)
  public void testGetMenu() {
    List<String> menu = scraper.getMenu();
    assertTrue(menu != null && menu.size()>2);
  }

}
