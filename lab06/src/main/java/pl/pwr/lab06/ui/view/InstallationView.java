package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pwr.lab06.entity.Address;
import pl.pwr.lab06.entity.Customer;
import pl.pwr.lab06.entity.Installation;
import pl.pwr.lab06.entity.PriceList;
import pl.pwr.lab06.service.CustomerService;
import pl.pwr.lab06.service.InstallationService;
import pl.pwr.lab06.service.PriceListService;
import pl.pwr.lab06.ui.layout.MainLayout;

@Route(value = "instalacje", layout = MainLayout.class)
@PageTitle("Instalacje | Zapnet Internet")
public class InstallationView extends VerticalLayout {

    private final InstallationService installationService;
    private final PriceListService priceListService;
    private final CustomerService customerService;
    private final Grid<Installation> grid = new Grid<>(Installation.class);
    private final Button addNewBtn = new Button("Dodaj nową instalację", VaadinIcon.PLUS.create());

    @Autowired
    public InstallationView(InstallationService installationService, CustomerService customerService, PriceListService priceListService) {
        this.customerService = customerService;
        this.installationService = installationService;
        this.priceListService = priceListService;
        configureGrid();
        configureAddNewButton();
        add(addNewBtn, grid);
        updateList();
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(installation -> installation.getAddress() != null ? installation.getAddress().getStreet() : "").setHeader("Ulica");
        grid.addColumn(installation -> installation.getAddress() != null ? installation.getAddress().getCity() : "").setHeader("Miasto");
        grid.addColumn(installation -> installation.getAddress() != null ? installation.getAddress().getPostalCode() : "").setHeader("Kod pocztowy");
        grid.addColumn(installation -> installation.getAddress() != null ? installation.getAddress().getCountry() : "").setHeader("Kraj");
        grid.addColumn(Installation::getRouterNumber).setHeader("Numer routera");
        grid.addColumn(installation -> installation.getServiceType() != null ? installation.getServiceType().getServiceType() : "").setHeader("Typ usługi");
        grid.addColumn(installation -> {
            Customer customer = installation.getCustomer();
            return customer != null ? customer.getFirstName() + " " + customer.getLastName() : "";
        }).setHeader("Klient");

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                editInstallation(event.getValue());
            }
        });
    }

    private void configureAddNewButton() {
        addNewBtn.addClickListener(click -> editInstallation(new Installation()));
    }

    private void updateList() {
        grid.setItems(installationService.findAll());
    }

    private void editInstallation(Installation installation) {
        Dialog formDialog = new Dialog();
        formDialog.setHeaderTitle(installation.getId() == null ? "Nowa instalacja" : "Edytuj instalację");

        TextField streetField = new TextField("Ulica");
        TextField cityField = new TextField("Miasto");
        TextField postalCodeField = new TextField("Kod pocztowy");
        TextField countryField = new TextField("Kraj");
        TextField routerNumberField = new TextField("Numer routera");
        ComboBox<PriceList> serviceTypeField = new ComboBox<>("Typ usługi");
        serviceTypeField.setItemLabelGenerator(PriceList::getServiceType);
        serviceTypeField.setItems(priceListService.findAll());
        serviceTypeField.setValue(installation.getServiceType());
        if (installation.getAddress() == null) {
            installation.setAddress(new Address());
        }
        streetField.setValue(installation.getAddress().getStreet() != null ? installation.getAddress().getStreet() : "");
        cityField.setValue(installation.getAddress().getCity() != null ? installation.getAddress().getCity() : "");
        postalCodeField.setValue(installation.getAddress().getPostalCode() != null ? installation.getAddress().getPostalCode() : "");
        countryField.setValue(installation.getAddress().getCountry() != null ? installation.getAddress().getCountry() : "");

        routerNumberField.setValue(installation.getRouterNumber() != null ? installation.getRouterNumber() : "");

        ComboBox<Customer> customerComboBox = new ComboBox<>("Klient");
        customerComboBox.setItemLabelGenerator(customer -> customer.getFirstName() + " " + customer.getLastName());
        customerComboBox.setItems(customerService.findAll());
        customerComboBox.setValue(installation.getCustomer());

        Button saveButton = new Button("Zapisz", event -> {
            Address address = installation.getAddress();
            if (address == null) {
                address = new Address();
                installation.setAddress(address);
            }

            address.setStreet(streetField.getValue());
            address.setCity(cityField.getValue());
            address.setPostalCode(postalCodeField.getValue());
            address.setCountry(countryField.getValue());

            installation.setRouterNumber(routerNumberField.getValue());
            installation.setServiceType(serviceTypeField.getValue());

            Customer selectedCustomer = customerComboBox.getValue();
            installation.setCustomer(selectedCustomer);

            Installation savedInstallation = installationService.save(installation);
            updateList();
            formDialog.close();
            Notification.show("Instalacja zapisana.");
        });

        Button deleteButton = new Button("Usuń", event -> {
            if (installation.getId() != null) {
                installationService.delete(installation.getId());
                updateList();
                formDialog.close();
                Notification.show("Instalacja usunięta.");
            }
        });
        Button cancelButton = new Button("Anuluj", e -> formDialog.close());

        formDialog.add(streetField, cityField, postalCodeField, countryField, routerNumberField, serviceTypeField, customerComboBox, saveButton, deleteButton, cancelButton);
        formDialog.open();
    }

}
