package com.example.william.my.basic.basic_module.router.path

/**
 * ARouter
 * https://github.com/alibaba/ARouter
 * 一般以模块名称作为一级目录，Activity名称作为二级目录。当分不同包时，前两个要不一样。
 */
object ARouterPath {

    const val Module_Main = "/module/main"

    object Service {
        const val FileIOUtilsService = "/service/file_io"
        const val ImageUtilsService = "/service/image"
        const val ResourceUtilsService = "/service/resource"
    }

    object Fragment {
        const val FragmentPrimary = "/base/fragment/primary"
        const val FragmentPrimaryDark = "/base/fragment/primary_dark"
        const val FragmentBasicRecycler = "/base/fragment/basic_recycler"
    }

    object Utils {
        const val Main = "/module_utils/utils_main"
        const val FileIOUtils = "/module_utils/FileIOUtils"
        const val PermissionUtils = "/module_utils/PermissionUtils"
    }

    object Demo {
        const val Main = "/module_demo/demo_main"
        const val Appbar = "/module_demo/appbar"
        const val Dialog = "/module_demo/dialog"
        const val FlexBox = "/module_demo/flexbox"
        const val Fragment1 = "/module_demo/fragment1"
        const val Fragment2 = "/module_demo/fragment2"
        const val FragmentTabHost = "/module_demo/fragment_tabhost"
        const val RecyclerView = "/module_demo/recyclerview"
        const val ShapeableView = "/module_demo/shapeableview"
        const val ViewFlipper = "/module_demo/viewflipper"
        const val ViewPager = "/module_demo/viewpager"
        const val ViewPager2 = "/module_demo/viewpager2"
        const val WebView = "/module_demo/webview"

        const val FragmentViewPager = "/module_demo/fragment_viewpager"
        const val RecyclerViewNested = "/module_demo/recyclerview_nested"

        const val AsyncTask = "/module_demo/async_task"
        const val Broadcast = "/module_demo/broadcast"
        const val HandlerThread = "/module_demo/handler_thread"
        const val JobScheduler = "/module_demo/job_scheduler"
        const val Messenger = "/module_demo/messenger"
        const val Notification = "/module_demo/notification"
        const val Permission = "/module_demo/permission"
        const val Service = "/module_demo/service"

        const val Animator = "/module_demo/animator"
        const val FloatWindow = "/module_demo/float_window"
        const val Hook = "/module_demo/hook"
        const val Crop = "/module_demo/pic_crop"
        const val TransitionAnim = "/module_demo/transition_anim"
        const val Turntable = "/module_demo/turntable"
        const val Typeface = "/module_demo/typeface"

        const val AlertDialog = "/module_widget/alert_dialog"
        const val BlurView = "/module_widget/blur_view"
        const val InfiniteImage = "/module_widget/infinite_image"
        const val MarqueeView = "/module_widget/marquee_View"
        const val Sensor3DView = "/module_widget/sensor_3d"
        const val Spinner = "/module_widget/spinner"
        const val TitleBar = "/module_widget/title_bar"
        const val VerifyCode = "/module_widget/verify_code"
    }

    object Libraries {
        const val Main = "/module_libs/libs_main"
        const val EventBus = "/module_libs/event_bus"
        const val NinePatch = "/module_libs/nine_patch"
        const val KeyValue = "/module_libs/key_value"
    }

    object Network {
        const val Main = "/module_net/net_main"
        const val Coil = "/module_net/coil"
        const val Glide = "/module_net/Glide"
        const val HttpURL = "/module_net/http_url"
        const val Volley = "/module_net/volley"
        const val VolleyHelper = "/module_net/volley_helper"
        const val OkHttp = "/module_net/okhttp"
        const val OkHttpHelper = "/module_net/okhttp_helper"
        const val OkHttpDownload = "/module_net/okhttp_download"
        const val Retrofit = "/module_net/retrofit"
        const val RetrofitHelper = "/module_net/retrofit_helper"
        const val RetrofitDownload = "/module_net/retrofit_download"
        const val RetrofitRxJava = "/module_net/retrofit_rx_java"
        const val RetrofitRxJavaHelper = "/module_net/retrofit_rx_java_helper"
        const val RetrofitRxJavaDownload = "/module_net/retrofit_rx_java_download"
        const val RxRetrofit = "/module_net/retrofit_rx_retrofit"
        const val RxDownload = "/module_net/retrofit_rx_download"
        const val WebSocket = "/module_net/websocket"
        const val WebSocketUtils = "/module_net/websocket_utils"
        const val Nano = "/module_net/nano"
        const val Netty = "/module_net/netty"
        const val Socket = "/module_net/socketserver"
    }

    object Opensource {
        const val Main = "/module_opensource/opensource_main"
        const val BadgeView = "/module_opensource/BadgeView"
        const val Banner = "/module_opensource/Banner"
        const val CityPicker = "/module_opensource/CityPicker"
        const val CountdownView = "/module_opensource/CountdownView"
        const val EasyFloat = "/module_opensource/EasyFloat"
        const val FlycoTabLayout = "/module_opensource/FlycoTabLayout"
        const val Lottie = "/module_opensource/Lottie"
        const val PhotoView = "/module_opensource/PhotoView"
        const val PickerView = "/module_opensource/PickerView"
        const val PopWindow = "/module_opensource/PopWindow"
        const val ShadowLayout = "/module_opensource/ShadowLayout"
        const val SVGAPlayer = "/module_opensource/SVGAPlayer"
        const val SwipeLayout = "/module_opensource/SwipeLayout"

        const val MMKV = "/module_opensource/MMKV"
        const val GreenDao = "/module_opensource/GreenDao"
        const val ObjectBox = "/module_opensource/ObjectBox"
    }

    object Sample {
        const val Main = "/module_sample/sample_main"

        const val Room = "/module_sample/Room"

        const val ViewModel = "/module_sample/ViewModel"
        const val Paging = "/module_sample/Paging"
        const val DataStore = "/module_sample/DataStore"
        const val WorkManager = "/module_sample/WorkManager"

        const val Coroutines = "/module_sample/Coroutines"
        const val Flow = "/module_sample/Flow"

        const val ActivityResultContract = "/module_sample/ActivityResultContract"
        const val OnBackPressedDispatcher = "/module_sample/OnBackPressedDispatcher"
    }

    object Arch {
        const val Main = "/module_arch/arch_main"
        const val MVP = "/module_arch/MVP"
        const val MVVM = "/module_arch/MVVM"
        const val MVI = "/module_arch/MVI"
        const val Counter = "/module_arch/Counter"
        const val Mavericks = "/module_arch/Mavericks"
    }

    object Flutter {
        const val Main = "/module_flutter/flutter_main"
    }
}