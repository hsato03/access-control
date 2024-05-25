package com.ufsc.access.control.user.repository;

import com.ufsc.access.control.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCpf(String cpf);
}
