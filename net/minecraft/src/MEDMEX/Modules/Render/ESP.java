package net.minecraft.src.MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;
import net.minecraft.src.de.Hero.settings.Setting;

public class ESP extends Module {
   public static boolean players;
   public static boolean items;
   public static boolean mobs;
   public static boolean animals;
   public static ESP instance;

   public ESP() {
      super("ESP", 0, Module.Category.RENDER);
      players = true;
      items = true;
      mobs = false;
      animals = false;
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Player ESP", this, true));
      Client.settingsmanager.rSetting(new Setting("Item ESP", this, true));
      Client.settingsmanager.rSetting(new Setting("Mob ESP", this, false));
      Client.settingsmanager.rSetting(new Setting("Animal ESP", this, false));
      Client.settingsmanager.rSetting(new Setting("Vehicle ESP", this, false));
      Client.settingsmanager.rSetting(new Setting("Misc ESP", this, false));
      ArrayList<String> options = new ArrayList();
      options.add("Box");
      options.add("Sphere");
      options.add("Outline");
      options.add("Filled");
      Client.settingsmanager.rSetting(new Setting("ESP Mode", this, "Outline", options));
   }

   public Color getColor(Entity e) {
      if (e instanceof EntityPlayer) {
         return Client.friends.contains(((EntityPlayer)e).username) ? new Color(0.1F, 0.8F, 0.1F, 1.0F) : new Color(0.709F, 0.576F, 0.858F, 1.0F);
      } else if (e instanceof EntityItem) {
         return new Color(0.709F, 0.576F, 0.858F, 1.0F);
      } else if (e instanceof EntityMob) {
         return new Color(0.98F, 0.0F, 0.274F, 1.0F);
      } else if (e instanceof EntityAnimal) {
         return new Color(0.0F, 0.98F, 0.176F, 1.0F);
      } else {
         return !(e instanceof EntityBoat) && !(e instanceof EntityMinecart) ? null : new Color(0.874F, 1.0F, 0.078F, 1.0F);
      }
   }

   public boolean shouldRenderEntity(Entity e) {
      if (e instanceof EntityPlayer && e != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Player ESP").getValBoolean()) {
         return true;
      } else if (e instanceof EntityItem && Client.settingsmanager.getSettingByName("Item ESP").getValBoolean()) {
         return true;
      } else if (e instanceof EntityMob && Client.settingsmanager.getSettingByName("Mob ESP").getValBoolean()) {
         return true;
      } else {
         return e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("Animal ESP").getValBoolean();
      }
   }

   public void onRender() {
      if (this.isEnabled()) {
         this.attribute = " §7[§f" + Client.settingsmanager.getSettingByName("ESP Mode").getValString() + "§7]";
         Entity e;
         Iterator var2;
         double cX;
         double cY;
         double cZ;
         double renderX;
         double renderY;
         double renderZ;
         EntityPlayer p;
         if (Client.settingsmanager.getSettingByName("ESP Mode").getValString().equalsIgnoreCase("Box")) {
            var2 = this.mc.theWorld.loadedEntityList.iterator();

            while(var2.hasNext()) {
               e = (Entity)var2.next();
               cX = e.posX;
               cY = e.posY;
               cZ = e.posZ;
               renderX = cX - RenderManager.renderPosX;
               renderY = cY - RenderManager.renderPosY;
               renderZ = cZ - RenderManager.renderPosZ;
               if (e instanceof EntityPlayer && e != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Player ESP").getValBoolean()) {
                  p = (EntityPlayer)e;
                  if (Client.friends.contains(p.username)) {
                     RenderUtils.drawOutlinedEntityESP(renderX, renderY, renderZ, (double)e.width, (double)e.height * 1.1D, 0.1F, 0.8F, 0.1F, 1.0F);
                  } else {
                     RenderUtils.drawOutlinedEntityESP(renderX, renderY, renderZ, (double)e.width, (double)e.height * 1.1D, 0.709F, 0.576F, 0.858F, 1.0F);
                  }
               }

               if (e instanceof EntityItem && Client.settingsmanager.getSettingByName("Item ESP").getValBoolean()) {
                  RenderUtils.drawOutlinedEntityESP(renderX, renderY, renderZ, (double)e.width, (double)e.height * 1.8D, 0.709F, 0.576F, 0.858F, 1.0F);
               }

               if (e instanceof EntityMob && Client.settingsmanager.getSettingByName("Mob ESP").getValBoolean()) {
                  RenderUtils.drawOutlinedEntityESP(renderX, renderY, renderZ, (double)e.width, (double)e.height, 0.98F, 0.0F, 0.274F, 1.0F);
               }

               if (e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("Animal ESP").getValBoolean()) {
                  RenderUtils.drawOutlinedEntityESP(renderX, renderY, renderZ, (double)e.width, (double)e.height * 1.2D, 0.0F, 0.98F, 0.176F, 1.0F);
               }
            }
         }

         if (Client.settingsmanager.getSettingByName("ESP Mode").getValString().equalsIgnoreCase("Sphere")) {
            var2 = this.mc.theWorld.loadedEntityList.iterator();

            while(var2.hasNext()) {
               e = (Entity)var2.next();
               cX = e.posX;
               cY = e.posY;
               cZ = e.posZ;
               renderX = cX - RenderManager.renderPosX;
               renderY = cY - RenderManager.renderPosY;
               renderZ = cZ - RenderManager.renderPosZ;
               if (e instanceof EntityPlayer && e != this.mc.thePlayer && Client.settingsmanager.getSettingByName("Player ESP").getValBoolean()) {
                  p = (EntityPlayer)e;
                  if (Client.friends.contains(p.username)) {
                     RenderUtils.renderSphere(renderX, renderY, renderZ, 1.2F, 0.1F, 0.8F, 0.1F, 1.0F);
                  } else {
                     RenderUtils.renderSphere(renderX, renderY, renderZ, 1.2F, 0.709F, 0.576F, 0.858F, 1.0F);
                  }
               }

               if (e instanceof EntityItem && Client.settingsmanager.getSettingByName("Item ESP").getValBoolean()) {
                  RenderUtils.renderSphere(renderX, renderY - 0.9D, renderZ, 0.3F, 0.709F, 0.576F, 0.858F, 1.0F);
               }

               if (e instanceof EntityMob && Client.settingsmanager.getSettingByName("Mob ESP").getValBoolean()) {
                  RenderUtils.renderSphere(renderX, renderY, renderZ, 1.2F, 0.98F, 0.0F, 0.274F, 1.0F);
               }

               if (e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("Animal ESP").getValBoolean()) {
                  RenderUtils.renderSphere(renderX, renderY, renderZ, 1.2F, 0.0F, 0.98F, 0.176F, 1.0F);
               }
            }
         }
      }

   }
}
