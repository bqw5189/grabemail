package com.grab.total;

import com.grab.JdbcUtils;
import com.grab.Utils;
import com.grab.entity.MailUser;
import org.springside.modules.utils.Encodes;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by baiqunwei on 15/6/25.
 */
public class ImapTask extends Thread {
    private MailUser mailUser;
    private Session session;
    private URLName urln;
    private Store store;
    private boolean running;
    private int pargress;
    private int total;

    public ImapTask(String name, MailUser mailUser) {

        super(name);
        this.mailUser = mailUser;
        urln = new URLName("imap", mailUser.getImapHost(), mailUser.getImapPort(), null,
                mailUser.getName(), mailUser.getPassword());
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.imap.partialfetch", "false");
        session = Session.getDefaultInstance(props, null);
//        session.setDebug(true);


        try {
            store = session.getStore(urln);
            store.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        Folder folder = null;
        try {
            running = true;
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);


//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) - 1);
//            Date mondayDate = calendar.getTime();
//            SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, mondayDate);
//            SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.LE, new Date());
//            SearchTerm comparisonAndTerm = new AndTerm(comparisonTermGe, comparisonTermLe);
//
//            // 搜索大于或等100KB的所有邮件
//            int mailSize = 1 ;
//            SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.GE, mailSize);
//
//            comparisonAndTerm = new AndTerm(comparisonAndTerm, comparisonAndTerm);
//
//
//            SearchTerm notTerm = new NotTerm(new FromStringTerm("智联招聘"));
////            Message[] messages = folder.search(notTerm);

            Message messages[] = folder.getMessages();

            messages = folder.getMessages((int)(messages.length*0.95), messages.length);

            for (int i = pargress; i < messages.length; i++) {
                pargress = i;

                Utils.printProgress(i, messages.length);

                Message message = messages[i];

                if(message.getSentDate().getTime() <= System.currentTimeMillis()- 4 * 24 * 60 * 60 * 1000){
                    continue;
                }

                if (JdbcUtils.isExist(message.getSubject())){
                    continue;
                }

                MimeMessage imapMessage = (MimeMessage) message;

                ReciveMail reciveMail = new ReciveMail(imapMessage);
                reciveMail.getMailContent(imapMessage);

                reciveMail.saveMessage(this.getName(), Encodes.encodeHex(reciveMail.getSubject().getBytes()), reciveMail.getBodyText());
//
                if (!JdbcUtils.isExist(reciveMail.getSubject()))
                    reciveMail.saveAttachMent(this.getName(), Encodes.encodeHex(reciveMail.getSubject().getBytes()), message);


            }

            System.out.println("mail:" + mailUser.getName() + " message:" + messages.length);


        } catch (Exception e) {
            running = false;
            e.printStackTrace();
        } finally {
            try {
                store.close();
            } catch (MessagingException e) {
                running = false;
            }
        }


    }

    public boolean isRunning() {
        return running;
    }

    public boolean isOver() {
        return this.pargress + 1 >= this.total;
    }

    public MailUser getMailUser() {
        return mailUser;
    }

    public void setPargress(int pargress) {
        this.pargress = pargress;
    }

    public int getPargress() {
        return pargress;
    }

    public int getTotal() {
        return total;
    }
}
