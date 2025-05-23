package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class NoHurtCam extends Module {
   public static NoHurtCam instance;

   public NoHurtCam() {
      super("NoHurtCam", 0, Module.Category.RENDER);
      instance = this;
   }
}
