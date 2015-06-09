package com.grab;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by baiqunwei on 15/6/3.
 */
public class ImapClient {
    private final static Logger logger = LoggerFactory.getLogger(ImapClient.class);

    public static void main(String[] args) throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);

        URLName urln = new URLName("imap", "imap.exmail.qq.com", 143, null,
                "hr", "a123456");

        Store store = session.getStore(urln);
        store.connect();


        Folder folder = store.getFolder("收件箱");
        folder.open(Folder.READ_ONLY);
        Message message[] = folder.getMessages();
        System.out.println("Messages's length: " + message.length);
        ReciveOneMail pmm = null;
        for (int i = 8554; i < message.length; i++) {
            try {

                pmm = new ReciveOneMail((MimeMessage) message[i]);
//

//            IProcessCurriculumVitae processCurriculumVitae = ProcessCurriculumVitaeFactory.instance(pmm.getFrom());


//            processCurriculumVitae.process(pmm.getSubject());


//            System.out.println("Message " + i + " subject: " + pmm.getSubject());
//            System.out.println("Message " + i + " sentdate: " + pmm.getSentDate());
//            System.out.println("Message " + i + " replysign: " + pmm.getReplySign());
//            System.out.println("Message " + i + " hasRead: " + pmm.isNew());
//            System.out.println("Message " + i + "  containAttachment: " + pmm.isContainAttach((Part) message[i]));
//            System.out.println("Message " + i + " form: " + pmm.getFrom());
//            System.out.println("Message " + i + " to: " + pmm.getMailAddress("to"));
//            System.out.println("Message " + i + " cc: " + pmm.getMailAddress("cc"));
//            System.out.println("Message " + i + " bcc: " + pmm.getMailAddress("bcc"));
//            pmm.setDateFormat("yy年MM月dd日 HH:mm");
//            System.out.println("Message " + i + " sentdate: " + pmm.getSentDate());
//            System.out.println("Message " + i + " Message-ID: " + pmm.getMessageId());
                // 获得邮件内容===============
//            pmm.getMailContent((Part) message[i]);
                pmm.save();
                Utils.printProgress(i, message.length);
//            Thread.sleep(100l);

//            System.out.println("Message " + i + " bodycontent: \r\n"
//                    + pmm.getBodyText());
//            pmm.setAttachPath("/Users/baiqunwei/Desktop/Attach/");
//            pmm.saveAttachMent((Part) message[i]);
            }catch (Exception e){
                logger.error("{} save file error!", pmm.getSubject());
                store = session.getStore(urln);
                store.connect();
                folder = store.getFolder("收件箱");
                folder.open(Folder.READ_ONLY);
                message = folder.getMessages();
                System.out.println("Messages's length: " + message.length);
            }
        }
    }




}

