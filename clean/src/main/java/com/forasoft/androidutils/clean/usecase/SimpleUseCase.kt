package com.forasoft.androidutils.clean.usecase

interface SimpleUseCase<in P, out R> {
    operator fun invoke(params: P): R
}
