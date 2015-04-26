package com.talesdev.core.player.data;

import com.google.common.collect.Iterators;
import com.talesdev.core.config.Savable;
import com.talesdev.core.entity.DamageData;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.system.Destroyable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Player Damaging info
 *
 * @author MoKunz
 */
public class PlayerDamage implements Savable, Destroyable {
    private CorePlayer corePlayer;
    private boolean god = false;
    private List<DamageData> damageDataList;

    public PlayerDamage(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        this.corePlayer.autoDestroy(this);
        this.corePlayer.autoSave(this);
        damageDataList = new ArrayList<>();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadFrom(FileConfiguration config) {

    }

    @Override
    public void saveTo(FileConfiguration config) {

    }

    @Override
    public String getName() {
        return "PlayerDamage";
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public void clearDamageData() {
        damageDataList.clear();
    }

    public void damage(DamageData damageData) {
        damageDataList.add(damageData);
    }

    public DamageData getLast() {
        for (DamageData damageData : damageDataList) {
            if (damageData.getTime() == Collections.max(getDamageTimes(damageDataList))) {
                return damageData;
            }
        }
        return Iterators.getLast(damageDataList.iterator());
    }

    public List<DamageData> getDamageDataList() {
        return new ArrayList<>(damageDataList);
    }

    public List<DamageData> getDamageDataList(EntityType... entityType) {
        List<EntityType> entityTypeList = Arrays.asList(entityType);
        return getEntityDamage().stream().filter((
                damageData -> entityTypeList.contains(damageData.getDamager().getType()))).collect(Collectors.toList());
    }

    public List<DamageData> getDamageDataList(Predicate<DamageData> damageDataPredicate) {
        return damageDataList.stream().filter(damageDataPredicate::test).collect(Collectors.toList());
    }

    public List<DamageData> getEntityDamage() {
        List<DamageData> damageDatas = new ArrayList<>(damageDataList);
        for (Iterator<DamageData> it = damageDatas.iterator(); it.hasNext(); ) {
            DamageData damageData = it.next();
            if (damageData.getDamager() == null) {
                it.remove();
            }
        }
        return damageDatas;
    }

    public List<Long> getDamageTimes(List<DamageData> damageDataList) {
        return damageDataList.stream().map(DamageData::getTime).collect(Collectors.toList());
    }

    public DamageData getFirst() {
        for (DamageData damageData : damageDataList) {
            if (damageData.getTime() == Collections.min(getDamageTimes(damageDataList))) {
                return damageData;
            }
        }
        return damageDataList.size() > 0 ? damageDataList.get(0) : null;
    }

    public DamageData getFirstEntity() {
        List<DamageData> entityDamage = getEntityDamage();
        for (DamageData damageData : entityDamage) {
            if (damageData.getTime() == Collections.min(getDamageTimes(entityDamage))) {
                return damageData;
            }
        }
        return entityDamage.size() > 0 ? entityDamage.get(0) : null;
    }

    public DamageData getLastEntity() {
        List<DamageData> entityDamage = getEntityDamage();
        for (DamageData damageData : entityDamage) {
            if (damageData.getTime() == Collections.max(getDamageTimes(entityDamage))) {
                return damageData;
            }
        }
        return Iterators.getLast(damageDataList.iterator());
    }
}
