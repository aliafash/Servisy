package androidx.compose.foundation.gestures;

import androidx.autofill.HintConstants;
import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.foundation.interaction.DragInteraction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.unit.Velocity;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* JADX INFO: compiled from: Draggable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B½\u0001\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u0012<\u0010\r\u001a8\b\u0001\u0012\u0004\u0012\u00020\u000f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u000e¢\u0006\u0002\b\u0017\u0012<\u0010\u0018\u001a8\b\u0001\u0012\u0004\u0012\u00020\u000f\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u000e¢\u0006\u0002\b\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u0007¢\u0006\u0002\u0010\u001cJ\u0006\u0010H\u001a\u00020\u0015J7\u0010I\u001a\u00020\u00152'\u0010J\u001a#\b\u0001\u0012\u0004\u0012\u00020L\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00160K¢\u0006\u0002\b\u0017H¦@¢\u0006\u0002\u0010MJ\b\u0010N\u001a\u00020\u0015H\u0016J\b\u0010O\u001a\u00020\u0015H\u0016J*\u0010P\u001a\u00020\u00152\u0006\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020VH\u0016ø\u0001\u0000¢\u0006\u0004\bW\u0010XJ\b\u0010Y\u001a\u00020\u0015H\u0002J\u001a\u0010Z\u001a\u00020\u0015*\u00020L2\u0006\u0010[\u001a\u00020\\H¦@¢\u0006\u0002\u0010]J\u0012\u0010^\u001a\u00020\u0015*\u00020\u000fH\u0082@¢\u0006\u0002\u0010_J\u001a\u0010`\u001a\u00020\u0015*\u00020\u000f2\u0006\u0010a\u001a\u00020bH\u0082@¢\u0006\u0002\u0010cJ\u001a\u0010d\u001a\u00020\u0015*\u00020\u000f2\u0006\u0010a\u001a\u00020eH\u0082@¢\u0006\u0002\u0010fR\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u000e\u00100\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000RR\u0010\r\u001a8\b\u0001\u0012\u0004\u0012\u00020\u000f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u000e¢\u0006\u0002\b\u0017X\u0086\u000e¢\u0006\u0010\n\u0002\u00105\u001a\u0004\b1\u00102\"\u0004\b3\u00104RR\u0010\u0018\u001a8\b\u0001\u0012\u0004\u0012\u00020\u000f\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u000e¢\u0006\u0002\b\u0017X\u0086\u000e¢\u0006\u0010\n\u0002\u00105\u001a\u0004\b6\u00102\"\u0004\b7\u00104R\u0012\u00108\u001a\u000209X¦\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0011\u0010<\u001a\u00020=¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R\u001a\u0010\u001b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010)\"\u0004\bA\u0010+R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020GX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006g"}, d2 = {"Landroidx/compose/foundation/gestures/AbstractDraggableNode;", "Landroidx/compose/ui/node/DelegatingNode;", "Landroidx/compose/ui/node/PointerInputModifierNode;", "Landroidx/compose/ui/node/CompositionLocalConsumerModifierNode;", "canDrag", "Lkotlin/Function1;", "Landroidx/compose/ui/input/pointer/PointerInputChange;", "", "enabled", "interactionSource", "Landroidx/compose/foundation/interaction/MutableInteractionSource;", "startDragImmediately", "Lkotlin/Function0;", "onDragStarted", "Lkotlin/Function3;", "Lkotlinx/coroutines/CoroutineScope;", "Landroidx/compose/ui/geometry/Offset;", "Lkotlin/ParameterName;", HintConstants.AUTOFILL_HINT_NAME, "startedPosition", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "onDragStopped", "Landroidx/compose/ui/unit/Velocity;", "velocity", "reverseDirection", "(Lkotlin/jvm/functions/Function1;ZLandroidx/compose/foundation/interaction/MutableInteractionSource;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "_canDrag", "_startDragImmediately", "getCanDrag", "()Lkotlin/jvm/functions/Function1;", "setCanDrag", "(Lkotlin/jvm/functions/Function1;)V", "channel", "Lkotlinx/coroutines/channels/Channel;", "Landroidx/compose/foundation/gestures/DragEvent;", "dragInteraction", "Landroidx/compose/foundation/interaction/DragInteraction$Start;", "getEnabled", "()Z", "setEnabled", "(Z)V", "getInteractionSource", "()Landroidx/compose/foundation/interaction/MutableInteractionSource;", "setInteractionSource", "(Landroidx/compose/foundation/interaction/MutableInteractionSource;)V", "isListeningForEvents", "getOnDragStarted", "()Lkotlin/jvm/functions/Function3;", "setOnDragStarted", "(Lkotlin/jvm/functions/Function3;)V", "Lkotlin/jvm/functions/Function3;", "getOnDragStopped", "setOnDragStopped", "pointerDirectionConfig", "Landroidx/compose/foundation/gestures/PointerDirectionConfig;", "getPointerDirectionConfig", "()Landroidx/compose/foundation/gestures/PointerDirectionConfig;", "pointerInputNode", "Landroidx/compose/ui/input/pointer/SuspendingPointerInputModifierNode;", "getPointerInputNode", "()Landroidx/compose/ui/input/pointer/SuspendingPointerInputModifierNode;", "getReverseDirection", "setReverseDirection", "getStartDragImmediately", "()Lkotlin/jvm/functions/Function0;", "setStartDragImmediately", "(Lkotlin/jvm/functions/Function0;)V", "velocityTracker", "Landroidx/compose/ui/input/pointer/util/VelocityTracker;", "disposeInteractionSource", "drag", "block", "Lkotlin/Function2;", "Landroidx/compose/foundation/gestures/AbstractDragScope;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCancelPointerInput", "onDetach", "onPointerEvent", "pointerEvent", "Landroidx/compose/ui/input/pointer/PointerEvent;", "pass", "Landroidx/compose/ui/input/pointer/PointerEventPass;", "bounds", "Landroidx/compose/ui/unit/IntSize;", "onPointerEvent-H0pRuoY", "(Landroidx/compose/ui/input/pointer/PointerEvent;Landroidx/compose/ui/input/pointer/PointerEventPass;J)V", "startListeningForEvents", "draggingBy", "dragDelta", "Landroidx/compose/foundation/gestures/DragEvent$DragDelta;", "(Landroidx/compose/foundation/gestures/AbstractDragScope;Landroidx/compose/foundation/gestures/DragEvent$DragDelta;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processDragCancel", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processDragStart", NotificationCompat.CATEGORY_EVENT, "Landroidx/compose/foundation/gestures/DragEvent$DragStarted;", "(Lkotlinx/coroutines/CoroutineScope;Landroidx/compose/foundation/gestures/DragEvent$DragStarted;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processDragStop", "Landroidx/compose/foundation/gestures/DragEvent$DragStopped;", "(Lkotlinx/coroutines/CoroutineScope;Landroidx/compose/foundation/gestures/DragEvent$DragStopped;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "foundation_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public abstract class AbstractDraggableNode extends DelegatingNode implements PointerInputModifierNode, CompositionLocalConsumerModifierNode {
    public static final int $stable = 8;
    private Function1<? super PointerInputChange, Boolean> canDrag;
    private DragInteraction.Start dragInteraction;
    private boolean enabled;
    private MutableInteractionSource interactionSource;
    private boolean isListeningForEvents;
    private Function3<? super CoroutineScope, ? super Offset, ? super Continuation<? super Unit>, ? extends Object> onDragStarted;
    private Function3<? super CoroutineScope, ? super Velocity, ? super Continuation<? super Unit>, ? extends Object> onDragStopped;
    private boolean reverseDirection;
    private Function0<Boolean> startDragImmediately;
    private final Function1<PointerInputChange, Boolean> _canDrag = new Function1<PointerInputChange, Boolean>() { // from class: androidx.compose.foundation.gestures.AbstractDraggableNode$_canDrag$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(PointerInputChange it) {
            return this.this$0.getCanDrag().invoke(it);
        }
    };
    private final Function0<Boolean> _startDragImmediately = new Function0<Boolean>() { // from class: androidx.compose.foundation.gestures.AbstractDraggableNode$_startDragImmediately$1
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return this.this$0.getStartDragImmediately().invoke();
        }
    };
    private final VelocityTracker velocityTracker = new VelocityTracker();
    private final SuspendingPointerInputModifierNode pointerInputNode = (SuspendingPointerInputModifierNode) delegate(SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new AbstractDraggableNode$pointerInputNode$1(this, null)));
    private final Channel<DragEvent> channel = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6, null);

    /* JADX INFO: renamed from: androidx.compose.foundation.gestures.AbstractDraggableNode$processDragCancel$1, reason: invalid class name */
    /* JADX INFO: compiled from: Draggable.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.compose.foundation.gestures.AbstractDraggableNode", f = "Draggable.kt", i = {0, 0}, l = {554, 557}, m = "processDragCancel", n = {"this", "$this$processDragCancel"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractDraggableNode.this.processDragCancel(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.compose.foundation.gestures.AbstractDraggableNode$processDragStart$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Draggable.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.compose.foundation.gestures.AbstractDraggableNode", f = "Draggable.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {536, 539, 541}, m = "processDragStart", n = {"this", "$this$processDragStart", NotificationCompat.CATEGORY_EVENT, "this", "$this$processDragStart", NotificationCompat.CATEGORY_EVENT, "interaction"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3"})
    static final class C02381 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C02381(Continuation<? super C02381> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractDraggableNode.this.processDragStart(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.compose.foundation.gestures.AbstractDraggableNode$processDragStop$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Draggable.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.compose.foundation.gestures.AbstractDraggableNode", f = "Draggable.kt", i = {0, 0, 0}, l = {546, 549}, m = "processDragStop", n = {"this", "$this$processDragStop", NotificationCompat.CATEGORY_EVENT}, s = {"L$0", "L$1", "L$2"})
    static final class C02391 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C02391(Continuation<? super C02391> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractDraggableNode.this.processDragStop(null, null, this);
        }
    }

    public abstract Object drag(Function2<? super AbstractDragScope, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation);

    public abstract Object draggingBy(AbstractDragScope abstractDragScope, DragEvent.DragDelta dragDelta, Continuation<? super Unit> continuation);

    public abstract PointerDirectionConfig getPointerDirectionConfig();

    public final Function1<PointerInputChange, Boolean> getCanDrag() {
        return this.canDrag;
    }

    public final void setCanDrag(Function1<? super PointerInputChange, Boolean> function1) {
        this.canDrag = function1;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final MutableInteractionSource getInteractionSource() {
        return this.interactionSource;
    }

    public final void setInteractionSource(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    public final Function0<Boolean> getStartDragImmediately() {
        return this.startDragImmediately;
    }

    public final void setStartDragImmediately(Function0<Boolean> function0) {
        this.startDragImmediately = function0;
    }

    public final Function3<CoroutineScope, Offset, Continuation<? super Unit>, Object> getOnDragStarted() {
        return this.onDragStarted;
    }

    public final void setOnDragStarted(Function3<? super CoroutineScope, ? super Offset, ? super Continuation<? super Unit>, ? extends Object> function3) {
        this.onDragStarted = function3;
    }

    public final Function3<CoroutineScope, Velocity, Continuation<? super Unit>, Object> getOnDragStopped() {
        return this.onDragStopped;
    }

    public final void setOnDragStopped(Function3<? super CoroutineScope, ? super Velocity, ? super Continuation<? super Unit>, ? extends Object> function3) {
        this.onDragStopped = function3;
    }

    public final boolean getReverseDirection() {
        return this.reverseDirection;
    }

    public final void setReverseDirection(boolean z) {
        this.reverseDirection = z;
    }

    public AbstractDraggableNode(Function1<? super PointerInputChange, Boolean> function1, boolean enabled, MutableInteractionSource interactionSource, Function0<Boolean> function0, Function3<? super CoroutineScope, ? super Offset, ? super Continuation<? super Unit>, ? extends Object> function3, Function3<? super CoroutineScope, ? super Velocity, ? super Continuation<? super Unit>, ? extends Object> function32, boolean reverseDirection) {
        this.canDrag = function1;
        this.enabled = enabled;
        this.interactionSource = interactionSource;
        this.startDragImmediately = function0;
        this.onDragStarted = function3;
        this.onDragStopped = function32;
        this.reverseDirection = reverseDirection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startListeningForEvents() {
        this.isListeningForEvents = true;
        BuildersKt__Builders_commonKt.launch$default(getCoroutineScope(), null, null, new C02401(null), 3, null);
    }

    /* JADX INFO: renamed from: androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Draggable.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1", f = "Draggable.kt", i = {0, 0, 1, 1, 2, 2, 3, 4, 5}, l = {431, 433, 435, 442, 444, 447}, m = "invokeSuspend", n = {"$this$launch", NotificationCompat.CATEGORY_EVENT, "$this$launch", NotificationCompat.CATEGORY_EVENT, "$this$launch", NotificationCompat.CATEGORY_EVENT, "$this$launch", "$this$launch", "$this$launch"}, s = {"L$0", "L$1", "L$0", "L$1", "L$0", "L$1", "L$0", "L$0", "L$0"})
    static final class C02401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        C02401(Continuation<? super C02401> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C02401 c02401 = AbstractDraggableNode.this.new C02401(continuation);
            c02401.L$0 = obj;
            return c02401;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00a5  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00df A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00e6 A[Catch: CancellationException -> 0x0042, TryCatch #1 {CancellationException -> 0x0042, blocks: (B:36:0x00c4, B:39:0x00e0, B:41:0x00e6, B:46:0x0106, B:48:0x010c, B:16:0x003d), top: B:62:0x003d }] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0106 A[Catch: CancellationException -> 0x0042, TryCatch #1 {CancellationException -> 0x0042, blocks: (B:36:0x00c4, B:39:0x00e0, B:41:0x00e6, B:46:0x0106, B:48:0x010c, B:16:0x003d), top: B:62:0x003d }] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0137 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x013a  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0140  */
        /* JADX WARN: Type inference failed for: r11v13 */
        /* JADX WARN: Type inference failed for: r11v15 */
        /* JADX WARN: Type inference failed for: r11v5, types: [T] */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v10, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v14, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v15, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v16, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v17, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v18 */
        /* JADX WARN: Type inference failed for: r1v19 */
        /* JADX WARN: Type inference failed for: r1v2 */
        /* JADX WARN: Type inference failed for: r1v20 */
        /* JADX WARN: Type inference failed for: r1v3, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r1v6 */
        /* JADX WARN: Type inference failed for: r1v7 */
        /* JADX WARN: Type inference failed for: r1v8, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r3v14, types: [androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1] */
        /* JADX WARN: Type inference failed for: r3v18 */
        /* JADX WARN: Type inference failed for: r3v23 */
        /* JADX WARN: Type inference failed for: r3v9 */
        /* JADX WARN: Type inference failed for: r5v0 */
        /* JADX WARN: Type inference failed for: r5v6 */
        /* JADX WARN: Type inference failed for: r6v7 */
        /* JADX WARN: Type inference failed for: r7v2 */
        /* JADX WARN: Type inference failed for: r7v4 */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x011f -> B:23:0x0072). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x0122 -> B:23:0x0072). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x0135 -> B:23:0x0072). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x013a -> B:23:0x0072). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) {
            /*
                Method dump skipped, instruction units count: 342
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AbstractDraggableNode.C02401.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Draggable.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/gestures/AbstractDragScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "androidx.compose.foundation.gestures.AbstractDraggableNode$startListeningForEvents$1$1", f = "Draggable.kt", i = {0, 1}, l = {437, 438}, m = "invokeSuspend", n = {"$this$drag", "$this$drag"}, s = {"L$0", "L$0"})
        static final class C00171 extends SuspendLambda implements Function2<AbstractDragScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<DragEvent> $event;
            private /* synthetic */ Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ AbstractDraggableNode this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00171(Ref.ObjectRef<DragEvent> objectRef, AbstractDraggableNode abstractDraggableNode, Continuation<? super C00171> continuation) {
                super(2, continuation);
                this.$event = objectRef;
                this.this$0 = abstractDraggableNode;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00171 c00171 = new C00171(this.$event, this.this$0, continuation);
                c00171.L$0 = obj;
                return c00171;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(AbstractDragScope abstractDragScope, Continuation<? super Unit> continuation) {
                return ((C00171) create(abstractDragScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:11:0x0040  */
            /* JADX WARN: Removed duplicated region for block: B:25:0x008e A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:26:0x008f  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x008f -> B:27:0x0096). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r10) {
                /*
                    r9 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r1 = r9.label
                    switch(r1) {
                        case 0: goto L2f;
                        case 1: goto L25;
                        case 2: goto L12;
                        default: goto L9;
                    }
                L9:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r0)
                    throw r10
                L12:
                    r1 = r9
                    java.lang.Object r2 = r1.L$1
                    kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref.ObjectRef) r2
                    java.lang.Object r3 = r1.L$0
                    androidx.compose.foundation.gestures.AbstractDragScope r3 = (androidx.compose.foundation.gestures.AbstractDragScope) r3
                    kotlin.ResultKt.throwOnFailure(r10)
                    r4 = r3
                    r3 = r2
                    r2 = r1
                    r1 = r0
                    r0 = r10
                    goto L96
                L25:
                    r1 = r9
                    r2 = 0
                    java.lang.Object r3 = r1.L$0
                    androidx.compose.foundation.gestures.AbstractDragScope r3 = (androidx.compose.foundation.gestures.AbstractDragScope) r3
                    kotlin.ResultKt.throwOnFailure(r10)
                    goto L75
                L2f:
                    kotlin.ResultKt.throwOnFailure(r10)
                    r1 = r9
                    java.lang.Object r2 = r1.L$0
                    androidx.compose.foundation.gestures.AbstractDragScope r2 = (androidx.compose.foundation.gestures.AbstractDragScope) r2
                    r3 = r2
                L38:
                    kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.foundation.gestures.DragEvent> r2 = r1.$event
                    T r2 = r2.element
                    boolean r2 = r2 instanceof androidx.compose.foundation.gestures.DragEvent.DragStopped
                    if (r2 != 0) goto L9d
                    kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.foundation.gestures.DragEvent> r2 = r1.$event
                    T r2 = r2.element
                    boolean r2 = r2 instanceof androidx.compose.foundation.gestures.DragEvent.DragCancelled
                    if (r2 != 0) goto L9d
                    kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.foundation.gestures.DragEvent> r2 = r1.$event
                    T r2 = r2.element
                    boolean r4 = r2 instanceof androidx.compose.foundation.gestures.DragEvent.DragDelta
                    r5 = 0
                    if (r4 == 0) goto L54
                    androidx.compose.foundation.gestures.DragEvent$DragDelta r2 = (androidx.compose.foundation.gestures.DragEvent.DragDelta) r2
                    goto L55
                L54:
                    r2 = r5
                L55:
                    if (r2 == 0) goto L76
                    androidx.compose.foundation.gestures.AbstractDraggableNode r2 = r1.this$0
                    kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.foundation.gestures.DragEvent> r4 = r1.$event
                    r6 = 0
                    T r4 = r4.element
                    java.lang.String r7 = "null cannot be cast to non-null type androidx.compose.foundation.gestures.DragEvent.DragDelta"
                    kotlin.jvm.internal.Intrinsics.checkNotNull(r4, r7)
                    androidx.compose.foundation.gestures.DragEvent$DragDelta r4 = (androidx.compose.foundation.gestures.DragEvent.DragDelta) r4
                    r1.L$0 = r3
                    r1.L$1 = r5
                    r5 = 1
                    r1.label = r5
                    java.lang.Object r2 = r2.draggingBy(r3, r4, r1)
                    if (r2 != r0) goto L74
                    return r0
                L74:
                    r2 = r6
                L75:
                L76:
                    kotlin.jvm.internal.Ref$ObjectRef<androidx.compose.foundation.gestures.DragEvent> r2 = r1.$event
                    androidx.compose.foundation.gestures.AbstractDraggableNode r4 = r1.this$0
                    kotlinx.coroutines.channels.Channel r4 = androidx.compose.foundation.gestures.AbstractDraggableNode.access$getChannel$p(r4)
                    r5 = r1
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r1.L$0 = r3
                    r1.L$1 = r2
                    r6 = 2
                    r1.label = r6
                    java.lang.Object r4 = r4.receive(r5)
                    if (r4 != r0) goto L8f
                    return r0
                L8f:
                    r8 = r0
                    r0 = r10
                    r10 = r4
                    r4 = r3
                    r3 = r2
                    r2 = r1
                    r1 = r8
                L96:
                    r3.element = r10
                    r10 = r0
                    r0 = r1
                    r1 = r2
                    r3 = r4
                    goto L38
                L9d:
                    kotlin.Unit r0 = kotlin.Unit.INSTANCE
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AbstractDraggableNode.C02401.C00171.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    public final SuspendingPointerInputModifierNode getPointerInputNode() {
        return this.pointerInputNode;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public void onDetach() {
        this.isListeningForEvents = false;
        disposeInteractionSource();
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* JADX INFO: renamed from: onPointerEvent-H0pRuoY */
    public void mo179onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pass, long bounds) {
        this.pointerInputNode.mo179onPointerEventH0pRuoY(pointerEvent, pass, bounds);
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public void onCancelPointerInput() {
        this.pointerInputNode.onCancelPointerInput();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object processDragStart(kotlinx.coroutines.CoroutineScope r9, androidx.compose.foundation.gestures.DragEvent.DragStarted r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            Method dump skipped, instruction units count: 206
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AbstractDraggableNode.processDragStart(kotlinx.coroutines.CoroutineScope, androidx.compose.foundation.gestures.DragEvent$DragStarted, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0089 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object processDragStop(kotlinx.coroutines.CoroutineScope r10, androidx.compose.foundation.gestures.DragEvent.DragStopped r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r9 = this;
            boolean r0 = r12 instanceof androidx.compose.foundation.gestures.AbstractDraggableNode.C02391
            if (r0 == 0) goto L14
            r0 = r12
            androidx.compose.foundation.gestures.AbstractDraggableNode$processDragStop$1 r0 = (androidx.compose.foundation.gestures.AbstractDraggableNode.C02391) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L19
        L14:
            androidx.compose.foundation.gestures.AbstractDraggableNode$processDragStop$1 r0 = new androidx.compose.foundation.gestures.AbstractDraggableNode$processDragStop$1
            r0.<init>(r12)
        L19:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            r3 = 0
            switch(r2) {
                case 0: goto L44;
                case 1: goto L33;
                case 2: goto L2f;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L8a
        L33:
            r10 = 0
            java.lang.Object r11 = r12.L$2
            androidx.compose.foundation.gestures.DragEvent$DragStopped r11 = (androidx.compose.foundation.gestures.DragEvent.DragStopped) r11
            java.lang.Object r2 = r12.L$1
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            java.lang.Object r4 = r12.L$0
            androidx.compose.foundation.gestures.AbstractDraggableNode r4 = (androidx.compose.foundation.gestures.AbstractDraggableNode) r4
            kotlin.ResultKt.throwOnFailure(r0)
            goto L6a
        L44:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r9
            r2 = r10
            androidx.compose.foundation.interaction.DragInteraction$Start r10 = r4.dragInteraction
            if (r10 == 0) goto L70
            r5 = 0
            androidx.compose.foundation.interaction.MutableInteractionSource r6 = r4.interactionSource
            if (r6 == 0) goto L6c
            androidx.compose.foundation.interaction.DragInteraction$Stop r7 = new androidx.compose.foundation.interaction.DragInteraction$Stop
            r7.<init>(r10)
            androidx.compose.foundation.interaction.Interaction r7 = (androidx.compose.foundation.interaction.Interaction) r7
            r12.L$0 = r4
            r12.L$1 = r2
            r12.L$2 = r11
            r8 = 1
            r12.label = r8
            java.lang.Object r10 = r6.emit(r7, r12)
            if (r10 != r1) goto L69
            return r1
        L69:
            r10 = r5
        L6a:
            r5 = r10
        L6c:
            r4.dragInteraction = r3
        L70:
            kotlin.jvm.functions.Function3<? super kotlinx.coroutines.CoroutineScope, ? super androidx.compose.ui.unit.Velocity, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> r10 = r4.onDragStopped
            long r5 = r11.getVelocity()
            androidx.compose.ui.unit.Velocity r5 = androidx.compose.ui.unit.Velocity.m5959boximpl(r5)
            r12.L$0 = r3
            r12.L$1 = r3
            r12.L$2 = r3
            r3 = 2
            r12.label = r3
            java.lang.Object r10 = r10.invoke(r2, r5, r12)
            if (r10 != r1) goto L8a
            return r1
        L8a:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AbstractDraggableNode.processDragStop(kotlinx.coroutines.CoroutineScope, androidx.compose.foundation.gestures.DragEvent$DragStopped, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object processDragCancel(kotlinx.coroutines.CoroutineScope r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.compose.foundation.gestures.AbstractDraggableNode.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r11
            androidx.compose.foundation.gestures.AbstractDraggableNode$processDragCancel$1 r0 = (androidx.compose.foundation.gestures.AbstractDraggableNode.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L19
        L14:
            androidx.compose.foundation.gestures.AbstractDraggableNode$processDragCancel$1 r0 = new androidx.compose.foundation.gestures.AbstractDraggableNode$processDragCancel$1
            r0.<init>(r11)
        L19:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L40;
                case 1: goto L33;
                case 2: goto L2f;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L84
        L33:
            r10 = 0
            java.lang.Object r2 = r11.L$1
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            java.lang.Object r4 = r11.L$0
            androidx.compose.foundation.gestures.AbstractDraggableNode r4 = (androidx.compose.foundation.gestures.AbstractDraggableNode) r4
            kotlin.ResultKt.throwOnFailure(r0)
            goto L64
        L40:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r9
            r2 = r10
            androidx.compose.foundation.interaction.DragInteraction$Start r10 = r4.dragInteraction
            if (r10 == 0) goto L6a
            r5 = 0
            androidx.compose.foundation.interaction.MutableInteractionSource r6 = r4.interactionSource
            if (r6 == 0) goto L66
            androidx.compose.foundation.interaction.DragInteraction$Cancel r7 = new androidx.compose.foundation.interaction.DragInteraction$Cancel
            r7.<init>(r10)
            androidx.compose.foundation.interaction.Interaction r7 = (androidx.compose.foundation.interaction.Interaction) r7
            r11.L$0 = r4
            r11.L$1 = r2
            r8 = 1
            r11.label = r8
            java.lang.Object r10 = r6.emit(r7, r11)
            if (r10 != r1) goto L63
            return r1
        L63:
            r10 = r5
        L64:
            r5 = r10
        L66:
            r4.dragInteraction = r3
        L6a:
            kotlin.jvm.functions.Function3<? super kotlinx.coroutines.CoroutineScope, ? super androidx.compose.ui.unit.Velocity, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> r10 = r4.onDragStopped
            androidx.compose.ui.unit.Velocity$Companion r5 = androidx.compose.ui.unit.Velocity.INSTANCE
            long r5 = r5.m5979getZero9UxMQ8M()
            androidx.compose.ui.unit.Velocity r5 = androidx.compose.ui.unit.Velocity.m5959boximpl(r5)
            r11.L$0 = r3
            r11.L$1 = r3
            r3 = 2
            r11.label = r3
            java.lang.Object r10 = r10.invoke(r2, r5, r11)
            if (r10 != r1) goto L84
            return r1
        L84:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AbstractDraggableNode.processDragCancel(kotlinx.coroutines.CoroutineScope, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void disposeInteractionSource() {
        DragInteraction.Start interaction = this.dragInteraction;
        if (interaction != null) {
            MutableInteractionSource mutableInteractionSource = this.interactionSource;
            if (mutableInteractionSource != null) {
                mutableInteractionSource.tryEmit(new DragInteraction.Cancel(interaction));
            }
            this.dragInteraction = null;
        }
    }
}
