package org.pedrinho.magicantylogout.configuration;

import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private final JavaPlugin plugin;

    private Integer AntylogoutDuration;

    private boolean ActionbarEnabled;
    private String ActionbarDuringCombat;
    private String ActionbarEndCombat;
    private boolean MessageEnabled;
    private String MessageStartCombat;
    private String MessageEndCombat;
    private boolean BossbarEnabled;
    private boolean BossbarProgressSolid;
    private BarColor BossbarColor;
    private BarStyle BossbarStyle;
    private String BossbarDuringCombat;
    private boolean BroadcastEnabled;
    private String BroadcastLogout;

    private String CommandsMessage;
    private List<String> whitelistCommands = new ArrayList<>();

    private String BlocksMessage;
    private List<Material> BlocksBlacklist = new ArrayList<>();

    private List<String> RegionsList = new ArrayList<>();

    private String CommandNopermission;

    public Config(JavaPlugin plugin) {
        try {
            plugin.saveDefaultConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.plugin = plugin;

        this.AntylogoutDuration = plugin.getConfig().getInt("ANTYLOGOUT.DURATION");
        this.ActionbarEnabled = plugin.getConfig().getBoolean("ACTIONBAR.ENABLED");
        this.ActionbarDuringCombat = plugin.getConfig().getString("ACTIONBAR.DURING-COMBAT");
        this.ActionbarEndCombat = plugin.getConfig().getString("ACTIONBAR.END-COMBAT");
        this.MessageEnabled = plugin.getConfig().getBoolean("MESSAGE.ENABLED");
        this.MessageStartCombat = plugin.getConfig().getString("MESSAGE.START-COMBAT");
        this.MessageEndCombat = plugin.getConfig().getString("MESSAGE.END-COMBAT");
        this.BossbarEnabled = plugin.getConfig().getBoolean("BOSSBAR.ENABLED");
        this.BossbarProgressSolid = plugin.getConfig().getBoolean("BOSSBAR.PROGRESS-SOLID");

        this.BossbarColor = BarColor.valueOf(plugin.getConfig().getString("BOSSBAR.COLOR"));
        this.BossbarStyle = BarStyle.valueOf(plugin.getConfig().getString("BOSSBAR.STYLE"));
        this.BossbarDuringCombat = plugin.getConfig().getString("BOSSBAR.DURING-COMBAT");

        BroadcastEnabled = plugin.getConfig().getBoolean("BROADCAST.ENABLED");
        BroadcastLogout = plugin.getConfig().getString("BROADCAST.LOGOUT");

        CommandsMessage = plugin.getConfig().getString("COMMANDS.MESSAGE");
        whitelistCommands = plugin.getConfig().getStringList("COMMANDS.WHITELIST");

        BlocksMessage = plugin.getConfig().getString("BLOCKS.MESSAGE");
        for (String string : plugin.getConfig().getStringList("BLOCKS.BLACKLIST")) {
            BlocksBlacklist.add(Material.valueOf(string));
        }

        RegionsList = plugin.getConfig().getStringList("REGIONS.BLOCKED-REGIONS");

        CommandNopermission = plugin.getConfig().getString("ANTYLOGOUT-COMMAND.NOPERMISSION");

    }

    public Integer getAntylogoutDuration() {
        return AntylogoutDuration;
    }

    public String getActionbarDuringCombat() {
        return ActionbarDuringCombat;
    }

    public String getActionbarEndCombat() {
        return ActionbarEndCombat;
    }

    public String getMessageStartCombat() {
        return MessageStartCombat;
    }


    public String getMessageEndCombat() {
        return MessageEndCombat;
    }

    public String getBossbarDuringCombat() {
        return BossbarDuringCombat;
    }

    public void reloadConfig() {
        this.plugin.reloadConfig();
    }

    public boolean isActionbarEnabled() {
        return ActionbarEnabled;
    }

    public boolean isMessageEnabled() {
        return MessageEnabled;
    }

    public boolean isBossbarEnabled() {
        return BossbarEnabled;
    }

    public boolean isBossbarProgressSolid() {
        return BossbarProgressSolid;
    }

    public BarColor getBossbarColor() {
        return BossbarColor;
    }

    public BarStyle getBossbarStyle() {
        return BossbarStyle;
    }

    public boolean isBroadcastEnabled() {
        return BroadcastEnabled;
    }

    public String getBroadcastLogout() {
        return BroadcastLogout;
    }

    public String getCommandsMessage() {
        return CommandsMessage;
    }

    public List<String> getWhitelistCommands() {
        return whitelistCommands;
    }

    public String getBlocksMessage() {
        return BlocksMessage;
    }

    public List<Material> getBlocksBlacklist() {
        return BlocksBlacklist;
    }

    public List<String> getRegionsList() {
        return RegionsList;
    }

    public String getCommandNopermission() {
        return CommandNopermission;
    }
}