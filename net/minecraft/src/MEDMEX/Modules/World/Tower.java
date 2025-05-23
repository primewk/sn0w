package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.Timer;
import org.lwjgl.input.Keyboard;

public class Tower extends Module {
   public static Tower instance;
   public static int X;
   public static int Z;
   public static int offsetX;
   public static int offsetZ;
   Timer timer = new Timer();

   public Tower() {
      super("Tower", 0, Module.Category.WORLD);
      instance = this;
   }

   public boolean canPlaceBlock(int x, int y, int z) {
      int id = this.mc.theWorld.getBlockId(x, y, z);
      return id == 0 || id == 10 || id == 11 || id == 8 || id == 9;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         X = (int)this.mc.thePlayer.posX;
         Z = (int)this.mc.thePlayer.posZ;
         if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
            offsetX = -1;
            offsetZ = -1;
         }

         if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
            offsetX = 0;
            offsetZ = 0;
         }

         if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
            offsetX = 0;
            offsetZ = -1;
         }

         if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
            offsetX = -1;
            offsetZ = 0;
         }

         if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.keyCode) && this.canPlaceBlock(X + offsetX, (int)this.mc.thePlayer.posY - 2, Z + offsetZ)) {
            if (this.timer.hasTimeElapsed(200L, true)) {
               this.mc.thePlayer.jump();
            }

            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), X + offsetX, (int)this.mc.thePlayer.posY - 3, Z + offsetZ, 1);
         }
      }

   }
}
