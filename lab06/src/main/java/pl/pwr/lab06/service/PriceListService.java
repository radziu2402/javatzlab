package pl.pwr.lab06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.lab06.entity.PriceList;
import pl.pwr.lab06.repository.PriceListRepository;

import java.util.List;

@Service
public class PriceListService {

    private final PriceListRepository priceListRepository;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    public List<PriceList> findAll() {
        return priceListRepository.findAll();
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public void delete(Long id) {
        priceListRepository.deleteById(id);
    }

    public PriceList findByServiceType(String serviceType) {
        return priceListRepository.findByServiceType(serviceType);
    }
}
