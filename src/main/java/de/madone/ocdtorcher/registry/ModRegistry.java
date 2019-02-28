package de.madone.ocdtorcher.registry;

import de.madone.ocdtorcher.block.ModBlocks;
import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.capability.ModCapabilities;
import de.madone.ocdtorcher.config.ModConfig;
import de.madone.ocdtorcher.gui.ModGuiHandler;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.item.ModItems;
import de.madone.ocdtorcher.network.ModNetwork;
import de.madone.ocdtorcher.tile.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//region Eventlist
/*
in order of firing:

RegistryEvent.NewRegistry
RegistryEvent.Register<Block>
RegistryEvent.Register<Item>
RegistryEvent.Register<DimensionType>
RegistryEvent.Register<ModDimension>
RegistryEvent.Register<Biome>
RegistryEvent.Register<Enchantment>
RegistryEvent.Register<EntityEntry>
RegistryEvent.Register<Potion>
RegistryEvent.Register<PotionType>
RegistryEvent.Register<SoundEvent>
RegistryEvent.Register<TileEntityType>
RegistryEvent.Register<VillagerProfession>
Configs
FMLCommonSetupEvent
FMLDedicatedServerSetupEvent
FMLClientSetupEvent
InterModEnqueueEvent
FMLLoadCompleteEvent
InterModProcessEvent
FMLServerAboutToStartEvent
FMLServerStartingEvent
FMLServerStartedEvent

Special events:
FMLFingerprintViolationEvent
FMLModIdMappingEvent
*/
//endregion

public class ModRegistry {

    protected final Logger LOGGER = LogManager.getLogger();

    //region Constructor

    public ModRegistry() {

        // New registry Event (for adding own registries ? )
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onNewRegistry);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLCommonSetupEvent);

        // Register the setup method for Server
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLDedicatedServerSetupEvent);

        // Register the setup method for Client
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLClientSetupEvent);

        // Register the enqueueIMC method for modloading
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModEnqueueEvent);

        // Register the processIMC method for modloading
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModProcessEvent);

        // Mod loading completed
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLLoadCompleteEvent);

        // Server before start
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLServerAboutToStartEvent);

        // Server starting
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLServerStartingEvent);

        // Server after start
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLServerStartedEvent);

        // on Fingerprint Violations
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLFingerprintViolationEvent);

        // on remapping Mod-IDs
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFMLModIdMappingEvent);


        // Register Block
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::onBlocksRegistry);
        // Register Item
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::onItemsRegistry);
        // Register DimensionType
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(DimensionType.class, this::onDimensionTypesRegistry);
        // Register ModDimension
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ModDimension.class, this::onModDimensionsRegistry);
        // Register Biome
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, this::onBiomesRegistry);
        // Register Enchantment
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Enchantment.class, this::onEnchantmentsRegistry);
        // Register EntityType
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::onEntityTypeRegistry);
        // Register Potion
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Potion.class, this::onPotionRegistry);
        // Register PotionType
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(PotionType.class, this::onPotionTypeRegistry);
        // Register SoundEvent
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(SoundEvent.class, this::onSoundEventRegistry);
        // Register TileEntityType
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::onTileEntityTypeRegistry);
        // Register VillagerProfession
        // FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(VillagerRegistry.VillagerProfession.class, this::onVillagerProfessionRegistry);

        // load Configfile
        ModConfig.Init();

        // Init Guis
        ModGuiHandler.Init();

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    //endregion

    //region Events

    private void onNewRegistry(final RegistryEvent.NewRegistry event) {
        LOGGER.info("Registering Registries...");
    }

    private void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        LOGGER.info("Registering Blocks...");
        ModBlocks.register(event);
    }

    private void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        LOGGER.info("Registering Items...");
        ModItems.register(event);
    }

    private void onDimensionTypesRegistry(final RegistryEvent.Register<DimensionType> event) {
        LOGGER.info("Registering Dimensions...");
    }

    private void onModDimensionsRegistry(final RegistryEvent.Register<ModDimension> event) {
        LOGGER.info("Registering ModDimensions...");
    }

    private void onBiomesRegistry(final RegistryEvent.Register<Biome> event) {
        LOGGER.info("Registering Biomes...");
    }

    private void onEnchantmentsRegistry(final RegistryEvent.Register<Enchantment> event) {
        LOGGER.info("Registering Enchantments...");
    }

    private void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        LOGGER.info("Registering EntityTypes...");
    }

    private void onPotionRegistry(final RegistryEvent.Register<Potion> event) {
        LOGGER.info("Registering Potions...");
    }

    private void onPotionTypeRegistry(final RegistryEvent.Register<PotionType> event) {
        LOGGER.info("Registering PotionTypes...");
    }

    private void onSoundEventRegistry(final RegistryEvent.Register<SoundEvent> event) {
        LOGGER.info("Registering SoundEvents...");
    }

    private void onTileEntityTypeRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        LOGGER.info("Registering TileEntityTypes...");
        ModTileEntities.Init(event);
    }

    private void onVillagerProfessionRegistry(final RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        LOGGER.info("Registering VillagerProfessions...");
    }

    private void onFMLCommonSetupEvent(final FMLCommonSetupEvent event) {
        LOGGER.info("Starting FMLCommonSetupEvent");
        ModCapabilities.Init();
        DeferredWorkQueue.runLater(ModNetwork::Init);
    }

    private void onFMLDedicatedServerSetupEvent(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Starting FMLDedicatedServerSetupEvent");
    }

    private void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        LOGGER.info("Starting FMLClientSetupEvent");
    }

    private void onInterModEnqueueEvent(final InterModEnqueueEvent event) {
        LOGGER.info("Starting InterModEnqueueEvent");
        // some example code to dispatch IMC to another mod
    }

    private void onInterModProcessEvent(final InterModProcessEvent event) {
        LOGGER.info("Starting InterModProcessEvent");
        // some example code to receive and process InterModComms from other mods
    }

    private void onFMLLoadCompleteEvent(final FMLLoadCompleteEvent event) {
        LOGGER.info("Starting FMLLoadCompleteEvent");
    }

    private void onFMLServerAboutToStartEvent(final FMLServerAboutToStartEvent event) {
        LOGGER.info("Starting FMLServerAboutToStartEvent");
    }

    private void onFMLServerStartingEvent(final FMLServerStartingEvent event) {
        LOGGER.info("Starting FMLServerStartingEvent");
    }

    private void onFMLServerStartedEvent(final FMLServerStartedEvent event) {
        LOGGER.info("Starting FMLServerStartedEvent");
    }

    private void onFMLFingerprintViolationEvent(final FMLFingerprintViolationEvent event) {
        LOGGER.info("Starting FMLFingerprintViolationEvent");
    }

    private void onFMLModIdMappingEvent(final FMLModIdMappingEvent event) {
        LOGGER.info("Starting FMLModIdMappingEvent");
    }

    @SubscribeEvent
    public static void onAttachCapatilityonItemStacks(final AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ItemOCDTorcher) {
            event.addCapability(CapabilityOCDTorcher.RLOC, new CapabilityOCDTorcher.Provider());
        }
    }

    //endregion






}
