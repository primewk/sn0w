package net.minecraft.src.MEDMEX.ExeterGUI.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Button;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.Item;
import org.lwjgl.opengl.GL11;

public abstract class Panel {
   private Minecraft minecraft;
   private final String label;
   private int angle;
   private int x;
   private int y;
   private int x2;
   private int y2;
   private int width;
   private int height;
   private boolean open;
   public boolean drag;
   private final ArrayList<Item> items;

   public Panel(String label, int x, int y, boolean open) {
      this.minecraft = Minecraft.theMinecraft;
      this.items = new ArrayList();
      this.label = label;
      this.x = x;
      this.y = y;
      this.angle = 180;
      this.width = 88;
      this.height = 18;
      this.open = open;
      this.setupItems();
   }

   public abstract void setupItems();

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drag(mouseX, mouseY);
      float totalItemHeight = this.open ? this.getTotalItemHeight() - 2.0F : 0.0F;
      RenderMethods.drawGradientRect((float)this.x, (float)this.y - 1.5F + (float)ClickGui.offset, (float)(this.x + this.width), (float)(this.y + this.height - 6 + ClickGui.offset), Colors.getClientColorCustomAlpha(77), Colors.getClientColorCustomAlpha(77));
      if (this.open) {
         RenderMethods.drawRect((float)this.x, (float)this.y + 12.5F + (float)ClickGui.offset, (float)(this.x + this.width), this.open ? (float)(this.y + this.height) + (float)ClickGui.offset + totalItemHeight : (float)(this.y + this.height - 1), 1996488704);
      }

      Minecraft.theMinecraft.fontRenderer.drawString(this.getLabel(), (double)((float)this.x + 3.0F), (double)((float)this.y + 1.5F + (float)ClickGui.offset), -1);
      if (!this.open) {
         if (this.angle > 0) {
            this.angle -= 6;
         }
      } else if (this.angle < 180) {
         this.angle += 6;
      }

      GL11.glPushMatrix();
      RenderMethods.glColor(new Color(255, 255, 255, 255));
      GL11.glBindTexture(3553, Minecraft.theMinecraft.renderEngine.getTexture("/arrow.png"));
      GL11.glTranslatef((float)(this.getX() + this.getWidth() - 7), (float)(this.getY() + 6) - 0.3F + (float)ClickGui.offset, 0.0F);
      GL11.glRotatef(calculateRotation((float)this.angle), 0.0F, 0.0F, 1.0F);
      RenderMethods.drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
      GL11.glPopMatrix();
      if (this.open) {
         float y = (float)(this.getY() + this.getHeight()) - 3.0F + (float)ClickGui.offset;

         Item item;
         for(Iterator var7 = this.getItems().iterator(); var7.hasNext(); y += (float)item.getHeight() + 1.5F) {
            item = (Item)var7.next();
            item.setLocation((float)this.x + 2.0F, y);
            item.setWidth(this.getWidth() - 4);
            item.drawScreen(mouseX, mouseY, partialTicks);
         }
      }

   }

   private void drag(int mouseX, int mouseY) {
      if (this.drag) {
         this.x = this.x2 + mouseX;
         this.y = this.y2 + mouseY;
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
         this.x2 = this.x - mouseX;
         this.y2 = this.y - mouseY;
         ClickGui.getClickGui().getPanels().forEach((panel) -> {
            if (panel.drag) {
               panel.drag = false;
            }

         });
         this.drag = true;
      } else if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
         this.open = !this.open;
      } else if (this.open) {
         this.getItems().forEach((item) -> {
            item.mouseClicked(mouseX, mouseY, mouseButton);
         });
      }
   }

   public void addButton(Button button) {
      this.items.add(button);
   }

   public void mouseMovedOrUp(int mouseX, int mouseY, int releaseButton) {
      if (releaseButton == 0) {
         this.drag = false;
      }

      if (this.open) {
         this.getItems().forEach((item) -> {
            item.mouseReleased(mouseX, mouseY, releaseButton);
         });
      }
   }

   public void keyTyped(char par1, int par2) {
      this.getItems().forEach((item) -> {
         item.keyTyped(par1, par2);
      });
   }

   public final String getLabel() {
      return this.label;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public boolean getOpen() {
      return this.open;
   }

   public final ArrayList<Item> getItems() {
      return this.items;
   }

   private boolean isHovering(int mouseX, int mouseY) {
      return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
   }

   public static float calculateRotation(float var0) {
      if ((var0 %= 360.0F) >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   private float getTotalItemHeight() {
      float height = 0.0F;

      Item item;
      for(Iterator var3 = this.getItems().iterator(); var3.hasNext(); height += (float)item.getHeight() + 1.5F) {
         item = (Item)var3.next();
      }

      return height;
   }

   public void setX(int dragX) {
      this.x = dragX;
   }

   public void setY(int dragY) {
      this.y = dragY;
   }
}
