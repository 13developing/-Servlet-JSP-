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


    <title>学生管理</title>
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
                    <h5>学生管理</h5>
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
                            <a href="/book/studentServlet?action=saveOrUpdatePage" class="btn btn-success "
                               type="button"><i
                                    class="fa fa-plus"></i>&nbsp;添加</a>
                        </div>

                        <div class="col-sm-3">
                            <form action="/book/studentServlet" method="get" id="myForm">
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
                                <th>编号</th>
                                <th>名称</th>
                                <th>学号</th>
                                <th>年龄</th>
                                <th>性别</th>
                                <th>邮箱</th>
                                <th>电话</th>
                                <th>微信</th>
                                <th>地址</th>
                                <th>所属院系</th>
                                <th>所属班级</th>
                                <th>日期</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.pageUtils.list}" var="entity">
                                <tr>
                                    <td>${entity.id}</td>
                                    <td>${entity.name}</td>
                                    <td>${entity.stuno}</td>
                                    <td>${entity.age}</td>
                                    <td>${entity.gender}</td>
                                    <td>${entity.email}</td>
                                    <td>${entity.telephone}</td>
                                    <td>${entity.wechat}</td>
                                    <td>${entity.address}</td>
                                    <td>${entity.departname}</td>
                                    <td>${entity.classname}</td>
                                    <td>${entity.createtime}</td>
                                    <td><a href="/book/studentServlet?action=saveOrUpdatePage&id=${entity.id}"
                                           class="btn btn-primary " type="button"><i
                                            class="fa fa-edit"></i>&nbsp;更新</a>
                                        <button class="btn btn-warning" onclick="removeData(${entity.id})"
                                                type="button  "><i class="fa fa-remove"></i>&nbsp;删除
                                        </button>
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

    function removeData(id) {
        swal({
            title: "您确定要删除该用户信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {  //  这里实际上是一个匿名的函数

            $.get("/book/studentServlet?action=remove&id=" + id, function (msg) {
                if (msg === 'ok') {
                    //表示删除
                    window.location.href = "/book/studentServlet?action=list"
                } else {
                    //表示不可被删除
                    swal("删除失败！", "该学生已经被分配，不可删除。", "Warning");
                }
                //之后再发起一个查询操作
            })
        });
    }
</script>
</body>
</html>
