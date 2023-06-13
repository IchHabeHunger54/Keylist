package ihh.keylist.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ihh.keylist.ClientConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;
import java.util.List;

@Mixin(KeyBindsList.class)
public class KeyBindsListMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/ArrayUtils;clone([Ljava/lang/Object;)[Ljava/lang/Object;"), method = "<init>")
    private static Object[] filterKeysToHide(Object[] array, Operation<KeyMapping[]> original) {
        List<String> hide = ClientConfig.getKeysToHide();
        return Arrays.stream(array).filter(e -> !hide.contains(((KeyMapping) e).getName())).map(e -> (KeyMapping) e).toList().toArray(new KeyMapping[0]);
    }
}
