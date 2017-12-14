package artintech;

import artintech.beans.IComponentContainer;
import artintech.beans.ISlide;
import artintech.dao.Dao;
import artintech.dao.DaoDogovors;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by Анатолий on 03.11.2015.
 */
public class SlideLoad extends VerticalLayout implements IComponentContainer, ISlide{
    static final int SLIDELOAD_1C = 1;
    static final int SLIDELOAD_DOCS = 2;
    static final int SLIDELOAD_STRUCTURE = 3;
    static final int SLIDELOAD_SVED = 4;
    String text1 = null;
    Image image = null;
    FileDownloader fileDownloader = null;
    Integer idFileDownloader = null;
    Dao dao = null;
    BigDecimal idDogovor = null;
    NotifySlider notifySlider = null;

    @Override
    public void init() {
        if (dao == null){
            dao = new Dao();
        }
        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");

        if (text1 == null){
           text1 = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">НЕ ЗАПОЛНЕНО</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> " +
                   "</font></p>";
        }
        GridLayout gridlayout = new GridLayout(1,2);
        gridlayout.setWidth("100%");
        gridlayout.setHeightUndefined();
        gridlayout.setSpacing(true);
        gridlayout.setMargin(false);
        Label label = new Label(text1, ContentMode.HTML);


        PanelSout panel1 = new PanelSout();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.addComponent(label);
        if (image != null){
            image.setWidth("90px");  image.setHeight("90px");
            HorizontalLayout hl = new HorizontalLayout(); hl.setSizeFull(); hl.setMargin(true);
            hl.addComponent(image);
            hl.setComponentAlignment(image, Alignment.BOTTOM_CENTER);
            vl.addComponent(hl);
        }

//        panel1.setContent(label);
        panel1.setContent(vl);
        panel1.setWidth("80%");
        panel1.setHeight("450px");
        gridlayout.addComponent(panel1);
        gridlayout.setComponentAlignment(panel1, Alignment.TOP_CENTER);
        Button.ClickListener clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
//                DaoDogovors.StateTrack stateTrack = (DaoDogovors.StateTrack) VaadinSession.getCurrent().getAttribute("stateTrack");
//                Integer currState = stateTrack.getCurrentstate();
//                Integer currLen = stateTrack.getActiveLength();
                //getUI().getNavigator().navigateTo("track");
                if (notifySlider !=null) {
                    notifySlider.doNotify(null);
                }
            }
        };
        Button button1 = new Button("Скачать файл");
        button1.setWidth("150px");
        button1.setDescription("Скачать файл с cервера на Ваш компьютер.");
        button1.setHeightUndefined();
        button1.addStyleName("shadow");
        button1.addClickListener(clickListener);
        if (fileDownloader == null){
            try {
                fileDownloader = dao.createFileDownloader(idDogovor,idFileDownloader);
                fileDownloader.extend(button1);
            } catch (final SQLException e) {
                e.printStackTrace();
                button1.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        Notification.show("Ошибка формирования файла " + e.getMessage().toString(), Notification.Type.WARNING_MESSAGE);
                    }
                });
            } catch (final Exception e) {
                e.printStackTrace();
                button1.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        Notification.show("Ошибка формирования файла " + e.getMessage().toString(), Notification.Type.WARNING_MESSAGE);
                    }
                });
            }
        } else{
            fileDownloader.extend(button1);
        }
        gridlayout.addComponent(button1);
        gridlayout.setComponentAlignment(button1, Alignment.BOTTOM_CENTER);
        addComponent(gridlayout);
    }

    public void setText(String text){
        this.text1 = text;
    }
    public void setImage(Image image){ this.image = image;}
    public void setIdFileDownloader(int idFileDownloader){
        this.idFileDownloader = idFileDownloader;
    }
    public void refresh(){
        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
    }

    public static SlideLoad createSlide(Integer idSlide){
        switch (idSlide){
            case SLIDELOAD_1C:{
                String text = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">СКАЧИВАНИЕ ПРОГРАММЫ ИМПОРТА ДАННЫХ ИЗ ПРОГРАММЫ 1С</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> Если в вашей компании установлены 1С:Предприятие или 1С:Бухгалтерия, Вы можете использовать" +
                        " программу, которая автоматически соберет необходимые данные, например, штатное расписание." +
                        " Особо обращаем Ваше внимание на то, что программа собирает только те данные, которые необходимы для " +
                        "СОУТ. Файл с собранными данными имеет формат xml и в целях " +
                        "безопасности, его содержимое до отправки может быть с легкостью проверено Вашим системным администратором," +
                        " или другим уполномоченным лицом. Для отправки полученного файла в экспертную организацию следует перйти на следующий шаг подготовки исходных данных.</font></p>";
                SlideLoad slideLoad = new SlideLoad();
                slideLoad.setText(text);
                slideLoad.setImage(new Image(null, new ThemeResource("img/90/download.png")));
                slideLoad.setIdFileDownloader(Dao.PLUGIN1C);
                slideLoad.setWidth("440px");
                return slideLoad;
            }
            case SLIDELOAD_DOCS:{
                String text ="<style type=\"text/css\">p {margin: 0.2em;}</style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">СКАЧИВАНИЕ СПИСКА ТРЕБУЕМЫХ ДОКУМЕНТОВ</font></p><p align=\"justify\">Для проведения СОУТ, вам необходимо предоставить в распоряжение экспертной организации ряд документов. Документы  предоставляются в электронном виде в произвольном формате. Список требуемых документов Вы можете получить, нажав кнопку \"Скачать файл\". Отправить собранные электронные копии документов в экспертную организацию вы можете здесь же. Для этого надо перейти на следующий шаг.</p>";
                SlideLoad slideLoad = new SlideLoad();
                slideLoad.setText(text);
                slideLoad.setImage(new Image(null, new ThemeResource("img/90/download.png")));
                slideLoad.setIdFileDownloader(Dao.PLUGIN1C);
                slideLoad.setWidth("440px");
                return slideLoad;
            }
            case SLIDELOAD_STRUCTURE:{
                String text = "<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">СКАЧИВАНИЕ БЛАНКА ФОРМЫ СТРУКТУРЫ ПРЕДПРИЯТИЯ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете скачать форму для ввода структуры предприятия в виде EXCEL файла. Для этого нажмите кнопку \"Скачать файл\".  После получения файла формы заполните соответствующие колонки файла и выгрузите результат в экспертную организацию. Выгрузку расширенной формы вы можете сдесь же. Для этого надо перейти на следующий шаг подготовки исходных данных." +
                        "</font></p>";
                SlideLoad slideLoad = new SlideLoad();
                slideLoad.setText(text);
                slideLoad.setImage(new Image(null, new ThemeResource("img/90/download.png")));
                slideLoad.setIdFileDownloader(Dao.STRUCTURE);
                slideLoad.setWidth("440px");
                return slideLoad;
            }
            case SLIDELOAD_SVED:{
                String text = "<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">СКАЧИВАНИЕ РАСШИРЕННОЙ ФОРМЫ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете скачать расширенную форму в EXCEL формате. Форма предназначена для ввода дополнительных данных по рабочим местам. Заполнение этой формы значительно уменьшает время проведения СОУТ и уменьшает количество ошибок на начальном этапе. Для скачивания расширенной формы нажмите кнопку \"Скачать файл\". После получения файла формы заполните соответствующие колонки файла и выгрузите результат в экспертную организацию. Выгрузку расширенной формы вы можете сдесь же. Для этого надо перейти на следующий шаг подготовки исходных данных." +
                        "</font></p>";
                SlideLoad slideLoad = new SlideLoad();
                slideLoad.setText(text);
                slideLoad.setImage(new Image(null, new ThemeResource("img/90/download.png")));
                slideLoad.setIdFileDownloader(Dao.SVED);
                slideLoad.setWidth("440px");
                return slideLoad;
            }
            default: return null;
        }
    }

    @Override
    public void setNotify(NotifySlider notify) {
        notifySlider = notify;
    }
}
