package artintech.spam;

import artintech.ToolBarImpl;
import artintech.beans.CommonView;
import artintech.spam.Welkome;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;

/**
 * Created by Анатолий on 09.01.2015.
 */
public class WelkomeView extends CommonView {
    public WelkomeView() {
        setToolBarContainer(new ToolBarImpl());
        setComponentContainer(new Welkome());
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        String preregmess  = (String) VaadinSession.getCurrent().getAttribute("preregmess");
        if (preregmess !=null)
            if (!preregmess.equals("") ) {
                Notification.show(preregmess);
            }

    }

}

