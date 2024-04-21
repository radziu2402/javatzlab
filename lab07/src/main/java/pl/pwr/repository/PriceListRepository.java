package pl.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.entity.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    PriceList findByServiceType(String serviceType);
}
