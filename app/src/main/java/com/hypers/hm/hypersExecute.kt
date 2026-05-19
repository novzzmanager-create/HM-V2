package com.hypers.hm

object hypersExecute {
    
        @JvmStatic
    fun getLowest(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 0")
        list.add("settings delete global debug.hwui.disable_vsync")
        list.add("pm trim-caches 999")
        list.add("cmd game set --mode 2 --downscale 0.6 $pkg")
        list.add("cmd game aet --mode 2 --downscale 0.6 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=0.6,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=0,downscale 0.6")
        list.add("cmd game mode 2 downscale 0.6 $pkg")
        list.add("settings put global window_animation_scale 1.0")
        list.add("settings put global transition_animation_scale 1.0")
        list.add("settings put global animator_duration_scale 1.0")
        list.add("am force-stop $pkg")
        return list
    }

    @JvmStatic
    fun getLow(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 0")
        list.add("settings delete global debug.hwui.disable_vsync")
        list.add("pm trim-caches 999")
        list.add("cmd game set --mode 2 --downscale 0.8 $pkg")
        list.add("cmd game set --mode 2 --downscale 0.8 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=0.8,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=2,downscale 0.8")
        list.add("cmd game mode 2 downscale 0.8 $pkg")
        list.add("settings put global window_animation_scale 1.0")
        list.add("settings put global transition_animation_scale 1.0")
        list.add("settings put global animator_duration_scale 1.0")
        list.add("am force-stop $pkg")
        return list
    }

    @JvmStatic
    fun getDefault(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 0")
        list.add("settings delete global debug.hwui.texture_cache_size")
        list.add("settings delete global debug.hwui.disable_vsync")
        list.add("cmd game set --mode 2 --downscale 1.0 $pkg")
        list.add("cmd game set --mode 2 --downscale 1.0 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=1.0,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=2,downscale 1.0")
        list.add("cmd game mode 2 downscale 1.0 $pkg")
        list.add("settings put global window_animation_scale 1.0")
        list.add("settings put global transition_animation_scale 1.0")
        list.add("settings put global animator_duration_scale 1.0")
        list.add("am force-stop $pkg")
        return list
    }

    @JvmStatic
    fun getSmooth(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 1")
        list.add("settings put global debug.hwui.texture_cache_size 72")
        list.add("cmd package compile -m speed-profile -f $pkg")
        list.add("cmd game set --mode 2 --downscale 1.8 $pkg")
        list.add("cmd game set --mode 2 --downscale 1.8 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=2.3,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=2,downscale 2.3")
        list.add("cmd game mode 2 downscale 1.8 $pkg")
        list.add("settings put secure long_press_timeout 200")
        list.add("settings put secure multi_press_timeout 150")
        list.add("settings put system scroll_friction 0.008")
        list.add("settings put global window_animation_scale 0.5")
        list.add("settings put global transition_animation_scale 0.5")
        list.add("settings put global animator_duration_scale 0.5")
        list.add("am force-stop $pkg")
        return list
    }

    @JvmStatic
    fun getHighest(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 1")
        list.add("settings put global debug.hwui.texture_cache_size 96")
        list.add("settings put global debug.hwui.disable_vsync true")
        list.add("cmd package compile -m speed-profile -f $pkg")
        list.add("cmd game set --mode 2 --downscale 3.0 $pkg")
        list.add("cmd game set --mode 2 --downscale 3.0 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=3.0,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=2,downscale 3.0")
        list.add("cmd game mode 2 downscale 3.0 $pkg")
        list.add("settings put secure long_press_timeout 180")
        list.add("settings put secure multi_press_timeout 120")
        list.add("settings put system scroll_friction 0.004")
        list.add("settings put global window_animation_scale 0.4")
        list.add("settings put global transition_animation_scale 0.4")
        list.add("settings put global animator_duration_scale 0.4")
        list.add("am force-stop $pkg")
        return list
    }

    @JvmStatic
    fun getUltra(pkg: String): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("settings put global disable_window_blurs 1")
        list.add("settings put global debug.hwui.texture_cache_size 128")
        list.add("settings put global debug.hwui.layer_cache_size 64")
        list.add("settings put global debug.hwui.disable_vsync true")
        list.add("pm trim-caches 999")
        list.add("cmd package compile -m speed -f $pkg")
        list.add("cmd game set --mode 2 --downscale 3.5 $pkg")
        list.add("cmd game set --mode 2 --downscale 3.5 --user 0 $pkg")
        list.add("cmd device_config put game_overlay $pkg \"mode=2,downscaleFactor=4.0,useAngle=false,fps=120,loadingBoost=2\"")
        list.add("setprop debug.game.overlay.$pkg mode=2,downscale 4.0")
        list.add("cmd game mode 2 downscale 3.5 $pkg")
        list.add("settings put secure long_press_timeout 150")
        list.add("settings put secure multi_press_timeout 120")
        list.add("settings put system scroll_friction 0.002")
        list.add("settings put global window_animation_scale 0")
        list.add("settings put global transition_animation_scale 0")
        list.add("settings put global animator_duration_scale 0")
        list.add("am force-stop $pkg")
        return list
    }


    
    
    // FPS GENERATED SURFACE
    @JvmStatic
fun getSf1(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.sf.enable_advanced_sf_phase_offset 1",
        "setprop debug.sf.early.app.duration 1100000",
        "setprop debug.sf.early.sf.duration 1100000",
        "setprop debug.sf.early_phase_offset_ns 1600000",
        "setprop debug.sf.late.app.duration 900000",
        "setprop debug.sf.late.sf.duration 900000"
    )
}
    
    @JvmStatic
fun getSf2(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.sf.enable_advanced_sf_phase_offset 1",
        "setprop debug.sf.early.app.duration 1200000",
        "setprop debug.sf.early.sf.duration 1200000",
        "setprop debug.sf.early_phase_offset_ns 2200000",
        "setprop debug.sf.late.app.duration 1000000",
        "setprop debug.sf.late.sf.duration 1000000"
    )
}
    
    @JvmStatic
fun getSf3(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.sf.enable_advanced_sf_phase_offset 1",
        "setprop debug.sf.early.app.duration 1600000",
        "setprop debug.sf.early.sf.duration 1600000",
        "setprop debug.sf.early_phase_offset_ns 2700000",
        "setprop debug.sf.late.app.duration 1300000",
        "setprop debug.sf.late.sf.duration 1300000"
    )
}
    
    @JvmStatic
fun getResetSf(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.sf.enable_advanced_sf_phase_offset 0",
        "setprop debug.sf.early.app.duration 0",
        "setprop debug.sf.early.sf.duration 0",
        "setprop debug.sf.early_phase_offset_ns 0",
        "setprop debug.sf.late.app.duration 0",
        "setprop debug.sf.late.sf.duration 0"
    )
}

    @JvmStatic
fun getJitter(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.hwui.renderer skiagl",
        "setprop debug.hwui.use_hint_manager true", 
        "setprop debug.hwui.target_cpu_duration 5ms", 
        "setprop debug.hwui.use_buffer_age true",
        
        "setprop debug.sf.latch_unsignaled 1",
        "setprop debug.sf.disable_backpressure 1",
        "setprop debug.sf.enable_gl_backpressure 0",
        "setprop debug.sf.predict_hwc_composition_strategy 1",
        "setprop debug.sf.enable_transaction_tracing false",
        "setprop debug.sf.use_phase_offsets_as_timestamps 1", 
        
        "setprop debug.input.high_fps 1",
        "setprop debug.input.resample true",
        "setprop debug.input.enable_prediction 1",
        "setprop persist.vendor.qti.input_boost true",
        
        "setprop persist.sys.composition.type gpu",
        "setprop debug.egl.hw 1",
        "setprop debug.egl.swapinterval 0",
        "setprop debug.gralloc.enable_fb_ubwc 1",
        
        "cmd power set-fixed-performance-mode-enabled true",
        "cmd thermal-service override-status 0"
    )
}

    
    @JvmStatic
fun getJitterDefault(): ArrayList<String> {
    return arrayListOf(
        "setprop debug.hwui.renderer ''",
        "setprop debug.hwui.use_hint_manager ''",
        "setprop debug.hwui.target_cpu_duration ''",
        "setprop debug.hwui.use_buffer_age false",
        
        "setprop debug.sf.latch_unsignaled 0",
        "setprop debug.sf.disable_backpressure 0",
        "setprop debug.sf.enable_gl_backpressure 1",
        "setprop debug.sf.predict_hwc_composition_strategy 0",
        "setprop debug.sf.enable_transaction_tracing true",
        "setprop debug.sf.use_phase_offsets_as_timestamps 0",
        
        "setprop debug.input.high_fps 0",
        "setprop debug.input.resample false",
        "setprop debug.input.enable_prediction 0",
        "setprop persist.vendor.qti.input_boost false",
        
        "setprop persist.sys.composition.type ''",
        "setprop debug.egl.hw 0",
        "setprop debug.egl.swapinterval 1",
        "setprop debug.gralloc.enable_fb_ubwc 0",
        
        "cmd power set-fixed-performance-mode-enabled false",
        "cmd thermal-service override-status 0"
    )
}
}

