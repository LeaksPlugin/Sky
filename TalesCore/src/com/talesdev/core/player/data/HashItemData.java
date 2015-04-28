package com.talesdev.core.player.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Item Data for hash-based collections
 *
 * @author MoKunz
 */
public abstract class HashItemData implements ItemData {
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(forItem()).append(dataType()).toHashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof ItemData) {
            if (object == this) {
                return true;
            }
            return new EqualsBuilder().
                    append(dataType(), ((ItemData) object).dataType()).
                    append(forItem(), ((ItemData) object).forItem()).
                    isEquals();
        } else {
            return false;
        }
    }
}
