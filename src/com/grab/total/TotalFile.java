package com.grab.total;

import com.grab.JdbcUtils;
import com.grab.Utils;
import com.grab.ZipUtils;
import com.grab.entity.HrMailEntity;
import com.grab.entity.MailUser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.springside.modules.utils.Encodes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class TotalFile {
    static Set<String> titleSet = new HashSet<String>();

    public static void main(String[] args) throws Exception {
        Collection<File> files = FileUtils.listFiles(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach"), FileFilterUtils.and(FileFilterUtils.ageFileFilter(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000,false), FileFilterUtils.suffixFileFilter(".html")), DirectoryFileFilter.DIRECTORY);
        listFile(files);
    }

    public static void listFile(File file) throws ParseException {
        HrMailEntity hrMailEntity = new HrMailEntity(file.getAbsolutePath());
        if (null == hrMailEntity.getTitle() || hrMailEntity.getTitle().endsWith(".jpg")|| hrMailEntity.getTitle().endsWith(".zip")|| hrMailEntity.getTitle().endsWith(".ppt")|| titleSet.contains(hrMailEntity.getTitle())){
            return;
        }
        titleSet.add(hrMailEntity.getTitle());

        JdbcUtils.insertIntoHrEmail(hrMailEntity);

        System.out.println(hrMailEntity);
    }

    public static void listFile(Collection<File> files) throws ParseException {
        for (File file:files){
            listFile(file);
        }
    }
}
