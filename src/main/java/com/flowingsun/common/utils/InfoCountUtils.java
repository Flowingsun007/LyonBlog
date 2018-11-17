package com.flowingsun.common.utils;

import com.flowingsun.common.annotation.MethodExcuteTimeLog;
import com.flowingsun.common.entity.BlogVisitor;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class InfoCountUtils {

    public static int countChinese(String text) {
        String Reg="^[\u4e00-\u9fa5]{1}$";//正则
        int result=0;
        for(int i=0;i<text.length();i++){
            String b=Character.toString(text.charAt(i));
            if(b.matches(Reg))
                result++;
            text.charAt(i);
        }
        return result;
    }

    public static String getPrintSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }


    public static String getIp(HttpServletRequest httpRequest){
        String ipAddress = httpRequest.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpRequest.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpRequest.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpRequest.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }
    public static String getBrowser(HttpServletRequest httpRequest){
        String agent = httpRequest.getHeader("User-Agent");
        String browserVersion = "";
        //从请求头中读取User-Agent值
        if(agent.indexOf("MSIE")>0){
            browserVersion = "IE";
        }else if(agent.indexOf("Firefox")>0){
            browserVersion = "Firefox";
        }else if(agent.indexOf("Chrome")>0){
            browserVersion = "Chrome";
        }else if(agent.indexOf("Safari")>0){
            browserVersion = "Safari";
        }else if(agent.indexOf("Camino")>0){
            browserVersion = "Camino";
        }else if(agent.indexOf("Konqueror")>0){
            browserVersion = "Konqueror";
        }else if(agent.indexOf("QQBrowser")>0){
            browserVersion = "QQBrowser";
        }
        if("".equals(browserVersion)){
            StringTokenizer st = new StringTokenizer(agent, "(;)");
            while(st.hasMoreElements()){
                browserVersion=st.nextToken();
            }
        }

        return browserVersion;
    }
    public static String getSysInfo(HttpServletRequest httpRequest){
        String systenInfo="";
        String agent = httpRequest.getHeader("User-Agent");
        if (agent.indexOf("NT 10.0") > 0){
            systenInfo = "Windows Vista/Server 10";
        }else if (agent.indexOf("NT 8.0") > 0){
            systenInfo = "Windows Vista/Server 8";
        }else if (agent.indexOf("NT 6.0") > 0){
            systenInfo = "Windows Vista/Server 2008";
        } else if (agent.indexOf("NT 5.2") > 0){
            systenInfo = "Windows Server 2003";
        } else if (agent.indexOf("NT 5.1") > 0){
            systenInfo = "Windows XP";
        } else if (agent.indexOf("NT 6.0") > 0){
            systenInfo = "Windows Vista";
        } else if (agent.indexOf("NT 6.1") > 0){
            systenInfo = "Windows 7";
        } else if (agent.indexOf("NT 6.2") > 0){
            systenInfo = "Windows Slate";
        } else if (agent.indexOf("NT 6.3") > 0){
            systenInfo = "Windows 8";
        } else if (agent.indexOf("NT 5") > 0){
            systenInfo = "Windows 2000";
        } else if (agent.indexOf("NT 4") > 0){
            systenInfo = "Windows NT4";
        } else if (agent.indexOf("Me") > 0){
            systenInfo = "Windows Me";
        } else if (agent.indexOf("98") > 0){
            systenInfo = "Windows 98";
        } else if (agent.indexOf("95") > 0){
            systenInfo = "Windows 95";
        } else if (agent.indexOf("Mac") > 0){
            systenInfo = "Mac";
        } else if (agent.indexOf("Unix") > 0){
            systenInfo = "UNIX";
        } else if (agent.indexOf("Linux") > 0){
            if(agent.indexOf("Android")>0){systenInfo = "Android";}
            else{systenInfo = "Linux";}
        } else if (agent.indexOf("SunOS") > 0){
            systenInfo = "SunOS";
        }
        if("".equals(systenInfo)){
            StringTokenizer st = new StringTokenizer(agent, "(;)");
            int i=0;
            while(st.hasMoreElements()&&i<=1){
                i++;
                systenInfo=st.nextToken();
            }
        }
        return systenInfo;
    }

    public static BlogVisitor getVisitorInfo(HttpServletRequest request){
//        //request.getHeaderNames()请求Header信息
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println(headerName+":"+headerValue);
//        }
        long startTime = System.currentTimeMillis();
        BlogVisitor blogVisitor = new BlogVisitor();
        blogVisitor.setBrowser(getBrowser(request));
        blogVisitor.setSourceurl(request.getHeader("Referer"));
        blogVisitor.setTargeturl(request.getRequestURI());
        blogVisitor.setIp(getIp(request));
        blogVisitor.setOs(getSysInfo(request));
        long endTime = System.currentTimeMillis();
        System.out.println("---------------------------【方法执行时间统计】--------------------------\nInfoCountUtils.getVisitorInfo():"+(endTime-startTime)+"(ms)\n");
        return blogVisitor;
    }
}
