package com.mensa.homecare.customer.local


object Constants {
    // Number
    const val SHOW_MAP = 10
    const val CHOOSE_RECIPIENT = 11
    const val CHOOSE_CART = 12
    const val SHOW_CART = 13
    const val SHOW_HISTORY = 14
    const val SHOW_PAYMENT = 15
    const val OPEN_MANUFACTURE = 16
    const val OPEN_MIDTRANS_PDF = 17
    const val PERMISSION_CAMERA = 18
    const val PERMISSION_STORAGE = 19
    const val EDIT_ADDRESS = 20
    const val SEARCH_LOCATION = 21
    const val SHOW_OTP = 22

    const val INITIAL_REQUEST = 110
    const val CALL_PHONE_PERMISSION_REQUEST_CODE = 111
    const val LOCATION_PERMISSION_REQUEST_CODE = 112

    // String
    const val APP_KEY = "EEoraAQ99O6muxojNAYlsj9eTx6ol2ws"

    // API RESPONSE
    const val STATUS = "Status"
    const val MESSAGE = "Message"

    // WEB VIEW
    const val WEB_TITLE = "title"
    const val WEB_URL = "url"

    // ORDER
    const val CART_TYPE = "cart_type"
    const val MBS = "MBS"
    const val NON_MBS = "OTHERS"
    const val INDOFOOD = "INDOFOOD"
    const val PHARMACY = "PHARMACY"
    const val PAYMENT_DATA = "payment_data"
    const val MANUFACTURER_ID = "manufacturer_id"


    // PROMO
    const val DESCRIPTION = "description"
    const val IMAGE_URL = "image_url"

    // PAYMENT
    const val COD = "cash"
    const val TENOR = "tenor"
    const val TRANSFER = "midtrans"
    const val BANK_PERMATA = "permata_va"
    const val BANK_BNI = "bni_va"
    const val BANK_BCA = "bca_va"
    const val BANK_MANDIRI = "echannel"
    const val PDF_URL = "pdf_url"

    // SEARCH LOCATION
    const val ADDRESS = "address"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"

    // SEARCH RECIPIENT / CUSTOMER / MIDWIVES
    const val MIDWIVES_NAME = "widwives_name"


    // Repository
    const val SUCCESS_MESSAGE = "success"
    const val ERROR_STATUS = 0
    const val ERROR_MESSAGE = "something when wrong"

    // OTHER
    const val HALODOC = "HALODOC"
    const val POSITION = "position"
    const val SUCCESS = "success"
    const val TOOLBAR_TITLE = "title"
    const val PHONE_NUMBER = "phone_number"
    const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
    const val JSON_ERROR = "json"
    const val ETHICAL = "3"
    const val NSR = 8
    const val PLAY_STORE_URL =
        "https://play.google.com/store/apps/details?id=com.mensa.apotikantarb2b"

    // required for firebase test lab
    const val IS_FIREBASE_TEST_LAB = false
    const val TEST_DEVICE_ID = "c619fdaed1282dae"
    const val TEST_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6NDAsInNhbHQiOiJVc3ZFek1hdFRyMXpyUHN6IiwiYXBwX2lkIjoibXVBQ3RGWG9UWWM2czJPQWpXRHNSVEpFaTZLSVdFcmEiLCJkZXZpY2VfaWQiOiJjNjE5ZmRhZWQxMjgyZGFlIn0.y0uE50p_P_ikQB3pjfs1q4iWEt40s3SIEnLaspxnLKE"
    const val TEST_ADDRESS = "Halodoc"
    const val TEST_LATITUDE = -6.214163
    const val TEST_LONGITUDE = 106.829535
    const val TEST_LOG_OUT = "Logout"

    val PRIVATE_MODE = 0
    val PREF_NAME = "homecare-pref"

    val format_time = "HH:mm"
    val format_date = "dd MMM yyyy"
    val format_date_save = "yyyy-MM-dd"
    val format_time_save = "HH:mm:ss"
    val format_raw = "yyyy-MM-dd HH:mm:ss"

    val ACCEPT = "approve"
    val REJECT = "reject"
    val RESCHEDULE = "reschedule"

    //ROLE
    val ADMIN = 1
    val DOCTOR = 2
    val MIDWIVE = 3
    val NURSE = 4
    val PATIENT = 5

    //Status Mapping
    val TERBENTUK = "create"
    val PROCESSED = "process"
    val WAITING = "approval"
    val REASSIGN = "reassign"
    val APPROVED = "all_approve"
    val DONE = "finish"

    val TODO = "todo"
    val dev_hash = "fcfYeZVySlO"


    val save_tag = "save"
    val finish_tag = "finish"

    val schedule = "1"
    val history = "2"
    val isPIC = "1"

    val OTP_INTENT = "com.mensa.homecare.sms.otp"
    val OTP_EXTRA = "com.mensa.homecare.sms"

    val STAT_All = "all"
    val STAT_APPROVED = "approve"
    val STAT_NOTYET = "todo"

    val time_out: Long = 120
    val retry = 3

    val MODE_EDIT = 1
    val MODE_CANCEL = 2
    val MODE_DISPLAY = 3

    val minDateCurrent = (System.currentTimeMillis() - 1000)

    val male = "m"
    val female = "f"
}
