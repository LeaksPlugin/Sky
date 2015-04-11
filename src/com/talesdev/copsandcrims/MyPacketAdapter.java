package com.talesdev.copsandcrims;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.talesdev.core.system.NMSClass;
import com.talesdev.core.system.ReflectionUtils;
import com.talesdev.core.system.ReflectionUtils.RefClass;
import com.talesdev.core.system.ReflectionUtils.RefMethod;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Packet listener
 *
 * @author MoKunz
 */
public class MyPacketAdapter extends PacketAdapter {
    public MyPacketAdapter(Plugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.SET_SLOT)) {
            PacketContainer packet = event.getPacket();
            ItemStack itemStack = packet.getItemModifier().read(0);
            removeEnchantments(itemStack);
        }
    }

    private void removeEnchantments(ItemStack stack) {
        if (stack == null)
            return;
        Object[] copy = stack.getEnchantments().keySet().toArray();
        for (Object enchantment : copy) {
            stack.removeEnchantment((Enchantment) enchantment);
        }
    }

    private void removeAttribute(ItemStack itemStack) {
        // get nms itemstack
        RefClass craftItemStackClass = ReflectionUtils.getRefClass(NMSClass.getCBClass("inventory.CraftItemStack"));
        RefClass nmsItemStackClass = ReflectionUtils.getRefClass(NMSClass.getNMSClass("ItemStack"));
        RefClass nbtTagCompoundClass = ReflectionUtils.getRefClass(NMSClass.getNMSClass("NBTTagCompound"));
        RefMethod asNMSCopyMethod = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
        RefMethod asBukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass.getRealClass());
        RefMethod cloneMethod = nmsItemStackClass.getMethod("cloneItemStack");
        RefMethod hasTagMethod = nmsItemStackClass.getMethod("hasTag");
        Object nmsItemStack = cloneMethod.of(asNMSCopyMethod.of(null).call(itemStack)).call();
        Object nbtTag = nbtTagCompoundClass.getConstructor().create();
        // get nbt tag
    }
}
