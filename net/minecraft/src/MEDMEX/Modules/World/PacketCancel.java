package net.minecraft.src.MEDMEX.Modules.World;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class PacketCancel extends Module {
   public static CopyOnWriteArrayList<Integer> cPackets = new CopyOnWriteArrayList();
   public static PacketCancel instance;

   public PacketCancel() {
      super("PacketCancel", 0, Module.Category.WORLD);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && cPackets.contains(e.getPacket().getPacketId())) {
         e.setCancelled(true);
      }

   }
}
