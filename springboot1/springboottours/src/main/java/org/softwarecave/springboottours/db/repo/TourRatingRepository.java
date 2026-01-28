package org.softwarecave.springboottours.db.repo;

import org.softwarecave.springboottours.db.model.TourRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TourRatingRepository extends JpaRepository<TourRating,Long> {
    public List<TourRating> findByTourId(Long tourId);
}
