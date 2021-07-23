package spring.boot.hello.world.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.boot.hello.world.model.Department;
import spring.boot.hello.world.repository.DepartmentRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartmentRepository repository;

    public DataLoader(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department department = new Department();
        department.setName("営業");
        repository.save(department);

        department = new Department();
        department.setName("開発");
        repository.save(department);
    }
}
