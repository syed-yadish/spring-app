package project.articles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.exception.ResourceNotFoundException;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticlesController {


    ArticlesRepository articlesRepository;
    ArticlesUpdate articlesService;

    public ArticlesController(ArticlesRepository articlesRepository, ArticlesUpdate articlesUpdate) {
        this.articlesRepository = articlesRepository;
        this.articlesService = articlesUpdate;
    }


    @Autowired

    //returns all articles
    @GetMapping
    public List<Articles> listAllArticles() {
        return articlesRepository.findAll();
    }


    //returns an article given by id
    @GetMapping("/{id}")
    public ResponseEntity<Articles> getArticleById(@PathVariable Long id) {

        Articles article = articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }


    //method POST to create an article
    @PostMapping
    public ResponseEntity<Articles> createArticle(@RequestBody Articles article) {
        articlesRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }


    //method PUT to update an article given by id
    @PutMapping ("/{id}")
    public ResponseEntity<Articles> updateArticleById(@PathVariable Long id, @RequestBody Articles updatedArticle ){
        Articles article = articlesService.updateArticle(id,updatedArticle);
        /*articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        Articles article = articlesRepository.save(updatedArticle);*/
        return ResponseEntity.ok(article);
    }


    //method DELETE to delete an article given by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Articles> deleteArticleById(@PathVariable Long id){
        Articles article= articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articlesRepository.delete(article);

        return ResponseEntity.ok(article);
    }

}
