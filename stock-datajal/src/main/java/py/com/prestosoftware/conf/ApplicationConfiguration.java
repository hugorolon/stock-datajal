package py.com.prestosoftware.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "py.com.prestosoftware")
@EnableJpaRepositories(basePackages = "py.com.prestosoftware.data.repository")
public class ApplicationConfiguration {
}
