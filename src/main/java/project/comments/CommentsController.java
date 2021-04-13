package project.comments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.exception.ResourceNotFoundException;
import project.articles.ArticlesRepository;
import project.articles.Articles;

import java.util.List;

@RestController
public class CommentsController {

    ArticlesRepository articlesRepository;
    CommentsRepository commentsRepository;


    public CommentsController(ArticlesRepository articlesRepository, CommentsRepository commentsRepository) {
        this.articlesRepository = articlesRepository;
        this.commentsRepository = commentsRepository;
    }


    //Get Method to return all comments
    @GetMapping("/comments")
    public List<Comments> listAllComments() {
        return commentsRepository.findAll();
    }


    //return comment of given id
    @GetMapping("/comments/{id}")

    public Comments getCommentById(@PathVariable Long id) {
        return commentsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    //return all comments of given id
    @GetMapping("/articles/{articleId}/comments")
    public List<Comments> getCommentsByID(@PathVariable Long articleId) {

        Articles article = articlesRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return article.getComments();

    }


    //return all comments of given author name
    @GetMapping(value = "/comments", params = {"authorName"})
    public List<Comments> getCommentsByAuthorName(@RequestParam String authorName) {
        return commentsRepository.findByAuthorName(authorName);

    }

    //method Post to generate a comment on given article id
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comments> createComment(@PathVariable Long articleId, @RequestBody Comments comment) {
        Articles article = articlesRepository
                .findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        comment.setRelatedArticle(article);
        commentsRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);

    }

    //method Put to update a given comment by id
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comments> updateCommentById(@PathVariable Long id, @RequestBody Comments upComment) {
        Comments comment = commentsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        upComment.setRelatedArticle(comment.getRelatedArticle());//persist the related article
        upComment.setId(id);//
        commentsRepository.save(upComment);
        return ResponseEntity.ok(upComment);
    }


    //method Delete to delete the comment given by id
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Comments> deleteCommentById(@PathVariable Long id) {
        Comments comment = commentsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentsRepository.delete(comment);

        return ResponseEntity.ok(comment);
    }


}
