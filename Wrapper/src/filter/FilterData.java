/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

/**
 *
 * @author Xuan Truong PC
 */
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wrapper.Work;

public class FilterData {

    public static void main(String args[]) {
        String filePath = "dataWebpage1.xml";
        new FilterData().getWork(filePath);
    }

    public Work getWork(String filePath) {
        File file = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("work");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Work work = this.getWork(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Work getWork(Node node) {
        Work work = new Work();
        NodeList lastNode = null;
        Node child = node.getFirstChild();
        child = child.getNextSibling();
        work.setId((child.getTextContent() == null) ? "" : child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setTitleJob(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        System.out.println(child.getNodeName());
        Node childOfChild = child.getFirstChild();
        childOfChild = childOfChild.getNextSibling();
        work.setCompanyName(childOfChild.getTextContent());
        childOfChild = childOfChild.getNextSibling();
        childOfChild = childOfChild.getNextSibling();
        work.setAddress((childOfChild.getTextContent() == null) ? "" : childOfChild.getTextContent());
        work.setCompanyLink(childOfChild.getNextSibling().getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setArea(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setSalary(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setDegree(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setCareer(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setExperience(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setFeatureJob(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setForm(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setGender(child.getTextContent() == "Nam" ? 1 : 0);
        child = child.getNextSibling();
        child = child.getNextSibling();
        work.setExpire(child.getTextContent());
        child = child.getNextSibling();
        child = child.getNextSibling();
        childOfChild = child.getFirstChild();
        childOfChild = childOfChild.getNextSibling();
        String des = "";
        while (childOfChild != null) {
            lastNode = childOfChild.getChildNodes();
            String value = "";
            for(int i = 0;i<lastNode.getLength();i++){
                Node n = lastNode.item(i);
                value += " "+n.getTextContent();
            }
            des += value;
            childOfChild = childOfChild.getNextSibling();
            childOfChild = childOfChild.getNextSibling();
        }
        work.setDescription(des);

        child = child.getNextSibling();
        child = child.getNextSibling();
        childOfChild = child.getFirstChild();
        childOfChild = childOfChild.getNextSibling();
        String req = "";
        while (childOfChild != null) {
            lastNode = childOfChild.getChildNodes();
            String value = "";
            for(int i = 0;i<lastNode.getLength();i++){
                Node n = lastNode.item(i);
                value += " "+n.getTextContent();
            }
            req += value;
            childOfChild = childOfChild.getNextSibling();
            childOfChild = childOfChild.getNextSibling();
        }
        work.setRequired(req);

        child = child.getNextSibling();
        child = child.getNextSibling();
        childOfChild = child.getFirstChild();
        childOfChild = childOfChild.getNextSibling();
        String interest = "";
        while (childOfChild != null) {
            lastNode = childOfChild.getChildNodes();
            String value = "";
            for(int i = 0;i<lastNode.getLength();i++){
                Node n = lastNode.item(i);
                value += " "+n.getTextContent();
            }
            interest += value;
            childOfChild = childOfChild.getNextSibling();
            childOfChild = childOfChild.getNextSibling();
        }
        work.setInterest(interest);

        child = child.getNextSibling();
        child = child.getNextSibling();
        childOfChild = child.getFirstChild();
        childOfChild = childOfChild.getNextSibling();
        work.setContactName(childOfChild.getTextContent());
        childOfChild = childOfChild.getNextSibling();
        childOfChild = childOfChild.getNextSibling();
        work.setContactAddress(childOfChild.getTextContent());
        childOfChild = childOfChild.getNextSibling();
        childOfChild = childOfChild.getNextSibling();
        work.setContactEmail(childOfChild.getTextContent());
        childOfChild = childOfChild.getNextSibling();
        childOfChild = childOfChild.getNextSibling();
        work.setContactPhone(childOfChild.getTextContent());
        work.toString("ad");
        return work;
    }
    
    public Work standardizedData(Work work){
        
        return work;
    }
    
    public boolean insertToDatabase(Work work){
        return false;
    }
}
