package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class Friends extends Command {
   public Friends() {
      super("Friends", "Add/Remove friends", "friends <add/del> <name>", "f");
   }

   public void onCommand(String[] args, String command) {
      try {
         if (args.length == 0) {
            Client.addChatMessage("Friends: " + Client.friends.toString());
         }

         if (args.length == 2) {
            String friendname = args[1];
            if (args[0].equalsIgnoreCase("add")) {
               Client.friends.add(friendname);
               Client.addChatMessage("Added " + friendname + " to friendslist");
            }

            if (args[0].equalsIgnoreCase("del")) {
               Client.friends.remove(friendname);
               Client.addChatMessage("Removed " + friendname + " from friendslist");
            }
         }
      } catch (Exception var4) {
         Client.addChatMessage("Usage: friends <add/del> <name>");
      }

   }
}
