package project.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {


    List<Comments> findByAuthorName(String authorName);
   // List<Comments> findByArticleId(Long articleId);

}
