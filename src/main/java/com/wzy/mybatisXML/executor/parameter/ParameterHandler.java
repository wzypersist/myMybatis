package com.wzy.mybatisXML.executor.parameter;

import java.sql.PreparedStatement;

public interface ParameterHandler {
    
    void setParameters(PreparedStatement paramPrepareedStatement);
    
}
