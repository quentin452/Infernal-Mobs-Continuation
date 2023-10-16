package atomicstryker.infernalmobs.common.mods;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.config.Configuration;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;
import atomicstryker.infernalmobs.common.mods.api.ModifierLoader;

public class MM_Wither extends MobModifier {

    private static int potionDuration;

    public MM_Wither(@Nullable MobModifier next) {
        super("Wither", next);
    }

    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage) {
        if (source.getEntity() != null && (source.getEntity() instanceof EntityLivingBase)
                && InfernalMobsCore.instance().getIsEntityAllowedTarget(source.getEntity())
                && !(source instanceof EntityDamageSourceIndirect)
                && !source.isProjectile()) {
            ((EntityLivingBase) source.getEntity())
                    .addPotionEffect(new PotionEffect(Potion.wither.id, potionDuration, 0));
        }

        return super.onHurt(mob, source, damage);
    }

    @Override
    public float onAttack(EntityLivingBase entity, DamageSource source, float damage) {
        if (entity != null && InfernalMobsCore.instance().getIsEntityAllowedTarget(entity)) {
            entity.addPotionEffect(new PotionEffect(Potion.wither.id, potionDuration, 0));
        }

        return super.onAttack(entity, source, damage);
    }

    @Override
    protected String[] getModNameSuffix() {
        return suffix;
    }

    private static String[] suffix = { "ofDarkSkulls", "Doomskull" };

    @Override
    protected String[] getModNamePrefix() {
        return prefix;
    }

    private static String[] prefix = { "withering" };

    public static class Loader extends ModifierLoader<MM_Wither> {

        public Loader() {
            super(MM_Wither.class);
        }

        @Override
        public MM_Wither make(@Nullable MobModifier next) {
            return new MM_Wither(next);
        }

        @Override
        public void loadConfig(Configuration config) {
            potionDuration = config
                    .get(getModifierClassName(), "witherDurationTicks", 120L, "Time attacker is withered").getInt(120);
        }
    }
}
