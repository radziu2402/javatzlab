package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
