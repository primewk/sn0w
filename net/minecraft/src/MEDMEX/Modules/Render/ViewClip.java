package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class ViewClip extends Module {
   public static ViewClip instance;

   public ViewClip() {
      super("ViewClip", 0, Module.Category.RENDER);
      instance = this;
   }
}
