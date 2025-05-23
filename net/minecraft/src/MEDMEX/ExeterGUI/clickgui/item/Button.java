package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.ClickGui;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Labeled;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Panel;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;

public class Button extends Item implements Labeled {
   private boolean state;

   public Button(String label) {
      super(label);
      this.height = 15;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderMethods.drawGradientRect(this.x, this.y, this.x + (float)this.width, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(55)) : (!this.isHovering(mouseX, mouseY) ? 861230421 : 2007673515), this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(55)) : (!this.isHovering(mouseX, mouseY) ? 1431655765 : 1722460843));
      Minecraft.theMinecraft.fontRenderer.drawString(this.getLabel(), (double)(this.x + 2.0F), (double)(this.y + 4.0F), this.getState() ? -1 : -5592406);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isHovering(mouseX, mouseY + ClickGui.offset)) {
         this.state = !this.state;
         this.toggle();
      }

   }

   public void toggle() {
   }

   public boolean getState() {
      return this.state;
   }

   public int getHeight() {
      return 14;
   }

   protected boolean isHovering(int mouseX, int mouseY) {
      Iterator var4 = ClickGui.getClickGui().getPanels().iterator();

      while(var4.hasNext()) {
         Panel panel = (Panel)var4.next();
         if (panel.drag) {
            return false;
         }
      }

      return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
   }
}
