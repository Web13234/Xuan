package com.unnamedtemp.xuan;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
//@Mod.EventBusSubscriber(modid = Xuan.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static final ModConfigSpec SPEC = BUILDER.build();

    //@SubscribeEvent
    //static void onLoad(final ModConfigEvent event)
    //{
    //
    //}
}
