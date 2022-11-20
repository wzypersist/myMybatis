package com.wzy.mybatisXML.mapping;

import lombok.Data;

import java.util.List;

@Data
public class ResultMap {
    private String id;
    private String type;
    private List<ColumnProperty> columnProperties;
    private ColumnProperty columnProperty;
}
