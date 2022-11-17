// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package dev.joaoshiozo.loader

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins

@IFMLLoadingPlugin.Name("LoaderCoreMod")
@IFMLLoadingPlugin.MCVersion(value = "1.12.2")
class LoaderCoreMod: IFMLLoadingPlugin {
    init {
        load()
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.aurora.json")
        MixinEnvironment.getDefaultEnvironment().side = MixinEnvironment.Side.CLIENT
        MixinEnvironment.getDefaultEnvironment().obfuscationContext = "searge"
        LoaderMod.log.info("Loader coremod initialized!")
        LoaderMod.log.info("ObfuscationContext: " + MixinEnvironment.getDefaultEnvironment().obfuscationContext)
    }

    override fun getModContainerClass(): String? = null

    override fun getASMTransformerClass(): Array<String> = emptyArray()

    override fun getSetupClass(): String? = null

    override fun injectData(data: MutableMap<String, Any>?) {}

    override fun getAccessTransformerClass(): String? = null
}