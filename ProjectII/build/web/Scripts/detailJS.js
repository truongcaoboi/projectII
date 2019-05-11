/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class Banner {
    constructor() {
        this.initEvent();
        this.executeAutoNextBanner();
    }

    initEvent() {
        $('.list_banner li').first().addClass('show');
        $('.show').show();
        $('.btn_left').on('click', this.prevBanner);
        $('.btn_right').on('click', this.nextBanner);
        $('.banner').mouseenter(function () {
            $('.btn_left').css('display', 'block');
            $('.btn_right').css('display', 'block');
        });
        $('.banner').mouseleave(function () {
            $('.btn_left').css('display', 'none');
            $('.btn_right').css('display', 'none');
        });

        $(window).scroll(function () {
            if ($(window).scrollTop() > 0) {
                $('.btn_up').show();
            } else {
                $('.btn_up').hide();
            }
        });
    }

    executeAutoNextBanner() {
        setInterval(function () {
            var cur = $('.show');
            var next = null;
            if ($(cur).hasClass('last')) {
                next = $('.first');
            } else {
                next = $(cur).next('li');
            }
            $('.show').hide();
            $('.show').removeClass('show');
            $(next).addClass('show');
            $(next).show();
        }, 4000);
        var i = 0;
        setInterval(function () {
            if (i % 2 == 0) {
                $('.btn_up').css('bottom', '70px');
            } else {
                $('.btn_up').css('bottom', '50px');
            }
            i++;
        }, 500);
    }

    nextBanner() {
        var cur = $('.show');
        var next = null;
        if ($(cur).hasClass('last')) {
            next = $('.first');
        } else {
            next = $(cur).next('li');
        }
        $('.show').hide();
        $('.show').removeClass('show');
        $(next).addClass('show');
        $(next).show();
    }

    prevBanner() {
        var cur = $('.show');
        var prev = null;
        if ($(cur).hasClass('first')) {
            prev = $('.last');
        } else {
            prev = $(cur).prev('li');
        }
        $('.show').hide();
        $('.show').removeClass('show');
        $(prev).addClass('show');
        $(prev).show();
    }
}

class Company {
    constructor() {

    }

    loadData(numCompany) {

    }

    buildData(data) {
        var text = "";
        //<div style="background-image: url('<%= work.getLogo()%>')" title="<%= work.getCompanyName()%>"></div>
        $.each(data, function (index, value) {
            var logo = value.logo;
            var name = value.name;
            var link = value.link;
            text = "<a href='" + link + "' target='_blank'><div style='background-image: url(\"" + logo + "\")' title='" + name + "'></div></a>";
            $("#companys").append(text);
        });
    }

    changeCompanys() {

    }
}

class Job {
    constructor() {
        $("#button_search").on("click",this.search);
        $(".linkCareer").on("click",this.getJobWithCareer);
    }

    loadData(numPage,title,career,district) {
        
        
        var data = [];
        data.push({name:"title",value:title});
        data.push({name:"career",value:career});
        data.push({name:"district",value:district});
        //data.push({name:"title",value:title});
        $.ajax({
           url:"Search",
           type:"post",
           dataType:"json",
           data:data,
           success: function(response){
               jobJS.buildData(response.jobs,$('#box_job_required'));
               window.location.href="#box_job_required";
           },
           error:function (xhr, status, error) {
            alert("co loi xay ra: " + xhr + " " + status + " " + error);
        }
        });
    }

    buildData(data,object) {
        object.text("");
        if(data.length==0){
            object.append("<h4>Không có dữ liệu</h4>");
            return;
        }
        var text = "";
        $.each(data, function (index, value) {
            console.log(value.logo);
            text += '<div class="job">';
            text += "<div class='logo_company' style='background-image: url(\""+value.logo+"\")'></div>";
            text += '<div class="info">';
            text += '<div class="info_job">';
            text += '<h3 style="cursor:pointer" idJob = "'+value.id+'" onclick="jobJS.getDetail()">'+value.titleJob+'</h3>';
            text += '<h3><a href="'+value.companyLink+'" target="_blank">'+value.companyName+'</a></h3>';
            text += '</div>';
            text += '<div class="detail_job">';
            text += '<table cellspacing="10">';
            text += '<tr>';
            text += '<td class="cha"><span class="icon_career"></span><span>Ngành nghề: '+value.career+'</span></td>';
            text += '<td class="cha"><span class="icon_experience"></span><span>Kinh nghiệm: '+value.experience+'</span></td>';
            text += '</tr>';
            text += '<tr>';
            text += '<td class="cha"><span class="icon_salary"></span><span>Mức lương: '+value.salary+'</span></td>';
            text += '<td class="cha"><span class="icon_expire"></span><span>Hạn nộp: '+value.expire+'</span></td>';
            text += '</tr>';
            text += '</table>';
            text += '</div>';
            text += '</div>';
            text += '</div>';
            object.append(text);
            text = "";
        })
    }
    
    search(){
        var title = "";
        var career = "";
        var district = "";
        
        if($('input[name="title"]').val()!=null&$('input[name="title"]').val().trim()!=""){
            title = $('input[name="title"]').val();
            title = encodeURI(title);
        }
        if($('select[name="career"]').val()!=null&$('select[name="career"]').val().trim()!=""){
            career = $('select[name="career"]').val();
            career = encodeURI(career);
        }
        if($('select[name="district"]').val()!=null&$('select[name="district"]').val().trim()!=""){
            district = $('select[name="district"]').val();
            district = encodeURI(district);
        }
        if(title==""&career==""&district==""){
            return;
        }
        jobJS.loadData(1,title,career,district);
    }
    
    getJobWithCareer(){
        var object = event.target;
        var career = "";
        career = $(object).attr("content");
        career = encodeURI(career);
        
        jobJS.loadData(1,"",career,"");
    }
    
    getDetail(){
        var object = event.target;
        var id = $(object).attr("idJob");
        window.open("detailJob.jsp?id="+id,"_blank");
    }
}

var bannerJS = new Banner();
var jobJS = new Job();
var companyJS = new Company();

window.onload = function () {
    $.ajax({
        url: "GetDataFirst",
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data.status == "OK") {
                jobJS.buildData(data.jobs,$('#box_job_required'));
                companyJS.buildData(data.companys);
            } else {
                alert("Khong co du lieu");
            }
        },
        error: function (xhr, status, error) {
            alert("co loi xay ra: " + xhr + " " + status + " " + error);
        }
    });
}

