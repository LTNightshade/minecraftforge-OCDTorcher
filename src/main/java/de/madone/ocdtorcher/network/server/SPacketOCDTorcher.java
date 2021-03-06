package de.madone.ocdtorcher.network.server;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class SPacketOCDTorcher {

    private static final Logger LOGGER = LogManager.getLogger();

    public static class PacketTorcher {
        boolean enabled;
        boolean pickUpEnabled;
        BlockPos origin;
        OCDTorcherPattern pattern;

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isPickUpEnabled() {
            return pickUpEnabled;
        }

        public BlockPos getOrigin() {
            return origin;
        }

        public OCDTorcherPattern getPattern() {
            return pattern;
        }

        public PacketTorcher() {
            this.origin = new BlockPos(0, 0, 0);
            this.pattern = new OCDTorcherPattern(12, 6, true);
        }

        public PacketTorcher(boolean enabled, boolean pickUpEnabled, BlockPos origin, OCDTorcherPattern pattern) {
            this.enabled = enabled;
            this.pickUpEnabled = pickUpEnabled;
            this.origin = origin;
            this.pattern = pattern;
        }
    }

    private final PacketTorcher data;

    public SPacketOCDTorcher(PacketTorcher data) {
        this.data = data;
    }

    public static void encode(SPacketOCDTorcher pkt, PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(pkt.data.enabled);
        packetBuffer.writeBoolean(pkt.data.pickUpEnabled);
        packetBuffer.writeBlockPos(pkt.data.origin);
        packetBuffer.writeInt(pkt.data.pattern.getHeight());
        packetBuffer.writeInt(pkt.data.pattern.getWidth());
        packetBuffer.writeBoolean(pkt.data.pattern.isAlternating());
    }

    public static SPacketOCDTorcher decode(PacketBuffer packetBuffer) {
        PacketTorcher data = new PacketTorcher();
        data.enabled = packetBuffer.readBoolean();
        data.pickUpEnabled = packetBuffer.readBoolean();
        data.origin = packetBuffer.readBlockPos();
        data.pattern.setHeight(packetBuffer.readInt());
        data.pattern.setWidth(packetBuffer.readInt());
        data.pattern.setAlternating(packetBuffer.readBoolean());
        return new SPacketOCDTorcher(data);
    }

    public static class Handler {
        public static void handle(final SPacketOCDTorcher pkt, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                LOGGER.info("Receiving OCD Torcher data from server.");
                EntityPlayerSP player = Minecraft.getInstance().player;
                Container c = player.openContainer;
                if (c instanceof ContainerOCDTorcher) {
                    ((ContainerOCDTorcher) c).ProcessMessage(pkt.data);
                }
                ctx.get().setPacketHandled(true);
            });
        }
    }
}
