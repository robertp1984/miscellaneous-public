package org.softwarecave.springboottours.db;

import org.softwarecave.springboottours.db.dto.TourRatingDTO;
import org.softwarecave.springboottours.db.model.Tour;
import org.softwarecave.springboottours.db.model.TourRating;
import org.softwarecave.springboottours.db.repo.NoSuchTourException;
import org.softwarecave.springboottours.db.repo.TourRatingRepository;
import org.softwarecave.springboottours.db.repo.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TourRatingService {

    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    public TourRating addTourRating(Long tourId, TourRatingDTO tourRatingDTO) {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (tour.isEmpty()) {
            throw new NoSuchTourException("Tour with id=%s not found".formatted(tourId));
        }

        TourRating tourRating = new TourRating(null, tour.get(), tourRatingDTO.getClientId(),
                tourRatingDTO.getComment(), tourRatingDTO.getRating());
        return tourRatingRepository.save(tourRating);
    }
}
