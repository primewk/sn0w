package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;

public class NewChunks extends Module {
   public static NewChunks instance;

   public NewChunks() {
      super("NewChunks", 0, Module.Category.RENDER);
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Newchunks Height", this, 0.0D, 0.0D, 130.0D, true));
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && e.getPacket() instanceof Packet53BlockChange) {
         Packet53BlockChange packet = (Packet53BlockChange)e.getPacket();
         if (packet.metadata == 8 || packet.metadata == 9 || packet.metadata == 10 || packet.metadata == 11) {
            int x = MathHelper.floor_double((double)packet.xPosition);
            int z = MathHelper.floor_double((double)packet.zPosition);
            int chunkx = Integer.valueOf(x & 15);
            int chunkz = Integer.valueOf(z & 15);
            int newchunkx = chunkx * 16 - 8;
            int newchunkz = chunkz * 16 - 8;
            RenderGlobal.x.add(newchunkx);
            RenderGlobal.z.add(newchunkz);
         }
      }

   }
}
