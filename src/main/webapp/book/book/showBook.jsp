<%--
  Created by IntelliJ IDEA.
  User: H
 
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>图书管理</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description"
          content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.7" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

</head>
<body class="gray-bg">
<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <i class="fa fa-laptop modal-icon"></i>
                <h4 class="modal-title">借阅图书</h4>
                <small class="font-bold">请选择对应的借书卡来借阅</small>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>请选择一个借书卡</label>
                    <select class="form-control m-b" name="publish" id="borrowCard" name="borrowCard">
                    </select>
                    <input type="hidden" id="bookId" name="bookId">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="submitData()">保存</button>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="tabs-container">
                <ul class="nav nav-tabs">
                    <c:forEach items="${list}" var="entity">
                        <li class="">
                            <a data-toggle="tab" href="tab-${entity.id}" aria-expanded="true"> ${entity.name}</a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="tab-content">
                    <c:forEach items="${list}" var="entity">
                        <div id="tab-${entity.id}" class="tab-pane ">
                            <div class="panel-body">
                                <div class="row">
                                    <c:forEach items="${entity.books}" var="book">
                                        <div class="col-sm-4">
                                            <div class="contact-box">
                                                <div class="col-sm-4">
                                                    <div class="text-center">
                                                        <img alt="image" class="m-t-xs img-responsive"
                                                             src="/sys/downloadServlet?fileName=${book.img}">
                                                        <div class="m-t-xs font-bold"></div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-8">
                                                    <h3><strong>《${book.name}》</strong></h3>
                                                    <strong style="font-size: 15px ;font-weight: bolder ">
                                                        作者： ${book.author}  </strong><br>
                                                    <address>
                                                        <strong style="font-size: 15px ;font-weight: bolder ">
                                                            价格：</strong>
                                                        <strong style="color: red">${book.price}￥</strong> <br>
                                                        <strong style="font-size: 15px ;font-weight: bolder ">
                                                            出版社： </strong> ${book.publish}<br>
                                                        <strong style="font-size: 15px ;font-weight: bolder ">
                                                            简介： </strong> ${book.notes}<br>
                                                        <c:if test="${book.state==0}">
                                                            <button data-target="#myModal" style="margin: 5px"
                                                                    type="button"
                                                                    onclick="goBorrowing(${book.id})"
                                                                    class="btn btn-w-m btn-primary">借阅
                                                            </button>
                                                        </c:if>
                                                        <c:if test="${book.state!=0}">
                                                            <button  type="button"
                                                                    class="btn btn-w-m btn-primary" style="margin: 5px ;background-color: grey; ">
                                                                已借出
                                                            </button>
                                                        </c:if>
                                                    </address>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.7"></script>

<!-- Peity -->
<script src="/js/plugins/peity/jquery.peity.min.js"></script>

<!-- 自定义js -->
<script src="/js/content.js?v=1.0.0"></script>

<!-- iCheck -->
<script src="/js/plugins/iCheck/icheck.min.js"></script>

<!-- Peity -->
<script src="/js/demo/peity-demo.js"></script>
<!-- Sweet alert -->
<script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

<script>
    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        $(".tabs-container .nav-tabs li").click(function () {
            var href = $(this).children()[0].href;
            var aId = href.substring(href.lastIndexOf('tab-'), href.length);
            $(".tab-pane").removeClass('active');
            //给当前点击的加上active
            $("#" + aId).addClass("active");
        })
        initTab();
    });

    function initTab() {
        var li = $(".tabs-container .nav-tabs ").children()[0];
        $(li).addClass('active')
        var href = $($(".tabs-container .nav-tabs ").children()[0]).children()[0].href
        var aId = href.substring(href.lastIndexOf('tab-'), href.length);
        $(".tab-pane").removeClass('active');
        //给当前点击的加上active
        $("#" + aId).addClass("active");
    }

    function removeData(id) {
        swal({
            title: "您确定要删除该书籍信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
            swal("删除成功！", "您已经删除该书籍信息。", "success");
            $.get("/book/bookServlet?action=remove&id=" + id, function (msg) {
                //之后再发起一个查询操作
                window.location.href = "/book/bookServlet?action=list"
            })
        });
    }

    //借阅书籍的方法
    function goBorrowing(bookId) {
        //判断是否有可用的借书卡
        $.get("/book/borrowCardServlet?action=checkHaveCard", function (data) {
            console.log("data" + data);
            if (data.msg === "ok") {   //表示可以借阅
                //绑定数据
                $("#bookId").val(bookId)
                var option="";
                //下拉菜单
                for (var i = 0; i < data.cards.length; i++) {
                    var card = data.cards[i];
                    option += "<option value= '" + card.id + "' >" + card.id + "  [ " + timestampToDate(card.starttime )+ "~" + timestampToDate(card.endtime)+ "]" + "</option>"
                }
                $("#borrowCard").html(option);
                //打开模态窗口
                $("#myModal").modal()

            } else {
                swal("借阅失败！", "无可使用的借书卡，请重新申请", "warning");
            }
        })
    }


    function timestampToDate(timestamp) {
        var date = new Date(timestamp)
        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return year + "-" + month + "-" + day;
    }
    //提交借阅信息
    function submitData(){
//获取bookId和cardId
        var bookId=$("#bookId").val();
        var cardId=$("#borrowCard").val();
        $.get("/book/borrowRecoderServlet?action=saveOrUpdate&bookId="+bookId+"&cardId="+cardId,function(data){
            location.href="/book/showBookServlet";
        })
    }

</script>

</body>

</html>
