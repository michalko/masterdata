package com.brain1.masterdata.records;

import java.util.List;

public record PostTransport(String id, int realPostsInTopics, Long topicID,
        List<String> tags) {
}