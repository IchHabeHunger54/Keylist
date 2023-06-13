package ihh.keylist;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Keylist.MOD_ID)
public class Keylist {
    public static final String MOD_ID = "keylist";
    public static final Logger LOGGER = LogManager.getLogger();

    public Keylist() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
