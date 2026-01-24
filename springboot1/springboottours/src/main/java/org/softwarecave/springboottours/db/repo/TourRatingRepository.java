package org.softwarecave.springboottours.db.repo;

import org.softwarecave.springboottours.db.model.TourRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRatingRepository extends JpaRepository<TourRating,Long> {
}
