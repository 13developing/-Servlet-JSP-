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


    <title>借阅记录</title>
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
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>借阅记录管理</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="table_basic.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="table_basic.html#">选项1</a>
                            </li>
                            <li><a href="table_basic.html#">选项2</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-9 m-b-xs">

                        </div>

                        <div class="col-sm-3">
                            <form action="/book/borrowRecoderServlet" method="get" id="myForm">
                                <div class="input-group">

                                    <input type="text" name="key" value="${pageUtils.key}" placeholder="请输入关键词"
                                           class="input-sm form-control">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-sm btn-primary"> 搜索</button>
                                    </span>
                                    <input type="hidden" name="action" value="list">
                                    <input type="hidden" name="pageNum" value="${pageUtils.pageNum}" id="pageNum">
                                    <input type="hidden" name="pageSize" value="${pageUtils.pageSize}" id="pageSize">

                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>

                                <th>学生姓名</th>
                                <th>借书卡编号</th>
                                <th>图书名称</th>
                                <th>图片</th>
                                <th>开始时间</th>
                                <th>归还时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.pageUtils.list}" var="entity">
                                <tr>

                                    <td>${entity.stuname}</td>
                                    <td>${entity.cardId}</td>
                                    <td>${entity.bookname}</td>
                                    <td><img src="/sys/downloadServlet?fileName=${entity.img}"
                                             style="width: 60px;height: 60px"></td>
                                    <td>${entity.starttime}</td>
                                    <td>${entity.endtime}</td>


                                    <td>
                                        <c:if test="${!sessionScope.loginUser.isAdmin&&entity.endtime==null}">

                                                <button class="btn btn-warning" onclick="backBook(${entity.id})"
                                                        type="button  ">
                                                    <i class="fa fa-backward"></i>&nbsp;归还
                                                </button>

                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <%--下面这里是我们的分页信息边框--%>
                    <jsp:include page="/common/commonPage.jsp"></jsp:include>
                    <%--                    <%@include file="/common/commonPage.jsp" %>--%>
                    <%--            此处经过抽取，抽取到了commonPage.jsp中 ，在这里进行引入--%>
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
    });


    function backBook(id) {
        swal({
            title: "您确定要归还该书籍吗",
            text: "归还后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "归还",
            closeOnConfirm: false
        }, function () {
            swal("归还成功！", "您已归还该书籍。", "success");
            $.get("/book/borrowRecoderServlet?action=backBook&id=" + id, function (msg) {

                //之后再发起一个查询操作
                window.location.href = "/book/borrowRecoderServlet?action=list"
            })
        });
    }
</script>

</body>

</html>
