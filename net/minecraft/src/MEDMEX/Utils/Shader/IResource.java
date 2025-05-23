package net.minecraft.src.MEDMEX.Utils.Shader;

import java.io.InputStream;

public interface IResource {
   InputStream getInputStream();

   boolean hasMetadata();

   IMetadataSection getMetadata(String var1);
}
