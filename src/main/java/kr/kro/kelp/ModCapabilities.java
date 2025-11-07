package kr.kro.kelp;

import kr.kro.kelp.item.BatteryItem;
import kr.kro.kelp.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

public final class ModCapabilities {

    public static void registerCaps(RegisterCapabilitiesEvent event) {
        // 이 아이템은 "에너지 저장 캡"을 갖는다
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new StackEnergyStorage(stack),
                ModItems.BATTERY.get());
    }

    // 아이템 스택 하나를 IEnergyStorage로 감싸는 어댑터
    public static class StackEnergyStorage implements IEnergyStorage {
        private final ItemStack stack;

        StackEnergyStorage(ItemStack stack) {
            this.stack = stack;
        }

        private BatteryItem asBattery() {
            return (BatteryItem) stack.getItem();
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return asBattery().receiveFE(stack, maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return asBattery().extractFE(stack, maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() {
            return stack.getOrDefault(ModDataComponents.ENERGY.get(), 0);
        }

        @Override
        public int getMaxEnergyStored() {
            return stack.getOrDefault(ModDataComponents.MAX_ENERGY.get(), 0);
        }

        @Override
        public boolean canExtract() { return true; }

        @Override
        public boolean canReceive() { return true; }
    }
}
