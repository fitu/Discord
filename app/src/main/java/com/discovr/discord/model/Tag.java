package com.discovr.discord.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({Tag.NORMAL, Tag.DRINK, Tag.HARDCORE, Tag.DISCORD})
@Retention(RetentionPolicy.CLASS)
public @interface Tag {
    String NORMAL = "normal";
    String DRINK = "drink";
    String HARDCORE = "hardcore";
    String DISCORD = "discord";
}