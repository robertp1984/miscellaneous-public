package org.softwarecave.springboottours.db.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.softwarecave.springboottours.db.model.validation.TourRatingValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourRatingDTO {
    @NonNull
    @NotNull
    @NotBlank
    private String tourCode;

    @NonNull
    @NotNull
    private Long clientId;

    @NonNull
    @NotNull
    @NotBlank
    private String comment;

    @TourRatingValue
    private int rating;
}
