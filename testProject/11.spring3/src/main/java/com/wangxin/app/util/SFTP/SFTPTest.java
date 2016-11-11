package com.wangxin.app.util.SFTP;

import com.jcraft.jsch.ChannelSftp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SFTPTest {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SFTPTest test = new SFTPTest();

        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "172.16.4.190");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "yhtest@868");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
        
        String src = "K:/202/JML.png"; // 本地文件名
        String dst = "/data/news/mp3/0003.mp3"; // 目标文件名
              
        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        File file = new File(src);
        long fileSize = file.length();

        chSftp.put(src, dst,new FileProgressMonitor(fileSize), ChannelSftp.APPEND); // 代码段2
        
        // chSftp.put(new FileInputStream(src), dst, ChannelSftp.OVERWRITE); // 代码段3
        
        chSftp.quit();
        channel.closeChannel();
    }
}