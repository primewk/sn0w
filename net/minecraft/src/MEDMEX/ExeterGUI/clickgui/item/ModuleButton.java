package net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.ClickGui;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.RenderMethods;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.BooleanButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.EnumButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.KeybindButton;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.properties.NumberSlider;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.opengl.GL11;

public class ModuleButton extends Button {
   private final Module module;
   private List<Item> items = new ArrayList();
   private boolean subOpen;
   private int progress;

   public ModuleButton(Module module) {
      super(module.name);
      this.module = module;
      this.progress = 0;
      if (Client.settingsmanager.getSettingsByMod(module) != null) {
         Iterator var3 = Client.settingsmanager.getSettingsByMod(module).iterator();

         while(var3.hasNext()) {
            Setting property = (Setting)var3.next();
            if (property.isCheck()) {
               this.items.add(new BooleanButton(property));
            }

            if (property.isCombo()) {
               this.items.add(new EnumButton(property));
            }

            if (property.isSlider()) {
               this.items.add(new NumberSlider(property));
            }
         }
      }

      this.items.add(new KeybindButton(module));
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

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      if (!this.items.isEmpty()) {
         if (Client.settingsmanager.getSettingsByMod(this.module) != null) {
            GL11.glPushMatrix();
            GL11.glBindTexture(3553, Minecraft.theMinecraft.renderEngine.getTexture("/gear.png"));
            GL11.glTranslatef(this.getX() + (float)this.getWidth() - 6.7F, this.getY() - (float)ClickGui.offset + 7.7F - 0.3F, 0.0F);
            GL11.glRotatef(calculateRotation((float)this.progress), 0.0F, 0.0F, 1.0F);
            RenderMethods.drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
            GL11.glPopMatrix();
         }

         if (this.subOpen) {
            float height = 1.0F;
            ++this.progress;
            Iterator var6 = this.items.iterator();

            while(var6.hasNext()) {
               Item item = (Item)var6.next();
               item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
               item.setHeight(15);
               item.setWidth(this.width - 9);
               item.drawScreen(mouseX, mouseY, partialTicks);
            }
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (!this.items.isEmpty()) {
         if (mouseButton == 1 && this.isHovering(mouseX, mouseY + ClickGui.offset)) {
            this.subOpen = !this.subOpen;
         }

         if (this.subOpen) {
            Iterator var5 = this.items.iterator();

            while(true) {
               while(var5.hasNext()) {
                  Item item = (Item)var5.next();
                  if (!(item instanceof EnumButton) && !(item instanceof KeybindButton)) {
                     item.mouseClicked(mouseX, mouseY, mouseButton);
                  } else {
                     item.mouseClicked(mouseX, mouseY + ClickGui.offset, mouseButton);
                  }
               }

               return;
            }
         }
      }

   }

   public void keyTyped(char par1, int par2) {
      Iterator var4 = this.items.iterator();

      while(var4.hasNext()) {
         Item item = (Item)var4.next();
         item.keyTyped(par1, par2);
      }

   }

   public int getHeight() {
      if (!this.subOpen) {
         return 14;
      } else {
         int height = 14;

         Item item;
         for(Iterator var3 = this.items.iterator(); var3.hasNext(); height += item.getHeight() + 1) {
            item = (Item)var3.next();
         }

         return height + 2;
      }
   }

   public void toggle() {
      this.module.toggle();
   }

   public boolean getState() {
      return this.module.toggled;
   }
}
