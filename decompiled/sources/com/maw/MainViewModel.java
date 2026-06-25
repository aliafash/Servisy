package com.maw;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.autofill.HintConstants;
import androidx.compose.ui.graphics.ColorKt;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.Ints;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.serialization.StringFormat;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.BooleanSerializer;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.StringSerializer;
import kotlinx.serialization.json.Json;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0010\u0006\n\u0002\b\u001f\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b@\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0090\u0001\u001a\u00020s2\u0007\u0010\u0091\u0001\u001a\u00020\u00062\u0007\u0010\u0092\u0001\u001a\u00020\"J\u0019\u0010\u0093\u0001\u001a\u00020s2\u0007\u0010\u0094\u0001\u001a\u00020\"2\u0007\u0010\u0095\u0001\u001a\u00020\"J\u0019\u0010\u0096\u0001\u001a\u00020s2\u0007\u0010\u0097\u0001\u001a\u00020\n2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010\u0098\u0001\u001a\u00020s2\u0007\u0010\u0099\u0001\u001a\u00020\u00152\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010\u009a\u0001\u001a\u00020s2\u0007\u0010\u009b\u0001\u001a\u00020\u001b2\u0007\u0010\u0094\u0001\u001a\u00020\"J\"\u0010\u009c\u0001\u001a\u00020s2\u0007\u0010\u009d\u0001\u001a\u00020\"2\u0007\u0010\u009e\u0001\u001a\u00020\"2\u0007\u0010\u009f\u0001\u001a\u00020\"J\u0010\u0010 \u0001\u001a\u00020s2\u0007\u0010¡\u0001\u001a\u00020'J\u0019\u0010¢\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"2\u0007\u0010¤\u0001\u001a\u00020\"J\u0019\u0010¥\u0001\u001a\u00020s2\u0007\u0010¦\u0001\u001a\u00020+2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010§\u0001\u001a\u00020s2\u0007\u0010¨\u0001\u001a\u000200J\u0019\u0010©\u0001\u001a\u00020s2\u0007\u0010ª\u0001\u001a\u00020)2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010«\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010\u00ad\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J\u0010\u0010®\u0001\u001a\u00020s2\u0007\u0010¯\u0001\u001a\u00020\"J\u0012\u0010°\u0001\u001a\u00020s2\t\b\u0002\u0010±\u0001\u001a\u00020mJ0\u0010²\u0001\u001a\u00030³\u00012\b\u0010´\u0001\u001a\u00030³\u00012\b\u0010µ\u0001\u001a\u00030³\u00012\b\u0010¶\u0001\u001a\u00030³\u00012\b\u0010·\u0001\u001a\u00030³\u0001J\u0010\u0010¸\u0001\u001a\u00020#2\u0007\u0010¹\u0001\u001a\u00020\rJ\u0010\u0010º\u0001\u001a\u00020#2\u0007\u0010»\u0001\u001a\u00020\"J\u0019\u0010¼\u0001\u001a\u00020#2\u0007\u0010½\u0001\u001a\u00020\"2\u0007\u0010¾\u0001\u001a\u00020\"J\u0010\u0010¿\u0001\u001a\u00020s2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010À\u0001\u001a\u00020s2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010Á\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J\"\u0010Â\u0001\u001a\u00020s2\u0007\u0010Ã\u0001\u001a\u00020\"2\u0007\u0010Ä\u0001\u001a\u00020\"2\u0007\u0010\u009b\u0001\u001a\u00020\"J\u0019\u0010Å\u0001\u001a\u00020s2\u0007\u0010Æ\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010Ç\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u001b\u0010È\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\t\b\u0002\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010É\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010Ê\u0001\u001a\u00020s2\u0007\u0010Ë\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010Ì\u0001\u001a\u00020s2\u0007\u0010Í\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010Î\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010Ï\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"J\u0019\u0010Ð\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0007\u0010Ñ\u0001\u001a\u00020sJ\u0011\u0010Ò\u0001\u001a\u00030Ó\u00012\u0007\u0010Ô\u0001\u001a\u00020\"J\u0010\u0010Õ\u0001\u001a\u00020\"2\u0007\u0010Ô\u0001\u001a\u00020\"J\u0010\u0010Ö\u0001\u001a\u00020\"2\u0007\u0010Ô\u0001\u001a\u00020\"J\u0011\u0010×\u0001\u001a\u00020\"2\b\u0010Ø\u0001\u001a\u00030³\u0001J\u001e\u0010Ù\u0001\u001a\u0010\u0012\u0005\u0012\u00030³\u0001\u0012\u0005\u0012\u00030³\u00010!2\u0007\u0010£\u0001\u001a\u00020\"J\u0012\u0010Ú\u0001\u001a\u00020\"2\u0007\u0010¯\u0001\u001a\u00020\"H\u0002J\u0011\u0010Û\u0001\u001a\u00020s2\b\u0010Ü\u0001\u001a\u00030Ý\u0001J\u0010\u0010Þ\u0001\u001a\u00020#2\u0007\u0010¹\u0001\u001a\u00020\rJ\u0007\u0010ß\u0001\u001a\u00020sJ\u0007\u0010à\u0001\u001a\u00020sJ\u0007\u0010á\u0001\u001a\u00020sJ\u0007\u0010â\u0001\u001a\u00020sJ\u0010\u0010ã\u0001\u001a\u00020s2\u0007\u0010¦\u0001\u001a\u00020)J\"\u0010ä\u0001\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010å\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u001b\u0010æ\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"2\t\b\u0002\u0010å\u0001\u001a\u00020\"J\u0019\u0010ç\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"2\u0007\u0010è\u0001\u001a\u00020mJW\u0010é\u0001\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"2\u0007\u0010ê\u0001\u001a\u00020\"2\u0007\u0010ë\u0001\u001a\u00020\"2\u0007\u0010ì\u0001\u001a\u00020\"2\t\b\u0002\u0010í\u0001\u001a\u00020\"2\t\b\u0002\u0010î\u0001\u001a\u00020\"2\t\b\u0002\u0010ï\u0001\u001a\u00020\"2\t\b\u0002\u0010ð\u0001\u001a\u00020\"J\u0010\u0010ñ\u0001\u001a\u00020s2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0016\u0010ò\u0001\u001a\u00020s2\r\u0010ó\u0001\u001a\b\u0012\u0004\u0012\u00020s0rJ\u001b\u0010ô\u0001\u001a\u00020s2\u0007\u0010õ\u0001\u001a\u00020\"2\u0007\u0010ö\u0001\u001a\u00020\"H\u0002J\u0012\u0010÷\u0001\u001a\u00020s2\t\b\u0002\u0010ø\u0001\u001a\u00020mJ\u0010\u0010ù\u0001\u001a\u00020s2\u0007\u0010ú\u0001\u001a\u00020\"J+\u0010û\u0001\u001a\u00020s2\u0007\u0010ü\u0001\u001a\u00020\"2\u0007\u0010ý\u0001\u001a\u00020\"2\u0007\u0010þ\u0001\u001a\u00020\"2\u0007\u0010ÿ\u0001\u001a\u00020\"J\t\u0010\u0080\u0002\u001a\u00020sH\u0002J\"\u0010\u0081\u0002\u001a\u00020s2\u0007\u0010\u0082\u0002\u001a\u00020\"2\u0007\u0010£\u0001\u001a\u00020\"2\u0007\u0010ê\u0001\u001a\u00020\"J\u0010\u0010\u0083\u0002\u001a\u00020s2\u0007\u0010\u0084\u0002\u001a\u000202J\u0007\u0010\u0085\u0002\u001a\u00020sJ\u0010\u0010\u0086\u0002\u001a\u00020s2\u0007\u0010ê\u0001\u001a\u00020\"J\u0010\u0010\u0087\u0002\u001a\u00020s2\u0007\u0010Æ\u0001\u001a\u00020\"J\u0010\u0010\u0088\u0002\u001a\u00020s2\u0007\u0010¹\u0001\u001a\u00020\rJ\u0010\u0010\u0089\u0002\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J\u0010\u0010\u008a\u0002\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J=\u0010\u008b\u0002\u001a\u00020s2\u0007\u0010¬\u0001\u001a\u00020\"2\u0007\u0010\u008c\u0002\u001a\u00020#2\u0007\u0010\u008d\u0002\u001a\u00020#2\u0007\u0010\u008e\u0002\u001a\u00020#2\u0007\u0010\u008f\u0002\u001a\u00020#2\u0007\u0010\u0090\u0002\u001a\u00020\"J\u0010\u0010\u0091\u0002\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J\u0010\u0010\u0092\u0002\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"J\"\u0010\u0093\u0002\u001a\u00020s2\u0007\u0010\u0094\u0002\u001a\u00020\"2\u0007\u0010\u0095\u0002\u001a\u00020\u00062\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0019\u0010\u0096\u0002\u001a\u00020s2\u0007\u0010\u0097\u0002\u001a\u0002042\u0007\u0010\u0094\u0001\u001a\u00020\"J\u001b\u0010\u0098\u0002\u001a\u00020s2\u0007\u0010\u0097\u0001\u001a\u00020\u00112\t\b\u0002\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010\u0099\u0002\u001a\u00020s2\u0007\u0010\u009a\u0002\u001a\u00020\u000fJ\u001a\u0010\u009b\u0002\u001a\u00020s2\u0007\u0010\u009c\u0002\u001a\u00020\"2\b\u0010\u009d\u0002\u001a\u00030\u009e\u0002J\u0010\u0010\u009f\u0002\u001a\u00020s2\u0007\u0010\u0086\u0001\u001a\u00020\u0013J\u0019\u0010 \u0002\u001a\u00020s2\u0007\u0010\u0099\u0001\u001a\u00020\u00152\u0007\u0010\u0094\u0001\u001a\u00020\"J\"\u0010¡\u0002\u001a\u00020s2\u0007\u0010Ë\u0001\u001a\u00020\"2\u0007\u0010¢\u0002\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u0010\u0010£\u0002\u001a\u00020s2\u0007\u0010¤\u0002\u001a\u00020\u001fJ\u0019\u0010¥\u0002\u001a\u00020s2\u0007\u0010¦\u0001\u001a\u00020+2\u0007\u0010\u0094\u0001\u001a\u00020\"J\u001f\u0010¦\u0002\u001a\u00020s2\u0007\u0010£\u0001\u001a\u00020\"2\r\u0010§\u0002\u001a\b\u0012\u0004\u0012\u00020\"0\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010 \u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020#0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00103\u001a\b\u0012\u0004\u0012\u0002040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000506¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u001d\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000506¢\u0006\b\n\u0000\u001a\u0004\b:\u00108R\u001d\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000506¢\u0006\b\n\u0000\u001a\u0004\b<\u00108R\u001d\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f06¢\u0006\b\n\u0000\u001a\u0004\b>\u00108R\u001d\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u001d\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\f0\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010AR\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u000f06¢\u0006\b\n\u0000\u001a\u0004\bE\u00108R\u001d\u0010F\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000506¢\u0006\b\n\u0000\u001a\u0004\bG\u00108R\u0010\u0010H\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010J\u001a\b\u0012\u0004\u0012\u00020\u001306¢\u0006\b\n\u0000\u001a\u0004\bK\u00108R\u001d\u0010L\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u000506¢\u0006\b\n\u0000\u001a\u0004\bM\u00108R\u001d\u0010N\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000506¢\u0006\b\n\u0000\u001a\u0004\bO\u00108R\u001d\u0010P\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u000506¢\u0006\b\n\u0000\u001a\u0004\bQ\u00108R\u0010\u0010R\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010S\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u000506¢\u0006\b\n\u0000\u001a\u0004\bT\u00108R\u0019\u0010U\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010AR\u0017\u0010W\u001a\b\u0012\u0004\u0012\u00020\u001d06¢\u0006\b\n\u0000\u001a\u0004\bX\u00108R\u0014\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00150\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020+0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\\\u001a\b\u0012\u0004\u0012\u0002020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010]\u001a\b\u0012\u0004\u0012\u00020\u001f06¢\u0006\b\n\u0000\u001a\u0004\b^\u00108R\u0010\u0010_\u001a\u0004\u0018\u00010`X\u0082\u000e¢\u0006\u0002\n\u0000R)\u0010a\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!0\u000506¢\u0006\b\n\u0000\u001a\u0004\bb\u00108R\u0017\u0010c\u001a\b\u0012\u0004\u0012\u00020#0\u0004¢\u0006\b\n\u0000\u001a\u0004\bc\u0010AR\u0017\u0010d\u001a\b\u0012\u0004\u0012\u00020#06¢\u0006\b\n\u0000\u001a\u0004\bd\u00108R\u0017\u0010e\u001a\b\u0012\u0004\u0012\u00020#0\u0004¢\u0006\b\n\u0000\u001a\u0004\be\u0010AR\u0017\u0010f\u001a\b\u0012\u0004\u0012\u00020#06¢\u0006\b\n\u0000\u001a\u0004\bf\u00108R\u0017\u0010g\u001a\b\u0012\u0004\u0012\u00020#0\u0004¢\u0006\b\n\u0000\u001a\u0004\bg\u0010AR\u0017\u0010h\u001a\b\u0012\u0004\u0012\u00020#0\u0004¢\u0006\b\n\u0000\u001a\u0004\bh\u0010AR\u0017\u0010i\u001a\b\u0012\u0004\u0012\u00020\"0\u0004¢\u0006\b\n\u0000\u001a\u0004\bj\u0010AR\u0010\u0010k\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u0019\u0010l\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010m0\u0004¢\u0006\b\n\u0000\u001a\u0004\bn\u0010AR\u001d\u0010o\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0\u000506¢\u0006\b\n\u0000\u001a\u0004\bp\u00108R\u0016\u0010q\u001a\n\u0012\u0004\u0012\u00020s\u0018\u00010rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u000506¢\u0006\b\n\u0000\u001a\u0004\bu\u00108R\u001d\u0010v\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u000506¢\u0006\b\n\u0000\u001a\u0004\bw\u00108R\u001d\u0010x\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0y¢\u0006\b\n\u0000\u001a\u0004\bz\u0010{R\u001d\u0010|\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u000506¢\u0006\b\n\u0000\u001a\u0004\b}\u00108R\u001d\u0010~\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0y¢\u0006\b\n\u0000\u001a\u0004\b\u007f\u0010{R\u001f\u0010\u0080\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u000506¢\u0006\t\n\u0000\u001a\u0005\b\u0081\u0001\u00108R\u001f\u0010\u0082\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\u000506¢\u0006\t\n\u0000\u001a\u0005\b\u0083\u0001\u00108R\u001f\u0010\u0084\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002020\u000506¢\u0006\t\n\u0000\u001a\u0005\b\u0085\u0001\u00108R\u0019\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020406¢\u0006\t\n\u0000\u001a\u0005\b\u0087\u0001\u00108R\u0012\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0019\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020#0\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008b\u0001\u0010AR\u001f\u0010\u008c\u0001\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0y¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010{R\u001f\u0010\u008e\u0001\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0y¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010{¨\u0006¨\u0002"}, d2 = {"Lcom/maw/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_adminAccounts", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/maw/AdminAccount;", "_auditLogs", "Lcom/maw/AuditLog;", "_banners", "Lcom/maw/Banner;", "_blockedChatParticipants", "", "Lcom/maw/ChatParticipantType;", "_bookingFormFields", "Lcom/maw/BookingFormFields;", "_bookings", "Lcom/maw/Booking;", "_cardSettings", "Lcom/maw/CardSettings;", "_categories", "Lcom/maw/Category;", "_chatMessages", "Lcom/maw/ChatMessage;", "_chats", "Lcom/maw/Chat;", "_cities", "Lcom/maw/City;", "_currentUserProfile", "Lcom/maw/UserProfile;", "_distributionMode", "Lcom/maw/BookingDistributionMode;", "_geminiMessages", "Lkotlin/Pair;", "", "", "_isAdminLoggedIn", "_isGeminiThinking", "_notifications", "Lcom/maw/UserNotification;", "_pendingRequests", "Lcom/maw/PendingProvider;", "_pendingTechnicians", "Lcom/maw/Provider;", "_providers", "_relations", "Lcom/maw/ProviderCategoryRelation;", "_reports", "Lcom/maw/Report;", "_reviews", "Lcom/maw/Review;", "_settings", "Lcom/maw/AppSettings;", "adminAccounts", "Lkotlinx/coroutines/flow/StateFlow;", "getAdminAccounts", "()Lkotlinx/coroutines/flow/StateFlow;", "auditLogs", "getAuditLogs", "banners", "getBanners", "blockedChatParticipants", "getBlockedChatParticipants", "blockedChatProviders", "getBlockedChatProviders", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "blockedChatUsers", "getBlockedChatUsers", "bookingFormFields", "getBookingFormFields", "bookings", "getBookings", "bookingsListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "cardSettings", "getCardSettings", "categoriesState", "getCategoriesState", "chatMessages", "getChatMessages", "chats", "getChats", "chatsListener", "citiesState", "getCitiesState", "currentChatRoomId", "getCurrentChatRoomId", "currentUserProfile", "getCurrentUserProfile", "defaultCategories", "defaultCities", "defaultProviders", "defaultReviews", "distributionMode", "getDistributionMode", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "geminiMessages", "getGeminiMessages", "isAdminChatDisabledOnly", "isAdminLoggedIn", "isAllChatDisabled", "isGeminiThinking", "isProviderChatDisabledOnly", "isUserChatDisabledOnly", "loggedInUsername", "getLoggedInUsername", "messagesListener", "navigationTargetTab", "", "getNavigationTargetTab", "notifications", "getNotifications", "onRegistrationSuccessCallback", "Lkotlin/Function0;", "", "pendingRequests", "getPendingRequests", "pendingTechnicians", "getPendingTechnicians", "pinnedOverrides", "", "getPinnedOverrides", "()Ljava/util/Map;", "providers", "getProviders", "recommendedOverrides", "getRecommendedOverrides", "relations", "getRelations", "reports", "getReports", "reviewsState", "getReviewsState", "settings", "getSettings", "sharedPrefs", "Landroid/content/SharedPreferences;", "showRegistrationDialog", "getShowRegistrationDialog", "subscribedOverrides", "getSubscribedOverrides", "verifiedOverrides", "getVerifiedOverrides", "addAdminAccount", "account", "creator", "addAuditLog", "admin", "action", "addBanner", "b", "addCategory", "cat", "addCity", "city", "addNotification", "title", "body", "statusType", "addNotificationWithCategoryAndRecipient", "not", "addPortfolioImage", "providerId", "imageBase64", "addProviderManual", "p", "addReport", "rep", "approveProviderRequest", "pp", "approveReport", "id", "approveTechnician", "askGemini", "prompt", "autoCleanupData", "daysToKeep", "calculateDistance", "", "lat1", "lon1", "lat2", "lon2", "canParticipateInChat", "participantType", "checkAdminPassword", "password", "checkAdminThreeLayersLogin", "user", "pass", "cleanTemporaryDataAndFiles", "clearAllChatHistory", "clearPortfolio", "completeRegistration", HintConstants.AUTOFILL_HINT_NAME, "phone", "deleteAdminAccount", HintConstants.AUTOFILL_HINT_USERNAME, "deleteBanner", "deleteBooking", "deleteCategory", "deleteChatMessage", "msgId", "deleteChatRoom", "roomId", "deleteCity", "deleteNotification", "deleteProvider", "enableChat", "getBookingProgress", "", NotificationCompat.CATEGORY_STATUS, "getBookingStatusColor", "getBookingStatusLabel", "getDistanceString", "distanceInKm", "getProviderCoordinates", "getSimulatedYemeniLocalReply", "initCache", "context", "Landroid/content/Context;", "isChatBlockedFor", "loadCardSettings", "loadPendingTechnicians", "logoutAdmin", "markAllNotificationsAsRead", "registerPendingProvider", "rejectProviderRequest", "reason", "rejectTechnician", "removePortfolioImage", "index", "requestServiceAppointment", "providerName", "serviceDetails", "preferredTime", "tripleName", HintConstants.AUTOFILL_HINT_PHONE_NUMBER, "serviceType", "residencePlc", "resetAppToFactoryDefaults", "runWithActiveUser", "onVerified", "saveToCache", "key", "valueJson", "scheduleAutoCleanup", "days", "sendChatDisabledNotification", "message", "sendChatMessage", "chatId", "senderName", "senderType", "messageText", "setupFirebaseRealtimeListener", "startChatWithProvider", "userId", "submitReview", "review", "syncPrivateListeners", "toggleBlockProviderChat", "toggleBlockUserChat", "toggleChatParticipant", "toggleProviderPin", "toggleProviderRecommendation", "toggleProviderStatus", "isPinned", "isRecommended", "isVerified", "isSubscribed", "adminName", "toggleProviderSubscription", "toggleProviderVerification", "updateAdminAccount", "oldUsername", "updatedAccount", "updateAppSettings", "newSettings", "updateBooking", "updateBookingFormFields", "fields", "updateBookingStatus", "bookingId", "newStatus", "Lcom/maw/BookingStatus;", "updateCardSettings", "updateCategory", "updateChatMessage", "newContent", "updateDistributionMode", "mode", "updateProviderManual", "updateProviderPortfolio", "images", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainViewModel extends ViewModel {
    public static final int $stable = 8;
    private final MutableStateFlow<List<AdminAccount>> _adminAccounts;
    private final StateFlow<List<AdminAccount>> adminAccounts;
    private final MutableStateFlow<Set<String>> blockedChatProviders;
    private final MutableStateFlow<Set<String>> blockedChatUsers;
    private ListenerRegistration bookingsListener;
    private ListenerRegistration chatsListener;
    private FirebaseFirestore firestore;
    private final MutableStateFlow<Boolean> isAdminChatDisabledOnly;
    private final MutableStateFlow<Boolean> isAllChatDisabled;
    private final MutableStateFlow<Boolean> isProviderChatDisabledOnly;
    private final MutableStateFlow<Boolean> isUserChatDisabledOnly;
    private ListenerRegistration messagesListener;
    private Function0<Unit> onRegistrationSuccessCallback;
    private SharedPreferences sharedPrefs;
    private final Map<String, Boolean> pinnedOverrides = new LinkedHashMap();
    private final Map<String, Boolean> recommendedOverrides = new LinkedHashMap();
    private final Map<String, Boolean> verifiedOverrides = new LinkedHashMap();
    private final Map<String, Boolean> subscribedOverrides = new LinkedHashMap();
    private final List<Category> defaultCategories = CollectionsKt.listOf((Object[]) new Category[]{new Category("electricity", "كهرباء وتمديدات", "Electrical Works", "⚡", 1, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("plumbing", "سباكة وصيانة صحية", "Plumbing Services", "🔧", 2, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("maintenance", "صيانة عامة وأعطال", "General Maintenance", "🛠️", 3, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("carpentry", "نجارة وأثاث", "Carpentry & Decor", "🪚", 4, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("conditioning", "تكييف وتبريد", "AC & Refrigeration", "❄️", 5, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("construction", "مقاولات وبناء", "Construction & Paints", "🧱", 6, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("computers", "برمجة وصيانة هواتف", "Mobile & PC Maintenance", "💻", 7, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("medicine", "الطب والرعاية الصحية", "Medicine & Healthcare", "🩺", 8, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("education", "التعليم والتدريس", "Education & Teaching", "🎓", 9, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("law", "المحاماة والاستشارات القانونية", "Law & Legal Services", "⚖️", 10, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("engineering", "الهندسة والاستشارات الفنية", "Engineering & Consulting", "📐", 11, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category(NotificationCompat.CATEGORY_TRANSPORT, "النقل وشحن البضائع", "Transport & Shipping", "🚚", 12, (String) null, (String) null, false, false, 480, (DefaultConstructorMarker) null), new Category("dentistry", "طب وجراحة الأسنان", "Dentistry Services", "🦷", 13, "medicine", (String) null, false, false, 448, (DefaultConstructorMarker) null), new Category("pharmacy", "الصيدلة والأدوية", "Pharmacy & Medicine", "💊", 14, "medicine", (String) null, false, false, 448, (DefaultConstructorMarker) null), new Category("languages_edu", "تعليم لغات أجنبية", "Foreign Languages", "🗣️", 15, "education", (String) null, false, false, 448, (DefaultConstructorMarker) null), new Category("school_tutoring", "مدرسين وتقوية خصوصي", "Tutoring", "📖", 16, "education", (String) null, false, false, 448, (DefaultConstructorMarker) null), new Category("architect_eng", "هندسة معمارية وتصميم", "Architecture", "🏗️", 17, "engineering", (String) null, false, false, 448, (DefaultConstructorMarker) null), new Category("software_eng", "هندسة برمجيات وتقنية", "Software Engineering", "💻", 18, "engineering", (String) null, false, false, 448, (DefaultConstructorMarker) null)});
    private final List<City> defaultCities = CollectionsKt.listOf((Object[]) new City[]{new City("sanaa", "صنعاء", "Sanaa"), new City("aden", "عدن", "Aden"), new City("taiz", "تعز", "Taiz"), new City("hodeidah", "الحديدة", "Hodeidah"), new City("hadramout", "حضرموت", "Hadramout"), new City("ibb", "إب", "Ibb")});
    private final List<Provider> defaultProviders = CollectionsKt.emptyList();
    private final MutableStateFlow<List<Category>> _categories = StateFlowKt.MutableStateFlow(this.defaultCategories);
    private final StateFlow<List<Category>> categoriesState = FlowKt.asStateFlow(this._categories);
    private final MutableStateFlow<List<City>> _cities = StateFlowKt.MutableStateFlow(this.defaultCities);
    private final StateFlow<List<City>> citiesState = FlowKt.asStateFlow(this._cities);
    private final MutableStateFlow<List<Provider>> _providers = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<Provider>> providers = FlowKt.asStateFlow(this._providers);
    private final MutableStateFlow<List<ProviderCategoryRelation>> _relations = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<ProviderCategoryRelation>> relations = FlowKt.asStateFlow(this._relations);
    private final MutableStateFlow<List<PendingProvider>> _pendingRequests = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<PendingProvider>> pendingRequests = FlowKt.asStateFlow(this._pendingRequests);
    private final MutableStateFlow<List<ChatMessage>> _chatMessages = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final MutableStateFlow<List<Chat>> _chats = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final MutableStateFlow<UserProfile> _currentUserProfile = StateFlowKt.MutableStateFlow(new UserProfile((String) null, (String) null, (String) null, (String) null, false, 31, (DefaultConstructorMarker) null));
    private final StateFlow<UserProfile> currentUserProfile = FlowKt.asStateFlow(this._currentUserProfile);
    private final MutableStateFlow<String> loggedInUsername = StateFlowKt.MutableStateFlow("");
    private final MutableStateFlow<String> currentChatRoomId = StateFlowKt.MutableStateFlow(null);
    private final MutableStateFlow<Integer> navigationTargetTab = StateFlowKt.MutableStateFlow(null);
    private final MutableStateFlow<Boolean> showRegistrationDialog = StateFlowKt.MutableStateFlow(false);
    private final StateFlow<List<Chat>> chats = FlowKt.stateIn(FlowKt.combine(this._chats, this._currentUserProfile, this.loggedInUsername, new MainViewModel$chats$1(null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, 5000, 0, 2, null), CollectionsKt.emptyList());
    private final StateFlow<List<ChatMessage>> chatMessages = FlowKt.stateIn(FlowKt.combine(this._chatMessages, this._currentUserProfile, this.loggedInUsername, new MainViewModel$chatMessages$1(null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, 5000, 0, 2, null), CollectionsKt.emptyList());
    private final MutableStateFlow<List<Banner>> _banners = StateFlowKt.MutableStateFlow(CollectionsKt.listOf((Object[]) new Banner[]{new Banner("b1", "https://images.unsplash.com/photo-1621905251189-08b45d6a269e?auto=format&fit=crop&w=600&q=80", "", "تأسيس وصيانة الكهرباء بأرقى المعايير", (String) null, (String) null, 0, 0, 240, (DefaultConstructorMarker) null), new Banner("b2", "https://images.unsplash.com/photo-1504307651254-35680f356dfd?auto=format&fit=crop&w=600&q=80", "", "دليل اليمن للربط المباشر مع المهندسين", (String) null, (String) null, 0, 0, 240, (DefaultConstructorMarker) null)}));
    private final StateFlow<List<Banner>> banners = FlowKt.asStateFlow(this._banners);
    private final MutableStateFlow<List<AuditLog>> _auditLogs = StateFlowKt.MutableStateFlow(CollectionsKt.listOf(new AuditLog("a1", "الأدمن", "إنشاء النظام وتأمين قواعد البيانات الافتراضية", 0, 8, (DefaultConstructorMarker) null)));
    private final StateFlow<List<AuditLog>> auditLogs = FlowKt.asStateFlow(this._auditLogs);
    private final MutableStateFlow<List<Report>> _reports = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<Report>> reports = FlowKt.asStateFlow(this._reports);
    private final List<Review> defaultReviews = CollectionsKt.emptyList();
    private final MutableStateFlow<List<Review>> _reviews = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<Review>> reviewsState = FlowKt.asStateFlow(this._reviews);
    private final MutableStateFlow<AppSettings> _settings = StateFlowKt.MutableStateFlow(new AppSettings(null, 0, null, null, null, null, null, false, null, null, null, null, null, false, 0, 0, false, null, 0, null, false, 0, null, false, 0, 0, null, null, null, null, null, false, false, false, false, false, null, null, 0, 0.0f, 0, 0, null, null, false, null, 0, null, null, null, false, false, null, null, null, null, false, null, null, false, null, null, false, false, null, 0.0f, null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, false, false, false, false, false, null, null, -1, -1, -1, null));
    private final StateFlow<AppSettings> settings = FlowKt.asStateFlow(this._settings);
    private final MutableStateFlow<Boolean> _isAdminLoggedIn = StateFlowKt.MutableStateFlow(false);
    private final StateFlow<Boolean> isAdminLoggedIn = FlowKt.asStateFlow(this._isAdminLoggedIn);
    private final MutableStateFlow<List<UserNotification>> _notifications = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<UserNotification>> notifications = FlowKt.stateIn(FlowKt.combine(this._notifications, this._currentUserProfile, this.loggedInUsername, new MainViewModel$notifications$1(null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, 5000, 0, 2, null), CollectionsKt.emptyList());
    private final MutableStateFlow<List<Pair<String, Boolean>>> _geminiMessages = StateFlowKt.MutableStateFlow(CollectionsKt.listOf(new Pair("مرحباً بك! أنا المساعد الذكي لخدمات اليمن. كيف يمكنني مساعدتك اليوم؟", false)));
    private final StateFlow<List<Pair<String, Boolean>>> geminiMessages = FlowKt.asStateFlow(this._geminiMessages);
    private final MutableStateFlow<Boolean> _isGeminiThinking = StateFlowKt.MutableStateFlow(false);
    private final StateFlow<Boolean> isGeminiThinking = FlowKt.asStateFlow(this._isGeminiThinking);
    private final MutableStateFlow<List<Booking>> _bookings = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<Booking>> bookings = FlowKt.stateIn(FlowKt.combine(this._bookings, this._currentUserProfile, this.loggedInUsername, new MainViewModel$bookings$1(null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, 5000, 0, 2, null), CollectionsKt.emptyList());
    private final MutableStateFlow<BookingFormFields> _bookingFormFields = StateFlowKt.MutableStateFlow(new BookingFormFields(false, false, false, false, false, false, false, false, false, false, false, false, 4095, (DefaultConstructorMarker) null));
    private final StateFlow<BookingFormFields> bookingFormFields = FlowKt.asStateFlow(this._bookingFormFields);
    private final MutableStateFlow<BookingDistributionMode> _distributionMode = StateFlowKt.MutableStateFlow(BookingDistributionMode.ADMIN_ONLY);
    private final StateFlow<BookingDistributionMode> distributionMode = FlowKt.asStateFlow(this._distributionMode);
    private final MutableStateFlow<List<Provider>> _pendingTechnicians = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final StateFlow<List<Provider>> pendingTechnicians = FlowKt.asStateFlow(this._pendingTechnicians);
    private final MutableStateFlow<CardSettings> _cardSettings = StateFlowKt.MutableStateFlow(new CardSettings(0, 0, 0, (String) null, (String) null, (String) null, (String) null, (String) null, false, false, false, (String) null, (String) null, (String) null, false, false, false, false, (String) null, (String) null, (String) null, (String) null, false, false, false, false, (String) null, 0, 0, false, 0.0f, Integer.MAX_VALUE, (DefaultConstructorMarker) null));
    private final StateFlow<CardSettings> cardSettings = FlowKt.asStateFlow(this._cardSettings);
    private final MutableStateFlow<Set<ChatParticipantType>> _blockedChatParticipants = StateFlowKt.MutableStateFlow(SetsKt.emptySet());
    private final StateFlow<Set<ChatParticipantType>> blockedChatParticipants = FlowKt.asStateFlow(this._blockedChatParticipants);

    public MainViewModel() {
        setupFirebaseRealtimeListener();
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3, null);
        this.blockedChatUsers = StateFlowKt.MutableStateFlow(SetsKt.emptySet());
        this.blockedChatProviders = StateFlowKt.MutableStateFlow(SetsKt.emptySet());
        this.isUserChatDisabledOnly = StateFlowKt.MutableStateFlow(false);
        this.isProviderChatDisabledOnly = StateFlowKt.MutableStateFlow(false);
        this.isAdminChatDisabledOnly = StateFlowKt.MutableStateFlow(false);
        this.isAllChatDisabled = StateFlowKt.MutableStateFlow(false);
        this._adminAccounts = StateFlowKt.MutableStateFlow(CollectionsKt.listOf(new AdminAccount("admin", "admin123", true, true, true, true, true)));
        this.adminAccounts = FlowKt.asStateFlow(this._adminAccounts);
    }

    public final Map<String, Boolean> getPinnedOverrides() {
        return this.pinnedOverrides;
    }

    public final Map<String, Boolean> getRecommendedOverrides() {
        return this.recommendedOverrides;
    }

    public final Map<String, Boolean> getVerifiedOverrides() {
        return this.verifiedOverrides;
    }

    public final Map<String, Boolean> getSubscribedOverrides() {
        return this.subscribedOverrides;
    }

    public final StateFlow<List<Category>> getCategoriesState() {
        return this.categoriesState;
    }

    public final StateFlow<List<City>> getCitiesState() {
        return this.citiesState;
    }

    public final StateFlow<List<Provider>> getProviders() {
        return this.providers;
    }

    public final StateFlow<List<ProviderCategoryRelation>> getRelations() {
        return this.relations;
    }

    public final StateFlow<List<PendingProvider>> getPendingRequests() {
        return this.pendingRequests;
    }

    public final StateFlow<UserProfile> getCurrentUserProfile() {
        return this.currentUserProfile;
    }

    public final MutableStateFlow<String> getLoggedInUsername() {
        return this.loggedInUsername;
    }

    public final MutableStateFlow<String> getCurrentChatRoomId() {
        return this.currentChatRoomId;
    }

    public final MutableStateFlow<Integer> getNavigationTargetTab() {
        return this.navigationTargetTab;
    }

    public final MutableStateFlow<Boolean> getShowRegistrationDialog() {
        return this.showRegistrationDialog;
    }

    public final void runWithActiveUser(Function0<Unit> onVerified) {
        Intrinsics.checkNotNullParameter(onVerified, "onVerified");
        if (this._currentUserProfile.getValue().isRegistered()) {
            onVerified.invoke();
        } else {
            this.onRegistrationSuccessCallback = onVerified;
            this.showRegistrationDialog.setValue(true);
        }
    }

    public final void completeRegistration(String name, String phone, String city) {
        SharedPreferences.Editor editorEdit;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(city, "city");
        String id = this._currentUserProfile.getValue().getId();
        if (StringsKt.isBlank(id)) {
            id = "user_" + UUID.randomUUID();
        }
        String uid = id;
        UserProfile profile = new UserProfile(uid, name, phone, city, true);
        this._currentUserProfile.setValue(profile);
        try {
            SharedPreferences sharedPreferences = this.sharedPrefs;
            if (sharedPreferences != null && (editorEdit = sharedPreferences.edit()) != null) {
                StringFormat $this$encodeToString$iv = Json.INSTANCE;
                $this$encodeToString$iv.getSerializersModule();
                SharedPreferences.Editor editorPutString = editorEdit.putString("user_profile_data", $this$encodeToString$iv.encodeToString(UserProfile.INSTANCE.serializer(), profile));
                if (editorPutString != null) {
                    editorPutString.apply();
                }
            }
        } catch (Exception e) {
        }
        syncPrivateListeners();
        Function0<Unit> function0 = this.onRegistrationSuccessCallback;
        if (function0 != null) {
            function0.invoke();
        }
        this.onRegistrationSuccessCallback = null;
        this.showRegistrationDialog.setValue(false);
    }

    public final void syncPrivateListeners() {
        CollectionReference collectionReferenceCollection;
        CollectionReference collectionReferenceCollection2;
        CollectionReference collectionReferenceCollection3;
        UserProfile profile = this._currentUserProfile.getValue();
        boolean isAdmin = !StringsKt.isBlank(this.loggedInUsername.getValue());
        ListenerRegistration listenerRegistrationAddSnapshotListener = null;
        if (profile.isRegistered() || isAdmin) {
            if (this.chatsListener == null) {
                FirebaseFirestore firebaseFirestore = this.firestore;
                this.chatsListener = (firebaseFirestore == null || (collectionReferenceCollection3 = firebaseFirestore.collection("chats")) == null) ? null : collectionReferenceCollection3.addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda13
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.syncPrivateListeners$lambda$3(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
            }
            if (this.messagesListener == null) {
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                this.messagesListener = (firebaseFirestore2 == null || (collectionReferenceCollection2 = firebaseFirestore2.collection("messages")) == null) ? null : collectionReferenceCollection2.addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda14
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.syncPrivateListeners$lambda$6(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
            }
            if (this.bookingsListener == null) {
                FirebaseFirestore firebaseFirestore3 = this.firestore;
                if (firebaseFirestore3 != null && (collectionReferenceCollection = firebaseFirestore3.collection("bookings")) != null) {
                    listenerRegistrationAddSnapshotListener = collectionReferenceCollection.addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda15
                        @Override // com.google.firebase.firestore.EventListener
                        public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                            MainViewModel.syncPrivateListeners$lambda$9(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                        }
                    });
                }
                this.bookingsListener = listenerRegistrationAddSnapshotListener;
                return;
            }
            return;
        }
        ListenerRegistration listenerRegistration = this.chatsListener;
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        this.chatsListener = null;
        this._chats.setValue(CollectionsKt.emptyList());
        ListenerRegistration listenerRegistration2 = this.messagesListener;
        if (listenerRegistration2 != null) {
            listenerRegistration2.remove();
        }
        this.messagesListener = null;
        this._chatMessages.setValue(CollectionsKt.emptyList());
        ListenerRegistration listenerRegistration3 = this.bookingsListener;
        if (listenerRegistration3 != null) {
            listenerRegistration3.remove();
        }
        this.bookingsListener = null;
        this._bookings.setValue(CollectionsKt.emptyList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void syncPrivateListeners$lambda$3(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable list = snap.toObjects(Chat.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            Iterable $this$sortedByDescending$iv = list;
            this$0._chats.setValue(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.maw.MainViewModel$syncPrivateListeners$lambda$3$lambda$2$$inlined$sortedByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Chat it = (Chat) t2;
                    Chat it2 = (Chat) t;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getTimestamp()), Long.valueOf(it2.getTimestamp()));
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void syncPrivateListeners$lambda$6(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable list = snap.toObjects(ChatMessage.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            Iterable $this$sortedBy$iv = list;
            this$0._chatMessages.setValue(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.maw.MainViewModel$syncPrivateListeners$lambda$6$lambda$5$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    ChatMessage it = (ChatMessage) t;
                    ChatMessage it2 = (ChatMessage) t2;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getTimestamp()), Long.valueOf(it2.getTimestamp()));
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void syncPrivateListeners$lambda$9(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable list = snap.toObjects(Booking.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            Iterable $this$sortedBy$iv = list;
            this$0._bookings.setValue(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.maw.MainViewModel$syncPrivateListeners$lambda$9$lambda$8$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Booking it = (Booking) t;
                    Booking it2 = (Booking) t2;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getTimestamp()), Long.valueOf(it2.getTimestamp()));
                }
            }));
        }
    }

    public final StateFlow<List<Chat>> getChats() {
        return this.chats;
    }

    public final StateFlow<List<ChatMessage>> getChatMessages() {
        return this.chatMessages;
    }

    public final StateFlow<List<Banner>> getBanners() {
        return this.banners;
    }

    public final StateFlow<List<AuditLog>> getAuditLogs() {
        return this.auditLogs;
    }

    public final StateFlow<List<Report>> getReports() {
        return this.reports;
    }

    public final StateFlow<List<Review>> getReviewsState() {
        return this.reviewsState;
    }

    public final StateFlow<AppSettings> getSettings() {
        return this.settings;
    }

    public final StateFlow<Boolean> isAdminLoggedIn() {
        return this.isAdminLoggedIn;
    }

    public final boolean checkAdminPassword(String password) {
        boolean z;
        Intrinsics.checkNotNullParameter(password, "password");
        Iterable $this$any$iv = this._adminAccounts.getValue();
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator it = $this$any$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    AdminAccount it2 = (AdminAccount) element$iv;
                    if (Intrinsics.areEqual(it2.getPasswordHash(), password) || Intrinsics.areEqual(password, "admin123")) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            z = false;
        }
        boolean matched = z || Intrinsics.areEqual(password, this._settings.getValue().getAdminPassword()) || Intrinsics.areEqual(password, "maher736462");
        if (matched) {
            this._isAdminLoggedIn.setValue(true);
        }
        return matched;
    }

    public final boolean checkAdminThreeLayersLogin(String user, String pass) {
        Object next;
        Intrinsics.checkNotNullParameter(user, "user");
        Intrinsics.checkNotNullParameter(pass, "pass");
        if (Intrinsics.areEqual(user, "WAM2026") && (Intrinsics.areEqual(pass, this._settings.getValue().getAdminPassword()) || Intrinsics.areEqual(pass, "maher736462"))) {
            this.loggedInUsername.setValue("WAM2026");
            this._isAdminLoggedIn.setValue(true);
            return true;
        }
        Iterator<T> it = this._adminAccounts.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            AdminAccount it2 = (AdminAccount) next;
            if (Intrinsics.areEqual(it2.getUsername(), user) && Intrinsics.areEqual(it2.getPasswordHash(), pass)) {
                break;
            }
        }
        AdminAccount supervisor = (AdminAccount) next;
        if (supervisor == null) {
            return false;
        }
        this.loggedInUsername.setValue(supervisor.getUsername());
        this._isAdminLoggedIn.setValue(true);
        return true;
    }

    public final void logoutAdmin() {
        this._isAdminLoggedIn.setValue(false);
        this.loggedInUsername.setValue("");
    }

    public final StateFlow<List<UserNotification>> getNotifications() {
        return this.notifications;
    }

    public final void addNotification(String title, String body, String statusType) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(statusType, "statusType");
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        UserNotification newNotify = new UserNotification(string, title, body, "الآن", System.currentTimeMillis(), false, statusType, (String) null, (String) null, 384, (DefaultConstructorMarker) null);
        this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(newNotify), (Iterable) this._notifications.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("notifications")) == null || (documentReferenceDocument = collectionReferenceCollection.document(newNotify.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(newNotify);
        } catch (Exception e) {
        }
    }

    public final void markAllNotificationsAsRead() {
        MutableStateFlow<List<UserNotification>> mutableStateFlow = this._notifications;
        Iterable $this$map$iv = this._notifications.getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            UserNotification it = (UserNotification) item$iv$iv;
            destination$iv$iv.add(it.copy((479 & 1) != 0 ? it.id : null, (479 & 2) != 0 ? it.title : null, (479 & 4) != 0 ? it.body : null, (479 & 8) != 0 ? it.time : null, (479 & 16) != 0 ? it.timestamp : 0L, (479 & 32) != 0 ? it.isRead : true, (479 & 64) != 0 ? it.statusType : null, (479 & 128) != 0 ? it.recipientId : null, (479 & 256) != 0 ? it.category : null));
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
    }

    public final StateFlow<List<Pair<String, Boolean>>> getGeminiMessages() {
        return this.geminiMessages;
    }

    public final StateFlow<Boolean> isGeminiThinking() {
        return this.isGeminiThinking;
    }

    public final StateFlow<List<Booking>> getBookings() {
        return this.bookings;
    }

    public final StateFlow<BookingFormFields> getBookingFormFields() {
        return this.bookingFormFields;
    }

    public final StateFlow<BookingDistributionMode> getDistributionMode() {
        return this.distributionMode;
    }

    public final StateFlow<List<Provider>> getPendingTechnicians() {
        return this.pendingTechnicians;
    }

    public final StateFlow<CardSettings> getCardSettings() {
        return this.cardSettings;
    }

    public final StateFlow<Set<ChatParticipantType>> getBlockedChatParticipants() {
        return this.blockedChatParticipants;
    }

    public final void initCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            this.sharedPrefs = context.getSharedPreferences("maw_cache", 0);
            SharedPreferences prefs = this.sharedPrefs;
            if (prefs != null) {
                String cachedProvidersStr = prefs.getString("providers", null);
                if (cachedProvidersStr != null) {
                    try {
                        Json this_$iv = Json.INSTANCE;
                        this_$iv.getSerializersModule();
                        List<Provider> list = (List) this_$iv.decodeFromString(new ArrayListSerializer(Provider.INSTANCE.serializer()), cachedProvidersStr);
                        if (!list.isEmpty()) {
                            this._providers.setValue(list);
                        }
                    } catch (Exception e) {
                    }
                }
                String cachedCategoriesStr = prefs.getString("categories", null);
                if (cachedCategoriesStr != null) {
                    try {
                        Json this_$iv2 = Json.INSTANCE;
                        this_$iv2.getSerializersModule();
                        Iterable list2 = (List) this_$iv2.decodeFromString(new ArrayListSerializer(Category.INSTANCE.serializer()), cachedCategoriesStr);
                        if (!((Collection) list2).isEmpty()) {
                            Iterable $this$sortedBy$iv = list2;
                            this._categories.setValue(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.maw.MainViewModel$initCache$lambda$14$$inlined$sortedBy$1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // java.util.Comparator
                                public final int compare(T t, T t2) {
                                    Category it = (Category) t;
                                    Category it2 = (Category) t2;
                                    return ComparisonsKt.compareValues(Integer.valueOf(it.getOrder()), Integer.valueOf(it2.getOrder()));
                                }
                            }));
                        }
                    } catch (Exception e2) {
                    }
                }
                String cachedCitiesStr = prefs.getString("cities", null);
                if (cachedCitiesStr != null) {
                    try {
                        Json this_$iv3 = Json.INSTANCE;
                        this_$iv3.getSerializersModule();
                        List<City> list3 = (List) this_$iv3.decodeFromString(new ArrayListSerializer(City.INSTANCE.serializer()), cachedCitiesStr);
                        if (!list3.isEmpty()) {
                            this._cities.setValue(list3);
                        }
                    } catch (Exception e3) {
                    }
                }
                String cachedSettingsStr = prefs.getString("settings", null);
                if (cachedSettingsStr != null) {
                    try {
                        Json this_$iv4 = Json.INSTANCE;
                        this_$iv4.getSerializersModule();
                        AppSettings value = (AppSettings) this_$iv4.decodeFromString(AppSettings.INSTANCE.serializer(), cachedSettingsStr);
                        this._settings.setValue(value);
                    } catch (Exception e4) {
                    }
                }
                String cachedProfileStr = prefs.getString("user_profile_data", null);
                if (cachedProfileStr != null) {
                    try {
                        Json this_$iv5 = Json.INSTANCE;
                        this_$iv5.getSerializersModule();
                        UserProfile profile = (UserProfile) this_$iv5.decodeFromString(UserProfile.INSTANCE.serializer(), cachedProfileStr);
                        this._currentUserProfile.setValue(profile);
                    } catch (Exception e5) {
                    }
                }
                String cachedPinnedOverrides = prefs.getString("pinned_overrides", null);
                if (cachedPinnedOverrides != null) {
                    try {
                        Json this_$iv6 = Json.INSTANCE;
                        this_$iv6.getSerializersModule();
                        this.pinnedOverrides.putAll((Map) this_$iv6.decodeFromString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), cachedPinnedOverrides));
                    } catch (Exception e6) {
                    }
                }
                String cachedRecommendedOverrides = prefs.getString("recommended_overrides", null);
                if (cachedRecommendedOverrides != null) {
                    try {
                        Json this_$iv7 = Json.INSTANCE;
                        this_$iv7.getSerializersModule();
                        this.recommendedOverrides.putAll((Map) this_$iv7.decodeFromString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), cachedRecommendedOverrides));
                    } catch (Exception e7) {
                    }
                }
                String cachedVerifiedOverrides = prefs.getString("verified_overrides", null);
                if (cachedVerifiedOverrides != null) {
                    try {
                        Json this_$iv8 = Json.INSTANCE;
                        this_$iv8.getSerializersModule();
                        this.verifiedOverrides.putAll((Map) this_$iv8.decodeFromString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), cachedVerifiedOverrides));
                    } catch (Exception e8) {
                    }
                }
                String cachedSubscribedOverrides = prefs.getString("subscribed_overrides", null);
                if (cachedSubscribedOverrides != null) {
                    try {
                        Json this_$iv9 = Json.INSTANCE;
                        this_$iv9.getSerializersModule();
                        try {
                            this.subscribedOverrides.putAll((Map) this_$iv9.decodeFromString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), cachedSubscribedOverrides));
                        } catch (Exception e9) {
                        }
                    } catch (Exception e10) {
                    }
                }
                syncPrivateListeners();
            }
        } catch (Exception e11) {
        }
    }

    private final void saveToCache(String key, String valueJson) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences.Editor editorPutString;
        try {
            SharedPreferences sharedPreferences = this.sharedPrefs;
            if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null || (editorPutString = editorEdit.putString(key, valueJson)) == null) {
                return;
            }
            editorPutString.apply();
        } catch (Exception e) {
        }
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$1", f = "MainActivity.kt", i = {}, l = {1107}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    MutableStateFlow mutableStateFlow = MainViewModel.this._currentUserProfile;
                    final MainViewModel mainViewModel = MainViewModel.this;
                    this.label = 1;
                    if (mutableStateFlow.collect(new FlowCollector() { // from class: com.maw.MainViewModel.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                            return emit((UserProfile) value, (Continuation<? super Unit>) $completion);
                        }

                        public final Object emit(UserProfile it, Continuation<? super Unit> continuation) {
                            mainViewModel.syncPrivateListeners();
                            return Unit.INSTANCE;
                        }
                    }, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$2, reason: invalid class name */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$2", f = "MainActivity.kt", i = {}, l = {1112}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainViewModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    MutableStateFlow<String> loggedInUsername = MainViewModel.this.getLoggedInUsername();
                    final MainViewModel mainViewModel = MainViewModel.this;
                    this.label = 1;
                    if (loggedInUsername.collect(new FlowCollector() { // from class: com.maw.MainViewModel.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                            return emit((String) value, (Continuation<? super Unit>) $completion);
                        }

                        public final Object emit(String it, Continuation<? super Unit> continuation) {
                            mainViewModel.syncPrivateListeners();
                            return Unit.INSTANCE;
                        }
                    }, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            throw new KotlinNothingValueException();
        }
    }

    private final void setupFirebaseRealtimeListener() {
        try {
            this.firestore = FirebaseFirestore.getInstance();
            final FirebaseFirestore db = this.firestore;
            if (db != null) {
                Task<DocumentSnapshot> task = db.collection("settings").document("global").get();
                final Function1<DocumentSnapshot, Unit> function1 = new Function1<DocumentSnapshot, Unit>() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DocumentSnapshot documentSnapshot) {
                        invoke2(documentSnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DocumentSnapshot snapshot) {
                        if (snapshot == null || !snapshot.exists()) {
                            db.collection("settings").document("global").set(new AppSettings(null, 0, null, null, null, null, null, false, null, null, null, null, null, false, 0, 0, false, null, 0, null, false, 0, null, false, 0, 0, null, null, null, null, null, false, false, false, false, false, null, null, 0, 0.0f, 0, 0, null, null, false, null, 0, null, null, null, false, false, null, null, null, null, false, null, null, false, null, null, false, false, null, 0.0f, null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, false, false, false, false, false, null, null, -1, -1, -1, null));
                        }
                    }
                };
                task.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda21
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$15(function1, obj);
                    }
                });
                Task<QuerySnapshot> task2 = db.collection("categories").get();
                final Function1<QuerySnapshot, Unit> function12 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                        invoke2(querySnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(QuerySnapshot snapshot) {
                        if (snapshot == null || snapshot.isEmpty()) {
                            Iterable $this$forEach$iv = this.this$0.defaultCategories;
                            FirebaseFirestore firebaseFirestore = db;
                            for (Object element$iv : $this$forEach$iv) {
                                Category cat = (Category) element$iv;
                                firebaseFirestore.collection("categories").document(cat.getId()).set(cat);
                            }
                        }
                    }
                };
                task2.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda3
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$16(function12, obj);
                    }
                });
                Task<QuerySnapshot> task3 = db.collection("providers").get();
                final Function1<QuerySnapshot, Unit> function13 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$1$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                        invoke2(querySnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(QuerySnapshot snapshot) {
                        if (snapshot == null || snapshot.isEmpty()) {
                            Iterable $this$forEach$iv = this.this$0.defaultProviders;
                            FirebaseFirestore firebaseFirestore = db;
                            for (Object element$iv : $this$forEach$iv) {
                                Provider prov = (Provider) element$iv;
                                firebaseFirestore.collection("providers").document(prov.getId()).set(prov);
                                ProviderCategoryRelation rel = new ProviderCategoryRelation(prov.getId() + "_" + prov.getCategory(), prov.getId(), prov.getCategory());
                                firebaseFirestore.collection("provider_category_relations").document(rel.getId()).set(rel);
                            }
                        }
                    }
                };
                task3.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda4
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$17(function13, obj);
                    }
                });
                Task<QuerySnapshot> task4 = db.collection("cities").get();
                final Function1<QuerySnapshot, Unit> function14 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                        invoke2(querySnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(QuerySnapshot snapshot) {
                        if (snapshot == null || snapshot.isEmpty()) {
                            Iterable $this$forEach$iv = this.this$0.defaultCities;
                            FirebaseFirestore firebaseFirestore = db;
                            for (Object element$iv : $this$forEach$iv) {
                                City city = (City) element$iv;
                                firebaseFirestore.collection("cities").document(city.getId()).set(city);
                            }
                        }
                    }
                };
                task4.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda5
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$18(function14, obj);
                    }
                });
                Task<QuerySnapshot> task5 = db.collection("reviews").get();
                final Function1<QuerySnapshot, Unit> function15 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$1$5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                        invoke2(querySnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(QuerySnapshot snapshot) {
                        if (snapshot == null || snapshot.isEmpty()) {
                            Iterable $this$forEach$iv = this.this$0.defaultReviews;
                            FirebaseFirestore firebaseFirestore = db;
                            for (Object element$iv : $this$forEach$iv) {
                                Review review = (Review) element$iv;
                                firebaseFirestore.collection("reviews").document(review.getId()).set(review);
                            }
                        }
                    }
                };
                task5.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda6
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$19(function15, obj);
                    }
                });
                db.collection("settings").document("global").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda7
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$20(this.f$0, (DocumentSnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("providers").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda8
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$23(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("categories").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda9
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$26(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("cities").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda10
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$28(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("provider_category_relations").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda12
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$30(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("reviews").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda22
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$32(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("banners").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda23
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$34(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("pending_requests").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda24
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$36(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("reports").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda25
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$38(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("notifications").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda26
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$41(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("admins").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda1
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$43(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
                db.collection("audit_logs").addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda2
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.setupFirebaseRealtimeListener$lambda$47$lambda$46(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$15(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$16(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$17(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$18(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$19(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$20(MainViewModel this$0, DocumentSnapshot snapshot, FirebaseFirestoreException error) {
        String str;
        String bgCol;
        int radiusVal;
        String str2;
        int iLongValue;
        String aCol;
        String aCol2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (error != null) {
            return;
        }
        if (snapshot == null || !snapshot.exists()) {
            return;
        }
        String string = snapshot.getString("footerText");
        if (string == null) {
            string = "wam 2026";
        }
        String footerTxt = string;
        Long l = snapshot.getLong("footerFontSize");
        int fontSize = l != null ? (int) l.longValue() : 11;
        String string2 = snapshot.getString("selectedFontName");
        if (string2 == null) {
            string2 = "SansSerif";
        }
        String fontName = string2;
        String string3 = snapshot.getString("downloadUrl");
        if (string3 == null) {
            string3 = "https://example.com/download/kol-khadamat";
        }
        String dUrl = string3;
        String string4 = snapshot.getString("aboutImageUrl");
        if (string4 == null) {
            string4 = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?auto=format&fit=crop&w=800&q=80";
        }
        String aboutImg = string4;
        String string5 = snapshot.getString("appNameAr");
        if (string5 == null) {
            string5 = "كل خدمات اليمن";
        }
        String appName = string5;
        String string6 = snapshot.getString("welcomeMessage");
        if (string6 == null) {
            string6 = "مرحباً بك في دليل المهن والخدمات اليمني الشامل لربط الكوادر والمهنيين";
        }
        String welcome = string6;
        Boolean bool = snapshot.getBoolean("footerTextVisible");
        if (bool == null) {
            bool = true;
        }
        boolean visible = bool.booleanValue();
        String string7 = snapshot.getString("primaryColorHex");
        if (string7 == null) {
            string7 = "#0A2463";
        }
        String pCol = string7;
        String string8 = snapshot.getString("accentColorHex");
        if (string8 == null) {
            string8 = "#3A7CA5";
        }
        String aCol3 = string8;
        String string9 = snapshot.getString("bgColorHex");
        if (string9 == null) {
            string9 = "#0D0D0D";
        }
        String bgCol2 = string9;
        String string10 = snapshot.getString("surfaceColorHex");
        if (string10 == null) {
            string10 = "#1A1A2E";
        }
        String sCol = string10;
        Boolean bool2 = snapshot.getBoolean("isWebSpeechEnabled");
        if (bool2 == null) {
            bool2 = true;
        }
        boolean webSpeech = bool2.booleanValue();
        Long l2 = snapshot.getLong("radiusSearchLimitKm");
        if (l2 != null) {
            str = "#3A7CA5";
            bgCol = bgCol2;
            radiusVal = (int) l2.longValue();
        } else {
            str = "#3A7CA5";
            bgCol = bgCol2;
            radiusVal = 30;
        }
        Long l3 = snapshot.getLong("autoCleanupDays");
        int cleanupDays = l3 != null ? (int) l3.longValue() : 30;
        Boolean bool3 = snapshot.getBoolean("isChatEnabled");
        if (bool3 == null) {
            bool3 = true;
        }
        boolean chatEnabled = bool3.booleanValue();
        String string11 = snapshot.getString("chatDisabledMessage");
        if (string11 == null) {
            string11 = "عذراً، تم إيقاف خدمة المحادثة الفورية والآمنة مؤقتاً لأعمال الصيانة الدورية.";
        }
        String chatDisMsg = string11;
        Long l4 = snapshot.getLong("chatIconSize");
        int cSize = l4 != null ? (int) l4.longValue() : 56;
        String string12 = snapshot.getString("chatIconColorHex");
        String cColHex = string12 == null ? "#0A2463" : string12;
        Boolean bool4 = snapshot.getBoolean("chatIconHidden");
        if (bool4 == null) {
            bool4 = false;
        }
        boolean cHidden = bool4.booleanValue();
        Long l5 = snapshot.getLong("assistantIconSize");
        int aSize = l5 != null ? (int) l5.longValue() : 56;
        String string13 = snapshot.getString("assistantIconColorHex");
        String aColHex = string13 == null ? "#0A2463" : string13;
        Boolean bool5 = snapshot.getBoolean("assistantIconHidden");
        if (bool5 == null) {
            bool5 = false;
        }
        boolean aHidden = bool5.booleanValue();
        Long l6 = snapshot.getLong("assistantIconXOffset");
        int aXOff = l6 != null ? (int) l6.longValue() : 0;
        Long l7 = snapshot.getLong("assistantIconYOffset");
        int aYOff = l7 != null ? (int) l7.longValue() : 75;
        String string14 = snapshot.getString("assistantIconType");
        if (string14 == null) {
            string14 = "SmartToy";
        }
        String aIconType = string14;
        String string15 = snapshot.getString("aboutPhone");
        String abPhone = string15 == null ? "777644" : string15;
        Boolean bool6 = snapshot.getBoolean("aboutPhoneVisible");
        if (bool6 == null) {
            bool6 = true;
        }
        boolean abPhoneVis = bool6.booleanValue();
        Boolean bool7 = snapshot.getBoolean("aboutWhatsappVisible");
        if (bool7 == null) {
            bool7 = true;
        }
        boolean abWhatsappVis = bool7.booleanValue();
        Boolean bool8 = snapshot.getBoolean("aboutEmailVisible");
        if (bool8 == null) {
            bool8 = true;
        }
        boolean abEmailVis = bool8.booleanValue();
        Boolean bool9 = snapshot.getBoolean("aboutShareUrlVisible");
        if (bool9 == null) {
            bool9 = true;
        }
        boolean abShareVis = bool9.booleanValue();
        Boolean bool10 = snapshot.getBoolean("aboutImageVisible");
        if (bool10 == null) {
            bool10 = true;
        }
        boolean abImgVis = bool10.booleanValue();
        String string16 = snapshot.getString("aboutWhatsapp");
        String abWhatsapp = string16 == null ? "777644" : string16;
        String string17 = snapshot.getString("aboutEmail");
        if (string17 == null) {
            string17 = "maa736462@gmail.com";
        }
        String abEmail = string17;
        String string18 = snapshot.getString("aboutShareUrl");
        if (string18 == null) {
            string18 = "https://kolkhadamat-yemen.com/share";
        }
        String abShareUrl = string18;
        String string19 = snapshot.getString("adminPassword");
        if (string19 == null) {
            string19 = "maher736462";
        }
        String admPass = string19;
        String string20 = snapshot.getString("fontColorHex");
        if (string20 == null) {
            string20 = "#FFFFFF";
        }
        String fnColHex = string20;
        Long l8 = snapshot.getLong("footerFontSizePercent");
        int fFontPercent = l8 != null ? (int) l8.longValue() : 100;
        Double d = snapshot.getDouble("footerOpacity");
        float fOpacityVal = d != null ? (float) d.doubleValue() : 1.0f;
        Long l9 = snapshot.getLong("assistantIconSizePercent");
        int aSizePercentVal = l9 != null ? (int) l9.longValue() : 100;
        Long l10 = snapshot.getLong("chatIconSizePercent");
        int cSizePercentVal = l10 != null ? (int) l10.longValue() : 100;
        String string21 = snapshot.getString("appLogoText");
        if (string21 == null) {
            string21 = "WAM";
        }
        String logTextVal = string21;
        String string22 = snapshot.getString("appLogoUrl");
        String logUrlVal = string22 == null ? "" : string22;
        Boolean bool11 = snapshot.getBoolean("isGeoSearchEnabled");
        if (bool11 == null) {
            bool11 = true;
        }
        boolean geoEnabled = bool11.booleanValue();
        String string23 = snapshot.getString("searchMatchingMethodHex");
        if (string23 == null) {
            string23 = "fuzzy";
        }
        String searchMatchMethod = string23;
        Long l11 = snapshot.getLong("maxPortfolioImages");
        if (l11 != null) {
            str2 = "";
            iLongValue = (int) l11.longValue();
        } else {
            str2 = "";
            iLongValue = 5;
        }
        int maxPortImages = iLongValue;
        String string24 = snapshot.getString("initiativeSupportNumber");
        String supportNo = string24 == null ? "777644" : string24;
        Boolean bool12 = snapshot.getBoolean("notificationsEnabled");
        if (bool12 == null) {
            bool12 = true;
        }
        boolean notifsEnabled = bool12.booleanValue();
        Boolean bool13 = snapshot.getBoolean("reviewSystemEnabled");
        if (bool13 == null) {
            bool13 = true;
        }
        boolean reviewsEnabled = bool13.booleanValue();
        String string25 = snapshot.getString("aboutTitleText");
        if (string25 == null) {
            string25 = "ℹ️ عن منصة دليل كل خدمات اليمن";
        }
        String abTitleTxt = string25;
        String string26 = snapshot.getString("aboutVersionLabel");
        if (string26 == null) {
            string26 = "النسخة الحالية:";
        }
        String abVerLbl = string26;
        String string27 = snapshot.getString("aboutVersionValue");
        if (string27 == null) {
            string27 = "v1.5.0";
        }
        String abVerVal = string27;
        Boolean bool14 = snapshot.getBoolean("aboutVersionVisible");
        if (bool14 == null) {
            bool14 = true;
        }
        boolean abVerVis = bool14.booleanValue();
        String string28 = snapshot.getString("aboutSecurityLabel");
        if (string28 == null) {
            string28 = "مستوى التشفير والحقن:";
        }
        String abSecLbl = string28;
        String string29 = snapshot.getString("aboutSecurityValue");
        if (string29 == null) {
            string29 = "تشفير آمن سحابي";
        }
        String abSecVal = string29;
        Boolean bool15 = snapshot.getBoolean("aboutSecurityVisible");
        if (bool15 == null) {
            bool15 = true;
        }
        boolean abSecVis = bool15.booleanValue();
        String string30 = snapshot.getString("geminiApiKey");
        String gKey = string30 == null ? str2 : string30;
        String string31 = snapshot.getString("assistantWelcomeText");
        if (string31 == null) {
            string31 = "أهلاً بك في دليل 'كل خدمات اليمن' الشامل (المساعد الذكي يعمل بالإنترنت وبدونه 🛡️). يمكنني مساعدتك في العثور على الأطباء، والمدرسين، والمهندسين، والمحامين، والكهربائيين، وكافة الحرفيين حتى في حال انقطاع الإنترنت. لمعلومات عن المبادرة أو رقم الدعم: 777644. ماذا يمكنني أن أبحث لك اليوم؟";
        }
        String aWelcomeText = string31;
        Boolean bool16 = snapshot.getBoolean("isPortfolioFeatureGloballyEnabled");
        if (bool16 == null) {
            bool16 = true;
        }
        boolean portFeatureEnabled = bool16.booleanValue();
        Boolean bool17 = snapshot.getBoolean("isPortfolioUploadGloballyAllowed");
        if (bool17 == null) {
            bool17 = true;
        }
        boolean portUploadAllowed = bool17.booleanValue();
        String string32 = snapshot.getString("registrationChipColorHex");
        String regChipColor = string32 == null ? str : string32;
        Double dValueOf = snapshot.getDouble("searchRatingWeight");
        if (dValueOf == null) {
            dValueOf = Double.valueOf(1.0d);
        }
        float searchWeightVal = (float) dValueOf.doubleValue();
        Object obj = snapshot.get("regChipBgColorsList");
        List listListOf = obj instanceof List ? (List) obj : null;
        if (listListOf == null) {
            listListOf = CollectionsKt.listOf((Object[]) new String[]{"#2A9D8F", "#3A7CA5", "#CE1126", "#FFB300", "#50C878", "#9B5DE5", "#F15BB5", "#00F5D4"});
        }
        List regChipColors = listListOf;
        String string33 = snapshot.getString("approvedProviderSortingMethod");
        if (string33 == null) {
            string33 = "admin_priority";
        }
        String sortingMethod = string33;
        Boolean bool18 = snapshot.getBoolean("searchBarVisible");
        if (bool18 == null) {
            bool18 = true;
        }
        boolean searchBarVis = bool18.booleanValue();
        Boolean bool19 = snapshot.getBoolean("regNameRequired");
        if (bool19 == null) {
            bool19 = true;
        }
        boolean regNameReq = bool19.booleanValue();
        Boolean bool20 = snapshot.getBoolean("regNameVisible");
        if (bool20 == null) {
            bool20 = true;
        }
        boolean regNameVis = bool20.booleanValue();
        Boolean bool21 = snapshot.getBoolean("regPhoneRequired");
        if (bool21 == null) {
            bool21 = true;
        }
        boolean regPhoneReq = bool21.booleanValue();
        Boolean bool22 = snapshot.getBoolean("regPhoneVisible");
        if (bool22 == null) {
            bool22 = true;
        }
        boolean regPhoneVis = bool22.booleanValue();
        Boolean bool23 = snapshot.getBoolean("regCategoryRequired");
        if (bool23 == null) {
            bool23 = true;
        }
        boolean regCatReq = bool23.booleanValue();
        Boolean bool24 = snapshot.getBoolean("regCategoryVisible");
        if (bool24 == null) {
            bool24 = true;
        }
        boolean regCatVis = bool24.booleanValue();
        Boolean bool25 = snapshot.getBoolean("regSelfieRequired");
        if (bool25 == null) {
            bool25 = true;
        }
        boolean regSelfieReq = bool25.booleanValue();
        Boolean bool26 = snapshot.getBoolean("regSelfieVisible");
        if (bool26 == null) {
            bool26 = true;
        }
        boolean regSelfieVis = bool26.booleanValue();
        Boolean bool27 = snapshot.getBoolean("regIdCardRequired");
        if (bool27 == null) {
            bool27 = true;
        }
        boolean regIdCardReq = bool27.booleanValue();
        Boolean bool28 = snapshot.getBoolean("regIdCardVisible");
        if (bool28 == null) {
            bool28 = true;
        }
        boolean regIdCardVis = bool28.booleanValue();
        Boolean bool29 = snapshot.getBoolean("regAreaRequired");
        if (bool29 == null) {
            bool29 = true;
        }
        boolean regAreaReq = bool29.booleanValue();
        Boolean bool30 = snapshot.getBoolean("regAreaVisible");
        if (bool30 == null) {
            bool30 = true;
        }
        boolean regAreaVis = bool30.booleanValue();
        Boolean bool31 = snapshot.getBoolean("regDescRequired");
        if (bool31 == null) {
            bool31 = true;
        }
        boolean regDescReq = bool31.booleanValue();
        Boolean bool32 = snapshot.getBoolean("regDescVisible");
        if (bool32 == null) {
            bool32 = true;
        }
        boolean regDescVis = bool32.booleanValue();
        Boolean bool33 = snapshot.getBoolean("autocompleteNamesEnabled");
        if (bool33 == null) {
            bool33 = true;
        }
        boolean autoNames = bool33.booleanValue();
        Boolean bool34 = snapshot.getBoolean("autocompletePhonesEnabled");
        if (bool34 == null) {
            bool34 = true;
        }
        boolean autoPhones = bool34.booleanValue();
        Boolean bool35 = snapshot.getBoolean("autocompleteLocationsEnabled");
        if (bool35 == null) {
            bool35 = true;
        }
        boolean autoLocs = bool35.booleanValue();
        Boolean bool36 = snapshot.getBoolean("isBookingsEnabled");
        if (bool36 == null) {
            bool36 = true;
        }
        boolean bkEnabled = bool36.booleanValue();
        String string34 = snapshot.getString("bookingsRoutingMode");
        if (string34 == null) {
            string34 = "both";
        }
        String bkRouting = string34;
        String string35 = snapshot.getString("noResultsMessage");
        if (string35 == null) {
            string35 = "عذراً، لم يتم العثور على فني مطبق لهذه الشروط بالدليل.";
        }
        String noResultsMsg = string35;
        Object obj2 = snapshot.get("blockedKeywords");
        List listListOf2 = obj2 instanceof List ? (List) obj2 : null;
        if (listListOf2 == null) {
            listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"كلب", "حمار", "سيئ", "نصاب"});
        }
        List blockedKeys = listListOf2;
        Object obj3 = snapshot.get("registrationRulesList");
        List rules = obj3 instanceof List ? (List) obj3 : null;
        if (rules == null) {
            rules = CollectionsKt.listOf((Object[]) new String[]{"يجب أن يكون المتقدم مواطناً يمنياً أو مقيماً مرخصاً بالجمهورية اليمنية.", "توفر خبرة مهنية وعملية لا تقل عن عامين في التخصص المطلوب.", "الالتزام بحسن التعامل والسلوك والأمانة المهنية الكاملة مع طالبي الخدمة.", "تقديم بيانات صحيحة ومطابقة ومستندات تثبت الهوية المهنية عند الطلب."});
        }
        String bgCol3 = bgCol;
        this$0._settings.setValue(new AppSettings(footerTxt, fontSize, fontName, dUrl, aboutImg, appName, welcome, visible, rules, pCol, aCol3, bgCol3, sCol, webSpeech, radiusVal, cleanupDays, chatEnabled, chatDisMsg, cSize, cColHex, cHidden, aSize, aColHex, aHidden, aXOff, aYOff, aIconType, abPhone, abWhatsapp, abEmail, abShareUrl, abPhoneVis, abWhatsappVis, abEmailVis, abShareVis, abImgVis, admPass, fnColHex, fFontPercent, fOpacityVal, aSizePercentVal, cSizePercentVal, logTextVal, logUrlVal, geoEnabled, searchMatchMethod, maxPortImages, null, null, supportNo, notifsEnabled, reviewsEnabled, blockedKeys, abTitleTxt, abVerLbl, abVerVal, abVerVis, abSecLbl, abSecVal, abSecVis, gKey, aWelcomeText, portFeatureEnabled, portUploadAllowed, regChipColor, searchWeightVal, regChipColors, sortingMethod, searchBarVis, regNameReq, regNameVis, regPhoneReq, regPhoneVis, regCatReq, regCatVis, regSelfieReq, regSelfieVis, regIdCardReq, regIdCardVis, regAreaReq, regAreaVis, regDescReq, regDescVis, autoNames, autoPhones, autoLocs, bkEnabled, bkRouting, noResultsMsg, false, false, false, false, false, null, null, 0, 98304, -33554432, null));
        try {
            AppTheme.INSTANCE.m6270setPrimaryRed8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(pCol, ColorKt.Color(4291694886L)));
            aCol = aCol3;
            try {
                AppTheme.INSTANCE.m6268setAccentGold8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(aCol, ColorKt.Color(4294956800L)));
                try {
                    AppTheme.INSTANCE.m6269setDarkBg8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(bgCol3, ColorKt.Color(4279048990L)));
                    try {
                        AppTheme.INSTANCE.m6271setSurfaceDark8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(sCol, ColorKt.Color(4279642669L)));
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
            }
        } catch (Exception e4) {
            aCol = aCol3;
        }
        if (this$0._geminiMessages.getValue().size() <= 1) {
            aCol2 = aWelcomeText;
            this$0._geminiMessages.setValue(CollectionsKt.listOf(new Pair(aCol2, false)));
        } else {
            aCol2 = aWelcomeText;
        }
        StringFormat $this$encodeToString$iv = Json.INSTANCE;
        AppSettings value = this$0._settings.getValue();
        $this$encodeToString$iv.getSerializersModule();
        this$0.saveToCache("settings", $this$encodeToString$iv.encodeToString(AppSettings.INSTANCE.serializer(), value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$23(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable loaded = snap.toObjects(Provider.class);
            Intrinsics.checkNotNullExpressionValue(loaded, "toObjects(...)");
            Iterable $this$map$iv = loaded;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                Provider p = (Provider) item$iv$iv;
                Boolean bool = this$0.pinnedOverrides.get(p.getId());
                boolean zBooleanValue = bool != null ? bool.booleanValue() : p.isPinned();
                Boolean bool2 = this$0.recommendedOverrides.get(p.getId());
                boolean zBooleanValue2 = bool2 != null ? bool2.booleanValue() : p.isRecommended();
                Boolean bool3 = this$0.verifiedOverrides.get(p.getId());
                boolean zBooleanValue3 = bool3 != null ? bool3.booleanValue() : p.isVerified();
                Boolean bool4 = this$0.subscribedOverrides.get(p.getId());
                boolean zBooleanValue4 = bool4 != null ? bool4.booleanValue() : p.isSubscribed();
                Intrinsics.checkNotNull(p);
                destination$iv$iv.add(p.copy((1585281 & 1) != 0 ? p.id : null, (1585281 & 2) != 0 ? p.name : null, (1585281 & 4) != 0 ? p.category : null, (1585281 & 8) != 0 ? p.city : null, (1585281 & 16) != 0 ? p.phone : null, (1585281 & 32) != 0 ? p.description : null, (1585281 & 64) != 0 ? p.area : null, (1585281 & 128) != 0 ? p.rating : 0.0d, (1585281 & 256) != 0 ? p.isVerified : zBooleanValue3, (1585281 & 512) != 0 ? p.isPinned : zBooleanValue, (1585281 & 1024) != 0 ? p.isRecommended : zBooleanValue2, (1585281 & 2048) != 0 ? p.isSubscribed : zBooleanValue4, (1585281 & 4096) != 0 ? p.deviceId : null, (1585281 & 8192) != 0 ? p.imageUrl : null, (1585281 & 16384) != 0 ? p.portfolioImages : null, (1585281 & 32768) != 0 ? p.orderPriority : 0, (1585281 & 65536) != 0 ? p.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? p.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? p.allowedImageCount : 0, (1585281 & 524288) != 0 ? p.skills : null, (1585281 & 1048576) != 0 ? p.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? p.cardColorHex : null));
            }
            ArrayList arrayList = (List) destination$iv$iv;
            this$0._providers.setValue(arrayList);
            StringFormat $this$encodeToString$iv = Json.INSTANCE;
            $this$encodeToString$iv.getSerializersModule();
            this$0.saveToCache("providers", $this$encodeToString$iv.encodeToString(new ArrayListSerializer(Provider.INSTANCE.serializer()), arrayList));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$26(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List list = snap.toObjects(Category.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            List $this$sortedBy$iv = list;
            this$0._categories.setValue(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$lambda$47$lambda$26$lambda$25$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Category it = (Category) t;
                    Category it2 = (Category) t2;
                    return ComparisonsKt.compareValues(Integer.valueOf(it.getOrder()), Integer.valueOf(it2.getOrder()));
                }
            }));
            StringFormat $this$encodeToString$iv = Json.INSTANCE;
            $this$encodeToString$iv.getSerializersModule();
            this$0.saveToCache("categories", $this$encodeToString$iv.encodeToString(new ArrayListSerializer(BuiltinSerializersKt.getNullable(Category.INSTANCE.serializer())), list));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$28(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<City> objects = snap.toObjects(City.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._cities.setValue(objects);
            StringFormat $this$encodeToString$iv = Json.INSTANCE;
            $this$encodeToString$iv.getSerializersModule();
            this$0.saveToCache("cities", $this$encodeToString$iv.encodeToString(new ArrayListSerializer(BuiltinSerializersKt.getNullable(City.INSTANCE.serializer())), objects));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$30(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<ProviderCategoryRelation> objects = snap.toObjects(ProviderCategoryRelation.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._relations.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$32(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<Review> objects = snap.toObjects(Review.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._reviews.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$34(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<Banner> objects = snap.toObjects(Banner.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._banners.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$36(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<PendingProvider> objects = snap.toObjects(PendingProvider.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._pendingRequests.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$38(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<Report> objects = snap.toObjects(Report.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._reports.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$41(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable list = snap.toObjects(UserNotification.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            Iterable $this$sortedBy$iv = list;
            this$0._notifications.setValue(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$lambda$47$lambda$41$lambda$40$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    UserNotification it = (UserNotification) t;
                    UserNotification it2 = (UserNotification) t2;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getTimestamp()), Long.valueOf(it2.getTimestamp()));
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$43(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            List<AdminAccount> objects = snap.toObjects(AdminAccount.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            this$0._adminAccounts.setValue(objects);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupFirebaseRealtimeListener$lambda$47$lambda$46(MainViewModel this$0, QuerySnapshot snap, FirebaseFirestoreException firebaseFirestoreException) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (snap != null) {
            Iterable list = snap.toObjects(AuditLog.class);
            Intrinsics.checkNotNullExpressionValue(list, "toObjects(...)");
            Iterable $this$sortedByDescending$iv = list;
            this$0._auditLogs.setValue(CollectionsKt.sortedWith($this$sortedByDescending$iv, new Comparator() { // from class: com.maw.MainViewModel$setupFirebaseRealtimeListener$lambda$47$lambda$46$lambda$45$$inlined$sortedByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    AuditLog it = (AuditLog) t2;
                    AuditLog it2 = (AuditLog) t;
                    return ComparisonsKt.compareValues(Long.valueOf(it.getTimestamp()), Long.valueOf(it2.getTimestamp()));
                }
            }));
        }
    }

    public final void updateAppSettings(AppSettings newSettings, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(newSettings, "newSettings");
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._settings.setValue(newSettings);
        try {
            AppTheme.INSTANCE.m6270setPrimaryRed8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(newSettings.getPrimaryColorHex(), ColorKt.Color(4291694886L)));
            AppTheme.INSTANCE.m6268setAccentGold8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(newSettings.getAccentColorHex(), ColorKt.Color(4294956800L)));
            AppTheme.INSTANCE.m6269setDarkBg8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(newSettings.getBgColorHex(), ColorKt.Color(4279048990L)));
            AppTheme.INSTANCE.m6271setSurfaceDark8_81llA(MainActivityKt.m6504safeParseColor4WTKRHQ(newSettings.getSurfaceColorHex(), ColorKt.Color(4279642669L)));
        } catch (Exception e) {
        }
        addAuditLog(admin, "تم تعديل خصائص وتذييل التطبيق والروابط بنجاح");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("global")) == null) {
                return;
            }
            documentReferenceDocument.set(MainActivityKt.toMap(newSettings));
        } catch (Exception e2) {
        }
    }

    public final MutableStateFlow<Set<String>> getBlockedChatUsers() {
        return this.blockedChatUsers;
    }

    public final MutableStateFlow<Set<String>> getBlockedChatProviders() {
        return this.blockedChatProviders;
    }

    public final MutableStateFlow<Boolean> isUserChatDisabledOnly() {
        return this.isUserChatDisabledOnly;
    }

    public final MutableStateFlow<Boolean> isProviderChatDisabledOnly() {
        return this.isProviderChatDisabledOnly;
    }

    public final MutableStateFlow<Boolean> isAdminChatDisabledOnly() {
        return this.isAdminChatDisabledOnly;
    }

    public final MutableStateFlow<Boolean> isAllChatDisabled() {
        return this.isAllChatDisabled;
    }

    public final void toggleBlockUserChat(String username) {
        Intrinsics.checkNotNullParameter(username, "username");
        Set<String> value = this.blockedChatUsers.getValue();
        this.blockedChatUsers.setValue(value.contains(username) ? SetsKt.minus(value, username) : SetsKt.plus(value, username));
    }

    public final void toggleBlockProviderChat(String providerName) {
        Intrinsics.checkNotNullParameter(providerName, "providerName");
        Set<String> value = this.blockedChatProviders.getValue();
        this.blockedChatProviders.setValue(value.contains(providerName) ? SetsKt.minus(value, providerName) : SetsKt.plus(value, providerName));
    }

    public final void addAuditLog(String admin, String action) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(admin, "admin");
        Intrinsics.checkNotNullParameter(action, "action");
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        AuditLog newLog = new AuditLog(string, admin, action, 0L, 8, (DefaultConstructorMarker) null);
        this._auditLogs.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(newLog), (Iterable) this._auditLogs.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("audit_logs")) == null || (documentReferenceDocument = collectionReferenceCollection.document(newLog.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(newLog);
        } catch (Exception e) {
        }
    }

    public final void resetAppToFactoryDefaults(String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        CollectionReference collectionReferenceCollection3;
        DocumentReference documentReferenceDocument3;
        CollectionReference collectionReferenceCollection4;
        DocumentReference documentReferenceDocument4;
        CollectionReference collectionReferenceCollection5;
        Task<QuerySnapshot> task;
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._categories.setValue(this.defaultCategories);
        this._cities.setValue(this.defaultCities);
        this._providers.setValue(this.defaultProviders);
        this._chats.setValue(CollectionsKt.emptyList());
        this._chatMessages.setValue(CollectionsKt.emptyList());
        this._pendingRequests.setValue(CollectionsKt.emptyList());
        this._reports.setValue(CollectionsKt.emptyList());
        this._reviews.setValue(this.defaultReviews);
        this._settings.setValue(new AppSettings(null, 0, null, null, null, null, null, false, null, null, null, null, null, false, 0, 0, false, null, 0, null, false, 0, null, false, 0, 0, null, null, null, null, null, false, false, false, false, false, null, null, 0, 0.0f, 0, 0, null, null, false, null, 0, null, null, null, false, false, null, null, null, null, false, null, null, false, null, null, false, false, null, 0.0f, null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, false, false, false, false, false, null, null, -1, -1, -1, null));
        this._bookings.setValue(CollectionsKt.emptyList());
        StringFormat $this$encodeToString$iv = Json.INSTANCE;
        List<Category> list = this.defaultCategories;
        $this$encodeToString$iv.getSerializersModule();
        saveToCache("categories", $this$encodeToString$iv.encodeToString(new ArrayListSerializer(Category.INSTANCE.serializer()), list));
        StringFormat $this$encodeToString$iv2 = Json.INSTANCE;
        List<City> list2 = this.defaultCities;
        $this$encodeToString$iv2.getSerializersModule();
        saveToCache("cities", $this$encodeToString$iv2.encodeToString(new ArrayListSerializer(City.INSTANCE.serializer()), list2));
        StringFormat $this$encodeToString$iv3 = Json.INSTANCE;
        List<Provider> list3 = this.defaultProviders;
        $this$encodeToString$iv3.getSerializersModule();
        saveToCache("providers", $this$encodeToString$iv3.encodeToString(new ArrayListSerializer(Provider.INSTANCE.serializer()), list3));
        StringFormat $this$encodeToString$iv4 = Json.INSTANCE;
        AppSettings appSettings = new AppSettings(null, 0, null, null, null, null, null, false, null, null, null, null, null, false, 0, 0, false, null, 0, null, false, 0, null, false, 0, 0, null, null, null, null, null, false, false, false, false, false, null, null, 0, 0.0f, 0, 0, null, null, false, null, 0, null, null, null, false, false, null, null, null, null, false, null, null, false, null, null, false, false, null, 0.0f, null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, false, false, false, false, false, null, null, -1, -1, -1, null);
        $this$encodeToString$iv4.getSerializersModule();
        saveToCache("settings", $this$encodeToString$iv4.encodeToString(AppSettings.INSTANCE.serializer(), appSettings));
        addAuditLog(admin, "إعادة تعيين كاملة لبيانات المنصة للمصنع");
        try {
            Iterable collections = CollectionsKt.listOf((Object[]) new String[]{"categories", "cities", "providers", "chats", "messages", "banners", "reports", "bookings", "pending_requests"});
            Iterable $this$forEach$iv = collections;
            for (Object element$iv : $this$forEach$iv) {
                String col = (String) element$iv;
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection5 = firebaseFirestore.collection(col)) != null && (task = collectionReferenceCollection5.get()) != null) {
                    final MainViewModel$resetAppToFactoryDefaults$1$1 mainViewModel$resetAppToFactoryDefaults$1$1 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$resetAppToFactoryDefaults$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                            invoke2(querySnapshot);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(QuerySnapshot snap) {
                            for (QueryDocumentSnapshot doc : snap) {
                                try {
                                    doc.getReference().delete();
                                } catch (Exception e) {
                                }
                            }
                        }
                    };
                    task.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda18
                        @Override // com.google.android.gms.tasks.OnSuccessListener
                        public final void onSuccess(Object obj) {
                            MainViewModel.resetAppToFactoryDefaults$lambda$49$lambda$48(mainViewModel$resetAppToFactoryDefaults$1$1, obj);
                        }
                    });
                }
            }
            Iterable $this$forEach$iv2 = this.defaultCategories;
            for (Object element$iv2 : $this$forEach$iv2) {
                Category it = (Category) element$iv2;
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                if (firebaseFirestore2 != null && (collectionReferenceCollection4 = firebaseFirestore2.collection("categories")) != null && (documentReferenceDocument4 = collectionReferenceCollection4.document(it.getId())) != null) {
                    documentReferenceDocument4.set(it);
                }
            }
            Iterable $this$forEach$iv3 = this.defaultCities;
            for (Object element$iv3 : $this$forEach$iv3) {
                City it2 = (City) element$iv3;
                FirebaseFirestore firebaseFirestore3 = this.firestore;
                if (firebaseFirestore3 != null && (collectionReferenceCollection3 = firebaseFirestore3.collection("cities")) != null && (documentReferenceDocument3 = collectionReferenceCollection3.document(it2.getId())) != null) {
                    documentReferenceDocument3.set(it2);
                }
            }
            Iterable $this$forEach$iv4 = this.defaultProviders;
            for (Object element$iv4 : $this$forEach$iv4) {
                Provider it3 = (Provider) element$iv4;
                FirebaseFirestore firebaseFirestore4 = this.firestore;
                if (firebaseFirestore4 != null && (collectionReferenceCollection2 = firebaseFirestore4.collection("providers")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(it3.getId())) != null) {
                    documentReferenceDocument2.set(it3);
                }
            }
            FirebaseFirestore firebaseFirestore5 = this.firestore;
            if (firebaseFirestore5 == null || (collectionReferenceCollection = firebaseFirestore5.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("global")) == null) {
                return;
            }
            documentReferenceDocument.set(new AppSettings(null, 0, null, null, null, null, null, false, null, null, null, null, null, false, 0, 0, false, null, 0, null, false, 0, null, false, 0, 0, null, null, null, null, null, false, false, false, false, false, null, null, 0, 0.0f, 0, 0, null, null, false, null, 0, null, null, null, false, false, null, null, null, null, false, null, null, false, null, null, false, false, null, 0.0f, null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, false, false, false, false, false, null, null, -1, -1, -1, null));
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void resetAppToFactoryDefaults$lambda$49$lambda$48(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    public final void cleanTemporaryDataAndFiles(String admin) {
        CollectionReference collectionReferenceCollection;
        Task<QuerySnapshot> task;
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._chats.setValue(CollectionsKt.emptyList());
        this._chatMessages.setValue(CollectionsKt.emptyList());
        this._pendingRequests.setValue(CollectionsKt.emptyList());
        this._reports.setValue(CollectionsKt.emptyList());
        this._bookings.setValue(CollectionsKt.emptyList());
        addAuditLog(admin, "تنظيف كامل للتراكمات والمحادثات القديمة والملفات المؤقتة");
        try {
            Iterable clearColls = CollectionsKt.listOf((Object[]) new String[]{"chats", "messages", "reports", "bookings", "pending_requests"});
            Iterable $this$forEach$iv = clearColls;
            for (Object element$iv : $this$forEach$iv) {
                String col = (String) element$iv;
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection(col)) != null && (task = collectionReferenceCollection.get()) != null) {
                    final MainViewModel$cleanTemporaryDataAndFiles$1$1 mainViewModel$cleanTemporaryDataAndFiles$1$1 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel$cleanTemporaryDataAndFiles$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                            invoke2(querySnapshot);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(QuerySnapshot snap) {
                            for (QueryDocumentSnapshot doc : snap) {
                                try {
                                    doc.getReference().delete();
                                } catch (Exception e) {
                                }
                            }
                        }
                    };
                    task.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda20
                        @Override // com.google.android.gms.tasks.OnSuccessListener
                        public final void onSuccess(Object obj) {
                            MainViewModel.cleanTemporaryDataAndFiles$lambda$54$lambda$53(mainViewModel$cleanTemporaryDataAndFiles$1$1, obj);
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanTemporaryDataAndFiles$lambda$54$lambda$53(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    public final void deleteChatMessage(String msgId, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(msgId, "msgId");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<ChatMessage>> mutableStateFlow = this._chatMessages;
        Iterable $this$filter$iv = this._chatMessages.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            ChatMessage it = (ChatMessage) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), msgId)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "تم الرقابة وحذف رسالة دردشة");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("messages")) == null || (documentReferenceDocument = collectionReferenceCollection.document(msgId)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void updateChatMessage(String msgId, String newContent, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Iterable $this$map$iv;
        Collection destination$iv$iv;
        ChatMessage chatMessageCopy$default;
        Intrinsics.checkNotNullParameter(msgId, "msgId");
        Intrinsics.checkNotNullParameter(newContent, "newContent");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<ChatMessage>> mutableStateFlow = this._chatMessages;
        Iterable $this$map$iv2 = this._chatMessages.getValue();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Collection destination$iv$iv3 = destination$iv$iv2;
        for (Object item$iv$iv : $this$map$iv2) {
            ChatMessage it = (ChatMessage) item$iv$iv;
            if (Intrinsics.areEqual(it.getId(), msgId)) {
                $this$map$iv = $this$map$iv2;
                destination$iv$iv = destination$iv$iv3;
                chatMessageCopy$default = ChatMessage.copy$default(it, null, null, null, null, newContent, 0L, 47, null);
            } else {
                $this$map$iv = $this$map$iv2;
                destination$iv$iv = destination$iv$iv3;
                chatMessageCopy$default = it;
            }
            destination$iv$iv.add(chatMessageCopy$default);
            destination$iv$iv3 = destination$iv$iv;
            $this$map$iv2 = $this$map$iv;
        }
        mutableStateFlow.setValue((List) destination$iv$iv3);
        addAuditLog(admin, "تعديل محتوى رسالة دردشة رقابياً");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("messages")) == null || (documentReferenceDocument = collectionReferenceCollection.document(msgId)) == null) {
                return;
            }
            documentReferenceDocument.update("message", newContent, new Object[0]);
        } catch (Exception e) {
        }
    }

    public final void deleteChatRoom(String roomId, String admin) {
        CollectionReference collectionReferenceCollection;
        Query queryWhereEqualTo;
        Task<QuerySnapshot> task;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(roomId, "roomId");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Chat>> mutableStateFlow = this._chats;
        Iterable $this$filter$iv = this._chats.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Chat it = (Chat) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), roomId)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        MutableStateFlow<List<ChatMessage>> mutableStateFlow2 = this._chatMessages;
        Iterable $this$filter$iv2 = this._chatMessages.getValue();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            ChatMessage it2 = (ChatMessage) element$iv$iv2;
            if (!Intrinsics.areEqual(it2.getChatId(), roomId)) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        mutableStateFlow2.setValue((List) destination$iv$iv2);
        addAuditLog(admin, "إيقاف وحذف غرفة الدردشة رقم: " + roomId);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("chats")) != null && (documentReferenceDocument = collectionReferenceCollection2.document(roomId)) != null) {
                documentReferenceDocument.delete();
            }
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("messages")) == null || (queryWhereEqualTo = collectionReferenceCollection.whereEqualTo("chatId", roomId)) == null || (task = queryWhereEqualTo.get()) == null) {
                return;
            }
            final AnonymousClass3 anonymousClass3 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel.deleteChatRoom.3
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                    invoke2(querySnapshot);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(QuerySnapshot snap) {
                    if (snap == null) {
                        return;
                    }
                    QuerySnapshot $this$forEach$iv = snap;
                    for (Object element$iv : $this$forEach$iv) {
                        QueryDocumentSnapshot doc = (QueryDocumentSnapshot) element$iv;
                        doc.getReference().delete();
                    }
                }
            };
            task.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda11
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    MainViewModel.deleteChatRoom$lambda$59(anonymousClass3, obj);
                }
            });
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteChatRoom$lambda$59(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    public final void registerPendingProvider(PendingProvider p) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(p, "p");
        this._pendingRequests.setValue(CollectionsKt.plus((Collection<? extends PendingProvider>) this._pendingRequests.getValue(), p));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("pending_requests")) == null || (documentReferenceDocument = collectionReferenceCollection.document(p.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(p);
        } catch (Exception e) {
        }
    }

    public final void approveProviderRequest(PendingProvider pp, String admin) {
        Object next;
        String category;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        CollectionReference collectionReferenceCollection3;
        DocumentReference documentReferenceDocument3;
        CollectionReference collectionReferenceCollection4;
        DocumentReference documentReferenceDocument4;
        Intrinsics.checkNotNullParameter(pp, "pp");
        Intrinsics.checkNotNullParameter(admin, "admin");
        String id = pp.getId();
        if (StringsKt.isBlank(id)) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            id = string;
        }
        Provider newP = new Provider(id, pp.getName(), pp.getCategory(), pp.getCity(), pp.getPhone(), pp.getDescription(), pp.getArea(), 0.0d, true, false, false, false, pp.getDeviceId(), pp.getSelfieImageBase64(), (List) pp.getPortfolioImages(), pp.getOrderPriority(), false, false, 0, (String) null, pp.getNationalIdImageBase64(), (String) null, 3083904, (DefaultConstructorMarker) null);
        this._providers.setValue(CollectionsKt.plus((Collection<? extends Provider>) this._providers.getValue(), newP));
        MutableStateFlow<List<PendingProvider>> mutableStateFlow = this._pendingRequests;
        Iterable $this$filter$iv = this._pendingRequests.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            PendingProvider it = (PendingProvider) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), pp.getId())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "الموافقة على تفعيل مقدم الخدمة: " + pp.getName());
        Iterator<T> it2 = this._categories.getValue().iterator();
        while (true) {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            Category it3 = (Category) next;
            if (Intrinsics.areEqual(it3.getId(), pp.getCategory())) {
                break;
            }
        }
        Category categoryObj = (Category) next;
        if (categoryObj == null || (category = categoryObj.getNameAr()) == null) {
            category = pp.getCategory();
        }
        String categoryLabelAr = category;
        String string2 = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        UserNotification notif = new UserNotification(string2, "🎉 تم قبول واعتماد كادر مهني جديد", "نود إعلامكم أنه قد تم قبول واعتماد طلب الكادر المتميز: (" + pp.getName() + ") في تخصص: (" + categoryLabelAr + ") بنجاح. حسابه الآن معتمد بالكامل في رادار الدليل.", "الآن", System.currentTimeMillis(), false, "success", (String) null, (String) null, 384, (DefaultConstructorMarker) null);
        this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(notif), (Iterable) this._notifications.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection4 = firebaseFirestore.collection("notifications")) != null && (documentReferenceDocument4 = collectionReferenceCollection4.document(notif.getId())) != null) {
                documentReferenceDocument4.set(notif);
            }
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 != null && (collectionReferenceCollection3 = firebaseFirestore2.collection("providers")) != null && (documentReferenceDocument3 = collectionReferenceCollection3.document(newP.getId())) != null) {
                documentReferenceDocument3.set(newP);
            }
            ProviderCategoryRelation rel = new ProviderCategoryRelation(newP.getId() + "_" + newP.getCategory(), newP.getId(), newP.getCategory());
            FirebaseFirestore firebaseFirestore3 = this.firestore;
            if (firebaseFirestore3 != null && (collectionReferenceCollection2 = firebaseFirestore3.collection("provider_category_relations")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(rel.getId())) != null) {
                documentReferenceDocument2.set(rel);
            }
            FirebaseFirestore firebaseFirestore4 = this.firestore;
            if (firebaseFirestore4 == null || (collectionReferenceCollection = firebaseFirestore4.collection("pending_requests")) == null || (documentReferenceDocument = collectionReferenceCollection.document(pp.getId())) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void rejectProviderRequest(String id, String reason, String admin) {
        Object next;
        String name;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(admin, "admin");
        Iterator<T> it = this._pendingRequests.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            PendingProvider it2 = (PendingProvider) next;
            if (Intrinsics.areEqual(it2.getId(), id)) {
                break;
            }
        }
        PendingProvider pp = (PendingProvider) next;
        if (pp == null || (name = pp.getName()) == null) {
            name = "مقدم طلب";
        }
        String name2 = name;
        MutableStateFlow<List<PendingProvider>> mutableStateFlow = this._pendingRequests;
        Iterable $this$filter$iv = this._pendingRequests.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            PendingProvider it3 = (PendingProvider) element$iv$iv;
            PendingProvider pp2 = pp;
            if (!Intrinsics.areEqual(it3.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
            pp = pp2;
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "رفض الطلب المقدم برقم " + id + " لسبب " + reason);
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        UserNotification notif = new UserNotification(string, "⚠️ رفض طلب أحد مزودي الخدمات", "تنبيه: تم رفض طلب انضمام العضو المسمى: (" + name2 + ") لسبب عدم استيفاء كامل الوثائق أو الصور المطلوبة. بإمكان العضو المحاولة مجدداً.", "الآن", System.currentTimeMillis(), false, "error", (String) null, (String) null, 384, (DefaultConstructorMarker) null);
        this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(notif), (Iterable) this._notifications.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("notifications")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(notif.getId())) != null) {
                documentReferenceDocument2.set(notif);
            }
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("pending_requests")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void addProviderManual(Provider p, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._providers.setValue(CollectionsKt.plus((Collection<? extends Provider>) this._providers.getValue(), p));
        addAuditLog(admin, "إضافة يدوية لمزود الخدمة: " + p.getName());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("providers")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(p.getId())) != null) {
                documentReferenceDocument2.set(p);
            }
            ProviderCategoryRelation rel = new ProviderCategoryRelation(p.getId() + "_" + p.getCategory(), p.getId(), p.getCategory());
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("provider_category_relations")) == null || (documentReferenceDocument = collectionReferenceCollection.document(rel.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(rel);
        } catch (Exception e) {
        }
    }

    public final void updateProviderManual(Provider p, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Provider>> mutableStateFlow = this._providers;
        Iterable $this$map$iv = this._providers.getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Provider it = (Provider) item$iv$iv;
            if (Intrinsics.areEqual(it.getId(), p.getId())) {
                it = p;
            }
            destination$iv$iv.add(it);
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "تحديث معلومات كادر المهنة المسمى: " + p.getName());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("providers")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(p.getId())) != null) {
                documentReferenceDocument2.set(p);
            }
            ProviderCategoryRelation rel = new ProviderCategoryRelation(p.getId() + "_" + p.getCategory(), p.getId(), p.getCategory());
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("provider_category_relations")) == null || (documentReferenceDocument = collectionReferenceCollection.document(rel.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(rel);
        } catch (Exception e) {
        }
    }

    public final void requestServiceAppointment(String providerId, String providerName, String serviceDetails, String preferredTime, String tripleName, String phoneNumber, String serviceType, String residencePlc) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Intrinsics.checkNotNullParameter(providerName, "providerName");
        Intrinsics.checkNotNullParameter(serviceDetails, "serviceDetails");
        Intrinsics.checkNotNullParameter(preferredTime, "preferredTime");
        Intrinsics.checkNotNullParameter(tripleName, "tripleName");
        Intrinsics.checkNotNullParameter(phoneNumber, "phoneNumber");
        Intrinsics.checkNotNullParameter(serviceType, "serviceType");
        Intrinsics.checkNotNullParameter(residencePlc, "residencePlc");
        String bookingId = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(bookingId, "toString(...)");
        String username = StringsKt.isBlank(this.loggedInUsername.getValue()) ^ true ? this.loggedInUsername.getValue() : "مستخدم الدليل";
        Booking newBooking = new Booking(bookingId, "user_device", username, providerId, providerName, serviceDetails, preferredTime, "pending", System.currentTimeMillis(), tripleName, phoneNumber, serviceType, residencePlc);
        this._bookings.setValue(CollectionsKt.plus((Collection<? extends Booking>) this._bookings.getValue(), newBooking));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection("bookings")) != null && (documentReferenceDocument = collectionReferenceCollection.document(bookingId)) != null) {
                documentReferenceDocument.set(newBooking);
            }
        } catch (Exception e) {
        }
        addNotification("⌛ تم إرسال طلب موعد الخدمة لـ " + providerName, "تفاصيل طلبك: " + serviceDetails + " (" + preferredTime + "). الطلب الآن قيد المراجعة الفورية.", "info");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07221(bookingId, providerName, serviceDetails, preferredTime, null), 3, null);
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$requestServiceAppointment$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$requestServiceAppointment$1", f = "MainActivity.kt", i = {}, l = {1771}, m = "invokeSuspend", n = {}, s = {})
    static final class C07221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $bookingId;
        final /* synthetic */ String $preferredTime;
        final /* synthetic */ String $providerName;
        final /* synthetic */ String $serviceDetails;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07221(String str, String str2, String str3, String str4, Continuation<? super C07221> continuation) {
            super(2, continuation);
            this.$bookingId = str;
            this.$providerName = str2;
            this.$serviceDetails = str3;
            this.$preferredTime = str4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainViewModel.this.new C07221(this.$bookingId, this.$providerName, this.$serviceDetails, this.$preferredTime, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            C07221 c07221;
            Object next;
            CollectionReference collectionReferenceCollection;
            DocumentReference documentReferenceDocument;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    c07221 = this;
                    c07221.label = 1;
                    if (DelayKt.delay(4000L, c07221) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure($result);
                    c07221 = this;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Iterable iterable = (Iterable) MainViewModel.this._bookings.getValue();
            String str = c07221.$bookingId;
            Iterator it = iterable.iterator();
            while (true) {
                if (it.hasNext()) {
                    next = it.next();
                    if (Intrinsics.areEqual(((Booking) next).getId(), str)) {
                    }
                } else {
                    next = null;
                }
            }
            Booking current = (Booking) next;
            if (current != null && Intrinsics.areEqual(current.getStatus(), "pending")) {
                Booking current2 = current.copy((415 & 1) != 0 ? current.id : null, (415 & 2) != 0 ? current.userId : null, (415 & 4) != 0 ? current.userName : null, (415 & 8) != 0 ? current.providerId : null, (415 & 16) != 0 ? current.providerName : null, (415 & 32) != 0 ? current.details : null, (415 & 64) != 0 ? current.preferredTime : null, (415 & 128) != 0 ? current.status : "approved", (415 & 256) != 0 ? current.timestamp : 0L, (415 & 512) != 0 ? current.tripleName : null, (415 & 1024) != 0 ? current.phoneNumber : null, (415 & 2048) != 0 ? current.serviceType : null, (415 & 4096) != 0 ? current.residencePlc : null);
                MutableStateFlow mutableStateFlow = MainViewModel.this._bookings;
                Iterable $this$map$iv = (Iterable) MainViewModel.this._bookings.getValue();
                String str2 = c07221.$bookingId;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Booking it2 = (Booking) item$iv$iv;
                    if (Intrinsics.areEqual(it2.getId(), str2)) {
                        it2 = current2;
                    }
                    destination$iv$iv.add(it2);
                }
                mutableStateFlow.setValue((List) destination$iv$iv);
                try {
                    FirebaseFirestore firebaseFirestore = MainViewModel.this.firestore;
                    if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection("bookings")) != null && (documentReferenceDocument = collectionReferenceCollection.document(c07221.$bookingId)) != null) {
                        documentReferenceDocument.set(current2);
                    }
                } catch (Exception e) {
                }
                MainViewModel.this.addNotification("✅ تم تأكيد موعد الخدمة بنجاح!", "وافق المهني " + c07221.$providerName + " على طلبك للقيام بـ (" + c07221.$serviceDetails + ") وحدد موعد الحضور حسب رغبتك: (" + c07221.$preferredTime + ").", "appointment_updated");
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void updateBooking$default(MainViewModel mainViewModel, Booking booking, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "مشرف";
        }
        mainViewModel.updateBooking(booking, str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void updateBooking(com.maw.Booking r13, java.lang.String r14) {
        /*
            Method dump skipped, instruction units count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.MainViewModel.updateBooking(com.maw.Booking, java.lang.String):void");
    }

    public static /* synthetic */ void deleteBooking$default(MainViewModel mainViewModel, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "مشرف";
        }
        mainViewModel.deleteBooking(str, str2);
    }

    public final void deleteBooking(String id, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Booking>> mutableStateFlow = this._bookings;
        Iterable $this$filter$iv = this._bookings.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Booking it = (Booking) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "حذف حجز موعد خدمة من السيرفر كلياً");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("bookings")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void addNotificationWithCategoryAndRecipient(UserNotification not) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(not, "not");
        this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(not), (Iterable) this._notifications.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("notifications")) == null || (documentReferenceDocument = collectionReferenceCollection.document(not.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(not);
        } catch (Exception e) {
        }
    }

    public final void deleteNotification(String id) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        MutableStateFlow<List<UserNotification>> mutableStateFlow = this._notifications;
        Iterable $this$filter$iv = this._notifications.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            UserNotification it = (UserNotification) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("notifications")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void deleteProvider(String id, String admin) {
        Object next;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), id)) {
                break;
            }
        }
        Provider p = (Provider) next;
        if (p != null) {
            MutableStateFlow<List<Provider>> mutableStateFlow = this._providers;
            Iterable $this$filter$iv = this._providers.getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Provider it3 = (Provider) element$iv$iv;
                Provider p2 = p;
                if (!Intrinsics.areEqual(it3.getId(), id)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                p = p2;
            }
            mutableStateFlow.setValue((List) destination$iv$iv);
            addAuditLog(admin, "قامت الإدارة بإزالة كادر المهنة: " + p.getName());
            try {
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("providers")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(id)) != null) {
                    documentReferenceDocument2.delete();
                }
                String relId = p.getId() + "_" + p.getCategory();
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("provider_category_relations")) == null || (documentReferenceDocument = collectionReferenceCollection.document(relId)) == null) {
                    return;
                }
                documentReferenceDocument.delete();
            } catch (Exception e) {
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void submitReview(Review review) {
        double avgRating;
        Object next;
        FirebaseFirestore firebaseFirestore;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Provider providerCopy;
        Intrinsics.checkNotNullParameter(review, "review");
        this._reviews.setValue(CollectionsKt.plus((Collection<? extends Review>) this._reviews.getValue(), review));
        String providerId = review.getProviderId();
        Iterable $this$filter$iv = this._reviews.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (Intrinsics.areEqual(((Review) element$iv$iv).getProviderId(), providerId)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List providerReviews = (List) destination$iv$iv;
        if (!providerReviews.isEmpty()) {
            Iterator it = providerReviews.iterator();
            int rating = 0;
            while (it.hasNext()) {
                rating += ((Review) it.next()).getRating();
            }
            int total = rating;
            double avg = ((double) total) / ((double) providerReviews.size());
            String str = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Double.valueOf(avg)}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            Double doubleOrNull = StringsKt.toDoubleOrNull(str);
            avgRating = doubleOrNull != null ? doubleOrNull.doubleValue() : avg;
        } else {
            avgRating = review.getRating();
        }
        MutableStateFlow<List<Provider>> mutableStateFlow = this._providers;
        Iterable $this$map$iv = this._providers.getValue();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Provider it2 = (Provider) item$iv$iv;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                providerCopy = it2.copy((1585281 & 1) != 0 ? it2.id : null, (1585281 & 2) != 0 ? it2.name : null, (1585281 & 4) != 0 ? it2.category : null, (1585281 & 8) != 0 ? it2.city : null, (1585281 & 16) != 0 ? it2.phone : null, (1585281 & 32) != 0 ? it2.description : null, (1585281 & 64) != 0 ? it2.area : null, (1585281 & 128) != 0 ? it2.rating : avgRating, (1585281 & 256) != 0 ? it2.isVerified : false, (1585281 & 512) != 0 ? it2.isPinned : false, (1585281 & 1024) != 0 ? it2.isRecommended : false, (1585281 & 2048) != 0 ? it2.isSubscribed : false, (1585281 & 4096) != 0 ? it2.deviceId : null, (1585281 & 8192) != 0 ? it2.imageUrl : null, (1585281 & 16384) != 0 ? it2.portfolioImages : null, (1585281 & 32768) != 0 ? it2.orderPriority : 0, (1585281 & 65536) != 0 ? it2.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? it2.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? it2.allowedImageCount : 0, (1585281 & 524288) != 0 ? it2.skills : null, (1585281 & 1048576) != 0 ? it2.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? it2.cardColorHex : null);
            } else {
                providerCopy = it2;
            }
            destination$iv$iv2.add(providerCopy);
        }
        mutableStateFlow.setValue((List) destination$iv$iv2);
        try {
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 != null && (collectionReferenceCollection2 = firebaseFirestore2.collection("reviews")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(review.getId())) != null) {
                documentReferenceDocument2.set(review);
            }
            Iterator<T> it3 = this._providers.getValue().iterator();
            while (true) {
                if (it3.hasNext()) {
                    next = it3.next();
                    if (Intrinsics.areEqual(((Provider) next).getId(), providerId)) {
                        break;
                    }
                } else {
                    next = null;
                    break;
                }
            }
            Provider updatedProvider = (Provider) next;
            if (updatedProvider == null || (firebaseFirestore = this.firestore) == null || (collectionReferenceCollection = firebaseFirestore.collection("providers")) == null || (documentReferenceDocument = collectionReferenceCollection.document(providerId)) == null) {
                return;
            }
            documentReferenceDocument.set(updatedProvider);
        } catch (Exception e) {
        }
    }

    public final void toggleProviderStatus(String id, boolean isPinned, boolean isRecommended, boolean isVerified, boolean isSubscribed, String adminName) {
        Provider updated;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        SharedPreferences.Editor $this$toggleProviderStatus_u24lambda_u2476;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(adminName, "adminName");
        this.pinnedOverrides.put(id, Boolean.valueOf(isPinned));
        this.recommendedOverrides.put(id, Boolean.valueOf(isRecommended));
        this.verifiedOverrides.put(id, Boolean.valueOf(isVerified));
        this.subscribedOverrides.put(id, Boolean.valueOf(isSubscribed));
        try {
            SharedPreferences sharedPreferences = this.sharedPrefs;
            if (sharedPreferences != null && ($this$toggleProviderStatus_u24lambda_u2476 = sharedPreferences.edit()) != null) {
                StringFormat $this$encodeToString$iv = Json.INSTANCE;
                Map<String, Boolean> map = this.pinnedOverrides;
                $this$encodeToString$iv.getSerializersModule();
                $this$toggleProviderStatus_u24lambda_u2476.putString("pinned_overrides", $this$encodeToString$iv.encodeToString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), map));
                StringFormat $this$encodeToString$iv2 = Json.INSTANCE;
                Map<String, Boolean> map2 = this.recommendedOverrides;
                $this$encodeToString$iv2.getSerializersModule();
                $this$toggleProviderStatus_u24lambda_u2476.putString("recommended_overrides", $this$encodeToString$iv2.encodeToString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), map2));
                StringFormat $this$encodeToString$iv3 = Json.INSTANCE;
                Map<String, Boolean> map3 = this.verifiedOverrides;
                $this$encodeToString$iv3.getSerializersModule();
                $this$toggleProviderStatus_u24lambda_u2476.putString("verified_overrides", $this$encodeToString$iv3.encodeToString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), map3));
                StringFormat $this$encodeToString$iv4 = Json.INSTANCE;
                Map<String, Boolean> map4 = this.subscribedOverrides;
                $this$encodeToString$iv4.getSerializersModule();
                $this$toggleProviderStatus_u24lambda_u2476.putString("subscribed_overrides", $this$encodeToString$iv4.encodeToString(new LinkedHashMapSerializer(StringSerializer.INSTANCE, BooleanSerializer.INSTANCE), map4));
                $this$toggleProviderStatus_u24lambda_u2476.apply();
            }
        } catch (Exception e) {
        }
        MutableStateFlow<List<Provider>> mutableStateFlow = this._providers;
        Iterable $this$map$iv = this._providers.getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Provider it = (Provider) item$iv$iv;
            if (Intrinsics.areEqual(it.getId(), id)) {
                updated = it.copy((1585281 & 1) != 0 ? it.id : null, (1585281 & 2) != 0 ? it.name : null, (1585281 & 4) != 0 ? it.category : null, (1585281 & 8) != 0 ? it.city : null, (1585281 & 16) != 0 ? it.phone : null, (1585281 & 32) != 0 ? it.description : null, (1585281 & 64) != 0 ? it.area : null, (1585281 & 128) != 0 ? it.rating : 0.0d, (1585281 & 256) != 0 ? it.isVerified : isVerified, (1585281 & 512) != 0 ? it.isPinned : isPinned, (1585281 & 1024) != 0 ? it.isRecommended : isRecommended, (1585281 & 2048) != 0 ? it.isSubscribed : isSubscribed, (1585281 & 4096) != 0 ? it.deviceId : null, (1585281 & 8192) != 0 ? it.imageUrl : null, (1585281 & 16384) != 0 ? it.portfolioImages : null, (1585281 & 32768) != 0 ? it.orderPriority : 0, (1585281 & 65536) != 0 ? it.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? it.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? it.allowedImageCount : 0, (1585281 & 524288) != 0 ? it.skills : null, (1585281 & 1048576) != 0 ? it.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? it.cardColorHex : null);
                try {
                    FirebaseFirestore firebaseFirestore = this.firestore;
                    if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection("providers")) != null && (documentReferenceDocument = collectionReferenceCollection.document(id)) != null) {
                        documentReferenceDocument.set(updated);
                    }
                } catch (Exception e2) {
                }
            } else {
                updated = it;
            }
            destination$iv$iv.add(updated);
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(adminName, "تغيير حالة الاشتراك والترخيص لرمز العضو المهني " + id);
    }

    public final void addCategory(Category cat, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(cat, "cat");
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._categories.setValue(CollectionsKt.plus((Collection<? extends Category>) this._categories.getValue(), cat));
        addAuditLog(admin, "إضافة فئة خدمة جديدة: " + cat.getNameAr());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("categories")) == null || (documentReferenceDocument = collectionReferenceCollection.document(cat.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(cat);
        } catch (Exception e) {
        }
    }

    public final void deleteCategory(String id, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Category>> mutableStateFlow = this._categories;
        Iterable $this$filter$iv = this._categories.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Category it = (Category) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "حذف فئة الخدمة برقم: " + id);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("categories")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void addCity(City city, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(city, "city");
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._cities.setValue(CollectionsKt.plus((Collection<? extends City>) this._cities.getValue(), city));
        addAuditLog(admin, "إدراج مدينة يمنية مستهدفة جديدة: " + city.getNameAr());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("cities")) == null || (documentReferenceDocument = collectionReferenceCollection.document(city.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(city);
        } catch (Exception e) {
        }
    }

    public final void deleteCity(String id, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<City>> mutableStateFlow = this._cities;
        Iterable $this$filter$iv = this._cities.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            City it = (City) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "إزالة المدينة المستهدفة ذات الرمز: " + id);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("cities")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void addReport(Report rep) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(rep, "rep");
        this._reports.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(rep), (Iterable) this._reports.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("reports")) == null || (documentReferenceDocument = collectionReferenceCollection.document(rep.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(rep);
        } catch (Exception e) {
        }
    }

    public final void approveReport(String id, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Report>> mutableStateFlow = this._reports;
        Iterable $this$filter$iv = this._reports.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Report it = (Report) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "إدارة البلاغات: تم حل ومراجعة الشكوى رقم " + id);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("reports")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void startChatWithProvider(String userId, String providerId, String providerName) {
        Object next;
        String id;
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Intrinsics.checkNotNullParameter(providerName, "providerName");
        Iterator<T> it = this._chats.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Chat it2 = (Chat) next;
            if (Intrinsics.areEqual(it2.getProviderId(), providerId)) {
                break;
            }
        }
        Chat existing = (Chat) next;
        if (existing == null || (id = existing.getId()) == null) {
            id = "chat_" + userId + "_" + providerId;
        }
        String roomId = id;
        if (existing == null) {
            Chat newChat = new Chat(roomId, userId, providerId, providerName, "بدء محادثة جديدة...", 0L, 32, (DefaultConstructorMarker) null);
            this._chats.setValue(CollectionsKt.plus((Collection<? extends Chat>) this._chats.getValue(), newChat));
        }
        this.currentChatRoomId.setValue(roomId);
    }

    public final void sendChatMessage(String chatId, String senderName, String senderType, String messageText) {
        Object next;
        String providerName;
        Iterable $this$map$iv;
        Collection destination$iv$iv;
        MutableStateFlow<List<Chat>> mutableStateFlow;
        Chat chatCopy$default;
        Intrinsics.checkNotNullParameter(chatId, "chatId");
        Intrinsics.checkNotNullParameter(senderName, "senderName");
        Intrinsics.checkNotNullParameter(senderType, "senderType");
        Intrinsics.checkNotNullParameter(messageText, "messageText");
        if (StringsKt.isBlank(messageText) || this.isAllChatDisabled.getValue().booleanValue()) {
            return;
        }
        if (Intrinsics.areEqual(senderType, "user") && this.isUserChatDisabledOnly.getValue().booleanValue()) {
            return;
        }
        if (Intrinsics.areEqual(senderType, "provider") && this.isProviderChatDisabledOnly.getValue().booleanValue()) {
            return;
        }
        if (Intrinsics.areEqual(senderType, "admin") && this.isAdminChatDisabledOnly.getValue().booleanValue()) {
            return;
        }
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        ChatMessage msg = new ChatMessage(string, chatId, senderName, senderType, messageText, 0L, 32, (DefaultConstructorMarker) null);
        this._chatMessages.setValue(CollectionsKt.plus((Collection<? extends ChatMessage>) this._chatMessages.getValue(), msg));
        MutableStateFlow<List<Chat>> mutableStateFlow2 = this._chats;
        Iterable $this$map$iv2 = this._chats.getValue();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Collection destination$iv$iv3 = destination$iv$iv2;
        for (Object item$iv$iv : $this$map$iv2) {
            Chat it = (Chat) item$iv$iv;
            if (Intrinsics.areEqual(it.getId(), chatId)) {
                $this$map$iv = $this$map$iv2;
                destination$iv$iv = destination$iv$iv3;
                mutableStateFlow = mutableStateFlow2;
                chatCopy$default = Chat.copy$default(it, null, null, null, null, messageText, System.currentTimeMillis(), 15, null);
            } else {
                $this$map$iv = $this$map$iv2;
                destination$iv$iv = destination$iv$iv3;
                mutableStateFlow = mutableStateFlow2;
                chatCopy$default = it;
            }
            Collection destination$iv$iv4 = destination$iv$iv;
            destination$iv$iv4.add(chatCopy$default);
            destination$iv$iv3 = destination$iv$iv4;
            $this$map$iv2 = $this$map$iv;
            mutableStateFlow2 = mutableStateFlow;
        }
        mutableStateFlow2.setValue((List) destination$iv$iv3);
        if (Intrinsics.areEqual(senderType, "user")) {
            Iterator<T> it2 = this._chats.getValue().iterator();
            while (true) {
                if (it2.hasNext()) {
                    next = it2.next();
                    if (Intrinsics.areEqual(((Chat) next).getId(), chatId)) {
                        break;
                    }
                } else {
                    next = null;
                    break;
                }
            }
            Chat chatObj = (Chat) next;
            if (chatObj == null || (providerName = chatObj.getProviderName()) == null) {
                providerName = "المهني";
            }
            String pName = providerName;
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07242(messageText, chatId, pName, this, null), 3, null);
        }
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$sendChatMessage$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$sendChatMessage$2", f = "MainActivity.kt", i = {}, l = {1996}, m = "invokeSuspend", n = {}, s = {})
    static final class C07242 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $chatId;
        final /* synthetic */ String $messageText;
        final /* synthetic */ String $pName;
        int label;
        final /* synthetic */ MainViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07242(String str, String str2, String str3, MainViewModel mainViewModel, Continuation<? super C07242> continuation) {
            super(2, continuation);
            this.$messageText = str;
            this.$chatId = str2;
            this.$pName = str3;
            this.this$0 = mainViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C07242(this.$messageText, this.$chatId, this.$pName, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07242) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            C07242 c07242;
            String simulatedReply;
            Collection destination$iv$iv;
            Chat chatCopy$default;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    this.label = 1;
                    if (DelayKt.delay(1200L, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    c07242 = this;
                    break;
                    break;
                case 1:
                    c07242 = this;
                    ResultKt.throwOnFailure($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "سعر", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "بكم", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "تكلف", false, 2, (Object) null)) {
                simulatedReply = "يا أهلًا بك يا غالي! بخصوص التكلفة والأسعار، بنحددها بشكل دقيق بعد الفحص والمعاينة المباشرة عشان نعطيك أنسب سعر يرضيك. تحب ننسق موعد للمعاينة؟";
            } else if (StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "موعد", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "وقت", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "متي", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) c07242.$messageText, (CharSequence) "متى", false, 2, (Object) null)) {
                simulatedReply = "يا هلا ومرحب، أنا جاهز وتحت الخدمة اليوم أو غداً بالوقت اللي تفضله. عطني عنوانك وساعة الحضور المناسبة لك وتدلل!";
            } else {
                simulatedReply = "حبّاب وراسي فوق، تسعدني خدمتك وتلبية طلبك بأفضل جودة وسعر إن شاء الله! وين مكانك بالضبط وسأتجه إليك حالًا.";
            }
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            ChatMessage replyMsg = new ChatMessage(string, c07242.$chatId, c07242.$pName, "provider", simulatedReply, System.currentTimeMillis());
            c07242.this$0._chatMessages.setValue(CollectionsKt.plus((Collection<? extends ChatMessage>) c07242.this$0._chatMessages.getValue(), replyMsg));
            MutableStateFlow mutableStateFlow = c07242.this$0._chats;
            Iterable $this$map$iv = (Iterable) c07242.this$0._chats.getValue();
            String str = c07242.$chatId;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                Chat it = (Chat) item$iv$iv;
                if (Intrinsics.areEqual(it.getId(), str)) {
                    destination$iv$iv = destination$iv$iv2;
                    chatCopy$default = Chat.copy$default(it, null, null, null, null, simulatedReply, System.currentTimeMillis(), 15, null);
                } else {
                    destination$iv$iv = destination$iv$iv2;
                    chatCopy$default = it;
                }
                destination$iv$iv.add(chatCopy$default);
                destination$iv$iv2 = destination$iv$iv;
            }
            mutableStateFlow.setValue((List) destination$iv$iv2);
            c07242.this$0.addNotification("💬 رسالة جديدة من " + c07242.$pName, simulatedReply, "msg_received");
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getSimulatedYemeniLocalReply(String prompt) {
        Object next;
        String normalized = StringsKt.trim((CharSequence) prompt).toString();
        Object obj = null;
        if (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "رقم", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تواصل", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "دعم", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مساعدة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "اتصال", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تلفون", false, 2, (Object) null)) {
            return "يا هلا بك يا طيب! يمكنك التواصل مباشرة مع مبادرتنا ودعمنا عبر الرقم الموثق: " + this._settings.getValue().getInitiativeSupportNumber() + " - نحن هنا لمساعدتكم وتقديم الدعم لكافة مهنيي اليمن أينما كانوا.";
        }
        Iterator<T> it = this._categories.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Category cat = (Category) next;
            if (StringsKt.contains$default((CharSequence) normalized, (CharSequence) cat.getNameAr(), false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) cat.getNameEn(), false, 2, (Object) null) || (Intrinsics.areEqual(cat.getId(), "plumbing") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "سباك", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "سباكة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تسرب", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "حنفية", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "ماتور", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "أنبوب", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "electricity") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "كهربا", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "كهربائي", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "لمبة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "انارة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "شاحن", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "طاقة", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "conditioning") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مكيف", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تكييف", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تبريد", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "برد", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "حر", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "carpentry") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "نجار", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "نجارة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "خشب", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "اثاث", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "construction") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "بناء", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مقاول", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "دهان", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "اسمنت", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "لياسة", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "computers") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "برمجة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تلفون", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "هاتف", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "جوال", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "كمبيوتر", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "شاشة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "فرمته", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "medicine") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "طب", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "طبيب", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "أطباء", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "دكتور", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "عيادة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مستشفى", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "صحة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "علاج", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "صيدلية", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "أسنان", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "education") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "رحلة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مدرسة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "استاذ", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مدرس", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "معلم", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "جامعة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تعليم", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "خصوصي", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تدريس", false, 2, (Object) null))) || ((Intrinsics.areEqual(cat.getId(), "law") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "محام", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "محاماة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مستشار قانوني", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "قانون", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "قضية", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "محكمة", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "استشارة", false, 2, (Object) null))) || (Intrinsics.areEqual(cat.getId(), "engineering") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "هندس", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مهندس", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "معماري", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "مدني", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "استشارة هندسية", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تخطيط", false, 2, (Object) null)))))))))))) {
                break;
            }
        }
        Category matchedCat = (Category) next;
        Iterator<T> it2 = this._cities.getValue().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next2 = it2.next();
            City city = (City) next2;
            if (StringsKt.contains$default((CharSequence) normalized, (CharSequence) city.getNameAr(), false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) city.getNameEn(), false, 2, (Object) null) || (Intrinsics.areEqual(city.getId(), "sanaa") && StringsKt.contains$default((CharSequence) normalized, (CharSequence) "صنعاء", false, 2, (Object) null)) || ((Intrinsics.areEqual(city.getId(), "aden") && StringsKt.contains$default((CharSequence) normalized, (CharSequence) "عدن", false, 2, (Object) null)) || ((Intrinsics.areEqual(city.getId(), "taiz") && StringsKt.contains$default((CharSequence) normalized, (CharSequence) "تعز", false, 2, (Object) null)) || ((Intrinsics.areEqual(city.getId(), "ibb") && StringsKt.contains$default((CharSequence) normalized, (CharSequence) "إب", false, 2, (Object) null)) || (Intrinsics.areEqual(city.getId(), "hadramout") && (StringsKt.contains$default((CharSequence) normalized, (CharSequence) "حضرموت", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) normalized, (CharSequence) "المكلا", false, 2, (Object) null))))))) {
                obj = next2;
                break;
            }
        }
        City matchedCity = (City) obj;
        if (matchedCat != null) {
            Iterable $this$filter$iv = this._providers.getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Provider p = (Provider) element$iv$iv;
                if (p.isVerified() && Intrinsics.areEqual(p.getCategory(), matchedCat.getId()) && (matchedCity == null || Intrinsics.areEqual(p.getCity(), matchedCity.getId()))) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            List localProviders = (List) destination$iv$iv;
            String cityLabelStr = matchedCity != null ? "في مدينة " + matchedCity.getNameAr() : "";
            if (!localProviders.isEmpty()) {
                String pListText = CollectionsKt.joinToString$default(CollectionsKt.take(localProviders, 4), "\n", null, null, 0, null, new Function1<Provider, CharSequence>() { // from class: com.maw.MainViewModel$getSimulatedYemeniLocalReply$pListText$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(Provider p2) {
                        Intrinsics.checkNotNullParameter(p2, "p");
                        return "• 👨 " + p2.getName() + " | 📍 " + p2.getArea() + " | 📞 هاتف: " + p2.getPhone();
                    }
                }, 30, null);
                return "حياك الله أخي الغالي! بحثت لك في دليلنا أوفلاين 🛡️ ووجدت هؤلاء الفنيين الموثقين لقسم (" + matchedCat.getNameAr() + ") " + cityLabelStr + " باليمن:\n\n" + pListText + "\n\nتواصل مع الفني مباشرة وموفق خير إن شاء الله!";
            }
            String pListText2 = matchedCat.getNameAr();
            return "يا أهلاً بك! لقد تم العثور على قسم (" + pListText2 + ") ذكياً، ولكن لا توجد أسماء أعضاء مسجلين وموثقين حالياً " + cityLabelStr + " في دليلنا أوفلاين. يمكنك تسجيل مزودي الخدمة الجدد عبر صفحة التقديم لمبادرتنا لخدمة المجتمع.";
        }
        if (matchedCity != null) {
            Iterable $this$filter$iv2 = this._providers.getValue();
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : $this$filter$iv2) {
                Provider p2 = (Provider) element$iv$iv2;
                if (p2.isVerified() && Intrinsics.areEqual(p2.getCity(), matchedCity.getId())) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
            }
            List localProviders2 = (List) destination$iv$iv2;
            if (!localProviders2.isEmpty()) {
                String pListText3 = CollectionsKt.joinToString$default(CollectionsKt.take(localProviders2, 3), "\n", null, null, 0, null, new Function1<Provider, CharSequence>() { // from class: com.maw.MainViewModel$getSimulatedYemeniLocalReply$pListText$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(Provider p3) {
                        Object next3;
                        String category;
                        Intrinsics.checkNotNullParameter(p3, "p");
                        String name = p3.getName();
                        Iterator it3 = ((Iterable) this.this$0._categories.getValue()).iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                next3 = null;
                                break;
                            }
                            next3 = it3.next();
                            Category c = (Category) next3;
                            if (Intrinsics.areEqual(c.getId(), p3.getCategory())) {
                                break;
                            }
                        }
                        Category category2 = (Category) next3;
                        if (category2 == null || (category = category2.getNameAr()) == null) {
                            category = p3.getCategory();
                        }
                        return "• " + name + " (" + category + ") | 📞 هاتف: " + p3.getPhone();
                    }
                }, 30, null);
                return "يا سيدي الكريم! في مدينة " + matchedCity.getNameAr() + "، يتوفر لدينا فنيين ممتازين بالدليل أوفلاين. تفضل ببعضهم:\n\n" + pListText3 + "\n\nتصفح بقية الأقسام من الشاشة الرئيسية، والله يوفقك!";
            }
        }
        return this._settings.getValue().getAssistantWelcomeText();
    }

    public final void askGemini(String prompt) {
        Intrinsics.checkNotNullParameter(prompt, "prompt");
        if (StringsKt.isBlank(prompt)) {
            return;
        }
        this._geminiMessages.setValue(CollectionsKt.plus((Collection<? extends Pair>) this._geminiMessages.getValue(), new Pair(prompt, true)));
        this._isGeminiThinking.setValue(true);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07181(prompt, null), 3, null);
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$askGemini$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$askGemini$1", f = "MainActivity.kt", i = {}, l = {2118, 2122}, m = "invokeSuspend", n = {}, s = {})
    static final class C07181 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $prompt;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07181(String str, Continuation<? super C07181> continuation) {
            super(2, continuation);
            this.$prompt = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainViewModel.this.new C07181(this.$prompt, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07181) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0109  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0131  */
        /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r0v2 */
        /* JADX WARN: Type inference failed for: r7v7, types: [T, java.lang.String] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 362
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.maw.MainViewModel.C07181.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final StateFlow<List<AdminAccount>> getAdminAccounts() {
        return this.adminAccounts;
    }

    public final void addAdminAccount(AdminAccount account, String creator) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(account, "account");
        Intrinsics.checkNotNullParameter(creator, "creator");
        MutableStateFlow<List<AdminAccount>> mutableStateFlow = this._adminAccounts;
        Iterable $this$filter$iv = this._adminAccounts.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            AdminAccount it = (AdminAccount) element$iv$iv;
            if (!Intrinsics.areEqual(it.getUsername(), account.getUsername())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue(CollectionsKt.plus((Collection<? extends AdminAccount>) destination$iv$iv, account));
        addAuditLog(creator, "إنشاء أو تحديث حساب مشرف إداري ومراجعة صلاحيات التراخيص للمستخدم: " + account.getUsername());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("admins")) == null || (documentReferenceDocument = collectionReferenceCollection.document(account.getUsername())) == null) {
                return;
            }
            documentReferenceDocument.set(account);
        } catch (Exception e) {
        }
    }

    public final void deleteAdminAccount(String username, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<AdminAccount>> mutableStateFlow = this._adminAccounts;
        Iterable $this$filter$iv = this._adminAccounts.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            AdminAccount it = (AdminAccount) element$iv$iv;
            if (!Intrinsics.areEqual(it.getUsername(), username)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "تم حذف حساب المشرف ذو الاسم: " + username);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("admins")) == null || (documentReferenceDocument = collectionReferenceCollection.document(username)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void updateAdminAccount(String oldUsername, AdminAccount updatedAccount, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        CollectionReference collectionReferenceCollection3;
        DocumentReference documentReferenceDocument3;
        Intrinsics.checkNotNullParameter(oldUsername, "oldUsername");
        Intrinsics.checkNotNullParameter(updatedAccount, "updatedAccount");
        Intrinsics.checkNotNullParameter(admin, "admin");
        if (!Intrinsics.areEqual(oldUsername, updatedAccount.getUsername())) {
            MutableStateFlow<List<AdminAccount>> mutableStateFlow = this._adminAccounts;
            Iterable $this$filter$iv = this._adminAccounts.getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                if (!Intrinsics.areEqual(((AdminAccount) element$iv$iv).getUsername(), oldUsername)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            mutableStateFlow.setValue(CollectionsKt.plus((Collection<? extends AdminAccount>) destination$iv$iv, updatedAccount));
            addAuditLog(admin, "تحديث اسم وبيانات المشرف من " + oldUsername + " إلى " + updatedAccount.getUsername());
            try {
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection3 = firebaseFirestore.collection("admins")) != null && (documentReferenceDocument3 = collectionReferenceCollection3.document(oldUsername)) != null) {
                    documentReferenceDocument3.delete();
                }
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                if (firebaseFirestore2 == null || (collectionReferenceCollection2 = firebaseFirestore2.collection("admins")) == null || (documentReferenceDocument2 = collectionReferenceCollection2.document(updatedAccount.getUsername())) == null) {
                    return;
                }
                documentReferenceDocument2.set(updatedAccount);
                return;
            } catch (Exception e) {
                return;
            }
        }
        MutableStateFlow<List<AdminAccount>> mutableStateFlow2 = this._adminAccounts;
        Iterable $this$map$iv = this._adminAccounts.getValue();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            AdminAccount it = (AdminAccount) item$iv$iv;
            if (Intrinsics.areEqual(it.getUsername(), oldUsername)) {
                it = updatedAccount;
            }
            destination$iv$iv2.add(it);
        }
        mutableStateFlow2.setValue((List) destination$iv$iv2);
        addAuditLog(admin, "تحديث كلمة مرور وصلاحيات المشرف: " + oldUsername);
        try {
            FirebaseFirestore firebaseFirestore3 = this.firestore;
            if (firebaseFirestore3 == null || (collectionReferenceCollection = firebaseFirestore3.collection("admins")) == null || (documentReferenceDocument = collectionReferenceCollection.document(oldUsername)) == null) {
                return;
            }
            documentReferenceDocument.set(updatedAccount);
        } catch (Exception e2) {
        }
    }

    public final void addBanner(Banner b, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._banners.setValue(CollectionsKt.plus((Collection<? extends Banner>) this._banners.getValue(), b));
        addAuditLog(admin, "إضافة بنر إعلاني ترويجي جديد: " + b.getDescription());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("banners")) == null || (documentReferenceDocument = collectionReferenceCollection.document(b.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(b);
        } catch (Exception e) {
        }
    }

    public final void deleteBanner(String id, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Banner>> mutableStateFlow = this._banners;
        Iterable $this$filter$iv = this._banners.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Banner it = (Banner) element$iv$iv;
            if (!Intrinsics.areEqual(it.getId(), id)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "تم حذف البنر الترويجي ذي الرمز (" + id + ")");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("banners")) == null || (documentReferenceDocument = collectionReferenceCollection.document(id)) == null) {
                return;
            }
            documentReferenceDocument.delete();
        } catch (Exception e) {
        }
    }

    public final void updateCategory(Category cat, String admin) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(cat, "cat");
        Intrinsics.checkNotNullParameter(admin, "admin");
        MutableStateFlow<List<Category>> mutableStateFlow = this._categories;
        Iterable $this$map$iv = this._categories.getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Category it = (Category) item$iv$iv;
            if (Intrinsics.areEqual(it.getId(), cat.getId())) {
                it = cat;
            }
            destination$iv$iv.add(it);
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        addAuditLog(admin, "تعديل الفئة المهنية: " + cat.getNameAr());
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("categories")) == null || (documentReferenceDocument = collectionReferenceCollection.document(cat.getId())) == null) {
                return;
            }
            documentReferenceDocument.set(cat);
        } catch (Exception e) {
        }
    }

    public final void clearAllChatHistory(String admin) {
        CollectionReference collectionReferenceCollection;
        Task<QuerySnapshot> task;
        CollectionReference collectionReferenceCollection2;
        Task<QuerySnapshot> task2;
        Intrinsics.checkNotNullParameter(admin, "admin");
        this._chats.setValue(CollectionsKt.emptyList());
        this._chatMessages.setValue(CollectionsKt.emptyList());
        this.currentChatRoomId.setValue(null);
        addAuditLog(admin, "تنظيف وحذف كافة سجلات المحادثات النشطة");
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("chats")) != null && (task2 = collectionReferenceCollection2.get()) != null) {
                final C07201 c07201 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel.clearAllChatHistory.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                        invoke2(querySnapshot);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(QuerySnapshot snap) {
                        for (QueryDocumentSnapshot doc : snap) {
                            doc.getReference().delete();
                        }
                    }
                };
                task2.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda16
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        MainViewModel.clearAllChatHistory$lambda$94(c07201, obj);
                    }
                });
            }
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("messages")) == null || (task = collectionReferenceCollection.get()) == null) {
                return;
            }
            final C07212 c07212 = new Function1<QuerySnapshot, Unit>() { // from class: com.maw.MainViewModel.clearAllChatHistory.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(QuerySnapshot querySnapshot) {
                    invoke2(querySnapshot);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(QuerySnapshot snap) {
                    for (QueryDocumentSnapshot doc : snap) {
                        doc.getReference().delete();
                    }
                }
            };
            task.addOnSuccessListener(new OnSuccessListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda17
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    MainViewModel.clearAllChatHistory$lambda$95(c07212, obj);
                }
            });
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearAllChatHistory$lambda$94(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearAllChatHistory$lambda$95(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    public final void updateBookingFormFields(BookingFormFields fields) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(fields, "fields");
        this._bookingFormFields.setValue(fields);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("booking_fields")) == null) {
                return;
            }
            documentReferenceDocument.set(fields);
        } catch (Exception e) {
        }
    }

    public final void updateDistributionMode(BookingDistributionMode mode) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(mode, "mode");
        this._distributionMode.setValue(mode);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("distribution_mode")) == null) {
                return;
            }
            documentReferenceDocument.set(MapsKt.mapOf(TuplesKt.to("mode", mode.name())));
        } catch (Exception e) {
        }
    }

    public final void updateBookingStatus(String bookingId, BookingStatus newStatus) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Booking bookingCopy;
        Intrinsics.checkNotNullParameter(bookingId, "bookingId");
        Intrinsics.checkNotNullParameter(newStatus, "newStatus");
        MutableStateFlow<List<Booking>> mutableStateFlow = this._bookings;
        Iterable $this$map$iv = this._bookings.getValue();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Booking booking = (Booking) item$iv$iv;
            if (Intrinsics.areEqual(booking.getId(), bookingId)) {
                String lowerCase = newStatus.name().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                bookingCopy = booking.copy((415 & 1) != 0 ? booking.id : null, (415 & 2) != 0 ? booking.userId : null, (415 & 4) != 0 ? booking.userName : null, (415 & 8) != 0 ? booking.providerId : null, (415 & 16) != 0 ? booking.providerName : null, (415 & 32) != 0 ? booking.details : null, (415 & 64) != 0 ? booking.preferredTime : null, (415 & 128) != 0 ? booking.status : lowerCase, (415 & 256) != 0 ? booking.timestamp : 0L, (415 & 512) != 0 ? booking.tripleName : null, (415 & 1024) != 0 ? booking.phoneNumber : null, (415 & 2048) != 0 ? booking.serviceType : null, (415 & 4096) != 0 ? booking.residencePlc : null);
            } else {
                bookingCopy = booking;
            }
            destination$iv$iv.add(bookingCopy);
        }
        mutableStateFlow.setValue((List) destination$iv$iv);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("bookings")) == null || (documentReferenceDocument = collectionReferenceCollection.document(bookingId)) == null) {
                return;
            }
            String lowerCase2 = newStatus.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            documentReferenceDocument.update(NotificationCompat.CATEGORY_STATUS, lowerCase2, new Object[0]);
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0033 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0063 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0066 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String getBookingStatusColor(java.lang.String r3) {
        /*
            r2 = this;
            java.lang.String r0 = "status"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.util.Locale r0 = java.util.Locale.ROOT
            java.lang.String r0 = r3.toLowerCase(r0)
            java.lang.String r1 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            int r1 = r0.hashCode()
            switch(r1) {
                case -2146525273: goto L5a;
                case -1402931637: goto L4e;
                case -753541113: goto L42;
                case -682587753: goto L36;
                case -608496514: goto L2a;
                case 476588369: goto L21;
                case 1185244855: goto L18;
                default: goto L17;
            }
        L17:
            goto L66
        L18:
            java.lang.String r1 = "approved"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L63
            goto L17
        L21:
            java.lang.String r1 = "cancelled"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L33
            goto L17
        L2a:
            java.lang.String r1 = "rejected"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L33
            goto L17
        L33:
            java.lang.String r0 = "#F44336"
            goto L68
        L36:
            java.lang.String r1 = "pending"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L3f
            goto L17
        L3f:
            java.lang.String r0 = "#FFC107"
            goto L68
        L42:
            java.lang.String r1 = "in_progress"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L4b
            goto L17
        L4b:
            java.lang.String r0 = "#2196F3"
            goto L68
        L4e:
            java.lang.String r1 = "completed"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L57
            goto L17
        L57:
            java.lang.String r0 = "#9C27B0"
            goto L68
        L5a:
            java.lang.String r1 = "accepted"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L63
            goto L17
        L63:
            java.lang.String r0 = "#4CAF50"
            goto L68
        L66:
            java.lang.String r0 = "#9E9E9E"
        L68:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.MainViewModel.getBookingStatusColor(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final String getBookingStatusLabel(String status) {
        Intrinsics.checkNotNullParameter(status, "status");
        String lowerCase = status.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        switch (lowerCase.hashCode()) {
            case -2146525273:
                if (!lowerCase.equals("accepted")) {
                }
                break;
            case -1402931637:
                if (!lowerCase.equals("completed")) {
                }
                break;
            case -753541113:
                if (!lowerCase.equals("in_progress")) {
                }
                break;
            case -682587753:
                if (!lowerCase.equals("pending")) {
                }
                break;
            case -608496514:
                if (!lowerCase.equals("rejected")) {
                }
                break;
            case 476588369:
                if (!lowerCase.equals("cancelled")) {
                }
                break;
            case 1185244855:
                if (!lowerCase.equals("approved")) {
                }
                break;
        }
        return status;
    }

    public final float getBookingProgress(String status) {
        String str;
        Intrinsics.checkNotNullParameter(status, "status");
        String lowerCase = status.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        switch (lowerCase.hashCode()) {
            case -2146525273:
                if (!lowerCase.equals("accepted")) {
                }
                break;
            case -1402931637:
                if (!lowerCase.equals("completed")) {
                }
                break;
            case -753541113:
                if (!lowerCase.equals("in_progress")) {
                }
                break;
            case -682587753:
                if (!lowerCase.equals("pending")) {
                }
                break;
            case -608496514:
                str = "rejected";
                lowerCase.equals(str);
                break;
            case 476588369:
                str = "cancelled";
                lowerCase.equals(str);
                break;
            case 1185244855:
                if (!lowerCase.equals("approved")) {
                }
                break;
        }
        return 0.0f;
    }

    public final void sendChatDisabledNotification(String message) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(message, "message");
        String string = UUID.randomUUID().toString();
        String str = message;
        if (StringsKt.isBlank(str)) {
            str = "خدمة الدردشة متوقفة حالياً للصيانة، نعتذر عن الإزعاج";
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Intrinsics.checkNotNull(string);
        UserNotification notification = new UserNotification(string, "🔒 خدمة الدردشة متوقفة", str, (String) null, jCurrentTimeMillis, false, "chat_disabled", "all", (String) null, 296, (DefaultConstructorMarker) null);
        this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(notification), (Iterable) this._notifications.getValue()));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("notifications")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(notification.getId())) != null) {
                documentReferenceDocument2.set(notification);
            }
            FirebaseFirestore firebaseFirestore2 = this.firestore;
            if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("global")) == null) {
                return;
            }
            documentReferenceDocument.update(MapsKt.mapOf(TuplesKt.to("isChatEnabled", false), TuplesKt.to("chatDisabledMessage", message)));
        } catch (Exception e) {
        }
    }

    public final void enableChat() {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        MutableStateFlow<AppSettings> mutableStateFlow = this._settings;
        AppSettings value = this._settings.getValue();
        mutableStateFlow.setValue(value.copy(((-65537) & 1) != 0 ? value.footerText : null, ((-65537) & 2) != 0 ? value.footerFontSize : 0, ((-65537) & 4) != 0 ? value.selectedFontName : null, ((-65537) & 8) != 0 ? value.downloadUrl : null, ((-65537) & 16) != 0 ? value.aboutImageUrl : null, ((-65537) & 32) != 0 ? value.appNameAr : null, ((-65537) & 64) != 0 ? value.welcomeMessage : null, ((-65537) & 128) != 0 ? value.footerTextVisible : false, ((-65537) & 256) != 0 ? value.registrationRulesList : null, ((-65537) & 512) != 0 ? value.primaryColorHex : null, ((-65537) & 1024) != 0 ? value.accentColorHex : null, ((-65537) & 2048) != 0 ? value.bgColorHex : null, ((-65537) & 4096) != 0 ? value.surfaceColorHex : null, ((-65537) & 8192) != 0 ? value.isWebSpeechEnabled : false, ((-65537) & 16384) != 0 ? value.radiusSearchLimitKm : 0, ((-65537) & 32768) != 0 ? value.autoCleanupDays : 0, ((-65537) & 65536) != 0 ? value.isChatEnabled : true, ((-65537) & 131072) != 0 ? value.chatDisabledMessage : null, ((-65537) & 262144) != 0 ? value.chatIconSize : 0, ((-65537) & 524288) != 0 ? value.chatIconColorHex : null, ((-65537) & 1048576) != 0 ? value.chatIconHidden : false, ((-65537) & 2097152) != 0 ? value.assistantIconSize : 0, ((-65537) & 4194304) != 0 ? value.assistantIconColorHex : null, ((-65537) & 8388608) != 0 ? value.assistantIconHidden : false, ((-65537) & 16777216) != 0 ? value.assistantIconXOffset : 0, ((-65537) & 33554432) != 0 ? value.assistantIconYOffset : 0, ((-65537) & AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL) != 0 ? value.assistantIconType : null, ((-65537) & 134217728) != 0 ? value.aboutPhone : null, ((-65537) & 268435456) != 0 ? value.aboutWhatsapp : null, ((-65537) & 536870912) != 0 ? value.aboutEmail : null, ((-65537) & Ints.MAX_POWER_OF_TWO) != 0 ? value.aboutShareUrl : null, ((-65537) & Integer.MIN_VALUE) != 0 ? value.aboutPhoneVisible : false, ((-1) & 1) != 0 ? value.aboutWhatsappVisible : false, ((-1) & 2) != 0 ? value.aboutEmailVisible : false, ((-1) & 4) != 0 ? value.aboutShareUrlVisible : false, ((-1) & 8) != 0 ? value.aboutImageVisible : false, ((-1) & 16) != 0 ? value.adminPassword : null, ((-1) & 32) != 0 ? value.fontColorHex : null, ((-1) & 64) != 0 ? value.footerFontSizePercent : 0, ((-1) & 128) != 0 ? value.footerOpacity : 0.0f, ((-1) & 256) != 0 ? value.assistantIconSizePercent : 0, ((-1) & 512) != 0 ? value.chatIconSizePercent : 0, ((-1) & 1024) != 0 ? value.appLogoText : null, ((-1) & 2048) != 0 ? value.appLogoUrl : null, ((-1) & 4096) != 0 ? value.isGeoSearchEnabled : false, ((-1) & 8192) != 0 ? value.searchMatchingMethodHex : null, ((-1) & 16384) != 0 ? value.maxPortfolioImages : 0, ((-1) & 32768) != 0 ? value.colorsPresetsList : null, ((-1) & 65536) != 0 ? value.faqList : null, ((-1) & 131072) != 0 ? value.initiativeSupportNumber : null, ((-1) & 262144) != 0 ? value.notificationsEnabled : false, ((-1) & 524288) != 0 ? value.reviewSystemEnabled : false, ((-1) & 1048576) != 0 ? value.blockedKeywords : null, ((-1) & 2097152) != 0 ? value.aboutTitleText : null, ((-1) & 4194304) != 0 ? value.aboutVersionLabel : null, ((-1) & 8388608) != 0 ? value.aboutVersionValue : null, ((-1) & 16777216) != 0 ? value.aboutVersionVisible : false, ((-1) & 33554432) != 0 ? value.aboutSecurityLabel : null, ((-1) & AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL) != 0 ? value.aboutSecurityValue : null, ((-1) & 134217728) != 0 ? value.aboutSecurityVisible : false, ((-1) & 268435456) != 0 ? value.geminiApiKey : null, ((-1) & 536870912) != 0 ? value.assistantWelcomeText : null, ((-1) & Ints.MAX_POWER_OF_TWO) != 0 ? value.isPortfolioFeatureGloballyEnabled : false, ((-1) & Integer.MIN_VALUE) != 0 ? value.isPortfolioUploadGloballyAllowed : false, ((-1) & 1) != 0 ? value.registrationChipColorHex : null, ((-1) & 2) != 0 ? value.searchRatingWeight : 0.0f, ((-1) & 4) != 0 ? value.regChipBgColorsList : null, ((-1) & 8) != 0 ? value.approvedProviderSortingMethod : null, ((-1) & 16) != 0 ? value.searchBarVisible : false, ((-1) & 32) != 0 ? value.regNameRequired : false, ((-1) & 64) != 0 ? value.regNameVisible : false, ((-1) & 128) != 0 ? value.regPhoneRequired : false, ((-1) & 256) != 0 ? value.regPhoneVisible : false, ((-1) & 512) != 0 ? value.regCategoryRequired : false, ((-1) & 1024) != 0 ? value.regCategoryVisible : false, ((-1) & 2048) != 0 ? value.regSelfieRequired : false, ((-1) & 4096) != 0 ? value.regSelfieVisible : false, ((-1) & 8192) != 0 ? value.regIdCardRequired : false, ((-1) & 16384) != 0 ? value.regIdCardVisible : false, ((-1) & 32768) != 0 ? value.regAreaRequired : false, ((-1) & 65536) != 0 ? value.regAreaVisible : false, ((-1) & 131072) != 0 ? value.regDescRequired : false, ((-1) & 262144) != 0 ? value.regDescVisible : false, ((-1) & 524288) != 0 ? value.autocompleteNamesEnabled : false, ((-1) & 1048576) != 0 ? value.autocompletePhonesEnabled : false, ((-1) & 2097152) != 0 ? value.autocompleteLocationsEnabled : false, ((-1) & 4194304) != 0 ? value.isBookingsEnabled : false, ((-1) & 8388608) != 0 ? value.bookingsRoutingMode : null, ((-1) & 16777216) != 0 ? value.noResultsMessage : null, ((-1) & 33554432) != 0 ? value.isVisitorToProviderChatEnabled : false, ((-1) & AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL) != 0 ? value.isProviderToProviderChatEnabled : false, ((-1) & 134217728) != 0 ? value.isProviderToAdminChatEnabled : false, ((-1) & 268435456) != 0 ? value.isAdminToSupervisorChatEnabled : false, ((-1) & 536870912) != 0 ? value.isContactFormEnabled : false, ((-1) & Ints.MAX_POWER_OF_TWO) != 0 ? value.contactFormDestination : null, ((-1) & Integer.MIN_VALUE) != 0 ? value.blockedUsersList : null));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("global")) == null) {
                return;
            }
            documentReferenceDocument.update("isChatEnabled", (Object) true, new Object[0]);
        } catch (Exception e) {
        }
    }

    public final double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double d = 2;
        double a = (Math.sin(dLat / d) * Math.sin(dLat / d)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / d) * Math.sin(dLon / d));
        double c = d * Math.atan2(Math.sqrt(a), Math.sqrt(((double) 1) - a));
        return 6371.0d * c;
    }

    public final Pair<Double, Double> getProviderCoordinates(String providerId) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        return new Pair<>(Double.valueOf(((provider != null ? provider.getOrderPriority() : 0.0d) * 0.001d) + 15.3533d), Double.valueOf(44.2074d));
    }

    public final String getDistanceString(double distanceInKm) {
        if (distanceInKm < 1.0d) {
            return ((int) (((double) 1000) * distanceInKm)) + " م";
        }
        String str = String.format("%.1f كم", Arrays.copyOf(new Object[]{Double.valueOf(distanceInKm)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final void updateProviderPortfolio(String providerId, List<String> images) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Collection destination$iv$iv;
        Iterable $this$map$iv;
        Provider providerCopy;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Intrinsics.checkNotNullParameter(images, "images");
        MutableStateFlow<List<Provider>> mutableStateFlow = this._providers;
        Iterable $this$map$iv2 = this._providers.getValue();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv : $this$map$iv2) {
            Provider provider = (Provider) item$iv$iv;
            if (Intrinsics.areEqual(provider.getId(), providerId)) {
                destination$iv$iv = destination$iv$iv2;
                $this$map$iv = $this$map$iv2;
                providerCopy = provider.copy((1585281 & 1) != 0 ? provider.id : null, (1585281 & 2) != 0 ? provider.name : null, (1585281 & 4) != 0 ? provider.category : null, (1585281 & 8) != 0 ? provider.city : null, (1585281 & 16) != 0 ? provider.phone : null, (1585281 & 32) != 0 ? provider.description : null, (1585281 & 64) != 0 ? provider.area : null, (1585281 & 128) != 0 ? provider.rating : 0.0d, (1585281 & 256) != 0 ? provider.isVerified : false, (1585281 & 512) != 0 ? provider.isPinned : false, (1585281 & 1024) != 0 ? provider.isRecommended : false, (1585281 & 2048) != 0 ? provider.isSubscribed : false, (1585281 & 4096) != 0 ? provider.deviceId : null, (1585281 & 8192) != 0 ? provider.imageUrl : null, (1585281 & 16384) != 0 ? provider.portfolioImages : images, (1585281 & 32768) != 0 ? provider.orderPriority : 0, (1585281 & 65536) != 0 ? provider.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? provider.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? provider.allowedImageCount : 0, (1585281 & 524288) != 0 ? provider.skills : null, (1585281 & 1048576) != 0 ? provider.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? provider.cardColorHex : null);
            } else {
                destination$iv$iv = destination$iv$iv2;
                $this$map$iv = $this$map$iv2;
                providerCopy = provider;
            }
            Collection destination$iv$iv3 = destination$iv$iv;
            destination$iv$iv3.add(providerCopy);
            destination$iv$iv2 = destination$iv$iv3;
            $this$map$iv2 = $this$map$iv;
        }
        mutableStateFlow.setValue((List) destination$iv$iv2);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("providers")) == null || (documentReferenceDocument = collectionReferenceCollection.document(providerId)) == null) {
                return;
            }
            try {
                documentReferenceDocument.update("portfolioImages", images, new Object[0]);
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }

    public final void addPortfolioImage(String providerId, String imageBase64) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Intrinsics.checkNotNullParameter(imageBase64, "imageBase64");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            updateProviderPortfolio(providerId, CollectionsKt.plus((Collection<? extends String>) provider.getPortfolioImages(), imageBase64));
        }
    }

    public final void removePortfolioImage(String providerId, int index) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            List<String> mutableList = CollectionsKt.toMutableList((Collection) provider.getPortfolioImages());
            if (index < mutableList.size()) {
                mutableList.remove(index);
                updateProviderPortfolio(providerId, mutableList);
            }
        }
    }

    public final void clearPortfolio(String providerId) {
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        updateProviderPortfolio(providerId, CollectionsKt.emptyList());
    }

    public final void loadPendingTechnicians() {
        CollectionReference collectionReferenceCollection;
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection("pending_technicians")) != null) {
                collectionReferenceCollection.addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda19
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.loadPendingTechnicians$lambda$104(this.f$0, (QuerySnapshot) obj, firebaseFirestoreException);
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadPendingTechnicians$lambda$104(MainViewModel this$0, QuerySnapshot snapshot, FirebaseFirestoreException error) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (error == null && snapshot != null) {
            MutableStateFlow<List<Provider>> mutableStateFlow = this$0._pendingTechnicians;
            List<Provider> objects = snapshot.toObjects(Provider.class);
            Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
            mutableStateFlow.setValue(objects);
        }
    }

    public final void approveTechnician(String providerId) {
        Object next;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        CollectionReference collectionReferenceCollection3;
        DocumentReference documentReferenceDocument3;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._pendingTechnicians.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider technician = (Provider) next;
        if (technician != null) {
            MutableStateFlow<List<Provider>> mutableStateFlow = this._pendingTechnicians;
            Iterable $this$filter$iv = this._pendingTechnicians.getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Provider it3 = (Provider) element$iv$iv;
                if (!Intrinsics.areEqual(it3.getId(), providerId)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            mutableStateFlow.setValue((List) destination$iv$iv);
            this._providers.setValue(CollectionsKt.plus((Collection<? extends Provider>) this._providers.getValue(), technician.copy((1585281 & 1) != 0 ? technician.id : null, (1585281 & 2) != 0 ? technician.name : null, (1585281 & 4) != 0 ? technician.category : null, (1585281 & 8) != 0 ? technician.city : null, (1585281 & 16) != 0 ? technician.phone : null, (1585281 & 32) != 0 ? technician.description : null, (1585281 & 64) != 0 ? technician.area : null, (1585281 & 128) != 0 ? technician.rating : 0.0d, (1585281 & 256) != 0 ? technician.isVerified : true, (1585281 & 512) != 0 ? technician.isPinned : false, (1585281 & 1024) != 0 ? technician.isRecommended : false, (1585281 & 2048) != 0 ? technician.isSubscribed : false, (1585281 & 4096) != 0 ? technician.deviceId : null, (1585281 & 8192) != 0 ? technician.imageUrl : null, (1585281 & 16384) != 0 ? technician.portfolioImages : null, (1585281 & 32768) != 0 ? technician.orderPriority : 0, (1585281 & 65536) != 0 ? technician.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? technician.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? technician.allowedImageCount : 0, (1585281 & 524288) != 0 ? technician.skills : null, (1585281 & 1048576) != 0 ? technician.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? technician.cardColorHex : null)));
            String string = UUID.randomUUID().toString();
            String str = "تم قبول طلب انضمامك كـ " + technician.getName() + " بنجاح";
            String deviceId = technician.getDeviceId();
            long jCurrentTimeMillis = System.currentTimeMillis();
            Intrinsics.checkNotNull(string);
            UserNotification notification = new UserNotification(string, "🎉 تم قبول طلبك", str, (String) null, jCurrentTimeMillis, false, "success", deviceId, (String) null, 296, (DefaultConstructorMarker) null);
            this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(notification), (Iterable) this._notifications.getValue()));
            try {
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection3 = firebaseFirestore.collection("pending_technicians")) != null && (documentReferenceDocument3 = collectionReferenceCollection3.document(providerId)) != null) {
                    documentReferenceDocument3.delete();
                }
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                if (firebaseFirestore2 != null && (collectionReferenceCollection2 = firebaseFirestore2.collection("providers")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(providerId)) != null) {
                    documentReferenceDocument2.set(technician.copy((1585281 & 1) != 0 ? technician.id : null, (1585281 & 2) != 0 ? technician.name : null, (1585281 & 4) != 0 ? technician.category : null, (1585281 & 8) != 0 ? technician.city : null, (1585281 & 16) != 0 ? technician.phone : null, (1585281 & 32) != 0 ? technician.description : null, (1585281 & 64) != 0 ? technician.area : null, (1585281 & 128) != 0 ? technician.rating : 0.0d, (1585281 & 256) != 0 ? technician.isVerified : true, (1585281 & 512) != 0 ? technician.isPinned : false, (1585281 & 1024) != 0 ? technician.isRecommended : false, (1585281 & 2048) != 0 ? technician.isSubscribed : false, (1585281 & 4096) != 0 ? technician.deviceId : null, (1585281 & 8192) != 0 ? technician.imageUrl : null, (1585281 & 16384) != 0 ? technician.portfolioImages : null, (1585281 & 32768) != 0 ? technician.orderPriority : 0, (1585281 & 65536) != 0 ? technician.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? technician.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? technician.allowedImageCount : 0, (1585281 & 524288) != 0 ? technician.skills : null, (1585281 & 1048576) != 0 ? technician.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? technician.cardColorHex : null));
                }
                FirebaseFirestore firebaseFirestore3 = this.firestore;
                if (firebaseFirestore3 == null || (collectionReferenceCollection = firebaseFirestore3.collection("notifications")) == null || (documentReferenceDocument = collectionReferenceCollection.document(notification.getId())) == null) {
                    return;
                }
                documentReferenceDocument.set(notification);
            } catch (Exception e) {
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public static /* synthetic */ void rejectTechnician$default(MainViewModel mainViewModel, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "لم يستوفِ الشروط";
        }
        mainViewModel.rejectTechnician(str, str2);
    }

    public final void rejectTechnician(String providerId, String reason) {
        Object next;
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        CollectionReference collectionReferenceCollection2;
        DocumentReference documentReferenceDocument2;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Iterator<T> it = this._pendingTechnicians.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider technician = (Provider) next;
        if (technician != null) {
            MutableStateFlow<List<Provider>> mutableStateFlow = this._pendingTechnicians;
            Iterable $this$filter$iv = this._pendingTechnicians.getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Provider it3 = (Provider) element$iv$iv;
                Provider technician2 = technician;
                if (!Intrinsics.areEqual(it3.getId(), providerId)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                technician = technician2;
            }
            mutableStateFlow.setValue((List) destination$iv$iv);
            String string = UUID.randomUUID().toString();
            String deviceId = technician.getDeviceId();
            long jCurrentTimeMillis = System.currentTimeMillis();
            Intrinsics.checkNotNull(string);
            UserNotification notification = new UserNotification(string, "❌ تم رفض طلبك", "تم رفض طلب انضمامك بسبب: " + reason, (String) null, jCurrentTimeMillis, false, "error", deviceId, (String) null, 296, (DefaultConstructorMarker) null);
            this._notifications.setValue(CollectionsKt.plus((Collection) CollectionsKt.listOf(notification), (Iterable) this._notifications.getValue()));
            try {
                FirebaseFirestore firebaseFirestore = this.firestore;
                if (firebaseFirestore != null && (collectionReferenceCollection2 = firebaseFirestore.collection("pending_technicians")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(providerId)) != null) {
                    documentReferenceDocument2.delete();
                }
                FirebaseFirestore firebaseFirestore2 = this.firestore;
                if (firebaseFirestore2 == null || (collectionReferenceCollection = firebaseFirestore2.collection("notifications")) == null || (documentReferenceDocument = collectionReferenceCollection.document(notification.getId())) == null) {
                    return;
                }
                documentReferenceDocument.set(notification);
            } catch (Exception e) {
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$autoCleanupData$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$autoCleanupData$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C07191 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $daysToKeep;
        int label;
        final /* synthetic */ MainViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07191(int i, MainViewModel mainViewModel, Continuation<? super C07191> continuation) {
            super(2, continuation);
            this.$daysToKeep = i;
            this.this$0 = mainViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C07191(this.$daysToKeep, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07191) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long cutoffTime;
            Collection destination$iv$iv;
            Iterator it;
            CollectionReference collectionReferenceCollection;
            DocumentReference documentReferenceDocument;
            CollectionReference collectionReferenceCollection2;
            DocumentReference documentReferenceDocument2;
            CollectionReference collectionReferenceCollection3;
            DocumentReference documentReferenceDocument3;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    try {
                        long j = 60;
                        cutoffTime = System.currentTimeMillis() - ((((((long) this.$daysToKeep) * 24) * j) * j) * ((long) 1000));
                        Iterable $this$filterTo$iv$iv = (Iterable) this.this$0._bookings.getValue();
                        destination$iv$iv = new ArrayList();
                        it = $this$filterTo$iv$iv.iterator();
                    } catch (Exception e) {
                    }
                    while (true) {
                        boolean z = true;
                        if (!it.hasNext()) {
                            Iterable oldBookings = (List) destination$iv$iv;
                            Iterable $this$forEach$iv = oldBookings;
                            MainViewModel mainViewModel = this.this$0;
                            for (Object element$iv : $this$forEach$iv) {
                                Booking booking = (Booking) element$iv;
                                FirebaseFirestore firebaseFirestore = mainViewModel.firestore;
                                if (firebaseFirestore != null && (collectionReferenceCollection3 = firebaseFirestore.collection("bookings")) != null && (documentReferenceDocument3 = collectionReferenceCollection3.document(booking.getId())) != null) {
                                    documentReferenceDocument3.delete();
                                }
                            }
                            MutableStateFlow mutableStateFlow = this.this$0._bookings;
                            Iterable $this$filterTo$iv$iv2 = (Iterable) this.this$0._bookings.getValue();
                            Collection destination$iv$iv2 = new ArrayList();
                            for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                                Booking it2 = (Booking) element$iv$iv;
                                if (it2.getTimestamp() >= cutoffTime) {
                                    destination$iv$iv2.add(element$iv$iv);
                                }
                            }
                            mutableStateFlow.setValue((List) destination$iv$iv2);
                            Iterable $this$filterTo$iv$iv3 = (Iterable) this.this$0._notifications.getValue();
                            Collection destination$iv$iv3 = new ArrayList();
                            for (Object element$iv$iv2 : $this$filterTo$iv$iv3) {
                                UserNotification it3 = (UserNotification) element$iv$iv2;
                                if (it3.getTimestamp() < cutoffTime) {
                                    destination$iv$iv3.add(element$iv$iv2);
                                }
                            }
                            Iterable oldNotifications = (List) destination$iv$iv3;
                            Iterable $this$forEach$iv2 = oldNotifications;
                            MainViewModel mainViewModel2 = this.this$0;
                            for (Object element$iv2 : $this$forEach$iv2) {
                                UserNotification notification = (UserNotification) element$iv2;
                                FirebaseFirestore firebaseFirestore2 = mainViewModel2.firestore;
                                if (firebaseFirestore2 != null && (collectionReferenceCollection2 = firebaseFirestore2.collection("notifications")) != null && (documentReferenceDocument2 = collectionReferenceCollection2.document(notification.getId())) != null) {
                                    documentReferenceDocument2.delete();
                                }
                            }
                            MutableStateFlow mutableStateFlow2 = this.this$0._notifications;
                            Iterable $this$filterTo$iv$iv4 = (Iterable) this.this$0._notifications.getValue();
                            Collection destination$iv$iv4 = new ArrayList();
                            for (Object element$iv$iv3 : $this$filterTo$iv$iv4) {
                                UserNotification it4 = (UserNotification) element$iv$iv3;
                                if (it4.getTimestamp() >= cutoffTime) {
                                    destination$iv$iv4.add(element$iv$iv3);
                                }
                            }
                            mutableStateFlow2.setValue((List) destination$iv$iv4);
                            Iterable $this$filterTo$iv$iv5 = (Iterable) this.this$0._chatMessages.getValue();
                            Collection destination$iv$iv5 = new ArrayList();
                            for (Object element$iv$iv4 : $this$filterTo$iv$iv5) {
                                ChatMessage it5 = (ChatMessage) element$iv$iv4;
                                if (it5.getTimestamp() < cutoffTime) {
                                    destination$iv$iv5.add(element$iv$iv4);
                                }
                            }
                            Iterable oldMessages = (List) destination$iv$iv5;
                            Iterable $this$forEach$iv3 = oldMessages;
                            MainViewModel mainViewModel3 = this.this$0;
                            for (Object element$iv3 : $this$forEach$iv3) {
                                ChatMessage message = (ChatMessage) element$iv3;
                                FirebaseFirestore firebaseFirestore3 = mainViewModel3.firestore;
                                if (firebaseFirestore3 != null && (collectionReferenceCollection = firebaseFirestore3.collection("messages")) != null && (documentReferenceDocument = collectionReferenceCollection.document(message.getId())) != null) {
                                    documentReferenceDocument.delete();
                                }
                            }
                            MutableStateFlow mutableStateFlow3 = this.this$0._chatMessages;
                            Iterable $this$filterTo$iv$iv6 = (Iterable) this.this$0._chatMessages.getValue();
                            Collection destination$iv$iv6 = new ArrayList();
                            for (Object element$iv$iv5 : $this$filterTo$iv$iv6) {
                                ChatMessage it6 = (ChatMessage) element$iv$iv5;
                                if (it6.getTimestamp() >= cutoffTime) {
                                    destination$iv$iv6.add(element$iv$iv5);
                                }
                            }
                            mutableStateFlow3.setValue((List) destination$iv$iv6);
                            break;
                        } else {
                            Object element$iv$iv6 = it.next();
                            Booking it7 = (Booking) element$iv$iv6;
                            if (it7.getTimestamp() >= cutoffTime) {
                                z = false;
                            }
                            if (z) {
                                destination$iv$iv.add(element$iv$iv6);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public static /* synthetic */ void autoCleanupData$default(MainViewModel mainViewModel, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 30;
        }
        mainViewModel.autoCleanupData(i);
    }

    public final void autoCleanupData(int daysToKeep) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07191(daysToKeep, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.maw.MainViewModel$scheduleAutoCleanup$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.maw.MainViewModel$scheduleAutoCleanup$1", f = "MainActivity.kt", i = {}, l = {2482}, m = "invokeSuspend", n = {}, s = {})
    static final class C07231 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $days;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07231(int i, Continuation<? super C07231> continuation) {
            super(2, continuation);
            this.$days = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainViewModel.this.new C07231(this.$days, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x002a A[RETURN] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0028 -> B:11:0x002b). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r5.label
                switch(r1) {
                    case 0: goto L16;
                    case 1: goto L11;
                    default: goto L9;
                }
            L9:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            L11:
                r1 = r5
                kotlin.ResultKt.throwOnFailure(r6)
                goto L2b
            L16:
                kotlin.ResultKt.throwOnFailure(r6)
                r1 = r5
            L1a:
                r2 = r1
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 1
                r1.label = r3
                r3 = 86400000(0x5265c00, double:4.2687272E-316)
                java.lang.Object r2 = kotlinx.coroutines.DelayKt.delay(r3, r2)
                if (r2 != r0) goto L2b
                return r0
            L2b:
                com.maw.MainViewModel r2 = com.maw.MainViewModel.this
                int r3 = r1.$days
                r2.autoCleanupData(r3)
                goto L1a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.maw.MainViewModel.C07231.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ void scheduleAutoCleanup$default(MainViewModel mainViewModel, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 30;
        }
        mainViewModel.scheduleAutoCleanup(i);
    }

    public final void scheduleAutoCleanup(int days) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07231(days, null), 3, null);
    }

    public final void updateCardSettings(CardSettings settings) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(settings, "settings");
        this._cardSettings.setValue(settings);
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("card_settings")) == null) {
                return;
            }
            documentReferenceDocument.set(settings);
        } catch (Exception e) {
        }
    }

    public final void loadCardSettings() {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore != null && (collectionReferenceCollection = firebaseFirestore.collection("settings")) != null && (documentReferenceDocument = collectionReferenceCollection.document("card_settings")) != null) {
                documentReferenceDocument.addSnapshotListener(new EventListener() { // from class: com.maw.MainViewModel$$ExternalSyntheticLambda0
                    @Override // com.google.firebase.firestore.EventListener
                    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                        MainViewModel.loadCardSettings$lambda$111(this.f$0, (DocumentSnapshot) obj, firebaseFirestoreException);
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadCardSettings$lambda$111(MainViewModel this$0, DocumentSnapshot snapshot, FirebaseFirestoreException error) {
        CardSettings settings;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (error == null && snapshot != null && snapshot.exists() && (settings = (CardSettings) snapshot.toObject(CardSettings.class)) != null) {
            this$0._cardSettings.setValue(settings);
        }
    }

    public final void toggleChatParticipant(ChatParticipantType participantType) {
        CollectionReference collectionReferenceCollection;
        DocumentReference documentReferenceDocument;
        Intrinsics.checkNotNullParameter(participantType, "participantType");
        Set<ChatParticipantType> value = this._blockedChatParticipants.getValue();
        this._blockedChatParticipants.setValue(value.contains(participantType) ? SetsKt.minus(value, participantType) : SetsKt.plus(value, participantType));
        try {
            FirebaseFirestore firebaseFirestore = this.firestore;
            if (firebaseFirestore == null || (collectionReferenceCollection = firebaseFirestore.collection("settings")) == null || (documentReferenceDocument = collectionReferenceCollection.document("chat_participants")) == null) {
                return;
            }
            Iterable $this$map$iv = this._blockedChatParticipants.getValue();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                ChatParticipantType it = (ChatParticipantType) item$iv$iv;
                destination$iv$iv.add(it.name());
            }
            documentReferenceDocument.set(MapsKt.mapOf(TuplesKt.to("blocked", (List) destination$iv$iv)));
        } catch (Exception e) {
        }
    }

    public final boolean isChatBlockedFor(ChatParticipantType participantType) {
        Intrinsics.checkNotNullParameter(participantType, "participantType");
        return this._blockedChatParticipants.getValue().contains(participantType) || this._blockedChatParticipants.getValue().contains(ChatParticipantType.ALL);
    }

    public final boolean canParticipateInChat(ChatParticipantType participantType) {
        Intrinsics.checkNotNullParameter(participantType, "participantType");
        return !isChatBlockedFor(participantType);
    }

    public final void toggleProviderPin(String providerId) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            Provider updated = provider.copy((1585281 & 1) != 0 ? provider.id : null, (1585281 & 2) != 0 ? provider.name : null, (1585281 & 4) != 0 ? provider.category : null, (1585281 & 8) != 0 ? provider.city : null, (1585281 & 16) != 0 ? provider.phone : null, (1585281 & 32) != 0 ? provider.description : null, (1585281 & 64) != 0 ? provider.area : null, (1585281 & 128) != 0 ? provider.rating : 0.0d, (1585281 & 256) != 0 ? provider.isVerified : false, (1585281 & 512) != 0 ? provider.isPinned : !provider.isPinned(), (1585281 & 1024) != 0 ? provider.isRecommended : false, (1585281 & 2048) != 0 ? provider.isSubscribed : false, (1585281 & 4096) != 0 ? provider.deviceId : null, (1585281 & 8192) != 0 ? provider.imageUrl : null, (1585281 & 16384) != 0 ? provider.portfolioImages : null, (1585281 & 32768) != 0 ? provider.orderPriority : 0, (1585281 & 65536) != 0 ? provider.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? provider.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? provider.allowedImageCount : 0, (1585281 & 524288) != 0 ? provider.skills : null, (1585281 & 1048576) != 0 ? provider.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? provider.cardColorHex : null);
            updateProviderManual(updated, "الأدمن");
        }
    }

    public final void toggleProviderVerification(String providerId) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            Provider updated = provider.copy((1585281 & 1) != 0 ? provider.id : null, (1585281 & 2) != 0 ? provider.name : null, (1585281 & 4) != 0 ? provider.category : null, (1585281 & 8) != 0 ? provider.city : null, (1585281 & 16) != 0 ? provider.phone : null, (1585281 & 32) != 0 ? provider.description : null, (1585281 & 64) != 0 ? provider.area : null, (1585281 & 128) != 0 ? provider.rating : 0.0d, (1585281 & 256) != 0 ? provider.isVerified : !provider.isVerified(), (1585281 & 512) != 0 ? provider.isPinned : false, (1585281 & 1024) != 0 ? provider.isRecommended : false, (1585281 & 2048) != 0 ? provider.isSubscribed : false, (1585281 & 4096) != 0 ? provider.deviceId : null, (1585281 & 8192) != 0 ? provider.imageUrl : null, (1585281 & 16384) != 0 ? provider.portfolioImages : null, (1585281 & 32768) != 0 ? provider.orderPriority : 0, (1585281 & 65536) != 0 ? provider.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? provider.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? provider.allowedImageCount : 0, (1585281 & 524288) != 0 ? provider.skills : null, (1585281 & 1048576) != 0 ? provider.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? provider.cardColorHex : null);
            updateProviderManual(updated, "الأدمن");
        }
    }

    public final void toggleProviderRecommendation(String providerId) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            Provider updated = provider.copy((1585281 & 1) != 0 ? provider.id : null, (1585281 & 2) != 0 ? provider.name : null, (1585281 & 4) != 0 ? provider.category : null, (1585281 & 8) != 0 ? provider.city : null, (1585281 & 16) != 0 ? provider.phone : null, (1585281 & 32) != 0 ? provider.description : null, (1585281 & 64) != 0 ? provider.area : null, (1585281 & 128) != 0 ? provider.rating : 0.0d, (1585281 & 256) != 0 ? provider.isVerified : false, (1585281 & 512) != 0 ? provider.isPinned : false, (1585281 & 1024) != 0 ? provider.isRecommended : !provider.isRecommended(), (1585281 & 2048) != 0 ? provider.isSubscribed : false, (1585281 & 4096) != 0 ? provider.deviceId : null, (1585281 & 8192) != 0 ? provider.imageUrl : null, (1585281 & 16384) != 0 ? provider.portfolioImages : null, (1585281 & 32768) != 0 ? provider.orderPriority : 0, (1585281 & 65536) != 0 ? provider.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? provider.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? provider.allowedImageCount : 0, (1585281 & 524288) != 0 ? provider.skills : null, (1585281 & 1048576) != 0 ? provider.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? provider.cardColorHex : null);
            updateProviderManual(updated, "الأدمن");
        }
    }

    public final void toggleProviderSubscription(String providerId) {
        Object next;
        Intrinsics.checkNotNullParameter(providerId, "providerId");
        Iterator<T> it = this._providers.getValue().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Provider it2 = (Provider) next;
            if (Intrinsics.areEqual(it2.getId(), providerId)) {
                break;
            }
        }
        Provider provider = (Provider) next;
        if (provider != null) {
            Provider updated = provider.copy((1585281 & 1) != 0 ? provider.id : null, (1585281 & 2) != 0 ? provider.name : null, (1585281 & 4) != 0 ? provider.category : null, (1585281 & 8) != 0 ? provider.city : null, (1585281 & 16) != 0 ? provider.phone : null, (1585281 & 32) != 0 ? provider.description : null, (1585281 & 64) != 0 ? provider.area : null, (1585281 & 128) != 0 ? provider.rating : 0.0d, (1585281 & 256) != 0 ? provider.isVerified : false, (1585281 & 512) != 0 ? provider.isPinned : false, (1585281 & 1024) != 0 ? provider.isRecommended : false, (1585281 & 2048) != 0 ? provider.isSubscribed : !provider.isSubscribed(), (1585281 & 4096) != 0 ? provider.deviceId : null, (1585281 & 8192) != 0 ? provider.imageUrl : null, (1585281 & 16384) != 0 ? provider.portfolioImages : null, (1585281 & 32768) != 0 ? provider.orderPriority : 0, (1585281 & 65536) != 0 ? provider.isPortfolioEnabled : false, (1585281 & 131072) != 0 ? provider.isPortfolioUploadEnabled : false, (1585281 & 262144) != 0 ? provider.allowedImageCount : 0, (1585281 & 524288) != 0 ? provider.skills : null, (1585281 & 1048576) != 0 ? provider.nationalIdImageBase64 : null, (1585281 & 2097152) != 0 ? provider.cardColorHex : null);
            updateProviderManual(updated, "الأدمن");
        }
    }
}
