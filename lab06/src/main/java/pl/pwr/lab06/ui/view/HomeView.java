package pl.pwr.lab06.ui.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.pwr.lab06.ui.layout.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Witaj | Zapnet Internet")
public class HomeView extends VerticalLayout {

    public HomeView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 welcomeText = new H1("System zarządzanie dla dostawcy internetu");
        add(welcomeText);
    }
}
