package ru.siv.articles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionService {

  @Autowired
  private HttpSession session;

  @Value("${session.session.nameAuthor}")
  private String nameSessionAuthor;

  /**
   * Возвращает статус текущего пользователя.
   * @return статус текущего пользователя (-1 - гость, 0 - админ, больше 0 - автор статей)
   */
  public Long getUserStatus() {
    Long idUser;
    try {
      idUser = Long.parseLong((String) session.getAttribute(nameSessionAuthor));
    } catch (NumberFormatException e) {
      idUser = -1L;
    }
    return idUser;
  }

  /**
   * Запоминание в сессии текущего автора.
   * @param idAuthor ключ текущего автора
   */
  public void setSessionAuthor(Long idAuthor) {
    session.setAttribute(nameSessionAuthor, idAuthor.toString());
  }
}
