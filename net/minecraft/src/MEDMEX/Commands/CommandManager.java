package net.minecraft.src.MEDMEX.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.impl.Bind;
import net.minecraft.src.MEDMEX.Commands.impl.ColoredSigns;
import net.minecraft.src.MEDMEX.Commands.impl.Drawn;
import net.minecraft.src.MEDMEX.Commands.impl.Friends;
import net.minecraft.src.MEDMEX.Commands.impl.Help;
import net.minecraft.src.MEDMEX.Commands.impl.PacketCancel;
import net.minecraft.src.MEDMEX.Commands.impl.PacketLogger;
import net.minecraft.src.MEDMEX.Commands.impl.Schem;
import net.minecraft.src.MEDMEX.Commands.impl.Search;
import net.minecraft.src.MEDMEX.Commands.impl.SpawnTP;
import net.minecraft.src.MEDMEX.Commands.impl.Toggle;
import net.minecraft.src.MEDMEX.Commands.impl.Vclip;
import net.minecraft.src.MEDMEX.Commands.impl.Wake;
import net.minecraft.src.MEDMEX.Commands.impl.Waypoints;
import net.minecraft.src.MEDMEX.Commands.impl.Yaw;
import net.minecraft.src.MEDMEX.Event.listeners.EventChat;

public class CommandManager {
   public static boolean chatencryption = false;
   public static List<Command> commands = new ArrayList();
   public String prefix = ".";

   public CommandManager() {
      this.setup();
   }

   public void setup() {
      commands.add(new Toggle());
      commands.add(new Bind());
      commands.add(new ColoredSigns());
      commands.add(new Waypoints());
      commands.add(new Vclip());
      commands.add(new Help());
      commands.add(new Search());
      commands.add(new Friends());
      commands.add(new Yaw());
      commands.add(new Drawn());
      commands.add(new PacketCancel());
      commands.add(new PacketLogger());
      commands.add(new SpawnTP());
      commands.add(new Wake());
      commands.add(new Schem());
   }

   public void handleChat(EventChat event) {
      String message = event.getMessage();
      if (message.startsWith(this.prefix)) {
         event.setCancelled(true);
         message = message.substring(this.prefix.length());
         boolean foundCommand = false;
         if (message.split(" ").length > 0) {
         }

         String commandName = message.split(" ")[0];
         Iterator var6 = commands.iterator();

         while(var6.hasNext()) {
            Command c = (Command)var6.next();
            if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
               c.onCommand((String[])Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
               foundCommand = true;
               break;
            }
         }

         if (!foundCommand) {
            Client.addChatMessage("Could not find command.");
         }

      }
   }
}
