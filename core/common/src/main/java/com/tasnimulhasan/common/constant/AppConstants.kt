package com.tasnimulhasan.common.constant

import com.tasnimulhasan.entity.ArabicAlphabet
import com.tasnimulhasan.entity.ExampleWord

object AppConstants {
    const val CHANNEL_ID = "quran_channel_id"
    const val CHANNEL_NAME = "Quran Notification"
    const val NOTIFICATION_REQUEST_CODE = 107

    const val DB_NAME = "quran_mobile.db"

    const val PRAYER_TIMES_BASE_URL = "https://api.aladhan.com/v1/"
    const val HADITH_BASE_URL = "https://hadithapi.com/"
    const val HADITH_API_KEY = $$"$2y$10$igEAXi4ASN4Z0fOZmBvOjKWKO6w0rGiSO52ub6d5YEZVNzAL7Xe"

    const val BISMILLAH = "بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ"

    const val NOTIFICATION_ID = 107
    const val NOTIFICATION_CHANNEL_NAME = "Quran Notification"
    const val NOTIFICATION_CHANNEL_ID = "quran_channel_id"

    const val FOREGROUND_ID = 122

    const val ACTION_PREVIOUS = "previous"
    const val ACTION_PLAY_PAUSE = "play_pause"
    const val ACTION_NEXT = "next"

    /* Hadith Book Names */
    const val SAHIH_BUKHARI = "Sahih Bukhari"
    const val SAHIH_MUSLIM = "Sahih Muslim"
    const val JAMI_AL_TIRMIDHI = "Jami' Al-Tirmidhi"
    const val SUNAN_ABU_DAWOOD = "Sunan Abu Dawood"
    const val SUNAN_IBN_MAJAH = "Sunan Ibn-e-Majah"
    const val SUNAN_AN_NASAI = "Sunan An-Nasa`i"
    const val MISHKAT_AL_MASABIH = "Mishkat Al-Masabih"
    const val MUSNAD_AHMAD = "Musnad Ahmad"
    const val AL_SILSILA_SAHIHA = "Al-Silsila Sahiha"

    const val DEMO_URL_FOR_QURAN_MP3 = "https://everyayah.com/data/Alafasy_128kbps/001001.mp3"

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

        ArabicAlphabet(
            "Alif", "ا", "ا", "ـا", "ـا", "a / ā",
            listOf(
                ExampleWord("آمَنُوا", "āmanū", "they believed"),
                ExampleWord("ٱلْكِتَابُ", "al-kitābu", "the Book"),
                ExampleWord("أَحَدٌ", "aḥad", "one")
            )
        ),

        ArabicAlphabet(
            "Baa", "ب", "بـ", "ـبـ", "ـب", "b",
            listOf(
                ExampleWord("بِسْمِ", "bismi", "in the name"),
                ExampleWord("بَلَىٰ", "balā", "yes"),
                ExampleWord("عِبَادِ", "ʿibād", "servants")
            )
        ),

        ArabicAlphabet(
            "Taa", "ت", "تـ", "ـتـ", "ـت", "t",
            listOf(
                ExampleWord("تَقْوَىٰ", "taqwā", "God-consciousness"),
                ExampleWord("تُوبُوا", "tūbū", "repent"),
                ExampleWord("جَنَّاتٍ", "jannāt", "gardens")
            )
        ),

        ArabicAlphabet(
            "Thaa", "ث", "ثـ", "ـثـ", "ـث", "th",
            listOf(
                ExampleWord("ثَوَابٌ", "thawāb", "reward"),
                ExampleWord("ثُمَّ", "thumma", "then"),
                ExampleWord("يَبْعَثُ", "yabʿathu", "He resurrects")
            )
        ),

        ArabicAlphabet(
            "Jeem", "ج", "جـ", "ـجـ", "ـج", "j",
            listOf(
                ExampleWord("جَنَّةٍ", "jannah", "garden"),
                ExampleWord("يَجْعَلْ", "yajʿal", "He makes"),
                ExampleWord("أَجْرٌ", "ajr", "reward")
            )
        ),

        ArabicAlphabet(
            "Haa", "ح", "حـ", "ـحـ", "ـح", "ḥ",
            listOf(
                ExampleWord("حَقٌّ", "ḥaqq", "truth"),
                ExampleWord("رَحْمَةً", "raḥmah", "mercy"),
                ExampleWord("يُحِبُّ", "yuḥibbu", "He loves")
            )
        ),

        ArabicAlphabet(
            "Khaa", "خ", "خـ", "ـخـ", "ـخ", "kh",
            listOf(
                ExampleWord("خَالِدِينَ", "khālidīn", "abiding forever"),
                ExampleWord("خَيْرٌ", "khayr", "good"),
                ExampleWord("أَخَذْنَا", "akhadhnā", "We took")
            )
        ),

        ArabicAlphabet(
            "Dal", "د", "د", "ـد", "ـد", "d",
            listOf(
                ExampleWord("دِينِ", "dīn", "religion"),
                ExampleWord("قَدْ", "qad", "indeed"),
                ExampleWord("هُدًى", "hudā", "guidance")
            )
        ),

        ArabicAlphabet(
            "Dhal", "ذ", "ذ", "ـذ", "ـذ", "dh",
            listOf(
                ExampleWord("ذَٰلِكَ", "dhālika", "that"),
                ExampleWord("إِذَا", "idhā", "when"),
                ExampleWord("يُعَذِّبُ", "yuʿadhdhibu", "He punishes")
            )
        ),

        ArabicAlphabet(
            "Raa", "ر", "ر", "ـر", "ـر", "r",
            listOf(
                ExampleWord("رَسُولُ", "rasūl", "messenger"),
                ExampleWord("رَحِيمٌ", "raḥīm", "merciful"),
                ExampleWord("يَرْزُقُ", "yarzuqu", "He provides")
            )
        ),

        ArabicAlphabet(
            "Zaay", "ز", "ز", "ـز", "ـز", "z",
            listOf(
                ExampleWord("زَكَاةَ", "zakāh", "charity"),
                ExampleWord("يَزِيدُ", "yazīdu", "He increases"),
                ExampleWord("رِزْقٍ", "rizq", "provision")
            )
        ),

        ArabicAlphabet(
            "Seen", "س", "سـ", "ـسـ", "ـس", "s",
            listOf(
                ExampleWord("سَمِيعٌ", "samīʿ", "All-Hearing"),
                ExampleWord("سُبْحَانَ", "subḥān", "glory be"),
                ExampleWord("مُسْلِمِينَ", "muslimīn", "Muslims")
            )
        ),

        ArabicAlphabet(
            "Sheen", "ش", "شـ", "ـشـ", "ـش", "sh",
            listOf(
                ExampleWord("شَيْءٍ", "shayʾ", "thing"),
                ExampleWord("شَاكِرٌ", "shākir", "thankful"),
                ExampleWord("مُشْرِكِينَ", "mushrikīn", "polytheists")
            )
        ),

        ArabicAlphabet(
            "Saad", "ص", "صـ", "ـصـ", "ـص", "ṣ",
            listOf(
                ExampleWord("صِرَاطَ", "ṣirāṭ", "path"),
                ExampleWord("صَابِرِينَ", "ṣābirīn", "patient ones"),
                ExampleWord("مُصِيبَةٌ", "muṣībah", "calamity")
            )
        ),

        ArabicAlphabet(
            "Daad", "ض", "ضـ", "ـضـ", "ـض", "ḍ",
            listOf(
                ExampleWord("ضَالِّينَ", "ḍāllīn", "those astray"),
                ExampleWord("يَضْرِبُ", "yaḍribu", "He strikes"),
                ExampleWord("أَرْضِ", "arḍ", "earth")
            )
        ),

        ArabicAlphabet(
            "Taa", "ط", "طـ", "ـطـ", "ـط", "ṭ",
            listOf(
                ExampleWord("طَيِّبَاتِ", "ṭayyibāt", "good things"),
                ExampleWord("طَاعَةٌ", "ṭāʿah", "obedience"),
                ExampleWord("يُطْعِمُ", "yuṭʿimu", "He feeds")
            )
        ),

        ArabicAlphabet(
            "Dhaa", "ظ", "ظـ", "ـظـ", "ـظ", "ẓ",
            listOf(
                ExampleWord("ظَالِمِينَ", "ẓālimīn", "wrongdoers"),
                ExampleWord("يَظْلِمُ", "yaẓlimu", "He wrongs"),
                ExampleWord("ظَنًّا", "ẓannan", "assumption")
            )
        ),

        ArabicAlphabet(
            "Ayn", "ع", "عـ", "ـعـ", "ـع", "ʿ",
            listOf(
                ExampleWord("عَالَمِينَ", "ʿālamīn", "worlds"),
                ExampleWord("عِبَادِ", "ʿibād", "servants"),
                ExampleWord("يَعْلَمُ", "yaʿlamu", "He knows")
            )
        ),

        ArabicAlphabet(
            "Ghayn", "غ", "غـ", "ـغـ", "ـغ", "gh",
            listOf(
                ExampleWord("غَفُورٌ", "ghafūr", "All-Forgiving"),
                ExampleWord("مَغْفِرَةٌ", "maghfirah", "forgiveness"),
                ExampleWord("يَغْفِرُ", "yaghfiru", "He forgives")
            )
        ),

        ArabicAlphabet(
            "Faa", "ف", "فـ", "ـفـ", "ـف", "f",
            listOf(
                ExampleWord("فَضْلُ", "faḍl", "bounty"),
                ExampleWord("فِرْعَوْنَ", "firʿawn", "Pharaoh"),
                ExampleWord("يَفْعَلُ", "yafʿalu", "He does")
            )
        ),

        ArabicAlphabet(
            "Qaaf", "ق", "قـ", "ـقـ", "ـق", "q",
            listOf(
                ExampleWord("قُرْآنٌ", "qurʾān", "Qur’an"),
                ExampleWord("قَدِيرٌ", "qadīr", "All-Powerful"),
                ExampleWord("يَقُولُ", "yaqūlu", "He says")
            )
        ),

        ArabicAlphabet(
            "Kaaf", "ك", "كـ", "ـكـ", "ـك", "k",
            listOf(
                ExampleWord("كِتَابٌ", "kitāb", "book"),
                ExampleWord("كَانَ", "kāna", "was"),
                ExampleWord("يُكَلِّمُ", "yukallimu", "He speaks")
            )
        ),

        ArabicAlphabet(
            "Laam", "ل", "لـ", "ـلـ", "ـل", "l",
            listOf(
                ExampleWord("اللَّهُ", "allāh", "Allah"),
                ExampleWord("لَعَلَّكُمْ", "laʿallakum", "so that you may"),
                ExampleWord("قُلْ", "qul", "say")
            )
        ),

        ArabicAlphabet(
            "Meem", "م", "مـ", "ـمـ", "ـم", "m",
            listOf(
                ExampleWord("مُؤْمِنِينَ", "muʾminīn", "believers"),
                ExampleWord("مَغْفِرَةٌ", "maghfirah", "forgiveness"),
                ExampleWord("رَحْمَةٌ", "raḥmah", "mercy")
            )
        ),

        ArabicAlphabet(
            "Noon", "ن", "نـ", "ـنـ", "ـن", "n",
            listOf(
                ExampleWord("نُورٌ", "nūr", "light"),
                ExampleWord("نَعِيمٍ", "naʿīm", "bliss"),
                ExampleWord("يُنْفِقُونَ", "yunfiqūn", "they spend")
            )
        ),

        ArabicAlphabet(
            "Haaʼ", "ه", "هـ", "ـهـ", "ـه", "h",
            listOf(
                ExampleWord("هُدًى", "hudā", "guidance"),
                ExampleWord("هُوَ", "huwa", "He"),
                ExampleWord("جَنَّهُ", "jannah", "his garden")
            )
        ),

        ArabicAlphabet(
            "Waw", "و", "و", "ـو", "ـو", "w",
            listOf(
                ExampleWord("وَاللَّهُ", "wa-allāhu", "and Allah"),
                ExampleWord("وُجُوهٌ", "wujūh", "faces"),
                ExampleWord("يُوَفِّي", "yuwaffī", "He will give in full")
            )
        ),

        ArabicAlphabet(
            "Yaaʼ", "ي", "يـ", "ـيـ", "ـي", "y",
            listOf(
                ExampleWord("يَوْمَ", "yawma", "day"),
                ExampleWord("يُؤْمِنُونَ", "yuʾminūn", "they believe"),
                ExampleWord("هَدَىٰ", "hadā", "He guided")
            )
        ),

        ArabicAlphabet(
            "Hamzah", "ء", "ء", "ء", "ء", "ʾ",
            listOf(
                ExampleWord("سُئِلَ", "suʾila", "was asked"),
                ExampleWord("يَسْأَلُونَ", "yasʾalūn", "they ask"),
                ExampleWord("مَلَائِكَةٌ", "malāʾikah", "angels")
            )
        ),

        ArabicAlphabet(
            "Laam Alif", "لا", "لا", "ـلا", "ـلا", "lā",
            listOf(
                ExampleWord("لَا إِلَٰهَ", "lā ilāha", "there is no god"),
                ExampleWord("لَا رَيْبَ", "lā rayba", "no doubt"),
                ExampleWord("لَا تَعْلَمُونَ", "lā taʿlamūn", "you do not know")
            )
        )
    )

}