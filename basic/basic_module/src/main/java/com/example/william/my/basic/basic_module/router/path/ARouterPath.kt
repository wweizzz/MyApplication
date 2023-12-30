package com.example.william.my.basic.basic_module.router.path

/**
 * ARouter
 * https://github.com/alibaba/ARouter
 * 一般以模块名称作为一级目录，Activity名称作为二级目录。当分不同包时，前两个要不一样。
 */
object ARouterPath {

    const val Module_Main = "/module/main"

    object Service {
        private const val Service = "/Service"

        const val FileIOUtilsService = "${Service}/FileIOUtilsService"
        const val ImageUtilsService = "${Service}/ImageUtilsService"
        const val ResourceUtilsService = "${Service}/ResourceUtilsService"
    }

    object Fragment {
        private const val Fragment = "/Fragment"

        const val FragmentPrimary = "/base/fragment/primary"
        const val FragmentPrimaryDark = "/base/fragment/primary_dark"
        const val FragmentBasicRecycler = "/base/fragment/basic_recycler"
    }

    object Demo {
        private const val Demo = "/Demo"

        const val Main = "${Demo}/demo_main"
        const val Appbar = "${Demo}/appbar"
        const val Dialog = "${Demo}/dialog"
        const val FlexBox = "${Demo}/flexbox"
        const val Fragment1 = "${Demo}/fragment1"
        const val Fragment2 = "${Demo}/fragment2"
        const val FragmentTabHost = "${Demo}/fragment_tab_host"
        const val RecyclerView = "${Demo}/recycler_view"
        const val ViewFlipper = "${Demo}/view_flipper"
        const val ViewPager = "${Demo}/viewpager"
        const val ViewPager2 = "${Demo}/viewpager2"
        const val WebView = "${Demo}/web_view"

        const val Animator = "${Demo}/animator"
        const val Notification = "${Demo}/notification"
        const val Permission = "${Demo}/permission"
        const val Transition = "${Demo}/transition"
        const val Typeface = "${Demo}/typeface"

        const val Crop = "${Demo}/pic_crop"
        const val FloatWindow = "${Demo}/float_window"
        const val Hook = "${Demo}/hook"
        const val Turntable = "${Demo}/turntable"

        const val AsyncTask = "${Demo}/async_task"
        const val Broadcast = "${Demo}/broadcast"
        const val HandlerThread = "${Demo}/handler_thread"
        const val JobScheduler = "${Demo}/job_scheduler"
        const val Messenger = "${Demo}/messenger"
        const val Service = "${Demo}/service"
    }

    object Widget {
        private const val Widget = "/Widget"

        const val Main = "${Widget}/libs_main"
        const val AlertDialog = "${Widget}/alert_dialog"
        const val BlurView = "${Widget}/blur_view"
        const val InfiniteImage = "${Widget}/infinite_image"
        const val MarqueeView = "${Widget}/marquee_View"
        const val Sensor3DView = "${Widget}/sensor_3d"
        const val Spinner = "${Widget}/spinner"
        const val TitleBar = "${Widget}/title_bar"
        const val VerifyCode = "${Widget}/verify_code"

        const val FragmentViewPager = "${Widget}/fragment_viewpager"
        const val RecyclerViewNested = "${Widget}/recyclerview_nested"
    }

    object Libraries {
        private const val Libraries = "/Libraries"

        const val Main = "${Libraries}/Main"
        const val EventBus = "${Libraries}/event_bus"
        const val NinePatch = "${Libraries}/nine_patch"
        const val KeyValue = "${Libraries}/key_value"
    }

    object Utils {
        private const val Utils = "/Utils"

        const val Main = "${Utils}/utils_main"
        const val AdaptScreenUtils = "${Utils}/AdaptScreenUtils"
        const val FileIOUtils = "${Utils}/FileIOUtils"
        const val PermissionUtils = "${Utils}/PermissionUtils"
    }

    object Opensource {
        private const val Opensource = "/Opensource"

        const val Main = "${Opensource}/Main"
        const val Badge = "${Opensource}/Badge"
        const val Banner = "${Opensource}/Banner"
        const val CityPicker = "${Opensource}/CityPicker"
        const val CountdownView = "${Opensource}/CountdownView"
        const val EasyFloat = "${Opensource}/EasyFloat"
        const val FlycoTabLayout = "${Opensource}/FlycoTabLayout"
        const val Lottie = "${Opensource}/Lottie"
        const val PhotoView = "${Opensource}/PhotoView"
        const val PickerView = "${Opensource}/PickerView"
        const val PopWindow = "${Opensource}/PopWindow"
        const val ShadowLayout = "${Opensource}/ShadowLayout"
        const val SVGAPlayer = "${Opensource}/SVGAPlayer"
        const val SwipeLayout = "${Opensource}/SwipeLayout"

        const val MMKV = "${Opensource}/MMKV"
        const val PermissionX = "${Opensource}/PermissionX"

        const val OAID = "${Opensource}/oaid"
    }

    object Network {
        private const val Network = "/Network"

        const val Main = "${Network}/net_main"
        const val Coil = "${Network}/coil"
        const val HttpURL = "${Network}/http_url"
        const val Volley = "${Network}/volley"
        const val VolleyHelper = "${Network}/volley_helper"
        const val OkHttp = "${Network}/okhttp"
        const val OkHttpHelper = "${Network}/okhttp_helper"
        const val OkHttpDownload = "${Network}/okhttp_download"
        const val Retrofit = "${Network}/retrofit"
        const val RetrofitHelper = "${Network}/retrofit_helper"
        const val RetrofitDownload = "${Network}/retrofit_download"
        const val RetrofitRxJava = "${Network}/retrofit_rx_java"
        const val RetrofitRxJavaHelper = "${Network}/retrofit_rx_java_helper"
        const val RetrofitRxJavaDownload = "${Network}/retrofit_rx_java_download"
        const val RxRetrofit = "${Network}/retrofit_rx_retrofit"
        const val RxDownload = "${Network}/retrofit_rx_download"
        const val WebSocket = "${Network}/websocket"
        const val WebSocketUtils = "${Network}/websocket_utils"
        const val Nano = "${Network}/nano"
        const val Netty = "${Network}/netty"
        const val Socket = "${Network}/socketserver"
    }

    object Sample {
        private const val Sample = "/Sample"

        const val Main = "${Sample}/sample_main"

        const val DataStore = "${Sample}/DataStore"
        const val ViewModel = "${Sample}/ViewModel"
        const val WorkManager = "${Sample}/WorkManager"

        const val Coroutines = "${Sample}/Coroutines"
        const val Flow = "${Sample}/Flow"

        const val ActivityResultContract = "${Sample}/ActivityResultContract"
        const val OnBackPressedDispatcher = "${Sample}/OnBackPressedDispatcher"
    }

    object Arch {
        private const val Arch = "/Arch"

        const val Main = "${Arch}/arch_main"
        const val MVP = "${Arch}/MVP"
        const val MVVM = "${Arch}/MVVM"
        const val MVI = "${Arch}/MVI"
        const val Counter = "${Arch}/Counter"
        const val Mavericks = "${Arch}/Mavericks"
    }

//    object Room {
//        private const val R = "/Room"
//
//        const val Room = "${R}/Room"
//        const val Paging = "${R}/Paging"
//        const val ViewModel = "${R}/ViewModel"
//    }
//
//    object Hilt {
//        private const val H = "/Hilt"
//
//        const val Hilt = "${H}/Hilt"
//    }
//
//    object Database {
//        private const val Database = "/Database"
//
//        const val GreenDao = "${Database}/GreenDao"
//        const val ObjectBox = "${Database}/ObjectBox"
//    }

    object Flutter {
        private const val Flutter = "/Flutter"

        const val Main = "${Flutter}/Main"
        const val Layout = "${Flutter}/Layout"
        const val Container = "${Flutter}/Container"
        const val Scroll = "${Flutter}/Scroll"
        const val Function = "${Flutter}/Function"
        const val Http = "${Flutter}/Http"
        const val State = "${Flutter}/State"
        const val Packages = "${Flutter}/Packages"
        const val Other = "${Flutter}/Other"
    }

}