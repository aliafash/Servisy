package com.maw;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AdminControllerScreens.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$1$1", f = "AdminControllerScreens.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class AdminControllerScreensKt$ComposedManualAddProviderTab$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ State<List<Category>> $categories$delegate;
    final /* synthetic */ State<List<City>> $cities$delegate;
    final /* synthetic */ MutableState<String> $selectedCat$delegate;
    final /* synthetic */ MutableState<String> $selectedCity$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    AdminControllerScreensKt$ComposedManualAddProviderTab$1$1(MutableState<String> mutableState, State<? extends List<Category>> state, MutableState<String> mutableState2, State<? extends List<City>> state2, Continuation<? super AdminControllerScreensKt$ComposedManualAddProviderTab$1$1> continuation) {
        super(2, continuation);
        this.$selectedCat$delegate = mutableState;
        this.$categories$delegate = state;
        this.$selectedCity$delegate = mutableState2;
        this.$cities$delegate = state2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AdminControllerScreensKt$ComposedManualAddProviderTab$1$1(this.$selectedCat$delegate, this.$categories$delegate, this.$selectedCity$delegate, this.$cities$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((AdminControllerScreensKt$ComposedManualAddProviderTab$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                if (StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$40(this.$selectedCat$delegate)) && (!AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$48(this.$categories$delegate).isEmpty())) {
                    this.$selectedCat$delegate.setValue(((Category) CollectionsKt.first(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$48(this.$categories$delegate))).getId());
                }
                if (StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$43(this.$selectedCity$delegate)) && (!AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$49(this.$cities$delegate).isEmpty())) {
                    this.$selectedCity$delegate.setValue(((City) CollectionsKt.first(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$49(this.$cities$delegate))).getId());
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
