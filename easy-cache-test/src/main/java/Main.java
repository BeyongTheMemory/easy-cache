import com.pop.test.TestClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/2/27.
 */
public class Main {
    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/spring-bean.xml"});
        String[] beanNames = context.getBeanNamesForAnnotation(Component.class);
        Object test = context.getBean(beanNames[0]);
        Method[] methods = test.getClass().getMethods();

        TestClass testClass = (TestClass) context.getBean("testClass");
        System.out.println(testClass.getString("a"));
        System.out.println(testClass.getString("a"));
    }
}
