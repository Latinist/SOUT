package artintech.dao;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 03.11.2015.
 */
public class Dao {
    public static final int PLUGIN1C = 1;
    public static final int DOCS = 2;
    public static final int STRUCTURE = 3;
    public static final int SVED = 4;

    String linkDB =  null;
    Integer idGlobal = null;
    //------------ фабрика классов --------------
    public FileDownloader createFileDownloader(BigDecimal idDogovor, int pattern) throws Exception {
        if (linkDB == null) {
            linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
            idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        }
        switch (pattern){
            case PLUGIN1C: {
                return get1CPlugin(idDogovor);
            }
            case DOCS: {
                return getlispDocs(idDogovor);
            }
            case STRUCTURE: {
                return getlispStructure(idDogovor);
            }
            case SVED: {
                return getlispSved(idDogovor);
            }
            default:{
                return null;
            }
        }
    }

    private FileDownloader get1CPlugin(BigDecimal idDogovor) throws Exception {
        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/documents/program";
        File myFolder = new File(holderNeedDocs);
        File[] files = myFolder.listFiles();
        if (files == null) {
            //Notification.show("Нет опубликованого документа");
            throw new Exception("Нет опубликованого документа");
        }
        File file = files[0];
        String filename = file.getName();
        String fullFileName = holderNeedDocs+"/"+filename;
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Подготовка к скачиванию файла : "+fullFileName);
        Resource res = new FileResource(new File(fullFileName));
        FileDownloader fd = new FileDownloader(res);
        return fd;
    }

    private FileDownloader getlispDocs(BigDecimal idDogovor) throws Exception {
        final String holderNeedDocs = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/documents/experts/"+idGlobal.toString()+"/downloads/needdocs";
        File myFolder = new File(holderNeedDocs);
        File[] files = myFolder.listFiles();
        File file = files[0];
        if (file == null) {
            //Notification.show("Нет опубликованого документа");
            throw new Exception("Нет опубликованого документа");
        }
        String filename = file.getName();
        String fullFileName = holderNeedDocs+"/"+filename;
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Подготовка к скачиванию файла : "+fullFileName);
        Resource res = new FileResource(new File(fullFileName));
        FileDownloader fd = new FileDownloader(res);
        return fd;
    }

    private FileDownloader getlispStructure(BigDecimal idDogovor) throws SQLException {
        DaoDocs dao = new DaoDocs(linkDB);
        Resource resStructure = null;
        resStructure = dao.getExelStruktureFile(idDogovor);
        FileDownloader fdEXEL = new FileDownloader(resStructure);
        return fdEXEL;
    }

    private FileDownloader getlispSved(BigDecimal idDogovor) throws SQLException {
        DaoDocs dao = new DaoDocs(linkDB);
        Resource resEXEL = null;
        resEXEL = dao.getExelFile(idDogovor);
        FileDownloader fdEXEL = new FileDownloader(resEXEL);
        return fdEXEL;
    }


    public interface GetFileDownloader{

    }
    public interface DogovorUpdated {
        void doUpdateView();
    }

}
