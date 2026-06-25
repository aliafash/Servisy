package androidx.compose.material3;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.tokens.ListTokens;
import androidx.compose.material3.tokens.TypographyKeyTokens;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.AlignmentLineKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.MultiContentMeasurePolicy;
import androidx.compose.ui.layout.MultiContentMeasurePolicyKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.profileinstaller.ProfileVerifier;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: ListItem.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a©\u0001\u0010\u0016\u001a\u00020\u00172\u0011\u0010\u0018\u001a\r\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\u0015\b\u0002\u0010\u001d\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0015\b\u0002\u0010\u001e\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0015\b\u0002\u0010\u001f\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0015\b\u0002\u0010 \u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\u00012\b\b\u0002\u0010$\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001at\u0010'\u001a\u00020\u00172\u0013\u0010(\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0013\u0010)\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0011\u0010*\u001a\r\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001a2\u0013\u0010+\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001a2\u0013\u0010,\u001a\u000f\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019¢\u0006\u0002\b\u001aH\u0003¢\u0006\u0002\u0010-\u001a5\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0011\u00103\u001a\r\u0012\u0004\u0012\u00020\u00170\u0019¢\u0006\u0002\b\u001aH\u0003ø\u0001\u0000¢\u0006\u0004\b4\u00105\u001a`\u00106\u001a\u000207*\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\b\u0010;\u001a\u0004\u0018\u00010:2\b\u0010<\u001a\u0004\u0018\u00010:2\b\u0010=\u001a\u0004\u0018\u00010:2\b\u0010>\u001a\u0004\u0018\u00010:2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002ø\u0001\u0000¢\u0006\u0004\bE\u0010F\u001a`\u0010G\u001a\u000207*\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\b\u0010;\u001a\u0004\u0018\u00010:2\b\u0010<\u001a\u0004\u0018\u00010:2\b\u0010=\u001a\u0004\u0018\u00010:2\b\u0010>\u001a\u0004\u0018\u00010:2\u0006\u0010H\u001a\u00020I2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002ø\u0001\u0000¢\u0006\u0004\bJ\u0010K\u001af\u0010L\u001a\u00020M*\u0002082\u0006\u0010N\u001a\u0002072\u0006\u0010O\u001a\u0002072\b\u00109\u001a\u0004\u0018\u00010:2\b\u0010;\u001a\u0004\u0018\u00010:2\b\u0010<\u001a\u0004\u0018\u00010:2\b\u0010=\u001a\u0004\u0018\u00010:2\b\u0010>\u001a\u0004\u0018\u00010:2\u0006\u0010P\u001a\u00020Q2\u0006\u0010H\u001a\u00020I2\u0006\u0010A\u001a\u00020BH\u0002\"\u001e\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001e\u0010\u0007\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\b\u0010\u0003\u001a\u0004\b\t\u0010\u0005\"\u001e\u0010\n\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\u000b\u0010\u0003\u001a\u0004\b\f\u0010\u0005\"\u001e\u0010\r\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\u000e\u0010\u0003\u001a\u0004\b\u000f\u0010\u0005\"\u001e\u0010\u0010\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\u0011\u0010\u0003\u001a\u0004\b\u0012\u0010\u0005\"\u001e\u0010\u0013\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0010\n\u0002\u0010\u0006\u0012\u0004\b\u0014\u0010\u0003\u001a\u0004\b\u0015\u0010\u0005\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006R"}, d2 = {"LeadingContentEndPadding", "Landroidx/compose/ui/unit/Dp;", "getLeadingContentEndPadding$annotations", "()V", "getLeadingContentEndPadding", "()F", "F", "ListItemEndPadding", "getListItemEndPadding$annotations", "getListItemEndPadding", "ListItemStartPadding", "getListItemStartPadding$annotations", "getListItemStartPadding", "ListItemThreeLineVerticalPadding", "getListItemThreeLineVerticalPadding$annotations", "getListItemThreeLineVerticalPadding", "ListItemVerticalPadding", "getListItemVerticalPadding$annotations", "getListItemVerticalPadding", "TrailingContentStartPadding", "getTrailingContentStartPadding$annotations", "getTrailingContentStartPadding", "ListItem", "", "headlineContent", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "modifier", "Landroidx/compose/ui/Modifier;", "overlineContent", "supportingContent", "leadingContent", "trailingContent", "colors", "Landroidx/compose/material3/ListItemColors;", "tonalElevation", "shadowElevation", "ListItem-HXNGIdc", "(Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/material3/ListItemColors;FFLandroidx/compose/runtime/Composer;II)V", "ListItemLayout", "leading", "trailing", "headline", "overline", "supporting", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;I)V", "ProvideTextStyleFromToken", "color", "Landroidx/compose/ui/graphics/Color;", "textToken", "Landroidx/compose/material3/tokens/TypographyKeyTokens;", "content", "ProvideTextStyleFromToken-3J-VO9M", "(JLandroidx/compose/material3/tokens/TypographyKeyTokens;Lkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;I)V", "calculateHeight", "", "Landroidx/compose/ui/layout/MeasureScope;", "leadingPlaceable", "Landroidx/compose/ui/layout/Placeable;", "trailingPlaceable", "headlinePlaceable", "overlinePlaceable", "supportingPlaceable", "listItemType", "Landroidx/compose/material3/ListItemType;", "paddingValues", "Landroidx/compose/foundation/layout/PaddingValues;", "constraints", "Landroidx/compose/ui/unit/Constraints;", "calculateHeight-N4Jib3Y", "(Landroidx/compose/ui/layout/MeasureScope;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;ILandroidx/compose/foundation/layout/PaddingValues;J)I", "calculateWidth", "layoutDirection", "Landroidx/compose/ui/unit/LayoutDirection;", "calculateWidth-xygx4p4", "(Landroidx/compose/ui/layout/MeasureScope;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/layout/Placeable;Landroidx/compose/ui/unit/LayoutDirection;Landroidx/compose/foundation/layout/PaddingValues;J)I", "place", "Landroidx/compose/ui/layout/MeasureResult;", "width", "height", "isThreeLine", "", "material3_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class ListItemKt {
    private static final float ListItemVerticalPadding = Dp.m5733constructorimpl(8);
    private static final float ListItemThreeLineVerticalPadding = Dp.m5733constructorimpl(12);
    private static final float ListItemStartPadding = Dp.m5733constructorimpl(16);
    private static final float ListItemEndPadding = Dp.m5733constructorimpl(16);
    private static final float LeadingContentEndPadding = Dp.m5733constructorimpl(16);
    private static final float TrailingContentStartPadding = Dp.m5733constructorimpl(16);

    public static /* synthetic */ void getLeadingContentEndPadding$annotations() {
    }

    public static /* synthetic */ void getListItemEndPadding$annotations() {
    }

    public static /* synthetic */ void getListItemStartPadding$annotations() {
    }

    public static /* synthetic */ void getListItemThreeLineVerticalPadding$annotations() {
    }

    public static /* synthetic */ void getListItemVerticalPadding$annotations() {
    }

    public static /* synthetic */ void getTrailingContentStartPadding$annotations() {
    }

    /* JADX INFO: renamed from: ListItem-HXNGIdc, reason: not valid java name */
    public static final void m1638ListItemHXNGIdc(final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Function2<? super Composer, ? super Integer, Unit> function24, Function2<? super Composer, ? super Integer, Unit> function25, ListItemColors colors, float tonalElevation, float shadowElevation, Composer $composer, final int $changed, final int i) {
        Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Function2<? super Composer, ? super Integer, Unit> function28;
        int i2;
        int i3;
        float f;
        Modifier.Companion modifier2;
        Function2<? super Composer, ? super Integer, Unit> function29;
        int i4;
        int i5;
        final ListItemColors colors2;
        float tonalElevation2;
        float shadowElevation2;
        Function2<? super Composer, ? super Integer, Unit> function210;
        Function2 supportingContent;
        Function2<? super Composer, ? super Integer, Unit> function211;
        Function2 function2ComposableLambda;
        Function2 function2ComposableLambda2;
        Function2 function2ComposableLambda3;
        Function2<? super Composer, ? super Integer, Unit> function212;
        Function2<? super Composer, ? super Integer, Unit> function213;
        Modifier modifier3;
        Function2<? super Composer, ? super Integer, Unit> function214;
        float tonalElevation3;
        float shadowElevation3;
        ListItemColors colors3;
        Function2<? super Composer, ? super Integer, Unit> function215;
        Composer $composer2 = $composer.startRestartGroup(-1647707763);
        ComposerKt.sourceInformation($composer2, "C(ListItem)P(1,3,4,6,2,8!1,7:c#ui.unit.Dp,5:c#ui.unit.Dp)89@4308L8,144@6169L5,140@6019L637:ListItem.kt#uh7d8r");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(function2) ? 4 : 2;
        }
        int i6 = i & 2;
        if (i6 != 0) {
            $dirty |= 48;
        } else if (($changed & 48) == 0) {
            $dirty |= $composer2.changed(modifier) ? 32 : 16;
        }
        int i7 = i & 4;
        if (i7 != 0) {
            $dirty |= 384;
            function26 = function22;
        } else if (($changed & 384) == 0) {
            function26 = function22;
            $dirty |= $composer2.changedInstance(function26) ? 256 : 128;
        } else {
            function26 = function22;
        }
        int i8 = i & 8;
        if (i8 != 0) {
            $dirty |= 3072;
            function27 = function23;
        } else if (($changed & 3072) == 0) {
            function27 = function23;
            $dirty |= $composer2.changedInstance(function27) ? 2048 : 1024;
        } else {
            function27 = function23;
        }
        int i9 = i & 16;
        if (i9 != 0) {
            $dirty |= 24576;
            function28 = function24;
        } else if (($changed & 24576) == 0) {
            function28 = function24;
            $dirty |= $composer2.changedInstance(function28) ? 16384 : 8192;
        } else {
            function28 = function24;
        }
        int i10 = i & 32;
        if (i10 != 0) {
            $dirty |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
        } else if ((196608 & $changed) == 0) {
            $dirty |= $composer2.changedInstance(function25) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty |= ((i & 64) == 0 && $composer2.changed(colors)) ? 1048576 : 524288;
        }
        int i11 = i & 128;
        if (i11 != 0) {
            $dirty |= 12582912;
            i2 = i11;
        } else if (($changed & 12582912) == 0) {
            i2 = i11;
            $dirty |= $composer2.changed(tonalElevation) ? 8388608 : 4194304;
        } else {
            i2 = i11;
        }
        int i12 = i & 256;
        if (i12 != 0) {
            $dirty |= 100663296;
            i3 = i12;
            f = shadowElevation;
        } else if (($changed & 100663296) == 0) {
            i3 = i12;
            f = shadowElevation;
            $dirty |= $composer2.changed(f) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        } else {
            i3 = i12;
            f = shadowElevation;
        }
        if (($dirty & 38347923) == 38347922 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier3 = modifier;
            function214 = function25;
            colors3 = colors;
            tonalElevation3 = tonalElevation;
            function212 = function27;
            function215 = function28;
            shadowElevation3 = f;
            function213 = function26;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                modifier2 = i6 != 0 ? Modifier.INSTANCE : modifier;
                if (i7 != 0) {
                    function26 = null;
                }
                if (i8 != 0) {
                    function27 = null;
                }
                if (i9 != 0) {
                    function28 = null;
                }
                function29 = i10 != 0 ? null : function25;
                if ((i & 64) != 0) {
                    i4 = i2;
                    i5 = i3;
                    colors2 = ListItemDefaults.INSTANCE.m1636colorsJ08w3E(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer2, 805306368, FrameMetricsAggregator.EVERY_DURATION);
                    $dirty &= -3670017;
                } else {
                    i4 = i2;
                    i5 = i3;
                    colors2 = colors;
                }
                tonalElevation2 = i4 != 0 ? ListItemDefaults.INSTANCE.m1637getElevationD9Ej5fM() : tonalElevation;
                shadowElevation2 = i5 != 0 ? ListItemDefaults.INSTANCE.m1637getElevationD9Ej5fM() : shadowElevation;
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 64) != 0) {
                    $dirty &= -3670017;
                }
                modifier2 = modifier;
                function29 = function25;
                colors2 = colors;
                tonalElevation2 = tonalElevation;
                shadowElevation2 = f;
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1647707763, $dirty, -1, "androidx.compose.material3.ListItem (ListItem.kt:92)");
            }
            final Function2 decoratedHeadlineContent = ComposableLambdaKt.composableLambda($composer2, -403249643, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$decoratedHeadlineContent$1
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
                    ComposerKt.sourceInformation($composer3, "C94@4498L160:ListItem.kt#uh7d8r");
                    if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-403249643, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous> (ListItem.kt:94)");
                        }
                        ListItemKt.m1639ProvideTextStyleFromToken3JVO9M(colors2.m1631headlineColorvNxB06k$material3_release(true), ListTokens.INSTANCE.getListItemLabelTextFont(), function2, $composer3, 48);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            });
            if (function27 != null) {
                final Function2<? super Composer, ? super Integer, Unit> function216 = function27;
                function210 = function27;
                supportingContent = ComposableLambdaKt.composableLambda($composer2, -1020860251, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$decoratedSupportingContent$1$1
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
                        ComposerKt.sourceInformation($composer3, "C102@4776L156:ListItem.kt#uh7d8r");
                        if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-1020860251, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous>.<anonymous> (ListItem.kt:102)");
                            }
                            ListItemKt.m1639ProvideTextStyleFromToken3JVO9M(colors2.m1634supportingColor0d7_KjU$material3_release(), ListTokens.INSTANCE.getListItemSupportingTextFont(), function216, $composer3, 48);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                });
            } else {
                function210 = function27;
                supportingContent = null;
            }
            final Function2 decoratedSupportingContent = supportingContent;
            if (function26 != null) {
                final Function2<? super Composer, ? super Integer, Unit> function217 = function26;
                function211 = function26;
                function2ComposableLambda = ComposableLambdaKt.composableLambda($composer2, -764441232, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$decoratedOverlineContent$1$1
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
                        ComposerKt.sourceInformation($composer3, "C111@5056L148:ListItem.kt#uh7d8r");
                        if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-764441232, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous>.<anonymous> (ListItem.kt:111)");
                            }
                            ListItemKt.m1639ProvideTextStyleFromToken3JVO9M(colors2.m1633overlineColor0d7_KjU$material3_release(), ListTokens.INSTANCE.getListItemOverlineFont(), function217, $composer3, 48);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                });
            } else {
                function211 = function26;
                function2ComposableLambda = null;
            }
            final Function2 decoratedOverlineContent = function2ComposableLambda;
            if (function28 != null) {
                final Function2<? super Composer, ? super Integer, Unit> function218 = function28;
                function2ComposableLambda2 = ComposableLambdaKt.composableLambda($composer2, 1400509200, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$decoratedLeadingContent$1$1
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
                        Function0<ComposeUiNode> function0;
                        ComposerKt.sourceInformation($composer3, "C120@5326L250:ListItem.kt#uh7d8r");
                        if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(1400509200, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous>.<anonymous> (ListItem.kt:120)");
                            }
                            Modifier modifier$iv = PaddingKt.m566paddingqDBjuR0$default(Modifier.INSTANCE, 0.0f, 0.0f, ListItemKt.getLeadingContentEndPadding(), 0.0f, 11, null);
                            ListItemColors listItemColors = colors2;
                            Function2<Composer, Integer, Unit> function219 = function218;
                            $composer3.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
                            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                            int $changed$iv$iv = (6 << 3) & 112;
                            $composer3.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                            CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
                            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                            if (!($composer3.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer3.startReusableNode();
                            if ($composer3.getInserting()) {
                                function0 = constructor;
                                $composer3.createNode(function0);
                            } else {
                                function0 = constructor;
                                $composer3.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer3);
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
                            }
                            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i13 = ($changed$iv$iv$iv >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i14 = ((6 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, 2011888581, "C121@5398L164:ListItem.kt#uh7d8r");
                            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.getLocalContentColor().provides(Color.m3396boximpl(listItemColors.m1632leadingIconColorvNxB06k$material3_release(true))), function219, $composer3, ProvidedValue.$stable | 0);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                });
            } else {
                function2ComposableLambda2 = null;
            }
            final Function2 decoratedLeadingContent = function2ComposableLambda2;
            if (function29 != null) {
                final Function2<? super Composer, ? super Integer, Unit> function219 = function29;
                function2ComposableLambda3 = ComposableLambdaKt.composableLambda($composer2, 1512306332, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$decoratedTrailingContent$1$1
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
                        ComposerKt.sourceInformation($composer3, "C130@5700L297:ListItem.kt#uh7d8r");
                        if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(1512306332, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous>.<anonymous> (ListItem.kt:130)");
                            }
                            Modifier modifier$iv = PaddingKt.m566paddingqDBjuR0$default(Modifier.INSTANCE, ListItemKt.getTrailingContentStartPadding(), 0.0f, 0.0f, 0.0f, 14, null);
                            ListItemColors listItemColors = colors2;
                            Function2<Composer, Integer, Unit> function220 = function219;
                            $composer3.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
                            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                            int $changed$iv$iv = (6 << 3) & 112;
                            $composer3.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                            CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
                            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                            if (!($composer3.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer3.startReusableNode();
                            if ($composer3.getInserting()) {
                                $composer3.createNode(constructor);
                            } else {
                                $composer3.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer3);
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
                            }
                            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i13 = ($changed$iv$iv$iv >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i14 = ((6 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, 2011888960, "C131@5777L206:ListItem.kt#uh7d8r");
                            ListItemKt.m1639ProvideTextStyleFromToken3JVO9M(listItemColors.m1635trailingIconColorvNxB06k$material3_release(true), ListTokens.INSTANCE.getListItemTrailingSupportingTextFont(), function220, $composer3, 48);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                });
            } else {
                function2ComposableLambda3 = null;
            }
            final Function2 decoratedTrailingContent = function2ComposableLambda3;
            SurfaceKt.m1976SurfaceT9BRK9s(SemanticsModifierKt.semantics(Modifier.INSTANCE, true, new Function1<SemanticsPropertyReceiver, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                    invoke2(semanticsPropertyReceiver);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SemanticsPropertyReceiver $this$semantics) {
                }
            }).then(modifier2), ListItemDefaults.INSTANCE.getShape($composer2, 6), colors2.getContainerColor(), colors2.m1631headlineColorvNxB06k$material3_release(true), tonalElevation2, shadowElevation2, null, ComposableLambdaKt.composableLambda($composer2, 1502590376, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$2
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
                    ComposerKt.sourceInformation($composer3, "C150@6378L272:ListItem.kt#uh7d8r");
                    if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1502590376, $changed2, -1, "androidx.compose.material3.ListItem.<anonymous> (ListItem.kt:150)");
                        }
                        ListItemKt.ListItemLayout(decoratedLeadingContent, decoratedTrailingContent, decoratedHeadlineContent, decoratedOverlineContent, decoratedSupportingContent, $composer3, 384);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), $composer2, (($dirty >> 9) & 57344) | 12582912 | (458752 & ($dirty >> 9)), 64);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function212 = function210;
            function213 = function211;
            modifier3 = modifier2;
            function214 = function29;
            tonalElevation3 = tonalElevation2;
            shadowElevation3 = shadowElevation2;
            colors3 = colors2;
            function215 = function28;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier4 = modifier3;
            final Function2<? super Composer, ? super Integer, Unit> function220 = function213;
            final Function2<? super Composer, ? super Integer, Unit> function221 = function212;
            final Function2<? super Composer, ? super Integer, Unit> function222 = function215;
            final Function2<? super Composer, ? super Integer, Unit> function223 = function214;
            final ListItemColors listItemColors = colors3;
            final float f2 = tonalElevation3;
            final float f3 = shadowElevation3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ListItem$3
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
                    ListItemKt.m1638ListItemHXNGIdc(function2, modifier4, function220, function221, function222, function223, listItemColors, f2, f3, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ListItemLayout(final Function2<? super Composer, ? super Integer, Unit> function2, final Function2<? super Composer, ? super Integer, Unit> function22, final Function2<? super Composer, ? super Integer, Unit> function23, final Function2<? super Composer, ? super Integer, Unit> function24, final Function2<? super Composer, ? super Integer, Unit> function25, Composer $composer, final int $changed) {
        MultiContentMeasurePolicy value$iv;
        Object value$iv$iv$iv;
        Function0<ComposeUiNode> function0;
        Composer $composer2 = $composer.startRestartGroup(2052297037);
        ComposerKt.sourceInformation($composer2, "C(ListItemLayout)P(1,4)168@6954L7,177@7152L3807,169@6966L3993:ListItem.kt#uh7d8r");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(function2) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer2.changedInstance(function22) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer2.changedInstance(function23) ? 256 : 128;
        }
        if (($changed & 3072) == 0) {
            $dirty |= $composer2.changedInstance(function24) ? 2048 : 1024;
        }
        if (($changed & 24576) == 0) {
            $dirty |= $composer2.changedInstance(function25) ? 16384 : 8192;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 9363) == 9362 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(2052297037, $dirty2, -1, "androidx.compose.material3.ListItemLayout (ListItem.kt:167)");
            }
            ProvidableCompositionLocal<LayoutDirection> localLayoutDirection = CompositionLocalsKt.getLocalLayoutDirection();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localLayoutDirection);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final LayoutDirection layoutDirection = (LayoutDirection) objConsume;
            Function2[] function2Arr = new Function2[5];
            function2Arr[0] = function23;
            function2Arr[1] = function24 == null ? ComposableSingletons$ListItemKt.INSTANCE.m1421getLambda1$material3_release() : function24;
            function2Arr[2] = function25 == null ? ComposableSingletons$ListItemKt.INSTANCE.m1422getLambda2$material3_release() : function25;
            function2Arr[3] = function2 == null ? ComposableSingletons$ListItemKt.INSTANCE.m1423getLambda3$material3_release() : function2;
            function2Arr[4] = function22 == null ? ComposableSingletons$ListItemKt.INSTANCE.m1424getLambda4$material3_release() : function22;
            List contents$iv = CollectionsKt.listOf((Object[]) function2Arr);
            $composer2.startReplaceableGroup(1361340338);
            ComposerKt.sourceInformation($composer2, "CC(remember):ListItem.kt#9igjgp");
            boolean invalid$iv = $composer2.changed(layoutDirection);
            Object it$iv = $composer2.rememberedValue();
            if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = new MultiContentMeasurePolicy() { // from class: androidx.compose.material3.ListItemKt$ListItemLayout$1$1
                    @Override // androidx.compose.ui.layout.MultiContentMeasurePolicy
                    /* JADX INFO: renamed from: measure-3p2s80s, reason: not valid java name */
                    public final MeasureResult mo1645measure3p2s80s(MeasureScope $this$Layout, List<? extends List<? extends Measurable>> list, long constraints) {
                        List<? extends Measurable> list2 = list.get(0);
                        List<? extends Measurable> list3 = list.get(1);
                        List<? extends Measurable> list4 = list.get(2);
                        List<? extends Measurable> list5 = list.get(3);
                        List<? extends Measurable> list6 = list.get(4);
                        long jM5679copyZbe2FdA = Constraints.m5679copyZbe2FdA(constraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(constraints) : 0, (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(constraints) : 0, (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(constraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(constraints) : 0);
                        float arg0$iv = ListItemKt.getListItemStartPadding();
                        float other$iv = ListItemKt.getListItemEndPadding();
                        int i = -$this$Layout.mo307roundToPx0680j_4(Dp.m5733constructorimpl(arg0$iv + other$iv));
                        float arg0$iv2 = ListItemKt.getListItemVerticalPadding();
                        long looseConstraints = ConstraintsKt.m5705offsetNN6EwU(jM5679copyZbe2FdA, i, -$this$Layout.mo307roundToPx0680j_4(Dp.m5733constructorimpl(2 * arg0$iv2)));
                        Measurable measurable = (Measurable) CollectionsKt.firstOrNull((List) list5);
                        Placeable leadingPlaceable = measurable != null ? measurable.mo4677measureBRTryo0(looseConstraints) : null;
                        int currentTotalWidth = TextFieldImplKt.widthOrZero(leadingPlaceable) + 0;
                        Measurable measurable2 = (Measurable) CollectionsKt.firstOrNull((List) list6);
                        Placeable trailingPlaceable = measurable2 != null ? measurable2.mo4677measureBRTryo0(ConstraintsKt.m5706offsetNN6EwU$default(looseConstraints, -currentTotalWidth, 0, 2, null)) : null;
                        int currentTotalWidth2 = currentTotalWidth + TextFieldImplKt.widthOrZero(trailingPlaceable);
                        Measurable measurable3 = (Measurable) CollectionsKt.firstOrNull((List) list2);
                        Placeable headlinePlaceable = measurable3 != null ? measurable3.mo4677measureBRTryo0(ConstraintsKt.m5706offsetNN6EwU$default(looseConstraints, -currentTotalWidth2, 0, 2, null)) : null;
                        int currentTotalHeight = 0 + TextFieldImplKt.heightOrZero(headlinePlaceable);
                        Measurable measurable4 = (Measurable) CollectionsKt.firstOrNull((List) list4);
                        Placeable supportingPlaceable = measurable4 != null ? measurable4.mo4677measureBRTryo0(ConstraintsKt.m5705offsetNN6EwU(looseConstraints, -currentTotalWidth2, -currentTotalHeight)) : null;
                        int currentTotalHeight2 = currentTotalHeight + TextFieldImplKt.heightOrZero(supportingPlaceable);
                        boolean isSupportingMultiline = (supportingPlaceable == null || supportingPlaceable.get(AlignmentLineKt.getFirstBaseline()) == supportingPlaceable.get(AlignmentLineKt.getLastBaseline())) ? false : true;
                        Measurable measurable5 = (Measurable) CollectionsKt.firstOrNull((List) list3);
                        Placeable overlinePlaceable = measurable5 != null ? measurable5.mo4677measureBRTryo0(ConstraintsKt.m5705offsetNN6EwU(looseConstraints, -currentTotalWidth2, -currentTotalHeight2)) : null;
                        int listItemType = ListItemType.INSTANCE.m1655getListItemTypeZLSjz4$material3_release(overlinePlaceable != null, supportingPlaceable != null, isSupportingMultiline);
                        boolean isThreeLine = ListItemType.m1650equalsimpl0(listItemType, ListItemType.INSTANCE.m1657getThreeLineAlXitO8());
                        PaddingValues paddingValues = PaddingKt.m558PaddingValuesa9UjIt4(ListItemKt.getListItemStartPadding(), isThreeLine ? ListItemKt.getListItemThreeLineVerticalPadding() : ListItemKt.getListItemVerticalPadding(), ListItemKt.getListItemEndPadding(), isThreeLine ? ListItemKt.getListItemThreeLineVerticalPadding() : ListItemKt.getListItemVerticalPadding());
                        int width = ListItemKt.m1644calculateWidthxygx4p4($this$Layout, leadingPlaceable, trailingPlaceable, headlinePlaceable, overlinePlaceable, supportingPlaceable, layoutDirection, paddingValues, constraints);
                        int height = ListItemKt.m1643calculateHeightN4Jib3Y($this$Layout, leadingPlaceable, trailingPlaceable, headlinePlaceable, overlinePlaceable, supportingPlaceable, listItemType, paddingValues, constraints);
                        return ListItemKt.place($this$Layout, width, height, leadingPlaceable, trailingPlaceable, headlinePlaceable, overlinePlaceable, supportingPlaceable, isThreeLine, layoutDirection, paddingValues);
                    }
                };
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            MultiContentMeasurePolicy measurePolicy$iv = (MultiContentMeasurePolicy) value$iv;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(1399185516);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)171@6874L62,168@6760L182:Layout.kt#80mrfh");
            Modifier modifier$iv = Modifier.INSTANCE;
            Function2<Composer, Integer, Unit> function2CombineAsVirtualLayouts = LayoutKt.combineAsVirtualLayouts(contents$iv);
            int i = (0 >> 6) & 14;
            $composer2.startReplaceableGroup(1157296644);
            ComposerKt.sourceInformation($composer2, "CC(remember)P(1):Composables.kt#9igjgp");
            boolean invalid$iv$iv$iv = $composer2.changed(measurePolicy$iv);
            Object it$iv$iv$iv = $composer2.rememberedValue();
            if (invalid$iv$iv$iv || it$iv$iv$iv == Composer.INSTANCE.getEmpty()) {
                value$iv$iv$iv = MultiContentMeasurePolicyKt.createMeasurePolicy(measurePolicy$iv);
                $composer2.updateRememberedValue(value$iv$iv$iv);
            } else {
                value$iv$iv$iv = it$iv$iv$iv;
            }
            $composer2.endReplaceableGroup();
            MeasurePolicy measurePolicy$iv$iv = (MeasurePolicy) value$iv$iv$iv;
            int $changed$iv$iv = 0 & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
            int $changed$iv = $changed$iv$iv << 9;
            int $changed$iv$iv$iv = ($changed$iv & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                function0 = constructor;
                $composer2.createNode(function0);
            } else {
                function0 = constructor;
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
            }
            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            function2CombineAsVirtualLayouts.invoke($composer2, Integer.valueOf(($changed$iv$iv$iv >> 9) & 14));
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt.ListItemLayout.2
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
                    ListItemKt.ListItemLayout(function2, function22, function23, function24, function25, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: calculateWidth-xygx4p4, reason: not valid java name */
    public static final int m1644calculateWidthxygx4p4(MeasureScope $this$calculateWidth_u2dxygx4p4, Placeable leadingPlaceable, Placeable trailingPlaceable, Placeable headlinePlaceable, Placeable overlinePlaceable, Placeable supportingPlaceable, LayoutDirection layoutDirection, PaddingValues paddingValues, long constraints) {
        if (Constraints.m5685getHasBoundedWidthimpl(constraints)) {
            return Constraints.m5689getMaxWidthimpl(constraints);
        }
        float arg0$iv = paddingValues.mo513calculateLeftPaddingu2uoSUM(layoutDirection);
        float other$iv = paddingValues.mo514calculateRightPaddingu2uoSUM(layoutDirection);
        int horizontalPadding = $this$calculateWidth_u2dxygx4p4.mo307roundToPx0680j_4(Dp.m5733constructorimpl(arg0$iv + other$iv));
        int mainContentWidth = Math.max(TextFieldImplKt.widthOrZero(headlinePlaceable), Math.max(TextFieldImplKt.widthOrZero(overlinePlaceable), TextFieldImplKt.widthOrZero(supportingPlaceable)));
        return TextFieldImplKt.widthOrZero(leadingPlaceable) + horizontalPadding + mainContentWidth + TextFieldImplKt.widthOrZero(trailingPlaceable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: calculateHeight-N4Jib3Y, reason: not valid java name */
    public static final int m1643calculateHeightN4Jib3Y(MeasureScope $this$calculateHeight_u2dN4Jib3Y, Placeable leadingPlaceable, Placeable trailingPlaceable, Placeable headlinePlaceable, Placeable overlinePlaceable, Placeable supportingPlaceable, int listItemType, PaddingValues paddingValues, long constraints) {
        float defaultMinHeight;
        if (ListItemType.m1650equalsimpl0(listItemType, ListItemType.INSTANCE.m1656getOneLineAlXitO8())) {
            defaultMinHeight = ListTokens.INSTANCE.m2610getListItemOneLineContainerHeightD9Ej5fM();
        } else {
            defaultMinHeight = ListItemType.m1650equalsimpl0(listItemType, ListItemType.INSTANCE.m1658getTwoLineAlXitO8()) ? ListTokens.INSTANCE.m2614getListItemTwoLineContainerHeightD9Ej5fM() : ListTokens.INSTANCE.m2612getListItemThreeLineContainerHeightD9Ej5fM();
        }
        int minHeight = Math.max(Constraints.m5690getMinHeightimpl(constraints), $this$calculateHeight_u2dN4Jib3Y.mo307roundToPx0680j_4(defaultMinHeight));
        float arg0$iv = paddingValues.getTop();
        float other$iv = paddingValues.getBottom();
        float arg0$iv2 = Dp.m5733constructorimpl(arg0$iv + other$iv);
        int mainContentHeight = TextFieldImplKt.heightOrZero(headlinePlaceable) + TextFieldImplKt.heightOrZero(overlinePlaceable) + TextFieldImplKt.heightOrZero(supportingPlaceable);
        return RangesKt.coerceAtMost(Math.max(minHeight, $this$calculateHeight_u2dN4Jib3Y.mo307roundToPx0680j_4(arg0$iv2) + Math.max(TextFieldImplKt.heightOrZero(leadingPlaceable), Math.max(mainContentHeight, TextFieldImplKt.heightOrZero(trailingPlaceable)))), Constraints.m5688getMaxHeightimpl(constraints));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MeasureResult place(final MeasureScope $this$place, final int width, final int height, final Placeable leadingPlaceable, final Placeable trailingPlaceable, final Placeable headlinePlaceable, final Placeable overlinePlaceable, final Placeable supportingPlaceable, final boolean isThreeLine, final LayoutDirection layoutDirection, final PaddingValues paddingValues) {
        return MeasureScope.layout$default($this$place, width, height, null, new Function1<Placeable.PlacementScope, Unit>() { // from class: androidx.compose.material3.ListItemKt.place.1
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
                int currentY;
                int startPadding = $this$place.mo307roundToPx0680j_4(PaddingKt.calculateStartPadding(paddingValues, layoutDirection));
                int endPadding = $this$place.mo307roundToPx0680j_4(PaddingKt.calculateEndPadding(paddingValues, layoutDirection));
                int topPadding = $this$place.mo307roundToPx0680j_4(paddingValues.getTop());
                Placeable it = leadingPlaceable;
                if (it != null) {
                    Placeable.PlacementScope.placeRelative$default($this$layout, it, startPadding, isThreeLine ? topPadding : Alignment.INSTANCE.getCenterVertically().align(it.getHeight(), height), 0.0f, 4, null);
                }
                Placeable it2 = trailingPlaceable;
                if (it2 != null) {
                    Placeable.PlacementScope.placeRelative$default($this$layout, it2, (width - endPadding) - it2.getWidth(), isThreeLine ? topPadding : Alignment.INSTANCE.getCenterVertically().align(it2.getHeight(), height), 0.0f, 4, null);
                }
                int mainContentX = TextFieldImplKt.widthOrZero(leadingPlaceable) + startPadding;
                if (isThreeLine) {
                    currentY = topPadding;
                } else {
                    int totalHeight = TextFieldImplKt.heightOrZero(headlinePlaceable) + TextFieldImplKt.heightOrZero(overlinePlaceable) + TextFieldImplKt.heightOrZero(supportingPlaceable);
                    currentY = Alignment.INSTANCE.getCenterVertically().align(totalHeight, height);
                }
                Placeable placeable = overlinePlaceable;
                if (placeable != null) {
                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable, mainContentX, currentY, 0.0f, 4, null);
                }
                int currentY2 = currentY + TextFieldImplKt.heightOrZero(overlinePlaceable);
                Placeable placeable2 = headlinePlaceable;
                if (placeable2 != null) {
                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable2, mainContentX, currentY2, 0.0f, 4, null);
                }
                int currentY3 = currentY2 + TextFieldImplKt.heightOrZero(headlinePlaceable);
                Placeable placeable3 = supportingPlaceable;
                if (placeable3 != null) {
                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable3, mainContentX, currentY3, 0.0f, 4, null);
                }
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: ProvideTextStyleFromToken-3J-VO9M, reason: not valid java name */
    public static final void m1639ProvideTextStyleFromToken3JVO9M(final long color, final TypographyKeyTokens textToken, final Function2<? super Composer, ? super Integer, Unit> function2, Composer $composer, final int $changed) {
        Composer $composer2 = $composer.startRestartGroup(1133967795);
        ComposerKt.sourceInformation($composer2, "C(ProvideTextStyleFromToken)P(0:c#ui.graphics.Color,2)520@20833L10,518@20747L142:ListItem.kt#uh7d8r");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changed(color) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer2.changed(textToken) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer2.changedInstance(function2) ? 256 : 128;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 147) != 146 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1133967795, $dirty2, -1, "androidx.compose.material3.ProvideTextStyleFromToken (ListItem.kt:518)");
            }
            ProvideContentColorTextStyleKt.m1763ProvideContentColorTextStyle3JVO9M(color, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), textToken), function2, $composer2, ($dirty2 & 14) | ($dirty2 & 896));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ListItemKt$ProvideTextStyleFromToken$1
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

                public final void invoke(Composer composer, int i) {
                    ListItemKt.m1639ProvideTextStyleFromToken3JVO9M(color, textToken, function2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    public static final float getListItemVerticalPadding() {
        return ListItemVerticalPadding;
    }

    public static final float getListItemThreeLineVerticalPadding() {
        return ListItemThreeLineVerticalPadding;
    }

    public static final float getListItemStartPadding() {
        return ListItemStartPadding;
    }

    public static final float getListItemEndPadding() {
        return ListItemEndPadding;
    }

    public static final float getLeadingContentEndPadding() {
        return LeadingContentEndPadding;
    }

    public static final float getTrailingContentStartPadding() {
        return TrailingContentStartPadding;
    }
}
