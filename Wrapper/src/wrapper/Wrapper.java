/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

/**
 *
 * @author Xuan Truong PC
 */
import crawl.*;
import wrapper.Work;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import java.util.Vector;
import org.bson.Document;
import org.bson.types.ObjectId;
import filter.Standard;

public class Wrapper {

    public static final String _hostname = "localhost";
    public static final int _port = 27017;
    public static final String dbname = "test";

    public static Vector<Work> works = new Vector<Work>();

    /**
     * @param args the command line arguments webPage1 :
     * https://www.timviecnhanh.com webPage2 : https://mywork.com.vn/ webPage3 :
     * https://vieclam24h.vn
     */
    public static void main(String args[]) {
        //new Wrapper1().crawlData();
        //new Wrapper2().crawlData();
        //new Wrapper3().crawlData();
       // standardlizeData();
       Standard st = new Standard();
       st.executeStandardData();
       st.addDataIntoDatabase();
       
    }

    public static void standardlizeData() {
        MongoClient mongoClient = new MongoClient(new ServerAddress(_hostname, _port).toString());
        MongoCollection<Document> mongoColection = (MongoCollection<Document>) mongoClient.getDatabase(dbname).getCollection("dataCrawl3");
        int i = 0;
        for (Document doc : mongoColection.find()) {
            i++;
            if (!isDuplicate(doc, mongoColection)) {
                Work work = new Work();
                String id = doc.get("_id").toString();//id
                String titleJob = doc.getString("titleJob");//tên công việc
                if (titleJob == null) {
                    mongoColection.deleteOne(new Document("_id", new ObjectId(doc.get("_id").toString())));
                    continue;
                }
                titleJob = doc.getString("titleJob").trim();
                String companyName = doc.getString("companyName");//tên công ty
                if (companyName == null) {
                    mongoColection.deleteOne(new Document("_id", new ObjectId(doc.get("_id").toString())));
                    continue;
                }
                companyName = doc.getString("companyName").trim();
                String companyLink = doc.getString("conpanyLink").replaceAll("Website:", "");//lien ket ngoai toi cong ty
                if (companyLink == null) {
                    mongoColection.deleteOne(new Document("_id", new ObjectId(doc.get("_id").toString())));
                    continue;
                }
                String area = doc.getString("area");//Khu vực tuyển dụng
                String address = doc.getString("companyAddress").replaceAll("Địa chỉ:", "");//Địa chỉ làm việc

                String salary = doc.getString("salary");//Mức lương
                String salarys[];
                String sala = "";
                try {
                    if (salary != null) {
                        if (salary.indexOf("USD") >= 0) {
                            salarys = salary.replaceAll("USD", "").split("-");
                            for (String sal : salarys) {
                                if (sal != null & sal != "") {
                                    sala += Integer.parseInt(sal.trim()) * 23500 + " - ";
                                }
                            }
                            sala = sala.substring(0, sala.length() - 3);
                        } else {
                            salarys = salary.split("-");
                            for (String sal : salarys) {
                                if (sal != null & sal != "") {
                                    if (Integer.parseInt(sal.trim()) < 100) {
                                        sala += Integer.parseInt(sal.trim()) * 1000000 + " - ";
                                    } else {
                                        sala += Integer.parseInt(sal.trim()) * 1 + " - ";
                                    }
                                }
                            }
                            sala = sala.substring(0, sala.length() - 3);
                        }
                    } else {
                        salary = "theo ý muốn";
                    }
                } catch (Exception e) {
                    sala = salary;
                }

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

                work.setTitleJob(titleJob);
                work.setCompanyName(companyName);
                work.setCompanyLink(companyLink);
                work.setAddress(address);
                work.setArea(area);
                work.setCareer(career);
                work.setContactAddress(contactAddress);
                work.setContactEmail(contactEmail);
                work.setContactPhone(contactPhone);
                work.setDegree(degree);
                work.setDescription(description);
                work.setExperience(experience);
                work.setExpire(expire);
                work.setFeatureJob(featureJob);
                work.setForm(form);
                work.setGender(gender);
                work.setInterest(interest);
                work.setId(id);
                work.setQuantity(quantity);
                work.setRequired(required);
                work.setSalary(sala);
                work.setContactName(contactName);
                work.setLogo(doc.getString("logo"));
                works.add(work);

                Document update = new Document("$set", work.getDocument());//Bộ dữ liệu update
                Document condition = new Document("_id", new ObjectId(work.getId()));//Bộ điều kiện
                mongoColection.updateOne(condition, update);
                System.err.println(i + ": " + work.getTitleJob());
            }
        }

        mongoClient.close();
        System.out.println(i);
    }

    public static boolean isDuplicate(Document doc, MongoCollection<Document> mongoColection) {
        if (doc.getString("companyName") == null | doc.getString("titleJob") == null | doc.getString("expire") == null) {
            return false;
        }
        if (works.size() != 0) {
            for (Work work : works) {
                if (work.getCompanyName().trim().replaceAll("CP", "Cổ phần").toUpperCase().equals(doc.getString("companyName").trim().replaceAll("CP", "Cổ phần").toUpperCase())) {
                    if (work.getTitleJob().trim().toUpperCase().equals(doc.getString("titleJob").trim().toUpperCase())) {
                        if (work.getExpire().trim().replaceAll("-", "/").equals(doc.getString("expire").trim().replaceAll("-", "/"))) {
                            if (work.getContactEmail() == null | work.getContactEmail() == "") {
                                work.setContactEmail(doc.getString("contactEmail"));
                            }
                            if (work.getContactPhone() == null | work.getContactPhone() == "") {
                                work.setContactPhone(doc.getString("contactPhone"));
                            }
                            if (work.getDegree() == null | work.getDegree() == "") {
                                work.setDegree(doc.getString("degree"));
                            }
                            if (work.getExperience() == null | work.getExperience() == "") {
                                work.setExperience(doc.getString("experience"));
                            }
                            if (work.getQuantity() == 0) {
                                work.setQuantity((doc.getInteger("quantity") == null) ? 0 : doc.getInteger("quantity"));
                            }
                            if (work.getFeatureJob() == null | work.getFeatureJob() == "") {
                                work.setFeatureJob(doc.getString("featureJob"));
                            }
                            if (work.getForm() == null | work.getForm() == "") {
                                work.setForm(doc.getString("form"));
                            }
                            if (work.getRequired() == null | work.getRequired() == "") {
                                work.setRequired(doc.getString("required"));
                            }
                            if (work.getInterest() == null | work.getInterest() == "") {
                                work.setInterest(doc.getString("interest"));
                            }
                            if (work.getGender() != 2) {
                                work.setGender((doc.getInteger("gender") == null) ? 2 : doc.getInteger("gender"));
                            }
                            if (work.getCompanyLink().equals("#")) {
                                work.setCompanyLink((doc.getString("conpanyLink") == null) ? "#" : doc.getString("conpanyLink"));
                            }
                            if (work.getLogo().equals("")) {
                                work.setLogo(doc.getString("logo"));
                            }
                            Document update = new Document("$set", work.getDocument());//Bộ dữ liệu update
                            Document condition = new Document("_id", new ObjectId(work.getId()));//Bộ điều kiện
                            mongoColection.updateOne(condition, update);

                            mongoColection.deleteOne(new Document("_id", new ObjectId(doc.get("_id").toString())));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void copyData() {

    }
}

//if (work.getTitleJob().toUpperCase().trim().equals(doc.getString("titleJob").toUpperCase().trim()) & work.getCompanyName().equals(doc.getString("companyName"))) {
//                    if (work.getContactEmail() == null | work.getContactEmail() == "") {
//                        work.setContactEmail(doc.getString("contactEmail"));
//                    }
//                    if (work.getContactPhone() == null | work.getContactPhone() == "") {
//                        work.setContactPhone(doc.getString("contactPhone"));
//                    }
//                    if (work.getDegree() == null | work.getDegree() == "") {
//                        work.setDegree(doc.getString("degree"));
//                    }
//                    if (work.getExperience() == null | work.getExperience() == "") {
//                        work.setExperience(doc.getString("experience"));
//                    }
//                    if (work.getQuantity() == 0) {
//                        work.setQuantity((doc.getInteger("quantity") == null) ? 0 : doc.getInteger("quantity"));
//                    }
//                    if (work.getFeatureJob() == null | work.getFeatureJob() == "") {
//                        work.setFeatureJob(doc.getString("featureJob"));
//                    }
//                    if (work.getForm() == null | work.getForm() == "") {
//                        work.setForm(doc.getString("form"));
//                    }
//                    if (work.getRequired() == null | work.getRequired() == "") {
//                        work.setRequired(doc.getString("required"));
//                    }
//                    if (work.getInterest() == null | work.getInterest() == "") {
//                        work.setInterest(doc.getString("interest"));
//                    }
//                    if (work.getGender() != 2) {
//                        work.setGender((doc.getInteger("gender") == null) ? 2 : doc.getInteger("gender"));
//                    }
//                    Document update = new Document("$set", work.getDocument());//Bộ dữ liệu update
//                    Document condition = new Document("_id", new ObjectId(work.getId()));//Bộ điều kiện
//                    mongoColection.updateOne(condition, update);
//
//                    mongoColection.deleteOne(new Document("_id", new ObjectId(doc.get("_id").toString())));
//                    return true;
//                }
