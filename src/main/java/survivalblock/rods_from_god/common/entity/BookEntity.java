package survivalblock.rods_from_god.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import survivalblock.atmosphere.atmospheric_api.not_mixin.entity.EntityWithAttributesImpl;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.cca.entity.BookTargetComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;

import java.util.function.Function;

public class BookEntity extends EntityWithAttributesImpl {

    public static final String PROJECTILE_DURATION_KEY = "projectileDuration";
    public static final float DEFAULT_SCALE = 1.0F;
    public static final Function<PlayerEntity, Boolean> CAN_EDIT = PlayerEntity::isCreativeLevelTwoOp;

    private static final Random RANDOM = Random.create();

    protected float prevScale = 0;
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float flipRandom;
    public float flipTurn;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    public float bookRotation;
    public float lastBookRotation;
    public float targetBookRotation;
    private int projectileDuration = 200;

    public BookEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public BookEntity(World world) {
        this(RodsFromGodEntityTypes.BOOK, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(PROJECTILE_DURATION_KEY)) {
            this.projectileDuration = nbt.getInt(PROJECTILE_DURATION_KEY);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(PROJECTILE_DURATION_KEY, this.projectileDuration);
    }

    @Override
    public void tick() {
        super.tick();
        float l = this.getScale();
        if (l != this.prevScale) {
            this.prevScale = l;
            this.calculateDimensions();
        }
        World world = this.getWorld();
        boolean isClient = this.getWorld().isClient;
        Vec3d pos = this.getPos().add(0.0F, this.getHeight() * 0.25, 0.0F);
        if (isClient) this.pageTurningSpeed = this.nextPageTurningSpeed;
        this.lastBookRotation = this.bookRotation;
        BookTargetComponent component = this.getComponent();
        final double range = component.getRange();
        Entity entity = component.onlyTargetsPlayers() ? world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), range, true) : world.getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable(), null, this.getX(), this.getY(), this.getZ(), new Box(this.getPos(), this.getPos()).expand(range));
        if (entity != null) {
            Vec3d eyePos = entity.getEyePos();
            double d = eyePos.getX() - pos.getX();
            double e = eyePos.getY() - pos.getY();
            double f = eyePos.getZ() - pos.getZ();
            double g = Math.sqrt(d * d + f * f);
            this.setPitch(MathHelper.wrapDegrees((float)(MathHelper.atan2(e, g) * 180.0F / (float)Math.PI)));
            this.setYaw(MathHelper.wrapDegrees((float)(MathHelper.atan2(f, d) * 180.0F / (float)Math.PI) - 90.0F));
            this.targetBookRotation = (float) MathHelper.atan2(f, d);
            if (isClient) {
                this.nextPageTurningSpeed += 0.1F;
                if (this.nextPageTurningSpeed < 0.5F || RANDOM.nextInt(40) == 0) {
                    float flip = this.flipRandom;

                    do {
                        this.flipRandom = this.flipRandom + (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                    } while (flip == this.flipRandom);
                }
            }
        } else {
            this.targetBookRotation += 0.02F;
            if (isClient) this.nextPageTurningSpeed -= 0.1F;
            this.setPitch(80.0F);
        }

        while (this.bookRotation >= (float) Math.PI) {
            this.bookRotation -= (float) (Math.PI * 2);
        }

        while (this.bookRotation < (float) -Math.PI) {
            this.bookRotation += (float) (Math.PI * 2);
        }

        while (this.targetBookRotation >= (float) Math.PI) {
            this.targetBookRotation -= (float) (Math.PI * 2);
        }

        while (this.targetBookRotation < (float) -Math.PI) {
            this.targetBookRotation += (float) (Math.PI * 2);
        }

        float g = this.targetBookRotation - this.bookRotation;

        while (g >= (float) Math.PI) {
            g -= (float) (Math.PI * 2);
        }

        while (g < (float) -Math.PI) {
            g += (float) (Math.PI * 2);
        }

        this.bookRotation += g * 0.4F;
        if (isClient) {
            this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
            this.ticks++;
            this.pageAngle = this.nextPageAngle;
            float h = (this.flipRandom - this.nextPageAngle) * 0.4F;
            float i = 0.2F;
            h = MathHelper.clamp(h, -i, i);
            this.flipTurn = this.flipTurn + (h - this.flipTurn) * 0.9F;
            this.nextPageAngle = this.nextPageAngle + this.flipTurn;
        } else if (entity != null && this.age % 4 == 0) {
            Vec3d vec3d = Vec3d.fromPolar(-this.getPitch(), this.getYaw()).normalize().addRandom(this.random, 0.02f).multiply(5);
            Vec3d arrowPos = pos.add(vec3d);
            EnchantedArrowEntity arrow = new EnchantedArrowEntity(world, arrowPos.getX(), arrowPos.getY(), arrowPos.getZ(), Items.ARROW.getDefaultStack(), null);
            arrow.setCritical(true);
            arrow.setOwner(this);
            arrow.setVelocity(vec3d);
            arrow.setDuration(this.projectileDuration);
            world.spawnEntity(arrow);
            arrow.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 180.0F / (float)Math.PI));
            arrow.setPitch((float)(MathHelper.atan2(vec3d.y, vec3d.horizontalLength()) * 180.0F / (float)Math.PI));
            arrow.prevYaw = this.getYaw();
            arrow.prevPitch = this.getPitch();
        }
    }

    public BookTargetComponent getComponent() {
        return RodsFromGodEntityComponents.BOOK_TARGET.get(this);
    }

    public float getScale() {
        AttributeContainer attributeContainer = this.getAttributes();
        return attributeContainer == null ? DEFAULT_SCALE : (float) attributeContainer.getValue(EntityAttributes.GENERIC_SCALE);
    }

    @Override
    public final EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(this.getScale());
    }

    @Override
    public AttributeContainer getDefaultAttributeContainer() {
        return new AttributeContainer(DefaultAttributeContainer.builder()
                .add(EntityAttributes.GENERIC_SCALE, DEFAULT_SCALE).build());
    }

    public int getProjectileDuration() {
        return this.projectileDuration;
    }

    public void setProjectileDuration(int projectileDuration) {
        this.projectileDuration = projectileDuration;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        return ActionResult.success(player.getWorld().isClient);
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }
}
