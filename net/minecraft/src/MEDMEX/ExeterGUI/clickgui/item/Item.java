package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item;

import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.ClickGui;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.Labeled;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.BooleanButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.EnumButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.KeybindButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.NumberSlider;

public class Item implements Labeled {
   private final String label;
   protected float x;
   protected float y;
   protected int width;
   protected int height;

   public Item(String label) {
      this.label = label;
   }

   public void setLocation(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
   }

   public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
   }

   public void keyTyped(char par1, int par2) {
   }

   public final String getLabel() {
      return this.label;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y + (float)ClickGui.offset;
   }

   public int getWidth() {
      return !(this instanceof BooleanButton) && !(this instanceof EnumButton) && !(this instanceof KeybindButton) && !(this instanceof NumberSlider) ? this.width : this.width + 8;
   }

   public int getHeight() {
      return this.height;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }
}
