package project.topics;

import project.articles.Articles;

import javax.persistence.*;
import java.util.List;


@Entity
public class Topics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;


    @ManyToMany (mappedBy = "topics",cascade = CascadeType.ALL)
    private List<Articles> articles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
