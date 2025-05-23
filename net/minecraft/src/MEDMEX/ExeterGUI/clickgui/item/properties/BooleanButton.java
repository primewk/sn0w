package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Button;
import net.minecraft.src.de.Hero.settings.Setting;

public class BooleanButton extends Button {
   private Setting property;

   public BooleanButton(Setting property) {
      super(property.getName());
      this.property = property;
      this.width = 15;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderMethods.drawRect(this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(77)) : (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
      Minecraft.theMinecraft.fontRenderer.drawString(this.getLabel(), (double)(this.x + 2.0F), (double)(this.y + 4.0F), this.getState() ? -1 : -5592406);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      this.isHovering(mouseX, mouseY);
   }

   public int getHeight() {
      return 14;
   }

   public void toggle() {
      this.property.setValBoolean(!Boolean.valueOf(this.property.getValBoolean()));
   }

   public boolean getState() {
      return Boolean.valueOf(this.property.getValBoolean());
   }
}
