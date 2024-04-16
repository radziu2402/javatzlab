package pl.pwr.lab06.entity;

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
    private String serviceType;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "installation")
    private Set<Charge> charges;

    @OneToMany(mappedBy = "installation")
    private Set<Payment> payments;

    public Installation() {
    }

    public Installation(Address address, String routerNumber, String serviceType, Customer customer, Set<Charge> charges, Set<Payment> payments) {
        this.address = address;
        this.routerNumber = routerNumber;
        this.serviceType = serviceType;
        this.customer = customer;
        this.charges = charges;
        this.payments = payments;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
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
