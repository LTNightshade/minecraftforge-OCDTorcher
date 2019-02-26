package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.nimox;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {

    protected String name;

    public BlockBase(String name, Properties properties) {
        super(properties);
        this.name = name;
        this.setRegistryName(name);
    }

    private Item.Properties getDefaultProperties() {
        return new Item.Properties()
                .defaultMaxDamage(0)
                .group(nimox.ITEM_GROUP_NIMOX)
                .maxStackSize(64)
                .rarity(EnumRarity.COMMON)
                .setNoRepair()
                ;
    }

    public Item getItemBlock() {
        return new ItemBlock(this, getDefaultProperties()).setRegistryName(this.getRegistryName().getPath());

    }

}
