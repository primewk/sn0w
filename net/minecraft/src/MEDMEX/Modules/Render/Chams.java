package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class Chams extends Module {
   public static Chams instance;

   public Chams() {
      super("Chams", 0, Module.Category.RENDER);
      instance = this;
   }
}
