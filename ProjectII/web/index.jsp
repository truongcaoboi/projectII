<%-- 
    Document   : index
    Created on : Apr 3, 2019, 11:18:31 AM
    Author     : Xuan Truong PC
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Vector"%>
<%@page import="model.Work"%>
<%@page import="org.bson.Document"%>
<%@page import="dataAccess.DataAccess"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <link href="CSS/indexCss.css" rel="stylesheet" type="text/css"/>
        <link rel="shortcut icon" type="image/png" href="CSS/content/favicon.png"/>
    </head>
    <body>
        <div class="banner" id="banner">
            <div class="frame">
                <ul class="list_banner">
                    <li class="item_banner first"><img src="CSS/content/banner1.jpg" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner2.jpg" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner3.png" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner4.jpg" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner5.png" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner6.png" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner7.jpg" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner9.jpg" alt=""/></li>
                    <li class="item_banner"><img src="CSS/content/banner10.png" alt=""/></li>
                    <li class="item_banner last"><img src="CSS/content/banner8.png" alt=""/></li>
                </ul>
            </div>
            <div  class="btn_left"></div>
            <div class="btn_right"></div>
        </div>
        <div class="body">
            <%
                MongoCollection<Document> mongoColection = new DataAccess().getConnection();
                Vector<Work> works = new Vector<Work>();
                Vector<Work> workSalarys = new Vector<Work>();
                Vector<Work> workExpires = new Vector<Work>();

                Date now = new Date();
                SimpleDateFormat output = new SimpleDateFormat("dd/mm/yyyy");
                String nowDate = output.format(now);
                
                int count=0;
                for (Document doc : mongoColection.find()) {
                    Work work = new Work();
                    work.setId(doc.get("_id").toString());
                    work.setTitleJob(doc.getString("titleJob"));
                    work.setCompanyName(doc.getString("companyName"));
                    work.setCompanyLink(doc.getString("conpanyLink"));
                    work.setAddress(doc.getString("companAddress"));
                    work.setArea(doc.getString("area"));
                    work.setCareer(doc.getString("career"));
                    work.setContactAddress(doc.getString("contactAddress"));
                    work.setContactEmail(doc.getString("contactEmail"));
                    work.setContactPhone(doc.getString("contactPhone"));
                    work.setDegree(doc.getString("degree"));
                    work.setDescription(doc.getString("description"));
                    work.setExperience(doc.getString("experience"));
                    work.setExpire(doc.getString("expire"));
                    work.setFeatureJob(doc.getString("featureJob"));
                    work.setForm(doc.getString("form"));
                    work.setGender(doc.getInteger("gender"));
                    work.setInterest(doc.getString("interest"));
                    //work.setId(id);
                    work.setQuantity(doc.getInteger("quantity"));
                    work.setRequired(doc.getString("required"));
                    work.setSalary(doc.getString("salary"));
                    work.setContactName(doc.getString("contactName"));
                    work.setLogo(doc.getString("logo"));

                    Date dateWork = output.parse(work.getExpire());
                    int sal = 0;
                    try {
                        sal = Integer.parseInt(work.getSalary().trim().substring(0, work.getSalary().trim().indexOf("-")).trim());
                    } catch (Exception e) {
                        sal = 0;
                    }
                    
                    works.add(work);
                    count++;
//                    if(count==20){
//                        break;
//                    }

//                    if (work.getExpire().equals(nowDate)) {
//                        workExpires.add(work);
//                    } else if (sal >= 10000000) {
//                        workSalarys.add(work);
//                    } else if (dateWork.getTime() >= now.getTime()) {
//                        works.add(work);
//                    }
                    //out.print(works.size());
                    request.getSession().setAttribute("works", works);
                    request.getSession().setAttribute("workSalarys", workSalarys);
                    request.getSession().setAttribute("workExpires", workExpires);
                }
            %>
            <div class="part_left">
                <div class="box_login">
                    <form class="form_login">
                        <table cellpadding = 5 cellspacing = 5>
                            <caption><h2>Đăng nhập</h2></caption>
                            <tr>
                                <td>Tên đăng nhập <span>(*)</span></td>
                                <td>
                                    <input type="text" name="username" maxlength="100" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <td>Mật khẩu <span>(*)</span></td>
                                <td>
                                    <input type="password" name="password" maxlength="20">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align = "center"><input type="button" value="Đăng nhập">&nbsp;<input type="button" value="Đăng ký"></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div class="box_company">
                    <h2 align="center">Một sô doanh nghiệp</h2>
                    <div>
                        <%
                            for (int i = 0; i < 12; i++) {

                                Work work = works.get(i);
                                if (!work.getLogo().equals("")) {


                        %>
                        <div style="background-image: url('<%= work.getLogo()%>')" title="<%= work.getCompanyName()%>"></div>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="part_middle">
                <div class="box_search">
                    <h2>Tìm kiếm nâng cao</h2>
                    <div>
                        <form>
                            <input type="text" name="title" placeholder="Nội dung tìm kiếm">
                            <select name="career">
                                <option value = "0">--Chọn ngành nghề--</option>
                                <option value = "1">Kinh doanh</option>
                                <option value = "2">Hành chính - Kế toán</option>
                                <option value = "3">Công nghệ thông tin</option>
                                <option value = "4">Điện/Điện tử</option>
                                <option value = "5">Vận tải</option>
                                <option value = "6">Cơ khí</option>
                                <option value="7">Báo chí</option>
                                <option value = "8">Sản xuất</option>
                                <option value = "9">An ninh</option>
                                <option value = "10">Khác</option>
                            </select>
                            <select name="district">
                                <option value = "0">--Chọn quận/huyện--</option>
                                <option value = "1">Ba Đình</option>
                                <option value = "2">Hoàn Kiếm</option>
                                <option value = "3">Hai Bà Trưng</option>
                                <option value = "4">Đống Đa</option>
                                <option value = "5">Tây Hồ</option>
                                <option value = "6">Cầu giấy</option>
                                <option value = "7">Thanh Xuân</option>
                                <option value = "8">Hoàng Mai</option>
                                <option value = "9">Long Biên</option>
                                <option value = "10">Từ Liêm</option>
                                <option value = "11">Thanh Trì</option>
                                <option value = "12">Gia Lâm</option>
                                <option value = "13">Đông Anh</option>
                                <option value = "14">Sóc Sơn</option>
                                <option value = "15">Hà Đông</option>
                                <option value = "16">Sơn Tây</option>
                                <option value = "17">Ba Vì</option>
                                <option value = "18">Phúc Thọ</option>
                                <option value = "19">Thạch Thất</option>
                                <option value = "20">Quốc Oai-</option>
                                <option value = "21">Chương Mỹ</option>
                                <option value = "22">Đan Phượng</option>
                                <option value = "23">Hoài Đức</option>
                                <option value = "24">Thanh Oai</option>
                                <option value = "25">Mỹ Đức</option>
                                <option value = "26">Ứng Hòa</option>
                                <option value = "27">Thường Tín</option>
                                <option value = "27">Phú Xuyên</option>
                                <option value = "28">Mê Link</option>
                            </select>
                            <input type="button" value="Tìm kiếm">
                        </form>
                    </div>
                </div>
                <div class="box_job_high_salary">
                    <h2>Việc làm lương cao</h2>
                    <div class="listJob">
                        <%
                            for (Work ws : workSalarys) {
                        %>
                        <div class="job">
                            <div class="logo_company" style="background-image: url('<%=ws.getLogo()%>')"></div>
                            <div class="info">
                                <div class="info_job">
                                    <h3><%=ws.getTitleJob()%></h3>
                                    <h3><%=ws.getCompanyName()%></h3>
                                </div>
                                <div class="detail_job">
                                    <table cellspacing="10">
                                        <tr>
                                            <td><span class="icon_address"></span><span>Địa điểm: Hà Nội</span></td>
                                            <td><span class="icon_experience"></span><span>Kinh nghiệm: <%=ws.getExperience()%></span></td>
                                        </tr>
                                        <tr>
                                            <td><span class="icon_salary"></span><span>Mức lương: <%=ws.getSalary()%></span></td>
                                            <td><span class="icon_expire"></span><span>Hạn nộp: <%=ws.getExpire()%></span></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <%
                    if (workExpires.size()
                            != 0) {
                %>
                <div class="box_job_outofdate">
                    <h2>Việc làm sắp hết hạn</h2>
                    <div class="listJob">
                        <%
                            for (Work ws : workExpires) {
                        %>
                        <div class="job">
                            <div class="logo_company" style="background-image: url('<%=ws.getLogo()%>')"></div>
                            <div class="info">
                                <div class="info_job">
                                    <h3><%=ws.getTitleJob()%></h3>
                                    <h3><%=ws.getCompanyName()%></h3>
                                </div>
                                <div class="detail_job">
                                    <table cellspacing="10">
                                        <tr>
                                            <td><span class="icon_address"></span><span>Địa điểm: Hà Nội</span></td>
                                            <td><span class="icon_experience"></span><span>Kinh nghiệm: <%=ws.getExperience()%></span></td>
                                        </tr>
                                        <tr>
                                            <td><span class="icon_salary"></span><span>Mức lương: <%=ws.getSalary()%></span></td>
                                            <td><span class="icon_expire"></span><span>Hạn nộp: <%=ws.getExpire()%></span></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <%
                    }
                %>
                <div class="box_job_required" id="box_job_required">
                    <h2>Việc làm</h2>
                    <div class="listJob">
                        <%                            for (Work ws : works) {
                        %>
                        <div class="job">
                            <div class="logo_company" style="background-image: url('<%=ws.getLogo()%>')"></div>
                            <div class="info">
                                <div class="info_job">
                                    <h3><%=ws.getTitleJob()%></h3>
                                    <h3><%=ws.getCompanyName()%></h3>
                                </div>
                                <div class="detail_job">
                                    <table cellspacing="10">
                                        <tr>
                                            <td><span class="icon_address"></span><span>Địa điểm: Hà Nội</span></td>
                                            <td><span class="icon_experience"></span><span>Kinh nghiệm: <%=ws.getExperience()%></span></td>
                                        </tr>
                                        <tr>
                                            <td><span class="icon_salary"></span><span>Mức lương: <%=ws.getSalary()%></span></td>
                                            <td><span class="icon_expire"></span><span>Hạn nộp: <%=ws.getExpire()%></span></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="part_right">
                <div class="box_career">
                    <h2>Ngành nghề</h2>
                    <ul>
                        <li><a href="#box_job_required" class="linkCareer" content = "1">Kinh doanh</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "2">Hành chính - Kế toán</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "3">Công nghệ thông tin</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "4">Điện/Điện tử</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "5">Vận tải</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "6">Cơ khí</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "7">Báo chí</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "8">Sản xuất</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "9">An ninh</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "10">Khác</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "0">Tất cả</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="box_find">
                <div class="find_province">

                </div>
                <div class="find_career">

                </div>
            </div>
            <div class="contact">

            </div>
        </div>

        <a class="btn_up" href="#banner">

        </a>
    </body>

    <script src="Scripts/jquery.js" type="text/javascript"></script>
    <script src="Scripts/indexJS.js" type="text/javascript"></script>
</html>
