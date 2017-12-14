package artintech.spam;

import artintech.Ruler;
import artintech.SeparatorL;
import artintech.beans.IComponentContainer;
import artintech.dao.DaoDocs;
import com.google.common.io.Files;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 20.01.2015.
 * Дизайн 1
 * ---------Выгрузка файлов на сервер - устарела--------
 *
 */

public class ExportDoc extends VerticalLayout{ // implements IComponentContainer {
//    private Label status = new Label("");
//    private ProgressIndicator pi = new ProgressIndicator();
//    private HorizontalLayout progressLayout = new HorizontalLayout();
    private Integer idGlobal = null;
    private String linkDB;
    private Integer idUser;
    private BigDecimal currentIdRequest = null;

    //@Override
    public void init(BigDecimal currentIdRequest) {
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
//        addComponent(new SeparatorL("Описание функций"));
        String ss = "<h1 align=\"center\">Загрузка программы импорта данных </h1> <font size=\"4\" FACE=\"Times New Roman\">"
                +" <p>Для ускорения проведения работ, уменьшения объема ручного ввода и количества ошибок при вводе данных мы предлагаем"
                +" импортировать часть выверенных данных по описанию рабочих мест из программы 1С. Импортированные данные будут оформлены в структурированные текстовые файлы,"
                +" содержание которых можно проконтролировать визуально, а затем импортировать в систему СОУТ."
                +" Для импорта данных, предлагается программа, которая представляет собой расшинение (плагин) программы 1С."
                +" Сама программа, ее описание и инструкция пользователя предлагаются ниже для скачивания и посмотра."
                +"</p>"
                +" <p> Если ваша кампания не желаете воспользоваться готовым программным решением (например из соображений безопасности), ваши сотрудники могут самостоятельно "
                +" разработать программу импорта ваших даных из программы 1С в структурированный текстовый формат. Образец файла данного формата  можно скачать на этойже странице ниже по тексту."
                +" </p>"
                +"</font>";

        Label headComment = new Label(ss, ContentMode.HTML);
//        Ruler ruler1 = new Ruler();
        Ruler ruler2 = new Ruler();

        addComponent(headComment);
        addComponent(ruler2);

        String puthToProgram = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..\\SETUP\\documents\\program";
       // Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Абсолютный путь: "+VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath());
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Путь для скачивания файлов: "+puthToProgram);
        addComponent(publicFields(puthToProgram));
//        Link sample = new Link("Скачать программу", new ExternalResource(
//                "http://www.google.com"));
//        sample.setDescription("Скачать програму импорта данных о состоянии рабочих мест.");
//        addComponent(sample);

        addComponent(new SeparatorL("Выгрузка импортированных данных на сервер SOUT"));
        String ss2 = " <p> Если вы успешно импортировали данные о наличии рабочих мест а предприятии в файл(ы), то теперь Вам необходимо переслать полученные файлы на сервер СОУТ для дальнейшей обработки."
                        +" Воспользуйтесь предлагаемым ниже инструментом для отправки полученных файлов."
                        +" </p>";


        addComponent(new Label(ss2, ContentMode.HTML));
        //addComponent(uploadFiles());
        uploadFiles();

        addComponent(new SeparatorL("Структура файла для импорта данных силами и средствами персонала заказчика"));
        String ss1 =
                " <p> Если вы не желаете воспользоваться предлагаемым плагином для прораммы 1С, то ваши сотрудники могут самостоятельно "
                +" разработать программу импорта даных из программы 1С в структурированный текстовый формат. Ниже предлагается шаблон такого файла и инстркуция по его заполнению."
                +" </p>";

        addComponent(new Label(ss1, ContentMode.HTML));
        String puthToShablon = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..\\SETUP\\documents\\shablon";
        addComponent(publicFields(puthToShablon));

        addComponent(new SeparatorL("......"));

    }

    private void uploadFiles() {
        //---------------------------------------------------------------------------------------
        final String PuthToTmp = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..\\TMP";
        final String PuthToExp = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "\\..\\..\\SETUP\\documents\\experts\\"+idGlobal.toString()+"\\uploads";
        //---------------------------------------------------------------------------------------

        FormLayout layout = new FormLayout();
        UploadFinishedHandler handler = new UploadFinishedHandler() {
            @Override
            public void handleFile(InputStream inputStream, String fileName, String mimType, long length) {
                String mess = "Файлы: ";
                try {
//                    FileOutputStream fos = new FileOutputStream(new File(PuthToTmp+"\\" + fileName));
/*
                    FileOutputStream fos = new FileOutputStream(new File(PuthToExp+"\\" + fileName));
                    fos.write(IOUtils.toByteArray(inputStream));
                    fos.close();
*/
                    DaoDocs dao = new DaoDocs(linkDB);
                    dao.setDocToDb(inputStream,fileName,idUser, currentIdRequest, new BigDecimal(100), null, null);

                    mess += " "+fileName;

                    //---- после тестирования----
/*
                    File iFile = new File(PuthToTmp+"\\"+fileName);
//                    File oFile = new File(PuthToExp+"\\"+fileName);
                    File oFile = new File(PuthToExp);
                    Files.copy(iFile,oFile);
                    iFile.delete();
*/
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, mess+" успешно загружены");
                    Notification.show(mess+" успешно загружены",Notification.Type.WARNING_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при загрузке файла на сервер");
                    Notification.show("Ошибка при загрузке файлов на сервер",Notification.Type.ERROR_MESSAGE);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при записи файла в БД");
                    Notification.show("Ошибка при записи файла в БД ("+e.getMessage()+")", Notification.Type.ERROR_MESSAGE);
                }

            }
        };

        UploadStateWindow window = new UploadStateWindow();
        MultiFileUpload singleUpload = new MultiFileUpload(handler,window, false);
        singleUpload.setCaption("Выгрузка одиночных файлов");
        singleUpload.setPanelCaption("Выгрузка одиночных файлов");
        singleUpload.getSmartUpload().setUploadButtonCaptions("Выгрузить файл","");

        MultiFileUpload multiUpload = new MultiFileUpload(handler,window);
        multiUpload.setCaption("Выгрузка группы файлов");
        multiUpload.setPanelCaption("Выгрузка группы файлов");
        multiUpload.getSmartUpload().setUploadButtonCaptions("Выгрузить файлы","Выгрузить файлы");

        layout.addComponent(singleUpload);
        layout.addComponent(multiUpload);
        addComponent(layout);

    }

    private Component publicFields(String holder) {
        ThemeResource ICON = new ThemeResource("img/16/action_save.gif");
        ThemeResource ICON1 = new ThemeResource("img/document_add.png");
        HorizontalLayout lay = new HorizontalLayout();
        lay.setSpacing(true);
        lay.setIcon(ICON1);
        lay.setCaption("Файлы на скачивание");
        File myFolder = new File(holder);
        File[] files = myFolder.listFiles();
        for (File file:files){
//            FileResource resource =  new FileResource(new File(myFolder+"\\"+file));
            FileResource resource =  new FileResource(file);
            String filename = file.getName();
            Link link = new Link(filename, resource);
            link.setTargetName("_blank");
            //link.setTargetName("_top");
            link.setIcon(ICON);

            lay.addComponent(link);
        }
        return lay;
    }






}



