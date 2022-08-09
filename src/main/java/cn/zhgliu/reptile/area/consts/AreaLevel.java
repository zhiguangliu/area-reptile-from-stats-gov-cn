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
    PROVINCE(1, "province"),
    CITY(2, "city"),
    COUNTY(3, "county"),
    TOWN(4, "town"),
    VILLAGE(5, "village");

    private Integer levelCode;
    private String levelName;

    AreaLevel(Integer levelCode, String levelName) {
        this.levelCode = levelCode;
        this.levelName = levelName;
    }

    public Integer getLevelCode() {
        return levelCode;
    }

    public static AreaLevel nextLevel(AreaLevel areaLevel){
        switch (areaLevel){
            case PROVINCE:
                return CITY;
            case CITY:
                return COUNTY;
            case COUNTY:
                return TOWN;
            case TOWN:
                return VILLAGE;
            default:
                throw new RuntimeException("Can not get next level of LV5");
        }
    }

    public AreaLevel nextLevel(){
        switch (this){
            case PROVINCE:
                return CITY;
            case CITY:
                return COUNTY;
            case COUNTY:
                return TOWN;
            case TOWN:
                return VILLAGE;
            default:
                throw new RuntimeException("Can not get next level of LV5");
        }
    }

    public String getLevelName() {
        return levelName;
    }

    public static void main(String[] args) {
        System.out.println(AreaLevel.PROVINCE.getLevelCode());
    }
}
