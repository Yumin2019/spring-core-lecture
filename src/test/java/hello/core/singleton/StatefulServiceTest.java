package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // thread a: a사용자가 10000원 주문
        int aPrice = statefulService1.order("userA", 10000);

        // thread b: b사용자가 20000원 주문
        int bPrice = statefulService2.order("userB", 20000);

        // thread a: a사용자가 주문 금액을 조회
        System.out.println("userA price = " + aPrice);
        System.out.println("userA price = " + bPrice);

        Assertions.assertThat(aPrice).isEqualTo(10000);
        Assertions.assertThat(bPrice).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}