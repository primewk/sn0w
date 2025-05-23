package net.minecraft.src.MEDMEX.ExeterGUI.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.ExeterGUI.clickgui.item.ModuleButton;
import net.minecraft.src.MEDMEX.Modules.Module;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public final class ClickGui extends GuiScreen {
   public static int offset;
   private static ClickGui clickGui;
   private final ArrayList<Panel> panels = new ArrayList();

   public ClickGui() {
      if (this.getPanels().isEmpty()) {
         this.load();
      }

   }

   public static ClickGui getClickGui() {
      return clickGui == null ? (clickGui = new ClickGui()) : clickGui;
   }

   private void load() {
      int x = -84;
      Module.Category[] var5;
      int var4 = (var5 = Module.Category.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         final Module.Category moduleType = var5[var3];
         x += 90;
         this.panels.add(new Panel(moduleType.name, x, 4, true) {
            public void setupItems() {
               Iterator var2 = Client.getModuleByCategory(moduleType).iterator();

               while(var2.hasNext()) {
                  Module m = (Module)var2.next();
                  this.addButton(new ModuleButton(m));
               }

            }
         });
      }

      this.panels.forEach((panel) -> {
         panel.getItems().sort((item1, item2) -> {
            return item1.getLabel().compareTo(item2.getLabel());
         });
      });
   }

   public void initGui() {
   }

   public void onGuiClosed() {
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (Mouse.hasWheel()) {
         int wheel = Mouse.getDWheel();
         if (wheel < 0) {
            offset += 26;
            if (offset > 0) {
               offset = 0;
            }
         } else if (wheel > 0) {
            offset -= 26;
         }
      }

      GL11.glPushMatrix();
      RenderMethods.drawGradientRect(0.0F, 0.0F, (float)this.mc.displayWidth, (float)this.mc.displayHeight, 536870912, -1879048192);
      GL11.glPopMatrix();
      this.panels.forEach((panel) -> {
         panel.drawScreen(mouseX, mouseY + offset, partialTicks);
      });
   }

   public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
      this.panels.forEach((panel) -> {
         panel.mouseClicked(mouseX, mouseY, clickedButton);
      });
   }

   public void mouseMovedOrUp(int mouseX, int mouseY, int releaseButton) {
      this.panels.forEach((panel) -> {
         panel.mouseMovedOrUp(mouseX, mouseY, releaseButton);
      });
   }

   public void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.panels.forEach((panel) -> {
         panel.keyTyped(par1, par2);
      });
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public final ArrayList<Panel> getPanels() {
      return this.panels;
   }
}
