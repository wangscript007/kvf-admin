package com.kalvin.kvf.gen.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 表格列配置
 * @author Kalvin
 * @Date 2019/06/13 10:45
 */
@Data
@Accessors(chain = true)
@ToString
public class ColumnConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String comment;
    private String _comment;    // 备注（全）
    private String dataType;
    private String isNullable;  // YES/NO
    private String variableType;   // 成员变量类型
    private boolean sort;
    private boolean format;
}
