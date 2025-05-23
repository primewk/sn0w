package net.minecraft.src.MEDMEX.Modules.Player;

import net.minecraft.src.MEDMEX.Modules.Module;

public class NoCollide extends Module {
   public static NoCollide instance;

   public NoCollide() {
      super("NoCollide", 0, Module.Category.PLAYER);
      instance = this;
   }
}
