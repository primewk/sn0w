package net.minecraft.src.MEDMEX.Utils.$nowmatica;

public class Vector3f {
   public float x;
   public float y;
   public float z;
   public static final Vector3f ZERO = new Vector3f();

   public Vector3f() {
      this.x = 0.0F;
      this.y = 0.0F;
      this.z = 0.0F;
   }

   public Vector3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3f add(Vector3f vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   public Vector3f add(float i) {
      return this.add(i, i, i);
   }

   public Vector3f add(float x, float y, float z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   public Vector3f sub(Vector3i vec) {
      return this.sub((float)vec.x, (float)vec.y, (float)vec.z);
   }

   public Vector3f sub(float i) {
      return this.sub(i, i, i);
   }

   public Vector3f sub(float x, float y, float z) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      return this;
   }

   public Vector3f clone() {
      return new Vector3f(this.x, this.y, this.z);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Vector3f)) {
         return false;
      } else {
         Vector3f o = (Vector3f)obj;
         return this.x == o.x && this.y == o.y && this.z == o.z;
      }
   }

   public int hashCode() {
      int hash = 7;
      int hash = (int)((float)(71 * hash) + this.x);
      hash = (int)((float)(71 * hash) + this.y);
      hash = (int)((float)(71 * hash) + this.z);
      return hash;
   }
}
