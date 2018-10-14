package overwrite.spring.context;

import overwrite.spring.beans.HomeBeanDefinition;
import overwrite.spring.context.support.HomeBeanDefinitionReader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jam
 * @description
 * @create 2018-09-16
 **/
public class HomeApplicationContext {

    private String[] configLocations;
    private HomeBeanDefinitionReader reader;
    private ConcurrentHashMap<String,HomeBeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    public HomeApplicationContext(){}

    public HomeApplicationContext(String ... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }


    public Object getBean(String beanName) {
        return null;
    }

    public void refresh(){
        //locate
        this.reader = new HomeBeanDefinitionReader(configLocations);

        //load
        List<String> beanClassNames = this.reader.loadBeanDefinitions();

        //registry
        registry(beanClassNames);

        //autowire
        autowire();
        System.out.println(beanDefinitions);
    }

    private void autowire() {

    }

    private void registry(List<String> beanClassNames) {
        for (String name : beanClassNames){
            try {
                Class<?> beanClass = Class.forName(name);
                if (beanClass.isInterface()){
                    continue;
                }
                HomeBeanDefinition beanDefinition = this.reader.registryBean(name);
                beanDefinitions.put(beanDefinition.getFactoryBeanName(),beanDefinition);

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class i : interfaces){
                    beanDefinitions.put(i.getName(),beanDefinition);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
