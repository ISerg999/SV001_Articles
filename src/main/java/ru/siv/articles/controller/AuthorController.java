package ru.siv.articles.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.siv.articles.model.TheAuthors;

@Controller
public class AuthorController extends BaseController {

  @Value("${msg.error.author.add}")
  private String messageErrorAdd;
  @Value("${msg.error.author.update}")
  private String messageErrorUpdate;

  @GetMapping("/author/all")
  public String listAuthor(Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("authors", authorsService.listAuthor());
    return "authors/author-list";
  }

  @GetMapping("/author/add")
  public String authorAdd(Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.setCurUserToModel(idUser, model);
    return "authors/author-add";
  }

  @PostMapping("/author/add")
  public String authorPostAdd(@RequestParam(name = "newNameAuthor") String newNameAuthor, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    if (!authorsService.addAuthor(newNameAuthor)) {
      authorsService.setCurUserToModel(idUser, model);
      model.addAttribute("messageError", convertToUTF(messageErrorAdd));
      return  "show-error";
    }
    return  "redirect:/author/all";
  }

  @GetMapping("/author/{id}/edit")
  public String authorEdit(@PathVariable(value="id") Long id, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    TheAuthors author = authorsService.getAuthorForId(id);
    if (null == author) return "redirect:/author/all";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("author", author);
    return "authors/author-edit";
  }

  @PostMapping("/author/{id}/edit")
  public String authorPostUpdate(@PathVariable(value="id") Long id, @RequestParam(name = "newNameAuthor") String newNameAuthor, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    int res = authorsService.updateAuthor(id, newNameAuthor);
    if (0 == res) return "redirect:/author/all";
    if (res < 0) {
      authorsService.setCurUserToModel(idUser, model);
      model.addAttribute("messageError", convertToUTF(messageErrorUpdate));
      return  "show-error";
    }
    return  "redirect:/author/all";
  }

  @GetMapping("/author/{id}/remove")
  public String authorDelete(@PathVariable(value="id") Long id, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.removeAuthor(id);
    return "redirect:/author/all";
  }
}
