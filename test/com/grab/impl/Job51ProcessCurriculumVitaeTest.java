package com.grab.impl;

import com.grab.IProcessCurriculumVitae;
import com.grab.ProcessCurriculumVitaeFactory;
import com.grab.Utils;
import com.grab.entity.CurriculumVitae;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by baiqunwei on 15/6/5.
 */
public class Job51ProcessCurriculumVitaeTest{
    private static String templateFileName = "/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/src/exportTemp.xlsx";

    @Test
    public void process() throws Exception{
        List<CurriculumVitae> cvs = new ArrayList<CurriculumVitae>();
        IProcessCurriculumVitae processCurriculumVitae = ProcessCurriculumVitaeFactory.instance("@quickmail.51job.com");

        Collection<File> cvFiles = FileUtils.listFiles(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/51job"), new String[]{"html"},true);
        for (File file:cvFiles){
            try {
                cvs.add(processCurriculumVitae.process(file));
            }catch (Exception e){
                System.out.println(file.getName());
                e.printStackTrace();
            }

            Utils.printProgress(cvs.size(), cvFiles.size());
        }

        writeToExcel("job51" +  ".xlsx",cvs);
    }

    private static void writeToExcel(String destFileName, List<CurriculumVitae> cvs) throws IOException, InvalidFormatException {
        Map<String, List<CurriculumVitae>> beans = new HashMap<String, List<CurriculumVitae>>();

        Configuration config = new Configuration();
//        config.setUTF16( true );

        XLSTransformer transformer = new XLSTransformer(config);

        beans.put("cv", cvs);

        transformer.transformXLS(templateFileName, beans, destFileName);
    }

//    @Test
    public void processFile() throws Exception{
        IProcessCurriculumVitae processCurriculumVitae = ProcessCurriculumVitaeFactory.instance("@quickmail.51job.com");
        File file = new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/51job/erninakbnlfjOtkpxkuvh9mhJyg2Ukobr2CgyEivxjivggyWiQqhG0r2Dr2Hh2ahCjhiWW.html");
            try {
                System.out.println(processCurriculumVitae.process(file));
            }catch (Exception e){
                System.out.println(file.getName());
                e.printStackTrace();
            }
    }

}
