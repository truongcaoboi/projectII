/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Loop{
    constructor(){
        this.loadHigh = null;
        this.loadExpire = null;
        this.loadSearch = null;
        this.changeCompany= null;
    }
    
    autoLoadHigh(){
        loopJS.loadHigh = setInterval(function(){
            var typeLoad = "high";
            var amount = $("#box_job_high_salary div.job").length;
            var dataSend = [];
            dataSend.push({name:"typeLoad",value:typeLoad});
            dataSend.push({name:"amount",value:amount});
            $.ajax({
                url:"AutoLoad",
                type:"post",
                dataType:"json",
                data:dataSend,
                async:false,
                success:function(data){
                    jobJS.buildData2(data.jobs,$('#box_job_high_salary'));
                    if(data.status=="yes"){
                        clearInterval(loopJS.loadHigh);
                    }
                }
            })
        },20000);
    }
    
    autoLoadExpire(){
        loopJS.loadExpire = setInterval(function(){
            var typeLoad = "expire";
            var amount = $("#box_job_outofdate div.job").length;
            var dataSend = [];
            dataSend.push({name:"typeLoad",value:typeLoad});
            dataSend.push({name:"amount",value:amount});
            $.ajax({
                url:"AutoLoad",
                type:"post",
                dataType:"json",
                data:dataSend,
                async:false,
                success:function(data){
                    jobJS.buildData2(data.jobs,$('#box_job_outofdate'));
                    if(data.status=="yes"){
                        clearInterval(loopJS.loadExpire);
                    }
                }
            })
        },20000);
    }
    
    autoLoadSearch(){
        loopJS.loadSearch = setInterval(function(){
            var typeLoad = "search";
            var amount = $("#box_job_required div.job").length;
            var dataSend = [];
            dataSend.push({name:"typeLoad",value:typeLoad});
            dataSend.push({name:"amount",value:amount});
            $.ajax({
                url:"AutoLoad",
                type:"post",
                dataType:"json",
                data:dataSend,
                async:false,
                success:function(data){
                    jobJS.buildData2(data.jobs,$('#box_job_required'));
                    if(data.status=="yes"){
                        clearInterval(loopJS.loadSearch);
                    }
                }
            })
        },20000);
    }
    
    autoChangeCompany(){
        loopJS.changeCompany = setInterval(function(){
            $.ajax({
                url:"AutoLoadCompany",
                type:"post",
                dataType:"json",
                async:false,
                success:function(data){
                    companyJS.buildData(data.companys);
                }
            })
        },20000);
    }
}

var loopJS = new Loop();


