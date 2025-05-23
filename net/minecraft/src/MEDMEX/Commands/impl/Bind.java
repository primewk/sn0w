package net.minecraft.src.MEDMEX.Commands.impl;

import java.util.Iterator;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
   public Bind() {
      super("Bind", "Binds a module by name.", "bind <name> <key> | clear", "b");
   }

   public void onCommand(String[] args, String command) {
      if (args.length == 0) {
         Client.addChatMessage("Usage: bind <module> <key> or bind clear");
      }

      if (args.length == 2) {
         String moduleName = args[0];
         String keyName = args[1];
         Iterator var6 = Client.modules.iterator();

         while(var6.hasNext()) {
            Module module = (Module)var6.next();
            if (module.name.equalsIgnoreCase(moduleName)) {
               module.keyCode.setKeyCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
               Client.addChatMessage(String.format("Bound %s to %s", module.name, Keyboard.getKeyName(module.getKey())));
               break;
            }
         }
      }

      if (args.length == 1) {
         if (args[0].equalsIgnoreCase("clear")) {
            Client.addChatMessage("Cleared all binds.");
            Iterator var8 = Client.modules.iterator();

            while(var8.hasNext()) {
               Module module = (Module)var8.next();
               module.keyCode.setKeyCode(0);
            }
         } else {
            Client.addChatMessage("Usage: bind <module> <key> or bind clear");
         }
      }

   }
}
