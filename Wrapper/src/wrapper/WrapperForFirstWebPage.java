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
/**
 * webPage1 : https://www.timviecnhanh.com
 *
 * @author Xuan Truong PC
 */

import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WrapperForFirstWebPage {

    private String[] links = {
        "https://www.timviecnhanh.com/vieclam24h",
        "https://www.timviecnhanh.com/vieclam"
    };

    private Vector<String> linkJobs = new Vector<String>();

    private Vector<Work> works = new Vector<Work>();

    private void getLinkJobs() {
        InputStream inStream = null;
        Document doc = null;
        try {
            for (int i = 0; i < this.links.length; i++) {
                inStream = new URL(this.links[i]).openStream();
                doc = Jsoup.parse(inStream, "UTF-8", this.links[i]);
                int numPageNavi = doc.select("div.page-navi a").size();
                for (int k = 1; k <= numPageNavi - 2; k++) {
                    String link = this.links[i] + "?page=" + k;
                    inStream = new URL(link).openStream();
                    doc = Jsoup.parse(inStream, "UTF-8", link);
                    Elements ATags = doc.select("div.job-attractive article div").get(2).select("table tbody tr td a.item");
                    for (Element element : ATags) {
                        String linkJob = element.attr("href");
                        System.out.println(linkJob);
                        this.linkJobs.add(linkJob);
                    }
                }
                //System.out.println(this.linkJobs.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLinkJobOfCareer() {
        try {
            InputStream inStream = new URL("https://www.timviecnhanh.com").openStream();
            Document doc = Jsoup.parse(inStream, "UTF-8", "https://www.timviecnhanh.com");
            Elements links = doc.select("#field-hot-content ul li a");
            for (Element element : links) {
                String career = element.attr("href");
                inStream = new URL(career).openStream();
                doc = Jsoup.parse(inStream, "UTF-8", career);
                Elements els = doc.select("table.table-content tbody tr td a");
                for (Element el : els) {
                    String link = el.attr("href");
                    System.out.println(link);
                    this.linkJobs.add(link);
                }
            }
        } catch (Exception e) {
        }
    }

    public Vector<Work> getWorks() {
        this.getLinkJobs();
        this.getLinkJobOfCareer();
        System.out.println("Luowng link: " + this.linkJobs.size());
        InputStream inStream = null;
        String link = "";

        try {
            int count = 0;
            int numFile = 1;
            OutputStreamWriter writer = null;
            String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<works>";
            String xml = "";
            String xmlEnd = "\r\n</works>";
            writer = new OutputStreamWriter(new FileOutputStream(new File("./dataFirstPage.xml")), "UTF-8");
            writer.write(xmlStart);
            Work work = null;
            Document doc = null;
            for (int i = 0; i < this.linkJobs.size(); i++) {
                try {
                    work = new Work();
                    link = this.linkJobs.get(i);
                    inStream = new URL(link).openStream();
                    doc = Jsoup.parse(inStream, "UTF-8", link);
                    Element cover = doc.selectFirst("#left-content");
                    String titleJob = doc.selectFirst("h1.title span").text();
                    Element company = doc.selectFirst("#left-content article div.offset10 h3 a");
                    String companyName = company.text();
                    String companyLink = company.attr("href");
                    String companyAddress = company.parent().siblingElements().get(0).text();

                    //Thong tin vee cong viec
                    Elements info = cover.select("article.block-content div.row div ul li");
                    String salary = info.get(0).text().split(":")[1];
                    String experience = info.get(1).text().split(":")[1];
                    String degree = info.get(2).text().split(":")[1];
                    String area = info.get(3).text().split(":")[1];
                    String career = info.get(4).text().split(":")[1];
                    String quantity = info.get(5).text().split(":")[1];
                    String gender = info.get(6).text().split(":")[1];
                    String feature = info.get(7).text().split(":")[1];
                    String form = info.get(8).text().split(":")[1];

                    //Thong tin khac
                    Elements table = cover.select("article.block-content table tbody tr");
                    String description = table.get(0).child(1).text();
                    String required = table.get(1).child(1).text();
                    String interest = table.get(2).child(1).text();
                    String expire = table.get(3).child(1).text();

                    //Thong tin lien he
                    Elements infoContact = cover.select("article.block-content div.block-info-company div.block-content table tbody tr");
                    String contactName = infoContact.get(0).child(1).text();
                    String contactAddress = infoContact.get(1).child(1).text();

                    //Kiem tra thong tin
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

                    //Gan gia tri
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
                    //Add vaof list
                    xml = work.convertToXMLObject();
                    writer.write(xml);
                    count++;
                    if (count % 500 == 0) {
                        writer.write(xmlEnd);
                        writer.flush();
                        writer.close();
                        String path = "./dataFirstPage" + numFile + ".xml";
                        writer = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
                        writer.write(xmlStart);
                        numFile++;
                        count = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            writer.write(xmlEnd);
            writer.flush();
            writer.close();
            System.out.println("Luong dau viec: "+((numFile-1)*500+count));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.works;
    }
}
