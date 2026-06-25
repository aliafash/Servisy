package com.maw;

import android.content.Context;
import android.widget.Toast;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$ProfessionalProfileDialog$1$1$1$4$7 extends Lambda implements Function0<Unit> {
    final /* synthetic */ MutableState<String> $cfContact$delegate;
    final /* synthetic */ MutableState<String> $cfMessage$delegate;
    final /* synthetic */ MutableState<String> $cfName$delegate;
    final /* synthetic */ Context $context;
    final /* synthetic */ Provider $provider;
    final /* synthetic */ State<AppSettings> $settings$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$ProfessionalProfileDialog$1$1$1$4$7(Context context, Provider provider, MutableState<String> mutableState, MutableState<String> mutableState2, MutableState<String> mutableState3, State<AppSettings> state) {
        super(0);
        this.$context = context;
        this.$provider = provider;
        this.$cfName$delegate = mutableState;
        this.$cfContact$delegate = mutableState2;
        this.$cfMessage$delegate = mutableState3;
        this.$settings$delegate = state;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ab  */
    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void invoke2() {
        /*
            Method dump skipped, instruction units count: 410
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.MainActivityKt$ProfessionalProfileDialog$1$1$1$4$7.invoke2():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(Function1 tmp0, Object p0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(p0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1(Context context, Exception e) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(e, "e");
        Toast.makeText(context, "❌ حدث خطأ في الإرسال: " + e.getMessage(), 0).show();
    }
}
