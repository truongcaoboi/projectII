/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
 * @author Xuan Truong PC crawl data from website: https://careerbuilder.vn/
 */
public class Wrapper3 {

    public static final String _hostname = "localhost";
    public static final int _port = 27017;
    public static final String dbname = "test";

    public String getCareer(String input) {
        if (input.indexOf("Kinh doanh") >= 0 | input.indexOf("Bán hàng") >= 0 | input.indexOf("Marketing") >= 0 | input.indexOf("Bán lẻ") >= 0 | input.indexOf("Bán sỉ") >= 0) {
            return "Kinh doanh";
        } else if (input.indexOf("Thống kê") >= 0 | input.indexOf("Hành chinh") >= 0 | input.indexOf("Tài chính") >= 0 | input.indexOf("Nhân sự") >= 0 | input.indexOf("Quản lý điều hành") >= 0 | input.indexOf("Kế toán") >= 0 | input.indexOf("Xuất nhập khẩu") >= 0) {
            return "Hành chính - Kế toán";
        } else if (input.indexOf("CNTT") >= 0 | input.indexOf("Phần cứng") >= 0 | input.indexOf("Phần mềm") >= 0 | input.indexOf("Quản lý chất lượng") >= 0) {
            return "Công nghệ thông tin";
        } else if (input.indexOf("Điện") >= 0) {
            return "Điện";
        } else if (input.indexOf("Vận chuyển") >= 0 | input.indexOf("Hàng hải") >= 0 | input.indexOf("Hàng không") >= 0 | input.indexOf("Ô tô") >= 0 | input.indexOf("Vận tải") >= 0 | input.indexOf("Giao vận") >= 0) {
            return "Vận tải";
        } else if (input.indexOf("Cơ khí") >= 0) {
            return "Cơ khí";
        } else if (input.indexOf("Biên tập") >= 0 | input.indexOf("In ấn/Xuất bản") >= 0 | input.indexOf("Truyền hình") >= 0 | input.indexOf("Báo chí") >= 0) {
            return "Báo chí";
        } else if (input.indexOf("Sản xuất") >= 0) {
            return "Sản xuất";
        } else if (input.indexOf("An ninh") >= 0) {
            return "An ninh";
        } else {
            return "Khác";
        }
    }

    public Work getWork(String link, String linkCom) {
        InputStream inStream = null;
        Document doc = null;
        Work work = new Work();
        work.setLinkRoot(link);
        try {
            inStream = new URL(link).openStream();
            doc = Jsoup.parse(inStream, "UTF-8", link);

            String titleJob = "";
            String companyName = "";
            String companyLink = "";
            String companyAddress = "";
            String area = "Hà Nội";
            String salary = "";
            String experience = "";
            String degree = "";
            String career = "";
            String quantity = "";
            String feature = "";
            String form = "";
            String description = "";
            String required = "";
            String interest = "";
            String contactName = "";
            String contactAddress = "";
            String expire = "";
            String gender = "";
            String logo = "";

            if (doc.selectFirst("ul.info") != null) {
                titleJob = doc.selectFirst("div.top-job-info h1").text();
                companyName = doc.selectFirst("div.top-job-info p").text();

                contactName = doc.selectFirst("i.fa-phone").text();
                contactAddress = doc.selectFirst("i.fa-map-marker").text();

                Elements infos = doc.select("ul.info li");
                for (Element info : infos) {
                    String text = info.text();
                    if (text.indexOf("Lương") >= 0) {
                        salary = text.replaceAll("Lương", "");
                        if (!salary.equals("Cạnh tranh")) {
                            salary = salary.replaceAll(",", "").replaceAll("VND", "").replaceAll(".", "").replaceAll("Tr", "").replaceAll("Triệu", "").replaceAll("triệu", "").replaceAll("tr", "");
                        }
                    } else if (text.indexOf("Ngành nghề") >= 0) {
                        career = this.getCareer(text.replaceAll("Ngành nghề", ""));
                    } else if (text.indexOf("Cấp bậc") >= 0) {
                        feature = text.replaceAll("Cấp bậc", "");
                    } else if (text.indexOf("Kinh nghiệm") >= 0) {
                        experience = text.replaceAll("Kinh nghiệm", "");
                    } else if (text.indexOf("Hết hạn nộp") >= 0) {
                        expire = text.replaceAll("Hết hạn nộp", "");
                    }
                }
            } else {
                try {
                    titleJob = doc.selectFirst("div.top-job-info h1").text();
                } catch (Exception ao) {
                    return null;
                }
                companyName = doc.selectFirst("div.top-job-info div").text();

                Elements contacts = doc.select("p.TitleDetailNew label");
                for (Element contact : contacts) {
                    String text = contact.text();
                    if (text.indexOf("Người liên hệ") >= 0) {
                        contactName = text.split(":")[1];
                    }
                }
                contactAddress = doc.select("p.TitleDetailNew label").get(0).text();

                Elements infos = doc.select("ul.DetailJobNew li p");
                for (Element info : infos) {
                    String text[] = info.text().split(":");
                    if (text[0].indexOf("Lương") >= 0) {
                        salary = text[1];
                        if (!salary.equals("Cạnh tranh")) {
                            salary = salary.replaceAll(",", "").replaceAll("VND", "");
                        }
                    } else if (text[0].indexOf("Ngành nghề") >= 0) {
                        career = this.getCareer(text[1]);
                    } else if (text[0].indexOf("Cấp bậc") >= 0) {
                        feature = text[1];
                    } else if (text[0].indexOf("Kinh nghiệm") >= 0) {
                        experience = text[1];
                    } else if (text[0].indexOf("Hết hạn nộp") >= 0) {
                        expire = text[1];
                    }
                }
            }
            Elements benifits = doc.select("ul.list-benefits li");
            for (Element benifit : benifits) {
                interest += benifit.text() + "<br>";
            }

            Elements descriptions = doc.select("div.content_fck").get(0).select("p");
            for (Element descrip : descriptions) {
                description += descrip.text() + "<br>";
            }

            Elements requireds = doc.select("div.content_fck").get(1).select("p");
            for (Element req : requireds) {
                required += req.text() + "<br>";
            }

            Elements others = doc.select("div.content_fck").get(2).select("ul li");
            for (Element other : others) {
                String text[] = other.text().split(":");
                if (text[0].indexOf("Bằng cấp") >= 0) {
                    degree = text[1];
                } else if (text[0].indexOf("Hình thức") >= 0) {
                    form = text[1];
                }
            }
            //Lấy thông tin về công ty
            try {
                doc = Jsoup.parse(new URL(linkCom).openStream(), "UTF-8", linkCom);
                companyName = doc.selectFirst("div.title h2").text();
                companyAddress = doc.select("div.title p").get(0).text().split(":")[1];
                try {
                    logo = doc.selectFirst("div.logo table tbody tr td img").attr("src");
                } catch (Exception e) {
                }
                Elements relatives = doc.select("div.title p span");
                for (Element relative : relatives) {
                    String text = relative.text();
                    if (text.startsWith("Website")) {
                        companyLink = text.replaceAll("Website:", "").trim();
                    }
                }
            } catch (Exception e) {
                System.err.println(linkCom);

                companyName = doc.selectFirst("div.cp_basic_info_details h2").text();
                companyAddress = doc.select("div.cp_basic_info_details ul li").get(0).text();
                Elements relatives = doc.select("div.cp_basic_info_details ul li");
                for (Element relative : relatives) {
                    String text = relative.text();
                    if (text.startsWith("Website")) {
                        companyLink = text.replaceAll("Website:", "").trim();
                    }
                }
            }

            if (companyLink.equals("")) {
                companyLink = "#";
            }

            quantity = "0";

            //Kiểm tra lại thông tin thu được
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
        String linkRoot = "https://careerbuilder.vn/viec-lam/ha-noi-l4-trang-1-vi.html";
        InputStream input = null;
        OutputStreamWriter writer = null;
        Document doc = null;
        String pathFile = "./dataWebpage3.xml";

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

            while (k < 201) {
                try {
                    String linkChild = "https://careerbuilder.vn/viec-lam/ha-noi-l4-trang-" + k + "-vi.html";
                    input = new URL(linkChild).openStream();
                    doc = Jsoup.parse(input, "UTF-8", linkChild);
                    //Lấy ra các link chi tiết công việc
                    Elements linkDetails = doc.select("span.jobtitle h3.job a");
                    for (Element linkDetail : linkDetails) {
                        String link = linkDetail.attr("href");
                        String linkCom = linkDetail.parent().parent().selectFirst("p.namecom a").attr("href");
                        work = this.getWork(link, linkCom);
                        if (work != null) {
                            work.toString(null);
                            mongoColection.insertOne(work.getDocument());
                            c++;
                        } else {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    k++;
                    continue;
                }
                k++;
            }
            mongoClient.close();
            System.out.println("Tong so dau viec: " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
