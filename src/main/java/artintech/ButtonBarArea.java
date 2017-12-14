package artintech;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;

import java.util.ArrayList;

/**
 * Created by Анатолий on 09.07.2015.
 */
public class ButtonBarArea extends HorizontalLayout{
    private Integer idGlobal = null;

    public ButtonBarArea() {

        this.setHeightUndefined();
        setWidth("100%");
        setStyleName("menubararea");
        setMargin(false);

        String hMenu = "40px";

        Button bSercons = new Button();
        final ThemeResource sercons = new ThemeResource("img/logo_sercons.png");
        bSercons.setIcon(sercons);
        bSercons.addStyleName("transparentColor");
        bSercons.setHeight(hMenu);
        addComponent(bSercons);

        Button bRefresh = new Button("Выход");
        final ThemeResource refresh = new ThemeResource("img/refreshblue.png");
        bRefresh.setIcon(refresh);
        bRefresh.addStyleName("transparentColor");
        bRefresh.setHeight(hMenu);
        bRefresh.setDescription("Перегрузить");
        bRefresh.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {rebootCommand();}
        });

        ArrayList<Button> buttons = new ArrayList<Button>();

        Button buttonChangeTrack = new Button("Сменить вариант");
        buttonChangeTrack.setDescription("Переопределить вариант подготовки исходных данных.");
        buttonChangeTrack.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("resource"); }
        });

        Button buttonToTrack = new Button("Подготовка");
        buttonToTrack.setDescription("Подготовке данных в соответствие с выбранным вариантом работы.");
        buttonToTrack.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("track"); }
        });


        Button buttonDogovor = new Button("Договор");
        buttonDogovor.setDescription("Просмотр доступных договоров и приложений.");
        buttonDogovor.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("request"); }
        });
//        Button buttonRequiredDoc = new Button("Документы");
//        buttonRequiredDoc.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("loadTools");}
//        });
//        buttonRequiredDoc.setDescription("Скачать список документов необходимых для проведения СОУТ.");
//        Button buttonServis = new Button("Сервис");
//        buttonServis.setDescription("Как ускорить выполнение работ по СОУТ.");
//        buttonServis.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("loadProgram");  }
//        });
//        Button buttonLoad = new Button("Загрузка");
//        buttonLoad.setDescription("Отправить документы в экспертную организацию.");
//        buttonLoad.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("uploadFiles");   }
//        });
        Button buttonKabinet = new Button("Кабинет");
        buttonKabinet.setDescription("Просмотр личных данных и смена пароля.");
        buttonKabinet.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("kabinet");   }
        });
        Button buttonHelp = new Button("Описание");
        buttonHelp.setDescription("Описание функций интернет ресурса.");
        buttonHelp.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) { goToPage("help");   }
        });



//        Button buttonRecover = new Button("Перезагрузить");
        buttons.add(buttonChangeTrack);
        buttons.add(buttonToTrack);
        buttons.add(buttonDogovor);
//        buttons.add(buttonRequiredDoc);
//        buttons.add(buttonServis);
//        buttons.add(buttonLoad);
        buttons.add(buttonKabinet);
        buttons.add(buttonHelp);
//        buttons.add(buttonRecover);

        for (Button value : buttons) {
            value.setWidth("100%");
            value.setHeight(hMenu);
            value.addStyleName("transparentColor");
            addComponent(value);
            setExpandRatio(value, 1.0f);
        };
        addComponent(bRefresh);
    }

    private void rebootCommand() {
            //  перенаправим на другую страницу. После закрытия сессии ничего уже не сделать
            idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
            String ss3  = getUI().getPage().getLocation().getPath()+"?from="+idGlobal.toString();
            getUI().getPage().setLocation(ss3);
            // закрываем сессию
            getSession().close();

        }
    private void goToPage(String namePage){
            if (getUI().getNavigator().getState().equals(namePage))
                return;
            getUI().getNavigator().navigateTo(namePage);
        }

}
