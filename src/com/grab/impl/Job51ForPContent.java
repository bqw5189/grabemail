package com.grab.impl;

import com.grab.IApplyInfo;
import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51ForPContent implements IApplyInfo{
    private static final Logger logger = LoggerFactory.getLogger(Job51ForPContent.class);
    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        Elements baseInfoTd = doc.select("html body table tbody tr td div#p_content div.v1 div.v1b_middle table.v_table01 tbody tr td");
        curriculumVitae.setName(baseInfoTd.get(1).text());
        curriculumVitae.setGender(baseInfoTd.get(3).text());
        curriculumVitae.setAge(baseInfoTd.get(5).text());
        curriculumVitae.setPlaceOfResidence(baseInfoTd.get(7).text());
        curriculumVitae.setStanding(baseInfoTd.get(9).text());
        curriculumVitae.setEmail(baseInfoTd.get(11).text());
        curriculumVitae.setAcademic(baseInfoTd.get(13).text());
        curriculumVitae.setSpecial(baseInfoTd.get(15).text());
        curriculumVitae.setPhone(baseInfoTd.get(21).text());
        curriculumVitae.setId(curriculumVitae.getPhone());
    }
}
