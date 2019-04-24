/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;
import org.bson.Document;
/**
 *
 * @author Xuan Truong PC
 */
public class Work {

    //khai báo thuộc tính
    private String id = "";//id
    private String titleJob;//tên công việc
    private String companyName;//tên công ty
    private String companyLink = "";//lien ket ngoai toi cong ty
    private String area = "";//Khu vực tuyển dụng
    private String address = "";//Địa chỉ làm việc
    private String salary;//Mức lương
    private String experience;//Kinh nghiệm
    private String career;//ngành nghề
    private int quantity;//Số lượng
    private String featureJob;//Đặc tính công việc
    private String form;//Hình thức
    private String degree;//Trình độ
    private int gender;//Giới tính
    private String description;//Mô tả
    private String required;//Yêu cầu
    private String interest;//quyền lợi
    private String expire;//hạn nộp
    private String contactName;//Người liên hệ
    private String contactAddress;//Địa chỉ liên hệ
    private String contactPhone = "";//Số điện thoại liên hệ
    private String contactEmail = "";//Email liên hệ
    private String logo = "";

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    
    
    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleJob() {
        return titleJob;
    }

    public void setTitleJob(String titleJob) {
        this.titleJob = titleJob;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFeatureJob() {
        return featureJob;
    }

    public void setFeatureJob(String featureJob) {
        this.featureJob = featureJob;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String convertToXMLObject() {
        String xml = "\r\n<work>";
        String descriptions[] = this.description.split("<br>");
//        if(descriptions.length <= 1){
//            descriptions = this.description.split(";");
//        }
        String requires[] = this.required.split("<br>");
//        if(requires.length <= 1){
//            requires = this.required.split(";");
//        }
        String interests[] = this.interest.split("<br>");
//        if(interests.length <= 1){
//            interests = this.interest.split(";");
//        }

        xml += "\r\n\t<id>" + this.id + "</id>";
        xml += "\r\n\t<title>" + this.titleJob + "</title>";
        xml += "\r\n\t<company>";
        xml += "\r\n\t\t<name>" + this.companyName + "</name>";
        xml += "\r\n\t\t<address>" + this.address + "</address>";
        xml += "\r\n\t\t<link>" + this.companyLink + "</link>";
        xml += "\r\n\t</company>";
        xml += "\r\n\t<area>" + this.area + "</area>";
        xml += "\r\n\t<salary>" + this.salary + "</salary>";
        xml += "\r\n\t<degree>" + this.degree + "</degree>";
        xml += "\r\n\t<career>" + this.career + "</career>";
        xml += "\r\n\t<experience>" + this.experience + "</experience>";
        xml += "\r\n\t<feature>" + this.featureJob + "</feature>";
        xml += "\r\n\t<form>" + this.form + "</form>";
        xml += "\r\n\t<gender>" + this.gender + "</gender>";
        xml += "\r\n\t<expire>" + this.expire + "</expire>";
        xml += "\r\n\t<descriptions>";
        for (int i = 0; i < descriptions.length; i++) {
            if (descriptions[i].trim() != "") {
                xml += "\r\n\t\t<description>" + descriptions[i] + "</description>";
            }
        }
        xml += "\r\n\t</descriptions>";

        xml += "\r\n\t<requires>";
        for (int i = 0; i < requires.length; i++) {
            if (requires[i].trim() != "") {
                xml += "\r\n\t\t<require>" + requires[i] + "</require>";
            }
        }
        xml += "\r\n\t</requires>";

        xml += "\r\n\t<interests>";
        for (int i = 0; i < interests.length; i++) {
            if (interests[i].trim() != "") {
                xml += "\r\n\t\t<interest>" + interests[i] + "</interest>";
            }
        }
        xml += "\r\n\t</interests>";

        xml += "\r\n\t<contact>";
        xml += "\r\n\t\t<name>" + this.contactName + "</name>";
        xml += "\r\n\t\t<address>" + this.contactAddress + "</address>";
        xml += "\r\n\t\t<email>" + this.contactEmail + "</email>";
        xml += "\r\n\t\t<phone>" + this.contactPhone + "</phone>";
        xml += "\r\n\t</contact>";
        xml += "\r\n</work>";

        return xml;
    }

    public void toString(String ak) {
        System.out.println("Nhan de: " + titleJob);
        System.out.println("Ten cong ty: " + companyName);
        System.out.println("Dia chi cong ty: "+address);
        System.out.println("Link cong ty: " + companyLink);
        System.out.println("khu vuc: " + area);
        System.out.println("Muc luong: " + salary);
        System.out.println("kinh nghiem: " + experience);
        System.out.println("nganh nghe: " + career);
        System.out.println("bang cap: " + degree);
        System.out.println("so luong: " + quantity);
        System.out.println("Tinhs chat: " + featureJob);
        System.out.println("hinh thuc: " + form);
        System.out.println("Mo ta: " + description);
        System.out.println("yeu cau: " + required);
        System.out.println("Quyen loi: " + interest);
        System.out.println("nguoi lien he: " + contactName);
        System.out.println("dia chi lien he: " + contactAddress);
        System.out.println("email lien he: " + contactEmail);
        System.out.println("dien thoai lien he: " + contactPhone);
        System.out.println("Han nop: " + expire);
        System.out.println("gioi tinh: " + gender);
        System.out.println("------------------------------");
    }
    
    public Document getDocument(){
        Document doc = new Document();
        doc.append("titleJob", titleJob);
        doc.append("companyName", companyName);
        doc.append("companyAddress", address);
        doc.append("conpanyLink", companyLink);
        doc.append("area", area);
        doc.append("salary", salary);
        doc.append("career", career);
        doc.append("degree", degree);
        doc.append("experience", experience);
        doc.append("quantity", quantity);
        doc.append("featureJob", featureJob);
        doc.append("form", form);
        doc.append("description", description);
        doc.append("required", required);
        doc.append("interest", interest);
        doc.append("contactName", contactName);
        doc.append("contactAddress", contactAddress);
        doc.append("contactEmail", contactEmail);
        doc.append("contactPhone", contactPhone);
        doc.append("expire", expire);
        doc.append("gender", gender);
        doc.append("logo", logo);
        
        return doc;
    }
}
