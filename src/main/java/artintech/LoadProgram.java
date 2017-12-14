package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoDocs;
import artintech.domain.User;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 30.06.2015.
 * Дизайн 2
 * ----------Скачивание 1с и EXCELL форм--------------
 */
public class LoadProgram extends VerticalLayout implements IComponentContainer{
    String linkDB;
    Integer idUser = null;
    User user = null;
    BigDecimal idDogovor;
//    private Integer idGlobal = null;
//    Button btnSave = new Button("Далее");

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        user   = (User) VaadinSession.getCurrent().getAttribute("user");
        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");

//        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
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
        final VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("100%");
        contentOutPanel.setHeightUndefined();

        Label lvelkome = new Label("<p align=\"center\">Для ускорения работ по СОУТ мы рекомендуем воспользоваться одним из предложенных вариантов предоставления информации</p>",ContentMode.HTML);
        lvelkome.setWidth("40%");
        //lvelkome.addStyleName("centerText");

        GridLayout gridlayout = new GridLayout(2,2);
        gridlayout.setWidth("100%");
        gridlayout.setHeightUndefined();
        gridlayout.setSpacing(true);
        gridlayout.setMargin(true);

//        HorizontalLayout dataPanel = new HorizontalLayout();
//        dataPanel.setWidth("100%");
//        dataPanel.setHeightUndefined();

        String lText = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">АВТОМАТИЧЕСКИЙ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> Если в вашей компании установлены 1С:Предприятие или 1С:Бухгалтерия, Вы можете использовать" +
                " программу, которая автоматически соберет необходимые данные, например, штатное расписание." +
                " Особо обращаем Ваше внимание на то, что программа собирает только те данные, которые необходимы для " +
                "СОУТ. Сбор происходит, практически мгновенно. Файл с собранными данными имеет формат xml и в целях " +
                "безопасности, его содержимое до отправки может быть с легкостью проверено Вашим системным администратором," +
                " или другим уполномоченным лицом.</font></p>";
        String rText ="<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ЗАПОЛНЕНИЕ ФОРМЫ В EXCEL</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> Если Вы не используете 1С:Предприятие или 1С:Бухгалтерия, Вы можете заполнить прикрепленную EXCEL форму. " +
                "Ее заполнение гарантирует, что мы получим все необходимые данные для оценки стоимости, а Вы получите " +
                "наше предложение и последующие результаты работы как можно скорее.</font></p>";

        HorizontalLayout layoutImage1 = new HorizontalLayout();
        layoutImage1.setSizeFull();
        final ThemeResource automatic = new ThemeResource("img/avtomaticheski.png");
        Image imageHand = new Image(null,automatic);
        //imageHand.setWidth("60%");
        layoutImage1.addComponent(imageHand);
        layoutImage1.setComponentAlignment(imageHand, Alignment.MIDDLE_CENTER);
        layoutImage1.setMargin(true);

        HorizontalLayout layoutImage2 = new HorizontalLayout();
        layoutImage2.setSizeFull();
        final ThemeResource excel = new ThemeResource("img/excel.png");
        Image imageExcel = new Image(null,excel);
        layoutImage2.addComponent(imageExcel);
        layoutImage2.setComponentAlignment(imageExcel, Alignment.MIDDLE_CENTER);
        layoutImage2.setMargin(true);

        VerticalLayout lPanelContent =  new VerticalLayout();
        lPanelContent.setSizeFull();
        lPanelContent.setSpacing(true);
        lPanelContent.addComponent(new Label(lText, ContentMode.HTML));
        lPanelContent.addComponent(layoutImage1);
        lPanelContent.setComponentAlignment(layoutImage1,Alignment.BOTTOM_CENTER);

        VerticalLayout rPanelContent =  new VerticalLayout();
        rPanelContent.setSpacing(true);
        rPanelContent.setSizeFull();
        rPanelContent.addComponent(new Label(rText,ContentMode.HTML));
        rPanelContent.addComponent(layoutImage2);
        rPanelContent.setComponentAlignment(layoutImage2, Alignment.BOTTOM_CENTER);

        Panel lPanel = new Panel();
        lPanel.setWidth("400px");
        lPanel.setHeight("400px");
        Panel rPanel = new Panel();
        rPanel.setWidth("400px");
        rPanel.setHeight("400px");

        lPanel.addStyleName(Reindeer.PANEL_LIGHT);
        lPanel.addStyleName("greylayout");
        lPanel.addStyleName("shadow");
        lPanel.addStyleName("paddingFull");
        lPanel.setContent(lPanelContent);
        rPanel.addStyleName(Reindeer.PANEL_LIGHT);
        rPanel.addStyleName("greylayout");
        rPanel.addStyleName("shadow");
        rPanel.addStyleName("paddingFull");
        rPanel.setContent(rPanelContent);
        Button loadButton = new Button("Скачать");
        loadButton.setWidth("150px");
        loadButton.setDescription("Скачать программу");
        loadButton.setHeightUndefined();
        //loadButton.setStyleName("multiline");
        Button loadExcelButton = new Button("Скачать шаблон");
        loadExcelButton.setWidth("150px");
        loadExcelButton.setDescription("Скачать EXCEL шаблон");
        loadExcelButton.setHeightUndefined();
//        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..\\SETUP\\documents\\program";
        //------------------ скачать 1C файл ---------------------------------------------------------------------------
        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/documents/program";
        File myFolder = new File(holderNeedDocs);
        File[] files = myFolder.listFiles();
        if (files == null) {
            Notification.show("Нет опубликованого документа");
            return;
        }
        File file = files[0];

        String filename = file.getName();
        String fullFileName = holderNeedDocs+"/"+filename;
//        FileDownloadResource resource = new FileDownloadResource(new File(fullFileName));
//        FileDownloader fd = new FileDownloader(resource);
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Скачивается файл : "+fullFileName);
        Resource res = new FileResource(new File(fullFileName));
        FileDownloader fd = new FileDownloader(res);

        fd.extend(loadButton);
        //------------------ скачать EXEL файл ---------------------------------------------------------------------------
//        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/documents/program";
//        File myFolder = new File(holderNeedDocs);
//        File[] files = myFolder.listFiles();
//        if (files == null) {
//            Notification.show("Нет опубликованого документа");
//            return;
//        }
//        File file = files[0];
//
//        String filename = file.getName();
//        String fullFileName = holderNeedDocs+"/"+filename;

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Скачивается файл : "+fullFileName);

//        Resource resEXEL = new FileResource(new File(fullFileName));
//        String fileName = "werwe";
//        StreamResource.StreamSource  streamSource =  new StreamResource.StreamSource() {
//            @Override
//            public InputStream getStream() {
//                return null;
//            }
//        }
//        Resource ff = new StreamResource(StreamResource.StreamSource streamSource, fileName);

        DaoDocs dao = new DaoDocs(linkDB);
        Resource resEXEL = null;
        try {
            resEXEL = dao.getExelFile(idDogovor);
            FileDownloader fdEXEL = new FileDownloader(resEXEL);
            fdEXEL.extend(loadExcelButton);
        } catch (final SQLException e) {
            e.printStackTrace();
            loadExcelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    Notification.show("Ошибка формирования файла " + e.getMessage().toString(), Notification.Type.WARNING_MESSAGE);
                }
            });

        }

        //--------------------------------------------------------------------------------------------------------



        Label podval = new Label("<p align=\"center\">Eсли Вы собрали данные - нажмите кнопку далее для перехода к выгрузке собранных данных на портал.</p>",ContentMode.HTML);
        podval.setWidth("80%");
        //podval.setWidthUndefined();
        Button nextButton = new Button("Далее");
        nextButton.setWidth("150px");
        nextButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                Animator.animate(podval, new Css().opacity(0.5));
//                Animator.animate(podval, new Css().translateX("1000px")).delay(1000).duration(2000);
                getUI().getNavigator().navigateTo("uploadFiles");
            }
        });

        gridlayout.addComponent(lPanel);
        gridlayout.setComponentAlignment(lPanel, Alignment.TOP_CENTER);
        gridlayout.addComponent(rPanel);
        gridlayout.setComponentAlignment(rPanel, Alignment.TOP_CENTER);
        gridlayout.addComponent(loadButton);
        gridlayout.setComponentAlignment(loadButton, Alignment.BOTTOM_CENTER);
        gridlayout.addComponent(loadExcelButton);
        gridlayout.setComponentAlignment(loadExcelButton, Alignment.BOTTOM_CENTER);

        //addComponent(nextButton);
        //setComponentAlignment(nextButton, Alignment.TOP_RIGHT);
        contentOutPanel.addComponent(lvelkome);
        contentOutPanel.setComponentAlignment(lvelkome, Alignment.TOP_CENTER);
        contentOutPanel.addComponent(gridlayout);
//        Ruler ruler = new Ruler();
//        contentOutPanel.addComponent(ruler);


        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);
        addComponent(podval);
        setComponentAlignment(podval, Alignment.BOTTOM_CENTER);
        addComponent(nextButton);
        setComponentAlignment(nextButton, Alignment.TOP_CENTER);


        //-----------------------------------------
        FactoryTrains factoryTrains =  new FactoryTrains();
        HorizontalLayout podballogo = (HorizontalLayout) factoryTrains.makeTrain(new String[]{"Договоры", "Список документов","Сервис","Загрузка"},3);
        addComponent(podballogo);
        setComponentAlignment(podballogo,Alignment.TOP_CENTER);
        //-----------------------------------------


        //outPanel.setComponentAlignment(dataPanel,Alignment.TOP_CENTER);

//        outPanel.addComponent(panel);
//        outPanel.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
//        buttonLayout.addComponent(loadButton);
//        buttonLayout.addComponent(nextButton);
//        outPanel.addComponent(buttonLayout);
//        outPanel.setComponentAlignment(buttonLayout,Alignment.TOP_CENTER);

    }
}
