package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class Xray extends Module {
   public static Xray instance;

   public Xray() {
      super("Xray", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onDisable() {
      this.mc.renderGlobal.loadRenderers();
   }

   public void onEnable() {
      this.mc.renderGlobal.loadRenderers();
   }
}
