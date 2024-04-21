package pl.pwr.service;

import org.springframework.stereotype.Service;
import pl.pwr.entity.Customer;
import pl.pwr.entity.Installation;
import pl.pwr.repository.InstallationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstallationService {

    private final InstallationRepository installationRepository;

    public InstallationService(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    public Optional<Installation> findById(Long id) {
        return installationRepository.findById(id);
    }

    public List<Installation> findAll() {
        return installationRepository.findAll();
    }

    public Installation save(Installation installation) {
        return installationRepository.save(installation);
    }

    public Optional<Installation> update(Long id, Installation installationDetails) {
        return installationRepository.findById(id)
                .map(installation -> {
                    installation.setAddress(installationDetails.getAddress());
                    installation.setRouterNumber(installationDetails.getRouterNumber());
                    installation.setServiceType(installationDetails.getServiceType());
                    return installationRepository.save(installation);
                });
    }

    public boolean delete(Long id) {
        return installationRepository.findById(id)
                .map(installation -> {
                    installationRepository.delete(installation);
                    return true;
                }).orElse(false);
    }
}
