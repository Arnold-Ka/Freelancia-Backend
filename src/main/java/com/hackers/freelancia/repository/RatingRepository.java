package com.hackers.freelancia.repository;


import java.util.Set;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.Rating;

public interface RatingRepository extends AbstractRepository<Rating, String> {
    Set<Rating> findByFreelanceProfileId(String freelanceProfileId);
}
