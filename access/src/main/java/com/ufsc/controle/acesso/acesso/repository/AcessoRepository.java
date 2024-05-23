package com.ufsc.controle.acesso.acesso.repository;

import com.ufsc.controle.acesso.acesso.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AcessoRepository extends JpaRepository<Acesso, UUID> {

}
