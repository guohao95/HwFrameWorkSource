package android.service.autofill;

import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc implements Consumer {
    public static final /* synthetic */ -$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc INSTANCE = new -$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc();

    private /* synthetic */ -$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc() {
    }

    public final void accept(Object obj) {
        ((AutofillService) obj).onConnected();
    }
}
