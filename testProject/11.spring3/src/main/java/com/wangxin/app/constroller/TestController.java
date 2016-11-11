package com.wangxin.app.constroller;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wangxin.app.util.DateUtil;
import com.wangxin.app.util.SFTP.SFTPUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administra on 2016/11/7.
 */
@Controller
public class TestController {


    @RequestMapping("/fileUploadTest")
    public @ResponseBody
    void fileUploadTest(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("开始");
        String fileName1 = file.getOriginalFilename();

        SFTPUtil sft = new SFTPUtil();
        String host = "172.16.60.202";
        int port = 22;
        String username = "yhfiletest";
        String password = "yhfiletest";
        String directory = "/upload/video/";

        // 设置图片路径
        try {
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File f = fi.getStoreLocation();

            //将原始图片名更名为原始图片名+时间字符串
            String fileName = DateUtil.getToday() + "_" + file.getOriginalFilename();
            ChannelSftp sftp = sft.connect(host, port, username, password);

            sft.upload(directory, f, fileName, sftp);
            sft.disconnect(sftp);
            sftp.quit();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
