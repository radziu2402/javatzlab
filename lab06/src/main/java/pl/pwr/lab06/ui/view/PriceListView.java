package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.pwr.lab06.ui.layout.MainLayout;

@Route(value = "cennik", layout = MainLayout.class)
@PageTitle("Cennik | Zapnet Internet")
public class PriceListView extends VerticalLayout {
    public PriceListView() {
        // Tutaj logika widoku cennika
    }
}
