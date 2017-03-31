package com.brno.client.translator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;

@Component
@Qualifier("google")
public class GoogleTranslatorClient implements TranslatorClient {

  final static Logger LOGGER = Logger.getLogger(GoogleTranslatorClient.class.getName());
  
  @Value("${google.api.apiKey}")
  private String apiKey;
  
  //safe size to avoid maximum values error from google api
  private static Integer chunckSize = 50;
  private Translate translator;
  

  @Override
  /**
   * Method to evaluate size of requests to chunk if necessary
   * @param text
   * @param languageTo
   * @return translatedList
   */
  @Cacheable("translated menus")
  public List<String> translate(List<String> textList, String languageTo) {

    List<String> translatedList = null;

    if (textList.size() > chunckSize) {

      translatedList = translateChunks(textList, languageTo);

    } else {
      translatedList = sendRequest(textList, languageTo);
    }

    return translatedList;
  }

  /**
   * Method to consume Google translator API
   * @param text
   * @param languageTo
   * @return translatedList
   */
  private List<String> sendRequest(List<String> text, String languageTo) {
    List<String> translatedList = new ArrayList<String>();
    Translate translator = getDefaulsTranslator();
    try {

      Translate.Translations.List list = translator.new Translations().list(text, languageTo);
      // Set your API-Key from https://console.developers.google.com/
      list.setKey(apiKey);
      TranslationsListResponse response = list.execute();
      
      response.getTranslations().forEach(tr->translatedList.add(tr.getTranslatedText()));
      
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }
    return translatedList;
  }  

  /**
   * Returs an intance of default constructor of Translator
   * @return
   */
  private Translate getDefaulsTranslator() {

    if (this.translator == null) {
      try {
        this.translator = new Translate.Builder(
            com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
            com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
                .setApplicationName("dailyMenuBrno").build();
      } catch (GeneralSecurityException | IOException e) {
        LOGGER.log(Level.SEVERE, e.toString(), e);
      }
    }
    return translator;
  }
  
  /**
   * Method to chunk the request to avoid excess limit size
   * @param textList
   * @param languageTo
   * @return
   */
  private List<String> translateChunks(List<String> textList, String languageTo) {
    List<String> translatedList = new ArrayList<String>();
    Integer totalLines = textList.size();
    Integer totalChunks = ((totalLines % chunckSize) == 0 ? totalLines / chunckSize
        : (totalLines / chunckSize) + 1);
    Integer chunksCounter = 0;
    Integer indexIni = 0;

    while (chunksCounter < totalChunks) {
      Integer indexEnd = (indexIni + chunckSize) > textList.size() ? textList.size()
          : indexIni + chunckSize;
      translatedList.addAll(sendRequest(textList.subList(indexIni, indexEnd), languageTo));
      indexIni = indexIni + chunckSize;
      chunksCounter++;
    }
    return translatedList;
  }
}
