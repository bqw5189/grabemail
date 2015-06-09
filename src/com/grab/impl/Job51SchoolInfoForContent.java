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
public class Job51SchoolInfoForContent implements ISchoolInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51SchoolInfoForContent.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        Elements academicInfoTd = doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table:eq(1) tbody tr td:eq(2) table tbody tr td");

        if (academicInfoTd.size() == 0){
            academicInfoTd = doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table:eq(3) tbody tr td:eq(2) table tbody tr td");
        }
        if (academicInfoTd.size() == 0){
            academicInfoTd = doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table:eq(2) tbody tr td:eq(2) table tbody tr td");
        }

        logger.debug("academicInfoTd:{}", academicInfoTd.outerHtml());

        curriculumVitae.setAcademic(academicInfoTd.get(2).text());
        curriculumVitae.setSpecial(academicInfoTd.get(4).text());
        curriculumVitae.setSchool(academicInfoTd.get(6).text());
    }
}
