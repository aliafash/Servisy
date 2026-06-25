package androidx.compose.foundation.text;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.unit.Dp;
import com.google.firebase.firestore.index.FirestoreIndexValueWriter;
import com.google.logging.type.LogSeverity;
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
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: TextFieldCursor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a4\u0010\b\u001a\u00020\t*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\"\u0016\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\n\n\u0002\u0010\u0004\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"DefaultCursorThickness", "Landroidx/compose/ui/unit/Dp;", "getDefaultCursorThickness", "()F", "F", "cursorAnimationSpec", "Landroidx/compose/animation/core/AnimationSpec;", "", "cursor", "Landroidx/compose/ui/Modifier;", "state", "Landroidx/compose/foundation/text/TextFieldState;", "value", "Landroidx/compose/ui/text/input/TextFieldValue;", "offsetMapping", "Landroidx/compose/ui/text/input/OffsetMapping;", "cursorBrush", "Landroidx/compose/ui/graphics/Brush;", "enabled", "", "foundation_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class TextFieldCursorKt {
    private static final AnimationSpec<Float> cursorAnimationSpec = AnimationSpecKt.m127infiniteRepeatable9IiC70o$default(AnimationSpecKt.keyframes(new Function1<KeyframesSpec.KeyframesSpecConfig<Float>, Unit>() { // from class: androidx.compose.foundation.text.TextFieldCursorKt$cursorAnimationSpec$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(KeyframesSpec.KeyframesSpecConfig<Float> keyframesSpecConfig) {
            invoke2(keyframesSpecConfig);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(KeyframesSpec.KeyframesSpecConfig<Float> keyframesSpecConfig) {
            keyframesSpecConfig.setDurationMillis(1000);
            Float fValueOf = Float.valueOf(1.0f);
            keyframesSpecConfig.at(fValueOf, 0);
            keyframesSpecConfig.at(fValueOf, 499);
            Float fValueOf2 = Float.valueOf(0.0f);
            keyframesSpecConfig.at(fValueOf2, LogSeverity.ERROR_VALUE);
            keyframesSpecConfig.at(fValueOf2, 999);
        }
    }), null, 0, 6, null);
    private static final float DefaultCursorThickness = Dp.m5733constructorimpl(2);

    public static final Modifier cursor(Modifier $this$cursor, final TextFieldState state, final TextFieldValue value, final OffsetMapping offsetMapping, final Brush cursorBrush, boolean enabled) {
        return enabled ? ComposedModifierKt.composed$default($this$cursor, null, new Function3<Modifier, Composer, Integer, Modifier>() { // from class: androidx.compose.foundation.text.TextFieldCursorKt.cursor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Modifier invoke(Modifier modifier, Composer composer, Integer num) {
                return invoke(modifier, composer, num.intValue());
            }

            public final Modifier invoke(Modifier $this$composed, Composer $composer, int $changed) {
                Object value$iv$iv;
                Modifier.Companion companionDrawWithContent;
                $composer.startReplaceableGroup(1634330012);
                ComposerKt.sourceInformation($composer, "C45@1739L27,48@1941L491:TextFieldCursor.kt#423gt5");
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(1634330012, $changed, -1, "androidx.compose.foundation.text.cursor.<anonymous> (TextFieldCursor.kt:45)");
                }
                $composer.startReplaceableGroup(-492369756);
                ComposerKt.sourceInformation($composer, "CC(remember):Composables.kt#9igjgp");
                Object it$iv$iv = $composer.rememberedValue();
                if (it$iv$iv == Composer.INSTANCE.getEmpty()) {
                    value$iv$iv = AnimatableKt.Animatable$default(1.0f, 0.0f, 2, null);
                    $composer.updateRememberedValue(value$iv$iv);
                } else {
                    value$iv$iv = it$iv$iv;
                }
                $composer.endReplaceableGroup();
                final Animatable cursorAlpha = (Animatable) value$iv$iv;
                boolean z = true;
                if (cursorBrush instanceof SolidColor) {
                    long $this$isUnspecified$iv = ((SolidColor) cursorBrush).getValue();
                    if (($this$isUnspecified$iv == Color.INSTANCE.m3442getUnspecified0d7_KjU() ? 1 : 0) != 0) {
                        z = false;
                    }
                }
                boolean isBrushSpecified = z;
                if (state.getHasFocus() && TextRange.m5226getCollapsedimpl(value.getSelection()) && isBrushSpecified) {
                    EffectsKt.LaunchedEffect(value.getText(), TextRange.m5220boximpl(value.getSelection()), new C00471(cursorAlpha, null), $composer, 512);
                    final OffsetMapping offsetMapping2 = offsetMapping;
                    final TextFieldValue textFieldValue = value;
                    final TextFieldState textFieldState = state;
                    final Brush brush = cursorBrush;
                    companionDrawWithContent = DrawModifierKt.drawWithContent($this$composed, new Function1<ContentDrawScope, Unit>() { // from class: androidx.compose.foundation.text.TextFieldCursorKt.cursor.1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ContentDrawScope contentDrawScope) {
                            invoke2(contentDrawScope);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(ContentDrawScope $this$drawWithContent) {
                            Rect rect;
                            TextLayoutResult value2;
                            $this$drawWithContent.drawContent();
                            float cursorAlphaValue = RangesKt.coerceIn(cursorAlpha.getValue().floatValue(), 0.0f, 1.0f);
                            if (!(cursorAlphaValue == 0.0f)) {
                                int transformedOffset = offsetMapping2.originalToTransformed(TextRange.m5232getStartimpl(textFieldValue.getSelection()));
                                TextLayoutResultProxy layoutResult = textFieldState.getLayoutResult();
                                if (layoutResult == null || (value2 = layoutResult.getValue()) == null || (rect = value2.getCursorRect(transformedOffset)) == null) {
                                    rect = new Rect(0.0f, 0.0f, 0.0f, 0.0f);
                                }
                                Rect cursorRect = rect;
                                float cursorWidth = $this$drawWithContent.mo313toPx0680j_4(TextFieldCursorKt.getDefaultCursorThickness());
                                float f = 2;
                                float cursorX = RangesKt.coerceAtLeast(RangesKt.coerceAtMost(cursorRect.getLeft() + (cursorWidth / f), Size.m3234getWidthimpl($this$drawWithContent.mo3956getSizeNHjbRc()) - (cursorWidth / f)), cursorWidth / f);
                                DrawScope.m3942drawLine1RTmtNc$default($this$drawWithContent, brush, OffsetKt.Offset(cursorX, cursorRect.getTop()), OffsetKt.Offset(cursorX, cursorRect.getBottom()), cursorWidth, 0, null, cursorAlphaValue, null, 0, 432, null);
                            }
                        }
                    });
                } else {
                    companionDrawWithContent = Modifier.INSTANCE;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                $composer.endReplaceableGroup();
                return companionDrawWithContent;
            }

            /* JADX INFO: renamed from: androidx.compose.foundation.text.TextFieldCursorKt$cursor$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: TextFieldCursor.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @DebugMetadata(c = "androidx.compose.foundation.text.TextFieldCursorKt$cursor$1$1", f = "TextFieldCursor.kt", i = {}, l = {51}, m = "invokeSuspend", n = {}, s = {})
            static final class C00471 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Animatable<Float, AnimationVector1D> $cursorAlpha;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00471(Animatable<Float, AnimationVector1D> animatable, Continuation<? super C00471> continuation) {
                    super(2, continuation);
                    this.$cursorAlpha = animatable;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00471(this.$cursorAlpha, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object $result) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    switch (this.label) {
                        case 0:
                            ResultKt.throwOnFailure($result);
                            this.label = 1;
                            if (BuildersKt.withContext(FixedMotionDurationScale.INSTANCE, new C00481(this.$cursorAlpha, null), this) == coroutine_suspended) {
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

                /* JADX INFO: renamed from: androidx.compose.foundation.text.TextFieldCursorKt$cursor$1$1$1, reason: invalid class name and collision with other inner class name */
                /* JADX INFO: compiled from: TextFieldCursor.kt */
                @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "androidx.compose.foundation.text.TextFieldCursorKt$cursor$1$1$1", f = "TextFieldCursor.kt", i = {}, l = {53, FirestoreIndexValueWriter.INDEX_TYPE_MAP}, m = "invokeSuspend", n = {}, s = {})
                static final class C00481 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ Animatable<Float, AnimationVector1D> $cursorAlpha;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C00481(Animatable<Float, AnimationVector1D> animatable, Continuation<? super C00481> continuation) {
                        super(2, continuation);
                        this.$cursorAlpha = animatable;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        return new C00481(this.$cursorAlpha, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((C00481) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:13:0x0051 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:14:0x0052  */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
                        /*
                            r10 = this;
                            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r1 = r10.label
                            switch(r1) {
                                case 0: goto L1c;
                                case 1: goto L17;
                                case 2: goto L12;
                                default: goto L9;
                            }
                        L9:
                            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                            r11.<init>(r0)
                            throw r11
                        L12:
                            r0 = r10
                            kotlin.ResultKt.throwOnFailure(r11)
                            goto L53
                        L17:
                            r1 = r10
                            kotlin.ResultKt.throwOnFailure(r11)
                            goto L35
                        L1c:
                            kotlin.ResultKt.throwOnFailure(r11)
                            r1 = r10
                            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r2 = r1.$cursorAlpha
                            r3 = 1065353216(0x3f800000, float:1.0)
                            java.lang.Float r3 = kotlin.coroutines.jvm.internal.Boxing.boxFloat(r3)
                            r4 = r1
                            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                            r5 = 1
                            r1.label = r5
                            java.lang.Object r2 = r2.snapTo(r3, r4)
                            if (r2 != r0) goto L35
                            return r0
                        L35:
                            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r2 = r1.$cursorAlpha
                            r3 = 0
                            java.lang.Float r3 = kotlin.coroutines.jvm.internal.Boxing.boxFloat(r3)
                            androidx.compose.animation.core.AnimationSpec r4 = androidx.compose.foundation.text.TextFieldCursorKt.access$getCursorAnimationSpec$p()
                            r7 = r1
                            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
                            r5 = 2
                            r1.label = r5
                            r5 = 0
                            r6 = 0
                            r8 = 12
                            r9 = 0
                            java.lang.Object r2 = androidx.compose.animation.core.Animatable.animateTo$default(r2, r3, r4, r5, r6, r7, r8, r9)
                            if (r2 != r0) goto L52
                            return r0
                        L52:
                            r0 = r1
                        L53:
                            kotlin.Unit r1 = kotlin.Unit.INSTANCE
                            return r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextFieldCursorKt.AnonymousClass1.C00471.C00481.invokeSuspend(java.lang.Object):java.lang.Object");
                    }
                }
            }
        }, 1, null) : $this$cursor;
    }

    public static final float getDefaultCursorThickness() {
        return DefaultCursorThickness;
    }
}
