package com.talesdev.core.game.skill;

import com.talesdev.core.TalesCore;

/**
 * Base skill
 *
 * @author MoKunz
 */
public abstract class BaseSkill {
    private TalesCore core = TalesCore.getPlugin();
    private String name = "";
    private String description = "";
    private SkillCost cost;

    public BaseSkill(SkillCost cost, String name, String description) {
        this.cost = cost;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SkillCost getCost() {
        return cost;
    }
}
