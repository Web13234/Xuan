package com.unnamedtemp.xuan.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public enum Element {
    Zenurik, Vazarin, Naramon, Madurai, Unairu;
    public static final Codec<Element> CODEC = Codec.STRING.xmap(Element::valueOf, Enum::toString);
}
