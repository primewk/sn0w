package net.minecraft.src.MEDMEX.Utils.$nowmatica;

import net.minecraft.src.Block;
import net.minecraft.src.Vec3D;

public class $nowmatic {
   Block block;
   Vec3D pos;

   public $nowmatic(Vec3D pos, Block block) {
      this.pos = pos;
      this.block = block;
   }

   public Vec3D getPos() {
      return this.pos;
   }

   public Block getBlock() {
      return this.block;
   }
}
