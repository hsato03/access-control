package com.ufsc.access.control.credit.service;

import com.ufsc.access.control.credit.model.Credit;
import com.ufsc.access.control.credit.model.CreditDTO;
import com.ufsc.access.control.credit.repository.CreditRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreditService {

    @Autowired
    CreditRepository repository;

    @Transactional(readOnly = true)
    public Page<Credit> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional(readOnly = true)
    public Credit findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with UUID %s not found.", id)));
    }

    @Transactional
    public Credit save(Credit credit) {
        return repository.save(credit);
    }

    @Transactional
    public Credit update(UUID id, CreditDTO credit) {
        Credit creditToUpdate = findById(id);
        creditToUpdate.setValue(credit.value());
        return repository.save(creditToUpdate);
    }

    public void delete(UUID id) {
        Credit creditToRemove = findById(id);
        repository.delete(creditToRemove);
    }
}
