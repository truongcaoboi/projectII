/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;

/**
 *
 * @author Xuan Truong PC
 */
public class Company {
    private String logo;
    private String name;
    private String link;
    
    public String toJson(){
        String result = "{";
        result += "\"logo\":\""+logo+"\",";
        result += "\"name\":\""+name+"\",";
        result += "\"link\":\""+link+"\"}";
        return result;
    }
    
    public static boolean isDuplicate(String companyName,Vector<Company> companys){
        if(companys.size()==0){
            return false;
        }
        else{
            for(Company company : companys){
                if(companyName.equals(company.name)){
                    return true;
                }
            }
            return false;
        }
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
