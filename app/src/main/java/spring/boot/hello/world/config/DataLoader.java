package spring.boot.hello.world.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.boot.hello.world.model.Comment;
import spring.boot.hello.world.repository.CommentRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final CommentRepository repository;

    public DataLoader(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Comment comment = new Comment();
        comment.setContent("こんにちは");
        repository.save(comment);

        comment = new Comment();
        comment.setContent("テストコード");
        repository.save(comment);
    }
}
