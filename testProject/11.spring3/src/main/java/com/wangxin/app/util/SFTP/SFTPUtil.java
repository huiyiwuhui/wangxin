/**
 * File Name:    SFTP.java
 *
 * File Desc:    
 *
 * Product AB:   
 *
 * Product Name: 
 *
 * Module Name:  
 *
 * Module AB:    
 *
 * Author:       Qizhan Lu
 *
 * History:      2010-7-18 created by Qizhan Lu
 */
package com.wangxin.app.util.SFTP;


import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Gxx
 * @version 1.0
 */
public class SFTPUtil
{
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SFTPUtil.class);

    private static void debug(String str)
    {
        LOGGER.debug(str);
    }


    private Session sshSession = null;


    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return ChannelSftp
     * @throws com.jcraft.jsch.JSchException 抛出JSchException
     */
    public ChannelSftp connect(String host, int port, String username,
                               String password) throws JSchException
    {
	    LOGGER.debug("SFTP服务器" + host + ":" + port + "连接开始！");
        JSch jsch = new JSch();
        sshSession = jsch.getSession(username, host, port);
        debug("Session created.");
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        debug("Session connected.");
        debug("Opening Channel.");
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        debug("Connected to " + host + ".");
        LOGGER.debug("SFTP服务器" + host + ":" + port + "连接成功！");
        return sftp;
    }

    /**
     * 断开连接
     *
     * @param sftp
     */
    public void disconnect(ChannelSftp sftp)
    {
        if (sftp != null)
        {
            if (sftp.isConnected())
            {
                sftp.disconnect();
                sshSession.disconnect();
            } else if (sftp.isClosed())
            {
                debug("sftp is closed already");
            }
        }

    }

    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件目录
     * @param sftp       sftp
     * @throws com.jcraft.jsch.SftpException e
     * @throws FileNotFoundException e
     */
    public void upload(String directory, String uploadFile, ChannelSftp sftp) throws SftpException, FileNotFoundException
    {
        createDir(directory, sftp);
        sftp.cd(directory);
        File file = new File(uploadFile);
        sftp.put(new FileInputStream(file), file.getName());
    }


    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp sftp
     * @throws SftpException
     * @throws FileNotFoundException
     */
    public void upload(String directory, File uploadFile,String fileName, ChannelSftp sftp) throws SftpException, FileNotFoundException
    {
        createDir(directory, sftp);
        sftp.cd(directory);
        sftp.put(new FileInputStream(uploadFile), fileName);
    }

    /**
     * 上传文件
     * @param host
     * @param port
     * @param username
     * @param password
     * @param directory
     * @param uploadFile
     * @throws com.jcraft.jsch.JSchException
     * @throws com.jcraft.jsch.SftpException
     * @throws FileNotFoundException
     */
    public void upload(String host, int port, String username, String password, String directory, String uploadFile)
            throws JSchException, SftpException, FileNotFoundException
    {
        ChannelSftp sftp = connect(host, port, username, password);
        createDir(directory, sftp);
        sftp.cd(directory);
        File file = new File(uploadFile);
        sftp.put(new FileInputStream(file), file.getName());
        disconnect(sftp);
    }

    /**
     * 创建目录
     *
     * @param filepath
     * @param sftp
     */
    private void createDir(String filepath, ChannelSftp sftp)
    {
        boolean bcreated = false;
        boolean bparent = false;

        File file = new File(filepath);
        LOGGER.info("目录名;" + filepath);

        String ppath = file.getParent().replace('\\', '/');
        LOGGER.info("父目录:" + ppath);


        try
        {
            sftp.cd(ppath);
            bparent = true;
        } catch (SftpException e1)
        {
            LOGGER.info("进父目录异常:" + ppath);
            bparent = false;
        }

        try
        {
            if (bparent)
            {
                try
                {
                    sftp.cd(filepath);
                    bcreated = true;
                } catch (Exception e)
                {
                    bcreated = false;
                }
                if (!bcreated)
                {
                    sftp.mkdir(filepath);
                    bcreated = true;
                }
                return;
            } else
            {
                createDir(ppath, sftp);
                LOGGER.info("创建目录完毕:" + ppath);
                sftp.cd(ppath);
                sftp.mkdir(filepath);
            }
        } catch (SftpException e)
        {
            debug("mkdir failed :" + filepath);
            e.printStackTrace();
        }

        try
        {
            sftp.cd(filepath);
        } catch (SftpException e)
        {
            e.printStackTrace();
            debug("can not cd into :" + filepath);
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp         sftp
     * @throws com.jcraft.jsch.SftpException e
     * @throws FileNotFoundException e
     */
    public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) throws SftpException, FileNotFoundException
    {
        LOGGER.info("directory = " + directory + ", downloadFile = " + downloadFile + ", saveFile = " + saveFile);
        sftp.cd(directory);
        debug("entered directory:" + directory);
        File file = new File(saveFile);
        if (!file.exists())
        {
            String dir = saveFile.substring(0, saveFile.lastIndexOf('/'));
            debug("Dir not found! Making dir:[" + dir + "]...");
            makeDir(dir);
        }
        sftp.get(downloadFile, new FileOutputStream(file));
        debug("downloadFile success..");
    }

    /**
     * 下载文件
     * @param host SFTP服务器IP
     * @param port SFTP服务器端口
     * @param username SFTP服务器用户名
     * @param password SFTP服务器密码
     * @param directory 远端目录
     * @param downloadFile 文件名
     * @param saveFile 本地文件路径
     * @throws com.jcraft.jsch.SftpException
     * @throws FileNotFoundException
     * @throws com.jcraft.jsch.JSchException
     */
    public void download(String host, int port, String username, String password, String directory, String downloadFile,
                         String saveFile) throws SftpException, FileNotFoundException, JSchException
    {
        ChannelSftp sftp = connect(host, port, username, password);
        sftp.cd(directory);
        debug("entered directory:" + directory);
        File file = new File(saveFile);
        if (!file.exists())
        {
            String dir = saveFile.substring(0, saveFile.lastIndexOf('/'));
            debug("Dir not found! Making dir:[" + dir + "]...");
            makeDir(dir);
        }
        sftp.get(downloadFile, new FileOutputStream(file));
        debug("downloadFile success..");
        sftp.disconnect();
    }

    private boolean makeDir(String path)
    {
        boolean success = true;

        StringTokenizer st = new StringTokenizer(path, "/");
        String path1 = st.nextToken() + "/";
        String path2 = path1;
        while (st.hasMoreTokens())
        {
            path1 = st.nextToken() + "/";
            path2 += path1;
            File inbox = new File(path2);
            if (!inbox.exists())
            {
                if (!inbox.mkdir())
                {
                    success = false;
                }
            }
        }
        return success;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp       sftp
     * @throws com.jcraft.jsch.SftpException e
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) throws SftpException
    {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @param sftp      sftp
     * @return vector
     * @throws com.jcraft.jsch.SftpException e
     */
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException
    {
        return sftp.ls(directory);
    }



    public static void main(String[] args) throws JSchException, FileNotFoundException, SftpException
    {
        SFTPUtil sf = new SFTPUtil();

        String host = "172.16.4.190";
        int port = 22;
        String username = "root";
        String password = "yhtest@868";

        String directory = "/data/news/mp3/";
        String uploadFile = "K:/202/JML.png";
//        String downloadFile = "gffund_purchase_20100723.txt";
//        String saveFile = "D:/gffund_purchase_20100723.txt";
//        String deleteFile = "delete.txt";
        ChannelSftp sftp = sf.connect(host, port, username, password);
        sf.upload(directory, uploadFile, sftp);
//        sf.download(directory, "2.txt", "D:/2.txt", sftp);
        System.out.println("开始断开连接");
        sf.disconnect(sftp);
        System.out.println("已经断开");
//        sf.download(directory, downloadFile, saveFile, sftp);
//        Vector v = sf.listFiles(directory, sftp);
        sftp.quit();
        System.out.println("11111111111");


//        sf.delete(directory, deleteFile, sftp);
        /* try
        {
            sftp.cd(directory);
            sftp.mkdir("ss");
            debug("finished");
        } catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }
}