package py.com.prestosoftware;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import py.com.prestosoftware.ui.main.MainController;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class StockApplication  {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(StockApplication.class).headless(false).run(args);
        MainController mainMenuController = context.getBean(MainController.class);
        mainMenuController.prepareAndOpenFrame();
	}	
}