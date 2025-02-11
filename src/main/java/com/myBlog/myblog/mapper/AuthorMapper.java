package com.myBlog.myblog.mapper;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.DTO.AuthorDTO;
import com.myBlog.myblog.model.Author;

@Component
public class AuthorMapper {
  
  public AuthorDTO convertToDTO(Author author) {
    AuthorDTO authorDTO = new AuthorDTO();
    authorDTO.setId(author.getId());
    authorDTO.setFirstname(author.getFirstname());
    authorDTO.setLastname(author.getLastname());
    if (author.getArticleAuthors() != null) {
      authorDTO.setArticleIds(author.getArticleAuthors().stream()
      .filter(articleAuthor -> articleAuthor.getArticle() != null)
      .map(articleAuthor -> {
        return articleAuthor.getArticle().getId();
      }).toList()); 
    }
    return authorDTO;
  }
  
}
