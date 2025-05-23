package net.minecraft.src.MEDMEX.Modules.Render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.RenderManager;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.RenderUtils;

public class Search extends Module {
   public static List<Integer> blocks = new ArrayList();
   public static List<Integer> searchx = new ArrayList();
   public static List<Integer> searchy = new ArrayList();
   public static List<Integer> searchz = new ArrayList();
   public static Search instance;

   public Search() {
      super("Search", 0, Module.Category.RENDER);
      instance = this;
   }

   public void onDisable() {
      searchx.clear();
      searchy.clear();
      searchz.clear();
      this.mc.renderGlobal.loadRenderers();
   }

   public void onEnable() {
      this.mc.renderGlobal.loadRenderers();
   }

   public void onRender() {
      if (this.isEnabled()) {
         for(int i = 0; i < searchx.size(); ++i) {
            double renderX = (double)(Integer)searchx.get(i) - RenderManager.renderPosX;
            Double RenderY = (double)(Integer)searchy.get(i) - RenderManager.renderPosY;
            double renderZ = (double)(Integer)searchz.get(i) - RenderManager.renderPosZ;
            if (blocks.contains(this.mc.theWorld.getBlockId((Integer)searchx.get(i), (Integer)searchy.get(i), (Integer)searchz.get(i)))) {
               RenderUtils.drawOutlinedBlockESP(renderX, RenderY, renderZ, 0.709F, 0.576F, 0.858F, 1.0F, 1.0F);
            }
         }
      }

   }
}
