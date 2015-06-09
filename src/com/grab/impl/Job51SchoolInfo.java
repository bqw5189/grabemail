package com.grab.impl;

import com.grab.ISchoolInfo;
import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51SchoolInfo implements ISchoolInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51SchoolInfo.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        Elements academicInfoTd = doc.select("html body table tbody tr td table tbody tr td table tbody tr td table tbody tr td table");
        if (academicInfoTd.size() > 2) {
            academicInfoTd = academicInfoTd.get(2).select("td");
        } else {
            academicInfoTd = doc.select("html body table tbody tr td div#c_content div.v2 div.v2_middle table tbody tr td table");
            if (academicInfoTd.size() > 1) {
                academicInfoTd = academicInfoTd.get(1).select("td");
            }
        }

        logger.debug("academicInfoTd:{}", academicInfoTd.text());

        if (academicInfoTd.size() > 6) {
            curriculumVitae.setAcademic(academicInfoTd.get(2).text());
            curriculumVitae.setSpecial(academicInfoTd.get(4).text());
            curriculumVitae.setSchool(academicInfoTd.get(6).text());
        } else if (academicInfoTd.size() == 6){
            curriculumVitae.setAcademic(academicInfoTd.get(1).text());
            curriculumVitae.setSpecial(academicInfoTd.get(3).text());
            curriculumVitae.setSchool(academicInfoTd.get(5).text());
        }else{}

    }
}
