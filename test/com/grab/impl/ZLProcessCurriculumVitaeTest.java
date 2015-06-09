package com.grab.impl;

import com.grab.IProcessCurriculumVitae;
import com.grab.ProcessCurriculumVitaeFactory;
import com.grab.Utils;
import com.grab.entity.CurriculumVitae;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * Created by baiqunwei on 15/6/5.
 */
public class ZLProcessCurriculumVitaeTest {
    @Test
    public void process() throws Exception{
        List<CurriculumVitae> cvs = new ArrayList<CurriculumVitae>();
        IProcessCurriculumVitae processCurriculumVitae = ProcessCurriculumVitaeFactory.instance("@quickmail.51job.com");

        Collection<File> cvFiles = FileUtils.listFiles(new File("/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/source/zhilian"), new String[]{"html"},true);
        for (File file:cvFiles){
            try {
                cvs.add(processCurriculumVitae.process(file));
            }catch (Exception e){
                System.out.println(file.getName());
                e.printStackTrace();
            }

            Utils.printProgress(cvs.size(), cvFiles.size());
        }

        Utils.writeToExcel("job51" + ".xlsx", cvs);
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
