package overwrite.spring.beans;

/**
 * @author jam
 * @description
 * @create 2018-09-26
 **/
public class HomeBeanDefinition {
    private String beanName;
    private String factoryBeanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
