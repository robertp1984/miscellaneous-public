package org.softwarecave.springboottours.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourRatingDTO {
    @NonNull
    private String tourCode;
    @NonNull
    private Long clientId;
    @NonNull
    private String comment;

    private int rating;

}
