package com.wzy.mybatisXML.mybatisSpring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class ImportBeanDefinitionRegistart implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName());
        String path = (String) annotationAttributes.get("value");
        BeanDefinitionScaner scanner = new BeanDefinitionScaner(registry);
        
        scanner.addIncludeFilter(((metadataReader, metadataReaderFactory) -> true));
        
        scanner.scan(path);
    }
}
