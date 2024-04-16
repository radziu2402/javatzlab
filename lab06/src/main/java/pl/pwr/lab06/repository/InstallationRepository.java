package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.Installation;

public interface InstallationRepository extends JpaRepository<Installation, Long> {
}
