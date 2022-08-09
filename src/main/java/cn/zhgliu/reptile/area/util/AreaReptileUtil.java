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
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.zhgliu.reptile.area.consts.AreaLevel;
import cn.zhgliu.reptile.area.model.AreaNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AreaReptileUtil {

    private static final Logger logger = LoggerFactory.getLogger(AreaReptileUtil.class);

    private static final String BASE_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/";

    public static Boolean getDebugMode() {
        return DEBUG_MODE;
    }

    public static void setDebugMode(Boolean debugMode) {
        DEBUG_MODE = debugMode;
    }

    public static Boolean DEBUG_MODE = false;

    public static List<AreaNode> doCrawlJson(String year, AreaLevel targetLevel) {
        AtomicInteger count = new AtomicInteger(0);

        List<AreaNode> provinceList = getProvince(year);
        logger.debug("获取省份{}个", provinceList.size());

        for (AreaNode province : provinceList) {
            int c = count.addAndGet(3);
            if (DEBUG_MODE && c > 1) break;
            getNodeList(province, targetLevel);
        }

        return provinceList;
    }

    public static List<AreaNode> doCrawlFlat(String year, AreaLevel targetLevel) {
        List<AreaNode> provinceList = AreaReptileUtil.doCrawlJson(year, targetLevel);
        List<AreaNode> flatten = flatten(provinceList);
        return flatten;
    }

    private static List<AreaNode> getChildren(AreaNode node) {
        List<AreaNode> ret = new LinkedList<>();
        String parentCode = node.getCode();
        List<AreaNode> children = node.getChildren();
        if (children == null) {
            return ret;
        }
        children.stream().forEach(item -> {
            ret.add(item.setpCode(parentCode));
            ret.addAll(getChildren(item));
            item.setChildren(null);
        });
        return ret;
    }

    private static List<AreaNode> flatten(List<AreaNode> resource) {
        List<AreaNode> ret = new LinkedList<>();
        resource.stream().forEach(node -> {
            ret.add(node);
            ret.addAll(getChildren(node));
            node.setChildren(null);
        });
        return ret;
    }

    private static String getHtmlUrl(String baseUrl, String param) {
        return new StringBuffer(baseUrl).append(param).append("/").toString();
    }

    private static Element getHtmlBodyElement(String url) {
        return getHtmlBodyElement(url, 0);
    }

    private static Element getHtmlBodyElement(String url, Integer failCount) {
        String html;
        try {
            HttpResponse response = HttpRequest.get(url)
                    //超时，毫秒
                    .timeout(5000)
                    .execute();
            if (response.getStatus() == 200) {
                html = response.body();
            } else {
                System.out.println(response);
                throw new RuntimeException("http返回值是" + response.getStatus());
            }
        } catch (Exception e) {
            if (failCount < 3) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                return getHtmlBodyElement(url, failCount + 1);
            } else {
                throw e;
            }
        }
        Assert.notNull(html, html);
//        logger.debug(html);
        Document parse = Jsoup.parse(html);
        return parse.body();
    }

    private static final Pattern PROVINCE_PATTERN = Pattern.compile("(^\\d{2})\\.html");

    private static List<AreaNode> getProvince(String year) {
        String htmlUrl = getHtmlUrl(BASE_URL, year);
        logger.debug(htmlUrl);
        Element htmlBodyElement = getHtmlBodyElement(htmlUrl);
        List<AreaNode> provinceList = new ArrayList<>(34);
        Elements aTags = htmlBodyElement.getElementsByTag("a");
        aTags.forEach(a -> {
            String provinceName = a.ownText();
            String provinceUrl = a.attr("href");
            Matcher provinceMatcher = PROVINCE_PATTERN.matcher(provinceUrl);
            if (provinceMatcher.matches()) {
                String areaCode = provinceMatcher.group(1);
                AreaNode node = new AreaNode(areaCode, provinceName, AreaLevel.PROVINCE);
                String detailUrl = htmlUrl + areaCode + ".html";
                node.setCurrentDetailUrl(detailUrl);
                provinceList.add(node);
            }
        });
        return provinceList;
    }


    private static void getNodeList(AreaNode areaNode, AreaLevel targetAreaLevel) {
        if (!targetAreaLevel.equals(areaNode.getLevel())) {
            areaNode.setChildren(new LinkedList<>());
            String currentDetailUrl = areaNode.getCurrentDetailUrl();
            logger.debug("CURRENT_DETAIL_URL IS: {}", currentDetailUrl);
            Element htmlBodyElement = getHtmlBodyElement(currentDetailUrl);
            Elements trTags = htmlBodyElement.getElementsByClass(areaNode.getLevel().nextLevel().getLevelName() + "tr");
            trTags.stream().forEach(trElement -> {
                Elements tds = trElement.getElementsByTag("td");
                AreaNode child;
                if (tds.size() == 2) {
                    child = new AreaNode(tds.get(0).text(), tds.get(1).text(), areaNode.getLevel().nextLevel());
                } else if (tds.size() == 3) {
                    child = new AreaNode(tds.get(0).text(), tds.get(2).text(), areaNode.getLevel().nextLevel());
                    child.setVillageCategoryCode(tds.get(1).text());
                } else {
                    throw new RuntimeException("统计局页面数据格式发生变化");
                }
                areaNode.getChildren().add(child);

                Elements a = trElement.getElementsByTag("a");
                if (a.size() == 0) {
                    return;
                }
                String href = a.get(0).attr("href");
                String detailUrl = currentDetailUrl.substring(0, currentDetailUrl.lastIndexOf("/")) + "/" + href;
                child.setCurrentDetailUrl(detailUrl);
                getNodeList(child, targetAreaLevel);
            });
        }


    }

}
