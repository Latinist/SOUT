package artintech;

import artintech.beans.IComponentContainer;
import artintech.domain.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

/**
 * Created by Анатолий on 27.10.2015.
 */
public class FrameRender extends VerticalLayout implements IComponentContainer {
    private IComponentContainer header;
    private ArrayList<IComponentContainer> mainContainer = new ArrayList<IComponentContainer>();
//    String linkDB;
//    Integer idUser = null;
//    User user = null;

    @Override
    public void init() {
//        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
//        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
//        user   = (User) VaadinSession.getCurrent().getAttribute("user");
        setMargin(false);
        addStyleName("Light");
        BuildMain();
        setImmediate(true);
    }
    public void addComponentContainer(IComponentContainer container){
        this.mainContainer.add(container);
    }
    public void setHeaderContainer(IComponentContainer header){
        this.header = header;
    };

    private void BuildMain() {
        if (header != null){
            header.init();
            header.setWidth("100%");
            //header.setHeight("20px");
            addComponent(header);
            //setComponentAlignment(header,Alignment.TOP_CENTER);
        }
        VerticalLayout outPanel = new VerticalLayout();
        outPanel.setWidth("95%");
        outPanel.setHeight("600px");
        outPanel.addStyleName("semitransparentlayout");
        outPanel.setMargin(false);

        VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("95%");
        contentOutPanel.setHeightUndefined();
        for (IComponentContainer container: mainContainer){
            container.init();
            outPanel.addComponent(container);
            outPanel.setComponentAlignment(container, Alignment.TOP_CENTER);
        }
        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);

    }
}
