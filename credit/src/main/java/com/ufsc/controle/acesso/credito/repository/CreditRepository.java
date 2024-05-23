package com.ufsc.controle.acesso.credito.repository;

import com.ufsc.controle.acesso.credito.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditRepository extends JpaRepository<Credit, UUID> {

}
