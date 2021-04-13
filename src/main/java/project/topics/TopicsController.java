package project.topics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.exception.ResourceNotFoundException;
import project.articles.Articles;
import project.articles.ArticlesRepository;


import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicsController {


    TopicsRepository topicsRepository;
    ArticlesRepository articlesRepository;


    public TopicsController(TopicsRepository topicsRepository, ArticlesRepository articlesRepository) {
        this.topicsRepository = topicsRepository;
        this.articlesRepository = articlesRepository;
    }

    //method GET to return all topics
    @GetMapping("/topics")
    public List<Topics> listAllTopics() {
        return topicsRepository.findAll();
    }


    //method to return all topics of an article given by article id
    @GetMapping("/articles/{articleId}/topics")
    public List<Topics> getTopicsFromArticleId( @PathVariable long articleId) {
        Articles article = articlesRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);

        return article.getTopics();
    }


    //method to return all articles of a topic given by topic id
    @GetMapping("/topics/{topicId}/articles")
    public List<Articles> getArticlesFromTopicId( @PathVariable long topicId) {
        Topics topic = topicsRepository
                .findById(topicId)
                .orElseThrow(ResourceNotFoundException::new);

        return topic.getArticles();
    }




    //method POST to create a new topic
    @PostMapping("/topics")
    public ResponseEntity<Topics> createTopic(@RequestBody Topics topic) {

        List<Topics> topicList = topicsRepository.findAll();
        boolean topicExists =false;

        for (Topics t:topicList) {
            if (t.getName().equals(topic.getName())) {
                topicExists = true;
                break;
            }
        }

        if (!topicExists){
            topicsRepository.save(topic);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }


    //Map the topic to an article given by article id
    @PostMapping("/articles/{articleId}/topics")

    public ResponseEntity<Topics> associateTopic(@PathVariable Long articleId, @Valid @RequestBody Topics topic) {

        Articles article = articlesRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);

        //add topic to the list of topics
        List<Topics> currentTopics=article.getTopics();
        currentTopics.add(topic);
        article.setTopics(currentTopics);

        //save the topic
        articlesRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);

    }


    //method PUT to update the topic
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topics> updateTopicById(@PathVariable Long id, @Valid @RequestBody Topics upTopic) {
        Topics topic = topicsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        //persist the articles
        upTopic.setArticles(topic.getArticles());
        upTopic.setId(id);//
        topicsRepository.save(upTopic);
        return ResponseEntity.ok(upTopic);
    }


    //method DELETE to delete a topic given by id
    @DeleteMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Topics> deleteTopicById(@PathVariable Long id) {
        Topics topic = topicsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        topicsRepository.delete(topic);

        return ResponseEntity.ok(topic);
    }


}
