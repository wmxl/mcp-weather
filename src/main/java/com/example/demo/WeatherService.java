package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class WeatherService {

    private final RestClient restClient;

    public WeatherService() {
        this.restClient = RestClient.builder()
            .baseUrl("https://api.weather.gov")
            .defaultHeader("Accept", "application/geo+json")
            .defaultHeader("User-Agent", "WeatherApiClient/1.0 (example@example.com)")
            .build();
    }

    public String getWeatherForecastByLocation(
        double latitude,   // Latitude coordinate
        double longitude   // Longitude coordinate
    ) {
        try {
            // First get the forecast grid endpoint
            Map<String, Object> pointsData = restClient.get()
                .uri("/points/{latitude},{longitude}", latitude, longitude)
                .retrieve()
                .body(Map.class);

            if (pointsData == null) {
                return "Unable to fetch forecast data for this location.";
            }

            // Get the forecast URL from the points response
            Map<String, Object> properties = (Map<String, Object>) pointsData.get("properties");
            String forecastUrl = (String) properties.get("forecast");

            // Get the forecast data
            Map<String, Object> forecastData = restClient.get()
                .uri(forecastUrl)
                .retrieve()
                .body(Map.class);

            if (forecastData == null) {
                return "Unable to fetch detailed forecast.";
            }

            // Format the periods into a readable forecast
            Map<String, Object> forecastProperties = (Map<String, Object>) forecastData.get("properties");
            java.util.List<Map<String, Object>> periods = (java.util.List<Map<String, Object>>) forecastProperties.get("periods");

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < Math.min(5, periods.size()); i++) {
                Map<String, Object> period = periods.get(i);
                result.append(period.get("name")).append(":\n");
                result.append("Temperature: ").append(period.get("temperature")).append("°").append(period.get("temperatureUnit")).append("\n");
                result.append("Wind: ").append(period.get("windSpeed")).append(" ").append(period.get("windDirection")).append("\n");
                result.append("Forecast: ").append(period.get("detailedForecast")).append("\n\n");
            }

            return result.toString();
        } catch (Exception e) {
            return "Error fetching forecast: " + e.getMessage();
        }
    }

    public String getAlerts(String state) {
        try {
            Map<String, Object> alertsData = restClient.get()
                .uri("/alerts/active/area/{state}", state)
                .retrieve()
                .body(Map.class);

            if (alertsData == null || !alertsData.containsKey("features")) {
                return "Unable to fetch alerts or no alerts found.";
            }

            java.util.List<Map<String, Object>> features = (java.util.List<Map<String, Object>>) alertsData.get("features");
            if (features.isEmpty()) {
                return "No active alerts for this state.";
            }

            StringBuilder result = new StringBuilder();
            for (Map<String, Object> feature : features) {
                Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
                result.append("Event: ").append(properties.getOrDefault("event", "Unknown")).append("\n");
                result.append("Area: ").append(properties.getOrDefault("areaDesc", "Unknown")).append("\n");
                result.append("Severity: ").append(properties.getOrDefault("severity", "Unknown")).append("\n");
                result.append("Description: ").append(properties.getOrDefault("description", "No description available")).append("\n");

                Object instruction = properties.get("instruction");
                if (instruction != null) {
                    result.append("Instructions: ").append(instruction).append("\n");
                } else {
                    result.append("Instructions: No specific instructions provided\n");
                }

                result.append("\n---\n\n");
            }

            return result.toString();
        } catch (Exception e) {
            return "Error fetching alerts: " + e.getMessage();
        }
    }
}
