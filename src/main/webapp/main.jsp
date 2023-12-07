<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="renderer" content="webkit"/>

    <title>校园图书管理系统 - 主页</title>

    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台"/>
    <meta name="description"
          content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术"/>

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

    <link rel="shortcut icon" href="favicon.ico"/>
    <link href="css/bootstrap.min.css?v=3.3.7" rel="stylesheet"/>
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet"/>
    <link href="css/animate.css" rel="stylesheet"/>
    <link href="css/style.css?v=4.1.0" rel="stylesheet"/>
    <link href="css/jquery.contextMenu.min.css" rel="stylesheet"/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden;">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i></div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <c:if test="${not empty sessionScope.loginUser.img}">
                            <span><img alt="image" class="img-circle"
                                       src="/sys/downloadServlet?fileName=${sessionScope.loginUser.img}" width="100px"
                                       height="100px"/></span>
                        </c:if>
                        <c:if test="${ empty sessionScope.loginUser.img}">
                            <span><img alt="image" class="img-circle" src="/img/profile_small.jpg"/></span>
                        </c:if>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
									<span class="clear">
										<span class="block m-t-xs"><strong
                                                class="font-bold">${sessionScope.loginUser.username}</strong></span>
										<span class="text-muted text-xs block">账户管理<b
                                                class="caret"></b></span>
									</span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="/sys/modifyUserInfoServlet?action=modifyPage&id=${loginUser.id}">修改密码/头像</a></li>
                            <li><a class="J_menuItem" href="/sys/modifyUserInfoServlet?action=ListInfo&id=${loginUser.id}">个人资料</a></li>

                            <li class="divider"></li>
                            <li><a href="/sys/logoutServlet">安全退出</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">H+</div>
                </li>

                <c:forEach items="${sessionScope.loginMenus}" var="parent">
                    <c:if test="${parent.parentId==-1}">
                        <li>
                            <a href="#">
                                <i class="fa fa-home"></i>
                                <span class="nav-label">${parent.name}</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <c:forEach items="${sessionScope.loginMenus}" var="sub">
                                    <c:if test="${sub.parentId==parent.id}">
                                        <li>
                                            <a class="J_menuItem" href="${sub.url}" data-index="0">${sub.name}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>

            </ul>
        </div>
    </nav>

    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">

        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="home.jsp">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown" data-toggle="dropdown">页签操作<span class="caret"></span></button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="tabCloseCurrent"><a>关闭当前</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他</a></li>
                    <li class="J_tabCloseAll"><a>全部关闭</a></li>
                </ul>
            </div>
            <a href="#" class="roll-nav roll-right tabReload"><i class="fa fa-refresh"></i> 刷新</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="home.jsp?v=4.1" frameborder="0"
                    data-id="home.jsp" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2014-2015 <a href="http://www.zi-han.net/" target="_blank">zihan's blog</a>
            </div>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.7"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="js/plugins/contextMenu/jquery.contextMenu.min.js"></script>
<script src="js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="js/contabs.js"></script>

<!-- 第三方插件 -->
<script src="js/plugins/pace/pace.min.js"></script>
<script type="text/javascript">
    function refreshPage() {
        location.reload();
    }
</script>
</body>
</html>