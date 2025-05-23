package net.minecraft.src.MEDMEX.Modules.World;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Event.EventPacket;
import net.minecraft.src.MEDMEX.Modules.Module;

public class PacketLogger extends Module {
   public static CopyOnWriteArrayList<Integer> lPackets = new CopyOnWriteArrayList();
   public static PacketLogger instance;

   public PacketLogger() {
      super("PacketLogger", 0, Module.Category.WORLD);
      instance = this;
   }

   public void getPacket(EventPacket e) {
      if (this.isEnabled() && this.mc.thePlayer != null && this.mc.theWorld != null && lPackets.contains(e.getPacket().getPacketId())) {
         Client.addChatMessage("" + e.getPacket());
      }

   }
}
