<%-- 
    Document   : detailJob
    Created on : May 3, 2019, 1:52:07 AM
    Author     : Xuan Truong PC
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="dataAccess.DataAccess"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="org.bson.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết công việc</title>
        <link href="CSS/indexCss.css" rel="stylesheet" type="text/css"/>
        <link href="CSS/detailCSS.css" rel="stylesheet" type="text/css"/>
        <link rel="shortcut icon" type="image/png" href="CSS/content/favicon.png"/>
    </head>
    <body>
        <%
            String id = request.getParameter("id");
            DataAccess da = new DataAccess();
            MongoCollection<Document> mongoColection = da.getConnection();
            Document doc = mongoColection.find(new Document("_id", new ObjectId(id))).first();
            da.closeConnect();
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        %>
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
                    <div id="companys">

                    </div>
                </div>
            </div>
            <div class="part_middle">
                <div class="box_detail_job">
                    <h2>Chi tiết công việc</h2>
                    <div class="hai">
                        <div class="job">
                            <div class="logo_company" style="background-image: url('<%=doc.getString("logo")%>')"></div>
                            <div class="info" style="margin-top:20px">
                                <div class="info_job">
                                    <h3><%= doc.getString("titleJob")%></h3>
                                    <h3><a href='<%= doc.getString("companyLink")%>'><%= doc.getString("companyName")%></a></h3>
                                    <h5><%= doc.getString("companyAddress")%></h5>
                                </div>

                            </div>

                        </div>
                        <div style="padding:10px;margin-top: -30px">
                            <div class="detail_job">
                                <table cellspacing="10">
                                    <tr>
                                        <td class="cha"><span class="icon_salary"></span><span>Mức lương: <%= doc.getString("salary")%></span></td>
                                        <td class="cha"><span class="icon_degree"></span><span>Trình độ: <%= doc.getString("degree")%></span></td>
                                    </tr>
                                    <tr>
                                        <td class="cha"><span class="icon_career"></span><span>Ngành nghề: <%= doc.getString("career")%></span></td>
                                        <td class="cha"><span class="icon_feature"></span><span>Đặc tính: <%= doc.getString("featureJob")%></span></td>
                                    </tr>
                                    <tr>
                                        <td class="cha"><span class="icon_experience"></span><span>Kinh nghiệm: <%= doc.getString("experience")%></span></td>
                                        <td class="cha"><span class="icon_form"></span><span>Hình thức: <%= doc.getString("form")%></span></td>
                                    </tr>
                                    <tr>
                                        <td class="cha"><span class="icon_quantity"></span><span>Số lượng: <%= doc.getInteger("quantity") == 0 ? "Đủ số" : doc.getInteger("quantity")%></span></td>
                                        <td class="cha"><span class="icon_expire"></span><span>Hạn nộp: <%= output.format(doc.getDate("expire"))%></span></td>
                                    </tr>
                                    <tr>
                                        <td class="cha"><span class="icon_gender"></span><span>Giới tính: <%= doc.getInteger("quantity") == 0 ? "Nữ" : (doc.getInteger("quantity") == 1 ? "Nam" : "Không yêu cầu")%></span></td>
                                        <td class="cha"></td>
                                    </tr>
                                </table>
                                <div>
                                    <h3>Thông tin thêm</h3>
                                    <table cellspacing="0" border="1" cellpadding="10">
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Mô tả</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("description")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Yêu cầu</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("required")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Quyền lợi</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("interest")%></td>
                                        </tr>
                                    </table>
                                </div>
                                <div>
                                    <h3>Thông tin liên hệ</h3>
                                    <table cellspacing="0" border="1">
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Người liên hệ</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%= doc.getString("contactName")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Địa chỉ liên hệ</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("contactAddress")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Điện thoại liên hệ</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("contactPhone")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Email liên hệ</b></td>
                                            <td style="width:800px;text-align: left;align-items: top"><%=doc.getString("contactEmail")%></td>
                                        </tr>
                                        <tr>
                                            <td style="width:150px;text-align: left;align-items: top"><b>Link gốc</b></td>
                                            <td style="width:800px;text-align: left;align-items: top">
                                            <%
                                                String linkRoot = doc.getString("linkRoot");
                                                String links [] = linkRoot.split(" dxt ");
                                                for(String link:links){
                                                    %>
                                                    <p><a href="<%=link%>" target="_blank"><%=link%></a></p>
                                                    <%
                                                }
                                            %>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box_job_required" >
                    <h2>Việc làm</h2>
                    <div class="listJob" id = "box_job_required">

                    </div>
                </div>
            </div>
            <div class="part_right">
                <div class="box_career">
                    <h2>Ngành nghề</h2>
                    <ul>
                        <li><a href="#box_job_required" class="linkCareer" content = "Kinh doanh">Kinh doanh</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Hành chính - Kế toán">Hành chính - Kế toán</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Công nghệ thông tin">Công nghệ thông tin</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Điện/Điện tử">Điện/Điện tử</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Vận tải">Vận tải</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Cơ khí">Cơ khí</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Báo chí">Báo chí</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Sản xuất">Sản xuất</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "An ninh">An ninh</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "Khác">Khác</a></li>
                        <li><a href="#box_job_required" class="linkCareer" content = "">Tất cả</a></li>
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
    <script src="Scripts/detailJS.js" type="text/javascript"></script>
</html>
