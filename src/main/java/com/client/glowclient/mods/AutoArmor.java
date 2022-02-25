//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.pepsimod.PUtils;
import com.client.glowclient.utils.extra.wurst.WPlayerController;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoArmor
extends ToggleMod {
    private SimpleTimer timer = new SimpleTimer();

    public AutoArmor() {
        super(Category.COMBAT, "AutoArmor", false, -1, "Automatically equip best armor");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        try{
        if (!this.timer.isStarted()) {
            this.timer.start();
        } else if (this.timer.hasTimeElapsed(0.0)) {
            this.timer.start();
            int[] bestArmorValues = new int[4];
            for (int type = 0; type < 4; ++type) {
                ItemStack oldArmor = Globals.MC.player.inventory.armorItemInSlot(type);
                if (!(oldArmor.getItem() instanceof ItemArmor)) continue;
                bestArmorValues[type] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            int[] bestArmorSlots = new int[]{-1, -1, -1, -1};
            for (int slot = 0; slot < 36; ++slot) {
                int type;
                ItemStack stack = Globals.MC.player.inventory.getStackInSlot(slot);
                ItemArmor armor = (ItemArmor)stack.getItem();
                if (!(stack.getItem() instanceof ItemArmor) || armor.damageReduceAmount <= bestArmorValues[type = PUtils.getArmorType(armor = (ItemArmor)stack.getItem())]) continue;
                bestArmorValues[type] = armor.damageReduceAmount;
                bestArmorSlots[type] = slot;
            }
            for (int type = 0; type < 4; ++type) {
                int slot = bestArmorSlots[type];
                if (slot == -1) continue;
                WPlayerController.windowClick_PICKUP(slot < 9 ? 36 + slot : slot);
                WPlayerController.windowClick_PICKUP(8 - type);
                WPlayerController.windowClick_PICKUP(slot < 9 ? 36 + slot : slot);
            }
        }
        }catch(Exception e){//Empty Try Catch Block...}}
}

