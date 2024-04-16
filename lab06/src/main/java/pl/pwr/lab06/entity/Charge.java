package pl.pwr.lab06.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDueDate;
    private Double amountDue;

    @ManyToOne
    private Installation installation;

    public Charge() {
    }

    public Charge(LocalDate paymentDueDate, Double amountDue, Installation installation) {
        this.paymentDueDate = paymentDueDate;
        this.amountDue = amountDue;
        this.installation = installation;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
}

