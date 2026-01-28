package org.softwarecave.springboottours.db.repo;

import org.softwarecave.springboottours.db.model.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TourPackageRepository extends JpaRepository<TourPackage, String> {
    Optional<TourPackage> findByCode(String code);
}
