package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.Modules.Movement.InventoryMove;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput {
   private boolean[] movementKeyStates = new boolean[10];
   private GameSettings gameSettings;

   public MovementInputFromOptions(GameSettings var1) {
      this.gameSettings = var1;
   }

   public void checkKeyForMovementInput(int var1, boolean var2) {
      byte var3 = -1;
      if (var1 == this.gameSettings.keyBindForward.keyCode) {
         var3 = 0;
      }

      if (var1 == this.gameSettings.keyBindBack.keyCode) {
         var3 = 1;
      }

      if (var1 == this.gameSettings.keyBindLeft.keyCode) {
         var3 = 2;
      }

      if (var1 == this.gameSettings.keyBindRight.keyCode) {
         var3 = 3;
      }

      if (var1 == this.gameSettings.keyBindJump.keyCode) {
         var3 = 4;
      }

      if (var1 == this.gameSettings.keyBindSneak.keyCode) {
         var3 = 5;
      }

      if (var3 >= 0) {
         this.movementKeyStates[var3] = var2;
      }

   }

   public void resetKeyState() {
      for(int var1 = 0; var1 < 10; ++var1) {
         this.movementKeyStates[var1] = false;
      }

   }

   public void updatePlayerMoveState(EntityPlayer var1) {
      if (InventoryMove.instance != null && InventoryMove.instance.isEnabled() && !(Minecraft.theMinecraft.currentScreen instanceof GuiChat) && !(Minecraft.theMinecraft.currentScreen instanceof GuiEditSign)) {
         this.moveStrafe = 0.0F;
         this.moveForward = 0.0F;
         if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.keyCode)) {
            ++this.moveForward;
         }

         if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.keyCode)) {
            --this.moveForward;
         }

         if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.keyCode)) {
            ++this.moveStrafe;
         }

         if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.keyCode)) {
            --this.moveStrafe;
         }

         this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.keyCode);
         this.sneak = Keyboard.isKeyDown(this.gameSettings.keyBindSneak.keyCode);
         if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
         }
      } else {
         this.moveStrafe = 0.0F;
         this.moveForward = 0.0F;
         if (this.movementKeyStates[0]) {
            ++this.moveForward;
         }

         if (this.movementKeyStates[1]) {
            --this.moveForward;
         }

         if (this.movementKeyStates[2]) {
            ++this.moveStrafe;
         }

         if (this.movementKeyStates[3]) {
            --this.moveStrafe;
         }

         this.jump = this.movementKeyStates[4];
         this.sneak = this.movementKeyStates[5];
         if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
         }
      }

   }
}
