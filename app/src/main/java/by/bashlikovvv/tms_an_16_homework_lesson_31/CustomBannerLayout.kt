package by.bashlikovvv.tms_an_16_homework_lesson_31

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import by.bashlikovvv.tms_an_16_homework_lesson_31.databinding.LayoutBannerBinding

class CustomBannerLayout : MotionLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setUpCustomAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        setUpCustomAttrs(attrs)
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

    private fun setUpCustomAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBannerLayout)
        val background = typedArray.getResourceId(R.styleable.CustomBannerLayout_bl_background, R.drawable.layout_banner_background)
        val padding = typedArray.getInt(R.styleable.CustomBannerLayout_bl_padding, 5)
        val icon = typedArray.getResourceId(R.styleable.CustomBannerLayout_bl_icon, 0)
        val title = typedArray.getString(R.styleable.CustomBannerLayout_bl_title)
        val subTitle = typedArray.getString(R.styleable.CustomBannerLayout_bl_subtitle)
        val firstText = typedArray.getString(R.styleable.CustomBannerLayout_bl_first_button_text)
        val secondText = typedArray.getString(R.styleable.CustomBannerLayout_bl_second_button_text)

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
            binding.bannerLayoutSecondButton.text = secondText
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

}