package net.minecraft.src.MEDMEX.Commands.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.Block;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Vec3D;
import net.minecraft.src.MEDMEX.Client;
import net.minecraft.src.MEDMEX.Commands.Command;
import net.minecraft.src.MEDMEX.Modules.Client.$nowmatica;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.$nowmatic;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.$nowmaticUtils;
import net.minecraft.src.MEDMEX.Utils.$nowmatica.SchematicWorld;

public class Schem extends Command {
   public static NBTTagCompound ClientConfig = null;
   public static int playerX;
   public static int playerY;
   public static int playerZ;
   public static short width;
   public static short length;
   public static short height;
   public static int moveX = 0;
   public static int moveY = 0;
   public static int moveZ = 0;
   public static RenderBlocks renderBlocksInstance = new RenderBlocks();
   public static CopyOnWriteArrayList<$nowmatic> schematic = new CopyOnWriteArrayList();
   int rotateIndex = 1;
   String loaded;
   public static SchematicWorld schem = null;
   public static RenderBlocks rb = null;

   public Schem() {
      super("Schem", "$nowmatica controller command", "<schem> <load/layer/move/blocks/rotate> <filename/up or down/xyz/layer or total/ />", "schem");
   }

   public void onCommand(String[] args, String command) {
      try {
         File file;
         NBTTagCompound tagCompound;
         byte[] localBlocks;
         byte[] localMetadata;
         Vec3D pos;
         int x;
         int y;
         int z;
         int index;
         int blockID;
         int meta;
         Block block;
         if (args[0].equalsIgnoreCase("rotate") && this.loaded != null) {
            schematic.clear();
            file = new File("$nowmatics", this.loaded + ".schematic");
            tagCompound = $nowmaticUtils.readTagCompoundFromFile(file);
            localBlocks = tagCompound.getByteArray("Blocks");
            localMetadata = tagCompound.getByteArray("Data");
            width = tagCompound.getShort("Width");
            length = tagCompound.getShort("Length");
            height = tagCompound.getShort("Height");
            pos = null;
            x = 0;

            while(true) {
               if (x >= width) {
                  ++this.rotateIndex;
                  $nowmatica.needsUpdate = true;
                  break;
               }

               for(y = 0; y < height; ++y) {
                  for(z = 0; z < length; ++z) {
                     index = x + (y * length + z) * width;
                     blockID = localBlocks[index] & 255;
                     meta = localMetadata[index] & 255;
                     block = Block.blocksList[blockID];
                     if (this.rotateIndex == 4) {
                        this.rotateIndex = 0;
                     }

                     if (this.rotateIndex == 0) {
                        pos = new Vec3D(this.mc.thePlayer.posX + (double)x, (double)y, this.mc.thePlayer.posZ + (double)z);
                     }

                     if (this.rotateIndex == 1) {
                        pos = new Vec3D(this.mc.thePlayer.posX + (double)z, (double)y, this.mc.thePlayer.posZ + (double)x);
                     }

                     if (this.rotateIndex == 2) {
                        pos = new Vec3D(this.mc.thePlayer.posX - (double)x, (double)y, this.mc.thePlayer.posZ - (double)z);
                     }

                     if (this.rotateIndex == 3) {
                        pos = new Vec3D(this.mc.thePlayer.posX - (double)z, (double)y, this.mc.thePlayer.posZ - (double)x);
                     }

                     try {
                        if (block != null) {
                           schematic.add(new $nowmatic(pos, block));
                        }
                     } catch (Exception var17) {
                     }
                  }
               }

               ++x;
            }
         }

         int yLevel;
         int i;
         int i;
         if (args[0].equalsIgnoreCase("blocks")) {
            if (args[1].equalsIgnoreCase("layer")) {
               yLevel = (int)$nowmatica.instance.getSet("yLevel").getValDouble();
               HashMap<String, Integer> blocks = new HashMap();

               for(i = 0; i < schematic.size(); ++i) {
                  $nowmatic sc = ($nowmatic)schematic.get(i);
                  if (sc.getPos().yCoord == (double)yLevel) {
                     String block = sc.getBlock().getBlockName();
                     if (!blocks.containsKey(block)) {
                        blocks.put(block, 1);
                     } else {
                        blocks.put(block, (Integer)blocks.get(block) + 1);
                     }
                  }
               }

               Client.addChatMessage("Blocks in layer: " + blocks);
            }

            if (args[1].equalsIgnoreCase("total")) {
               HashMap<String, Integer> blocks = new HashMap();

               for(i = 0; i < schematic.size(); ++i) {
                  $nowmatic sc = ($nowmatic)schematic.get(i);
                  String block = sc.getBlock().getBlockName();
                  if (!blocks.containsKey(block)) {
                     blocks.put(block, 1);
                  } else {
                     blocks.put(block, (Integer)blocks.get(block) + 1);
                  }
               }

               Client.addChatMessage("Blocks in layer: " + blocks);
            }
         }

         if (args[0].equalsIgnoreCase("move")) {
            yLevel = Integer.valueOf(args[1]);
            i = Integer.valueOf(args[2]);
            i = Integer.valueOf(args[3]);
            moveX += yLevel;
            moveY += i;
            moveZ += i;
         }

         if (args[0].equalsIgnoreCase("layer")) {
            if (args[1].equalsIgnoreCase("up")) {
               $nowmatica.instance.getSet("Ylevel").setValDouble($nowmatica.instance.getSet("Ylevel").getValDouble() + 1.0D);
               Client.addChatMessage("Moved layer up 1");
            }

            if (args[1].equalsIgnoreCase("down")) {
               $nowmatica.instance.getSet("Ylevel").setValDouble($nowmatica.instance.getSet("Ylevel").getValDouble() - 1.0D);
               Client.addChatMessage("Moved layer down 1");
            }

            $nowmatica.needsUpdate = true;
         }

         if (args[0].equalsIgnoreCase("load")) {
            schematic.clear();
            file = new File("$nowmatics", args[1] + ".schematic");
            this.loaded = args[1];
            this.rotateIndex = 1;
            tagCompound = $nowmaticUtils.readTagCompoundFromFile(file);
            schem = new SchematicWorld();
            schem.readFromNBT(tagCompound);
            rb = new RenderBlocks(schem);
            playerX = (int)this.mc.thePlayer.posX;
            playerY = (int)this.mc.thePlayer.posY;
            playerZ = (int)this.mc.thePlayer.posZ;
            localBlocks = tagCompound.getByteArray("Blocks");
            localMetadata = tagCompound.getByteArray("Data");
            width = tagCompound.getShort("Width");
            length = tagCompound.getShort("Length");
            height = tagCompound.getShort("Height");

            for(x = 0; x < width; ++x) {
               for(y = 0; y < height; ++y) {
                  for(z = 0; z < length; ++z) {
                     index = x + (y * length + z) * width;
                     blockID = localBlocks[index] & 255;
                     meta = localMetadata[index] & 255;
                     block = Block.blocksList[blockID];
                     pos = new Vec3D(this.mc.thePlayer.posX + (double)x, (double)y, this.mc.thePlayer.posZ + (double)z);

                     try {
                        if (block != null) {
                           schematic.add(new $nowmatic(pos, block));
                        }
                     } catch (Exception var16) {
                     }
                  }
               }
            }
         }
      } catch (IOException var18) {
      }

   }
}
