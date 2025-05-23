package net.minecraft.src;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {
   public static NBTTagCompound loadGzippedCompoundFromOutputStream(InputStream var0) throws IOException {
      DataInputStream var1 = new DataInputStream(new GZIPInputStream(var0));

      NBTTagCompound var2;
      try {
         var2 = read((DataInput)var1);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static void writeGzippedCompoundToOutputStream(NBTTagCompound var0, OutputStream var1) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

      try {
         write(var0, var2);
      } finally {
         var2.close();
      }

   }

   public static NBTTagCompound read(DataInput var0) throws IOException {
      NBTBase var1 = NBTBase.readTag(var0);
      if (var1 instanceof NBTTagCompound) {
         return (NBTTagCompound)var1;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(NBTTagCompound var0, DataOutput var1) throws IOException {
      NBTBase.writeTag(var0, var1);
   }

   public static NBTTagCompound readUncompressed(byte[] par0ArrayOfByte) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(par0ArrayOfByte)));

      NBTTagCompound var2;
      try {
         var2 = read((DataInput)var1);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static byte[] writeUncompressed(NBTTagCompound par0NBTTagCompound) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      DataOutputStream var2 = new DataOutputStream(var1);

      try {
         write(par0NBTTagCompound, var2);
      } finally {
         var2.close();
      }

      return var1.toByteArray();
   }

   public static void writeCompressed(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));

      try {
         write(par0NBTTagCompound, var2);
      } finally {
         var2.close();
      }

   }

   public static NBTTagCompound read(File par0File) throws IOException {
      if (!par0File.exists()) {
         return null;
      } else {
         DataInputStream var1 = new DataInputStream(new FileInputStream(par0File));

         NBTTagCompound var2;
         try {
            var2 = read((DataInput)var1);
         } finally {
            var1.close();
         }

         return var2;
      }
   }

   public static NBTTagCompound readCompressed(InputStream par0InputStream) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));

      NBTTagCompound var2;
      try {
         var2 = read((DataInput)var1);
      } finally {
         var1.close();
      }

      return var2;
   }
}
