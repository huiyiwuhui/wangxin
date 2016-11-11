package com.wangxin.app;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Administra on 2016/11/10.
 */
public class Test1 {

    public static void main(String[] args) {


        try {

                int port = 6001;
                String host = "127.0.0.1";
                Socket socket = new Socket();

             socket.setReuseAddress(true);


            String path = "/yhapi/web/signAgreement.do";
                SocketAddress dest = new InetSocketAddress(host, port);
                socket.connect(dest);
                OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);

                bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
                bufferedWriter.write("Host: " + host + "\r\n");
                bufferedWriter.write("\r\n");
                bufferedWriter.flush();

                BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                bufferedWriter.close();
                bufferedReader.close();
                bufferedWriter.flush();
                socket.close();
        } catch (Exception e) {

        }
    }
}
