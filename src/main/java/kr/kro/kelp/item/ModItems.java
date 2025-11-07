package kr.kro.kelp.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static kr.kro.kelp.EngineersToolbox.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<Item> BATTERY =
            ITEMS.register("battery", () -> new BatteryItem(new Item.Properties(), 100_000, 500));
}
