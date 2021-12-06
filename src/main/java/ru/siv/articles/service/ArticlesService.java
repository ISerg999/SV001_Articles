package ru.siv.articles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siv.articles.model.Articles;
import ru.siv.articles.model.TheAuthors;
import ru.siv.articles.model.Topics;
import ru.siv.articles.repository.ArticlesRepository;

import java.util.List;

@Component
public class ArticlesService extends BaseModule {

  @Autowired
  private ArticlesRepository articlesRepository;
  @Autowired
  private TheAuthorsService authorsService;
  @Autowired
  private TopicsService topicsService;

  /**
   * Возвращает объект статьи по заданному ключу.
   * @param id ключ статьи
   * @return объект статьи
   */
  public Articles getArticleForId(Long id) {
    return articlesRepository.findById(id).orElse(null);
  }

  /**
   * Возвращает список имеющихся статей.
   * @return список статей
   */
  public List<Articles> listArticle() {
    return articlesRepository.filterAllOrderByTopicOrderByAuthorOrderByTitle();
  }


  /**
   * Добавление новой статьи.
   * @param idUser  ключ автора статьи
   * @param idTopic ключ темы
   * @param title   заголовок статьи
   * @param text    содержимое статьи
   * @return в случае успеха или существование статьи с такими же темой, заголовком, статьей, автором, возвращается ключ статьи, если есть статья совпадающая либо по заголовку, либо по тексту, возвращается -1, в остальных случаях 0
   */
  public Long addArticle(Long idUser, Long idTopic, String title, String text) {
    List<Articles> articles = articlesRepository.filterByAuthorAndTitle(idUser, title);
    if (!(null == articles || articles.isEmpty())) {
      for (Articles el: articles) {
        if (idTopic == el.getTopic().getId() && text.equals(el.getArticle())) return el.getId();
        else {
          if (idTopic == el.getTopic().getId() || text.equals(el.getArticle())) return -1L;
        }
      }
      return 0L;
    }
    TheAuthors author = authorsService.getAuthorForId(idUser);
    Topics topic = topicsService.getTopicForId(idTopic);
    if (null == author || null == topic) return 0L;
    Articles article = new Articles();
    article.setAuthor(author);
    article.setTopic(topic);
    article.setTitle(title);
    article.setArticle(text);
    article = articlesRepository.save(article);
    return article.getId();
  }

  /**
   * Изменение статьи.
   * @param id           ключ статьи
   * @param idTopic      ключ темы статьи
   * @param articleTitle заголовок статьи
   * @param articleText  текст статьи
   * @return в случае успеха возвращает ключ статьи, если есть совпадение по заголовку и тексту, возвращает -1, иначе 0.
   */
  public Long updateArticle(Long id, Long idTopic, String articleTitle, String articleText) {
    Articles art = getArticleForId(id);
    if (null == art) return 0L;
    if (idTopic == art.getTopic().getId() && articleTitle.equals(art.getTitle()) && articleText.equals(art.getArticle())) return id;
    Topics top = topicsService.getTopicForId(idTopic);
    if (null == top || null == articleTitle) return 0L;
    List<Articles> articles = articlesRepository.filterCompareByTitleByText(articleTitle, articleText);
    if (null != articles && !articles.isEmpty()) return -1L;
    art.setTopic(top);
    art.setTitle(articleTitle);
    art.setArticle(articleText);
    articlesRepository.save(art);
    return art.getId();
  }

  /**
   * Удаление статьи.
   * @param id ключ статьи
   */
  public void removeArticle(long id) {
    Articles art = getArticleForId(id);
    if (null != art) {
      articlesRepository.delete(art);
    }
  }
}
