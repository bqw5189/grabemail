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
import java.util.*;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class TotalFile {
    static List<String> fileNames = new ArrayList<String>();
//    static Map<String, HrMailEntity> hrMailEntityMap = new HashMap<String, HrMailEntity>();
    static Set<String> titleSet = new HashSet<String>();

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        listFile(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach"));
        System.out.println(fileNames.size());
    }

    public static void listFile(File file){
        if (fileNames.contains(file.getAbsolutePath()))return;

//        if (file.getAbsolutePath().endsWith(".zip")){
//            try {
//                ZipUtils.unzip(file.getAbsolutePath(), file.getPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            fileNames.add(file.getAbsolutePath());
//            listFile(new File(file.getPath()));
//        }

        fileNames.add(file.getAbsolutePath());

//        System.out.println(file.getAbsolutePath());

        if (file.isDirectory()){
            Collection<File> files = FileUtils.listFilesAndDirs(file, FileFilterUtils.or(FileFilterUtils.trueFileFilter(), FileFileFilter.FILE) , DirectoryFileFilter.DIRECTORY);
            listFile(files);
        }else{
            HrMailEntity hrMailEntity = new HrMailEntity(file.getAbsolutePath());
            if (null == hrMailEntity.getTitle() || hrMailEntity.getTitle().endsWith(".jpg")|| hrMailEntity.getTitle().endsWith(".zip")|| hrMailEntity.getTitle().endsWith(".ppt")|| titleSet.contains(hrMailEntity.getTitle())){
                return;
            }
            titleSet.add(hrMailEntity.getTitle());

            JdbcUtils.insertIntoHrEmail(hrMailEntity);
//            System.out.println(hrMailEntity);
        }
    }

    public static void listFile(Collection<File> files){
        for (File file:files){
            listFile(file);
        }
    }
}
