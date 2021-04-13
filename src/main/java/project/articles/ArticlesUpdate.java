package project.articles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.exception.ResourceNotFoundException;

@Service
public class ArticlesUpdate {

    ArticlesRepository articlesRepository;

    @Autowired
    public ArticlesUpdate(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }


    public Articles updateArticle(Long id,Articles updatedArticle){

        articlesRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        Articles article = articlesRepository.save(updatedArticle);

        return article;

    }







}
