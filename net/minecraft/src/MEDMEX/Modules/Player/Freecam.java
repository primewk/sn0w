package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
   public static Freecam instance;
   public static double x;
   public static double y;
   public static double z;
   Entity ent;

   public Freecam() {
      super("Freecam", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Freecam Speed", this, 1.0D, 1.0D, 5.0D, false));
   }

   public void onDisable() {
      this.mc.thePlayer.setPosition(x, y, z);
      if (this.ent != null) {
         this.mc.theWorld.setEntityDead(this.ent);
      }

      this.mc.thePlayer.noClip = false;
   }

   public void onEnable() {
      if (this.mc.thePlayer != null && this.mc.theWorld != null) {
         EntityPlayer entity = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.username);
         entity.copyDataFrom(this.mc.thePlayer, true);
         entity.posY -= (double)this.mc.thePlayer.yOffset;
         this.ent = entity;
         this.mc.theWorld.joinEntityInSurroundings(entity);
         x = this.mc.thePlayer.posX;
         y = this.mc.thePlayer.posY;
         z = this.mc.thePlayer.posZ;
      }

   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
         if (e.getPacket() instanceof Packet10Flying) {
            e.setCancelled(true);
         }

         if (e.getPacket() instanceof Packet11PlayerPosition) {
            e.setCancelled(true);
         }

         if (e.getPacket() instanceof Packet13PlayerLookMove) {
            e.setCancelled(true);
         }
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.mc.thePlayer.noClip = true;
         this.mc.thePlayer.motionY = 0.0D;
         if (this.mc.currentScreen == null) {
            if (Keyboard.isKeyDown(57)) {
               this.mc.thePlayer.motionY = 1.0D * Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
            }

            if (Keyboard.isKeyDown(42)) {
               this.mc.thePlayer.motionY = -1.0D * Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
            }
         }

         float var1 = this.mc.thePlayer.moveStrafing;
         float var2 = this.mc.thePlayer.moveForward;
         float var3 = 0.2F * (float)Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
         float var4 = MathHelper.sqrt_float(var1 * var1 + var2 * var2);
         if (var4 >= 0.01F) {
            if (var4 < 1.0F) {
               var4 = 1.0F;
            }

            var4 = var3 / var4;
            var1 *= var4;
            var2 *= var4;
            float var5 = MathHelper.sin(this.mc.thePlayer.rotationYaw * 3.1415927F / 180.0F);
            float var6 = MathHelper.cos(this.mc.thePlayer.rotationYaw * 3.1415927F / 180.0F);
            EntityPlayerSP var10000 = this.mc.thePlayer;
            var10000.motionX += (double)(var1 * var6 - var2 * var5);
            var10000 = this.mc.thePlayer;
            var10000.motionZ += (double)(var2 * var6 + var1 * var5);
         }

         this.mc.getSendQueue().addToSendQueue(new Packet0KeepAlive());
      }

   }
}
