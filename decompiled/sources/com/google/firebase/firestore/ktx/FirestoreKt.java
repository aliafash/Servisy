package com.google.firebase.firestore.ktx;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.MemoryEagerGcSettings;
import com.google.firebase.firestore.MemoryLruGcSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.PersistentCacheSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ktx.FirestoreKt;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.ktx.Firebase;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: Firestore.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a!\u0010\u0005\u001a\u00020\u00062\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000bH\u0007\u001a\u001f\u0010\f\u001a\u00020\r2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001a\u001f\u0010\u000f\u001a\u00020\u00102\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001a\u001f\u0010\u0012\u001a\u00020\u00132\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001a\u001f\u0010\u0015\u001a\u00020\u00162\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001a+\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u001a0\u0019\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0087\b\u001a/\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001a0\u001f0\u0019\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u00020 2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0087\b\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\"\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010#\u001a\u00020$\u001a$\u0010%\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&2\u0006\u0010'\u001a\u00020(H\u0087\b¢\u0006\u0002\u0010)\u001a,\u0010%\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010*\u001a\u00020+H\u0087\b¢\u0006\u0002\u0010,\u001a$\u0010%\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&2\u0006\u0010-\u001a\u00020$H\u0087\b¢\u0006\u0002\u0010.\u001a,\u0010%\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&2\u0006\u0010-\u001a\u00020$2\u0006\u0010*\u001a\u00020+H\u0087\b¢\u0006\u0002\u0010/\u001a\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020&0\u0019*\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007\u001a\u001c\u00100\u001a\b\u0012\u0004\u0012\u0002010\u0019*\u00020 2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007\u001a\u001c\u00102\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&H\u0087\b¢\u0006\u0002\u00103\u001a$\u00102\u001a\u0004\u0018\u0001H\u001a\"\u0006\b\u0000\u0010\u001a\u0018\u0001*\u00020&2\u0006\u0010*\u001a\u00020+H\u0087\b¢\u0006\u0002\u00104\u001a\u001e\u00102\u001a\u0002H\u001a\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u000205H\u0087\b¢\u0006\u0002\u00106\u001a&\u00102\u001a\u0002H\u001a\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u0002052\u0006\u0010*\u001a\u00020+H\u0087\b¢\u0006\u0002\u00107\u001a\u001f\u00108\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u001f\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u000201H\u0087\b\u001a'\u00108\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u001f\"\n\b\u0000\u0010\u001a\u0018\u0001*\u00020\u001b*\u0002012\u0006\u0010*\u001a\u00020+H\u0087\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u00069"}, d2 = {"firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "Lcom/google/firebase/ktx/Firebase;", "getFirestore", "(Lcom/google/firebase/ktx/Firebase;)Lcom/google/firebase/firestore/FirebaseFirestore;", "firestoreSettings", "Lcom/google/firebase/firestore/FirebaseFirestoreSettings;", "init", "Lkotlin/Function1;", "Lcom/google/firebase/firestore/FirebaseFirestoreSettings$Builder;", "", "Lkotlin/ExtensionFunctionType;", "memoryCacheSettings", "Lcom/google/firebase/firestore/MemoryCacheSettings;", "Lcom/google/firebase/firestore/MemoryCacheSettings$Builder;", "memoryEagerGcSettings", "Lcom/google/firebase/firestore/MemoryEagerGcSettings;", "Lcom/google/firebase/firestore/MemoryEagerGcSettings$Builder;", "memoryLruGcSettings", "Lcom/google/firebase/firestore/MemoryLruGcSettings;", "Lcom/google/firebase/firestore/MemoryLruGcSettings$Builder;", "persistentCacheSettings", "Lcom/google/firebase/firestore/PersistentCacheSettings;", "Lcom/google/firebase/firestore/PersistentCacheSettings$Builder;", "dataObjects", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lcom/google/firebase/firestore/DocumentReference;", "metadataChanges", "Lcom/google/firebase/firestore/MetadataChanges;", "", "Lcom/google/firebase/firestore/Query;", "app", "Lcom/google/firebase/FirebaseApp;", "database", "", "getField", "Lcom/google/firebase/firestore/DocumentSnapshot;", "fieldPath", "Lcom/google/firebase/firestore/FieldPath;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/FieldPath;)Ljava/lang/Object;", "serverTimestampBehavior", "Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/FieldPath;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "field", "(Lcom/google/firebase/firestore/DocumentSnapshot;Ljava/lang/String;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Ljava/lang/String;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "snapshots", "Lcom/google/firebase/firestore/QuerySnapshot;", "toObject", "(Lcom/google/firebase/firestore/DocumentSnapshot;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "Lcom/google/firebase/firestore/QueryDocumentSnapshot;", "(Lcom/google/firebase/firestore/QueryDocumentSnapshot;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/QueryDocumentSnapshot;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "toObjects", "com.google.firebase-firebase-firestore"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class FirestoreKt {
    public static final FirebaseFirestore getFirestore(Firebase $this$firestore) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Intrinsics.checkNotNullExpressionValue(firebaseFirestore, "getInstance()");
        return firebaseFirestore;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, FirebaseApp app) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(app);
        Intrinsics.checkNotNullExpressionValue(firebaseFirestore, "getInstance(app)");
        return firebaseFirestore;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, FirebaseApp app, String database) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(database, "database");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(app, database);
        Intrinsics.checkNotNullExpressionValue(firebaseFirestore, "getInstance(app, database)");
        return firebaseFirestore;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, String database) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(database, "database");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(database);
        Intrinsics.checkNotNullExpressionValue(firebaseFirestore, "getInstance(database)");
        return firebaseFirestore;
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T toObject(DocumentSnapshot documentSnapshot) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.toObject(Object.class);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T toObject(DocumentSnapshot documentSnapshot, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.toObject(Object.class, serverTimestampBehavior);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T getField(DocumentSnapshot documentSnapshot, String field) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.get(field, Object.class);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T getField(DocumentSnapshot documentSnapshot, String field, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.get(field, Object.class, serverTimestampBehavior);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T getField(DocumentSnapshot documentSnapshot, FieldPath fieldPath) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(fieldPath, "fieldPath");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.get(fieldPath, Object.class);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T getField(DocumentSnapshot documentSnapshot, FieldPath fieldPath, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter(documentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(fieldPath, "fieldPath");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) documentSnapshot.get(fieldPath, Object.class, serverTimestampBehavior);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T toObject(QueryDocumentSnapshot queryDocumentSnapshot) {
        Intrinsics.checkNotNullParameter(queryDocumentSnapshot, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) queryDocumentSnapshot.toObject(Object.class);
        Intrinsics.checkNotNullExpressionValue(t, "toObject(T::class.java)");
        return t;
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> T toObject(QueryDocumentSnapshot queryDocumentSnapshot, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter(queryDocumentSnapshot, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        T t = (T) queryDocumentSnapshot.toObject(Object.class, serverTimestampBehavior);
        Intrinsics.checkNotNullExpressionValue(t, "toObject(T::class.java, serverTimestampBehavior)");
        return t;
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> List<T> toObjects(QuerySnapshot $this$toObjects) {
        Intrinsics.checkNotNullParameter($this$toObjects, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        List<T> objects = $this$toObjects.toObjects(Object.class);
        Intrinsics.checkNotNullExpressionValue(objects, "toObjects(T::class.java)");
        return objects;
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> List<T> toObjects(QuerySnapshot $this$toObjects, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$toObjects, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        List<T> objects = $this$toObjects.toObjects(Object.class, serverTimestampBehavior);
        Intrinsics.checkNotNullExpressionValue(objects, "toObjects(T::class.java, serverTimestampBehavior)");
        return objects;
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final FirebaseFirestoreSettings firestoreSettings(Function1<? super FirebaseFirestoreSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirebaseFirestoreSettings.Builder builder = new FirebaseFirestoreSettings.Builder();
        init.invoke(builder);
        FirebaseFirestoreSettings firebaseFirestoreSettingsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(firebaseFirestoreSettingsBuild, "builder.build()");
        return firebaseFirestoreSettingsBuild;
    }

    public static final MemoryCacheSettings memoryCacheSettings(Function1<? super MemoryCacheSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryCacheSettings.Builder builder = MemoryCacheSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder()");
        init.invoke(builder);
        MemoryCacheSettings memoryCacheSettingsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(memoryCacheSettingsBuild, "builder.build()");
        return memoryCacheSettingsBuild;
    }

    public static final MemoryEagerGcSettings memoryEagerGcSettings(Function1<? super MemoryEagerGcSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryEagerGcSettings.Builder builder = MemoryEagerGcSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder()");
        init.invoke(builder);
        MemoryEagerGcSettings memoryEagerGcSettingsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(memoryEagerGcSettingsBuild, "builder.build()");
        return memoryEagerGcSettingsBuild;
    }

    public static final MemoryLruGcSettings memoryLruGcSettings(Function1<? super MemoryLruGcSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryLruGcSettings.Builder builder = MemoryLruGcSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder()");
        init.invoke(builder);
        MemoryLruGcSettings memoryLruGcSettingsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(memoryLruGcSettingsBuild, "builder.build()");
        return memoryLruGcSettingsBuild;
    }

    public static final PersistentCacheSettings persistentCacheSettings(Function1<? super PersistentCacheSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        PersistentCacheSettings.Builder builder = PersistentCacheSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder()");
        init.invoke(builder);
        PersistentCacheSettings persistentCacheSettingsBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(persistentCacheSettingsBuild, "builder.build()");
        return persistentCacheSettingsBuild;
    }

    public static /* synthetic */ Flow snapshots$default(DocumentReference documentReference, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        return snapshots(documentReference, metadataChanges);
    }

    /* JADX INFO: renamed from: com.google.firebase.firestore.ktx.FirestoreKt$snapshots$1, reason: invalid class name */
    /* JADX INFO: compiled from: Firestore.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/google/firebase/firestore/DocumentSnapshot;"}, k = 3, mv = {1, 7, 1}, xi = 48)
    @DebugMetadata(c = "com.google.firebase.firestore.ktx.FirestoreKt$snapshots$1", f = "Firestore.kt", i = {}, l = {412}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<ProducerScope<? super DocumentSnapshot>, Continuation<? super Unit>, Object> {
        final /* synthetic */ MetadataChanges $metadataChanges;
        final /* synthetic */ DocumentReference $this_snapshots;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DocumentReference documentReference, MetadataChanges metadataChanges, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_snapshots = documentReference;
            this.$metadataChanges = metadataChanges;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_snapshots, this.$metadataChanges, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super DocumentSnapshot> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    final ProducerScope $this$callbackFlow = (ProducerScope) this.L$0;
                    final ListenerRegistration registration = this.$this_snapshots.addSnapshotListener(Executors.BACKGROUND_EXECUTOR, this.$metadataChanges, new EventListener() { // from class: com.google.firebase.firestore.ktx.FirestoreKt$snapshots$1$$ExternalSyntheticLambda0
                        @Override // com.google.firebase.firestore.EventListener
                        public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                            FirestoreKt.AnonymousClass1.m6162invokeSuspend$lambda0($this$callbackFlow, (DocumentSnapshot) obj, firebaseFirestoreException);
                        }
                    });
                    Intrinsics.checkNotNullExpressionValue(registration, "addSnapshotListener(BACK…apshot)\n        }\n      }");
                    this.label = 1;
                    if (ProduceKt.awaitClose($this$callbackFlow, new Function0<Unit>() { // from class: com.google.firebase.firestore.ktx.FirestoreKt.snapshots.1.1
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
                            registration.remove();
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
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m6162invokeSuspend$lambda0(ProducerScope $$this$callbackFlow, DocumentSnapshot snapshot, FirebaseFirestoreException exception) {
            if (exception != null) {
                CoroutineScopeKt.cancel($$this$callbackFlow, "Error getting DocumentReference snapshot", exception);
            } else if (snapshot != null) {
                ChannelsKt.trySendBlocking($$this$callbackFlow, snapshot);
            }
        }
    }

    @Deprecated(message = "com.google.firebase.fires", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final Flow<DocumentSnapshot> snapshots(DocumentReference $this$snapshots, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$snapshots, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        return FlowKt.callbackFlow(new AnonymousClass1($this$snapshots, metadataChanges, null));
    }

    public static /* synthetic */ Flow snapshots$default(Query query, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        return snapshots(query, metadataChanges);
    }

    /* JADX INFO: renamed from: com.google.firebase.firestore.ktx.FirestoreKt$snapshots$2, reason: invalid class name */
    /* JADX INFO: compiled from: Firestore.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/google/firebase/firestore/QuerySnapshot;"}, k = 3, mv = {1, 7, 1}, xi = 48)
    @DebugMetadata(c = "com.google.firebase.firestore.ktx.FirestoreKt$snapshots$2", f = "Firestore.kt", i = {}, l = {445}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<ProducerScope<? super QuerySnapshot>, Continuation<? super Unit>, Object> {
        final /* synthetic */ MetadataChanges $metadataChanges;
        final /* synthetic */ Query $this_snapshots;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Query query, MetadataChanges metadataChanges, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$this_snapshots = query;
            this.$metadataChanges = metadataChanges;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_snapshots, this.$metadataChanges, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super QuerySnapshot> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    final ProducerScope $this$callbackFlow = (ProducerScope) this.L$0;
                    final ListenerRegistration registration = this.$this_snapshots.addSnapshotListener(Executors.BACKGROUND_EXECUTOR, this.$metadataChanges, new EventListener() { // from class: com.google.firebase.firestore.ktx.FirestoreKt$snapshots$2$$ExternalSyntheticLambda0
                        @Override // com.google.firebase.firestore.EventListener
                        public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
                            FirestoreKt.AnonymousClass2.m6163invokeSuspend$lambda0($this$callbackFlow, (QuerySnapshot) obj, firebaseFirestoreException);
                        }
                    });
                    Intrinsics.checkNotNullExpressionValue(registration, "addSnapshotListener(BACK…apshot)\n        }\n      }");
                    this.label = 1;
                    if (ProduceKt.awaitClose($this$callbackFlow, new Function0<Unit>() { // from class: com.google.firebase.firestore.ktx.FirestoreKt.snapshots.2.1
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
                            registration.remove();
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
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m6163invokeSuspend$lambda0(ProducerScope $$this$callbackFlow, QuerySnapshot snapshot, FirebaseFirestoreException exception) {
            if (exception != null) {
                CoroutineScopeKt.cancel($$this$callbackFlow, "Error getting Query snapshot", exception);
            } else if (snapshot != null) {
                ChannelsKt.trySendBlocking($$this$callbackFlow, snapshot);
            }
        }
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final Flow<QuerySnapshot> snapshots(Query $this$snapshots, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$snapshots, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        return FlowKt.callbackFlow(new AnonymousClass2($this$snapshots, metadataChanges, null));
    }

    public static /* synthetic */ Flow dataObjects$default(Query $this$dataObjects_u24default, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        Intrinsics.checkNotNullParameter($this$dataObjects_u24default, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Flow<QuerySnapshot> flowSnapshots = snapshots($this$dataObjects_u24default, metadataChanges);
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$1(flowSnapshots);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> Flow<List<T>> dataObjects(Query $this$dataObjects, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$dataObjects, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Flow<QuerySnapshot> flowSnapshots = snapshots($this$dataObjects, metadataChanges);
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$1(flowSnapshots);
    }

    public static /* synthetic */ Flow dataObjects$default(DocumentReference $this$dataObjects_u24default, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        Intrinsics.checkNotNullParameter($this$dataObjects_u24default, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Flow<DocumentSnapshot> flowSnapshots = snapshots($this$dataObjects_u24default, metadataChanges);
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$2(flowSnapshots);
    }

    @Deprecated(message = "Migrate to use the KTX API from the main module: https://firebase.google.com/docs/android/kotlin-migration.", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public static final /* synthetic */ <T> Flow<T> dataObjects(DocumentReference $this$dataObjects, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$dataObjects, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Flow<DocumentSnapshot> flowSnapshots = snapshots($this$dataObjects, metadataChanges);
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$2(flowSnapshots);
    }
}
