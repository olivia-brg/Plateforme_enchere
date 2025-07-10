package fr.eni.encheres.controller;


import fr.eni.encheres.bll.article.ArticleService;

import fr.eni.encheres.bll.bid.BidService;
import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bll.ImageService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.exception.BusinessException;
<<<<<<< Updated upstream
=======
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
>>>>>>> Stashed changes

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.eni.encheres.dal.AddressDAO;
import fr.eni.encheres.dal.ArticleDAO;

import fr.eni.encheres.dal.CategoryDAO;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {


    private final UserService userService;
<<<<<<< Updated upstream
    private ArticleDAO articleDAO;
    private ArticleService articleService;
    private AddressDAO addressDAO;
    private CategoryDAO categoryDAO;
    private BidService bidService;


    EnchereController(ArticleService articleService, ArticleDAO articleDAO, AddressDAO addressDAO, CategoryDAO categoryDAO, UserService userService, BidService bidService) {
		this.articleService = articleService ;
=======
    private final ArticleDAO articleDAO;
    private final ArticleService articleService;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final BidService bidService;
    private final ImageService imageService;



    EnchereController(ArticleService articleService,
                      ArticleDAO articleDAO,
                      AddressDAO addressDAO,
                      CategoryDAO categoryDAO,
                      UserService userService,
                      BidService bidService,
                      ImageService imageService
    ) {
        this.articleService = articleService;
>>>>>>> Stashed changes
        this.articleDAO = articleDAO;
        this.addressDAO = addressDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
        this.bidService = bidService;
        this.imageService = imageService;


    }

    @RequestMapping(path = {"/", "/encheres"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("connectedUser") User connectedUser,@RequestParam(required = false) Long category,@RequestParam(required = false) String search, Model model) throws BusinessException {
    	List<Article> articles = articleService.consultArticles();
        List<Category> listeCategories = articleService.consultCategories();

        model.addAttribute("listeCategories", listeCategories);
        model.addAttribute("category", category);

        if (search != null && !search.isBlank()) {
            articles = articles.stream().filter( article ->article.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("article", articles);


        return "encheres";
    }

    @GetMapping("/sell")
    public String newArticle(@ModelAttribute("connectedUser") User connectedUser, Model model){
        List<Category> listeCategories = articleService.consultCategories();
        Article article = new Article();
        Address tempAdress = new Address();
        tempAdress.setCity(userService.findById(connectedUser.getId()).getCity());
        tempAdress.setStreet(userService.findById(connectedUser.getId()).getStreet());
        tempAdress.setPostalCode(userService.findById(connectedUser.getId()).getPostalCode());
        article.setWithdrawalAddress(tempAdress);
        model.addAttribute("article", article);
        model.addAttribute("listeCategories", listeCategories);
        return "new-product";
    }

<<<<<<< Updated upstream
    @PostMapping(path="/sell")
    String insererArticle(@ModelAttribute("article") Article article, @ModelAttribute("connectedUser") User connectedUser ){
        article.setUser(connectedUser);
=======
    @PostMapping(path = "/sell")
    String insererArticle(@Valid @ModelAttribute("article") Article article,
                          BindingResult bindingResult,
                          @ModelAttribute("connectedUser") User connectedUser,
                          @RequestParam(value="picture", required = false
                          ) MultipartFile imageFile,
                          Model model) {

        User user  = userService.findById(connectedUser.getId());
        article.setUser(user);

        logger.info("connectedUser info : {}", user.toString());
>>>>>>> Stashed changes
        article.setAuctionStartDate(LocalDateTime.now());
        logger.info("user associé {}", article.getUser());
        logger.info("===== DEBUG IMAGE =====");
        logger.info("ImageFile is null: {}", imageFile == null);
        if (imageFile != null) {
            logger.info("ImageFile empty: {}", imageFile.isEmpty());
            logger.info("ImageFile original name: {}", imageFile.getOriginalFilename());
            logger.info("ImageFile size: {}", imageFile.getSize());
            logger.info("ImageFile content type: {}", imageFile.getContentType());
        }
        logger.info("========================");

<<<<<<< Updated upstream

        //redirection vers l'accueil pour le moment je n'arrive pas a renvoyer sur la page article en conservant l'id.
        return "redirect:/";
        //return "redirect:/detailArticle(id=${article.id})";
        //"@{/detailArticle(id=${a.id})}" a essayer d'ajouter
=======
        logger.info("info image : {}", imageFile.getOriginalFilename());
        logger.info("BindingResult has errors: {}", bindingResult.hasErrors());

        if (bindingResult.hasErrors()) {
            logger.warn("Erreurs de validation détectées:");
            bindingResult.getAllErrors().forEach(error -> {
                logger.warn("Erreur: {}", error.getDefaultMessage());
            });

            List<Category> listeCategories = articleService.consultCategories();
            model.addAttribute("listeCategories", listeCategories);
            return "new-product";
        }

        try {
            // Gestion de l'upload d'image
            if (imageFile != null &&
                    !imageFile.isEmpty()) {
                logger.info("=== APPEL DU SERVICE SAVEIMAGE ===");
                String filename = imageService.saveImage(imageFile);
                article.setImageURL("/img/"+filename);
                logger.info("Image sauvegardée avec succès : {}", filename);

            }



            // Appel du service pour créer l'article
            articleService.createArticle(article, connectedUser.getId());

            model.addAttribute("article", article);

        } catch (IOException e) {
            logger.error("Erreur lors de l'upload de l'image", e);
            model.addAttribute("errorMessage", "Erreur lors du téléchargement de l'image");
            List<Category> listeCategories = articleService.consultCategories();
            model.addAttribute("listeCategories", listeCategories);
            return "new-product";
        } catch (IllegalArgumentException e) {
            logger.error("Fichier image invalide", e);
            model.addAttribute("errorMessage", e.getMessage());
            List<Category> listeCategories = articleService.consultCategories();
            model.addAttribute("listeCategories", listeCategories);
            return "new-product";
        }

        return "redirect:/detail-vente";
>>>>>>> Stashed changes
    }


    @GetMapping("/detailArticle")
    public String afficherUnArticle(@RequestParam(name = "id") int id, Model model, @ModelAttribute("connectedUser") User connectedUser) {
        User user = connectedUser;
        Article current = articleService.consultArticleById(id);
<<<<<<< Updated upstream
=======
        System.out.println("Article pic ID is" + current.getImageURL());

>>>>>>> Stashed changes
        if (current != null) {

            User vendeur = userService.findById(current.getUser().getId());
            Address address = articleService.consultAddressById(current.getWithdrawalAddress().getDeliveryAddressId());
            Category category = articleService.consultCategoryById(current.getCategory().getId());
            current.setUser(vendeur);
            current.setWithdrawalAddress(address);

            current.setCategory(category);

            model.addAttribute("article", current);

            model.addAttribute("connectedUser", user);

            Bid maxBid = bidService.getHighestBid(current.getId());
            model.addAttribute("imgUrl", current.getImageURL());
            model.addAttribute("maxBid", maxBid);

        }else
        {System.out.println("Article inconnu!!");}
        return "detail-vente";
    }

    @ModelAttribute("connectedUser")
    public User AddUser() {
        System.out.println("Add Attribut User to Session");
        return new User();
    }

    @PostMapping("/bid")
    public String newBid(@ModelAttribute("connectedUser") User connectedUser,
                         @RequestParam("user-bid") float bidAmount,
                         @RequestParam("articleId") int articleId,
                         Model model) throws BusinessException {

        Article currentArticle = articleService.consultArticleById(articleId);
        User vendeur = userService.findById(currentArticle.getUser().getId());
        Address address = articleService.consultAddressById(currentArticle.getWithdrawalAddress().getDeliveryAddressId());
        Category category = articleService.consultCategoryById(currentArticle.getCategory().getId());
        currentArticle.setUser(vendeur);
        currentArticle.setWithdrawalAddress(address);
        currentArticle.setCategory(category);


        Bid bid = new Bid();

        try {
            userService.isCreditValid(bidAmount, connectedUser.getId());
            bidService.isBidValid(bidAmount, currentArticle.getId());

            userService.substractCredit(bidAmount, connectedUser.getId());
            Bid maxBid = bidService.getHighestBid(currentArticle.getId());

            if (maxBid != null) {
            userService.addCredit(maxBid.getBidAmount(), maxBid.getArticle().getUser().getId());
            }

            bid.setArticle(currentArticle);
            bid.setBidAmount(bidAmount);
            bid.setBidDate(LocalDate.now());

            bidService.createBid(bid, connectedUser.getId(), currentArticle.getId());
            maxBid = bidService.getHighestBid(currentArticle.getId());

            model.addAttribute("bid", bid);
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("article", currentArticle);



            return "detail-vente";

        }catch (BusinessException be){
            Bid maxBid = bidService.getHighestBid(currentArticle.getId());
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("bid", bid);
            model.addAttribute("article", currentArticle);
            model.addAttribute("errorMessages", be.getMessages());
            return "detail-vente";
        }
    }


}