package org.taskspfe.pfe;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.taskspfe.pfe.model.role.Role;
import org.taskspfe.pfe.repository.RoleRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class PfeApplication {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(PfeApplication.class, args);
    }

//    @PostConstruct
//    private void initProject()
//    {
//        initRoles();
//    }
//    private void initRoles()
//    {
//        roleRepository.save(new Role("ADMIN"));
//        roleRepository.save(new Role("CLIENT"));
//        roleRepository.save(new Role("TECHNICIAN"));
//        roleRepository.save(new Role("SUPERVISOR"));
//    }
}
