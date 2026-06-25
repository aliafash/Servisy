package com.maw;

import android.content.Context;
import android.widget.Toast;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.TextFieldColors;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.profileinstaller.ProfileVerifier;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$BookingsTab$5$3 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
    final /* synthetic */ State<List<Provider>> $approvedProviders$delegate;
    final /* synthetic */ Context $context;
    final /* synthetic */ MutableState<String> $mDetails$delegate;
    final /* synthetic */ MutableState<String> $mPhoneNumber$delegate;
    final /* synthetic */ MutableState<String> $mResidencePlc$delegate;
    final /* synthetic */ MutableState<Provider> $mSelectedProvider$delegate;
    final /* synthetic */ MutableState<String> $mServiceType$delegate;
    final /* synthetic */ MutableState<String> $mTimeChosen$delegate;
    final /* synthetic */ MutableState<String> $mTripleName$delegate;
    final /* synthetic */ MutableState<Boolean> $showManualAddForm$delegate;
    final /* synthetic */ MainViewModel $vm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    MainActivityKt$BookingsTab$5$3(MutableState<Provider> mutableState, State<? extends List<Provider>> state, MutableState<String> mutableState2, MutableState<String> mutableState3, MutableState<String> mutableState4, MutableState<String> mutableState5, MutableState<String> mutableState6, MutableState<String> mutableState7, Context context, MainViewModel mainViewModel, MutableState<Boolean> mutableState8) {
        super(3);
        this.$mSelectedProvider$delegate = mutableState;
        this.$approvedProviders$delegate = state;
        this.$mTripleName$delegate = mutableState2;
        this.$mPhoneNumber$delegate = mutableState3;
        this.$mServiceType$delegate = mutableState4;
        this.$mResidencePlc$delegate = mutableState5;
        this.$mDetails$delegate = mutableState6;
        this.$mTimeChosen$delegate = mutableState7;
        this.$context = context;
        this.$vm = mainViewModel;
        this.$showManualAddForm$delegate = mutableState8;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
        invoke(columnScope, composer, num.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(ColumnScope Card, Composer $composer, int $changed) {
        Function0<ComposeUiNode> function0;
        Function0<ComposeUiNode> function02;
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Object value$iv6;
        Object value$iv7;
        final MutableState<String> mutableState;
        Object value$iv8;
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C11152@571141L6794:MainActivity.kt#foq9o6");
        if (($changed & 81) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
            return;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-74494995, $changed, -1, "com.maw.BookingsTab.<anonymous>.<anonymous> (MainActivity.kt:11152)");
        }
        Modifier modifier$iv = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(14));
        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
        final MutableState<Provider> mutableState2 = this.$mSelectedProvider$delegate;
        final State<List<Provider>> state = this.$approvedProviders$delegate;
        final MutableState<String> mutableState3 = this.$mTripleName$delegate;
        final MutableState<String> mutableState4 = this.$mPhoneNumber$delegate;
        final MutableState<String> mutableState5 = this.$mServiceType$delegate;
        final MutableState<String> mutableState6 = this.$mResidencePlc$delegate;
        final MutableState<String> mutableState7 = this.$mDetails$delegate;
        MutableState<String> mutableState8 = this.$mTimeChosen$delegate;
        final Context context = this.$context;
        final MainViewModel mainViewModel = this.$vm;
        final MutableState<Boolean> mutableState9 = this.$showManualAddForm$delegate;
        $composer.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
        MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer, ((54 >> 3) & 14) | ((54 >> 3) & 112));
        int $changed$iv$iv = (54 << 3) & 112;
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
        int i2 = ((54 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -1470534695, "C11153@571257L125,11155@571404L122,11156@571572L34,11157@571627L1418,11188@573378L72,11185@573171L20,11183@573067L405,11195@573801L72,11192@573598L21,11190@573493L496,11203@574322L72,11200@574115L21,11198@574010L406,11210@574757L72,11207@574543L22,11205@574437L414,11217@575180L72,11214@574973L17,11212@574872L402,11224@575606L72,11221@575399L20,11219@575295L405,11259@577569L50,11227@575722L2195:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("إدخال وبناء طلب خدمة جديد مباشرة يدوياً:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131026);
        TextKt.m2124Text4IGK_g("اختر الكادر أو مقدم الخدمة الموجه إليه الحجز:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200070, 0, 131026);
        $composer.startReplaceableGroup(-1470534380);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object value$iv9 = $composer.rememberedValue();
        if (value$iv9 == Composer.INSTANCE.getEmpty()) {
            value$iv9 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer.updateRememberedValue(value$iv9);
        }
        final MutableState showProvsDropdown$delegate = (MutableState) value$iv9;
        $composer.endReplaceableGroup();
        Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        $composer.startReplaceableGroup(733328855);
        ComposerKt.sourceInformation($composer, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
        Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
        MeasurePolicy measurePolicy$iv2 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer, ((6 >> 3) & 14) | ((6 >> 3) & 112));
        int $changed$iv$iv2 = (6 << 3) & 112;
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
        ComposerKt.sourceInformationMarkerStart($composer, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
        int i4 = ((6 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -83566674, "C11160@571821L48,11159@571739L28,11158@571693L480,11168@572317L29,11166@572198L825:MainActivity.kt#foq9o6");
        ButtonColors buttonColorsM1266buttonColorsro_MJ88 = ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4279181861L), 0L, 0L, 0L, $composer, (ButtonDefaults.$stable << 12) | 6, 14);
        Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        $composer.startReplaceableGroup(-83566628);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv = $composer.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$1$1
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
                    MainActivityKt$BookingsTab$5$3.invoke$lambda$12$lambda$2(showProvsDropdown$delegate, true);
                }
            };
            $composer.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        $composer.endReplaceableGroup();
        ButtonKt.Button((Function0) value$iv, modifierFillMaxWidth$default, false, null, buttonColorsM1266buttonColorsro_MJ88, null, null, null, null, ComposableLambdaKt.composableLambda($composer, 746846297, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$2
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
                String provLabel;
                Intrinsics.checkNotNullParameter(Button, "$this$Button");
                ComposerKt.sourceInformation($composer2, "C11164@572093L54:MainActivity.kt#foq9o6");
                if (($changed2 & 81) != 16 || !$composer2.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(746846297, $changed2, -1, "com.maw.BookingsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:11163)");
                    }
                    Provider providerBookingsTab$lambda$652 = MainActivityKt.BookingsTab$lambda$652(mutableState2);
                    if (providerBookingsTab$lambda$652 == null || (provLabel = providerBookingsTab$lambda$652.getName()) == null) {
                        provLabel = "انقر لتحديد الفني المستهدف...";
                    }
                    TextKt.m2124Text4IGK_g(provLabel, (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 3456, 0, 131058);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer2.skipToGroupEnd();
            }
        }), $composer, 805306422, 492);
        boolean zInvoke$lambda$12$lambda$1 = invoke$lambda$12$lambda$1(showProvsDropdown$delegate);
        $composer.startReplaceableGroup(-83566050);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv2 = $composer.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$3$1
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
                    MainActivityKt$BookingsTab$5$3.invoke$lambda$12$lambda$2(showProvsDropdown$delegate, false);
                }
            };
            $composer.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        $composer.endReplaceableGroup();
        AndroidMenu_androidKt.m1225DropdownMenu4kj_NE(zInvoke$lambda$12$lambda$1, (Function0) value$iv2, SizeKt.fillMaxWidth(BackgroundKt.m210backgroundbw27NRU$default(Modifier.INSTANCE, AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), null, 2, null), 0.9f), 0L, null, null, ComposableLambdaKt.composableLambda($composer, -1001565988, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$4
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
                Intrinsics.checkNotNullParameter(DropdownMenu, "$this$DropdownMenu");
                ComposerKt.sourceInformation($composer2, "C*11172@572572L395:MainActivity.kt#foq9o6");
                if (($changed2 & 81) != 16 || !$composer2.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1001565988, $changed2, -1, "com.maw.BookingsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:11171)");
                    }
                    Iterable $this$forEach$iv = MainActivityKt.BookingsTab$lambda$626(state);
                    final MutableState<Provider> mutableState10 = mutableState2;
                    final MutableState<Boolean> mutableState11 = showProvsDropdown$delegate;
                    for (Object element$iv : $this$forEach$iv) {
                        final Provider prov = (Provider) element$iv;
                        AndroidMenu_androidKt.DropdownMenuItem(ComposableLambdaKt.composableLambda($composer2, -299508100, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$4$1$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                invoke(composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(Composer $composer3, int $changed3) {
                                ComposerKt.sourceInformation($composer3, "C11173@572635L78:MainActivity.kt#foq9o6");
                                if (($changed3 & 11) == 2 && $composer3.getSkipping()) {
                                    $composer3.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-299508100, $changed3, -1, "com.maw.BookingsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:11173)");
                                }
                                TextKt.m2124Text4IGK_g(prov.getName() + " (" + prov.getCategory() + ")", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3456, 0, 131058);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }), new Function0<Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$1$4$1$2
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
                                mutableState10.setValue(prov);
                                MainActivityKt$BookingsTab$5$3.invoke$lambda$12$lambda$2(mutableState11, false);
                            }
                        }, null, null, null, false, null, null, null, $composer2, 6, 508);
                        mutableState10 = mutableState10;
                        mutableState11 = mutableState11;
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
        String strBookingsTab$lambda$655 = MainActivityKt.BookingsTab$lambda$655(mutableState3);
        Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_0 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        $composer.startReplaceableGroup(-1470532781);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv3 = $composer.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = (Function1) new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState3.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$655, (Function1<? super String, Unit>) value$iv3, modifierFillMaxWidth$default2, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6354getLambda126$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_0, $composer, 1573296, 0, 0, 4194232);
        String strBookingsTab$lambda$658 = MainActivityKt.BookingsTab$lambda$658(mutableState4);
        Modifier modifierFillMaxWidth$default3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_02 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        KeyboardOptions keyboardOptions = new KeyboardOptions(0, false, KeyboardType.INSTANCE.m5462getPhonePjHm6EE(), 0, null, 27, null);
        $composer.startReplaceableGroup(-1470532354);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv4 = $composer.rememberedValue();
        if (it$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = (Function1) new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$3$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState4.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$658, (Function1<? super String, Unit>) value$iv4, modifierFillMaxWidth$default3, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6355getLambda127$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, keyboardOptions, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_02, $composer, 1573296, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 0, 4161464);
        String strBookingsTab$lambda$661 = MainActivityKt.BookingsTab$lambda$661(mutableState5);
        Modifier modifierFillMaxWidth$default4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_03 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        $composer.startReplaceableGroup(-1470531837);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv5 = $composer.rememberedValue();
        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = (Function1) new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$4$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState5.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv5);
        } else {
            value$iv5 = it$iv5;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$661, (Function1<? super String, Unit>) value$iv5, modifierFillMaxWidth$default4, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6356getLambda128$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_03, $composer, 1573296, 0, 0, 4194232);
        String strBookingsTab$lambda$664 = MainActivityKt.BookingsTab$lambda$664(mutableState6);
        Modifier modifierFillMaxWidth$default5 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_04 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        $composer.startReplaceableGroup(-1470531409);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv6 = $composer.rememberedValue();
        if (it$iv6 == Composer.INSTANCE.getEmpty()) {
            value$iv6 = (Function1) new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$5$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState6.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv6);
        } else {
            value$iv6 = it$iv6;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$664, (Function1<? super String, Unit>) value$iv6, modifierFillMaxWidth$default5, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6357getLambda129$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_04, $composer, 1573296, 0, 0, 4194232);
        String strBookingsTab$lambda$667 = MainActivityKt.BookingsTab$lambda$667(mutableState7);
        Modifier modifierFillMaxWidth$default6 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_05 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        $composer.startReplaceableGroup(-1470530979);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv7 = $composer.rememberedValue();
        if (it$iv7 == Composer.INSTANCE.getEmpty()) {
            value$iv7 = (Function1) new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$6$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState7.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv7);
        } else {
            value$iv7 = it$iv7;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$667, (Function1<? super String, Unit>) value$iv7, modifierFillMaxWidth$default6, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6359getLambda130$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_05, $composer, 1573296, 0, 0, 4194232);
        String strBookingsTab$lambda$670 = MainActivityKt.BookingsTab$lambda$670(mutableState8);
        Modifier modifierFillMaxWidth$default7 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        TextFieldColors textFieldColorsM1726colors0hiis_06 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer, 54, 0, 0, 0, 3072, 2147483644, 4095);
        $composer.startReplaceableGroup(-1470530553);
        ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv8 = $composer.rememberedValue();
        if (it$iv8 == Composer.INSTANCE.getEmpty()) {
            mutableState = mutableState8;
            value$iv8 = new Function1<String, Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$7$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    mutableState.setValue(it);
                }
            };
            $composer.updateRememberedValue(value$iv8);
        } else {
            mutableState = mutableState8;
            value$iv8 = it$iv8;
        }
        $composer.endReplaceableGroup();
        OutlinedTextFieldKt.OutlinedTextField(strBookingsTab$lambda$670, (Function1<? super String, Unit>) value$iv8, modifierFillMaxWidth$default7, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$MainActivityKt.INSTANCE.m6360getLambda131$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_06, $composer, 1573296, 0, 0, 4194232);
        final MutableState<String> mutableState10 = mutableState;
        ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.MainActivityKt$BookingsTab$5$3$1$8
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
                Provider provider = MainActivityKt.BookingsTab$lambda$652(mutableState2);
                if (provider != null) {
                    if (StringsKt.isBlank(MainActivityKt.BookingsTab$lambda$655(mutableState3)) || StringsKt.isBlank(MainActivityKt.BookingsTab$lambda$658(mutableState4)) || StringsKt.isBlank(MainActivityKt.BookingsTab$lambda$661(mutableState5)) || StringsKt.isBlank(MainActivityKt.BookingsTab$lambda$664(mutableState6)) || StringsKt.isBlank(MainActivityKt.BookingsTab$lambda$667(mutableState7))) {
                        Toast.makeText(context, "الرجاء تعبئة كامل الحقول المطلوبة لإنشاء حجز صحيح يدوياً", 0).show();
                        return;
                    }
                    mainViewModel.requestServiceAppointment(provider.getId(), provider.getName(), MainActivityKt.BookingsTab$lambda$667(mutableState7), MainActivityKt.BookingsTab$lambda$670(mutableState10), MainActivityKt.BookingsTab$lambda$655(mutableState3), MainActivityKt.BookingsTab$lambda$658(mutableState4), MainActivityKt.BookingsTab$lambda$661(mutableState5), MainActivityKt.BookingsTab$lambda$664(mutableState6));
                    mutableState3.setValue("");
                    mutableState4.setValue("");
                    mutableState5.setValue("");
                    mutableState6.setValue("");
                    mutableState7.setValue("");
                    mutableState2.setValue(null);
                    MainActivityKt.BookingsTab$lambda$650(mutableState9, false);
                    Toast.makeText(context, "تم إرسال ونشر طلب خدمة العميل يدوياً على الخوادم!", 0).show();
                    return;
                }
                Toast.makeText(context, "الرجاء تحديد كادر الخدمة من القائمة أولاً", 0).show();
            }
        }, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8)), ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableSingletons$MainActivityKt.INSTANCE.m6361getLambda132$app_debug(), $composer, 805306416, 484);
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

    private static final boolean invoke$lambda$12$lambda$1(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$12$lambda$2(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
