package coil.fetch;

import android.net.Uri;
import android.webkit.MimeTypeMap;
import coil.ImageLoader;
import coil.decode.DataSource;
import coil.decode.ImageSource;
import coil.decode.ImageSources;
import coil.disk.DiskCache;
import coil.fetch.Fetcher;
import coil.network.CacheResponse;
import coil.network.CacheStrategy;
import coil.request.Options;
import coil.util.Utils;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.FileSystem;
import okio.Okio;
import okio.Path;

/* JADX INFO: compiled from: HttpUriFetcher.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 02\u00020\u0001:\u000201B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0007\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0019\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0019J\u0011\u0010\u001a\u001a\u00020\u001bH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u001cJ!\u0010\u001d\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0001¢\u0006\u0002\b J\u0018\u0010!\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u0016H\u0002J\b\u0010#\u001a\u00020\u0018H\u0002J\n\u0010$\u001a\u0004\u0018\u00010%H\u0002J.\u0010&\u001a\u0004\u0018\u00010%2\b\u0010'\u001a\u0004\u0018\u00010%2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u00162\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u000e\u0010*\u001a\u0004\u0018\u00010)*\u00020%H\u0002J\f\u0010+\u001a\u00020,*\u00020\u0016H\u0002J\f\u0010-\u001a\u00020.*\u00020%H\u0002J\f\u0010-\u001a\u00020.*\u00020/H\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u00062"}, d2 = {"Lcoil/fetch/HttpUriFetcher;", "Lcoil/fetch/Fetcher;", ImagesContract.URL, "", "options", "Lcoil/request/Options;", "callFactory", "Lkotlin/Lazy;", "Lokhttp3/Call$Factory;", "diskCache", "Lcoil/disk/DiskCache;", "respectCacheHeaders", "", "(Ljava/lang/String;Lcoil/request/Options;Lkotlin/Lazy;Lkotlin/Lazy;Z)V", "diskCacheKey", "getDiskCacheKey", "()Ljava/lang/String;", "fileSystem", "Lokio/FileSystem;", "getFileSystem", "()Lokio/FileSystem;", "executeNetworkRequest", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "(Lokhttp3/Request;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetch", "Lcoil/fetch/FetchResult;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMimeType", "contentType", "Lokhttp3/MediaType;", "getMimeType$coil_base_release", "isCacheable", "response", "newRequest", "readFromDiskCache", "Lcoil/disk/DiskCache$Snapshot;", "writeToDiskCache", "snapshot", "cacheResponse", "Lcoil/network/CacheResponse;", "toCacheResponse", "toDataSource", "Lcoil/decode/DataSource;", "toImageSource", "Lcoil/decode/ImageSource;", "Lokhttp3/ResponseBody;", "Companion", "Factory", "coil-base_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HttpUriFetcher implements Fetcher {
    private static final String MIME_TYPE_TEXT_PLAIN = "text/plain";
    private final Lazy<Call.Factory> callFactory;
    private final Lazy<DiskCache> diskCache;
    private final Options options;
    private final boolean respectCacheHeaders;
    private final String url;
    private static final CacheControl CACHE_CONTROL_FORCE_NETWORK_NO_CACHE = new CacheControl.Builder().noCache().noStore().build();
    private static final CacheControl CACHE_CONTROL_NO_NETWORK_NO_CACHE = new CacheControl.Builder().noCache().onlyIfCached().build();

    /* JADX INFO: renamed from: coil.fetch.HttpUriFetcher$executeNetworkRequest$1, reason: invalid class name */
    /* JADX INFO: compiled from: HttpUriFetcher.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "coil.fetch.HttpUriFetcher", f = "HttpUriFetcher.kt", i = {}, l = {224}, m = "executeNetworkRequest", n = {}, s = {})
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HttpUriFetcher.this.executeNetworkRequest(null, this);
        }
    }

    /* JADX INFO: renamed from: coil.fetch.HttpUriFetcher$fetch$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: HttpUriFetcher.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "coil.fetch.HttpUriFetcher", f = "HttpUriFetcher.kt", i = {0, 0, 0, 1, 1, 1}, l = {77, 106}, m = "fetch", n = {"this", "snapshot", "cacheStrategy", "this", "snapshot", "response"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2"})
    static final class C06111 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C06111(Continuation<? super C06111> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HttpUriFetcher.this.fetch(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HttpUriFetcher(String url, Options options, Lazy<? extends Call.Factory> lazy, Lazy<? extends DiskCache> lazy2, boolean respectCacheHeaders) {
        this.url = url;
        this.options = options;
        this.callFactory = lazy;
        this.diskCache = lazy2;
        this.respectCacheHeaders = respectCacheHeaders;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x012e A[Catch: Exception -> 0x01a4, TryCatch #2 {Exception -> 0x01a4, blocks: (B:50:0x011e, B:52:0x012e, B:54:0x013c, B:55:0x0140, B:57:0x014a, B:59:0x0152, B:61:0x016a), top: B:78:0x011e }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x014a A[Catch: Exception -> 0x01a4, TryCatch #2 {Exception -> 0x01a4, blocks: (B:50:0x011e, B:52:0x012e, B:54:0x013c, B:55:0x0140, B:57:0x014a, B:59:0x0152, B:61:0x016a), top: B:78:0x011e }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    @Override // coil.fetch.Fetcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object fetch(kotlin.coroutines.Continuation<? super coil.fetch.FetchResult> r14) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 452
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.fetch.HttpUriFetcher.fetch(kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final DiskCache.Snapshot readFromDiskCache() {
        DiskCache value;
        if (!this.options.getDiskCachePolicy().getReadEnabled() || (value = this.diskCache.getValue()) == null) {
            return null;
        }
        return value.openSnapshot(getDiskCacheKey());
    }

    private final DiskCache.Snapshot writeToDiskCache(DiskCache.Snapshot snapshot, Request request, Response response, CacheResponse cacheResponse) {
        DiskCache.Editor editorOpenEditor;
        if (!isCacheable(request, response)) {
            if (snapshot != null) {
                Utils.closeQuietly(snapshot);
            }
            return null;
        }
        if (snapshot != null) {
            editorOpenEditor = snapshot.closeAndOpenEditor();
        } else {
            DiskCache value = this.diskCache.getValue();
            editorOpenEditor = value != null ? value.openEditor(getDiskCacheKey()) : null;
        }
        DiskCache.Editor editor = editorOpenEditor;
        try {
            if (editor == null) {
                return null;
            }
            try {
                if (response.code() == 304 && cacheResponse != null) {
                    Response combinedResponse = response.newBuilder().headers(CacheStrategy.INSTANCE.combineHeaders(cacheResponse.getResponseHeaders(), response.headers())).build();
                    FileSystem $this$iv = getFileSystem();
                    Path file$iv = editor.getMetadata();
                    Closeable $this$use$iv$iv = Okio.buffer($this$iv.sink(file$iv, false));
                    Object result$iv$iv = null;
                    Throwable thrown$iv$iv = null;
                    try {
                        BufferedSink it$iv = (BufferedSink) $this$use$iv$iv;
                        new CacheResponse(combinedResponse).writeTo(it$iv);
                        result$iv$iv = Unit.INSTANCE;
                        if ($this$use$iv$iv != null) {
                            try {
                                $this$use$iv$iv.close();
                            } catch (Throwable t$iv$iv) {
                                thrown$iv$iv = t$iv$iv;
                            }
                        }
                    } catch (Throwable t$iv$iv2) {
                        thrown$iv$iv = t$iv$iv2;
                        if ($this$use$iv$iv != null) {
                            try {
                                $this$use$iv$iv.close();
                            } catch (Throwable t$iv$iv3) {
                                ExceptionsKt.addSuppressed(thrown$iv$iv, t$iv$iv3);
                            }
                        }
                    }
                    if (thrown$iv$iv != null) {
                        throw thrown$iv$iv;
                    }
                    Intrinsics.checkNotNull(result$iv$iv);
                } else {
                    FileSystem $this$iv2 = getFileSystem();
                    Path file$iv2 = editor.getMetadata();
                    Closeable $this$use$iv$iv2 = Okio.buffer($this$iv2.sink(file$iv2, false));
                    Object result$iv$iv2 = null;
                    Throwable thrown$iv$iv2 = null;
                    try {
                        BufferedSink it$iv2 = (BufferedSink) $this$use$iv$iv2;
                        new CacheResponse(response).writeTo(it$iv2);
                        result$iv$iv2 = Unit.INSTANCE;
                        if ($this$use$iv$iv2 != null) {
                            try {
                                $this$use$iv$iv2.close();
                            } catch (Throwable t$iv$iv4) {
                                thrown$iv$iv2 = t$iv$iv4;
                            }
                        }
                    } catch (Throwable t$iv$iv5) {
                        thrown$iv$iv2 = t$iv$iv5;
                        if ($this$use$iv$iv2 != null) {
                            try {
                                $this$use$iv$iv2.close();
                            } catch (Throwable t$iv$iv6) {
                                ExceptionsKt.addSuppressed(thrown$iv$iv2, t$iv$iv6);
                            }
                        }
                    }
                    if (thrown$iv$iv2 != null) {
                        throw thrown$iv$iv2;
                    }
                    Intrinsics.checkNotNull(result$iv$iv2);
                    FileSystem $this$iv3 = getFileSystem();
                    Path file$iv3 = editor.getData();
                    Closeable $this$use$iv$iv3 = Okio.buffer($this$iv3.sink(file$iv3, false));
                    Object result$iv$iv3 = null;
                    Throwable thrown$iv$iv3 = null;
                    try {
                        BufferedSink it$iv3 = (BufferedSink) $this$use$iv$iv3;
                        ResponseBody responseBodyBody = response.body();
                        Intrinsics.checkNotNull(responseBodyBody);
                        result$iv$iv3 = Long.valueOf(responseBodyBody.getBodySource().readAll(it$iv3));
                        if ($this$use$iv$iv3 != null) {
                            try {
                                $this$use$iv$iv3.close();
                            } catch (Throwable t$iv$iv7) {
                                thrown$iv$iv3 = t$iv$iv7;
                            }
                        }
                    } catch (Throwable t$iv$iv8) {
                        thrown$iv$iv3 = t$iv$iv8;
                        if ($this$use$iv$iv3 != null) {
                            try {
                                $this$use$iv$iv3.close();
                            } catch (Throwable t$iv$iv9) {
                                ExceptionsKt.addSuppressed(thrown$iv$iv3, t$iv$iv9);
                            }
                        }
                    }
                    if (thrown$iv$iv3 != null) {
                        throw thrown$iv$iv3;
                    }
                    Intrinsics.checkNotNull(result$iv$iv3);
                }
                return editor.commitAndOpenSnapshot();
            } catch (Exception e) {
                Utils.abortQuietly(editor);
                throw e;
            }
        } finally {
            Utils.closeQuietly(response);
        }
    }

    private final Request newRequest() {
        Request.Builder request = new Request.Builder().url(this.url).headers(this.options.getHeaders());
        for (Map.Entry<Class<?>, Object> entry : this.options.getTags().asMap().entrySet()) {
            Class<?> key = entry.getKey();
            Intrinsics.checkNotNull(key, "null cannot be cast to non-null type java.lang.Class<kotlin.Any>");
            request.tag(key, entry.getValue());
        }
        boolean diskRead = this.options.getDiskCachePolicy().getReadEnabled();
        boolean networkRead = this.options.getNetworkCachePolicy().getReadEnabled();
        if (!networkRead && diskRead) {
            request.cacheControl(CacheControl.FORCE_CACHE);
        } else if (!networkRead || diskRead) {
            if (!networkRead && !diskRead) {
                request.cacheControl(CACHE_CONTROL_NO_NETWORK_NO_CACHE);
            }
        } else if (this.options.getDiskCachePolicy().getWriteEnabled()) {
            request.cacheControl(CacheControl.FORCE_NETWORK);
        } else {
            request.cacheControl(CACHE_CONTROL_FORCE_NETWORK_NO_CACHE);
        }
        return request.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object executeNetworkRequest(okhttp3.Request r6, kotlin.coroutines.Continuation<? super okhttp3.Response> r7) throws java.io.IOException {
        /*
            r5 = this;
            boolean r0 = r7 instanceof coil.fetch.HttpUriFetcher.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r7
            coil.fetch.HttpUriFetcher$executeNetworkRequest$1 r0 = (coil.fetch.HttpUriFetcher.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            coil.fetch.HttpUriFetcher$executeNetworkRequest$1 r0 = new coil.fetch.HttpUriFetcher$executeNetworkRequest$1
            r0.<init>(r7)
        L19:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L32;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r0
            goto L75
        L32:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            boolean r3 = coil.util.Utils.isMainThread()
            if (r3 == 0) goto L5f
            coil.request.Options r1 = r2.options
            coil.request.CachePolicy r1 = r1.getNetworkCachePolicy()
            boolean r1 = r1.getReadEnabled()
            if (r1 != 0) goto L59
            kotlin.Lazy<okhttp3.Call$Factory> r1 = r2.callFactory
            java.lang.Object r1 = r1.getValue()
            okhttp3.Call$Factory r1 = (okhttp3.Call.Factory) r1
            okhttp3.Call r1 = r1.newCall(r6)
            okhttp3.Response r6 = r1.execute()
            goto L77
        L59:
            android.os.NetworkOnMainThreadException r6 = new android.os.NetworkOnMainThreadException
            r6.<init>()
            throw r6
        L5f:
            kotlin.Lazy<okhttp3.Call$Factory> r3 = r2.callFactory
            java.lang.Object r3 = r3.getValue()
            okhttp3.Call$Factory r3 = (okhttp3.Call.Factory) r3
            okhttp3.Call r3 = r3.newCall(r6)
            r4 = 1
            r7.label = r4
            java.lang.Object r6 = coil.util.Calls.await(r3, r7)
            if (r6 != r1) goto L75
            return r1
        L75:
            okhttp3.Response r6 = (okhttp3.Response) r6
        L77:
            boolean r1 = r6.isSuccessful()
            if (r1 != 0) goto L97
            int r1 = r6.code()
            r2 = 304(0x130, float:4.26E-43)
            if (r1 == r2) goto L97
            okhttp3.ResponseBody r1 = r6.body()
            if (r1 == 0) goto L91
            java.io.Closeable r1 = (java.io.Closeable) r1
            coil.util.Utils.closeQuietly(r1)
        L91:
            coil.network.HttpException r1 = new coil.network.HttpException
            r1.<init>(r6)
            throw r1
        L97:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.fetch.HttpUriFetcher.executeNetworkRequest(okhttp3.Request, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final String getMimeType$coil_base_release(String url, MediaType contentType) {
        String it;
        String rawContentType = contentType != null ? contentType.getMediaType() : null;
        if ((rawContentType == null || StringsKt.startsWith$default(rawContentType, MIME_TYPE_TEXT_PLAIN, false, 2, (Object) null)) && (it = Utils.getMimeTypeFromUrl(MimeTypeMap.getSingleton(), url)) != null) {
            return it;
        }
        if (rawContentType != null) {
            return StringsKt.substringBefore$default(rawContentType, ';', (String) null, 2, (Object) null);
        }
        return null;
    }

    private final boolean isCacheable(Request request, Response response) {
        return this.options.getDiskCachePolicy().getWriteEnabled() && (!this.respectCacheHeaders || CacheStrategy.INSTANCE.isCacheable(request, response));
    }

    private final CacheResponse toCacheResponse(DiskCache.Snapshot $this$toCacheResponse) throws Throwable {
        try {
            FileSystem this_$iv = getFileSystem();
            Path file$iv = $this$toCacheResponse.getMetadata();
            Closeable $this$use$iv$iv = Okio.buffer(this_$iv.source(file$iv));
            CacheResponse cacheResponse = null;
            Throwable thrown$iv$iv = null;
            try {
                BufferedSource it$iv = (BufferedSource) $this$use$iv$iv;
                cacheResponse = new CacheResponse(it$iv);
                if ($this$use$iv$iv != null) {
                    try {
                        $this$use$iv$iv.close();
                    } catch (Throwable t$iv$iv) {
                        thrown$iv$iv = t$iv$iv;
                    }
                }
            } catch (Throwable t$iv$iv2) {
                thrown$iv$iv = t$iv$iv2;
                if ($this$use$iv$iv != null) {
                    try {
                        $this$use$iv$iv.close();
                    } catch (Throwable t$iv$iv3) {
                        ExceptionsKt.addSuppressed(thrown$iv$iv, t$iv$iv3);
                    }
                }
            }
            if (thrown$iv$iv != null) {
                throw thrown$iv$iv;
            }
            Intrinsics.checkNotNull(cacheResponse);
            return cacheResponse;
        } catch (IOException e) {
            return null;
        }
    }

    private final ImageSource toImageSource(DiskCache.Snapshot $this$toImageSource) {
        return ImageSources.create($this$toImageSource.getData(), getFileSystem(), getDiskCacheKey(), $this$toImageSource);
    }

    private final ImageSource toImageSource(ResponseBody $this$toImageSource) {
        return ImageSources.create($this$toImageSource.getBodySource(), this.options.getContext());
    }

    private final DataSource toDataSource(Response $this$toDataSource) {
        return $this$toDataSource.networkResponse() != null ? DataSource.NETWORK : DataSource.DISK;
    }

    private final String getDiskCacheKey() {
        String diskCacheKey = this.options.getDiskCacheKey();
        return diskCacheKey == null ? this.url : diskCacheKey;
    }

    private final FileSystem getFileSystem() {
        DiskCache value = this.diskCache.getValue();
        Intrinsics.checkNotNull(value);
        return value.getFileSystem();
    }

    /* JADX INFO: compiled from: HttpUriFetcher.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B+\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0002H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcoil/fetch/HttpUriFetcher$Factory;", "Lcoil/fetch/Fetcher$Factory;", "Landroid/net/Uri;", "callFactory", "Lkotlin/Lazy;", "Lokhttp3/Call$Factory;", "diskCache", "Lcoil/disk/DiskCache;", "respectCacheHeaders", "", "(Lkotlin/Lazy;Lkotlin/Lazy;Z)V", "create", "Lcoil/fetch/Fetcher;", "data", "options", "Lcoil/request/Options;", "imageLoader", "Lcoil/ImageLoader;", "isApplicable", "coil-base_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Factory implements Fetcher.Factory<Uri> {
        private final Lazy<Call.Factory> callFactory;
        private final Lazy<DiskCache> diskCache;
        private final boolean respectCacheHeaders;

        /* JADX WARN: Multi-variable type inference failed */
        public Factory(Lazy<? extends Call.Factory> lazy, Lazy<? extends DiskCache> lazy2, boolean respectCacheHeaders) {
            this.callFactory = lazy;
            this.diskCache = lazy2;
            this.respectCacheHeaders = respectCacheHeaders;
        }

        @Override // coil.fetch.Fetcher.Factory
        public Fetcher create(Uri data, Options options, ImageLoader imageLoader) {
            if (isApplicable(data)) {
                return new HttpUriFetcher(data.toString(), options, this.callFactory, this.diskCache, this.respectCacheHeaders);
            }
            return null;
        }

        private final boolean isApplicable(Uri data) {
            return Intrinsics.areEqual(data.getScheme(), "http") || Intrinsics.areEqual(data.getScheme(), "https");
        }
    }
}
