package artintech.spam;

import artintech.dao.DaoDocs;
import artintech.domain.ReeDocs;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.wcs.wcslib.vaadin.widget.multifileupload.component.MultiUploadHandler;
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
 * Created by Анатолий on 03.02.2015.
 * Дизайн 1
 * ---------Выгрузка документов устарела-------------
 */
public class UploadDocsBref  extends VerticalLayout {
    private Integer idGlobal = null;
    private String linkDB;
    private Integer idUser;
    private BigDecimal currentIdRequest = null;
    private Table table;
    private Object myItemId;

    public UploadDocsBref(){}
    public void init(BigDecimal currentIdRequest){
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        this.currentIdRequest = currentIdRequest;

        setMargin(true);
        setHeight("100%");
        setSizeFull();
        setStyleName("LispRaWindow");

        BuildMain();
        setImmediate(true);

    }

    private void BuildMain() {
        String ss = "<h1 align=\"center\">Отправка документов в экспертную организацию </h1> <font size=\"4\" FACE=\"Times New Roman\">"
//                +" <p>На этой странице вы можете выгрузить электронные или сканированные документы (из списка предоставляемых документов)  на ресурс экспертной организации"
//                +"</p>"
                +"</font>";

        Label headComment = new Label(ss, ContentMode.HTML);
//        Ruler ruler1 = new Ruler();
//        Ruler ruler2 = new Ruler();

        addComponent(headComment);
//        addComponent(ruler2);
//        addComponent(new SeparatorL("Список предоставляемых файлов"));
        HorizontalLayout toolBar = new HorizontalLayout();
        FormLayout toolBar1 = new FormLayout();

        UploadStateWindow window = new UploadStateWindow();
//        MultiUploadHandler handler = new MultiUploadHandler()

        UploadFinishedHandler handler = new UploadFinishedHandler() {
            @Override
            public void handleFile(InputStream inputStream, String fileName, String mimType, long length) {
                String mess = "Файлы: ";
                try {
                    DaoDocs dao = new DaoDocs(linkDB);
                    dao.uploadDocToDb(inputStream,fileName,idUser, currentIdRequest,null, null , null );
                    mess += " "+fileName;
                    //---- после тестирования----
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, mess+" успешно загружены");
                    Notification.show(mess + " успешно загружены");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при загрузке файла на сервер");
                    Notification.show("Ошибка при загрузке файлов на сервер ("+ex.getMessage()+")",Notification.Type.ERROR_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Notification.show("Ошибка при загрузке файлов на сервер.("+e.getMessage()+")", Notification.Type.ERROR_MESSAGE);
                }
                JPAContainer<ReeDocs> ct  = (JPAContainer<ReeDocs>) table.getContainerDataSource();
                ct.refresh();
                table.refreshRowCache();
            }

        };
        MultiFileUpload multiUpload = new MultiFileUpload(handler, window);
        multiUpload.setCaption("Выгрузка группы документов");
        multiUpload.setPanelCaption("Выгрузка группы файлов");
        multiUpload.getSmartUpload().setUploadButtonCaptions("Выгрузить файлы", "Выгрузить файлы");
        toolBar1.addComponent(multiUpload);
        toolBar.addComponent(toolBar1);
//        Button del = new Button("Удалить документ");
//        FormLayout fl = new FormLayout();
//        fl.addComponent(del);
//        toolBar.addComponent(fl);
        //toolBar.setWidth("100%");

        addComponent(toolBar);

//        DaoDocs dao = new DaoDocs(linkDB);
//        JPAContainer<ReeDocs> ree = dao.getReeDocs( currentIdRequest);

        JPAContainer<ReeDocs> docs = JPAContainerFactory.make(ReeDocs.class, linkDB);
        docs.addContainerFilter(new Compare.Equal("idRequests", currentIdRequest.toBigInteger()));
        docs.applyFilters();

        table = new Table("Список выгруженных документов для заключения договора с экспертной организацией",docs);
        table.setWidth("100%");
        table.setHeight("500px");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setVisibleColumns(new Object[]{"id", "filename", "dttm"});//  });
        table.setColumnHeaders(new String[]{"№", "Название файла", "Дата выгрузки"});
        table.setColumnWidth("id",50);
        table.setColumnWidth("dttm",120);

        final Button.ClickListener btnListener = new ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Object item = clickEvent.getButton().getData();
                table.removeItem(item);
                JPAContainer<ReeDocs> ct  = (JPAContainer<ReeDocs>) table.getContainerDataSource();
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
        table.setColumnWidth("Операция", 75);


        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                myItemId = itemClickEvent.getItemId();
            }
        });

//        table.addContainerProperty("Операция", Button.class, null);

        addComponent(table);
//        addComponent(new SeparatorL("...."));
    }

}
