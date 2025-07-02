package com.tasnimulhasan.apiresponse.prayertimes

data class IslamicMonthNamesApiResponse(
    val code: Int?,
    val `data`: Data?,
    val status: String?
)

data class Data(
    val `1`: X1?,
    val `2`: X1?,
    val `3`: X1?,
    val `4`: X1?,
    val `5`: X1?,
    val `6`: X1?,
    val `7`: X1?,
    val `8`: X1?,
    val `9`: X1?,
    val `10`: X1?,
    val `11`: X1?,
    val `12`: X1?
)

data class X1(
    val ar: String?,
    val en: String?,
    val number: Int?
)

/*
data class X10(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X11(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X12(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X2(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X3(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X4(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X5(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X6(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X7(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X8(
    val ar: String?,
    val en: String?,
    val number: Int?
)

data class X9(
    val ar: String?,
    val en: String?,
    val number: Int?
)*/