package ru.siv.articles.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.siv.articles.model.Articles;

import java.util.List;

@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {
  @Query("SELECT a FROM Articles a ORDER BY a.topic.name, a.author.name, a.title")
  List<Articles> filterAllOrderByTopicOrderByAuthorOrderByTitle();
  @Query("SELECT a FROM Articles a WHERE a.author.id = :idUser AND lower(a.title) = lower(:title)")
  List<Articles> filterByAuthorAndTitle(Long idUser, String title);
  @Query("SELECT a FROM Articles a WHERE lower(a.title) = lower(:title) AND lower(a.article) = lower(:text)")
  List<Articles> filterCompareByTitleByText(String title, String text);
}
