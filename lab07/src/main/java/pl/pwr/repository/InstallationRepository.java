package pl.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.entity.Installation;

public interface InstallationRepository extends JpaRepository<Installation, Long> {
}
