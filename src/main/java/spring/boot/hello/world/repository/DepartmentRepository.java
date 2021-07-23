package spring.boot.hello.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.hello.world.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
