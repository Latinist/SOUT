package artintech.domain;

/**
 * Created by Анатолий on 19.01.2015.
 */
public class User {
    private Integer id;
    private String eMail;
    private String nameOrg;
    private String telephone;
    private Integer idGlobal;
    private String webState;
    public void setId(Integer id){     this.id = id;  }
    public  Integer getId(){   return id;  }
    public void seteMail(String eMail){ this.eMail = eMail;}
    public String geteMail(){return eMail;}
    public String geteMailStr(){ return eMail == null ? "нет" : eMail;}
    public void setNameOrg(String nameOrg){ this.nameOrg = nameOrg;}
    public String getNameOrg(){return nameOrg;}
    public String getNameOrgStr(){ return nameOrg == null ? "нет" :  nameOrg;}
    public void setTelephone(String telephone){this.telephone = telephone;}
    public String getTelephone(){return telephone;}
    public String getTelephoneStr(){
        return telephone == null ? "нет" : telephone;}
    public void setIdGlobal(Integer idGlobal){ this.idGlobal = idGlobal;}
    public Integer getIdGlobal(){
        return idGlobal;}
    public String getWebState() {
        return webState;
    }

    public void setWebState(String webState) {
        this.webState = webState;
    }
}
