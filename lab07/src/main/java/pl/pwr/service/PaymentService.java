package pl.pwr.service;

import org.springframework.stereotype.Service;
import pl.pwr.entity.Payment;
import pl.pwr.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Optional<Payment> update(Long id, Payment paymentDetails) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setPaymentDate(paymentDetails.getPaymentDate());
                    payment.setPaymentAmount(paymentDetails.getPaymentAmount());
                    return paymentRepository.save(payment);
                });
    }


    public boolean delete(Long id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    paymentRepository.delete(payment);
                    return true;
                }).orElse(false);
    }
}
