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
public class Job51PersonalInfo implements IPersonalInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51PersonalInfo.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {

        curriculumVitae.setName(doc.select("html body table tbody tr td table tbody tr td table tbody tr td table tbody tr td span strong").text());

        String[] baseInfos = doc.select("html body table tbody tr td table tbody tr td table tbody tr td table tbody tr td table tbody tr td span.blue1").text().split("\\|");
        logger.debug("baseInfos:{}", Arrays.toString(baseInfos));

        switch (baseInfos.length) {
            case 6:
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
