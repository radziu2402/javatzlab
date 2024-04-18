package pl.pwr.lab06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.lab06.entity.Charge;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.repository.ChargeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChargeService {

    private final ChargeRepository chargeRepository;

    @Autowired
    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public List<Charge> findChargesByInstallation(Installation installation) {
        return chargeRepository.findByInstallation(installation);
    }

    public Charge addCharge(Charge charge) {
        return chargeRepository.save(charge);
    }

    public List<Charge> findOverdueCharges(LocalDate currentDate) {
        return chargeRepository.findByPaymentDueDateBeforeAndPaidIsFalse(currentDate);
    }
    public Charge save(Charge charge) {
        return chargeRepository.save(charge);
    }

    public void delete(Charge charge) {
        chargeRepository.delete(charge);
    }

    public List<Charge> findUnpaidCharges(Installation installation) {
        return chargeRepository.findByInstallationAndPaidFalse(installation);
    }
}
