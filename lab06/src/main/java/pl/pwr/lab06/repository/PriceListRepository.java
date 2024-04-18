package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    PriceList findByServiceType(String serviceType);
}
