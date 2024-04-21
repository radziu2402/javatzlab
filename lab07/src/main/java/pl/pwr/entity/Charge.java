package pl.pwr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDueDate;
    private BigDecimal amountDue;
    private boolean paid = false;

    @ManyToOne
    @JsonIgnore
    private Installation installation;

    public Charge() {
    }

    public Charge(LocalDate paymentDueDate, BigDecimal amountDue, Installation installation) {
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

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
