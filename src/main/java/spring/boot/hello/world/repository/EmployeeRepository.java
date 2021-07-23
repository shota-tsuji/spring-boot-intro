package spring.boot.hello.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.hello.world.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
