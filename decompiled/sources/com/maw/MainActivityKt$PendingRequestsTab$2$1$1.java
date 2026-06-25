package com.maw;

import android.graphics.Bitmap;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
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
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.BadgeKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonKt;
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
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.AndroidImageBitmap_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.ContentScale;
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
import androidx.compose.ui.window.AndroidDialog_androidKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$PendingRequestsTab$2$1$1 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
    final /* synthetic */ PendingProvider $pp;
    final /* synthetic */ MainViewModel $vm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$PendingRequestsTab$2$1$1(PendingProvider pendingProvider, MainViewModel mainViewModel) {
        super(3);
        this.$pp = pendingProvider;
        this.$vm = mainViewModel;
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
        Function0<ComposeUiNode> function04;
        String str;
        Composer $composer2;
        MainViewModel mainViewModel;
        String str2;
        Function0<ComposeUiNode> function05;
        Function0<ComposeUiNode> function06;
        Function0<ComposeUiNode> function07;
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C8421@416262L11466:MainActivity.kt#foq9o6");
        if (($changed & 81) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
            return;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(876511173, $changed, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8421)");
        }
        Modifier modifier$iv = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
        final PendingProvider pendingProvider = this.$pp;
        MainViewModel mainViewModel2 = this.$vm;
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
        ComposerKt.sourceInformationMarkerStart($composer, 194505565, "C8422@416331L478,8428@416834L40,8429@416899L90,8430@417014L82,8431@417121L95,8432@417241L106,8433@417372L91,8435@417489L41,8438@417649L8882,8571@426557L41,8572@426623L1083:MainActivity.kt#foq9o6");
        Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getSpaceBetween();
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
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        int i4 = ((438 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -286006490, "C8423@416499L90,8424@416618L165:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g(pendingProvider.getName(), (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(13), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199680, 0, 131026);
        BadgeKt.m1244BadgeeopBjH0(null, AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, ComposableSingletons$MainActivityKt.INSTANCE.m6427getLambda34$app_debug(), $composer, 3072, 5);
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(6)), $composer, 6);
        TextKt.m2124Text4IGK_g("📌 الرقم المهني الموحد للتواصل: " + pendingProvider.getPhone(), (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 0, 131058);
        TextKt.m2124Text4IGK_g("📁 تخصص القسم: " + pendingProvider.getCategory(), (Modifier) null, AppTheme.INSTANCE.m6263getGrayText0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 0, 131058);
        TextKt.m2124Text4IGK_g("📍 عنوان السكن والمنطقة: " + pendingProvider.getArea() + " (" + pendingProvider.getCity() + ")", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 0, 131058);
        TextKt.m2124Text4IGK_g("📝 نبذة ومهارات الكادر: " + pendingProvider.getDescription(), (Modifier) null, Color.INSTANCE.m3438getLightGray0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 2, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 3072, 122866);
        TextKt.m2124Text4IGK_g("👤 الجنس والمعرف السحابي للطلب: " + pendingProvider.getDeviceId(), (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3456, 0, 131058);
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(10)), $composer, 6);
        Modifier modifier$iv3 = PaddingKt.m564paddingVpY3zN4$default(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.0f, Dp.m5733constructorimpl(8), 1, null);
        Arrangement.Horizontal horizontalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
        $composer.startReplaceableGroup(693286680);
        ComposerKt.sourceInformation($composer, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
        Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getTop();
        int $i$f$Row = ((54 >> 3) & 14) | ((54 >> 3) & 112);
        MeasurePolicy measurePolicy$iv3 = RowKt.rowMeasurePolicy(horizontalArrangement$iv2, verticalAlignment$iv2, $composer, $i$f$Row);
        int $changed$iv$iv3 = (54 << 3) & 112;
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
        ComposerKt.sourceInformationMarkerStart($composer, -326681643, "C92@4661L9:Row.kt#2w3rfo");
        int i6 = ((54 >> 6) & 112) | 6;
        RowScope $this$invoke_u24lambda_u2417_u24lambda_u2415 = RowScopeInstance.INSTANCE;
        ComposerKt.sourceInformationMarkerStart($composer, -286004975, "C8445@418014L4207,8508@422314L4191:MainActivity.kt#foq9o6");
        Modifier modifier$iv4 = RowScope.weight$default($this$invoke_u24lambda_u2417_u24lambda_u2415, Modifier.INSTANCE, 1.0f, false, 2, null);
        Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getCenterHorizontally();
        $composer.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.getTop();
        int $i$f$Layout = ((384 >> 3) & 14) | ((384 >> 3) & 112);
        MeasurePolicy measurePolicy$iv4 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer, $i$f$Layout);
        int $changed$iv$iv4 = (384 << 3) & 112;
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
            function04 = constructor4;
            $composer.createNode(function04);
        } else {
            function04 = constructor4;
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
        int i8 = ((384 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -872859763, "C8449@418233L103,8450@418369L40,8451@418463L42:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("صورة مقدم الطلب 👤", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131026);
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer, 6);
        final Bitmap personalBitmap = MainActivityKt.rememberBase64Bitmap(pendingProvider.getSelfieImageBase64(), $composer, 0);
        if (personalBitmap != null) {
            $composer.startReplaceableGroup(-872859430);
            ComposerKt.sourceInformation($composer, "8453@418624L34,8458@418936L23,8454@418695L917,8470@419762L24,8470@419736L1715");
            $composer.startReplaceableGroup(-872859372);
            ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
            mainViewModel = mainViewModel2;
            Object it$iv = $composer.rememberedValue();
            str = "C92@4661L9:Row.kt#2w3rfo";
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                str2 = "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo";
                value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
                $composer.updateRememberedValue(value$iv3);
            } else {
                str2 = "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo";
                value$iv3 = it$iv;
            }
            final MutableState showLightbox$delegate = (MutableState) value$iv3;
            $composer.endReplaceableGroup();
            $composer2 = $composer;
            Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(100)), 0.0f, 1, null);
            $composer.startReplaceableGroup(-872859060);
            ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
            Object it$iv2 = $composer.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv4 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$1$1
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
                        MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$7$lambda$3(showLightbox$delegate, true);
                    }
                };
                $composer.updateRememberedValue(value$iv4);
            } else {
                value$iv4 = it$iv2;
            }
            $composer.endReplaceableGroup();
            CardKt.Card(ClickableKt.m244clickableXHw0xAI$default(modifierFillMaxWidth$default, false, null, null, (Function0) value$iv4, 7, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8)), null, null, BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L)), ComposableLambdaKt.composableLambda($composer, 940532249, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card2, Composer $composer3, int $changed2) {
                    Intrinsics.checkNotNullParameter(Card2, "$this$Card");
                    ComposerKt.sourceInformation($composer3, "C8462@419202L372:MainActivity.kt#foq9o6");
                    if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(940532249, $changed2, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8462)");
                        }
                        ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(personalBitmap), "Personal Photo", SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, ContentScale.INSTANCE.getCrop(), 0.0f, null, 0, $composer3, 25016, 232);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), $composer, 221184, 12);
            if (invoke$lambda$17$lambda$15$lambda$7$lambda$2(showLightbox$delegate)) {
                $composer.startReplaceableGroup(-872858234);
                ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
                Object it$iv3 = $composer.rememberedValue();
                if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                    value$iv5 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$3$1
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
                            MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$7$lambda$3(showLightbox$delegate, false);
                        }
                    };
                    $composer.updateRememberedValue(value$iv5);
                } else {
                    value$iv5 = it$iv3;
                }
                $composer.endReplaceableGroup();
                AndroidDialog_androidKt.Dialog((Function0) value$iv5, null, ComposableLambdaKt.composableLambda($composer, -2058888487, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                        invoke(composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer $composer3, int $changed2) {
                        ComposerKt.sourceInformation($composer3, "C8471@419834L1575:MainActivity.kt#foq9o6");
                        if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-2058888487, $changed2, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8471)");
                            }
                            Modifier modifierFillMaxHeight = SizeKt.fillMaxHeight(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.7f);
                            RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12));
                            BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU());
                            final Bitmap bitmap = personalBitmap;
                            final MutableState<Boolean> mutableState = showLightbox$delegate;
                            CardKt.Card(modifierFillMaxHeight, roundedCornerShapeM831RoundedCornerShape0680j_4, null, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer3, 1179761099, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$4.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope Card2, Composer $composer4, int $changed3) {
                                    Object value$iv6;
                                    Intrinsics.checkNotNullParameter(Card2, "$this$Card");
                                    ComposerKt.sourceInformation($composer4, "C8476@420220L1143:MainActivity.kt#foq9o6");
                                    if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(1179761099, $changed3, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8476)");
                                        }
                                        Modifier modifier$iv5 = SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null);
                                        Bitmap bitmap2 = bitmap;
                                        final MutableState<Boolean> mutableState2 = mutableState;
                                        $composer4.startReplaceableGroup(733328855);
                                        ComposerKt.sourceInformation($composer4, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                                        Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
                                        MeasurePolicy measurePolicy$iv5 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer4, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                                        int $changed$iv$iv5 = (6 << 3) & 112;
                                        $composer4.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                                        CompositionLocalMap localMap$iv$iv5 = $composer4.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifier$iv5);
                                        int $changed$iv$iv$iv5 = (($changed$iv$iv5 << 9) & 7168) | 6;
                                        if (!($composer4.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer4.startReusableNode();
                                        if ($composer4.getInserting()) {
                                            $composer4.createNode(constructor5);
                                        } else {
                                            $composer4.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer4);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                                            $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                                            $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
                                        }
                                        function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
                                        $composer4.startReplaceableGroup(2058660585);
                                        int i9 = ($changed$iv$iv$iv5 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer4, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                                        int i10 = ((6 >> 6) & 112) | 6;
                                        BoxScope $this$invoke_u24lambda_u241 = BoxScopeInstance.INSTANCE;
                                        ComposerKt.sourceInformationMarkerStart($composer4, 269987728, "C8477@420313L432,8484@420876L24,8483@420798L515:MainActivity.kt#foq9o6");
                                        ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(bitmap2), "Enlarged Selfie", SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, ContentScale.INSTANCE.getFit(), 0.0f, null, 0, $composer4, 25016, 232);
                                        $composer4.startReplaceableGroup(269988291);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):MainActivity.kt#9igjgp");
                                        Object it$iv4 = $composer4.rememberedValue();
                                        if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                                            value$iv6 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$1$4$1$1$1$1
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
                                                    MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$7$lambda$3(mutableState2, false);
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv6);
                                        } else {
                                            value$iv6 = it$iv4;
                                        }
                                        $composer4.endReplaceableGroup();
                                        Modifier modifierM562padding3ABfNKs = PaddingKt.m562padding3ABfNKs($this$invoke_u24lambda_u241.align(Modifier.INSTANCE, Alignment.INSTANCE.getTopEnd()), Dp.m5733constructorimpl(8));
                                        long jM3432getBlack0d7_KjU = Color.INSTANCE.m3432getBlack0d7_KjU();
                                        IconButtonKt.IconButton((Function0) value$iv6, BackgroundKt.m209backgroundbw27NRU(modifierM562padding3ABfNKs, Color.m3404copywmQWz5c(jM3432getBlack0d7_KjU, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM3432getBlack0d7_KjU) : 0.6f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM3432getBlack0d7_KjU) : 0.0f), RoundedCornerShapeKt.getCircleShape()), false, null, null, ComposableSingletons$MainActivityKt.INSTANCE.m6428getLambda35$app_debug(), $composer4, 196614, 28);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        $composer4.endReplaceableGroup();
                                        $composer4.endNode();
                                        $composer4.endReplaceableGroup();
                                        $composer4.endReplaceableGroup();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                            return;
                                        }
                                        return;
                                    }
                                    $composer4.skipToGroupEnd();
                                }
                            }), $composer3, 196614, 12);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                }), $composer, 390, 2);
            }
            $composer.endReplaceableGroup();
        } else {
            str = "C92@4661L9:Row.kt#2w3rfo";
            $composer2 = $composer;
            mainViewModel = mainViewModel2;
            str2 = "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo";
            $composer.startReplaceableGroup(-872856467);
            ComposerKt.sourceInformation($composer, "8494@421567L590");
            Modifier modifier$iv5 = BackgroundKt.m210backgroundbw27NRU$default(ClipKt.clip(SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(100)), 0.0f, 1, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8))), ColorKt.Color(4279181861L), null, 2, null);
            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
            $composer.startReplaceableGroup(733328855);
            ComposerKt.sourceInformation($composer, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
            MeasurePolicy measurePolicy$iv5 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer, ((48 >> 3) & 14) | ((48 >> 3) & 112));
            int $changed$iv$iv5 = (48 << 3) & 112;
            $composer.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
            CompositionLocalMap localMap$iv$iv5 = $composer.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifier$iv5);
            int $changed$iv$iv$iv5 = (($changed$iv$iv5 << 9) & 7168) | 6;
            if (!($composer.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer.startReusableNode();
            if ($composer.getInserting()) {
                function05 = constructor5;
                $composer.createNode(function05);
            } else {
                function05 = constructor5;
                $composer.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
            }
            function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
            $composer.startReplaceableGroup(2058660585);
            int i9 = ($changed$iv$iv$iv5 >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i10 = ((48 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -1294325087, "C8502@422060L59:MainActivity.kt#foq9o6");
            TextKt.m2124Text4IGK_g("لم يتم الرفع ❌", (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 0, 131058);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            $composer.endReplaceableGroup();
            $composer.endNode();
            $composer.endReplaceableGroup();
            $composer.endReplaceableGroup();
            $composer.endReplaceableGroup();
        }
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        Modifier modifier$iv6 = RowScope.weight$default($this$invoke_u24lambda_u2417_u24lambda_u2415, Modifier.INSTANCE, 1.0f, false, 2, null);
        Alignment.Horizontal horizontalAlignment$iv3 = Alignment.INSTANCE.getCenterHorizontally();
        $composer.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Arrangement.Vertical verticalArrangement$iv3 = Arrangement.INSTANCE.getTop();
        MeasurePolicy measurePolicy$iv6 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv3, horizontalAlignment$iv3, $composer, ((384 >> 3) & 14) | ((384 >> 3) & 112));
        int $changed$iv$iv6 = (384 << 3) & 112;
        $composer.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv6 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
        CompositionLocalMap localMap$iv$iv6 = $composer.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor6 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf6 = LayoutKt.modifierMaterializerOf(modifier$iv6);
        int $changed$iv$iv$iv6 = (($changed$iv$iv6 << 9) & 7168) | 6;
        if (!($composer.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer.startReusableNode();
        if ($composer.getInserting()) {
            function06 = constructor6;
            $composer.createNode(function06);
        } else {
            function06 = constructor6;
            $composer.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv6 = Updater.m2936constructorimpl($composer);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, measurePolicy$iv6, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, localMap$iv$iv6, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash6 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv6.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv6.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv6))) {
            $this$Layout_u24lambda_u240$iv$iv6.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv6));
            $this$Layout_u24lambda_u240$iv$iv6.apply(Integer.valueOf(compositeKeyHash$iv$iv6), setCompositeKeyHash6);
        }
        function3ModifierMaterializerOf6.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv6 >> 3) & 112));
        $composer.startReplaceableGroup(2058660585);
        int i11 = ($changed$iv$iv$iv6 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer, 276693656, "C79@3979L9:Column.kt#2w3rfo");
        ColumnScopeInstance columnScopeInstance3 = ColumnScopeInstance.INSTANCE;
        int i12 = ((384 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer, -872855463, "C8512@422533L107,8513@422673L40,8514@422761L46:MainActivity.kt#foq9o6");
        TextKt.m2124Text4IGK_g("صورة الهوية الوطنية 🪪", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131026);
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer, 6);
        final Bitmap idBitmap = MainActivityKt.rememberBase64Bitmap(pendingProvider.getNationalIdImageBase64(), $composer, 0);
        if (idBitmap != null) {
            $composer.startReplaceableGroup(-872855134);
            ComposerKt.sourceInformation($composer, "8516@422920L34,8521@423232L23,8517@422991L910,8533@424051L24,8533@424025L1710");
            $composer.startReplaceableGroup(-872855076);
            ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
            Object value$iv6 = $composer.rememberedValue();
            if (value$iv6 == Composer.INSTANCE.getEmpty()) {
                value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
                $composer.updateRememberedValue(value$iv6);
            }
            final MutableState showLightbox$delegate2 = (MutableState) value$iv6;
            $composer.endReplaceableGroup();
            Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(100)), 0.0f, 1, null);
            $composer.startReplaceableGroup(-872854764);
            ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
            Object it$iv4 = $composer.rememberedValue();
            if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                value$iv = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$1$1
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
                        MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$14$lambda$10(showLightbox$delegate2, true);
                    }
                };
                $composer.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv4;
            }
            $composer.endReplaceableGroup();
            CardKt.Card(ClickableKt.m244clickableXHw0xAI$default(modifierFillMaxWidth$default2, false, null, null, (Function0) value$iv, 7, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8)), null, null, BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L)), ComposableLambdaKt.composableLambda($composer, 1497464400, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card2, Composer $composer3, int $changed2) {
                    Intrinsics.checkNotNullParameter(Card2, "$this$Card");
                    ComposerKt.sourceInformation($composer3, "C8525@423498L365:MainActivity.kt#foq9o6");
                    if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1497464400, $changed2, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8525)");
                        }
                        ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(idBitmap), "ID Card Photo", SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, ContentScale.INSTANCE.getCrop(), 0.0f, null, 0, $composer3, 25016, 232);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), $composer, 221184, 12);
            if (invoke$lambda$17$lambda$15$lambda$14$lambda$9(showLightbox$delegate2)) {
                $composer.startReplaceableGroup(-872853945);
                ComposerKt.sourceInformation($composer, "CC(remember):MainActivity.kt#9igjgp");
                Object it$iv5 = $composer.rememberedValue();
                if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                    value$iv2 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$3$1
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
                            MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$14$lambda$10(showLightbox$delegate2, false);
                        }
                    };
                    $composer.updateRememberedValue(value$iv2);
                } else {
                    value$iv2 = it$iv5;
                }
                $composer.endReplaceableGroup();
                AndroidDialog_androidKt.Dialog((Function0) value$iv2, null, ComposableLambdaKt.composableLambda($composer, 977192720, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                        invoke(composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer $composer3, int $changed2) {
                        ComposerKt.sourceInformation($composer3, "C8534@424123L1570:MainActivity.kt#foq9o6");
                        if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(977192720, $changed2, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8534)");
                            }
                            Modifier modifierFillMaxHeight = SizeKt.fillMaxHeight(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.7f);
                            RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12));
                            BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU());
                            final Bitmap bitmap = idBitmap;
                            final MutableState<Boolean> mutableState = showLightbox$delegate2;
                            CardKt.Card(modifierFillMaxHeight, roundedCornerShapeM831RoundedCornerShape0680j_4, null, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer3, -531844734, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$4.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope Card2, Composer $composer4, int $changed3) {
                                    Object value$iv7;
                                    Intrinsics.checkNotNullParameter(Card2, "$this$Card");
                                    ComposerKt.sourceInformation($composer4, "C8539@424509L1138:MainActivity.kt#foq9o6");
                                    if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-531844734, $changed3, -1, "com.maw.PendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MainActivity.kt:8539)");
                                        }
                                        Modifier modifier$iv7 = SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null);
                                        Bitmap bitmap2 = bitmap;
                                        final MutableState<Boolean> mutableState2 = mutableState;
                                        $composer4.startReplaceableGroup(733328855);
                                        ComposerKt.sourceInformation($composer4, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                                        Alignment contentAlignment$iv2 = Alignment.INSTANCE.getTopStart();
                                        MeasurePolicy measurePolicy$iv7 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv2, false, $composer4, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                                        int $changed$iv$iv7 = (6 << 3) & 112;
                                        $composer4.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv7 = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                                        CompositionLocalMap localMap$iv$iv7 = $composer4.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor7 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf7 = LayoutKt.modifierMaterializerOf(modifier$iv7);
                                        int $changed$iv$iv$iv7 = (($changed$iv$iv7 << 9) & 7168) | 6;
                                        if (!($composer4.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer4.startReusableNode();
                                        if ($composer4.getInserting()) {
                                            $composer4.createNode(constructor7);
                                        } else {
                                            $composer4.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv7 = Updater.m2936constructorimpl($composer4);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, measurePolicy$iv7, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, localMap$iv$iv7, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash7 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv7.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv7.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv7))) {
                                            $this$Layout_u24lambda_u240$iv$iv7.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv7));
                                            $this$Layout_u24lambda_u240$iv$iv7.apply(Integer.valueOf(compositeKeyHash$iv$iv7), setCompositeKeyHash7);
                                        }
                                        function3ModifierMaterializerOf7.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv7 >> 3) & 112));
                                        $composer4.startReplaceableGroup(2058660585);
                                        int i13 = ($changed$iv$iv$iv7 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer4, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                                        int i14 = ((6 >> 6) & 112) | 6;
                                        BoxScope $this$invoke_u24lambda_u241 = BoxScopeInstance.INSTANCE;
                                        ComposerKt.sourceInformationMarkerStart($composer4, 269992017, "C8540@424602L427,8547@425160L24,8546@425082L515:MainActivity.kt#foq9o6");
                                        ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(bitmap2), "Enlarged ID Card", SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, ContentScale.INSTANCE.getFit(), 0.0f, null, 0, $composer4, 25016, 232);
                                        $composer4.startReplaceableGroup(269992575);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):MainActivity.kt#9igjgp");
                                        Object it$iv6 = $composer4.rememberedValue();
                                        if (it$iv6 == Composer.INSTANCE.getEmpty()) {
                                            value$iv7 = (Function0) new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$2$2$4$1$1$1$1
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
                                                    MainActivityKt$PendingRequestsTab$2$1$1.invoke$lambda$17$lambda$15$lambda$14$lambda$10(mutableState2, false);
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv7);
                                        } else {
                                            value$iv7 = it$iv6;
                                        }
                                        $composer4.endReplaceableGroup();
                                        Modifier modifierM562padding3ABfNKs = PaddingKt.m562padding3ABfNKs($this$invoke_u24lambda_u241.align(Modifier.INSTANCE, Alignment.INSTANCE.getTopEnd()), Dp.m5733constructorimpl(8));
                                        long jM3432getBlack0d7_KjU = Color.INSTANCE.m3432getBlack0d7_KjU();
                                        IconButtonKt.IconButton((Function0) value$iv7, BackgroundKt.m209backgroundbw27NRU(modifierM562padding3ABfNKs, Color.m3404copywmQWz5c(jM3432getBlack0d7_KjU, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM3432getBlack0d7_KjU) : 0.6f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM3432getBlack0d7_KjU) : 0.0f), RoundedCornerShapeKt.getCircleShape()), false, null, null, ComposableSingletons$MainActivityKt.INSTANCE.m6429getLambda36$app_debug(), $composer4, 196614, 28);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        $composer4.endReplaceableGroup();
                                        $composer4.endNode();
                                        $composer4.endReplaceableGroup();
                                        $composer4.endReplaceableGroup();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                            return;
                                        }
                                        return;
                                    }
                                    $composer4.skipToGroupEnd();
                                }
                            }), $composer3, 196614, 12);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                }), $composer, 390, 2);
            }
            $composer.endReplaceableGroup();
        } else {
            $composer.startReplaceableGroup(-872852183);
            ComposerKt.sourceInformation($composer, "8557@425851L590");
            Modifier modifier$iv7 = BackgroundKt.m210backgroundbw27NRU$default(ClipKt.clip(SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(100)), 0.0f, 1, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8))), ColorKt.Color(4279181861L), null, 2, null);
            Alignment contentAlignment$iv2 = Alignment.INSTANCE.getCenter();
            $composer.startReplaceableGroup(733328855);
            ComposerKt.sourceInformation($composer, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
            MeasurePolicy measurePolicy$iv7 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv2, false, $composer, ((48 >> 3) & 14) | ((48 >> 3) & 112));
            int $changed$iv$iv7 = (48 << 3) & 112;
            $composer.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv7 = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
            CompositionLocalMap localMap$iv$iv7 = $composer.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor7 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf7 = LayoutKt.modifierMaterializerOf(modifier$iv7);
            int $changed$iv$iv$iv7 = (($changed$iv$iv7 << 9) & 7168) | 6;
            if (!($composer.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer.startReusableNode();
            if ($composer.getInserting()) {
                function07 = constructor7;
                $composer.createNode(function07);
            } else {
                function07 = constructor7;
                $composer.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv7 = Updater.m2936constructorimpl($composer);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, measurePolicy$iv7, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, localMap$iv$iv7, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash7 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv7.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv7.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv7))) {
                $this$Layout_u24lambda_u240$iv$iv7.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv7));
                $this$Layout_u24lambda_u240$iv$iv7.apply(Integer.valueOf(compositeKeyHash$iv$iv7), setCompositeKeyHash7);
            }
            function3ModifierMaterializerOf7.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer)), $composer, Integer.valueOf(($changed$iv$iv$iv7 >> 3) & 112));
            $composer.startReplaceableGroup(2058660585);
            int i13 = ($changed$iv$iv$iv7 >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
            BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
            int i14 = ((48 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -1294320803, "C8565@426344L59:MainActivity.kt#foq9o6");
            TextKt.m2124Text4IGK_g("لم يتم الرفع ❌", (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 0, 131058);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            $composer.endReplaceableGroup();
            $composer.endNode();
            $composer.endReplaceableGroup();
            $composer.endReplaceableGroup();
            $composer.endReplaceableGroup();
        }
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer2.endReplaceableGroup();
        $composer2.endNode();
        $composer2.endReplaceableGroup();
        $composer2.endReplaceableGroup();
        Composer $composer3 = $composer2;
        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(10)), $composer3, 6);
        Arrangement.HorizontalOrVertical horizontalOrVerticalM471spacedBy0680j_4 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
        Modifier modifierFillMaxWidth$default3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        $composer3.startReplaceableGroup(693286680);
        ComposerKt.sourceInformation($composer3, str2);
        Alignment.Vertical verticalAlignment$iv3 = Alignment.INSTANCE.getTop();
        MeasurePolicy measurePolicy$iv8 = RowKt.rowMeasurePolicy(horizontalOrVerticalM471spacedBy0680j_4, verticalAlignment$iv3, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
        int $changed$iv$iv8 = (54 << 3) & 112;
        $composer3.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv8 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
        CompositionLocalMap localMap$iv$iv8 = $composer3.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor8 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf8 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default3);
        int $changed$iv$iv$iv8 = (($changed$iv$iv8 << 9) & 7168) | 6;
        if (!($composer3.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer3.startReusableNode();
        if ($composer3.getInserting()) {
            $composer3.createNode(constructor8);
        } else {
            $composer3.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv8 = Updater.m2936constructorimpl($composer3);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, measurePolicy$iv8, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, localMap$iv$iv8, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash8 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv8.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv8.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv8))) {
            $this$Layout_u24lambda_u240$iv$iv8.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv8));
            $this$Layout_u24lambda_u240$iv$iv8.apply(Integer.valueOf(compositeKeyHash$iv$iv8), setCompositeKeyHash8);
        }
        function3ModifierMaterializerOf8.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv8 >> 3) & 112));
        $composer3.startReplaceableGroup(2058660585);
        int i15 = ($changed$iv$iv$iv8 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, str);
        int i16 = ((54 >> 6) & 112) | 6;
        RowScope $this$invoke_u24lambda_u2417_u24lambda_u2416 = RowScopeInstance.INSTANCE;
        ComposerKt.sourceInformationMarkerStart($composer3, -285996244, "C8575@426896L48,8573@426745L439,8582@427397L50,8580@427213L467:MainActivity.kt#foq9o6");
        final MainViewModel mainViewModel3 = mainViewModel;
        ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$3$1
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
                mainViewModel3.approveProviderRequest(pendingProvider, "الأدمن");
            }
        }, SizeKt.m597height3ABfNKs(RowScope.weight$default($this$invoke_u24lambda_u2417_u24lambda_u2416, Modifier.INSTANCE, 1.0f, false, 2, null), Dp.m5733constructorimpl(36)), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4281236786L), 0L, 0L, 0L, $composer3, (ButtonDefaults.$stable << 12) | 6, 14), null, null, null, null, ComposableSingletons$MainActivityKt.INSTANCE.m6430getLambda37$app_debug(), $composer3, 805306368, 492);
        ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.MainActivityKt$PendingRequestsTab$2$1$1$1$3$2
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
                mainViewModel3.rejectProviderRequest(pendingProvider.getId(), "المستندات والصورة غير واضحة", "الأدمن");
            }
        }, SizeKt.m597height3ABfNKs(RowScope.weight$default($this$invoke_u24lambda_u2417_u24lambda_u2416, Modifier.INSTANCE, 0.8f, false, 2, null), Dp.m5733constructorimpl(36)), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer3, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableSingletons$MainActivityKt.INSTANCE.m6431getLambda38$app_debug(), $composer3, 805306368, 492);
        ComposerKt.sourceInformationMarkerEnd($composer3);
        ComposerKt.sourceInformationMarkerEnd($composer3);
        $composer3.endReplaceableGroup();
        $composer3.endNode();
        $composer3.endReplaceableGroup();
        $composer3.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer3);
        ComposerKt.sourceInformationMarkerEnd($composer);
        $composer.endReplaceableGroup();
        $composer.endNode();
        $composer.endReplaceableGroup();
        $composer.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    private static final boolean invoke$lambda$17$lambda$15$lambda$7$lambda$2(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$17$lambda$15$lambda$7$lambda$3(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$17$lambda$15$lambda$14$lambda$10(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    private static final boolean invoke$lambda$17$lambda$15$lambda$14$lambda$9(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }
}
