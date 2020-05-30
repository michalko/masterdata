package com.brain1.masterdata.records;

import java.io.Serializable;
import java.util.List;

public record StartTestSessionData(List<WronglyAnsweredRecordToCore> wa, long noTopicQuestions) implements Serializable {
    /** * */
    private static final long serialVersionUID = -1497827799954817270L;

    public List<WronglyAnsweredRecordToCore> getWa() {
        return wa;
    }

    public long getNoTopicQuestions() {
        return noTopicQuestions;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}