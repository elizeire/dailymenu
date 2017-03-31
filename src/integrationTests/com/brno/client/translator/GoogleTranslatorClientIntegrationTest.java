package com.brno.client.translator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GoogleTranslatorClient.class})
public class GoogleTranslatorClientIntegrationTest {


  @Autowired
  private TranslatorClient translator;
  
  @Test
  @Timed(millis=10000)
  public void testTranslate() {
    List<String> strToTranslate = new ArrayList<String>();
    strToTranslate.add("Bom dia!");
    List<String> strTranslated = translator.translate(strToTranslate, "EN");
    assertTrue("Good morning!".equalsIgnoreCase(strTranslated.get(0)));
  }

}
