package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class Wake extends Command {
   public Wake() {
      super("Wake", "Wake serverside", "Wake", "Wake");
   }

   public void onCommand(String[] args, String command) {
      try {
         Client.sendPacket(new Packet19EntityAction(this.mc.thePlayer, 3));
      } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException var4) {
         Client.addChatMessage("Usage: Wake");
      }

   }
}
