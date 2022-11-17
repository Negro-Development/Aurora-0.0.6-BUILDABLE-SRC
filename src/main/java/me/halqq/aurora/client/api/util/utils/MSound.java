// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

import javax.annotation.Nullable;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class MSound {
    public static final ISound sound;
    private static final String song = "msound";
    private static final ResourceLocation loc;

    static {
        loc = new ResourceLocation("sounds/msound.ogg");
        sound = new ISound(){
            private final int pitch = 1;
            private final int volume = 1;

            @Override
            public ResourceLocation getSoundLocation() {
                return loc;
            }

            @Nullable
            @Override
            public SoundEventAccessor createAccessor(SoundHandler soundHandler) {
                return new SoundEventAccessor(loc, "Nya");
            }

            @Override
            public Sound getSound() {
                return new Sound(MSound.song, 1.0f, 1.0f, 1, Sound.Type.SOUND_EVENT, false);
            }

            @Override
            public SoundCategory getCategory() {
                return SoundCategory.VOICE;
            }

            @Override
            public boolean canRepeat() {
                return true;
            }

            @Override
            public int getRepeatDelay() {
                return 2;
            }

            @Override
            public float getVolume() {
                return 1.0f;
            }

            @Override
            public float getPitch() {
                return 1.0f;
            }

            @Override
            public float getXPosF() {
                return 1.0f;
            }

            @Override
            public float getYPosF() {
                return 0.0f;
            }

            @Override
            public float getZPosF() {
                return 0.0f;
            }

            @Override
            public ISound.AttenuationType getAttenuationType() {
                return ISound.AttenuationType.LINEAR;
            }
        };
    }

    public MSound(String song, float v, float v1, int i, Sound.Type soundEvent) {
    }
}