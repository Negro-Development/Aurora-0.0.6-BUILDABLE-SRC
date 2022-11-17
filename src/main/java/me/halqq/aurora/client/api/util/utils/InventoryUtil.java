// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

import me.halqq.aurora.client.api.util.Minecraftable;
import net.minecraft.block.Block;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import scala.tools.nsc.doc.model.Public;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryUtil implements Minecraftable {



    public static int swapToHotbarSlot(int slot, boolean silent){
        if (mc.player.inventory.currentItem == slot || slot < 0 || slot > 8) return slot;
        InventoryUtil.mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));        if (!silent) mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
        return slot;
    }



    public static int getItemSlot(Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (mc.player.inventory.getStackInSlot(i).getItem().equals(item)) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }



    public static void switchToSlot(int slot) {
        if (mc.player.inventory.currentItem == slot || slot == -1) {
            return;
        }
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }





    public static int findItem(Class clazz) {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) continue;
            if (clazz.isInstance(stack.getItem())) {
                return i;
            }
            if (!(stack.getItem() instanceof ItemBlock) || !clazz.isInstance((( ItemBlock ) stack.getItem()).getBlock()))
                continue;
            return i;
        }
        return -1;
    }
}




