package com.wzy.mybatisXML.constants;

public interface Constant {
    
    String CHARSET_UTF8 = "UTF-8";
    
    String MAPPER_LOCATION = "mapper.location";
    
    String DB_DRIVER = "db.driver";
    
    String DB_URL = "db.url";
    
    String DB_USERNAME = "db.username";
    
    String DB_PASSWORD = "db.password";

    String MAPPER_FILE_SUFFIX = ".xml";

    String XML_ROOT_LABEL = "mapper";
    
    String XML_ELEMENT_ID = "id";
    
    String XML_SELECT_NAMESPACE = "namespace";
    
    String XML_SELECT_RESULTTYPE = "resultType";
    
    String XML_SELECT_RESULTMAP = "resultMap";
    String XML_TYPE = "type";
    String XML_RESULTMAP_PROPERTY = "property";
    String XML_RESULTMAP_COLUMN = "column";
    
    
    String XML_PARAMETERTYPE = "parameterType";
    
    public enum SqlType{
        SELECT("select"),
        INSERT("insert"),
        UPDATE("update"),
        DEFAULT("default");
        
        private String value;
        
        private SqlType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
}
