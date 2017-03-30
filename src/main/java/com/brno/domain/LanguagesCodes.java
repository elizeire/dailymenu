package com.brno.domain;

public enum LanguagesCodes {
  
  ENGLISH("EN"),CZECH("CS"),PORT_BR("pt-BR");
  
  private String code;

  private LanguagesCodes(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
  
  
}
  
  