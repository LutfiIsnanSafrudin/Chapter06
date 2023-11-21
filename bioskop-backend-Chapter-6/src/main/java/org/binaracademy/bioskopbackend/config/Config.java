package org.binaracademy.bioskopbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.bioskopbackend.enumeration.ERole;
import org.binaracademy.bioskopbackend.model.Roles;
import org.binaracademy.bioskopbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {

    @Value("${nama.kelas.binar}")
    private String namaKelas; // ""

    Config(RoleRepository roleRepository) {
//        log.info("Cheking roles presented");
//        for(ERole c : ERole.values()) {
//            try {
//                Roles roles = roleRepository.findByRoleName(c)
//                        .orElseThrow(() -> new RuntimeException("Roles not found"));
//                log.info("Role {} has been found!", roles.getRoleName());
//            } catch(RuntimeException rte) {
//                log.info("Role {} is not found, inserting to DB . . .", c.name());
//                Roles roles = new Roles();
//                roles.setRoleName(c);
//                roleRepository.save(roles);
//            }
//        }
    }

    @Bean
    public String basicConfiguration() {
        log.info("Basic configuration is initializing. . .");
        log.info("Initialize success by {}", namaKelas);
        return this.namaKelas;
    }
}
