package com.tasnimulhasan.common.constant

import com.tasnimulhasan.entity.ArabicAlphabet

object AppConstants {
    const val CHANNEL_ID = "quran_channel_id"
    const val CHANNEL_NAME = "Quran Notification"
    const val NOTIFICATION_REQUEST_CODE = 107

    const val DB_NAME = "quran_mobile.db"

    const val PRAYER_TIMES_BASE_URL = "https://api.aladhan.com/v1/"
    const val HADITH_BASE_URL = "https://hadithapi.com/api/"
    const val HADITH_API_KEY = $$"$2y$10$igEAXi4ASN4Z0fOZmBvOjKWKO6w0rGiSO52ub6d5YEZVNzAL7Xe"

    const val BISMILLAH = "بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ"

    const val NOTIFICATION_ID = 107
    const val NOTIFICATION_CHANNEL_NAME = "Quran Notification"
    const val NOTIFICATION_CHANNEL_ID = "quran_channel_id"

    const val FOREGROUND_ID = 122

    const val ACTION_PREVIOUS = "previous"
    const val ACTION_PLAY_PAUSE = "play_pause"
    const val ACTION_NEXT = "next"

    fun getHijriMonthName(month: Int): String {
        return when (month) {
            1 -> "Muharram"
            2 -> "Safar"
            3 -> "Rabi al-awwal"
            4 -> "Rabi al-thani"
            5 -> "Jumada al-awwal"
            6 -> "Jumada al-thani"
            7 -> "Rajab"
            8 -> "Sha'ban"
            9 -> "Ramadan"
            10 -> "Shawwal"
            11 -> "Dhu al-Qidah"
            12 -> "Dhu al-Hijjah"
            else -> "Unknown"
        }
    }

    fun getDirectionName(degree: Float): String {
        return when ((degree % 360 + 360) % 360) {
            in 337.5..360.0, in 0.0..22.5 -> "North"
            in 22.5..67.5 -> "Northeast"
            in 67.5..112.5 -> "East"
            in 112.5..157.5 -> "Southeast"
            in 157.5..202.5 -> "South"
            in 202.5..247.5 -> "Southwest"
            in 247.5..292.5 -> "West"
            in 292.5..337.5 -> "Northwest"
            else -> ""
        }
    }

    val arabicLetters = listOf(
        ArabicAlphabet("Alif", "ا", "ا", "ـا", "ـا", "a / ā"),
        ArabicAlphabet("Baa", "ب", "بـ", "ـبـ", "ـب", "b"),
        ArabicAlphabet("Taa", "ت", "تـ", "ـتـ", "ـت", "t"),
        ArabicAlphabet("Thaa", "ث", "ثـ", "ـثـ", "ـث", "th"),
        ArabicAlphabet("Jeem", "ج", "جـ", "ـجـ", "ـج", "j"),
        ArabicAlphabet("Haa", "ح", "حـ", "ـحـ", "ـح", "ḥ"),
        ArabicAlphabet("Khaa", "خ", "خـ", "ـخـ", "ـخ", "kh"),
        ArabicAlphabet("Dal", "د", "د", "ـد", "ـد", "d"),
        ArabicAlphabet("Dhal", "ذ", "ذ", "ـذ", "ـذ", "dh"),
        ArabicAlphabet("Raa", "ر", "ر", "ـر", "ـر", "r"),
        ArabicAlphabet("Zaay", "ز", "ز", "ـز", "ـز", "z"),
        ArabicAlphabet("Seen", "س", "سـ", "ـسـ", "ـس", "s"),
        ArabicAlphabet("Sheen", "ش", "شـ", "ـشـ", "ـش", "sh"),
        ArabicAlphabet("Saad", "ص", "صـ", "ـصـ", "ـص", "ṣ"),
        ArabicAlphabet("Daad", "ض", "ضـ", "ـضـ", "ـض", "ḍ"),
        ArabicAlphabet("Taa", "ط", "طـ", "ـطـ", "ـط", "ṭ"),
        ArabicAlphabet("Dhaa", "ظ", "ظـ", "ـظـ", "ـظ", "ẓ"),
        ArabicAlphabet("A\'yen", "ع", "عـ", "ـعـ", "ـع", "\'"),
        ArabicAlphabet("Ghayn", "غ", "غـ", "ـغـ", "ـغ", "gh"),
        ArabicAlphabet("Faa", "ف", "فـ", "ـفـ", "ـف", "f"),
        ArabicAlphabet("Qaaf", "ق", "قـ", "ـقـ", "ـق", "q"),
        ArabicAlphabet("Kaaf", "ك", "كـ", "ـكـ", "ـك", "k"),
        ArabicAlphabet("Laam", "ل", "لـ", "ـلـ", "ـل", "l"),
        ArabicAlphabet("Meem", "م", "مـ", "ـمـ", "ـم", "m"),
        ArabicAlphabet("Noon", "ن", "نـ", "ـنـ", "ـن", "n"),
        ArabicAlphabet("Haa\'", "ه", "هـ", "ـهـ", "ـه", "h"),
        ArabicAlphabet("Waw", "و", "و", "ـو", "ـو", "w"),
        ArabicAlphabet("Yaa\'", "ي", "يـ", "ـيـ", "ـي", "y"),
        ArabicAlphabet("Hamzah", "ء", "ء", "ء", "ء", "\'"),
        ArabicAlphabet("Laam Alif", "لا", "لا", "ـلا", "ـلا", "lā")
    )

}