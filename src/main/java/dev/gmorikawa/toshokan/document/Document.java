package dev.gmorikawa.toshokan.document;

import java.util.ArrayList;
import java.util.List;

import dev.gmorikawa.toshokan.author.Author;
import dev.gmorikawa.toshokan.category.Category;
import dev.gmorikawa.toshokan.topic.Topic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255)
    private String title;

    @Column(nullable = true)
    private Integer year;

    @JoinTable(
        name = "document_authors",
        joinColumns = @JoinColumn(
            name = "document_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        )
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> authors;

    @Column(length = 1024, nullable = true)
    private String description;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @JoinTable(
        name = "document_topics",
        joinColumns = @JoinColumn(
            name = "document_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "topic_id",
            referencedColumnName = "id"
        )
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Topic> topics;

    public Document() {
        topics = new ArrayList<>();
    }

    public Document(String id, String title, Integer year, List<Author> authors, String description, Category category) {
        this.id = id;
        this.title = title.trim();
        this.year = year;
        this.authors = authors;
        this.description = description.trim();
        this.category = category;

        topics = new ArrayList<>();
    }

    public Document(String title, Integer year, List<Author> authors, String description, Category category) {
        this.title = title.trim();
        this.year = year;
        this.authors = authors;
        this.description = description.trim();
        this.category = category;

        topics = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void addTopics(Topic topic) {
        this.topics.add(topic);
    }

    public abstract String getFilePath();
}