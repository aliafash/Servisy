package androidx.compose.ui.window;

import android.view.View;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.Saver;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AndroidDialog.android.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a8\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0011\u0010\u0006\u001a\r\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0007H\u0007¢\u0006\u0002\u0010\b\u001a*\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0011\u0010\u0006\u001a\r\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0007H\u0003¢\u0006\u0002\u0010\f¨\u0006\r²\u0006\u0015\u0010\u000e\u001a\r\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0007X\u008a\u0084\u0002"}, d2 = {"Dialog", "", "onDismissRequest", "Lkotlin/Function0;", "properties", "Landroidx/compose/ui/window/DialogProperties;", "content", "Landroidx/compose/runtime/Composable;", "(Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/window/DialogProperties;Lkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;II)V", "DialogLayout", "modifier", "Landroidx/compose/ui/Modifier;", "(Landroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;II)V", "ui_release", "currentContent"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class AndroidDialog_androidKt {
    public static final void Dialog(final Function0<Unit> function0, DialogProperties dialogProperties, final Function2<? super Composer, ? super Integer, Unit> function2, Composer composer, final int i, final int i2) {
        DialogProperties dialogProperties2;
        LayoutDirection layoutDirection;
        final DialogProperties dialogProperties3;
        Composer composer2;
        Object obj;
        Composer composer3;
        Composer composerStartRestartGroup = composer.startRestartGroup(-2032877254);
        ComposerKt.sourceInformation(composerStartRestartGroup, "C(Dialog)P(1,2)155@6564L7,156@6603L7,157@6658L7,158@6688L28,159@6743L29,160@6792L38,161@6848L616,182@7470L154,191@7630L193:AndroidDialog.android.kt#2oxthz");
        int i3 = i;
        if ((i2 & 1) != 0) {
            i3 |= 6;
        } else if ((i & 14) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function0) ? 4 : 2;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
            dialogProperties2 = dialogProperties;
        } else if ((i & 112) == 0) {
            dialogProperties2 = dialogProperties;
            i3 |= composerStartRestartGroup.changed(dialogProperties2) ? 32 : 16;
        } else {
            dialogProperties2 = dialogProperties;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 896) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function2) ? 256 : 128;
        }
        int i5 = i3;
        if ((i5 & 731) == 146 && composerStartRestartGroup.getSkipping()) {
            composerStartRestartGroup.skipToGroupEnd();
            dialogProperties3 = dialogProperties2;
            composer3 = composerStartRestartGroup;
        } else {
            DialogProperties dialogProperties4 = i4 != 0 ? new DialogProperties(false, false, (SecureFlagPolicy) null, 7, (DefaultConstructorMarker) null) : dialogProperties2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-2032877254, i5, -1, "androidx.compose.ui.window.Dialog (AndroidDialog.android.kt:154)");
            }
            ProvidableCompositionLocal<View> localView = AndroidCompositionLocals_androidKt.getLocalView();
            ComposerKt.sourceInformationMarkerStart(composerStartRestartGroup, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = composerStartRestartGroup.consume(localView);
            ComposerKt.sourceInformationMarkerEnd(composerStartRestartGroup);
            View view = (View) objConsume;
            ProvidableCompositionLocal<Density> localDensity = CompositionLocalsKt.getLocalDensity();
            ComposerKt.sourceInformationMarkerStart(composerStartRestartGroup, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume2 = composerStartRestartGroup.consume(localDensity);
            ComposerKt.sourceInformationMarkerEnd(composerStartRestartGroup);
            Density density = (Density) objConsume2;
            ProvidableCompositionLocal<LayoutDirection> localLayoutDirection = CompositionLocalsKt.getLocalLayoutDirection();
            ComposerKt.sourceInformationMarkerStart(composerStartRestartGroup, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume3 = composerStartRestartGroup.consume(localLayoutDirection);
            ComposerKt.sourceInformationMarkerEnd(composerStartRestartGroup);
            LayoutDirection layoutDirection2 = (LayoutDirection) objConsume3;
            CompositionContext compositionContextRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerStartRestartGroup, 0);
            final State stateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerStartRestartGroup, (i5 >> 6) & 14);
            UUID uuid = (UUID) RememberSaveableKt.m3023rememberSaveable(new Object[0], (Saver) null, (String) null, (Function0) new Function0<UUID>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialogId$1
                @Override // kotlin.jvm.functions.Function0
                public final UUID invoke() {
                    return UUID.randomUUID();
                }
            }, composerStartRestartGroup, 3080, 6);
            composerStartRestartGroup.startReplaceableGroup(511388516);
            ComposerKt.sourceInformation(composerStartRestartGroup, "CC(remember)P(1,2):Composables.kt#9igjgp");
            boolean zChanged = composerStartRestartGroup.changed(view) | composerStartRestartGroup.changed(density);
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (zChanged || objRememberedValue == Composer.INSTANCE.getEmpty()) {
                layoutDirection = layoutDirection2;
                dialogProperties3 = dialogProperties4;
                composer2 = composerStartRestartGroup;
                DialogWrapper dialogWrapper = new DialogWrapper(function0, dialogProperties4, view, layoutDirection, density, uuid);
                dialogWrapper.setContent(compositionContextRememberCompositionContext, ComposableLambdaKt.composableLambdaInstance(488261145, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialog$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer4, Integer num) {
                        invoke(composer4, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer $composer, int $changed) {
                        ComposerKt.sourceInformation($composer, "C173@7293L141:AndroidDialog.android.kt#2oxthz");
                        if (($changed & 11) != 2 || !$composer.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(488261145, $changed, -1, "androidx.compose.ui.window.Dialog.<anonymous>.<anonymous>.<anonymous> (AndroidDialog.android.kt:173)");
                            }
                            Modifier modifierSemantics$default = SemanticsModifierKt.semantics$default(Modifier.INSTANCE, false, new Function1<SemanticsPropertyReceiver, Unit>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialog$1$1$1.1
                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                                    invoke2(semanticsPropertyReceiver);
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(SemanticsPropertyReceiver $this$semantics) {
                                    SemanticsPropertiesKt.dialog($this$semantics);
                                }
                            }, 1, null);
                            final State<Function2<Composer, Integer, Unit>> state = stateRememberUpdatedState;
                            AndroidDialog_androidKt.DialogLayout(modifierSemantics$default, ComposableLambdaKt.composableLambda($composer, -533674951, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$dialog$1$1$1.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(Composer composer4, Integer num) {
                                    invoke(composer4, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(Composer $composer2, int $changed2) {
                                    ComposerKt.sourceInformation($composer2, "C176@7400L16:AndroidDialog.android.kt#2oxthz");
                                    if (($changed2 & 11) != 2 || !$composer2.getSkipping()) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-533674951, $changed2, -1, "androidx.compose.ui.window.Dialog.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AndroidDialog.android.kt:176)");
                                        }
                                        AndroidDialog_androidKt.Dialog$lambda$0(state).invoke($composer2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                            return;
                                        }
                                        return;
                                    }
                                    $composer2.skipToGroupEnd();
                                }
                            }), $composer, 48, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer.skipToGroupEnd();
                    }
                }));
                composerStartRestartGroup.updateRememberedValue(dialogWrapper);
                obj = dialogWrapper;
            } else {
                layoutDirection = layoutDirection2;
                dialogProperties3 = dialogProperties4;
                composer2 = composerStartRestartGroup;
                obj = objRememberedValue;
            }
            composer2.endReplaceableGroup();
            final DialogWrapper dialogWrapper2 = (DialogWrapper) obj;
            Composer composer4 = composer2;
            EffectsKt.DisposableEffect(dialogWrapper2, new Function1<DisposableEffectScope, DisposableEffectResult>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt.Dialog.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final DisposableEffectResult invoke(DisposableEffectScope $this$DisposableEffect) {
                    dialogWrapper2.show();
                    final DialogWrapper dialogWrapper3 = dialogWrapper2;
                    return new DisposableEffectResult() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt$Dialog$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public void dispose() {
                            dialogWrapper3.dismiss();
                            dialogWrapper3.disposeComposition();
                        }
                    };
                }
            }, composer4, 8);
            final LayoutDirection layoutDirection3 = layoutDirection;
            EffectsKt.SideEffect(new Function0<Unit>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt.Dialog.2
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
                    dialogWrapper2.updateParameters(function0, dialogProperties3, layoutDirection3);
                }
            }, composer4, 0);
            composer3 = composer4;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
                composer3 = composer4;
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer3.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final DialogProperties dialogProperties5 = dialogProperties3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.ui.window.AndroidDialog_androidKt.Dialog.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer5, Integer num) {
                    invoke(composer5, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer5, int i6) {
                    AndroidDialog_androidKt.Dialog(function0, dialogProperties5, function2, composer5, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Function2<Composer, Integer, Unit> Dialog$lambda$0(State<? extends Function2<? super Composer, ? super Integer, Unit>> state) {
        Object thisObj$iv = state.getValue();
        return (Function2) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void DialogLayout(androidx.compose.ui.Modifier r21, final kotlin.jvm.functions.Function2<? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instruction units count: 360
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidDialog_androidKt.DialogLayout(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }
}
