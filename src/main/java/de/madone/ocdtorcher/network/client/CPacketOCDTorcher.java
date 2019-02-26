package de.madone.ocdtorcher.network.client;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.item.ModItems;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.function.Supplier;

public class CPacketOCDTorcher {

    protected static final Logger LOGGER = LogManager.getLogger();

    public static class PacketTorcher {
        ItemStack torcher;
        int level;
        boolean enabled;
        boolean pickUpEnabled;
        BlockPos origin;
        OCDTorcherPattern pattern;

        public ItemStack getTorcher() {
            return torcher;
        }

        public int getLevel() {
            return level;
        }

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
        }

        public PacketTorcher(ItemStack torcher, int level, boolean enabled, boolean pickUpEnabled, BlockPos origin, OCDTorcherPattern pattern) {
            this.torcher = torcher;
            this.level = level;
            this.enabled = enabled;
            this.pickUpEnabled = pickUpEnabled;
            this.origin = origin;
            this.pattern = pattern;
        }
    }
    private final PacketTorcher data;

    public CPacketOCDTorcher(PacketTorcher data) {
        this.data = data;
    }

    public static void encode(CPacketOCDTorcher pkt, PacketBuffer packetBuffer) {
        packetBuffer.writeItemStack(pkt.data.torcher);
        packetBuffer.writeInt(pkt.data.level);
        packetBuffer.writeBoolean(pkt.data.enabled);
        packetBuffer.writeBoolean(pkt.data.pickUpEnabled);
        packetBuffer.writeBlockPos(pkt.data.origin);
        packetBuffer.writeInt(pkt.data.pattern.getHeight());
        packetBuffer.writeInt(pkt.data.pattern.getWidth());
        packetBuffer.writeBoolean(pkt.data.pattern.isAlternating());
    }

    public static CPacketOCDTorcher decode(PacketBuffer packetBuffer                                           ) {
        PacketTorcher data = new PacketTorcher();
        data.torcher = packetBuffer.readItemStack();
        data.level = packetBuffer.readInt();
        data.enabled = packetBuffer.readBoolean();
        data.pickUpEnabled = packetBuffer.readBoolean();
        data.origin = packetBuffer.readBlockPos();
        data.pattern.setHeight(packetBuffer.readInt());
        data.pattern.setWidth(packetBuffer.readInt());
        data.pattern.setAlternating(packetBuffer.readBoolean());
        return new CPacketOCDTorcher(data);
    }

    public static class Handler
    {
        public static void handle(final CPacketOCDTorcher pkt, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                LOGGER.info("Receiving EMC data from client.");
                EntityPlayerMP player = ctx.get().getSender();
                ItemStack torcher = player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
                CapabilityOCDTorcher.ICapabilityOCDTorcher cap = torcher.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElseThrow(NullPointerException::new);
                cap.SetLevel(pkt.data.level);
                cap.SetOrigin(pkt.data.origin);
                cap.SetEnabled(pkt.data.enabled);
                cap.SetPickupEnabled(pkt.data.pickUpEnabled);
                cap.SetPattern(pkt.data.pattern);
            });
        }
    }

    /*
    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        torcher = packetBuffer.readItemStack();
        level = packetBuffer.readInt();
        enabled = packetBuffer.readBoolean();
        pickUpEnabled = packetBuffer.readBoolean();
        origin = packetBuffer.readBlockPos();
        pattern.setHeight(packetBuffer.readInt());
        pattern.setWidth(packetBuffer.readInt());
        pattern.setAlternating(packetBuffer.readBoolean());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeItemStack(torcher);
        packetBuffer.writeInt(level);
        packetBuffer.writeBoolean(enabled);
        packetBuffer.writeBoolean(pickUpEnabled);
        packetBuffer.writeBlockPos(origin);
        packetBuffer.writeInt(pattern.getHeight());
        packetBuffer.writeInt(pattern.getWidth());
        packetBuffer.writeBoolean(pattern.isAlternating());
    }

    @Override
    public void processPacket(INetHandlerPlayServer iNetHandlerPlayServer) {
        ModItems.ITEM_OCD_TORCHER.processOCDTorcherEdited(this);
    }
    */
}
