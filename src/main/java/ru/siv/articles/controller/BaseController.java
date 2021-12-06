package ru.siv.articles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.siv.articles.service.BaseModule;
import ru.siv.articles.service.SessionService;
import ru.siv.articles.service.TheAuthorsService;

public abstract class BaseController extends BaseModule {

  @Autowired
  protected SessionService sessionService;
  @Autowired
  protected TheAuthorsService authorsService;

}