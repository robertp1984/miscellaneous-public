package org.softwarecave.springboottours.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.softwarecave.springboottours.db.model.Difficulty;
import org.softwarecave.springboottours.db.model.Region;
import org.softwarecave.springboottours.db.model.Tour;
import org.softwarecave.springboottours.db.model.TourPackage;
import org.softwarecave.springboottours.db.repo.TourPackageRepository;
import org.softwarecave.springboottours.db.repo.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DBLoader {

    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;
    private final ObjectMapper objectMapper;

    public DBLoader(TourRepository tourRepository, TourPackageRepository tourPackageRepository, ObjectMapper objectMapper) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.objectMapper = objectMapper;
    }

    private void createTourPackageIfNotExists(@NonNull String code, @NonNull String name) {
        if (tourPackageRepository.findByCode(code).isEmpty()) {
            tourPackageRepository.save(new TourPackage(code, name));
        }
    }

    private void createTourIfNotExists(@NonNull Tour tour) {
        if (tourRepository.findByCode(tour.getCode()).isEmpty()) {
            tourRepository.save(tour);
            log.info("Tour created: {}", tour);
        }
    }

    private void createTourPackages() {
        log.info("Creating tour packages");
        createTourPackageIfNotExists("BC", "Backpack Cal");
        createTourPackageIfNotExists("CC", "California Calm");
        createTourPackageIfNotExists("CH", "California Hot springs");
        createTourPackageIfNotExists("CY", "Cycle California");
        createTourPackageIfNotExists("DS", "From Desert to Sea");
        createTourPackageIfNotExists("KC", "Kids California");
        createTourPackageIfNotExists("NW", "Nature Watch");
        createTourPackageIfNotExists("SC", "Snowboard Cali");
        createTourPackageIfNotExists("TC", "Taste of California");
        log.info("Created tour packages");
    }

    private TourPackage getTourPackage(String code) {
        return tourPackageRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Tour package not found: " + code));
    }

    public void loadFromFile(@NonNull Reader file) throws IOException {
        createTourPackages();
        List<TourFileEntry> tourFileEntries = objectMapper.readValue(file, new TypeReference<List<TourFileEntry>>() {
        });

        for (TourFileEntry tourFileEntry : tourFileEntries) {
            Tour tour = new Tour(null, tourFileEntry.code, tourFileEntry.name, tourFileEntry.description,
                    tourFileEntry.difficulty, tourFileEntry.region,
                    getTourPackage(tourFileEntry.tourPackageCode));
            createTourIfNotExists(tour);
        }
    }

    private record TourFileEntry(String code,
                                 String name,
                                 String description,
                                 Difficulty difficulty,
                                 Region region,
                                 String tourPackageCode) {
    }
}
