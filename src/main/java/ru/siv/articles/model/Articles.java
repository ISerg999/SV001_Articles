package ru.siv.articles.model;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Articles {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private TheAuthors author;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topics topic;

  @NonNull
  private String title;

  private String article;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime modTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TheAuthors getAuthor() {
    return author;
  }

  public void setAuthor(TheAuthors author) {
    this.author = author;
  }

  public Topics getTopic() {
    return topic;
  }

  public void setTopic(Topics topic) {
    this.topic = topic;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  public LocalDateTime getModTime() {
    return modTime;
  }

  public void setModTime(LocalDateTime modTime) {
    this.modTime = modTime;
  }

  public Articles() { }

  public Articles(Articles article) {
    this.author = article.author;
    this.topic = article.topic;
    this.title = article.title;
    this.article = article.article;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Articles articles = (Articles) o;
    return id != null && Objects.equals(id, articles.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
