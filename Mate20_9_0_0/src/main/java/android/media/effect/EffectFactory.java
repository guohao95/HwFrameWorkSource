package android.media.effect;

public class EffectFactory {
    public static final String EFFECT_AUTOFIX = "android.media.effect.effects.AutoFixEffect";
    public static final String EFFECT_BACKDROPPER = "android.media.effect.effects.BackDropperEffect";
    public static final String EFFECT_BITMAPOVERLAY = "android.media.effect.effects.BitmapOverlayEffect";
    public static final String EFFECT_BLACKWHITE = "android.media.effect.effects.BlackWhiteEffect";
    public static final String EFFECT_BRIGHTNESS = "android.media.effect.effects.BrightnessEffect";
    public static final String EFFECT_CONTRAST = "android.media.effect.effects.ContrastEffect";
    public static final String EFFECT_CROP = "android.media.effect.effects.CropEffect";
    public static final String EFFECT_CROSSPROCESS = "android.media.effect.effects.CrossProcessEffect";
    public static final String EFFECT_DOCUMENTARY = "android.media.effect.effects.DocumentaryEffect";
    public static final String EFFECT_DUOTONE = "android.media.effect.effects.DuotoneEffect";
    public static final String EFFECT_FILLLIGHT = "android.media.effect.effects.FillLightEffect";
    public static final String EFFECT_FISHEYE = "android.media.effect.effects.FisheyeEffect";
    public static final String EFFECT_FLIP = "android.media.effect.effects.FlipEffect";
    public static final String EFFECT_GRAIN = "android.media.effect.effects.GrainEffect";
    public static final String EFFECT_GRAYSCALE = "android.media.effect.effects.GrayscaleEffect";
    public static final String EFFECT_IDENTITY = "IdentityEffect";
    public static final String EFFECT_LOMOISH = "android.media.effect.effects.LomoishEffect";
    public static final String EFFECT_NEGATIVE = "android.media.effect.effects.NegativeEffect";
    private static final String[] EFFECT_PACKAGES = new String[]{"android.media.effect.effects.", ""};
    public static final String EFFECT_POSTERIZE = "android.media.effect.effects.PosterizeEffect";
    public static final String EFFECT_REDEYE = "android.media.effect.effects.RedEyeEffect";
    public static final String EFFECT_ROTATE = "android.media.effect.effects.RotateEffect";
    public static final String EFFECT_SATURATE = "android.media.effect.effects.SaturateEffect";
    public static final String EFFECT_SEPIA = "android.media.effect.effects.SepiaEffect";
    public static final String EFFECT_SHARPEN = "android.media.effect.effects.SharpenEffect";
    public static final String EFFECT_STRAIGHTEN = "android.media.effect.effects.StraightenEffect";
    public static final String EFFECT_TEMPERATURE = "android.media.effect.effects.ColorTemperatureEffect";
    public static final String EFFECT_TINT = "android.media.effect.effects.TintEffect";
    public static final String EFFECT_VIGNETTE = "android.media.effect.effects.VignetteEffect";
    private EffectContext mEffectContext;

    EffectFactory(EffectContext effectContext) {
        this.mEffectContext = effectContext;
    }

    public Effect createEffect(String effectName) {
        Class effectClass = getEffectClassByName(effectName);
        if (effectClass != null) {
            return instantiateEffect(effectClass, effectName);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot instantiate unknown effect '");
        stringBuilder.append(effectName);
        stringBuilder.append("'!");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static boolean isEffectSupported(String effectName) {
        return getEffectClassByName(effectName) != null;
    }

    private static Class getEffectClassByName(String className) {
        Class effectClass = null;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String[] strArr = EFFECT_PACKAGES;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String packageName = strArr[i];
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(packageName);
                stringBuilder.append(className);
                effectClass = contextClassLoader.loadClass(stringBuilder.toString());
                if (effectClass != null) {
                    break;
                }
                i++;
            } catch (ClassNotFoundException e) {
            }
        }
        return effectClass;
    }

    private Effect instantiateEffect(Class effectClass, String name) {
        try {
            effectClass.asSubclass(Effect.class);
            Effect effectConstructor = null;
            try {
                try {
                    return (Effect) effectClass.getConstructor(new Class[]{EffectContext.class, String.class}).newInstance(new Object[]{this.mEffectContext, name});
                } catch (Throwable t) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("There was an error constructing the effect '");
                    stringBuilder.append(effectClass);
                    stringBuilder.append("'!");
                    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString(), t);
                }
            } catch (NoSuchMethodException e) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("The effect class '");
                stringBuilder2.append(effectClass);
                stringBuilder2.append("' does not have the required constructor.");
                throw new RuntimeException(stringBuilder2.toString(), e);
            }
        } catch (ClassCastException e2) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Attempting to allocate effect '");
            stringBuilder3.append(effectClass);
            stringBuilder3.append("' which is not a subclass of Effect!");
            throw new IllegalArgumentException(stringBuilder3.toString(), e2);
        }
    }
}
