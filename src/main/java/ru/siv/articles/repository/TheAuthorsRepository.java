package ru.siv.articles.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.siv.articles.model.TheAuthors;

import java.util.List;

@Repository
public interface TheAuthorsRepository extends CrudRepository<TheAuthors, Long> {
  TheAuthors findByNameIgnoreCase(String name);
  @Query("SELECT ta FROM TheAuthors ta ORDER BY ta.name")
  List<TheAuthors> filterAllOrderByName();
}
