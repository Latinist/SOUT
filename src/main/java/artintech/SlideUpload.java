package artintech;

import artintech.beans.IComponentContainer;
import artintech.beans.ISlide;
import artintech.dao.Dao;
import artintech.dao.DaoDocs;
import artintech.domain.User;
import artintech.domain.VReeDocs;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 03.11.2015.
 */
public class SlideUpload  extends VerticalLayout implements IComponentContainer,ISlide {
    static final int UPLOAD_1C = 1;
    static final int UPLOAD_DOCS = 2;
    static final int UPLOAD_STRUCTURE = 3;
    static final int UPLOAD_SVED = 4;
    private Table table;
    //String text1 = null;
    Dao dao = null;
    BigDecimal idDogovor = null;
    private String linkDB;
    private Integer idGlobal = null;
    Integer idUser = null;
    private BigDecimal currentIdRequest = null;
    private String text = null;
    NotifySlider notifySlider = null;

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        if (VaadinSession.getCurrent().getAttribute("idRequest") != null) {
            currentIdRequest = (BigDecimal) VaadinSession.getCurrent().getAttribute("idRequest");
        }
        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");

//        if (dao == null){
//            dao = new Dao();
//        }
        if (text == null){
            text = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">Внимание</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете выгрузить файлы ранее подготовленных электронных документов в экспертную организацию. Для этого необходимо нажать кнопку \"Загрузить файлы\" и выбрать требуемые документы из каталога на Вашем компьютере. После загрузки файл появится в таблице выгруженных документов приведенной ниже. По окончании выгрузки всех файлов Вы можете перейти к следующему шагу подготовки исходных данных." +
                    "</font></p>";
        }

        GridLayout gridlayout = new GridLayout(1,3);
        gridlayout.setWidth("100%");
        gridlayout.setHeightUndefined();
        gridlayout.setSpacing(true);
        gridlayout.setMargin(false);

        JPAContainer<VReeDocs> docs = JPAContainerFactory.make(VReeDocs.class, linkDB);
        docs.setReadOnly(true);
        docs.addContainerFilter(new Compare.Equal("idContract", idDogovor.toBigInteger()));
        docs.applyFilters();

        Label label = new Label(text, ContentMode.HTML);
        PanelSout panel1 = new PanelSout();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.addComponent(label);
        panel1.setContent(vl);
        panel1.setWidth("850px");
        panel1.setHeight("140px");
        gridlayout.addComponent(panel1);
        gridlayout.setComponentAlignment(panel1, Alignment.TOP_CENTER);


        table = new Table();
        table.setContainerDataSource(docs);
        table.setWidth("850px");
        table.setHeight("300px");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setVisibleColumns(new Object[]{"dttm", "filename", "note" });//  });
        table.setColumnHeaders(new String[]{"Дата выгрузки", "Название файла","Комментарий"});
        //table.setColumnWidth("id",50);
        table.setColumnWidth("dttm",140);
        table.setColumnWidth("note",140);
        table.addStyleName("bigShadow");
        table.setCaption("Список документов уже выгруженных в экспертную организацию для проведения СОУТ");

        final Button.ClickListener btnListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Object item = clickEvent.getButton().getData();
                //table.removeItem(item);
                int iItem = ((BigDecimal) item).intValue();
                boolean isXml = (iItem%10 == 1);
                int fId = iItem/10;
                // 0 = REE_DOCS 1-REE_XML
                DaoDocs dao = new DaoDocs(linkDB);
                BigDecimal bId = BigDecimal.valueOf(iItem/10);
                dao.deleteDoc(bId,isXml);

                JPAContainer<VReeDocs> ct  = (JPAContainer<VReeDocs>) table.getContainerDataSource();
                ct.refresh();
                table.refreshRowCache();
                Notification.show("Запись удалена");
            }
        };

        table.addGeneratedColumn("Операция", new Table.ColumnGenerator() {
            public Object generateCell(Table source, Object itemId, Object columnId) {
                //myItemId = itemId;
                Button tf = new Button("Удалить");
                tf.setData(itemId);
                tf.addClickListener(btnListener);
                return tf;
            }
        });
        table.setColumnWidth("Операция", 90);



        gridlayout.addComponent(table);
        gridlayout.setComponentAlignment(table, Alignment.TOP_CENTER);


        //----------------------------------------------------------------------
        UploadFinishedHandler handler = new UploadFinishedHandler() {
            @Override
            public void handleFile(InputStream inputStream, String fileName, String mimType, long length) {
                String mess = "Файл: ";
                String note = null;
                try {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Начинается выгрузка файла "+fileName);
                    DaoDocs dao = new DaoDocs(linkDB);

                    String ext = fileName.substring(fileName.lastIndexOf('.')+1,fileName.length()).toUpperCase();
                    BigDecimal fileSize = new BigDecimal(length);
                    if (ext.equals("XML")){
                        dao.setDocToDb(inputStream, fileName, idUser, currentIdRequest, new BigDecimal(100), idDogovor, fileSize);
                    } else {
//                        dao.uploadDocToDb(inputStream,fileName,idUser, currentIdRequest,new BigDecimal(entyti1.getId()) );
                        note = dao.uploadDocToDb(inputStream,fileName,idUser, currentIdRequest,new BigDecimal(101), idDogovor ,fileSize);
                    }
                    mess += " "+fileName;
                    //---- после тестирования----
                    if (note != null){
                        note = " с собшением: "+note;
                    } else
                        note = "";
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, mess+" успешно загружен "+note);
                    Notification.show(mess+" успешно загружен "+ note,Notification.Type.WARNING_MESSAGE);
                    note = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при загрузке файла на сервер");
                    Notification.show("Ошибка при загрузке файла на сервер",Notification.Type.ERROR_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при записи файла в БД");
                    Notification.show("Ошибка при записи файла в БД ("+e.getMessage()+")", Notification.Type.ERROR_MESSAGE);
                }

                JPAContainer<VReeDocs> ct  = (JPAContainer<VReeDocs>) table.getContainerDataSource();
                ct.refresh();
                table.refreshRowCache();
                if (notifySlider !=null) {
                    notifySlider.doNotify(null);
                }

            }
        };
        //-------------------------------------------------------------------------
        UploadStateWindow window = new UploadStateWindow();
        //-------------------------------------------------------------------------
        MultiFileUpload multiUpload = new MultiFileUpload(handler,window);
        multiUpload.setPanelCaption("Загрузить файлы");
        multiUpload.getSmartUpload().setUploadButtonCaptions("Загрузить одиночный файл","Загрузить файлы");
        multiUpload.addStyleName("shadow");
        HorizontalLayout uploadLayout =  new HorizontalLayout();
        uploadLayout.setWidthUndefined();
        uploadLayout.setSpacing(true);
        uploadLayout.setMargin(false);
        uploadLayout.addComponent(multiUpload);

        gridlayout.addComponent(uploadLayout);
        gridlayout.setComponentAlignment(uploadLayout, Alignment.BOTTOM_CENTER);
        addComponent(gridlayout);

    }

    public void refresh(){
        idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
    }
    public void setText(String text){
        this.text = text;
    }

    public static SlideUpload createSlide(Integer idSlide){
        switch (idSlide){
            case UPLOAD_1C:{
                String text = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВЫГРУЗКА ДАННЫХ ИМПОРТИРОВАННЫХ ИЗ ПРОГРАММЫ 1С</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\"> На этой странице вы можете выгрузить XML файл предварительно сформированный приложением к программе 1С. Данное приложение Вам предлагалось скачать на предыдущеи шаге подготовки данных. Для выгрузки XML файла Вам необходимо нажать кнопку \"Загрузить файлы\" и выбрать требуемый файл из каталога на вашем компьютере. После загрузки файл появится в таблице выгруженных документов приведенной ниже. По окончании выгрузки, вы можете перейти к следующему шагу подготовки исходных данных." +
                        "</font></p>";
                SlideUpload slideUpload = new SlideUpload();
                slideUpload.setText(text);
//                slideUpload.setIdFileDownloader(Dao.PLUGIN1C);
                //1slideUpload.setWidth("400px");
                return slideUpload;
            }
            case UPLOAD_DOCS:{
                SlideUpload slideUpload = new SlideUpload();
                String txt = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВЫГРУЗКА ЭЛЕКТРОННЫХ КОПИЙ ПЕРВИЧНЫХ ДОКУМЕНТОВ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете выгрузить файлы ранее подготовленных электронных документов в экспертную организацию. Для этого необходимо нажать кнопку \"Загрузить файлы\" и выбрать требуемые документы из каталога на вашем компьютере. После загрузки файл появится в таблице выгруженных документов приведенной ниже. По окончании выгрузки всех файлов Вы можете перейти к следующему шагу подготовки исходных данных." +
                        "</font></p>";
                slideUpload.setText(txt);
                return slideUpload;
//                String text ="<style type=\"text/css\">p {margin: 0.2em;}</style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВНИМАНИЕ</font></p><p align=\"justify\">Здесь можете  скачать список документов, копии которых в электронном виде, необходимы для проведения работ по СОУТ.</p>";
//                SlideLoad slideLoad = new SlideLoad();
//                slideLoad.setText(text);
//                slideLoad.setIdFileDownloader(Dao.PLUGIN1C);
//                slideLoad.setWidth("400px");
//                return slideUpload;
            }
            case UPLOAD_STRUCTURE:{
                SlideUpload slideUpload = new SlideUpload();
                String txt = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВЫГРУЗКА ФОРМЫ СТРУКТУРЫ ПРЕДПРИЯТИЯ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете выгрузить в экспертную организацию EXCEL файл формы структуры предприятия, заполненной на предыдущем этапе. Для этого необходимо нажать кнопку \"Загрузить файлы\" и выбрать требуемй EXCEL файл из каталога на вашем компьютере. После выгрузки файл появится в таблице выгруженных документов приведенной ниже. По окончании операции Вы можете перейти к следующему шагу подготовки исходных данных." +
                        "</font></p>";
                slideUpload.setText(txt);
                return slideUpload;
            }
            case UPLOAD_SVED:{
                SlideUpload slideUpload = new SlideUpload();
                String txt = "<style type=\"text/css\">p {margin: 0.1em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВЫГРУЗКА РАСШИРЕННОЙ ФОРМЫ</font></p><p class=\"dline\" align=\"justify\"><font face=\"Uni Sans Regular\" size=\"3\">На этой странице вы можете выгрузить в экспертную организацию файл расширенной формы, заполненной на предыдущем этапе. Для этого необходимо нажать кнопку \"Загрузить файлы\" и выбрать требуемй EXCEL файл из каталога на Вашем компьютере. После загрузки файл появится в таблице выгруженных документов приведенной ниже. На этом сбор предварительной информации для проведения СОУТ завершен." +
                        "</font></p>";
                slideUpload.setText(txt);
                return slideUpload;
            }
            default: return null;
        }

    }

    @Override
    public void setNotify(NotifySlider notify) {
        notifySlider = notify;
    }
}
