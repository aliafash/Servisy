package com.maw;

import android.content.Context;
import android.net.Uri;
import androidx.activity.compose.ManagedActivityResultLauncher;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.text.font.FontFamily;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u000b¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Landroidx/compose/foundation/layout/ColumnScope;", "invoke", "(Landroidx/compose/foundation/layout/ColumnScope;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class MainActivityKt$CategoriesCitiesTab$1$2 extends Lambda implements Function3<ColumnScope, Composer, Integer, Unit> {
    final /* synthetic */ MutableState<String> $catDescAr$delegate;
    final /* synthetic */ MutableState<String> $catIconSim$delegate;
    final /* synthetic */ ManagedActivityResultLauncher<String, Uri> $catImagePicker;
    final /* synthetic */ MutableState<String> $catNameAr$delegate;
    final /* synthetic */ MutableState<String> $catNameEn$delegate;
    final /* synthetic */ List<Category> $categories;
    final /* synthetic */ Context $context;
    final /* synthetic */ MutableState<Boolean> $displayDirectlyCheck$delegate;
    final /* synthetic */ MutableState<Category> $editingCategory$delegate;
    final /* synthetic */ FontFamily $fontFamily;
    final /* synthetic */ MutableState<String> $parentCatIdSelected$delegate;
    final /* synthetic */ MutableState<Boolean> $pinCategoryCheck$delegate;
    final /* synthetic */ MainViewModel $vm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MainActivityKt$CategoriesCitiesTab$1$2(FontFamily fontFamily, MutableState<Category> mutableState, MutableState<String> mutableState2, MutableState<String> mutableState3, MutableState<String> mutableState4, MutableState<String> mutableState5, MutableState<String> mutableState6, MutableState<Boolean> mutableState7, ManagedActivityResultLauncher<String, Uri> managedActivityResultLauncher, List<Category> list, MutableState<Boolean> mutableState8, Context context, MainViewModel mainViewModel) {
        super(3);
        this.$fontFamily = fontFamily;
        this.$editingCategory$delegate = mutableState;
        this.$catNameAr$delegate = mutableState2;
        this.$catNameEn$delegate = mutableState3;
        this.$catDescAr$delegate = mutableState4;
        this.$catIconSim$delegate = mutableState5;
        this.$parentCatIdSelected$delegate = mutableState6;
        this.$pinCategoryCheck$delegate = mutableState7;
        this.$catImagePicker = managedActivityResultLauncher;
        this.$categories = list;
        this.$displayDirectlyCheck$delegate = mutableState8;
        this.$context = context;
        this.$vm = mainViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
        invoke(columnScope, composer, num.intValue());
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x1093  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x10a7  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x119c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x11a8  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x11ac  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x125f  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x126f  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x12eb  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x12f2  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x1381  */
    /* JADX WARN: Removed duplicated region for block: B:171:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void invoke(androidx.compose.foundation.layout.ColumnScope r271, androidx.compose.runtime.Composer r272, int r273) {
        /*
            Method dump skipped, instruction units count: 4997
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.maw.MainActivityKt$CategoriesCitiesTab$1$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$18$lambda$10(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    private static final boolean invoke$lambda$18$lambda$9(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }
}
