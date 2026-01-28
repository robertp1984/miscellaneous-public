package org.softwarecave.springboottours.db.repo;

import org.softwarecave.springboottours.db.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
