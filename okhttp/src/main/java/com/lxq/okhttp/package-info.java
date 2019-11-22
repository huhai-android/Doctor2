/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
//        //持久化存储cookie
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
//
//        //log拦截器
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        //自定义OkHttp
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .cookieJar(cookieJar)       //设置开启cookie
//                .addInterceptor(logging)            //设置开启log
//                .build();
//        mMyOkHttp = new MyOkHttp(okHttpClient);
//
//        //默认
////        mMyOkHttp = new MyOkHttp();
//
//        mDownloadMgr = (DownloadMgr) new DownloadMgr.Builder()
//                .myOkHttp(mMyOkHttp)
//                .maxDownloadIngNum(5)       //设置最大同时下载数量（不设置默认5）
//                .saveProgressBytes(50 * 1204)   //设置每50kb触发一次saveProgress保存进度 （不能在onProgress每次都保存 过于频繁） 不设置默认50kb
//                .build();
//
//        mDownloadMgr.resumeTasks();     //恢复本地所有未完成的任务
/**
 * 模型类所在包
 */
/**
 * @author Lemon
 * @use 通用使用方法：
 *      XXBean xxb = new XXBean(...);
 *      xxb.setXX(...);
 *      xxb.getXX();
 */
package com.lxq.okhttp;

//    /**
//     * POST请求 + Json返回
//     */
//    private void doPost() {
//        String url = "http://192.168.2.135/myokhttp/post.php";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("name", "tsy");
//        params.put("age", "24");
//
//        mMyOkhttp.post()
//                .url(url)
//                .params(params)
//                .tag(this)
//                .enqueue(new JsonResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, JSONObject response) {
//                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, JSONArray response) {
//                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doPost onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * POST请求Json参数 + Json返回
//     */
//    private void doPostJSON() {
//        String url = "http://192.168.2.135/myokhttp/post_json.php";
//
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("name", "tsy");
//            jsonObject.put("age", 24);
//            jsonObject.put("type", "json");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        mMyOkhttp.post()
//                .url(url)
//                .jsonParams(jsonObject.toString())          //与params不共存 以jsonParams优先
//                .tag(this)
//                .enqueue(new JsonResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, JSONObject response) {
//                        Log.d(TAG, "doPostJSON onSuccess JSONObject:" + response);
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, JSONArray response) {
//                        Log.d(TAG, "doPostJSON onSuccess JSONArray:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doPostJSON onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * GET请求 + Raw String返回
//     */
//    private void doGet() {
//        String url = "http://192.168.2.135/myokhttp/get.php";
//
//        mMyOkhttp.get()
//                .url(url)
//                .addParam("name", "tsy")
//                .addParam("id", "5")
//                .tag(this)
//                .enqueue(new RawResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String response) {
//                        Log.d(TAG, "doGet onSuccess:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doGet onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * PUT请求 + Json返回
//     */
//    private void doPut() {
//        String url = "http://192.168.2.135/myokhttp/put.php/id/5/name/tsy/age/15";
//
//        mMyOkhttp.put()
//                .url(url)
//                .tag(this)
//                .enqueue(new JsonResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, JSONObject response) {
//                        Log.d(TAG, "doPut onSuccess:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doPut onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * PATCH请求 + Json返回
//     */
//    private void doPatch() {
//        String url = "http://192.168.2.135/myokhttp/patch.php/id/5/name/tsy";
//
//        mMyOkhttp.patch()
//                .url(url)
//                .tag(this)
//                .enqueue(new JsonResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, JSONObject response) {
//                        Log.d(TAG, "doPatch onSuccess:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doPatch onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * DELETE请求 + Json返回
//     */
//    private void doDelete() {
//        String url = "http://192.168.2.135/myokhttp/delete.php/id/5";
//
//        mMyOkhttp.delete()
//                .url(url)
//                .tag(this)
//                .enqueue(new JsonResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, JSONObject response) {
//                        Log.d(TAG, "doDelete onSuccess:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doDelete onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * UPLOAD + Gson返回
//     */
//    private void doUpload() {
//        String url = "http://192.168.1.144:8080/upload.php";
//
//        mMyOkhttp.upload()
//                .url(url)
//                .addParam("name", "tsy")
//                .addFile("avatar",
//                        new File(getFilesDir()
//                                + "/local20180124083154.png"))        //上传已经存在的File
////                .addFile("avatar2", "asdsda.png", byteContents)    //直接上传File bytes
//                .tag(this)
//                .enqueue(new GsonResponseHandler<UploadModel>() {
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doUpload onFailure:" + error_msg);
//                    }
//
//                    @Override
//                    public void onProgress(long currentBytes, long totalBytes) {
//                        Log.d(TAG, "doUpload onProgress:" + currentBytes + "/" + totalBytes);
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, UploadModel response) {
//                        Log.d(TAG, "doUpload onSuccess:" + response.ret + " " + response.msg);
//                    }
//                });
//    }
//
//    /**
//     * Download
//     */
//    private void doDownload() {
//        String url = "http://192.168.2.135/myokhttp/head.jpg";
//
//        mMyOkhttp.download()
//                .url(url)
//                .filePath(Environment.getExternalStorageDirectory() + "/ahome/a.jpg")
//                .tag(this)
//                .enqueue(new DownloadResponseHandler() {
//                    @Override
//                    public void onStart(long totalBytes) {
//                        Log.d(TAG, "doDownload onStart");
//                    }
//
//                    @Override
//                    public void onFinish(File downloadFile) {
//                        Log.d(TAG, "doDownload onFinish:");
//                    }
//
//                    @Override
//                    public void onProgress(long currentBytes, long totalBytes) {
//                        Log.d(TAG, "doDownload onProgress:" + currentBytes + "/" + totalBytes);
//                    }
//
//                    @Override
//                    public void onFailure(String error_msg) {
//                        Log.d(TAG, "doDownload onFailure:" + error_msg);
//                    }
//                });
//    }
//
//    /**
//     * cookie测试
//     *
//     * php代码：
//     * if(empty($_COOKIE['mycookie'])) {
//     *      setcookie('mycookie','value', time()+20);
//     *      die("no cookie 'mycookie', set 'mycookie' => 'value'");
//     * }
//     * die("has cookie 'mycookie' => " . $_COOKIE['mycookie']);
//     *
//     */
//    private void doCookie() {
//        String url = "http://192.168.2.135/myokhttp/cookie.php";
//
//        mMyOkhttp.post()
//                .url(url)
//                .tag(this)
//                .enqueue(new RawResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String response) {
//                        Log.d(TAG, "doCookie onSuccess:" + response);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//                        Log.d(TAG, "doCookie onFailure:" + error_msg);
//                    }
//                });
//    }
