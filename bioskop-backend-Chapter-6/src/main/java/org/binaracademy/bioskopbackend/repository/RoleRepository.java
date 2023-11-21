package org.binaracademy.bioskopbackend.repository;

import org.binaracademy.bioskopbackend.enumeration.ERole;
import org.binaracademy.bioskopbackend.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(ERole name);

}
