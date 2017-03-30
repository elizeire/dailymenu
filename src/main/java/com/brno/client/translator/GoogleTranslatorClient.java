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


  @Override
  public List<String> translate(List<String> text, String languageTo) {

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
      list.setKey("change for your own key");
      TranslationsListResponse response = list.execute();
      for (TranslationsResource tr : response.getTranslations()) {
        translatedList.add(tr.getTranslatedText());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return translatedList;
  }

}
