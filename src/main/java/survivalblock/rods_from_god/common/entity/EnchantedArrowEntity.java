package survivalblock.rods_from_god.common.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;

@SuppressWarnings("unused")
public class EnchantedArrowEntity extends PersistentProjectileEntity {

    private int duration = 200;
    protected float scale = 1;
    protected float prevScale = 0;

    public EnchantedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected EnchantedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(entityType, owner, world, stack, shotFrom);
    }

    protected EnchantedArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(entityType, x, y, z, world, stack, shotFrom);
    }

    public EnchantedArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        this(RodsFromGodEntityTypes.ENCHANTED_ARROW, world, owner, stack, shotFrom);
    }

    public EnchantedArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        this(RodsFromGodEntityTypes.ENCHANTED_ARROW, world, x, y, z, stack, shotFrom);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getOwner() instanceof BookEntity book) {
            this.scale = (float) (book.getAttributeValue(EntityAttributes.GENERIC_SCALE) * 0.375); // 1/2 * 3/4
            if (this.scale != this.prevScale) {
                this.prevScale = this.scale;
                this.calculateDimensions();
            }
        }
        if (this.getWorld().isClient) {
            if (!this.inGround) {
                this.getWorld().addParticle(ParticleTypes.ENCHANT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        } else {
            if (this.age > this.duration || this.inGroundTime > 5 || this.getVelocity().isWithinRangeOf(Vec3d.ZERO, 1, 1)) {
                this.discard();
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Duration")) {
            this.duration = nbt.getInt("Duration");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Duration", this.duration);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return this.getBaseDimensions(pose).scaled(this.getScale());
    }

    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.getType().getDimensions();
    }

    public float getScale() {
        return this.scale;
    }
}
