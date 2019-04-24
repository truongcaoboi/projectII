package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import model.Work;
import org.bson.Document;
import dataAccess.DataAccess;
import com.mongodb.client.MongoCollection;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Trang chủ</title>\n");
      out.write("        <link href=\"CSS/indexCss.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <link rel=\"shortcut icon\" type=\"image/png\" href=\"CSS/content/favicon.png\"/>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"banner\" id=\"banner\">\n");
      out.write("            <div class=\"frame\">\n");
      out.write("                <ul class=\"list_banner\">\n");
      out.write("                    <li class=\"item_banner first\"><img src=\"CSS/content/banner1.jpg\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner2.jpg\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner3.png\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner4.jpg\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner5.png\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner6.png\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner7.jpg\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner9.jpg\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner\"><img src=\"CSS/content/banner10.png\" alt=\"\"/></li>\n");
      out.write("                    <li class=\"item_banner last\"><img src=\"CSS/content/banner8.png\" alt=\"\"/></li>\n");
      out.write("                </ul>\n");
      out.write("            </div>\n");
      out.write("            <div  class=\"btn_left\"></div>\n");
      out.write("            <div class=\"btn_right\"></div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"body\">\n");
      out.write("            ");

                MongoCollection<Document> mongoColection = new DataAccess().getConnection();
                Vector<Work> works = new Vector<Work>();
                Vector<Work> workSalarys = new Vector<Work>();
                Vector<Work> workExpires = new Vector<Work>();

                Date now = new Date();
                SimpleDateFormat output = new SimpleDateFormat("dd/mm/yyyy");
                String nowDate = output.format(now);

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

                    if (work.getExpire().equals(nowDate)) {
                        workExpires.add(work);
                    } else if (sal >= 10000000) {
                        workSalarys.add(work);
                    } else if(dateWork.getTime()>=now.getTime()){
                        works.add(work);
                    }

                    request.getSession().setAttribute("works", works);
                    request.getSession().setAttribute("workSalarys", workSalarys);
                    request.getSession().setAttribute("workExpires", workExpires);
                }
            
      out.write("\n");
      out.write("            <div class=\"part_left\">\n");
      out.write("                <div class=\"box_login\">\n");
      out.write("                    <form class=\"form_login\">\n");
      out.write("                        <table cellpadding = 5 cellspacing = 5>\n");
      out.write("                            <caption><h2>Đăng nhập</h2></caption>\n");
      out.write("                            <tr>\n");
      out.write("                                <td>Tên đăng nhập <span>(*)</span></td>\n");
      out.write("                                <td>\n");
      out.write("                                    <input type=\"text\" name=\"username\" maxlength=\"100\" autocomplete=\"off\">\n");
      out.write("                                </td>\n");
      out.write("                            </tr>\n");
      out.write("                            <tr>\n");
      out.write("                                <td>Mật khẩu <span>(*)</span></td>\n");
      out.write("                                <td>\n");
      out.write("                                    <input type=\"password\" name=\"password\" maxlength=\"20\">\n");
      out.write("                                </td>\n");
      out.write("                            </tr>\n");
      out.write("                            <tr>\n");
      out.write("                                <td colspan=\"2\" align = \"center\"><input type=\"button\" value=\"Đăng nhập\">&nbsp;<input type=\"button\" value=\"Đăng ký\"></td>\n");
      out.write("                            </tr>\n");
      out.write("                        </table>\n");
      out.write("                    </form>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"box_company\">\n");
      out.write("                    <h2 align=\"center\">Một sô doanh nghiệp</h2>\n");
      out.write("                    <div>\n");
      out.write("                        ");

                            {
                                Work work = works.get(i);
                                if (!work.getLogo().equals(""))


                        
      out.write("\n");
      out.write("                        <div style=\"background-image: url('");
      out.print( work.getLogo() );
      out.write("')\" title=\"");
      out.print( work.getCompanyName());
      out.write("\"></div>\n");
      out.write("                        ");

                                }
                            }
                        
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"part_middle\">\n");
      out.write("                <div class=\"box_search\">\n");
      out.write("                    <h2>Tìm kiếm nâng cao</h2>\n");
      out.write("                    <div>\n");
      out.write("                        <form>\n");
      out.write("                            <input type=\"text\" name=\"title\" placeholder=\"Nội dung tìm kiếm\">\n");
      out.write("                            <select name=\"career\">\n");
      out.write("                                <option value = \"0\">--Chọn ngành nghề--</option>\n");
      out.write("                                <option value = \"1\">Kinh doanh</option>\n");
      out.write("                                <option value = \"2\">Hành chính - Kế toán</option>\n");
      out.write("                                <option value = \"3\">Công nghệ thông tin</option>\n");
      out.write("                                <option value = \"4\">Điện/Điện tử</option>\n");
      out.write("                                <option value = \"5\">Vận tải</option>\n");
      out.write("                                <option value = \"6\">Cơ khí</option>\n");
      out.write("                                <option value=\"7\">Báo chí</option>\n");
      out.write("                                <option value = \"8\">Sản xuất</option>\n");
      out.write("                                <option value = \"9\">An ninh</option>\n");
      out.write("                                <option value = \"10\">Khác</option>\n");
      out.write("                            </select>\n");
      out.write("                            <select name=\"district\">\n");
      out.write("                                <option value = \"0\">--Chọn quận/huyện--</option>\n");
      out.write("                                <option value = \"1\">Ba Đình</option>\n");
      out.write("                                <option value = \"2\">Hoàn Kiếm</option>\n");
      out.write("                                <option value = \"3\">Hai Bà Trưng</option>\n");
      out.write("                                <option value = \"4\">Đống Đa</option>\n");
      out.write("                                <option value = \"5\">Tây Hồ</option>\n");
      out.write("                                <option value = \"6\">Cầu giấy</option>\n");
      out.write("                                <option value = \"7\">Thanh Xuân</option>\n");
      out.write("                                <option value = \"8\">Hoàng Mai</option>\n");
      out.write("                                <option value = \"9\">Long Biên</option>\n");
      out.write("                                <option value = \"10\">Từ Liêm</option>\n");
      out.write("                                <option value = \"11\">Thanh Trì</option>\n");
      out.write("                                <option value = \"12\">Gia Lâm</option>\n");
      out.write("                                <option value = \"13\">Đông Anh</option>\n");
      out.write("                                <option value = \"14\">Sóc Sơn</option>\n");
      out.write("                                <option value = \"15\">Hà Đông</option>\n");
      out.write("                                <option value = \"16\">Sơn Tây</option>\n");
      out.write("                                <option value = \"17\">Ba Vì</option>\n");
      out.write("                                <option value = \"18\">Phúc Thọ</option>\n");
      out.write("                                <option value = \"19\">Thạch Thất</option>\n");
      out.write("                                <option value = \"20\">Quốc Oai-</option>\n");
      out.write("                                <option value = \"21\">Chương Mỹ</option>\n");
      out.write("                                <option value = \"22\">Đan Phượng</option>\n");
      out.write("                                <option value = \"23\">Hoài Đức</option>\n");
      out.write("                                <option value = \"24\">Thanh Oai</option>\n");
      out.write("                                <option value = \"25\">Mỹ Đức</option>\n");
      out.write("                                <option value = \"26\">Ứng Hòa</option>\n");
      out.write("                                <option value = \"27\">Thường Tín</option>\n");
      out.write("                                <option value = \"27\">Phú Xuyên</option>\n");
      out.write("                                <option value = \"28\">Mê Link</option>\n");
      out.write("                            </select>\n");
      out.write("                            <input type=\"button\" value=\"Tìm kiếm\">\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"box_job_high_salary\">\n");
      out.write("                    <h2>Việc làm lương cao</h2>\n");
      out.write("                    <div class=\"listJob\">\n");
      out.write("                        ");

                            for (Work ws : workSalarys

                            
                                ) {
                        
      out.write("\n");
      out.write("                        <div class=\"job\">\n");
      out.write("                            <div class=\"logo_company\" style=\"background-image: url('");
      out.print(ws.getLogo());
      out.write("')\"></div>\n");
      out.write("                            <div class=\"info\">\n");
      out.write("                                <div class=\"info_job\">\n");
      out.write("                                    <h3>");
      out.print(ws.getTitleJob());
      out.write("</h3>\n");
      out.write("                                    <h3>");
      out.print(ws.getCompanyName());
      out.write("</h3>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"detail_job\">\n");
      out.write("                                    <table cellspacing=\"10\">\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_address\"></span><span>Địa điểm: Hà Nội</span></td>\n");
      out.write("                                            <td><span class=\"icon_experience\"></span><span>Kinh nghiệm: ");
      out.print(ws.getExperience());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_salary\"></span><span>Mức lương: ");
      out.print(ws.getSalary());
      out.write("</span></td>\n");
      out.write("                                            <td><span class=\"icon_expire\"></span><span>Hạn nộp: ");
      out.print(ws.getExpire());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                    </table>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                        ");

                            }
                        
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                ");

                    if (workExpires.size () 
                        != 0) {
                
      out.write("\n");
      out.write("                <div class=\"box_job_outofdate\">\n");
      out.write("                    <h2>Việc làm sắp hết hạn</h2>\n");
      out.write("                    <div class=\"listJob\">\n");
      out.write("                        ");

                            for (Work ws : workExpires) {
                        
      out.write("\n");
      out.write("                        <div class=\"job\">\n");
      out.write("                            <div class=\"logo_company\" style=\"background-image: url('");
      out.print(ws.getLogo());
      out.write("')\"></div>\n");
      out.write("                            <div class=\"info\">\n");
      out.write("                                <div class=\"info_job\">\n");
      out.write("                                    <h3>");
      out.print(ws.getTitleJob());
      out.write("</h3>\n");
      out.write("                                    <h3>");
      out.print(ws.getCompanyName());
      out.write("</h3>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"detail_job\">\n");
      out.write("                                    <table cellspacing=\"10\">\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_address\"></span><span>Địa điểm: Hà Nội</span></td>\n");
      out.write("                                            <td><span class=\"icon_experience\"></span><span>Kinh nghiệm: ");
      out.print(ws.getExperience());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_salary\"></span><span>Mức lương: ");
      out.print(ws.getSalary());
      out.write("</span></td>\n");
      out.write("                                            <td><span class=\"icon_expire\"></span><span>Hạn nộp: ");
      out.print(ws.getExpire());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                    </table>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                        ");

                            }
                        
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                ");

                    }
                
      out.write("\n");
      out.write("                <div class=\"box_job_required\" id=\"box_job_required\">\n");
      out.write("                    <h2>Việc làm</h2>\n");
      out.write("                    <div class=\"listJob\">\n");
      out.write("                        ");
                            for (Work ws : works

                            
                                ) {
                        
      out.write("\n");
      out.write("                        <div class=\"job\">\n");
      out.write("                            <div class=\"logo_company\" style=\"background-image: url('");
      out.print(ws.getLogo());
      out.write("')\"></div>\n");
      out.write("                            <div class=\"info\">\n");
      out.write("                                <div class=\"info_job\">\n");
      out.write("                                    <h3>");
      out.print(ws.getTitleJob());
      out.write("</h3>\n");
      out.write("                                    <h3>");
      out.print(ws.getCompanyName());
      out.write("</h3>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"detail_job\">\n");
      out.write("                                    <table cellspacing=\"10\">\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_address\"></span><span>Địa điểm: Hà Nội</span></td>\n");
      out.write("                                            <td><span class=\"icon_experience\"></span><span>Kinh nghiệm: ");
      out.print(ws.getExperience());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                        <tr>\n");
      out.write("                                            <td><span class=\"icon_salary\"></span><span>Mức lương: ");
      out.print(ws.getSalary());
      out.write("</span></td>\n");
      out.write("                                            <td><span class=\"icon_expire\"></span><span>Hạn nộp: ");
      out.print(ws.getExpire());
      out.write("</span></td>\n");
      out.write("                                        </tr>\n");
      out.write("                                    </table>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                        ");

                            }
                        
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"part_right\">\n");
      out.write("                <div class=\"box_career\">\n");
      out.write("                    <h2>Ngành nghề</h2>\n");
      out.write("                    <ul>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"1\">Kinh doanh</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"2\">Hành chính - Kế toán</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"3\">Công nghệ thông tin</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"4\">Điện/Điện tử</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"5\">Vận tải</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"6\">Cơ khí</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"7\">Báo chí</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"8\">Sản xuất</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"9\">An ninh</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"10\">Khác</a></li>\n");
      out.write("                        <li><a href=\"#box_job_required\" class=\"linkCareer\" content = \"0\">Tất cả</a></li>\n");
      out.write("                    </ul>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"footer\">\n");
      out.write("            <div class=\"box_find\">\n");
      out.write("                <div class=\"find_province\">\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <div class=\"find_career\">\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"contact\">\n");
      out.write("\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <a class=\"btn_up\" href=\"#banner\">\n");
      out.write("\n");
      out.write("        </a>\n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("    <script src=\"Scripts/jquery.js\" type=\"text/javascript\"></script>\n");
      out.write("    <script src=\"Scripts/indexJS.js\" type=\"text/javascript\"></script>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
