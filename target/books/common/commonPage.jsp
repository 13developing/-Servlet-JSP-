<%--
  Created by IntelliJ IDEA.
  User: H
 
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">


<div class="row">
    <div class="col-sm-6">
        <div class="dataTables_info" id="DataTables_Table_0_info" role="alert" aria-live="polite"
             aria-relevant="all">显示 ${pageUtils.start+1} 到 ${pageUtils.end}
            项，共 ${pageUtils.totalCount} 项
        </div>
    </div>
    <div class="col-sm-6">
        <div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
            <ul class="pagination">

                <%--    ================  首先对上一页进行讨论    ,是第一页就disabled  ，不是第一页就 gopre() ==========================--%>

                <c:if test="${pageUtils.pageNum==1}">
                    <li class="paginate_button disabled" aria-controls="DataTables_Table_0"
                        tabindex="0" id="DataTables_Table_0_previous"><a
                            href="#">上一页</a>
                    </li>
                </c:if>

                <c:if test="${pageUtils.pageNum!=1}">
                    <li class="paginate_button previous " aria-controls="DataTables_Table_0"
                        tabindex="0" id="DataTables_Table_0_previous"><a
                            href="javaScript:void(0)" onclick="goPre()">上一页</a>
                    </li>
                </c:if>


                <%--        ======================================     这里是对页码的循环打印,以及对本页页码进行深色--%>
                <c:forEach items="${requestScope.pageUtils.pageList}" var="page">

                    <c:if test="${page!=pageUtils.pageNum}">
                        <li class="paginate_button" aria-controls="DataTables_Table_0" tabindex="0">
                            <a href="javaScript:void(0)" onclick="goPage(${page})">${page}</a></li>

                    </c:if>
                    <c:if test="${page==pageUtils.pageNum}">
                        <li class="paginate_button active" aria-controls="DataTables_Table_0"
                            tabindex="0">
                            <a href="javaScript:void(0)" onclick="goPage(${page})">${page}</a></li>

                    </c:if>
                </c:forEach>

                <%--           ==============================下面这里是分别对不同情况下的下一页按钮进行了设置，判断是不是最后一页，是最后一页就不可以点击，不是最后一页就可以进行点击--%>
                <c:if test="${pageUtils.pageNum==pageUtils.totalPage}">
                    <li class="paginate_button next disabled" aria-controls="DataTables_Table_0"
                        tabindex="0" id="DataTables_Table_0_next">
                        <a href="#">下一页</a>
                    </li>
                </c:if>

                <c:if test="${pageUtils.pageNum!=pageUtils.totalPage}">
                    <li class="paginate_button next" aria-controls="DataTables_Table_0"
                        tabindex="0" id="DataTables_Table_0_next">
                        <a href="javaScript:void(0)" onclick="goNext()"> 下一页</a>
                    </li>

                </c:if>


            </ul>
        </div>
    </div>
</div>


<script>
    //下面就是页面下标跳转的相关JS
    function goPre() {
        $("#pageNum").val(${pageUtils.pageNum-1});
        $("#myForm").submit();

    }

    function goPage(page) {
        $("#pageNum").val(page);
        $("#myForm").submit();
    }

    function goNext() {
        $("#pageNum").val(${pageUtils.pageNum+1});
        $("#myForm").submit();
    }

</script>