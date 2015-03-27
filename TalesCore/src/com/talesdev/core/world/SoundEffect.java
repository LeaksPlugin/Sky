package com.talesdev.core.world;

import org.bukkit.entity.*;

/**
 * Enum of sound
 *
 * @author MoKunz
 */
public enum SoundEffect {
    AMBIENT_CAVE_CAVE,
    AMBIENT_WEATHER_RAIN,
    AMBIENT_WEATHER_THUNDER,

    GAME_PLAYER_HURT_FALL_BIG,
    GAME_NEUTRAL_HURT_FALL_BIG,
    GAME_HOSTILE_HURT_FALL_BIG,
    GAME_PLAYER_HURT_FALL_SMALL,
    GAME_NEUTRAL_HURT_FALL_SMALL,
    GAME_HOSTILE_HURT_FALL_SMALL,
    GAME_PLAYER_HURT,
    GAME_NEUTRAL_HURT,
    GAME_HOSTILE_HURT,
    GAME_PLAYER_DIE,
    GAME_NEUTRAL_DIE,
    GAME_HOSTILE_DIE,

    DIG_CLOTH,
    DIG_GLASS,
    GAME_POTION_SMASH,
    DIG_GRASS,
    DIG_GRAVEL,
    DIG_SAND,
    DIG_SNOW,
    DIG_STONE,
    DIG_WOOD,

    FIRE_FIRE,
    FIRE_IGNITE,
    ITEM_FIRECHARGE_USE,

    FIREWORKS_BLAST,
    FIREWORKS_BLAST_FAR,
    FIREWORKS_LARGEBLAST,
    FIREWORKS_LARGEBLAST_FAR,
    FIREWORKS_LAUNCH,
    FIREWORKS_TWINKLE,
    FIREWORKS_TWINKLE_FAR,

    GAME_PLAYER_SWIM_SPLASH,
    GAME_NEUTRAL_SWIM_SPLASH,
    GAME_HOSTILE_SWIM_SPLASH,
    GAME_PLAYER_SWIM,
    GAME_NEUTRAL_SWIM,
    GAME_HOSTILE_SWIM,

    LIQUID_LAVA,
    LIQUID_LAVAPOP,
    LIQUID_WATER,

    MINECART_BASE,
    MINECART_INSIDE,

    NOTE_BASS,
    NOTE_BASSATTACK,
    NOTE_BD,
    NOTE_HARP,
    NOTE_HAT,
    NOTE_PLING,
    NOTE_SNARE,

    PORTAL_PORTAL,
    PORTAL_TRAVEL,
    PORTAL_TRIGGER,

    RANDOM_ANVIL_BREAK,
    RANDOM_ANVIL_LAND,
    RANDOM_ANVIL_USE,
    RANDOM_BOW,
    RANDOM_BOWHIT,
    RANDOM_BREAK,
    RANDOM_BURP,
    RANDOM_CHESTCLOSED,
    RANDOM_CHESTOPEN,
    GUI_BUTTON_PRESS,
    RANDOM_CLICK,
    RANDOM_DOOR_OPEN,
    RANDOM_DOOR_CLOSE,
    RANDOM_DRINK,
    RANDOM_EAT,
    RANDOM_EXPLODE,
    RANDOM_FIZZ,
    GAME_TNT_PRIMED,
    CREEPER_PRIMED,
    RANDOM_LEVELUP,
    RANDOM_ORB,
    RANDOM_POP,
    RANDOM_SPLASH,
    RANDOM_SUCCESSFUL_HIT,
    RANDOM_WOOD_CLICK,

    STEP_CLOTH,
    STEP_GRASS,
    STEP_GRAVEL,
    STEP_LADDER,
    STEP_SAND,
    STEP_SNOW,
    STEP_STONE,
    STEP_WOOD,

    TILE_PISTON_IN,
    TILE_PISTON_OUT,

    MOB_BAT_DEATH,
    MOB_BAT_HURT,
    MOB_BAT_IDLE,
    MOB_BAT_LOOP,
    MOB_BAT_TAKEOFF,

    MOB_BLAZE_BREATHE,
    MOB_BLAZE_DEATH,
    MOB_BLAZE_HIT,

    MOB_CAT_HISS,
    MOB_CAT_HITT,
    MOB_CAT_MEOW,
    MOB_CAT_PURR,
    MOB_CAT_PURREOW,

    MOB_CHICKEN_HURT,
    MOB_CHICKEN_PLOP,
    MOB_CHICKEN_SAY,
    MOB_CHICKEN_STEP,

    MOB_COW_HURT,
    MOB_COW_SAY,
    MOB_COW_STEP,

    MOB_CREEPER_DEATH,
    MOB_CREEPER_SAY,

    MOB_ENDERDRAGON_END,
    MOB_ENDERDRAGON_GROWL,
    MOB_ENDERDRAGON_HIT,
    MOB_ENDERDRAGON_WINGS,

    MOB_ENDERMEN_DEATH,
    MOB_ENDERMEN_HIT,
    MOB_ENDERMEN_IDLE,
    MOB_ENDERMEN_PORTAL,
    MOB_ENDERMEN_SCREAM,
    MOB_ENDERMEN_STARE,

    MOB_GHAST_AFFECTIONATE_SCREAM,
    MOB_GHAST_CHARGE,
    MOB_GHAST_DEATH,
    MOB_GHAST_FIREBALL,
    MOB_GHAST_MOAN,
    MOB_GHAST_SCREAM,

    MOB_GUARDIAN_HIT,
    MOB_GUARDIAN_IDLE,
    MOB_GUARDIAN_DEATH,
    MOB_GUARDIAN_ELDER_HIT,
    MOB_GUARDIAN_ELDER_IDLE,
    MOB_GUARDIAN_ELDER_DEATH,
    MOB_GUARDIAN_LAND_HIT,
    MOB_GUARDIAN_LAND_IDLE,
    MOB_GUARDIAN_LAND_DEATH,
    MOB_GUARDIAN_CURSE,
    MOB_GUARDIAN_ATTACK,
    MOB_GUARDIAN_FLOP,

    MOB_HORSE_ANGRY,
    MOB_HORSE_ARMOR,
    MOB_HORSE_BREATHE,
    MOB_HORSE_DEATH,
    MOB_HORSE_DONKEY_ANGRY,
    MOB_HORSE_DONKEY_DEATH,
    MOB_HORSE_DONKEY_HIT,
    MOB_HORSE_DONKEY_IDLE,
    MOB_HORSE_GALLOP,
    MOB_HORSE_HIT,
    MOB_HORSE_IDLE,
    MOB_HORSE_JUMP,
    MOB_HORSE_LAND,
    MOB_HORSE_LEATHER,
    MOB_HORSE_SKELETON_DEATH,
    MOB_HORSE_SKELETON_HIT,
    MOB_HORSE_SKELETON_IDLE,
    MOB_HORSE_SOFT,
    MOB_HORSE_WOOD,
    MOB_HORSE_ZOMBIE_DEATH,
    MOB_HORSE_ZOMBIE_HIT,
    MOB_HORSE_ZOMBIE_IDLE,

    MOB_IRONGOLEM_DEATH,
    MOB_IRONGOLEM_HIT,
    MOB_IRONGOLEM_THROW,
    MOB_IRONGOLEM_WALK,

    MOB_MAGMACUBE_BIG,
    MOB_MAGMACUBE_JUMP,
    MOB_MAGMACUBE_SMALL,

    MOB_PIG_DEATH,
    MOB_PIG_SAY,
    MOB_PIG_STEP,

    MOB_RABBIT_HURT,
    MOB_RABBIT_IDLE,
    MOB_RABBIT_HOP,
    MOB_RABBIT_DEATH,

    MOB_SHEEP_SAY,
    MOB_SHEEP_SHEAR,
    MOB_SHEEP_STEP,

    MOB_SILVERFISH_HIT,
    MOB_SILVERFISH_KILL,
    MOB_SILVERFISH_SAY,
    MOB_SILVERFISH_STEP,

    MOB_SKELETON_DEATH,
    MOB_SKELETON_HURT,
    MOB_SKELETON_SAY,
    MOB_SKELETON_STEP,

    MOB_SLIME_ATTACK,
    MOB_SLIME_BIG,
    MOB_SLIME_SMALL,

    MOB_SPIDER_DEATH,
    MOB_SPIDER_SAY,
    MOB_SPIDER_STEP,

    MOB_VILLAGER_DEATH,
    MOB_VILLAGER_HAGGLE,
    MOB_VILLAGER_HIT,
    MOB_VILLAGER_IDLE,
    MOB_VILLAGER_NO,
    MOB_VILLAGER_YES,

    MOB_WITHER_DEATH,
    MOB_WITHER_HURT,
    MOB_WITHER_IDLE,
    MOB_WITHER_SHOOT,
    MOB_WITHER_SPAWN,

    MOB_WOLF_BARK,
    MOB_WOLF_DEATH,
    MOB_WOLF_GROWL,
    MOB_WOLF_HOWL,
    MOB_WOLF_HURT,
    MOB_WOLF_PANTING,
    MOB_WOLF_SHAKE,
    MOB_WOLF_STEP,
    MOB_WOLF_WHINE,

    MOB_ZOMBIE_DEATH,
    MOB_ZOMBIE_HURT,
    MOB_ZOMBIE_INFECT,
    MOB_ZOMBIE_METAL,
    MOB_ZOMBIE_REMEDY,
    MOB_ZOMBIE_SAY,
    MOB_ZOMBIE_STEP,
    MOB_ZOMBIE_UNFECT,
    MOB_ZOMBIE_WOOD,
    MOB_ZOMBIE_WOODBREAK,

    MOB_ZOMBIEPIG_ZPIG,
    MOB_ZOMBIEPIG_ZPIGANGRY,
    MOB_ZOMBIEPIG_ZPIGDEATH,
    MOB_ZOMBIEPIG_ZPIGHURT,

    RECORDS_11,
    RECORDS_13,
    RECORDS_BLOCKS,
    RECORDS_CAT,
    RECORDS_CHIRP,
    RECORDS_FAR,
    RECORDS_MALL,
    RECORDS_MELLOHI,
    RECORDS_STAL,
    RECORDS_STRAD,
    RECORDS_WAIT,
    RECORDS_WARD,

    MUSIC_MENU,
    MUSIC_GAME,
    MUSIC_GAME_CREATIVE,
    MUSIC_GAME_END,
    MUSIC_GAME_END_DRAGON,
    MUSIC_GAME_END_CREDITS,
    MUSIC_GAME_NETHER,
    BLANK;

    public enum Pitch {
        BABY(1.5F);

        private float pitch;

        Pitch(float pitch) {
            this.pitch = pitch;
        }

        public float getPitch() {
            return pitch;
        }
    }


    public String toString() {
        return super.toString().replaceAll("_", "\\.").toLowerCase();
    }

    public static Sound getMobHurtSound(Entity entity) {
        EntityType type = entity.getType();
        SoundEffect soundEffect = SoundEffect.BLANK;
        Sound sound = new Sound(soundEffect,1.0F,1.0F);
        boolean isBaby = false;
        if (entity instanceof Ageable) {
            isBaby = !((Ageable) entity).isAdult();
        }
        if (entity instanceof Zombie) {
            isBaby = ((Zombie) entity).isBaby();
        }
        switch (type) {
            case BAT:
                soundEffect = SoundEffect.MOB_BAT_HURT;
                break;
            case CHICKEN:
                soundEffect = SoundEffect.MOB_CHICKEN_HURT;
                break;
            case COW:
                soundEffect = SoundEffect.MOB_COW_HURT;
                break;
            case MUSHROOM_COW:
                soundEffect = SoundEffect.MOB_COW_HURT;
                break;
            case PIG:
                soundEffect = SoundEffect.MOB_PIG_SAY;
                break;
            case RABBIT:
                soundEffect = SoundEffect.MOB_RABBIT_HURT;
                break;
            case SHEEP:
                soundEffect = SoundEffect.MOB_SHEEP_SAY;
                break;
            case SQUID:
                soundEffect = SoundEffect.BLANK;
                break;
            case VILLAGER:
                soundEffect = SoundEffect.MOB_VILLAGER_HIT;
                break;
            case SPIDER:
                soundEffect = SoundEffect.MOB_SPIDER_SAY;
                break;
            case CAVE_SPIDER:
                soundEffect = SoundEffect.MOB_SPIDER_SAY;
                break;
            case ENDERMAN:
                soundEffect = SoundEffect.MOB_ENDERMEN_HIT;
                break;
            case PIG_ZOMBIE:
                soundEffect = SoundEffect.MOB_ZOMBIEPIG_ZPIGHURT;
                break;
            case BLAZE:
                soundEffect = SoundEffect.MOB_BLAZE_HIT;
                break;
            case CREEPER:
                soundEffect = SoundEffect.MOB_CREEPER_SAY;
                break;
            case GUARDIAN:
                Guardian guardian = (Guardian) entity;
                if (guardian.isElder()) {
                    soundEffect = SoundEffect.MOB_GUARDIAN_ELDER_HIT;
                    break;
                } else {
                    soundEffect = SoundEffect.MOB_GUARDIAN_HIT;
                    break;
                }
            case ENDERMITE:
                soundEffect = SoundEffect.MOB_SILVERFISH_HIT;
                break;
            case GHAST:
                soundEffect = SoundEffect.MOB_GHAST_AFFECTIONATE_SCREAM;
                break;
            case MAGMA_CUBE:
                MagmaCube magmaCube = (MagmaCube) entity;
                int size = magmaCube.getSize();
                if (size < 1) {
                    soundEffect = SoundEffect.MOB_MAGMACUBE_SMALL;
                    break;
                } else {
                    soundEffect = SoundEffect.MOB_MAGMACUBE_BIG;
                    break;
                }
            case SILVERFISH:
                soundEffect = SoundEffect.MOB_SILVERFISH_HIT;
                break;
            case SKELETON:
                soundEffect = SoundEffect.MOB_SKELETON_HURT;
                break;
            case SLIME:
                Slime slime = (Slime) entity;
                int size2 = slime.getSize();
                if (size2 < 1) {
                    soundEffect = SoundEffect.MOB_SLIME_SMALL;
                    break;
                } else {
                    soundEffect = SoundEffect.MOB_SLIME_BIG;
                    break;
                }
            case WITCH:
                soundEffect = SoundEffect.BLANK;
                break;
            case ZOMBIE:
                soundEffect = SoundEffect.MOB_ZOMBIE_HURT;
                break;
            case HORSE:
                if (entity instanceof Horse) {
                    Horse horse = (Horse) entity;
                    switch (horse.getVariant()) {
                        case HORSE:
                        case MULE:
                            soundEffect = SoundEffect.MOB_HORSE_HIT;
                            break;
                        case DONKEY:
                            soundEffect = SoundEffect.MOB_HORSE_DONKEY_HIT;
                            break;
                        case SKELETON_HORSE:
                            soundEffect = SoundEffect.MOB_HORSE_SKELETON_HIT;
                            break;
                        case UNDEAD_HORSE:
                            soundEffect = SoundEffect.MOB_HORSE_ZOMBIE_HIT;
                            break;
                    }
                }
                break;
            case OCELOT:
                soundEffect = SoundEffect.MOB_CAT_HITT;
                break;
            case WOLF:
                soundEffect = SoundEffect.MOB_WOLF_HURT;
                break;
            case IRON_GOLEM:
                soundEffect = SoundEffect.MOB_IRONGOLEM_HIT;
                break;
            case SNOWMAN:
                soundEffect = SoundEffect.BLANK;
                break;
            case ENDER_DRAGON:
                soundEffect = SoundEffect.MOB_ENDERDRAGON_HIT;
                break;
            case WITHER:
                soundEffect = SoundEffect.MOB_WITHER_HURT;
                break;
            default:
                soundEffect = SoundEffect.BLANK;
                break;
        }
        sound.setSoundEffect(soundEffect);
        if(isBaby) sound.setPitch(1.5F);
        return sound;
    }
}
