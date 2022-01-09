package fr.nilowk.chat;

import fr.nilowk.chat.listeners.PlayerChatAsync;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new PlayerChatAsync(this), this);

    }

}
