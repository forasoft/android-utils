package com.forasoft.androidutils.kotlin.common.size


fun Iterable<Size>.sumOf() = Size(this.sumOf { it.bitCount })
