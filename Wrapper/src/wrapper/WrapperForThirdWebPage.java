/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
/**
 * webPage3 : https://vieclam24h.vn
 *
 * @author Xuan Truong PC
 */
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WrapperForThirdWebPage {

    private String[] links = {
        //"https://vieclam24h.vn/viec-lam-quan-ly", 
        "https://vieclam24h.vn/viec-lam-chuyen-mon"
    //"https://vieclam24h.vn/viec-lam-lao-dong-pho-thong",
    //"https://vieclam24h.vn/viec-lam-sinh-vien-ban-thoi-gian"
    };

    private Vector<String> linkJobs = new Vector<String>();

    private Vector<Work> works = new Vector<Work>();

    public void crawlData() {
        InputStream inStream = null;
        Document doc = null;
        OutputStreamWriter writer = null;
        String linkRoot = "";
        String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<works>";
        String xml = "";
        String xmlEnd = "\r\n</works>";
        Work work = null;
        int count = 0;
        int numFile = 4;
        int jump = 0;
        try {
            Scanner scan = new Scanner(System.in);
            writer = new OutputStreamWriter(new FileOutputStream(new File("./viec_lam_chuyen_mon3.xml")), "UTF-8");
            writer.write(xmlStart);
            for (int i = 0; i < this.links.length; i++) {
                linkRoot = this.links[i];
                System.out.println("Link gốc: " + linkRoot);
                try {
                    inStream = new URL(linkRoot).openStream();
                    doc = Jsoup.parse(inStream, "UTF-8", linkRoot);
                    //Lấy các chủ đề - các ngành nghề tìm kiếm
                    Elements subjects = doc.select("#gate_nganhnghe_hot_menu_right div.list-items div.news-title");
                    for (int j=jump;j<subjects.size();j++) {
                        Element el = subjects.get(j);
                        try {
                            if (!el.hasClass("hidden")) {
                                String subject = el.child(0).attr("href");
                                if (!subject.startsWith("https://vieclam24h.vn")) {
                                    subject = "https://vieclam24h.vn" + subject;
                                }
                                //Đi tới các trang chủ đề
                                inStream = new URL(subject).openStream();
                                doc = Jsoup.parse(inStream, "UTF-8", subject);

                                System.out.println("link theo chu de: " + subject);
                                //Lấy ra các link thông tin về việc làm
                                Elements els = doc.select("span.title-blockjob-main a");
                                //div.list-items div.detail-link div.content_list_item_line span.title-blockjob-main a
                                //Duyệt từng link chi tiết công việc
                                for (Element ele : els) {
                                    String title = ele.text();
                                    String link = ele.attr("href");
                                    if (!link.startsWith("https://vieclam24h.vn")) {
                                        link = "https://vieclam24h.vn" + link;
                                    }
                                    System.out.println(title + ": " + link);
                                    //Phân tách thông tin
                                    work = getWork(link);
                                    if (work == null) {
                                        continue;
                                    }
                                    xml = work.convertToXMLObject();
                                    writer.write(xml);
                                    count++;
                                    if (count % 500 == 0) {
                                        writer.write(xmlEnd);
                                        writer.flush();
                                        writer.close();
                                        String path = "./viec_lam_chuyen_mon" + numFile + ".xml";
                                        writer = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
                                        writer.write(xmlStart);
                                        numFile++;
                                        count = 0;
                                    }
                                }

                                //Lấy tiếp các thành phần được phân trang
                                boolean flag = true;
                                int numNext = 2;
                                while (numNext<=50) {
                                    String nextPage = subject + "?page=" + numNext;
                                    System.out.println("link theo chu de: " + nextPage);
                                    System.out.print("Đồng ý lấy trang này: ");
                                    //String condition = scan.nextLine();
                                    String condition = "y";
                                    if (condition.equals("y")|| condition.equals("Y")) {
                                        numNext++;
                                        inStream = new URL(nextPage).openStream();
                                        doc = Jsoup.parse(inStream, "UTF-8", nextPage);
                                        els = doc.select("div.list-items div.detail-link div.content_list_item_line span.title-blockjob-main a");
                                        if(els.size() == 0){
                                            break;
                                        }
                                        for (Element ele : els) {
                                            String title = ele.text();
                                            String link = ele.attr("href");
                                            if (!link.startsWith("https://vieclam24h.vn")) {
                                                link = "https://vieclam24h.vn" + link;
                                            }
                                            System.out.println(title + ": " + link);
                                            //Phân tách thông tin
                                            work = getWork(link);
                                            if (work == null) {
                                                continue;
                                            }
                                            xml = work.convertToXMLObject();
                                            writer.write(xml);
                                            count++;
                                            if (count % 500 == 0) {
                                                writer.write(xmlEnd);
                                                writer.flush();
                                                writer.close();
                                                String path = "./viec_lam_chuyen_mon" + numFile + ".xml";
                                                writer = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
                                                writer.write(xmlStart);
                                                numFile++;
                                                count = 0;
                                            }
                                        }
                                    } else {
                                        numNext = 2;
                                        flag = false;
                                    }
                                }
                            }
                        } catch (Exception ex1) {
                            ex1.printStackTrace();
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Link bi loi: " + linkRoot);
                    e.printStackTrace();
                    continue;
                }
            }
            writer.write(xmlEnd);
            writer.flush();
            writer.close();
            System.out.println("Tổng số đầu việc: " + ((numFile - 1) * 500 + count));
        } catch (Exception ex) {
            ex.printStackTrace();
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
            String salary = cover.selectFirst("i.icon-money").siblingElements().get(0).child(0).text();
            String experience = cover.selectFirst("i.icon-exp").siblingElements().get(0).child(0).text();
            String degree = cover.selectFirst("i.icon-edu").siblingElements().get(0).child(0).text();
            String area = cover.selectFirst("i.icon-address").siblingElements().get(0).child(0).text();
            String career = cover.selectFirst("div.job_detail div div h2 a").text();
            String quantity = cover.selectFirst("i.icon-quantity").siblingElements().get(0).child(0).text();
            String gender = cover.selectFirst("i.icon-gender").siblingElements().get(0).child(0).text();
            String feature = cover.selectFirst("i.icon-job-type").siblingElements().get(0).child(0).text();
            String form = cover.selectFirst("i.icon-position").siblingElements().get(0).child(0).text();

            //Nhưng thông tin liên quan tới tuyển dụng
            String description = doc.select("p.word_break").get(0).text();
            String required = doc.select("p.word_break").get(2).text();
            String interest = doc.select("p.word_break").get(1).text();
            String expire = cover.selectFirst("i.icon-countdown").siblingElements().get(0).child(0).text();

            //Thông tin liên hệ
            Element table = doc.select("div.job_description").get(1);
            String contactName = table.select("div.item").get(1).child(1).text();
            String contactAddress = table.select("div.item").get(2).child(1).text();

            //Kiểm tra lại thông tin thu được
            System.out.println("Nhan de: " + titleJob);
            System.out.println("Ten cong ty: " + companyName);
            System.out.println("Dia chi cong ty: " + companyAddress);
            System.out.println("Link cong ty: " + companyLink);
            System.out.println("khu vuc: " + area);
            System.out.println("Muc luong: " + salary);
            System.out.println("kinh nghiem: " + experience);
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
            work.setGender((gender == "Nam") ? 1 : 0);
            work.setInterest(interest);
            work.setQuantity(Integer.parseInt(quantity.trim()));
            work.setRequired(required);
            work.setSalary(salary);
            work.setTitleJob(titleJob);
            work.setCompanyLink(companyLink);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Link bị lỗi: " + link);
            work = null;
        }
        return work;
    }
}
