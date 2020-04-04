package ru.magentasmalltalk.web.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.magentasmalltalk.db")
public class AppConfiguration {
}
