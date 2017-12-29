/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.util;

import mage.MageObject;
import mage.ObjectColor;

/**
 *
 * @author LevelX2
 */
public final class GameLog {

    static final String LOG_COLOR_PLAYER = "#20B2AA"; // LightSeaGreen
    static final String LOG_COLOR_PLAYER_REQUEST = "#D2691E"; // Chocolate
    static final String LOG_COLOR_PLAYER_CONFIRM = "#D2691E"; // Chocolate
    // colors for more dark background
    static final String LOG_COLOR_GREEN = "#90EE90"; // LightGreen
    static final String LOG_COLOR_RED = "#FF6347";   // Tomato
    static final String LOG_COLOR_BLUE = "#87CEFA";  // LightSkyBlue
    static final String LOG_COLOR_BLACK = "#696969";  // DimGray  	   // "#5F9EA0"; // CadetBlue
    static final String LOG_COLOR_WHITE = "#F0E68C"; // Khaki
    static final String LOG_COLOR_MULTI = "#DAA520"; // GoldenRod
    static final String LOG_COLOR_COLORLESS = "#B0C4DE"; // LightSteelBlue
    // colors for tooltip (light background)
    static final String LOG_TT_COLOR_RED = "Red";   // Tomato
    static final String LOG_TT_COLOR_GREEN = "Green"; // LightGreen
    static final String LOG_TT_COLOR_BLUE = "Blue";
    static final String LOG_TT_COLOR_BLACK = "Black";
    static final String LOG_TT_COLOR_WHITE = "#FDFFE6";
    static final String LOG_TT_COLOR_MULTI = "#FFAC40";
    static final String LOG_TT_COLOR_COLORLESS = "#94A4BA";
    static final String LOG_COLOR_NEUTRAL = "#F0F8FF"; // AliceBlue

    public static String replaceNameByColoredName(MageObject mageObject, String text) {
        return text.replaceAll(mageObject.getName(), getColoredObjectIdName(mageObject));
    }

    public static String getColoredObjectName(MageObject mageObject) {
        return "<font color=\'" + getColorName(mageObject.getColor(null)) + "\'>" + mageObject.getName() + "</font>";
    }

    public static String getColoredObjectIdName(MageObject mageObject) {
        return "<font color=\'" + getColorName(mageObject.getColor(null)) + "\'>" + mageObject.getName() + " [" + mageObject.getId().toString().substring(0, 3) + "]</font>";
    }

    public static String getColoredObjectIdNameForTooltip(MageObject mageObject) {
        return "<font color=\'" + getTooltipColorName(mageObject.getColor(null)) + "\'>" + mageObject.getName() + " [" + mageObject.getId().toString().substring(0, 3) + "]</font>";
    }

    public static String getNeutralColoredText(String text) {
        return "<font color=\'" + LOG_COLOR_NEUTRAL + "\'>" + text + "</font>";
    }

    public static String getColoredPlayerName(String name) {
        return "<font color=\'" + LOG_COLOR_PLAYER + "\'>" + name + "</font>";
    }

    public static String getPlayerRequestColoredText(String name) {
        return "<font color=\'" + LOG_COLOR_PLAYER_REQUEST + "\'>" + name + "</font>";
    }

    public static String getPlayerConfirmColoredText(String name) {
        return "<font color=\'" + LOG_COLOR_PLAYER_CONFIRM + "\'>" + name + "</font>";
    }

    public static String getSmallSecondLineText(String text) {
        return "<div style='font-size:11pt'>" + text + "</div>";
    }

    private static String getColorName(ObjectColor objectColor) {
        if (objectColor.isMulticolored()) {
            return LOG_COLOR_MULTI;
        } else if (objectColor.isColorless()) {
            return LOG_COLOR_COLORLESS;
        } else if (objectColor.isRed()) {
            return LOG_COLOR_RED;
        } else if (objectColor.isGreen()) {
            return LOG_COLOR_GREEN;
        } else if (objectColor.isBlue()) {
            return LOG_COLOR_BLUE;
        } else if (objectColor.isWhite()) {
            return LOG_COLOR_WHITE;
        } else {
            return LOG_COLOR_BLACK;
        }
    }

    private static String getTooltipColorName(ObjectColor objectColor) {
        if (objectColor.isMulticolored()) {
            return LOG_TT_COLOR_MULTI;
        } else if (objectColor.isColorless()) {
            return LOG_TT_COLOR_COLORLESS;
        } else if (objectColor.isRed()) {
            return LOG_TT_COLOR_RED;
        } else if (objectColor.isGreen()) {
            return LOG_TT_COLOR_GREEN;
        } else if (objectColor.isBlue()) {
            return LOG_TT_COLOR_BLUE;
        } else if (objectColor.isWhite()) {
            return LOG_TT_COLOR_WHITE;
        } else {
            return LOG_TT_COLOR_BLACK;
        }
    }
}
