package ihh.keylist;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@EventBusSubscriber(value = Dist.CLIENT, modid = "keylist", bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    static void setup(FMLLoadCompleteEvent e) {
        e.enqueueWork(() -> {
            if (ClientConfig.PRINT_KEYS.get()) {
                LOGGER.debug("Keys:");
                KeyMapping.ALL.keySet().stream().sorted().forEach(LOGGER::debug);
            }
            if (ClientConfig.PRINT_CATEGORIES.get()) {
                LOGGER.debug("Categories:");
                KeyMapping.CATEGORY_SORT_ORDER.keySet().stream().sorted().forEach(LOGGER::debug);
            }
            for (Map.Entry<KeyMapping, String> key : ClientConfig.getKeysToChange().entrySet()) {
                key.getKey().category = key.getValue();
            }
            KeyMapping.CATEGORY_SORT_ORDER.putAll(ClientConfig.getNewCategoryIndices());
            KeyMapping.CATEGORIES.clear();
            KeyMapping.CATEGORIES.addAll(KeyMapping.CATEGORY_SORT_ORDER.keySet());
        });
    }
}
