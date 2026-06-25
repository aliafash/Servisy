package com.maw;

import android.speech.tts.TextToSpeech;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.maw.MainActivityKt$SmartAssistantSheet$2$1", f = "MainActivity.kt", i = {}, l = {12890}, m = "invokeSuspend", n = {}, s = {})
final class MainActivityKt$SmartAssistantSheet$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ State<List<Pair<String, Boolean>>> $geminiHistory$delegate;
    final /* synthetic */ MutableState<Boolean> $isTtsAutoSpeak$delegate;
    final /* synthetic */ LazyListState $lazyListState;
    final /* synthetic */ MutableState<TextToSpeech> $tts$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    MainActivityKt$SmartAssistantSheet$2$1(LazyListState lazyListState, State<? extends List<Pair<String, Boolean>>> state, MutableState<Boolean> mutableState, MutableState<TextToSpeech> mutableState2, Continuation<? super MainActivityKt$SmartAssistantSheet$2$1> continuation) {
        super(2, continuation);
        this.$lazyListState = lazyListState;
        this.$geminiHistory$delegate = state;
        this.$isTtsAutoSpeak$delegate = mutableState;
        this.$tts$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new MainActivityKt$SmartAssistantSheet$2$1(this.$lazyListState, this.$geminiHistory$delegate, this.$isTtsAutoSpeak$delegate, this.$tts$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((MainActivityKt$SmartAssistantSheet$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MainActivityKt$SmartAssistantSheet$2$1 mainActivityKt$SmartAssistantSheet$2$1;
        Pair lastMsg;
        TextToSpeech textToSpeechSmartAssistantSheet$lambda$896;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                if (!MainActivityKt.SmartAssistantSheet$lambda$890(this.$geminiHistory$delegate).isEmpty()) {
                    this.label = 1;
                    if (LazyListState.animateScrollToItem$default(this.$lazyListState, MainActivityKt.SmartAssistantSheet$lambda$890(this.$geminiHistory$delegate).size() - 1, 0, this, 2, null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    mainActivityKt$SmartAssistantSheet$2$1 = this;
                    lastMsg = (Pair) CollectionsKt.last(MainActivityKt.SmartAssistantSheet$lambda$890(mainActivityKt$SmartAssistantSheet$2$1.$geminiHistory$delegate));
                    if (!((Boolean) lastMsg.getSecond()).booleanValue() && MainActivityKt.SmartAssistantSheet$lambda$899(mainActivityKt$SmartAssistantSheet$2$1.$isTtsAutoSpeak$delegate) && (textToSpeechSmartAssistantSheet$lambda$896 = MainActivityKt.SmartAssistantSheet$lambda$896(mainActivityKt$SmartAssistantSheet$2$1.$tts$delegate)) != null) {
                        Boxing.boxInt(textToSpeechSmartAssistantSheet$lambda$896.speak((CharSequence) lastMsg.getFirst(), 0, null, null));
                    }
                }
                return Unit.INSTANCE;
            case 1:
                mainActivityKt$SmartAssistantSheet$2$1 = this;
                ResultKt.throwOnFailure($result);
                lastMsg = (Pair) CollectionsKt.last(MainActivityKt.SmartAssistantSheet$lambda$890(mainActivityKt$SmartAssistantSheet$2$1.$geminiHistory$delegate));
                if (!((Boolean) lastMsg.getSecond()).booleanValue()) {
                    Boxing.boxInt(textToSpeechSmartAssistantSheet$lambda$896.speak((CharSequence) lastMsg.getFirst(), 0, null, null));
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
