package net.minecraft.src.MEDMEX.Modules.Movement;

import net.minecraft.src.MEDMEX.Modules.Module;

public class InventoryMove extends Module {
   public static InventoryMove instance;

   public InventoryMove() {
      super("InventoryMove", 0, Module.Category.MOVEMENT);
      instance = this;
   }
}
