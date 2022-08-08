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

package cn.zhgliu.reptile.area.consts;

public enum AreaLevel {
    LV1(1, "province"),
    LV2(1, "city"),
    LV3(1, "county"),
    LV4(1, "town"),
    LV5(1, "village");

    private Integer levelCode;
    private String levelName;

    private AreaLevel(Integer levelCode, String levelName) {
        this.levelCode = levelCode;
        this.levelName = levelName;
    }

    public Integer getLevelCode() {
        return levelCode;
    }

    public String getLevelName() {
        return levelName;
    }

    public static void main(String[] args) {
        System.out.println(AreaLevel.LV1.getLevelCode());
    }
}
