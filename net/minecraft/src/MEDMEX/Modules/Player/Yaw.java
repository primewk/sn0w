package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class Yaw extends Module {
   public static Yaw instance;
   public static Vec3D Destination;

   public Yaw() {
      super("Yaw", 0, Module.Category.PLAYER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Direction", this, 0.0D, 0.0D, 3.0D, true));
      Client.settingsmanager.rSetting(new Setting("Custom Pos", this, false));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (Client.settingsmanager.getSettingByName("Custom Pos").getValBoolean()) {
            if (Destination != null) {
               int dX = (int)Destination.xCoord;
               int dY = (int)Destination.yCoord;
               int dZ = (int)Destination.zCoord;
               float yaw = (float)(-(Math.atan2((double)dX - this.mc.thePlayer.posX, (double)dZ - this.mc.thePlayer.posZ) * 57.29577951308232D));
               this.mc.thePlayer.rotationYaw = yaw;
            }
         } else {
            this.mc.thePlayer.rotationYaw = (float)this.getYawFromDir((int)Client.settingsmanager.getSettingByName("Direction").getValDouble());
         }
      }

   }

   public int getYawFromDir(int dir) {
      if (dir == 0) {
         return 0;
      } else if (dir == 1) {
         return 90;
      } else {
         return dir == 2 ? 180 : 270;
      }
   }
}
