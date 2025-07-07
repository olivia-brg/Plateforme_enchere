package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Article;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToArticleConverter implements Converter<String, Article> {
    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }


    @Override
    public Article convert(@NonNull String id) {
        int TheId = Integer.parseInt(id);
        return articleService.consultArticleById(TheId);
    }
}
