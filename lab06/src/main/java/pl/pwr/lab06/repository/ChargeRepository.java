package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.Charge;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
}
