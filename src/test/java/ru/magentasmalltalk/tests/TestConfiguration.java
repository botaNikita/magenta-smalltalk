package ru.magentasmalltalk.tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.magentasmalltalk.web.configurations.ProdConfiguration;
import ru.magentasmalltalk.web.configurations.WebConfiguration;

@Configuration
@ComponentScan(basePackages = { "ru.magentasmalltalk.web", "ru.magentasmalltalk.db" },
                excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = { ProdConfiguration.class, WebConfiguration.class}))
public class TestConfiguration {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName("TestPersistenceUnit");
        return bean;
    }
}
