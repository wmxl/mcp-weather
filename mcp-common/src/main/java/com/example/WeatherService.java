package com.example;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Tool(description = "Get weather forecast for a city")
    public String getWeatherForCityTool(@ToolParam(description = "City name (e.g., Beijing, Shanghai, NY)") String city) {
        switch (city.toLowerCase()) {
            case "beijing":
                return "Beijing Weather:\n" +
                        "Temperature: 25°C\n" +
                        "Condition: Sunny with occasional haze\n" +
                        "Humidity: 45%\n" +
                        "Wind: Light breeze from northeast";

            case "shanghai":
                return "Shanghai Weather:\n" +
                        "Temperature: 28°C\n" +
                        "Condition: Partly cloudy with afternoon showers\n" +
                        "Humidity: 75%\n" +
                        "Wind: Moderate southeasterly winds";

            case "new york":
            case "ny":
                return "New York Weather:\n" +
                        "Temperature: 18°C\n" +
                        "Condition: Clear skies\n" +
                        "Humidity: 55%\n" +
                        "Wind: Brisk westerly wind";

            default:
                return "Weather information not available for " + city + ".";
        }
    }

    @Tool(description = "Get beijing weather")
    public String getBeijingWeather() {
        return getWeatherForCityTool("beijing");
    }
} 