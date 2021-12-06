package ru.siv.articles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер главной страницы.
 */
@Controller
public class MainController extends BaseController {

  @GetMapping("/")
  public String main(Model model) {
    Long idUser = sessionService.getUserStatus();
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("lstAuthor", authorsService.getListAuthorWithAdmin());
    return "main";
  }

  @PostMapping("/")
  public String mainSelectedAuthor(@RequestParam(name = "idAuthor") Long idAuthor, Model model) {
    sessionService.setSessionAuthor(idAuthor);
    return "redirect:/";
  }
}
