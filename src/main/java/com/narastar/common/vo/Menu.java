package com.narastar.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu {
    private String menuId;
    private String menuName;
    private String menuUrl;
    private String parentId;
    private int sortOrder;
    private int level;
    private String orderPath;
}
