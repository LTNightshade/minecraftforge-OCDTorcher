package de.madone.ocdtorcher.item;

import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name, Properties p) {
        super(p);
        this.name = name;
        this.setRegistryName(name);
    }

}
