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

package mage.player.ai;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Config2 {

    private static final Logger logger = Logger.getLogger(Config2.class);

//    public static final int maxDepth;
    public static final int maxNodes;
    public static final int evaluatorLifeFactor;
    public static final int evaluatorPermanentFactor;
    public static final int evaluatorCreatureFactor;
    public static final int evaluatorHandFactor;
//    public static final int maxThinkSeconds;

    static {
        Properties p = new Properties();
        try {
            File file = new File(Config2.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            File propertiesFile = new File(file.getParent() + File.separator + "AIMinimax.properties");
            if (propertiesFile.exists()) {
                p.load(new FileInputStream(propertiesFile));
            } else {
//                p.setProperty("maxDepth", "10");
                p.setProperty("maxNodes", "50000");
                p.setProperty("evaluatorLifeFactor", "2");
                p.setProperty("evaluatorPermanentFactor", "1");
                p.setProperty("evaluatorCreatureFactor", "1");
                p.setProperty("evaluatorHandFactor", "1");
//                p.setProperty("maxThinkSeconds", "30");
            }
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (URISyntaxException ex) {
            logger.error(null, ex);
        }
//        maxDepth = Integer.parseInt(p.getProperty("maxDepth"));
        maxNodes = Integer.parseInt(p.getProperty("maxNodes"));
        evaluatorLifeFactor = Integer.parseInt(p.getProperty("evaluatorLifeFactor"));
        evaluatorPermanentFactor = Integer.parseInt(p.getProperty("evaluatorPermanentFactor"));
        evaluatorCreatureFactor = Integer.parseInt(p.getProperty("evaluatorCreatureFactor"));
        evaluatorHandFactor = Integer.parseInt(p.getProperty("evaluatorHandFactor"));
//        maxThinkSeconds = Integer.parseInt(p.getProperty("maxThinkSeconds"));
    }

}
