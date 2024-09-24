package com.renatusnetwork.glyphs.listeners;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;

public class LuckPermsMutateEvent {

    public void onAdd(NodeAddEvent event) {
        if (!event.isUser() || !ConfigUtils.luckperms_enabled) {
            return;
        }

        User target = (User) event.getTarget();
        Node node = event.getNode();
        PlayerStats playerStats = PlayerStatsManager.getInstance().get(target.getUniqueId());

        if (playerStats != null && node instanceof PermissionNode) {
            String permission = ((PermissionNode) node).getPermission();

            if (permission.startsWith(ConfigUtils.permission_node_prefix)) {
                Tag tag = TagsManager.getInstance().get(permission.split(ConfigUtils.permission_node_prefix)[1]);

                if (tag != null) {
                    playerStats.sendMessage(ChatUtils.color("&7You have gained access to the tag &c" + tag.getTitle()));
                }
            }
        }
    }
}
