## 🎮 How to use
You can use query placeholders in any plugin that has PlaceholderAPI support

## 🖨️ Placeholders
`%query_player_count<server_name>%` - Placeholder for display the number of players

`%query_max_players<server_name>%` - Placeholder for display the maximum number of players

`%query_motd<server_name>%` - Placeholder for display server motd

`%query_minecraft_version<server_name>%` - Placeholder for display minecraft server version

## ⚙️ Plugin сonfiguration
```yml
# Timeout time for query request
timeout: 2000 # In milliseconds

# Interval for updating query information
update: 1200 # In ticks

# Servers for query
servers:
  zeqa:                 # Server name
    address: "zeqa.net" # Server address
    port: 19132         # Server port
  nethergames:
    address: "play.nethergames.org"
    port: 19132
```

## 🛠 API
Getting QueryPlaceholders class
```java
QueryPlaceholders queryPlaceholders = QueryPlaceholders.getInstance();
```
Getting query of server
```java
BedrockQueryResponse query = queryPlaceholders.getQuery("server_name");
```
Getting ServerEntry class by name
```java
ServerEntry serverEntry = queryPlaceholders.getServer("server_name");
```
Getting BedrockQuery class
```java
BedrockQuery bedrockQuery = BedrockQueryFactory.getBedrockQuery();
```
