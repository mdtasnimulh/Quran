package com.tasnimulhasan.entity.tasbih

data class DhikrCountEntity(
    val alhamdulillah: Int = 0,
    val subhanAllah: Int = 0,
    val allahuAkbar: Int = 0,
    val laIlahaIllallah: Int = 0,
    val totalCount: Int = 0
) {
    companion object {
        fun empty() = DhikrCountEntity()
    }
}