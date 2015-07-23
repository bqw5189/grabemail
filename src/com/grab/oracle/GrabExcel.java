package com.grab.oracle;

import com.grab.JdbcUtils;
import com.grab.entity.HrMailEntity;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by baiqunwei on 15/7/2.
 */
public class GrabExcel {
    private static Queue<HrMailEntity> hrMailEntityQueue = new ArrayBlockingQueue<HrMailEntity>(1024);

    public static void main(String[] args) {
        new InfoRead().start();
        new StudentRead().start();
    }

    static class InfoRead extends Thread {

        @Override
        public void run() {
            try {

                Workbook workbook = WorkbookFactory.create(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/oracle/students-2.xls"));

                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 3; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    HrMailEntity hrMailEntity = new HrMailEntity("");
                    hrMailEntity.setName(row.getCell(3) + "");
                    hrMailEntity.setSentDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                    hrMailEntity.setType("oracle");
                    hrMailEntity.setTitle(row.getCell(3) + "(" + row.getCell(5) + ")");
                    hrMailEntity.setSource("oracle");

                    System.out.println(hrMailEntity);

                    if (!JdbcUtils.isExist(hrMailEntity.getTitle())) {
                        JdbcUtils.insertIntoHrEmail(hrMailEntity);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class StudentRead extends Thread {

        @Override
        public void run() {
            try {

                Workbook workbook = WorkbookFactory.create(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/oracle/info-1.xls"));

                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 3; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    HrMailEntity hrMailEntity = new HrMailEntity("");
                    hrMailEntity.setName(row.getCell(3) + "");
                    hrMailEntity.setSentDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                    hrMailEntity.setType("oracle");
                    hrMailEntity.setTitle(row.getCell(3) + "(" + row.getCell(5) + ")");
                    hrMailEntity.setSource("oracle");

                    System.out.println(hrMailEntity);
                    if (!JdbcUtils.isExist(hrMailEntity.getTitle())) {
                        JdbcUtils.insertIntoHrEmail(hrMailEntity);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



