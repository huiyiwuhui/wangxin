package com.wangxin.app.constroller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Administra on 2016/11/2.
 */
@Controller
public class FileUploadController {




    @RequestMapping("/fileUpload1")
    public @ResponseBody
    void fileupload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {

        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println("开始");
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
//        String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/fileUploadMore")
    public @ResponseBody
    void fileUploadMore(HttpServletRequest request, PrintWriter writer,
                HttpServletResponse response) throws Exception {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator iter = multipartRequest.getFileNames();
        List<MultipartFile> files = new ArrayList<MultipartFile>();
        // 获得所有上传的文件
        while (iter.hasNext()) {
            String name = String.valueOf(iter.next());
            MultipartFile file = multipartRequest.getFile(name);
            if (file.getSize() == 0) {
            }
            if (file.getOriginalFilename().length() > 0) {
                files.add(multipartRequest.getFile(name));
            }
        }

        for (MultipartFile file : files) {
            System.out.println(file.getName());
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getSize());

        }

        // 设置返回值
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("name", file.getOriginalFilename());
        response.setStatus(200);
        writer.write("{\"name\":\"aaa\"}");
    }

//    @RequestMapping("/fileSubsectionUpload")
//    public @ResponseBody
//    void fileSubsectionUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
//
//        System.out.println(file.getName());
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getSize());
//        String path = request.getSession().getServletContext().getRealPath("upload");
//        String fileName = file.getOriginalFilename();
////        String fileName = new Date().getTime()+".jpg";
//        System.out.println(path);
//        File targetFile = new File(path, fileName);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
//
//        //保存
//        try {
//            file.transferTo(targetFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//
    @RequestMapping("/fileSubsectionUpload")
    public @ResponseBody
    void upload(HttpServletRequest request, PrintWriter writer,
                HttpServletResponse response) throws Exception {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取传入文件
        multipartRequest.setCharacterEncoding("utf-8");
        MultipartFile file = multipartRequest.getFile("file");
        String path = request.getSession().getServletContext().getRealPath("upload");
        this.SaveAs(path + file.getOriginalFilename(), file, request,
                response);
        // 设置返回值
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", file.getOriginalFilename());
        response.setStatus(200);
        writer.write("{\"name\":\""+file.getOriginalFilename()+"\"}");
    }

    private void SaveAs(String saveFilePath, MultipartFile file,
                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        long lStartPos = 0;
        int startPosition = 0;
        int endPosition = 0;
        int fileLength = 100000;
        OutputStream fs = null;

        String contentRange = request.getHeader("Content-Range");
        System.out.println(contentRange);
        if (!new File("uploadDemo").exists()) {
            new File("uploadDemo").mkdirs();
        }
        if (contentRange == null) {
            FileUtils.writeByteArrayToFile(new File(saveFilePath),
                    file.getBytes());

        } else {
            // bytes 10000-19999/1157632     将获取到的数据进行处理截取出开始跟结束位置
            if (contentRange != null && contentRange.length() > 0) {
                contentRange = contentRange.replace("bytes", "").trim();
                contentRange = contentRange.substring(0,
                        contentRange.indexOf("/"));
                String[] ranges = contentRange.split("-");
                startPosition = Integer.parseInt(ranges[0]);
                endPosition = Integer.parseInt(ranges[1]);
            }

            //判断所上传文件是否已经存在，若存在则返回存在文件的大小
            if (new File(saveFilePath).exists()) {
                fs = new FileOutputStream(saveFilePath, true);
                FileInputStream fi = new FileInputStream(saveFilePath);
                lStartPos = fi.available();
                fi.close();
            } else {
                fs = new FileOutputStream(saveFilePath);
                lStartPos = 0;
            }

            //判断所上传文件片段是否存在，若存在则直接返回
            if (lStartPos > endPosition) {
                fs.close();
                return;
            } else if (lStartPos < startPosition) {
                byte[] nbytes = new byte[fileLength];
                int nReadSize = 0;
                file.getInputStream().skip(startPosition);
                nReadSize = file.getInputStream().read(nbytes, 0, fileLength);
                if (nReadSize > 0) {
                    fs.write(nbytes, 0, nReadSize);
                    nReadSize = file.getInputStream().read(nbytes, 0,
                            fileLength);
                }
            } else if (lStartPos > startPosition && lStartPos < endPosition) {
                byte[] nbytes = new byte[fileLength];
                int nReadSize = 0;
                file.getInputStream().skip(lStartPos);
                int end = (int) (endPosition - lStartPos);
                nReadSize = file.getInputStream().read(nbytes, 0, end);
                if (nReadSize > 0) {
                    fs.write(nbytes, 0, nReadSize);
                    nReadSize = file.getInputStream().read(nbytes, 0, end);
                }
            }
        }
        if (fs != null) {
            fs.flush();
            fs.close();
            fs = null;
        }

    }
}
