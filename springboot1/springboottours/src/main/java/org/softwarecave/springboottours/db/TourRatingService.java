package org.softwarecave.springboottours.db;

import org.softwarecave.springboottours.db.dto.TourRatingDTO;
import org.softwarecave.springboottours.db.dto.TourRatingDTOConverter;
import org.softwarecave.springboottours.db.model.Client;
import org.softwarecave.springboottours.db.model.Tour;
import org.softwarecave.springboottours.db.model.TourRating;
import org.softwarecave.springboottours.db.repo.ClientRepository;
import org.softwarecave.springboottours.db.repo.NoSuchClientException;
import org.softwarecave.springboottours.db.repo.NoSuchTourException;
import org.softwarecave.springboottours.db.repo.TourRatingRepository;
import org.softwarecave.springboottours.db.repo.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourRatingService {

    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;
    private final ClientRepository clientRepository;

    public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository, ClientRepository clientRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
        this.clientRepository = clientRepository;
    }

    public TourRating addTourRating(Long tourId, TourRatingDTO tourRatingDTO) {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (tour.isEmpty()) {
            throw new NoSuchTourException("Tour with id=%s not found".formatted(tourId));
        }
        Optional<Client> client = clientRepository.findById(tourRatingDTO.getClientId());
        if (client.isEmpty()) {
            throw new NoSuchClientException("Client with id=%d not found".formatted(tourRatingDTO.getClientId()));
        }

        TourRating tourRating = new TourRating(null, tour.get(), client.orElseThrow(),
                tourRatingDTO.getComment(), tourRatingDTO.getRating());
        return tourRatingRepository.save(tourRating);
    }

    public List<TourRatingDTO> findByTourId(Long tourId) {
        List<TourRating> tourRatings = tourRatingRepository.findByTourId(tourId);
        TourRatingDTOConverter tourRatingDTOConverter = new TourRatingDTOConverter();
        return tourRatings.stream().map(tourRatingDTOConverter::convertToTourRatingDTO).toList();
    }
}
