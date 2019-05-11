/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import java.util.Vector;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Xuan Truong PC
 */

public class Standard {
    public static final String _hostname = "localhost";
    public static final int _port = 27017;
    public static final String dbname = "test";
    
    private Vector<Job> jobs = new Vector<Job>();
    public void executeStandardData(){
        //Tao ket noi lay du lieu
        MongoClient mongoClient = new MongoClient(new ServerAddress(_hostname, _port).toString());
        MongoCollection<Document> mongoColection = (MongoCollection<Document>) mongoClient.getDatabase(dbname).getCollection("dataCrawl3");
        
        //Xu li
        //Standard st = new Standard();
        for (Document doc : mongoColection.find()) {
            if(isDuplicate(doc)){
                System.out.println("cong viec da ton tai trong tap du lieu hoac bi loi");
            }else{
                Job job = new Job();
                //chen du lieu
                String titleJob = doc.getString("titleJob");
                String companyName = doc.getString("companyName");
                String companyLink = doc.getString("companyLink");
                String address = doc.getString("companyAddress");
                String area = doc.getString("area");
                String experience = doc.getString("experience");//Kinh nghiệm
                String career = doc.getString("career");//ngành nghề
                int quantity = (doc.getInteger("quantity") == null) ? 0 : doc.getInteger("quantity");//Số lượng
                String featureJob = doc.getString("featureJob");//Đặc tính công việc
                String form = doc.getString("form");//Hình thức
                String degree = doc.getString("degree");//Trình độ
                int gender = (doc.getInteger("gender") == null) ? 2 : doc.getInteger("gender");//Giới tính
                String description = doc.getString("description");//Mô tả
                String required = doc.getString("required");//Yêu cầu
                String interest = doc.getString("interest");//quyền lợi
                String expire = doc.getString("expire").replaceAll("-", "/");//hạn nộp
                String contactName = doc.getString("contactName");//Người liên hệ
                String contactAddress = doc.getString("contactAddress");//Địa chỉ liên hệ
                String contactPhone = doc.getString("contactPhone");//Số điện thoại liên hệ
                String contactEmail = doc.getString("contactEmail");//Email liên hệ
                String linkRoot = doc.getString("linkRoot");
                String salary = doc.getString("salary");
                String id = doc.get("_id").toString();

                job.setTitleJob(titleJob);
                job.setCompanyName(companyName);
                job.setCompanyLink(companyLink);
                job.setAddress(address);
                job.setArea(area);
                job.setCareer(career);
                job.setContactAddress(contactAddress);
                job.setContactEmail(contactEmail);
                job.setContactPhone(contactPhone);
                job.setDegree(degree);
                job.setDescription(description);
                job.setExperience(experience);
                job.setExpire(expire);
                job.setFeatureJob(featureJob);
                job.setForm(form);
                job.setGender(gender);
                job.setInterest(interest);
                job.setId(id);
                job.setQuantity(quantity);
                job.setRequired(required);
                job.setSalary(salary);
                job.setContactName(contactName);
                job.setLogo(doc.getString("logo"));
                job.setLinkRoot(linkRoot);
                
                job.standardData();
                jobs.add(job);
            }
        }
    }
    
    public boolean isDuplicate(Document doc) {
        if (doc.getString("companyName") == null | doc.getString("titleJob") == null | doc.getString("expire") == null) {
            return true;
        }
        if (jobs.size() != 0) {
            for (Job job : jobs) {
                if (job.getCompanyName().trim().replaceAll("CP", "Cổ phần").toUpperCase().equals(doc.getString("companyName").trim().replaceAll("CP", "Cổ phần").toUpperCase())) {
                    if (job.getTitleJob().trim().toUpperCase().equals(doc.getString("titleJob").trim().toUpperCase())) {
                        if (job.getExpire().trim().replaceAll("-", "/").equals(doc.getString("expire").trim().replaceAll("-", "/"))) {
                            if (job.getContactEmail() == null | job.getContactEmail() == "") {
                                job.setContactEmail(doc.getString("contactEmail"));
                            }
                            if (job.getContactPhone() == null | job.getContactPhone() == "") {
                                job.setContactPhone(doc.getString("contactPhone"));
                            }
                            if (job.getDegree() == null | job.getDegree() == "") {
                                job.setDegree(doc.getString("degree"));
                            }
                            if (job.getExperience() == null | job.getExperience() == "") {
                                job.setExperience(doc.getString("experience"));
                            }
                            if (job.getQuantity() == 0) {
                                job.setQuantity((doc.getInteger("quantity") == null) ? 0 : doc.getInteger("quantity"));
                            }
                            if (job.getFeatureJob() == null | job.getFeatureJob() == "") {
                                job.setFeatureJob(doc.getString("featureJob"));
                            }
                            if (job.getForm() == null | job.getForm() == "") {
                                job.setForm(doc.getString("form"));
                            }
                            if (job.getRequired() == null | job.getRequired() == "") {
                                job.setRequired(doc.getString("required"));
                            }
                            if (job.getInterest() == null | job.getInterest() == "") {
                                job.setInterest(doc.getString("interest"));
                            }
                            if (job.getGender() != 2) {
                                job.setGender((doc.getInteger("gender") == null) ? 2 : doc.getInteger("gender"));
                            }
                            if (job.getCompanyLink().equals("#")) {
                                job.setCompanyLink((doc.getString("conpanyLink") == null) ? "#" : doc.getString("conpanyLink"));
                            }
                            if (job.getLogo().equals("")) {
                                job.setLogo(doc.getString("logo"));
                            }
                            if(job.getLinkRoot().indexOf(doc.getString("linkRoot"))<0){
                                job.setLinkRoot(job.getLinkRoot()+" dxt "+doc.getString("linkRoot"));
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void addDataIntoDatabase(){
        if(jobs.size()==0){
            System.out.println("khong co du lieu de them");
            return;
        }
        
        MongoClient mongoClient = new MongoClient(new ServerAddress(_hostname, _port).toString());
        MongoCollection<Document> mongoColection = (MongoCollection<Document>) mongoClient.getDatabase(dbname).getCollection("project");
        for(Job job : jobs){
            Document doc = job.createDocument();
            mongoColection.insertOne(doc);
            //job.show();
        }
    }
}
