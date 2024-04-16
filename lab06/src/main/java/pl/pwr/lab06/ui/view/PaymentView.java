package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.pwr.lab06.ui.layout.MainLayout;

@Route(value = "platnosci", layout = MainLayout.class)
@PageTitle("Płatności | Zapnet Internet")
public class PaymentView extends VerticalLayout {
    public PaymentView() {
        // Tutaj logika widoku płatności
    }
}
