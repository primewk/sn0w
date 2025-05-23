package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.Packet;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class PacketCancel extends Command {
   public PacketCancel() {
      super("PacketCancel", "Cancel specified packets", "PacketCancel <packetID>", "pc");
   }

   public void onCommand(String[] args, String command) {
      try {
         String format;
         if (net.minecraft.src.MEDMEX.Modules.World.PacketCancel.cPackets.contains(Integer.valueOf(args[0]))) {
            net.minecraft.src.MEDMEX.Modules.World.PacketCancel.cPackets.remove(Integer.valueOf(args[0]));
            format = "" + Packet.packetIdToClassMap.get(Integer.valueOf(args[0]));
            Client.addChatMessage("Stopped cancelling: " + format.replace("class net.minecraft.src.", ""));
         } else {
            net.minecraft.src.MEDMEX.Modules.World.PacketCancel.cPackets.add(Integer.valueOf(args[0]));
            format = "" + Packet.packetIdToClassMap.get(Integer.valueOf(args[0]));
            Client.addChatMessage("Started cancelling: " + format.replace("class net.minecraft.src.", ""));
         }
      } catch (Exception var4) {
         System.out.println(var4);
         Client.addChatMessage("Usage: PacketCancel <packetID>");
      }

   }
}
