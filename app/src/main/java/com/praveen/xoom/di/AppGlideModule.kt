package com.praveen.xoom.di

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class XoomGlideModule: AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}