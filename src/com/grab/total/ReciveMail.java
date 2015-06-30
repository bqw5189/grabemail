package com.grab.total;

/**
 * Created by baiqunwei on 15/6/3.
 */

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 有一封邮件就需要建立一个ReciveMail对象
 */
public class ReciveMail {
    private final static Logger logger = LoggerFactory.getLogger(ReciveMail.class);

    private MimeMessage mimeMessage = null;
    private String saveAttachPath = ""; //附件下载后的存放目录
    private StringBuffer bodytext = new StringBuffer();//存放邮件内容
    private String dateformat = "yy-MM-dd HH:mm"; //默认的日前显示格式

    public ReciveMail(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获得发件人的地址和姓名
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)
            personal = "";
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
     */
    public String getMailAddress(String type) throws Exception {
        String mailaddr = "";
        String addtype = type.toUpperCase();
        InternetAddress[] address = null;
        if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC")) {
            if (addtype.equals("TO")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            } else if (addtype.equals("CC")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String email = address[i].getAddress();
                    if (email == null)
                        email = "";
                    else {
                        email = MimeUtility.decodeText(email);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null)
                        personal = "";
                    else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "<" + email + ">";
                    mailaddr += "," + compositeto;
                }
                mailaddr = mailaddr.substring(1);
            }
        } else {
            throw new Exception("Error emailaddr type!");
        }
        return mailaddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
     */
    public String getMailAddressTO() throws Exception {
        String mailaddr = "";
        InternetAddress[] address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);

        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                String email = address[i].getAddress();
                if (email == null)
                    email = "";
                else {
                    email = MimeUtility.decodeText(email);
                }
                String personal = address[i].getPersonal();
                if (personal == null)
                    personal = "";
                else {
                    personal = MimeUtility.decodeText(personal);
                }
                String compositeto = personal;
                mailaddr += "," + compositeto;
            }
            mailaddr = mailaddr.substring(1);
        }

        return mailaddr;
    }

    /**
     * 获得邮件主题
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if (subject == null)
                subject = "";
        } catch (Exception exce) {
        }
        return subject;
    }

    /**
     * 获得邮件发送日期
     */
    public String getSentDate() throws Exception {
        Date sentdate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        return format.format(sentdate);
    }

    /**
     * 获得邮件正文内容
     */
    public String getBodyText() {
        return bodytext.toString();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     */
    public void getMailContent(Part part) throws Exception {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)
            conname = true;
//        System.out.println("CONTENTTYPE: " + contenttype);
        if (part.isMimeType("text/plain") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        } else {
        }
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean getReplySign() throws MessagingException {
        boolean replysign = false;
        String needreply[] = mimeMessage
                .getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replysign = true;
        }
        return replysign;
    }

    /**
     * 获得此邮件的Message-ID
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags's length: " + flag.length);
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                System.out.println("seen Message.......");
                break;
            }
        }
        return isnew;
    }

    /**
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachflag = true;
                else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1)
                        attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1)
                        attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }

    public void saveAttachMent(String userName, String emailName, Message message) throws Exception {

        if (this.getFrom().indexOf("51job") > -1){
            this.setAttachPath("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/51job/" + userName + "/" + this.getSentDate().split(" ")[0] + "/" + emailName);
        }else if(this.getFrom().indexOf("Zhaopin") > -1){
            this.setAttachPath("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/zhaopin/" + userName + "/" + this.getSentDate().split(" ")[0] + "/" + emailName);
        }else{
            this.setAttachPath("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/other/" + userName + "/" + this.getSentDate().split(" ")[0] + "/" + emailName);
        }

        Object o = message.getContent();
        if(o instanceof Multipart) {
            Multipart multipart = (Multipart) o ;
            saveAttachMent(multipart);
        } else if (o instanceof Part){
            Part part = (Part) o;
            saveAttachMent(part);
        } else {
//            System.out.println("类型" + message.getContentType());
//            System.out.println("内容" + message.getContent());
        }

    }

    public void saveAttachMent(Multipart multiparts) throws Exception {
        for (int i = 0; i < multiparts.getCount(); i++) {
            Part part = multiparts.getBodyPart(i);
            try {
                if (part.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) part.getContent();// 转成小包裹
                    saveAttachMent(multipart);
                }else{
                    saveAttachMent(part);
                }
            }catch (IOException e){

            }
        }
    }

    /**
     * 【保存附件】
     */
    public void saveAttachMent(Part part) throws Exception {
        String fileName = "";

        fileName = part.getFileName();

        if ((fileName != null)) {
//            System.out.println("发现附件: " +  MimeUtility.decodeText(part.getFileName()));
//            System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));
//            System.out.println("附件内容:" + part.getContent());

            fileName = fileName.replaceAll("\\?==\\?", "?=\t\r\n=?");

            if (fileName.indexOf("utf-8")>-1 || fileName.indexOf("GBK")>-1 || fileName.indexOf("gb2312")>-1){
                fileName = MimeUtility.decodeText(fileName);
            }

            if (!fileName.endsWith(".txt")) {
                fileName = MimeUtility.decodeText(fileName);
            }
            saveFile(fileName, part);
        }

    }

    /**
     * 【设置附件存放路径】
     */

    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /**
     * 【设置日期显示格式】
     */
    public void setDateFormat(String format) throws Exception {
        this.dateformat = format;
    }

    /**
     * 【获得附件存放路径】
     */
    public String getAttachPath() {
        return saveAttachPath;
    }


    private void saveFile(String fileName, Part part) throws Exception {
//        System.out.println(fileName);

        File dir = new File(this.getAttachPath());
        if(!dir.exists()){
            dir.mkdirs();
        }


        if(fileName.indexOf(File.separator) > -1){
            fileName = fileName.replaceAll(File.separator, "-");
        }

        File storefile = new File(this.getAttachPath() + File.separator + fileName);
        if (storefile.exists()){
            return;
        }

        try {

//            // 轮询间隔 5 秒
//            long interval = TimeUnit.SECONDS.toMillis(5);
//            // 创建一个文件观察器用于处理文件的格式
//            FileAlterationObserver observer = new FileAlterationObserver(storefile);
//            //设置文件变化监听器
//            observer.addListener(new FileListener(part.getSize()));
//            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
//            monitor.start();
            if (part.isMimeType("text/*")){
                FileUtils.writeStringToFile(storefile, part.getContent()+"","GBK");
            }else if (part.isMimeType("image/*")){
                FileUtils.copyInputStreamToFile(part.getInputStream(), storefile);
            }else{
                part.writeTo(new FileOutputStream(storefile));
            }

        }catch (Exception e){
            storefile.delete();
        }


        System.out.println("storefile's path: " + storefile.toString() + "("  + storefile.getTotalSpace() + ")");
    }

    /**
     * 【真正的保存附件到指定目录里】
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
        DataInputStream din = new DataInputStream(in);
        FileOutputStream out = null;
        System.out.println(fileName);
        File dir = new File(this.getAttachPath());
        if(!dir.exists()){
            dir.mkdirs();
        }


        if(fileName.indexOf(File.separator) > -1){
            fileName = fileName.replaceAll(File.separator, "-");
        }

        File storefile = new File(this.getAttachPath() + File.separator + fileName);
        if (storefile.exists()){
            return;
        }

//        Boolean b = storefile.exists();
//        if(!b) {
//            out = new FileOutputStream(storefile);
//            int data;
//            // 循环读写
//            while((data=in.read()) != -1) {
//                out.write(data);
//                System.out.println("附件：【" + fileName + "】下载...，保存路径为：" + storefile.getPath());
//
//            }
//            out.flush();
//            din.close();
//            out.close();
//        }

//        try {
            FileUtils.copyInputStreamToFile(MimeUtility.decode(in,"quoted-printable"), storefile);
//            System.out.println("storefile's path: " + storefile.toString() + "("  + ")");
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            System.out.println("文件保存失败!=>" + storefile);
//            storefile.delete();
//        } finally {
//        }
    }
}
