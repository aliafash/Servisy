package com.maw;

import android.graphics.Color;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.graphics.ColorKt;
import com.maw.MainActivityKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.MainActivityKt$AppNavigationLayout$6$1$7$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class MainActivityKt$AppNavigationLayout$6$1$7$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<String> $bdPrimaryColor$delegate;
    final /* synthetic */ MutableState<String> $bdSecondaryColor$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$AppNavigationLayout$6$1$7$1(MutableState<String> mutableState, MutableState<String> mutableState2, Continuation<? super MainActivityKt$AppNavigationLayout$6$1$7$1> continuation) {
        super(2, continuation);
        this.$bdPrimaryColor$delegate = mutableState;
        this.$bdSecondaryColor$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new MainActivityKt$AppNavigationLayout$6$1$7$1(this.$bdPrimaryColor$delegate, this.$bdSecondaryColor$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((MainActivityKt$AppNavigationLayout$6$1$7$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                try {
                    AppTheme.INSTANCE.m6270setPrimaryRed8_81llA(ColorKt.Color(Color.parseColor(MainActivityKt.AnonymousClass6.invoke$lambda$63$lambda$14(this.$bdPrimaryColor$delegate))));
                    AppTheme.INSTANCE.m6268setAccentGold8_81llA(ColorKt.Color(Color.parseColor(MainActivityKt.AnonymousClass6.invoke$lambda$63$lambda$17(this.$bdSecondaryColor$delegate))));
                    break;
                } catch (Exception e) {
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
