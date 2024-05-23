package com.ufsc.controle.acesso.acesso.repository;

import com.ufsc.controle.acesso.acesso.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessRepository extends JpaRepository<Access, UUID> {

}
