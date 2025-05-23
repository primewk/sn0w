package net.minecraft.src.MEDMEX.Utils.$nowmatica;

public class Vector3i {
   public int x;
   public int y;
   public int z;
   public static final Vector3i ZERO = new Vector3i();

   public Vector3i() {
      this.x = 0;
      this.y = 0;
      this.z = 0;
   }

   public Vector3i(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3i add(Vector3i vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   public Vector3i add(int i) {
      return this.add(i, i, i);
   }

   public Vector3i add(int x, int y, int z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public Vector3i sub(Vector3i vec) {
      return this.sub(vec.x, vec.y, vec.z);
   }

   public Vector3i sub(int i) {
      return this.sub(i, i, i);
   }

   public Vector3i sub(int x, int y, int z) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      return this;
   }

   public Vector3i clone() {
      return new Vector3i(this.x, this.y, this.z);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Vector3i)) {
         return false;
      } else {
         Vector3i o = (Vector3i)obj;
         return this.x == o.x && this.y == o.y && this.z == o.z;
      }
   }

   public int hashCode() {
      int hash = 7;
      int hash = 71 * hash + this.x;
      hash = 71 * hash + this.y;
      hash = 71 * hash + this.z;
      return hash;
   }
}
