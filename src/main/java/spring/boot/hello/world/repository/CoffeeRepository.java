package spring.boot.hello.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.hello.world.model.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
