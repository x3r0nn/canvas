package org.example;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.inv.ClickableItem;
import org.example.inv.content.InventoryContents;
import org.example.inv.content.InventoryProvider;
import org.example.inv.content.Pagination;
import org.example.inv.content.SlotIterator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlockGUI implements InventoryProvider {

    private List<Material> materials;
    private Block block;

    public BlockGUI(Block block) {
        this.materials = Main.getInstance().getManager().getBlocks();
        this.block = block;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Pagination pagination = contents.pagination();


        ClickableItem[] items = new ClickableItem[materials.size()];
        int i = 0;
        for(Material material: materials) {

            ItemStack is = new ItemStack(material);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("§f"+Arrays.stream(material.name().split("_")).map(word -> word.substring(0, 1) + word.substring(1).toLowerCase()).collect(Collectors.joining(" ")));
            is.setItemMeta(im);
            items[i] = (ClickableItem.of(is,inventoryClickEvent -> {
                block.setType(material);
                Main.getInstance().getManager().getCanvasPlayer(player.getName()).setLastInteraction(System.currentTimeMillis());
                Main.getInstance().getManager().getCanvasBlockByLocation(block.getLocation()).setLastPlayerInteracted(player.getName());
                player.closeInventory();
            }));
            i++;
        }
        pagination.setItems(items);
        pagination.setItemsPerPage(45);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        ItemStack next = new ItemStack(Material.ARROW);
        ItemMeta nextM = next.getItemMeta();
        nextM.setDisplayName("§fDalší");
        next.setItemMeta(nextM);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backM = back.getItemMeta();
        backM.setDisplayName("§fZpět");
        back.setItemMeta(backM);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitM = exit.getItemMeta();
        exitM.setDisplayName("§fZrušit");
        exit.setItemMeta(exitM);

        contents.fillRow(5, ClickableItem.empty(ItemStack.of(Material.BLACK_STAINED_GLASS_PANE)));

        if(materials.size()>45) {
            contents.set(5, 5, ClickableItem.of(next,
                    e -> Main.getInstance().getController().open(player, block).open(player, pagination.next().getPage())));
            contents.set(5, 3, ClickableItem.of(back,
                    e -> Main.getInstance().getController().open(player, block).open(player, pagination.previous().getPage())));
        }
        contents.set(5,8,ClickableItem.of(exit,inventoryClickEvent -> {
            player.closeInventory();
        }));
    }
}
