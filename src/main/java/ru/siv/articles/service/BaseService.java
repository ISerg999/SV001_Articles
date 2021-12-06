package ru.siv.articles.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public abstract class BaseService {

//  @Autowired
//  protected SessionService sessionService;

  protected String convertToUTF(String str) {
    return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
  }
}
