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

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.MainActivityKt$ManualAddProviderTab$1$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class MainActivityKt$ManualAddProviderTab$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<String> $areaInput$delegate;
    final /* synthetic */ MutableState<String> $descInput$delegate;
    final /* synthetic */ MutableState<String> $feeInput$delegate;
    final /* synthetic */ MutableState<Boolean> $isEliteVip$delegate;
    final /* synthetic */ MutableState<Boolean> $isPinnedVal$delegate;
    final /* synthetic */ MutableState<Boolean> $isRecommendedVal$delegate;
    final /* synthetic */ MutableState<String> $nameInput$delegate;
    final /* synthetic */ MutableState<String> $orderPriorityVal$delegate;
    final /* synthetic */ MutableState<String> $phoneInput$delegate;
    final /* synthetic */ MutableState<String> $selectCatId$delegate;
    final /* synthetic */ MutableState<String> $selectCityId$delegate;
    final /* synthetic */ MutableState<Provider> $selectedProviderForEdit$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$ManualAddProviderTab$1$1(MutableState<Provider> mutableState, MutableState<String> mutableState2, MutableState<String> mutableState3, MutableState<String> mutableState4, MutableState<String> mutableState5, MutableState<String> mutableState6, MutableState<String> mutableState7, MutableState<String> mutableState8, MutableState<Boolean> mutableState9, MutableState<Boolean> mutableState10, MutableState<Boolean> mutableState11, MutableState<String> mutableState12, Continuation<? super MainActivityKt$ManualAddProviderTab$1$1> continuation) {
        super(2, continuation);
        this.$selectedProviderForEdit$delegate = mutableState;
        this.$nameInput$delegate = mutableState2;
        this.$phoneInput$delegate = mutableState3;
        this.$areaInput$delegate = mutableState4;
        this.$descInput$delegate = mutableState5;
        this.$feeInput$delegate = mutableState6;
        this.$selectCatId$delegate = mutableState7;
        this.$selectCityId$delegate = mutableState8;
        this.$isEliteVip$delegate = mutableState9;
        this.$isPinnedVal$delegate = mutableState10;
        this.$isRecommendedVal$delegate = mutableState11;
        this.$orderPriorityVal$delegate = mutableState12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new MainActivityKt$ManualAddProviderTab$1$1(this.$selectedProviderForEdit$delegate, this.$nameInput$delegate, this.$phoneInput$delegate, this.$areaInput$delegate, this.$descInput$delegate, this.$feeInput$delegate, this.$selectCatId$delegate, this.$selectCityId$delegate, this.$isEliteVip$delegate, this.$isPinnedVal$delegate, this.$isRecommendedVal$delegate, this.$orderPriorityVal$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((MainActivityKt$ManualAddProviderTab$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                Provider p = MainActivityKt.ManualAddProviderTab$lambda$349(this.$selectedProviderForEdit$delegate);
                if (p != null) {
                    MutableState<String> mutableState = this.$nameInput$delegate;
                    MutableState<String> mutableState2 = this.$phoneInput$delegate;
                    MutableState<String> mutableState3 = this.$areaInput$delegate;
                    MutableState<String> mutableState4 = this.$descInput$delegate;
                    MutableState<String> mutableState5 = this.$feeInput$delegate;
                    MutableState<String> mutableState6 = this.$selectCatId$delegate;
                    MutableState<String> mutableState7 = this.$selectCityId$delegate;
                    MutableState<Boolean> mutableState8 = this.$isEliteVip$delegate;
                    MutableState<Boolean> mutableState9 = this.$isPinnedVal$delegate;
                    MutableState<Boolean> mutableState10 = this.$isRecommendedVal$delegate;
                    MutableState<String> mutableState11 = this.$orderPriorityVal$delegate;
                    mutableState.setValue(p.getName());
                    mutableState2.setValue(p.getPhone());
                    mutableState3.setValue(p.getArea());
                    mutableState4.setValue(p.getDescription());
                    mutableState5.setValue("0");
                    mutableState6.setValue(p.getCategory());
                    mutableState7.setValue(p.getCity());
                    MainActivityKt.ManualAddProviderTab$lambda$374(mutableState8, p.isSubscribed());
                    MainActivityKt.ManualAddProviderTab$lambda$377(mutableState9, p.isPinned());
                    MainActivityKt.ManualAddProviderTab$lambda$380(mutableState10, p.isRecommended());
                    mutableState11.setValue(String.valueOf(p.getOrderPriority()));
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
