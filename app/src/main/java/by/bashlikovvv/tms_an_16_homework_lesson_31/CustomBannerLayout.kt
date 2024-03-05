package by.bashlikovvv.tms_an_16_homework_lesson_31

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.motion.widget.MotionLayout
import by.bashlikovvv.tms_an_16_homework_lesson_31.databinding.LayoutBannerBinding
import kotlin.properties.Delegates

class CustomBannerLayout : MotionLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setUpCustomAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        setUpCustomAttrs(attrs)
    }

    private var _state: BannerLayoutState by Delegates.observable(BannerLayoutState.Info()) { _, _, _ ->
        setState(true)
    }

    private val binding: LayoutBannerBinding = LayoutBannerBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    var isVisible = false
        private set

    private var clickListeners: OnCLikListeners = object : OnCLikListeners {
        override fun notifyFirstButtonClicked() {  }
        override fun notifySecondButtonClicked() {  }
    }

    init {
        dropProgress()
        binding.bannerLayoutFirstButton.setOnClickListener {
            clickListeners.notifyFirstButtonClicked()
            hideBanner()
        }
        binding.bannerLayoutSecondButton.setOnClickListener {
            clickListeners.notifySecondButtonClicked()
            hideBanner()
        }
    }

    fun showBanner() {
        binding.bannerLayout.visibility = VISIBLE
        binding.root.transitionToEnd()
        isVisible = true
    }

    fun hideBanner() {
        binding.root.transitionToStart()
        binding.bannerLayout.visibility = GONE
        isVisible = false
    }

    fun setClickListeners(newListeners: OnCLikListeners) {
        this.clickListeners = newListeners
    }

    fun setState(state: BannerLayoutState) {
        _state = state
    }

    private fun setState(hideButtons: Boolean) {
        _state.let {
            with(binding) {
                bannerLayout.setBackgroundResource(it.background)
                bannerLayoutIcon.setImageResource(it.icon)
                bannerLayoutIcon.visibility = VISIBLE
                val color = resources.getColor(it.textColor, resources.newTheme())
                bannerLayoutTitle.setTextColor(color)
                bannerLayoutSubTitle.setTextColor(color)
                bannerLayoutIcon.setColorFilter(color)
                if (hideButtons) {
                    ValueAnimator.ofFloat(500f, 0f).apply {
                        addUpdateListener {
                            val alpha = linearInterpolation(it.animatedFraction, 1f, 0f)
                            binding.bannerLayoutFirstButton.alpha = alpha
                            binding.bannerLayoutSecondButton.alpha = alpha
                            if (alpha == 0f) {
                                binding.bannerLayoutFirstButton.visibility = GONE
                                binding.bannerLayoutSecondButton.visibility = GONE
                            }
                        }
                        interpolator = LinearInterpolator()
                        duration = 300
                        start()
                    }
                }
            }
        }
    }

    private fun linearInterpolation(t: Float, a: Float, b: Float) = (1 - t) * a + t * b

    private fun setUpCustomAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBannerLayout)
        val background = typedArray.getResourceId(R.styleable.CustomBannerLayout_bl_background, R.drawable.layout_banner_background)
        val padding = typedArray.getInt(R.styleable.CustomBannerLayout_bl_padding, 5)
        val icon = typedArray.getResourceId(R.styleable.CustomBannerLayout_bl_icon, 0)
        val title = typedArray.getString(R.styleable.CustomBannerLayout_bl_title)
        val subTitle = typedArray.getString(R.styleable.CustomBannerLayout_bl_subtitle)
        val firstText = typedArray.getString(R.styleable.CustomBannerLayout_bl_first_button_text)
        val secondText = typedArray.getString(R.styleable.CustomBannerLayout_bl_second_button_text)

        setState(false)
        with(binding) {
            bannerLayout.setBackgroundResource(background)
            bannerLayout.setPadding(padding, padding, padding, padding)
            if (icon != 0) {
                bannerLayoutIcon.setImageResource(icon)
                bannerLayoutIcon.visibility = VISIBLE
            } else {
                bannerLayoutIcon.visibility = GONE
            }
            bannerLayoutTitle.text = title
            if (subTitle != null) {
                bannerLayoutSubTitle.text = subTitle
                bannerLayoutSubTitle.visibility = VISIBLE
            } else {
                bannerLayoutSubTitle.visibility = GONE
            }
            if (secondText != null) {
                binding.bannerLayoutSecondButton.text = secondText
                binding.bannerLayoutSecondButton.visibility = VISIBLE
            } else {
                binding.bannerLayoutSecondButton.visibility = GONE
            }
            if (firstText != null) {
                binding.bannerLayoutFirstButton.text = firstText
                binding.bannerLayoutFirstButton.visibility = VISIBLE
            } else {
                binding.bannerLayoutFirstButton.visibility = GONE
            }
        }
        typedArray.recycle()
    }

    private fun dropProgress() {
        binding.root.transitionToStart()
        isVisible = false
    }

    interface OnCLikListeners {

        fun notifyFirstButtonClicked()

        fun notifySecondButtonClicked()

    }

    sealed class BannerLayoutState(
        @DrawableRes val icon: Int,
        @DrawableRes val background: Int,
        @ColorRes val textColor: Int
    ) {

        class Info : BannerLayoutState(
            icon = R.drawable.info,
            background = R.drawable.layout_banner_background_info,
            textColor = R.color.info
        )

        class Warning : BannerLayoutState(
            icon = R.drawable.warning,
            background = R.drawable.layout_banner_background_warning,
            textColor = R.color.warning
        )

        class Error : BannerLayoutState(
            icon = R.drawable.error,
            background = R.drawable.layout_banner_background_error,
            textColor = R.color.error
        )

        class Success : BannerLayoutState(
            icon = R.drawable.success,
            background = R.drawable.layout_banner_background_success,
            textColor = R.color.success
        )

    }

}