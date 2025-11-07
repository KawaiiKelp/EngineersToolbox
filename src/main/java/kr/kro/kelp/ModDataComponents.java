package kr.kro.kelp;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static kr.kro.kelp.EngineersToolbox.MODID;

public class ModDataComponents {

    // 데이터 컴포넌트용 레지스트리 선언
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);
    /// 아이템 하나하나가 가질 수 있는 데이터의 형태를 정의하는 타입
    /// 배터리의 현재 에너지
    /// 내구도
    /// 처럼 모든 데이터 값을 컴포넌트로 등록함


    // 에너지 컴포넌트 등록
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = // ENERGY라는 이름으로 컴포넌트 등록
            DATA_COMPONENT_TYPES.register("energy", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT) // 컴포넌트를 NBT로 직렬화할 때 어떻게 쓸지 지정
                            .networkSynchronized(ByteBufCodecs.VAR_INT) // 클라이언트 <-> 서버 사이 전송용 직렬화 설정 (멀티플레이 시 값 동기화)
                            .build()); // 설정을 종합하고 최종적으로 DataComponentType<T> 객체 생성

    // 최대 저장량을 담는 최대 에너지 컴포넌트 등록
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_ENERGY =
            DATA_COMPONENT_TYPES.register("max_energy", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build());
}
