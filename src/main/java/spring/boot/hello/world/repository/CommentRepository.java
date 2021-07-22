package spring.boot.hello.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.hello.world.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JpaRepositoryを継承することでデータの取得，保存などが行えるようになる.
}
