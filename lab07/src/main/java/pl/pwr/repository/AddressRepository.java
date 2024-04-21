package pl.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
