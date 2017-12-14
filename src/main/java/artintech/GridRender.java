package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoDogovors;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by Анатолий on 30.10.2015.
 */
public class GridRender extends VerticalLayout implements IComponentContainer {
    String linkDB;
    private BigDecimal idDogovor = null;
    Label lvelkome = new Label("<p align=\"center\">Для ускорения работ по СОУТ мы рекомендуем воспользоваться одним из предложенных вариантов предоставления информации</p>", ContentMode.HTML);
    String text1 = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">АВТОМАТИЧЕСКИЙ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> Если в вашей компании установлены 1С:Предприятие или 1С:Бухгалтерия, Вы можете использовать" +
            " программу, которая автоматически соберет необходимые данные, например, штатное расписание." +
            " Особо обращаем Ваше внимание на то, что программа собирает только те данные, которые необходимы для " +
            "СОУТ. Файл с собранными данными имеет формат xml и в целях " +
            "безопасности, его содержимое до отправки может быть с легкостью проверено Вашим системным администратором," +
            " или другим уполномоченным лицом.</font></p>";
    String text2 ="<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ПОЛУ-АВТОМАТИЧЕСКИЙ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> Для ускорения работ со СОУТ и снижения стоимости, Вы можете заполнить одну или две EXCEL форм. " +
            "Их заполнение гарантирует, что мы получим комплект начальных данные для проведения СОУТ и оценки стоимости работ, а Вы получите " +
            "наше предложение и последующие результаты работы как можно скорее.</font></p>";
    String text3 ="<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">РУЧНОЙ ВАРИАНТ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> В этом случае Вы предоставляете только необходимые первичые документы. Вся заботу по подготовке исходных данных и выполнит наш эксперт. Этот вариант дороже чем два предыдущих варианта, но зато не требует привлечения Ваших сотрудников для предварительной подготовки данных." +
            "</font></p>";


    GridLayout gridlayout = new GridLayout(3,2);

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
//        if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
//            idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
//        }

        lvelkome.setWidth("40%");

        gridlayout.setWidth("100%");
        gridlayout.setHeightUndefined();
        gridlayout.setSpacing(true);
        gridlayout.setMargin(false);

        PanelSout panel1 = new PanelSout();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.addComponent(new Label(text1,ContentMode.HTML));
        //Image image1 =  new Image(null, new ThemeResource("img/excel.png"));
        Image image1 =  new Image(null, new ThemeResource("img/128/excel.png"));
        image1.setWidth("90px"); image1.setHeight("90px");
        HorizontalLayout hl = new HorizontalLayout(); hl.setSizeFull(); hl.setMargin(true);
        hl.addComponent(image1);
        hl.setComponentAlignment(image1, Alignment.BOTTOM_CENTER);
        vl.addComponent(hl);
        panel1.setContent(vl);
//        panel1.setContent(new Label(text1,ContentMode.HTML));
        panel1.setWidth("80%");
        panel1.setHeight("400px");

        PanelSout panel2 = new PanelSout();
        VerticalLayout vl2= new VerticalLayout();
        vl2.setSizeFull();
        vl2.addComponent(new Label(text2,ContentMode.HTML));
//        Image image2 =  new Image(null, new ThemeResource("img/avtomaticheski.png"));
        Image image2 =  new Image(null, new ThemeResource("img/128/computer.png"));
        image2.setWidth("90px"); image1.setHeight("90px");
        HorizontalLayout hl2 = new HorizontalLayout(); hl2.setSizeFull(); hl2.setMargin(true);
        hl2.addComponent(image2);
        hl2.setComponentAlignment(image2, Alignment.BOTTOM_CENTER);
        vl2.addComponent(hl2);
        panel2.setContent(vl2);
//        panel2.setContent(new Label(text2,ContentMode.HTML));
        panel2.setWidth("80%");
        panel2.setHeight("400px");

        PanelSout panel3 = new PanelSout();
        VerticalLayout vl3 = new VerticalLayout();
        vl3.setSizeFull();
        vl3.addComponent(new Label(text3,ContentMode.HTML));
//        Image image3 =  new Image(null, new ThemeResource("img/avtomaticheski.png"));
        Image image3 =  new Image(null, new ThemeResource("img/128/text.png"));
        image3.setWidth("90px"); image1.setHeight("90px");
        HorizontalLayout hl3 = new HorizontalLayout(); hl3.setSizeFull(); hl3.setMargin(true);
        hl3.addComponent(image3);
        hl3.setComponentAlignment(image3, Alignment.BOTTOM_CENTER);
        vl3.addComponent(hl3);
        panel3.setContent(vl3);
        //panel3.setContent(new Label(text3, ContentMode.HTML));
        panel3.setWidth("80%");
        panel3.setHeight("400px");

        gridlayout.addComponent(panel1);
        gridlayout.setComponentAlignment(panel1, Alignment.TOP_CENTER);
        gridlayout.addComponent(panel2);
        gridlayout.setComponentAlignment(panel2, Alignment.TOP_CENTER);
        gridlayout.addComponent(panel3);
        gridlayout.setComponentAlignment(panel3, Alignment.TOP_CENTER);


        Button.ClickListener clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Integer idTrack = (Integer) event.getButton().getData();
                VaadinSession.getCurrent().setAttribute("idTrack", idTrack);
                DaoDogovors daoD = new DaoDogovors(linkDB);
                DaoDogovors.StateTrack stateTrack;
                try {
                    if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
                        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
                    }
                    stateTrack = daoD.getTrack(idDogovor);
                    stateTrack.setIdTrack(idTrack);
                    daoD.setStateTrack(idDogovor,stateTrack);
                    // выложим StateTrack  в доступ
                    VaadinSession.getCurrent().setAttribute("stateTrack", stateTrack);

                } catch (SQLException e) {
                    e.printStackTrace();
                    Notification.show("Ошибка: "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Notification.show("Ошибка: "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
                VaadinSession.getCurrent().setAttribute("regreshSlider", true);
                getUI().getNavigator().navigateTo("track");
            }
        };
        Button button1 = new Button("Согласен");
        button1.setWidth("150px");
        button1.setDescription("Выбрать этот вариант");
        button1.setHeightUndefined();
        button1.setData(1);
        button1.addClickListener(clickListener);

        Button button2 = new Button("Согласен");
        button2.setWidth("150px");
        button2.setDescription("Выбрать этот вариант");
        button2.setHeightUndefined();
        button2.setData(2);
        button2.addClickListener(clickListener);

        Button button3 = new Button("Согласен");
        button3.setWidth("150px");
        button3.setDescription("Выбрать этот вариант");
        button3.setHeightUndefined();
        button3.setData(3);
        button3.addClickListener(clickListener);

        gridlayout.addComponent(button1);
        gridlayout.setComponentAlignment(button1, Alignment.BOTTOM_CENTER);
        gridlayout.addComponent(button2);
        gridlayout.setComponentAlignment(button2, Alignment.BOTTOM_CENTER);
        gridlayout.addComponent(button3);
        gridlayout.setComponentAlignment(button3, Alignment.BOTTOM_CENTER);


        addComponent(lvelkome);
        setComponentAlignment(lvelkome, Alignment.TOP_CENTER);
        addComponent(gridlayout);
        setComponentAlignment(gridlayout, Alignment.TOP_CENTER);
    }

    public void notifyUpdateDogovor(){
        DaoDogovors.StateTrack stateTrack = (DaoDogovors.StateTrack) VaadinSession.getCurrent().getAttribute("stateTrack");
        if (stateTrack.getIdTrack() != null){
            VaadinSession.getCurrent().setAttribute("regreshSlider", true);
            getUI().getNavigator().navigateTo("track");
        }

    }

}
