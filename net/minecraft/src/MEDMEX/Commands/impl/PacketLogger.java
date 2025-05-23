package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.Packet;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class PacketLogger extends Command {
   public PacketLogger() {
      super("PacketLogger", "Log specified packets", "PacketLogger <packetID>", "pl");
   }

   public void onCommand(String[] args, String command) {
      try {
         String format;
         if (net.minecraft.src.MEDMEX.Modules.World.PacketLogger.lPackets.contains(Integer.valueOf(args[0]))) {
            net.minecraft.src.MEDMEX.Modules.World.PacketLogger.lPackets.remove(Integer.valueOf(args[0]));
            format = "" + Packet.packetIdToClassMap.get(Integer.valueOf(args[0]));
            Client.addChatMessage("Stopped logging: " + format.replace("class net.minecraft.src.", ""));
         } else {
            net.minecraft.src.MEDMEX.Modules.World.PacketLogger.lPackets.add(Integer.valueOf(args[0]));
            format = "" + Packet.packetIdToClassMap.get(Integer.valueOf(args[0]));
            Client.addChatMessage("Started logging: " + format.replace("class net.minecraft.src.", ""));
         }
      } catch (Exception var4) {
         System.out.println(var4);
         Client.addChatMessage("Usage: PacketLogger <packetID>");
      }

   }
}
