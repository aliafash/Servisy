package androidx.compose.material3;

import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.tokens.AssistChipTokens;
import androidx.compose.material3.tokens.FilterChipTokens;
import androidx.compose.material3.tokens.InputChipTokens;
import androidx.compose.material3.tokens.SuggestionChipTokens;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.profileinstaller.ProfileVerifier;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Chip.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u001a¦\u0001\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010%\u001a¦\u0001\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010&2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010'\u001a¹\u0001\u0010(\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0013\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0013\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0003ø\u0001\u0000¢\u0006\u0004\b/\u00100\u001a\u0094\u0001\u00101\u001a\u00020\u00112\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0013\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0013\u00102\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0013\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0006\u00103\u001a\u00020,2\u0006\u00104\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a¦\u0001\u00107\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010%\u001a¦\u0001\u00107\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010&2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010'\u001a®\u0001\u00108\u001a\u00020\u00112\u0006\u00109\u001a\u00020\u00192\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020:2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010;2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010<\u001a\u008f\u0001\u0010=\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010>\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010?\u001a\u008f\u0001\u0010=\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010>\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010&2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010@\u001a®\u0001\u0010A\u001a\u00020\u00112\u0006\u00109\u001a\u00020\u00192\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020:2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010;2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010<\u001aÅ\u0001\u0010B\u001a\u00020\u00112\u0006\u00109\u001a\u00020\u00192\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u00102\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0015\b\u0002\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020:2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010;2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010C\u001aÎ\u0001\u0010D\u001a\u00020\u00112\u0006\u00109\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\u0006\u0010)\u001a\u00020*2\u0013\u0010\u001a\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0013\u00102\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0013\u0010\u001b\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020:2\b\u0010\u001f\u001a\u0004\u0018\u00010;2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0003ø\u0001\u0000¢\u0006\u0004\bE\u0010F\u001a\u008f\u0001\u0010G\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010>\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010?\u001a\u008f\u0001\u0010G\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00132\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u00110\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0015\b\u0002\u0010>\u001a\u000f\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0013¢\u0006\u0002\b\u00152\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010&2\b\b\u0002\u0010#\u001a\u00020$H\u0007¢\u0006\u0002\u0010@\u001a&\u0010H\u001a\u00020\u00012\b\b\u0002\u0010I\u001a\u00020\u00192\b\b\u0002\u0010J\u001a\u00020\u00192\b\b\u0002\u0010K\u001a\u00020\u0019H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u0018\u0010\u000b\u001a\u00020\f*\u00020\r8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006L"}, d2 = {"AssistChipPadding", "Landroidx/compose/foundation/layout/PaddingValues;", "FilterChipPadding", "HorizontalElementsPadding", "Landroidx/compose/ui/unit/Dp;", "F", "LabelLayoutId", "", "LeadingIconLayoutId", "SuggestionChipPadding", "TrailingIconLayoutId", "defaultSuggestionChipColors", "Landroidx/compose/material3/ChipColors;", "Landroidx/compose/material3/ColorScheme;", "getDefaultSuggestionChipColors", "(Landroidx/compose/material3/ColorScheme;)Landroidx/compose/material3/ChipColors;", "AssistChip", "", "onClick", "Lkotlin/Function0;", ChipKt.LabelLayoutId, "Landroidx/compose/runtime/Composable;", "modifier", "Landroidx/compose/ui/Modifier;", "enabled", "", ChipKt.LeadingIconLayoutId, ChipKt.TrailingIconLayoutId, "shape", "Landroidx/compose/ui/graphics/Shape;", "colors", "elevation", "Landroidx/compose/material3/ChipElevation;", "border", "Landroidx/compose/foundation/BorderStroke;", "interactionSource", "Landroidx/compose/foundation/interaction/MutableInteractionSource;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/ChipColors;Landroidx/compose/material3/ChipElevation;Landroidx/compose/foundation/BorderStroke;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;III)V", "Landroidx/compose/material3/ChipBorder;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/ChipColors;Landroidx/compose/material3/ChipElevation;Landroidx/compose/material3/ChipBorder;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;III)V", "Chip", "labelTextStyle", "Landroidx/compose/ui/text/TextStyle;", "labelColor", "Landroidx/compose/ui/graphics/Color;", "minHeight", "paddingValues", "Chip-nkUnTEs", "(Landroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function0;ZLkotlin/jvm/functions/Function2;Landroidx/compose/ui/text/TextStyle;JLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/ChipColors;Landroidx/compose/material3/ChipElevation;Landroidx/compose/foundation/BorderStroke;FLandroidx/compose/foundation/layout/PaddingValues;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;II)V", "ChipContent", "avatar", "leadingIconColor", "trailingIconColor", "ChipContent-fe0OD_I", "(Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/text/TextStyle;JLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;JJFLandroidx/compose/foundation/layout/PaddingValues;Landroidx/compose/runtime/Composer;I)V", "ElevatedAssistChip", "ElevatedFilterChip", "selected", "Landroidx/compose/material3/SelectableChipColors;", "Landroidx/compose/material3/SelectableChipElevation;", "(ZLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/SelectableChipColors;Landroidx/compose/material3/SelectableChipElevation;Landroidx/compose/foundation/BorderStroke;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;III)V", "ElevatedSuggestionChip", "icon", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/ChipColors;Landroidx/compose/material3/ChipElevation;Landroidx/compose/foundation/BorderStroke;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;II)V", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/ChipColors;Landroidx/compose/material3/ChipElevation;Landroidx/compose/material3/ChipBorder;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;II)V", "FilterChip", "InputChip", "(ZLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/Modifier;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/SelectableChipColors;Landroidx/compose/material3/SelectableChipElevation;Landroidx/compose/foundation/BorderStroke;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;III)V", "SelectableChip", "SelectableChip-u0RnIRE", "(ZLandroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function0;ZLkotlin/jvm/functions/Function2;Landroidx/compose/ui/text/TextStyle;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Landroidx/compose/ui/graphics/Shape;Landroidx/compose/material3/SelectableChipColors;Landroidx/compose/material3/SelectableChipElevation;Landroidx/compose/foundation/BorderStroke;FLandroidx/compose/foundation/layout/PaddingValues;Landroidx/compose/foundation/interaction/MutableInteractionSource;Landroidx/compose/runtime/Composer;II)V", "SuggestionChip", "inputChipPadding", "hasAvatar", "hasLeadingIcon", "hasTrailingIcon", "material3_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
public final class ChipKt {
    private static final String LabelLayoutId = "label";
    private static final String LeadingIconLayoutId = "leadingIcon";
    private static final String TrailingIconLayoutId = "trailingIcon";
    private static final float HorizontalElementsPadding = Dp.m5733constructorimpl(8);
    private static final PaddingValues AssistChipPadding = PaddingKt.m557PaddingValuesYgX7TsA$default(HorizontalElementsPadding, 0.0f, 2, null);
    private static final PaddingValues FilterChipPadding = PaddingKt.m557PaddingValuesYgX7TsA$default(HorizontalElementsPadding, 0.0f, 2, null);
    private static final PaddingValues SuggestionChipPadding = PaddingKt.m557PaddingValuesYgX7TsA$default(HorizontalElementsPadding, 0.0f, 2, null);

    public static final void AssistChip(final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Shape shape, ChipColors colors, ChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean enabled2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape2;
        int $dirty;
        ChipColors colors3;
        Modifier modifier3;
        int $dirty1;
        ChipElevation elevation2;
        BorderStroke border2;
        MutableInteractionSource interactionSource2;
        BorderStroke border3;
        Function2<? super Composer, ? super Integer, Unit> function24;
        ChipElevation elevation3;
        Shape shape3;
        Function2<? super Composer, ? super Integer, Unit> function25;
        int $dirty2;
        Object value$iv;
        int $dirty12;
        Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Shape shape4;
        ChipElevation elevation4;
        ChipColors colors4;
        Modifier modifier4;
        BorderStroke border4;
        boolean enabled3;
        MutableInteractionSource interactionSource3;
        Composer $composer2 = $composer.startRestartGroup(313450168);
        ComposerKt.sourceInformation($composer2, "C(AssistChip)P(8,5,7,3,6,10,9,1,2)119@6048L5,120@6099L18,121@6170L21,122@6240L25,123@6317L39,129@6493L10,124@6362L507:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
            modifier2 = modifier;
        } else if (($changed & 384) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changedInstance(function22) ? 16384 : 8192;
        }
        int i6 = i & 32;
        if (i6 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty3 |= $composer2.changedInstance(function23) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty3 |= ((i & 64) == 0 && $composer2.changed(shape)) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 8388608 : 4194304;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(elevation)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty3 |= ((i & 512) == 0 && $composer2.changed(border)) ? 536870912 : 268435456;
        }
        int i8 = i & 1024;
        if (i8 != 0) {
            $dirty13 |= 6;
            i2 = i8;
            mutableInteractionSource = interactionSource;
        } else if (($changed1 & 6) == 0) {
            i2 = i8;
            mutableInteractionSource = interactionSource;
            $dirty13 |= $composer2.changed(mutableInteractionSource) ? 4 : 2;
        } else {
            i2 = i8;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && ($dirty13 & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            function26 = function22;
            function27 = function23;
            shape4 = shape;
            elevation4 = elevation;
            border4 = border;
            $dirty12 = $dirty13;
            colors4 = colors2;
            modifier4 = modifier2;
            enabled3 = enabled2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i3 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i4 != 0 ? true : enabled2;
                Function2<? super Composer, ? super Integer, Unit> function28 = i5 != 0 ? null : function22;
                Function2<? super Composer, ? super Integer, Unit> function29 = i6 != 0 ? null : function23;
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                    shape2 = AssistChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape2 = shape;
                }
                if ((i & 128) != 0) {
                    $dirty = $dirty3 & (-29360129);
                    colors3 = AssistChipDefaults.INSTANCE.assistChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 256) != 0) {
                    $dirty1 = $dirty13;
                    modifier3 = modifier5;
                    $dirty &= -234881025;
                    elevation2 = AssistChipDefaults.INSTANCE.m1239assistChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                } else {
                    modifier3 = modifier5;
                    $dirty1 = $dirty13;
                    elevation2 = elevation;
                }
                if ((i & 512) != 0) {
                    border2 = AssistChipDefaults.INSTANCE.m1237assistChipBorderh1eTWw(enabled4, 0L, 0L, 0.0f, $composer2, (($dirty >> 9) & 14) | 24576, 14);
                    $dirty &= -1879048193;
                } else {
                    border2 = border;
                }
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(839902005);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    MutableInteractionSource interactionSource4 = (MutableInteractionSource) value$iv;
                    $composer2.endReplaceableGroup();
                    modifier2 = modifier3;
                    border3 = border2;
                    interactionSource2 = interactionSource4;
                    function24 = function29;
                    elevation3 = elevation2;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    function25 = function28;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                } else {
                    modifier2 = modifier3;
                    interactionSource2 = interactionSource;
                    border3 = border2;
                    function24 = function29;
                    elevation3 = elevation2;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    function25 = function28;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    $dirty3 &= -234881025;
                }
                if ((i & 512) != 0) {
                    int i9 = (-1879048193) & $dirty3;
                    function25 = function22;
                    shape3 = shape;
                    elevation3 = elevation;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    function24 = function23;
                    $dirty2 = i9;
                } else {
                    shape3 = shape;
                    elevation3 = elevation;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    function24 = function23;
                    $dirty2 = $dirty3;
                    function25 = function22;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                $dirty12 = $dirty1;
                ComposerKt.traceEventStart(313450168, $dirty2, $dirty12, "androidx.compose.material3.AssistChip (Chip.kt:124)");
            } else {
                $dirty12 = $dirty1;
            }
            m1340ChipnkUnTEs(modifier2, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), AssistChipTokens.INSTANCE.getLabelTextFont()), colors2.m1330labelColorvNxB06k$material3_release(enabled2), function25, function24, shape3, colors2, elevation3, border3, AssistChipDefaults.INSTANCE.m1242getHeightD9Ej5fM(), AssistChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 6) & 29360128) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 3456 | (($dirty2 >> 24) & 112) | (($dirty12 << 12) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function26 = function25;
            function27 = function24;
            shape4 = shape3;
            elevation4 = elevation3;
            colors4 = colors2;
            modifier4 = modifier2;
            border4 = border3;
            enabled3 = enabled2;
            interactionSource3 = interactionSource2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function210 = function26;
            final Function2<? super Composer, ? super Integer, Unit> function211 = function27;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final BorderStroke borderStroke = border4;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.AssistChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i10) {
                    ChipKt.AssistChip(function0, function2, modifier6, z, function210, function211, shape5, chipColors, chipElevation, borderStroke, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Maintained for binary compatibility. Use version with AssistChip that take a BorderStroke instead", replaceWith = @ReplaceWith(expression = "AssistChip(onClick, label, modifier, enabled,leadingIcon, trailingIcon, shape, colors, elevation, border, interactionSource", imports = {}))
    public static final /* synthetic */ void AssistChip(final Function0 onClick, final Function2 label, Modifier modifier, boolean enabled, Function2 leadingIcon, Function2 trailingIcon, Shape shape, ChipColors colors, ChipElevation elevation, ChipBorder border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean enabled2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape2;
        int $dirty;
        ChipColors colors3;
        Modifier modifier3;
        int $dirty1;
        ChipElevation elevation2;
        ChipBorder border2;
        MutableInteractionSource interactionSource2;
        ChipBorder border3;
        Function2 trailingIcon2;
        Shape shape3;
        Function2 leadingIcon2;
        int $dirty2;
        Object value$iv;
        int $dirty12;
        Function2 leadingIcon3;
        Function2 trailingIcon3;
        Shape shape4;
        ChipBorder border4;
        ChipColors colors4;
        Modifier modifier4;
        MutableInteractionSource interactionSource3;
        boolean enabled3;
        ChipElevation elevation3;
        Composer $composer2 = $composer.startRestartGroup(-1932300596);
        ComposerKt.sourceInformation($composer2, "C(AssistChip)P(8,5,7,3,6,10,9,1,2)199@10095L5,200@10146L18,201@10217L21,202@10285L18,203@10355L39,209@10531L10,204@10400L537:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(onClick) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(label) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
            modifier2 = modifier;
        } else if (($changed & 384) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changedInstance(leadingIcon) ? 16384 : 8192;
        }
        int i6 = i & 32;
        if (i6 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty3 |= $composer2.changedInstance(trailingIcon) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty3 |= ((i & 64) == 0 && $composer2.changed(shape)) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 8388608 : 4194304;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(elevation)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty3 |= ((i & 512) == 0 && $composer2.changed(border)) ? 536870912 : 268435456;
        }
        int i8 = i & 1024;
        if (i8 != 0) {
            $dirty13 |= 6;
            i2 = i8;
            mutableInteractionSource = interactionSource;
        } else if (($changed1 & 6) == 0) {
            i2 = i8;
            mutableInteractionSource = interactionSource;
            $dirty13 |= $composer2.changed(mutableInteractionSource) ? 4 : 2;
        } else {
            i2 = i8;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && ($dirty13 & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            leadingIcon3 = leadingIcon;
            trailingIcon3 = trailingIcon;
            shape4 = shape;
            elevation3 = elevation;
            border4 = border;
            $dirty12 = $dirty13;
            colors4 = colors2;
            modifier4 = modifier2;
            enabled3 = enabled2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i3 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i4 != 0 ? true : enabled2;
                Function2 leadingIcon4 = i5 != 0 ? null : leadingIcon;
                Function2 trailingIcon4 = i6 != 0 ? null : trailingIcon;
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                    shape2 = AssistChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape2 = shape;
                }
                if ((i & 128) != 0) {
                    $dirty = $dirty3 & (-29360129);
                    colors3 = AssistChipDefaults.INSTANCE.assistChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 256) != 0) {
                    $dirty1 = $dirty13;
                    modifier3 = modifier5;
                    $dirty &= -234881025;
                    elevation2 = AssistChipDefaults.INSTANCE.m1239assistChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                } else {
                    modifier3 = modifier5;
                    $dirty1 = $dirty13;
                    elevation2 = elevation;
                }
                if ((i & 512) != 0) {
                    border2 = AssistChipDefaults.INSTANCE.m1236assistChipBorderd_3_b6Q(0L, 0L, 0.0f, $composer2, 3072, 7);
                    $dirty &= -1879048193;
                } else {
                    border2 = border;
                }
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(839906043);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    modifier2 = modifier3;
                    border3 = border2;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    trailingIcon2 = trailingIcon4;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    leadingIcon2 = leadingIcon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                } else {
                    modifier2 = modifier3;
                    interactionSource2 = interactionSource;
                    border3 = border2;
                    trailingIcon2 = trailingIcon4;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    leadingIcon2 = leadingIcon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    $dirty3 &= -234881025;
                }
                if ((i & 512) != 0) {
                    int i9 = (-1879048193) & $dirty3;
                    leadingIcon2 = leadingIcon;
                    shape3 = shape;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = i9;
                    $dirty1 = $dirty13;
                    trailingIcon2 = trailingIcon;
                    elevation2 = elevation;
                } else {
                    shape3 = shape;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = $dirty3;
                    $dirty1 = $dirty13;
                    leadingIcon2 = leadingIcon;
                    trailingIcon2 = trailingIcon;
                    elevation2 = elevation;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                $dirty12 = $dirty1;
                ComposerKt.traceEventStart(-1932300596, $dirty2, $dirty12, "androidx.compose.material3.AssistChip (Chip.kt:204)");
            } else {
                $dirty12 = $dirty1;
            }
            TextStyle textStyleFromToken = TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), AssistChipTokens.INSTANCE.getLabelTextFont());
            long jM1330labelColorvNxB06k$material3_release = colors2.m1330labelColorvNxB06k$material3_release(enabled2);
            $composer2.startReplaceableGroup(839906470);
            ComposerKt.sourceInformation($composer2, "216@10782L21");
            State<BorderStroke> stateBorderStroke$material3_release = border3 == null ? null : border3.borderStroke$material3_release(enabled2, $composer2, (($dirty2 >> 9) & 14) | (($dirty2 >> 24) & 112));
            $composer2.endReplaceableGroup();
            m1340ChipnkUnTEs(modifier2, onClick, enabled2, label, textStyleFromToken, jM1330labelColorvNxB06k$material3_release, leadingIcon2, trailingIcon2, shape3, colors2, elevation2, stateBorderStroke$material3_release != null ? stateBorderStroke$material3_release.getValue() : null, AssistChipDefaults.INSTANCE.m1242getHeightD9Ej5fM(), AssistChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 6) & 29360128) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 3456 | (($dirty12 << 12) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            leadingIcon3 = leadingIcon2;
            trailingIcon3 = trailingIcon2;
            shape4 = shape3;
            border4 = border3;
            colors4 = colors2;
            modifier4 = modifier2;
            interactionSource3 = interactionSource2;
            enabled3 = enabled2;
            elevation3 = elevation2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2 function2 = leadingIcon3;
            final Function2 function22 = trailingIcon3;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation3;
            final ChipBorder chipBorder = border4;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.AssistChip.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i10) {
                    ChipKt.AssistChip(onClick, label, modifier6, z, function2, function22, shape5, chipColors, chipElevation, chipBorder, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final void ElevatedAssistChip(final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Shape shape, ChipColors colors, ChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean enabled2;
        ChipColors colors2;
        int i2;
        int i3;
        MutableInteractionSource mutableInteractionSource;
        Shape shape2;
        int $dirty;
        ChipColors colors3;
        Modifier modifier3;
        int $dirty1;
        ChipElevation elevation2;
        MutableInteractionSource interactionSource2;
        ChipElevation elevation3;
        BorderStroke border2;
        Function2<? super Composer, ? super Integer, Unit> function24;
        Shape shape3;
        Function2<? super Composer, ? super Integer, Unit> function25;
        int $dirty2;
        Object value$iv;
        int $dirty12;
        Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Shape shape4;
        ChipElevation elevation4;
        ChipColors colors4;
        Modifier modifier4;
        BorderStroke border3;
        boolean enabled3;
        MutableInteractionSource interactionSource3;
        Composer $composer2 = $composer.startRestartGroup(1594789934);
        ComposerKt.sourceInformation($composer2, "C(ElevatedAssistChip)P(8,5,7,3,6,10,9,1,2)270@13792L5,271@13843L26,272@13922L29,274@14037L39,280@14212L10,275@14081L507:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 32 : 16;
        }
        int i4 = i & 4;
        if (i4 != 0) {
            $dirty3 |= 384;
            modifier2 = modifier;
        } else if (($changed & 384) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        int i5 = i & 8;
        if (i5 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i6 = i & 16;
        if (i6 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changedInstance(function22) ? 16384 : 8192;
        }
        int i7 = i & 32;
        if (i7 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty3 |= $composer2.changedInstance(function23) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty3 |= ((i & 64) == 0 && $composer2.changed(shape)) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                colors2 = colors;
                int i8 = $composer2.changed(colors2) ? 8388608 : 4194304;
                $dirty3 |= i8;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i8;
        } else {
            colors2 = colors;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(elevation)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i9 = i & 512;
        if (i9 != 0) {
            $dirty3 |= 805306368;
            i2 = i9;
        } else if (($changed & 805306368) == 0) {
            i2 = i9;
            $dirty3 |= $composer2.changed(border) ? 536870912 : 268435456;
        } else {
            i2 = i9;
        }
        int i10 = i & 1024;
        if (i10 != 0) {
            $dirty13 |= 6;
            i3 = i10;
            mutableInteractionSource = interactionSource;
        } else if (($changed1 & 6) == 0) {
            i3 = i10;
            mutableInteractionSource = interactionSource;
            $dirty13 |= $composer2.changed(mutableInteractionSource) ? 4 : 2;
        } else {
            i3 = i10;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && ($dirty13 & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            function26 = function22;
            function27 = function23;
            shape4 = shape;
            elevation4 = elevation;
            border3 = border;
            $dirty12 = $dirty13;
            colors4 = colors2;
            modifier4 = modifier2;
            enabled3 = enabled2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i4 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i5 != 0 ? true : enabled2;
                Function2<? super Composer, ? super Integer, Unit> function28 = i6 != 0 ? null : function22;
                Function2<? super Composer, ? super Integer, Unit> function29 = i7 != 0 ? null : function23;
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                    shape2 = AssistChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape2 = shape;
                }
                if ((i & 128) != 0) {
                    $dirty = $dirty3 & (-29360129);
                    colors3 = AssistChipDefaults.INSTANCE.elevatedAssistChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 256) != 0) {
                    $dirty1 = $dirty13;
                    modifier3 = modifier5;
                    elevation2 = AssistChipDefaults.INSTANCE.m1241elevatedAssistChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty &= -234881025;
                } else {
                    modifier3 = modifier5;
                    $dirty1 = $dirty13;
                    elevation2 = elevation;
                }
                BorderStroke border4 = i2 != 0 ? null : border;
                if (i3 != 0) {
                    $composer2.startReplaceableGroup(-227035565);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    modifier2 = modifier3;
                    elevation3 = elevation2;
                    border2 = border4;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    function24 = function29;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    function25 = function28;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                } else {
                    modifier2 = modifier3;
                    interactionSource2 = interactionSource;
                    elevation3 = elevation2;
                    border2 = border4;
                    function24 = function29;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    function25 = function28;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    int i11 = $dirty3 & (-234881025);
                    function25 = function22;
                    shape3 = shape;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    function24 = function23;
                    $dirty2 = i11;
                } else {
                    shape3 = shape;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    function24 = function23;
                    $dirty2 = $dirty3;
                    function25 = function22;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                $dirty12 = $dirty1;
                ComposerKt.traceEventStart(1594789934, $dirty2, $dirty12, "androidx.compose.material3.ElevatedAssistChip (Chip.kt:275)");
            } else {
                $dirty12 = $dirty1;
            }
            m1340ChipnkUnTEs(modifier2, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), AssistChipTokens.INSTANCE.getLabelTextFont()), colors2.m1330labelColorvNxB06k$material3_release(enabled2), function25, function24, shape3, colors2, elevation3, border2, AssistChipDefaults.INSTANCE.m1242getHeightD9Ej5fM(), AssistChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 6) & 29360128) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 3456 | (($dirty2 >> 24) & 112) | (($dirty12 << 12) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function26 = function25;
            function27 = function24;
            shape4 = shape3;
            elevation4 = elevation3;
            colors4 = colors2;
            modifier4 = modifier2;
            border3 = border2;
            enabled3 = enabled2;
            interactionSource3 = interactionSource2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function210 = function26;
            final Function2<? super Composer, ? super Integer, Unit> function211 = function27;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final BorderStroke borderStroke = border3;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.ElevatedAssistChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i12) {
                    ChipKt.ElevatedAssistChip(function0, function2, modifier6, z, function210, function211, shape5, chipColors, chipElevation, borderStroke, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Maintained for binary compatibility. Use version with ElevatedAssistChip that take a BorderStroke instead", replaceWith = @ReplaceWith(expression = "ElevatedAssistChip(onClick, label, modifier, enabled,leadingIcon, trailingIcon, shape, colors, elevation, border, interactionSource", imports = {}))
    public static final /* synthetic */ void ElevatedAssistChip(final Function0 onClick, final Function2 label, Modifier modifier, boolean enabled, Function2 leadingIcon, Function2 trailingIcon, Shape shape, ChipColors colors, ChipElevation elevation, ChipBorder border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean enabled2;
        ChipColors colors2;
        int i2;
        int i3;
        MutableInteractionSource mutableInteractionSource;
        Shape shape2;
        int $dirty;
        ChipColors colors3;
        Modifier modifier3;
        int $dirty1;
        ChipElevation elevation2;
        MutableInteractionSource interactionSource2;
        ChipElevation elevation3;
        ChipBorder border2;
        Function2 trailingIcon2;
        Shape shape3;
        Function2 leadingIcon2;
        int $dirty2;
        Object value$iv;
        int $dirty12;
        Function2 leadingIcon3;
        Function2 trailingIcon3;
        Shape shape4;
        ChipElevation elevation4;
        ChipColors colors4;
        Modifier modifier4;
        ChipBorder border3;
        boolean enabled3;
        MutableInteractionSource interactionSource3;
        Composer $composer2 = $composer.startRestartGroup(1295844802);
        ComposerKt.sourceInformation($composer2, "C(ElevatedAssistChip)P(8,5,7,3,6,10,9,1,2)349@17824L5,350@17875L26,351@17954L29,353@18067L39,359@18242L10,354@18111L537:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(onClick) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(label) ? 32 : 16;
        }
        int i4 = i & 4;
        if (i4 != 0) {
            $dirty3 |= 384;
            modifier2 = modifier;
        } else if (($changed & 384) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 256 : 128;
        } else {
            modifier2 = modifier;
        }
        int i5 = i & 8;
        if (i5 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i6 = i & 16;
        if (i6 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changedInstance(leadingIcon) ? 16384 : 8192;
        }
        int i7 = i & 32;
        if (i7 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty3 |= $composer2.changedInstance(trailingIcon) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty3 |= ((i & 64) == 0 && $composer2.changed(shape)) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                colors2 = colors;
                int i8 = $composer2.changed(colors2) ? 8388608 : 4194304;
                $dirty3 |= i8;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i8;
        } else {
            colors2 = colors;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(elevation)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i9 = i & 512;
        if (i9 != 0) {
            $dirty3 |= 805306368;
            i2 = i9;
        } else if (($changed & 805306368) == 0) {
            i2 = i9;
            $dirty3 |= $composer2.changed(border) ? 536870912 : 268435456;
        } else {
            i2 = i9;
        }
        int i10 = i & 1024;
        if (i10 != 0) {
            $dirty13 |= 6;
            i3 = i10;
            mutableInteractionSource = interactionSource;
        } else if (($changed1 & 6) == 0) {
            i3 = i10;
            mutableInteractionSource = interactionSource;
            $dirty13 |= $composer2.changed(mutableInteractionSource) ? 4 : 2;
        } else {
            i3 = i10;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && ($dirty13 & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            leadingIcon3 = leadingIcon;
            trailingIcon3 = trailingIcon;
            shape4 = shape;
            elevation4 = elevation;
            border3 = border;
            $dirty12 = $dirty13;
            colors4 = colors2;
            modifier4 = modifier2;
            enabled3 = enabled2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i4 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i5 != 0 ? true : enabled2;
                Function2 leadingIcon4 = i6 != 0 ? null : leadingIcon;
                Function2 trailingIcon4 = i7 != 0 ? null : trailingIcon;
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                    shape2 = AssistChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape2 = shape;
                }
                if ((i & 128) != 0) {
                    $dirty = $dirty3 & (-29360129);
                    colors3 = AssistChipDefaults.INSTANCE.elevatedAssistChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 256) != 0) {
                    $dirty1 = $dirty13;
                    modifier3 = modifier5;
                    elevation2 = AssistChipDefaults.INSTANCE.m1241elevatedAssistChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty &= -234881025;
                } else {
                    modifier3 = modifier5;
                    $dirty1 = $dirty13;
                    elevation2 = elevation;
                }
                ChipBorder border4 = i2 != 0 ? null : border;
                if (i3 != 0) {
                    $composer2.startReplaceableGroup(-227031535);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    modifier2 = modifier3;
                    elevation3 = elevation2;
                    border2 = border4;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    trailingIcon2 = trailingIcon4;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    leadingIcon2 = leadingIcon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                } else {
                    modifier2 = modifier3;
                    interactionSource2 = interactionSource;
                    elevation3 = elevation2;
                    border2 = border4;
                    trailingIcon2 = trailingIcon4;
                    shape3 = shape2;
                    enabled2 = enabled4;
                    leadingIcon2 = leadingIcon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    int i11 = $dirty3 & (-234881025);
                    leadingIcon2 = leadingIcon;
                    shape3 = shape;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    trailingIcon2 = trailingIcon;
                    $dirty2 = i11;
                } else {
                    shape3 = shape;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    trailingIcon2 = trailingIcon;
                    $dirty2 = $dirty3;
                    leadingIcon2 = leadingIcon;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                $dirty12 = $dirty1;
                ComposerKt.traceEventStart(1295844802, $dirty2, $dirty12, "androidx.compose.material3.ElevatedAssistChip (Chip.kt:354)");
            } else {
                $dirty12 = $dirty1;
            }
            TextStyle textStyleFromToken = TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), AssistChipTokens.INSTANCE.getLabelTextFont());
            long jM1330labelColorvNxB06k$material3_release = colors2.m1330labelColorvNxB06k$material3_release(enabled2);
            float fM1242getHeightD9Ej5fM = AssistChipDefaults.INSTANCE.m1242getHeightD9Ej5fM();
            PaddingValues paddingValues = AssistChipPadding;
            $composer2.startReplaceableGroup(-227031027);
            ComposerKt.sourceInformation($composer2, "368@18575L21");
            State<BorderStroke> stateBorderStroke$material3_release = border2 == null ? null : border2.borderStroke$material3_release(enabled2, $composer2, (($dirty2 >> 9) & 14) | (($dirty2 >> 24) & 112));
            $composer2.endReplaceableGroup();
            m1340ChipnkUnTEs(modifier2, onClick, enabled2, label, textStyleFromToken, jM1330labelColorvNxB06k$material3_release, leadingIcon2, trailingIcon2, shape3, colors2, elevation3, stateBorderStroke$material3_release != null ? stateBorderStroke$material3_release.getValue() : null, fM1242getHeightD9Ej5fM, paddingValues, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 6) & 29360128) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 3456 | (($dirty12 << 12) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            leadingIcon3 = leadingIcon2;
            trailingIcon3 = trailingIcon2;
            shape4 = shape3;
            elevation4 = elevation3;
            colors4 = colors2;
            modifier4 = modifier2;
            border3 = border2;
            enabled3 = enabled2;
            interactionSource3 = interactionSource2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2 function2 = leadingIcon3;
            final Function2 function22 = trailingIcon3;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final ChipBorder chipBorder = border3;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.ElevatedAssistChip.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i12) {
                    ChipKt.ElevatedAssistChip(onClick, label, modifier6, z, function2, function22, shape5, chipColors, chipElevation, chipBorder, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final void FilterChip(final boolean selected, final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Shape shape, SelectableChipColors colors, SelectableChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean z;
        Function2<? super Composer, ? super Integer, Unit> function24;
        Function2<? super Composer, ? super Integer, Unit> function25;
        Shape shape2;
        int i2;
        MutableInteractionSource interactionSource2;
        int $dirty1;
        Modifier.Companion modifier3;
        boolean enabled2;
        Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Shape shape3;
        SelectableChipColors colors2;
        int $dirty;
        int i3;
        SelectableChipElevation elevation2;
        int $dirty2;
        BorderStroke border2;
        int $dirty12;
        Object value$iv;
        Modifier modifier4;
        SelectableChipElevation elevation3;
        BorderStroke border3;
        MutableInteractionSource interactionSource3;
        Composer $composer2 = $composer.startRestartGroup(-1711985619);
        ComposerKt.sourceInformation($composer2, "C(FilterChip)P(9,8,5,7,3,6,11,10,1,2)430@21945L5,431@22006L18,432@22087L21,433@22157L35,434@22244L39,441@22454L10,435@22288L516:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changed(selected) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
            modifier2 = modifier;
        } else if (($changed & 3072) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 2048 : 1024;
        } else {
            modifier2 = modifier;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
            z = enabled;
        } else if (($changed & 24576) == 0) {
            z = enabled;
            $dirty3 |= $composer2.changed(z) ? 16384 : 8192;
        } else {
            z = enabled;
        }
        int i6 = i & 32;
        if (i6 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
            function24 = function22;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            function24 = function22;
            $dirty3 |= $composer2.changedInstance(function24) ? 131072 : 65536;
        } else {
            function24 = function22;
        }
        int i7 = i & 64;
        if (i7 != 0) {
            $dirty3 |= 1572864;
            function25 = function23;
        } else if (($changed & 1572864) == 0) {
            function25 = function23;
            $dirty3 |= $composer2.changedInstance(function25) ? 1048576 : 524288;
        } else {
            function25 = function23;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                shape2 = shape;
                int i8 = $composer2.changed(shape2) ? 8388608 : 4194304;
                $dirty3 |= i8;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i8;
        } else {
            shape2 = shape;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(colors)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty3 |= ((i & 512) == 0 && $composer2.changed(elevation)) ? 536870912 : 268435456;
        }
        if (($changed1 & 6) == 0) {
            $dirty13 |= ((i & 1024) == 0 && $composer2.changed(border)) ? 4 : 2;
        }
        int i9 = i & 2048;
        if (i9 != 0) {
            $dirty1 = $dirty13 | 48;
            i2 = i9;
            interactionSource2 = interactionSource;
        } else {
            if (($changed1 & 48) == 0) {
                i2 = i9;
                interactionSource2 = interactionSource;
                $dirty13 |= $composer2.changed(interactionSource2) ? 32 : 16;
            } else {
                i2 = i9;
                interactionSource2 = interactionSource;
            }
            $dirty1 = $dirty13;
        }
        if ((306783379 & $dirty3) == 306783378 && ($dirty1 & 19) == 18 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            colors2 = colors;
            elevation3 = elevation;
            border3 = border;
            enabled2 = z;
            function26 = function24;
            function27 = function25;
            shape3 = shape2;
            modifier4 = modifier2;
            interactionSource3 = interactionSource2;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                modifier3 = i4 != 0 ? Modifier.INSTANCE : modifier2;
                enabled2 = i5 != 0 ? true : z;
                function26 = i6 != 0 ? null : function24;
                function27 = i7 != 0 ? null : function25;
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                    shape3 = FilterChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 256) != 0) {
                    $dirty = $dirty3 & (-234881025);
                    colors2 = FilterChipDefaults.INSTANCE.filterChipColors($composer2, 6);
                } else {
                    colors2 = colors;
                    $dirty = $dirty3;
                }
                if ((i & 512) != 0) {
                    i3 = i2;
                    elevation2 = FilterChipDefaults.INSTANCE.m1563filterChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty2 = $dirty & (-1879048193);
                } else {
                    i3 = i2;
                    elevation2 = elevation;
                    $dirty2 = $dirty;
                }
                if ((i & 1024) != 0) {
                    border2 = FilterChipDefaults.INSTANCE.m1561filterChipBorder_7El2pE(enabled2, selected, 0L, 0L, 0L, 0L, 0.0f, 0.0f, $composer2, (($dirty2 >> 12) & 14) | 100663296 | (($dirty2 << 3) & 112), 252);
                    $dirty1 &= -15;
                } else {
                    border2 = border;
                }
                if (i3 != 0) {
                    $composer2.startReplaceableGroup(276179389);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    $dirty12 = $dirty1;
                } else {
                    interactionSource2 = interactionSource;
                    $dirty12 = $dirty1;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    $dirty3 &= -234881025;
                }
                if ((i & 512) != 0) {
                    $dirty3 &= -1879048193;
                }
                if ((i & 1024) != 0) {
                    colors2 = colors;
                    border2 = border;
                    $dirty2 = $dirty3;
                    enabled2 = z;
                    function26 = function24;
                    function27 = function25;
                    shape3 = shape2;
                    elevation2 = elevation;
                    $dirty12 = $dirty1 & (-15);
                    modifier3 = modifier2;
                } else {
                    colors2 = colors;
                    border2 = border;
                    $dirty2 = $dirty3;
                    enabled2 = z;
                    function26 = function24;
                    function27 = function25;
                    shape3 = shape2;
                    modifier3 = modifier2;
                    $dirty12 = $dirty1;
                    elevation2 = elevation;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1711985619, $dirty2, $dirty12, "androidx.compose.material3.FilterChip (Chip.kt:435)");
            }
            m1342SelectableChipu0RnIRE(selected, modifier3, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), FilterChipTokens.INSTANCE.getLabelTextFont()), function26, null, function27, shape3, colors2, elevation2, border2, FilterChipDefaults.INSTANCE.m1564getHeightD9Ej5fM(), FilterChipPadding, interactionSource2, $composer2, ($dirty2 & 14) | 12582912 | (($dirty2 >> 6) & 112) | (($dirty2 << 3) & 896) | (($dirty2 >> 3) & 7168) | (($dirty2 << 6) & 57344) | (($dirty2 << 3) & 3670016) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 27648 | (($dirty2 >> 24) & 112) | (($dirty12 << 6) & 896) | (($dirty12 << 12) & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier4 = modifier3;
            elevation3 = elevation2;
            border3 = border2;
            interactionSource3 = interactionSource2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier5 = modifier4;
            final boolean z2 = enabled2;
            final Function2<? super Composer, ? super Integer, Unit> function28 = function26;
            final Function2<? super Composer, ? super Integer, Unit> function29 = function27;
            final Shape shape4 = shape3;
            final SelectableChipColors selectableChipColors = colors2;
            final SelectableChipElevation selectableChipElevation = elevation3;
            final BorderStroke borderStroke = border3;
            final MutableInteractionSource mutableInteractionSource = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.FilterChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i10) {
                    ChipKt.FilterChip(selected, function0, function2, modifier5, z2, function28, function29, shape4, selectableChipColors, selectableChipElevation, borderStroke, mutableInteractionSource, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final void ElevatedFilterChip(final boolean selected, final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Shape shape, SelectableChipColors colors, SelectableChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        Function2<? super Composer, ? super Integer, Unit> function24;
        Function2<? super Composer, ? super Integer, Unit> function25;
        Shape shape2;
        int i2;
        int i3;
        MutableInteractionSource mutableInteractionSource;
        Shape shape3;
        SelectableChipColors colors2;
        int $dirty;
        Modifier modifier3;
        int $dirty1;
        int i4;
        int i5;
        SelectableChipElevation elevation2;
        MutableInteractionSource interactionSource2;
        SelectableChipElevation elevation3;
        BorderStroke border2;
        boolean enabled2;
        SelectableChipColors colors3;
        int $dirty2;
        Object value$iv;
        int $dirty12;
        boolean enabled3;
        SelectableChipColors colors4;
        SelectableChipElevation elevation4;
        BorderStroke border3;
        Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Shape shape4;
        Modifier modifier4;
        MutableInteractionSource interactionSource3;
        Composer $composer2 = $composer.startRestartGroup(-106647389);
        ComposerKt.sourceInformation($composer2, "C(ElevatedFilterChip)P(9,8,5,7,3,6,11,10,1,2)509@25986L5,510@26047L26,511@26136L29,513@26251L39,520@26461L10,514@26295L516:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changed(selected) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 256 : 128;
        }
        int i6 = i & 8;
        if (i6 != 0) {
            $dirty3 |= 3072;
            modifier2 = modifier;
        } else if (($changed & 3072) == 0) {
            modifier2 = modifier;
            $dirty3 |= $composer2.changed(modifier2) ? 2048 : 1024;
        } else {
            modifier2 = modifier;
        }
        int i7 = i & 16;
        if (i7 != 0) {
            $dirty3 |= 24576;
        } else if (($changed & 24576) == 0) {
            $dirty3 |= $composer2.changed(enabled) ? 16384 : 8192;
        }
        int i8 = i & 32;
        if (i8 != 0) {
            $dirty3 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
            function24 = function22;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            function24 = function22;
            $dirty3 |= $composer2.changedInstance(function24) ? 131072 : 65536;
        } else {
            function24 = function22;
        }
        int i9 = i & 64;
        if (i9 != 0) {
            $dirty3 |= 1572864;
            function25 = function23;
        } else if (($changed & 1572864) == 0) {
            function25 = function23;
            $dirty3 |= $composer2.changedInstance(function25) ? 1048576 : 524288;
        } else {
            function25 = function23;
        }
        if (($changed & 12582912) == 0) {
            if ((i & 128) == 0) {
                shape2 = shape;
                int i10 = $composer2.changed(shape2) ? 8388608 : 4194304;
                $dirty3 |= i10;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i10;
        } else {
            shape2 = shape;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(colors)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty3 |= ((i & 512) == 0 && $composer2.changed(elevation)) ? 536870912 : 268435456;
        }
        int i11 = i & 1024;
        if (i11 != 0) {
            $dirty13 |= 6;
            i2 = i11;
        } else if (($changed1 & 6) == 0) {
            i2 = i11;
            $dirty13 |= $composer2.changed(border) ? 4 : 2;
        } else {
            i2 = i11;
        }
        int i12 = i & 2048;
        if (i12 != 0) {
            $dirty13 |= 48;
            i3 = i12;
            mutableInteractionSource = interactionSource;
        } else if (($changed1 & 48) == 0) {
            i3 = i12;
            mutableInteractionSource = interactionSource;
            $dirty13 |= $composer2.changed(mutableInteractionSource) ? 32 : 16;
        } else {
            i3 = i12;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && ($dirty13 & 19) == 18 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            enabled3 = enabled;
            colors4 = colors;
            elevation4 = elevation;
            border3 = border;
            $dirty12 = $dirty13;
            function26 = function24;
            function27 = function25;
            shape4 = shape2;
            modifier4 = modifier2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i6 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i7 != 0 ? true : enabled;
                Function2<? super Composer, ? super Integer, Unit> function28 = i8 != 0 ? null : function24;
                Function2<? super Composer, ? super Integer, Unit> function29 = i9 != 0 ? null : function25;
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                    shape3 = FilterChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 256) != 0) {
                    $dirty = $dirty3 & (-234881025);
                    colors2 = FilterChipDefaults.INSTANCE.elevatedFilterChipColors($composer2, 6);
                } else {
                    colors2 = colors;
                    $dirty = $dirty3;
                }
                if ((i & 512) != 0) {
                    $dirty1 = $dirty13;
                    modifier3 = modifier5;
                    int i13 = i2;
                    i4 = i3;
                    i5 = i13;
                    elevation2 = FilterChipDefaults.INSTANCE.m1560elevatedFilterChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty &= -1879048193;
                } else {
                    modifier3 = modifier5;
                    $dirty1 = $dirty13;
                    int i14 = i2;
                    i4 = i3;
                    i5 = i14;
                    elevation2 = elevation;
                }
                BorderStroke border4 = i5 != 0 ? null : border;
                if (i4 != 0) {
                    $composer2.startReplaceableGroup(-790761894);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    MutableInteractionSource interactionSource4 = (MutableInteractionSource) value$iv;
                    $composer2.endReplaceableGroup();
                    modifier2 = modifier3;
                    border2 = border4;
                    interactionSource2 = interactionSource4;
                    function24 = function28;
                    function25 = function29;
                    shape2 = shape3;
                    colors3 = colors2;
                    $dirty2 = $dirty;
                    elevation3 = elevation2;
                    enabled2 = enabled4;
                } else {
                    modifier2 = modifier3;
                    interactionSource2 = interactionSource;
                    elevation3 = elevation2;
                    border2 = border4;
                    function24 = function28;
                    function25 = function29;
                    shape2 = shape3;
                    enabled2 = enabled4;
                    colors3 = colors2;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    $dirty3 &= -234881025;
                }
                if ((i & 512) != 0) {
                    int i15 = (-1879048193) & $dirty3;
                    enabled2 = enabled;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    colors3 = colors;
                    $dirty2 = i15;
                } else {
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    colors3 = colors;
                    $dirty2 = $dirty3;
                    enabled2 = enabled;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                $dirty12 = $dirty1;
                ComposerKt.traceEventStart(-106647389, $dirty2, $dirty12, "androidx.compose.material3.ElevatedFilterChip (Chip.kt:514)");
            } else {
                $dirty12 = $dirty1;
            }
            m1342SelectableChipu0RnIRE(selected, modifier2, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), FilterChipTokens.INSTANCE.getLabelTextFont()), function24, null, function25, shape2, colors3, elevation3, border2, FilterChipDefaults.INSTANCE.m1564getHeightD9Ej5fM(), FilterChipPadding, interactionSource2, $composer2, ($dirty2 & 14) | 12582912 | (($dirty2 >> 6) & 112) | (($dirty2 << 3) & 896) | (($dirty2 >> 3) & 7168) | (($dirty2 << 6) & 57344) | (($dirty2 << 3) & 3670016) | (($dirty2 << 6) & 234881024) | (($dirty2 << 6) & 1879048192), (($dirty2 >> 24) & 14) | 27648 | (($dirty2 >> 24) & 112) | (($dirty12 << 6) & 896) | (($dirty12 << 12) & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            enabled3 = enabled2;
            colors4 = colors3;
            elevation4 = elevation3;
            border3 = border2;
            function26 = function24;
            function27 = function25;
            shape4 = shape2;
            modifier4 = modifier2;
            interactionSource3 = interactionSource2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function210 = function26;
            final Function2<? super Composer, ? super Integer, Unit> function211 = function27;
            final Shape shape5 = shape4;
            final SelectableChipColors selectableChipColors = colors4;
            final SelectableChipElevation selectableChipElevation = elevation4;
            final BorderStroke borderStroke = border3;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.ElevatedFilterChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i16) {
                    ChipKt.ElevatedFilterChip(selected, function0, function2, modifier6, z, function210, function211, shape5, selectableChipColors, selectableChipElevation, borderStroke, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final void InputChip(final boolean selected, final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Function2<? super Composer, ? super Integer, Unit> function23, Function2<? super Composer, ? super Integer, Unit> function24, Shape shape, SelectableChipColors colors, SelectableChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1, final int i) {
        Modifier modifier2;
        boolean enabled2;
        Function2<? super Composer, ? super Integer, Unit> function25;
        final Function2<? super Composer, ? super Integer, Unit> function26;
        Function2<? super Composer, ? super Integer, Unit> function27;
        Modifier.Companion modifier3;
        Function2<? super Composer, ? super Integer, Unit> function28;
        Shape shape2;
        SelectableChipColors colors2;
        int $dirty;
        int i2;
        SelectableChipElevation elevation2;
        BorderStroke border2;
        MutableInteractionSource interactionSource2;
        int $dirty1;
        int $dirty12;
        Object value$iv;
        Function2<? super Composer, ? super Integer, Unit> function29;
        boolean z;
        Function2<? super Composer, ? super Integer, Unit> function210;
        Modifier modifier4;
        SelectableChipElevation elevation3;
        BorderStroke border3;
        MutableInteractionSource interactionSource3;
        boolean enabled3;
        Function2<? super Composer, ? super Integer, Unit> function211;
        Composer $composer2 = $composer.startRestartGroup(1658928131);
        ComposerKt.sourceInformation($composer2, "C(InputChip)P(10,9,6,8,4,7!1,12,11,2,3)593@30171L5,594@30231L17,595@30310L20,596@30378L34,597@30464L39,624@31431L10,618@31241L747:Chip.kt#uh7d8r");
        int $dirty2 = $changed;
        int $dirty13 = $changed1;
        if ((i & 1) != 0) {
            $dirty2 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty2 |= $composer2.changed(selected) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty2 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty2 |= $composer2.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty2 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty2 |= $composer2.changedInstance(function2) ? 256 : 128;
        }
        int i3 = i & 8;
        if (i3 != 0) {
            $dirty2 |= 3072;
            modifier2 = modifier;
        } else if (($changed & 3072) == 0) {
            modifier2 = modifier;
            $dirty2 |= $composer2.changed(modifier2) ? 2048 : 1024;
        } else {
            modifier2 = modifier;
        }
        int i4 = i & 16;
        if (i4 != 0) {
            $dirty2 |= 24576;
            enabled2 = enabled;
        } else if (($changed & 24576) == 0) {
            enabled2 = enabled;
            $dirty2 |= $composer2.changed(enabled2) ? 16384 : 8192;
        } else {
            enabled2 = enabled;
        }
        int i5 = i & 32;
        if (i5 != 0) {
            $dirty2 |= ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE;
            function25 = function22;
        } else if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            function25 = function22;
            $dirty2 |= $composer2.changedInstance(function25) ? 131072 : 65536;
        } else {
            function25 = function22;
        }
        int i6 = i & 64;
        if (i6 != 0) {
            $dirty2 |= 1572864;
            function26 = function23;
        } else if (($changed & 1572864) == 0) {
            function26 = function23;
            $dirty2 |= $composer2.changedInstance(function26) ? 1048576 : 524288;
        } else {
            function26 = function23;
        }
        int i7 = i & 128;
        if (i7 != 0) {
            $dirty2 |= 12582912;
            function27 = function24;
        } else if (($changed & 12582912) == 0) {
            function27 = function24;
            $dirty2 |= $composer2.changedInstance(function27) ? 8388608 : 4194304;
        } else {
            function27 = function24;
        }
        if (($changed & 100663296) == 0) {
            $dirty2 |= ((i & 256) == 0 && $composer2.changed(shape)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty2 |= ((i & 512) == 0 && $composer2.changed(colors)) ? 536870912 : 268435456;
        }
        if (($changed1 & 6) == 0) {
            $dirty13 |= ((i & 1024) == 0 && $composer2.changed(elevation)) ? 4 : 2;
        }
        if (($changed1 & 48) == 0) {
            $dirty13 |= ((i & 2048) == 0 && $composer2.changed(border)) ? 32 : 16;
        }
        int i8 = i & 4096;
        if (i8 != 0) {
            $dirty13 |= 384;
        } else if (($changed1 & 384) == 0) {
            $dirty13 |= $composer2.changed(interactionSource) ? 256 : 128;
        }
        if (($dirty2 & 306783379) == 306783378 && ($dirty13 & 147) == 146 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            shape2 = shape;
            colors2 = colors;
            elevation3 = elevation;
            border3 = border;
            interactionSource3 = interactionSource;
            modifier4 = modifier2;
            enabled3 = enabled2;
            function211 = function25;
            function210 = function26;
            function28 = function27;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                modifier3 = i3 != 0 ? Modifier.INSTANCE : modifier2;
                boolean enabled4 = i4 != 0 ? true : enabled2;
                Function2<? super Composer, ? super Integer, Unit> function212 = i5 != 0 ? null : function25;
                Function2<? super Composer, ? super Integer, Unit> function213 = i6 != 0 ? null : function26;
                function28 = i7 != 0 ? null : function27;
                if ((i & 256) != 0) {
                    $dirty2 &= -234881025;
                    shape2 = InputChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape2 = shape;
                }
                if ((i & 512) != 0) {
                    $dirty = $dirty2 & (-1879048193);
                    colors2 = InputChipDefaults.INSTANCE.inputChipColors($composer2, 6);
                } else {
                    colors2 = colors;
                    $dirty = $dirty2;
                }
                if ((i & 1024) != 0) {
                    i2 = i8;
                    elevation2 = InputChipDefaults.INSTANCE.m1610inputChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty13 &= -15;
                } else {
                    i2 = i8;
                    elevation2 = elevation;
                }
                if ((i & 2048) != 0) {
                    border2 = InputChipDefaults.INSTANCE.m1608inputChipBorder_7El2pE(enabled4, selected, 0L, 0L, 0L, 0L, 0.0f, 0.0f, $composer2, (($dirty >> 12) & 14) | 100663296 | (($dirty << 3) & 112), 252);
                    $dirty13 &= -113;
                } else {
                    border2 = border;
                }
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(-1371993939);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    $composer2.endReplaceableGroup();
                    $dirty1 = $dirty13;
                    enabled2 = enabled4;
                    function25 = function212;
                    function26 = function213;
                    $dirty12 = $dirty;
                } else {
                    interactionSource2 = interactionSource;
                    $dirty1 = $dirty13;
                    enabled2 = enabled4;
                    function25 = function212;
                    function26 = function213;
                    $dirty12 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 256) != 0) {
                    $dirty2 &= -234881025;
                }
                if ((i & 512) != 0) {
                    $dirty2 &= -1879048193;
                }
                if ((i & 1024) != 0) {
                    $dirty13 &= -15;
                }
                if ((i & 2048) != 0) {
                    shape2 = shape;
                    colors2 = colors;
                    border2 = border;
                    $dirty1 = $dirty13 & (-113);
                    $dirty12 = $dirty2;
                    modifier3 = modifier2;
                    function28 = function27;
                    elevation2 = elevation;
                    interactionSource2 = interactionSource;
                } else {
                    shape2 = shape;
                    colors2 = colors;
                    border2 = border;
                    $dirty1 = $dirty13;
                    modifier3 = modifier2;
                    function28 = function27;
                    interactionSource2 = interactionSource;
                    $dirty12 = $dirty2;
                    elevation2 = elevation;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1658928131, $dirty12, $dirty1, "androidx.compose.material3.InputChip (Chip.kt:598)");
            }
            Function2 shapedAvatar = null;
            $composer2.startReplaceableGroup(-1371993712);
            ComposerKt.sourceInformation($composer2, "604@30854L5");
            if (function26 != null) {
                final float avatarOpacity = enabled2 ? 1.0f : 0.38f;
                final Shape avatarShape = ShapesKt.getValue(InputChipTokens.INSTANCE.getAvatarShape(), $composer2, 6);
                Function2<Composer, Integer, Unit> function214 = new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.InputChip.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                        invoke(composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer $composer3, int $changed2) {
                        Object value$iv2;
                        ComposerKt.sourceInformation($composer3, "C607@30964L148,606@30909L311:Chip.kt#uh7d8r");
                        if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(1154227507, $changed2, -1, "androidx.compose.material3.InputChip.<anonymous> (Chip.kt:606)");
                            }
                            Modifier.Companion companion = Modifier.INSTANCE;
                            $composer3.startReplaceableGroup(946467138);
                            ComposerKt.sourceInformation($composer3, "CC(remember):Chip.kt#9igjgp");
                            boolean invalid$iv = $composer3.changed(avatarOpacity) | $composer3.changed(avatarShape);
                            final float f = avatarOpacity;
                            final Shape shape3 = avatarShape;
                            Object it$iv2 = $composer3.rememberedValue();
                            if (invalid$iv || it$iv2 == Composer.INSTANCE.getEmpty()) {
                                value$iv2 = new Function1<GraphicsLayerScope, Unit>() { // from class: androidx.compose.material3.ChipKt$InputChip$2$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(GraphicsLayerScope graphicsLayerScope) {
                                        invoke2(graphicsLayerScope);
                                        return Unit.INSTANCE;
                                    }

                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(GraphicsLayerScope $this$graphicsLayer) {
                                        $this$graphicsLayer.setAlpha(f);
                                        $this$graphicsLayer.setShape(shape3);
                                        $this$graphicsLayer.setClip(true);
                                    }
                                };
                                $composer3.updateRememberedValue(value$iv2);
                            } else {
                                value$iv2 = it$iv2;
                            }
                            $composer3.endReplaceableGroup();
                            Modifier modifier$iv = GraphicsLayerModifierKt.graphicsLayer(companion, (Function1) value$iv2);
                            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
                            Function2<Composer, Integer, Unit> function215 = function26;
                            $composer3.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                            int $changed$iv$iv = (48 << 3) & 112;
                            $composer3.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                            CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
                            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                            if (!($composer3.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer3.startReusableNode();
                            if ($composer3.getInserting()) {
                                $composer3.createNode(constructor);
                            } else {
                                $composer3.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer3);
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
                            }
                            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i9 = ($changed$iv$iv$iv >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i10 = ((48 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1294405811, "C614@31198L8:Chip.kt#uh7d8r");
                            function215.invoke($composer3, 0);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                };
                function29 = function26;
                z = true;
                shapedAvatar = ComposableLambdaKt.composableLambda($composer2, 1154227507, true, function214);
            } else {
                function29 = function26;
                z = true;
            }
            $composer2.endReplaceableGroup();
            TextStyle textStyleFromToken = TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), InputChipTokens.INSTANCE.getLabelTextFont());
            float fM1606getHeightD9Ej5fM = InputChipDefaults.INSTANCE.m1606getHeightD9Ej5fM();
            boolean z2 = shapedAvatar != null ? z : false;
            boolean z3 = function25 != null ? z : false;
            if (function28 == null) {
                z = false;
            }
            m1342SelectableChipu0RnIRE(selected, modifier3, function0, enabled2, function2, textStyleFromToken, function25, shapedAvatar, function28, shape2, colors2, elevation2, border2, fM1606getHeightD9Ej5fM, inputChipPadding(z2, z3, z), interactionSource2, $composer2, ($dirty12 & 14) | (($dirty12 >> 6) & 112) | (($dirty12 << 3) & 896) | (($dirty12 >> 3) & 7168) | (($dirty12 << 6) & 57344) | (($dirty12 << 3) & 3670016) | (($dirty12 << 3) & 234881024) | (($dirty12 << 3) & 1879048192), (($dirty12 >> 27) & 14) | 3072 | (($dirty1 << 3) & 112) | (($dirty1 << 3) & 896) | (($dirty1 << 9) & 458752));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function210 = function29;
            modifier4 = modifier3;
            elevation3 = elevation2;
            border3 = border2;
            interactionSource3 = interactionSource2;
            enabled3 = enabled2;
            function211 = function25;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier5 = modifier4;
            final boolean z4 = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function215 = function211;
            final Function2<? super Composer, ? super Integer, Unit> function216 = function210;
            final Function2<? super Composer, ? super Integer, Unit> function217 = function28;
            final Shape shape3 = shape2;
            final SelectableChipColors selectableChipColors = colors2;
            final SelectableChipElevation selectableChipElevation = elevation3;
            final BorderStroke borderStroke = border3;
            final MutableInteractionSource mutableInteractionSource = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.InputChip.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i9) {
                    ChipKt.InputChip(selected, function0, function2, modifier5, z4, function215, function216, function217, shape3, selectableChipColors, selectableChipElevation, borderStroke, mutableInteractionSource, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1), i);
                }
            });
        }
    }

    public static final void SuggestionChip(final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Shape shape, ChipColors colors, ChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int i) {
        boolean enabled2;
        Function2<? super Composer, ? super Integer, Unit> function23;
        Shape shape2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape3;
        int $dirty;
        ChipColors colors3;
        Modifier modifier2;
        ChipElevation elevation2;
        BorderStroke border2;
        MutableInteractionSource interactionSource2;
        BorderStroke border3;
        ChipElevation elevation3;
        int $dirty2;
        Modifier modifier3;
        Object value$iv;
        Modifier modifier4;
        ChipElevation elevation4;
        BorderStroke border4;
        MutableInteractionSource interactionSource3;
        boolean enabled3;
        Function2<? super Composer, ? super Integer, Unit> function24;
        Shape shape4;
        ChipColors colors4;
        Composer $composer2 = $composer.startRestartGroup(-1700130831);
        ComposerKt.sourceInformation($composer2, "C(SuggestionChip)P(8,6,7,3,4,9,1,2)688@34668L5,689@34723L22,690@34802L25,691@34880L29,692@34961L39,698@35137L10,693@35006L504:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changed(modifier) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
            function23 = function22;
        } else if (($changed & 24576) == 0) {
            function23 = function22;
            $dirty3 |= $composer2.changedInstance(function23) ? 16384 : 8192;
        } else {
            function23 = function22;
        }
        if ((196608 & $changed) == 0) {
            if ((i & 32) == 0) {
                shape2 = shape;
                int i6 = $composer2.changed(shape2) ? 131072 : 65536;
                $dirty3 |= i6;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i6;
        } else {
            shape2 = shape;
        }
        if ((1572864 & $changed) == 0) {
            if ((i & 64) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 1048576 : 524288;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 12582912) == 0) {
            $dirty3 |= ((i & 128) == 0 && $composer2.changed(elevation)) ? 8388608 : 4194304;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(border)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i8 = i & 512;
        if (i8 != 0) {
            $dirty3 |= 805306368;
            i2 = i8;
            mutableInteractionSource = interactionSource;
        } else if (($changed & 805306368) == 0) {
            i2 = i8;
            mutableInteractionSource = interactionSource;
            $dirty3 |= $composer2.changed(mutableInteractionSource) ? 536870912 : 268435456;
        } else {
            i2 = i8;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier4 = modifier;
            elevation4 = elevation;
            border4 = border;
            enabled3 = enabled2;
            function24 = function23;
            shape4 = shape2;
            colors4 = colors2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i3 != 0 ? Modifier.INSTANCE : modifier;
                boolean enabled4 = i4 != 0 ? true : enabled2;
                Function2<? super Composer, ? super Integer, Unit> function25 = i5 != 0 ? null : function23;
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                    shape3 = SuggestionChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 64) != 0) {
                    $dirty = $dirty3 & (-3670017);
                    colors3 = SuggestionChipDefaults.INSTANCE.suggestionChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 128) != 0) {
                    modifier2 = modifier5;
                    $dirty &= -29360129;
                    elevation2 = SuggestionChipDefaults.INSTANCE.m1975suggestionChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                } else {
                    modifier2 = modifier5;
                    elevation2 = elevation;
                }
                if ((i & 256) != 0) {
                    border2 = SuggestionChipDefaults.INSTANCE.m1973suggestionChipBorderh1eTWw(enabled4, 0L, 0L, 0.0f, $composer2, (($dirty >> 9) & 14) | 24576, 14);
                    $dirty &= -234881025;
                } else {
                    border2 = border;
                }
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(2118462942);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    border3 = border2;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    elevation3 = elevation2;
                    shape2 = shape3;
                    enabled2 = enabled4;
                    function23 = function25;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    modifier3 = modifier2;
                } else {
                    interactionSource2 = interactionSource;
                    border3 = border2;
                    elevation3 = elevation2;
                    shape2 = shape3;
                    enabled2 = enabled4;
                    function23 = function25;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    modifier3 = modifier2;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                }
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    int i9 = $dirty3 & (-234881025);
                    modifier3 = modifier;
                    elevation3 = elevation;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = i9;
                } else {
                    elevation3 = elevation;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = $dirty3;
                    modifier3 = modifier;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1700130831, $dirty2, -1, "androidx.compose.material3.SuggestionChip (Chip.kt:693)");
            }
            m1340ChipnkUnTEs(modifier3, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), SuggestionChipTokens.INSTANCE.getLabelTextFont()), colors2.m1330labelColorvNxB06k$material3_release(enabled2), function23, null, shape2, colors2, elevation3, border3, SuggestionChipDefaults.INSTANCE.m1970getHeightD9Ej5fM(), SuggestionChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | 12582912 | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 9) & 234881024) | (($dirty2 << 9) & 1879048192), (($dirty2 >> 21) & 14) | 3456 | (($dirty2 >> 21) & 112) | (($dirty2 >> 15) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier4 = modifier3;
            elevation4 = elevation3;
            border4 = border3;
            interactionSource3 = interactionSource2;
            enabled3 = enabled2;
            function24 = function23;
            shape4 = shape2;
            colors4 = colors2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function26 = function24;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final BorderStroke borderStroke = border4;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.SuggestionChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i10) {
                    ChipKt.SuggestionChip(function0, function2, modifier6, z, function26, shape5, chipColors, chipElevation, borderStroke, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Maintained for binary compatibility. Use version with SuggestionChip that take a BorderStroke instead", replaceWith = @ReplaceWith(expression = "SuggestionChip(onClick, label, modifier, enabled, icon, shape, colors, elevation, border, interactionSource", imports = {}))
    public static final /* synthetic */ void SuggestionChip(final Function0 onClick, final Function2 label, Modifier modifier, boolean enabled, Function2 icon, Shape shape, ChipColors colors, ChipElevation elevation, ChipBorder border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int i) {
        boolean enabled2;
        Function2 icon2;
        Shape shape2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape3;
        int $dirty;
        ChipColors colors3;
        Modifier modifier2;
        ChipElevation elevation2;
        ChipBorder border2;
        MutableInteractionSource interactionSource2;
        ChipBorder border3;
        int $dirty2;
        Modifier modifier3;
        Object value$iv;
        Modifier modifier4;
        ChipBorder border4;
        MutableInteractionSource interactionSource3;
        boolean enabled3;
        Function2 icon3;
        Shape shape4;
        ChipColors colors4;
        ChipElevation elevation3;
        Composer $composer2 = $composer.startRestartGroup(170629701);
        ComposerKt.sourceInformation($composer2, "C(SuggestionChip)P(8,6,7,3,4,9,1,2)765@38541L5,766@38596L22,767@38675L25,768@38751L22,769@38825L39,775@39001L10,770@38870L534:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(onClick) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(label) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changed(modifier) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
            enabled2 = enabled;
        } else if (($changed & 3072) == 0) {
            enabled2 = enabled;
            $dirty3 |= $composer2.changed(enabled2) ? 2048 : 1024;
        } else {
            enabled2 = enabled;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
            icon2 = icon;
        } else if (($changed & 24576) == 0) {
            icon2 = icon;
            $dirty3 |= $composer2.changedInstance(icon2) ? 16384 : 8192;
        } else {
            icon2 = icon;
        }
        if ((196608 & $changed) == 0) {
            if ((i & 32) == 0) {
                shape2 = shape;
                int i6 = $composer2.changed(shape2) ? 131072 : 65536;
                $dirty3 |= i6;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i6;
        } else {
            shape2 = shape;
        }
        if ((1572864 & $changed) == 0) {
            if ((i & 64) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 1048576 : 524288;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 12582912) == 0) {
            $dirty3 |= ((i & 128) == 0 && $composer2.changed(elevation)) ? 8388608 : 4194304;
        }
        if (($changed & 100663296) == 0) {
            $dirty3 |= ((i & 256) == 0 && $composer2.changed(border)) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i8 = i & 512;
        if (i8 != 0) {
            $dirty3 |= 805306368;
            i2 = i8;
            mutableInteractionSource = interactionSource;
        } else if (($changed & 805306368) == 0) {
            i2 = i8;
            mutableInteractionSource = interactionSource;
            $dirty3 |= $composer2.changed(mutableInteractionSource) ? 536870912 : 268435456;
        } else {
            i2 = i8;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier4 = modifier;
            elevation3 = elevation;
            border4 = border;
            enabled3 = enabled2;
            icon3 = icon2;
            shape4 = shape2;
            colors4 = colors2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier.Companion modifier5 = i3 != 0 ? Modifier.INSTANCE : modifier;
                boolean enabled4 = i4 != 0 ? true : enabled2;
                Function2 icon4 = i5 != 0 ? null : icon2;
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                    shape3 = SuggestionChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 64) != 0) {
                    $dirty = $dirty3 & (-3670017);
                    colors3 = SuggestionChipDefaults.INSTANCE.suggestionChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 128) != 0) {
                    modifier2 = modifier5;
                    $dirty &= -29360129;
                    elevation2 = SuggestionChipDefaults.INSTANCE.m1975suggestionChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                } else {
                    modifier2 = modifier5;
                    elevation2 = elevation;
                }
                if ((i & 256) != 0) {
                    border2 = SuggestionChipDefaults.INSTANCE.m1972suggestionChipBorderd_3_b6Q(0L, 0L, 0.0f, $composer2, 3072, 7);
                    $dirty &= -234881025;
                } else {
                    border2 = border;
                }
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(2118466806);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    shape2 = shape3;
                    enabled2 = enabled4;
                    icon2 = icon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    border3 = border2;
                    modifier3 = modifier2;
                } else {
                    interactionSource2 = interactionSource;
                    border3 = border2;
                    shape2 = shape3;
                    enabled2 = enabled4;
                    icon2 = icon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    modifier3 = modifier2;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                }
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    $dirty3 &= -29360129;
                }
                if ((i & 256) != 0) {
                    int i9 = $dirty3 & (-234881025);
                    modifier3 = modifier;
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = i9;
                    elevation2 = elevation;
                } else {
                    border3 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = $dirty3;
                    modifier3 = modifier;
                    elevation2 = elevation;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(170629701, $dirty2, -1, "androidx.compose.material3.SuggestionChip (Chip.kt:770)");
            }
            TextStyle textStyleFromToken = TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), SuggestionChipTokens.INSTANCE.getLabelTextFont());
            long jM1330labelColorvNxB06k$material3_release = colors2.m1330labelColorvNxB06k$material3_release(enabled2);
            $composer2.startReplaceableGroup(2118467222);
            ComposerKt.sourceInformation($composer2, "782@39241L21");
            State<BorderStroke> stateBorderStroke$material3_release = border3 == null ? null : border3.borderStroke$material3_release(enabled2, $composer2, (($dirty2 >> 9) & 14) | (($dirty2 >> 21) & 112));
            $composer2.endReplaceableGroup();
            m1340ChipnkUnTEs(modifier3, onClick, enabled2, label, textStyleFromToken, jM1330labelColorvNxB06k$material3_release, icon2, null, shape2, colors2, elevation2, stateBorderStroke$material3_release != null ? stateBorderStroke$material3_release.getValue() : null, SuggestionChipDefaults.INSTANCE.m1970getHeightD9Ej5fM(), SuggestionChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | 12582912 | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 9) & 234881024) | (1879048192 & ($dirty2 << 9)), (($dirty2 >> 21) & 14) | 3456 | (($dirty2 >> 15) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier4 = modifier3;
            border4 = border3;
            interactionSource3 = interactionSource2;
            enabled3 = enabled2;
            icon3 = icon2;
            shape4 = shape2;
            colors4 = colors2;
            elevation3 = elevation2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier6 = modifier4;
            final boolean z = enabled3;
            final Function2 function2 = icon3;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation3;
            final ChipBorder chipBorder = border4;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.SuggestionChip.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i10) {
                    ChipKt.SuggestionChip(onClick, label, modifier6, z, function2, shape5, chipColors, chipElevation, chipBorder, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    public static final void ElevatedSuggestionChip(final Function0<Unit> function0, final Function2<? super Composer, ? super Integer, Unit> function2, Modifier modifier, boolean enabled, Function2<? super Composer, ? super Integer, Unit> function22, Shape shape, ChipColors colors, ChipElevation elevation, BorderStroke border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int i) {
        Function2<? super Composer, ? super Integer, Unit> function23;
        Shape shape2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape3;
        int $dirty;
        ChipColors colors3;
        ChipElevation elevation2;
        MutableInteractionSource interactionSource2;
        BorderStroke border2;
        ChipElevation elevation3;
        Modifier modifier2;
        boolean enabled2;
        int $dirty2;
        Object value$iv;
        Modifier modifier3;
        boolean enabled3;
        ChipElevation elevation4;
        BorderStroke border3;
        MutableInteractionSource interactionSource3;
        Function2<? super Composer, ? super Integer, Unit> function24;
        Shape shape4;
        ChipColors colors4;
        Composer $composer2 = $composer.startRestartGroup(-818834969);
        ComposerKt.sourceInformation($composer2, "C(ElevatedSuggestionChip)P(8,6,7,3,4,9,1,2)833@42061L5,834@42116L30,835@42203L33,837@42322L39,843@42498L10,838@42367L504:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(function0) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(function2) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changed(modifier) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
        } else if (($changed & 3072) == 0) {
            $dirty3 |= $composer2.changed(enabled) ? 2048 : 1024;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
            function23 = function22;
        } else if (($changed & 24576) == 0) {
            function23 = function22;
            $dirty3 |= $composer2.changedInstance(function23) ? 16384 : 8192;
        } else {
            function23 = function22;
        }
        if ((196608 & $changed) == 0) {
            if ((i & 32) == 0) {
                shape2 = shape;
                int i6 = $composer2.changed(shape2) ? 131072 : 65536;
                $dirty3 |= i6;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i6;
        } else {
            shape2 = shape;
        }
        if ((1572864 & $changed) == 0) {
            if ((i & 64) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 1048576 : 524288;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 12582912) == 0) {
            $dirty3 |= ((i & 128) == 0 && $composer2.changed(elevation)) ? 8388608 : 4194304;
        }
        int i8 = i & 256;
        if (i8 != 0) {
            $dirty3 |= 100663296;
        } else if (($changed & 100663296) == 0) {
            $dirty3 |= $composer2.changed(border) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i9 = i & 512;
        if (i9 != 0) {
            $dirty3 |= 805306368;
            i2 = i9;
            mutableInteractionSource = interactionSource;
        } else if (($changed & 805306368) == 0) {
            i2 = i9;
            mutableInteractionSource = interactionSource;
            $dirty3 |= $composer2.changed(mutableInteractionSource) ? 536870912 : 268435456;
        } else {
            i2 = i9;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier3 = modifier;
            enabled3 = enabled;
            elevation4 = elevation;
            border3 = border;
            function24 = function23;
            shape4 = shape2;
            colors4 = colors2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier modifier4 = i3 != 0 ? Modifier.INSTANCE : modifier;
                boolean enabled4 = i4 != 0 ? true : enabled;
                Function2<? super Composer, ? super Integer, Unit> function25 = i5 != 0 ? null : function23;
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                    shape3 = SuggestionChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 64) != 0) {
                    $dirty = $dirty3 & (-3670017);
                    colors3 = SuggestionChipDefaults.INSTANCE.elevatedSuggestionChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 128) != 0) {
                    elevation2 = SuggestionChipDefaults.INSTANCE.m1969elevatedSuggestionChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty &= -29360129;
                } else {
                    elevation2 = elevation;
                }
                BorderStroke border4 = i8 != 0 ? null : border;
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(-1455582059);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    border2 = border4;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    shape2 = shape3;
                    modifier2 = modifier4;
                    function23 = function25;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    elevation3 = elevation2;
                    enabled2 = enabled4;
                } else {
                    interactionSource2 = interactionSource;
                    border2 = border4;
                    elevation3 = elevation2;
                    shape2 = shape3;
                    modifier2 = modifier4;
                    enabled2 = enabled4;
                    function23 = function25;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                }
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    int i10 = $dirty3 & (-29360129);
                    enabled2 = enabled;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = i10;
                    modifier2 = modifier;
                } else {
                    modifier2 = modifier;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = $dirty3;
                    enabled2 = enabled;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-818834969, $dirty2, -1, "androidx.compose.material3.ElevatedSuggestionChip (Chip.kt:838)");
            }
            m1340ChipnkUnTEs(modifier2, function0, enabled2, function2, TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), SuggestionChipTokens.INSTANCE.getLabelTextFont()), colors2.m1330labelColorvNxB06k$material3_release(enabled2), function23, null, shape2, colors2, elevation3, border2, SuggestionChipDefaults.INSTANCE.m1970getHeightD9Ej5fM(), SuggestionChipPadding, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | 12582912 | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 9) & 234881024) | (($dirty2 << 9) & 1879048192), (($dirty2 >> 21) & 14) | 3456 | (($dirty2 >> 21) & 112) | (($dirty2 >> 15) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier2;
            enabled3 = enabled2;
            elevation4 = elevation3;
            border3 = border2;
            interactionSource3 = interactionSource2;
            function24 = function23;
            shape4 = shape2;
            colors4 = colors2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier5 = modifier3;
            final boolean z = enabled3;
            final Function2<? super Composer, ? super Integer, Unit> function26 = function24;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final BorderStroke borderStroke = border3;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.ElevatedSuggestionChip.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i11) {
                    ChipKt.ElevatedSuggestionChip(function0, function2, modifier5, z, function26, shape5, chipColors, chipElevation, borderStroke, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Maintained for binary compatibility. Use version with ElevatedSuggestionChip that take a BorderStroke instead", replaceWith = @ReplaceWith(expression = "ElevatedSuggestionChip(onClick, label, modifier, enabled, icon, shape, colors, elevation, border, interactionSource", imports = {}))
    public static final /* synthetic */ void ElevatedSuggestionChip(final Function0 onClick, final Function2 label, Modifier modifier, boolean enabled, Function2 icon, Shape shape, ChipColors colors, ChipElevation elevation, ChipBorder border, MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int i) {
        Function2 icon2;
        Shape shape2;
        ChipColors colors2;
        int i2;
        MutableInteractionSource mutableInteractionSource;
        Shape shape3;
        int $dirty;
        ChipColors colors3;
        ChipElevation elevation2;
        MutableInteractionSource interactionSource2;
        ChipBorder border2;
        ChipElevation elevation3;
        Modifier modifier2;
        boolean enabled2;
        int $dirty2;
        Object value$iv;
        Modifier modifier3;
        boolean enabled3;
        ChipElevation elevation4;
        ChipBorder border3;
        MutableInteractionSource interactionSource3;
        Function2 icon3;
        Shape shape4;
        ChipColors colors4;
        Composer $composer2 = $composer.startRestartGroup(1668751803);
        ComposerKt.sourceInformation($composer2, "C(ElevatedSuggestionChip)P(8,6,7,3,4,9,1,2)909@45897L5,910@45952L30,911@46039L33,913@46156L39,919@46332L10,914@46201L534:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        if ((i & 1) != 0) {
            $dirty3 |= 6;
        } else if (($changed & 6) == 0) {
            $dirty3 |= $composer2.changedInstance(onClick) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty3 |= 48;
        } else if (($changed & 48) == 0) {
            $dirty3 |= $composer2.changedInstance(label) ? 32 : 16;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            $dirty3 |= 384;
        } else if (($changed & 384) == 0) {
            $dirty3 |= $composer2.changed(modifier) ? 256 : 128;
        }
        int i4 = i & 8;
        if (i4 != 0) {
            $dirty3 |= 3072;
        } else if (($changed & 3072) == 0) {
            $dirty3 |= $composer2.changed(enabled) ? 2048 : 1024;
        }
        int i5 = i & 16;
        if (i5 != 0) {
            $dirty3 |= 24576;
            icon2 = icon;
        } else if (($changed & 24576) == 0) {
            icon2 = icon;
            $dirty3 |= $composer2.changedInstance(icon2) ? 16384 : 8192;
        } else {
            icon2 = icon;
        }
        if ((196608 & $changed) == 0) {
            if ((i & 32) == 0) {
                shape2 = shape;
                int i6 = $composer2.changed(shape2) ? 131072 : 65536;
                $dirty3 |= i6;
            } else {
                shape2 = shape;
            }
            $dirty3 |= i6;
        } else {
            shape2 = shape;
        }
        if ((1572864 & $changed) == 0) {
            if ((i & 64) == 0) {
                colors2 = colors;
                int i7 = $composer2.changed(colors2) ? 1048576 : 524288;
                $dirty3 |= i7;
            } else {
                colors2 = colors;
            }
            $dirty3 |= i7;
        } else {
            colors2 = colors;
        }
        if (($changed & 12582912) == 0) {
            $dirty3 |= ((i & 128) == 0 && $composer2.changed(elevation)) ? 8388608 : 4194304;
        }
        int i8 = i & 256;
        if (i8 != 0) {
            $dirty3 |= 100663296;
        } else if (($changed & 100663296) == 0) {
            $dirty3 |= $composer2.changed(border) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        int i9 = i & 512;
        if (i9 != 0) {
            $dirty3 |= 805306368;
            i2 = i9;
            mutableInteractionSource = interactionSource;
        } else if (($changed & 805306368) == 0) {
            i2 = i9;
            mutableInteractionSource = interactionSource;
            $dirty3 |= $composer2.changed(mutableInteractionSource) ? 536870912 : 268435456;
        } else {
            i2 = i9;
            mutableInteractionSource = interactionSource;
        }
        if (($dirty3 & 306783379) == 306783378 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            modifier3 = modifier;
            enabled3 = enabled;
            elevation4 = elevation;
            border3 = border;
            icon3 = icon2;
            shape4 = shape2;
            colors4 = colors2;
            interactionSource3 = mutableInteractionSource;
        } else {
            $composer2.startDefaults();
            if (($changed & 1) == 0 || $composer2.getDefaultsInvalid()) {
                Modifier modifier4 = i3 != 0 ? Modifier.INSTANCE : modifier;
                boolean enabled4 = i4 != 0 ? true : enabled;
                Function2 icon4 = i5 != 0 ? null : icon2;
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                    shape3 = SuggestionChipDefaults.INSTANCE.getShape($composer2, 6);
                } else {
                    shape3 = shape2;
                }
                if ((i & 64) != 0) {
                    $dirty = $dirty3 & (-3670017);
                    colors3 = SuggestionChipDefaults.INSTANCE.elevatedSuggestionChipColors($composer2, 6);
                } else {
                    $dirty = $dirty3;
                    colors3 = colors2;
                }
                if ((i & 128) != 0) {
                    elevation2 = SuggestionChipDefaults.INSTANCE.m1969elevatedSuggestionChipElevationaqJV_2Y(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, $composer2, 1572864, 63);
                    $dirty &= -29360129;
                } else {
                    elevation2 = elevation;
                }
                ChipBorder border4 = i8 != 0 ? null : border;
                if (i2 != 0) {
                    $composer2.startReplaceableGroup(-1455578225);
                    ComposerKt.sourceInformation($composer2, "CC(remember):Chip.kt#9igjgp");
                    Object it$iv = $composer2.rememberedValue();
                    if (it$iv == Composer.INSTANCE.getEmpty()) {
                        value$iv = InteractionSourceKt.MutableInteractionSource();
                        $composer2.updateRememberedValue(value$iv);
                    } else {
                        value$iv = it$iv;
                    }
                    $composer2.endReplaceableGroup();
                    border2 = border4;
                    interactionSource2 = (MutableInteractionSource) value$iv;
                    shape2 = shape3;
                    modifier2 = modifier4;
                    icon2 = icon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                    elevation3 = elevation2;
                    enabled2 = enabled4;
                } else {
                    interactionSource2 = interactionSource;
                    border2 = border4;
                    elevation3 = elevation2;
                    shape2 = shape3;
                    modifier2 = modifier4;
                    enabled2 = enabled4;
                    icon2 = icon4;
                    colors2 = colors3;
                    $dirty2 = $dirty;
                }
            } else {
                $composer2.skipToGroupEnd();
                if ((i & 32) != 0) {
                    $dirty3 &= -458753;
                }
                if ((i & 64) != 0) {
                    $dirty3 &= -3670017;
                }
                if ((i & 128) != 0) {
                    int i10 = $dirty3 & (-29360129);
                    enabled2 = enabled;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = i10;
                    modifier2 = modifier;
                } else {
                    modifier2 = modifier;
                    elevation3 = elevation;
                    border2 = border;
                    interactionSource2 = interactionSource;
                    $dirty2 = $dirty3;
                    enabled2 = enabled;
                }
            }
            $composer2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1668751803, $dirty2, -1, "androidx.compose.material3.ElevatedSuggestionChip (Chip.kt:914)");
            }
            TextStyle textStyleFromToken = TypographyKt.fromToken(MaterialTheme.INSTANCE.getTypography($composer2, 6), SuggestionChipTokens.INSTANCE.getLabelTextFont());
            long jM1330labelColorvNxB06k$material3_release = colors2.m1330labelColorvNxB06k$material3_release(enabled2);
            float fM1970getHeightD9Ej5fM = SuggestionChipDefaults.INSTANCE.m1970getHeightD9Ej5fM();
            PaddingValues paddingValues = SuggestionChipPadding;
            $composer2.startReplaceableGroup(-1455577719);
            ComposerKt.sourceInformation($composer2, "928@46662L21");
            State<BorderStroke> stateBorderStroke$material3_release = border2 == null ? null : border2.borderStroke$material3_release(enabled2, $composer2, (($dirty2 >> 9) & 14) | (($dirty2 >> 21) & 112));
            $composer2.endReplaceableGroup();
            m1340ChipnkUnTEs(modifier2, onClick, enabled2, label, textStyleFromToken, jM1330labelColorvNxB06k$material3_release, icon2, null, shape2, colors2, elevation3, stateBorderStroke$material3_release != null ? stateBorderStroke$material3_release.getValue() : null, fM1970getHeightD9Ej5fM, paddingValues, interactionSource2, $composer2, (($dirty2 >> 6) & 14) | 12582912 | (($dirty2 << 3) & 112) | (($dirty2 >> 3) & 896) | (($dirty2 << 6) & 7168) | (($dirty2 << 6) & 3670016) | (($dirty2 << 9) & 234881024) | (1879048192 & ($dirty2 << 9)), (($dirty2 >> 21) & 14) | 3456 | (($dirty2 >> 15) & 57344));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier2;
            enabled3 = enabled2;
            elevation4 = elevation3;
            border3 = border2;
            interactionSource3 = interactionSource2;
            icon3 = icon2;
            shape4 = shape2;
            colors4 = colors2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier5 = modifier3;
            final boolean z = enabled3;
            final Function2 function2 = icon3;
            final Shape shape5 = shape4;
            final ChipColors chipColors = colors4;
            final ChipElevation chipElevation = elevation4;
            final ChipBorder chipBorder = border3;
            final MutableInteractionSource mutableInteractionSource2 = interactionSource3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt.ElevatedSuggestionChip.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i11) {
                    ChipKt.ElevatedSuggestionChip(onClick, label, modifier5, z, function2, shape5, chipColors, chipElevation, chipBorder, mutableInteractionSource2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: Chip-nkUnTEs, reason: not valid java name */
    public static final void m1340ChipnkUnTEs(final Modifier modifier, final Function0<Unit> function0, final boolean enabled, final Function2<? super Composer, ? super Integer, Unit> function2, final TextStyle labelTextStyle, final long labelColor, final Function2<? super Composer, ? super Integer, Unit> function22, final Function2<? super Composer, ? super Integer, Unit> function23, final Shape shape, final ChipColors colors, final ChipElevation elevation, final BorderStroke border, final float minHeight, final PaddingValues paddingValues, final MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1) {
        int $dirty;
        Composer $composer2;
        int $dirty2;
        Composer $composer3 = $composer.startRestartGroup(1400504719);
        ComposerKt.sourceInformation($composer3, "C(Chip)P(10,11,3,5,7,6:c#ui.graphics.Color,8,14,13,1,2!1,9:c#ui.unit.Dp,12)1860@92959L917:Chip.kt#uh7d8r");
        int $dirty3 = $changed;
        int $dirty1 = $changed1;
        if (($changed & 6) == 0) {
            $dirty3 |= $composer3.changed(modifier) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty3 |= $composer3.changedInstance(function0) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty3 |= $composer3.changed(enabled) ? 256 : 128;
        }
        if (($changed & 3072) == 0) {
            $dirty3 |= $composer3.changedInstance(function2) ? 2048 : 1024;
        }
        if (($changed & 24576) == 0) {
            $dirty3 |= $composer3.changed(labelTextStyle) ? 16384 : 8192;
        }
        if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty = $dirty3 | ($composer3.changed(labelColor) ? 131072 : 65536);
        } else {
            $dirty = $dirty3;
        }
        if (($changed & 1572864) == 0) {
            $dirty |= $composer3.changedInstance(function22) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            $dirty |= $composer3.changedInstance(function23) ? 8388608 : 4194304;
        }
        if (($changed & 100663296) == 0) {
            $dirty |= $composer3.changed(shape) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty |= $composer3.changed(colors) ? 536870912 : 268435456;
        }
        int $dirty4 = $dirty;
        if (($changed1 & 6) == 0) {
            $dirty1 |= $composer3.changed(elevation) ? 4 : 2;
        }
        if (($changed1 & 48) == 0) {
            $dirty1 |= $composer3.changed(border) ? 32 : 16;
        }
        if (($changed1 & 384) == 0) {
            $dirty1 |= $composer3.changed(minHeight) ? 256 : 128;
        }
        if (($changed1 & 3072) == 0) {
            $dirty1 |= $composer3.changed(paddingValues) ? 2048 : 1024;
        }
        if (($changed1 & 24576) == 0) {
            $dirty1 |= $composer3.changed(interactionSource) ? 16384 : 8192;
        }
        if ((306783379 & $dirty4) != 306783378 || ($dirty1 & 9363) != 9362 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1400504719, $dirty4, $dirty1, "androidx.compose.material3.Chip (Chip.kt:1859)");
            }
            Modifier modifierSemantics$default = SemanticsModifierKt.semantics$default(modifier, false, new Function1<SemanticsPropertyReceiver, Unit>() { // from class: androidx.compose.material3.ChipKt$Chip$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                    invoke2(semanticsPropertyReceiver);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SemanticsPropertyReceiver $this$semantics) {
                    SemanticsPropertiesKt.m5076setRolekuIjeqM($this$semantics, Role.INSTANCE.m5060getButtono7Vup1c());
                }
            }, 1, null);
            long jM1320containerColorvNxB06k$material3_release = colors.m1320containerColorvNxB06k$material3_release(enabled);
            float fM1339tonalElevationu2uoSUM$material3_release = elevation != null ? elevation.m1339tonalElevationu2uoSUM$material3_release(enabled) : Dp.m5733constructorimpl(0);
            $composer3.startReplaceableGroup(64045438);
            ComposerKt.sourceInformation($composer3, "1867@93261L43");
            State<Dp> stateShadowElevation$material3_release = elevation == null ? null : elevation.shadowElevation$material3_release(enabled, interactionSource, $composer3, (($dirty4 >> 6) & 14) | (($dirty1 >> 9) & 112) | (($dirty1 << 6) & 896));
            $composer3.endReplaceableGroup();
            int $dirty12 = $dirty1;
            $composer2 = $composer3;
            $dirty2 = $dirty4;
            SurfaceKt.m1979Surfaceo_FOJdg(function0, modifierSemantics$default, enabled, shape, jM1320containerColorvNxB06k$material3_release, 0L, fM1339tonalElevationu2uoSUM$material3_release, stateShadowElevation$material3_release != null ? stateShadowElevation$material3_release.getValue().m5747unboximpl() : Dp.m5733constructorimpl(0), border, interactionSource, ComposableLambdaKt.composableLambda($composer2, -1985962652, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$Chip$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer4, int $changed2) {
                    ComposerKt.sourceInformation($composer4, "C1871@93409L461:Chip.kt#uh7d8r");
                    if (($changed2 & 3) != 2 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1985962652, $changed2, -1, "androidx.compose.material3.Chip.<anonymous> (Chip.kt:1871)");
                        }
                        ChipKt.m1341ChipContentfe0OD_I(function2, labelTextStyle, labelColor, function22, null, function23, colors.m1331leadingIconContentColorvNxB06k$material3_release(enabled), colors.m1332trailingIconContentColorvNxB06k$material3_release(enabled), minHeight, paddingValues, $composer4, 24576);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer4.skipToGroupEnd();
                }
            }), $composer2, (($dirty2 >> 3) & 14) | ($dirty2 & 896) | (($dirty2 >> 15) & 7168) | (($dirty12 << 21) & 234881024) | (1879048192 & ($dirty12 << 15)), 6, 32);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
            $dirty2 = $dirty4;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$Chip$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ChipKt.m1340ChipnkUnTEs(modifier, function0, enabled, function2, labelTextStyle, labelColor, function22, function23, shape, colors, elevation, border, minHeight, paddingValues, interactionSource, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: SelectableChip-u0RnIRE, reason: not valid java name */
    public static final void m1342SelectableChipu0RnIRE(final boolean selected, final Modifier modifier, final Function0<Unit> function0, final boolean enabled, final Function2<? super Composer, ? super Integer, Unit> function2, final TextStyle labelTextStyle, final Function2<? super Composer, ? super Integer, Unit> function22, final Function2<? super Composer, ? super Integer, Unit> function23, final Function2<? super Composer, ? super Integer, Unit> function24, final Shape shape, final SelectableChipColors colors, final SelectableChipElevation elevation, final BorderStroke border, final float minHeight, final PaddingValues paddingValues, final MutableInteractionSource interactionSource, Composer $composer, final int $changed, final int $changed1) {
        Composer $composer2;
        Composer $composer3 = $composer.startRestartGroup(402951308);
        ComposerKt.sourceInformation($composer3, "C(SelectableChip)P(13,10,11,4,6,7,8!1,15,14,2,3!1,9:c#ui.unit.Dp,12)1912@94717L33,1906@94515L1012:Chip.kt#uh7d8r");
        int $dirty = $changed;
        int $dirty1 = $changed1;
        if (($changed & 6) == 0) {
            $dirty |= $composer3.changed(selected) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer3.changed(modifier) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer3.changedInstance(function0) ? 256 : 128;
        }
        if (($changed & 3072) == 0) {
            $dirty |= $composer3.changed(enabled) ? 2048 : 1024;
        }
        if (($changed & 24576) == 0) {
            $dirty |= $composer3.changedInstance(function2) ? 16384 : 8192;
        }
        if (($changed & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty |= $composer3.changed(labelTextStyle) ? 131072 : 65536;
        }
        if (($changed & 1572864) == 0) {
            $dirty |= $composer3.changedInstance(function22) ? 1048576 : 524288;
        }
        if (($changed & 12582912) == 0) {
            $dirty |= $composer3.changedInstance(function23) ? 8388608 : 4194304;
        }
        if (($changed & 100663296) == 0) {
            $dirty |= $composer3.changedInstance(function24) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty |= $composer3.changed(shape) ? 536870912 : 268435456;
        }
        if (($changed1 & 6) == 0) {
            $dirty1 |= $composer3.changed(colors) ? 4 : 2;
        }
        if (($changed1 & 48) == 0) {
            $dirty1 |= $composer3.changed(elevation) ? 32 : 16;
        }
        if (($changed1 & 384) == 0) {
            $dirty1 |= $composer3.changed(border) ? 256 : 128;
        }
        if (($changed1 & 3072) == 0) {
            $dirty1 |= $composer3.changed(minHeight) ? 2048 : 1024;
        }
        if (($changed1 & 24576) == 0) {
            $dirty1 |= $composer3.changed(paddingValues) ? 16384 : 8192;
        }
        if (($changed1 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            $dirty1 |= $composer3.changed(interactionSource) ? 131072 : 65536;
        }
        if (($dirty & 306783379) != 306783378 || (74899 & $dirty1) != 74898 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(402951308, $dirty, $dirty1, "androidx.compose.material3.SelectableChip (Chip.kt:1904)");
            }
            Modifier modifierSemantics$default = SemanticsModifierKt.semantics$default(modifier, false, new Function1<SemanticsPropertyReceiver, Unit>() { // from class: androidx.compose.material3.ChipKt$SelectableChip$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                    invoke2(semanticsPropertyReceiver);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SemanticsPropertyReceiver $this$semantics) {
                    SemanticsPropertiesKt.m5076setRolekuIjeqM($this$semantics, Role.INSTANCE.m5061getCheckboxo7Vup1c());
                }
            }, 1, null);
            long jM3416unboximpl = colors.containerColor$material3_release(enabled, selected, $composer3, (($dirty >> 9) & 14) | (($dirty << 3) & 112) | (($dirty1 << 6) & 896)).getValue().m3416unboximpl();
            float fM1838tonalElevationu2uoSUM$material3_release = elevation != null ? elevation.m1838tonalElevationu2uoSUM$material3_release(enabled) : Dp.m5733constructorimpl(0);
            $composer3.startReplaceableGroup(1036687563);
            ComposerKt.sourceInformation($composer3, "1914@94864L43");
            State<Dp> stateShadowElevation$material3_release = elevation == null ? null : elevation.shadowElevation$material3_release(enabled, interactionSource, $composer3, (($dirty >> 9) & 14) | (($dirty1 >> 12) & 112) | (($dirty1 << 3) & 896));
            $composer3.endReplaceableGroup();
            int $dirty2 = $dirty;
            int $dirty12 = $dirty1;
            $composer2 = $composer3;
            SurfaceKt.m1977Surfaced85dljk(selected, function0, modifierSemantics$default, enabled, shape, jM3416unboximpl, 0L, fM1838tonalElevationu2uoSUM$material3_release, stateShadowElevation$material3_release != null ? stateShadowElevation$material3_release.getValue().m5747unboximpl() : Dp.m5733constructorimpl(0), border, interactionSource, ComposableLambdaKt.composableLambda($composer3, -577614814, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$SelectableChip$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer4, int $changed2) {
                    ComposerKt.sourceInformation($composer4, "C1918@95012L509:Chip.kt#uh7d8r");
                    if (($changed2 & 3) != 2 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-577614814, $changed2, -1, "androidx.compose.material3.SelectableChip.<anonymous> (Chip.kt:1918)");
                        }
                        ChipKt.m1341ChipContentfe0OD_I(function2, labelTextStyle, colors.m1829labelColorWaAFU9c$material3_release(enabled, selected), function22, function23, function24, colors.m1830leadingIconContentColorWaAFU9c$material3_release(enabled, selected), colors.m1831trailingIconContentColorWaAFU9c$material3_release(enabled, selected), minHeight, paddingValues, $composer4, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer4.skipToGroupEnd();
                }
            }), $composer2, ($dirty2 & 14) | (($dirty2 >> 3) & 112) | ($dirty2 & 7168) | (($dirty2 >> 15) & 57344) | (($dirty12 << 21) & 1879048192), (($dirty12 >> 15) & 14) | 48, 64);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$SelectableChip$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ChipKt.m1342SelectableChipu0RnIRE(selected, modifier, function0, enabled, function2, labelTextStyle, function22, function23, function24, shape, colors, elevation, border, minHeight, paddingValues, interactionSource, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), RecomposeScopeImplKt.updateChangedFlags($changed1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: ChipContent-fe0OD_I, reason: not valid java name */
    public static final void m1341ChipContentfe0OD_I(final Function2<? super Composer, ? super Integer, Unit> function2, final TextStyle labelTextStyle, final long labelColor, final Function2<? super Composer, ? super Integer, Unit> function22, final Function2<? super Composer, ? super Integer, Unit> function23, final Function2<? super Composer, ? super Integer, Unit> function24, final long leadingIconColor, final long trailingIconColor, final float minHeight, final PaddingValues paddingValues, Composer $composer, final int $changed) {
        Composer $composer2 = $composer.startRestartGroup(-782878228);
        ComposerKt.sourceInformation($composer2, "C(ChipContent)P(1,3,2:c#ui.graphics.Color,4!1,8,5:c#ui.graphics.Color,9:c#ui.graphics.Color,6:c#ui.unit.Dp)1946@95904L3672:Chip.kt#uh7d8r");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(function2) ? 4 : 2;
        }
        if (($changed & 48) == 0) {
            $dirty |= $composer2.changed(labelTextStyle) ? 32 : 16;
        }
        if (($changed & 384) == 0) {
            $dirty |= $composer2.changed(labelColor) ? 256 : 128;
        }
        if (($changed & 3072) == 0) {
            $dirty |= $composer2.changedInstance(function22) ? 2048 : 1024;
        }
        if (($changed & 24576) == 0) {
            $dirty |= $composer2.changedInstance(function23) ? 16384 : 8192;
        }
        if ((196608 & $changed) == 0) {
            $dirty |= $composer2.changedInstance(function24) ? 131072 : 65536;
        }
        if ((1572864 & $changed) == 0) {
            $dirty |= $composer2.changed(leadingIconColor) ? 1048576 : 524288;
        }
        if ((12582912 & $changed) == 0) {
            $dirty |= $composer2.changed(trailingIconColor) ? 8388608 : 4194304;
        }
        if ((100663296 & $changed) == 0) {
            $dirty |= $composer2.changed(minHeight) ? AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL : 33554432;
        }
        if (($changed & 805306368) == 0) {
            $dirty |= $composer2.changed(paddingValues) ? 536870912 : 268435456;
        }
        if (($dirty & 306783379) != 306783378 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-782878228, $dirty, -1, "androidx.compose.material3.ChipContent (Chip.kt:1945)");
            }
            CompositionLocalKt.CompositionLocalProvider((ProvidedValue<?>[]) new ProvidedValue[]{ContentColorKt.getLocalContentColor().provides(Color.m3396boximpl(labelColor)), TextKt.getLocalTextStyle().provides(labelTextStyle)}, ComposableLambdaKt.composableLambda($composer2, 1748799148, true, new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$ChipContent$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    Function0<ComposeUiNode> function0;
                    Function0<ComposeUiNode> function02;
                    long j;
                    Function0<ComposeUiNode> function03;
                    ComposerKt.sourceInformation($composer3, "C1950@96040L3530:Chip.kt#uh7d8r");
                    if (($changed2 & 3) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1748799148, $changed2, -1, "androidx.compose.material3.ChipContent.<anonymous> (Chip.kt:1950)");
                        }
                        Modifier modifierPadding = PaddingKt.padding(SizeKt.m596defaultMinSizeVpY3zN4$default(Modifier.INSTANCE, 0.0f, minHeight, 1, null), paddingValues);
                        AnonymousClass1 anonymousClass1 = new MeasurePolicy() { // from class: androidx.compose.material3.ChipKt$ChipContent$1.1
                            @Override // androidx.compose.ui.layout.MeasurePolicy
                            /* JADX INFO: renamed from: measure-3p2s80s */
                            public final MeasureResult mo38measure3p2s80s(MeasureScope $this$Layout, List<? extends Measurable> list, long constraints) {
                                Object it$iv;
                                Object it$iv2;
                                int index$iv$iv = 0;
                                int size = list.size();
                                while (true) {
                                    if (index$iv$iv >= size) {
                                        it$iv = null;
                                        break;
                                    }
                                    it$iv = list.get(index$iv$iv);
                                    Measurable it = (Measurable) it$iv;
                                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(it), "leadingIcon")) {
                                        break;
                                    }
                                    index$iv$iv++;
                                }
                                Measurable measurable = (Measurable) it$iv;
                                final Placeable leadingIconPlaceable = measurable != null ? measurable.mo4677measureBRTryo0(Constraints.m5679copyZbe2FdA(constraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(constraints) : 0, (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(constraints) : 0, (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(constraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(constraints) : 0)) : null;
                                final int leadingIconWidth = TextFieldImplKt.widthOrZero(leadingIconPlaceable);
                                final int leadingIconHeight = TextFieldImplKt.heightOrZero(leadingIconPlaceable);
                                int index$iv$iv2 = 0;
                                int size2 = list.size();
                                while (true) {
                                    if (index$iv$iv2 >= size2) {
                                        it$iv2 = null;
                                        break;
                                    }
                                    it$iv2 = list.get(index$iv$iv2);
                                    Measurable it2 = (Measurable) it$iv2;
                                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(it2), "trailingIcon")) {
                                        break;
                                    }
                                    index$iv$iv2++;
                                }
                                Measurable measurable2 = (Measurable) it$iv2;
                                final Placeable trailingIconPlaceable = measurable2 != null ? measurable2.mo4677measureBRTryo0(Constraints.m5679copyZbe2FdA(constraints, (11 & 1) != 0 ? Constraints.m5691getMinWidthimpl(constraints) : 0, (11 & 2) != 0 ? Constraints.m5689getMaxWidthimpl(constraints) : 0, (11 & 4) != 0 ? Constraints.m5690getMinHeightimpl(constraints) : 0, (11 & 8) != 0 ? Constraints.m5688getMaxHeightimpl(constraints) : 0)) : null;
                                int trailingIconWidth = TextFieldImplKt.widthOrZero(trailingIconPlaceable);
                                final int trailingIconHeight = TextFieldImplKt.heightOrZero(trailingIconPlaceable);
                                List<? extends Measurable> list2 = list;
                                int $i$f$fastFirst = 0;
                                int index$iv$iv3 = 0;
                                int size3 = list2.size();
                                while (index$iv$iv3 < size3) {
                                    Object item$iv$iv = list2.get(index$iv$iv3);
                                    Measurable it3 = (Measurable) item$iv$iv;
                                    List<? extends Measurable> list3 = list2;
                                    int $i$f$fastFirst2 = $i$f$fastFirst;
                                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(it3), "label")) {
                                        final Placeable labelPlaceable = ((Measurable) item$iv$iv).mo4677measureBRTryo0(ConstraintsKt.m5706offsetNN6EwU$default(constraints, -(leadingIconWidth + trailingIconWidth), 0, 2, null));
                                        int width = labelPlaceable.getWidth() + leadingIconWidth + trailingIconWidth;
                                        final int height = Math.max(leadingIconHeight, Math.max(labelPlaceable.getHeight(), trailingIconHeight));
                                        return MeasureScope.layout$default($this$Layout, width, height, null, new Function1<Placeable.PlacementScope, Unit>() { // from class: androidx.compose.material3.ChipKt.ChipContent.1.1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Unit invoke(Placeable.PlacementScope placementScope) {
                                                invoke2(placementScope);
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(Placeable.PlacementScope $this$layout) {
                                                Placeable placeable = leadingIconPlaceable;
                                                if (placeable != null) {
                                                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable, 0, Alignment.INSTANCE.getCenterVertically().align(leadingIconHeight, height), 0.0f, 4, null);
                                                }
                                                Placeable.PlacementScope.placeRelative$default($this$layout, labelPlaceable, leadingIconWidth, 0, 0.0f, 4, null);
                                                Placeable placeable2 = trailingIconPlaceable;
                                                if (placeable2 != null) {
                                                    Placeable.PlacementScope.placeRelative$default($this$layout, placeable2, labelPlaceable.getWidth() + leadingIconWidth, Alignment.INSTANCE.getCenterVertically().align(trailingIconHeight, height), 0.0f, 4, null);
                                                }
                                            }
                                        }, 4, null);
                                    }
                                    index$iv$iv3++;
                                    list2 = list3;
                                    $i$f$fastFirst = $i$f$fastFirst2;
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        };
                        Function2<Composer, Integer, Unit> function25 = function23;
                        Function2<Composer, Integer, Unit> function26 = function22;
                        Function2<Composer, Integer, Unit> function27 = function24;
                        long j2 = leadingIconColor;
                        Function2<Composer, Integer, Unit> function28 = function2;
                        long j3 = trailingIconColor;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifierPadding);
                        int $changed$iv$iv = ((384 << 9) & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function0 = constructor;
                            $composer3.createNode(function0);
                        } else {
                            function0 = constructor;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv, anonymousClass1, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv, localMap$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv))) {
                            $this$Layout_u24lambda_u240$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv));
                            $this$Layout_u24lambda_u240$iv.apply(Integer.valueOf(compositeKeyHash$iv), setCompositeKeyHash);
                        }
                        function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i = ($changed$iv$iv >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, 651014416, "C1972@96983L351:Chip.kt#uh7d8r");
                        $composer3.startReplaceableGroup(651014416);
                        ComposerKt.sourceInformation($composer3, "1956@96281L667");
                        if (function25 == null && function26 == null) {
                            j = j3;
                        } else {
                            Modifier modifier$iv = LayoutIdKt.layoutId(Modifier.INSTANCE, "leadingIcon");
                            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
                            $composer3.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                            $composer3.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                            CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv);
                            int $changed$iv$iv$iv = ((((54 << 3) & 112) << 9) & 7168) | 6;
                            if (!($composer3.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer3.startReusableNode();
                            if ($composer3.getInserting()) {
                                function02 = constructor2;
                                $composer3.createNode(function02);
                            } else {
                                function02 = constructor2;
                                $composer3.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer3);
                            j = j3;
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash2);
                            }
                            function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i2 = ($changed$iv$iv$iv >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i3 = ((54 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, 1725997148, "C:Chip.kt#uh7d8r");
                            if (function25 != null) {
                                $composer3.startReplaceableGroup(1725997168);
                                ComposerKt.sourceInformation($composer3, "1962@96569L8");
                                function25.invoke($composer3, 0);
                                $composer3.endReplaceableGroup();
                            } else if (function26 != null) {
                                $composer3.startReplaceableGroup(1725997271);
                                ComposerKt.sourceInformation($composer3, "1964@96672L198");
                                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.getLocalContentColor().provides(Color.m3396boximpl(j2)), function26, $composer3, ProvidedValue.$stable | 0);
                                $composer3.endReplaceableGroup();
                            } else {
                                $composer3.startReplaceableGroup(1725997533);
                                $composer3.endReplaceableGroup();
                            }
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                        }
                        $composer3.endReplaceableGroup();
                        Modifier modifier$iv2 = PaddingKt.m563paddingVpY3zN4(LayoutIdKt.layoutId(Modifier.INSTANCE, "label"), ChipKt.HorizontalElementsPadding, Dp.m5733constructorimpl(0));
                        Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getStart();
                        Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv2 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv2 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                        int $changed$iv$iv$iv2 = ((((438 << 3) & 112) << 9) & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function03 = constructor3;
                            $composer3.createNode(function03);
                        } else {
                            function03 = constructor3;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash3);
                        }
                        function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i4 = ($changed$iv$iv$iv2 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        int i5 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1725997940, "C1978@97307L7:Chip.kt#uh7d8r");
                        function28.invoke($composer3, 0);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        $composer3.startReplaceableGroup(-313041442);
                        ComposerKt.sourceInformation($composer3, "1981@97399L471");
                        if (function27 != null) {
                            Modifier modifier$iv3 = LayoutIdKt.layoutId(Modifier.INSTANCE, "trailingIcon");
                            Alignment contentAlignment$iv2 = Alignment.INSTANCE.getCenter();
                            $composer3.startReplaceableGroup(733328855);
                            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                            MeasurePolicy measurePolicy$iv3 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv2, false, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                            $composer3.startReplaceableGroup(-1323940314);
                            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)77@3132L23,79@3222L420:Layout.kt#80mrfh");
                            int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                            CompositionLocalMap localMap$iv$iv3 = $composer3.getCurrentCompositionLocalMap();
                            Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                            int $changed$iv$iv$iv3 = ((((54 << 3) & 112) << 9) & 7168) | 6;
                            if (!($composer3.getApplier() instanceof Applier)) {
                                ComposablesKt.invalidApplier();
                            }
                            $composer3.startReusableNode();
                            if ($composer3.getInserting()) {
                                $composer3.createNode(constructor4);
                            } else {
                                $composer3.useNode();
                            }
                            Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer3);
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                            if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                                $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                                $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash4);
                            }
                            function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i6 = ($changed$iv$iv$iv3 >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                            int i7 = ((54 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, 1725998267, "C1986@97634L188:Chip.kt#uh7d8r");
                            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.getLocalContentColor().provides(Color.m3396boximpl(j)), function27, $composer3, ProvidedValue.$stable | 0);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                        }
                        $composer3.endReplaceableGroup();
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), $composer2, 48);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: androidx.compose.material3.ChipKt$ChipContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ChipKt.m1341ChipContentfe0OD_I(function2, labelTextStyle, labelColor, function22, function23, function24, leadingIconColor, trailingIconColor, minHeight, paddingValues, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    public static final ChipColors getDefaultSuggestionChipColors(ColorScheme $this$defaultSuggestionChipColors) {
        ChipColors defaultSuggestionChipColorsCached = $this$defaultSuggestionChipColors.getDefaultSuggestionChipColorsCached();
        if (defaultSuggestionChipColorsCached != null) {
            return defaultSuggestionChipColorsCached;
        }
        long jM3441getTransparent0d7_KjU = Color.INSTANCE.m3441getTransparent0d7_KjU();
        long jFromToken = ColorSchemeKt.fromToken($this$defaultSuggestionChipColors, SuggestionChipTokens.INSTANCE.getLabelTextColor());
        long jFromToken2 = ColorSchemeKt.fromToken($this$defaultSuggestionChipColors, SuggestionChipTokens.INSTANCE.getLeadingIconColor());
        long jM3442getUnspecified0d7_KjU = Color.INSTANCE.m3442getUnspecified0d7_KjU();
        long jM3441getTransparent0d7_KjU2 = Color.INSTANCE.m3441getTransparent0d7_KjU();
        long jFromToken3 = ColorSchemeKt.fromToken($this$defaultSuggestionChipColors, SuggestionChipTokens.INSTANCE.getDisabledLabelTextColor());
        long jM3404copywmQWz5c = Color.m3404copywmQWz5c(jFromToken3, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jFromToken3) : 0.38f, (14 & 2) != 0 ? Color.m3412getRedimpl(jFromToken3) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jFromToken3) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jFromToken3) : 0.0f);
        long jFromToken4 = ColorSchemeKt.fromToken($this$defaultSuggestionChipColors, SuggestionChipTokens.INSTANCE.getDisabledLeadingIconColor());
        ChipColors it = new ChipColors(jM3441getTransparent0d7_KjU, jFromToken, jFromToken2, jM3442getUnspecified0d7_KjU, jM3441getTransparent0d7_KjU2, jM3404copywmQWz5c, Color.m3404copywmQWz5c(jFromToken4, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jFromToken4) : 0.38f, (14 & 2) != 0 ? Color.m3412getRedimpl(jFromToken4) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jFromToken4) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jFromToken4) : 0.0f), Color.INSTANCE.m3442getUnspecified0d7_KjU(), null);
        $this$defaultSuggestionChipColors.setDefaultSuggestionChipColorsCached$material3_release(it);
        return it;
    }

    static /* synthetic */ PaddingValues inputChipPadding$default(boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        if ((i & 4) != 0) {
            z3 = false;
        }
        return inputChipPadding(z, z2, z3);
    }

    private static final PaddingValues inputChipPadding(boolean hasAvatar, boolean hasLeadingIcon, boolean hasTrailingIcon) {
        float start = (hasAvatar || !hasLeadingIcon) ? Dp.m5733constructorimpl(4) : Dp.m5733constructorimpl(8);
        float end = hasTrailingIcon ? Dp.m5733constructorimpl(8) : Dp.m5733constructorimpl(4);
        return PaddingKt.m559PaddingValuesa9UjIt4$default(start, 0.0f, end, 0.0f, 10, null);
    }
}
