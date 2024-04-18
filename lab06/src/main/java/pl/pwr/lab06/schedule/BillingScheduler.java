package pl.pwr.lab06.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pwr.lab06.entity.Charge;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.service.ChargeService;
import pl.pwr.lab06.service.InstallationService;
import pl.pwr.lab06.service.TimeSimulationService;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BillingScheduler {

    private final TimeSimulationService timeSimulationService;

    private final ChargeService chargeService;

    private final InstallationService installationService;

    public BillingScheduler(TimeSimulationService timeSimulationService, ChargeService chargeService, InstallationService installationService) {
        this.timeSimulationService = timeSimulationService;
        this.chargeService = chargeService;
        this.installationService = installationService;
    }

    @Scheduled(fixedRate = 120000)
    public void generateMonthlyCharges() {
        LocalDateTime simulatedCurrentTime = timeSimulationService.getSimulatedTime();

        installationService.findAll().forEach(installation -> {
            BigDecimal amountDue = calculateAmountDue(installation);
            Charge newCharge = new Charge(simulatedCurrentTime.plusDays(30).toLocalDate(), amountDue, installation);
            chargeService.addCharge(newCharge);
        });

        timeSimulationService.advanceTimeByDays(30);
        System.out.println("Generated charges for all installations for month starting on " + simulatedCurrentTime);
    }

    @Scheduled(fixedRate = 120000)
    public void checkOverdueCharges() {
        LocalDateTime currentTime = timeSimulationService.getSimulatedTime();

        List<Charge> overdueCharges = chargeService.findOverdueCharges(currentTime.toLocalDate());
        if (!overdueCharges.isEmpty()) {
            try {
                FileWriter writer = new FileWriter("opoznione_platnosci_log.txt", true);
                for (Charge charge : overdueCharges) {
                    String logMessage = String.format("Opóźniona płatność za instalację o numerze routera %s. Właściciel: %s %s. Termin płatności: %s%n",
                            charge.getInstallation().getRouterNumber(),
                            charge.getInstallation().getCustomer().getFirstName(),
                            charge.getInstallation().getCustomer().getLastName(),
                            charge.getPaymentDueDate());
                    writer.write(logMessage);
                }
                writer.close();
                System.out.println("File with delayed payments updated: " + currentTime);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BigDecimal calculateAmountDue(Installation installation) {
        if (installation.getServiceType() != null && installation.getServiceType().getPrice() != null) {
            return installation.getServiceType().getPrice();
        } else {
            return new BigDecimal("100.00");
        }
    }
}
