package de.madone.ocdtorcher.network.client;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.network.ModNetwork;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class CPacketOCDTorcher {

    private static final Logger LOGGER = LogManager.getLogger();

    public enum CommandID implements IStringSerializable {
        NONE(0, "none"),
        BUTTON_ENABLE(1, "button_enable"),
        BUTTON_PICKUP(2, "button_pickup"),
        BUTTON_ALTERNATE(3, "button_alternate"),
        TEXTFIELD_PATTERN_X(7, "textfield_pattern_x"),
        TEXTFIELD_PATTERN_Z(8, "textfield_pattern_z");


        private int index;
        private String name;

        CommandID(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class PacketTorcher {
        private CommandID command_id;
        private int value;

        public PacketTorcher(CommandID command_id, int value) {
            this.command_id = command_id;
            this.value = value;
        }

        public PacketTorcher() {
            this.command_id = CommandID.NONE;
            this.value = 0;
        }
    }

    private final PacketTorcher data;

    public CPacketOCDTorcher(PacketTorcher data) {
        this.data = data;
    }

    public static void encode(CPacketOCDTorcher pkt, PacketBuffer packetBuffer) {
        packetBuffer.writeEnumValue(pkt.data.command_id);
        packetBuffer.writeInt(pkt.data.value);
    }

    public static CPacketOCDTorcher decode(PacketBuffer packetBuffer) {
        PacketTorcher data = new PacketTorcher();
        data.command_id = packetBuffer.readEnumValue(CommandID.class);
        data.value = packetBuffer.readInt();
        return new CPacketOCDTorcher(data);
    }

    public static class Handler {
        @SuppressWarnings("ConstantConditions")
        public static void handle(final CPacketOCDTorcher pkt, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                LOGGER.info("Receiving OCD Torcher data from client.");
                EntityPlayerMP player = ctx.get().getSender();

                assert player != null;

                ItemStack torcher = player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher ? player.getHeldItemMainhand() : player.getHeldItemOffhand();

                CapabilityOCDTorcher.ICapabilityOCDTorcher cap = torcher.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElseThrow(NullPointerException::new);
                switch (pkt.data.command_id) {
                    case BUTTON_ENABLE:
                        cap.SetEnabled(pkt.data.value == 1);
                        break;
                    case BUTTON_PICKUP:
                        cap.SetPickupEnabled(pkt.data.value == 1);
                        break;
                    case BUTTON_ALTERNATE:
                        cap.GetPattern().setAlternating(pkt.data.value == 1);
                        break;
                    case TEXTFIELD_PATTERN_X:
                        cap.GetPattern().setWidth(pkt.data.value);
                        break;
                    case TEXTFIELD_PATTERN_Z:
                        cap.GetPattern().setHeight(pkt.data.value);
                        break;
                }
                ModNetwork.sendOCDTorcherData(cap.GetEnabled(), cap.GetPickupEnabled(), cap.GetOrigin(), cap.GetPattern(), player);
                ctx.get().setPacketHandled(true);
            });
        }
    }
}
