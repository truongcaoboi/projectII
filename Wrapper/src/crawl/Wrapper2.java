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
import static crawl.Wrapper1._hostname;
import static crawl.Wrapper1._port;
import static crawl.Wrapper1.dbname;

/**
 *
 * @author Xuan Truong PC crawl data from website: https://vieclam24h.vn
 */
public class Wrapper2 {

    public static final String _hostname = "localhost";
    public static final int _port = 27017;
    public static final String dbname = "test";

    public String getCareer(String input) {
        if (input.indexOf("kinh doanh") > 0 | input.indexOf("Bán hàng") > 0 | input.indexOf("Marketing") > 0 | input.indexOf("PR") > 0 | input.indexOf("Tiếp thị") > 0 | input.indexOf("Thương mại") > 0) {
            return "Kinh doanh";
        } else if (input.indexOf("Hành chinh") > 0 | input.indexOf("Tài chính") > 0 | input.indexOf("Nhân sự") > 0 | input.indexOf("Ngoại thương") > 0 | input.indexOf("Kế toán") > 0 | input.indexOf("Trợ lý") > 0) {
            return "Hành chính - Kế toán";
        } else if (input.indexOf("IT") > 0) {
            return "Công nghệ thông tin";
        } else if (input.indexOf("Điện") > 0) {
            return "Điện";
        } else if (input.indexOf("Vận tải") > 0 | input.indexOf("Ô tô") > 0) {
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
    }

    public Work getWork(String link) {
        InputStream inStream = null;
        Document doc = null;
        Work work = new Work();
        try {
            inStream = new URL(link).openStream();
            doc = Jsoup.parse(inStream, "UTF-8", link);

            Element cover = doc.selectFirst("div.box_chi_tiet_cong_viec");
            String titleJob = doc.selectFirst("h1.title_big").text();
            Element company = doc.selectFirst("h1.title_big").siblingElements().get(0).child(0);
            String companyName = company.text();
            String companyLink = company.attr("href");
            String companyAddress = "";

            //Thông tin về công việc
            String salary = cover.selectFirst("i.icon-money").siblingElements().get(0).child(0).text().replaceAll("triệu", "");
            String experience = cover.selectFirst("i.icon-exp").siblingElements().get(0).child(0).text();
            String degree = cover.selectFirst("i.icon-edu").siblingElements().get(0).child(0).text();
            //String area = cover.selectFirst("i.icon-address").siblingElements().get(0).child(0).text();
            String area = "Hà Nội";
            String career = this.getCareer(cover.selectFirst("div.job_detail div div h2").text().split(":")[1]);
            String quantity = cover.selectFirst("i.icon-quantity").siblingElements().get(0).child(0).text();
            String gender = cover.selectFirst("i.icon-gender").siblingElements().get(0).child(0).text();
            String feature = cover.selectFirst("i.icon-job-type").siblingElements().get(0).child(0).text();
            String form = cover.selectFirst("i.icon-position").siblingElements().get(0).child(0).text();

            //Nhưng thông tin liên quan tới tuyển dụng
            String description = doc.select("p.word_break").get(0).html();
            String required = doc.select("p.word_break").get(2).html();
            String interest = doc.select("p.word_break").get(1).html();
            String expire = cover.selectFirst("i.icon-countdown").siblingElements().get(0).child(0).text();

            //Thông tin liên hệ
            Element table = doc.select("div.job_description").get(1);
            String contactName = table.select("div.item").get(1).child(1).text();
            String contactAddress = table.select("div.item").get(2).child(1).text();

            //Lấy thông tin của công ty
            doc = Jsoup.parse(new URL(companyLink).openStream(), "UTF-8", companyLink);
            try {
                if (doc.selectFirst(".icon-address-blue") != null) {
                    companyAddress = doc.selectFirst(".icon-address-blue").parent().text().replaceAll("Địa chỉ:", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (doc.selectFirst(".icon-website") != null) {
                    companyLink = doc.selectFirst(".icon-website").siblingElements().get(0).text().replaceAll("Website:", "");
                }else{
                    companyLink = "#";
                }
            } catch (Exception e) {
                companyLink = "#";
                System.err.println("link cong ty bi loi: " + companyLink);
            }
            String logo ="";
            try {
                logo = doc.selectFirst("div.logo-company img").attr("src");
            } catch (Exception e) {
            }

            //Kiểm tra lại thông tin thu được
            System.out.println("Nhan de: " + titleJob);
            System.out.println("Ten cong ty: " + companyName);
            System.out.println("Dia chi cong ty: " + companyAddress);
            System.out.println("Link cong ty: " + companyLink);
            System.out.println("khu vuc: " + area);
            System.out.println("Muc luong: " + salary);
            System.out.println("kinh nghiem: " + experience);
            System.out.println("trinh do: " + degree);
            System.out.println("nganh nghe: " + career);
            System.out.println("so luong: " + quantity);
            System.out.println("Tinhs chat: " + feature);
            System.out.println("hinh thuc: " + form);
            System.out.println("Mo ta: " + description);
            System.out.println("yeu cau: " + required);
            System.out.println("Quyen loi: " + interest);
            System.out.println("nguoi lien he: " + contactName);
            System.out.println("dia chi lien he: " + contactAddress);
            System.out.println("Han nop: " + expire);
            System.out.println("gioi tinh: " + gender);
            System.out.println("-------------------------------");
            //Gán giá trị cho đối tượng việc làm
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
            work.setDegree(degree);
            work.setGender((gender == "Nam") ? 1 : 0);
            work.setInterest(interest);
            work.setQuantity(Integer.parseInt(quantity.trim()));
            work.setRequired(required);
            work.setSalary(salary);
            work.setTitleJob(titleJob);
            work.setCompanyLink(companyLink);
            work.setLogo(logo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Link bị lỗi: " + link);
            work = null;
        }
        return work;
    }

    public void crawlData() {
        String linkRoot = "https://vieclam24h.vn/ha-noi-p2.html?page=";
        InputStream input = null;
        OutputStreamWriter writer = null;
        Document doc = null;
        String pathFile = "./dataWebpage2.xml";

        String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<works>";
        String xml = "";
        String xmlEnd = "\r\n</works>";
        int numFile = 1;
        int count = 0;
        int c = 0;
        Work work = null;
        try {
            //Mở kết nối
            MongoClient mongoClient = new MongoClient(new ServerAddress(_hostname, _port).toString());
            MongoCollection<org.bson.Document> mongoColection = (MongoCollection<org.bson.Document>) mongoClient.getDatabase(dbname).getCollection("dataCrawl3");
            //Mở file ghi kết quả crawl
//            writer = new OutputStreamWriter(new FileOutputStream(new File(pathFile)), "UTF-8");
//            writer.write(xmlStart);
            int k = 1;
//            String linkStart = "https://vieclam24h.vn/viec-lam-quan-ly";
//            input = new URL(linkStart).openStream();
//            doc = Jsoup.parse(input, "UTF-8", linkStart);
//            linkRoot = doc.select("div.tinh_item a").get(1).attr("href") + "?pape=";
            while (k < 100) {
                String linkChild = linkRoot + k;
                input = new URL(linkChild).openStream();
                doc = Jsoup.parse(input, "UTF-8", linkChild);
                //Lấy ra các link chi tiết công việc
                Elements linkDetails = doc.select("span.title-blockjob-main a");
                for (Element linkDetail : linkDetails) {
                    String link = linkDetail.attr("href");
                    if (!link.startsWith("https://vieclam24h.vn")) {
                        link = "https://vieclam24h.vn" + link;
                    }
                    work = this.getWork(link);
                    if (work != null) {
                        mongoColection.insertOne(work.getDocument());
                    } else {
                        continue;
                    }
                }
                k++;
            }
//            writer.write(xmlEnd);
//            writer.flush();
//            writer.close();
            mongoClient.close();
            System.out.println("Tong so dau viec: " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
