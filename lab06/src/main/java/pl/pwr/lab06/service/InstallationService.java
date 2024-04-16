package pl.pwr.lab06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.repository.InstallationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstallationService {

    private final InstallationRepository installationRepository;

    @Autowired
    public InstallationService(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    public List<Installation> findAll() {
        return installationRepository.findAll();
    }

    public Optional<Installation> findById(Long id) {
        return installationRepository.findById(id);
    }

    public Installation save(Installation installation) {
        return installationRepository.save(installation);
    }

    public void delete(Long id) {
        installationRepository.deleteById(id);
    }
}
