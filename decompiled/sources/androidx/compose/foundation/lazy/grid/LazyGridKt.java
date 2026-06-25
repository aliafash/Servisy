package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.CheckScrollableContainerConstraintsKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsStateKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffsetKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: LazyGrid.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a~\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0014\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u0015¢\u0006\u0002\b\u0017H\u0001¢\u0006\u0002\u0010\u0018\u001av\u0010\u0019\u001a\u0019\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001a¢\u0006\u0002\b\u00172\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010!\u001a\u00020\"H\u0003¢\u0006\u0002\u0010#¨\u0006$"}, d2 = {"LazyGrid", "", "modifier", "Landroidx/compose/ui/Modifier;", "state", "Landroidx/compose/foundation/lazy/grid/LazyGridState;", "slots", "Landroidx/compose/foundation/lazy/grid/LazyGridSlotsProvider;", "contentPadding", "Landroidx/compose/foundation/layout/PaddingValues;", "reverseLayout", "", "isVertical", "flingBehavior", "Landroidx/compose/foundation/gestures/FlingBehavior;", "userScrollEnabled", "verticalArrangement", "Landroidx/compose/foundation/layout/Arrangement$Vertical;", "horizontalArrangement", "Landroidx/compose/foundation/layout/Arrangement$Horizontal;", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/lazy/grid/LazyGridScope;", "Lkotlin/ExtensionFunctionType;", "(Landroidx/compose/ui/Modifier;Landroidx/compose/foundation/lazy/grid/LazyGridState;Landroidx/compose/foundation/lazy/grid/LazyGridSlotsProvider;Landroidx/compose/foundation/layout/PaddingValues;ZZLandroidx/compose/foundation/gestures/FlingBehavior;ZLandroidx/compose/foundation/layout/Arrangement$Vertical;Landroidx/compose/foundation/layout/Arrangement$Horizontal;Lkotlin/jvm/functions/Function1;Landroidx/compose/runtime/Composer;III)V", "rememberLazyGridMeasurePolicy", "Lkotlin/Function2;", "Landroidx/compose/foundation/lazy/layout/LazyLayoutMeasureScope;", "Landroidx/compose/ui/unit/Constraints;", "Landroidx/compose/ui/layout/MeasureResult;", "itemProviderLambda", "Lkotlin/Function0;", "Landroidx/compose/foundation/lazy/grid/LazyGridItemProvider;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "(Lkotlin/jvm/functions/Function0;Landroidx/compose/foundation/lazy/grid/LazyGridState;Landroidx/compose/foundation/lazy/grid/LazyGridSlotsProvider;Landroidx/compose/foundation/layout/PaddingValues;ZZLandroidx/compose/foundation/layout/Arrangement$Horizontal;Landroidx/compose/foundation/layout/Arrangement$Vertical;Lkotlinx/coroutines/CoroutineScope;Landroidx/compose/runtime/Composer;I)Lkotlin/jvm/functions/Function2;", "foundation_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class LazyGridKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void LazyGrid(androidx.compose.ui.Modifier r42, final androidx.compose.foundation.lazy.grid.LazyGridState r43, final androidx.compose.foundation.lazy.grid.LazyGridSlotsProvider r44, androidx.compose.foundation.layout.PaddingValues r45, boolean r46, final boolean r47, androidx.compose.foundation.gestures.FlingBehavior r48, final boolean r49, final androidx.compose.foundation.layout.Arrangement.Vertical r50, final androidx.compose.foundation.layout.Arrangement.Horizontal r51, final kotlin.jvm.functions.Function1<? super androidx.compose.foundation.lazy.grid.LazyGridScope, kotlin.Unit> r52, androidx.compose.runtime.Composer r53, final int r54, final int r55, final int r56) {
        /*
            Method dump skipped, instruction units count: 1004
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridKt.LazyGrid(androidx.compose.ui.Modifier, androidx.compose.foundation.lazy.grid.LazyGridState, androidx.compose.foundation.lazy.grid.LazyGridSlotsProvider, androidx.compose.foundation.layout.PaddingValues, boolean, boolean, androidx.compose.foundation.gestures.FlingBehavior, boolean, androidx.compose.foundation.layout.Arrangement$Vertical, androidx.compose.foundation.layout.Arrangement$Horizontal, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int, int):void");
    }

    private static final Function2<LazyLayoutMeasureScope, Constraints, MeasureResult> rememberLazyGridMeasurePolicy(final Function0<? extends LazyGridItemProvider> function0, final LazyGridState state, final LazyGridSlotsProvider slots, final PaddingValues contentPadding, final boolean reverseLayout, final boolean isVertical, final Arrangement.Horizontal horizontalArrangement, final Arrangement.Vertical verticalArrangement, final CoroutineScope coroutineScope, Composer $composer, int $changed) {
        Object value$iv$iv;
        $composer.startReplaceableGroup(-2068958445);
        ComposerKt.sourceInformation($composer, "C(rememberLazyGridMeasurePolicy)P(4,7,6!1,5,3,2,8)167@6903L8834:LazyGrid.kt#7791vq");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-2068958445, $changed, -1, "androidx.compose.foundation.lazy.grid.rememberLazyGridMeasurePolicy (LazyGrid.kt:167)");
        }
        Object[] keys$iv = {state, slots, contentPadding, Boolean.valueOf(reverseLayout), Boolean.valueOf(isVertical), horizontalArrangement, verticalArrangement};
        $composer.startReplaceableGroup(-568225417);
        ComposerKt.sourceInformation($composer, "CC(remember)P(1):Composables.kt#9igjgp");
        boolean invalid$iv = false;
        for (Object key$iv : keys$iv) {
            invalid$iv |= $composer.changed(key$iv);
        }
        Object it$iv$iv = $composer.rememberedValue();
        if (invalid$iv || it$iv$iv == Composer.INSTANCE.getEmpty()) {
            value$iv$iv = new Function2<LazyLayoutMeasureScope, Constraints, LazyGridMeasureResult>() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ LazyGridMeasureResult invoke(LazyLayoutMeasureScope lazyLayoutMeasureScope, Constraints constraints) {
                    return m698invoke0kLqBqw(lazyLayoutMeasureScope, constraints.getValue());
                }

                /* JADX WARN: Type inference failed for: r0v47, types: [androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1] */
                /* JADX WARN: Type inference failed for: r0v48, types: [androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1] */
                /* JADX INFO: renamed from: invoke-0kLqBqw, reason: not valid java name */
                public final LazyGridMeasureResult m698invoke0kLqBqw(final LazyLayoutMeasureScope $this$null, final long containerConstraints) throws Throwable {
                    int i;
                    int i2;
                    int i3;
                    float spacing;
                    int iM5689getMaxWidthimpl;
                    final long visualItemOffset;
                    Snapshot previous$iv$iv;
                    int firstVisibleLineIndex;
                    int firstVisibleLineScrollOffset;
                    CheckScrollableContainerConstraintsKt.m238checkScrollableContainerConstraintsK40F9xA(containerConstraints, isVertical ? Orientation.Vertical : Orientation.Horizontal);
                    if (isVertical) {
                        i = $this$null.mo307roundToPx0680j_4(contentPadding.mo513calculateLeftPaddingu2uoSUM($this$null.getLayoutDirection()));
                    } else {
                        i = $this$null.mo307roundToPx0680j_4(PaddingKt.calculateStartPadding(contentPadding, $this$null.getLayoutDirection()));
                    }
                    int startPadding = i;
                    if (isVertical) {
                        i2 = $this$null.mo307roundToPx0680j_4(contentPadding.mo514calculateRightPaddingu2uoSUM($this$null.getLayoutDirection()));
                    } else {
                        i2 = $this$null.mo307roundToPx0680j_4(PaddingKt.calculateEndPadding(contentPadding, $this$null.getLayoutDirection()));
                    }
                    int endPadding = i2;
                    int topPadding = $this$null.mo307roundToPx0680j_4(contentPadding.getTop());
                    int bottomPadding = $this$null.mo307roundToPx0680j_4(contentPadding.getBottom());
                    final int totalVerticalPadding = topPadding + bottomPadding;
                    final int totalHorizontalPadding = startPadding + endPadding;
                    int totalMainAxisPadding = isVertical ? totalVerticalPadding : totalHorizontalPadding;
                    if (isVertical && !reverseLayout) {
                        i3 = topPadding;
                    } else if (isVertical && reverseLayout) {
                        i3 = bottomPadding;
                    } else {
                        i3 = (isVertical || reverseLayout) ? endPadding : startPadding;
                    }
                    final int beforeContentPadding = i3;
                    final int afterContentPadding = totalMainAxisPadding - beforeContentPadding;
                    long contentConstraints = ConstraintsKt.m5705offsetNN6EwU(containerConstraints, -totalHorizontalPadding, -totalVerticalPadding);
                    final LazyGridItemProvider itemProvider = function0.invoke();
                    final LazyGridSpanLayoutProvider spanLayoutProvider = itemProvider.getSpanLayoutProvider();
                    final LazyGridSlots resolvedSlots = slots.mo687invoke0kLqBqw($this$null, containerConstraints);
                    int slotsPerLine = resolvedSlots.getSizes().length;
                    spanLayoutProvider.setSlotsPerLine(slotsPerLine);
                    state.setDensity$foundation_release($this$null);
                    state.setSlotsPerLine$foundation_release(slotsPerLine);
                    if (isVertical) {
                        Arrangement.Vertical vertical = verticalArrangement;
                        if (vertical == null) {
                            throw new IllegalArgumentException("null verticalArrangement when isVertical == true".toString());
                        }
                        spacing = vertical.getSpacing();
                    } else {
                        Arrangement.Horizontal horizontal = horizontalArrangement;
                        if (horizontal == null) {
                            throw new IllegalArgumentException("null horizontalArrangement when isVertical == false".toString());
                        }
                        spacing = horizontal.getSpacing();
                    }
                    float spaceBetweenLinesDp = spacing;
                    final int spaceBetweenLines = $this$null.mo307roundToPx0680j_4(spaceBetweenLinesDp);
                    final int itemsCount = itemProvider.getItemCount();
                    if (isVertical) {
                        iM5689getMaxWidthimpl = Constraints.m5688getMaxHeightimpl(containerConstraints) - totalVerticalPadding;
                    } else {
                        iM5689getMaxWidthimpl = Constraints.m5689getMaxWidthimpl(containerConstraints) - totalHorizontalPadding;
                    }
                    int mainAxisAvailableSize = iM5689getMaxWidthimpl;
                    if (!reverseLayout || mainAxisAvailableSize > 0) {
                        visualItemOffset = IntOffsetKt.IntOffset(startPadding, topPadding);
                    } else {
                        visualItemOffset = IntOffsetKt.IntOffset(isVertical ? startPadding : startPadding + mainAxisAvailableSize, isVertical ? topPadding + mainAxisAvailableSize : topPadding);
                    }
                    final LazyGridState lazyGridState = state;
                    final boolean z = isVertical;
                    final boolean z2 = reverseLayout;
                    final ?? r0 = new LazyGridMeasuredItemProvider(itemProvider, $this$null, spaceBetweenLines, lazyGridState, z, z2, beforeContentPadding, afterContentPadding, visualItemOffset) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1
                        final /* synthetic */ int $afterContentPadding;
                        final /* synthetic */ int $beforeContentPadding;
                        final /* synthetic */ boolean $isVertical;
                        final /* synthetic */ boolean $reverseLayout;
                        final /* synthetic */ LazyGridState $state;
                        final /* synthetic */ LazyLayoutMeasureScope $this_null;
                        final /* synthetic */ long $visualItemOffset;

                        {
                            this.$this_null = $this$null;
                            this.$state = lazyGridState;
                            this.$isVertical = z;
                            this.$reverseLayout = z2;
                            this.$beforeContentPadding = beforeContentPadding;
                            this.$afterContentPadding = afterContentPadding;
                            this.$visualItemOffset = visualItemOffset;
                        }

                        @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider
                        public LazyGridMeasuredItem createItem(int index, Object key, Object contentType, int crossAxisSize, int mainAxisSpacing, List<? extends Placeable> placeables) {
                            return new LazyGridMeasuredItem(index, key, this.$isVertical, crossAxisSize, mainAxisSpacing, this.$reverseLayout, this.$this_null.getLayoutDirection(), this.$beforeContentPadding, this.$afterContentPadding, placeables, this.$visualItemOffset, contentType, this.$state.getPlacementAnimator(), null);
                        }
                    };
                    final boolean z3 = isVertical;
                    final ?? r02 = new LazyGridMeasuredLineProvider(z3, resolvedSlots, itemsCount, spaceBetweenLines, r0, spanLayoutProvider) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1
                        final /* synthetic */ boolean $isVertical;
                        final /* synthetic */ LazyGridSlots $resolvedSlots;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(z3, resolvedSlots, itemsCount, spaceBetweenLines, r0, spanLayoutProvider);
                            this.$isVertical = z3;
                            this.$resolvedSlots = resolvedSlots;
                        }

                        @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider
                        public LazyGridMeasuredLine createLine(int index, LazyGridMeasuredItem[] items, List<GridItemSpan> spans, int mainAxisSpacing) {
                            return new LazyGridMeasuredLine(index, items, this.$resolvedSlots, spans, this.$isVertical, mainAxisSpacing);
                        }
                    };
                    state.setPrefetchInfoRetriever$foundation_release(new Function1<Integer, ArrayList<Pair<? extends Integer, ? extends Constraints>>>() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ ArrayList<Pair<? extends Integer, ? extends Constraints>> invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final ArrayList<Pair<Integer, Constraints>> invoke(int line) {
                            LazyGridSpanLayoutProvider.LineConfiguration lineConfiguration = spanLayoutProvider.getLineConfiguration(line);
                            int index = lineConfiguration.getFirstItemIndex();
                            int slot = 0;
                            ArrayList<Pair<Integer, Constraints>> arrayList = new ArrayList<>(lineConfiguration.getSpans().size());
                            List<GridItemSpan> spans = lineConfiguration.getSpans();
                            LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 = r02;
                            int index$iv = 0;
                            int size = spans.size();
                            while (index$iv < size) {
                                Object item$iv = spans.get(index$iv);
                                long it = ((GridItemSpan) item$iv).getPackedValue();
                                int span = GridItemSpan.m683getCurrentLineSpanimpl(it);
                                arrayList.add(TuplesKt.to(Integer.valueOf(index), Constraints.m5677boximpl(lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1.m705childConstraintsJhjzzOo$foundation_release(slot, span))));
                                index++;
                                slot += span;
                                index$iv++;
                                lineConfiguration = lineConfiguration;
                            }
                            return arrayList;
                        }
                    });
                    Snapshot.Companion this_$iv = Snapshot.INSTANCE;
                    LazyGridState lazyGridState2 = state;
                    Snapshot snapshot$iv = this_$iv.createNonObservableSnapshot();
                    try {
                        Snapshot previous$iv$iv2 = snapshot$iv.makeCurrent();
                        int firstVisibleLineIndex2 = 0;
                        try {
                            int index = lazyGridState2.updateScrollPositionIfTheFirstItemWasMoved$foundation_release(itemProvider, lazyGridState2.getFirstVisibleItemIndex());
                            if (index < itemsCount || itemsCount <= 0) {
                                try {
                                    firstVisibleLineIndex = spanLayoutProvider.getLineIndexOfItem(index);
                                } catch (Throwable th) {
                                    th = th;
                                    previous$iv$iv = previous$iv$iv2;
                                }
                                try {
                                    firstVisibleLineScrollOffset = lazyGridState2.getFirstVisibleItemScrollOffset();
                                    try {
                                        Unit unit = Unit.INSTANCE;
                                        try {
                                            snapshot$iv.restoreCurrent(previous$iv$iv2);
                                            snapshot$iv.dispose();
                                            LazyGridMeasureResult it = LazyGridMeasureKt.m700measureLazyGridW2FL7xs(itemsCount, (LazyGridMeasuredLineProvider) r02, (LazyGridMeasuredItemProvider) r0, mainAxisAvailableSize, beforeContentPadding, afterContentPadding, spaceBetweenLines, firstVisibleLineIndex, firstVisibleLineScrollOffset, state.getScrollToBeConsumed(), contentConstraints, isVertical, verticalArrangement, horizontalArrangement, reverseLayout, $this$null, state.getPlacementAnimator(), spanLayoutProvider, LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(itemProvider, state.getPinnedItems(), state.getBeyondBoundsInfo()), coroutineScope, state.m707getPlacementScopeInvalidatorzYiylxw$foundation_release(), new Function3<Integer, Integer, Function1<? super Placeable.PlacementScope, ? extends Unit>, MeasureResult>() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.3
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public /* bridge */ /* synthetic */ MeasureResult invoke(Integer num, Integer num2, Function1<? super Placeable.PlacementScope, ? extends Unit> function1) {
                                                    return invoke(num.intValue(), num2.intValue(), (Function1<? super Placeable.PlacementScope, Unit>) function1);
                                                }

                                                public final MeasureResult invoke(int width, int height, Function1<? super Placeable.PlacementScope, Unit> function1) {
                                                    return $this$null.layout(ConstraintsKt.m5703constrainWidthK40F9xA(containerConstraints, totalHorizontalPadding + width), ConstraintsKt.m5702constrainHeightK40F9xA(containerConstraints, totalVerticalPadding + height), MapsKt.emptyMap(), function1);
                                                }
                                            });
                                            LazyGridState.applyMeasureResult$foundation_release$default(state, it, false, 2, null);
                                            return it;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        previous$iv$iv = previous$iv$iv2;
                                        firstVisibleLineIndex2 = firstVisibleLineIndex;
                                        try {
                                            snapshot$iv.restoreCurrent(previous$iv$iv);
                                            throw th;
                                        } catch (Throwable th4) {
                                            th = th4;
                                        }
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                    previous$iv$iv = previous$iv$iv2;
                                    firstVisibleLineIndex2 = firstVisibleLineIndex;
                                    snapshot$iv.restoreCurrent(previous$iv$iv);
                                    throw th;
                                }
                            } else {
                                try {
                                    firstVisibleLineIndex = spanLayoutProvider.getLineIndexOfItem(itemsCount - 1);
                                    firstVisibleLineScrollOffset = 0;
                                    Unit unit2 = Unit.INSTANCE;
                                    snapshot$iv.restoreCurrent(previous$iv$iv2);
                                    snapshot$iv.dispose();
                                    LazyGridMeasureResult it2 = LazyGridMeasureKt.m700measureLazyGridW2FL7xs(itemsCount, (LazyGridMeasuredLineProvider) r02, (LazyGridMeasuredItemProvider) r0, mainAxisAvailableSize, beforeContentPadding, afterContentPadding, spaceBetweenLines, firstVisibleLineIndex, firstVisibleLineScrollOffset, state.getScrollToBeConsumed(), contentConstraints, isVertical, verticalArrangement, horizontalArrangement, reverseLayout, $this$null, state.getPlacementAnimator(), spanLayoutProvider, LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(itemProvider, state.getPinnedItems(), state.getBeyondBoundsInfo()), coroutineScope, state.m707getPlacementScopeInvalidatorzYiylxw$foundation_release(), new Function3<Integer, Integer, Function1<? super Placeable.PlacementScope, ? extends Unit>, MeasureResult>() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ MeasureResult invoke(Integer num, Integer num2, Function1<? super Placeable.PlacementScope, ? extends Unit> function1) {
                                            return invoke(num.intValue(), num2.intValue(), (Function1<? super Placeable.PlacementScope, Unit>) function1);
                                        }

                                        public final MeasureResult invoke(int width, int height, Function1<? super Placeable.PlacementScope, Unit> function1) {
                                            return $this$null.layout(ConstraintsKt.m5703constrainWidthK40F9xA(containerConstraints, totalHorizontalPadding + width), ConstraintsKt.m5702constrainHeightK40F9xA(containerConstraints, totalVerticalPadding + height), MapsKt.emptyMap(), function1);
                                        }
                                    });
                                    LazyGridState.applyMeasureResult$foundation_release$default(state, it2, false, 2, null);
                                    return it2;
                                } catch (Throwable th6) {
                                    th = th6;
                                    previous$iv$iv = previous$iv$iv2;
                                    snapshot$iv.restoreCurrent(previous$iv$iv);
                                    throw th;
                                }
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            previous$iv$iv = previous$iv$iv2;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                    }
                    snapshot$iv.dispose();
                    throw th;
                }
            };
            $composer.updateRememberedValue(value$iv$iv);
        } else {
            value$iv$iv = it$iv$iv;
        }
        $composer.endReplaceableGroup();
        Function2<LazyLayoutMeasureScope, Constraints, MeasureResult> function2 = (Function2) value$iv$iv;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        $composer.endReplaceableGroup();
        return function2;
    }
}
