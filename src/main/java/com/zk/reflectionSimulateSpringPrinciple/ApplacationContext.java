package com.zk.reflectionSimulateSpringPrinciple;

import com.zk.demo.Foo;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此示例演示java反射机制实现spring底层的动态加载类到容器并创建对象是如何实现的
 */
public class ApplacationContext {

    //缓存Spring容器的bean对象
    private Map<String,Object> beans = new HashMap<>();

    //构造方法传参,利用配置文件初始化当前容器
    public ApplacationContext(String xml) throws Exception{

        //利用dom4j读取配置文件,从resources（ClassPath）中读取流
        InputStream in = getClass().getClassLoader().getResourceAsStream(xml);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(in);
        in.close();

        //解析配置文件内容，得到beanname和id
        Element root = doc.getRootElement();
        List<Element> list = root.elements("bean");
        for (Element e : list) {
            String id = e.attributeValue("id");
            String classname = e.attributeValue("class");

            //根据类名动态加载类并创建对象
            Class cls = Class.forName(classname);
            Object bean = cls.newInstance();

            //将对应的id和对象添加到map中
            beans.put(id,bean);
        }

    }

    //通过id获取bean对象
    public Object getBean(String id){
        //根据id查找map并返回bean对象
        return beans.get(id);
    }

    //泛型方法，优点是可以减少一次类型转换
    public <T> T getBean(String id, Class<T> fooClass) {
        return (T)beans.get(id);
    }
}
