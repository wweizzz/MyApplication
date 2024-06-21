package com.example.william.my.basic.basic_module.router.path

/**
 * ARouter
 * https://github.com/alibaba/ARouter
 * 一般以模块名称作为一级目录，Activity名称作为二级目录。当分不同包时，前两个要不一样。
 */
object RouterPath {

    const val Module_Main = "/module/main"

    const val PERMISSION_LOGIN = 1; // 登录才能显示的页面

    object Service {
        private const val Service = "/Service"

        const val FileIOUtilsService = "${Service}/FileIOUtilsService"
        const val ImageUtilsService = "${Service}/ImageUtilsService"
        const val ResourceUtilsService = "${Service}/ResourceUtilsService"
    }

    object Fragment {
        private const val Fragment = "/Fragment"

        const val FragmentPrimary = "${Fragment}/fragment/primary"
        const val FragmentPrimaryDark = "${Fragment}/fragment/primary_dark"
        const val FragmentBasicRecycler = "${Fragment}/fragment/basic_recycler"
    }

    object Demo {
        private const val Demo = "/Demo"

        const val Main = "${Demo}/Main"

        const val Appbar = "${Demo}/Appbar"
        const val Dialog = "${Demo}/Dialog"
        const val FlexBox = "${Demo}/FlexBox"
        const val Fragment1 = "${Demo}/Fragment1"
        const val Fragment2 = "${Demo}/Fragment2"
        const val FragmentTabHost = "${Demo}/FragmentTabHost"
        const val RecyclerView = "${Demo}/RecyclerView"
        const val ViewFlipper = "${Demo}/ViewFlipper"
        const val ViewPager = "${Demo}/ViewPager"
        const val ViewPager2 = "${Demo}/ViewPager2"
        const val WebView = "${Demo}/WebView"

        const val Animator = "${Demo}/Animator"
        const val Notification = "${Demo}/Notification"
        const val Permission = "${Demo}/Permission"
        const val Transition = "${Demo}/Transition"
        const val Typeface = "${Demo}/Typeface"

        const val Crop = "${Demo}/Crop"
        const val FloatWindow = "${Demo}/FloatWindow"
        const val Hook = "${Demo}/Hook"
        const val Turntable = "${Demo}/Turntable"

        const val AsyncTask = "${Demo}/AsyncTask"
        const val Broadcast = "${Demo}/Broadcast"
        const val HandlerThread = "${Demo}/HandlerThread"
        const val JobScheduler = "${Demo}/JobScheduler"
        const val Messenger = "${Demo}/Messenger"
        const val Service = "${Demo}/Service"
    }

    object Widget {
        private const val Widget = "/Widget"

        const val Main = "${Widget}/Main"

        const val AlertDialog = "${Widget}/AlertDialog"
        const val BlurView = "${Widget}/BlurView"
        const val InfiniteImage = "${Widget}/InfiniteImage"
        const val MarqueeView = "${Widget}/MarqueeView"
        const val Sensor3DView = "${Widget}/Sensor3DView"
        const val Spinner = "${Widget}/Spinner"
        const val TitleBar = "${Widget}/TitleBar"
        const val VerifyCode = "${Widget}/VerifyCode"

        const val FragmentViewPager = "${Widget}/FragmentViewPager"
        const val RecyclerViewNested = "${Widget}/RecyclerViewNested"
    }

    object Libraries {
        private const val Libraries = "/Libraries"

        const val Main = "${Libraries}/Main"
        const val EventBus = "${Libraries}/event_bus"
        const val RxEventBus = "${Libraries}/rx_event_bus"
        const val LiveEventBus = "${Libraries}/live_event_bus"
        const val FlowEventBus = "${Libraries}/flow_event_bus"
        const val NinePatch = "${Libraries}/nine_patch"
    }

    object Opensource {
        private const val Opensource = "/Opensource"

        const val Main = "${Opensource}/Main"
        const val Banner = "${Opensource}/Banner"
        const val CityPicker = "${Opensource}/CityPicker"
        const val CountdownView = "${Opensource}/CountdownView"
        const val EasyFloat = "${Opensource}/EasyFloat"
        const val FlycoTabLayout = "${Opensource}/FlycoTabLayout"
        const val PhotoView = "${Opensource}/PhotoView"
        const val PickerView = "${Opensource}/PickerView"
        const val PictureSelector = "${Opensource}/PictureSelector"
        const val PopWindow = "${Opensource}/PopWindow"
        const val ShadowLayout = "${Opensource}/ShadowLayout"
        const val SwipeLayout = "${Opensource}/SwipeLayout"

        const val Pag = "${Opensource}/Pag"
        const val Lottie = "${Opensource}/Lottie"
        const val SVGAPlayer = "${Opensource}/SVGAPlayer"

        const val MMKV = "${Opensource}/MMKV"
        const val PermissionX = "${Opensource}/PermissionX"
    }

    object Database {
        private const val Database = "/Database"

        const val Main = "${Database}/Main"
        const val GreenDao = "${Database}/GreenDao"
        const val ObjectBox = "${Database}/ObjectBox"
    }

    object Utils {
        private const val Utils = "/Utils"

        const val Main = "${Utils}/Main"

        const val AdaptScreenUtils = "${Utils}/AdaptScreenUtils"
        const val FileIOUtils = "${Utils}/FileIOUtils"
        const val PermissionUtils = "${Utils}/PermissionUtils"
    }

    object Network {
        private const val Network = "/Network"

        const val Main = "${Network}/Main"

        const val Coil = "${Network}/Coil"

        const val HttpURL = "${Network}/HttpURL"

        const val Volley = "${Network}/Volley"
        const val VolleyHelper = "${Network}/VolleyHelper"

        const val OkHttp = "${Network}/OkHttp"
        const val OkHttpHelper = "${Network}/OkHttpHelper"

        const val Retrofit = "${Network}/Retrofit"
        const val RetrofitHelper = "${Network}/RetrofitHelper"

        const val RetrofitRxJava = "${Network}/RetrofitRxJava"
        const val RetrofitRxJavaHelper = "${Network}/RetrofitRxJavaHelper"

        const val RxRetrofit = "${Network}/RxRetrofit"

        const val OkHttpUpload = "${Network}/OkHttpUpload"
        const val OkHttpDownload = "${Network}/OkHttpDownload"

        const val RetrofitUpload = "${Network}/RetrofitUpload"
        const val RetrofitDownload = "${Network}/RetrofitDownload"

        const val RxUpload = "${Network}/RxUpload"
        const val RxDownload = "${Network}/RxDownload"

        const val WebSocket = "${Network}/WebSocket"
        const val WebSocketUtils = "${Network}/WebSocketUtils"

        const val Nano = "${Network}/Nano"
        const val Netty = "${Network}/Netty"
        const val Socket = "${Network}/Socket"
    }

    object Sample {
        private const val Sample = "/Sample"

        const val Main = "${Sample}/Main"

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

        const val Main = "${Arch}/Main"

        const val MVP = "${Arch}/MVP"
        const val MVVM = "${Arch}/MVVM"
        const val MVI = "${Arch}/MVI"

        const val Counter = "${Arch}/Counter"
        const val Mavericks = "${Arch}/Mavericks"
    }

    object Room {
        private const val R = "/Room"

        const val Main = "${R}/Main"

        const val Room = "${R}/Room"
        const val Paging = "${R}/Paging"

        const val ViewModel = "${R}/ViewModel"
    }

    object Hilt {
        private const val H = "/Hilt"

        const val Main = "${H}/Main"
    }

    object Compose {
        private const val Compose = "/Compose"

        const val Main = "${Compose}/Main"

        const val Text = "${Compose}/Text"
        const val Button = "${Compose}/Button"
        const val Image = "${Compose}/Image"
        const val Canvas = "${Compose}/Canvas"

        const val ConstraintLayout = "${Compose}/ConstraintLayout"
        const val HorizontalPager = "${Compose}/HorizontalPager"

        const val BackHandler = "${Compose}/BackHandler"

        const val CompositionLocal = "${Compose}/CompositionLocal"

        const val Draggable = "${Compose}/Draggable"
        const val DragGestures = "${Compose}/DragGestures"

        const val GuaguaCard = "${Compose}/GuaguaCard"

        const val NavHost = "${Compose}/NavHost"

        const val BottomNavigation = "${Compose}/BottomNavigation"
        const val NavigationBar = "${Compose}/NavigationBar"

        const val Remember = "${Compose}/Remember"

        const val AnchoredDraggable = "${Compose}/AnchoredDraggable"

        const val SmartRefresh = "${Compose}/SmartRefresh"

        const val ScrollableTab = "${Compose}/ScrollableTab"
    }

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