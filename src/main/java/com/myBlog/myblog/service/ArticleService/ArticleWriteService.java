package com.myBlog.myblog.service.ArticleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.mapper.ArticleMapper;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.ArticleAuthor;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.repository.ArticleAuthorRepository;
import com.myBlog.myblog.repository.ArticleRepository;
import com.myBlog.myblog.repository.AuthorRepository;
import com.myBlog.myblog.repository.CategoryRepository;
import com.myBlog.myblog.repository.ImageRepository;

@Service
public class ArticleWriteService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;
  private final CategoryRepository categoryRepository;
  private final ImageRepository imageRepository;
  private final AuthorRepository authorRepository;
  private final ArticleAuthorRepository articleAuthorRepository;

  public ArticleWriteService(ArticleRepository articleRepository, ArticleMapper articleMapper, CategoryRepository categoryRepository, ImageRepository imageRepository, AuthorRepository authorRepository, ArticleAuthorRepository articleAuthorRepository) {
    this.articleRepository = articleRepository;
    this.articleMapper = articleMapper;
    this.categoryRepository = categoryRepository;
    this.imageRepository = imageRepository;
    this.articleAuthorRepository = articleAuthorRepository;
    this.authorRepository = authorRepository;
  }

  public ArticleDTO createArticle(Article article) {
    article.setCreatedAt(LocalDateTime.now());
    article.setUpdatedAt(LocalDateTime.now());

    if (article.getCategory() != null) {
      Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
      if(category == null) return null;
        article.setCategory(category);
    }

    if (article.getImages() != null && !article.getImages().isEmpty()) {
      List<Image> validImages = new ArrayList<>();
      for (Image image : article.getImages()) {
        if (image.getId() != null) {
          Image existingImage = imageRepository.findById(image.getId()).orElse(null);
          if(existingImage != null) {
            validImages.add(existingImage);
          } else {
          return null;
          }
        } else {
          Image savedImage = imageRepository.save(image);
          validImages.add(savedImage);
        }
      }
        article.setImages(validImages);
    }
    Article savedArticle = articleRepository.save(article);

    if (article.getArticleAuthors() != null) {
      for (ArticleAuthor articleAuthor : article.getArticleAuthors()) {
        Author author = articleAuthor.getAuthor();
        author = authorRepository.findById(author.getId()).orElse(null);
        if (author == null) return null;

        articleAuthor.setAuthor(author);
        articleAuthor.setArticle(savedArticle);
        articleAuthor.setContribution(articleAuthor.getContribution());
        articleAuthorRepository.save(articleAuthor);
      }
    }
      return articleMapper.convertToDTO(savedArticle);
  }

  public ArticleDTO updateArticle(Long id, Article articleDetails) {
    Article article = articleRepository.findById(id).orElse(null);
    if (article == null) return null;

    article.setTitle(articleDetails.getTitle());
    article.setContent(articleDetails.getContent());
    article.setUpdatedAt(LocalDateTime.now());

    if (articleDetails.getCategory() != null) {
      Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
      if (category == null) return null;
      article.setCategory(category);
    }

    updateImages(article, articleDetails);
    updateAuthors(article, articleDetails);

    Article updatedArticle = articleRepository.save(article);
    return articleMapper.convertToDTO(updatedArticle);
  }

  public boolean deleteArticle(Long id) {
    Article article = articleRepository.findById(id).orElse(null);

    if (article == null) return false;
      articleAuthorRepository.deleteAll(article.getArticleAuthors());
      articleRepository.delete(article);
      return true;
  }

  private void updateAuthors(Article article, Article articleDetails) {
    if (articleDetails.getArticleAuthors() != null) {

      for (ArticleAuthor oldArticleAuthor : article.getArticleAuthors()) {
        articleAuthorRepository.delete(oldArticleAuthor);
      }
      List<ArticleAuthor> updatedArticleAuthors = new ArrayList<>();

      for (ArticleAuthor articleAuthorDetails : articleDetails.getArticleAuthors()) {
        Author author = articleAuthorDetails.getAuthor();
        author = authorRepository.findById(author.getId()).orElse(null);
        if (author == null) return;

        ArticleAuthor newArticleAuthor = new ArticleAuthor();
        newArticleAuthor.setAuthor(author);
        newArticleAuthor.setArticle(article);
        newArticleAuthor.setContribution(articleAuthorDetails.getContribution());
        updatedArticleAuthors.add(newArticleAuthor);
      }

      for (ArticleAuthor articleAuthor : updatedArticleAuthors) {
        articleAuthorRepository.save(articleAuthor);
      }
      article.setArticleAuthors(updatedArticleAuthors);
    }
  }

  private void updateImages(Article article, Article articleDetails) {
    if (articleDetails.getImages() != null) {
      List<Image> validImages = new ArrayList<>();
      for (Image image : articleDetails.getImages()) {
        if (image.getId() != null) {
          Image existingImage = imageRepository.findById(image.getId()).orElse(null);
          if (existingImage != null) {
            validImages.add(existingImage);
          }
        } else {
          Image savedImage = imageRepository.save(image);
          validImages.add(savedImage);
        }
      }
      article.setImages(validImages);
    } else {
      article.getImages().clear();
    }
  
  }
}
