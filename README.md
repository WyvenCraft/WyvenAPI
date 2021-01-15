# WyvenCore - API

### IN DEVELOPEMENT!

This i an API for my plugin WyvenCore, which is made for my upcoming minecraft server. OFFICIAL DISCORD SERVER: https://discord.gg/DEZfvFAmWc

See the wiki: https://core.wyvencraft.com ()

# Integration

[![](https://jitpack.io/v/WyvenCraft/WyvenCore.svg)](https://jitpack.io/#WyvenCraft/WyvenCore)

**_Include with Maven_**

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.WyvenCraft</groupId>
	  <artifactId>WyvenAPI</artifactId>
	  <version>VERSION</version>
</dependency>
```

**_Include with Gradle_**

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.WyvenCraft:WyvenAPI:-SNAPSHOT'
}
```
Alternatively, you can download the jar from: https://github.com/WyvenCraft/WyvenAPI/releases/tag/1.0.0

## Implementing WyvenCore

Add "_softdepend: [WyvenCore]_" to your plugin.yml
```
private final WyvenCore wyven;

public WyvenIntegration(Plugin yourPlugin) {
    this.wyven = new WyvenCore(yourPlugin);
}

public void test(Player player) {
    final LanguageManger lang = wyven.getLang();
    final String message = lang.getMessageColored("some-message");
    
    player.sendMessage(message);
}
```

### Addons implementation coming soon.
