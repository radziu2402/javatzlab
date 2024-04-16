package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pwr.lab06.entity.Customer;
import pl.pwr.lab06.service.CustomerService;
import pl.pwr.lab06.ui.layout.MainLayout;

@Route(value = "klienci", layout = MainLayout.class)
@PageTitle("Klienci | Zapnet Internet")
public class CustomerView extends VerticalLayout {

    private CustomerService customerService;
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private Button addNewBtn = new Button("Dodaj nowego klienta", VaadinIcon.PLUS.create());

    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;
        addNewBtn.addClickListener(e -> editCustomer(new Customer()));
        add(addNewBtn, grid);
        grid.setColumns("firstName", "lastName", "customerNumber");
        grid.asSingleSelect().addValueChangeListener(e -> editCustomer(e.getValue()));
        updateList();
    }

    private void updateList() {
        grid.setItems(customerService.findAll());
    }

    private void editCustomer(Customer customer) {
        if (customer == null) {
            return;
        }
        Dialog dialog = new Dialog();
        TextField firstName = new TextField("Imię");
        firstName.setValue(customer.getFirstName() != null ? customer.getFirstName() : "");
        TextField lastName = new TextField("Nazwisko");
        lastName.setValue(customer.getLastName() != null ? customer.getLastName() : "");
        TextField customerNumber = new TextField("Numer klienta");
        customerNumber.setValue(customer.getCustomerNumber() != null ? customer.getCustomerNumber() : "");
        Button save = new Button("Zapisz", e -> {
            customer.setFirstName(firstName.getValue());
            customer.setLastName(lastName.getValue());
            customer.setCustomerNumber(customerNumber.getValue());
            customerService.save(customer);
            updateList();
            dialog.close();
            Notification.show("Klient zapisany.");
        });
        Button delete = new Button("Usuń", e -> {
            Dialog confirmDialog = new Dialog();
            confirmDialog.add("Czy na pewno chcesz usunąć klienta?");
            Button confirmButton = new Button("Tak", event -> {
                customerService.delete(customer.getId());
                updateList();
                dialog.close();
                confirmDialog.close();
                Notification.show("Klient usunięty.");
            });
            Button cancelButton = new Button("Nie", event -> confirmDialog.close());
            confirmDialog.add(confirmButton, cancelButton);
            confirmDialog.open();
        });
        dialog.add(firstName, lastName, customerNumber, save, delete);
        dialog.open();
    }
}
