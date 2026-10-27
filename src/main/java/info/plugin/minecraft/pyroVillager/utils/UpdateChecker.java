package info.plugin.minecraft.pyrovillager.utils;

import org.bukkit.Bukkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.bukkit.configuration.file.FileConfiguration;

public class UpdateChecker {

    public static void checkUpdate(FileConfiguration config) {
        String updateUrl = config.getString("update-url");
        if (updateUrl == null || updateUrl.isEmpty()) {
            return;
        }

        String currentVersion = config.getString("version", "1.0.0");

        try {
            URL url = new URL(updateUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                String response = content.toString().trim();
                if (response.contains("\"version\":")) {
                    String latestVersion = response.split("\"version\":\\s*\"")[1].split("\"")[0];
                    if (!latestVersion.equals(currentVersion)) {
                        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0], () -> {
                            Bukkit.getConsoleSender().sendMessage("§c[PyroVillager] Update available! Current: " + currentVersion + ", Latest: " + latestVersion);
                            Bukkit.getConsoleSender().sendMessage("§eDownload link: " + updateUrl);
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
