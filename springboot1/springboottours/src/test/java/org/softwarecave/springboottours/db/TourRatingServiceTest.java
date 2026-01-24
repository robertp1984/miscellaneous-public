package org.softwarecave.springboottours.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.softwarecave.springboottours.db.dto.TourRatingDTO;
import org.softwarecave.springboottours.db.model.Tour;
import org.softwarecave.springboottours.db.model.TourRating;
import org.softwarecave.springboottours.db.repo.NoSuchTourException;
import org.softwarecave.springboottours.db.repo.TourRatingRepository;
import org.softwarecave.springboottours.db.repo.TourRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class TourRatingServiceTest {

    @InjectMocks
    private TourRatingService tourRatingService;

    @Mock
    private TourRepository tourRepository;

    @Mock
    private TourRatingRepository tourRatingRepository;

    @Test
    public void testAddTourRating_NoSuchTourException() {
        long id = 10000L;
        doReturn(Optional.empty()).when(tourRepository).findById(id);

        assertThrows(NoSuchTourException.class, () -> tourRatingService.addTourRating(id, createDummyTourRatingDTO()));

        verify(tourRepository, times(1)).findById(id);
        verifyNoInteractions(tourRatingRepository);
    }

    @Test
    public void testAddTourRating_Success() {
        long id = 1000L;
        TourRatingDTO tourRatingDTO = createDummyTourRatingDTO();
        Tour tour = new Tour();
        doReturn(Optional.of(tour)).when(tourRepository).findById(id);
        doReturn(null).when(tourRatingRepository).save(any(TourRating.class));

        tourRatingService.addTourRating(id, tourRatingDTO);

        ArgumentCaptor<TourRating> tourRatingArgumentCaptor = ArgumentCaptor.forClass(TourRating.class);
        verify(tourRepository, times(1)).findById(id);
        verify(tourRatingRepository, times(1)).save(tourRatingArgumentCaptor.capture());
        assertThat(tourRatingArgumentCaptor.getValue())
                .hasFieldOrPropertyWithValue("clientId", tourRatingDTO.getClientId())
                .hasFieldOrPropertyWithValue("comment", tourRatingDTO.getComment())
                .hasFieldOrPropertyWithValue("score", tourRatingDTO.getRating());
    }

    private TourRatingDTO createDummyTourRatingDTO() {
        TourRatingDTO dto = new TourRatingDTO();
        dto.setTourCode("AA");
        dto.setClientId(1);
        dto.setComment("Great tour");
        dto.setRating(5);
        return dto;
    }
}
