package pl.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
