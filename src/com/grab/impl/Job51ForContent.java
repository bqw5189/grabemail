package com.grab.impl;

import com.grab.IApplyInfo;
import com.grab.IProcessDocument;
import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by baiqunwei on 15/6/8.
 */
public class Job51ForContent implements IApplyInfo {
    private static final Logger logger = LoggerFactory.getLogger(Job51ForContent.class);

    @Override
    public void process(CurriculumVitae curriculumVitae, Document doc) {
        IProcessDocument job51PersonalInfoForContent = new Job51PersonalInfoForContent();
        job51PersonalInfoForContent.process(curriculumVitae, doc);

        IProcessDocument job51AddressInfoForContent = new Job51AddressInfoForContent();
        job51AddressInfoForContent.process(curriculumVitae, doc);

        IProcessDocument job51SchoolInfoForContent = new Job51SchoolInfoForContent();
        job51SchoolInfoForContent.process(curriculumVitae, doc);
    }

}
