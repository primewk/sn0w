package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomeGenBase {
   public static final BiomeGenBase rainforest = (new BiomeGenRainforest()).setColor(588342).setBiomeName("Rainforest").setFillerBlockMetadata(2094168);
   public static final BiomeGenBase swampland = (new BiomeGenSwamp()).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376);
   public static final BiomeGenBase seasonalForest = (new BiomeGenBase()).setColor(10215459).setBiomeName("Seasonal Forest");
   public static final BiomeGenBase forest = (new BiomeGenForest()).setColor(353825).setBiomeName("Forest").setFillerBlockMetadata(5159473);
   public static final BiomeGenBase savanna = (new BiomeGenDesert()).setColor(14278691).setBiomeName("Savanna");
   public static final BiomeGenBase shrubland = (new BiomeGenBase()).setColor(10595616).setBiomeName("Shrubland");
   public static final BiomeGenBase taiga = (new BiomeGenTaiga()).setColor(3060051).setBiomeName("Taiga").setEnableSnow().setFillerBlockMetadata(8107825);
   public static final BiomeGenBase desert = (new BiomeGenDesert()).setColor(16421912).setBiomeName("Desert").setDisableRain();
   public static final BiomeGenBase plains = (new BiomeGenDesert()).setColor(16767248).setBiomeName("Plains");
   public static final BiomeGenBase iceDesert = (new BiomeGenDesert()).setColor(16772499).setBiomeName("Ice Desert").setEnableSnow().setDisableRain().setFillerBlockMetadata(12899129);
   public static final BiomeGenBase tundra = (new BiomeGenBase()).setColor(5762041).setBiomeName("Tundra").setEnableSnow().setFillerBlockMetadata(12899129);
   public static final BiomeGenBase hell = (new BiomeGenHell()).setColor(16711680).setBiomeName("Hell").setDisableRain();
   public static final BiomeGenBase sky = (new BiomeGenSky()).setColor(8421631).setBiomeName("Sky").setDisableRain();
   public String biomeName;
   public int color;
   public byte topBlock;
   public byte fillerBlock;
   public int fillerBlockMetadata;
   protected List spawnableMonsterList;
   protected List spawnableCreatureList;
   protected List spawnableWaterCreatureList;
   private boolean enableSnow;
   private boolean enableRain;
   private static BiomeGenBase[] biomeLookupTable = new BiomeGenBase[4096];

   static {
      generateBiomeLookup();
   }

   protected BiomeGenBase() {
      this.topBlock = (byte)Block.grass.blockID;
      this.fillerBlock = (byte)Block.dirt.blockID;
      this.fillerBlockMetadata = 5169201;
      this.spawnableMonsterList = new ArrayList();
      this.spawnableCreatureList = new ArrayList();
      this.spawnableWaterCreatureList = new ArrayList();
      this.enableRain = true;
      this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10));
      this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10));
      this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10));
      this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10));
      this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12));
      this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10));
      this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10));
      this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8));
      this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10));
   }

   private BiomeGenBase setDisableRain() {
      this.enableRain = false;
      return this;
   }

   public static void generateBiomeLookup() {
      for(int var0 = 0; var0 < 64; ++var0) {
         for(int var1 = 0; var1 < 64; ++var1) {
            biomeLookupTable[var0 + var1 * 64] = getBiome((float)var0 / 63.0F, (float)var1 / 63.0F);
         }
      }

      desert.topBlock = desert.fillerBlock = (byte)Block.sand.blockID;
      iceDesert.topBlock = iceDesert.fillerBlock = (byte)Block.sand.blockID;
   }

   public WorldGenerator getRandomWorldGenForTrees(Random var1) {
      return (WorldGenerator)(var1.nextInt(10) == 0 ? new WorldGenBigTree() : new WorldGenTrees());
   }

   protected BiomeGenBase setEnableSnow() {
      this.enableSnow = true;
      return this;
   }

   protected BiomeGenBase setBiomeName(String var1) {
      this.biomeName = var1;
      return this;
   }

   protected BiomeGenBase setFillerBlockMetadata(int var1) {
      this.fillerBlockMetadata = var1;
      return this;
   }

   protected BiomeGenBase setColor(int var1) {
      this.color = var1;
      return this;
   }

   public static BiomeGenBase getBiomeFromLookup(double var0, double var2) {
      int var4 = (int)(var0 * 63.0D);
      int var5 = (int)(var2 * 63.0D);
      return biomeLookupTable[var4 + var5 * 64];
   }

   public static BiomeGenBase getBiome(float var0, float var1) {
      var1 *= var0;
      if (var0 < 0.1F) {
         return tundra;
      } else if (var1 < 0.2F) {
         if (var0 < 0.5F) {
            return tundra;
         } else {
            return var0 < 0.95F ? savanna : desert;
         }
      } else if (var1 > 0.5F && var0 < 0.7F) {
         return swampland;
      } else if (var0 < 0.5F) {
         return taiga;
      } else if (var0 < 0.97F) {
         return var1 < 0.35F ? shrubland : forest;
      } else if (var1 < 0.45F) {
         return plains;
      } else {
         return var1 < 0.9F ? seasonalForest : rainforest;
      }
   }

   public int getSkyColorByTemp(float var1) {
      var1 /= 3.0F;
      if (var1 < -1.0F) {
         var1 = -1.0F;
      }

      if (var1 > 1.0F) {
         var1 = 1.0F;
      }

      return Color.getHSBColor(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F).getRGB();
   }

   public List getSpawnableList(EnumCreatureType var1) {
      if (var1 == EnumCreatureType.monster) {
         return this.spawnableMonsterList;
      } else if (var1 == EnumCreatureType.creature) {
         return this.spawnableCreatureList;
      } else {
         return var1 == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : null;
      }
   }

   public boolean getEnableSnow() {
      return this.enableSnow;
   }

   public boolean canSpawnLightningBolt() {
      return this.enableSnow ? false : this.enableRain;
   }
}
