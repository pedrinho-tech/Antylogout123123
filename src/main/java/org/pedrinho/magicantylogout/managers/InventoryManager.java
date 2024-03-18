package org.pedrinho.magicantylogout.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.pedrinho.magicantylogout.utils.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager {

    public Inventory mainInventory;
    public Inventory configurationInventory;

    public void openMainInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, Chat.color(" &8» &d&lMAGIC&f&lANTYLOGOUT"));
        purpleGlass(inventory);

        ItemStack whiteGlassIS = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);

        ItemMeta whiteGlassIM = whiteGlassIS.getItemMeta();
        whiteGlassIM.setDisplayName(" ");
        whiteGlassIS.setItemMeta(whiteGlassIM);

        int[] whiteGlass = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

        for (int glass : whiteGlass) {
            inventory.setItem(glass, whiteGlassIS);
        }

        ItemStack versionIS = new ItemStack(Material.NAME_TAG, 1);

        ItemMeta versionIM = versionIS.getItemMeta();
        versionIM.setDisplayName(Chat.color(" &dWersja"));
        List<String> versionLore = new ArrayList<>();
        versionLore.add(" &8» &fSprawdź, czy jest dostępna");
        versionLore.add(" &8» &fnowsza &dwersja &fpluginu.");
        versionIM.setLore(Chat.colorList(versionLore));
        versionIS.setItemMeta(versionIM);

        inventory.setItem(20, versionIS);


        ItemStack deleteIS = new ItemStack(Material.RED_DYE, 1);

        ItemMeta deleteIM = deleteIS.getItemMeta();
        deleteIM.setDisplayName(Chat.color(" &dUsuń Antylogout"));
        List<String> deleteLore = new ArrayList<>();
        deleteLore.add(" &8» &fUsuwa &dAntyLogout &f z");
        deleteLore.add(" &8» &fwszystkich graczy. ");
        deleteIM.setLore(Chat.colorList(deleteLore));
        deleteIS.setItemMeta(deleteIM);

        inventory.setItem(22, deleteIS);

        ItemStack reloadIS = new ItemStack(Material.GREEN_DYE, 1);

        ItemMeta reloadIM = reloadIS.getItemMeta();
        reloadIM.setDisplayName(Chat.color(" &dReload"));
        List<String> reloadLore = new ArrayList<>();
        reloadLore.add(Chat.color(" &8» &fPrzeładuj &dplik konfiguracyjny"));
        reloadLore.add(Chat.color(" &8» &fpluginu."));
        reloadIM.setLore(reloadLore);
        reloadIS.setItemMeta(reloadIM);

        inventory.setItem(24, reloadIS);

        ItemStack configurationIS = new ItemStack(Material.OAK_SIGN, 1);

        ItemMeta configurationIM = configurationIS.getItemMeta();
        configurationIM.setDisplayName(Chat.color(" &dKonfiguruj"));
        List<String> configurationLore = new ArrayList<>();
        configurationLore.add(Chat.color(" &8» &fKonfiguruj &dplugin &fz poziomu"));
        configurationLore.add(Chat.color(" &8» &fMinecrafta."));
        configurationIM.setLore(configurationLore);
        configurationIS.setItemMeta(configurationIM);

        //inventory.setItem(24, configurationIS);

        ItemStack creditsIS = new ItemStack(Material.PAPER, 1);

        ItemMeta creditsIM = creditsIS.getItemMeta();
        creditsIM.setDisplayName(Chat.color("&d&o© MagicSolutions"));

        creditsIS.setItemMeta(creditsIM);

        inventory.setItem(40, creditsIS);

        this.mainInventory = inventory;
        player.openInventory(inventory);
    }

    private void purpleGlass(Inventory inventory) {
        int[] purpleGlass = {0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};

        ItemStack purpleGlassIS = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);

        ItemMeta purpleGlassIM = purpleGlassIS.getItemMeta();
        purpleGlassIM.setDisplayName(" ");
        purpleGlassIS.setItemMeta(purpleGlassIM);

        for (int glass : purpleGlass) {
            inventory.setItem(glass, purpleGlassIS);
        }

        int[] pinkGlass = {2, 3, 4, 5, 6, 18, 26, 27, 35, 47, 48, 49, 50, 51};
        ItemStack pinkGlassIS = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);

        ItemMeta pinkGlassIM = pinkGlassIS.getItemMeta();
        pinkGlassIM.setDisplayName(" ");
        pinkGlassIS.setItemMeta(pinkGlassIM);

        for (int glass : pinkGlass) {
            inventory.setItem(glass, pinkGlassIS);
        }
    }

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, Chat.color(" &8» &d&lKONFIGURACJA"));

        purpleGlass(inventory);

        ItemStack antylogoutDurationIS = new ItemStack(Material.COMPASS, 1);
        ItemMeta antylogoutDurationIM = antylogoutDurationIS.getItemMeta();
        antylogoutDurationIM.setDisplayName(Chat.color("&dCzas trwania"));
        antylogoutDurationIS.setItemMeta(antylogoutDurationIM);
        antylogoutDurationIM.setLore(Collections.singletonList("S"));

        inventory.setItem(10, antylogoutDurationIS);

        this.configurationInventory = inventory;

        player.openInventory(inventory);
    }
}
