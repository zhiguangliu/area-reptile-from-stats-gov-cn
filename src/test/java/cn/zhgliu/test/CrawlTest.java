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

package cn.zhgliu.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.json.JSONUtil;
import cn.zhgliu.reptile.area.consts.AreaLevel;
import cn.zhgliu.reptile.area.model.AreaNode;
import cn.zhgliu.reptile.area.util.AreaReptileUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CrawlTest {
    private static Logger logger = LoggerFactory.getLogger(CrawlTest.class);

    @Test
    public void testCrawlJson() {
        AreaReptileUtil.setDebugMode(true);
        List<AreaNode> areaNode = AreaReptileUtil.doCrawlJson("2009", AreaLevel.COUNTY);
        String result = JSONUtil.toJsonStr(areaNode);
        FileUtil.writeString(result, new File("2009-json-COUNTY-data.json"), StandardCharsets.UTF_8);
    }

    @Test
    public void testCrawlFlat() {
        List<AreaNode> areaNode = AreaReptileUtil.doCrawlFlat("2021", AreaLevel.TOWN);
        CsvWriter writer = CsvUtil.getWriter(new File("2021-flatten-TOWN-data.csv"), StandardCharsets.UTF_8);
        writer.writeBeans(areaNode);
    }
}
