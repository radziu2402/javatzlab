package pl.pwr.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String customerNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Installation> installations;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String customerNumber, Set<Installation> installations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumber;
        this.installations = installations;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Set<Installation> getInstallations() {
        return installations;
    }

    public void setInstallations(Set<Installation> installations) {
        this.installations = installations;
    }
}
