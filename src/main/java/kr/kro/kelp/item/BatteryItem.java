package kr.kro.kelp.item;

import kr.kro.kelp.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BatteryItem extends Item {
    private final int defaultMax;
    private final int ioPerTick;

    public BatteryItem(Properties props, int defaultMaxFE, int ioPerTick) {
        super(props.stacksTo(1));
        this.defaultMax = defaultMaxFE;
        this.ioPerTick = ioPerTick;
    }

    /* --------- 내부 헬퍼 --------- */
    private int getEnergy(ItemStack st) {
        return st.getOrDefault(ModDataComponents.ENERGY.get(), 0);
    }

    private int getMax(ItemStack st) {
        return st.getOrDefault(ModDataComponents.MAX_ENERGY.get(), defaultMax);
    }

    private void setEnergy(ItemStack st, int v) {
        int max = getMax(st);
        if (v < 0) v = 0;
        if (v > max) v = max;
        st.set(ModDataComponents.ENERGY.get(), v);
        // MAX_ENERGY 기본값 보장
        if (st.get(ModDataComponents.MAX_ENERGY.get()) == null)
            st.set(ModDataComponents.MAX_ENERGY.get(), max);
    }

    /* --------- 기본값 주입 --------- */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (stack.get(ModDataComponents.MAX_ENERGY.get()) == null)
            stack.set(ModDataComponents.MAX_ENERGY.get(), defaultMax);
        if (stack.get(ModDataComponents.ENERGY.get()) == null)
            stack.set(ModDataComponents.ENERGY.get(), 0);
    }

    /* --------- 게이지 툴팁 --------- */
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int cur = getEnergy(stack);
        int max = getMax(stack);
        return max == 0 ? 0 : Math.round(13f * cur / max);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xEB3636; // 빨강
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> tip, TooltipFlag flag) {
        tip.add(Component.literal(getEnergy(stack) + " / " + getMax(stack) + " FE"));
        tip.add(Component.literal("IO/t: " + ioPerTick));
    }

    /* --------- 방전/충전 API (다른 코드에서 호출) --------- */
    public int receiveFE(ItemStack stack, int maxReceive, boolean simulate) {
        int cur = getEnergy(stack);
        int max = getMax(stack);
        int toRecv = Math.min(maxReceive, Math.min(ioPerTick, max - cur));
        if (!simulate && toRecv > 0) setEnergy(stack, cur + toRecv);
        return toRecv;
    }

    public int extractFE(ItemStack stack, int maxExtract, boolean simulate) {
        int cur = getEnergy(stack);
        int toExt = Math.min(maxExtract, Math.min(ioPerTick, cur));
        if (!simulate && toExt > 0) setEnergy(stack, cur - toExt);
        return toExt;
    }
}
