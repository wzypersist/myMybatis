package com.wzy.mybatisXML.utils;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.wzy.mybatisXML.constants.Constant;
import com.wzy.mybatisXML.constants.Constant.SqlType;
import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.mapping.ColumnProperty;
import com.wzy.mybatisXML.mapping.ResultMap;
import com.wzy.mybatisXML.sqlsession.Configuration;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlUtils {
    
    public static void readMapperXml(File fileName, Configuration configuration){
        try{
            SAXReader saxReader = new SAXReader();
            ResultMap rsMap = new ResultMap();
            ColumnProperty columnProperty = new ColumnProperty();
            List<ColumnProperty> columnProperties = new ArrayList<>();
            saxReader.setEncoding(Constant.CHARSET_UTF8);

            Document document = saxReader.read(fileName);
            
            Element rootElement = document.getRootElement();
            if(!Constant.XML_ROOT_LABEL.equals(rootElement.getName())){
                System.err.println("mapper xml文件根元素不是mapper");
                return;
            }
            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);
            List<MappedStatement> statements = new ArrayList<>();
            List<String > properties = new ArrayList<>();
            for(Iterator iterator = rootElement.elementIterator(); iterator.hasNext();){
                Element element = (Element)iterator.next();
                String eleName = element.getName();
                MappedStatement statement = new MappedStatement();
                if("resultMap".equals(eleName)){
                    String id = element.attributeValue(Constant.XML_ELEMENT_ID);
                    String type = element.attributeValue(Constant.XML_TYPE);
                    rsMap.setId(id);
                    rsMap.setType(type);
                    for(Iterator<Element> elementIterator = element.elementIterator();elementIterator.hasNext();){
                        Element e = elementIterator.next();
                        String property = e.attributeValue(Constant.XML_RESULTMAP_PROPERTY);
                        String column =e.attributeValue(Constant.XML_RESULTMAP_COLUMN);
                        properties.add(property);
                        columnProperty.setColumn(column);
                        columnProperty.setProperty(property);
                        columnProperty.setProperties(properties);
                        columnProperties.add(columnProperty);
                        rsMap.setColumnProperties(columnProperties);
                    }
                    continue;
                }
                if(SqlType.SELECT.getValue().equals(eleName)){
                    String resultType = element.attributeValue(Constant.XML_SELECT_RESULTTYPE);
                    String resultMap = element.attributeValue(Constant.XML_SELECT_RESULTMAP);
                    if(resultMap != null){
                        statement.setResultMap(rsMap);
                    }
                    if(resultType != null){
                        statement.setResultType(resultType);
                    }
                    statement.setColumnProperty(columnProperty);
                    statement.setSqlCommandType(SqlType.SELECT);
                }
                else if(SqlType.UPDATE.getValue().equals(eleName)){
                    statement.setSqlCommandType(SqlType.UPDATE);
                }
                else if(SqlType.INSERT.getValue().equals(eleName)){
                    String parameterType = element.attributeValue(Constant.XML_PARAMETERTYPE);
                    statement.setParameterType(parameterType);
                    statement.setSqlCommandType(SqlType.INSERT);
                }
                else{
                    System.err.println("不支持此xml标签解析:" + eleName);
                    statement.setSqlCommandType(SqlType.DEFAULT);
                }
                String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);
                statement.setSqlId(sqlId);
                statement.setSql(CommonUtils.stringTrim(element.getStringValue()));
                statement.setNamespace(namespace);
               
                statements.add(statement);
                System.out.println(statement);
                configuration.addMappedStatement(sqlId,statement);
                //在MapperRegistry中生产一个mapper对应的代理工厂
                configuration.addMapper(Class.forName(namespace));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    } 
    
}
