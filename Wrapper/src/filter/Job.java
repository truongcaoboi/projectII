/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.text.DecimalFormat;
import java.util.Date;
import org.bson.Document;
import java.text.SimpleDateFormat;

/**
 *
 * @author Xuan Truong PC
 */
public class Job {

    private SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

    //Phan thuoc tinh
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
    private String linkRoot = "";

    private double maxSalary;
    private double minSalary;

    //Tao document de insert du lieu
    public Document createDocument() {
        Document doc = new Document();
        doc.append("titleJob", titleJob);
        doc.append("companyName", companyName);
        doc.append("companyAddress", address);
        doc.append("companyLink", companyLink);
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
        try {
            doc.append("expire", output.parse(expire));
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.append("gender", gender);
        doc.append("logo", logo);
        doc.append("linkRoot", linkRoot);
        doc.append("minSalary", minSalary);
        doc.append("maxSalary", maxSalary);
        return doc;
    }

    //Chuan hoa lai nguon du lieu
    public void standardData() {
        titleJob = titleJob.trim().toUpperCase();
        companyName = companyName.trim().toUpperCase().replaceAll("CP", "CỔ PHẦN");
        area = "HÀ NỘI";
        address = address.trim().toUpperCase().replaceAll("ĐỊA CHỈ:", "");

        DecimalFormat format = new DecimalFormat("###,###,###");
        salary = salary.trim().toUpperCase();
        //System.out.println(salary);
        if (salary.indexOf("USD") >= 0) {
            salary = salary.replaceAll("\\.", "");
            salary = salary.replaceAll("USD", "");
            if (salary.indexOf("TRÊN") >= 0) {
                salary = salary.replaceAll("TRÊN", "").trim();
                if (salary.indexOf("HOA HỒNG") >= 0) {
                    salary = salary.substring(0, salary.indexOf("(") - 1).trim();
                }
                try {
                    minSalary = Double.parseDouble(salary);
                    maxSalary = minSalary;
                    salary = "Trên " + format.format(minSalary)+" VNĐ";
                } catch (Exception e) {
                    System.out.println("Tren:" + salary);
                    minSalary = 10000000;
                    maxSalary = minSalary;
                    salary = "Trên " + format.format(minSalary)+" VNĐ";
                    e.printStackTrace();
                }
            } else if (salary.indexOf("DƯỚI") >= 0) {
                salary = salary.replaceAll("DƯỚI", "").trim();
                try {
                    minSalary = Double.parseDouble(salary);
                    maxSalary = minSalary;
                    salary = "Dưới " + format.format(minSalary)+" VNĐ";
                } catch (Exception e) {
                    System.out.println("duoi:" + salary);
                    minSalary = 10000000;
                    maxSalary = minSalary;
                    salary = "Dưới " + format.format(minSalary)+" VNĐ";
                    e.printStackTrace();
                }
            } else {
                String itemSalarys[] = salary.split("-");
                try {
                    minSalary = Double.parseDouble(itemSalarys[0].trim()) * 23500;
                    maxSalary = Double.parseDouble(itemSalarys[1].trim()) * 23500;
                    salary = format.format(minSalary)+" VNĐ" + " - " + format.format(maxSalary)+" VNĐ";
                } catch (Exception e) {
                    System.err.println("USD:" + salary);
                    minSalary = 10000000;
                    maxSalary = 10000000;
                    salary = "Trên 10,000,000 VNĐ";
                }
            }
        } else if (salary.indexOf("TRÊN") >= 0) {
            salary = salary.replaceAll("TRÊN", "").trim();
            if (salary.indexOf("HOA HỒNG") >= 0) {
                salary = salary.substring(0, salary.indexOf("(") - 1).trim();
            }
            try {
                minSalary = Double.parseDouble(salary);
                maxSalary = minSalary;
                salary = "Trên " + format.format(minSalary)+" VNĐ";
            } catch (Exception e) {
                System.out.println("Tren:" + salary);
                minSalary = 10000000;
                maxSalary = minSalary;
                salary = "Trên " + format.format(minSalary)+" VNĐ";
                e.printStackTrace();
            }
        } else if (salary.indexOf("DƯỚI") >= 0) {
            salary = salary.replaceAll("DƯỚI", "").trim();
            try {
                minSalary = Double.parseDouble(salary);
                maxSalary = minSalary;
                salary = "Dưới " + format.format(minSalary)+" VNĐ";
            } catch (Exception e) {
                System.out.println("duoi:" + salary);
                minSalary = 10000000;
                maxSalary = minSalary;
                salary = "Dưới " + format.format(minSalary)+" VNĐ";
                e.printStackTrace();
            }
        } else if (salary.indexOf("HOA HỒNG") >= 0) {
            salary = salary.substring(0, salary.indexOf("(") - 1).trim();
            //System.out.println(salary);
            String itemSalarys[] = salary.split("-");
            try {
                minSalary = Double.parseDouble(itemSalarys[0].trim());
                maxSalary = Double.parseDouble(itemSalarys[1].trim());
                if (minSalary < 1000) {
                    minSalary = minSalary * 1000000;
                }
                if (maxSalary < 1000) {
                    maxSalary = maxSalary * 1000000;
                }
                salary = format.format(minSalary)+" VNĐ" + " - " + format.format(maxSalary)+" VNĐ";
            } catch (Exception e) {
                System.out.println("hoa hong:" + salary);
                minSalary = 10000000;
                maxSalary = minSalary;
                salary = "Trên " + format.format(minSalary)+" VNĐ";
                e.printStackTrace();
            }
        } else {
            if (salary == null | salary.equals("") | salary.equals("CẠNH TRANH")) {
                minSalary = 10000000;
                maxSalary = minSalary;
                salary = "Cạnh tranh";
            } else {
//                String min = salary.substring(0, salary.indexOf("-"));
//                String max = salary.substring(salary.lastIndexOf("-")+1, salary.length());
                String min = "";
                String max = "";
                salary = salary.replaceAll(" ", "");
                for(int i =0 ;i<salary.length();i++){
                    char c = salary.charAt(i);
                    if(c>=48&c<=57){
                        min+=c;
                    }else{
                        min+="-";
                    }
                }
                String fake = min;
                String itemSalarys []= min.split("-");
                try {
                    minSalary = Double.parseDouble(itemSalarys[0].trim());
                    maxSalary = Double.parseDouble(itemSalarys[1].trim());
                    if (minSalary < 1000) {
                        minSalary = minSalary * 1000000;
                    }
                    if (maxSalary < 1000) {
                        maxSalary = maxSalary * 1000000;
                    }
                    salary = format.format(minSalary)+" VNĐ" + " - " + format.format(maxSalary)+" VNĐ";
                } catch (Exception e) {
                    System.err.println("binh thuong:" + salary.replaceAll("-", " "));
                    minSalary = 10000000;
                    maxSalary = minSalary;
                    salary = "Trên " + format.format(minSalary)+" VNĐ";
                    //e.printStackTrace();
                }
            }
        }

        if (degree == null | degree.trim().equals("")) {
            degree = "Không yêu cầu";
        }
        expire.replaceAll("-", "/");
    }

    public void show() {
        System.out.println("Nhan de: " + titleJob);
        System.out.println("Ten cong ty: " + companyName);
        System.out.println("Dia chi cong ty: " + address);
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
        System.out.println("logo: " + logo);
        System.out.println("linkRoot: " + linkRoot);
        System.out.println("Mức lương thấp nhất: " + minSalary);
        System.out.println("Mức lương cao nhất: " + maxSalary);
        System.out.println("------------------------------");
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

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLinkRoot() {
        return linkRoot;
    }

    public void setLinkRoot(String linkRoot) {
        this.linkRoot = linkRoot;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

}
