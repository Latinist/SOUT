package artintech;

import artintech.beans.IComponentContainer;
import artintech.domain.User;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

import java.io.*;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 29.06.2015.
 * Дизайн 2
 * ---------скачать список требуемых документов-----------
 */
public class LoadTools extends VerticalLayout implements IComponentContainer{
    String linkDB;
    Integer idUser = null;
    User user = null;
    private Integer idGlobal = null;
//    Button btnSave = new Button("Далее");

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        user   = (User) VaadinSession.getCurrent().getAttribute("user");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");

        setMargin(false);
        addStyleName("Light");

        BuildMain();
        setImmediate(true);

    }
    private void BuildMain(){
        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/documents/experts/"+idGlobal.toString()+"/downloads/needdocs";
        VerticalLayout outPanel = new VerticalLayout();
        outPanel.setWidth("95%");
        outPanel.setHeight("600px");
        outPanel.addStyleName("semitransparentlayout");
        outPanel.setMargin(true);
        VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("100%");
        contentOutPanel.setHeightUndefined();

        Panel panel = new Panel();
        //panel.setCaption("fgdgdfg");
        panel.setWidth("400px");
        panel.setHeight("400px");

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        //------------------------------------------------------------------------
        HorizontalLayout layoutImage = new HorizontalLayout();
        layoutImage.setSizeFull();
        final ThemeResource automatic = new ThemeResource("img/avtomaticheski.png");
        Image imageHand = new Image(null,automatic);
        //imageHand.setWidth("60%");
        layoutImage.addComponent(imageHand);
        layoutImage.setComponentAlignment(imageHand, Alignment.MIDDLE_CENTER);
        layoutImage.setMargin(true);
        addComponent(layoutImage);


        contentLayout.addComponent(new Label("<style type=\"text/css\">p {margin: 0.2em;}</style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">ВНИМАНИЕ</font></p><p align=\"justify\">Здесь можете  скачать список документов, копии которых в электронном виде, необходимы для проведения работ по СОУТ.</p>", ContentMode.HTML));
        contentLayout.addComponent(layoutImage);
        contentLayout.setComponentAlignment(layoutImage,Alignment.BOTTOM_CENTER);
        panel.setContent(contentLayout);
        //panel.addStyleName(ValoTheme.PANEL_WELL);
        panel.addStyleName(Reindeer.PANEL_LIGHT);
        panel.addStyleName("greylayout");
        panel.addStyleName("shadow");
        panel.addStyleName("paddingFull");
        //panel.addStyleName("transparentlayout");

        HorizontalLayout buttonLayout =  new HorizontalLayout();
        buttonLayout.setWidthUndefined();
        buttonLayout.setHeightUndefined();
        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);
        final Button loadButton = new Button("Скачать");
//        loadButton.addClickListener( new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) {
//
//                File myFolder = new File(holderNeedDocs);
//                File[] files = myFolder.listFiles();
////                Integer ii = 0;
////                for (File file:files){
////                    FileResource resource =  new FileResource(file);
////                    String filename = file.getName();
////                }
//                File file = files[0];
//                String filename = file.getName();
//                String fullFileName = holderNeedDocs+"/"+filename;
//                FileDownloadResource resource = new FileDownloadResource(new File(fullFileName));
//                FileDownloader fd = new FileDownloader(resource);
//                fd.extend(loadButton);
//
//            }
//        });
//

        // кодировка оси String osEncoding=System.getProperty("file.encoding");

        File myFolder = new File(holderNeedDocs);
        File[] files = myFolder.listFiles();

        File file = files[0];
        if (file == null) {
            Notification.show("Нет опубликованого документа");
            return;
        }
        String filename = file.getName();
        String fullFileName = holderNeedDocs+"/"+filename;
//   ??? Это стало скачивать в другой кодировке    FileDownloadResource resource = new FileDownloadResource(new File(fullFileName));
//   ??? Это стало скачивать в другой кодировке     FileDownloader fd = new FileDownloader(resource);

        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Скачивается файл : "+fullFileName);

//        String urlEncodedFileName = null;
//        try {
//            urlEncodedFileName = URLEncoder.encode(fullFileName, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        fullFileName = urlEncodedFileName.replace("+", "%20");

        Resource res = new FileResource(new File(fullFileName));
        FileDownloader fd = new FileDownloader(res);
        fd.extend(loadButton);




        loadButton.setWidth("150px");
        loadButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //??????/
            }
        });
        Button nextButton = new Button("Далее");
        nextButton.setWidth("150px");
        nextButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getNavigator().navigateTo("loadProgram");
            }
        });


        HorizontalLayout prokladka  = new HorizontalLayout();
        prokladka. setHeight("50px");

        //dataPanel.setWidthUndefined(); // setWidth("100%");
        //addComponent(nextButton);
        //setComponentAlignment(nextButton, Alignment.TOP_RIGHT);
        contentOutPanel.addComponent(prokladka);
        contentOutPanel.addComponent(panel);
        contentOutPanel.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        buttonLayout.addComponent(loadButton);
        //buttonLayout.addComponent(nextButton);

        VerticalLayout prokladka2 = new VerticalLayout();
        prokladka2.setHeight("10px");
        contentOutPanel.addComponent(prokladka2);
        contentOutPanel.addComponent(buttonLayout);
        contentOutPanel.setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);

        //Ruler ruler = new Ruler();
        Label podval = new Label("<p align=\"center\">Eсли Вы уже скачали список документов - нажмите кнопку далее для перехода к сбору информации для проведения СОУТ.</p>",ContentMode.HTML);
        podval.setWidth("80%");

        //contentOutPanel.addComponent(ruler);

//        contentOutPanel.addComponent(podval);
//        contentOutPanel.setComponentAlignment(podval, Alignment.TOP_CENTER);
//        contentOutPanel.addComponent(nextButton);
//        contentOutPanel.setComponentAlignment(nextButton, Alignment.TOP_CENTER);

        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);

        //VerticalLayout prokladka3 = new VerticalLayout();
        //prokladka3.setHeight("10px");
        //addComponent(prokladka3);
        addComponent(podval);
        setComponentAlignment(podval, Alignment.TOP_CENTER);
        addComponent(nextButton);
        setComponentAlignment(nextButton, Alignment.TOP_CENTER);
        //-----------------------------------------
        FactoryTrains factoryTrains =  new FactoryTrains();
        HorizontalLayout podballogo = (HorizontalLayout) factoryTrains.makeTrain(new String[]{"Договоры", "Список документов","Сервис","Загрузка"},2);
        addComponent(podballogo);
        setComponentAlignment(podballogo,Alignment.TOP_CENTER);
        //-----------------------------------------

    }

//    public class FileDownloadResource extends FileResource {
//
//        public FileDownloadResource(File sourceFile) {
//            super(sourceFile);
//        }
//
//        public DownloadStream getStream() {
//            try {
//                final DownloadStream ds = new DownloadStream(
//                        new FileInputStream(getSourceFile()), getMIMEType(),
//                        getFilename());
//                ds.setParameter("Content-Disposition", "attachment; filename=" +getFilename());
//                ds.setCacheTime(getCacheTime());
//                return ds;
//            } catch (final FileNotFoundException e) {
//                // No logging for non-existing files at this level.
//                return null;
//            }
//        }
//    }


}
