package ru.siv.articles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.siv.articles.model.Topics;
import ru.siv.articles.repository.TopicsRepository;

import java.util.List;

@Component
public class TopicsService extends BaseModule {

  @Autowired
  private TopicsRepository topicsRepository;

  /**
   * Получение темы по её ключу.
   * @param id ключ темы
   * @return тема, или null
   */
  public Topics getTopicForId(Long id) {
    return topicsRepository.findById(id).orElse(null);
  }

  /**
   * Получение списка тем в отсортированном порядке.
   * @return список тем
   */
  public List<Topics> listTopic() {
    return topicsRepository.filterAllOrderByName();
  }

  /**
   * Добавление новой темы.
   * @param newTopic название новой темы
   * @return true - если добавление прошло успешно, иначе false
   */
  public boolean addTopic(String newTopic) {
    Topics topic = topicsRepository.findByNameIgnoreCase(newTopic);
    if (null != topic) return false;
    topic = new Topics();
    topic.setName(newTopic);
    topicsRepository.save(topic);
    return true;
  }

  /**
   * Изменяет название темы.
   * @param id       ключ темы
   * @param newTopic новое название темы
   * @return результат обновления: если больше 0, то успешен, если 0, то либо не найдена тема по id либо новая тема равна старой, меньше 0 то тема с новым названием уже существует
   */
  public int updateTopic(Long id, String newTopic) {
    Topics topic = getTopicForId(id);
    if (null == topic || newTopic.equalsIgnoreCase(topic.getName())) return 0;
    Topics topicForName = topicsRepository.findByNameIgnoreCase(newTopic);
    if (null != topicForName) return -1;
    topic.setName(newTopic);
    topicsRepository.save(topic);
    return 1;
  }

  /**
   * Удаление темы.
   * @param id ключ темы
   */
  public void removeTopic(Long id) {
    Topics topic = getTopicForId(id);
    if (null != topic) topicsRepository.delete(topic);
  }

}
