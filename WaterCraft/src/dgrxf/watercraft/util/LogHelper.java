package dgrxf.watercraft.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import dgrxf.watercraft.lib.ModInfo;


/*
 * Lol nice job copying the class right out of EE3, you could at least change the header xD
 * 
 * No, at least the LGPL License has to stay! -Robotic
 * 
 * We're in skype if you'd like to join us -xandayn
 * 
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class LogHelper {

    private static Logger logger = Logger.getLogger(ModInfo.MODID);

    public static void init() {

        logger.setParent(FMLLog.getLogger());
    }

    public static void log(Level logLevel, Object object) {

        logger.log(logLevel, object.toString());
    }

    public static void severe(Object object) {

        log(Level.SEVERE, object.toString());
    }

    public static void debug(Object object) {

        log(Level.WARNING, "[DEBUG] " + object.toString());
    }

    public static void warning(Object object) {

        log(Level.WARNING, object.toString());
    }

    public static void info(Object object) {

        log(Level.INFO, object.toString());
    }

    public static void config(Object object) {

        log(Level.CONFIG, object.toString());
    }

    public static void fine(Object object) {

        log(Level.FINE, object.toString());
    }

    public static void finer(Object object) {

        log(Level.FINER, object.toString());
    }

    public static void finest(Object object) {

        log(Level.FINEST, object.toString());
    }
}
