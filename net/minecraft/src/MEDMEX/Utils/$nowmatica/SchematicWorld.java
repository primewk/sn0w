package net.minecraft.src.MEDMEX.Utils.$nowmatica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class SchematicWorld extends World {
   private int[][][] blocks;
   private int[][][] metadata;
   private List<TileEntity> tileEntities;
   private short width;
   private short length;
   private short height;

   public SchematicWorld() {
      super(Minecraft.theMinecraft.theWorld, Minecraft.theMinecraft.theWorld.worldProvider);
      this.blocks = null;
      this.metadata = null;
      this.tileEntities = null;
      this.width = 0;
      this.length = 0;
      this.height = 0;
   }

   public SchematicWorld(int[][][] blocks, int[][][] metadata, List<TileEntity> tileEntities, short width, short height, short length) {
      this();
      this.blocks = blocks;
      this.metadata = metadata;
      this.tileEntities = tileEntities;
      this.width = width;
      this.length = length;
      this.height = height;
   }

   public void readFromNBT(NBTTagCompound tagCompound) {
      byte[] localBlocks = tagCompound.getByteArray("Blocks");
      byte[] localMetadata = tagCompound.getByteArray("Data");
      boolean extra = false;
      byte[] extraBlocks = null;
      if (extra = tagCompound.hasKey("Add")) {
         extraBlocks = tagCompound.getByteArray("Add");
      }

      this.width = tagCompound.getShort("Width");
      this.length = tagCompound.getShort("Length");
      this.height = tagCompound.getShort("Height");
      this.blocks = new int[this.width][this.height][this.length];
      this.metadata = new int[this.width][this.height][this.length];

      int y;
      for(int x = 0; x < this.width; ++x) {
         for(y = 0; y < this.height; ++y) {
            for(int z = 0; z < this.length; ++z) {
               this.blocks[x][y][z] = localBlocks[x + (y * this.length + z) * this.width] & 255;
               this.metadata[x][y][z] = localMetadata[x + (y * this.length + z) * this.width] & 255;
               if (extra) {
                  int[] var10000 = this.blocks[x][y];
                  var10000[z] |= (extraBlocks[x + (y * this.length + z) * this.width] & 255) << 8;
               }
            }
         }
      }

      this.tileEntities = new ArrayList();
      NBTTagList tileEntitiesList = tagCompound.getTagList("TileEntities");

      for(y = 0; y < tileEntitiesList.tagCount(); ++y) {
         TileEntity tileEntity = TileEntity.createAndLoadEntity((NBTTagCompound)tileEntitiesList.tagAt(y));
         tileEntity.worldObj = this;
         this.tileEntities.add(tileEntity);
      }

      this.refreshChests();
   }

   public void writeToNBT(NBTTagCompound tagCompound) {
      tagCompound.setShort("Width", this.width);
      tagCompound.setShort("Length", this.length);
      tagCompound.setShort("Height", this.height);
      byte[] localBlocks = new byte[this.width * this.length * this.height];
      byte[] localMetadata = new byte[this.width * this.length * this.height];
      byte[] extraBlocks = new byte[this.width * this.length * this.height];
      boolean extra = false;

      for(int x = 0; x < this.width; ++x) {
         for(int y = 0; y < this.height; ++y) {
            for(int z = 0; z < this.length; ++z) {
               localBlocks[x + (y * this.length + z) * this.width] = (byte)this.blocks[x][y][z];
               localMetadata[x + (y * this.length + z) * this.width] = (byte)this.metadata[x][y][z];
               if ((extraBlocks[x + (y * this.length + z) * this.width] = (byte)(this.blocks[x][y][z] >> 8)) > 0) {
                  extra = true;
               }
            }
         }
      }

      tagCompound.setString("Materials", "Classic");
      tagCompound.setByteArray("Blocks", localBlocks);
      tagCompound.setByteArray("Data", localMetadata);
      if (extra) {
         tagCompound.setByteArray("Add", extraBlocks);
      }

      tagCompound.setTag("Entities", new NBTTagList());
      NBTTagList tileEntitiesList = new NBTTagList();
      Iterator var12 = this.tileEntities.iterator();

      while(var12.hasNext()) {
         TileEntity tileEntity = (TileEntity)var12.next();
         NBTTagCompound tileEntityTagCompound = new NBTTagCompound();
         tileEntity.writeToNBT(tileEntityTagCompound);
         tileEntitiesList.setTag(tileEntityTagCompound);
      }

      tagCompound.setTag("TileEntities", tileEntitiesList);
   }

   public int getBlockId(int x, int y, int z) {
      return x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.height && z < this.length ? this.blocks[x][y][z] & 4095 : 0;
   }

   public TileEntity getBlockTileEntity(int x, int y, int z) {
      for(int i = 0; i < this.tileEntities.size(); ++i) {
         if (((TileEntity)this.tileEntities.get(i)).xCoord == x && ((TileEntity)this.tileEntities.get(i)).yCoord == y && ((TileEntity)this.tileEntities.get(i)).zCoord == z) {
            return (TileEntity)this.tileEntities.get(i);
         }
      }

      return null;
   }

   public float getBrightness(int var1, int var2, int var3, int var4) {
      return 1.0F;
   }

   public float getLightBrightness(int x, int y, int z) {
      return 1.0F;
   }

   public int getBlockMetadata(int x, int y, int z) {
      return x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.height && z < this.length ? this.metadata[x][y][z] : 0;
   }

   public Material getBlockMaterial(int x, int y, int z) {
      return this.getBlock(x, y, z) != null ? this.getBlock(x, y, z).blockMaterial : Material.air;
   }

   public boolean isBlockOpaqueCube(int x, int y, int z) {
      return this.getBlock(x, y, z) != null && this.getBlock(x, y, z).isOpaqueCube();
   }

   public boolean isBlockNormalCube(int x, int y, int z) {
      return this.getBlockMaterial(x, y, z).getIsTranslucent() && this.getBlock(x, y, z) != null && this.getBlock(x, y, z).renderAsNormalBlock();
   }

   public boolean isAirBlock(int x, int y, int z) {
      if (x >= 0 && y >= 0 && z >= 0 && x < this.width && y < this.height && z < this.length) {
         return this.blocks[x][y][z] == 0;
      } else {
         return true;
      }
   }

   protected IChunkProvider getChunkProvider() {
      return null;
   }

   public void setBlockMetadata(int x, int y, int z, byte metadata) {
      this.metadata[x][y][z] = metadata;
   }

   public Block getBlock(int x, int y, int z) {
      return Block.blocksList[this.getBlockId(x, y, z)];
   }

   public void setTileEntities(List<TileEntity> tileEntities) {
      this.tileEntities = tileEntities;
   }

   public List<TileEntity> getTileEntities() {
      return this.tileEntities;
   }

   public void refreshChests() {
   }

   private void checkForAdjacentChests(TileEntityChest tileEntityChest) {
   }

   public void flip() {
      for(int x = 0; x < this.width; ++x) {
         for(int y = 0; y < this.height; ++y) {
            for(int z = 0; z < (this.length + 1) / 2; ++z) {
               int tmp = this.blocks[x][y][z];
               this.blocks[x][y][z] = this.blocks[x][y][this.length - 1 - z];
               this.blocks[x][y][this.length - 1 - z] = tmp;
               if (z == this.length - 1 - z) {
                  this.metadata[x][y][z] = this.flipMetadataZ(this.metadata[x][y][z], this.blocks[x][y][z]);
               } else {
                  tmp = this.metadata[x][y][z];
                  this.metadata[x][y][z] = this.flipMetadataZ(this.metadata[x][y][this.length - 1 - z], this.blocks[x][y][z]);
                  this.metadata[x][y][this.length - 1 - z] = this.flipMetadataZ(tmp, this.blocks[x][y][this.length - 1 - z]);
               }
            }
         }
      }

      this.refreshChests();
   }

   private int flipMetadataZ(int blockMetadata, int blockId) {
      if (blockId != Block.torchWood.blockID && blockId != Block.torchRedstoneActive.blockID && blockId != Block.torchRedstoneIdle.blockID) {
         if (blockId == Block.rail.blockID) {
            switch(blockMetadata) {
            case 4:
               return 5;
            case 5:
               return 4;
            case 6:
               return 9;
            case 7:
               return 8;
            case 8:
               return 7;
            case 9:
               return 6;
            }
         } else if (blockId != Block.railDetector.blockID && blockId != Block.railPowered.blockID) {
            if (blockId != Block.stairCompactCobblestone.blockID && blockId != Block.stairCompactPlanks.blockID) {
               if (blockId == Block.lever.blockID) {
                  switch(blockMetadata & 7) {
                  case 3:
                     return (byte)(4 | blockMetadata & 8);
                  case 4:
                     return (byte)(3 | blockMetadata & 8);
                  }
               } else if (blockId != Block.doorWood.blockID && blockId != Block.doorSteel.blockID) {
                  if (blockId == Block.signPost.blockID) {
                     switch(blockMetadata) {
                     case 0:
                        return 8;
                     case 1:
                        return 7;
                     case 2:
                        return 6;
                     case 3:
                        return 5;
                     case 4:
                        return 4;
                     case 5:
                        return 3;
                     case 6:
                        return 2;
                     case 7:
                        return 1;
                     case 8:
                        return 0;
                     case 9:
                        return 15;
                     case 10:
                        return 14;
                     case 11:
                        return 13;
                     case 12:
                        return 12;
                     case 13:
                        return 11;
                     case 14:
                        return 10;
                     case 15:
                        return 9;
                     }
                  } else if (blockId != Block.ladder.blockID && blockId != Block.signWall.blockID && blockId != Block.stoneOvenActive.blockID && blockId != Block.stoneOvenIdle.blockID && blockId != Block.dispenser.blockID && blockId != Block.chest.blockID) {
                     if (blockId != Block.pumpkin.blockID && blockId != Block.pumpkinLantern.blockID) {
                        if (blockId == Block.blockBed.blockID) {
                           switch(blockMetadata & 3) {
                           case 0:
                              return (byte)(2 | blockMetadata & 12);
                           case 1:
                           default:
                              break;
                           case 2:
                              return (byte)(blockMetadata & 12);
                           }
                        } else if (blockId != Block.redstoneRepeaterActive.blockID && blockId != Block.redstoneRepeaterIdle.blockID) {
                           if (blockId == Block.trapdoor.blockID) {
                              switch(blockMetadata) {
                              case 0:
                                 return 1;
                              case 1:
                                 return 0;
                              }
                           } else if (blockId == Block.pistonBase.blockID || blockId == Block.pistonStickyBase.blockID || blockId == Block.pistonExtension.blockID) {
                              switch(blockMetadata & 7) {
                              case 2:
                                 return (byte)(3 | blockMetadata & 8);
                              case 3:
                                 return (byte)(2 | blockMetadata & 8);
                              }
                           }
                        } else {
                           switch(blockMetadata & 3) {
                           case 0:
                              return (byte)(2 | blockMetadata & 12);
                           case 1:
                           default:
                              break;
                           case 2:
                              return (byte)(blockMetadata & 12);
                           }
                        }
                     } else {
                        switch(blockMetadata) {
                        case 0:
                           return 2;
                        case 1:
                        default:
                           break;
                        case 2:
                           return 0;
                        }
                     }
                  } else {
                     switch(blockMetadata) {
                     case 2:
                        return 3;
                     case 3:
                        return 2;
                     }
                  }
               } else {
                  if ((blockMetadata & 8) == 8) {
                     return (byte)(blockMetadata ^ 1);
                  }

                  switch(blockMetadata & 3) {
                  case 1:
                     return (byte)(3 | blockMetadata & 12);
                  case 2:
                  default:
                     break;
                  case 3:
                     return (byte)(1 | blockMetadata & 12);
                  }
               }
            } else {
               switch(blockMetadata & 3) {
               case 2:
                  return (byte)(3 | blockMetadata & 4);
               case 3:
                  return (byte)(2 | blockMetadata & 4);
               }
            }
         } else {
            switch(blockMetadata & 7) {
            case 4:
               return (byte)(5 | blockMetadata & 8);
            case 5:
               return (byte)(4 | blockMetadata & 8);
            }
         }
      } else {
         switch(blockMetadata) {
         case 3:
            return 4;
         case 4:
            return 3;
         }
      }

      return blockMetadata;
   }

   public void rotate() {
      int[][][] localBlocks = new int[this.length][this.height][this.width];
      int[][][] localMetadata = new int[this.length][this.height][this.width];

      for(int x = 0; x < this.width; ++x) {
         for(int y = 0; y < this.height; ++y) {
            for(int z = 0; z < this.length; ++z) {
               localBlocks[z][y][x] = this.blocks[this.width - 1 - x][y][z];
               localMetadata[z][y][x] = this.rotateMetadata(this.metadata[this.width - 1 - x][y][z], this.blocks[this.width - 1 - x][y][z]);
            }
         }
      }

      this.blocks = localBlocks;
      this.metadata = localMetadata;
      this.refreshChests();
      short tmp = this.width;
      this.width = this.length;
      this.length = tmp;
   }

   private int rotateMetadata(int blockMetadata, int blockId) {
      if (blockId != Block.torchWood.blockID && blockId != Block.torchRedstoneActive.blockID && blockId != Block.torchRedstoneIdle.blockID) {
         if (blockId == Block.rail.blockID) {
            switch(blockMetadata) {
            case 0:
               return 1;
            case 1:
               return 0;
            case 2:
               return 4;
            case 3:
               return 5;
            case 4:
               return 3;
            case 5:
               return 2;
            case 6:
               return 9;
            case 7:
               return 6;
            case 8:
               return 7;
            case 9:
               return 8;
            }
         } else if (blockId != Block.railDetector.blockID && blockId != Block.railPowered.blockID) {
            if (blockId != Block.stairCompactCobblestone.blockID && blockId != Block.stairCompactPlanks.blockID) {
               if (blockId == Block.lever.blockID) {
                  switch(blockMetadata & 7) {
                  case 1:
                     return (byte)(4 | blockMetadata & 8);
                  case 2:
                     return (byte)(3 | blockMetadata & 8);
                  case 3:
                     return (byte)(1 | blockMetadata & 8);
                  case 4:
                     return (byte)(2 | blockMetadata & 8);
                  case 5:
                     return (byte)(6 | blockMetadata & 8);
                  case 6:
                     return (byte)(5 | blockMetadata & 8);
                  }
               } else if (blockId != Block.doorWood.blockID && blockId != Block.doorSteel.blockID) {
                  if (blockId == Block.signPost.blockID) {
                     return (byte)((blockMetadata + 12) % 16);
                  }

                  if (blockId != Block.ladder.blockID && blockId != Block.signWall.blockID && blockId != Block.stoneOvenActive.blockID && blockId != Block.stoneOvenIdle.blockID && blockId != Block.dispenser.blockID && blockId != Block.chest.blockID) {
                     if (blockId != Block.pumpkin.blockID && blockId != Block.pumpkinLantern.blockID) {
                        if (blockId == Block.blockBed.blockID) {
                           switch(blockMetadata & 3) {
                           case 0:
                              return (byte)(3 | blockMetadata & 12);
                           case 1:
                              return (byte)(blockMetadata & 12);
                           case 2:
                              return (byte)(1 | blockMetadata & 12);
                           case 3:
                              return (byte)(2 | blockMetadata & 12);
                           }
                        } else if (blockId != Block.redstoneRepeaterActive.blockID && blockId != Block.redstoneRepeaterIdle.blockID) {
                           if (blockId == Block.trapdoor.blockID) {
                              switch(blockMetadata) {
                              case 0:
                                 return 2;
                              case 1:
                                 return 3;
                              case 2:
                                 return 1;
                              case 3:
                                 return 0;
                              }
                           } else if (blockId == Block.pistonBase.blockID || blockId == Block.pistonStickyBase.blockID || blockId == Block.pistonExtension.blockID) {
                              switch(blockMetadata & 7) {
                              case 0:
                                 return (byte)(blockMetadata & 8);
                              case 1:
                                 return (byte)(1 | blockMetadata & 8);
                              case 2:
                                 return (byte)(4 | blockMetadata & 8);
                              case 3:
                                 return (byte)(5 | blockMetadata & 8);
                              case 4:
                                 return (byte)(3 | blockMetadata & 8);
                              case 5:
                                 return (byte)(2 | blockMetadata & 8);
                              }
                           }
                        } else {
                           switch(blockMetadata & 3) {
                           case 0:
                              return (byte)(3 | blockMetadata & 12);
                           case 1:
                              return (byte)(blockMetadata & 12);
                           case 2:
                              return (byte)(1 | blockMetadata & 12);
                           case 3:
                              return (byte)(2 | blockMetadata & 12);
                           }
                        }
                     } else {
                        switch(blockMetadata) {
                        case 0:
                           return 3;
                        case 1:
                           return 0;
                        case 2:
                           return 1;
                        case 3:
                           return 2;
                        }
                     }
                  } else {
                     switch(blockMetadata) {
                     case 2:
                        return 4;
                     case 3:
                        return 5;
                     case 4:
                        return 3;
                     case 5:
                        return 2;
                     }
                  }
               } else {
                  if ((blockMetadata & 8) == 8) {
                     return blockMetadata;
                  }

                  switch(blockMetadata & 3) {
                  case 0:
                     return (byte)(3 | blockMetadata & 12);
                  case 1:
                     return (byte)(blockMetadata & 12);
                  case 2:
                     return (byte)(1 | blockMetadata & 12);
                  case 3:
                     return (byte)(2 | blockMetadata & 12);
                  }
               }
            } else {
               switch(blockMetadata & 3) {
               case 0:
                  return (byte)(3 | blockMetadata & 4);
               case 1:
                  return (byte)(2 | blockMetadata & 4);
               case 2:
                  return (byte)(blockMetadata & 4);
               case 3:
                  return (byte)(1 | blockMetadata & 4);
               }
            }
         } else {
            switch(blockMetadata & 7) {
            case 0:
               return (byte)(1 | blockMetadata & 8);
            case 1:
               return (byte)(blockMetadata & 8);
            case 2:
               return (byte)(4 | blockMetadata & 8);
            case 3:
               return (byte)(5 | blockMetadata & 8);
            case 4:
               return (byte)(3 | blockMetadata & 8);
            case 5:
               return (byte)(2 | blockMetadata & 8);
            }
         }
      } else {
         switch(blockMetadata) {
         case 1:
            return 4;
         case 2:
            return 3;
         case 3:
            return 1;
         case 4:
            return 2;
         }
      }

      return blockMetadata;
   }

   public int width() {
      return this.width;
   }

   public int length() {
      return this.length;
   }

   public int height() {
      return this.height;
   }
}
