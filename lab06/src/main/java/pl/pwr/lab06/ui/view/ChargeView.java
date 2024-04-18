package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pwr.lab06.entity.Charge;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.service.ChargeService;
import pl.pwr.lab06.service.InstallationService;
import pl.pwr.lab06.ui.layout.MainLayout;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "naleznosci", layout = MainLayout.class)
@PageTitle("Zarządzanie płatnościami")
public class ChargeView extends VerticalLayout {

    private final ComboBox<Installation> installationComboBox = new ComboBox<>("Wybierz instalację");
    private final Grid<Charge> chargesGrid = new Grid<>(Charge.class);
    private final Button addChargeButton = new Button("Dodaj należność");
    private final Button deleteChargeButton = new Button("Usuń zaznaczone należności");

    private final InstallationService installationService;
    private final ChargeService chargeService;

    @Autowired
    public ChargeView(InstallationService installationService, ChargeService chargeService) {
        this.installationService = installationService;
        this.chargeService = chargeService;
        configureComponents();
    }

    private void configureComponents() {
        configureInstallationComboBox();
        configureChargesGrid();
        configureButtons();
        add(installationComboBox, chargesGrid, addChargeButton, deleteChargeButton);
    }

    private void configureInstallationComboBox() {
        List<Installation> installations = installationService.findAll();
        installationComboBox.setItems(installations);
        installationComboBox.setItemLabelGenerator(installation -> installation.getCustomer().getFirstName() + " "
                + installation.getCustomer().getLastName() + " - " + installation.getRouterNumber());
        installationComboBox.addValueChangeListener(e -> updateChargesGrid(e.getValue()));
    }

    private void configureChargesGrid() {
        chargesGrid.removeAllColumns();
        chargesGrid.addColumn(Charge::getPaymentDueDate).setHeader("Termin płatności");
        chargesGrid.addColumn(Charge::getAmountDue).setHeader("Kwota należności");
        chargesGrid.addColumn(charge -> charge.isPaid() ? "Tak" : "Nie").setHeader("Opłacone");
        chargesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
    }

    private void configureButtons() {
        addChargeButton.addClickListener(e -> showAddChargeDialog(installationComboBox.getValue()));
        deleteChargeButton.addClickListener(e -> deleteSelectedCharges());
    }

    private void updateChargesGrid(Installation installation) {
        if (installation != null) {
            chargesGrid.setItems(chargeService.findChargesByInstallation(installation));
        } else {
            chargesGrid.setItems();
        }
    }

    private void showAddChargeDialog(Installation installation) {
        if (installation == null) {
            return;
        }

        Dialog dialog = new Dialog();
        BigDecimalField amountField = new BigDecimalField("Kwota należności");
        amountField.setPlaceholder("Wprowadź kwotę...");

        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Data płatności");

        Button saveButton = new Button("Zapisz", event -> {
            BigDecimal chargeAmount = amountField.getValue();
            LocalDate paymentDate = datePicker.getValue();

            if (paymentDate == null) {
                Notification.show("Wybierz termin płatności", 3000, Notification.Position.MIDDLE);
                return;
            }

            Charge newCharge = new Charge(paymentDate, chargeAmount, installation);
            chargeService.save(newCharge);

            dialog.close();
            updateChargesGrid(installation);
        });

        Button cancelButton = new Button("Anuluj", event -> dialog.close());
        dialog.add(amountField, datePicker, saveButton, cancelButton);
        dialog.open();
    }


    private void deleteSelectedCharges() {
        List<Charge> selectedCharges = new ArrayList<>(chargesGrid.getSelectedItems());
        if (!selectedCharges.isEmpty()) {
            for (Charge charge : selectedCharges) {
                chargeService.delete(charge);
            }
            updateChargesGrid(installationComboBox.getValue());
        }
    }
}
