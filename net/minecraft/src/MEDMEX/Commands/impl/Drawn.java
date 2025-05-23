package net.minecraft.src.MEDMEX.Commands.impl;

import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Drawn extends Command {
   public Drawn() {
      super("Drawn", "Removes/adds modules from drawn arraylist", "drawn <module>", "d");
   }

   public void onCommand(String[] args, String command) {
      try {
         if (args.length == 1) {
            String moduleN = args[0];
            Iterator var5 = Client.modules.iterator();

            while(var5.hasNext()) {
               Module module = (Module)var5.next();
               if (module.name.equalsIgnoreCase(moduleN)) {
                  if (Client.drawn.contains(module)) {
                     Client.drawn.remove(module);
                     Client.addChatMessage(module.name + " Will now be drawn again");
                  } else {
                     Client.drawn.add(module);
                     Client.addChatMessage(module.name + " Will no longer be drawn");
                  }
               }
            }
         }
      } catch (Exception var6) {
         Client.addChatMessage("Usage: Drawn <module>");
      }

   }
}
