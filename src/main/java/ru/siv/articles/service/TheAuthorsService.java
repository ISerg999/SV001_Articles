package ru.siv.articles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.siv.articles.model.TheAuthors;
import ru.siv.articles.repository.TheAuthorsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class TheAuthorsService extends BaseService {

  @Value("${session.nameAdmin}")
  private String nameAdmin;
  @Value("${page.author.user}")
  private String typeUser;
  @Value("${page.author.name}")
  private String pageNameAuthor;
  @Value("${session.nameGuest}")
  private String nameGuest;

  @Autowired
  private TheAuthorsRepository authorsRepository;

  /**
   * Получение объекта автора по его ключу.
   * @param id ключ автора
   * @return объект автора или null
   */
  public TheAuthors getAuthorForId(Long id) {
    return authorsRepository.findById(id).orElse(null);
  }

  /**
   * Возвращает отсортированный список авторов.
   * @return список авторов
   */
  public List<TheAuthors> listAuthor() {
    return authorsRepository.filterAllOrderByName();
  }

  /**
   * Установка на странице текущего пользователя-автора.
   * @param curUser ключ текущего пользователя-автора
   * @param model   модель страницы
   */
  public void setCurUserToModel(Long curUser, Model model) {
    String name = "Not found";
    if (curUser < 0) name = convertToUTF(nameGuest);
    else {
      if (0 == curUser) name = convertToUTF(nameAdmin);
      else {
        TheAuthors author = getAuthorForId(curUser);
        name = null != author ? author.getName(): name;
      }
    }
    model.addAttribute(pageNameAuthor, name);
    model.addAttribute(typeUser, curUser);
  }

  /**
   * Возвращает список авторов вместе с админом для первой страницы.
   * @return список авторов с админом для первой страницы
   */
  public List<TheAuthors> getListAuthorWithAdmin() {
    List<TheAuthors> listAuthors = new ArrayList<>();
    TheAuthors admin = new TheAuthors();
    admin.setId(0L);
    admin.setName(convertToUTF(nameAdmin));
    listAuthors.add(admin);
    listAuthors.addAll(listAuthor());
    return listAuthors;
  }

  /**
   * Добавление нового автора.
   * @param newName имя автора
   * @return true - если добавление прошло успешно, иначе false
   */
  public boolean addAuthor(String newName) {
    List<TheAuthors> authors = (List<TheAuthors>) authorsRepository.findByNameIgnoreCase(newName);
    if (null != authors && !authors.isEmpty()) return false;
    TheAuthors theAuthor = new TheAuthors();
    theAuthor.setName(newName);
    authorsRepository.save(theAuthor);
    return true;
  }

  /**
   * Изменяет имя автора.
   * @param id      ключ обновляемого автора
   * @param newName новое имя автора
   * @return результат обновления: если больше 0, то успешен, если 0, то либо не найден автор по id либо новое имя равно старому, меньше 0 то автор с новым именем уже существует
   */
  public int updateAuthor(Long id, String newName) {
    TheAuthors author = getAuthorForId(id);
    if (null == author || newName.equals(author.getName())) return 0;
    TheAuthors authorForName = authorsRepository.findByNameIgnoreCase(newName);
    if (null != authorForName) return -1;
    author.setName(newName);
    authorsRepository.save(author);
    return 1;
  }

  /**
   * Удаляет автора с заданным ключем.
   * @param id ключ удаляемого автора
   */
  public void removeAuthor(Long id) {
    TheAuthors author = getAuthorForId(id);
    if (null != author) authorsRepository.delete(author);
  }
}
