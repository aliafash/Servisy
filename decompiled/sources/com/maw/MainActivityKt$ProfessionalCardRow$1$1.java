package com.maw;

import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Landroidx/compose/ui/input/pointer/PointerInputScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.MainActivityKt$ProfessionalCardRow$1$1", f = "MainActivity.kt", i = {}, l = {4872}, m = "invokeSuspend", n = {}, s = {})
final class MainActivityKt$ProfessionalCardRow$1$1 extends SuspendLambda implements Function2<PointerInputScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<Boolean> $isExpanded$delegate;
    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$ProfessionalCardRow$1$1(MutableState<Boolean> mutableState, MutableState<Boolean> mutableState2, Continuation<? super MainActivityKt$ProfessionalCardRow$1$1> continuation) {
        super(2, continuation);
        this.$isPressed$delegate = mutableState;
        this.$isExpanded$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        MainActivityKt$ProfessionalCardRow$1$1 mainActivityKt$ProfessionalCardRow$1$1 = new MainActivityKt$ProfessionalCardRow$1$1(this.$isPressed$delegate, this.$isExpanded$delegate, continuation);
        mainActivityKt$ProfessionalCardRow$1$1.L$0 = obj;
        return mainActivityKt$ProfessionalCardRow$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(PointerInputScope pointerInputScope, Continuation<? super Unit> continuation) {
        return ((MainActivityKt$ProfessionalCardRow$1$1) create(pointerInputScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                PointerInputScope $this$pointerInput = (PointerInputScope) this.L$0;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isPressed$delegate, null);
                final MutableState<Boolean> mutableState = this.$isExpanded$delegate;
                Function1<Offset, Unit> function1 = new Function1<Offset, Unit>() { // from class: com.maw.MainActivityKt$ProfessionalCardRow$1$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Offset offset) {
                        m6507invokek4lQ0M(offset.getPackedValue());
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke-k-4lQ0M, reason: not valid java name */
                    public final void m6507invokek4lQ0M(long it) {
                        MainActivityKt.ProfessionalCardRow$lambda$158(mutableState, !MainActivityKt.ProfessionalCardRow$lambda$157(mutableState));
                    }
                };
                this.label = 1;
                if (TapGestureDetectorKt.detectTapGestures($this$pointerInput, (7 & 1) != 0 ? null : null, (7 & 2) != 0 ? null : null, (7 & 4) != 0 ? TapGestureDetectorKt.NoPressGesture : anonymousClass1, (7 & 8) != 0 ? null : function1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.maw.MainActivityKt$ProfessionalCardRow$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/gestures/PressGestureScope;", "it", "Landroidx/compose/ui/geometry/Offset;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainActivityKt$ProfessionalCardRow$1$1$1", f = "MainActivity.kt", i = {}, l = {4875}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function3<PressGestureScope, Offset, Continuation<? super Unit>, Object> {
        final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(MutableState<Boolean> mutableState, Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
            this.$isPressed$delegate = mutableState;
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(PressGestureScope pressGestureScope, Offset offset, Continuation<? super Unit> continuation) {
            return m6506invoked4ec7I(pressGestureScope, offset.getPackedValue(), continuation);
        }

        /* JADX INFO: renamed from: invoke-d-4ec7I, reason: not valid java name */
        public final Object m6506invoked4ec7I(PressGestureScope pressGestureScope, long j, Continuation<? super Unit> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isPressed$delegate, continuation);
            anonymousClass1.L$0 = pressGestureScope;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            AnonymousClass1 anonymousClass1;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    PressGestureScope $this$detectTapGestures = (PressGestureScope) this.L$0;
                    MainActivityKt.ProfessionalCardRow$lambda$155(this.$isPressed$delegate, true);
                    try {
                        this.label = 1;
                        if ($this$detectTapGestures.awaitRelease(this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } catch (Exception e) {
                    }
                    anonymousClass1 = this;
                    break;
                case 1:
                    anonymousClass1 = this;
                    try {
                        ResultKt.throwOnFailure($result);
                    } catch (Exception e2) {
                    }
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            MainActivityKt.ProfessionalCardRow$lambda$155(anonymousClass1.$isPressed$delegate, false);
            return Unit.INSTANCE;
        }
    }
}
