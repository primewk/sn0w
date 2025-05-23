package net.minecraft.src.MEDMEX.Modules.World;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.Block;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class Nuker extends Module {
   CopyOnWriteArrayList<Vec3D> blockVecs = new CopyOnWriteArrayList();
   long t = 0L;

   public Nuker() {
      super("Nuker", 0, Module.Category.WORLD);
   }

   public void setup() {
      ArrayList<String> options = new ArrayList();
      options.add("BedPvP");
      options.add("Above");
      Client.settingsmanager.rSetting(new Setting("Nuker Mode", this, "BedPvP", options));
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate && e.isPre()) {
         byte b0;
         int A;
         int B;
         int C;
         int A1;
         int B1;
         int C1;
         int BId;
         Block block;
         if (Client.settingsmanager.getSettingByName("Nuker Mode").getValString().equalsIgnoreCase("BedPvP")) {
            b0 = 3;

            for(A = b0; A > -b0; --A) {
               for(B = b0; B > -b0; --B) {
                  for(C = b0; C > -b0; --C) {
                     A1 = (int)(this.mc.thePlayer.posX + (double)A);
                     B1 = (int)(this.mc.thePlayer.posY + (double)B);
                     C1 = (int)(this.mc.thePlayer.posZ + (double)C);
                     BId = this.mc.theWorld.getBlockId(A1, B1, C1);
                     block = Block.blocksList[BId];
                     if (block != null) {
                        if (block.blockID == 51) {
                           this.mc.playerController.clickBlock(A1, B1 - 1, C1, 1);
                        }

                        if (block.blockID == 40 || block.blockID == 39) {
                           this.mc.playerController.clickBlock(A1, B1, C1, 0);
                        }
                     }
                  }
               }
            }
         }

         if (Client.settingsmanager.getSettingByName("Nuker Mode").getValString().equalsIgnoreCase("Above")) {
            b0 = 3;

            for(A = b0; A > -b0; --A) {
               for(B = 0; B < b0; ++B) {
                  for(C = b0; C > -b0; --C) {
                     A1 = (int)(this.mc.thePlayer.posX + (double)A);
                     B1 = (int)(this.mc.thePlayer.posY + (double)B);
                     C1 = (int)(this.mc.thePlayer.posZ + (double)C);
                     BId = this.mc.theWorld.getBlockId(A1, B1, C1);
                     block = Block.blocksList[BId];
                     if (block != null) {
                        this.blockVecs.add(new Vec3D((double)A1, (double)B1, (double)C1));
                        if (this.blockVecs.size() > 30) {
                           this.blockVecs.remove(30);
                        }

                        if (!this.blockVecs.isEmpty()) {
                           Vec3D tobreak = (Vec3D)this.blockVecs.get(0);
                           if (!this.mc.theWorld.isAirBlock((int)tobreak.xCoord, (int)tobreak.yCoord, (int)tobreak.zCoord)) {
                              ++this.t;
                              if (this.t > 5L) {
                                 Client.sendPacket(new Packet14BlockDig(0, (int)tobreak.xCoord, (int)tobreak.yCoord, (int)tobreak.zCoord, 0));
                                 Client.sendPacket(new Packet14BlockDig(2, (int)tobreak.xCoord, (int)tobreak.yCoord, (int)tobreak.zCoord, 0));
                                 this.t = 0L;
                              }
                           } else {
                              this.blockVecs.remove(tobreak);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
