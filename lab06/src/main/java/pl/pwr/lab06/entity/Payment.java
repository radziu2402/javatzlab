package pl.pwr.lab06.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private Double paymentAmount;

    @ManyToOne
    private Installation installation;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, Double paymentAmount, Installation installation) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.installation = installation;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
}
