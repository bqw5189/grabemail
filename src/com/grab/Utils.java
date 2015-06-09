package com.grab;

import com.grab.entity.CurriculumVitae;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Utils {
    private static String templateFileName = "/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/src/exportTemp.xlsx";

    public static void printProgress(int pargress, int total) {
        int percentage = (int)((pargress * 1.0 /total)*100) + 1;
        String[] pargressStr = new String[]{"-", "\\", "|", "/"};

        System.out.print("\r[" + StringUtils.repeat("=", percentage) + pargressStr[pargress%4]+"][" + percentage + "%][" + pargress + "/" + total + "]");

    }

    public static void writeToExcel(String destFileName, List<CurriculumVitae> cvs) throws IOException, InvalidFormatException {
        Map<String, List<CurriculumVitae>> beans = new HashMap<String, List<CurriculumVitae>>();

        Configuration config = new Configuration();
//        config.setUTF16( true );

        XLSTransformer transformer = new XLSTransformer(config);

        beans.put("cv", cvs);

        transformer.transformXLS(templateFileName, beans, destFileName);
    }
}
