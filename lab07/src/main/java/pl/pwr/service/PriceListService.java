package pl.pwr.service;

import org.springframework.stereotype.Service;
import pl.pwr.entity.PriceList;
import pl.pwr.repository.PriceListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PriceListService {

    private final PriceListRepository priceListRepository;

    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    public List<PriceList> findAll() {
        return priceListRepository.findAll();
    }

    public Optional<PriceList> findById(Long id) {
        return priceListRepository.findById(id);
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public Optional<PriceList> update(Long id, PriceList priceListDetails) {
        return priceListRepository.findById(id)
                .map(priceList -> {
                    priceList.setServiceType(priceListDetails.getServiceType());
                    priceList.setPrice(priceListDetails.getPrice());
                    return priceListRepository.save(priceList);
                });
    }

    public boolean delete(Long id) {
        return priceListRepository.findById(id)
                .map(priceList -> {
                    priceListRepository.delete(priceList);
                    return true;
                }).orElse(false);
    }
}
