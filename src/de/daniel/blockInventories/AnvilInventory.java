package de.daniel.blockInventories;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import com.nukkitx.fakeinventories.inventory.FakeInventory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class AnvilInventory extends FakeInventory {

    public AnvilInventory() {
        this(null);
    }

    public AnvilInventory(InventoryHolder ih) {
        super(InventoryType.ANVIL, ih);
    }

    @Override
    protected List<BlockVector3> onOpenBlock(Player p) {
        BlockVector3 bv3 = new BlockVector3((int) p.x, ((int) p.y + 2), (int) p.z);
        setAnvil(p, bv3);
        return Collections.singletonList(bv3);
    }

    private void setAnvil(Player p, BlockVector3 bv3) {
        UpdateBlockPacket ubp = new UpdateBlockPacket();
        ubp.x = bv3.x; ubp.y = bv3.y; ubp.z = bv3.z;
        ubp.blockRuntimeId = GlobalBlockPalette.getOrCreateRuntimeId(Block.ANVIL, 0);

        BlockEntityDataPacket bedp = new BlockEntityDataPacket();
        bedp.x = bv3.x; bedp.y = bv3.y; bedp.z = bv3.z;
        bedp.namedTag = getNBT(bv3, getName());
        p.dataPacket(ubp); p.dataPacket(bedp);
    }

    private static byte[] getNBT(BlockVector3 pos, String name) {
        CompoundTag tag = (new CompoundTag()).putString("id", "Anvil").putInt("x", pos.x).putInt("y", pos.y).putInt("z", pos.z).putString("CustomName", (name == null) ? "Anvil" : name);
        try {
            return NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN, true);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create NBT");
        }
    }
}