package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Phase extends Module {
   public static Phase instance;
   int offsetX = 0;
   int offsetZ = 0;

   public Phase() {
      super("Phase", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         double multiplier = 0.08D;
         double mx = -Math.sin(Math.toRadians((double)this.mc.thePlayer.rotationYaw));
         double mz = Math.cos(Math.toRadians((double)this.mc.thePlayer.rotationYaw));
         double x = (double)this.mc.thePlayer.movementInput.moveForward * multiplier * mx + (double)this.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
         double z = (double)this.mc.thePlayer.movementInput.moveForward * multiplier * mz - (double)this.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
         if (this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isOnLadder()) {
            this.offsets();
            Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z, false));
            if (this.mc.theWorld.isAirBlock((int)this.mc.thePlayer.posX + this.offsetX, (int)this.mc.thePlayer.posY, (int)this.mc.thePlayer.posZ + this.offsetZ)) {
               for(int i = 1; i < 10; ++i) {
                  Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.posX, 8.988465674311579E307D, 8.988465674311579E307D, this.mc.thePlayer.posZ, false));
               }
            }

            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z);
            double moveSpeed = (double)MathHelper.sqrt_double(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
            float forward = this.mc.thePlayer.movementInput.moveForward;
            float strafe = this.mc.thePlayer.movementInput.moveStrafe;
            float yaw = this.mc.thePlayer.rotationYaw;
            if (forward == 0.0F && strafe == 0.0F) {
               this.mc.thePlayer.motionX = 0.0D;
               this.mc.thePlayer.motionZ = 0.0D;
            } else if (forward != 0.0F) {
               float var10000;
               if (strafe >= 1.0F) {
                  var10000 = yaw + (float)(forward > 0.0F ? -45 : 45);
                  strafe = 0.0F;
               } else if (strafe <= -1.0F) {
                  var10000 = yaw + (float)(forward > 0.0F ? 45 : -45);
                  strafe = 0.0F;
               }
            }

            if (forward > 0.0F) {
               forward = 1.0F;
            } else if (forward < 0.0F) {
               forward = -1.0F;
            }

            this.mc.thePlayer.motionX = (double)forward * moveSpeed * mx + (double)strafe * moveSpeed * mz;
            this.mc.thePlayer.motionZ = (double)forward * moveSpeed * mz - (double)strafe * moveSpeed * mx;
            this.mc.timer.timerSpeed = 1.0888F;
         }
      }

   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet12PlayerLook) {
         e.setCancelled(true);
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
}
