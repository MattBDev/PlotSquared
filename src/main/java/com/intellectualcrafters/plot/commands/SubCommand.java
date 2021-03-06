////////////////////////////////////////////////////////////////////////////////////////////////////
// PlotSquared - A plot manager and world generator for the Bukkit API                             /
// Copyright (c) 2014 IntellectualSites/IntellectualCrafters                                       /
//                                                                                                 /
// This program is free software; you can redistribute it and/or modify                            /
// it under the terms of the GNU General Public License as published by                            /
// the Free Software Foundation; either version 3 of the License, or                               /
// (at your option) any later version.                                                             /
//                                                                                                 /
// This program is distributed in the hope that it will be useful,                                 /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                                  /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                   /
// GNU General Public License for more details.                                                    /
//                                                                                                 /
// You should have received a copy of the GNU General Public License                               /
// along with this program; if not, write to the Free Software Foundation,                         /
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA                               /
//                                                                                                 /
// You can contact us via: support@intellectualsites.com                                           /
////////////////////////////////////////////////////////////////////////////////////////////////////
package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.PlotPlayer;

/**
 * SubCommand class
 *
 */
@SuppressWarnings({ "deprecation" })
public abstract class SubCommand extends com.plotsquared.general.commands.Command<PlotPlayer> {
    
    /**
     * The category
     */
    public CommandCategory category;
    
    /**
     * Send a message
     *
     * @param plr  Player who will receive the mssage
     * @param c    Caption
     * @param args Arguments (%s's)
     *
     * @see com.intellectualcrafters.plot.util.MainUtil#sendMessage(PlotPlayer, C, String...)
     */
    public boolean sendMessage(final PlotPlayer plr, final C c, final String... args) {
        c.send(plr, args);
        return true;
    }
}
