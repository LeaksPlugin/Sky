package com.talesdev.core.player;

import org.bukkit.event.block.Action;

/**
 * Player click interaction
 *
 * @author MoKunz
 */
public enum ClickingAction {
    LEFT_CLICK,
    RIGHT_CLICK,
    NONE;

    public static ClickingAction getClick(Action action) {
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return ClickingAction.RIGHT_CLICK;
        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            return ClickingAction.LEFT_CLICK;
        } else {
            return ClickingAction.NONE;
        }
    }

    public static boolean isRightClick(Action action) {
        return getClick(action).equals(ClickingAction.RIGHT_CLICK);
    }

    public static boolean isLeftClick(Action action) {
        return getClick(action).equals(ClickingAction.LEFT_CLICK);
    }

    public static boolean isBlockClicked(Action action) {
        return action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK);
    }
}
