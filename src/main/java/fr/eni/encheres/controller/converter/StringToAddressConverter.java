package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Adress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAdressConverter implements Converter<String, Adress> {


    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @Override
    public Adress convert(String id) {
        Integer TheId = Integer.parseInt(id);
        return articleService.consultAdressById(TheId);
    }
}
