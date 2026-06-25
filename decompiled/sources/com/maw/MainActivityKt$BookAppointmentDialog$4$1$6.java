package com.maw;

import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.AspectRatioKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.Updater;
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
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$BookAppointmentDialog$4$1$6 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
    final /* synthetic */ FontFamily $fontFamily;
    final /* synthetic */ MutableState<String> $timeChosen$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$BookAppointmentDialog$4$1$6(FontFamily fontFamily, MutableState<String> mutableState) {
        super(3);
        this.$fontFamily = fontFamily;
        this.$timeChosen$delegate = mutableState;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
        invoke(columnScope, composer, num.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(ColumnScope Card, Composer $composer, int $changed) {
        Function0<ComposeUiNode> function0;
        Object value$iv;
        Object value$iv2;
        Function0<ComposeUiNode> function02;
        String str;
        final MutableState<String> mutableState;
        Object value$iv3;
        long jColor;
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C6580@317870L4272:MainActivity.kt#foq9o6");
        if (($changed & 81) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
            return;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1447185507, $changed, -1, "com.maw.BookAppointmentDialog.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:6580)");
        }
        Modifier modifier$iv = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(8));
        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
        FontFamily fontFamily = this.$fontFamily;
        MutableState<String> mutableState2 = this.$timeChosen$delegate;
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
            $composer.createNode(constructor);
        } else {
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
        ComposerKt.sourceInformationMarkerStart($composer, 109505870, "C6582@318032L410,6592@318629L705,6607@319472L31,6608@319548L38:MainActivity.kt#foq9o6");
        Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getCenter();
        Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
        $composer.startReplaceableGroup(693286680);
        String str2 = "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo";
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
            function0 = constructor2;
            $composer.createNode(function0);
        } else {
            function0 = constructor2;
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
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        int i4 = ((438 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -386702465, "C6587@318304L112:MainActivity.kt#foq9o6");
        Function0<ComposeUiNode> function03 = constructor;
        MutableState<String> mutableState3 = mutableState2;
        FontFamily fontFamily2 = fontFamily;
        TextKt.m2124Text4IGK_g("يونيو ٢٠٢٦", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200070, 0, 130962);
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        List weekdaysList = CollectionsKt.listOf((Object[]) new String[]{"أحد", "اثنين", "ثلاثاء", "أربعاء", "خميس", "جمعة", "سبت"});
        Modifier modifier$iv3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        Arrangement.Horizontal horizontalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(4));
        $composer.startReplaceableGroup(693286680);
        ComposerKt.sourceInformation($composer, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
        Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getTop();
        MeasurePolicy measurePolicy$iv3 = RowKt.rowMeasurePolicy(horizontalArrangement$iv2, verticalAlignment$iv2, $composer, ((54 >> 3) & 14) | ((54 >> 3) & 112));
        int $changed$iv$iv3 = (54 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        String str3 = "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh";
        ComposerKt.sourceInformation($composer, str3);
        int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv3 = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
        int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
        Function0<ComposeUiNode> function04 = constructor3;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            $composer.createNode(function04);
        } else {
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
        Composer $composer$iv = $composer;
        String str4 = "C92@4661L9:Row.kt#2w3rfo";
        ComposerKt.sourceInformationMarkerStart($composer$iv, -326681643, str4);
        int i6 = ((54 >> 6) & 112) | 6;
        RowScope $this$invoke_u24lambda_u2414_u24lambda_u242 = RowScopeInstance.INSTANCE;
        Composer $composer$iv2 = $composer;
        String str5 = "C:MainActivity.kt#foq9o6";
        Alignment.Vertical verticalAlignment$iv3 = verticalAlignment$iv2;
        Composer $composer2 = $composer$iv;
        ComposerKt.sourceInformationMarkerStart($composer2, -386702018, "C:MainActivity.kt#foq9o6");
        $composer2.startReplaceableGroup(109506602);
        ComposerKt.sourceInformation($composer2, "*6594@318815L463");
        for (Object element$iv : weekdaysList) {
            String wkDay = (String) element$iv;
            Composer $composer3 = $composer2;
            TextKt.m2124Text4IGK_g(wkDay, RowScope.weight$default($this$invoke_u24lambda_u2414_u24lambda_u242, Modifier.INSTANCE, 1.0f, false, 2, null), Color.INSTANCE.m3438getLightGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily2, 0L, (TextDecoration) null, TextAlign.m5618boximpl(TextAlign.INSTANCE.m5625getCentere0LSkKk()), 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200064, 0, 130448);
            $composer$iv = $composer$iv;
            measurePolicy$iv3 = measurePolicy$iv3;
            localMap$iv$iv3 = localMap$iv$iv3;
            function3ModifierMaterializerOf3 = function3ModifierMaterializerOf3;
            function04 = function04;
            verticalAlignment$iv3 = verticalAlignment$iv3;
            $composer2 = $composer3;
            $composer$iv2 = $composer$iv2;
            str3 = str3;
            str4 = str4;
            str5 = str5;
        }
        String str6 = str3;
        Composer $composer4 = $composer2;
        String str7 = str4;
        String str8 = str5;
        Composer $composer5 = $composer$iv2;
        int i7 = 0;
        $composer4.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer4);
        ComposerKt.sourceInformationMarkerEnd($composer$iv);
        $composer5.endReplaceableGroup();
        $composer5.endNode();
        $composer5.endReplaceableGroup();
        $composer5.endReplaceableGroup();
        $composer5.startReplaceableGroup(109507310);
        String str9 = "CC(remember):MainActivity.kt#9igjgp";
        ComposerKt.sourceInformation($composer5, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv = $composer5.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(21, null, 2, null);
            $composer5.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState selectedDay$delegate = (MutableState) value$iv;
        $composer5.endReplaceableGroup();
        $composer5.startReplaceableGroup(109507386);
        ComposerKt.sourceInformation($composer5, "CC(remember):MainActivity.kt#9igjgp");
        Object it$iv2 = $composer5.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("09:00 ص", null, 2, null);
            $composer5.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final MutableState selectedSlot$delegate = (MutableState) value$iv2;
        $composer5.endReplaceableGroup();
        int i8 = 1;
        List daysList = CollectionsKt.toList(new IntRange(1, 30));
        List chunkedRows = CollectionsKt.chunked(daysList, 7);
        $composer5.startReplaceableGroup(1575261532);
        ComposerKt.sourceInformation($composer5, "*6614@319792L2302");
        List $this$forEach$iv = chunkedRows;
        int $i$f$forEach = 0;
        for (Object element$iv2 : $this$forEach$iv) {
            List rowDays = (List) element$iv2;
            Function0<ComposeUiNode> function05 = function03;
            Modifier modifier$iv4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, i8, null);
            Arrangement.Horizontal horizontalArrangement$iv3 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(4));
            int $changed$iv = 54;
            int $i$f$Row = 0;
            List weekdaysList2 = weekdaysList;
            $composer5.startReplaceableGroup(693286680);
            ComposerKt.sourceInformation($composer5, str2);
            Alignment.Vertical verticalAlignment$iv4 = Alignment.INSTANCE.getTop();
            String str10 = str2;
            MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(horizontalArrangement$iv3, verticalAlignment$iv4, $composer5, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            int $changed$iv$iv4 = (54 << 3) & 112;
            $composer5.startReplaceableGroup(-1323940314);
            String str11 = str6;
            ComposerKt.sourceInformation($composer5, str11);
            int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer5, i7);
            CompositionLocalMap localMap$iv$iv4 = $composer5.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
            int $changed$iv$iv$iv4 = (($changed$iv$iv4 << 9) & 7168) | 6;
            List daysList2 = daysList;
            if (!($composer5.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer5.startReusableNode();
            if ($composer5.getInserting()) {
                function02 = constructor4;
                $composer5.createNode(function02);
            } else {
                function02 = constructor4;
                $composer5.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer5);
            List chunkedRows2 = chunkedRows;
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
            }
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3 = function3ModifierMaterializerOf4;
            function3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
            $composer5.startReplaceableGroup(2058660585);
            int i9 = ($changed$iv$iv$iv4 >> 9) & 14;
            String str12 = str7;
            ComposerKt.sourceInformationMarkerStart($composer5, -326681643, str12);
            int i10 = ((54 >> 6) & 112) | 6;
            RowScope $this$invoke_u24lambda_u2414_u24lambda_u2413_u24lambda_u2412 = RowScopeInstance.INSTANCE;
            String str13 = str8;
            ComposerKt.sourceInformationMarkerStart($composer5, 708588910, str13);
            $composer5.startReplaceableGroup(708588918);
            ComposerKt.sourceInformation($composer5, "*6622@320352L219,6626@320635L230,6618@320114L1646");
            List $this$forEach$iv2 = rowDays;
            for (Object element$iv3 : $this$forEach$iv2) {
                Iterable $this$forEach$iv3 = $this$forEach$iv2;
                final int dNum = ((Number) element$iv3).intValue();
                String str14 = str13;
                final boolean isSelected = invoke$lambda$14$lambda$4(selectedDay$delegate) == dNum;
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function32 = function3;
                final boolean isToday = dNum == 21;
                int $changed$iv2 = $changed$iv;
                Modifier modifier$iv5 = modifier$iv4;
                int $i$f$Row2 = $i$f$Row;
                Iterable $this$forEach$iv4 = $this$forEach$iv;
                int $i$f$forEach2 = $i$f$forEach;
                Modifier modifierAspectRatio$default = AspectRatioKt.aspectRatio$default(RowScope.weight$default($this$invoke_u24lambda_u2414_u24lambda_u2413_u24lambda_u2412, Modifier.INSTANCE, 1.0f, false, 2, null), 1.0f, false, 2, null);
                $composer5.startReplaceableGroup(-1508665023);
                ComposerKt.sourceInformation($composer5, str9);
                boolean invalid$iv = $composer5.changed(dNum);
                Object it$iv3 = $composer5.rememberedValue();
                if (invalid$iv || it$iv3 == Composer.INSTANCE.getEmpty()) {
                    str = str9;
                    mutableState = mutableState3;
                    value$iv3 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$BookAppointmentDialog$4$1$6$1$3$1$1$1$1
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
                            MainActivityKt$BookAppointmentDialog$4$1$6.invoke$lambda$14$lambda$5(selectedDay$delegate, dNum);
                            mutableState.setValue("٢٠٢٦/٠٦/" + dNum + " - الموعد: " + MainActivityKt$BookAppointmentDialog$4$1$6.invoke$lambda$14$lambda$7(selectedSlot$delegate));
                        }
                    };
                    $composer5.updateRememberedValue(value$iv3);
                } else {
                    str = str9;
                    value$iv3 = it$iv3;
                    mutableState = mutableState3;
                }
                $composer5.endReplaceableGroup();
                Modifier modifierM244clickableXHw0xAI$default = ClickableKt.m244clickableXHw0xAI$default(modifierAspectRatio$default, false, null, null, (Function0) value$iv3, 7, null);
                CardDefaults cardDefaults = CardDefaults.INSTANCE;
                if (isSelected) {
                    jColor = AppTheme.INSTANCE.m6261getAccentGold0d7_KjU();
                } else if (isToday) {
                    long jM6265getPrimaryRed0d7_KjU = AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU();
                    jColor = Color.m3404copywmQWz5c(jM6265getPrimaryRed0d7_KjU, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM6265getPrimaryRed0d7_KjU) : 0.3f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM6265getPrimaryRed0d7_KjU) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM6265getPrimaryRed0d7_KjU) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM6265getPrimaryRed0d7_KjU) : 0.0f);
                } else {
                    jColor = ColorKt.Color(4279642669L);
                }
                CardColors cardColorsM1287cardColorsro_MJ88 = cardDefaults.m1287cardColorsro_MJ88(jColor, 0L, 0L, 0L, $composer5, CardDefaults.$stable << 12, 14);
                final FontFamily fontFamily3 = fontFamily2;
                CardKt.Card(modifierM244clickableXHw0xAI$default, null, cardColorsM1287cardColorsro_MJ88, null, BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), isSelected ? Color.INSTANCE.m3443getWhite0d7_KjU() : ColorKt.Color(4280169785L)), ComposableLambdaKt.composableLambda($composer5, -577187432, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$BookAppointmentDialog$4$1$6$1$3$1$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                        invoke(columnScope, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(ColumnScope Card2, Composer $composer6, int $changed2) {
                        Intrinsics.checkNotNullParameter(Card2, "$this$Card");
                        ComposerKt.sourceInformation($composer6, "C6631@321067L655:MainActivity.kt#foq9o6");
                        if (($changed2 & 81) != 16 || !$composer6.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-577187432, $changed2, -1, "com.maw.BookAppointmentDialog.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:6631)");
                            }
                            Modifier modifier$iv6 = SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null);
                            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
                            int i11 = dNum;
                            boolean z = isSelected;
                            boolean z2 = isToday;
                            FontFamily fontFamily4 = fontFamily3;
                            $composer6.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer6, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            MeasurePolicy measurePolicy$iv5 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer6, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                            int $changed$iv$iv5 = (54 << 3) & 112;
                            $composer6.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer6, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer6, 0);
                            CompositionLocalMap localMap$iv$iv5 = $composer6.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifier$iv6);
                            int $changed$iv$iv$iv5 = (($changed$iv$iv5 << 9) & 7168) | 6;
                            if (!($composer6.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer6.startReusableNode();
                            if ($composer6.getInserting()) {
                                $composer6.createNode(constructor5);
                            } else {
                                $composer6.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer6);
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                                $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                                $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
                            }
                            function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer6)), $composer6, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
                            $composer6.startReplaceableGroup(2058660585);
                            int i12 = ($changed$iv$iv$iv5 >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer6, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i13 = ((54 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer6, -1386798008, "C6632@321189L491:MainActivity.kt#foq9o6");
                            String strValueOf = String.valueOf(i11);
                            Color.Companion companion = Color.INSTANCE;
                            TextKt.m2124Text4IGK_g(strValueOf, (Modifier) null, z ? companion.m3432getBlack0d7_KjU() : companion.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (z || z2) ? FontWeight.INSTANCE.getBold() : FontWeight.INSTANCE.getNormal(), fontFamily4, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3072, 0, 130962);
                            ComposerKt.sourceInformationMarkerEnd($composer6);
                            ComposerKt.sourceInformationMarkerEnd($composer6);
                            $composer6.endReplaceableGroup();
                            $composer6.endNode();
                            $composer6.endReplaceableGroup();
                            $composer6.endReplaceableGroup();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer6.skipToGroupEnd();
                    }
                }), $composer5, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 10);
                mutableState3 = mutableState;
                $this$forEach$iv = $this$forEach$iv4;
                $this$forEach$iv2 = $this$forEach$iv3;
                function3 = function32;
                $changed$iv = $changed$iv2;
                modifier$iv4 = modifier$iv5;
                $i$f$Row = $i$f$Row2;
                $i$f$forEach = $i$f$forEach2;
                str13 = str14;
                str9 = str;
            }
            str8 = str13;
            String str15 = str9;
            Iterable $this$forEach$iv5 = $this$forEach$iv;
            int $i$f$forEach3 = $i$f$forEach;
            MutableState<String> mutableState4 = mutableState3;
            FontFamily fontFamily4 = fontFamily2;
            $composer5.endReplaceableGroup();
            $composer5.startReplaceableGroup(109509665);
            ComposerKt.sourceInformation($composer5, "*6644@321954L38");
            if (rowDays.size() < 7) {
                int size = 7 - rowDays.size();
                for (int i11 = 0; i11 < size; i11++) {
                    SpacerKt.Spacer(RowScope.weight$default($this$invoke_u24lambda_u2414_u24lambda_u2413_u24lambda_u2412, Modifier.INSTANCE, 1.0f, false, 2, null), $composer5, 0);
                }
            }
            $composer5.endReplaceableGroup();
            ComposerKt.sourceInformationMarkerEnd($composer5);
            ComposerKt.sourceInformationMarkerEnd($composer5);
            $composer5.endReplaceableGroup();
            $composer5.endNode();
            $composer5.endReplaceableGroup();
            $composer5.endReplaceableGroup();
            i7 = 0;
            mutableState3 = mutableState4;
            fontFamily2 = fontFamily4;
            i8 = 1;
            $this$forEach$iv = $this$forEach$iv5;
            function03 = function05;
            weekdaysList = weekdaysList2;
            str2 = str10;
            daysList = daysList2;
            chunkedRows = chunkedRows2;
            str6 = str11;
            str7 = str12;
            $i$f$forEach = $i$f$forEach3;
            str9 = str15;
        }
        $composer5.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer5);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    private static final int invoke$lambda$14$lambda$4(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$14$lambda$5(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String invoke$lambda$14$lambda$7(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }
}
