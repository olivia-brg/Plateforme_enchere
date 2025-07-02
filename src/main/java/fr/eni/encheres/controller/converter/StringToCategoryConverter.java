package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.article.ArticleServiceImpl;
import fr.eni.encheres.bo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @Override
    public Category convert(String id) {
        Integer TheId = Integer.parseInt(id);
        return articleService.consultCategoryById(TheId);
    }
}
