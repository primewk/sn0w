package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class SpawnTP extends Command {
   public SpawnTP() {
      super("SpawnTP", "SpawnTp", "SpawnTP", "stp");
   }

   public void onCommand(String[] args, String command) {
      try {
         Client.sendPacket(new Packet11PlayerPosition(Double.NaN, Double.NaN, Double.NaN, Double.NaN, true));
      } catch (Exception var4) {
      }

   }
}
