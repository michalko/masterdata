package com.brain1.masterdata.records;

import java.io.Serializable;

public record WronglyAnsweredRecordToCore(String topic, Integer realId, String pid) implements Serializable {

    private static final long serialVersionUID = 9157966109804107492L;
    public String getTopic() {
        return topic;
    }

    public Integer getRealId() {
        return realId;
    }

    public String getPid() {
        return pid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}