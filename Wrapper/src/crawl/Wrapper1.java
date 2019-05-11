/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import wrapper.Work;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;


/**
 *
 * @author Xuan Truong PC thu thập dữ liệu từ trang web:
 * https://www.timviecnhanh.com/viec-lam-ha-noi-2.html
 */
public class Wrapper1 {
    public static final String _hostname = "localhost";
    public static final int _port = 27017;
    public static final String dbname = "test";
    
    public String getCareer(String input) {
        String output = "";
        if (input.indexOf("Kinh doanh") > 0 | input.indexOf("Bán hàng") > 0 | input.indexOf("Marketing") > 0 | input.indexOf("PR") > 0) {
            return "Kinh doanh";
        } else if (input.indexOf("Hành chinh/Thư ký/Trợ lý") > 0 | input.indexOf("Tài chính/Kế toán/Kiểm toán") > 0 | input.indexOf("Nhân sự") > 0 | input.indexOf("Xuất-nhập khẩu/Ngoại thương") > 0 | input.indexOf("Lễ tân") > 0) {
            return "Hành chính - Kế toán";
        } else if (input.indexOf("Công nghệ thông tin") > 0) {
            return "Công nghệ thông tin";
        } else if (input.indexOf("Điện") > 0) {
            return "Điện/Điện tử";
        } else if (input.indexOf("Vận tải") > 0 | input.indexOf("Tài xế") > 0) {
            return "Vận tải";
        } else if (input.indexOf("Cơ khí") > 0) {
            return "Cơ khí";
        } else if (input.indexOf("Báo chí/Biên tập viên") > 0) {
            return "Báo chí";
        } else if (input.indexOf("Sản xuất") > 0) {
            return "Sản xuất";
        } else if (input.indexOf("An ninh") > 0) {
            return "An ninh";
        } else {
            return "Khác";
        }
        //return output;
    }

    public Work getWork(String link) {
        Work work = new Work();
        work.setLinkRoot(link);
        try {
            InputStream input = new URL(link).openStream();
            Document doc = Jsoup.parse(input, "utf-8", link);
            String titleJob = doc.selectFirst("h1.title span").text();
            String companyName = doc.selectFirst("h3 a").text();
            String companyAddress = doc.selectFirst("h3 a").parent().siblingElements().get(0).text();
            String companyLink = doc.selectFirst("h3 a").attr("href");
            String area = "Hà Nội";
            Elements details = doc.select("ul.no-style li");
            String salary = details.get(0).text().replace("triệu", "").split(":")[1];
            String experience = details.get(1).text().split(":")[1];
            String career = this.getCareer(details.get(4).text());
            String quantity = details.get(5).text().split(":")[1];
            String feature = details.get(7).text().split(":")[1];
            String form = details.get(8).text().split(":")[1];
            String degree = details.get(2).text().split(":")[1];
            String expire = doc.selectFirst("td b.text-danger").text();
            String gender = details.get(6).text().split(":")[1];

            Element cover = doc.selectFirst("#left-content");
            //Thong tin khac
            Elements table = cover.select("article.block-content table tbody tr");
            String description = table.get(0).child(1).child(0).html();
            String required = table.get(1).child(1).child(0).html();
            String interest = table.get(2).child(1).child(0).html();

            //Thong tin lien he
            Elements infoContact = cover.select("article.block-content div.block-info-company div.block-content table tbody tr");
            String contactName = infoContact.get(0).child(1).text();
            String contactAddress = infoContact.get(1).child(1).text();

            //Lấy thông tin của công ty
            doc = Jsoup.parse(new URL(companyLink).openStream(), "UTF-8", companyLink);
            companyLink = "";
            try {
                if (doc.selectFirst(".title-employer") != null) {
                    Elements relatives = doc.selectFirst(".title-employer").siblingElements();
                    for(Element re:relatives){
                        String text = re.text();
                        if(text.startsWith("Website:")){
                            companyLink = text.replaceAll("Website:", "");
                        }
                    }
                    if(companyLink.equals("")){
                        companyLink = "#";
                    }
                }
            }catch(Exception ex){
                companyLink = "#";
                ex.printStackTrace();
            }
            String logo = "";
            try {
                logo = doc.selectFirst("a.logo-company img").attr("src");
            } catch (Exception e) {
            }

            //Kiem tra thong tin
//            System.out.println("Nhan de: " + titleJob);
//            System.out.println("Ten cong ty: " + companyName);
//            System.out.println("Dia chi cong ty: " + companyAddress);
//            System.out.println("Link cong ty: " + companyLink);
//            System.out.println("khu vuc: " + area);
//            System.out.println("Muc luong: " + salary);
//            System.out.println("kinh nghiem: " + experience);
//            System.out.println("trinh do: " + degree);
//            System.out.println("nganh nghe: " + career);
//            System.out.println("so luong: " + quantity);
//            System.out.println("Tinhs chat: " + feature);
//            System.out.println("hinh thuc: " + form);
//            System.out.println("Mo ta: " + description);
//            System.out.println("yeu cau: " + required);
//            System.out.println("Quyen loi: " + interest);
//            System.out.println("nguoi lien he: " + contactName);
//            System.out.println("dia chi lien he: " + contactAddress);
//            System.out.println("Han nop: " + expire);
//            System.out.println("gioi tinh: " + gender);
//            System.out.println("-------------------------------");

            //Gan gia tri
            work.setDegree(degree);
            work.setAddress(companyAddress);
            work.setArea(area);
            work.setCareer(career);
            work.setCompanyName(companyName);
            work.setContactAddress(contactAddress);
            work.setContactName(contactName);
            work.setDescription(description);
            work.setExperience(experience);
            work.setExpire(expire);
            work.setFeatureJob(feature);
            work.setForm(form);
            work.setGender((gender.equals("Nam")) ? 1 : (gender.equals("Nữ")) ? 0 : 2);
            work.setInterest(interest);
            work.setQuantity(Integer.parseInt(quantity.trim()));
            work.setRequired(required);
            work.setSalary(salary);
            work.setTitleJob(titleJob);
            work.setCompanyLink(companyLink);
            work.setLogo(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return work;
    }

    public void crawlData() {
        String linkRoot = "https://www.timviecnhanh.com/viec-lam-ha-noi-2.html";
        String pathFile = "./dataWebpage1.xml";
        OutputStreamWriter writer = null;
        InputStream inputStream = null;
        String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<works>";
        String xml = "";
        String xmlEnd = "\r\n</works>";
        Document doc = null;
        
        Work work = null;
        int count = 0;
        int numFile = 1;
        try {
            //Mở kết nối
            MongoClient mongoClient = new MongoClient(new ServerAddress(_hostname,_port).toString());
            MongoCollection<org.bson.Document> mongoColection = (MongoCollection<org.bson.Document>) mongoClient.getDatabase(dbname).getCollection("dataCrawl3");
            //Mở file ghi kết quả crawl
//            writer = new OutputStreamWriter(new FileOutputStream(new File(pathFile)), "UTF-8");
//            writer.write(xmlStart);
            //Mở tài liệu crawl
            inputStream = new URL(linkRoot).openStream();
            doc = Jsoup.parse(inputStream, "utf-8", linkRoot);
            //Xử lí tài liệu crawl
            //lấy ra các link trỏ sang chi tiết tuyển dụng
            Elements linkDetails = doc.select("td.block-item a.item");
            //Xử lí từng link một
            for (Element linkDetail : linkDetails) {
                try {
                    work = this.getWork(linkDetail.attr("href"));
                    if (work != null) {
                        work.toString(null);
                           mongoColection.insertOne(work.getDocument());
//                        xml = work.convertToXMLObject();
//                        writer.write(xml);
//                        count++;
//                        if (count % 500 == 0) {
//                            writer.write(xmlEnd);
//                            writer.flush();
//                            writer.close();
//                            String path = "./dataWebpage1_" + numFile + ".xml";
//                            writer = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
//                            writer.write(xmlStart);
//                            numFile++;
//                            count = 0;
//                        }
                    } else {
                        continue;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    continue;
                }
            }

            int k = 2;
            while (k < 79) {
                try {
                    linkRoot = "https://www.timviecnhanh.com/viec-lam-ha-noi-2.html?page=" + k;
                    k++;
                    inputStream = new URL(linkRoot).openStream();
                    doc = Jsoup.parse(inputStream, "utf-8", linkRoot);
                    linkDetails = doc.select("td.block-item a.item");
                    //Xử lí từng link một
                    for (Element linkDetail : linkDetails) {
                        try {
                            work = this.getWork(linkDetail.attr("href"));
                            if (work != null) {
                                work.toString(null);
                                mongoColection.insertOne(work.getDocument());
                            } else {
                                continue;
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            continue;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    continue;
                }
            }
//            writer.write(xmlEnd);
//            writer.flush();
//            writer.close();
            mongoClient.close();
            System.out.println("Tong so dau viec la: " + (count + (numFile - 1) * 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
