package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.MathHelper;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;
import net.minecraft.src.de.Hero.settings.Setting;

public class Scaffold extends Module {
   public static Scaffold instance;
   Timer timer = new Timer();

   public Scaffold() {
      super("Scaffold", 0, Module.Category.WORLD);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Scaffold Reach", this, 3.0D, 1.0D, 5.0D, true));
   }

   public boolean canPlaceBlock(int x, int y, int z) {
      int id = this.mc.theWorld.getBlockId(x, y, z);
      return id == 0 || id == 10 || id == 11 || id == 8 || id == 9;
   }

   public void placeBlock(int x, int y, int z) {
      if (!this.canPlaceBlock(x - 1, y, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x - 1, y, z, 5);
      } else if (!this.canPlaceBlock(x + 1, y, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x + 1, y, z, 4);
      } else if (!this.canPlaceBlock(x, y, z - 1)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y, z - 1, 3);
      } else if (!this.canPlaceBlock(x, y, z + 1)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y, z + 1, 2);
      } else if (!this.canPlaceBlock(x, y - 1, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y - 1, z, 1);
      } else if (!this.canPlaceBlock(x, y + 1, z)) {
         this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), x, y - 1, z, 0);
      }
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         int plrX = MathHelper.floor_double(this.mc.thePlayer.posX);
         int plrY = MathHelper.floor_double(this.mc.thePlayer.posY);
         int plrZ = MathHelper.floor_double(this.mc.thePlayer.posZ);
         int radius = (int)(Client.settingsmanager.getSettingByName("Scaffold Reach").getValDouble() - 2.0D);

         for(int x = plrX - radius; x <= plrX + radius; ++x) {
            for(int z = plrZ - radius; z <= plrZ + radius; ++z) {
               if (this.mc.thePlayer.ticksExisted % 8 == 0) {
                  this.placeBlock(x, plrY - 2, z);
               }
            }
         }
      }

   }
}
