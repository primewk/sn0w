package net.minecraft.src.MEDMEX.Modules.Client;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.impl.Schem;
import net.minecraft.src.MEDMEX.Event.Event;
import net.minecraft.src.MEDMEX.Event.listeners.EventUpdate;
import net.minecraft.src.MEDMEX.Modules.Module;
import net.minecraft.src.MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.$nowmatic;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.SchematicWorld;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.Vector3f;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.Vector3i;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.Vector4i;
import net.minecraft.src.de.Hero.settings.Setting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class $nowmatica extends Module {
   public static $nowmatica instance;
   boolean lastLayersVal = false;
   double lastYlevelVal = 0.0D;
   RenderBlocks rb;
   public float blockDelta = 0.005F;
   public static boolean needsUpdate = true;
   private final int glBlockList = GL11.glGenLists(1);
   private int bufferSizeQuad = 1048576;
   private FloatBuffer cBufferQuad;
   private FloatBuffer vBufferQuad;
   private int objectCountQuad;
   private boolean needsExpansionQuad;
   private int bufferSizeLine;
   private FloatBuffer cBufferLine;
   private FloatBuffer vBufferLine;
   private int objectCountLine;
   private boolean needsExpansionLine;

   public $nowmatica() {
      super("$nowmatica", 0, Module.Category.CLIENT);
      this.cBufferQuad = BufferUtils.createFloatBuffer(this.bufferSizeQuad * 4);
      this.vBufferQuad = BufferUtils.createFloatBuffer(this.bufferSizeQuad * 3);
      this.objectCountQuad = -1;
      this.needsExpansionQuad = false;
      this.bufferSizeLine = 1048576;
      this.cBufferLine = BufferUtils.createFloatBuffer(this.bufferSizeLine * 4);
      this.vBufferLine = BufferUtils.createFloatBuffer(this.bufferSizeLine * 3);
      this.objectCountLine = -1;
      this.needsExpansionLine = false;
      instance = this;
   }

   public void setup() {
      Client.settingsmanager.rSetting(new Setting("Layers", this, false));
      Client.settingsmanager.rSetting(new Setting("Ylevel", this, 0.0D, 0.0D, 256.0D, true));
      Client.settingsmanager.rSetting(new Setting("Printer", this, false));
   }

   public void onEnable() {
      needsUpdate = true;
   }

   public void onEvent(Event e) {
      if (e instanceof EventUpdate) {
         if (e.isPre()) {
            if (this.lastLayersVal != this.getSet("Layers").getValBoolean()) {
               needsUpdate = true;
            }

            if (this.lastYlevelVal != this.getSet("Ylevel").getValDouble()) {
               needsUpdate = true;
            }

            if (this.getSet("Printer").getValBoolean() && !Schem.schematic.isEmpty()) {
               for(int i = 0; i < Schem.schematic.size(); ++i) {
                  $nowmatic s = ($nowmatic)Schem.schematic.get(i);
                  Vec3D pos = s.getPos();
                  int[] offsets;
                  int x;
                  int y;
                  int z;
                  Vec3D newpos;
                  int blockid;
                  int slot;
                  int[] values;
                  if (this.getSet("Layers").getValBoolean()) {
                     if ((double)((int)this.getSet("Ylevel").getValDouble()) == pos.yCoord) {
                        offsets = this.offsets();
                        x = (int)pos.xCoord + Schem.moveX;
                        y = (int)pos.yCoord + Schem.moveY;
                        z = (int)pos.zCoord + Schem.moveZ;
                        newpos = new Vec3D((double)x, (double)y, (double)z);
                        if (this.mc.thePlayer.getDistance((double)x, (double)y, (double)z) <= 5.0D) {
                           blockid = s.getBlock().blockID;
                           slot = InventoryUtils.getHotbarslotBlock(blockid);
                           if (slot != -1) {
                              this.mc.thePlayer.inventory.currentItem = slot;
                           }

                           if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().itemID == blockid) {
                              values = this.getDir(newpos);
                              this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                           }
                        }
                     }
                  } else {
                     offsets = this.offsets();
                     x = (int)pos.xCoord + Schem.moveX;
                     y = (int)pos.yCoord + Schem.moveY;
                     z = (int)pos.zCoord + Schem.moveZ;
                     newpos = new Vec3D((double)x, (double)y, (double)z);
                     if (this.mc.thePlayer.getDistance((double)x, (double)y, (double)z) <= 5.0D) {
                        blockid = s.getBlock().blockID;
                        slot = InventoryUtils.getHotbarslotBlock(blockid);
                        if (slot != -1) {
                           this.mc.thePlayer.inventory.currentItem = slot;
                        }

                        if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().itemID == blockid) {
                           values = this.getDir(newpos);
                           if (values[1] != 0) {
                              this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3]);
                           }
                        }
                     }
                  }
               }
            }
         }

         this.lastLayersVal = this.getSet("Layers").getValBoolean();
         this.lastYlevelVal = this.getSet("Ylevel").getValDouble();
      }

   }

   public void onRender() {
      if (this.isEnabled() && !Schem.schematic.isEmpty()) {
         GL11.glPushMatrix();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         if (this.needsExpansionQuad) {
            this.bufferSizeQuad *= 2;
            this.cBufferQuad = BufferUtils.createFloatBuffer(this.bufferSizeQuad * 4);
            this.vBufferQuad = BufferUtils.createFloatBuffer(this.bufferSizeQuad * 3);
            this.needsExpansionQuad = false;
         }

         if (this.needsExpansionLine) {
            this.bufferSizeLine *= 2;
            this.cBufferLine = BufferUtils.createFloatBuffer(this.bufferSizeLine * 4);
            this.vBufferLine = BufferUtils.createFloatBuffer(this.bufferSizeLine * 3);
            this.needsExpansionLine = false;
         }

         int minX = 0;
         int maxX = 0;
         int minY = 0;
         int maxY = 0;
         int minZ = 0;
         int maxZ = 0;
         if (Schem.schem != null) {
            maxX = Schem.schem.width();
            maxY = Schem.schem.height();
            maxZ = Schem.schem.length();
            if (this.getSet("Layers").getValBoolean() && (int)this.getSet("Ylevel").getValDouble() >= 0) {
               minY = (int)this.getSet("Ylevel").getValDouble();
               maxY = (int)this.getSet("Ylevel").getValDouble() + 1;
            }
         }

         if (needsUpdate) {
            this.objectCountQuad = 0;
            this.objectCountLine = 0;
            this.cBufferQuad.clear();
            this.vBufferQuad.clear();
            this.cBufferLine.clear();
            this.vBufferLine.clear();
            GL11.glNewList(this.glBlockList, 4864);
            if (Schem.schem != null) {
               this.renderBlocks(minX, minY, minZ, maxX, maxY, maxZ);
            }

            GL11.glEndList();
            needsUpdate = false;
         }

         GL11.glTranslatef(-this.getTranslationX(), -this.getTranslationY(), -this.getTranslationZ());
         GL11.glCallList(this.glBlockList);
         if (this.objectCountQuad > 0 || this.objectCountLine > 0) {
            this.cBufferQuad.flip();
            this.vBufferQuad.flip();
            this.cBufferLine.flip();
            this.vBufferLine.flip();
            GL11.glDisable(3553);
            GL11.glLineWidth(1.5F);
            GL11.glEnableClientState(32884);
            GL11.glEnableClientState(32886);
            if (this.objectCountQuad > 0) {
               GL11.glColorPointer(4, 0, this.cBufferQuad);
               GL11.glVertexPointer(3, 0, this.vBufferQuad);
               GL11.glDrawArrays(7, 0, this.objectCountQuad);
            }

            if (this.objectCountLine > 0) {
               GL11.glColorPointer(4, 0, this.cBufferLine);
               GL11.glVertexPointer(3, 0, this.vBufferLine);
               GL11.glDrawArrays(1, 0, this.objectCountLine);
            }

            GL11.glDisableClientState(32886);
            GL11.glDisableClientState(32884);
            GL11.glEnable(3553);
         }

         GL11.glTranslatef(this.getTranslationX(), this.getTranslationY(), this.getTranslationZ());
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

   }

   public int[] getDir(Vec3D blockpos) {
      int[] values;
      if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord - 1, (int)blockpos.zCoord + 0, 1};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord + 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 4};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord - 1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord - 1, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 0, 5};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord + 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord + 1, 2};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord - 1)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord + 0, (int)blockpos.zCoord - 1, 3};
         return values;
      } else if (!this.mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
         values = new int[]{(int)blockpos.xCoord + 0, (int)blockpos.yCoord - 1, (int)blockpos.zCoord, 0};
         return values;
      } else {
         values = new int[4];
         return values;
      }
   }

   public int[] offsets() {
      int[] offsets = new int[2];
      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         offsets[0] = -1;
         offsets[1] = -1;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         offsets[0] = 0;
         offsets[1] = 0;
      }

      if (this.mc.thePlayer.posX > 0.0D && this.mc.thePlayer.posZ < 0.0D) {
         offsets[0] = 0;
         offsets[1] = -1;
      }

      if (this.mc.thePlayer.posX < 0.0D && this.mc.thePlayer.posZ > 0.0D) {
         offsets[0] = -1;
         offsets[1] = 0;
      }

      return offsets;
   }

   private void renderBlocks(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      IBlockAccess mcWorld = this.mc.theWorld;
      SchematicWorld world = Schem.schem;
      RenderBlocks renderBlocks = Schem.rb;
      List<Vector4i> invalidBlockId = new ArrayList();
      List<Vector4i> invalidBlockMetadata = new ArrayList();
      List<Vector4i> todoBlocks = new ArrayList();
      int blockId = false;
      int mcBlockId = false;
      int sides = false;
      Block block = null;
      boolean ambientOcclusion = this.mc.gameSettings.ambientOcclusion;
      this.mc.gameSettings.ambientOcclusion = false;
      Tessellator.instance.startDrawingQuads();

      for(int x = minX; x < maxX; ++x) {
         for(int y = minY; y < maxY; ++y) {
            for(int z = minZ; z < maxZ; ++z) {
               try {
                  int blockId = world.getBlockId(x, y, z);
                  block = Block.blocksList[blockId];
                  int[] offsets = this.offsets();
                  int mcBlockId = mcWorld.getBlockId(x + offsets[0] + Schem.moveX, y + Schem.moveY, z + offsets[1] + Schem.moveZ);
                  int sides = 0;
                  if (block != null) {
                     if (block.shouldSideBeRendered(world, x, y - 1, z, 0)) {
                        sides |= 1;
                     }

                     if (block.shouldSideBeRendered(world, x, y + 1, z, 1)) {
                        sides |= 2;
                     }

                     if (block.shouldSideBeRendered(world, x, y, z - 1, 2)) {
                        sides |= 4;
                     }

                     if (block.shouldSideBeRendered(world, x, y, z + 1, 3)) {
                        sides |= 8;
                     }

                     if (block.shouldSideBeRendered(world, x - 1, y, z, 4)) {
                        sides |= 16;
                     }

                     if (block.shouldSideBeRendered(world, x + 1, y, z, 5)) {
                        sides |= 32;
                     }
                  }

                  if (mcBlockId != 0) {
                     if (blockId != mcBlockId) {
                        invalidBlockId.add(new Vector4i(x, y, z, sides));
                     } else if (world.getBlockMetadata(x, y, z) != mcWorld.getBlockMetadata(x + offsets[0] + Schem.moveX, y + Schem.moveY, z + offsets[1] + Schem.moveZ)) {
                        invalidBlockMetadata.add(new Vector4i(x, y, z, sides));
                     }
                  } else if (mcBlockId == 0 && blockId > 0 && blockId < 4096) {
                     todoBlocks.add(new Vector4i(x + Schem.playerX, y, z + Schem.playerZ, sides));
                     if (block != null) {
                        renderBlocks.renderBlockByRenderType(block, x + Schem.playerX, y, z + Schem.playerZ);
                     }
                  }
               } catch (Exception var24) {
                  var24.printStackTrace();
               }
            }
         }
      }

      Tessellator.instance.draw();
      this.mc.gameSettings.ambientOcclusion = ambientOcclusion;
      this.drawCuboidLine(Vector3i.ZERO, new Vector3i(world.width(), world.height(), world.length()), 63, 0.75F, 0.0F, 0.75F, 0.25F);
      Iterator var23 = invalidBlockId.iterator();

      Vector4i todoBlock;
      Vector3i tmp;
      while(var23.hasNext()) {
         todoBlock = (Vector4i)var23.next();
         tmp = new Vector3i(todoBlock.x, todoBlock.y, todoBlock.z);
         this.drawCuboidQuad(tmp, tmp.clone().add(1), todoBlock.w, 1.0F, 0.0F, 0.0F, 0.25F);
         this.drawCuboidLine(tmp, tmp.clone().add(1), todoBlock.w, 1.0F, 0.0F, 0.0F, 0.25F);
      }

      var23 = invalidBlockMetadata.iterator();

      while(var23.hasNext()) {
         todoBlock = (Vector4i)var23.next();
         tmp = new Vector3i(todoBlock.x, todoBlock.y, todoBlock.z);
         this.drawCuboidQuad(tmp, tmp.clone().add(1), todoBlock.w, 0.75F, 0.35F, 0.0F, 0.45F);
         this.drawCuboidLine(tmp, tmp.clone().add(1), todoBlock.w, 0.75F, 0.35F, 0.0F, 0.45F);
      }

      var23 = todoBlocks.iterator();

      while(var23.hasNext()) {
         todoBlock = (Vector4i)var23.next();
         tmp = new Vector3i(todoBlock.x, todoBlock.y, todoBlock.z);
         this.drawCuboidQuad(tmp, tmp.clone().add(1), todoBlock.w, 0.0F, 0.75F, 1.0F, 0.25F);
         this.drawCuboidLine(tmp, tmp.clone().add(1), todoBlock.w, 0.0F, 0.75F, 1.0F, 0.25F);
      }

   }

   private void drawCuboidQuad(Vector3i a, Vector3i b, int sides, float red, float green, float blue, float alpha) {
      Vector3f zero = (new Vector3f((float)a.x, (float)a.y, (float)a.z)).sub(this.blockDelta);
      Vector3f size = (new Vector3f((float)b.x, (float)b.y, (float)b.z)).add(this.blockDelta);
      if (this.objectCountQuad + 24 >= this.bufferSizeQuad) {
         this.needsExpansionQuad = true;
      } else {
         int total = 0;
         if ((sides & 16) != 0) {
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            total += 4;
         }

         if ((sides & 32) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            total += 4;
         }

         if ((sides & 4) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            total += 4;
         }

         if ((sides & 8) != 0) {
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            total += 4;
         }

         if ((sides & 1) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            total += 4;
         }

         if ((sides & 2) != 0) {
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            total += 4;
         }

         for(int i = 0; i < total; ++i) {
            this.cBufferQuad.put(red).put(green).put(blue).put(alpha);
         }

         this.objectCountQuad += total;
      }
   }

   private void drawCuboidLine(Vector3i a, Vector3i b, int sides, float red, float green, float blue, float alpha) {
      Vector3f zero = (new Vector3f((float)a.x, (float)a.y, (float)a.z)).sub(this.blockDelta);
      Vector3f size = (new Vector3f((float)b.x, (float)b.y, (float)b.z)).add(this.blockDelta);
      if (this.objectCountLine + 24 >= this.bufferSizeLine) {
         this.needsExpansionLine = true;
      } else {
         int total = 0;
         if ((sides & 17) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            total += 2;
         }

         if ((sides & 18) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            total += 2;
         }

         if ((sides & 33) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            total += 2;
         }

         if ((sides & 34) != 0) {
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
         }

         if ((sides & 5) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            total += 2;
         }

         if ((sides & 6) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            total += 2;
         }

         if ((sides & 9) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            total += 2;
         }

         if ((sides & 10) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
         }

         if ((sides & 20) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            total += 2;
         }

         if ((sides & 36) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            total += 2;
         }

         if ((sides & 24) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            total += 2;
         }

         if ((sides & 40) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
         }

         for(int i = 0; i < total; ++i) {
            this.cBufferLine.put(red).put(green).put(blue).put(alpha);
         }

         this.objectCountLine += total;
      }
   }

   public float getTranslationX() {
      return (float)(this.mc.thePlayer.posX - (double)Schem.moveX);
   }

   public float getTranslationY() {
      return (float)(this.mc.thePlayer.posY - (double)Schem.moveY);
   }

   public float getTranslationZ() {
      return (float)(this.mc.thePlayer.posZ - (double)Schem.moveZ);
   }
}
