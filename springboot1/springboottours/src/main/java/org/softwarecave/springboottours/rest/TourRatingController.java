package org.softwarecave.springboottours.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.softwarecave.springboottours.db.TourRatingService;
import org.softwarecave.springboottours.db.dto.TourRatingDTO;
import org.softwarecave.springboottours.db.model.TourRating;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tours/{tourId}/ratings")
@Slf4j
public class TourRatingController {
    private final TourRatingService tourRatingService;

    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @PostMapping(value = "tourRating", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    private void addTourRating(@RequestBody @Valid TourRatingDTO tourRatingDTO,
                               @PathVariable("tourId") Long tourId) {
        log.info("Called addTourRating for tourId={} with {}", tourId, tourRatingDTO);
        TourRating added = tourRatingService.addTourRating(tourId, tourRatingDTO);
    }
}
