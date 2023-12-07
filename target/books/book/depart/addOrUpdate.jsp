<%--
  Created by IntelliJ IDEA.
  User: H
 
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>院系管理</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description"
          content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="/css/bootstrap.min.css?v=3.3.7" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <%--    下面两个是百度webUpload的--%>
    <link rel="stylesheet" type="text/css" href="/css/plugins/webuploader/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/css/demo/webuploader-demo.css">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>院系管理</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="form_basic.html#">选项1</a>
                            </li>
                            <li><a href="form_basic.html#">选项2</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="signupForm" action="/book/departServlet?action=saveOrUpdate"
                          method="post">
                        <input type="hidden" name="id" value="${entity.id}">

                        <div class="form-group">
                            <label class="col-sm-3 control-label" >名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" class="form-control" type="text" value="${entity.name}"
                                       aria-required="true" aria-invalid="true" class="error">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" >描述：</label>
                            <div class="col-sm-8">
                                <input id="notes" name="notes" class="form-control" type="text" value="${entity.notes}"
                                       aria-required="true" aria-invalid="false" class="valid">
                            </div>
                        </div>


                        <div class=" form-group
                                ">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <button class="btn btn-default" onclick="resetPage()" type="button">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.7"></script>

<!-- 自定义js -->
<script src="/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/js/plugins/validate/messages_zh.min.js"></script>

<script src="/js/demo/form-validate-demo.js"></script>

<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
<!--统计代码，可删除-->

<%--Webupload--%>
<script type="text/javascript">
    // 添加全局站点信息
    var BASE_URL = 'js/plugins/webuploader';
</script>
<script src="/js/plugins/webuploader/webuploader.min.js"></script>

<script src="/js/demo/webuploader-demo.js"></script>


<script>
    function resetPage() {

        window.location.href = "/book/departServlet?action=list"
    }

    var uploader = WebUploader.create({
// 选完文件后，是否自动上传。
        auto: true,
// swf文件路径
        swf: BASE_URL + '/js/Uploader.swf',

        server: '/sys/uploadServlet',   //  此处是文件被上传后，被接受到的地址
// 选择文件的按钮。可选。
// 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        accept: {   //   支持的上传文件的类型
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    uploader.on( 'uploadSuccess', function( file,data ) {
        // $( '#'+file.id ).addClass('upload-state-done');
        $("#imgName").val(data._raw)
        $("#fileList").text(data._raw)
        // $("#msg").text(data._raw)
    });


</script>
</body>

</html>
