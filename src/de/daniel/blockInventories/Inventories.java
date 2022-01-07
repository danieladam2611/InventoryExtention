package de.daniel.blockInventories;

import cn.nukkit.plugin.PluginBase;

public class Inventories extends PluginBase {

    private static Inventories instance;

    public void onEnable() {
        instance = this;
        getLogger().info("§b" + getName() + " §fwas successfully §aEnabled");
    }
}
