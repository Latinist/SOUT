package artintech;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.awt.*;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Created by Анатолий on 20.01.2015.
 */
public class MenuBarArea extends MenuBar {
    private static final ThemeResource ICON = new ThemeResource("img/refresh3.png");
    private static final ThemeResource ICONPREF = new ThemeResource("img/preferences.png");
    private static final ThemeResource ICONDOCADD = new ThemeResource("img/document_add.png");
    private static final ThemeResource ICONKGET = new ThemeResource("img/kget_list.png");
    private Integer idGlobal = null;

    public MenuBarArea(){
        setStyleName("menubararea");
        final com.vaadin.ui.MenuBar.MenuItem menuReboot = addItem("", ICON ,rebootCommand);
        menuReboot.setDescription("Перегрузить программу в исходное состояние");
//        menuReboot.setIcon(ICON);
        final com.vaadin.ui.MenuBar.MenuItem menu1 = addItem("Заявки",ICONDOCADD, requestCommand);
        menu1.setDescription("Просмотр заявок на проведение работ");
        final com.vaadin.ui.MenuBar.MenuItem menu2 = addItem("Подготовка договора",ICONKGET, exportCommand);
        menu2.setDescription("Экспорт документов на обработку");
        final com.vaadin.ui.MenuBar.MenuItem menu3 = addItem("Настройки",ICONPREF, tuning);
        menu3.setDescription("Просмотр и корректировка настроек пользователя и пароля");
    }

    private Command rebootCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            //  перенаправим на другую страницу. После закрытия сессии ничего уже не сделать
            idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
            String ss3  = getUI().getPage().getLocation().getPath()+"?from="+idGlobal.toString();
            getUI().getPage().setLocation(ss3);
            // закрываем сессию
            getSession().close();

        }
    };

    private Command requestCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            if (getUI().getNavigator().getState().equals("request"))
                return;
            getUI().getNavigator().navigateTo("request");

        }
    };

    private Command exportCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            if (getUI().getNavigator().getState().equals("export"))
                return;
            getUI().getNavigator().navigateTo("export");
        }
    };

    private Command tuning = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {

            if (getUI().getNavigator().getState().equals("kabinet"))
                return;
            getUI().getNavigator().navigateTo("kabinet");

        }
    };

}

/*
public class MenuBarArea extends HorizontalLayout {
    private MenuBar menuBar;
    public MenuBarArea(){
        menuBar  = new MenuBar();
        menuBar.setStyleName("menubararea");
        final com.vaadin.ui.MenuBar.MenuItem menu1 = menuBar.addItem("Заявки", null);
        final com.vaadin.ui.MenuBar.MenuItem menu2 = menuBar.addItem("Заявки", null);
        final com.vaadin.ui.MenuBar.MenuItem menu3 = menuBar.addItem("Заявки", null);
    }

}
*/