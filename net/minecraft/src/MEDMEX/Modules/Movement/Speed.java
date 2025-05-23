package net.minecraft.src.MEDMEX.Modules.Movement;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.MathUtil;
import net.minecraft.src.de.Hero.settings.Setting;

public class Speed extends Module {
   protected int lowStage;
   public static Speed instance;
   private double moveSpeed;
   private double lastDist;
   public static int tunnelstage;
   long boostTimer = 0L;
   public double fakeTimer = 1.2D;

   public Speed() {
      super("Speed", 0, Module.Category.MOVEMENT);
      instance = this;
   }

   public void setup() {
      ArrayList<String> options = new ArrayList();
      options.add("Strafe");
      options.add("OnGround");
      options.add("LowHop");
      options.add("Boost");
      options.add("Tunnel");
      options.add("BHop");
      options.add("Bypass");
      Client.settingsmanager.rSetting(new Setting("Mode", this, "Strafe", options));
   }

   public void onEnable() {
      this.lowStage = 4;
      this.moveSpeed = this.getBaseMoveSpeed();
      this.lastDist = 0.0D;
      tunnelstage = 2;
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
      this.boostTimer = 0L;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         this.attribute = " §7[§f" + this.getSet("Mode").getValString() + "§7]";
         if (this.getSet("Mode").getValString().equalsIgnoreCase("OnGround")) {
            if (this.mc.thePlayer.movementInput.moveForward == 0.0F && this.mc.thePlayer.movementInput.moveStrafe == 0.0F) {
               return;
            }

            if (this.mc.thePlayer.isInWater() || this.mc.thePlayer.isOnLadder() || this.mc.thePlayer.isCollidedHorizontally) {
               return;
            }

            EntityPlayerSP var10000 = this.mc.thePlayer;
            var10000.posY -= 0.3993000090122223D;
            this.mc.thePlayer.motionY = -1000.0D;
            this.mc.thePlayer.cameraPitch = 0.3F;
            this.mc.thePlayer.distanceWalkedModified = 44.0F;
            this.mc.timer.timerSpeed = 1.0F;
            if (this.mc.thePlayer.onGround) {
               var10000 = this.mc.thePlayer;
               var10000.posY += 0.3993000090122223D;
               this.mc.thePlayer.motionY = 0.3993000090122223D;
               this.mc.thePlayer.distanceWalkedModified = 44.0F;
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 1.590000033378601D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 1.590000033378601D;
               this.mc.thePlayer.cameraPitch = 0.0F;
               this.mc.timer.timerSpeed = 1.199F;
            }
         }

         float yaw;
         if (this.getSet("Mode").getValString().equalsIgnoreCase("Strafe")) {
            boolean moving = (double)Math.abs(this.mc.thePlayer.movementInput.moveForward) > 0.1D || (double)Math.abs(this.mc.thePlayer.movementInput.moveStrafe) > 0.1D;
            if (moving) {
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
               }

               double moveSpeed = (double)MathHelper.sqrt_double(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
               float forward = this.mc.thePlayer.movementInput.moveForward;
               yaw = this.mc.thePlayer.movementInput.moveStrafe;
               float yaw = this.mc.thePlayer.rotationYaw;
               if (forward == 0.0F && yaw == 0.0F) {
                  this.mc.thePlayer.motionX = 0.0D;
                  this.mc.thePlayer.motionZ = 0.0D;
               } else if (forward != 0.0F) {
                  if (yaw >= 1.0F) {
                     yaw += (float)(forward > 0.0F ? -45 : 45);
                     yaw = 0.0F;
                  } else if (yaw <= -1.0F) {
                     yaw += (float)(forward > 0.0F ? 45 : -45);
                     yaw = 0.0F;
                  }
               }

               if (forward > 0.0F) {
                  forward = 1.0F;
               } else if (forward < 0.0F) {
                  forward = -1.0F;
               }

               double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
               double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
               this.mc.thePlayer.motionX = (double)forward * moveSpeed * mx + (double)yaw * moveSpeed * mz;
               this.mc.thePlayer.motionZ = (double)forward * moveSpeed * mz - (double)yaw * moveSpeed * mx;
            } else {
               this.mc.thePlayer.motionX = 0.0D;
               this.mc.thePlayer.motionZ = 0.0D;
            }
         }

         if (this.getSet("Mode").getValString().equalsIgnoreCase("LowHop")) {
            this.mc.timer.timerSpeed = 1.0888F;
            if (!this.mc.thePlayer.isCollidedHorizontally) {
               if (MathUtil.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == MathUtil.round(0.4D, 3)) {
                  this.mc.thePlayer.motionY = 0.31D;
               } else if (MathUtil.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == MathUtil.round(0.71D, 3)) {
                  this.mc.thePlayer.motionY = 0.04D;
               } else if (MathUtil.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == MathUtil.round(0.75D, 3)) {
                  this.mc.thePlayer.motionY = -0.2D;
               } else if (MathUtil.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == MathUtil.round(0.55D, 3)) {
                  this.mc.thePlayer.motionY = -0.14D;
               } else if (MathUtil.round(this.mc.thePlayer.posY - (double)((int)this.mc.thePlayer.posY), 3) == MathUtil.round(0.41D, 3)) {
                  this.mc.thePlayer.motionY = -0.2D;
               }
            }
         }

         if (this.getSet("Mode").getValString().equalsIgnoreCase("Boost")) {
            ++this.boostTimer;
            if (this.boostTimer >= 1L && this.boostTimer <= 5L) {
               this.mc.timer.timerSpeed = 10.0F;
            }

            if (this.boostTimer > 4L && this.boostTimer <= 9L) {
               this.mc.timer.timerSpeed = 1.0F;
            }

            if (this.boostTimer == 10L) {
               this.mc.timer.timerSpeed = 0.2F;
               this.boostTimer = 1L;
            }
         }

         if (this.getSet("Mode").getValString().equalsIgnoreCase("Tunnel")) {
            double strafe;
            double forward;
            if (this.mc.thePlayer.onGround || tunnelstage == 3) {
               if ((this.mc.thePlayer.isCollidedHorizontally || this.mc.thePlayer.moveForward == 0.0F) && this.mc.thePlayer.moveStrafing == 0.0F) {
                  this.mc.timer.timerSpeed = 1.0F;
               } else if (tunnelstage == 2) {
                  this.moveSpeed *= 2.149D;
                  tunnelstage = 3;
               } else if (tunnelstage == 3) {
                  tunnelstage = 2;
                  forward = 0.66D * (this.lastDist - this.getBaseMoveSpeed());
                  this.moveSpeed = this.lastDist - forward;
               } else {
                  List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0D, this.mc.thePlayer.motionY, 0.0D));
                  if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                     tunnelstage = 1;
                  }
               }

               this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
               forward = (double)this.mc.thePlayer.movementInput.moveForward;
               strafe = (double)this.mc.thePlayer.movementInput.moveStrafe;
               yaw = this.mc.thePlayer.rotationYaw;
               if (forward == 0.0D && strafe == 0.0D) {
                  this.mc.thePlayer.motionX = 0.0D;
                  this.mc.thePlayer.motionZ = 0.0D;
               } else {
                  if (forward != 0.0D) {
                     if (strafe > 0.0D) {
                        strafe = 1.0D;
                        yaw += (float)(forward > 0.0D ? -45 : 45);
                     } else if (strafe < 0.0D) {
                        yaw += (float)(forward > 0.0D ? 45 : -45);
                     }

                     strafe = 0.0D;
                     if (forward > 0.0D) {
                        forward = 1.0D;
                     } else if (forward < 0.0D) {
                        forward = -1.0D;
                     }
                  }

                  this.mc.thePlayer.motionX = forward * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
                  this.mc.thePlayer.motionZ = forward * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
               }
            }

            this.mc.timer.timerSpeed = 1.085F;
            forward = (double)this.mc.thePlayer.movementInput.moveForward;
            strafe = (double)this.mc.thePlayer.movementInput.moveStrafe;
            if ((forward != 0.0D || strafe != 0.0D) && !this.mc.thePlayer.isJumping && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isOnLadder()) {
               boolean var14 = this.mc.thePlayer.isCollidedHorizontally;
            }

            this.moveSpeed = Math.max(this.mc.thePlayer.ticksExisted % 2 == 0 ? 2.1D : 1.3D, this.getBaseMoveSpeed());
            yaw = this.mc.thePlayer.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
               this.mc.thePlayer.motionX = 0.0D;
               this.mc.thePlayer.motionZ = 0.0D;
            } else {
               if (forward != 0.0D) {
                  if (strafe > 0.0D) {
                     yaw += (float)(forward > 0.0D ? -45 : 45);
                  } else if (strafe < 0.0D) {
                     yaw += (float)(forward > 0.0D ? 45 : -45);
                  }

                  strafe = 0.0D;
                  if (forward > 0.0D) {
                     forward = 0.15D;
                  } else if (forward < 0.0D) {
                     forward = -0.15D;
                  }
               }

               if (strafe > 0.0D) {
                  strafe = 0.15D;
               } else if (strafe < 0.0D) {
                  strafe = -0.15D;
               }

               this.mc.thePlayer.motionX = forward * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
               this.mc.thePlayer.motionZ = forward * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
            }
         }

         if (this.getSet("Mode").getValString().equalsIgnoreCase("BHop")) {
            if (this.mc.thePlayer.movementInput.moveForward == 0.0F && this.mc.thePlayer.movementInput.moveStrafe == 0.0F) {
               this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0D;
            } else if (this.mc.thePlayer.onGround) {
               if (this.mc.thePlayer.hurtTime != 0) {
                  this.setMotion((double)this.getSpeed() * 1.075D);
               }

               this.setMotion((double)this.getSpeed() * 1.08D);
               this.setMotion((double)(this.getSpeed() * this.getSpeedPotMultiplier(0.1D)));
               this.mc.thePlayer.jump();
            } else {
               this.setMotion((double)this.getSpeed());
            }
         }

         if (this.getSet("Mode").getValString().equalsIgnoreCase("Bypass")) {
            this.fakeTimer = 1.2D;
         }
      }

   }

   public float getSpeedPotMultiplier(double multi) {
      return 1.0F;
   }

   public float getSpeed() {
      return (float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
   }

   public void updatePlayer() {
      double var2 = this.mc.thePlayer.posX;
      double var4 = this.mc.thePlayer.boundingBox.minY;
      double var6 = this.mc.thePlayer.posY;
      double var8 = this.mc.thePlayer.posZ;
      double var10 = (double)this.mc.thePlayer.rotationYaw;
      double var12 = (double)this.mc.thePlayer.rotationPitch;
      boolean var14 = var4 != 0.0D || var6 != 0.0D || var2 != 0.0D || var8 != 0.0D;
      boolean var15 = var10 != 0.0D || var12 != 0.0D;
      if (this.mc.thePlayer.ridingEntity != null) {
         if (var15) {
            Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.motionX, -999.0D, -999.0D, this.mc.thePlayer.motionZ, this.mc.thePlayer.onGround));
         } else {
            Client.sendPacket(new Packet13PlayerLookMove(this.mc.thePlayer.motionX, -999.0D, -999.0D, this.mc.thePlayer.motionZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
         }

         var14 = false;
      } else if (var14 && var15) {
         Client.sendPacket(new Packet13PlayerLookMove(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
      } else if (var14) {
         Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
      } else if (var15) {
         Client.sendPacket(new Packet12PlayerLook(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
      } else {
         Client.sendPacket(new Packet10Flying(this.mc.thePlayer.onGround));
      }

   }

   public void setMotion(double speed) {
      float yaw = this.mc.thePlayer.rotationYaw;
      double forward = (double)this.mc.thePlayer.moveForward;
      double strafe = (double)this.mc.thePlayer.moveStrafing;
      if (forward == 0.0D && strafe == 0.0D) {
         this.mc.thePlayer.motionX = 0.0D;
         this.mc.thePlayer.motionZ = 0.0D;
      } else {
         if (forward != 0.0D) {
            if (strafe > 0.0D) {
               yaw += (float)(forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
               yaw += (float)(forward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (forward > 0.0D) {
               forward = 1.0D;
            } else if (forward < 0.0D) {
               forward = -1.0D;
            }
         }

         this.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         this.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      }

   }

   public double getBaseMoveSpeed() {
      if (this.mc.thePlayer != null) {
         double baseSpeed = 0.2873D;
         return baseSpeed;
      } else {
         return 0.0D;
      }
   }
}
