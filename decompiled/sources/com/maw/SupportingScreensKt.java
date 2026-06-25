package com.maw;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import androidx.autofill.HintConstants;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.LockKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.FlowExtKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: SupportingScreens.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\u001a-\u0010\u0000\u001a\u00020\u00012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007¢\u0006\u0002\u0010\u0005\u001a'\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007¢\u0006\u0002\u0010\r\u001a'\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0014\u001a\u0016\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u0016²\u0006\n\u0010\u0017\u001a\u00020\u0018X\u008a\u0084\u0002²\u0006\n\u0010\t\u001a\u00020\nX\u008a\u008e\u0002"}, d2 = {"NetworkStatus", "", "onOnline", "Lkotlin/Function0;", "onOffline", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V", "PhoneDisplayWithPrivacy", HintConstants.AUTOFILL_HINT_PHONE_NUMBER, "", "isOnline", "", "modifier", "Landroidx/compose/ui/Modifier;", "(Ljava/lang/String;ZLandroidx/compose/ui/Modifier;Landroidx/compose/runtime/Composer;II)V", "getPhoneNumberWithFallback", "context", "Landroid/content/Context;", "vm", "Lcom/maw/MainViewModel;", "defaultNumber", "(Landroid/content/Context;Lcom/maw/MainViewModel;Ljava/lang/String;Landroidx/compose/runtime/Composer;II)Ljava/lang/String;", "savePhoneForOffline", "app_debug", "settings", "Lcom/maw/AppSettings;"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SupportingScreensKt {
    public static final String getPhoneNumberWithFallback(Context context, MainViewModel vm, String defaultNumber, Composer $composer, int $changed, int i) {
        String str;
        Object value$iv;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(vm, "vm");
        $composer.startReplaceableGroup(2094533381);
        ComposerKt.sourceInformation($composer, "C(getPhoneNumberWithFallback)P(!1,2)37@1287L29,39@1333L411:SupportingScreens.kt#foq9o6");
        if ((i & 4) != 0) {
            defaultNumber = "wam777644";
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(2094533381, $changed, -1, "com.maw.getPhoneNumberWithFallback (SupportingScreens.kt:36)");
        }
        State settings$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getSettings(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer, 8, 7);
        AppSettings phoneNumberWithFallback$lambda$0 = getPhoneNumberWithFallback$lambda$0(settings$delegate);
        $composer.startReplaceableGroup(-926711212);
        ComposerKt.sourceInformation($composer, "CC(remember):SupportingScreens.kt#9igjgp");
        boolean invalid$iv = $composer.changed(phoneNumberWithFallback$lambda$0) | $composer.changed(context);
        Object it$iv = $composer.rememberedValue();
        if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
            if (MainActivityKt.isNetworkAvailable(context)) {
                String aboutPhone = getPhoneNumberWithFallback$lambda$0(settings$delegate).getAboutPhone();
                if (StringsKt.isBlank(aboutPhone)) {
                    aboutPhone = defaultNumber;
                }
                str = aboutPhone;
            } else {
                SharedPreferences prefs = context.getSharedPreferences("app_prefs", 0);
                String string = prefs.getString("offline_phone", defaultNumber);
                str = string == null ? defaultNumber : string;
                Intrinsics.checkNotNull(str);
            }
            value$iv = str;
            $composer.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        String str2 = (String) value$iv;
        $composer.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        $composer.endReplaceableGroup();
        return str2;
    }

    private static final AppSettings getPhoneNumberWithFallback$lambda$0(State<AppSettings> state) {
        Object thisObj$iv = state.getValue();
        return (AppSettings) thisObj$iv;
    }

    public static final void savePhoneForOffline(Context context, String phoneNumber) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", 0);
        prefs.edit().putString("offline_phone", phoneNumber).apply();
    }

    public static final void PhoneDisplayWithPrivacy(final String phoneNumber, final boolean isOnline, Modifier modifier, Composer $composer, final int $changed, final int i) {
        Modifier modifier2;
        Modifier modifier3;
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        Composer $composer2 = $composer.startRestartGroup(-982406262);
        ComposerKt.sourceInformation($composer2, "C(PhoneDisplayWithPrivacy)P(2)67@2180L727:SupportingScreens.kt#foq9o6");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(phoneNumber) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer2.changed(isOnline) ? 32 : 16;
        }
        int i2 = i & 4;
        if (i2 != 0) {
            $dirty |= 384;
            modifier2 = modifier;
        } else if (($changed & 896) == 0) {
            modifier2 = modifier;
            $dirty |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 731) == 146 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier3 = modifier2;
        } else {
            Modifier.Companion modifier4 = i2 != 0 ? Modifier.INSTANCE : modifier2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-982406262, $dirty2, -1, "com.maw.PhoneDisplayWithPrivacy (SupportingScreens.kt:66)");
            }
            Alignment.Vertical centerVertically = Alignment.INSTANCE.getCenterVertically();
            int $changed$iv = (($dirty2 >> 6) & 14) | 384;
            $composer2.startReplaceableGroup(693286680);
            ComposerKt.sourceInformation($composer2, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
            Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getStart();
            MeasurePolicy measurePolicy$iv = RowKt.rowMeasurePolicy(horizontalArrangement$iv, centerVertically, $composer2, (($changed$iv >> 3) & 14) | (($changed$iv >> 3) & 112));
            int $changed$iv$iv = ($changed$iv << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier4);
            Modifier modifier5 = modifier4;
            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
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
            ComposerKt.sourceInformationMarkerStart($composer2, -326681643, "C92@4661L9:Row.kt#2w3rfo");
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            int i4 = (($changed$iv >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer2, 1596059032, "C71@2285L179:SupportingScreens.kt#foq9o6");
            String str = isOnline ? "📞 " + phoneNumber : "📱 wam777644";
            Color.Companion companion = Color.INSTANCE;
            TextKt.m2124Text4IGK_g(str, (Modifier) null, isOnline ? companion.m3443getWhite0d7_KjU() : companion.m3436getGray0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 3072, 0, 131058);
            $composer2.startReplaceableGroup(366144316);
            ComposerKt.sourceInformation($composer2, "78@2511L191,84@2715L39,85@2767L124");
            if (!isOnline) {
                IconKt.m1597Iconww6aTOc(LockKt.getLock(Icons.INSTANCE.getDefault()), "Offline Mode", SizeKt.m611size3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12)), Color.INSTANCE.m3436getGray0d7_KjU(), $composer2, 3504, 0);
                SpacerKt.Spacer(SizeKt.m616width3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer2, 6);
                TextKt.m2124Text4IGK_g("(غير متصل)", (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(8), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 3462, 0, 131058);
            }
            $composer2.endReplaceableGroup();
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier5;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.SupportingScreensKt.PhoneDisplayWithPrivacy.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i5) {
                    SupportingScreensKt.PhoneDisplayWithPrivacy(phoneNumber, isOnline, modifier6, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    public static final void NetworkStatus(final Function0<Unit> function0, final Function0<Unit> function02, Composer $composer, final int $changed, final int i) {
        Object value$iv;
        Composer $composer2 = $composer.startRestartGroup(42424458);
        ComposerKt.sourceInformation($composer2, "C(NetworkStatus)P(1)102@3076L7,103@3104L64,105@3178L856,130@4044L329:SupportingScreens.kt#foq9o6");
        int $dirty = $changed;
        int i2 = i & 1;
        if (i2 != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer2.changedInstance(function0) ? 4 : 2;
        }
        int i3 = i & 2;
        if (i3 != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer2.changedInstance(function02) ? 32 : 16;
        }
        if (($dirty & 91) != 18 || !$composer2.getSkipping()) {
            if (i2 != 0) {
                Function0 onOnline = new Function0<Unit>() { // from class: com.maw.SupportingScreensKt.NetworkStatus.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                function0 = onOnline;
            }
            if (i3 != 0) {
                Function0 onOffline = new Function0<Unit>() { // from class: com.maw.SupportingScreensKt.NetworkStatus.2
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                function02 = onOffline;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(42424458, $dirty, -1, "com.maw.NetworkStatus (SupportingScreens.kt:101)");
            }
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final Context context = (Context) objConsume;
            $composer2.startReplaceableGroup(1089219064);
            ComposerKt.sourceInformation($composer2, "CC(remember):SupportingScreens.kt#9igjgp");
            Object it$iv = $composer2.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(MainActivityKt.isNetworkAvailable(context)), null, 2, null);
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState isOnline$delegate = (MutableState) value$iv;
            $composer2.endReplaceableGroup();
            EffectsKt.DisposableEffect(Unit.INSTANCE, new Function1<DisposableEffectScope, DisposableEffectResult>() { // from class: com.maw.SupportingScreensKt.NetworkStatus.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r1v2, types: [com.maw.SupportingScreensKt$NetworkStatus$3$callback$1] */
                @Override // kotlin.jvm.functions.Function1
                public final DisposableEffectResult invoke(DisposableEffectScope DisposableEffect) {
                    Intrinsics.checkNotNullParameter(DisposableEffect, "$this$DisposableEffect");
                    Object systemService = context.getSystemService("connectivity");
                    Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                    final ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
                    final Function0<Unit> function03 = function0;
                    final MutableState<Boolean> mutableState = isOnline$delegate;
                    final Function0<Unit> function04 = function02;
                    final ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.maw.SupportingScreensKt$NetworkStatus$3$callback$1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onAvailable(Network network) {
                            Intrinsics.checkNotNullParameter(network, "network");
                            SupportingScreensKt.NetworkStatus$lambda$6(mutableState, true);
                            function03.invoke();
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLost(Network network) {
                            Intrinsics.checkNotNullParameter(network, "network");
                            SupportingScreensKt.NetworkStatus$lambda$6(mutableState, false);
                            function04.invoke();
                        }
                    };
                    NetworkRequest networkRequest = new NetworkRequest.Builder().addCapability(12).build();
                    connectivityManager.registerNetworkCallback(networkRequest, (ConnectivityManager.NetworkCallback) r1);
                    return new DisposableEffectResult() { // from class: com.maw.SupportingScreensKt$NetworkStatus$3$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public void dispose() {
                            connectivityManager.unregisterNetworkCallback(r1);
                        }
                    };
                }
            }, $composer2, 6);
            EffectsKt.LaunchedEffect(Unit.INSTANCE, new AnonymousClass4(context, function0, function02, isOnline$delegate, null), $composer2, 70);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.SupportingScreensKt.NetworkStatus.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i4) {
                    SupportingScreensKt.NetworkStatus(function0, function02, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean NetworkStatus$lambda$5(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void NetworkStatus$lambda$6(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: renamed from: com.maw.SupportingScreensKt$NetworkStatus$4, reason: invalid class name */
    /* JADX INFO: compiled from: SupportingScreens.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.SupportingScreensKt$NetworkStatus$4", f = "SupportingScreens.kt", i = {}, l = {138}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ MutableState<Boolean> $isOnline$delegate;
        final /* synthetic */ Function0<Unit> $onOffline;
        final /* synthetic */ Function0<Unit> $onOnline;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(Context context, Function0<Unit> function0, Function0<Unit> function02, MutableState<Boolean> mutableState, Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$onOnline = function0;
            this.$onOffline = function02;
            this.$isOnline$delegate = mutableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass4(this.$context, this.$onOnline, this.$onOffline, this.$isOnline$delegate, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            AnonymousClass4 anonymousClass4;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    anonymousClass4 = this;
                    break;
                case 1:
                    anonymousClass4 = this;
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            do {
                boolean currentStatus = MainActivityKt.isNetworkAvailable(anonymousClass4.$context);
                if (currentStatus != SupportingScreensKt.NetworkStatus$lambda$5(anonymousClass4.$isOnline$delegate)) {
                    SupportingScreensKt.NetworkStatus$lambda$6(anonymousClass4.$isOnline$delegate, currentStatus);
                    (SupportingScreensKt.NetworkStatus$lambda$5(anonymousClass4.$isOnline$delegate) ? anonymousClass4.$onOnline : anonymousClass4.$onOffline).invoke();
                }
                anonymousClass4.label = 1;
            } while (DelayKt.delay(2000L, anonymousClass4) != coroutine_suspended);
            return coroutine_suspended;
        }
    }
}
