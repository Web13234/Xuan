package com.unnamedtemp.xuan.data;

import com.mojang.serialization.Codec;

public enum Element {
    Zenurik, Vazarin, Naramon, Madurai, Unairu;

    //编码器，与String互相转换
    public static final Codec<Element> CODEC = Codec.STRING.xmap(Element::valueOf, Enum::toString);
}
