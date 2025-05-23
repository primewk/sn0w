package net.minecraft.src.MEDMEX.Commands.impl;

import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;
import net.minecraft.src.MEDMEX.Modules.Module;

public class Toggle extends Command {
   public Toggle() {
      super("Toggle", "toggles a module by name.", "Toggle <name>", "t");
   }

   public void onCommand(String[] args, String command) {
      try {
         String moduleName = args[0];
         boolean foundModule = false;
         Iterator var6 = Client.modules.iterator();

         while(var6.hasNext()) {
            Module module = (Module)var6.next();
            if (module.name.equalsIgnoreCase(moduleName)) {
               module.toggle();
               Client.addChatMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.name);
               foundModule = true;
               break;
            }
         }

         if (!foundModule) {
            Client.addChatMessage("Could not find module.");
         }
      } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException var7) {
         Client.addChatMessage("Usage: Toggle/t <module>");
      }

   }
}
