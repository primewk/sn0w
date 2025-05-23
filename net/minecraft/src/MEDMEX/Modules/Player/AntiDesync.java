package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.MEDMEX.Modules.Module;

public class AntiDesync extends Module {
   public static AntiDesync instance;

   public AntiDesync() {
      super("AntiDesync", 0, Module.Category.PLAYER);
      instance = this;
   }
}
