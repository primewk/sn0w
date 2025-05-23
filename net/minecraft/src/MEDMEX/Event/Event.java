package net.minecraft.src.MEDMEX.Event;

public class Event<T> {
   public boolean cancelled;
   public EventType type;
   public EventDirection direction;
   private float rotationYaw;
   private float rotationYawHead;
   private float rotationPitch;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public EventType getType() {
      return this.type;
   }

   public void setType(EventType type) {
      this.type = type;
   }

   public EventDirection getDirection() {
      return this.direction;
   }

   public void setDirection(EventDirection direction) {
      this.direction = direction;
   }

   public boolean isPre() {
      if (this.type == null) {
         return false;
      } else {
         return this.type == EventType.PRE;
      }
   }

   public boolean isPost() {
      if (this.type == null) {
         return false;
      } else {
         return this.type == EventType.POST;
      }
   }

   public boolean isIncoming() {
      if (this.direction == null) {
         return false;
      } else {
         return this.direction == EventDirection.INCOMING;
      }
   }

   public boolean isOutgoing() {
      if (this.direction == null) {
         return false;
      } else {
         return this.direction == EventDirection.OUTGOING;
      }
   }

   public float getRotationYaw() {
      return this.rotationYaw;
   }

   public void setRotationYaw(float rotationYaw) {
      this.rotationYaw = rotationYaw;
   }

   public float getRotationYawHead() {
      return this.rotationYawHead;
   }

   public void setRotationYawHead(float rotationYawHead) {
      this.rotationYawHead = rotationYawHead;
   }

   public float getRotationPitch() {
      return this.rotationPitch;
   }

   public void setRotationPitch(float rotationPitch) {
      this.rotationPitch = rotationPitch;
   }
}
