package com.ufsc.access.control.user.repository;

import com.ufsc.access.control.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<User, UUID> {
}
