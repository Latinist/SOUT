package artintech.spam;

import artintech.Ruler;
import artintech.SeparatorL;
import artintech.dao.DaoDocs;
import artintech.dao.DaoSprDocs;
import artintech.domain.SprDocs;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 02.02.2015.
 * ----------ДИзайн 1 выгрузка сканированных документов -- устарела--------------
 */
public class UploadDocs extends VerticalLayout {
    private Integer idGlobal = null;
    private String linkDB;
    private Integer idUser;
    private BigDecimal currentIdRequest = null;
    
    public UploadDocs(){}
    
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
                +" <p>На этой странице вы можете выгрузить электронные или сканированные документы (из списка предоставляемых документов)  на ресурс экспертной организации"
                +"</p>"
                +"</font>";

        Label headComment = new Label(ss, ContentMode.HTML);
//        Ruler ruler1 = new Ruler();
        Ruler ruler2 = new Ruler();

        addComponent(headComment);
        addComponent(ruler2);
        addComponent(new SeparatorL("Список предоставляемых файлов"));

        //----------------- Шапка таблицы----------------
        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td>Название документа</td>");
        output.append(" <td>Операция</td>");
        output.append(" <td>Колическво файлов</td>");
        output.append("</tr>");

        //----------------тело ------------------------
        DaoSprDocs dao = new DaoSprDocs(linkDB);
        Collection<SprDocs> sprDocses = dao.getLispDoc();
        Iterator it =  sprDocses.iterator();
        SprDocs entyti = null;
        while (it.hasNext()){
            entyti = (SprDocs) it.next();
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\" >"+entyti.getNm()+"</td>");
            output.append(" <td align=\"left\" location=\"ID"+entyti.getId().toString()+"\" ></td>");
            output.append(" <td align=\"left\" >2</td>");
            output.append("</tr>");
        }
        //----------------------разместим кнопки------------------------

        output.append("</tbody></table>");
        CustomLayout custom = null;
        try {
            custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStateWindow window = new UploadStateWindow();

        Iterator it1 =  sprDocses.iterator();
        while (it1.hasNext()){
            final SprDocs entyti1 = (SprDocs) it1.next();
            //----------------------------------------------------------------
            UploadFinishedHandler handler = new UploadFinishedHandler() {
                @Override
                public void handleFile(InputStream inputStream, String fileName, String mimType, long length) {
                    String mess = "Файлы: ";
                    try {
                        DaoDocs dao = new DaoDocs(linkDB);
                        //--- Заглушки---
                        BigDecimal idContract = null;
                        BigDecimal fileSize = new BigDecimal(length);
                        //---------------
                        dao.uploadDocToDb(inputStream,fileName,idUser, currentIdRequest,new BigDecimal(entyti1.getId()), idContract , fileSize);

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
                }
            };
            MultiFileUpload multiUpload = new MultiFileUpload(handler,window);
            //multiUpload.setCaption("Выгрузка группы файлов");
            multiUpload.setPanelCaption("Выгрузка группы файлов");
            multiUpload.getSmartUpload().setUploadButtonCaptions("Выгрузить файлы", "Выгрузить файлы");
            custom.addComponent(multiUpload, "ID"+entyti1.getId());

        }

        addComponent(custom);
        addComponent(new SeparatorL("...."));



    }
}
