package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pwr.lab06.entity.Charge;
import pl.pwr.lab06.entity.Customer;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.entity.Payment;
import pl.pwr.lab06.service.ChargeService;
import pl.pwr.lab06.service.InstallationService;
import pl.pwr.lab06.service.PaymentService;
import pl.pwr.lab06.ui.layout.MainLayout;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Route(value = "platnosci", layout = MainLayout.class)
@PageTitle("Płatności | Zapnet Internet")
public class PaymentView extends VerticalLayout {

    private final PaymentService paymentService;
    private final ChargeService chargeService;
    private final InstallationService installationService;
    private final Grid<Payment> grid = new Grid<>(Payment.class);
    private final Button addPaymentBtn = new Button("Dodaj wpłatę", VaadinIcon.PLUS.create());
    private Binder<Payment> binder = new Binder<>(Payment.class);

    @Autowired
    public PaymentView(PaymentService paymentService, ChargeService chargeService, InstallationService installationService) {
        this.paymentService = paymentService;
        this.chargeService = chargeService;
        this.installationService = installationService;
        configureGrid();
        add(addPaymentBtn, grid);
        updateList();

        addPaymentBtn.addClickListener(e -> showPaymentForm(new Payment()));
    }

    private void configureGrid() {
        grid.removeAllColumns();

        grid.addColumn(payment -> {
            Installation installation = payment.getInstallation();
            if (installation != null && installation.getCustomer() != null) {
                Customer customer = installation.getCustomer();
                return customer.getFirstName() + " " + customer.getLastName();
            }
            return "";
        }).setHeader("Klient");

        grid.addColumn(payment -> {
            Installation installation = payment.getInstallation();
            if (installation != null) {
                return installation.getServiceType() != null ? installation.getServiceType().getServiceType() : "Brak typu usługi";
            }
            return "";
        }).setHeader("Usługa");

        grid.addColumn(payment -> {
            Installation installation = payment.getInstallation();
            if (installation != null) {
                return installation.getRouterNumber() != null ? installation.getRouterNumber() : "Brak numeru routera";
            }
            return "";
        }).setHeader("Router");

        grid.addColumn(Payment::getPaymentDate).setHeader("Data wpłaty");
        grid.addColumn(Payment::getPaymentAmount).setHeader("Kwota wpłaty");

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showPaymentForm(event.getValue());
            }
        });
    }


    private void updateList() {
        grid.setItems(paymentService.findAll());
    }

    private void showPaymentForm(Payment payment) {
        Dialog dialog = new Dialog();

        DatePicker paymentDate = new DatePicker("Data wpłaty");
        ComboBox<Installation> installationComboBox = new ComboBox<>("Instalacja");
        List<Installation> installations = installationService.findAll();
        installationComboBox.setItems(installations);
        installationComboBox.setItemLabelGenerator(installation -> installation.getCustomer().getFirstName() + " " +
                installation.getCustomer().getLastName() + " - " + installation.getServiceType().getServiceType());

        ComboBox<Charge> chargeComboBox = new ComboBox<>("Należność");
        chargeComboBox.setItemLabelGenerator(charge -> charge.getPaymentDueDate().toString() + " - " + charge.getAmountDue().toString());

        installationComboBox.addValueChangeListener(e -> {
            List<Charge> unpaidCharges = chargeService.findUnpaidCharges(e.getValue());
            chargeComboBox.setItems(unpaidCharges);
        });

        paymentDate.setValue(payment.getPaymentDate());
        installationComboBox.setValue(payment.getInstallation());

        Button saveButton = new Button("Zapisz", e -> {
            Charge selectedCharge = chargeComboBox.getValue();
            if (selectedCharge != null) {
                selectedCharge.setPaid(true);
                chargeService.save(selectedCharge);
            }

            payment.setPaymentDate(paymentDate.getValue());
            payment.setInstallation(installationComboBox.getValue());
            payment.setPaymentAmount(selectedCharge.getAmountDue());
            paymentService.save(payment);

            updateList();
            dialog.close();
            Notification.show("Wpłata zapisana.");
        });

        Button deleteButton = new Button("Usuń", event -> {
            paymentService.delete(payment.getId());
            updateList();
            dialog.close();
            Notification.show("Pozycja usunięta.");
        });

        Button cancelButton = new Button("Anuluj", e -> dialog.close());

        dialog.add(paymentDate, installationComboBox, chargeComboBox, saveButton, deleteButton, cancelButton);
        dialog.open();
    }
}
