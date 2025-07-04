package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToBidConverter implements Converter<String, Bid> {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }

    @Override
    public Bid convert(@NonNull String id) {
        int TheId = Integer.parseInt(id);
                return articleService.consultBidById(TheId);
    }
}
