package de.madone.ocdtorcher.network;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.network.client.CPacketOCDTorcher;
import de.madone.ocdtorcher.network.server.SPacketOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
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

    @SuppressWarnings("UnusedAssignment")
    public static void Init() {
        int messageId = 0;
        HANDLER.registerMessage(messageId++,CPacketOCDTorcher.class, CPacketOCDTorcher::encode, CPacketOCDTorcher::decode,CPacketOCDTorcher.Handler::handle );
        HANDLER.registerMessage(messageId++,SPacketOCDTorcher.class, SPacketOCDTorcher::encode, SPacketOCDTorcher::decode,SPacketOCDTorcher.Handler::handle );
    }

    public static void sendOCDTorcherData(boolean enabled, boolean PickupEnabled, BlockPos Origin, OCDTorcherPattern pattern, EntityPlayerMP player) {
        if (player.openContainer instanceof ContainerOCDTorcher) {
            HANDLER.sendTo(new SPacketOCDTorcher(new SPacketOCDTorcher.PacketTorcher(enabled, PickupEnabled, Origin, pattern)), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT );
        }
    }
}
