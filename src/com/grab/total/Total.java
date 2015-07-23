package com.grab.total;

import com.grab.entity.MailUser;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class Total {
    public static final String IMAP_HOST = "imap.exmail.qq.com";
    public static final int IMAP_PORT = 143;

    public static final String POP_HOST = "pop.exmail.qq.com";
    public static final int POP_PORT = 110;

    public static List<MailUser> users = new ArrayList<MailUser>();
    private static final List<ImapTask> IMAP_TASKS = new ArrayList<ImapTask>();

    static {
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrbeijing@daoshenggroup.com", "a123456"));

        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrchongqing@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrchengdu@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrdalian@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrguangzhou@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrnanjing@daoshenggroup.com", "a123456"));

        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrqingdao@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrshanghai@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrtianjing@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrwuhan@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrxian@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrxiamen@daoshenggroup.com", "a123456"));

        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrchangsha@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrhangzhou@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrsuzhou@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hrshengzhen@daoshenggroup.com", "a123456"));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "intern.li@daoshenggroup.com", "0p;/9ol."));
        users.add(new MailUser(IMAP_HOST, IMAP_PORT, "hr@shangrucc.com", "Hr12345"));
    }

    public static void main(String[] args) throws MessagingException {
        for (MailUser mailUser : users) {
            ImapTask imapTask = new ImapTask(mailUser.getName(), mailUser);
            imapTask.start();
            IMAP_TASKS.add(imapTask);
        }

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(60 * 1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (ImapTask imapTask : IMAP_TASKS) {
                        System.out.println("Task:" + imapTask.getName() + " isRunning:" + imapTask.isRunning() + " isOver:" + imapTask.isOver() + " process:" + imapTask.getPargress() + " total:" + imapTask.getTotal());
                        if (!imapTask.isRunning() && !imapTask.isOver()) {
                            IMAP_TASKS.remove(imapTask);

                            ImapTask newImapTask = new ImapTask(imapTask.getName(),imapTask.getMailUser());
                            newImapTask.setPargress(imapTask.getPargress());
                            newImapTask.start();

                            IMAP_TASKS.add(newImapTask);

                            System.out.println("Task:" + imapTask.getName() + " restart ... ");
                        }
                    }
                }
            }
        }.start();
    }
}
