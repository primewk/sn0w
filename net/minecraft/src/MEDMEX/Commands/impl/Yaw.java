package net.minecraft.src.MEDMEX.Commands.impl;

import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;

public class Yaw extends Command {
   public Yaw() {
      super("Yaw", "Sets yaw destination", "<Yaw> <x> <y> <z>", "Yaw");
   }

   public void onCommand(String[] args, String command) {
      try {
         Vec3D dest = new Vec3D((double)Integer.valueOf(args[0]), (double)Integer.valueOf(args[1]), (double)Integer.valueOf(args[2]));
         net.minecraft.src.MEDMEX.Modules.Player.Yaw.Destination = dest;
      } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException var4) {
         Client.addChatMessage("Usage: Toggle/t <module>");
      }

   }
}
