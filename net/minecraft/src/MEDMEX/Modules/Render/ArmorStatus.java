package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.GuiIngame;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.opengl.GL11;

public class ArmorStatus extends Module {
   public static int hotbarwidth;
   public static ArmorStatus instance;

   public ArmorStatus() {
      super("ArmorStatus", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onRenderGUI() {
      if (this.isEnabled()) {
         ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
         GL11.glPushMatrix();
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         RenderHelper.enableStandardItemLighting();
         GL11.glPopMatrix();
         GL11.glDisable(2896);
         GL11.glEnable(32826);
         GL11.glEnable(2903);
         GL11.glEnable(2896);
         ItemStack boots = this.mc.thePlayer.inventory.armorInventory[0];
         ItemStack legs = this.mc.thePlayer.inventory.armorInventory[1];
         ItemStack chest = this.mc.thePlayer.inventory.armorInventory[2];
         ItemStack helmet = this.mc.thePlayer.inventory.armorInventory[3];
         ItemStack held = this.mc.thePlayer.inventory.getCurrentItem();
         GuiIngame.itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, boots, hotbarwidth - 20, sr.getScaledHeight() - 16);
         GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, boots, hotbarwidth - 20, sr.getScaledHeight() - 16);
         GuiIngame.itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, legs, hotbarwidth - 20, sr.getScaledHeight() - 30);
         GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, legs, hotbarwidth - 20, sr.getScaledHeight() - 30);
         GuiIngame.itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, chest, hotbarwidth - 20, sr.getScaledHeight() - 44);
         GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, chest, hotbarwidth - 20, sr.getScaledHeight() - 44);
         GuiIngame.itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, helmet, hotbarwidth - 20, sr.getScaledHeight() - 58);
         GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, helmet, hotbarwidth - 20, sr.getScaledHeight() - 58);
         GuiIngame.itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, held, hotbarwidth - 20, sr.getScaledHeight() - 72);
         GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, held, hotbarwidth - 20, sr.getScaledHeight() - 72);
         GL11.glDisable(2896);
         GL11.glDepthMask(true);
         GL11.glEnable(2929);
      }

   }
}
