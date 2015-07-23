package com.grab;

import com.grab.entity.CurriculumVitae;
import com.grab.entity.HrMailEntity;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springside.modules.utils.Encodes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Utils {
    private static String templateFileName = "/Volumes/MACINTOSH-WORK/work/code/utils/grabemail/src/exportTemp.xlsx";

    public static void printProgress(int pargress, int total) {
        int percentage = (int)((pargress * 1.0 /total)*100) + 1;
        String[] pargressStr = new String[]{"-", "\\", "|", "/"};

        System.out.println("\r" + Thread.currentThread().getName() + "=>[" + StringUtils.repeat("=", percentage) + pargressStr[pargress%4]+"][" + percentage + "%][" + pargress + "/" + total + "]");

    }

    public static void writeToExcel(String destFileName, List<CurriculumVitae> cvs) throws IOException, InvalidFormatException {
        Map<String, List<CurriculumVitae>> beans = new HashMap<String, List<CurriculumVitae>>();

        Configuration config = new Configuration();
//        config.setUTF16( true );

        XLSTransformer transformer = new XLSTransformer(config);

        beans.put("cv", cvs);

        transformer.transformXLS(templateFileName, beans, destFileName);
    }
    //Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/51job/hr@shangrucc.com/14-04-09/2835316a6f622e636f6d29e794b3e8afb7e8b4b5e585ace58fb8e8a18ce694bfe4b893e591982fe58aa9e79086efbc88e58c97e4baac2de69c9de998b3e58cbaefbc89efbc8de4bbbbe6b88520/resume.html
    //Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/other/hr@shangrucc.com/15-06-15/e7868ae5ada6e6b19fe79a84e4b8aae4babae7ae80e58e86/熊学江-iOS初级开发人员-猎聘网简历.doc
    public static void parseFileName(HrMailEntity hrMailEntity, String fileName) {
        System.out.println(fileName);
        if (fileName.indexOf(".DS_Store") > -1){
            return;
        }

        int startIndex = 8;
        String[] fileNames = fileName.split("/");
        if (fileNames.length <= 10){
            return;
        }

        System.out.println(Arrays.toString(fileNames));

        hrMailEntity.setName(fileNames[startIndex + 1]);
        hrMailEntity.setSentDate(fileNames[startIndex + 2]);

        if (fileNames[fileNames.length -1].equals("mail.html")){
            hrMailEntity.setSource(fileNames[startIndex + 0]);
            hrMailEntity.setTitle(new String(Encodes.decodeHex(fileNames[fileNames.length - 2])));
            hrMailEntity.setType(getTypeByTitle(hrMailEntity.getTitle()));
        }else if ("other".equals(fileNames[startIndex + 0])){
            hrMailEntity.setSource(getSourceByTitle(fileNames[fileNames.length -1]));
            hrMailEntity.setTitle(fileNames[fileNames.length -1]);
            hrMailEntity.setType(getTypeByTitle(hrMailEntity.getTitle()));
        }else if("zhilian".equals(fileNames[startIndex + 0])){
            hrMailEntity.setSource(getSourceByTitle(fileNames[fileNames.length -1]));
            hrMailEntity.setTitle(fileNames[fileNames.length -1]);
            hrMailEntity.setType(getTypeByTitle(hrMailEntity.getTitle()));
        }else {
            hrMailEntity.setSource(fileNames[startIndex + 0]);
            hrMailEntity.setTitle(new String(Encodes.decodeHex(fileNames[startIndex + 3])));
            hrMailEntity.setType(getTypeByTitle(hrMailEntity.getTitle()));
        }

    }

    private static String getSourceByTitle(String fileName) {
        if (fileName.toLowerCase().indexOf("51job")>-1){
            return "51job";
        }else if(fileName.indexOf("智联")>-1){
            return "智联";
        }else if(fileName.indexOf("猎聘")>-1){
            return "猎聘";
        }else if(fileName.indexOf("拉勾")>-1){
            return "拉勾";
        }else{
            return "其他";
        }
    }
//select name, source, type, sent_date, count(title) as "count_email" from t_email group by name, source, type, sent_date order by name, source
    private static String getTypeByTitle(String title) {
        if (title.toUpperCase().indexOf("JAVA")>-1){
            return "JAVA";
        }else if(title.toUpperCase().indexOf("DBA")>-1){
            return "DBA";
        }else if(title.toUpperCase().indexOf("数据库")>-1){
            return "DBA";
        }else if(title.toUpperCase().indexOf("UI")>-1){
            return "UI";
        }else if(title.toUpperCase().indexOf("PHP")>-1){
            return "PHP";
        }else if(title.toUpperCase().indexOf("ECLIPSE")>-1){
            return "ECLIPSE";
        }else if(title.toUpperCase().indexOf("网络工程师")>-1){
            return "网络工程师";
        }else if(title.toUpperCase().indexOf("美工")>-1){
            return "美工";
        }else if(title.toUpperCase().indexOf("运营推广总监")>-1){
            return "运营推广总监";
        }else if(title.toUpperCase().indexOf("Sharepoint")>-1){
            return "Sharepoint";
        }else if(title.toUpperCase().indexOf("IOS")>-1){
            return "IOS";
        }else if(title.toUpperCase().indexOf("ANDROID")>-1){
            return "ANDROID";
        }else if(title.toUpperCase().indexOf("NET")>-1){
            return "NET";
        }else if(title.toUpperCase().indexOf("Eclipse")>-1){
            return "ECLIPSE";
        }else if(title.toUpperCase().indexOf("WEB")>-1){
            return "WEB";
        }else if(title.toUpperCase().indexOf("人事经理")>-1){
            return "人事经理";
        }else if(title.toUpperCase().indexOf("人事行政专员")>-1){
            return "人事";
        }else if(title.toUpperCase().indexOf("出纳")>-1){
            return "出纳";
        }else if(title.toUpperCase().indexOf("司机")>-1){
            return "司机";
        }else if(title.toUpperCase().indexOf("销售")>-1){
            return "销售";
        }else if(title.toUpperCase().indexOf("营销")>-1){
            return "营销";
        }else if(title.toUpperCase().indexOf("商务助理")>-1){
            return "商务助理";
         }else if(title.toUpperCase().indexOf("客户经理")>-1){
            return "客户经理";
        }else if(title.toUpperCase().indexOf("行政")>-1){
            return "行政";
        }else if(title.toUpperCase().indexOf("财务")>-1){
            return "财务";
        }else if(title.toUpperCase().indexOf("C++")>-1){
            return "C++";
        }else{
            return "其他";
        }
    }

    public static void printRunTimes(String methodName, long start) {
        System.out.println(methodName + " used : " + (System.currentTimeMillis() - start)/1000);
    }

    ///Volumes/MACINTOSH-WORK/work/code/utils/grabemail/attach/51job/hr@shangrucc.com/14-06-26
    public static Date getSentDate(String fileName) throws ParseException {
        String[] fileNames = fileName.split("/");
        if (fileNames.length == 11){
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            if (fileNames[10].indexOf("-") == -1){
                return null;
            }
            return sdf.parse(fileNames[10]);
        }
        return null;
    }
}
