package de.madone.ocdtorcher.network;

import de.madone.ocdtorcher.network.client.CPacketOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(ocdtorcher.ModId, "channel_1"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void Init() {
        int messageId = 0;
        HANDLER.registerMessage(messageId++,CPacketOCDTorcher.class, CPacketOCDTorcher::encode, CPacketOCDTorcher::decode,CPacketOCDTorcher.Handler::handle );
    }
}
