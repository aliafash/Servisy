package com.maw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;
import androidx.activity.compose.ActivityResultRegistryKt;
import androidx.activity.compose.ManagedActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.autofill.HintConstants;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.CategoryKt;
import androidx.compose.material.icons.filled.CheckCircleKt;
import androidx.compose.material.icons.filled.HourglassEmptyKt;
import androidx.compose.material.icons.filled.PeopleKt;
import androidx.compose.material3.AndroidAlertDialog_androidKt;
import androidx.compose.material3.BadgeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SwitchKt;
import androidx.compose.material3.TextFieldColors;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.AndroidImageBitmap_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.window.AndroidDialog_androidKt;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.profileinstaller.ProfileVerifier;
import com.google.android.gms.actions.SearchIntents;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AdminControllerScreens.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\u001aG\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007¢\u0006\u0002\u0010\r\u001a#\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a#\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a\u0015\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0017\u001a#\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a+\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00102\u0006\u0010\u001e\u001a\u00020\u0007H\u0007¢\u0006\u0002\u0010\u001f\u001a+\u0010 \u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00102\u0006\u0010\u001e\u001a\u00020\u0007H\u0007¢\u0006\u0002\u0010\u001f\u001a#\u0010#\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a\u0015\u0010&\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0017\u001a\u0015\u0010'\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0017\u001a\u0015\u0010(\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0017\u001a#\u0010)\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020*0\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a\u0015\u0010+\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0005H\u0007¢\u0006\u0002\u0010\u0017\u001a#\u0010,\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a#\u0010-\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020.0\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a<\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002012\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\b\b\u0002\u00107\u001a\u000208H\u0007ø\u0001\u0000¢\u0006\u0004\b9\u0010:\u001a\u0015\u0010;\u001a\u00020\u00012\u0006\u0010<\u001a\u000201H\u0007¢\u0006\u0002\u0010=\u001a\u001c\u0010>\u001a\u00020\u00012\u0006\u0010?\u001a\u00020@2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020%0\u0010\u001a\u0016\u0010B\u001a\u0002012\u0006\u0010?\u001a\u00020@2\u0006\u0010\u0013\u001a\u00020\u0005\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006C²\u0006\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010D\u001a\b\u0012\u0004\u0012\u00020*0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010A\u001a\b\u0012\u0004\u0012\u00020%0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010X\u008a\u0084\u0002²\u0006\f\u0010E\u001a\u0004\u0018\u00010*X\u008a\u008e\u0002²\u0006\n\u0010F\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010G\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010I\u001a\u00020JX\u008a\u008e\u0002²\u0006\n\u0010K\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010L\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010M\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010N\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010O\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010P\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010Q\u001a\u000201X\u008a\u008e\u0002²\u0006\u0010\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0010X\u008a\u0084\u0002²\u0006\n\u00100\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010R\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010S\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010T\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010U\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010V\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010W\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010X\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010Y\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010Z\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010[\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010\\\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010]\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010^\u001a\u00020HX\u008a\u008e\u0002²\u0006\f\u0010_\u001a\u0004\u0018\u00010\u001dX\u008a\u008e\u0002²\u0006\n\u0010`\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010a\u001a\u000201X\u008a\u008e\u0002²\u0006\u0010\u0010b\u001a\b\u0012\u0004\u0012\u00020c0\u0010X\u008a\u0084\u0002²\u0006\u0010\u0010d\u001a\b\u0012\u0004\u0012\u00020e0\u0010X\u008a\u0084\u0002²\u0006\n\u0010f\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010g\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010h\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010i\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010j\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010k\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010l\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010m\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010n\u001a\u00020HX\u008a\u008e\u0002²\u0006\f\u0010o\u001a\u0004\u0018\u00010.X\u008a\u008e\u0002²\u0006\n\u0010p\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010q\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010r\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010s\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010t\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010u\u001a\u00020HX\u008a\u008e\u0002²\u0006\u0010\u0010v\u001a\b\u0012\u0004\u0012\u00020w0\u0010X\u008a\u0084\u0002²\u0006\n\u0010x\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u00100\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010y\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010z\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010{\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010|\u001a\u00020HX\u008a\u008e\u0002²\u0006\n\u0010}\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010~\u001a\u000201X\u008a\u008e\u0002²\u0006\n\u0010\u007f\u001a\u00020HX\u008a\u008e\u0002²\u0006\u000b\u0010\u0080\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u0081\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u0010\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0010X\u008a\u0084\u0002²\u0006\f\u0010\u0082\u0001\u001a\u00030\u0083\u0001X\u008a\u0084\u0002²\u0006\u000b\u0010\u0084\u0001\u001a\u00020HX\u008a\u008e\u0002²\u0006\u000b\u0010\u0085\u0001\u001a\u00020HX\u008a\u008e\u0002²\u0006\u000b\u0010\u0086\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u0087\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u0088\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u0089\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u008a\u0001\u001a\u000201X\u008a\u008e\u0002²\u0006\u000b\u0010\u008b\u0001\u001a\u000201X\u008a\u008e\u0002"}, d2 = {"BookingAdminCard", "", "booking", "Lcom/maw/Booking;", "viewModel", "Lcom/maw/MainViewModel;", "currentFont", "Landroidx/compose/ui/text/font/FontFamily;", "onStatusChange", "Lkotlin/Function1;", "Lcom/maw/BookingStatus;", "onDelete", "Lkotlin/Function0;", "(Lcom/maw/Booking;Lcom/maw/MainViewModel;Landroidx/compose/ui/text/font/FontFamily;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "BookingsAdminTab", "bookings", "", "(Lcom/maw/MainViewModel;Ljava/util/List;Landroidx/compose/runtime/Composer;I)V", "ComposedActiveProvidersTab", "vm", "providers", "Lcom/maw/Provider;", "ComposedAdminStatsDashboardTab", "(Lcom/maw/MainViewModel;Landroidx/compose/runtime/Composer;I)V", "ComposedAdsAndBannersTab", "banners", "Lcom/maw/Banner;", "ComposedCategoryManagementTab", "categories", "Lcom/maw/Category;", "fontFamily", "(Lcom/maw/MainViewModel;Ljava/util/List;Landroidx/compose/ui/text/font/FontFamily;Landroidx/compose/runtime/Composer;I)V", "ComposedCitiesManagementTab", "cities", "Lcom/maw/City;", "ComposedComplaintsAndReportsTab", "list", "Lcom/maw/Report;", "ComposedDatabaseBackupSchedulerTab", "ComposedManualAddProviderTab", "ComposedNotificationsTab", "ComposedPendingRequestsTab", "Lcom/maw/PendingProvider;", "ComposedPrivacyAndChatLogsTab", "ComposedSubscriptionsAndLimitsTab", "ComposedSupervisorsAdminTab", "Lcom/maw/AdminAccount;", "KpiStatCard", "title", "", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "modifier", "Landroidx/compose/ui/Modifier;", "KpiStatCard-uDo3WH8", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JLandroidx/compose/ui/Modifier;Landroidx/compose/runtime/Composer;II)V", "SelfieWithZoomLightbox", "base64", "(Ljava/lang/String;Landroidx/compose/runtime/Composer;I)V", "exportReportsToCSV", "context", "Landroid/content/Context;", "reports", "triggerLocalBackupSimulation", "app_debug", "pendingRequests", "activeRejectItem", "rejectReasonText", "showLightbox", "", "scale", "", HintConstants.AUTOFILL_HINT_NAME, "phone", "area", "description", "selectedCat", "selectedCity", "manualBase64Image", "desc", "imageUrl", "redirectLink", "dispSeconds", "sizeHeightSelect", "typeSelector", "nameAr", "nameEn", "descAr", "iconSim", "parentIdVal", "orderNo", "isPinnedCheck", "editingCategory", "cityNameAr", "cityNameEn", "chats", "Lcom/maw/Chat;", "messages", "Lcom/maw/ChatMessage;", "globalChatDisabled", SearchIntents.EXTRA_QUERY, "newSupervisorName", "newSupervisorPass", "canApproveRequests", "canManageCategories", "canManageBanners", "canDeleteActiveProviders", "canSeeReports", "editSupervisorTarget", "editPass", "editCanApproveRequests", "editCanManageCategories", "editCanManageBanners", "editCanDeleteActiveProviders", "editCanSeeReports", "auditLogs", "Lcom/maw/AuditLog;", "showAuditLogsDialog", "body", "notifyOnAccepts", "notifyOnReports", "automaticBackupEnabled", "autoCleanupSelection", "backupStatusText", "showChooseFolderDialog", "filterStatus", "searchQuery", "settings", "Lcom/maw/AppSettings;", "showStatusMenu", "showEditDialog", "editTripleName", "editPhoneNumber", "editServiceType", "editResidencePlc", "editDetails", "editTime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AdminControllerScreensKt {
    public static final void ComposedAdminStatsDashboardTab(final MainViewModel vm, Composer $composer, final int $changed) {
        Function0<ComposeUiNode> function0;
        Function0<ComposeUiNode> function02;
        Function0<ComposeUiNode> function03;
        Intrinsics.checkNotNullParameter(vm, "vm");
        Composer $composer2 = $composer.startRestartGroup(-129956550);
        ComposerKt.sourceInformation($composer2, "C(ComposedAdminStatsDashboardTab)50@2136L29,51@2207L29,52@2270L29,53@2346L29,54@2406L29,55@2468L29,62@2674L21,59@2583L5305:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(vm) ? 4 : 2;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 11) != 2 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-129956550, $dirty2, -1, "com.maw.ComposedAdminStatsDashboardTab (AdminControllerScreens.kt:49)");
            }
            final State providers$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getProviders(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            final State categories$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getCategoriesState(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            FlowExtKt.collectAsStateWithLifecycle(vm.getCitiesState(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            State pendingRequests$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getPendingRequests(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            FlowExtKt.collectAsStateWithLifecycle(vm.getReports(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            State bookings$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getBookings(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
            final FontFamily currentFont = MainActivityKt.resolveAppFontFamily(vm.getSettings().getValue().getSelectedFontName());
            Modifier modifier$iv = ScrollKt.verticalScroll$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), ScrollKt.rememberScrollState(0, $composer2, 0, 1), false, null, false, 14, null);
            Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(12));
            $composer2.startReplaceableGroup(-483455358);
            ComposerKt.sourceInformation($composer2, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
            Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
            int $i$f$Column = ((48 >> 3) & 14) | ((48 >> 3) & 112);
            MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer2, $i$f$Column);
            int $changed$iv$iv = (48 << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                function0 = constructor;
                $composer2.createNode(function0);
            } else {
                function0 = constructor;
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
            }
            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            int i = ($changed$iv$iv$iv >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer2, 276693656, "C79@3979L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i2 = ((48 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer2, -84367238, "C65@2772L239,74@3047L654,91@3711L643,110@4464L49,109@4424L2338,160@6846L49,159@6806L1076:AdminControllerScreens.kt#foq9o6");
            TextKt.m2124Text4IGK_g("📊 نظرة عامة سريعة على مؤشرات نمو المنصة والجمهورية", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(13), (FontStyle) null, FontWeight.INSTANCE.getBold(), currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 199686, 0, 130962);
            Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
            Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
            $composer2.startReplaceableGroup(693286680);
            ComposerKt.sourceInformation($composer2, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
            Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getTop();
            MeasurePolicy measurePolicy$iv2 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer2, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            int $changed$iv$iv2 = (54 << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv2 = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
            int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                function02 = constructor2;
                $composer2.createNode(function02);
            } else {
                function02 = constructor2;
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
            }
            function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer2, -326681643, "C92@4661L9:Row.kt#2w3rfo");
            int i4 = ((54 >> 6) & 112) | 6;
            RowScope $this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u246 = RowScopeInstance.INSTANCE;
            ComposerKt.sourceInformationMarkerStart($composer2, 65168152, "C75@3153L258,82@3424L267:AdminControllerScreens.kt#foq9o6");
            m6260KpiStatCarduDo3WH8("الكوادر المعتمدة", String.valueOf(ComposedAdminStatsDashboardTab$lambda$0(providers$delegate).size()), PeopleKt.getPeople(Icons.INSTANCE.getDefault()), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), RowScope.weight$default($this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u246, Modifier.INSTANCE, 1.0f, false, 2, null), $composer2, 6, 0);
            m6260KpiStatCarduDo3WH8("طلبات جارية", String.valueOf(ComposedAdminStatsDashboardTab$lambda$3(pendingRequests$delegate).size()), HourglassEmptyKt.getHourglassEmpty(Icons.INSTANCE.getDefault()), AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), RowScope.weight$default($this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u246, Modifier.INSTANCE, 1.0f, false, 2, null), $composer2, 6, 0);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            Modifier modifier$iv3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
            Arrangement.Horizontal horizontalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
            $composer2.startReplaceableGroup(693286680);
            ComposerKt.sourceInformation($composer2, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
            Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getTop();
            MeasurePolicy measurePolicy$iv3 = RowKt.rowMeasurePolicy(horizontalArrangement$iv2, verticalAlignment$iv2, $composer2, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            int $changed$iv$iv3 = (54 << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv3 = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
            int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                function03 = constructor3;
                $composer2.createNode(function03);
            } else {
                function03 = constructor3;
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
            }
            function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer2, -326681643, "C92@4661L9:Row.kt#2w3rfo");
            int i6 = ((54 >> 6) & 112) | 6;
            RowScope $this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u247 = RowScopeInstance.INSTANCE;
            ComposerKt.sourceInformationMarkerStart($composer2, 65168816, "C92@3817L252,99@4082L262:AdminControllerScreens.kt#foq9o6");
            m6260KpiStatCarduDo3WH8("المجموعات والحرف", String.valueOf(ComposedAdminStatsDashboardTab$lambda$1(categories$delegate).size()), CategoryKt.getCategory(Icons.INSTANCE.getDefault()), Color.INSTANCE.m3434getCyan0d7_KjU(), RowScope.weight$default($this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u247, Modifier.INSTANCE, 1.0f, false, 2, null), $composer2, 3078, 0);
            m6260KpiStatCarduDo3WH8("الحجوزات الفعلية", String.valueOf(ComposedAdminStatsDashboardTab$lambda$5(bookings$delegate).size()), CheckCircleKt.getCheckCircle(Icons.INSTANCE.getDefault()), AppTheme.INSTANCE.m6264getLightGreen0d7_KjU(), RowScope.weight$default($this$ComposedAdminStatsDashboardTab_u24lambda_u248_u24lambda_u247, Modifier.INSTANCE, 1.0f, false, 2, null), $composer2, 6, 0);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            CardKt.Card(null, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(10)), CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, -1297192878, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedAdminStatsDashboardTab$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:61:0x0415  */
                /* JADX WARN: Removed duplicated region for block: B:64:0x0421  */
                /* JADX WARN: Removed duplicated region for block: B:65:0x0427  */
                /* JADX WARN: Removed duplicated region for block: B:68:0x045a  */
                /* JADX WARN: Removed duplicated region for block: B:72:0x046e  */
                /* JADX WARN: Removed duplicated region for block: B:76:0x064b  */
                /* JADX WARN: Removed duplicated region for block: B:79:0x0657  */
                /* JADX WARN: Removed duplicated region for block: B:80:0x065d  */
                /* JADX WARN: Removed duplicated region for block: B:83:0x0690  */
                /* JADX WARN: Removed duplicated region for block: B:87:0x06a6 A[ADDED_TO_REGION] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r131, androidx.compose.runtime.Composer r132, int r133) {
                    /*
                        Method dump skipped, instruction units count: 1996
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt$ComposedAdminStatsDashboardTab$1$3.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 25);
            CardKt.Card(null, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(10)), CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, -878436933, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedAdminStatsDashboardTab$1$4
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:42:0x033c  */
                /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r69, androidx.compose.runtime.Composer r70, int r71) {
                    /*
                        Method dump skipped, instruction units count: 832
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt$ComposedAdminStatsDashboardTab$1$4.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 25);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedAdminStatsDashboardTab.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i7) {
                    AdminControllerScreensKt.ComposedAdminStatsDashboardTab(vm, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Provider> ComposedAdminStatsDashboardTab$lambda$0(State<? extends List<Provider>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Category> ComposedAdminStatsDashboardTab$lambda$1(State<? extends List<Category>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final List<City> ComposedAdminStatsDashboardTab$lambda$2(State<? extends List<City>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final List<PendingProvider> ComposedAdminStatsDashboardTab$lambda$3(State<? extends List<PendingProvider>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final List<Report> ComposedAdminStatsDashboardTab$lambda$4(State<? extends List<Report>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final List<Booking> ComposedAdminStatsDashboardTab$lambda$5(State<? extends List<Booking>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    /* JADX INFO: renamed from: KpiStatCard-uDo3WH8, reason: not valid java name */
    public static final void m6260KpiStatCarduDo3WH8(final String title, final String value, final ImageVector icon, final long color, Modifier modifier, Composer $composer, final int $changed, final int i) {
        Modifier modifier2;
        Modifier modifier3;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Composer $composer2 = $composer.startRestartGroup(-585313022);
        ComposerKt.sourceInformation($composer2, "C(KpiStatCard)P(3,4,1,0:c#ui.graphics.Color)185@8092L49,184@8056L742:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(title) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer2.changed(value) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty |= 384;
        } else if (($changed & 896) == 0) {
            $dirty |= $composer2.changed(icon) ? 256 : 128;
        }
        if ((i & 8) != 0) {
            $dirty |= 3072;
        } else if (($changed & 7168) == 0) {
            $dirty |= $composer2.changed(color) ? 2048 : 1024;
        }
        int i2 = i & 16;
        if (i2 != 0) {
            $dirty |= 24576;
            modifier2 = modifier;
        } else if ((57344 & $changed) == 0) {
            modifier2 = modifier;
            $dirty |= $composer2.changed(modifier2) ? 16384 : 8192;
        } else {
            modifier2 = modifier;
        }
        int $dirty2 = $dirty;
        if ((46811 & $dirty2) != 9362 || !$composer2.getSkipping()) {
            modifier3 = i2 != 0 ? Modifier.INSTANCE : modifier2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-585313022, $dirty2, -1, "com.maw.KpiStatCard (AdminControllerScreens.kt:183)");
            }
            CardKt.Card(modifier3, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(10)), CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 663547728, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$KpiStatCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card, Composer $composer3, int $changed2) {
                    Function0<ComposeUiNode> function0;
                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                    ComposerKt.sourceInformation($composer3, "C189@8230L562:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(663547728, $changed2, -1, "com.maw.KpiStatCard.<anonymous> (AdminControllerScreens.kt:189)");
                        }
                        Modifier modifierM562padding3ABfNKs = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
                        Alignment.Vertical centerVertically = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween = Arrangement.INSTANCE.getSpaceBetween();
                        ImageVector imageVector = icon;
                        String str = title;
                        long j = color;
                        String str2 = value;
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv = RowKt.rowMeasurePolicy(spaceBetween, centerVertically, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifierM562padding3ABfNKs);
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
                        int i3 = ($changed$iv$iv$iv >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                        int i4 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, -512618242, "C194@8428L242,199@8683L99:AdminControllerScreens.kt#foq9o6");
                        $composer3.startReplaceableGroup(-483455358);
                        ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                        Modifier modifier$iv = Modifier.INSTANCE;
                        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.getTop();
                        Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
                        MeasurePolicy measurePolicy$iv2 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer3, ((0 >> 3) & 14) | ((0 >> 3) & 112));
                        int $changed$iv$iv2 = (0 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv2 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv);
                        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function0 = constructor2;
                            $composer3.createNode(function0);
                        } else {
                            function0 = constructor2;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                        }
                        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i5 = ($changed$iv$iv$iv2 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        int i6 = ((0 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, -1808299976, "C195@8453L49,196@8519L40,197@8576L80:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g(str, (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3456, 0, 131058);
                        SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer3, 6);
                        TextKt.m2124Text4IGK_g(str2, (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(18), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200064, 0, 131026);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        IconKt.m1597Iconww6aTOc(imageVector, str, SizeKt.m611size3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(24)), j, $composer3, 384, 0);
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
            }), $composer2, (($dirty2 >> 12) & 14) | ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
            modifier3 = modifier2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Modifier modifier4 = modifier3;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$KpiStatCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    AdminControllerScreensKt.m6260KpiStatCarduDo3WH8(title, value, icon, color, modifier4, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    public static final void ComposedPendingRequestsTab(final MainViewModel vm, final List<PendingProvider> list, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Composer $composer2;
        final MutableState activeRejectItem$delegate;
        Object value$iv3;
        Function0<ComposeUiNode> function0;
        Function0<ComposeUiNode> function02;
        Intrinsics.checkNotNullParameter(vm, "vm");
        Intrinsics.checkNotNullParameter(list, "list");
        Composer $composer3 = $composer.startRestartGroup(-2128509805);
        ComposerKt.sourceInformation($composer3, "C(ComposedPendingRequestsTab)P(1):AdminControllerScreens.kt#foq9o6");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-2128509805, $changed, -1, "com.maw.ComposedPendingRequestsTab (AdminControllerScreens.kt:208)");
        }
        final FontFamily currentFont = MainActivityKt.resolveAppFontFamily(vm.getSettings().getValue().getSelectedFontName());
        if (list.isEmpty()) {
            $composer3.startReplaceableGroup(712952655);
            ComposerKt.sourceInformation($composer3, "212@9277L497");
            Modifier modifier$iv = SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null);
            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
            $composer3.startReplaceableGroup(733328855);
            ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            int $changed$iv$iv = (54 << 3) & 112;
            $composer3.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
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
                function0 = constructor;
                $composer3.createNode(function0);
            } else {
                function0 = constructor;
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
            int i = ($changed$iv$iv$iv >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i2 = ((54 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer3, -1509424172, "C213@9367L397:AdminControllerScreens.kt#foq9o6");
            Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getCenterHorizontally();
            $composer3.startReplaceableGroup(-483455358);
            ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
            Modifier modifier$iv2 = Modifier.INSTANCE;
            Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.getTop();
            int $changed$iv$iv2 = ((384 >> 3) & 14) | ((384 >> 3) & 112);
            MeasurePolicy measurePolicy$iv2 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer3, $changed$iv$iv2);
            int $changed$iv$iv3 = (384 << 3) & 112;
            $composer3.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
            CompositionLocalMap localMap$iv$iv2 = $composer3.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
            int $changed$iv$iv$iv2 = (($changed$iv$iv3 << 9) & 7168) | 6;
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
            Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer3);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
            }
            function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
            $composer3.startReplaceableGroup(2058660585);
            int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i4 = ((384 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer3, -2074012990, "C214@9444L116,215@9577L40,216@9634L116:AdminControllerScreens.kt#foq9o6");
            IconKt.m1597Iconww6aTOc(HourglassEmptyKt.getHourglassEmpty(Icons.INSTANCE.getDefault()), "Empty", SizeKt.m611size3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(48)), Color.INSTANCE.m3436getGray0d7_KjU(), $composer3, 3504, 0);
            SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(8)), $composer3, 6);
            TextKt.m2124Text4IGK_g("لا توجد أي طلبات مستلمة معلقة بالوقت الحالي.", (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 130994);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            $composer3.endReplaceableGroup();
            $composer3.endNode();
            $composer3.endReplaceableGroup();
            $composer3.endReplaceableGroup();
            ComposerKt.sourceInformationMarkerEnd($composer3);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            $composer3.endReplaceableGroup();
            $composer3.endNode();
            $composer3.endReplaceableGroup();
            $composer3.endReplaceableGroup();
            $composer3.endReplaceableGroup();
            $composer2 = $composer3;
        } else {
            $composer3.startReplaceableGroup(712953174);
            ComposerKt.sourceInformation($composer3, "220@9820L51,221@9904L31,223@9945L4243,290@14318L27,290@14292L2707");
            $composer3.startReplaceableGroup(712953208);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv = $composer3.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(null, null, 2, null);
                $composer3.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState activeRejectItem$delegate2 = (MutableState) value$iv;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(712953292);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv2 = $composer3.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableState rejectReasonText$delegate = (MutableState) value$iv2;
            $composer3.endReplaceableGroup();
            $composer2 = $composer3;
            LazyDslKt.LazyColumn(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, null, false, Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8)), null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedPendingRequestsTab.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                    invoke2(lazyListScope);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(LazyListScope LazyColumn) {
                    Intrinsics.checkNotNullParameter(LazyColumn, "$this$LazyColumn");
                    final List<PendingProvider> list2 = list;
                    final FontFamily fontFamily = currentFont;
                    final MainViewModel mainViewModel = vm;
                    final MutableState<PendingProvider> mutableState = activeRejectItem$delegate2;
                    final MutableState<String> mutableState2 = rejectReasonText$delegate;
                    final Function1 contentType$iv = new Function1() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$invoke$$inlined$items$default$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((PendingProvider) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(PendingProvider pendingProvider) {
                            return null;
                        }
                    };
                    LazyColumn.items(list2.size(), null, new Function1<Integer, Object>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$invoke$$inlined$items$default$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return contentType$iv.invoke(list2.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$invoke$$inlined$items$default$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it, Composer $composer4, int $changed2) {
                            ComposerKt.sourceInformation($composer4, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer4.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer4.changed(it) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer4.getSkipping()) {
                                $composer4.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            int i5 = $dirty & 14;
                            final PendingProvider pp = (PendingProvider) list2.get(it);
                            ComposerKt.sourceInformationMarkerStart($composer4, -2074012343, "C*226@10139L49,225@10091L4073:AdminControllerScreens.kt#foq9o6");
                            CardColors cardColorsM1287cardColorsro_MJ88 = CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer4, CardDefaults.$stable << 12, 14);
                            RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(10));
                            BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L));
                            final FontFamily fontFamily2 = fontFamily;
                            final MainViewModel mainViewModel2 = mainViewModel;
                            final MutableState mutableState3 = mutableState;
                            final MutableState mutableState4 = mutableState2;
                            CardKt.Card(null, roundedCornerShapeM831RoundedCornerShape0680j_4, cardColorsM1287cardColorsro_MJ88, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer4, -1241837313, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope Card, Composer $composer5, int $changed3) {
                                    Function0<ComposeUiNode> function03;
                                    Function0<ComposeUiNode> function04;
                                    Function0<ComposeUiNode> function05;
                                    Function0<ComposeUiNode> function06;
                                    Function0<ComposeUiNode> function07;
                                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                    ComposerKt.sourceInformation($composer5, "C230@10352L3794:AdminControllerScreens.kt#foq9o6");
                                    if (($changed3 & 81) == 16 && $composer5.getSkipping()) {
                                        $composer5.skipToGroupEnd();
                                        return;
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-1241837313, $changed3, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:230)");
                                    }
                                    Modifier modifier$iv3 = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
                                    final PendingProvider pendingProvider = pp;
                                    final FontFamily fontFamily3 = fontFamily2;
                                    final MainViewModel mainViewModel3 = mainViewModel2;
                                    final MutableState<PendingProvider> mutableState5 = mutableState3;
                                    final MutableState<String> mutableState6 = mutableState4;
                                    $composer5.startReplaceableGroup(-483455358);
                                    ComposerKt.sourceInformation($composer5, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                                    Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.getTop();
                                    Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                                    MeasurePolicy measurePolicy$iv3 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer5, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                                    int $changed$iv$iv4 = (6 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv3 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                                    int $changed$iv$iv$iv3 = (($changed$iv$iv4 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function03 = constructor3;
                                        $composer5.createNode(function03);
                                    } else {
                                        function03 = constructor3;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                                        $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                                        $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                                    }
                                    function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i6 = ($changed$iv$iv$iv3 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                    int i7 = ((6 >> 6) & 112) | 6;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 971749205, "C231@10421L550,237@10996L40,238@11061L75,239@11161L86,240@11272L95,241@11392L106,243@11524L41,246@11663L1253,266@12942L41,267@13008L1116:AdminControllerScreens.kt#foq9o6");
                                    Modifier modifier$iv4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                                    Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getSpaceBetween();
                                    Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
                                    $composer5.startReplaceableGroup(693286680);
                                    ComposerKt.sourceInformation($composer5, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                    MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer5, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                                    int $changed$iv$iv5 = (438 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv4 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
                                    int $changed$iv$iv$iv4 = (($changed$iv$iv5 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function04 = constructor4;
                                        $composer5.createNode(function04);
                                    } else {
                                        function04 = constructor4;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                                        $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                                        $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                                    }
                                    function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i8 = ($changed$iv$iv$iv4 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                    int i9 = ((438 >> 6) & 112) | 6;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 702663198, "C232@10589L116,233@10734L211:AdminControllerScreens.kt#foq9o6");
                                    TextKt.m2124Text4IGK_g(pendingProvider.getName(), (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(13), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily3, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199680, 0, 130962);
                                    BadgeKt.m1244BadgeeopBjH0(null, AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, ComposableLambdaKt.composableLambda($composer5, 127882310, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1$1$1$1
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Badge, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Badge, "$this$Badge");
                                            ComposerKt.sourceInformation($composer6, "C234@10812L103:AdminControllerScreens.kt#foq9o6");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(127882310, $changed4, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:234)");
                                            }
                                            TextKt.m2124Text4IGK_g("قيد الانتظار والمراجعة المجهرية", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, fontFamily3, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3462, 0, 130994);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 3072, 5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(6)), $composer5, 6);
                                    TextKt.m2124Text4IGK_g("📌 الرقم المهني: " + pendingProvider.getPhone(), (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3456, 0, 131058);
                                    TextKt.m2124Text4IGK_g("📁 الفئة الوظيفية: " + pendingProvider.getCategory(), (Modifier) null, AppTheme.INSTANCE.m6263getGrayText0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3072, 0, 131058);
                                    TextKt.m2124Text4IGK_g("📍 عنوان السكن والمنطقة: " + pendingProvider.getArea() + " (" + pendingProvider.getCity() + ")", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3456, 0, 131058);
                                    TextKt.m2124Text4IGK_g("📝 نبذة ومهارات الكادر: " + pendingProvider.getDescription(), (Modifier) null, Color.INSTANCE.m3438getLightGray0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 2, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3456, 3072, 122866);
                                    SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(10)), $composer5, 6);
                                    Modifier modifier$iv5 = PaddingKt.m564paddingVpY3zN4$default(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.0f, Dp.m5733constructorimpl(4), 1, null);
                                    Arrangement.Horizontal horizontalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
                                    $composer5.startReplaceableGroup(693286680);
                                    ComposerKt.sourceInformation($composer5, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                    Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getTop();
                                    MeasurePolicy measurePolicy$iv5 = RowKt.rowMeasurePolicy(horizontalArrangement$iv2, verticalAlignment$iv2, $composer5, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                                    int $changed$iv$iv6 = (54 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv5 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifier$iv5);
                                    int $changed$iv$iv$iv5 = (($changed$iv$iv6 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function05 = constructor5;
                                        $composer5.createNode(function05);
                                    } else {
                                        function05 = constructor5;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                                        $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                                        $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
                                    }
                                    function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i10 = ($changed$iv$iv$iv5 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                    int i11 = ((54 >> 6) & 112) | 6;
                                    RowScope $this$invoke_u24lambda_u245_u24lambda_u243 = RowScopeInstance.INSTANCE;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 702664618, "C253@12009L403,259@12483L407:AdminControllerScreens.kt#foq9o6");
                                    Modifier modifier$iv6 = RowScope.weight$default($this$invoke_u24lambda_u245_u24lambda_u243, Modifier.INSTANCE, 1.0f, false, 2, null);
                                    Alignment.Horizontal horizontalAlignment$iv3 = Alignment.INSTANCE.getCenterHorizontally();
                                    $composer5.startReplaceableGroup(-483455358);
                                    ComposerKt.sourceInformation($composer5, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                                    Arrangement.Vertical verticalArrangement$iv3 = Arrangement.INSTANCE.getTop();
                                    MeasurePolicy measurePolicy$iv6 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv3, horizontalAlignment$iv3, $composer5, ((384 >> 3) & 14) | ((384 >> 3) & 112));
                                    int $changed$iv$iv7 = (384 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv6 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv6 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor6 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf6 = LayoutKt.modifierMaterializerOf(modifier$iv6);
                                    int $changed$iv$iv$iv6 = (($changed$iv$iv7 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function06 = constructor6;
                                        $composer5.createNode(function06);
                                    } else {
                                        function06 = constructor6;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv6 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, measurePolicy$iv6, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, localMap$iv$iv6, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash6 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv6.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv6.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv6))) {
                                        $this$Layout_u24lambda_u240$iv$iv6.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv6));
                                        $this$Layout_u24lambda_u240$iv$iv6.apply(Integer.valueOf(compositeKeyHash$iv$iv6), setCompositeKeyHash6);
                                    }
                                    function3ModifierMaterializerOf6.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv6 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i12 = ($changed$iv$iv$iv6 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                                    ColumnScopeInstance columnScopeInstance3 = ColumnScopeInstance.INSTANCE;
                                    int i13 = ((384 >> 6) & 112) | 6;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -835667768, "C254@12134L98,255@12265L40,256@12338L44:AdminControllerScreens.kt#foq9o6");
                                    TextKt.m2124Text4IGK_g("صورة شخصية 👤", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199686, 0, 131026);
                                    SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer5, 6);
                                    AdminControllerScreensKt.SelfieWithZoomLightbox(pendingProvider.getSelfieImageBase64(), $composer5, 0);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    Modifier modifier$iv7 = RowScope.weight$default($this$invoke_u24lambda_u245_u24lambda_u243, Modifier.INSTANCE, 1.0f, false, 2, null);
                                    Alignment.Horizontal horizontalAlignment$iv4 = Alignment.INSTANCE.getCenterHorizontally();
                                    $composer5.startReplaceableGroup(-483455358);
                                    ComposerKt.sourceInformation($composer5, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                                    Arrangement.Vertical verticalArrangement$iv4 = Arrangement.INSTANCE.getTop();
                                    MeasurePolicy measurePolicy$iv7 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv4, horizontalAlignment$iv4, $composer5, ((384 >> 3) & 14) | ((384 >> 3) & 112));
                                    int $changed$iv$iv8 = (384 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv7 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv7 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor7 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf7 = LayoutKt.modifierMaterializerOf(modifier$iv7);
                                    int $changed$iv$iv$iv7 = (($changed$iv$iv8 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function07 = constructor7;
                                        $composer5.createNode(function07);
                                    } else {
                                        function07 = constructor7;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv7 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, measurePolicy$iv7, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, localMap$iv$iv7, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash7 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv7.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv7.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv7))) {
                                        $this$Layout_u24lambda_u240$iv$iv7.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv7));
                                        $this$Layout_u24lambda_u240$iv$iv7.apply(Integer.valueOf(compositeKeyHash$iv$iv7), setCompositeKeyHash7);
                                    }
                                    function3ModifierMaterializerOf7.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv7 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i14 = ($changed$iv$iv$iv7 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                                    ColumnScopeInstance columnScopeInstance4 = ColumnScopeInstance.INSTANCE;
                                    int i15 = ((384 >> 6) & 112) | 6;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -835667294, "C260@12608L98,261@12739L40,262@12812L48:AdminControllerScreens.kt#foq9o6");
                                    TextKt.m2124Text4IGK_g("صورة هويته 🪪", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199686, 0, 131026);
                                    SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(4)), $composer5, 6);
                                    AdminControllerScreensKt.SelfieWithZoomLightbox(pendingProvider.getNationalIdImageBase64(), $composer5, 0);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    SpacerKt.Spacer(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(10)), $composer5, 6);
                                    Arrangement.HorizontalOrVertical horizontalOrVerticalM471spacedBy0680j_4 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
                                    Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                                    $composer5.startReplaceableGroup(693286680);
                                    ComposerKt.sourceInformation($composer5, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                    Alignment.Vertical verticalAlignment$iv3 = Alignment.INSTANCE.getTop();
                                    MeasurePolicy measurePolicy$iv8 = RowKt.rowMeasurePolicy(horizontalOrVerticalM471spacedBy0680j_4, verticalAlignment$iv3, $composer5, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                                    int $changed$iv$iv9 = (54 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv8 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv8 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor8 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf8 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default);
                                    int $changed$iv$iv$iv8 = (($changed$iv$iv9 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        $composer5.createNode(constructor8);
                                    } else {
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv8 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, measurePolicy$iv8, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, localMap$iv$iv8, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash8 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv8.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv8.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv8))) {
                                        $this$Layout_u24lambda_u240$iv$iv8.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv8));
                                        $this$Layout_u24lambda_u240$iv$iv8.apply(Integer.valueOf(compositeKeyHash$iv$iv8), setCompositeKeyHash8);
                                    }
                                    function3ModifierMaterializerOf8.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv8 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i16 = ($changed$iv$iv$iv8 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                    int i17 = ((54 >> 6) & 112) | 6;
                                    RowScope $this$invoke_u24lambda_u245_u24lambda_u244 = RowScopeInstance.INSTANCE;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 702665739, "C270@13281L48,268@13130L467,277@13782L50,275@13626L472:AdminControllerScreens.kt#foq9o6");
                                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1$1$3$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }

                                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                            mainViewModel3.approveProviderRequest(pendingProvider, "الأدمن");
                                        }
                                    }, SizeKt.m597height3ABfNKs(RowScope.weight$default($this$invoke_u24lambda_u245_u24lambda_u244, Modifier.INSTANCE, 1.2f, false, 2, null), Dp.m5733constructorimpl(36)), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4281236786L), 0L, 0L, 0L, $composer5, (ButtonDefaults.$stable << 12) | 6, 14), null, null, null, null, ComposableLambdaKt.composableLambda($composer5, 1250975041, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1$1$3$2
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Button, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                            ComposerKt.sourceInformation($composer6, "C273@13474L93:AdminControllerScreens.kt#foq9o6");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(1250975041, $changed4, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:273)");
                                            }
                                            TextKt.m2124Text4IGK_g("قبول وتفعيل الكادر ✅", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily3, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3462, 0, 130994);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 805306368, 492);
                                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1$1$3$3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }

                                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                            mutableState5.setValue(pendingProvider);
                                            mutableState6.setValue("");
                                        }
                                    }, SizeKt.m597height3ABfNKs(RowScope.weight$default($this$invoke_u24lambda_u245_u24lambda_u244, Modifier.INSTANCE, 1.0f, false, 2, null), Dp.m5733constructorimpl(36)), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer5, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableLambdaKt.composableLambda($composer5, -245628296, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$2$1$1$1$3$4
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Button, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                            ComposerKt.sourceInformation($composer6, "C280@13975L93:AdminControllerScreens.kt#foq9o6");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(-245628296, $changed4, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:280)");
                                            }
                                            TextKt.m2124Text4IGK_g("رفض مع توضيح السبب ❌", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily3, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3462, 0, 130994);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 805306368, 492);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }), $composer4, 221184, 9);
                            ComposerKt.sourceInformationMarkerEnd($composer4);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
            }, $composer2, 24582, 238);
            if (ComposedPendingRequestsTab$lambda$12(activeRejectItem$delegate2) != null) {
                $composer2.startReplaceableGroup(712957706);
                ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object it$iv3 = $composer2.rememberedValue();
                if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                    activeRejectItem$delegate = activeRejectItem$delegate2;
                    value$iv3 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            activeRejectItem$delegate.setValue(null);
                        }
                    };
                    $composer2.updateRememberedValue(value$iv3);
                } else {
                    activeRejectItem$delegate = activeRejectItem$delegate2;
                    value$iv3 = it$iv3;
                }
                $composer2.endReplaceableGroup();
                AndroidDialog_androidKt.Dialog((Function0) value$iv3, null, ComposableLambdaKt.composableLambda($composer2, 497867625, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedPendingRequestsTab.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                        invoke(composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer $composer4, int $changed2) {
                        ComposerKt.sourceInformation($composer4, "C292@14413L49,291@14365L2620:AdminControllerScreens.kt#foq9o6");
                        if (($changed2 & 11) != 2 || !$composer4.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(497867625, $changed2, -1, "com.maw.ComposedPendingRequestsTab.<anonymous> (AdminControllerScreens.kt:291)");
                            }
                            CardColors cardColorsM1287cardColorsro_MJ88 = CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer4, CardDefaults.$stable << 12, 14);
                            RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12));
                            BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU());
                            final FontFamily fontFamily = currentFont;
                            final MutableState<PendingProvider> mutableState = activeRejectItem$delegate;
                            final MutableState<String> mutableState2 = rejectReasonText$delegate;
                            final MainViewModel mainViewModel = vm;
                            CardKt.Card(null, roundedCornerShapeM831RoundedCornerShape0680j_4, cardColorsM1287cardColorsro_MJ88, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer4, 350849627, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedPendingRequestsTab.4.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope Card, Composer $composer5, int $changed3) {
                                    Function0<ComposeUiNode> function03;
                                    final MutableState<String> mutableState3;
                                    Object value$iv4;
                                    Function0<ComposeUiNode> function04;
                                    Object value$iv5;
                                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                    ComposerKt.sourceInformation($composer5, "C296@14628L2339:AdminControllerScreens.kt#foq9o6");
                                    if (($changed3 & 81) == 16 && $composer5.getSkipping()) {
                                        $composer5.skipToGroupEnd();
                                        return;
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(350849627, $changed3, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:296)");
                                    }
                                    Modifier modifier$iv3 = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(14));
                                    Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
                                    final FontFamily fontFamily2 = fontFamily;
                                    final MutableState<PendingProvider> mutableState4 = mutableState;
                                    MutableState<String> mutableState5 = mutableState2;
                                    final MainViewModel mainViewModel2 = mainViewModel;
                                    $composer5.startReplaceableGroup(-483455358);
                                    ComposerKt.sourceInformation($composer5, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                                    Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                                    MeasurePolicy measurePolicy$iv3 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer5, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                                    int $changed$iv$iv4 = (54 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv3 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                                    int $changed$iv$iv$iv3 = (($changed$iv$iv4 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function03 = constructor3;
                                        $composer5.createNode(function03);
                                    } else {
                                        function03 = constructor3;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                                        $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                                        $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                                    }
                                    function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                    int i6 = ((54 >> 6) & 112) | 6;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -805548901, "C297@14748L139,298@14912L114,305@15472L72,302@15193L25,300@15076L494,308@15596L1349:AdminControllerScreens.kt#foq9o6");
                                    TextKt.m2124Text4IGK_g("⚠️ إرسال قرار رفض مسبب للطلب", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199686, 0, 130962);
                                    PendingProvider pendingProviderComposedPendingRequestsTab$lambda$12 = AdminControllerScreensKt.ComposedPendingRequestsTab$lambda$12(mutableState4);
                                    Intrinsics.checkNotNull(pendingProviderComposedPendingRequestsTab$lambda$12);
                                    TextKt.m2124Text4IGK_g("الاسم المرفوض: " + pendingProviderComposedPendingRequestsTab$lambda$12.getName(), (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, fontFamily2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3456, 0, 130994);
                                    String strComposedPendingRequestsTab$lambda$15 = AdminControllerScreensKt.ComposedPendingRequestsTab$lambda$15(mutableState5);
                                    Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                                    TextFieldColors textFieldColorsM1726colors0hiis_0 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer5, 54, 0, 0, 0, 3072, 2147483644, 4095);
                                    $composer5.startReplaceableGroup(-805548456);
                                    ComposerKt.sourceInformation($composer5, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                    Object it$iv4 = $composer5.rememberedValue();
                                    if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                                        mutableState3 = mutableState5;
                                        value$iv4 = new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$4$1$1$1$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                                invoke2(str);
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(String it) {
                                                Intrinsics.checkNotNullParameter(it, "it");
                                                mutableState3.setValue(it);
                                            }
                                        };
                                        $composer5.updateRememberedValue(value$iv4);
                                    } else {
                                        mutableState3 = mutableState5;
                                        value$iv4 = it$iv4;
                                    }
                                    $composer5.endReplaceableGroup();
                                    OutlinedTextFieldKt.OutlinedTextField(strComposedPendingRequestsTab$lambda$15, (Function1<? super String, Unit>) value$iv4, modifierFillMaxWidth$default, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6272getLambda1$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_0, $composer5, 1573296, 0, 0, 4194232);
                                    Modifier modifier$iv4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                                    Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
                                    $composer5.startReplaceableGroup(693286680);
                                    ComposerKt.sourceInformation($composer5, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                    Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getTop();
                                    MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer5, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                                    int $changed$iv$iv5 = (54 << 3) & 112;
                                    $composer5.startReplaceableGroup(-1323940314);
                                    ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                    int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                    CompositionLocalMap localMap$iv$iv4 = $composer5.getCurrentCompositionLocalMap();
                                    Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
                                    int $changed$iv$iv$iv4 = (($changed$iv$iv5 << 9) & 7168) | 6;
                                    if (!($composer5.getApplier() instanceof Applier)) {
                                        ComposablesKt.invalidApplier();
                                    }
                                    $composer5.startReusableNode();
                                    if ($composer5.getInserting()) {
                                        function04 = constructor4;
                                        $composer5.createNode(function04);
                                    } else {
                                        function04 = constructor4;
                                        $composer5.useNode();
                                    }
                                    Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer5);
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                    if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                                        $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                                        $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                                    }
                                    function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                                    $composer5.startReplaceableGroup(2058660585);
                                    int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
                                    ComposerKt.sourceInformationMarkerStart($composer5, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                    int i8 = ((54 >> 6) & 112) | 6;
                                    RowScope $this$invoke_u24lambda_u243_u24lambda_u242 = RowScopeInstance.INSTANCE;
                                    ComposerKt.sourceInformationMarkerStart($composer5, 971754502, "C315@16167L50,309@15718L753,322@16635L45,321@16550L27,320@16500L419:AdminControllerScreens.kt#foq9o6");
                                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$4$1$1$2$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }

                                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                            String strComposedPendingRequestsTab$lambda$152 = AdminControllerScreensKt.ComposedPendingRequestsTab$lambda$15(mutableState3);
                                            if (StringsKt.isBlank(strComposedPendingRequestsTab$lambda$152)) {
                                                strComposedPendingRequestsTab$lambda$152 = "المستندات والصور الشخصية أو صورة الهوية غير مطابقة لشروط الدليل";
                                            }
                                            String reason = strComposedPendingRequestsTab$lambda$152;
                                            MainViewModel mainViewModel3 = mainViewModel2;
                                            PendingProvider pendingProviderComposedPendingRequestsTab$lambda$122 = AdminControllerScreensKt.ComposedPendingRequestsTab$lambda$12(mutableState4);
                                            Intrinsics.checkNotNull(pendingProviderComposedPendingRequestsTab$lambda$122);
                                            mainViewModel3.rejectProviderRequest(pendingProviderComposedPendingRequestsTab$lambda$122.getId(), reason, "الأدمن");
                                            mutableState4.setValue(null);
                                        }
                                    }, RowScope.weight$default($this$invoke_u24lambda_u243_u24lambda_u242, Modifier.INSTANCE, 1.2f, false, 2, null), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer5, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableLambdaKt.composableLambda($composer5, 407026981, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$4$1$1$2$2
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Button, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                            ComposerKt.sourceInformation($composer6, "C318@16348L93:AdminControllerScreens.kt#foq9o6");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(407026981, $changed4, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:318)");
                                            }
                                            TextKt.m2124Text4IGK_g("تأكيد الرفض والإرسال", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3462, 0, 130994);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 805306368, 492);
                                    ButtonColors buttonColorsM1266buttonColorsro_MJ88 = ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(Color.INSTANCE.m3435getDarkGray0d7_KjU(), 0L, 0L, 0L, $composer5, (ButtonDefaults.$stable << 12) | 6, 14);
                                    Modifier modifierWeight$default = RowScope.weight$default($this$invoke_u24lambda_u243_u24lambda_u242, Modifier.INSTANCE, 0.8f, false, 2, null);
                                    $composer5.startReplaceableGroup(971755334);
                                    ComposerKt.sourceInformation($composer5, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                    Object it$iv5 = $composer5.rememberedValue();
                                    if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                                        value$iv5 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$4$1$1$2$3$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public /* bridge */ /* synthetic */ Unit invoke() {
                                                invoke2();
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2() {
                                                mutableState4.setValue(null);
                                            }
                                        };
                                        $composer5.updateRememberedValue(value$iv5);
                                    } else {
                                        value$iv5 = it$iv5;
                                    }
                                    $composer5.endReplaceableGroup();
                                    ButtonKt.Button((Function0) value$iv5, modifierWeight$default, false, null, buttonColorsM1266buttonColorsro_MJ88, null, null, null, null, ComposableLambdaKt.composableLambda($composer5, 1110242972, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedPendingRequestsTab$4$1$1$2$4
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Button, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                            ComposerKt.sourceInformation($composer6, "C325@16811L78:AdminControllerScreens.kt#foq9o6");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(1110242972, $changed4, -1, "com.maw.ComposedPendingRequestsTab.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:325)");
                                            }
                                            TextKt.m2124Text4IGK_g("إلغاء", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, 3462, 0, 130994);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 805306374, 492);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    ComposerKt.sourceInformationMarkerEnd($composer5);
                                    $composer5.endReplaceableGroup();
                                    $composer5.endNode();
                                    $composer5.endReplaceableGroup();
                                    $composer5.endReplaceableGroup();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 9);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer4.skipToGroupEnd();
                    }
                }), $composer2, 390, 2);
            }
            $composer2.endReplaceableGroup();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedPendingRequestsTab.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i5) {
                    AdminControllerScreensKt.ComposedPendingRequestsTab(vm, list, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PendingProvider ComposedPendingRequestsTab$lambda$12(MutableState<PendingProvider> mutableState) {
        MutableState<PendingProvider> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedPendingRequestsTab$lambda$15(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    public static final void SelfieWithZoomLightbox(final String base64, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Intrinsics.checkNotNullParameter(base64, "base64");
        Composer $composer2 = $composer.startRestartGroup(-414517285);
        ComposerKt.sourceInformation($composer2, "C(SelfieWithZoomLightbox)338@17176L28:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(base64) ? 4 : 2;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 11) != 2 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-414517285, $dirty2, -1, "com.maw.SelfieWithZoomLightbox (AdminControllerScreens.kt:337)");
            }
            final Bitmap personalBitmap = MainActivityKt.rememberBase64Bitmap(base64, $composer2, $dirty2 & 14);
            if (personalBitmap == null) {
                $composer2.startReplaceableGroup(-2108964935);
                ComposerKt.sourceInformation($composer2, "422@21233L337");
                Modifier modifier$iv = BackgroundKt.m210backgroundbw27NRU$default(ClipKt.clip(SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(80)), 0.0f, 1, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(6))), ColorKt.Color(4279181861L), null, 2, null);
                Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
                $composer2.startReplaceableGroup(733328855);
                ComposerKt.sourceInformation($composer2, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer2, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                int $changed$iv$iv = (48 << 3) & 112;
                $composer2.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
                CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
                int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                if (!($composer2.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer2.startReusableNode();
                if ($composer2.getInserting()) {
                    $composer2.createNode(constructor);
                } else {
                    $composer2.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                    $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                    $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
                }
                function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                $composer2.startReplaceableGroup(2058660585);
                int i = ($changed$iv$iv$iv >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer2, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                int i2 = ((48 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer2, 284956656, "C430@21501L59:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("لا توجد صورة ❌", (Modifier) null, Color.INSTANCE.m3436getGray0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 3462, 0, 131058);
                ComposerKt.sourceInformationMarkerEnd($composer2);
                ComposerKt.sourceInformationMarkerEnd($composer2);
                $composer2.endReplaceableGroup();
                $composer2.endNode();
                $composer2.endReplaceableGroup();
                $composer2.endReplaceableGroup();
                $composer2.endReplaceableGroup();
            } else {
                $composer2.startReplaceableGroup(-2108968921);
                ComposerKt.sourceInformation($composer2, "340@17267L34,345@17438L23,341@17310L523,357@17887L36,358@17962L24,358@17936L3265");
                $composer2.startReplaceableGroup(-2108968891);
                ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object it$iv = $composer2.rememberedValue();
                if (it$iv == Composer.INSTANCE.getEmpty()) {
                    value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
                    $composer2.updateRememberedValue(value$iv);
                } else {
                    value$iv = it$iv;
                }
                final MutableState showLightbox$delegate = (MutableState) value$iv;
                $composer2.endReplaceableGroup();
                Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(80)), 0.0f, 1, null);
                $composer2.startReplaceableGroup(-2108968720);
                ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object it$iv2 = $composer2.rememberedValue();
                if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                    value$iv2 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$20(showLightbox$delegate, true);
                        }
                    };
                    $composer2.updateRememberedValue(value$iv2);
                } else {
                    value$iv2 = it$iv2;
                }
                $composer2.endReplaceableGroup();
                CardKt.Card(ClickableKt.m244clickableXHw0xAI$default(modifierFillMaxWidth$default, false, null, null, (Function0) value$iv2, 7, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(6)), null, null, BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L)), ComposableLambdaKt.composableLambda($composer2, -555259736, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.SelfieWithZoomLightbox.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                        invoke(columnScope, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(ColumnScope Card, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(Card, "$this$Card");
                        ComposerKt.sourceInformation($composer3, "C349@17592L231:AdminControllerScreens.kt#foq9o6");
                        if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-555259736, $changed2, -1, "com.maw.SelfieWithZoomLightbox.<anonymous> (AdminControllerScreens.kt:349)");
                            }
                            ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(personalBitmap), "Photo Preview", SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, ContentScale.INSTANCE.getCrop(), 0.0f, null, 0, $composer3, 25016, 232);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        $composer3.skipToGroupEnd();
                    }
                }), $composer2, 221184, 12);
                if (SelfieWithZoomLightbox$lambda$19(showLightbox$delegate)) {
                    $composer2.startReplaceableGroup(-2108968271);
                    ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv3 = $composer2.rememberedValue();
                    if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                        value$iv3 = PrimitiveSnapshotStateKt.mutableFloatStateOf(1.0f);
                        $composer2.updateRememberedValue(value$iv3);
                    } else {
                        value$iv3 = it$iv3;
                    }
                    final MutableFloatState scale$delegate = (MutableFloatState) value$iv3;
                    $composer2.endReplaceableGroup();
                    $composer2.startReplaceableGroup(-2108968196);
                    ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv4 = $composer2.rememberedValue();
                    if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                        value$iv4 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$3$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$20(showLightbox$delegate, false);
                            }
                        };
                        $composer2.updateRememberedValue(value$iv4);
                    } else {
                        value$iv4 = it$iv4;
                    }
                    $composer2.endReplaceableGroup();
                    AndroidDialog_androidKt.Dialog((Function0) value$iv4, null, ComposableLambdaKt.composableLambda($composer2, -598986392, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.SelfieWithZoomLightbox.4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C359@18006L3181:AdminControllerScreens.kt#foq9o6");
                            if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-598986392, $changed2, -1, "com.maw.SelfieWithZoomLightbox.<anonymous> (AdminControllerScreens.kt:359)");
                                }
                                Modifier modifierFillMaxHeight = SizeKt.fillMaxHeight(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.75f);
                                RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12));
                                BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU());
                                final Bitmap bitmap = personalBitmap;
                                final MutableFloatState mutableFloatState = scale$delegate;
                                final MutableState<Boolean> mutableState = showLightbox$delegate;
                                CardKt.Card(modifierFillMaxHeight, roundedCornerShapeM831RoundedCornerShape0680j_4, null, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer3, 1659899866, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.SelfieWithZoomLightbox.4.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                        invoke(columnScope, composer, num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(ColumnScope Card, Composer $composer4, int $changed3) {
                                        Function0<ComposeUiNode> function0;
                                        Function0<ComposeUiNode> function02;
                                        Object value$iv5;
                                        Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                        ComposerKt.sourceInformation($composer4, "C366@18303L2866:AdminControllerScreens.kt#foq9o6");
                                        if (($changed3 & 81) == 16 && $composer4.getSkipping()) {
                                            $composer4.skipToGroupEnd();
                                            return;
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(1659899866, $changed3, -1, "com.maw.SelfieWithZoomLightbox.<anonymous>.<anonymous> (AdminControllerScreens.kt:366)");
                                        }
                                        Modifier modifier$iv2 = BackgroundKt.m210backgroundbw27NRU$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), Color.INSTANCE.m3432getBlack0d7_KjU(), null, 2, null);
                                        Bitmap bitmap2 = bitmap;
                                        final MutableFloatState mutableFloatState2 = mutableFloatState;
                                        final MutableState<Boolean> mutableState2 = mutableState;
                                        $composer4.startReplaceableGroup(733328855);
                                        ComposerKt.sourceInformation($composer4, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                                        Alignment contentAlignment$iv2 = Alignment.INSTANCE.getTopStart();
                                        MeasurePolicy measurePolicy$iv2 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv2, false, $composer4, ((6 >> 3) & 14) | ((6 >> 3) & 112));
                                        int $changed$iv$iv2 = (6 << 3) & 112;
                                        $composer4.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                                        CompositionLocalMap localMap$iv$iv2 = $composer4.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                                        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                                        if (!($composer4.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer4.startReusableNode();
                                        if ($composer4.getInserting()) {
                                            function0 = constructor2;
                                            $composer4.createNode(function0);
                                        } else {
                                            function0 = constructor2;
                                            $composer4.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer4);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                                            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                                            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                                        }
                                        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                                        $composer4.startReplaceableGroup(2058660585);
                                        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer4, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                                        int i4 = ((6 >> 6) & 112) | 6;
                                        BoxScope $this$invoke_u24lambda_u245 = BoxScopeInstance.INSTANCE;
                                        ComposerKt.sourceInformationMarkerStart($composer4, 1356529037, "C367@18392L518,381@19037L24,380@18987L474,391@19571L1576:AdminControllerScreens.kt#foq9o6");
                                        ImageKt.m266Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(bitmap2), "Enlarged Zoomable Photo", GraphicsLayerModifierKt.m3568graphicsLayerAp8cVGQ$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$23(mutableFloatState2), AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$23(mutableFloatState2), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, null, false, null, 0L, 0L, 0, 131068, null), null, ContentScale.INSTANCE.getFit(), 0.0f, null, 0, $composer4, 24632, 232);
                                        $composer4.startReplaceableGroup(1356529682);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        Object value$iv6 = $composer4.rememberedValue();
                                        if (value$iv6 == Composer.INSTANCE.getEmpty()) {
                                            value$iv6 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$4$1$1$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$20(mutableState2, false);
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv6);
                                        }
                                        $composer4.endReplaceableGroup();
                                        Modifier modifierM562padding3ABfNKs = PaddingKt.m562padding3ABfNKs($this$invoke_u24lambda_u245.align(Modifier.INSTANCE, Alignment.INSTANCE.getTopEnd()), Dp.m5733constructorimpl(8));
                                        long jM3432getBlack0d7_KjU = Color.INSTANCE.m3432getBlack0d7_KjU();
                                        IconButtonKt.IconButton((Function0) value$iv6, BackgroundKt.m209backgroundbw27NRU(modifierM562padding3ABfNKs, Color.m3404copywmQWz5c(jM3432getBlack0d7_KjU, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM3432getBlack0d7_KjU) : 0.6f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM3432getBlack0d7_KjU) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM3432getBlack0d7_KjU) : 0.0f), RoundedCornerShapeKt.getCircleShape()), false, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6283getLambda2$app_debug(), $composer4, 196614, 28);
                                        Modifier modifierClip = ClipKt.clip(PaddingKt.m562padding3ABfNKs($this$invoke_u24lambda_u245.align(Modifier.INSTANCE, Alignment.INSTANCE.getBottomCenter()), Dp.m5733constructorimpl(12)), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(20)));
                                        long jM3432getBlack0d7_KjU2 = Color.INSTANCE.m3432getBlack0d7_KjU();
                                        Modifier modifierM563paddingVpY3zN4 = PaddingKt.m563paddingVpY3zN4(BackgroundKt.m210backgroundbw27NRU$default(modifierClip, Color.m3404copywmQWz5c(jM3432getBlack0d7_KjU2, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM3432getBlack0d7_KjU2) : 0.7f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM3432getBlack0d7_KjU2) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM3432getBlack0d7_KjU2) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM3432getBlack0d7_KjU2) : 0.0f), null, 2, null), Dp.m5733constructorimpl(14), Dp.m5733constructorimpl(6));
                                        Alignment.Vertical centerVertically = Alignment.INSTANCE.getCenterVertically();
                                        Arrangement.HorizontalOrVertical horizontalOrVerticalM471spacedBy0680j_4 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(14));
                                        $composer4.startReplaceableGroup(693286680);
                                        ComposerKt.sourceInformation($composer4, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                        MeasurePolicy measurePolicy$iv3 = RowKt.rowMeasurePolicy(horizontalOrVerticalM471spacedBy0680j_4, centerVertically, $composer4, ((432 >> 3) & 14) | ((432 >> 3) & 112));
                                        int $changed$iv$iv3 = (432 << 3) & 112;
                                        $composer4.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                                        CompositionLocalMap localMap$iv$iv3 = $composer4.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifierM563paddingVpY3zN4);
                                        int $i$f$Layout = $changed$iv$iv3 << 9;
                                        int $changed$iv$iv$iv3 = ($i$f$Layout & 7168) | 6;
                                        if (!($composer4.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer4.startReusableNode();
                                        if ($composer4.getInserting()) {
                                            function02 = constructor3;
                                            $composer4.createNode(function02);
                                        } else {
                                            function02 = constructor3;
                                            $composer4.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer4);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                                            $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                                            $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                                        }
                                        function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                                        $composer4.startReplaceableGroup(2058660585);
                                        int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer4, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                        int i6 = ((432 >> 6) & 112) | 6;
                                        ComposerKt.sourceInformationMarkerStart($composer4, -653385955, "C401@20188L44,401@20167L214,404@20410L276,410@20736L48,410@20715L222,413@20987L14,413@20966L155:AdminControllerScreens.kt#foq9o6");
                                        $composer4.startReplaceableGroup(-653385934);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        Object value$iv7 = $composer4.rememberedValue();
                                        if (value$iv7 == Composer.INSTANCE.getEmpty()) {
                                            value$iv7 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$4$1$1$2$1$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    mutableFloatState2.setFloatValue(RangesKt.coerceAtMost(AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$23(mutableFloatState2) + 0.25f, 3.0f));
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv7);
                                        }
                                        $composer4.endReplaceableGroup();
                                        IconButtonKt.IconButton((Function0) value$iv7, null, false, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6294getLambda3$app_debug(), $composer4, 196614, 30);
                                        TextKt.m2124Text4IGK_g("تكبير: " + ((int) (AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$23(mutableFloatState2) * 100)) + "%", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200064, 0, 131026);
                                        $composer4.startReplaceableGroup(-653385386);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        Object value$iv8 = $composer4.rememberedValue();
                                        if (value$iv8 == Composer.INSTANCE.getEmpty()) {
                                            value$iv8 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$4$1$1$2$2$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    mutableFloatState2.setFloatValue(RangesKt.coerceAtLeast(AdminControllerScreensKt.SelfieWithZoomLightbox$lambda$23(mutableFloatState2) - 0.25f, 0.75f));
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv8);
                                        }
                                        $composer4.endReplaceableGroup();
                                        IconButtonKt.IconButton((Function0) value$iv8, null, false, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6305getLambda4$app_debug(), $composer4, 196614, 30);
                                        $composer4.startReplaceableGroup(-653385135);
                                        ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        Object it$iv5 = $composer4.rememberedValue();
                                        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                                            value$iv5 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$SelfieWithZoomLightbox$4$1$1$2$3$1
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    mutableFloatState2.setFloatValue(1.0f);
                                                }
                                            };
                                            $composer4.updateRememberedValue(value$iv5);
                                        } else {
                                            value$iv5 = it$iv5;
                                        }
                                        $composer4.endReplaceableGroup();
                                        ButtonKt.TextButton((Function0) value$iv5, null, false, null, null, null, null, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6316getLambda5$app_debug(), $composer4, 805306374, 510);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        $composer4.endReplaceableGroup();
                                        $composer4.endNode();
                                        $composer4.endReplaceableGroup();
                                        $composer4.endReplaceableGroup();
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        $composer4.endReplaceableGroup();
                                        $composer4.endNode();
                                        $composer4.endReplaceableGroup();
                                        $composer4.endReplaceableGroup();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }), $composer3, 196614, 12);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer3.skipToGroupEnd();
                        }
                    }), $composer2, 390, 2);
                }
                $composer2.endReplaceableGroup();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.SelfieWithZoomLightbox.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    AdminControllerScreensKt.SelfieWithZoomLightbox(base64, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    private static final boolean SelfieWithZoomLightbox$lambda$19(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void SelfieWithZoomLightbox$lambda$20(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float SelfieWithZoomLightbox$lambda$23(MutableFloatState $scale$delegate) {
        MutableFloatState $this$getValue$iv = $scale$delegate;
        return $this$getValue$iv.getFloatValue();
    }

    public static final void ComposedManualAddProviderTab(final MainViewModel vm, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Object value$iv6;
        Object value$iv7;
        Object value$iv8;
        Composer $composer2;
        Intrinsics.checkNotNullParameter(vm, "vm");
        Composer $composer3 = $composer.startRestartGroup(-1750029806);
        ComposerKt.sourceInformation($composer3, "C(ComposedManualAddProviderTab)440@21939L7,441@21963L31,442@22012L31,443@22060L31,444@22115L31,445@22170L31,446@22226L31,447@22287L31,449@22361L29,450@22424L29,453@22573L197,453@22538L232,458@22800L413,468@23219L6375:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(vm) ? 4 : 2;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 11) != 2 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1750029806, $dirty2, -1, "com.maw.ComposedManualAddProviderTab (AdminControllerScreens.kt:439)");
            }
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer3, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer3.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            final Context context = (Context) objConsume;
            $composer3.startReplaceableGroup(1002052216);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv = $composer3.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState name$delegate = (MutableState) value$iv;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052265);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv2 = $composer3.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableState phone$delegate = (MutableState) value$iv2;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052313);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv3 = $composer3.rememberedValue();
            if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv3);
            } else {
                value$iv3 = it$iv3;
            }
            final MutableState area$delegate = (MutableState) value$iv3;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052368);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv4 = $composer3.rememberedValue();
            if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv4);
            } else {
                value$iv4 = it$iv4;
            }
            final MutableState description$delegate = (MutableState) value$iv4;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052423);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv5 = $composer3.rememberedValue();
            if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv5);
            } else {
                value$iv5 = it$iv5;
            }
            final MutableState selectedCat$delegate = (MutableState) value$iv5;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052479);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv6 = $composer3.rememberedValue();
            if (it$iv6 == Composer.INSTANCE.getEmpty()) {
                value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv6);
            } else {
                value$iv6 = it$iv6;
            }
            final MutableState selectedCity$delegate = (MutableState) value$iv6;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1002052540);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv7 = $composer3.rememberedValue();
            if (it$iv7 == Composer.INSTANCE.getEmpty()) {
                value$iv7 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv7);
            } else {
                value$iv7 = it$iv7;
            }
            final MutableState manualBase64Image$delegate = (MutableState) value$iv7;
            $composer3.endReplaceableGroup();
            final State categories$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getCategoriesState(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer3, 8, 7);
            final State cities$delegate = FlowExtKt.collectAsStateWithLifecycle(vm.getCitiesState(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer3, 8, 7);
            final FontFamily currentFont = MainActivityKt.resolveAppFontFamily(vm.getSettings().getValue().getSelectedFontName());
            List<Category> listComposedManualAddProviderTab$lambda$48 = ComposedManualAddProviderTab$lambda$48(categories$delegate);
            List<City> listComposedManualAddProviderTab$lambda$49 = ComposedManualAddProviderTab$lambda$49(cities$delegate);
            $composer3.startReplaceableGroup(1002052826);
            ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
            boolean invalid$iv = $composer3.changed(categories$delegate) | $composer3.changed(cities$delegate);
            Object it$iv8 = $composer3.rememberedValue();
            if (invalid$iv || it$iv8 == Composer.INSTANCE.getEmpty()) {
                value$iv8 = new AdminControllerScreensKt$ComposedManualAddProviderTab$1$1(selectedCat$delegate, categories$delegate, selectedCity$delegate, cities$delegate, null);
                $composer3.updateRememberedValue(value$iv8);
            } else {
                value$iv8 = it$iv8;
            }
            $composer3.endReplaceableGroup();
            EffectsKt.LaunchedEffect(listComposedManualAddProviderTab$lambda$48, listComposedManualAddProviderTab$lambda$49, (Function2) value$iv8, $composer3, 584);
            final ManagedActivityResultLauncher manualImagePicker = ActivityResultRegistryKt.rememberLauncherForActivityResult(new ActivityResultContracts.GetContent(), new Function1<Uri, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$manualImagePicker$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Uri uri) {
                    invoke2(uri);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Uri uri) {
                    if (uri != null) {
                        Context context2 = context;
                        MutableState<String> mutableState = manualBase64Image$delegate;
                        String compressed = MainActivityKt.compressImageBase64(context2, uri, 300, 300, 70);
                        mutableState.setValue(compressed);
                        Toast.makeText(context2, "تم ضغط الصورة الشخصية وتخزينها يدوياً بنجاح! 🖼️", 0).show();
                    }
                }
            }, $composer3, 8);
            $composer2 = $composer3;
            ScaffoldKt.m1779ScaffoldTvnljyQ(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, null, null, null, 0, Color.INSTANCE.m3441getTransparent0d7_KjU(), 0L, null, ComposableLambdaKt.composableLambda($composer2, 1459567843, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedManualAddProviderTab.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                    invoke(paddingValues, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(PaddingValues paddingValues, Composer $composer4, int $changed2) {
                    Function0<ComposeUiNode> function0;
                    final MutableState<String> mutableState;
                    Object value$iv9;
                    Object value$iv10;
                    Object value$iv11;
                    Object value$iv12;
                    Object value$iv13;
                    Intrinsics.checkNotNullParameter(paddingValues, "paddingValues");
                    ComposerKt.sourceInformation($composer4, "C476@23491L21,472@23348L6240:AdminControllerScreens.kt#foq9o6");
                    int $dirty3 = $changed2;
                    if (($changed2 & 14) == 0) {
                        $dirty3 |= $composer4.changed(paddingValues) ? 4 : 2;
                    }
                    if (($dirty3 & 91) == 18 && $composer4.getSkipping()) {
                        $composer4.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(1459567843, $dirty3, -1, "com.maw.ComposedManualAddProviderTab.<anonymous> (AdminControllerScreens.kt:472)");
                    }
                    Modifier modifier$iv = ScrollKt.verticalScroll$default(SizeKt.fillMaxSize$default(PaddingKt.padding(Modifier.INSTANCE, paddingValues), 0.0f, 1, null), ScrollKt.rememberScrollState(0, $composer4, 0, 1), false, null, false, 14, null);
                    Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
                    final FontFamily fontFamily = currentFont;
                    final State<List<Category>> state = categories$delegate;
                    final State<List<City>> state2 = cities$delegate;
                    MutableState<String> mutableState2 = name$delegate;
                    final MutableState<String> mutableState3 = phone$delegate;
                    final MutableState<String> mutableState4 = area$delegate;
                    final MutableState<String> mutableState5 = description$delegate;
                    final MutableState<String> mutableState6 = selectedCat$delegate;
                    final MutableState<String> mutableState7 = selectedCity$delegate;
                    final ManagedActivityResultLauncher<String, Uri> managedActivityResultLauncher = manualImagePicker;
                    final MutableState<String> mutableState8 = manualBase64Image$delegate;
                    final Context context2 = context;
                    final MainViewModel mainViewModel = vm;
                    $composer4.startReplaceableGroup(-483455358);
                    ComposerKt.sourceInformation($composer4, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                    Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
                    MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer4, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                    int $changed$iv$iv = (48 << 3) & 112;
                    $composer4.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                    CompositionLocalMap localMap$iv$iv = $composer4.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
                    int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                    if (!($composer4.getApplier() instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                    }
                    $composer4.startReusableNode();
                    if ($composer4.getInserting()) {
                        function0 = constructor;
                        $composer4.createNode(function0);
                    } else {
                        function0 = constructor;
                        $composer4.useNode();
                    }
                    Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer4);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                        $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                        $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
                    }
                    function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                    $composer4.startReplaceableGroup(2058660585);
                    int i = ($changed$iv$iv$iv >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer4, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    int i2 = ((48 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer4, -1397685088, "C479@23601L263,492@24141L72,489@23959L13,487@23878L414,501@24576L72,498@24388L14,496@24306L356,509@24945L72,506@24757L13,504@24676L420,518@25404L72,515@25198L20,513@25110L445,523@25609L128,524@25810L687,524@25750L747,540@26547L123,541@26743L689,541@26683L749,559@27596L51,557@27481L482,592@29296L48,565@27977L1601:AdminControllerScreens.kt#foq9o6");
                    TextKt.m2124Text4IGK_g("➕ تفعيل مباشر لعضو مهني وحرفي بدون شروط مراجعة وتحكم مسبق:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 199686, 0, 130962);
                    String strComposedManualAddProviderTab$lambda$28 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$28(mutableState2);
                    Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    TextFieldColors textFieldColorsM1726colors0hiis_0 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer4, 54, 0, 0, 0, 3072, 2147483644, 4095);
                    TextStyle textStyle = new TextStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, fontFamily, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777183, (DefaultConstructorMarker) null);
                    $composer4.startReplaceableGroup(-1397684730);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv9 = $composer4.rememberedValue();
                    if (it$iv9 == Composer.INSTANCE.getEmpty()) {
                        mutableState = mutableState2;
                        value$iv9 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState.setValue(it);
                            }
                        };
                        $composer4.updateRememberedValue(value$iv9);
                    } else {
                        mutableState = mutableState2;
                        value$iv9 = it$iv9;
                    }
                    $composer4.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedManualAddProviderTab$lambda$28, (Function1<? super String, Unit>) value$iv9, modifierFillMaxWidth$default, false, false, textStyle, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6320getLambda6$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_0, $composer4, 1573296, 0, 0, 4194200);
                    String strComposedManualAddProviderTab$lambda$31 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$31(mutableState3);
                    final MutableState<String> mutableState9 = mutableState;
                    Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    TextFieldColors textFieldColorsM1726colors0hiis_02 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer4, 54, 0, 0, 0, 3072, 2147483644, 4095);
                    $composer4.startReplaceableGroup(-1397684301);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object value$iv14 = $composer4.rememberedValue();
                    if (value$iv14 == Composer.INSTANCE.getEmpty()) {
                        value$iv14 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState3.setValue(it);
                            }
                        };
                        $composer4.updateRememberedValue(value$iv14);
                    }
                    $composer4.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedManualAddProviderTab$lambda$31, (Function1<? super String, Unit>) value$iv14, modifierFillMaxWidth$default2, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6321getLambda7$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_02, $composer4, 1573296, 0, 0, 4194232);
                    String strComposedManualAddProviderTab$lambda$34 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$34(mutableState4);
                    Modifier modifierFillMaxWidth$default3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    TextFieldColors textFieldColorsM1726colors0hiis_03 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer4, 54, 0, 0, 0, 3072, 2147483644, 4095);
                    TextStyle textStyle2 = new TextStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, fontFamily, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777183, (DefaultConstructorMarker) null);
                    $composer4.startReplaceableGroup(-1397683932);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv10 = $composer4.rememberedValue();
                    if (it$iv10 == Composer.INSTANCE.getEmpty()) {
                        value$iv10 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$3$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState4.setValue(it);
                            }
                        };
                        $composer4.updateRememberedValue(value$iv10);
                    } else {
                        value$iv10 = it$iv10;
                    }
                    $composer4.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedManualAddProviderTab$lambda$34, (Function1<? super String, Unit>) value$iv10, modifierFillMaxWidth$default3, false, false, textStyle2, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6322getLambda8$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_03, $composer4, 1573296, 0, 0, 4194200);
                    String strComposedManualAddProviderTab$lambda$37 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$37(mutableState5);
                    Modifier modifierFillMaxWidth$default4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    TextFieldColors textFieldColorsM1726colors0hiis_04 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer4, 54, 0, 0, 0, 3072, 2147483644, 4095);
                    TextStyle textStyle3 = new TextStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, fontFamily, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777183, (DefaultConstructorMarker) null);
                    $composer4.startReplaceableGroup(-1397683491);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv11 = $composer4.rememberedValue();
                    if (it$iv11 == Composer.INSTANCE.getEmpty()) {
                        value$iv11 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$4$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState5.setValue(it);
                            }
                        };
                        $composer4.updateRememberedValue(value$iv11);
                    } else {
                        value$iv11 = it$iv11;
                    }
                    $composer4.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedManualAddProviderTab$lambda$37, (Function1<? super String, Unit>) value$iv11, modifierFillMaxWidth$default4, false, false, textStyle3, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6323getLambda9$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_04, $composer4, 1573296, 0, 0, 4194200);
                    TextKt.m2124Text4IGK_g("اختر مظلة التخصص والنشاط:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200070, 0, 130962);
                    Arrangement.HorizontalOrVertical horizontalOrVerticalM471spacedBy0680j_4 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(6));
                    $composer4.startReplaceableGroup(-1397682879);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    boolean invalid$iv2 = $composer4.changed(state);
                    Object it$iv12 = $composer4.rememberedValue();
                    if (invalid$iv2 || it$iv12 == Composer.INSTANCE.getEmpty()) {
                        value$iv12 = new Function1<LazyListScope, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$5$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                                invoke2(lazyListScope);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(LazyListScope LazyRow) {
                                Intrinsics.checkNotNullParameter(LazyRow, "$this$LazyRow");
                                final List items$iv = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$48(state);
                                final MutableState<String> mutableState10 = mutableState6;
                                final Function1 contentType$iv = new Function1() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$5$1$invoke$$inlined$items$default$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                        return invoke((Category) p1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Void invoke(Category category) {
                                        return null;
                                    }
                                };
                                LazyRow.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$5$1$invoke$$inlined$items$default$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                                        return invoke(num.intValue());
                                    }

                                    public final Object invoke(int index) {
                                        return contentType$iv.invoke(items$iv.get(index));
                                    }
                                }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$5$1$invoke$$inlined$items$default$4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(4);
                                    }

                                    @Override // kotlin.jvm.functions.Function4
                                    public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                                        invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(LazyItemScope $this$items, int it, Composer $composer5, int $changed3) {
                                        Object value$iv15;
                                        Function0<ComposeUiNode> function02;
                                        ComposerKt.sourceInformation($composer5, "C148@6730L22:LazyDsl.kt#428nma");
                                        int $dirty4 = $changed3;
                                        if (($changed3 & 14) == 0) {
                                            $dirty4 |= $composer5.changed($this$items) ? 4 : 2;
                                        }
                                        if (($changed3 & 112) == 0) {
                                            $dirty4 |= $composer5.changed(it) ? 32 : 16;
                                        }
                                        if (($dirty4 & 731) == 146 && $composer5.getSkipping()) {
                                            $composer5.skipToGroupEnd();
                                            return;
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-632812321, $dirty4, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                                        }
                                        int $changed4 = $dirty4 & 14;
                                        final Category cat = (Category) items$iv.get(it);
                                        ComposerKt.sourceInformationMarkerStart($composer5, -1469346843, "C*531@26179L24,527@25934L531:AdminControllerScreens.kt#foq9o6");
                                        boolean isSelected = Intrinsics.areEqual(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$40(mutableState10), cat.getId());
                                        Modifier modifierM210backgroundbw27NRU$default = BackgroundKt.m210backgroundbw27NRU$default(ClipKt.clip(Modifier.INSTANCE, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8))), isSelected ? AppTheme.INSTANCE.m6261getAccentGold0d7_KjU() : Color.INSTANCE.m3435getDarkGray0d7_KjU(), null, 2, null);
                                        $composer5.startReplaceableGroup(-1469346539);
                                        ComposerKt.sourceInformation($composer5, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        boolean invalid$iv3 = ((($changed4 & 112) ^ 48) > 32 && $composer5.changed(cat)) || ($changed4 & 48) == 32;
                                        Object it$iv13 = $composer5.rememberedValue();
                                        if (invalid$iv3 || it$iv13 == Composer.INSTANCE.getEmpty()) {
                                            final MutableState mutableState11 = mutableState10;
                                            value$iv15 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$5$1$1$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    mutableState11.setValue(cat.getId());
                                                }
                                            };
                                            $composer5.updateRememberedValue(value$iv15);
                                        } else {
                                            value$iv15 = it$iv13;
                                        }
                                        $composer5.endReplaceableGroup();
                                        Modifier modifier$iv2 = PaddingKt.m563paddingVpY3zN4(ClickableKt.m244clickableXHw0xAI$default(modifierM210backgroundbw27NRU$default, false, null, null, (Function0) value$iv15, 7, null), Dp.m5733constructorimpl(10), Dp.m5733constructorimpl(6));
                                        $composer5.startReplaceableGroup(733328855);
                                        ComposerKt.sourceInformation($composer5, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                                        Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
                                        MeasurePolicy measurePolicy$iv2 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer5, ((0 >> 3) & 14) | ((0 >> 3) & 112));
                                        int $changed$iv$iv2 = (0 << 3) & 112;
                                        $composer5.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                        CompositionLocalMap localMap$iv$iv2 = $composer5.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                                        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                                        if (!($composer5.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer5.startReusableNode();
                                        if ($composer5.getInserting()) {
                                            function02 = constructor2;
                                            $composer5.createNode(function02);
                                        } else {
                                            function02 = constructor2;
                                            $composer5.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer5);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                                            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                                            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                                        }
                                        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                                        $composer5.startReplaceableGroup(2058660585);
                                        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer5, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        int i4 = ((0 >> 6) & 112) | 6;
                                        ComposerKt.sourceInformationMarkerStart($composer5, 861201609, "C534@26326L117:AdminControllerScreens.kt#foq9o6");
                                        String nameAr = cat.getNameAr();
                                        Color.Companion companion = Color.INSTANCE;
                                        TextKt.m2124Text4IGK_g(nameAr, (Modifier) null, isSelected ? companion.m3432getBlack0d7_KjU() : companion.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199680, 0, 131026);
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        $composer5.endReplaceableGroup();
                                        $composer5.endNode();
                                        $composer5.endReplaceableGroup();
                                        $composer5.endReplaceableGroup();
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }));
                            }
                        };
                        $composer4.updateRememberedValue(value$iv12);
                    } else {
                        value$iv12 = it$iv12;
                    }
                    $composer4.endReplaceableGroup();
                    LazyDslKt.LazyRow(null, null, null, false, horizontalOrVerticalM471spacedBy0680j_4, null, null, false, (Function1) value$iv12, $composer4, 24576, 239);
                    TextKt.m2124Text4IGK_g("اختر مدينة المزاولة:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200070, 0, 130962);
                    Arrangement.HorizontalOrVertical horizontalOrVerticalM471spacedBy0680j_42 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(6));
                    $composer4.startReplaceableGroup(-1397681946);
                    ComposerKt.sourceInformation($composer4, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    boolean invalid$iv3 = $composer4.changed(state2);
                    Object it$iv13 = $composer4.rememberedValue();
                    if (invalid$iv3 || it$iv13 == Composer.INSTANCE.getEmpty()) {
                        value$iv13 = new Function1<LazyListScope, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$6$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                                invoke2(lazyListScope);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(LazyListScope LazyRow) {
                                Intrinsics.checkNotNullParameter(LazyRow, "$this$LazyRow");
                                final List items$iv = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$49(state2);
                                final MutableState<String> mutableState10 = mutableState7;
                                final Function1 contentType$iv = new Function1() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$6$1$invoke$$inlined$items$default$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                        return invoke((City) p1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Void invoke(City city) {
                                        return null;
                                    }
                                };
                                LazyRow.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$6$1$invoke$$inlined$items$default$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                                        return invoke(num.intValue());
                                    }

                                    public final Object invoke(int index) {
                                        return contentType$iv.invoke(items$iv.get(index));
                                    }
                                }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$6$1$invoke$$inlined$items$default$4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(4);
                                    }

                                    @Override // kotlin.jvm.functions.Function4
                                    public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                                        invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(LazyItemScope $this$items, int it, Composer $composer5, int $changed3) {
                                        Object value$iv15;
                                        Function0<ComposeUiNode> function02;
                                        ComposerKt.sourceInformation($composer5, "C148@6730L22:LazyDsl.kt#428nma");
                                        int $dirty4 = $changed3;
                                        if (($changed3 & 14) == 0) {
                                            $dirty4 |= $composer5.changed($this$items) ? 4 : 2;
                                        }
                                        if (($changed3 & 112) == 0) {
                                            $dirty4 |= $composer5.changed(it) ? 32 : 16;
                                        }
                                        if (($dirty4 & 731) == 146 && $composer5.getSkipping()) {
                                            $composer5.skipToGroupEnd();
                                            return;
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-632812321, $dirty4, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                                        }
                                        int $changed4 = $dirty4 & 14;
                                        final City city = (City) items$iv.get(it);
                                        ComposerKt.sourceInformationMarkerStart($composer5, -1469345913, "C*548@27111L26,544@26866L534:AdminControllerScreens.kt#foq9o6");
                                        boolean isSelected = Intrinsics.areEqual(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$43(mutableState10), city.getId());
                                        Modifier modifierM210backgroundbw27NRU$default = BackgroundKt.m210backgroundbw27NRU$default(ClipKt.clip(Modifier.INSTANCE, RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8))), isSelected ? AppTheme.INSTANCE.m6261getAccentGold0d7_KjU() : Color.INSTANCE.m3435getDarkGray0d7_KjU(), null, 2, null);
                                        $composer5.startReplaceableGroup(-1469345607);
                                        ComposerKt.sourceInformation($composer5, "CC(remember):AdminControllerScreens.kt#9igjgp");
                                        boolean invalid$iv4 = ((($changed4 & 112) ^ 48) > 32 && $composer5.changed(city)) || ($changed4 & 48) == 32;
                                        Object it$iv14 = $composer5.rememberedValue();
                                        if (invalid$iv4 || it$iv14 == Composer.INSTANCE.getEmpty()) {
                                            final MutableState mutableState11 = mutableState10;
                                            value$iv15 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$6$1$1$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public /* bridge */ /* synthetic */ Unit invoke() {
                                                    invoke2();
                                                    return Unit.INSTANCE;
                                                }

                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2() {
                                                    mutableState11.setValue(city.getId());
                                                }
                                            };
                                            $composer5.updateRememberedValue(value$iv15);
                                        } else {
                                            value$iv15 = it$iv14;
                                        }
                                        $composer5.endReplaceableGroup();
                                        Modifier modifier$iv2 = PaddingKt.m563paddingVpY3zN4(ClickableKt.m244clickableXHw0xAI$default(modifierM210backgroundbw27NRU$default, false, null, null, (Function0) value$iv15, 7, null), Dp.m5733constructorimpl(10), Dp.m5733constructorimpl(6));
                                        $composer5.startReplaceableGroup(733328855);
                                        ComposerKt.sourceInformation($composer5, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                                        Alignment contentAlignment$iv = Alignment.INSTANCE.getTopStart();
                                        MeasurePolicy measurePolicy$iv2 = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer5, ((0 >> 3) & 14) | ((0 >> 3) & 112));
                                        int $changed$iv$iv2 = (0 << 3) & 112;
                                        $composer5.startReplaceableGroup(-1323940314);
                                        ComposerKt.sourceInformation($composer5, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer5, 0);
                                        CompositionLocalMap localMap$iv$iv2 = $composer5.getCurrentCompositionLocalMap();
                                        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                                        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                                        if (!($composer5.getApplier() instanceof Applier)) {
                                            ComposablesKt.invalidApplier();
                                        }
                                        $composer5.startReusableNode();
                                        if ($composer5.getInserting()) {
                                            function02 = constructor2;
                                            $composer5.createNode(function02);
                                        } else {
                                            function02 = constructor2;
                                            $composer5.useNode();
                                        }
                                        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer5);
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                                            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                                            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                                        }
                                        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer5)), $composer5, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                                        $composer5.startReplaceableGroup(2058660585);
                                        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                                        ComposerKt.sourceInformationMarkerStart($composer5, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        int i4 = ((0 >> 6) & 112) | 6;
                                        ComposerKt.sourceInformationMarkerStart($composer5, 861202543, "C551@27260L118:AdminControllerScreens.kt#foq9o6");
                                        String nameAr = city.getNameAr();
                                        Color.Companion companion = Color.INSTANCE;
                                        TextKt.m2124Text4IGK_g(nameAr, (Modifier) null, isSelected ? companion.m3432getBlack0d7_KjU() : companion.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 199680, 0, 131026);
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        $composer5.endReplaceableGroup();
                                        $composer5.endNode();
                                        $composer5.endReplaceableGroup();
                                        $composer5.endReplaceableGroup();
                                        ComposerKt.sourceInformationMarkerEnd($composer5);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }));
                            }
                        };
                        $composer4.updateRememberedValue(value$iv13);
                    } else {
                        value$iv13 = it$iv13;
                    }
                    $composer4.endReplaceableGroup();
                    LazyDslKt.LazyRow(null, null, null, false, horizontalOrVerticalM471spacedBy0680j_42, null, null, false, (Function1) value$iv13, $composer4, 24576, 239);
                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            managedActivityResultLauncher.launch("image/*");
                        }
                    }, BorderKt.m221borderxT4_qwU(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(8))), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer4, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableLambdaKt.composableLambda($composer4, -1663784631, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$8
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                            invoke(rowScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(RowScope Button, Composer $composer5, int $changed3) {
                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                            ComposerKt.sourceInformation($composer5, "C562@27790L159:AdminControllerScreens.kt#foq9o6");
                            if (($changed3 & 81) == 16 && $composer5.getSkipping()) {
                                $composer5.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-1663784631, $changed3, -1, "com.maw.ComposedManualAddProviderTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:562)");
                            }
                            TextKt.m2124Text4IGK_g(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$46(mutableState8).length() == 0 ? "🖼️ رفع صورة رمزية اختيارية للملف" : "تعديل الصورة الشخصية المحددة الحالية ✅", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 3456, 0, 131058);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }), $composer4, 805306368, 492);
                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$9
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            if (StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$28(mutableState9)) || StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$31(mutableState3)) || StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$40(mutableState6)) || StringsKt.isBlank(AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$43(mutableState7))) {
                                Toast.makeText(context2, "يرجى تعبئة الاسم والهاتف واختيار الفئات لإضافة الفني بشكل سليم!", 1).show();
                                return;
                            }
                            String string = UUID.randomUUID().toString();
                            String strComposedManualAddProviderTab$lambda$282 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$28(mutableState9);
                            String strComposedManualAddProviderTab$lambda$312 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$31(mutableState3);
                            String strComposedManualAddProviderTab$lambda$342 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$34(mutableState4);
                            String strComposedManualAddProviderTab$lambda$372 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$37(mutableState5);
                            String strComposedManualAddProviderTab$lambda$40 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$40(mutableState6);
                            String strComposedManualAddProviderTab$lambda$43 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$43(mutableState7);
                            String strComposedManualAddProviderTab$lambda$46 = AdminControllerScreensKt.ComposedManualAddProviderTab$lambda$46(mutableState8);
                            Intrinsics.checkNotNull(string);
                            Provider newProvider = new Provider(string, strComposedManualAddProviderTab$lambda$282, strComposedManualAddProviderTab$lambda$40, strComposedManualAddProviderTab$lambda$43, strComposedManualAddProviderTab$lambda$312, strComposedManualAddProviderTab$lambda$372, strComposedManualAddProviderTab$lambda$342, 0.0d, true, false, false, false, (String) null, strComposedManualAddProviderTab$lambda$46, (List) null, 0, false, false, 0, (String) null, (String) null, (String) null, 4185728, (DefaultConstructorMarker) null);
                            mainViewModel.addProviderManual(newProvider, "الأدمن");
                            Toast.makeText(context2, "تم حفظ العضو وتفعيل ملفه في الديل بكافة الأجهزة بنجاح فوري! 🎉", 0).show();
                            mutableState9.setValue("");
                            mutableState3.setValue("");
                            mutableState4.setValue("");
                            mutableState5.setValue("");
                            mutableState8.setValue("");
                        }
                    }, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4281236786L), 0L, 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 6, 14), null, null, null, null, ComposableLambdaKt.composableLambda($composer4, -817177600, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedManualAddProviderTab$2$1$10
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                            invoke(rowScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(RowScope Button, Composer $composer5, int $changed3) {
                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                            ComposerKt.sourceInformation($composer5, "C595@29429L135:AdminControllerScreens.kt#foq9o6");
                            if (($changed3 & 81) == 16 && $composer5.getSkipping()) {
                                $composer5.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-817177600, $changed3, -1, "com.maw.ComposedManualAddProviderTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:595)");
                            }
                            TextKt.m2124Text4IGK_g("تأكيد التثبيت المباشر بالدليل 🚀", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer5, 200070, 0, 130962);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }), $composer4, 805306416, 492);
                    ComposerKt.sourceInformationMarkerEnd($composer4);
                    ComposerKt.sourceInformationMarkerEnd($composer4);
                    $composer4.endReplaceableGroup();
                    $composer4.endNode();
                    $composer4.endReplaceableGroup();
                    $composer4.endReplaceableGroup();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), $composer2, 806879238, 446);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedManualAddProviderTab.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdminControllerScreensKt.ComposedManualAddProviderTab(vm, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$28(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$31(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$34(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$37(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$40(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$43(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedManualAddProviderTab$lambda$46(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Category> ComposedManualAddProviderTab$lambda$48(State<? extends List<Category>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<City> ComposedManualAddProviderTab$lambda$49(State<? extends List<City>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x042f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x04cd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x04da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedAdsAndBannersTab(final com.maw.MainViewModel r97, final java.util.List<com.maw.Banner> r98, androidx.compose.runtime.Composer r99, final int r100) {
        /*
            Method dump skipped, instruction units count: 1245
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedAdsAndBannersTab(com.maw.MainViewModel, java.util.List, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$52(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    private static final String ComposedAdsAndBannersTab$lambda$55(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$58(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$61(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$64(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$67(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedAdsAndBannersTab$lambda$70(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x040b A[LOOP:0: B:53:0x0405->B:55:0x040b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x04ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedCategoryManagementTab(final com.maw.MainViewModel r72, final java.util.List<com.maw.Category> r73, final androidx.compose.ui.text.font.FontFamily r74, androidx.compose.runtime.Composer r75, final int r76) {
        /*
            Method dump skipped, instruction units count: 1206
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedCategoryManagementTab(com.maw.MainViewModel, java.util.List, androidx.compose.ui.text.font.FontFamily, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$75(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$78(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$81(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$84(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$87(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCategoryManagementTab$lambda$90(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedCategoryManagementTab$lambda$93(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedCategoryManagementTab$lambda$94(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Category ComposedCategoryManagementTab$lambda$96(MutableState<Category> mutableState) {
        MutableState<Category> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0539  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0548  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedCitiesManagementTab(final com.maw.MainViewModel r105, final java.util.List<com.maw.City> r106, final androidx.compose.ui.text.font.FontFamily r107, androidx.compose.runtime.Composer r108, final int r109) {
        /*
            Method dump skipped, instruction units count: 1357
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedCitiesManagementTab(com.maw.MainViewModel, java.util.List, androidx.compose.ui.text.font.FontFamily, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCitiesManagementTab$lambda$102(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedCitiesManagementTab$lambda$105(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x05dd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x05e6  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x05f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedComplaintsAndReportsTab(final com.maw.MainViewModel r112, final java.util.List<com.maw.Report> r113, androidx.compose.runtime.Composer r114, final int r115) {
        /*
            Method dump skipped, instruction units count: 1526
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedComplaintsAndReportsTab(com.maw.MainViewModel, java.util.List, androidx.compose.runtime.Composer, int):void");
    }

    public static final void exportReportsToCSV(Context context, List<Report> reports) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reports, "reports");
        try {
            File cacheDir = context.getCacheDir();
            File file = new File(cacheDir, "Yemen_Services_Complaints_Report_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".csv");
            FileWriter writer = new FileWriter(file);
            writer.append((CharSequence) "ID,Reporter Name,Reported Provider,Issue Details,Timestamp\n");
            List<Report> $this$forEach$iv = reports;
            int $i$f$forEach = 0;
            for (Object element$iv : $this$forEach$iv) {
                Report rep = (Report) element$iv;
                String cleanDetails = StringsKt.replace$default(StringsKt.replace$default(rep.getIssue(), ",", " ", false, 4, (Object) null), "\n", " ", false, 4, (Object) null);
                writer.append((CharSequence) (rep.getId() + "," + rep.getReporterName() + "," + rep.getProviderName() + "," + cleanDetails + "," + rep.getTimestamp() + "\n"));
                $this$forEach$iv = $this$forEach$iv;
                $i$f$forEach = $i$f$forEach;
            }
            writer.flush();
            writer.close();
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/csv");
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.putExtra("android.intent.extra.SUBJECT", "تصدير بلاغات الشكاوى في كل خدمات اليمن");
            intent.putExtra("android.intent.extra.TEXT", "تجدون مرفقاً بالصيغة المباشرة Excel/CSV تقرير تدوين الشكاوى وبلاغات الجودة بالمنصة للمصادقة والمراجعة الإدارية.");
            intent.addFlags(1);
            context.startActivity(Intent.createChooser(intent, "مشاركة مستند الشكاوى عبر..."));
            Toast.makeText(context, "تم إنشاء وتصدير مستند Excel/CSV بنجاح! 📂", 0).show();
        } catch (Exception e) {
            Toast.makeText(context, "فشل تصدير التقرير: " + e.getMessage(), 1).show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x03db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedPrivacyAndChatLogsTab(final com.maw.MainViewModel r56, androidx.compose.runtime.Composer r57, final int r58) {
        /*
            Method dump skipped, instruction units count: 1007
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedPrivacyAndChatLogsTab(com.maw.MainViewModel, androidx.compose.runtime.Composer, int):void");
    }

    private static final List<Chat> ComposedPrivacyAndChatLogsTab$lambda$116(State<? extends List<Chat>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final List<ChatMessage> ComposedPrivacyAndChatLogsTab$lambda$117(State<? extends List<ChatMessage>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedPrivacyAndChatLogsTab$lambda$119(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedPrivacyAndChatLogsTab$lambda$120(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0321  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedActiveProvidersTab(final com.maw.MainViewModel r151, final java.util.List<com.maw.Provider> r152, androidx.compose.runtime.Composer r153, final int r154) {
        /*
            Method dump skipped, instruction units count: 990
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedActiveProvidersTab(com.maw.MainViewModel, java.util.List, androidx.compose.runtime.Composer, int):void");
    }

    private static final String ComposedActiveProvidersTab$lambda$125(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x04ae  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x04b4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x04e7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x04fd  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x06c3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x06fc  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0712 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x077b  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x077e  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0788  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x078f  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x084b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0854  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0861  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedSubscriptionsAndLimitsTab(final com.maw.MainViewModel r135, final java.util.List<com.maw.Provider> r136, androidx.compose.runtime.Composer r137, final int r138) {
        /*
            Method dump skipped, instruction units count: 2148
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedSubscriptionsAndLimitsTab(com.maw.MainViewModel, java.util.List, androidx.compose.runtime.Composer, int):void");
    }

    public static final void ComposedSupervisorsAdminTab(final MainViewModel vm, final List<AdminAccount> list, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Object value$iv6;
        Object value$iv7;
        Object value$iv8;
        Object value$iv9;
        Object value$iv10;
        Object value$iv11;
        Object value$iv12;
        Object value$iv13;
        Object value$iv14;
        Object value$iv15;
        Function0<ComposeUiNode> function0;
        Function0<ComposeUiNode> function02;
        Object value$iv16;
        String str;
        final FontFamily currentFont;
        boolean z;
        final MutableState editSupervisorTarget$delegate;
        final State auditLogs$delegate;
        final MutableState showAuditLogsDialog$delegate;
        Object value$iv17;
        Object value$iv18;
        Intrinsics.checkNotNullParameter(vm, "vm");
        Intrinsics.checkNotNullParameter(list, "list");
        Composer $composer2 = $composer.startRestartGroup(-974316772);
        ComposerKt.sourceInformation($composer2, "C(ComposedSupervisorsAdminTab)P(1)1453@76616L7,1454@76653L31,1455@76714L31,1458@76826L33,1459@76891L33,1460@76953L33,1461@77023L34,1462@77083L33,1465@77169L48,1466@77238L31,1467@77304L33,1468@77373L33,1469@77439L33,1470@77513L34,1471@77577L33,1473@77646L29,1476@77787L34,1481@77918L21,1478@77827L16032:AdminControllerScreens.kt#foq9o6");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-974316772, $changed, -1, "com.maw.ComposedSupervisorsAdminTab (AdminControllerScreens.kt:1452)");
        }
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object objConsume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) objConsume;
        $composer2.startReplaceableGroup(1472833638);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState newSupervisorName$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472833699);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final MutableState newSupervisorPass$delegate = (MutableState) value$iv2;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472833811);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        final MutableState canApproveRequests$delegate = (MutableState) value$iv3;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472833876);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv4 = $composer2.rememberedValue();
        if (it$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        final MutableState canManageCategories$delegate = (MutableState) value$iv4;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472833938);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv5 = $composer2.rememberedValue();
        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv5);
        } else {
            value$iv5 = it$iv5;
        }
        final MutableState canManageBanners$delegate = (MutableState) value$iv5;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834008);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv6 = $composer2.rememberedValue();
        if (it$iv6 == Composer.INSTANCE.getEmpty()) {
            value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv6);
        } else {
            value$iv6 = it$iv6;
        }
        final MutableState canDeleteActiveProviders$delegate = (MutableState) value$iv6;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834068);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv7 = $composer2.rememberedValue();
        if (it$iv7 == Composer.INSTANCE.getEmpty()) {
            value$iv7 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv7);
        } else {
            value$iv7 = it$iv7;
        }
        final MutableState canSeeReports$delegate = (MutableState) value$iv7;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834154);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv8 = $composer2.rememberedValue();
        if (it$iv8 == Composer.INSTANCE.getEmpty()) {
            value$iv8 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(null, null, 2, null);
            $composer2.updateRememberedValue(value$iv8);
        } else {
            value$iv8 = it$iv8;
        }
        final MutableState editSupervisorTarget$delegate2 = (MutableState) value$iv8;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834223);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv9 = $composer2.rememberedValue();
        if (it$iv9 == Composer.INSTANCE.getEmpty()) {
            value$iv9 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
            $composer2.updateRememberedValue(value$iv9);
        } else {
            value$iv9 = it$iv9;
        }
        final MutableState editPass$delegate = (MutableState) value$iv9;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834289);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv10 = $composer2.rememberedValue();
        if (it$iv10 == Composer.INSTANCE.getEmpty()) {
            value$iv10 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv10);
        } else {
            value$iv10 = it$iv10;
        }
        final MutableState editCanApproveRequests$delegate = (MutableState) value$iv10;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834358);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv11 = $composer2.rememberedValue();
        if (it$iv11 == Composer.INSTANCE.getEmpty()) {
            value$iv11 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv11);
        } else {
            value$iv11 = it$iv11;
        }
        final MutableState editCanManageCategories$delegate = (MutableState) value$iv11;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834424);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv12 = $composer2.rememberedValue();
        if (it$iv12 == Composer.INSTANCE.getEmpty()) {
            value$iv12 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv12);
        } else {
            value$iv12 = it$iv12;
        }
        final MutableState editCanManageBanners$delegate = (MutableState) value$iv12;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834498);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv13 = $composer2.rememberedValue();
        if (it$iv13 == Composer.INSTANCE.getEmpty()) {
            value$iv13 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv13);
        } else {
            value$iv13 = it$iv13;
        }
        final MutableState editCanDeleteActiveProviders$delegate = (MutableState) value$iv13;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472834562);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv14 = $composer2.rememberedValue();
        if (it$iv14 == Composer.INSTANCE.getEmpty()) {
            value$iv14 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
            $composer2.updateRememberedValue(value$iv14);
        } else {
            value$iv14 = it$iv14;
        }
        final MutableState editCanSeeReports$delegate = (MutableState) value$iv14;
        $composer2.endReplaceableGroup();
        State auditLogs$delegate2 = FlowExtKt.collectAsStateWithLifecycle(vm.getAuditLogs(), (LifecycleOwner) null, (Lifecycle.State) null, (CoroutineContext) null, $composer2, 8, 7);
        final FontFamily currentFont2 = MainActivityKt.resolveAppFontFamily(vm.getSettings().getValue().getSelectedFontName());
        $composer2.startReplaceableGroup(1472834772);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv15 = $composer2.rememberedValue();
        if (it$iv15 == Composer.INSTANCE.getEmpty()) {
            value$iv15 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv15);
        } else {
            value$iv15 = it$iv15;
        }
        final MutableState showAuditLogsDialog$delegate2 = (MutableState) value$iv15;
        $composer2.endReplaceableGroup();
        Modifier modifier$iv = ScrollKt.verticalScroll$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), ScrollKt.rememberScrollState(0, $composer2, 0, 1), false, null, false, 14, null);
        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
        $composer2.startReplaceableGroup(-483455358);
        ComposerKt.sourceInformation($composer2, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
        Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
        MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer2, ((48 >> 3) & 14) | ((48 >> 3) & 112));
        int $changed$iv$iv = (48 << 3) & 112;
        $composer2.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
        CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
        int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
        if (!($composer2.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer2.startReusableNode();
        if ($composer2.getInserting()) {
            function0 = constructor;
            $composer2.createNode(function0);
        } else {
            function0 = constructor;
            $composer2.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
            $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
            $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
        }
        function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
        $composer2.startReplaceableGroup(2058660585);
        int i = ($changed$iv$iv$iv >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer2, 276693656, "C79@3979L9:Column.kt#2w3rfo");
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        int i2 = ((48 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer2, 425198170, "C1484@78016L793,1502@78877L49,1502@78850L4897,1652@88716L157:AdminControllerScreens.kt#foq9o6");
        Modifier modifier$iv2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
        Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getSpaceBetween();
        Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
        $composer2.startReplaceableGroup(693286680);
        ComposerKt.sourceInformation($composer2, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
        MeasurePolicy measurePolicy$iv2 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer2, ((438 >> 3) & 14) | ((438 >> 3) & 112));
        int $changed$iv$iv2 = (438 << 3) & 112;
        $composer2.startReplaceableGroup(-1323940314);
        ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
        int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
        CompositionLocalMap localMap$iv$iv2 = $composer2.getCurrentCompositionLocalMap();
        Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
        int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
        if (!($composer2.getApplier() instanceof Applier)) {
            ComposablesKt.invalidApplier();
        }
        $composer2.startReusableNode();
        if ($composer2.getInserting()) {
            function02 = constructor2;
            $composer2.createNode(function02);
        } else {
            function02 = constructor2;
            $composer2.useNode();
        }
        Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer2);
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
        if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
            $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
            $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
        }
        function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
        $composer2.startReplaceableGroup(2058660585);
        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
        ComposerKt.sourceInformationMarkerStart($composer2, -326681643, "C92@4661L9:Row.kt#2w3rfo");
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        int i4 = ((438 >> 6) & 112) | 6;
        ComposerKt.sourceInformationMarkerStart($composer2, -194023871, "C1489@78214L169,1493@78515L48,1492@78443L30,1491@78409L390:AdminControllerScreens.kt#foq9o6");
        TextKt.m2124Text4IGK_g("🛡️ لوحة توثيق المشرفين وصلاحيات الحماية وتقارير التحركات:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), currentFont2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 199686, 0, 130962);
        ButtonColors buttonColorsM1266buttonColorsro_MJ88 = ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(ColorKt.Color(4284955319L), 0L, 0L, 0L, $composer2, (ButtonDefaults.$stable << 12) | 6, 14);
        Modifier modifierM597height3ABfNKs = SizeKt.m597height3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(30));
        PaddingValues paddingValuesM557PaddingValuesYgX7TsA$default = PaddingKt.m557PaddingValuesYgX7TsA$default(Dp.m5733constructorimpl(8), 0.0f, 2, null);
        $composer2.startReplaceableGroup(-194023642);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
        Object it$iv16 = $composer2.rememberedValue();
        if (it$iv16 == Composer.INSTANCE.getEmpty()) {
            value$iv16 = (Function0) new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$181(showAuditLogsDialog$delegate2, true);
                }
            };
            $composer2.updateRememberedValue(value$iv16);
        } else {
            value$iv16 = it$iv16;
        }
        $composer2.endReplaceableGroup();
        ButtonKt.Button((Function0) value$iv16, modifierM597height3ABfNKs, false, null, buttonColorsM1266buttonColorsro_MJ88, null, null, paddingValuesM557PaddingValuesYgX7TsA$default, null, ComposableLambdaKt.composableLambda($composer2, 135907986, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$1$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                invoke(rowScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(RowScope Button, Composer $composer3, int $changed2) {
                Intrinsics.checkNotNullParameter(Button, "$this$Button");
                ComposerKt.sourceInformation($composer3, "C1497@78714L71:AdminControllerScreens.kt#foq9o6");
                if (($changed2 & 81) == 16 && $composer3.getSkipping()) {
                    $composer3.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(135907986, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:1497)");
                }
                TextKt.m2124Text4IGK_g("الأرشيف والعمليات 📝", (Modifier) null, 0L, TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, currentFont2, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3078, 0, 130998);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }), $composer2, 817889334, 364);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        $composer2.endReplaceableGroup();
        $composer2.endNode();
        $composer2.endReplaceableGroup();
        $composer2.endReplaceableGroup();
        CardKt.Card(null, null, CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, -1701963724, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                invoke(columnScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(ColumnScope Card, Composer $composer3, int $changed2) {
                Function0<ComposeUiNode> function03;
                Object value$iv19;
                Object value$iv20;
                Function0<ComposeUiNode> function04;
                Function0<ComposeUiNode> function05;
                Function0<ComposeUiNode> function06;
                Function0<ComposeUiNode> function07;
                Function0<ComposeUiNode> function08;
                Intrinsics.checkNotNullParameter(Card, "$this$Card");
                ComposerKt.sourceInformation($composer3, "C1503@78942L4795:AdminControllerScreens.kt#foq9o6");
                if (($changed2 & 81) == 16 && $composer3.getSkipping()) {
                    $composer3.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1701963724, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1503)");
                }
                Modifier modifier$iv3 = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
                Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
                final MutableState<String> mutableState = newSupervisorName$delegate;
                final MutableState<String> mutableState2 = newSupervisorPass$delegate;
                final MutableState<Boolean> mutableState3 = canApproveRequests$delegate;
                final MutableState<Boolean> mutableState4 = canManageCategories$delegate;
                final MutableState<Boolean> mutableState5 = canManageBanners$delegate;
                final MutableState<Boolean> mutableState6 = canDeleteActiveProviders$delegate;
                final MutableState<Boolean> mutableState7 = canSeeReports$delegate;
                final Context context2 = context;
                final MainViewModel mainViewModel = vm;
                $composer3.startReplaceableGroup(-483455358);
                ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                MeasurePolicy measurePolicy$iv3 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                int $changed$iv$iv3 = (54 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv3 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
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
                Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                    $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                    $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                }
                function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                int i6 = ((54 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1738683177, "C1504@79053L110,1508@79283L26,1506@79181L270,1515@79571L26,1513@79469L274,1520@79761L2220,1571@83485L50,1545@81999L1724:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("إضافة كود وحساب مشرف جزئي للنظام:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200070, 0, 131026);
                String strComposedSupervisorsAdminTab$lambda$137 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$137(mutableState);
                Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(1738683407);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object it$iv17 = $composer3.rememberedValue();
                if (it$iv17 == Composer.INSTANCE.getEmpty()) {
                    value$iv19 = new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                            invoke2(str2);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            mutableState.setValue(it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv19);
                } else {
                    value$iv19 = it$iv17;
                }
                $composer3.endReplaceableGroup();
                OutlinedTextFieldKt.OutlinedTextField(strComposedSupervisorsAdminTab$lambda$137, (Function1<? super String, Unit>) value$iv19, modifierFillMaxWidth$default, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6300getLambda35$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, (TextFieldColors) null, $composer3, 1573296, 0, 0, 8388536);
                String strComposedSupervisorsAdminTab$lambda$140 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$140(mutableState2);
                Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(1738683695);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object it$iv18 = $composer3.rememberedValue();
                if (it$iv18 == Composer.INSTANCE.getEmpty()) {
                    value$iv20 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                            invoke2(str2);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(String it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            mutableState2.setValue(it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv20);
                } else {
                    value$iv20 = it$iv18;
                }
                $composer3.endReplaceableGroup();
                OutlinedTextFieldKt.OutlinedTextField(strComposedSupervisorsAdminTab$lambda$140, (Function1<? super String, Unit>) value$iv20, modifierFillMaxWidth$default2, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6301getLambda36$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, (TextFieldColors) null, $composer3, 1573296, 0, 0, 8388536);
                Arrangement.Vertical verticalArrangement$iv3 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(4));
                $composer3.startReplaceableGroup(-483455358);
                ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                Modifier modifier$iv4 = Modifier.INSTANCE;
                Alignment.Horizontal horizontalAlignment$iv3 = Alignment.INSTANCE.getStart();
                int $i$f$Column = ((48 >> 3) & 14) | ((48 >> 3) & 112);
                MeasurePolicy measurePolicy$iv4 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv3, horizontalAlignment$iv3, $composer3, $i$f$Column);
                int $changed$iv$iv4 = (48 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv4 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
                int $changed$iv$iv$iv4 = (($changed$iv$iv4 << 9) & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    function04 = constructor4;
                    $composer3.createNode(function04);
                } else {
                    function04 = constructor4;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                    $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                    $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                }
                function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                ColumnScopeInstance columnScopeInstance3 = ColumnScopeInstance.INSTANCE;
                int i8 = ((48 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1747095133, "C1521@79840L118,1523@80000L380,1527@80401L376,1531@80798L368,1535@81187L384,1539@81592L371:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("حدد صلاحيات وخصائص المشرف الجديد:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 199686, 0, 131026);
                Alignment.Vertical centerVertically = Alignment.INSTANCE.getCenterVertically();
                Arrangement.HorizontalOrVertical spaceBetween = Arrangement.INSTANCE.getSpaceBetween();
                Modifier modifierFillMaxWidth$default3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(693286680);
                ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                int $i$f$Row = ((438 >> 3) & 14) | ((438 >> 3) & 112);
                MeasurePolicy measurePolicy$iv5 = RowKt.rowMeasurePolicy(spaceBetween, centerVertically, $composer3, $i$f$Row);
                int $changed$iv$iv5 = (438 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv5 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default3);
                int $changed$iv$iv$iv5 = (($changed$iv$iv5 << 9) & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    function05 = constructor5;
                    $composer3.createNode(function05);
                } else {
                    function05 = constructor5;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                    $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                    $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
                }
                function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i9 = ($changed$iv$iv$iv5 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                int i10 = ((438 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1908588098, "C1524@80164L86,1525@80330L27,1525@80275L83:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("موافقة وتأكيد طلبات الانضمام والخدمات 📃", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                boolean zComposedSupervisorsAdminTab$lambda$143 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$143(mutableState3);
                $composer3.startReplaceableGroup(1908588264);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object value$iv21 = $composer3.rememberedValue();
                if (value$iv21 == Composer.INSTANCE.getEmpty()) {
                    value$iv21 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$3$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean it) {
                            AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$144(mutableState3, it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv21);
                }
                $composer3.endReplaceableGroup();
                SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$143, (Function1) value$iv21, null, null, false, null, null, $composer3, 48, 124);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                Alignment.Vertical centerVertically2 = Alignment.INSTANCE.getCenterVertically();
                Arrangement.HorizontalOrVertical spaceBetween2 = Arrangement.INSTANCE.getSpaceBetween();
                Modifier modifierFillMaxWidth$default4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(693286680);
                ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                MeasurePolicy measurePolicy$iv6 = RowKt.rowMeasurePolicy(spaceBetween2, centerVertically2, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                int $changed$iv$iv6 = (438 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv6 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv6 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor6 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf6 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default4);
                int $i$f$Layout = $changed$iv$iv6 << 9;
                int $changed$iv$iv$iv6 = ($i$f$Layout & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    function06 = constructor6;
                    $composer3.createNode(function06);
                } else {
                    function06 = constructor6;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv6 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, measurePolicy$iv6, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, localMap$iv$iv6, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash6 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv6.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv6.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv6))) {
                    $this$Layout_u24lambda_u240$iv$iv6.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv6));
                    $this$Layout_u24lambda_u240$iv$iv6.apply(Integer.valueOf(compositeKeyHash$iv$iv6), setCompositeKeyHash6);
                }
                function3ModifierMaterializerOf6.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv6 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i11 = ($changed$iv$iv$iv6 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                RowScopeInstance rowScopeInstance3 = RowScopeInstance.INSTANCE;
                int i12 = ((438 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1908588499, "C1528@80565L80,1529@80726L28,1529@80670L85:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("التحكم بالتصنيفات وتعديل الفئات 📁", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                boolean zComposedSupervisorsAdminTab$lambda$146 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$146(mutableState4);
                $composer3.startReplaceableGroup(1908588660);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object value$iv22 = $composer3.rememberedValue();
                if (value$iv22 == Composer.INSTANCE.getEmpty()) {
                    value$iv22 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$3$2$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean it) {
                            AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$147(mutableState4, it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv22);
                }
                $composer3.endReplaceableGroup();
                SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$146, (Function1) value$iv22, null, null, false, null, null, $composer3, 48, 124);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                Alignment.Vertical centerVertically3 = Alignment.INSTANCE.getCenterVertically();
                Arrangement.HorizontalOrVertical spaceBetween3 = Arrangement.INSTANCE.getSpaceBetween();
                Modifier modifierFillMaxWidth$default5 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(693286680);
                ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                MeasurePolicy measurePolicy$iv7 = RowKt.rowMeasurePolicy(spaceBetween3, centerVertically3, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                int $changed$iv$iv7 = (438 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv7 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv7 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor7 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf7 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default5);
                int $i$f$Row2 = $changed$iv$iv7 << 9;
                int $changed$iv$iv$iv7 = ($i$f$Row2 & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    function07 = constructor7;
                    $composer3.createNode(function07);
                } else {
                    function07 = constructor7;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv7 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, measurePolicy$iv7, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, localMap$iv$iv7, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash7 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv7.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv7.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv7))) {
                    $this$Layout_u24lambda_u240$iv$iv7.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv7));
                    $this$Layout_u24lambda_u240$iv$iv7.apply(Integer.valueOf(compositeKeyHash$iv$iv7), setCompositeKeyHash7);
                }
                function3ModifierMaterializerOf7.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv7 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i13 = ($changed$iv$iv$iv7 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                RowScopeInstance rowScopeInstance4 = RowScopeInstance.INSTANCE;
                int i14 = ((438 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1908588896, "C1532@80962L78,1533@81118L25,1533@81065L79:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("تعديل وإضافة بنرات الإعلانات 🖼️", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                boolean zComposedSupervisorsAdminTab$lambda$149 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$149(mutableState5);
                $composer3.startReplaceableGroup(1908589052);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object value$iv23 = $composer3.rememberedValue();
                if (value$iv23 == Composer.INSTANCE.getEmpty()) {
                    value$iv23 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$3$3$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean it) {
                            AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$150(mutableState5, it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv23);
                }
                $composer3.endReplaceableGroup();
                SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$149, (Function1) value$iv23, null, null, false, null, null, $composer3, 48, 124);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                Alignment.Vertical centerVertically4 = Alignment.INSTANCE.getCenterVertically();
                Arrangement.HorizontalOrVertical spaceBetween4 = Arrangement.INSTANCE.getSpaceBetween();
                Modifier modifierFillMaxWidth$default6 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(693286680);
                ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                MeasurePolicy measurePolicy$iv8 = RowKt.rowMeasurePolicy(spaceBetween4, centerVertically4, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                int $changed$iv$iv8 = (438 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv8 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv8 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor8 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf8 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default6);
                int $i$f$Row3 = $changed$iv$iv8 << 9;
                int $changed$iv$iv$iv8 = ($i$f$Row3 & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    function08 = constructor8;
                    $composer3.createNode(function08);
                } else {
                    function08 = constructor8;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv8 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, measurePolicy$iv8, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, localMap$iv$iv8, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash8 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv8.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv8.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv8))) {
                    $this$Layout_u24lambda_u240$iv$iv8.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv8));
                    $this$Layout_u24lambda_u240$iv$iv8.apply(Integer.valueOf(compositeKeyHash$iv$iv8), setCompositeKeyHash8);
                }
                function3ModifierMaterializerOf8.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv8 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i15 = ($changed$iv$iv$iv8 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                RowScopeInstance rowScopeInstance5 = RowScopeInstance.INSTANCE;
                int i16 = ((438 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1908589285, "C1536@81351L78,1537@81515L33,1537@81454L95:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("شطب وحذف أعضاء الدليل النشطين 🚨", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                boolean zComposedSupervisorsAdminTab$lambda$152 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$152(mutableState6);
                $composer3.startReplaceableGroup(1908589449);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object value$iv24 = $composer3.rememberedValue();
                if (value$iv24 == Composer.INSTANCE.getEmpty()) {
                    value$iv24 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$3$4$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean it) {
                            AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$153(mutableState6, it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv24);
                }
                $composer3.endReplaceableGroup();
                SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$152, (Function1) value$iv24, null, null, false, null, null, $composer3, 48, 124);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                Alignment.Vertical centerVertically5 = Alignment.INSTANCE.getCenterVertically();
                Arrangement.HorizontalOrVertical spaceBetween5 = Arrangement.INSTANCE.getSpaceBetween();
                Modifier modifierFillMaxWidth$default7 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                $composer3.startReplaceableGroup(693286680);
                ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                MeasurePolicy measurePolicy$iv9 = RowKt.rowMeasurePolicy(spaceBetween5, centerVertically5, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                int $changed$iv$iv9 = (438 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv9 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv9 = $composer3.getCurrentCompositionLocalMap();
                Function0<ComposeUiNode> constructor9 = ComposeUiNode.INSTANCE.getConstructor();
                Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf9 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default7);
                int $i$f$Row4 = $changed$iv$iv9 << 9;
                int $changed$iv$iv$iv9 = ($i$f$Row4 & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    $composer3.createNode(constructor9);
                } else {
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv9 = Updater.m2936constructorimpl($composer3);
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv9, measurePolicy$iv9, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv9, localMap$iv$iv9, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash9 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv9.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv9.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv9))) {
                    $this$Layout_u24lambda_u240$iv$iv9.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv9));
                    $this$Layout_u24lambda_u240$iv$iv9.apply(Integer.valueOf(compositeKeyHash$iv$iv9), setCompositeKeyHash9);
                }
                function3ModifierMaterializerOf9.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv9 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i17 = ($changed$iv$iv$iv9 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                RowScopeInstance rowScopeInstance6 = RowScopeInstance.INSTANCE;
                int i18 = ((438 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, 1908589690, "C1540@81756L87,1541@81918L22,1541@81868L73:AdminControllerScreens.kt#foq9o6");
                TextKt.m2124Text4IGK_g("تصفح وقراءة البلاغات والتقارير الواردة 📊", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                boolean zComposedSupervisorsAdminTab$lambda$155 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$155(mutableState7);
                $composer3.startReplaceableGroup(1908589852);
                ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                Object value$iv25 = $composer3.rememberedValue();
                if (value$iv25 == Composer.INSTANCE.getEmpty()) {
                    value$iv25 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$3$5$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean it) {
                            AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$156(mutableState7, it);
                        }
                    };
                    $composer3.updateRememberedValue(value$iv25);
                }
                $composer3.endReplaceableGroup();
                SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$155, (Function1) value$iv25, null, null, false, null, null, $composer3, 48, 124);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$2$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        if (StringsKt.isBlank(AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$137(mutableState)) || StringsKt.isBlank(AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$140(mutableState2))) {
                            Toast.makeText(context2, "الرجاء توفير اسم ورمز سري لتسهيل حيازة التراخيص!", 0).show();
                            return;
                        }
                        AdminAccount newAccount = new AdminAccount(AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$137(mutableState), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$140(mutableState2), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$143(mutableState3), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$146(mutableState4), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$149(mutableState5), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$152(mutableState6), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$155(mutableState7));
                        mainViewModel.addAdminAccount(newAccount, "الأدمن العام");
                        Toast.makeText(context2, "تم حفظ المشرف الجديد وتعميله في الحماية بنجاح! 💾", 0).show();
                        mutableState.setValue("");
                        mutableState2.setValue("");
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$144(mutableState3, true);
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$147(mutableState4, true);
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$150(mutableState5, true);
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$153(mutableState6, false);
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$156(mutableState7, true);
                    }
                }, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer3, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6302getLambda37$app_debug(), $composer3, 805306416, 492);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 27);
        $composer2.startReplaceableGroup(425203945);
        ComposerKt.sourceInformation($composer2, "1583@83935L31,1582@83887L4708");
        if (ComposedSupervisorsAdminTab$lambda$158(editSupervisorTarget$delegate2) != null) {
            final AdminAccount target = ComposedSupervisorsAdminTab$lambda$158(editSupervisorTarget$delegate2);
            Intrinsics.checkNotNull(target);
            long jM6266getSurfaceDark0d7_KjU = AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU();
            $composer2.startReplaceableGroup(425204089);
            ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv17 = $composer2.rememberedValue();
            if (it$iv17 == Composer.INSTANCE.getEmpty()) {
                value$iv18 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$3$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        editSupervisorTarget$delegate2.setValue(null);
                    }
                };
                $composer2.updateRememberedValue(value$iv18);
            } else {
                value$iv18 = it$iv17;
            }
            $composer2.endReplaceableGroup();
            z = false;
            str = "CC(remember):AdminControllerScreens.kt#9igjgp";
            currentFont = currentFont2;
            editSupervisorTarget$delegate = editSupervisorTarget$delegate2;
            AndroidAlertDialog_androidKt.m1224AlertDialogOix01E0((Function0) value$iv18, ComposableLambdaKt.composableLambda($composer2, 1453291955, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    ComposerKt.sourceInformation($composer3, "C1637@88037L50,1623@87137L1117:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1453291955, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1623)");
                        }
                        final AdminAccount adminAccount = target;
                        final MainViewModel mainViewModel = vm;
                        final Context context2 = context;
                        final MutableState<String> mutableState = editPass$delegate;
                        final MutableState<Boolean> mutableState2 = editCanApproveRequests$delegate;
                        final MutableState<Boolean> mutableState3 = editCanManageCategories$delegate;
                        final MutableState<Boolean> mutableState4 = editCanManageBanners$delegate;
                        final MutableState<Boolean> mutableState5 = editCanDeleteActiveProviders$delegate;
                        final MutableState<Boolean> mutableState6 = editCanSeeReports$delegate;
                        final MutableState<AdminAccount> mutableState7 = editSupervisorTarget$delegate2;
                        Function0<Unit> function03 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$4.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                AdminAccount updated = AdminAccount.copy$default(adminAccount, null, AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$161(mutableState), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$164(mutableState2), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$167(mutableState3), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$170(mutableState4), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$173(mutableState5), AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$176(mutableState6), 1, null);
                                mainViewModel.addAdminAccount(updated, "الأدمن العام");
                                mutableState7.setValue(null);
                                Toast.makeText(context2, "تم حفظ وتحديث صلاحيات المشرف الجديد سحابياً!", 0).show();
                            }
                        };
                        ButtonColors buttonColorsM1266buttonColorsro_MJ882 = ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), 0L, 0L, 0L, $composer3, ButtonDefaults.$stable << 12, 14);
                        final FontFamily fontFamily = currentFont2;
                        ButtonKt.Button(function03, null, false, null, buttonColorsM1266buttonColorsro_MJ882, null, null, null, null, ComposableLambdaKt.composableLambda($composer3, -183146077, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$4.2
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                invoke(rowScope, composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(RowScope Button, Composer $composer4, int $changed3) {
                                Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                ComposerKt.sourceInformation($composer4, "C1639@88136L96:AdminControllerScreens.kt#foq9o6");
                                if (($changed3 & 81) == 16 && $composer4.getSkipping()) {
                                    $composer4.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-183146077, $changed3, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:1639)");
                                }
                                TextKt.m2124Text4IGK_g("تطبيق وحفظ الصلاحيات 💾", (Modifier) null, Color.INSTANCE.m3432getBlack0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3462, 0, 130994);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }), $composer3, 805306368, 494);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), null, ComposableLambdaKt.composableLambda($composer2, 637244725, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    Object value$iv19;
                    ComposerKt.sourceInformation($composer3, "C1643@88349L31,1643@88328L180:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(637244725, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1643)");
                    }
                    $composer3.startReplaceableGroup(-194013736);
                    ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    final MutableState<AdminAccount> mutableState = editSupervisorTarget$delegate;
                    Object it$iv18 = $composer3.rememberedValue();
                    if (it$iv18 == Composer.INSTANCE.getEmpty()) {
                        value$iv19 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$5$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                mutableState.setValue(null);
                            }
                        };
                        $composer3.updateRememberedValue(value$iv19);
                    } else {
                        value$iv19 = it$iv18;
                    }
                    $composer3.endReplaceableGroup();
                    final FontFamily fontFamily = currentFont;
                    ButtonKt.TextButton((Function0) value$iv19, null, false, null, null, null, null, null, null, ComposableLambdaKt.composableLambda($composer3, 277225330, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$5.2
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                            invoke(rowScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(RowScope TextButton, Composer $composer4, int $changed3) {
                            Intrinsics.checkNotNullParameter(TextButton, "$this$TextButton");
                            ComposerKt.sourceInformation($composer4, "C1644@88408L78:AdminControllerScreens.kt#foq9o6");
                            if (($changed3 & 81) == 16 && $composer4.getSkipping()) {
                                $composer4.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(277225330, $changed3, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous>.<anonymous> (AdminControllerScreens.kt:1644)");
                            }
                            TextKt.m2124Text4IGK_g("تراجع", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3462, 0, 130994);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }), $composer3, 805306374, 510);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), null, ComposableLambdaKt.composableLambda($composer2, -178802505, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$6
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    ComposerKt.sourceInformation($composer3, "C1584@83994L103:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-178802505, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1584)");
                    }
                    TextKt.m2124Text4IGK_g("تعديل صلاحيات وخصائص المشرف ⚙️", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(13), (FontStyle) null, (FontWeight) null, currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 130994);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), ComposableLambdaKt.composableLambda($composer2, -586826120, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    Function0<ComposeUiNode> function03;
                    Function0<ComposeUiNode> function04;
                    Function0<ComposeUiNode> function05;
                    Function0<ComposeUiNode> function06;
                    ComposerKt.sourceInformation($composer3, "C1587@84228L21,1586@84146L2917:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-586826120, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1586)");
                        }
                        Modifier modifier$iv3 = ScrollKt.verticalScroll$default(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), ScrollKt.rememberScrollState(0, $composer3, 0, 1), false, null, false, 14, null);
                        Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
                        AdminAccount adminAccount = target;
                        final MutableState<String> mutableState = editPass$delegate;
                        final MutableState<Boolean> mutableState2 = editCanApproveRequests$delegate;
                        final MutableState<Boolean> mutableState3 = editCanManageCategories$delegate;
                        final MutableState<Boolean> mutableState4 = editCanManageBanners$delegate;
                        final MutableState<Boolean> mutableState5 = editCanDeleteActiveProviders$delegate;
                        final MutableState<Boolean> mutableState6 = editCanSeeReports$delegate;
                        $composer3.startReplaceableGroup(-483455358);
                        ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                        Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                        MeasurePolicy measurePolicy$iv3 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer3, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                        int $changed$iv$iv3 = (48 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv3 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                        int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
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
                        Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                            $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                            $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                        }
                        function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                        ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                        int i6 = ((48 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1738688498, "C1590@84374L117,1597@84838L72,1594@84626L17,1592@84517L419,1600@84962L400,1604@85387L396,1608@85808L388,1612@86221L404,1616@86650L391:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("اسم المستخدم: " + adminAccount.getUsername(), (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 199680, 0, 131026);
                        String strComposedSupervisorsAdminTab$lambda$161 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$161(mutableState);
                        Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        TextFieldColors textFieldColorsM1726colors0hiis_0 = OutlinedTextFieldDefaults.INSTANCE.m1726colors0hiis_0(Color.INSTANCE.m3443getWhite0d7_KjU(), Color.INSTANCE.m3443getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, null, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer3, 54, 0, 0, 0, 3072, 2147483644, 4095);
                        $composer3.startReplaceableGroup(1738688750);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv19 = $composer3.rememberedValue();
                        if (value$iv19 == Composer.INSTANCE.getEmpty()) {
                            value$iv19 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                                    invoke2(str2);
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(String it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    mutableState.setValue(it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv19);
                        }
                        $composer3.endReplaceableGroup();
                        OutlinedTextFieldKt.OutlinedTextField(strComposedSupervisorsAdminTab$lambda$161, (Function1<? super String, Unit>) value$iv19, modifierFillMaxWidth$default, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6303getLambda38$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, textFieldColorsM1726colors0hiis_0, $composer3, 1573296, 0, 0, 4194232);
                        Alignment.Vertical centerVertically = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween = Arrangement.INSTANCE.getSpaceBetween();
                        Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(spaceBetween, centerVertically, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv4 = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv4 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default2);
                        int $i$f$Row = $changed$iv$iv4 << 9;
                        int $changed$iv$iv$iv4 = ($i$f$Row & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function04 = constructor4;
                            $composer3.createNode(function04);
                        } else {
                            function04 = constructor4;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                            $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                            $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                        }
                        function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                        int i8 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1747100423, "C1601@85130L86,1602@85304L31,1602@85245L91:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("موافقة وتأكيد طلبات الانضمام والخدمات 📃", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                        boolean zComposedSupervisorsAdminTab$lambda$164 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$164(mutableState2);
                        $composer3.startReplaceableGroup(1747100597);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv20 = $composer3.rememberedValue();
                        if (value$iv20 == Composer.INSTANCE.getEmpty()) {
                            value$iv20 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$2$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                    invoke(bool.booleanValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(boolean it) {
                                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$165(mutableState2, it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv20);
                        }
                        $composer3.endReplaceableGroup();
                        SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$164, (Function1) value$iv20, null, null, false, null, null, $composer3, 48, 124);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        Alignment.Vertical centerVertically2 = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween2 = Arrangement.INSTANCE.getSpaceBetween();
                        Modifier modifierFillMaxWidth$default3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv5 = RowKt.rowMeasurePolicy(spaceBetween2, centerVertically2, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv5 = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv5 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv5 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor5 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf5 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default3);
                        int $i$f$Row2 = $changed$iv$iv5 << 9;
                        int $changed$iv$iv$iv5 = ($i$f$Row2 & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function05 = constructor5;
                            $composer3.createNode(function05);
                        } else {
                            function05 = constructor5;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv5 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, measurePolicy$iv5, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv5, localMap$iv$iv5, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash5 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv5.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv5.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv5))) {
                            $this$Layout_u24lambda_u240$iv$iv5.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv5));
                            $this$Layout_u24lambda_u240$iv$iv5.apply(Integer.valueOf(compositeKeyHash$iv$iv5), setCompositeKeyHash5);
                        }
                        function3ModifierMaterializerOf5.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv5 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i9 = ($changed$iv$iv$iv5 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance3 = RowScopeInstance.INSTANCE;
                        int i10 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1747100848, "C1605@85555L80,1606@85724L32,1606@85664L93:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("التحكم بالتصنيفات وتعديل الفئات 📁", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                        boolean zComposedSupervisorsAdminTab$lambda$167 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$167(mutableState3);
                        $composer3.startReplaceableGroup(1747101017);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv21 = $composer3.rememberedValue();
                        if (value$iv21 == Composer.INSTANCE.getEmpty()) {
                            value$iv21 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$3$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                    invoke(bool.booleanValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(boolean it) {
                                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$168(mutableState3, it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv21);
                        }
                        $composer3.endReplaceableGroup();
                        SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$167, (Function1) value$iv21, null, null, false, null, null, $composer3, 48, 124);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        Alignment.Vertical centerVertically3 = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween3 = Arrangement.INSTANCE.getSpaceBetween();
                        Modifier modifierFillMaxWidth$default4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv6 = RowKt.rowMeasurePolicy(spaceBetween3, centerVertically3, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv6 = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv6 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv6 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor6 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf6 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default4);
                        int $i$f$Row3 = $changed$iv$iv6 << 9;
                        int $changed$iv$iv$iv6 = ($i$f$Row3 & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            function06 = constructor6;
                            $composer3.createNode(function06);
                        } else {
                            function06 = constructor6;
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv6 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, measurePolicy$iv6, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv6, localMap$iv$iv6, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash6 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv6.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv6.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv6))) {
                            $this$Layout_u24lambda_u240$iv$iv6.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv6));
                            $this$Layout_u24lambda_u240$iv$iv6.apply(Integer.valueOf(compositeKeyHash$iv$iv6), setCompositeKeyHash6);
                        }
                        function3ModifierMaterializerOf6.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv6 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i11 = ($changed$iv$iv$iv6 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance4 = RowScopeInstance.INSTANCE;
                        int i12 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1747101269, "C1609@85976L78,1610@86140L29,1610@86083L87:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("تعديل وإضافة بنرات الإعلانات 🖼️", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                        boolean zComposedSupervisorsAdminTab$lambda$170 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$170(mutableState4);
                        $composer3.startReplaceableGroup(1747101433);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv22 = $composer3.rememberedValue();
                        if (value$iv22 == Composer.INSTANCE.getEmpty()) {
                            value$iv22 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$4$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                    invoke(bool.booleanValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(boolean it) {
                                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$171(mutableState4, it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv22);
                        }
                        $composer3.endReplaceableGroup();
                        SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$170, (Function1) value$iv22, null, null, false, null, null, $composer3, 48, 124);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        Alignment.Vertical centerVertically4 = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween4 = Arrangement.INSTANCE.getSpaceBetween();
                        Modifier modifierFillMaxWidth$default5 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv7 = RowKt.rowMeasurePolicy(spaceBetween4, centerVertically4, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv7 = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv7 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv7 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor7 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf7 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default5);
                        int $i$f$Row4 = $changed$iv$iv7 << 9;
                        int $changed$iv$iv$iv7 = ($i$f$Row4 & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            $composer3.createNode(constructor7);
                        } else {
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv7 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, measurePolicy$iv7, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv7, localMap$iv$iv7, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash7 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv7.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv7.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv7))) {
                            $this$Layout_u24lambda_u240$iv$iv7.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv7));
                            $this$Layout_u24lambda_u240$iv$iv7.apply(Integer.valueOf(compositeKeyHash$iv$iv7), setCompositeKeyHash7);
                        }
                        function3ModifierMaterializerOf7.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv7 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i13 = ($changed$iv$iv$iv7 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance5 = RowScopeInstance.INSTANCE;
                        int i14 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1747101682, "C1613@86389L78,1614@86561L37,1614@86496L103:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("شطب وحذف أعضاء الدليل النشطين 🚨", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                        boolean zComposedSupervisorsAdminTab$lambda$173 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$173(mutableState5);
                        $composer3.startReplaceableGroup(1747101854);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv23 = $composer3.rememberedValue();
                        if (value$iv23 == Composer.INSTANCE.getEmpty()) {
                            value$iv23 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$5$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                    invoke(bool.booleanValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(boolean it) {
                                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$174(mutableState5, it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv23);
                        }
                        $composer3.endReplaceableGroup();
                        SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$173, (Function1) value$iv23, null, null, false, null, null, $composer3, 48, 124);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        Alignment.Vertical centerVertically5 = Alignment.INSTANCE.getCenterVertically();
                        Arrangement.HorizontalOrVertical spaceBetween5 = Arrangement.INSTANCE.getSpaceBetween();
                        Modifier modifierFillMaxWidth$default6 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                        $composer3.startReplaceableGroup(693286680);
                        ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv8 = RowKt.rowMeasurePolicy(spaceBetween5, centerVertically5, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                        int $changed$iv$iv8 = (438 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv8 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv8 = $composer3.getCurrentCompositionLocalMap();
                        Function0<ComposeUiNode> constructor8 = ComposeUiNode.INSTANCE.getConstructor();
                        Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf8 = LayoutKt.modifierMaterializerOf(modifierFillMaxWidth$default6);
                        int $changed$iv$iv$iv8 = (($changed$iv$iv8 << 9) & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            $composer3.createNode(constructor8);
                        } else {
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv8 = Updater.m2936constructorimpl($composer3);
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, measurePolicy$iv8, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv8, localMap$iv$iv8, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash8 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if ($this$Layout_u24lambda_u240$iv$iv8.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv8.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv8))) {
                            $this$Layout_u24lambda_u240$iv$iv8.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv8));
                            $this$Layout_u24lambda_u240$iv$iv8.apply(Integer.valueOf(compositeKeyHash$iv$iv8), setCompositeKeyHash8);
                        }
                        function3ModifierMaterializerOf8.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv8 >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i15 = ($changed$iv$iv$iv8 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                        RowScopeInstance rowScopeInstance6 = RowScopeInstance.INSTANCE;
                        int i16 = ((438 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 1747102111, "C1617@86818L87,1618@86988L26,1618@86934L81:AdminControllerScreens.kt#foq9o6");
                        TextKt.m2124Text4IGK_g("تصفح وقراءة البلاغات والتقارير الواردة 📊", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 131058);
                        boolean zComposedSupervisorsAdminTab$lambda$176 = AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$176(mutableState6);
                        $composer3.startReplaceableGroup(1747102281);
                        ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                        Object value$iv24 = $composer3.rememberedValue();
                        if (value$iv24 == Composer.INSTANCE.getEmpty()) {
                            value$iv24 = (Function1) new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$7$1$6$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                    invoke(bool.booleanValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(boolean it) {
                                    AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$177(mutableState6, it);
                                }
                            };
                            $composer3.updateRememberedValue(value$iv24);
                        }
                        $composer3.endReplaceableGroup();
                        SwitchKt.Switch(zComposedSupervisorsAdminTab$lambda$176, (Function1) value$iv24, null, null, false, null, null, $composer3, 48, 124);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
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
            }), null, jM6266getSurfaceDark0d7_KjU, 0L, 0L, 0L, 0.0f, null, $composer2, 1772598, 0, 16020);
        } else {
            str = "CC(remember):AdminControllerScreens.kt#9igjgp";
            currentFont = currentFont2;
            z = false;
            editSupervisorTarget$delegate = editSupervisorTarget$delegate2;
        }
        $composer2.endReplaceableGroup();
        TextKt.m2124Text4IGK_g("👥 قائمة المشرفين والأجهزة البيضاء المصرح لها بالولوج:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 200070, 0, 130962);
        $composer2.startReplaceableGroup(425209041);
        ComposerKt.sourceInformation($composer2, "*1654@88943L49,1654@88916L2664");
        List<AdminAccount> $this$forEach$iv = list;
        for (Object element$iv : $this$forEach$iv) {
            final AdminAccount acc = (AdminAccount) element$iv;
            final MutableState mutableState = editSupervisorTarget$delegate;
            CardKt.Card(null, null, CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 932799451, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$8$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:86:0x06cd  */
                /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r91, androidx.compose.runtime.Composer r92, int r93) {
                    /*
                        Method dump skipped, instruction units count: 1745
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$8$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 27);
            editSupervisorTarget$delegate = editSupervisorTarget$delegate;
        }
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1472848633);
        ComposerKt.sourceInformation($composer2, "1694@91713L31,1694@91687L2156");
        if (ComposedSupervisorsAdminTab$lambda$180(showAuditLogsDialog$delegate2)) {
            $composer2.startReplaceableGroup(425211867);
            ComposerKt.sourceInformation($composer2, str);
            Object it$iv18 = $composer2.rememberedValue();
            if (it$iv18 == Composer.INSTANCE.getEmpty()) {
                showAuditLogsDialog$delegate = showAuditLogsDialog$delegate2;
                value$iv17 = new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$9$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        AdminControllerScreensKt.ComposedSupervisorsAdminTab$lambda$181(showAuditLogsDialog$delegate, false);
                    }
                };
                $composer2.updateRememberedValue(value$iv17);
            } else {
                showAuditLogsDialog$delegate = showAuditLogsDialog$delegate2;
                value$iv17 = it$iv18;
            }
            $composer2.endReplaceableGroup();
            auditLogs$delegate = auditLogs$delegate2;
            AndroidDialog_androidKt.Dialog((Function0) value$iv17, null, ComposableLambdaKt.composableLambda($composer2, 1050269, true, new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$10
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
                    ComposerKt.sourceInformation($composer3, "C1696@91812L49,1695@91764L2065:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1050269, $changed2, -1, "com.maw.ComposedSupervisorsAdminTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1695)");
                        }
                        CardColors cardColorsM1287cardColorsro_MJ88 = CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer3, CardDefaults.$stable << 12, 14);
                        RoundedCornerShape roundedCornerShapeM831RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12));
                        BorderStroke borderStrokeM237BorderStrokecXLIe8U = BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), AppTheme.INSTANCE.m6261getAccentGold0d7_KjU());
                        final State<List<AuditLog>> state = auditLogs$delegate;
                        final FontFamily fontFamily = currentFont;
                        final MutableState<Boolean> mutableState2 = showAuditLogsDialog$delegate;
                        CardKt.Card(SizeKt.fillMaxHeight(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), 0.75f), roundedCornerShapeM831RoundedCornerShape0680j_4, cardColorsM1287cardColorsro_MJ88, null, borderStrokeM237BorderStrokecXLIe8U, ComposableLambdaKt.composableLambda($composer3, 1961270635, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$10.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                invoke(columnScope, composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:42:0x02fd  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x030d  */
                            /* JADX WARN: Removed duplicated region for block: B:50:0x038e  */
                            /* JADX WARN: Removed duplicated region for block: B:53:0x03d8  */
                            /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public final void invoke(androidx.compose.foundation.layout.ColumnScope r79, androidx.compose.runtime.Composer r80, int r81) {
                                /*
                                    Method dump skipped, instruction units count: 988
                                    To view this dump add '--comments-level debug' option
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt$ComposedSupervisorsAdminTab$1$10.AnonymousClass1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                            }
                        }), $composer3, 196614, 8);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer3.skipToGroupEnd();
                }
            }), $composer2, 390, 2);
        } else {
            auditLogs$delegate = auditLogs$delegate2;
        }
        $composer2.endReplaceableGroup();
        ComposerKt.sourceInformationMarkerEnd($composer2);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        $composer2.endReplaceableGroup();
        $composer2.endNode();
        $composer2.endReplaceableGroup();
        $composer2.endReplaceableGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedSupervisorsAdminTab.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i5) {
                    AdminControllerScreensKt.ComposedSupervisorsAdminTab(vm, list, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedSupervisorsAdminTab$lambda$137(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedSupervisorsAdminTab$lambda$140(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$143(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$144(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$146(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$147(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$149(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$150(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$152(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$153(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$155(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$156(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    private static final AdminAccount ComposedSupervisorsAdminTab$lambda$158(MutableState<AdminAccount> mutableState) {
        MutableState<AdminAccount> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedSupervisorsAdminTab$lambda$161(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$164(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$165(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$167(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$168(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$170(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$171(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$173(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$174(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedSupervisorsAdminTab$lambda$176(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$177(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<AuditLog> ComposedSupervisorsAdminTab$lambda$178(State<? extends List<AuditLog>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final boolean ComposedSupervisorsAdminTab$lambda$180(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedSupervisorsAdminTab$lambda$181(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    public static final void ComposedNotificationsTab(final MainViewModel vm, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Function0<ComposeUiNode> function0;
        Intrinsics.checkNotNullParameter(vm, "vm");
        Composer $composer2 = $composer.startRestartGroup(1067727542);
        ComposerKt.sourceInformation($composer2, "C(ComposedNotificationsTab)1741@94592L7,1742@94617L31,1743@94665L31,1747@94813L33,1748@94874L33,1753@95004L21,1750@94913L3942:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(vm) ? 4 : 2;
        }
        if (($dirty & 11) != 2 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1067727542, $dirty, -1, "com.maw.ComposedNotificationsTab (AdminControllerScreens.kt:1740)");
            }
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer2.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final Context context = (Context) objConsume;
            $composer2.startReplaceableGroup(-608424414);
            ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv = $composer2.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState title$delegate = (MutableState) value$iv;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(-608424366);
            ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv2 = $composer2.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer2.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableState body$delegate = (MutableState) value$iv2;
            $composer2.endReplaceableGroup();
            final FontFamily currentFont = MainActivityKt.resolveAppFontFamily(vm.getSettings().getValue().getSelectedFontName());
            $composer2.startReplaceableGroup(-608424218);
            ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv3 = $composer2.rememberedValue();
            if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
                $composer2.updateRememberedValue(value$iv3);
            } else {
                value$iv3 = it$iv3;
            }
            final MutableState notifyOnAccepts$delegate = (MutableState) value$iv3;
            $composer2.endReplaceableGroup();
            $composer2.startReplaceableGroup(-608424157);
            ComposerKt.sourceInformation($composer2, "CC(remember):AdminControllerScreens.kt#9igjgp");
            Object it$iv4 = $composer2.rememberedValue();
            if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(true, null, 2, null);
                $composer2.updateRememberedValue(value$iv4);
            } else {
                value$iv4 = it$iv4;
            }
            final MutableState notifyOnReports$delegate = (MutableState) value$iv4;
            $composer2.endReplaceableGroup();
            Modifier modifier$iv = ScrollKt.verticalScroll$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), ScrollKt.rememberScrollState(0, $composer2, 0, 1), false, null, false, 14, null);
            Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(10));
            $composer2.startReplaceableGroup(-483455358);
            ComposerKt.sourceInformation($composer2, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
            Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
            MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer2, ((48 >> 3) & 14) | ((48 >> 3) & 112));
            int $changed$iv$iv = (48 << 3) & 112;
            $composer2.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer2, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer2, 0);
            CompositionLocalMap localMap$iv$iv = $composer2.getCurrentCompositionLocalMap();
            Function0<ComposeUiNode> constructor = ComposeUiNode.INSTANCE.getConstructor();
            Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf = LayoutKt.modifierMaterializerOf(modifier$iv);
            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
            if (!($composer2.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer2.startReusableNode();
            if ($composer2.getInserting()) {
                function0 = constructor;
                $composer2.createNode(function0);
            } else {
                function0 = constructor;
                $composer2.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m2936constructorimpl($composer2);
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), setCompositeKeyHash);
            }
            function3ModifierMaterializerOf.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer2)), $composer2, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
            $composer2.startReplaceableGroup(2058660585);
            int i = ($changed$iv$iv$iv >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer2, 276693656, "C79@3979L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i2 = ((48 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer2, -1432900440, "C1756@95102L164,1758@95303L49,1758@95276L2324,1805@97610L144,1807@97799L49,1807@97772L1077:AdminControllerScreens.kt#foq9o6");
            TextKt.m2124Text4IGK_g("🔔 بث وبث الإشعارات الجماعية لجميع الأجهزة وتحكم FCM:", (Modifier) null, AppTheme.INSTANCE.m6261getAccentGold0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 199686, 0, 130962);
            CardKt.Card(null, null, CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 608161742, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card, Composer $composer3, int $changed2) {
                    Function0<ComposeUiNode> function02;
                    Object value$iv5;
                    Object value$iv6;
                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                    ComposerKt.sourceInformation($composer3, "C1759@95368L2222:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 81) == 16 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(608161742, $changed2, -1, "com.maw.ComposedNotificationsTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1759)");
                    }
                    Modifier modifier$iv2 = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(12));
                    Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(8));
                    final MutableState<String> mutableState = title$delegate;
                    final MutableState<String> mutableState2 = body$delegate;
                    final Context context2 = context;
                    final MainViewModel mainViewModel = vm;
                    $composer3.startReplaceableGroup(-483455358);
                    ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                    Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                    MeasurePolicy measurePolicy$iv2 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                    int $changed$iv$iv2 = (54 << 3) & 112;
                    $composer3.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                    CompositionLocalMap localMap$iv$iv2 = $composer3.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                    int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
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
                    Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer3);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                        $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                        $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                    }
                    function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                    $composer3.startReplaceableGroup(2058660585);
                    int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                    int i4 = ((54 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer3, -1240978717, "C1760@95479L122,1764@95709L14,1762@95619L241,1771@95967L13,1769@95878L251,1797@97300L50,1776@96147L1429:AdminControllerScreens.kt#foq9o6");
                    TextKt.m2124Text4IGK_g("صياغة وبث إشعار عاجل فوري للأفراد والمجموعات:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200070, 0, 131026);
                    String strComposedNotificationsTab$lambda$189 = AdminControllerScreensKt.ComposedNotificationsTab$lambda$189(mutableState);
                    Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    $composer3.startReplaceableGroup(-1240978487);
                    ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv5 = $composer3.rememberedValue();
                    if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                        value$iv5 = new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$1$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState.setValue(it);
                            }
                        };
                        $composer3.updateRememberedValue(value$iv5);
                    } else {
                        value$iv5 = it$iv5;
                    }
                    $composer3.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedNotificationsTab$lambda$189, (Function1<? super String, Unit>) value$iv5, modifierFillMaxWidth$default, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6308getLambda42$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, (TextFieldColors) null, $composer3, 1573296, 0, 0, 8388536);
                    String strComposedNotificationsTab$lambda$192 = AdminControllerScreensKt.ComposedNotificationsTab$lambda$192(mutableState2);
                    Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    $composer3.startReplaceableGroup(-1240978229);
                    ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv6 = $composer3.rememberedValue();
                    if (it$iv6 == Composer.INSTANCE.getEmpty()) {
                        value$iv6 = (Function1) new Function1<String, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$1$1$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                mutableState2.setValue(it);
                            }
                        };
                        $composer3.updateRememberedValue(value$iv6);
                    } else {
                        value$iv6 = it$iv6;
                    }
                    $composer3.endReplaceableGroup();
                    OutlinedTextFieldKt.OutlinedTextField(strComposedNotificationsTab$lambda$192, (Function1<? super String, Unit>) value$iv6, modifierFillMaxWidth$default2, false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6309getLambda43$app_debug(), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, 0, (MutableInteractionSource) null, (Shape) null, (TextFieldColors) null, $composer3, 1573296, 0, 0, 8388536);
                    ButtonKt.Button(new Function0<Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$1$1$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            if (StringsKt.isBlank(AdminControllerScreensKt.ComposedNotificationsTab$lambda$189(mutableState)) || StringsKt.isBlank(AdminControllerScreensKt.ComposedNotificationsTab$lambda$192(mutableState2))) {
                                Toast.makeText(context2, "الرجاء كتابة العنوان والمحتوى لبث الإشعار سحابياً!", 0).show();
                                return;
                            }
                            String string = UUID.randomUUID().toString();
                            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                            UserNotification notifObj = new UserNotification(string, AdminControllerScreensKt.ComposedNotificationsTab$lambda$189(mutableState), AdminControllerScreensKt.ComposedNotificationsTab$lambda$192(mutableState2), "الآن", System.currentTimeMillis(), false, "info", (String) null, (String) null, 384, (DefaultConstructorMarker) null);
                            mainViewModel.addNotificationWithCategoryAndRecipient(notifObj);
                            mainViewModel.addAuditLog("الأدمن", "بث إشعار عام فوري: " + AdminControllerScreensKt.ComposedNotificationsTab$lambda$189(mutableState));
                            Toast.makeText(context2, "تم بث الإشعار المباشر وتعميمه بنجاح فوري! 🔔", 0).show();
                            mutableState.setValue("");
                            mutableState2.setValue("");
                        }
                    }, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, null, ButtonDefaults.INSTANCE.m1266buttonColorsro_MJ88(AppTheme.INSTANCE.m6265getPrimaryRed0d7_KjU(), 0L, 0L, 0L, $composer3, ButtonDefaults.$stable << 12, 14), null, null, null, null, ComposableSingletons$AdminControllerScreensKt.INSTANCE.m6310getLambda44$app_debug(), $composer3, 805306416, 492);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    $composer3.endReplaceableGroup();
                    $composer3.endNode();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 27);
            TextKt.m2124Text4IGK_g("⚙️ قنوات الإشعار والتحكيم الداخلي لـ FCM:", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), currentFont, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer2, 200070, 0, 130962);
            CardKt.Card(null, null, CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 1063258103, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card, Composer $composer3, int $changed2) {
                    Function0<ComposeUiNode> function02;
                    Object value$iv5;
                    Object value$iv6;
                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                    ComposerKt.sourceInformation($composer3, "C1808@97864L975:AdminControllerScreens.kt#foq9o6");
                    if (($changed2 & 81) == 16 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(1063258103, $changed2, -1, "com.maw.ComposedNotificationsTab.<anonymous>.<anonymous> (AdminControllerScreens.kt:1808)");
                    }
                    Modifier modifier$iv2 = PaddingKt.m562padding3ABfNKs(Modifier.INSTANCE, Dp.m5733constructorimpl(10));
                    Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.m471spacedBy0680j_4(Dp.m5733constructorimpl(4));
                    FontFamily fontFamily = currentFont;
                    final MutableState<Boolean> mutableState = notifyOnAccepts$delegate;
                    final MutableState<Boolean> mutableState2 = notifyOnReports$delegate;
                    $composer3.startReplaceableGroup(-483455358);
                    ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                    Alignment.Horizontal horizontalAlignment$iv2 = Alignment.INSTANCE.getStart();
                    MeasurePolicy measurePolicy$iv2 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, horizontalAlignment$iv2, $composer3, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                    int $changed$iv$iv2 = (54 << 3) & 112;
                    $composer3.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                    CompositionLocalMap localMap$iv$iv2 = $composer3.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor2 = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf2 = LayoutKt.modifierMaterializerOf(modifier$iv2);
                    int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                    if (!($composer3.getApplier() instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                    }
                    $composer3.startReusableNode();
                    if ($composer3.getInserting()) {
                        $composer3.createNode(constructor2);
                    } else {
                        $composer3.useNode();
                    }
                    Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m2936constructorimpl($composer3);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv2.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                        $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                        $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), setCompositeKeyHash2);
                    }
                    function3ModifierMaterializerOf2.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                    $composer3.startReplaceableGroup(2058660585);
                    int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                    int i4 = ((54 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer3, -1240976221, "C1809@97975L378,1813@98370L46,1814@98433L392:AdminControllerScreens.kt#foq9o6");
                    Modifier modifier$iv3 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.getSpaceBetween();
                    Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getCenterVertically();
                    $composer3.startReplaceableGroup(693286680);
                    ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                    int $changed$iv = ((438 >> 3) & 14) | ((438 >> 3) & 112);
                    MeasurePolicy measurePolicy$iv3 = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer3, $changed$iv);
                    int $changed$iv$iv3 = (438 << 3) & 112;
                    $composer3.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                    CompositionLocalMap localMap$iv$iv3 = $composer3.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor3 = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf3 = LayoutKt.modifierMaterializerOf(modifier$iv3);
                    int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
                    if (!($composer3.getApplier() instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                    }
                    $composer3.startReusableNode();
                    if ($composer3.getInserting()) {
                        function02 = constructor3;
                        $composer3.createNode(function02);
                    } else {
                        function02 = constructor3;
                        $composer3.useNode();
                    }
                    Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m2936constructorimpl($composer3);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv3.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                        $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                        $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), setCompositeKeyHash3);
                    }
                    function3ModifierMaterializerOf3.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                    $composer3.startReplaceableGroup(2058660585);
                    int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    int i6 = ((438 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer3, 442611044, "C1810@98135L102,1811@98310L24,1811@98258L77:AdminControllerScreens.kt#foq9o6");
                    TextKt.m2124Text4IGK_g("إرسال إشارات عند قبول الكوادر", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 130994);
                    boolean zComposedNotificationsTab$lambda$195 = AdminControllerScreensKt.ComposedNotificationsTab$lambda$195(mutableState);
                    $composer3.startReplaceableGroup(442611219);
                    ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv5 = $composer3.rememberedValue();
                    if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                        value$iv5 = new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$2$1$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                invoke(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(boolean it) {
                                AdminControllerScreensKt.ComposedNotificationsTab$lambda$196(mutableState, it);
                            }
                        };
                        $composer3.updateRememberedValue(value$iv5);
                    } else {
                        value$iv5 = it$iv5;
                    }
                    $composer3.endReplaceableGroup();
                    SwitchKt.Switch(zComposedNotificationsTab$lambda$195, (Function1) value$iv5, null, null, false, null, null, $composer3, 48, 124);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    $composer3.endReplaceableGroup();
                    $composer3.endNode();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                    long jM3436getGray0d7_KjU = Color.INSTANCE.m3436getGray0d7_KjU();
                    DividerKt.m1523Divider9IZ8Weo(null, 0.0f, Color.m3404copywmQWz5c(jM3436getGray0d7_KjU, (14 & 1) != 0 ? Color.m3408getAlphaimpl(jM3436getGray0d7_KjU) : 0.2f, (14 & 2) != 0 ? Color.m3412getRedimpl(jM3436getGray0d7_KjU) : 0.0f, (14 & 4) != 0 ? Color.m3411getGreenimpl(jM3436getGray0d7_KjU) : 0.0f, (14 & 8) != 0 ? Color.m3409getBlueimpl(jM3436getGray0d7_KjU) : 0.0f), $composer3, 384, 3);
                    Modifier modifier$iv4 = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                    Arrangement.Horizontal horizontalArrangement$iv2 = Arrangement.INSTANCE.getSpaceBetween();
                    Alignment.Vertical verticalAlignment$iv2 = Alignment.INSTANCE.getCenterVertically();
                    $composer3.startReplaceableGroup(693286680);
                    ComposerKt.sourceInformation($composer3, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                    MeasurePolicy measurePolicy$iv4 = RowKt.rowMeasurePolicy(horizontalArrangement$iv2, verticalAlignment$iv2, $composer3, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                    int $changed$iv$iv4 = (438 << 3) & 112;
                    $composer3.startReplaceableGroup(-1323940314);
                    ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                    int compositeKeyHash$iv$iv4 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                    CompositionLocalMap localMap$iv$iv4 = $composer3.getCurrentCompositionLocalMap();
                    Function0<ComposeUiNode> constructor4 = ComposeUiNode.INSTANCE.getConstructor();
                    Function3<SkippableUpdater<ComposeUiNode>, Composer, Integer, Unit> function3ModifierMaterializerOf4 = LayoutKt.modifierMaterializerOf(modifier$iv4);
                    int $changed$iv$iv$iv4 = (($changed$iv$iv4 << 9) & 7168) | 6;
                    if (!($composer3.getApplier() instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                    }
                    $composer3.startReusableNode();
                    if ($composer3.getInserting()) {
                        $composer3.createNode(constructor4);
                    } else {
                        $composer3.useNode();
                    }
                    Composer $this$Layout_u24lambda_u240$iv$iv4 = Updater.m2936constructorimpl($composer3);
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, measurePolicy$iv4, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                    Updater.m2943setimpl($this$Layout_u24lambda_u240$iv$iv4, localMap$iv$iv4, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                    Function2<ComposeUiNode, Integer, Unit> setCompositeKeyHash4 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                    if ($this$Layout_u24lambda_u240$iv$iv4.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv4.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv4))) {
                        $this$Layout_u24lambda_u240$iv$iv4.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv4));
                        $this$Layout_u24lambda_u240$iv$iv4.apply(Integer.valueOf(compositeKeyHash$iv$iv4), setCompositeKeyHash4);
                    }
                    function3ModifierMaterializerOf4.invoke(SkippableUpdater.m2927boximpl(SkippableUpdater.m2928constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv4 >> 3) & 112));
                    $composer3.startReplaceableGroup(2058660585);
                    int i7 = ($changed$iv$iv$iv4 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer3, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                    RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                    int i8 = ((438 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer3, 442611502, "C1815@98593L116,1816@98782L24,1816@98730L77:AdminControllerScreens.kt#foq9o6");
                    TextKt.m2124Text4IGK_g("تنبيه الإدارة عند ورود بلاغ جودة شكاوى جديد", (Modifier) null, Color.INSTANCE.m3443getWhite0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, (FontWeight) null, fontFamily, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3462, 0, 130994);
                    boolean zComposedNotificationsTab$lambda$198 = AdminControllerScreensKt.ComposedNotificationsTab$lambda$198(mutableState2);
                    $composer3.startReplaceableGroup(442611691);
                    ComposerKt.sourceInformation($composer3, "CC(remember):AdminControllerScreens.kt#9igjgp");
                    Object it$iv6 = $composer3.rememberedValue();
                    if (it$iv6 == Composer.INSTANCE.getEmpty()) {
                        value$iv6 = new Function1<Boolean, Unit>() { // from class: com.maw.AdminControllerScreensKt$ComposedNotificationsTab$1$2$1$2$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                                invoke(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(boolean it) {
                                AdminControllerScreensKt.ComposedNotificationsTab$lambda$199(mutableState2, it);
                            }
                        };
                        $composer3.updateRememberedValue(value$iv6);
                    } else {
                        value$iv6 = it$iv6;
                    }
                    $composer3.endReplaceableGroup();
                    SwitchKt.Switch(zComposedNotificationsTab$lambda$198, (Function1) value$iv6, null, null, false, null, null, $composer3, 48, 124);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    $composer3.endReplaceableGroup();
                    $composer3.endNode();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    $composer3.endReplaceableGroup();
                    $composer3.endNode();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), $composer2, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 27);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.ComposedNotificationsTab.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    AdminControllerScreensKt.ComposedNotificationsTab(vm, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedNotificationsTab$lambda$189(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedNotificationsTab$lambda$192(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedNotificationsTab$lambda$195(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedNotificationsTab$lambda$196(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedNotificationsTab$lambda$198(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedNotificationsTab$lambda$199(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x040b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ComposedDatabaseBackupSchedulerTab(final com.maw.MainViewModel r68, androidx.compose.runtime.Composer r69, final int r70) {
        /*
            Method dump skipped, instruction units count: 1060
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.ComposedDatabaseBackupSchedulerTab(com.maw.MainViewModel, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ComposedDatabaseBackupSchedulerTab$lambda$202(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedDatabaseBackupSchedulerTab$lambda$203(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedDatabaseBackupSchedulerTab$lambda$205(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ComposedDatabaseBackupSchedulerTab$lambda$208(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    private static final boolean ComposedDatabaseBackupSchedulerTab$lambda$211(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ComposedDatabaseBackupSchedulerTab$lambda$212(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    public static final String triggerLocalBackupSimulation(Context context, MainViewModel vm) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(vm, "vm");
        try {
            File backupDir = new File(context.getFilesDir(), "backups");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            File backupFile = new File(backupDir, "YemenServices_SQL_Backup_" + System.currentTimeMillis() + ".txt");
            FileWriter writer = new FileWriter(backupFile);
            writer.append((CharSequence) "# BANNER EXPORT DATA DUMP\n");
            for (Object element$iv : vm.getBanners().getValue()) {
                Banner bn = (Banner) element$iv;
                writer.append((CharSequence) ("BANNER: " + bn.getId() + " - " + bn.getDescription() + " - " + bn.getImageUrl() + "\n"));
                backupDir = backupDir;
            }
            writer.append((CharSequence) "\n# PROVIDERS EXPORT DATA DUMP\n");
            Iterable $this$forEach$iv = vm.getProviders().getValue();
            int $i$f$forEach = 0;
            for (Object element$iv2 : $this$forEach$iv) {
                Provider pr = (Provider) element$iv2;
                writer.append((CharSequence) ("PROVIDER: " + pr.getId() + " - " + pr.getName() + " - " + pr.getPhone() + " - " + pr.getCategory() + " - " + pr.getCity() + "\n"));
                $this$forEach$iv = $this$forEach$iv;
                $i$f$forEach = $i$f$forEach;
            }
            writer.flush();
            writer.close();
            String name = backupFile.getName();
            Intrinsics.checkNotNull(name);
            return name;
        } catch (Exception e) {
            return "local_memory_backup.sql";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:156:0x0b0c A[LOOP:1: B:154:0x0b06->B:156:0x0b0c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01b7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void BookingsAdminTab(final com.maw.MainViewModel r169, final java.util.List<com.maw.Booking> r170, androidx.compose.runtime.Composer r171, final int r172) {
        /*
            Method dump skipped, instruction units count: 3040
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.BookingsAdminTab(com.maw.MainViewModel, java.util.List, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String BookingsAdminTab$lambda$218(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    private static final String BookingsAdminTab$lambda$221(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    private static final List<Category> BookingsAdminTab$lambda$223(State<? extends List<Category>> state) {
        Object thisObj$iv = state.getValue();
        return (List) thisObj$iv;
    }

    private static final AppSettings BookingsAdminTab$lambda$224(State<AppSettings> state) {
        Object thisObj$iv = state.getValue();
        return (AppSettings) thisObj$iv;
    }

    public static final void BookingAdminCard(final Booking booking, final MainViewModel viewModel, final FontFamily currentFont, final Function1<? super BookingStatus, Unit> onStatusChange, final Function0<Unit> onDelete, Composer $composer, final int $changed) {
        long parsedColor;
        Composer $composer2;
        Intrinsics.checkNotNullParameter(booking, "booking");
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        Intrinsics.checkNotNullParameter(currentFont, "currentFont");
        Intrinsics.checkNotNullParameter(onStatusChange, "onStatusChange");
        Intrinsics.checkNotNullParameter(onDelete, "onDelete");
        Composer $composer3 = $composer.startRestartGroup(1885027226);
        ComposerKt.sourceInformation($composer3, "C(BookingAdminCard)P(!1,4!1,3)2163@115122L7,2176@115553L49,2175@115517L12295:AdminControllerScreens.kt#foq9o6");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(booking) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changed(viewModel) ? 32 : 16;
        }
        if (($changed & 896) == 0) {
            $dirty |= $composer3.changed(currentFont) ? 256 : 128;
        }
        if (($changed & 7168) == 0) {
            $dirty |= $composer3.changedInstance(onStatusChange) ? 2048 : 1024;
        }
        if ((57344 & $changed) == 0) {
            $dirty |= $composer3.changedInstance(onDelete) ? 16384 : 8192;
        }
        int $dirty2 = $dirty;
        if ((46811 & $dirty2) != 9362 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1885027226, $dirty2, -1, "com.maw.BookingAdminCard (AdminControllerScreens.kt:2162)");
            }
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer3, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object objConsume = $composer3.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            Context context = (Context) objConsume;
            String status = booking.getStatus();
            if (status == null) {
                status = "pending";
            }
            String bStatus = status;
            String statusColor = viewModel.getBookingStatusColor(bStatus);
            String statusLabel = viewModel.getBookingStatusLabel(bStatus);
            float progress = viewModel.getBookingProgress(bStatus);
            try {
                parsedColor = ColorKt.Color(android.graphics.Color.parseColor(statusColor));
            } catch (Exception e) {
                parsedColor = Color.INSTANCE.m3436getGray0d7_KjU();
            }
            $composer2 = $composer3;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m831RoundedCornerShape0680j_4(Dp.m5733constructorimpl(12)), CardDefaults.INSTANCE.m1287cardColorsro_MJ88(AppTheme.INSTANCE.m6266getSurfaceDark0d7_KjU(), 0L, 0L, 0L, $composer3, CardDefaults.$stable << 12, 14), null, BorderStrokeKt.m237BorderStrokecXLIe8U(Dp.m5733constructorimpl(1), ColorKt.Color(4280432185L)), ComposableLambdaKt.composableLambda($composer2, 156583692, true, new AnonymousClass1(progress, parsedColor, booking, currentFont, bStatus, statusLabel, onDelete, onStatusChange, viewModel, context)), $composer2, 221190, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = $composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.maw.AdminControllerScreensKt.BookingAdminCard.2
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
                    AdminControllerScreensKt.BookingAdminCard(booking, viewModel, currentFont, onStatusChange, onDelete, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.maw.AdminControllerScreensKt$BookingAdminCard$1, reason: invalid class name */
    /* JADX INFO: compiled from: AdminControllerScreens.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
        final /* synthetic */ String $bStatus;
        final /* synthetic */ Booking $booking;
        final /* synthetic */ Context $context;
        final /* synthetic */ FontFamily $currentFont;
        final /* synthetic */ Function0<Unit> $onDelete;
        final /* synthetic */ Function1<BookingStatus, Unit> $onStatusChange;
        final /* synthetic */ long $parsedColor;
        final /* synthetic */ float $progress;
        final /* synthetic */ String $statusLabel;
        final /* synthetic */ MainViewModel $viewModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(float f, long j, Booking booking, FontFamily fontFamily, String str, String str2, Function0<Unit> function0, Function1<? super BookingStatus, Unit> function1, MainViewModel mainViewModel, Context context) {
            super(3);
            this.$progress = f;
            this.$parsedColor = j;
            this.$booking = booking;
            this.$currentFont = fontFamily;
            this.$bStatus = str;
            this.$statusLabel = str2;
            this.$onDelete = function0;
            this.$onStatusChange = function1;
            this.$viewModel = mainViewModel;
            this.$context = context;
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
            invoke(columnScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x08fe  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x090a  */
        /* JADX WARN: Removed duplicated region for block: B:105:0x090e  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x093d  */
        /* JADX WARN: Removed duplicated region for block: B:112:0x0953 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:116:0x09d5  */
        /* JADX WARN: Removed duplicated region for block: B:117:0x09ed  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x0a6d  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x0a7d  */
        /* JADX WARN: Removed duplicated region for block: B:124:0x0ad3  */
        /* JADX WARN: Removed duplicated region for block: B:125:0x0ae3  */
        /* JADX WARN: Removed duplicated region for block: B:128:0x0b43  */
        /* JADX WARN: Removed duplicated region for block: B:129:0x0b5c  */
        /* JADX WARN: Removed duplicated region for block: B:132:0x0b79  */
        /* JADX WARN: Removed duplicated region for block: B:185:0x0df7  */
        /* JADX WARN: Removed duplicated region for block: B:188:0x0e1d  */
        /* JADX WARN: Removed duplicated region for block: B:189:0x0e2b  */
        /* JADX WARN: Removed duplicated region for block: B:192:0x0eb8  */
        /* JADX WARN: Removed duplicated region for block: B:195:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0210  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x021c  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0222  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0255  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x026b  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0336  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0342  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0348  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x037b  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0391 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x03f9  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0453  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0565  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0571  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0577  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x05aa  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x05c0 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x062c  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0633  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x06e9  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x073c  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x078f  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x0797  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x07f4  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x080c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void invoke(androidx.compose.foundation.layout.ColumnScope r127, androidx.compose.runtime.Composer r128, int r129) {
            /*
                Method dump skipped, instruction units count: 3772
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.maw.AdminControllerScreensKt.AnonymousClass1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
        }

        private static final boolean invoke$lambda$32$lambda$31$lambda$4(MutableState<Boolean> mutableState) {
            MutableState<Boolean> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue().booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$32$lambda$31$lambda$5(MutableState<Boolean> mutableState, boolean value) {
            mutableState.setValue(Boolean.valueOf(value));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$32$lambda$31$lambda$10(MutableState<Boolean> mutableState, boolean value) {
            mutableState.setValue(Boolean.valueOf(value));
        }

        private static final boolean invoke$lambda$32$lambda$31$lambda$9(MutableState<Boolean> mutableState) {
            MutableState<Boolean> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue().booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$12(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$15(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$18(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$21(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$24(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$lambda$32$lambda$31$lambda$27(MutableState<String> mutableState) {
            MutableState<String> $this$getValue$iv = mutableState;
            return $this$getValue$iv.getValue();
        }
    }
}
