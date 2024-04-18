package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pwr.lab06.entity.PriceList;
import pl.pwr.lab06.service.PriceListService;
import pl.pwr.lab06.ui.layout.MainLayout;

import java.math.BigDecimal;

@Route(value = "cennik", layout = MainLayout.class)
@PageTitle("Cennik | Zapnet Internet")
public class PriceListView extends VerticalLayout {

    private final PriceListService priceListService;
    private final Grid<PriceList> grid = new Grid<>(PriceList.class);
    private final Button addNewBtn = new Button("Dodaj nową pozycję", VaadinIcon.PLUS.create());

    @Autowired
    public PriceListView(PriceListService priceListService) {
        this.priceListService = priceListService;
        configureGrid();
        add(addNewBtn, grid);
        addNewBtn.addClickListener(e -> showPriceListForm(new PriceList()));
        updateList();
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(PriceList::getServiceType).setHeader("Typ usługi");
        grid.addColumn(price -> price.getPrice().toString()).setHeader("Cena");
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showPriceListForm(event.getValue());
            }
        });
    }

    private void updateList() {
        grid.setItems(priceListService.findAll());
    }

    private void showPriceListForm(PriceList priceList) {
        Dialog dialog = new Dialog();
        TextField serviceTypeField = new TextField("Typ usługi");
        TextField priceField = new TextField("Cena");

        Binder<PriceList> binder = new Binder<>(PriceList.class);
        binder.forField(serviceTypeField)
                .bind(PriceList::getServiceType, PriceList::setServiceType);
        binder.forField(priceField)
                .withConverter(new StringToBigDecimalConverter("Musisz wprowadzić liczbę"))
                .bind(PriceList::getPrice, PriceList::setPrice);


        serviceTypeField.setValue(priceList.getServiceType() != null ? priceList.getServiceType() : "");
        priceField.setValue(priceList.getPrice() != null ? priceList.getPrice().toString() : "");

        Button saveButton = new Button("Zapisz", event -> {
            priceList.setServiceType(serviceTypeField.getValue());
            priceList.setPrice(new BigDecimal(priceField.getValue()));
            priceListService.save(priceList);
            updateList();
            dialog.close();
            Notification.show("Pozycja zapisana.");
        });

        Button deleteButton = new Button("Usuń", event -> {
            priceListService.delete(priceList.getId());
            updateList();
            dialog.close();
            Notification.show("Pozycja usunięta.");
        });

        Button cancelButton = new Button("Anuluj", e -> dialog.close());

        dialog.add(serviceTypeField, priceField, saveButton, deleteButton, cancelButton);
        dialog.open();
    }
}
