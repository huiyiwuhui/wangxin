package com.wangxin.app;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket; 
import java.net.URL; 
import java.net.URLEncoder; 
import java.text.SimpleDateFormat; 
import java.util.Arrays; 
import java.util.Date; 
import java.util.HashMap; 
import java.util.Map; 
 
/**
 * 使用JavaSocket编写发送HTTP_POST请求的工具类
 *     与之类似的还有一个HttpClientUtil工具类
 *     地址为http://blog.csdn.net/jadyer/article/details/8087960
 *     还有一个使用Java原生API编写发送HTTP_POST请求的工具类
 *     地址为http://blog.csdn.net/jadyer/article/details/8637228
 * @create Apr 4, 2013 8:37:44 PM
 * @author 玄玉<http://blog.csdn.net/jadyer>
 */ 
public class HTTPUtil { 
    private HTTPUtil(){} 
     
    /**
     * 发送HTTP_POST请求
     *     本方法默认的连接超时和读取超时均为30秒
     *     请求参数含有中文时,亦可直接传入本方法中,本方法内部会自动根据reqCharset参数进行<code>URLEncoder.encode()</code>
     *     解码响应正文时,默认取响应头[Content-Type=text/html; charset=GBK]字符集,若无Content-Type,则使用UTF-8解码
     * @param reqURL     请求地址
     * @param reqParams  请求正文数据
     * @param reqCharset 请求报文的编码字符集(主要针对请求参数值含中文而言)
     * @return reqMsg-->HTTP请求完整报文,respMsg-->HTTP响应完整报文,respMsgHex-->HTTP响应的原始字节的十六进制表示
     */ 
    public static Map<String, String> sendPostRequest(String reqURL, Map<String, String> reqParams, String reqCharset) { 
        return sendPostRequest(reqURL, "", reqCharset);
    } 
     
     
    /**
     * 发送HTTP_POST请求
     *     you can see {@link HTTPUtil#sendPostRequest(String, Map, String)}
     *     注意:若欲直接调用本方法,切记请求参数值含中文时,一定要对该参数值<code>URLEncoder.encode(value, reqCharset)</code>
     *     注意:这里只是对key=value中的'value'进行encode,而非'key='..encode完毕后,再组织成key=newValue传给本方法
     */ 
    public static Map<String, String> sendPostRequest(String reqURL, String reqData1, String reqCharset){
        Map<String, String> respMap = new HashMap<String, String>(); 
        OutputStream out = null; //写  
        InputStream in = null;   //读  
        Socket socket = null;    //客户机  
        String respMsg = null; 
        String respMsgHex = null; 
        String respCharset = "UTF-8"; 
        StringBuilder reqMsg1 = new StringBuilder();
        try { 
            URL sendURL = new URL(reqURL); 
            String host = sendURL.getHost(); 
            int port = sendURL.getPort()==-1 ? 80 : sendURL.getPort(); 
            /**
             * 创建Socket
             *     ---------------------------------------------------------------------------------------------------
             *     通过有参构造方法创建Socket对象时,客户机就已经发出了网络连接请求,连接成功则返回Socket对象,反之抛IOException
             *     客户端在连接服务器时,也要进行通讯,客户端也需要分配一个端口,这个端口在客户端程序中不曾指定
             *     这时就由客户端操作系统自动分配一个空闲的端口,默认的是自动的连续分配
             *     如服务器端一直运行着,而客户端不停的重复运行,就会发现默认分配的端口是连续分配的
             *     即使客户端程序已经退出了,系统也没有立即重复使用先前的端口
             *     socket = new Socket(host, port);
             *     ---------------------------------------------------------------------------------------------------
             *     不过,可以通过下面的方式显式的设定客户端的IP和Port
             *     socket = new Socket(host, port, InetAddress.getByName("127.0.0.1"), 8765);
             *     ---------------------------------------------------------------------------------------------------
             */ 
            socket = new Socket(); 
            /**
             * 设置Socket属性
             */ 
            //true表示关闭Socket的缓冲,立即发送数据..其默认值为false  
            //若Socket的底层实现不支持TCP_NODELAY选项,则会抛出SocketException  
            socket.setTcpNoDelay(true); 
            //表示是否允许重用Socket所绑定的本地地址  
            socket.setReuseAddress(true); 
            //表示接收数据时的等待超时时间,单位毫秒..其默认值为0,表示会无限等待,永远不会超时  
            //当通过Socket的输入流读数据时,如果还没有数据,就会等待  
            //超时后会抛出SocketTimeoutException,且抛出该异常后Socket仍然是连接的,可以尝试再次读数据  
            socket.setSoTimeout(30000); 
            //表示当执行Socket.close()时,是否立即关闭底层的Socket  
            //这里设置为当Socket关闭后,底层Socket延迟5秒后再关闭,而5秒后所有未发送完的剩余数据也会被丢弃  
            //默认情况下,执行Socket.close()方法,该方法会立即返回,但底层的Socket实际上并不立即关闭  
            //它会延迟一段时间,直到发送完所有剩余的数据,才会真正关闭Socket,断开连接  
            //Tips:当程序通过输出流写数据时,仅仅表示程序向网络提交了一批数据,由网络负责输送到接收方  
            //Tips:当程序关闭Socket,有可能这批数据还在网络上传输,还未到达接收方  
            //Tips:这里所说的"未发送完的剩余数据"就是指这种还在网络上传输,未被接收方接收的数据  
            socket.setSoLinger(true, 5); 
            //表示发送数据的缓冲区的大小  
            socket.setSendBufferSize(1024); 
            //表示接收数据的缓冲区的大小  
            socket.setReceiveBufferSize(1024); 
            //表示对于长时间处于空闲状态(连接的两端没有互相传送数据)的Socket,是否要自动把它关闭,true为是  
            //其默认值为false,表示TCP不会监视连接是否有效,不活动的客户端可能会永久存在下去,而不会注意到服务器已经崩溃  
            socket.setKeepAlive(true); 
            //表示是否支持发送一个字节的TCP紧急数据,socket.sendUrgentData(data)用于发送一个字节的TCP紧急数据  
            //其默认为false,即接收方收到紧急数据时不作任何处理,直接将其丢弃..若用户希望发送紧急数据,则应设其为true  
            //设为true后,接收方会把收到的紧急数据与普通数据放在同样的队列中  
            socket.setOOBInline(true); 
            //该方法用于设置服务类型,以下代码请求高可靠性和最小延迟传输服务(把0x04与0x10进行位或运算)  
            //Socket类用4个整数表示服务类型  
            //0x02:低成本(二进制的倒数第二位为1)  
            //0x04:高可靠性(二进制的倒数第三位为1)  
            //0x08:最高吞吐量(二进制的倒数第四位为1)  
            //0x10:最小延迟(二进制的倒数第五位为1)  
            socket.setTrafficClass(0x04 | 0x10); 
            //该方法用于设定连接时间,延迟,带宽的相对重要性(该方法的三个参数表示网络传输数据的3项指标)  
            //connectionTime--该参数表示用最少时间建立连接  
            //latency---------该参数表示最小延迟  
            //bandwidth-------该参数表示最高带宽  
            //可以为这些参数赋予任意整数值,这些整数之间的相对大小就决定了相应参数的相对重要性  
            //如这里设置的就是---最高带宽最重要,其次是最小连接时间,最后是最小延迟  
            socket.setPerformancePreferences(2, 1, 3); 
            /**
             * 连接服务端
             */ 
            //客户端的Socket构造方法请求与服务器连接时,可能要等待一段时间  
            //默认的Socket构造方法会一直等待下去,直到连接成功,或者出现异常  
            //若欲设定这个等待时间,就要像下面这样使用不带参数的Socket构造方法,单位是毫秒  
            //若超过下面设置的30秒等待建立连接的超时时间,则会抛出SocketTimeoutException  
            //注意:如果超时时间设为0,则表示永远不会超时  
            socket.connect(new InetSocketAddress(host, port), 30000); 
            /**
             * 构造HTTP请求报文
             */ 
//            reqMsg.append("POST ").append(sendURL.getPath()).append(" HTTP/1.1\r\n");
//            reqMsg.append("Cache-Control: no-cache\r\n");
//            reqMsg.append("Pragma: no-cache\r\n");
//            reqMsg.append("User-Agent: JavaSocket/").append(System.getProperty("java.version")).append("\r\n");
//            reqMsg.append("Host: ").append(sendURL.getHost()).append("\r\n");
//            reqMsg.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
//            reqMsg.append("Connection: keep-alive\r\n");
//            reqMsg.append("Content-Type: application/x-www-form-urlencoded; charset=").append(reqCharset).append("\r\n");
//            reqMsg.append("Content-Length: ").append(reqData.getBytes().length).append("\r\n");
//            reqMsg.append("\r\n");
//            reqMsg.append(reqData);
            /**
             * 发送HTTP请求 
             */ 
            out = socket.getOutputStream(); 
            //这里针对getBytes()补充一下:之所以没有在该方法中指明字符集(包括上面头信息组装Content-Length的时候)  
            //是因为传进来的请求正文里面不会含中文,而非中文的英文字母符号等等,其getBytes()无论是否指明字符集,得到的都是内容一样的字节数组  
            //所以更建议不要直接调用本方法,而是通过sendPostRequest(String, Map<String, String>, String)间接调用本方法  
            //sendPostRequest(.., Map, ..)在调用本方法前,会自动对请求参数值进行URLEncoder(注意不包括key=value中的'key=')  
            //而该方法的第三个参数reqCharset只是为了拼装HTTP请求头信息用的,目的是告诉服务器使用哪种字符集解码HTTP请求报文里面的中文信息  
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
            String path = "/yhapi/fundInfo/queryFundList.do";
            bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
            bufferedWriter.write("Host: " + host + "\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            /**
             * 接收HTTP响应
             */ 
            in = socket.getInputStream();


            BufferedInputStream streamReader = new BufferedInputStream(in);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("与[" + reqURL + "]通信遇到异常,堆栈信息如下"); 
            e.printStackTrace(); 
        } finally { 
            if (null!=socket && socket.isConnected() && !socket.isClosed()) { 
                try { 
                    //此时socket的输出流和输入流也都会被关闭  
                    //值得注意的是:先后调用Socket的shutdownInput()和shutdownOutput()方法  
                    //值得注意的是:仅仅关闭了输入流和输出流,并不等价于调用Socket.close()方法  
                    //通信结束后,仍然要调用Socket.close()方法,因为只有该方法才会释放Socket占用的资源,如占用的本地端口等  
                    socket.close(); 
                } catch (IOException e) { 
                    System.out.println("关闭客户机Socket时发生异常,堆栈信息如下"); 
                    e.printStackTrace(); 
                } 
            } 
        } 
        return respMap;
    } 
     
     
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("111111111111");

        Map<String, String> stringStringMap = sendPostRequest("http://127.0.0.1:6001/yhapi", new HashMap<String, String>(), "UTF-8");
        System.out.println(stringStringMap);
        }
    }
} 