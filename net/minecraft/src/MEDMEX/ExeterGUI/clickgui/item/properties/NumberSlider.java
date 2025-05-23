package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.ClickGui;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Colors;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Panel;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Item;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.input.Mouse;

public class NumberSlider extends Item {
   private Setting numberProperty;
   private Number min;
   private Number max;
   private int difference;

   public NumberSlider(Setting numberProperty) {
      super(numberProperty.getName());
      this.numberProperty = numberProperty;
      this.min = numberProperty.getMin();
      this.max = numberProperty.getMax();
      this.difference = this.max.intValue() - this.min.intValue();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.dragSetting(mouseX, mouseY);
      RenderMethods.drawRect(this.x, this.y, Double.valueOf(this.numberProperty.getValDouble()).floatValue() <= this.min.floatValue() ? this.x : this.x + ((float)this.width + 7.4F) * this.partialMultiplier(), this.y + (float)this.height - 0.5F, !this.isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(55));
      Minecraft.theMinecraft.fontRenderer.drawString(String.format("%s§7 %s", this.getLabel(), this.numberProperty.getValDouble()), (double)(this.x + 2.0F), (double)(this.y + 4.0F), -1);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.isHovering(mouseX, mouseY) && mouseButton == 0) {
         this.setSettingFromX(mouseX);
      }

   }

   private void setSettingFromX(int mouseX) {
      float percent = ((float)mouseX - this.x) / ((float)this.width + 8.0F);
      if (!this.numberProperty.onlyInt()) {
         double result = Double.valueOf(this.numberProperty.getMin()) + (double)((float)this.difference * percent);
         this.numberProperty.setValDouble((double)Math.round(10.0D * result) / 10.0D);
      } else if (this.numberProperty.onlyInt()) {
         this.numberProperty.setValDouble((double)((int)this.numberProperty.getMin() + (int)((float)this.difference * percent)));
      }

   }

   public int getHeight() {
      return 14;
   }

   private void dragSetting(int mouseX, int mouseY) {
      if (this.isHovering(mouseX, mouseY) && Mouse.isButtonDown(0)) {
         this.setSettingFromX(mouseX);
      }

   }

   private boolean isHovering(int mouseX, int mouseY) {
      Iterator var4 = ClickGui.getClickGui().getPanels().iterator();

      while(var4.hasNext()) {
         Panel panel = (Panel)var4.next();
         if (panel.drag) {
            return false;
         }
      }

      return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
   }

   private float getValueWidth() {
      return Double.valueOf(this.numberProperty.getMax()).floatValue() - Double.valueOf(this.numberProperty.getMin()).floatValue() + Double.valueOf(this.numberProperty.getValDouble()).floatValue();
   }

   private float middle() {
      return this.max.floatValue() - this.min.floatValue();
   }

   private float part() {
      return Double.valueOf(this.numberProperty.getValDouble()).floatValue() - this.min.floatValue();
   }

   private float partialMultiplier() {
      return this.part() / this.middle();
   }
}
