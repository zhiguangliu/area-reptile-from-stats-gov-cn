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

package cn.zhgliu.reptile.area.model;

import cn.zhgliu.reptile.area.consts.AreaLevel;

import java.util.List;

public class AreaNode {
    private String code;

    private String pCode;

    private String name;

    private AreaLevel level;

    private List<AreaNode> children;

    private String currentDetailUrl;

    private String villageCategoryCode;

    public AreaNode(String code, String name, AreaLevel level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public AreaNode setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public AreaNode setName(String name) {
        this.name = name;
        return this;
    }

    public AreaLevel getLevel() {
        return level;
    }

    public AreaNode setLevel(AreaLevel level) {
        this.level = level;
        return this;
    }

    public List<AreaNode> getChildren() {
        return children;
    }

    public AreaNode setChildren(List<AreaNode> children) {
        this.children = children;
        return this;
    }

    public String getVillageCategoryCode() {
        return villageCategoryCode;
    }

    public AreaNode setVillageCategoryCode(String villageCategoryCode) {
        this.villageCategoryCode = villageCategoryCode;
        return this;
    }

    public String getCurrentDetailUrl() {
        return currentDetailUrl;
    }

    public AreaNode setCurrentDetailUrl(String currentDetailUrl) {
        this.currentDetailUrl = currentDetailUrl;
        return this;
    }

    public String getpCode() {
        return pCode;
    }

    public AreaNode setpCode(String pCode) {
        this.pCode = pCode;
        return this;
    }
}
