package org.softwarecave.springboottours.db.dto;

import org.softwarecave.springboottours.db.model.TourRating;

public class TourRatingDTOConverter {
    public TourRatingDTO convertToTourRatingDTO(TourRating tourRating) {
        return new TourRatingDTO(tourRating.getTour().getCode(), tourRating.getClient().getId(), tourRating.getComment(), tourRating.getScore());
    }

}
