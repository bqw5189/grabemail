package com.grab;

import com.grab.entity.CurriculumVitae;
import org.jsoup.nodes.Document;

/**
 * Created by baiqunwei on 15/6/8.
 */
public interface ISchoolInfo extends IProcessDocument{

    @Override
    public void process(CurriculumVitae curriculumVitae, final Document doc);
}
