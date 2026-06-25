package androidx.compose.material3;

import androidx.autofill.HintConstants;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeLayoutKt;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.profileinstaller.ProfileVerifier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: BottomSheetScaffold.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u001a\u008a\u0002\u0010\u0000\u001a\u00020\u00012\u001c\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0005¢\u0006\u0002\b\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\f2\u0015\b\u0002\u0010\u0015\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0016¢\u0006\u0002\b\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u0015\b\u0002\u0010\u0019\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0016¢\u0006\u0002\b\u00052\u0019\b\u0002\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00112\b\b\u0002\u0010\u001d\u001a\u00020\u00112\u0017\u0010\u001e\u001a\u0013\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001aÀ\u0001\u0010\"\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0013\u0010\u0019\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0016¢\u0006\u0002\b\u00052&\u0010#\u001a\"\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u00052&\u0010'\u001a\"\u0012\u0013\u0012\u00110(¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u00052\u0011\u0010\u001a\u001a\r\u0012\u0004\u0012\u00020\u00010\u0016¢\u0006\u0002\b\u00052\u0006\u0010\u000b\u001a\u00020\f2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00162\u0006\u0010,\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011H\u0003ø\u0001\u0000¢\u0006\u0004\b.\u0010/\u001a¶\u0001\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020-2'\u00102\u001a#\u0012\u0013\u0012\u001103¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(4\u0012\n\u0012\b\u0012\u0004\u0012\u000206050\u00032\u0006\u00107\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u00108\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u00109\u001a\u00020\f2\u0006\u0010:\u001a\u00020\f2\u0013\u0010;\u001a\u000f\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0016¢\u0006\u0002\b\u00052\u001c\u0010\u001e\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0005¢\u0006\u0002\b\u0006H\u0003ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a!\u0010>\u001a\u00020\n2\b\b\u0002\u0010?\u001a\u00020-2\b\b\u0002\u0010@\u001a\u00020\u001bH\u0007¢\u0006\u0002\u0010A\u001a7\u0010B\u001a\u00020-2\b\b\u0002\u0010C\u001a\u0002062\u0014\b\u0002\u0010D\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0002\u0010E\u001a\u00020\u0018H\u0007¢\u0006\u0002\u0010F\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006G"}, d2 = {"BottomSheetScaffold", "", "sheetContent", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "modifier", "Landroidx/compose/ui/Modifier;", "scaffoldState", "Landroidx/compose/material3/BottomSheetScaffoldState;", "sheetPeekHeight", "Landroidx/compose/ui/unit/Dp;", "sheetMaxWidth", "sheetShape", "Landroidx/compose/ui/graphics/Shape;", "sheetContainerColor", "Landroidx/compose/ui/graphics/Color;", "sheetContentColor", "sheetTonalElevation", "sheetShadowElevation", "sheetDragHandle", "Lkotlin/Function0;", "sheetSwipeEnabled", "", "topBar", "snackbarHost", "Landroidx/compose/material3/SnackbarHostState;", "containerColor", "contentColor", "content", "Landroidx/compose/foundation/layout/PaddingValues;", "BottomSheetScaffold-sdMYb0k", "(Lkotlin/jvm/functions/Function3;Landroidx/compose/ui/Modifier;Landroidx/compose/material3/BottomSheetScaffoldState;FFLandroidx/compose/ui/graphics/Shape;JJFFLkotlin/jvm/functions/Function2;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;JJLkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;III)V", "BottomSheetScaffoldLayout", "body", "Lkotlin/ParameterName;", HintConstants.AUTOFILL_HINT_NAME, "innerPadding", "bottomSheet", "", "layoutHeight", "sheetOffset", "", "sheetState", "Landroidx/compose/material3/SheetState;", "BottomSheetScaffoldLayout-PxNyym8", "(Landroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function2;FLkotlin/jvm/functions/Function0;Landroidx/compose/material3/SheetState;JJLandroidx/compose/runtime/Composer;I)V", "StandardBottomSheet", "state", "calculateAnchors", "Landroidx/compose/ui/unit/IntSize;", "sheetSize", "Landroidx/compose/material3/DraggableAnchors;", "Landroidx/compose/material3/SheetValue;", "peekHeight", "shape", "tonalElevation", "shadowElevation", "dragHandle", "StandardBottomSheet-XcniZvE", "(Landroidx/compose/material3/SheetState;Lkotlin/jvm/functions/Function1;FFZLandroidx/compose/ui/graphics/Shape;JJFFLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;II)V", "rememberBottomSheetScaffoldState", "bottomSheetState", "snackbarHostState", "(Landroidx/compose/material3/SheetState;Landroidx/compose/material3/SnackbarHostState;Landroidx/compose/runtime/Composer;II)Landroidx/compose/material3/BottomSheetScaffoldState;", "rememberStandardBottomSheetState", "initialValue", "confirmValueChange", "skipHiddenState", "(Landroidx/compose/material3/SheetValue;Lkotlin/jvm/functions/Function1;ZLandroidx/compose/runtime/Composer;II)Landroidx/compose/material3/SheetState;", "material3_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class BottomSheetScaffoldKt {
    /* JADX INFO: renamed from: BottomSheetScaffold-sdMYb0k, reason: not valid java name */
    public static final void m1250BottomSheetScaffoldsdMYb0k(final Function3<? super ColumnScope, ? super Composer, ? super Integer, Unit> function3, Modifier modifier, BottomSheetScaffoldState scaffoldState, float sheetPeekHeight, float sheetMaxWidth, Shape sheetShape, long sheetContainerColor, long sheetContentColor, float sheetTonalElevation, float sheetShadowElevation, Function2<? super Composer, ? super Integer, Unit> function2, boolean sheetSwipeEnabled, Function2<? super Composer, ? super Integer, Unit> function22, Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function32, long containerColor, long contentColor, final Function3<? super PaddingValues, ? super Composer, ? super Integer, Unit> function33, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        BottomSheetScaffoldState scaffoldState2;
        Shape sheetShape2;
        float sheetPeekHeight2;
        int i2;
        long sheetContainerColor2;
        long sheetContentColor2;
        float sheetTonalElevation2;
        int $dirty;
        int $dirty1;
        long containerColor2;
        float sheetPeekHeight3;
        int $dirty2;
        long contentColor2;
        long containerColor3;
        Function2<? super Composer, ? super Integer, Unit> function23;
        boolean sheetSwipeEnabled2;
        Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function34;
        int $dirty12;
        float sheetMaxWidth2;
        long sheetContentColor3;
        Modifier modifier3;
        final BottomSheetScaffoldState scaffoldState3;
        float sheetShadowElevation2;
        Function2<? super Composer, ? super Integer, Unit> function24;
        long sheetContainerColor3;
        Modifier modifier4;
        Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function35;
        Object value$iv;
        Modifier modifier5;
        Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function36;
        float sheetMaxWidth3;
        BottomSheetScaffoldState scaffoldState4;
        float sheetPeekHeight4;
        Shape sheetShape3;
        long sheetContainerColor4;
        Function2<? super Composer, ? super Integer, Unit> function25;
        long containerColor4;
        long contentColor3;
        float sheetShadowElevation3;
        float sheetTonalElevation3;
        Function2<? super Composer, ? super Integer, Unit> function26;
        boolean sheetSwipeEnabled3;
        long sheetContentColor4;
        Composer $composer2 = $composer.startRestartGroup(-1523924135);
        ComposerKt.sourceInformation($composer2, "C(BottomSheetScaffold)P(6,3,4,10:c#ui.unit.Dp,9:c#ui.unit.Dp,12,5:c#ui.graphics.Color,7:c#ui.graphics.Color,14:c#ui.unit.Dp,11:c#ui.unit.Dp,8,13,16,15,0:c#ui.graphics.Color,2:c#ui.graphics.Color)103@5431L34,106@5633L13,107@5701L14,108@5748L36,115@6205L11,116@6252L31,*119@6379L7,130@6700L50,122@6436L1811:BottomSheetScaffold.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(function3) ? 4 : 2;
        }
        int i3 = i & 2;
        if (i3 != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changed(modifier) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty3 |= ((i & 4) == 0 && $composer2.changed(scaffoldState)) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
        } else if (($changed & 3072) == 0) {
            $dirty3 |= $composer2.changed(sheetPeekHeight) ? 2048 : 1024;
        }
        int i5 = i & 16;
        int i6 = 8192;
        if (i5 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changed(sheetMaxWidth) ? 16384 : 8192;
        }
        if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty3 |= ((i & 32) == 0 && $composer2.changed(sheetShape)) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty3 |= ((i & 64) == 0 && $composer2.changed(sheetContainerColor)) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            $dirty3 |= ((i & 128) == 0 && $composer2.changed(sheetContentColor)) ? 8388608 : 4194304;
        }
        int i7 = i & 256;
        if (i7 != 0) {
            $dirty3 |= 100663296;
        } else if (($changed & 100663296) == 0) {
            $dirty3 |= $composer2.changed(sheetTonalElevation) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i8 = i & 512;
        if (i8 != 0) {
            $dirty3 |= 805306368;
        } else if (($changed & 805306368) == 0) {
            $dirty3 |= $composer2.changed(sheetShadowElevation) ? 536870912 : 268435456;
        }
        int i9 = i & 1024;
        if (i9 != 0) {
            $dirty13 |= 6;
        } else if (($changed1 & 6) == 0) {
            $dirty13 |= $composer2.changedInstance(function2) ? 4 : 2;
        }
        int i10 = i & 2048;
        if (i10 != 0) {
            $dirty13 |= 48;
        } else if (($changed1 & 48) == 0) {
            $dirty13 |= $composer2.changed(sheetSwipeEnabled) ? 32 : 16;
        }
        int i11 = i & 4096;
        if (i11 != 0) {
            $dirty13 |= 384;
        } else if (($changed1 & 384) == 0) {
            $dirty13 |= $composer2.changedInstance(function22) ? 256 : 128;
        }
        int i12 = i & 8192;
        if (i12 != 0) {
            $dirty13 |= 3072;
        } else if (($changed1 & 3072) == 0) {
            $dirty13 |= $composer2.changedInstance(function32) ? 2048 : 1024;
        }
        if (($changed1 & 24576) == 0) {
            if ((i & 16384) == 0 && $composer2.changed(containerColor)) {
                i6 = 16384;
            }
            $dirty13 |= i6;
        }
        if (($changed1 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty13 |= ((i & 32768) == 0 && $composer2.changed(contentColor)) ? 131072 : 65536;
        }
        if ((i & 65536) != 0) {
            $dirty13 |= 1572864;
        } else if (($changed1 & 1572864) == 0) {
            $dirty13 |= $composer2.changedInstance(function33) ? 1048576 : 524288;
        }
        if (($dirty3 & 306783379) == 306783378 && (599187 & $dirty13) == 599186 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier5 = modifier;
            scaffoldState4 = scaffoldState;
            sheetPeekHeight4 = sheetPeekHeight;
            sheetMaxWidth3 = sheetMaxWidth;
            sheetShape3 = sheetShape;
            sheetContainerColor4 = sheetContainerColor;
            sheetContentColor4 = sheetContentColor;
            sheetTonalElevation3 = sheetTonalElevation;
            sheetShadowElevation3 = sheetShadowElevation;
            function26 = function2;
            sheetSwipeEnabled3 = sheetSwipeEnabled;
            function25 = function22;
            function36 = function32;
            containerColor4 = containerColor;
            contentColor3 = contentColor;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier6 = i3 != 0 ? Modifier.INSTANCE : modifier;
                if ((i & 4) != 0) {
                    modifier2 = modifier6;
                    scaffoldState2 = rememberBottomSheetScaffoldState(null, null, $composer2, 0, 3);
                    $dirty3 &= -897;
                } else {
                    modifier2 = modifier6;
                    scaffoldState2 = scaffoldState;
                }
                float sheetPeekHeight5 = i4 != 0 ? BottomSheetDefaults.INSTANCE.m1249getSheetPeekHeightD9Ej5fM() : sheetPeekHeight;
                float sheetMaxWidth4 = i5 != 0 ? BottomSheetDefaults.INSTANCE.m1248getSheetMaxWidthD9Ej5fM() : sheetMaxWidth;
                BottomSheetScaffoldState scaffoldState5 = scaffoldState2;
                if ((i & 32) != 0) {
                    sheetShape2 = BottomSheetDefaults.INSTANCE.getExpandedShape($composer2, 6);
                    $dirty3 &= -458753;
                } else {
                    sheetShape2 = sheetShape;
                }
                if ((i & 64) != 0) {
                    sheetPeekHeight2 = sheetPeekHeight5;
                    $dirty3 &= -3670017;
                    i2 = i7;
                    sheetContainerColor2 = BottomSheetDefaults.INSTANCE.getContainerColor($composer2, 6);
                } else {
                    sheetPeekHeight2 = sheetPeekHeight5;
                    i2 = i7;
                    sheetContainerColor2 = sheetContainerColor;
                }
                float sheetMaxWidth5 = sheetMaxWidth4;
                if ((i & 128) != 0) {
                    sheetContentColor2 = ColorSchemeKt.m1392contentColorForek8zF_U(sheetContainerColor2, $composer2, ($dirty3 >> 18) & 14);
                    $dirty3 &= -29360129;
                } else {
                    sheetContentColor2 = sheetContentColor;
                }
                sheetTonalElevation2 = i2 != 0 ? BottomSheetDefaults.INSTANCE.m1247getElevationD9Ej5fM() : sheetTonalElevation;
                float sheetShadowElevation4 = i8 != 0 ? BottomSheetDefaults.INSTANCE.m1247getElevationD9Ej5fM() : sheetShadowElevation;
                Function2<? super Composer, ? super Integer, Unit> function2M1414getLambda1$material3_release = i9 != 0 ? ComposableSingletons$BottomSheetScaffoldKt.INSTANCE.m1414getLambda1$material3_release() : function2;
                boolean sheetSwipeEnabled4 = i10 != 0 ? true : sheetSwipeEnabled;
                Function2<? super Composer, ? super Integer, Unit> function27 = i11 != 0 ? null : function22;
                Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function3M1415getLambda2$material3_release = i12 != 0 ? ComposableSingletons$BottomSheetScaffoldKt.INSTANCE.m1415getLambda2$material3_release() : function32;
                if ((i & 16384) != 0) {
                    $dirty = $dirty3;
                    $dirty1 = $dirty13 & (-57345);
                    containerColor2 = MaterialTheme.INSTANCE.getColorScheme($composer2, 6).getSurface();
                } else {
                    $dirty = $dirty3;
                    $dirty1 = $dirty13;
                    containerColor2 = containerColor;
                }
                if ((i & 32768) != 0) {
                    long sheetContainerColor5 = sheetContainerColor2;
                    $dirty2 = $dirty;
                    containerColor3 = containerColor2;
                    contentColor2 = ColorSchemeKt.m1392contentColorForek8zF_U(containerColor2, $composer2, ($dirty1 >> 12) & 14);
                    function23 = function27;
                    sheetSwipeEnabled2 = sheetSwipeEnabled4;
                    function34 = function3M1415getLambda2$material3_release;
                    $dirty12 = $dirty1 & (-458753);
                    sheetMaxWidth2 = sheetMaxWidth5;
                    sheetContentColor3 = sheetContentColor2;
                    modifier3 = modifier2;
                    scaffoldState3 = scaffoldState5;
                    sheetPeekHeight3 = sheetPeekHeight2;
                    sheetShadowElevation2 = sheetShadowElevation4;
                    function24 = function2M1414getLambda1$material3_release;
                    sheetContainerColor3 = sheetContainerColor5;
                } else {
                    long sheetContainerColor6 = sheetContainerColor2;
                    sheetPeekHeight3 = sheetPeekHeight2;
                    $dirty2 = $dirty;
                    contentColor2 = contentColor;
                    containerColor3 = containerColor2;
                    function23 = function27;
                    sheetSwipeEnabled2 = sheetSwipeEnabled4;
                    function34 = function3M1415getLambda2$material3_release;
                    $dirty12 = $dirty1;
                    sheetMaxWidth2 = sheetMaxWidth5;
                    sheetContentColor3 = sheetContentColor2;
                    modifier3 = modifier2;
                    scaffoldState3 = scaffoldState5;
                    sheetShadowElevation2 = sheetShadowElevation4;
                    function24 = function2M1414getLambda1$material3_release;
                    sheetContainerColor3 = sheetContainerColor6;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 4) != 0) {
                    $dirty3 &= -897;
                }
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                }
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 16384) != 0) {
                    $dirty13 &= -57345;
                }
                if ((i & 32768) != 0) {
                    $dirty13 &= -458753;
                }
                sheetPeekHeight3 = sheetPeekHeight;
                sheetMaxWidth2 = sheetMaxWidth;
                sheetShape2 = sheetShape;
                sheetContainerColor3 = sheetContainerColor;
                sheetContentColor3 = sheetContentColor;
                sheetTonalElevation2 = sheetTonalElevation;
                sheetShadowElevation2 = sheetShadowElevation;
                function24 = function2;
                sheetSwipeEnabled2 = sheetSwipeEnabled;
                function23 = function22;
                function34 = function32;
                containerColor3 = containerColor;
                contentColor2 = contentColor;
                $dirty2 = $dirty3;
                $dirty12 = $dirty13;
                modifier3 = modifier;
                scaffoldState3 = scaffoldState;
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                modifier4 = modifier3;
                function35 = function34;
                ComposerKt.traceEventStart(-1523924135, $dirty2, $dirty12, "androidx.compose.material3.BottomSheetScaffold (BottomSheetScaffold.kt:118)");
            } else {
                modifier4 = modifier3;
                function35 = function34;
            }
            ProvidableCompositionLocal<Density> localDensity = CompositionLocalsKt.getLocalDensity();
            int $dirty14 = $dirty12;
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localDensity);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            Density $this$BottomSheetScaffold_sdMYb0k_u24lambda_u240 = (Density) objConsume;
            final int peekHeightPx = $this$BottomSheetScaffold_sdMYb0k_u24lambda_u240.mo307roundToPx0680j_4(sheetPeekHeight3);
            SheetState bottomSheetState = scaffoldState3.getBottomSheetState();
            final BottomSheetScaffoldState bottomSheetScaffoldState = scaffoldState3;
            final float f = sheetPeekHeight3;
            final float f2 = sheetMaxWidth2;
            final boolean z = sheetSwipeEnabled2;
            final Shape shape = sheetShape2;
            final long j = sheetContainerColor3;
            final long j2 = sheetContentColor3;
            final float f3 = sheetTonalElevation2;
            final float f4 = sheetShadowElevation2;
            final Function2<? super Composer, ? super Integer, Unit> function28 = function24;
            float sheetMaxWidth6 = sheetMaxWidth2;
            ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer2, -680109608, true, new Function3<Integer, Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Composer composer, Integer num2) {
                    invoke(num.intValue(), composer, num2.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(final int layoutHeight, Composer $composer3, int $changed2) {
                    Object value$iv2;
                    ComposerKt.sourceInformation($composer3, "C140@7195L686,135@6935L1296:BottomSheetScaffold.kt#uh7d8r");
                    int $dirty4 = $changed2;
                    if (($changed2 & 6) == 0) {
                        $dirty4 |= $composer3.changed(layoutHeight) ? 4 : 2;
                    }
                    int $dirty5 = $dirty4;
                    if (($dirty5 & 19) != 18 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-680109608, $dirty5, -1, "androidx.compose.material3.BottomSheetScaffold.<anonymous> (BottomSheetScaffold.kt:135)");
                        }
                        SheetState bottomSheetState2 = bottomSheetScaffoldState.getBottomSheetState();
                        $composer3.startReplaceableGroup(1237684821);
                        ComposerKt.sourceInformation($composer3, "CC(remember):BottomSheetScaffold.kt#9igjgp");
                        boolean invalid$iv = $composer3.changed(bottomSheetScaffoldState) | (($dirty5 & 14) == 4) | $composer3.changed(peekHeightPx);
                        final BottomSheetScaffoldState bottomSheetScaffoldState2 = bottomSheetScaffoldState;
                        final int i13 = peekHeightPx;
                        Object it$iv = $composer3.rememberedValue();
                        if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
                            value$iv2 = new Function1<IntSize, DraggableAnchors<SheetValue>>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ DraggableAnchors<SheetValue> invoke(IntSize intSize) {
                                    return m1255invokeozmzZPI(intSize.getPackedValue());
                                }

                                /* JADX INFO: renamed from: invoke-ozmzZPI, reason: not valid java name */
                                public final DraggableAnchors<SheetValue> m1255invokeozmzZPI(long sheetSize) {
                                    final int sheetHeight = IntSize.m5902getHeightimpl(sheetSize);
                                    final BottomSheetScaffoldState bottomSheetScaffoldState3 = bottomSheetScaffoldState2;
                                    final int i14 = layoutHeight;
                                    final int i15 = i13;
                                    return AnchoredDraggableKt.DraggableAnchors(new Function1<DraggableAnchorsConfig<SheetValue>, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$1$1$1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        public /* bridge */ /* synthetic */ Unit invoke(DraggableAnchorsConfig<SheetValue> draggableAnchorsConfig) {
                                            invoke2(draggableAnchorsConfig);
                                            return Unit.INSTANCE;
                                        }

                                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2(DraggableAnchorsConfig<SheetValue> draggableAnchorsConfig) {
                                            if (!bottomSheetScaffoldState3.getBottomSheetState().getSkipPartiallyExpanded()) {
                                                draggableAnchorsConfig.at(SheetValue.PartiallyExpanded, i14 - i15);
                                            }
                                            if (sheetHeight != i15) {
                                                draggableAnchorsConfig.at(SheetValue.Expanded, Math.max(i14 - sheetHeight, 0));
                                            }
                                            if (!bottomSheetScaffoldState3.getBottomSheetState().getSkipHiddenState()) {
                                                draggableAnchorsConfig.at(SheetValue.Hidden, i14);
                                            }
                                        }
                                    });
                                }
                            };
                            $composer3.updateRememberedValue(value$iv2);
                        } else {
                            value$iv2 = it$iv;
                        }
                        $composer3.endReplaceableGroup();
                        BottomSheetScaffoldKt.m1252StandardBottomSheetXcniZvE(bottomSheetState2, (Function1) value$iv2, f, f2, z, shape, j, j2, f3, f4, function28, function3, $composer3, 0, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            });
            final Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function37 = function35;
            long sheetContainerColor7 = sheetContainerColor3;
            boolean invalid$iv = true;
            ComposableLambda composableLambda2 = ComposableLambdaKt.composableLambda($composer2, 88659390, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    ComposerKt.sourceInformation($composer3, "C127@6578L45:BottomSheetScaffold.kt#uh7d8r");
                    if (($changed2 & 3) == 2 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(88659390, $changed2, -1, "androidx.compose.material3.BottomSheetScaffold.<anonymous> (BottomSheetScaffold.kt:127)");
                    }
                    function37.invoke(scaffoldState3.getSnackbarHostState(), $composer3, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            });
            $composer2.startReplaceableGroup(-1567544379);
            ComposerKt.sourceInformation($composer2, "CC(remember):BottomSheetScaffold.kt#9igjgp");
            if (((($dirty2 & 896) ^ 384) <= 256 || !$composer2.changed(scaffoldState3)) && ($dirty2 & 384) != 256) {
                invalid$iv = false;
            }
            long sheetContentColor5 = sheetContentColor3;
            Object it$iv = $composer2.rememberedValue();
            if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = (Function0) new Function0<Float>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$3$1
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Float invoke() {
                        return Float.valueOf(scaffoldState3.getBottomSheetState().requireOffset());
                    }
                };
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            $composer2.endReplaceableGroup();
            m1251BottomSheetScaffoldLayoutPxNyym8(modifier4, function23, function33, composableLambda, composableLambda2, sheetPeekHeight3, (Function0) value$iv, bottomSheetState, containerColor3, contentColor2, $composer2, (($dirty2 >> 3) & 14) | 27648 | (($dirty14 >> 3) & 112) | (($dirty14 >> 12) & 896) | (($dirty2 << 6) & 458752) | (($dirty14 << 12) & 234881024) | (($dirty14 << 12) & 1879048192));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier5 = modifier4;
            function36 = function35;
            sheetMaxWidth3 = sheetMaxWidth6;
            scaffoldState4 = scaffoldState3;
            sheetPeekHeight4 = sheetPeekHeight3;
            sheetShape3 = sheetShape2;
            sheetContainerColor4 = sheetContainerColor7;
            function25 = function23;
            containerColor4 = containerColor3;
            contentColor3 = contentColor2;
            sheetShadowElevation3 = sheetShadowElevation2;
            sheetTonalElevation3 = sheetTonalElevation2;
            function26 = function24;
            sheetSwipeEnabled3 = sheetSwipeEnabled2;
            sheetContentColor4 = sheetContentColor5;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier7 = modifier5;
            final BottomSheetScaffoldState bottomSheetScaffoldState2 = scaffoldState4;
            final float f5 = sheetPeekHeight4;
            final float f6 = sheetMaxWidth3;
            final Shape shape2 = sheetShape3;
            final long j3 = sheetContainerColor4;
            final long j4 = sheetContentColor4;
            final float f7 = sheetTonalElevation3;
            final float f8 = sheetShadowElevation3;
            final Function2<? super Composer, ? super Integer, Unit> function29 = function26;
            final boolean z2 = sheetSwipeEnabled3;
            final Function2<? super Composer, ? super Integer, Unit> function210 = function25;
            final Function3<? super SnackbarHostState, ? super Composer, ? super Integer, Unit> function38 = function36;
            final long j5 = containerColor4;
            final long j6 = contentColor3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffold$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i13) {
                    BottomSheetScaffoldKt.m1250BottomSheetScaffoldsdMYb0k(function3, modifier7, bottomSheetScaffoldState2, f5, f6, shape2, j3, j4, f7, f8, function29, z2, function210, function38, j5, j6, function33, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final BottomSheetScaffoldState rememberBottomSheetScaffoldState(SheetState bottomSheetState, SnackbarHostState snackbarHostState, Composer $composer, int $changed, int i) {
        Object value$iv;
        Object value$iv2;
        $composer.startReplaceableGroup(-1474606134);
        ComposerKt.sourceInformation($composer, "C(rememberBottomSheetScaffoldState)189@9008L34,190@9087L32,192@9161L196:BottomSheetScaffold.kt#uh7d8r");
        if ((i & 1) != 0) {
            bottomSheetState = rememberStandardBottomSheetState(null, null, false, $composer, 0, 7);
        }
        if ((i & 2) != 0) {
            $composer.startReplaceableGroup(667326536);
            ComposerKt.sourceInformation($composer, "CC(remember):BottomSheetScaffold.kt#9igjgp");
            Object it$iv = $composer.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv2 = new SnackbarHostState();
                $composer.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv;
            }
            snackbarHostState = (SnackbarHostState) value$iv2;
            $composer.endReplaceableGroup();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1474606134, $changed, -1, "androidx.compose.material3.rememberBottomSheetScaffoldState (BottomSheetScaffold.kt:191)");
        }
        $composer.startReplaceableGroup(667326610);
        ComposerKt.sourceInformation($composer, "CC(remember):BottomSheetScaffold.kt#9igjgp");
        boolean invalid$iv = (((($changed & 14) ^ 6) > 4 && $composer.changed(bottomSheetState)) || ($changed & 6) == 4) | (((($changed & 112) ^ 48) > 32 && $composer.changed(snackbarHostState)) || ($changed & 48) == 32);
        Object it$iv2 = $composer.rememberedValue();
        if (invalid$iv || it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv = new BottomSheetScaffoldState(bottomSheetState, snackbarHostState);
            $composer.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv2;
        }
        BottomSheetScaffoldState bottomSheetScaffoldState = (BottomSheetScaffoldState) value$iv;
        $composer.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        $composer.endReplaceableGroup();
        return bottomSheetScaffoldState;
    }

    public static final SheetState rememberStandardBottomSheetState(SheetValue initialValue, Function1<? super SheetValue, Boolean> function1, boolean skipHiddenState, Composer $composer, int $changed, int i) {
        $composer.startReplaceableGroup(678511581);
        ComposerKt.sourceInformation($composer, "C(rememberStandardBottomSheetState)P(1)214@9987L76:BottomSheetScaffold.kt#uh7d8r");
        if ((i & 1) != 0) {
            initialValue = SheetValue.PartiallyExpanded;
        }
        if ((i & 2) != 0) {
            Function1 confirmValueChange = new Function1<SheetValue, Boolean>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt.rememberStandardBottomSheetState.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(SheetValue it) {
                    return true;
                }
            };
            function1 = confirmValueChange;
        }
        if ((i & 4) != 0) {
            skipHiddenState = true;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(678511581, $changed, -1, "androidx.compose.material3.rememberStandardBottomSheetState (BottomSheetScaffold.kt:214)");
        }
        SheetState sheetStateRememberSheetState = SheetDefaultsKt.rememberSheetState(false, function1, initialValue, skipHiddenState, $composer, ($changed & 112) | 6 | (($changed << 6) & 896) | (($changed << 3) & 7168), 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        $composer.endReplaceableGroup();
        return sheetStateRememberSheetState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02dc  */
    /* JADX INFO: renamed from: StandardBottomSheet-XcniZvE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m1252StandardBottomSheetXcniZvE(final androidx.compose.material3.SheetState r34, final kotlin.jvm.functions.Function1<? super androidx.compose.ui.unit.IntSize, ? extends androidx.compose.material3.DraggableAnchors<androidx.compose.material3.SheetValue>> r35, final float r36, final float r37, final boolean r38, final androidx.compose.ui.graphics.Shape r39, final long r40, final long r42, final float r44, final float r45, final kotlin.jvm.functions.Function2<? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r46, final kotlin.jvm.functions.Function3<? super androidx.compose.foundation.layout.ColumnScope, ? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r47, androidx.compose.runtime.Composer r48, final int r49, final int r50) {
        /*
            Method dump skipped, instruction units count: 797
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.BottomSheetScaffoldKt.m1252StandardBottomSheetXcniZvE(androidx.compose.material3.SheetState, kotlin.jvm.functions.Function1, float, float, boolean, androidx.compose.ui.graphics.Shape, long, long, float, float, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: BottomSheetScaffoldLayout-PxNyym8, reason: not valid java name */
    public static final void m1251BottomSheetScaffoldLayoutPxNyym8(final Modifier modifier, final Function2<? super Composer, ? super Integer, Unit> function2, final Function3<? super PaddingValues, ? super Composer, ? super Integer, Unit> function3, final Function3<? super Integer, ? super Composer, ? super Integer, Unit> function32, final Function2<? super Composer, ? super Integer, Unit> function22, final float sheetPeekHeight, final Function0<Float> function0, final SheetState sheetState, final long containerColor, final long contentColor, Composer $composer, final int $changed) {
        int i;
        Composer $composer2 = $composer.startRestartGroup(-1120561936);
        ComposerKt.sourceInformation($composer2, "C(BottomSheetScaffoldLayout)P(4,9!2,8,6:c#ui.unit.Dp,5,7,2:c#ui.graphics.Color,3:c#ui.graphics.Color)334@15050L7,335@15073L44,335@15062L55,338@15139L1965,338@15122L1982:BottomSheetScaffold.kt#uh7d8r");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changed(modifier) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer2.changedInstance(function2) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer2.changedInstance(function3) ? 256 : 128;
        }
        if (($changed & 3072) == 0) {
            $dirty |= $composer2.changedInstance(function32) ? 2048 : 1024;
        }
        if (($changed & 24576) == 0) {
            $dirty |= $composer2.changedInstance(function22) ? 16384 : 8192;
        }
        if ((196608 & $changed) == 0) {
            $dirty |= $composer2.changed(sheetPeekHeight) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty |= $composer2.changedInstance(function0) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            $dirty |= $composer2.changed(sheetState) ? 8388608 : 4194304;
        }
        if (($changed & 100663296) == 0) {
            $dirty |= $composer2.changed(containerColor) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty |= $composer2.changed(contentColor) ? 536870912 : 268435456;
        }
        if (($dirty & 306783379) != 306783378 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1120561936, $dirty, -1, "androidx.compose.material3.BottomSheetScaffoldLayout (BottomSheetScaffold.kt:332)");
            }
            ProvidableCompositionLocal<Density> localDensity = CompositionLocalsKt.getLocalDensity();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localDensity);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final Density density = (Density) objConsume;
            $composer2.startReplaceableGroup(-99158096);
            ComposerKt.sourceInformation($composer2, "CC(remember):BottomSheetScaffold.kt#9igjgp");
            boolean invalid$iv = (($dirty & 29360128) == 8388608) | $composer2.changed(density);
            Object value$iv = $composer2.rememberedValue();
            if (invalid$iv || value$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = (Function0) new Function0<Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        sheetState.setDensity$material3_release(density);
                    }
                };
                $composer2.updateRememberedValue(value$iv);
            }
            $composer2.endReplaceableGroup();
            EffectsKt.SideEffect((Function0) value$iv, $composer2, 0);
            $composer2.startReplaceableGroup(-99158030);
            ComposerKt.sourceInformation($composer2, "CC(remember):BottomSheetScaffold.kt#9igjgp");
            boolean invalid$iv2 = (($dirty & 7168) == 2048) | (($dirty & 112) == 32) | (($dirty & 14) == 4) | ((234881024 & $dirty) == 67108864) | ((1879048192 & $dirty) == 536870912) | (($dirty & 896) == 256) | ((458752 & $dirty) == 131072) | ((57344 & $dirty) == 16384) | ((3670016 & $dirty) == 1048576) | ((29360128 & $dirty) == 8388608);
            Object value$iv2 = $composer2.rememberedValue();
            if (invalid$iv2 || value$iv2 == Composer.INSTANCE.getEmpty()) {
                i = 1;
                value$iv2 = new Function2<SubcomposeMeasureScope, Constraints, MeasureResult>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ MeasureResult invoke(SubcomposeMeasureScope subcomposeMeasureScope, Constraints constraints) {
                        return m1256invoke0kLqBqw(subcomposeMeasureScope, constraints.getValue());
                    }

                    /* JADX INFO: renamed from: invoke-0kLqBqw, reason: not valid java name */
                    public final MeasureResult m1256invoke0kLqBqw(SubcomposeMeasureScope $this$SubcomposeLayout, long constraints) {
                        Placeable placeableMo4677measureBRTryo0;
                        final int layoutWidth = Constraints.m5689getMaxWidthimpl(constraints);
                        final int layoutHeight = Constraints.m5688getMaxHeightimpl(constraints);
                        long looseConstraints = Constraints.m5679copyZbe2FdA(constraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(constraints) : 0, (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(constraints) : 0, (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(constraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(constraints) : 0);
                        BottomSheetScaffoldLayoutSlot bottomSheetScaffoldLayoutSlot = BottomSheetScaffoldLayoutSlot.Sheet;
                        final Function3<Integer, Composer, Integer, Unit> function33 = function32;
                        final Placeable sheetPlaceable = $this$SubcomposeLayout.subcompose(bottomSheetScaffoldLayoutSlot, ComposableLambdaKt.composableLambdaInstance(-1192048628, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1$sheetPlaceable$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                invoke(composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(Composer $composer3, int $changed2) {
                                ComposerKt.sourceInformation($composer3, "C344@15421L25:BottomSheetScaffold.kt#uh7d8r");
                                if (($changed2 & 3) == 2 && $composer3.getSkipping()) {
                                    $composer3.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-1192048628, $changed2, -1, "androidx.compose.material3.BottomSheetScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (BottomSheetScaffold.kt:344)");
                                }
                                function33.invoke(Integer.valueOf(layoutHeight), $composer3, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        })).get(0).mo4677measureBRTryo0(looseConstraints);
                        if (function2 != null) {
                            final Function2<Composer, Integer, Unit> function23 = function2;
                            placeableMo4677measureBRTryo0 = $this$SubcomposeLayout.subcompose(BottomSheetScaffoldLayoutSlot.TopBar, ComposableLambdaKt.composableLambdaInstance(-873203005, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1$topBarPlaceable$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                    invoke(composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(Composer $composer3, int $changed2) {
                                    ComposerKt.sourceInformation($composer3, "C348@15594L8:BottomSheetScaffold.kt#uh7d8r");
                                    if (($changed2 & 3) == 2 && $composer3.getSkipping()) {
                                        $composer3.skipToGroupEnd();
                                        return;
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-873203005, $changed2, -1, "androidx.compose.material3.BottomSheetScaffoldLayout.<anonymous>.<anonymous>.<anonymous>.<anonymous> (BottomSheetScaffold.kt:348)");
                                    }
                                    function23.invoke($composer3, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            })).get(0).mo4677measureBRTryo0(looseConstraints);
                        } else {
                            placeableMo4677measureBRTryo0 = null;
                        }
                        final Placeable topBarPlaceable = placeableMo4677measureBRTryo0;
                        final int topBarHeight = topBarPlaceable != null ? topBarPlaceable.getHeight() : 0;
                        long bodyConstraints = Constraints.m5679copyZbe2FdA(looseConstraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(looseConstraints) : 0, (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(looseConstraints) : 0, (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(looseConstraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(looseConstraints) : layoutHeight - topBarHeight);
                        BottomSheetScaffoldLayoutSlot bottomSheetScaffoldLayoutSlot2 = BottomSheetScaffoldLayoutSlot.Body;
                        final Modifier modifier2 = modifier;
                        final long j = containerColor;
                        final long j2 = contentColor;
                        final Function3<PaddingValues, Composer, Integer, Unit> function34 = function3;
                        final float f = sheetPeekHeight;
                        final Placeable bodyPlaceable = $this$SubcomposeLayout.subcompose(bottomSheetScaffoldLayoutSlot2, ComposableLambdaKt.composableLambdaInstance(-1459220575, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1$bodyPlaceable$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                invoke(composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(Composer $composer3, int $changed2) {
                                ComposerKt.sourceInformation($composer3, "C355@15900L194:BottomSheetScaffold.kt#uh7d8r");
                                if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-1459220575, $changed2, -1, "androidx.compose.material3.BottomSheetScaffoldLayout.<anonymous>.<anonymous>.<anonymous> (BottomSheetScaffold.kt:355)");
                                    }
                                    Modifier modifier3 = modifier2;
                                    long j3 = j;
                                    long j4 = j2;
                                    final Function3<PaddingValues, Composer, Integer, Unit> function35 = function34;
                                    final float f2 = f;
                                    SurfaceKt.m1976SurfaceT9BRK9s(modifier3, null, j3, j4, 0.0f, 0.0f, null, ComposableLambdaKt.composableLambda($composer3, 1725620860, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1$bodyPlaceable$1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        /* JADX WARN: Multi-variable type inference failed */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                            invoke(composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(Composer $composer4, int $changed3) {
                                            ComposerKt.sourceInformation($composer4, "C359@16047L45:BottomSheetScaffold.kt#uh7d8r");
                                            if (($changed3 & 3) == 2 && $composer4.getSkipping()) {
                                                $composer4.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(1725620860, $changed3, -1, "androidx.compose.material3.BottomSheetScaffoldLayout.<anonymous>.<anonymous>.<anonymous>.<anonymous> (BottomSheetScaffold.kt:359)");
                                            }
                                            function35.invoke(PaddingKt.m559PaddingValuesa9UjIt4$default(0.0f, 0.0f, 0.0f, f2, 7, null), $composer4, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer3, 12582912, 114);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                        return;
                                    }
                                    return;
                                }
                                $composer3.skipToGroupEnd();
                            }
                        })).get(0).mo4677measureBRTryo0(bodyConstraints);
                        final Placeable snackbarPlaceable = $this$SubcomposeLayout.subcompose(BottomSheetScaffoldLayoutSlot.Snackbar, function22).get(0).mo4677measureBRTryo0(looseConstraints);
                        final Function0<Float> function02 = function0;
                        final SheetState sheetState2 = sheetState;
                        return MeasureScope.layout$default($this$SubcomposeLayout, layoutWidth, layoutHeight, null, new Function1<Placeable.PlacementScope, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1.1

                            /* JADX INFO: renamed from: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$2$1$1$WhenMappings */
                            /* JADX INFO: compiled from: BottomSheetScaffold.kt */
                            @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                            public /* synthetic */ class WhenMappings {
                                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                static {
                                    int[] iArr = new int[SheetValue.values().length];
                                    try {
                                        iArr[SheetValue.PartiallyExpanded.ordinal()] = 1;
                                    } catch (NoSuchFieldError e) {
                                    }
                                    try {
                                        iArr[SheetValue.Expanded.ordinal()] = 2;
                                    } catch (NoSuchFieldError e2) {
                                    }
                                    try {
                                        iArr[SheetValue.Hidden.ordinal()] = 3;
                                    } catch (NoSuchFieldError e3) {
                                    }
                                    $EnumSwitchMapping$0 = iArr;
                                }
                            }

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(Placeable.PlacementScope placementScope) {
                                invoke2(placementScope);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(Placeable.PlacementScope $this$layout) {
                                int snackbarOffsetY;
                                int sheetOffsetY = MathKt.roundToInt(function02.invoke().floatValue());
                                int sheetOffsetX = Integer.max(0, (layoutWidth - sheetPlaceable.getWidth()) / 2);
                                int snackbarOffsetX = (layoutWidth - snackbarPlaceable.getWidth()) / 2;
                                switch (WhenMappings.$EnumSwitchMapping$0[sheetState2.getCurrentValue().ordinal()]) {
                                    case 1:
                                        snackbarOffsetY = sheetOffsetY - snackbarPlaceable.getHeight();
                                        break;
                                    case 2:
                                    case 3:
                                        snackbarOffsetY = layoutHeight - snackbarPlaceable.getHeight();
                                        break;
                                    default:
                                        throw new NoWhenBranchMatchedException();
                                }
                                Placeable.PlacementScope.placeRelative$default($this$layout, bodyPlaceable, 0, topBarHeight, 0.0f, 4, null);
                                Placeable placeable = topBarPlaceable;
                                if (placeable != null) {
                                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable, 0, 0, 0.0f, 4, null);
                                }
                                Placeable.PlacementScope.placeRelative$default($this$layout, sheetPlaceable, sheetOffsetX, sheetOffsetY, 0.0f, 4, null);
                                Placeable.PlacementScope.placeRelative$default($this$layout, snackbarPlaceable, snackbarOffsetX, snackbarOffsetY, 0.0f, 4, null);
                            }
                        }, 4, null);
                    }
                };
                $composer2.updateRememberedValue(value$iv2);
            } else {
                i = 1;
            }
            $composer2.endReplaceableGroup();
            SubcomposeLayoutKt.SubcomposeLayout(null, (Function2) value$iv2, $composer2, 0, i);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$BottomSheetScaffoldLayout$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i2) {
                    BottomSheetScaffoldKt.m1251BottomSheetScaffoldLayoutPxNyym8(modifier, function2, function3, function32, function22, sheetPeekHeight, function0, sheetState, containerColor, contentColor, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }
}
