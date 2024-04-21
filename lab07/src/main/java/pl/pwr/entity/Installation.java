package pl.pwr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Installation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String routerNumber;
    @ManyToOne
    private PriceList serviceType;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "installation")
    private Set<Charge> charges;

    @OneToMany(mappedBy = "installation")
    private Set<Payment> payments;

    public Installation() {
    }

    public Long getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRouterNumber() {
        return routerNumber;
    }

    public void setRouterNumber(String routerNumber) {
        this.routerNumber = routerNumber;
    }

    public PriceList getServiceType() {
        return serviceType;
    }

    public void setServiceType(PriceList serviceType) {
        this.serviceType = serviceType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
