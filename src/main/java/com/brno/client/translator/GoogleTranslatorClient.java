package com.brno.client.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;

@Component
@Qualifier("google")
public class GoogleTranslatorClient implements TranslatorClient {

  private static Integer chunckSize = 50;

  @Override
  public List<String> translate(List<String> textList, String languageTo) {

    List<String> translatedList = new ArrayList<String>();

    if (textList.size() > chunckSize) {

      Integer totalLines = textList.size();
      Integer totalChunks = ((totalLines % chunckSize)==0?totalLines / chunckSize:(totalLines / chunckSize)+1);
      Integer chunksCounter = 0;
      Integer indexIni = 0;

      while (chunksCounter < totalChunks) {
        Integer indexEnd = (indexIni + chunckSize)>textList.size()?textList.size():indexIni + chunckSize;
        translatedList.addAll(
            translateChunkes(textList.subList(indexIni, indexEnd), languageTo));
        indexIni = indexIni + chunckSize;
        chunksCounter++;
      }

    } else {
      translatedList = translateChunkes(textList, languageTo);
    }

    return translatedList;
  }

  private List<String> translateChunkes(List<String> text, String languageTo) {

    List<String> translatedList = new ArrayList<String>();
    try {
      // See comments on
      // https://developers.google.com/resources/api-libraries/documentation/translate/v2/java/latest/
      // on options to set
      Translate t = new Translate.Builder(
          com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
          com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
              // Need to update this to your App-Name
              .setApplicationName("dailyMenuBrno").build();
      Translate.Translations.List list = t.new Translations().list(text,
          // Target language
          languageTo);
      // Set your API-Key from https://console.developers.google.com/
      list.setKey("your API-Key");
      TranslationsListResponse response = list.execute();
      for (TranslationsResource tr : response.getTranslations()) {
        translatedList.add(tr.getTranslatedText());
      }
    } catch (Exception e) {
      System.out.println(text.size());
      text.forEach(System.out::println);
      e.printStackTrace();
    }

    return translatedList;
  }

}
