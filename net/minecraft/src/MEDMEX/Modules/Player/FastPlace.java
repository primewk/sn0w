package net.minecraft.src.MEDMEX.Modules.Player;

import java.lang.reflect.Field;
import net.minecraft.src.MEDMEX.Modules.Module;

public class FastPlace extends Module {
   public static FastPlace instance;
   private Field rcdelay = null;

   public FastPlace() {
      super("FastPlace", 0, Module.Category.PLAYER);
      instance = this;
   }
}
