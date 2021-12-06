package ru.siv.articles.service;

import java.nio.charset.StandardCharsets;

public abstract class BaseService {

  public String convertToUTF(String str) {
    return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
  }
}
