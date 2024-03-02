package com.unnamedtemp.xuan.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

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
}