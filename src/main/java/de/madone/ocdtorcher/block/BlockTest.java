package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.item.ModItems;
import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockTest extends Block {

    protected String name;

    public BlockTest() {
        super(
                Block.Properties.create(Material.IRON)
                        .hardnessAndResistance(15.0f, 15.0f)
                        .lightValue(1)
                        .sound(SoundType.METAL)
        );
        this.name = "test";
        this.setRegistryName(name);
        CreateItemBlock();
    }

    private Item.Properties getDefaultProperties() {
        return new Item.Properties()
                .defaultMaxDamage(0)
                .group(ocdtorcher.ITEM_GROUP_OCDTORCHER)
                .maxStackSize(64)
                .rarity(EnumRarity.COMMON)
                .setNoRepair()
                ;
    }

    private void CreateItemBlock() {
        ModItems.ITEMS.add(new ItemBlock(this, this.getDefaultProperties()).setRegistryName(this.getRegistryName().getPath()));
    }

}
