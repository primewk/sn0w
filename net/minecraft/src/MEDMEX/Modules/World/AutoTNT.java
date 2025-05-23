package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.Material;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.de.Hero.settings.Setting;

public class AutoTNT extends Module {
   public static AutoTNT instance;
   int offsetX = 0;
   int offsetZ = 0;

   public AutoTNT() {
      super("AutoTNT", 0, Module.Category.WORLD);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Place Frequency", this, 3.0D, 0.0D, 5.0D, true));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         int slot = InventoryUtils.getHotbarslotItem(46);
         if (slot != -1) {
            this.mc.thePlayer.inventory.currentItem = slot;
         }

         if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().itemID == 46) {
            this.offsets();

            for(int i = -3; i < 4; ++i) {
               for(int j = -3; j < 4; ++j) {
                  for(int k = -3; k < 4; ++k) {
                     int pX = (int)this.mc.thePlayer.posX + this.offsetX;
                     int pY = (int)this.mc.thePlayer.posY;
                     int pZ = (int)this.mc.thePlayer.posZ + this.offsetZ;
                     int x = pX - pX % (int)Client.settingsmanager.getSettingByName("Place Frequency").getValDouble() - i * (int)Client.settingsmanager.getSettingByName("Place Frequency").getValDouble();
                     int y = pY + k;
                     int z = pZ - pZ % (int)Client.settingsmanager.getSettingByName("Place Frequency").getValDouble() - j * (int)Client.settingsmanager.getSettingByName("Place Frequency").getValDouble();
                     if (this.mc.thePlayer.getDistance((double)x, (double)y, (double)z) <= 4.0D && !this.mc.theWorld.getBlockMaterial(x, y, z).isSolid() && !this.mc.theWorld.getBlockMaterial(x, y + 1, z).equals(Material.tnt) && !this.mc.theWorld.getBlockMaterial(x, y - 1, z).equals(Material.tnt)) {
                        int[] values = this.getPlace(new Vec3D((double)x, (double)y, (double)z));
                        if (values[0] != 0 || values[1] != 0 || values[2] != 0 || values[3] != 0) {
                           this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public void offsets() {
      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         this.offsetX = -1;
         this.offsetZ = -1;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         this.offsetX = 0;
         this.offsetZ = 0;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         this.offsetX = 0;
         this.offsetZ = -1;
      }

      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         this.offsetX = -1;
         this.offsetZ = 0;
      }

   }

   public int[] getPlace(Vec3D blockpos) {
      int[] values;
      if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + -1, (int)blockpos.zCoord + 0, 1};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord + 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 4};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord - 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord - 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 5};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord + 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 1, 2};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord - 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord - 1, 3};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord - 1, (int)blockpos.zCoord, 0};
         return values;
      } else {
         values = new int[4];
         return values;
      }
   }
}
