package fr.nilowk.chat.listeners;

import fr.nilowk.chat.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class PlayerChatAsync implements Listener {

    private Main instance;
    private FileConfiguration config;
    private Map<Integer, String> distanceColor;

    public PlayerChatAsync(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.distanceColor = new HashMap<>();

    }

    @EventHandler
    public void onChatAsync(AsyncPlayerChatEvent event) {

        ConfigurationSection cf = this.config.getConfigurationSection("distance_color");
        this.loadDistanceColor(cf);

        Player player = event.getPlayer();

        for (Player pl : player.getWorld().getPlayers()) {

            if (!(((int) player.getLocation().distance(pl.getLocation())) > config.getInt("distance_color.max"))) {

                String color = getColor(player, pl);
                String format = config.getString("style.format")
                        .replace("{COLOR}", color)
                        .replace("{NAME}", player.getName())
                        .replace("{MESSAGE}", event.getMessage());

                pl.sendMessage(format);

            }

        }

        event.setCancelled(true);

    }

    private void loadDistanceColor(ConfigurationSection cf) {

        for (String dist : cf.getKeys(false)) {

            int distance = Integer.parseInt(dist);
            String color = cf.getString(dist).replace("&", "§");

            this.distanceColor.put(distance, color);

        }

    }

    private String getColor(Player player, Player pl) {

        List<Integer> distance = new ArrayList<>();

        for (int key : distanceColor.keySet()) {

            distance.add(key);

        }

        for (int i = 0; i < distanceColor.keySet().size(); i++) {

            Integer max = Collections.max(distance);
            int distanceBetween = (int) player.getLocation().distance(pl.getLocation());

            if (distanceBetween >= max.intValue()) {

                return distanceColor.get(max);

            } else {

                distance.remove(max);

            }

        }

        return "";

    }

}
