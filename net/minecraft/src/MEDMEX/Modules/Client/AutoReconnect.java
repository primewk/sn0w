package net.minecraft.src.MEDMEX.Modules.Client;

import net.minecraft.src.MEDMEX.Modules.Module;

public class AutoReconnect extends Module {
   public static AutoReconnect instance;

   public AutoReconnect() {
      super("AutoReconnect", 0, Module.Category.CLIENT);
      instance = this;
   }
}
