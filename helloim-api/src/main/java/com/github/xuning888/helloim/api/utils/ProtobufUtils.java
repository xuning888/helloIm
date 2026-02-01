package com.github.xuning888.helloim.api.utils;


import com.github.xuning888.helloim.api.protobuf.common.v1.ImUser;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.Timestamps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author xuning
 * @date 2026/2/1 10:03
 */
public class ProtobufUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProtobufUtils.class);

    public static Date convertDate(com.google.protobuf.Timestamp timestamp) {
        return new Date(Timestamps.toMillis(timestamp));
    }

    public static com.google.protobuf.Timestamp convertTimestamp(Date date) {
        return Timestamps.fromMillis(date.getTime());
    }

    public static String toJson(Message message) {
        try {
            return JsonFormat.printer().includingDefaultValueFields().print(message);
        } catch (Exception ex) {
            logger.error("toJson error: {}", ex.getMessage(), ex);
            throw new RuntimeException("toJson error", ex);
        }
    }

    public static <T extends Message> T fromJson(String jsonStr, Message.Builder builder) {
        try {
            JsonFormat.Parser jsonParser = JsonFormat.parser().ignoringUnknownFields();
            jsonParser.merge(jsonStr, builder);
            return (T) builder.build();
        } catch (Exception ex) {
            logger.error("fromJson error: {}", ex.getMessage(), ex);
            throw new RuntimeException("fromJson error", ex);
        }
    }
}
