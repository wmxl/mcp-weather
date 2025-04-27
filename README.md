# Weather MCP Server

This is a simple Java implementation of a Model Context Protocol (MCP) server that provides weather information tools.

## Features

The server exposes two MCP tools:

1. `getWeatherForecastByLocation` - Get weather forecast for a specific latitude/longitude
2. `getAlerts` - Get weather alerts for a US state

## Prerequisites

- Java 17 or higher
- Maven

## Building the Server

```bash
./mvnw clean install
```

This will generate a JAR file in the `target` directory.

## Running the Server

```bash
java -jar target/mcp-weather-stdio-server-0.0.1-SNAPSHOT
```

## Configuring Claude for Desktop

To use this MCP server with Claude for Desktop:

1. Make sure you have Claude for Desktop installed and updated to the latest version.
2. Open your Claude for Desktop App configuration at `~/Library/Application Support/Claude/claude_desktop_config.json` (create the file if it doesn't exist).
3. Add your server configuration:

```json
{
  "mcpServers": {
    "weather": {
      "command": "java",
      "args": [
        "-jar",
        "/ABSOLUTE/PATH/TO/mcp-weather-stdio-server-0.0.1-SNAPSHOT"
      ]
    }
  }
}
```

Replace `/ABSOLUTE/PATH/TO/` with the absolute path to your JAR file.

4. Save the file and restart Claude for Desktop.

## Testing the Server

In Claude for Desktop, you can test the server with queries like:

- "What's the weather in Sacramento?" (will use latitude/longitude for Sacramento)
- "What are the active weather alerts in Texas?"

Note: This server uses the US National Weather Service API, so queries will only work for US locations.
