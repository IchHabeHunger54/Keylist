package ihh.keylist;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.Map;

@EventBusSubscriber(value = Dist.CLIENT, modid = Keylist.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    static void setup(FMLLoadCompleteEvent e) {
        e.enqueueWork(() -> {
            if (ClientConfig.PRINT_KEYS.get()) {
                Keylist.LOGGER.info("Keys:");
                KeyMapping.ALL.keySet().stream().sorted().forEach(Keylist.LOGGER::info);
            }
            if (ClientConfig.PRINT_CATEGORIES.get()) {
                Keylist.LOGGER.info("Categories:");
                KeyMapping.CATEGORY_SORT_ORDER.keySet().stream().sorted().forEach(Keylist.LOGGER::info);
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
