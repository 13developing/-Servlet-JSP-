package com.Forming.sys.servlet;

import com.Forming.sys.utils.Constant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


@WebServlet(name = "uploadServlet", urlPatterns = {"/sys/uploadServlet"})
public class UploadServlet extends HttpServlet {

    /*文件处理上传的方法*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //上传的文件信息
        String uploadPath = Constant.UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
//如果目录不存在，就创建
            uploadDir.mkdir();

        }
//做文件上传的处理
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);

        try{
            //可以通过ServletFileUpload 对上传的请求做解析
            // FileItem 其实就是客户端表单提交的各个表单域内容
           List<FileItem> fileItems= upload.parseRequest(req);

           if(fileItems!=null&&fileItems.size()>0){
               //说明有表单信息的提交
               for(FileItem fileItem:fileItems){
                   //判断当前获取的信息是否是文件
                   if(!fileItem.isFormField()){
                       //表示获取的是上传的文件信息
                       //获取上传文件的名称
                       String fileName=new File(fileItem.getName()).getName();
                       //先上传的文件会生成一个新的文件名
                       fileName=new Date().getTime()+fileName.substring(fileName.lastIndexOf("."));
                       //拼接完整的文件上传文件的路径
                       String filePath=uploadPath+File.separator+fileName;
                       System.out.println("filepath:"+filePath);
                       //之后把上传的文件存储到磁盘中
                       fileItem.write(new File(filePath));
                       //需要把生成的文件的名称返回给客户端
                       PrintWriter writer=resp.getWriter();
                       writer.write(fileName);
                       writer.flush();
                       writer.close();

                   }
               }

           }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
