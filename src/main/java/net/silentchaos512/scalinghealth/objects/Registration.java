package net.silentchaos512.scalinghealth.objects;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.loot.TableGlobalModifier;
import net.silentchaos512.scalinghealth.objects.block.ShardOreBlock;
import net.silentchaos512.scalinghealth.objects.item.DifficultyMutatorItem;
import net.silentchaos512.scalinghealth.objects.item.HealingItem;
import net.silentchaos512.scalinghealth.objects.item.HeartCrystal;
import net.silentchaos512.scalinghealth.objects.item.PowerCrystal;
import net.silentchaos512.scalinghealth.objects.potion.BandagedEffect;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ScalingHealth.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ScalingHealth.MOD_ID);
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ScalingHealth.MOD_ID);
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ScalingHealth.MOD_ID);
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ScalingHealth.MOD_ID);
    private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLMS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ScalingHealth.MOD_ID);

    public static final RegistryObject<Block> HEART_CRYSTAL_ORE = BLOCKS.register("heart_crystal_ore", () ->
            new ShardOreBlock(Block.Properties.of(Material.STONE).strength(3, 15)));
    public static final RegistryObject<Block> POWER_CRYSTAL_ORE = BLOCKS.register("power_crystal_ore", () ->
            new ShardOreBlock(Block.Properties.of(Material.STONE).strength(3, 15)));

    public static final RegistryObject<Item> HEART_CRYSTAL_ORE_ITEM = ITEMS.register("heart_crystal_ore", () ->
            new BlockItem(HEART_CRYSTAL_ORE.get(), new Item.Properties().tab(ScalingHealth.SH)));
    public static final RegistryObject<Item> POWER_CRYSTAL_ORE_ITEM = ITEMS.register("power_crystal_ore", () ->
            new BlockItem(POWER_CRYSTAL_ORE.get(), new Item.Properties().tab(ScalingHealth.SH)));

    //Crystals
    public static final RegistryObject<Item> HEART_CRYSTAL = ITEMS.register("heart_crystal", () ->
            new HeartCrystal(grouped()));
    public static final RegistryObject<Item> HEART_CRYSTAL_SHARD = ITEMS.register("heart_crystal_shard", () ->
            new Item(grouped()));
    public static final RegistryObject<Item> HEART_DUST = ITEMS.register("heart_dust", () ->
            new Item(grouped()));
    public static final RegistryObject<Item> POWER_CRYSTAL = ITEMS.register("power_crystal", () ->
            new PowerCrystal(grouped()));
    public static final RegistryObject<Item> POWER_CRYSTAL_SHARD = ITEMS.register("power_crystal_shard", () ->
            new Item(grouped()));

    //healing
    public static final RegistryObject<Item> BANDAGES = ITEMS.register("bandages", () ->
            new HealingItem(0.3f, 1));
    public static final RegistryObject<Item> MEDKIT = ITEMS.register("medkit", () ->
            new HealingItem(0.7f, 4));

    //difficulty hearts
    public static final RegistryObject<Item> CURSED_HEART = ITEMS.register("cursed_heart", () ->
            new DifficultyMutatorItem(DifficultyMutatorItem.Type.CURSED, grouped()));
    public static final RegistryObject<Item> ENCHANTED_HEART = ITEMS.register("enchanted_heart", () ->
            new DifficultyMutatorItem(DifficultyMutatorItem.Type.ENCHANTED, grouped()));
    public static final RegistryObject<Item> CHANCE_HEART = ITEMS.register("chance_heart", () ->
            new DifficultyMutatorItem(DifficultyMutatorItem.Type.CHANCE, grouped()));

    public static final RegistryObject<Effect> BANDAGED = EFFECTS.register("bandaged", () ->
            new BandagedEffect(EffectType.NEUTRAL, 0xf7dcad)
                    .addAttributeModifier(
                            Attributes.MOVEMENT_SPEED,
                            BandagedEffect.MOD_UUID,
                            BandagedEffect.SPEED_MODIFIER,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));

    public static final RegistryObject<BasicParticleType> HEART_CRYSTAL_PARTICLE = PARTICLES.register("heart_crystal", () ->
            new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> POWER_CRYSTAL_PARTICLE = PARTICLES.register("power_crystal", () ->
            new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> CURSED_HEART_PARTICLE = PARTICLES.register("cursed_heart", () ->
            new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> ENCHANTED_HEART_PARTICLE = PARTICLES.register("enchanted_heart", () ->
            new BasicParticleType(false));

    public static final RegistryObject<SoundEvent> CURSED_HEART_USE = makeSound("cursed_heart_use");
    public static final RegistryObject<SoundEvent> ENCHANTED_HEART_USE = makeSound("enchanted_heart_use");
    public static final RegistryObject<SoundEvent> HEART_CRYSTAL_USE = makeSound("heart_crystal_use");
    public static final RegistryObject<SoundEvent> PLAYER_DIED = makeSound("player_died");

    public static final RegistryObject<GlobalLootModifierSerializer<TableGlobalModifier>> TABLE_INJECTOR =
            GLMS.register("table_loot_mod", TableGlobalModifier.Serializer::new);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        PARTICLES.register(bus);
        EFFECTS.register(bus);
        SOUNDS.register(bus);
        GLMS.register(bus);
    }

    private static RegistryObject<SoundEvent> makeSound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(ScalingHealth.getId(name)));
    }

    private static Item.Properties grouped() {
        return new Item.Properties().tab(ScalingHealth.SH);
    }
}
