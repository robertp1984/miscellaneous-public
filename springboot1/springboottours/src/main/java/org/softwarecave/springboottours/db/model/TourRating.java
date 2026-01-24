package org.softwarecave.springboottours.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TOUR_RATING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourRating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_rating_seq")
    @SequenceGenerator(name = "tour_rating_seq", sequenceName = "tour_rating_seq", allocationSize = 1)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Tour tour;

    private int clientId;

    private String comment;

    private int score;
}
