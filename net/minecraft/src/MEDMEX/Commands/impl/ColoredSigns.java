package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class ColoredSigns extends Command {
   public ColoredSigns() {
      super("ColoredSigns", "Set text for coloredsigns", "ColoredSigns <line> <text>", "cs");
   }

   public void onCommand(String[] args, String command) {
      try {
         int line = Integer.valueOf(args[0]);
         String text = args[1].replace("&", "§");
         net.minecraft.src.MEDMEX.Modules.World.ColoredSigns.newlines[line] = text;
         Client.addChatMessage("Line " + args[0] + " set to: {" + text + "§7}");
      } catch (Exception var5) {
      }

   }
}
