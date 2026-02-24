package com.hackers.freelancia.repository;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Category;

public interface CategoryRepository extends AbstractRepository<Category, String> {

    boolean existsByNameAndStatut(String name, Statut active);

}
