package net.minecraft.src.MEDMEX.Modules.Render;

import net.minecraft.src.MEDMEX.Modules.Module;

public class LogoutSpots extends Module {
   public static LogoutSpots instance;

   public LogoutSpots() {
      super("LogoutSpots", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onLog(String bound, String player) {
   }
}
