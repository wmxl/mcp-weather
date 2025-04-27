package spring.ai.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAIMcp {

	public static void main(String[] args) {
		SpringApplication.run(SpringAIMcp.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService, ImageTool imageTool) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService, imageTool).build();
	}

}
