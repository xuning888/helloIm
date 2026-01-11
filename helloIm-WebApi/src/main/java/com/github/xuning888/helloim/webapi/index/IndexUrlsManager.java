package com.github.xuning888.helloim.webapi.index;


import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.common.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xuning
 * @date 2026/1/1 01:15
 */
public class IndexUrlsManager {

    private static final List<URL> providerUrls = new CopyOnWriteArrayList<>();

    public static void callbackUrls(List<URL> newUrls) {
        newUrls.clear();
        if (CollectionUtils.isEmpty(newUrls)) {
            return;
        }
        providerUrls.addAll(newUrls);
    }

    public static List<URL> getUrls() {
        return new ArrayList<>(providerUrls);
    }
}
