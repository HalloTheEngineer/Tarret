package de.hallotheengineer.tarret.mixin;

import de.hallotheengineer.tarret.client.TarretClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    public void getWindowTitle(CallbackInfoReturnable<String> cir) {
        if (!Objects.equals(TarretClient.forcedTitle, "")) {
            cir.setReturnValue(TarretClient.forcedTitle);
        }
    }
}
