package net.minecraft.src.MEDMEX.Utils.$nowmatica;

public class Vector4i {
   public int x;
   public int y;
   public int z;
   public int w;
   public static final Vector4i ZERO = new Vector4i();

   public Vector4i() {
      this.x = 0;
      this.y = 0;
      this.z = 0;
      this.w = 0;
   }

   public Vector4i(int x, int y, int z, int w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public Vector4i add(Vector4i vec) {
      return this.add(vec.x, vec.y, vec.z, vec.w);
   }

   public Vector4i add(int i) {
      return this.add(i, i, i, i);
   }

   public Vector4i add(int x, int y, int z, int w) {
      this.x += x;
      this.y += y;
      this.z += z;
      this.w += w;
      return this;
   }

   public Vector4i sub(Vector4i vec) {
      return this.sub(vec.x, vec.y, vec.z, vec.w);
   }

   public Vector4i sub(int i) {
      return this.sub(i, i, i, i);
   }

   public Vector4i sub(int x, int y, int z, int w) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      this.w -= w;
      return this;
   }

   public Vector4i clone() {
      return new Vector4i(this.x, this.y, this.z, this.w);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Vector4i)) {
         return false;
      } else {
         Vector4i o = (Vector4i)obj;
         return this.x == o.x && this.y == o.y && this.z == o.z && this.w == o.w;
      }
   }

   public int hashCode() {
      int hash = 7;
      int hash = 71 * hash + this.x;
      hash = 71 * hash + this.y;
      hash = 71 * hash + this.z;
      hash = 71 * hash + this.w;
      return hash;
   }
}
