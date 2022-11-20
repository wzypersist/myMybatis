package com.wzy.mybatisXML.mapping;

import lombok.Data;

import java.util.List;

@Data
public class ColumnProperty {
    private String column;
    private String property;
    private List<String> properties;
    
    
    public void contains(){
        
    }
    
}
