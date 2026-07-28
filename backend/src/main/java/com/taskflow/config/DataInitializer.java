package com.taskflow.config;

import com.taskflow.entity.Role;
import com.taskflow.enums.RoleType;
import com.taskflow.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (RoleType rt : RoleType.values()) {
            roleRepository.findByName(rt).orElseGet(() -> {
                Role r = new Role();
                r.setName(rt);
                return roleRepository.save(r);
            });
        }
    }
}

