package com.maw;

import androidx.compose.runtime.MutableState;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.MainActivityKt$ProfessionalCategoryFilterComponent$1$1", f = "MainActivity.kt", i = {}, l = {3985}, m = "invokeSuspend", n = {}, s = {})
final class MainActivityKt$ProfessionalCategoryFilterComponent$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<Boolean> $isListLoading$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$ProfessionalCategoryFilterComponent$1$1(MutableState<Boolean> mutableState, Continuation<? super MainActivityKt$ProfessionalCategoryFilterComponent$1$1> continuation) {
        super(2, continuation);
        this.$isListLoading$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new MainActivityKt$ProfessionalCategoryFilterComponent$1$1(this.$isListLoading$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((MainActivityKt$ProfessionalCategoryFilterComponent$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MainActivityKt$ProfessionalCategoryFilterComponent$1$1 mainActivityKt$ProfessionalCategoryFilterComponent$1$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                MainActivityKt.ProfessionalCategoryFilterComponent$lambda$68(this.$isListLoading$delegate, true);
                this.label = 1;
                if (DelayKt.delay(500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                mainActivityKt$ProfessionalCategoryFilterComponent$1$1 = this;
                break;
                break;
            case 1:
                mainActivityKt$ProfessionalCategoryFilterComponent$1$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        MainActivityKt.ProfessionalCategoryFilterComponent$lambda$68(mainActivityKt$ProfessionalCategoryFilterComponent$1$1.$isListLoading$delegate, false);
        return Unit.INSTANCE;
    }
}
