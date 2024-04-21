package pl.pwr.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.pwr.entity.Charge;
import pl.pwr.repository.ChargeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChargeService {

    private final ChargeRepository chargeRepository;

    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public List<Charge> findAll() {
        return chargeRepository.findAll();
    }

    public Charge addCharge(Charge charge) {
        return chargeRepository.save(charge);
    }

    public List<Charge> findOverdueCharges(LocalDate currentDate) {
        return chargeRepository.findByPaymentDueDateBeforeAndPaidIsFalse(currentDate);
    }

    public Optional<Charge> findById(Long id) {
        return chargeRepository.findById(id);
    }

    public Charge save(Charge charge) {
        return chargeRepository.save(charge);
    }

    public ResponseEntity<Charge> update(Long id, Charge chargeDetails) {
        return chargeRepository.findById(id)
                .map(charge -> {
                    charge.setPaymentDueDate(chargeDetails.getPaymentDueDate());
                    charge.setAmountDue(chargeDetails.getAmountDue());
                    Charge updatedCharge = chargeRepository.save(charge);
                    return ResponseEntity.ok(updatedCharge);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> delete(Long id) {
        return chargeRepository.findById(id)
                .map(charge -> {
                    chargeRepository.delete(charge);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void deleteAll() {
        chargeRepository.deleteAll();
    }
}
