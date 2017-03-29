package com.brno.client.translator;

import java.util.List;

public interface TranslatorClient {
  
  
  public List<String> translate(List<String> text, String languageTo);

}
