package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class Vclip extends Command {
   public Vclip() {
      super("Vclip", "Clip vertically", "Vclip <blocks>", "vclip");
   }

   public void onCommand(String[] args, String command) {
      try {
         this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Double.valueOf(args[0]), this.mc.thePlayer.posZ);
         Client.addChatMessage("Vclipped " + args[0] + " blocks");
      } catch (Exception var4) {
      }

   }
}
