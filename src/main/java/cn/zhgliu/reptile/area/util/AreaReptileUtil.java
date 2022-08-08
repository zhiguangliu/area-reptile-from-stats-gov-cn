/*
 * Copyright (c) [2022] [zhi_guang_liu@163.com]
 * [area-reptile-from-stats-gov-cn] is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */

package cn.zhgliu.reptile.area.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.zhgliu.reptile.area.consts.AreaLevel;
import cn.zhgliu.reptile.area.model.AreaNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaReptileUtil {
    public static List<AreaNode> doCrawl(String year, AreaLevel level) {
        List<AreaNode> provinceList = getProvince(year);
        System.out.println(provinceList.size() + "个" + JSONUtil.toJsonStr(provinceList));


        return provinceList;

    }


    private static final String BASE_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/";
    private static final String YEAR = "2021";

    private static final String getHtmlUrl(String baseUrl, String param) {
        return new StringBuffer(baseUrl).append(param).append("/").toString();
    }

    private static final Element getHtmlBodyElement(String url) {
        String html = HttpUtil.get(url);
        Assert.notNull(html, html);
        Document parse = Jsoup.parse(html);
        return parse.body();
    }

    private static final List<AreaNode> getChildren(String baseUrl, AreaNode areaNode, AreaLevel areaLevel) {
//        areaNode.getLevel()
        String htmlUrl = getHtmlUrl(baseUrl, areaNode.getCode());
        System.out.println(htmlUrl);

        List<AreaNode> provinceList = new ArrayList<>(34);
        return provinceList;
    }

    private static final Pattern provincePattern = Pattern.compile("(^\\d{2})\\.html");

    private static final List<AreaNode> getProvince(String year) {
        String htmlUrl = getHtmlUrl(BASE_URL, year);
        Element htmlBodyElement = getHtmlBodyElement(htmlUrl);
        List<AreaNode> provinceList = new ArrayList<>(34);
        Elements aTags = htmlBodyElement.getElementsByTag("a");
        aTags.forEach(a -> {
            String provinceName = a.ownText();
            String provinceUrl = a.attr("href");
            Matcher provinceMatcher = provincePattern.matcher(provinceUrl);
            if (provinceMatcher.matches()) {
                String areaCode = provinceMatcher.group(1);
                AreaNode node = new AreaNode(areaCode, provinceName, AreaLevel.LV1);
                provinceList.add(node);
            }
        });
        return provinceList;
    }

}
