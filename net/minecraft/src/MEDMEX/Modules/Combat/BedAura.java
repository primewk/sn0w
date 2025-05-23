package net.minecraft.src.MEDMEX.Modules.Combat;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;
import net.minecraft.src.MEDMEX.Utils.Timer;
import net.minecraft.src.de.Hero.settings.Setting;

public class BedAura extends Module {
   float yaw;
   Vec3D target;
   Vec3D toplace;
   public static Timer timer = new Timer();

   public BedAura() {
      super("BedAura", 0, Module.Category.COMBAT);
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Auto Place", this, true));
   }

   public void onRender() {
      if (this.isEnabled()) {
         if (this.target != null) {
            if (this.mc.theWorld.getBlockId((int)this.target.xCoord, (int)this.target.yCoord, (int)this.target.zCoord) == 26) {
               Vec3D bed;
               if (this.mc.theWorld.getBlockId((int)this.target.xCoord + 1, (int)this.target.yCoord, (int)this.target.zCoord) == 26) {
                  bed = new Vec3D(this.target.xCoord + 1.0D, this.target.yCoord, this.target.zCoord);
                  RenderUtils.bedESPBoxFilled(bed, new Color(200, 25, 25, 120));
               }

               if (this.mc.theWorld.getBlockId((int)this.target.xCoord - 1, (int)this.target.yCoord, (int)this.target.zCoord) == 26) {
                  bed = new Vec3D(this.target.xCoord - 1.0D, this.target.yCoord, this.target.zCoord);
                  RenderUtils.bedESPBoxFilled(bed, new Color(200, 25, 25, 120));
               }

               if (this.mc.theWorld.getBlockId((int)this.target.xCoord, (int)this.target.yCoord, (int)this.target.zCoord + 1) == 26) {
                  bed = new Vec3D(this.target.xCoord, this.target.yCoord, this.target.zCoord + 1.0D);
                  RenderUtils.bedESPBoxFilled(bed, new Color(200, 25, 25, 120));
               }

               if (this.mc.theWorld.getBlockId((int)this.target.xCoord, (int)this.target.yCoord, (int)this.target.zCoord - 1) == 26) {
                  bed = new Vec3D(this.target.xCoord, this.target.yCoord, this.target.zCoord - 1.0D);
                  RenderUtils.bedESPBoxFilled(bed, new Color(200, 25, 25, 120));
               }

               RenderUtils.bedESPBoxFilled(this.target, new Color(200, 25, 25, 120));
            } else {
               this.target = null;
            }
         }

         if (this.toplace != null) {
            RenderUtils.blockESPBoxFilled(this.toplace, new Color(25, 200, 25, 120));
         }
      }

   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet53BlockChange) {
         Packet53BlockChange packet = (Packet53BlockChange)e.getPacket();
         if (packet.type == 26 && this.mc.theWorld.getBlockId(packet.xPosition, packet.yPosition, packet.zPosition) == 26) {
            this.target = new Vec3D((double)packet.xPosition, (double)packet.yPosition, (double)packet.zPosition);
         }
      }

   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         if (this.target != null && this.mc.thePlayer.getDistance(this.target.xCoord, this.target.yCoord, this.target.zCoord) <= 6.0D && this.mc.thePlayer.dimension != 0) {
            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), (int)this.target.xCoord, (int)this.target.yCoord, (int)this.target.zCoord, 1);
         }

         if (Client.settingsmanager.getSettingByName("Auto Place").getValBoolean() && this.mc.thePlayer.dimension != 0) {
            Iterator var3 = this.mc.theWorld.playerEntities.iterator();

            while(true) {
               EntityPlayer p;
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        p = (EntityPlayer)var3.next();
                     } while(!(this.mc.thePlayer.getDistanceToEntity(p) <= 6.0F));
                  } while(p == this.mc.thePlayer);
               } while(Client.friends.contains(p.username));

               int plrX = MathHelper.floor_double(p.posX);
               int plrY = MathHelper.floor_double(p.posY);
               int plrZ = MathHelper.floor_double(p.posZ);
               int radius = 2;

               for(int x = plrX - radius; x <= plrX + radius; ++x) {
                  for(int z = plrZ - radius; z <= plrZ + radius; ++z) {
                     for(int y = plrY - radius; y <= plrY + radius; ++y) {
                        if (!this.mc.theWorld.getBlockMaterial(x, y, z).isSolid() && this.mc.theWorld.getBlockMaterial(x, y - 1, z).isSolid() && this.mc.thePlayer.getDistance((double)x, (double)(y - 1), (double)z) <= 4.0D && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().itemID == 355 && this.isBedPlaceable(new Vec3D((double)x, (double)(y - 1), (double)z)) && timer.hasTimeElapsed(100L, true)) {
                           this.toplace = new Vec3D((double)x, (double)(y - 1), (double)z);
                           this.mc.getSendQueue().addToSendQueue(new Packet12PlayerLook(this.yaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
                           this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), x, y - 1, z, 1);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public boolean isBedPlaceable(Vec3D bedpos) {
      if (!this.mc.theWorld.isAirBlock((int)bedpos.xCoord, (int)bedpos.yCoord, (int)bedpos.zCoord)) {
         if (this.mc.theWorld.isAirBlock((int)bedpos.xCoord + 1, (int)bedpos.yCoord + 1, (int)bedpos.zCoord) && !this.mc.theWorld.isAirBlock((int)bedpos.xCoord + 1, (int)bedpos.yCoord, (int)bedpos.zCoord)) {
            this.yaw = -90.0F;
            return true;
         }

         if (this.mc.theWorld.isAirBlock((int)bedpos.xCoord - 1, (int)bedpos.yCoord + 1, (int)bedpos.zCoord) && !this.mc.theWorld.isAirBlock((int)bedpos.xCoord - 1, (int)bedpos.yCoord, (int)bedpos.zCoord)) {
            this.yaw = 90.0F;
            return true;
         }

         if (this.mc.theWorld.isAirBlock((int)bedpos.xCoord, (int)bedpos.yCoord + 1, (int)bedpos.zCoord + 1) && !this.mc.theWorld.isAirBlock((int)bedpos.xCoord, (int)bedpos.yCoord, (int)bedpos.zCoord + 1)) {
            this.yaw = 0.0F;
            return true;
         }

         if (this.mc.theWorld.isAirBlock((int)bedpos.xCoord, (int)bedpos.yCoord + 1, (int)bedpos.zCoord - 1) && !this.mc.theWorld.isAirBlock((int)bedpos.xCoord, (int)bedpos.yCoord, (int)bedpos.zCoord - 1)) {
            this.yaw = 180.0F;
            return true;
         }
      }

      return false;
   }
}
