package com.Forming.sys.servlet;

import com.Forming.sys.utils.Constant;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


//处理文件下载的Servlet
@WebServlet(name = "downloadServlet", urlPatterns = "/sys/downloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //文件下载的操作
        //需要获取下载文件的名称
      String fileName=  req.getParameter("fileName");
        //对应的文件存储的基本路径
        String  basePath= Constant.UPLOAD_DIRECTORY;
        //处理文件下载的操作
//        InputStream in=new FileInputStream(basePath+ File.separator+ fileName);
        InputStream in=new FileInputStream(basePath+File.separator+fileName);
        int size=in.available();//获取需要下载的额文件长度
        byte data[]=new byte[size];
        in.read(data); // 读取数据到字节数组中
        in.close();  //  关闭输入流

        //响应的信息需要我们 浏览器知道
        if(fileName.contains(".jpg")||fileName.contains((".png"))){
            //就说明下载的是图片
            resp.setContentType("image/ipg");
        }
        else{  //说明下载的是文件
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("multipart/form-data");
            resp.setHeader("Content-Disposition", "attachment;filename="+ fileName
                    +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        }
        //输出下载的文件
        ServletOutputStream outputStream= resp.getOutputStream();
        outputStream.write(data);// 把数据写出去
        outputStream.flush();//刷新一下
        }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
