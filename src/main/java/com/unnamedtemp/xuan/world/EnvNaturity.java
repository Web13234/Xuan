package com.unnamedtemp.xuan.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unnamedtemp.xuan.Xuan;
import com.unnamedtemp.xuan.data.Element;
import lombok.Getter;
import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.HashMap;
import java.util.Map;

import static com.unnamedtemp.xuan.Register.*;

@Getter
public class EnvNaturity {
    private static final Codec<Map<Element, Integer>> CONC_CODEC = Codec.unboundedMap(
            Element.CODEC.fieldOf("element").codec(),
            Codec.INT.fieldOf("value").codec()
    );
    public static final Codec<EnvNaturity> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CONC_CODEC.optionalFieldOf("conc", new HashMap<>()).orElse(new HashMap<>())
                            .forGetter(EnvNaturity::getConcMap),
                    Codec.doubleRange(0d, 1d).optionalFieldOf("polarity", .5d).orElse(.5d)
                            .forGetter(EnvNaturity::getPolarity)
            ).apply(instance, EnvNaturity::new)
    );

    public EnvNaturity(Map<Element, Integer> concMap, double polarity) {
        ConcMap = concMap;
        Polarity = polarity;
    }

    public EnvNaturity(Map<Element, Integer> concMap) {
        ConcMap = concMap;
    }

    public EnvNaturity() {
        this(new HashMap<>());
    }

    private final Map<Element, Integer> ConcMap;

    private double Polarity = .5d;

    public EnvNaturity setConc(Element element, int value) {
        ConcMap.put(element, value);
        return this;
    }

    public EnvNaturity setPolarity(double polarity) {
        if (polarity > 1d) polarity = 1d;
        if (polarity < 0d) polarity = 0d;
        Polarity = polarity;
        return this;
    }

    public int getConc(Element element) {
        return ConcMap.getOrDefault(element, 0);
    }

    @Mod.EventBusSubscriber(modid = Xuan.MODID)
    private static class Event {
        @SubscribeEvent
        static void EnvNaturityInit(ChunkEvent.Load event) {
            if (event.getChunk() instanceof LevelChunk chunk && !chunk.hasData(AttachmentTypes.ENV_NATURITY)) {
                var nat = new EnvNaturity();
                nat.setConc(Element.Zenurik, 100)
                        .setConc(Element.Vazarin, 100)
                        .setConc(Element.Naramon, 100)
                        .setConc(Element.Madurai, 100)
                        .setConc(Element.Unairu, 200);
                chunk.setData(AttachmentTypes.ENV_NATURITY, nat);
            }
        }
    }
}
