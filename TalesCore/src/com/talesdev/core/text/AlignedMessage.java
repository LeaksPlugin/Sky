package com.talesdev.core.text;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.util.ChatPaginator;

/**
 * Aligned message
 *
 * @author MoKunz
 */
public class AlignedMessage {
    private String text;

    public AlignedMessage(String text) {
        this.text = text;
    }

    public String center() {
        return StringUtils.center(text, ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH);
    }
}
