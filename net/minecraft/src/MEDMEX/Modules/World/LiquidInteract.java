package net.minecraft.src.MEDMEX.Modules.World;

import net.minecraft.src.MEDMEX.Modules.Module;

public class LiquidInteract extends Module {
   public static LiquidInteract instance;

   public LiquidInteract() {
      super("LiquidInteract", 0, Module.Category.WORLD);
      instance = this;
   }
}
