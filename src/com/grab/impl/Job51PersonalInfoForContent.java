package com.grab.impl;

import com.grab.IPersonalInfo;
import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51PersonalInfoForContent implements IPersonalInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51PersonalInfoForContent.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        curriculumVitae.setName(doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table tbody tr td span strong").text());

        String[] baseInfos = doc.select("html body table tbody tr td div#content div.v3 div.v3_middle table tbody tr td table tbody tr td span.blue").text().split("\\|");
        logger.debug("baseInfos:{}", Arrays.toString(baseInfos));

        switch (baseInfos.length) {
            case 5: {
                curriculumVitae.setTall(baseInfos[4]);
            }
            case 4: {
                curriculumVitae.setMarryStatus(baseInfos[3]);
            }
            case 3: {
                curriculumVitae.setStanding(baseInfos[0]);
                curriculumVitae.setGender(baseInfos[1]);
                curriculumVitae.setAge(baseInfos[2]);
            }
        }
    }
}
