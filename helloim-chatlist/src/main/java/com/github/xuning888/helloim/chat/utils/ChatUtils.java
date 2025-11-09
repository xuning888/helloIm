package com.github.xuning888.helloim.chat.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.zookeeper.common.StringUtils;

/**
 * @author xuning
 * @date 2025/11/9 23:09
 */
public class ChatUtils {


    public static String chatKey(String chatId, Integer chatType) {
        return chatId + "_" + chatType;
    }

    public static Pair<String, Integer> getSubInfo(String chatKey) {
        if (StringUtils.isBlank(chatKey)) {
            return null;
        }
        String[] strs = chatKey.split("_");
        return Pair.of(strs[0], Integer.parseInt(strs[1]));
    }
}
