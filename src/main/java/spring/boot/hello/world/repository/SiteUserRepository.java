package spring.boot.hello.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.hello.world.model.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsername(String username);
    boolean existsByUsername(String username);
}
