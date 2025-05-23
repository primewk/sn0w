package net.minecraft.src.MEDMEX.Modules.Client;

import net.minecraft.src.MEDMEX.Modules.Module;

public class ChatTime extends Module {
   public static ChatTime instance;

   public ChatTime() {
      super("ChatTime", 0, Module.Category.CLIENT);
      instance = this;
   }
}
