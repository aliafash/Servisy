package com.maw;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowDropDownKt;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.core.view.PointerIconCompat;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$MockMapViewScreen$3$1 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
    final /* synthetic */ State<List<City>> $cities$delegate;
    final /* synthetic */ MutableFloatState $maxDistanceFilter$delegate;
    final /* synthetic */ MutableState<Provider> $selectedProviderForMap$delegate;
    final /* synthetic */ MutableState<String> $selectedUserCityId$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    MainActivityKt$MockMapViewScreen$3$1(State<? extends List<City>> state, MutableState<String> mutableState, MutableState<Provider> mutableState2, MutableFloatState mutableFloatState) {
        super(3);
        this.$cities$delegate = state;
        this.$selectedUserCityId$delegate = mutableState;
        this.$selectedProviderForMap$delegate = mutableState2;
        this.$maxDistanceFilter$delegate = mutableFloatState;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
        invoke(columnScope, composer, num.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(ColumnScope Card, Composer $composer, int $changed) {
        Function0<ComposeUiNode> function0;
        Function0<ComposeUiNode> function02;
        Function0<ComposeUiNode> function03;
        Object obj;
        final String activeLabel;
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C6093@293940L3741:MainActivity.kt#foq9o6");
        if (($changed & 81) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
            return;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(460095450, $changed, -1, "com.maw.MockMapViewScreen.<anonymous>.<anonymous> (MainActivity.kt:6093)");
        }
        Modifier modifier$iv = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
        final State<List<City>> state = this.$cities$delegate;
        final MutableState<String> mutableState = this.$selectedUserCityId$delegate;
        final MutableState<Provider> mutableState2 = this.$selectedProviderForMap$delegate;
        final MutableFloatState mutableFloatState = this.$maxDistanceFilter$delegate;
        $composer.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.getTop();
        Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
        MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer, ((6 >> 3) & 14) | ((6 >> 3) & 112));
        int $changed$iv$iv = (6 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
        int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            function0 = constructor;
            $composer.createNode(function0);
        } else {
            function0 = constructor;
            $composer.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
            $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
            $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
        }
        function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
        $composer.startReplaceableGroup(2058660585);
        int i = ($changed$iv$iv$iv >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer, 276693656, "C79@3979L9:Column.kt#2w3rfo");
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        int i2 = ((6 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, 809695561, "C6094@294001L230,6100@294248L40,6102@294306L3361:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("🌐 خارطة رادار الخدمات وإحداثيات الموقع:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131026);
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(6)), $composer, 6);
        Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
        Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
        $composer.startReplaceableGroup(693286680);
        ComposerKt.sourceInformation($composer, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
        MeasurePolicy measurePolicy$iv2 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer, ((438 >> 3) & 14) | ((438 >> 3) & 112));
        int $changed$iv$iv2 = (438 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv2 = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            function02 = constructor2;
            $composer.createNode(function02);
        } else {
            function02 = constructor2;
            $composer.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
        }
        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
        $composer.startReplaceableGroup(2058660585);
        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer, -326681643, "C92@4661L9:Row.kt#2w3rfo");
        int i4 = ((438 >> 6) & 112) | 6;
        RowScope $this$invoke_u24lambda_u2410_u24lambda_u249 = RowScopeInstance.INSTANCE;
        ComposerKt.sourceInformationMarkerStart($composer, 837646731, "C6107@294546L94,6110@294729L2020,6146@296820L829:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("موقعي الحالي:", RowScope.weight$default($this$invoke_u24lambda_u2410_u24lambda_u249, Modifier.INSTANCE, 0.4f, false, 2, null), Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 0, 131056);
        Modifier modifier$iv3 = RowScope.weight$default($this$invoke_u24lambda_u2410_u24lambda_u249, Modifier.INSTANCE, 1.0f, false, 2, null);
        $composer.startReplaceableGroup(733328855);
        ComposerKt.sourceInformation($composer, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
        Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
        MeasurePolicy measurePolicy$iv3 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer, ((0 >> 3) & 14) | ((0 >> 3) & 112));
        int $changed$iv$iv3 = (0 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv3 = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
        int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            function03 = constructor3;
            $composer.createNode(function03);
        } else {
            function03 = constructor3;
            $composer.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
            $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
            $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
        }
        function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
        $composer.startReplaceableGroup(2058660585);
        int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
        int i6 = ((0 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, 1085198113, "C6111@294815L34,6116@295134L48,6115@295053L27,6114@295007L826,6129@296001L28,6127@295883L844:MainActivity.kt#foq9o6");
        $composer.startReplaceableGroup(1085198137);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object value$iv4 = $composer.rememberedValue();
        if (value$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer.updateRememberedValue(value$iv4);
        }
        final MutableState dropdownExpanded$delegate = (MutableState) value$iv4;
        $composer.endReplaceableGroup();
        Iterator it = MainActivityKt.MockMapViewScreen$lambda$182(state).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            City it2 = (City) next;
            Iterator it3 = it;
            if (Intrinsics.areEqual(it2.getId(), MainActivityKt.MockMapViewScreen$lambda$184(mutableState))) {
                obj = next;
                break;
            }
            it = it3;
        }
        City city = (City) obj;
        if (city == null || (activeLabel = city.getNameAr()) == null) {
            activeLabel = "صنعاء 🌍";
        }
        ButtonColors buttonColorsM1266buttonColorsro_MJ88 = ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4279181861L), 0L, 0L, 0L, $composer, (ButtonDefaults.$stable << 12) | 6, 14);
        RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8));
        Modifier modifierM597height3ABfNKs = SizeKt.m597height3ABfNKs(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m5733constructorimpl(36));
        PaddingValues paddingValuesM556PaddingValuesYgX7TsA = PaddingKt.m556PaddingValuesYgX7TsA(Dp.m5733constructorimpl(10), Dp.m5733constructorimpl(0));
        $composer.startReplaceableGroup(1085198375);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv = $composer.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$1$1
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
                    MainActivityKt$MockMapViewScreen$3$1.invoke$lambda$10$lambda$9$lambda$6$lambda$2(dropdownExpanded$delegate, true);
                }
            };
            $composer.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        $composer.endReplaceableGroup();
        ButtonKt.Button((Function0) value$iv, modifierM597height3ABfNKs, false, roundedCornerShapeM831RoundedCornerShape0680j_4, buttonColorsM1266buttonColorsro_MJ88, null, null, paddingValuesM556PaddingValuesYgX7TsA, null, ComposableLambdaKt.composableLambda($composer, 790150954, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                invoke(rowScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(RowScope Button, Composer $composer2, int $changed2) {
                Intrinsics.checkNotNullParameter(Button, "$this$Button");
                ComposerKt.sourceInformation($composer2, "C6121@295476L331:MainActivity.kt#foq9o6");
                if (($changed2 & 81) != 16 || !$composer2.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(790150954, $changed2, -1, "com.maw.MockMapViewScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:6121)");
                    }
                    Arrangement.HorizontalOrVertical spaceBetween = Arrangement.INSTANCE.getSpaceBetween();
                    Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    String str = activeLabel;
                    $composer2.startReplaceableGroup(693286680);
                    ComposerKt.sourceInformation($composer2, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                    Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getTop();
                    MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(spaceBetween, verticalAlignment$iv2, $composer2, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                    int $changed$iv$iv4 = (54 << 3) & 112;
                    $composer2.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
                    CompositionLocalMap localMap$iv$iv4 = $composer2.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default);
                    int $changed$iv$iv$iv4 = (($changed$iv$iv4 << 9) & 7168) | 6;
                    if (!($composer2.getApplier() instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                    }
                    $composer2.startReusableNode();
                    if ($composer2.getInserting()) {
                        $composer2.createNode(constructor4);
                    } else {
                        $composer2.useNode();
                    }
                    Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer2);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                        $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                        $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                    }
                    function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                    $composer2.startReplaceableGroup(2058660585);
                    int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer2, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    int i8 = ((54 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer2, 1272905484, "C6122@295600L56,6123@295689L88:MainActivity.kt#foq9o6");
                    TextKt.m2124Text4IGK_g(str, (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 3456, 0, 131058);
                    IconKt.m1597Iconww6aTOc(ArrowDropDownKt.getArrowDropDown(Icons.INSTANCE.getDefault()), (String) null, (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), $composer2, 48, 4);
                    ComposerKt.sourceInformationMarkerEnd($composer2);
                    ComposerKt.sourceInformationMarkerEnd($composer2);
                    $composer2.endReplaceableGroup();
                    $composer2.endNode();
                    $composer2.endReplaceableGroup();
                    $composer2.endReplaceableGroup();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer2.skipToGroupEnd();
            }
        }), $composer, 817889334, 356);
        boolean zInvoke$lambda$10$lambda$9$lambda$6$lambda$1 = invoke$lambda$10$lambda$9$lambda$6$lambda$1(dropdownExpanded$delegate);
        $composer.startReplaceableGroup(1085199323);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv2 = $composer.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$3$1
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
                    MainActivityKt$MockMapViewScreen$3$1.invoke$lambda$10$lambda$9$lambda$6$lambda$2(dropdownExpanded$delegate, false);
                }
            };
            $composer.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        $composer.endReplaceableGroup();
        AndroidMenu_androidKt.m1225DropdownMenu4kj_NE(zInvoke$lambda$10$lambda$9$lambda$6$lambda$1, (Function0) value$iv2, BackgroundKt.m210backgroundbw27NRU$default(Modifier.INSTANCE, AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), null, 2, null), 0L, null, null, ComposableLambdaKt.composableLambda($composer, -902839315, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                invoke(columnScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(ColumnScope DropdownMenu, Composer $composer2, int $changed2) {
                Object value$iv5;
                Intrinsics.checkNotNullParameter(DropdownMenu, "$this$DropdownMenu");
                ComposerKt.sourceInformation($composer2, "C*6135@296394L243,6133@296225L446:MainActivity.kt#foq9o6");
                if (($changed2 & 81) != 16 || !$composer2.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-902839315, $changed2, -1, "com.maw.MockMapViewScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:6132)");
                    }
                    Iterable $this$forEach$iv = MainActivityKt.MockMapViewScreen$lambda$182(state);
                    final MutableState<String> mutableState3 = mutableState;
                    final MutableState<Provider> mutableState4 = mutableState2;
                    final MutableState<Boolean> mutableState5 = dropdownExpanded$delegate;
                    for (Object element$iv : $this$forEach$iv) {
                        final City city2 = (City) element$iv;
                        ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer2, 1308172295, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$4$1$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                invoke(composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(Composer $composer3, int $changed3) {
                                ComposerKt.sourceInformation($composer3, "C6134@296288L56:MainActivity.kt#foq9o6");
                                if (($changed3 & 11) == 2 && $composer3.getSkipping()) {
                                    $composer3.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1308172295, $changed3, -1, "com.maw.MockMapViewScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:6134)");
                                }
                                TextKt.m2124Text4IGK_g(city2.getNameAr(), (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3456, 0, 131058);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        });
                        $composer2.startReplaceableGroup(1272906278);
                        ComposerKt.sourceInformation($composer2, "CC(remember):MainActivity.kt#9igjgp");
                        boolean invalid$iv = $composer2.changed(city2);
                        Object it$iv3 = $composer2.rememberedValue();
                        if (invalid$iv || it$iv3 == Composer.INSTANCE.getEmpty()) {
                            value$iv5 = new Function0<Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$1$4$1$2$1
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
                                    mutableState3.setValue(city2.getId());
                                    mutableState4.setValue(null);
                                    MainActivityKt$MockMapViewScreen$3$1.invoke$lambda$10$lambda$9$lambda$6$lambda$2(mutableState5, false);
                                }
                            };
                            $composer2.updateRememberedValue(value$iv5);
                        } else {
                            value$iv5 = it$iv3;
                        }
                        $composer2.endReplaceableGroup();
                        AndroidMenu_androidKt.DropdownMenuItem(composableLambda, (Function0) value$iv5, null, null, null, false, null, null, null, $composer2, 6, 508);
                        mutableState5 = mutableState5;
                        mutableState3 = mutableState3;
                        mutableState4 = mutableState4;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer2.skipToGroupEnd();
            }
        }), $composer, 1572912, 56);
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        Modifier modifier$iv4 = RowScope.weight$default($this$invoke_u24lambda_u2410_u24lambda_u249, Modifier.INSTANCE, 1.3f, false, 2, null);
        $composer.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.getTop();
        Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
        MeasurePolicy measurePolicy$iv4 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer, ((0 >> 3) & 14) | ((0 >> 3) & 112));
        int $changed$iv$iv4 = (0 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv4 = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
        int $changed$iv$iv$iv4 = (($changed$iv$iv4 << 9) & 7168) | 6;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            $composer.createNode(constructor4);
        } else {
            $composer.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
            $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
            $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
        }
        function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
        $composer.startReplaceableGroup(2058660585);
        int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer, 276693656, "C79@3979L9:Column.kt#2w3rfo");
        ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
        int i8 = ((0 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, 1085200209, "C6147@296887L215,6156@297364L174,6154@297234L26,6152@297127L500:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("نطاق البحث: بقرب " + ((int) MainActivityKt.MockMapViewScreen$lambda$190(mutableFloatState)) + " كم", (Modifier) null, Color.INSTANCE.m3438getLightGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 0, 131058);
        float fMockMapViewScreen$lambda$190 = MainActivityKt.MockMapViewScreen$lambda$190(mutableFloatState);
        ClosedFloatingPointRange<Float> closedFloatingPointRangeRangeTo = RangesKt.rangeTo(2.0f, 50.0f);
        SliderColors sliderColorsM1868colorsq0g_0yA = SliderDefaults.INSTANCE.m1868colorsq0g_0yA(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 0, 6, PointerIconCompat.TYPE_GRAB);
        Modifier modifierM597height3ABfNKs2 = SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(20));
        $composer.startReplaceableGroup(1085200556);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv3 = $composer.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = (Function1) new Function1<Float, Unit>() { // from class: com.maw.MainActivityKt$MockMapViewScreen$3$1$1$1$2$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Float f) {
                    invoke(f.floatValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(float it4) {
                    mutableFloatState.setFloatValue(it4);
                }
            };
            $composer.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        $composer.endReplaceableGroup();
        SliderKt.Slider(fMockMapViewScreen$lambda$190, (Function1) value$iv3, modifierM597height3ABfNKs2, false, closedFloatingPointRangeRangeTo, 0, null, sliderColorsM1868colorsq0g_0yA, null, $composer, 432, 360);
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    private static final boolean invoke$lambda$10$lambda$9$lambda$6$lambda$1(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$10$lambda$9$lambda$6$lambda$2(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
