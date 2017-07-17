package cn.zengcanxiang.mvp_practice_project_template.api;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 简单的请求log拦截打印
 */
public class HttpLog implements Interceptor {
    private final String TAG = "HttpLog";

    private boolean isDebug;

    public HttpLog() {
        this(BuildConfig.DEBUG);
    }

    public HttpLog(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //非debug模式，直接进行请求
        if (!isDebug) {
            return chain.proceed(request);
        }
        logRequest(request);
        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        return logForResponse(response, tookMs);
    }

    private void logRequest(Request request) {
        StringBuffer logStr = new StringBuffer("请求Url：");
        logStr.append(request.url().toString() + "\n");
        logStr.append("请求方法类型：");
        logStr.append(request.method() + "\n");
        logStr.append("请求参数：");
        logStr.append(bodyToString(request));
        Logger.t(TAG, 0).d(logStr.toString());
    }

    /**
     * @param tookMs 请求耗时
     */
    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        try {
            if (isPlaintext(responseBody.contentType())) {
                String body = responseBody.string();
                String url = clone.request().url().toString();

                Logger.t(TAG, 0).
                        e("---------------------------- " + url + " 返回参数----------------------------\n" +
                                "---------------------------- 请求耗时" + tookMs + "MS ----------------------------");
                try {
                    Logger.t(TAG, 0).json(body);
                } catch (Exception e) {
                    Logger.t(TAG, 0).d(body);
                }

                responseBody = ResponseBody.create(responseBody.contentType(), body);
                return response.newBuilder().body(responseBody).build();
            } else {
                Logger.t(TAG, 0).d("\tbody: maybe [file part] , too large too print , ignored!");
            }
        } catch (Exception e) {
            Logger.t(TAG, 0).e(e.getMessage(), e.getCause());
        }

        return response;
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            RequestBody body = copy.body();
            //get请求则body为空
            if (body == null) {
                return "";
            }
            body.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            return URLDecoder.decode(buffer.readString(charset), "UTF-8");
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

}
