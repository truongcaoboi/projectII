/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

});

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
        $('.banner').mouseenter(function(){
            $('.btn_left').css('display','block');
            $('.btn_right').css('display','block');
        });
        $('.banner').mouseleave(function(){
            $('.btn_left').css('display','none');
            $('.btn_right').css('display','none');
        });
        
        $(window).scroll(function(){
            if($(window).scrollTop() > 0){
                $('.btn_up').show();
            }else{
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
        setInterval(function(){
            if(i%2 == 0){
                $('.btn_up').css('bottom','70px');
            }else{
                $('.btn_up').css('bottom','50px');
            }
            i++;
        },500);
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

var bannerJS = new Banner();


