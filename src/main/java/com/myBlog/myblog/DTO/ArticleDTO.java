package com.myBlog.myblog.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.myBlog.myblog.model.ArticleAuthor;

public class ArticleDTO {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime updatedAt;
  private String categoryName;
  private List<String> imageUrls;
  private List<AuthorDTO> articleAuthors;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

    public List<AuthorDTO> getArticleAuthors() {
    return articleAuthors;
  }

  public void setArticleAuthors(List<AuthorDTO> articleAuthors) {
    this.articleAuthors = articleAuthors;
  }

}
