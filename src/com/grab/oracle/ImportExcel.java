package com.grab.oracle;

import com.alibaba.fastjson.JSON;
import com.grab.entity.HrMailEntity;
import com.grab.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by baiqunwei on 15/7/2.
 */
public class ImportExcel {
    private static Queue<HrMailEntity> hrMailEntityQueue = new ArrayBlockingQueue<HrMailEntity>(1024);
    public static String resourceUrl = "http://localhost:8090/console/api/v1/account/register";


    public static void main(String[] args) {
//        new InfoRead().start();
        new StudentRead().start();
    }
}


class InfoRead extends Thread {
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public void run() {
        try {

            Workbook workbook = WorkbookFactory.create(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/oracle/students-2.xls"));

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 4395; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                User user = new User();

                user.setName(StringUtils.trimAllWhitespace(row.getCell(3) + ""));
                user.setPlainPassword(StringUtils.trimAllWhitespace(row.getCell(7) + ""));
                user.setLoginName(StringUtils.trimAllWhitespace(row.getCell(7) + ""));

//{"loginName":"xuchao@daoshenggroup.com","plainPassword":"xuchao","name":"xuchao"}
                System.out.println(i + "->" + JSON.toJSON(user));

                Object loginResult = (restTemplate.postForObject(ImportExcel.resourceUrl, user, Object.class));

                System.out.println(loginResult);
            }

            System.out.println("users:" + (sheet.getLastRowNum() - 3));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class StudentRead extends Thread {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run() {
        try {

            Workbook workbook = WorkbookFactory.create(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/oracle/info-1.xls"));

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 4570; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                User user = new User();

                user.setName(StringUtils.trimAllWhitespace(row.getCell(1) + ""));
                user.setPlainPassword(StringUtils.trimAllWhitespace(row.getCell(7) + ""));
                user.setLoginName(StringUtils.trimAllWhitespace(row.getCell(7) + ""));

//{"loginName":"xuchao@daoshenggroup.com","plainPassword":"xuchao","name":"xuchao"}
                System.out.println(i + "->" + JSON.toJSON(user));

                Object loginResult = (restTemplate.postForObject(ImportExcel.resourceUrl, user, Object.class));

                System.out.println(loginResult);
            }

            System.out.println("users:" + (sheet.getLastRowNum() - 3));

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
