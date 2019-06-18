package com.shapestudio.common.vo;

import lombok.Data;

/**
 * 基础分页查询对象
 */
@Data
public class BaseQuery {
    private int pageIndex = 0;
    private int pageSize = 10;
}
