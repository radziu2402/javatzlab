package pl.pwr.lab06.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import pl.pwr.lab06.ui.view.CustomerView;
import pl.pwr.lab06.ui.view.InstallationView;
import pl.pwr.lab06.ui.view.PaymentView;
import pl.pwr.lab06.ui.view.PriceListView;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Zapnet Internet");
        logo.addClassNames("text-l", "m-m");

        MenuBar menuBar = new MenuBar();
        RouterLink customersLink = new RouterLink("Klienci", CustomerView.class);
        RouterLink installationsLink = new RouterLink("Instalacje", InstallationView.class);
        RouterLink paymentsLink = new RouterLink("Płatności", PaymentView.class);
        RouterLink priceListLink = new RouterLink("Cennik", PriceListView.class);

        menuBar.addItem(customersLink);
        menuBar.addItem(installationsLink);
        menuBar.addItem(paymentsLink);
        menuBar.addItem(priceListLink);

        addToNavbar(logo, menuBar);
    }
}

