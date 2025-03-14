package com.myBlog.myblog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.AuthorDTO;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.service.AuthorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/authors")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
    List<AuthorDTO> authors = authorService.getAllAuthors();
    if (authors.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(authors);
  
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
      AuthorDTO author = authorService.getAuthorById(id);
      return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
  }

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestBody Author authorBody) {
        AuthorDTO savedAuthor = authorService.createAuthor(authorBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @RequestBody Author authorBody) {
      AuthorDTO author = authorService.updateAuthor(id, authorBody);

      return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
      return authorService.deleteAuthor(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
