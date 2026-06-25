package androidx.compose.material3;

import android.content.res.Configuration;
import android.view.View;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.material3.Strings;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: ExposedDropdownMenu.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aQ\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00040\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\b¢\u0006\u0002\b\r¢\u0006\u0002\b\u000eH\u0007¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016H\u0003¢\u0006\u0002\u0010\u0017\u001a\"\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0019H\u0002\u001a2\u0010\u001e\u001a\u00020\n*\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00162\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 H\u0002\u001a\u000e\u0010\"\u001a\u00020\u001b*\u0004\u0018\u00010#H\u0002\u001a\f\u0010$\u001a\u00020\u001b*\u00020\u0012H\u0002\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002¨\u0006%²\u0006\f\u0010&\u001a\u0004\u0018\u00010#X\u008a\u008e\u0002²\u0006\n\u0010'\u001a\u00020\u0019X\u008a\u008e\u0002²\u0006\n\u0010(\u001a\u00020\u0019X\u008a\u008e\u0002"}, d2 = {"ExposedDropdownMenuItemHorizontalPadding", "Landroidx/compose/ui/unit/Dp;", "F", "ExposedDropdownMenuBox", "", "expanded", "", "onExpandedChange", "Lkotlin/Function1;", "modifier", "Landroidx/compose/ui/Modifier;", "content", "Landroidx/compose/material3/ExposedDropdownMenuBoxScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "(ZLkotlin/jvm/functions/Function1;Landroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;II)V", "SoftKeyboardListener", "view", "Landroid/view/View;", "density", "Landroidx/compose/ui/unit/Density;", "onKeyboardVisibilityChange", "Lkotlin/Function0;", "(Landroid/view/View;Landroidx/compose/ui/unit/Density;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "calculateMaxHeight", "", "windowBounds", "Landroidx/compose/ui/geometry/Rect;", "anchorBounds", "verticalMargin", "expandable", "expandedDescription", "", "collapsedDescription", "getAnchorBounds", "Landroidx/compose/ui/layout/LayoutCoordinates;", "getWindowBounds", "material3_release", "anchorCoordinates", "anchorWidth", "menuMaxHeight"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class ExposedDropdownMenu_androidKt {
    private static final float ExposedDropdownMenuItemHorizontalPadding = Dp.m5733constructorimpl(16);

    public static final void ExposedDropdownMenuBox(final boolean expanded, final Function1<? super Boolean, Unit> function1, Modifier modifier, final Function3<? super ExposedDropdownMenuBoxScope, ? super Composer, ? super Integer, Unit> function3, Composer $composer, final int $changed, final int i) {
        Modifier modifier2;
        Modifier modifier3;
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        String str;
        final FocusRequester focusRequester;
        final MutableIntState menuMaxHeight$delegate;
        final int verticalMargin;
        Density density;
        View view;
        int $dirty;
        String str2;
        Object value$iv5;
        Object value$iv6;
        Composer $composer2 = $composer.startRestartGroup(2067579792);
        ComposerKt.sourceInformation($composer2, "C(ExposedDropdownMenuBox)P(1,3,2)116@5545L7,117@5578L7,118@5617L7,122@5730L53,123@5807L33,124@5866L33,126@5926L29,127@5986L31,128@6049L32,130@6099L1761,166@7866L45,180@8254L59,180@8243L70:ExposedDropdownMenu.android.kt#uh7d8r");
        int $dirty2 = $changed;
        if ((i & 1) != 0) {
            $dirty2 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty2 |= $composer2.changed(expanded) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty2 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty2 |= $composer2.changedInstance(function1) ? 32 : 16;
        }
        int i2 = i & 4;
        if (i2 != 0) {
            $dirty2 |= 384;
            modifier2 = modifier;
        } else if (($changed & 384) == 0) {
            modifier2 = modifier;
            $dirty2 |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        if ((i & 8) != 0) {
            $dirty2 |= 3072;
        } else if (($changed & 3072) == 0) {
            $dirty2 |= $composer2.changedInstance(function3) ? 2048 : 1024;
        }
        int $dirty3 = $dirty2;
        if (($dirty3 & 1171) == 1170 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier3 = modifier2;
        } else {
            modifier3 = i2 != 0 ? Modifier.INSTANCE : modifier2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(2067579792, $dirty3, -1, "androidx.compose.material3.ExposedDropdownMenuBox (ExposedDropdownMenu.android.kt:115)");
            }
            ProvidableCompositionLocal<Configuration> localConfiguration = AndroidCompositionLocals_androidKt.getLocalConfiguration();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localConfiguration);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            Configuration config = (Configuration) objConsume;
            ProvidableCompositionLocal<View> localView = AndroidCompositionLocals_androidKt.getLocalView();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume2 = $composer2.consume(localView);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final View view2 = (View) objConsume2;
            ProvidableCompositionLocal<Density> localDensity = CompositionLocalsKt.getLocalDensity();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume3 = $composer2.consume(localDensity);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            Density density2 = (Density) objConsume3;
            int verticalMargin2 = density2.mo307roundToPx0680j_4(MenuKt.getMenuVerticalMargin());
            $composer2.startReplaceableGroup(983580452);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            Object it$iv = $composer2.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(null, null, 2, null);
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState anchorCoordinates$delegate = (MutableState) value$iv;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(983580529);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            Object it$iv2 = $composer2.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = SnapshotIntStateKt.mutableIntStateOf(0);
                $composer2.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableIntState anchorWidth$delegate = (MutableIntState) value$iv2;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(983580588);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            Object it$iv3 = $composer2.rememberedValue();
            if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                value$iv3 = SnapshotIntStateKt.mutableIntStateOf(0);
                $composer2.updateRememberedValue(value$iv3);
            } else {
                value$iv3 = it$iv3;
            }
            MutableIntState menuMaxHeight$delegate2 = (MutableIntState) value$iv3;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(983580648);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            Object it$iv4 = $composer2.rememberedValue();
            if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                value$iv4 = new FocusRequester();
                $composer2.updateRememberedValue(value$iv4);
            } else {
                value$iv4 = it$iv4;
            }
            FocusRequester focusRequester2 = (FocusRequester) value$iv4;
            $composer2.endReplaceableGroup();
            Strings.Companion companion = Strings.INSTANCE;
            final String expandedDescription = Strings_androidKt.m1966getStringNWtq28(Strings.m1897constructorimpl(R.string.m3c_dropdown_menu_expanded), $composer2, 0);
            Strings.Companion companion2 = Strings.INSTANCE;
            final String collapsedDescription = Strings_androidKt.m1966getStringNWtq28(Strings.m1897constructorimpl(R.string.m3c_dropdown_menu_collapsed), $composer2, 0);
            $composer2.startReplaceableGroup(983580821);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            boolean invalid$iv = (($dirty3 & 112) == 32) | (($dirty3 & 14) == 4) | $composer2.changed(config) | $composer2.changed(view2) | $composer2.changed(density2);
            Object value$iv7 = $composer2.rememberedValue();
            if (invalid$iv || value$iv7 == Composer.INSTANCE.getEmpty()) {
                str = "CC(remember):ExposedDropdownMenu.android.kt#9igjgp";
                focusRequester = focusRequester2;
                menuMaxHeight$delegate = menuMaxHeight$delegate2;
                verticalMargin = verticalMargin2;
                density = density2;
                view = view2;
                $dirty = $dirty3;
                value$iv7 = new ExposedDropdownMenuBoxScope() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1
                    @Override // androidx.compose.material3.ExposedDropdownMenuBoxScope
                    public Modifier menuAnchor(Modifier $this$menuAnchor) {
                        final View view3 = view2;
                        final int i3 = verticalMargin;
                        final MutableState<LayoutCoordinates> mutableState = anchorCoordinates$delegate;
                        final MutableIntState mutableIntState = anchorWidth$delegate;
                        final MutableIntState mutableIntState2 = menuMaxHeight$delegate;
                        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned($this$menuAnchor, new Function1<LayoutCoordinates, Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1$menuAnchor$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(LayoutCoordinates layoutCoordinates) {
                                invoke2(layoutCoordinates);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(LayoutCoordinates it) {
                                mutableState.setValue(it);
                                mutableIntState.setIntValue(IntSize.m5903getWidthimpl(it.mo4684getSizeYbymL2g()));
                                mutableIntState2.setIntValue(ExposedDropdownMenu_androidKt.calculateMaxHeight(ExposedDropdownMenu_androidKt.getWindowBounds(view3.getRootView()), ExposedDropdownMenu_androidKt.getAnchorBounds(ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox$lambda$2(mutableState)), i3));
                            }
                        });
                        boolean z = expanded;
                        final Function1<Boolean, Unit> function12 = function1;
                        final boolean z2 = expanded;
                        return FocusRequesterModifierKt.focusRequester(ExposedDropdownMenu_androidKt.expandable(modifierOnGloballyPositioned, z, new Function0<Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1$menuAnchor$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
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
                                function12.invoke(Boolean.valueOf(!z2));
                            }
                        }, expandedDescription, collapsedDescription), focusRequester);
                    }

                    @Override // androidx.compose.material3.ExposedDropdownMenuBoxScope
                    public Modifier exposedDropdownSize(Modifier $this$exposedDropdownSize, final boolean matchTextFieldWidth) {
                        final MutableIntState mutableIntState = anchorWidth$delegate;
                        final MutableIntState mutableIntState2 = menuMaxHeight$delegate;
                        return LayoutModifierKt.layout($this$exposedDropdownSize, new Function3<MeasureScope, Measurable, Constraints, MeasureResult>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1$exposedDropdownSize$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ MeasureResult invoke(MeasureScope measureScope, Measurable measurable, Constraints constraints) {
                                return m1547invoke3p2s80s(measureScope, measurable, constraints.getValue());
                            }

                            /* JADX INFO: renamed from: invoke-3p2s80s, reason: not valid java name */
                            public final MeasureResult m1547invoke3p2s80s(MeasureScope $this$layout, Measurable measurable, long constraints) {
                                int menuWidth = ConstraintsKt.m5703constrainWidthK40F9xA(constraints, ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox$lambda$5(mutableIntState));
                                long menuConstraints = Constraints.m5679copyZbe2FdA(constraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(constraints) : matchTextFieldWidth ? menuWidth : Constraints.m5691getMinWidthimpl(constraints), (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(constraints) : matchTextFieldWidth ? menuWidth : Constraints.m5689getMaxWidthimpl(constraints), (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(constraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(constraints) : ConstraintsKt.m5702constrainHeightK40F9xA(constraints, ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox$lambda$8(mutableIntState2)));
                                final Placeable placeable = measurable.mo4677measureBRTryo0(menuConstraints);
                                return MeasureScope.layout$default($this$layout, placeable.getWidth(), placeable.getHeight(), null, new Function1<Placeable.PlacementScope, Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1$exposedDropdownSize$1.1
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(Placeable.PlacementScope placementScope) {
                                        invoke2(placementScope);
                                        return Unit.INSTANCE;
                                    }

                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(Placeable.PlacementScope $this$layout2) {
                                        Placeable.PlacementScope.place$default($this$layout2, placeable, 0, 0, 0.0f, 4, null);
                                    }
                                }, 4, null);
                            }
                        });
                    }
                };
                $composer2.updateRememberedValue(value$iv7);
            } else {
                str = "CC(remember):ExposedDropdownMenu.android.kt#9igjgp";
                focusRequester = focusRequester2;
                menuMaxHeight$delegate = menuMaxHeight$delegate2;
                verticalMargin = verticalMargin2;
                density = density2;
                view = view2;
                $dirty = $dirty3;
            }
            ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1 scope = (ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$scope$1$1) value$iv7;
            $composer2.endReplaceableGroup();
            int $changed$iv = ($dirty >> 6) & 14;
            $composer2.startReplaceableGroup(733328855);
            ComposerKt.sourceInformation($composer2, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
            Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer2, (($changed$iv >> 3) & 14) | (($changed$iv >> 3) & 112));
            int $changed$iv$iv = ($changed$iv << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier3);
            int $i$f$Box = $changed$iv$iv << 9;
            int $changed$iv$iv$iv = ($i$f$Box & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                $composer2.createNode(constructor);
            } else {
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
            }
            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            int i3 = ($changed$iv$iv$iv >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer2, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i4 = (($changed$iv >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer2, 1004943029, "C167@7896L9:ExposedDropdownMenu.android.kt#uh7d8r");
            function3.invoke(scope, $composer2, Integer.valueOf(($dirty >> 6) & 112));
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(983582639);
            ComposerKt.sourceInformation($composer2, "171@7977L254,171@7941L290");
            if (expanded) {
                $composer2.startReplaceableGroup(983582699);
                str2 = str;
                ComposerKt.sourceInformation($composer2, str2);
                final View view3 = view;
                final int verticalMargin3 = verticalMargin;
                boolean invalid$iv2 = $composer2.changedInstance(view3) | $composer2.changed(verticalMargin3);
                Object it$iv5 = $composer2.rememberedValue();
                if (invalid$iv2 || it$iv5 == Composer.INSTANCE.getEmpty()) {
                    final MutableIntState menuMaxHeight$delegate3 = menuMaxHeight$delegate;
                    value$iv6 = new Function0<Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$2$1
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
                            menuMaxHeight$delegate3.setIntValue(ExposedDropdownMenu_androidKt.calculateMaxHeight(ExposedDropdownMenu_androidKt.getWindowBounds(view3.getRootView()), ExposedDropdownMenu_androidKt.getAnchorBounds(ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox$lambda$2(anchorCoordinates$delegate)), verticalMargin3));
                        }
                    };
                    $composer2.updateRememberedValue(value$iv6);
                } else {
                    value$iv6 = it$iv5;
                }
                $composer2.endReplaceableGroup();
                SoftKeyboardListener(view3, density, (Function0) value$iv6, $composer2, 0);
            } else {
                str2 = str;
            }
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(983582976);
            ComposerKt.sourceInformation($composer2, str2);
            boolean invalid$iv3 = ($dirty & 14) == 4;
            Object it$iv6 = $composer2.rememberedValue();
            if (invalid$iv3 || it$iv6 == Composer.INSTANCE.getEmpty()) {
                final FocusRequester focusRequester3 = focusRequester;
                value$iv5 = new Function0<Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$ExposedDropdownMenuBox$3$1
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
                        if (expanded) {
                            focusRequester3.requestFocus();
                        }
                    }
                };
                $composer2.updateRememberedValue(value$iv5);
            } else {
                value$iv5 = it$iv6;
            }
            $composer2.endReplaceableGroup();
            EffectsKt.SideEffect((Function0) value$iv5, $composer2, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier4 = modifier3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox.4
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

                public final void invoke(Composer composer, int i5) {
                    ExposedDropdownMenu_androidKt.ExposedDropdownMenuBox(expanded, function1, modifier4, function3, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LayoutCoordinates ExposedDropdownMenuBox$lambda$2(MutableState<LayoutCoordinates> mutableState) {
        MutableState<LayoutCoordinates> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ExposedDropdownMenuBox$lambda$5(MutableIntState $anchorWidth$delegate) {
        MutableIntState $this$getValue$iv = $anchorWidth$delegate;
        return $this$getValue$iv.getIntValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ExposedDropdownMenuBox$lambda$8(MutableIntState $menuMaxHeight$delegate) {
        MutableIntState $this$getValue$iv = $menuMaxHeight$delegate;
        return $this$getValue$iv.getIntValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void SoftKeyboardListener(final View view, final Density density, final Function0<Unit> function0, Composer $composer, final int $changed) {
        Object value$iv;
        Composer $composer2 = $composer.startRestartGroup(-1319522472);
        ComposerKt.sourceInformation($composer2, "C(SoftKeyboardListener)P(2)193@8669L1413,193@8637L1445:ExposedDropdownMenu.android.kt#uh7d8r");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(view) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer2.changed(density) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer2.changedInstance(function0) ? 256 : 128;
        }
        if (($dirty & 147) != 146 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1319522472, $dirty, -1, "androidx.compose.material3.SoftKeyboardListener (ExposedDropdownMenu.android.kt:190)");
            }
            $composer2.startReplaceableGroup(-491766155);
            ComposerKt.sourceInformation($composer2, "CC(remember):ExposedDropdownMenu.android.kt#9igjgp");
            boolean invalid$iv = $composer2.changedInstance(view) | (($dirty & 896) == 256);
            Object it$iv = $composer2.rememberedValue();
            if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = new Function1<DisposableEffectScope, DisposableEffectResult>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$SoftKeyboardListener$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final DisposableEffectResult invoke(DisposableEffectScope $this$DisposableEffect) {
                        final ExposedDropdownMenu_androidKt$SoftKeyboardListener$1$1$listener$1 listener = new ExposedDropdownMenu_androidKt$SoftKeyboardListener$1$1$listener$1(view, function0);
                        return new DisposableEffectResult() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt$SoftKeyboardListener$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public void dispose() {
                                listener.dispose();
                            }
                        };
                    }
                };
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            $composer2.endReplaceableGroup();
            EffectsKt.DisposableEffect(view, density, (Function1) value$iv, $composer2, ($dirty & 14) | ($dirty & 112));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt.SoftKeyboardListener.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ExposedDropdownMenu_androidKt.SoftKeyboardListener(view, density, function0, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: renamed from: androidx.compose.material3.ExposedDropdownMenu_androidKt$expandable$1, reason: invalid class name */
    /* JADX INFO: compiled from: ExposedDropdownMenu.android.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Landroidx/compose/ui/input/pointer/PointerInputScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.compose.material3.ExposedDropdownMenu_androidKt$expandable$1", f = "ExposedDropdownMenu.android.kt", i = {}, l = {1041}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<PointerInputScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<Unit> $onExpandedChange;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Function0<Unit> function0, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$onExpandedChange = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$onExpandedChange, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(PointerInputScope pointerInputScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(pointerInputScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: androidx.compose.material3.ExposedDropdownMenu_androidKt$expandable$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExposedDropdownMenu.android.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Landroidx/compose/ui/input/pointer/AwaitPointerEventScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "androidx.compose.material3.ExposedDropdownMenu_androidKt$expandable$1$1", f = "ExposedDropdownMenu.android.kt", i = {0}, l = {1044, 1045}, m = "invokeSuspend", n = {"$this$awaitEachGesture"}, s = {"L$0"})
        static final class C01031 extends RestrictedSuspendLambda implements Function2<AwaitPointerEventScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function0<Unit> $onExpandedChange;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01031(Function0<Unit> function0, Continuation<? super C01031> continuation) {
                super(2, continuation);
                this.$onExpandedChange = function0;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C01031 c01031 = new C01031(this.$onExpandedChange, continuation);
                c01031.L$0 = obj;
                return c01031;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(AwaitPointerEventScope awaitPointerEventScope, Continuation<? super Unit> continuation) {
                return ((C01031) create(awaitPointerEventScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:13:0x0050 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:14:0x0051  */
            /* JADX WARN: Removed duplicated region for block: B:17:0x0057  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r10) {
                /*
                    r9 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r9.label
                    switch(r1) {
                        case 0: goto L22;
                        case 1: goto L19;
                        case 2: goto L12;
                        default: goto L9;
                    }
                L9:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r0)
                    throw r10
                L12:
                    r0 = r9
                    kotlin.ResultKt.throwOnFailure(r10)
                    r1 = r0
                    r0 = r10
                    goto L53
                L19:
                    r1 = r9
                    java.lang.Object r2 = r1.L$0
                    androidx.compose.ui.input.pointer.AwaitPointerEventScope r2 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r2
                    kotlin.ResultKt.throwOnFailure(r10)
                    goto L3f
                L22:
                    kotlin.ResultKt.throwOnFailure(r10)
                    r1 = r9
                    java.lang.Object r2 = r1.L$0
                    androidx.compose.ui.input.pointer.AwaitPointerEventScope r2 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r2
                    androidx.compose.ui.input.pointer.PointerEventPass r5 = androidx.compose.ui.input.pointer.PointerEventPass.Initial
                    r6 = r1
                    kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                    r1.L$0 = r2
                    r3 = 1
                    r1.label = r3
                    r4 = 0
                    r7 = 1
                    r8 = 0
                    r3 = r2
                    java.lang.Object r3 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r3, r4, r5, r6, r7, r8)
                    if (r3 != r0) goto L3f
                    return r0
                L3f:
                    androidx.compose.ui.input.pointer.PointerEventPass r3 = androidx.compose.ui.input.pointer.PointerEventPass.Initial
                    r4 = r1
                    kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                    r5 = 0
                    r1.L$0 = r5
                    r5 = 2
                    r1.label = r5
                    java.lang.Object r2 = androidx.compose.foundation.gestures.TapGestureDetectorKt.waitForUpOrCancellation(r2, r3, r4)
                    if (r2 != r0) goto L51
                    return r0
                L51:
                    r0 = r10
                    r10 = r2
                L53:
                    androidx.compose.ui.input.pointer.PointerInputChange r10 = (androidx.compose.ui.input.pointer.PointerInputChange) r10
                    if (r10 == 0) goto L5c
                    kotlin.jvm.functions.Function0<kotlin.Unit> r10 = r1.$onExpandedChange
                    r10.invoke()
                L5c:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ExposedDropdownMenu_androidKt.AnonymousClass1.C01031.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    PointerInputScope $this$pointerInput = (PointerInputScope) this.L$0;
                    this.label = 1;
                    if (ForEachGestureKt.awaitEachGesture($this$pointerInput, new C01031(this.$onExpandedChange, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Modifier expandable(Modifier $this$expandable, final boolean expanded, final Function0<Unit> function0, final String expandedDescription, final String collapsedDescription) {
        return SemanticsModifierKt.semantics$default(SuspendingPointerInputFilterKt.pointerInput($this$expandable, function0, new AnonymousClass1(function0, null)), false, new Function1<SemanticsPropertyReceiver, Unit>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt.expandable.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                invoke2(semanticsPropertyReceiver);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SemanticsPropertyReceiver $this$semantics) {
                SemanticsPropertiesKt.setStateDescription($this$semantics, expanded ? expandedDescription : collapsedDescription);
                SemanticsPropertiesKt.m5076setRolekuIjeqM($this$semantics, Role.INSTANCE.m5062getDropdownListo7Vup1c());
                final Function0<Unit> function02 = function0;
                SemanticsPropertiesKt.onClick$default($this$semantics, null, new Function0<Boolean>() { // from class: androidx.compose.material3.ExposedDropdownMenu_androidKt.expandable.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Boolean invoke() {
                        function02.invoke();
                        return true;
                    }
                }, 1, null);
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int calculateMaxHeight(Rect windowBounds, Rect anchorBounds, int verticalMargin) {
        int iRoundToInt;
        if (anchorBounds == null) {
            return 0;
        }
        float marginedWindowTop = windowBounds.getTop() + verticalMargin;
        float marginedWindowBottom = windowBounds.getBottom() - verticalMargin;
        if (anchorBounds.getTop() > windowBounds.getBottom() || anchorBounds.getBottom() < windowBounds.getTop()) {
            float heightAbove = marginedWindowBottom - marginedWindowTop;
            iRoundToInt = MathKt.roundToInt(heightAbove);
        } else {
            float heightAbove2 = anchorBounds.getTop() - marginedWindowTop;
            float heightBelow = marginedWindowBottom - anchorBounds.getBottom();
            iRoundToInt = MathKt.roundToInt(Math.max(heightAbove2, heightBelow));
        }
        int availableHeight = iRoundToInt;
        return Math.max(availableHeight, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Rect getWindowBounds(View $this$getWindowBounds) {
        android.graphics.Rect it = new android.graphics.Rect();
        $this$getWindowBounds.getWindowVisibleDisplayFrame(it);
        return RectHelper_androidKt.toComposeRect(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Rect getAnchorBounds(LayoutCoordinates $this$getAnchorBounds) {
        return $this$getAnchorBounds == null ? Rect.INSTANCE.getZero() : RectKt.m3205Recttz77jQw(LayoutCoordinatesKt.positionInWindow($this$getAnchorBounds), IntSizeKt.m5913toSizeozmzZPI($this$getAnchorBounds.mo4684getSizeYbymL2g()));
    }
}
