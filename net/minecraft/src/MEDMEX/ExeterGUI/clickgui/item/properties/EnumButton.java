package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Button;
import net.minecraft.src.de.Hero.settings.Setting;

public class EnumButton extends Button {
   private Setting property;

   public EnumButton(Setting property) {
      super(property.getName());
      this.property = property;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderMethods.drawRect(this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(55)) : (!this.isHovering(mouseX, mouseY) ? 288568115 : -2009910477));
      Minecraft.theMinecraft.fontRenderer.drawString(String.format("%s§7 %s", this.getLabel(), this.property.getValString()), (double)(this.x + 2.0F), (double)(this.y + 4.0F), this.getState() ? -1 : -5592406);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.isHovering(mouseX, mouseY)) {
         int length = this.property.getOptions().size();
         int currentindex = this.property.getOptions().indexOf(this.property.getValString());
         if (mouseButton == 0) {
            if (currentindex + 1 >= length) {
               this.property.setValString((String)this.property.getOptions().get(0));
            } else {
               this.property.setValString((String)this.property.getOptions().get(currentindex + 1));
            }
         } else if (mouseButton == 1) {
            if (currentindex - 1 < 0) {
               this.property.setValString((String)this.property.getOptions().get(length - 1));
            } else {
               this.property.setValString((String)this.property.getOptions().get(currentindex - 1));
            }
         }
      }

   }

   public int getHeight() {
      return 14;
   }

   public void toggle() {
   }

   public boolean getState() {
      return true;
   }
}
