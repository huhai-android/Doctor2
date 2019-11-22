package com.lxq.okhttp.exception;

/**
 * Created by jhon 2018/3/1.
 */

public class ExceptionEngine {
    //对应HTTP的状态码
    /**
     * 解析错误
     */
    public static final int GSON_ERROR = 1001;//Gson解析失败
    private static final int FAIL = 0;
    private static final int UserNOTexit = 2;
    public static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //一、1xx（临时响应）
//表示临时响应并需要请求者继续执行操作的状态代码。
    private static final int code100 = 100;// （继续） 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
    private static final int code101 = 101;// （切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换。

    // 二、2xx （成功）
//表示成功处理了请求的状态代码。
    private static final int code200 = 200;// （成功） 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
    private static final int code201 = 201;// 已创建） 请求成功并且服务器创建了新的资源。
    private static final int code202 = 202;// 已接受） 服务器已接受请求，但尚未处理。
    private static final int code203 = 203;// 非授权信息） 服务器已成功处理了请求，但返回的信息可能来自另一来源。
    private static final int code204 = 204;// 无内容） 服务器成功处理了请求，但没有返回任何内容。
    private static final int code205 = 205;// 重置内容） 服务器成功处理了请求，但没有返回任何内容。
    private static final int code206 = 206;// 部分内容） 服务器成功处理了部分 GET 请求。

    //    三、3xx;// 重定向）
//    表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
    private static final int code300 = 300;// 多种选择） 针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。
    private static final int code301 = 301;// 永久移动） 请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。
    private static final int code302 = 302;// 临时移动） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
    private static final int code303 = 303;// 查看其他位置） 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。
    private static final int code304 = 304;// 未修改） 自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。
    private static final int code305 = 305;// 使用代理） 请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。
    private static final int code307 = 307;// 临时重定向） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。

    //    四、4xx（请求错误）
//    这些状态代码表示请求可能出错，妨碍了服务器的处理。
    private static final int code400 = 400;// 错误请求） 服务器不理解请求的语法。
    private static final int code401 = 401;// 未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
    private static final int code403 = 403;// 禁止） 服务器拒绝请求。
    private static final int code404 = 404;// 未找到） 服务器找不到请求的网页。
    private static final int code405 = 405;// 方法禁用） 禁用请求中指定的方法。
    private static final int code406 = 406;// 不接受） 无法使用请求的内容特性响应请求的网页。
    private static final int code407 = 407;// 需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
    private static final int code408 = 408;// 请求超时） 服务器等候请求时发生超时。
    private static final int code409 = 409;// 冲突） 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
    private static final int code410 = 410;// 已删除） 如果请求的资源已永久删除，服务器就会返回此响应。
    private static final int code411 = 411;// 需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
    private static final int code412 = 412;// 未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
    private static final int code413 = 413;// 请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
    private static final int code414 = 414;// 请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
    private static final int code415 = 415;// 不支持的媒体类型） 请求的格式不受请求页面的支持。
    private static final int code416 = 416;// 请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
    private static final int code417 = 417;// 未满足期望值） 服务器未满足"期望"请求标头字段的要求。

    //    五、5xx（服务器错误）
//    这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
    private static final int code500 = 500;// 服务器内部错误） 服务器遇到错误，无法完成请求。
    private static final int code501 = 501;// 尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
    private static final int code502 = 502;// 错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
    private static final int code503 = 503;// 服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
    private static final int code504 = 504;// 网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
    private static final int code505 = 505;// HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。


    public static String getException(int code) {
        String msg = "未知错误";
        switch (code) {
            case FAIL:
                msg = "网络连接异常，请检查网络连接";
                break;
            case UNAUTHORIZED:
                msg = "token失效，请重新登录";
                break;
            case FORBIDDEN:
                msg = "服务器拒绝请求";
                break;
            case NOT_FOUND:
                msg = "服务器找不到请求";
                break;
            case REQUEST_TIMEOUT:
                msg = "请求超时";
                break;
            case GATEWAY_TIMEOUT:
                msg = "服务器作为网关或代理，但是没有及时从上游服务器收到请求";
                break;
            case INTERNAL_SERVER_ERROR:
                msg = "服务器遇到错误，无法完成请求";
                break;
            case BAD_GATEWAY:
                msg = "作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应";
                break;
            case SERVICE_UNAVAILABLE:
                msg = "由于临时的服务器维护或者过载，服务器当前无法处理请求";
                break;
            case GSON_ERROR:
                msg = "数据解析失败";
                break;
            case UserNOTexit:
                msg = "用户不存在";
                break;

            default:
                msg = "网络错误";  //其它均视为网络错误
                break;
        }
        return msg;
//             case code100:
//                 ex.setMessage("请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分"); break;
//             case code101:
//                 ex.setMessage("（切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换"); break;
//             case code200:
//                 ex.setMessage("服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页"); break;
//             case code201:
//                 ex.setMessage("请求成功并且服务器创建了新的资源"); break;
//             case code202:
//                 ex.setMessage("服务器已接受请求，但尚未处理"); break;
//             case code203:
//                 ex.setMessage("服务器已成功处理了请求，但返回的信息可能来自另一来源"); break;
//             case code204:
//                 ex.setMessage("服务器成功处理了请求，但没有返回任何内容"); break;
//             case code205:
//                 ex.setMessage("服务器成功处理了请求，但没有返回任何内容"); break;
//             case code206:
//                 ex.setMessage("服务器成功处理了部分 GET 请求"); break;
//             case code300:
//                 ex.setMessage("针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择"); break;
//             case code301:
//                 ex.setMessage("请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置"); break;
//             case code302:
//                 ex.setMessage("服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"); break;
//             case code303:
//                 ex.setMessage("请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码"); break;
//             case code304:
//                 ex.setMessage("自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容"); break;
//             case code305:
//                 ex.setMessage("请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理"); break;
//             case code307:
//                 ex.setMessage("服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"); break;
//             case code400:
//                 ex.setMessage("服务器不理解请求的语法"); break;
//             case code401:
//                 ex.setMessage("请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应"); break;
//             case code403:
//                 ex.setMessage("服务器拒绝请求"); break;
//             case code404:
//                 ex.setMessage("服务器找不到请求的网页"); break;
//             case code405:
//                 ex.setMessage("禁用请求中指定的方法"); break;
//             case code406:
//                 ex.setMessage("无法使用请求的内容特性响应请求的网页"); break;
//             case code407:
//                 ex.setMessage("此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理"); break;
//             case code408:
//                 ex.setMessage("服务器等候请求时发生超时"); break;
//             case code409:
//                 ex.setMessage("服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息"); break;
//             case code410:
//                 ex.setMessage("如果请求的资源已永久删除，服务器就会返回此响应"); break;
//             case code411:
//                 ex.setMessage("服务器不接受不含有效内容长度标头字段的请求"); break;
//             case code412:
//                 ex.setMessage("服务器未满足请求者在请求中设置的其中一个前提条件"); break;
//             case code413:
//                 ex.setMessage("服务器无法处理请求，因为请求实体过大，超出服务器的处理能力"); break;
//             case code414:
//                 ex.setMessage("请求的 URI（通常为网址）过长，服务器无法处理"); break;
//             case code415:
//                 ex.setMessage("请求的格式不受请求页面的支持"); break;
//             case code416:
//                 ex.setMessage("如果页面无法提供请求的范围，则服务器会返回此状态代码"); break;
//             case code417:
//                 ex.setMessage("服务器未满足\"期望\"请求标头字段的要求"); break;
//             case code500:
//                 ex.setMessage("服务器遇到错误，无法完成请求"); break;
//             case code501:
//                 ex.setMessage("服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码"); break;
//             case code502:
//                 ex.setMessage("服务器作为网关或代理，从上游服务器收到无效响应"); break;
//             case code503:
//                 ex.setMessage("服务器目前无法使用（由于超载或停机维护）通常，这只是暂时状态"); break;
//             case code504:
//                 ex.setMessage("服务器作为网关或代理，但是没有及时从上游服务器收到请求"); break;
//             case code505:
//                 ex.setMessage("服务器不支持请求中所用的 HTTP 协议版本"); break;

    }

}

