package com.grab;

import com.grab.impl.Job51ProcessCurriculumVitae;
import com.grab.zlimpl.ZLProcessCurriculumVitae;

/**
 * Created by baiqunwei on 15/6/5.
 */
public class ProcessCurriculumVitaeFactory {

    public final static IProcessCurriculumVitae instance(String from) {
        if (from.endsWith("@quickmail.51job.com")) {
            return new Job51ProcessCurriculumVitae();
        } else if (from.endsWith("@quickmail.51job.com")) {
            return new ZLProcessCurriculumVitae();
        } else {
            return null;
        }
    }
}
