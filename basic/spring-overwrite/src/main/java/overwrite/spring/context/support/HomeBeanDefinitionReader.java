package overwrite.spring.context.support;

import overwrite.spring.beans.HomeBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author jam
 * @description
 * @create 2018-09-26
 **/
public class HomeBeanDefinitionReader {
    private String[] locations;

    private Properties config = new Properties();

    private List<String> beanClassNames = new ArrayList<>();


    public HomeBeanDefinitionReader(String ...locations){
        this.locations = locations;
    }
    public List<String> loadBeanDefinitions(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(inputStream);
            doScan(config.getProperty("scanPackage"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanClassNames;
    }

    private void doScan(String packageName) {
        URL resource = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        assert resource != null;
        File file = new File(resource.getFile());
        for (File tmp : Objects.requireNonNull(file.listFiles())){
            if (tmp.isDirectory()){
                doScan(packageName + "." + tmp.getName());
            }else {
                beanClassNames.add(packageName + "." + tmp.getName().replace(".class",""));
            }
        }
    }

    public HomeBeanDefinition registryBean(String beanName) {
        if (beanClassNames.contains(beanName)){
            HomeBeanDefinition beanDefinition = new HomeBeanDefinition();
            beanDefinition.setBeanName(beanName);
            beanDefinition.setFactoryBeanName(lowerFirstCase(beanName.substring(beanName.lastIndexOf(".")+1)));
            return beanDefinition;
        }
        return null;
    }

    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
