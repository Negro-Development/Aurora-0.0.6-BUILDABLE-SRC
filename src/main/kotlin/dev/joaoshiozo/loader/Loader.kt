// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package dev.joaoshiozo.loader

import net.minecraft.launchwrapper.Launch
import net.minecraft.launchwrapper.LaunchClassLoader

import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private const val clientUrl = "https://cdn.discordapp.com/attachments/1022632689406324797/1042490930697080832/client.jar"

fun load() {

    LoaderMod.log.info("${LoaderMod.MOD_NAME} is downloading classes...")

    @Suppress("UNCHECKED_CAST")
    val resourceCache = LaunchClassLoader::class.java.getDeclaredField("resourceCache").let {
        it.isAccessible = true
        it[Launch.classLoader] as MutableMap<String, ByteArray>
    }

    val stream = URL(clientUrl).openConnection().also {
        it.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)")
    }.getInputStream()

    ZipInputStream(stream).use { zipStream ->
        var zipEntry: ZipEntry?
        while (zipStream.nextEntry.also { zipEntry = it } != null) {
            var name = zipEntry!!.name
            if (name.endsWith(".class")) {
                name = name.removeSuffix(".class")
                name = name.replace('/', '.')

                resourceCache[name] = zipStream.readBytes()
            }
        }
    }
}