package pl.pwr.lab06.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.lab06.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
