package su.a71.tardim_ic.tardim_ic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "tardim_ic";
    public static final String MOD_NAME = "TARDIM: In Control";
    public static final Logger LOG;
    public static final Gson GSON;

    static {
        LOG = LoggerFactory.getLogger(MOD_NAME);
        GSON = (new GsonBuilder()).setPrettyPrinting().create();
    }
}