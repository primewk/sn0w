package net.minecraft.src.MEDMEX.Commands.impl;

import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;
import net.minecraft.src.MEDMEX.Commands.CommandManager;

public class Help extends Command {
   public Help() {
      super("Help", "Help command", "help", "help");
   }

   public void onCommand(String[] args, String command) {
      Iterator var4 = CommandManager.commands.iterator();

      while(var4.hasNext()) {
         Command c = (Command)var4.next();
         Client.addChatMessage(c.name + " - " + c.syntax + " - " + c.description);
      }

   }
}
