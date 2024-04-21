package pl.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
