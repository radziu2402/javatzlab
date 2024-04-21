package pl.pwr.service;

import org.springframework.stereotype.Service;
import pl.pwr.entity.Address;
import pl.pwr.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public boolean delete(Long id) {
        return addressRepository.findById(id)
                .map(installation -> {
                    addressRepository.delete(installation);
                    return true;
                }).orElse(false);
    }
}
