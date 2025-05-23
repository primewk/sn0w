package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Button;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.input.Keyboard;

public class KeybindButton extends Button {
   Module m;
   String keystring = "";
   boolean listening = false;

   public KeybindButton(Module m) {
      super(m.name + " key");
      this.width = 15;
      this.m = m;
      this.keystring = Keyboard.getKeyName(this.m.keyCode.getKeyCode());
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderMethods.drawRect(this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(77)) : (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
      Minecraft.theMinecraft.fontRenderer.drawString("Key: " + this.keystring, (double)(this.x + 2.0F), (double)(this.y + 4.0F), this.getState() ? -1 : -5592406);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.isHovering(mouseX, mouseY)) {
         this.keystring = "...";
         this.listening = true;
      }

   }

   public void keyTyped(char par1, int par2) {
      if (this.listening) {
         if (par2 == 1) {
            par2 = 0;
         }

         this.keystring = Keyboard.getKeyName(par2);
         this.m.keyCode.setKeyCode(par2);
         this.listening = false;
      }

   }

   public int getHeight() {
      return 14;
   }
}
