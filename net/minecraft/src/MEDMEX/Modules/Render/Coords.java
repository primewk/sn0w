package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class Coords extends Module {
   public static Coords instance;

   public Coords() {
      super("Coords", 0, Module.Category.RENDER);
      instance = this;
   }
}
