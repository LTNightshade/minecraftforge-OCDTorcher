package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.item.ModItems;
import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockOreCopper extends BlockOre {

    protected String name;

    public BlockOreCopper() {
        super( Properties.create(
                Material.IRON,
                MaterialColor.BROWN
                )
                .hardnessAndResistance(5.0f,5.0f)
                .lightValue(0)
                .sound(SoundType.METAL)
        );
        this.name = "block_ore_copper";
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
