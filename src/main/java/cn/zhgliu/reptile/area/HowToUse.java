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

package cn.zhgliu.reptile.area;

import cn.hutool.json.JSONUtil;
import cn.zhgliu.reptile.area.consts.AreaLevel;
import cn.zhgliu.reptile.area.model.AreaNode;
import cn.zhgliu.reptile.area.util.AreaReptileUtil;

import java.util.List;

public class HowToUse {
    public static void main(String[] args) {
        List<AreaNode> areaNode = AreaReptileUtil.doCrawl("2021",AreaLevel.LV1);
        System.out.println(JSONUtil.toJsonStr(areaNode));

    }
}
