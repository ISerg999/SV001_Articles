package ru.siv.articles.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.siv.articles.model.Articles;

@Controller
public class ArticleController extends BaseController {

  @Value("${msg.error.article.add}")
  private String messageErrorAdd;
  @Value("${msg.error.article.update}")
  private String messageErrorUpdate;

  @GetMapping("/article/all")
  public String listArticle(Model model) {
    Long idUser = sessionService.getUserStatus();
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("articles", articlesService.listArticle());
    return "articles/article-list";
  }

  @GetMapping("/article/add")
  public String articleAdd(Model model) {
    Long idUser = sessionService.getUserStatus();
    if (idUser <= 0) return "redirect:/article/all";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("topics", topicsService.listTopic());
    return "articles/article-add";
  }

  @PostMapping("/article/add")
  public String articlePostAdd(@RequestParam(name = "selectedTopic") Long selectedTopic,
                               @RequestParam(name = "titleArticle") String titleArticle,
                               @RequestParam(name = "textArticle") String textArticle,
                               Model model) {
    Long idUser = sessionService.getUserStatus();
    if (idUser > 0) {
      Long newId = articlesService.addArticle(idUser, selectedTopic, titleArticle, textArticle);
      if (newId > 0) return "redirect:/article/" + newId;
      if (newId < 0) {
        authorsService.setCurUserToModel(idUser, model);
        model.addAttribute("messageError", convertToUTF(messageErrorAdd));
        return  "show-error";
      }
    }
    return "redirect:/article/all";
  }

  @GetMapping("/article/{id}")
  public String articleRead(@PathVariable(value="id") Long id, Model model) {
    Articles art = articlesService.getArticleForId(id);
    if (null == art) return "redirect:/article/all";
    Long idUser = sessionService.getUserStatus();
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("article", art);
    return "articles/article-read";
  }

  @GetMapping("/article/{id}/edit")
  public String articleEdit(@PathVariable(value="id") Long id, Model model) {
    Long idUser = sessionService.getUserStatus();
    Articles art = articlesService.getArticleForId(id);
    if (null == art || art.getAuthor().getId() != idUser) return "redirect:/article/all";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("article", art);
    model.addAttribute("topics", topicsService.listTopic());
    return "articles/article-edit";
  }

  @PostMapping("/article/{id}/edit")
  public String articlePostUpdate(@PathVariable(value="id") Long id,
                                  @RequestParam(name = "idTopic") Long idTopic,
                                  @RequestParam(name = "articleTitle") String articleTitle,
                                  @RequestParam(name = "articleText") String articleText,
                                  Model model) {
    Long idUser = sessionService.getUserStatus();
    Long newId = articlesService.updateArticle(id, idTopic, articleTitle, articleText);
    if (newId > 0) return "redirect:/article/" + newId;
    if (newId < 0) {
      authorsService.setCurUserToModel(idUser, model);
      model.addAttribute("messageError", convertToUTF(messageErrorUpdate));
      return  "show-error";
    }
    return "redirect:/article/all";
  }

  @GetMapping("/article/{id}/remove")
  public String articleDelete(@PathVariable(value="id") Long id, Model model) {
    Long idUser = sessionService.getUserStatus();
    Articles article = articlesService.getArticleForId(id);
    if (null != article) {
      if (0 == idUser || idUser == article.getAuthor().getId()) {
        articlesService.removeArticle(id);
      }
    }
    return "redirect:/article/all";
  }
}
