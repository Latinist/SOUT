package artintech;

import artintech.beans.IComponentContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

/**
 * Created by Анатолий on 13.07.2015.
 */
public class Helper extends VerticalLayout implements IComponentContainer {
    String PuthToHelp;

    public Helper(){};

    @Override
    public void init() {
        //---------------------------------------------------------------------------------------
        PuthToHelp = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..";
        //---------------------------------------------------------------------------------------
        setMargin(false);
        addStyleName("Light");

        BuildMain();
        setImmediate(true);

    }

    private void BuildMain() {
        VerticalLayout outPanel = new VerticalLayout();
        outPanel.setWidth("95%");
        outPanel.setHeight("600px");
        outPanel.addStyleName("semitransparentlayout");
        outPanel.setMargin(true);
        VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("100%");
        contentOutPanel.setHeightUndefined();
        //-----------------------------------------------------
//        Embedded e = new Embedded("", new ThemeResource("help1.html"));
//        //e.setAlternateText("Vaadin web site");
//        e.setType(Embedded.TYPE_BROWSER);

        BrowserFrame e  =  new BrowserFrame("", new ThemeResource("help1.html"));
        // e.setType(Browser ("iframe") );
        e.setWidth("100%");
        e.setHeight("560px");
        contentOutPanel.addComponent(e);



//        contentOutPanel.addComponent(lab1); contentOutPanel.setComponentAlignment(lab1, Alignment.TOP_CENTER);
//        contentOutPanel.addComponent(request); contentOutPanel.setComponentAlignment(request,Alignment.TOP_CENTER);
        //-----------------------------------------------------
        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);
        //-----------------------------------------
//        FactoryTrains factoryTrains =  new FactoryTrains();
//        HorizontalLayout podballogo = (HorizontalLayout) factoryTrains.makeTrain(new String[]{"Договоры", "Список документов","Сервис","Загрузка"},4);
//        addComponent(podballogo);
//        setComponentAlignment(podballogo,Alignment.TOP_CENTER);
        //-----------------------------------------

    }
}
