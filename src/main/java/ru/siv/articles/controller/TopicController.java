package ru.siv.articles.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.siv.articles.model.Topics;

@Controller
public class TopicController extends BaseController {

  @Value("${msg.error.topic.add}")
  private String messageErrorAdd;
  @Value("${msg.error.topic.update}")
  private String messageErrorUpdate;

  @GetMapping("/topic/all")
  public String listTopic(Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("topics", topicsService.listTopic());
    return "topics/topic-list";
  }

  @GetMapping("/topic/add")
  public String topicAdd(Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.setCurUserToModel(idUser, model);
    return "topics/topic-add";
  }

  @PostMapping("/topic/add")
  public String topicPostAdd(@RequestParam(name = "newTopic") String newTopic, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    if (!topicsService.addTopic(newTopic)) {
      authorsService.setCurUserToModel(idUser, model);
      model.addAttribute("messageError", convertToUTF(messageErrorAdd));
      return  "show-error";
    }
    return  "redirect:/topic/all";
  }

  @GetMapping("/topic/{id}/edit")
  public String topicEdit(@PathVariable(value="id") Long id, Model model) {
    Topics topic = topicsService.getTopicForId(id);
    if (null == topic) return "redirect:/topic/all";
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    authorsService.setCurUserToModel(idUser, model);
    model.addAttribute("topic", topic);
    return "topics/topic-edit";
  }

  @PostMapping("/topic/{id}/edit")
  public String topicPostUpdate(@PathVariable(value="id") Long id, @RequestParam(name = "newTopic") String newTopic, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    int res = topicsService.updateTopic(id, newTopic);
    if (0 == res) return "redirect:/topic/all";
    if (res < 0) {
      authorsService.setCurUserToModel(idUser, model);
      model.addAttribute("messageError", convertToUTF(messageErrorUpdate));
      return  "show-error";
    }
    return  "redirect:/topic/all";
  }

  @GetMapping("/topic/{id}/remove")
  public String topicDelete(@PathVariable(value="id") Long id, Model model) {
    Long idUser = sessionService.getUserStatus();
    if (0 != idUser) return "redirect:/";
    topicsService.removeTopic(id);
    return "redirect:/topic/all";
  }
}
