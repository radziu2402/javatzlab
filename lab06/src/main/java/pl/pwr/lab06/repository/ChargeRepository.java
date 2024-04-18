package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.Charge;
import pl.pwr.lab06.entity.Installation;

import java.time.LocalDate;
import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findByInstallation(Installation installation);
    List<Charge> findByInstallationAndPaidFalse(Installation installation);
    List<Charge> findByPaymentDueDateBeforeAndPaidIsFalse(LocalDate currentDate);
}
