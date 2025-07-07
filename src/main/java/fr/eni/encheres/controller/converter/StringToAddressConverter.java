package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToAddressConverter implements Converter<String, Address> {


    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @Override
    public Address convert(@NonNull String id) {
        int TheId = Integer.parseInt(id);
        return articleService.consultAddressById(TheId);
    }
}
