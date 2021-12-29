package py.com.prestosoftware;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import py.com.prestosoftware.ui.main.MainController;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class StockApplication  {

	private static final Logger LOGGER=LoggerFactory.getLogger(StockApplication.class);
	
	public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(StockApplication.class).headless(false).run(args);
        MainController mainMenuController = context.getBean(MainController.class);
        mainMenuController.prepareAndOpenFrame();
        LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
	}	
}