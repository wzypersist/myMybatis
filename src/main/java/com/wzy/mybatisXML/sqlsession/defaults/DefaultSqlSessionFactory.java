package com.wzy.mybatisXML.sqlsession.defaults;

import com.sun.javaws.jnl.XMLUtils;
import com.wzy.mybatisXML.constants.Constant;
import com.wzy.mybatisXML.sqlsession.Configuration;
import com.wzy.mybatisXML.sqlsession.SqlSession;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactory;
import com.wzy.mybatisXML.utils.CommonUtils;
import com.wzy.mybatisXML.utils.XmlUtils;

import java.io.File;
import java.net.URL;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMappersInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.","/"));
    }

    private void loadMappersInfo(String dirName) {
        URL resource = this.getClass().getClassLoader().getResource(dirName);
        File mapperDir = new File(resource.getFile());
        if(mapperDir.isDirectory()){
            File[] mappers = mapperDir.listFiles();
            if(CommonUtils.isNotEmpty(mappers)){
                for (File file : mappers) {
                    if(file.isDirectory()){
                        loadMappersInfo(dirName + "/" + file.getName());
                    }
                    else if(file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)){
                        XmlUtils.readMapperXml(file,this.configuration);
                    }
                }
            }
        }
    }

    @Override
    public SqlSession openSession() {
        SqlSession session = new DefaultSqlSession(this.configuration);
        return session;
    }
}
