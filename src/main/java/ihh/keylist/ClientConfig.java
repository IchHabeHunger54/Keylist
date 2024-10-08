package ihh.keylist;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientConfig {
    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> KEY_CATEGORIES;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> CATEGORY_ORDER;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> HIDE_KEYS;
    public static final ModConfigSpec.BooleanValue PRINT_KEYS;
    public static final ModConfigSpec.BooleanValue PRINT_CATEGORIES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        KEY_CATEGORIES = builder.comment("A list of \"key;category\" entries that change in which category in the Controls menu the key will appear. New categories will be created if necessary.", "To get the name of a key, use the print_keys option and find the one you want.", "If you use a custom category, you should translate it using a resource pack.").defineList("key_categories", new ArrayList<>(), () -> "", e -> true);
        CATEGORY_ORDER = builder.comment("A list of \"category;index\" entries that define the category order in the Controls menu. Vanilla defaults:", "\"key.categories.movement;1\", \"key.categories.gameplay;2\", \"key.categories.inventory;3\", \"key.categories.creative;4\", \"key.categories.multiplayer;5\", \"key.categories.ui;6\", \"key.categories.misc;7\".", "If you use a custom category, you should translate it using a resource pack.").defineList("category_order", new ArrayList<>(), () -> "", e -> true);
        HIDE_KEYS = builder.comment("A list of \"category\" entries that define the categories to be hidden from view entirely.").defineList("hide_keys", new ArrayList<>(), () -> "", e -> true);
        PRINT_KEYS = builder.comment("Prints all keys into the logs.").define("print_keys", false);
        PRINT_CATEGORIES = builder.comment("Prints all categories into the logs.").define("print_categories", false);
        SPEC = builder.build();
    }

    public static Map<KeyMapping, String> getKeysToChange() {
        Map<KeyMapping, String> map = new HashMap<>();
        for (String s : KEY_CATEGORIES.get()) {
            String[] array = s.split(";");
            if (array.length != 2) {
                Keylist.LOGGER.warn("Could not process config value {}: format is invalid", s);
                continue;
            }
            KeyMapping key = KeyMapping.ALL.get(array[0]);
            if (key != null && !array[1].isEmpty()) {
                map.put(key, array[1]);
            } else {
                Keylist.LOGGER.warn("Could not process config value: unknown key {}", array[0]);
            }
        }
        return map;
    }

    public static List<String> getKeysToHide() {
        List<String> list = new ArrayList<>();
        for (String e : HIDE_KEYS.get()) {
            if (KeyMapping.ALL.containsKey(e)) {
                list.add(e);
            } else {
                Keylist.LOGGER.warn("Could not process config value: unknown key {}", e);
            }
        }
        return list;
    }

    public static Map<String, Integer> getNewCategoryIndices() {
        Map<String, Integer> map = new HashMap<>();
        for (String s : CATEGORY_ORDER.get()) {
            String[] array = s.split(";");
            if (array.length == 2 && !array[1].isEmpty()) {
                try {
                    int index = Integer.parseInt(array[1]);
                    map.put(array[0], index);
                } catch (NumberFormatException e) {
                    Keylist.LOGGER.warn("Could not process config value {}: value \"{}\" is not a number", s, array[1]);
                }
            } else {
                Keylist.LOGGER.warn("Could not process config value {}: format is invalid", s);
            }
        }
        return map;
    }
}
