package ru.siv.articles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.siv.articles.service.*;

public abstract class BaseController extends BaseModule {

  @Autowired
  protected SessionService sessionService;
  @Autowired
  protected TheAuthorsService authorsService;
  @Autowired
  protected TopicsService topicsService;
  @Autowired
  protected ArticlesService articlesService;

}
