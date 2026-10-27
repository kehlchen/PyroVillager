package info.plugin.minecraft.pyrovillager;

import org.bukkit.plugin.java.JavaPlugin;
import info.plugin.minecraft.pyrovillager.utils.UpdateChecker;

public class PyroVillager extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        UpdateChecker.checkUpdate(getConfig());
        getLogger().info("PyroVillager enabled!");
    }
}
