package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class Fullbright extends Module {
   public static Fullbright instance;

   public Fullbright() {
      super("Fullbright", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onDisable() {
      this.mc.gameSettings.updateWorldLightLevels();
      this.mc.renderGlobal.updateAllRenderers();
   }

   public void onEnable() {
      this.mc.gameSettings.updateWorldLightLevels();
      this.mc.renderGlobal.updateAllRenderers();
   }
}
