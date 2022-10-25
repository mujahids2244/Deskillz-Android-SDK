package com.arhamsoft.deskilz.ui.backGroundLayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.arhamsoft.deskilz.R

class BackGroundLayoutView @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null
                                                     ,defStyleAttr: Int = R.attr.BackGroundViewStyle
) : View(context, attrs, defStyleAttr) {

    @Dimension
    var corners: Float = 300F

    var cornersSide: Int = 0

    @Dimension
    var upperCutValue: Float = 150F

    @Dimension
    var lowerCutValue: Float = 200F

    @ColorInt
    var gradient1: Int = Color.WHITE

    @ColorInt
    var gradient2: Int = Color.BLACK

    var applyGradient: Int = 0

    @ColorInt
    var backgroundColorView: Int = Color.BLACK

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BackgroundLayout,
            defStyleAttr,
            0
        )

        try {

            cornersSide = typedArray.getInteger(
                R.styleable.BackgroundLayout_corners_side,
                cornersSide
            )

            corners = typedArray.getDimension(
                R.styleable.BackgroundLayout_cornersValue,
                corners
            )

            upperCutValue = typedArray.getDimension(
                R.styleable.BackgroundLayout_upper_cut_value,
                upperCutValue
            )

            lowerCutValue = typedArray.getDimension(
                R.styleable.BackgroundLayout_lower_cut_value,
                lowerCutValue
            )

            applyGradient = typedArray.getInteger(
                R.styleable.BackgroundLayout_apply_gradient,
                applyGradient
            )

            gradient1 = typedArray.getColor(
                R.styleable.BackgroundLayout_gradient1,
                gradient1
            )

            gradient2 = typedArray.getColor(
                R.styleable.BackgroundLayout_gradient2,
                gradient2
            )

            backgroundColorView = typedArray.getColor(
                R.styleable.BackgroundLayout_backgroundColorView,
                backgroundColorView
            )

        } catch (ex: Exception) {

        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()

        if (applyGradient == 1) {
            val grad = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                0F,
                gradient1,
                gradient2,
                Shader.TileMode.MIRROR
            )
            paint.shader = grad
        } else {
            paint.color = backgroundColorView
        }

        if (cornersSide == 0) {
            val rActSmall1 = RectF(
                upperCutValue,
                0f,
                width.toFloat(),
                height - lowerCutValue
            )

            canvas?.drawRoundRect(
                rActSmall1,
                corners,
                corners,
                paint
            )

            val rActSmall2 = RectF(
                0F,
                upperCutValue,
                width - lowerCutValue,
                height.toFloat()
            )

            canvas?.drawRoundRect(rActSmall2,
                corners,
                corners,
                paint
            )

            val pathCut = Path()
            pathCut.lineTo(upperCutValue, 0F)
            pathCut.lineTo(width - corners, 0f)
            pathCut.lineTo(width.toFloat(), corners)
            pathCut.lineTo(width.toFloat(),
                height - lowerCutValue)
            pathCut.lineTo(width - lowerCutValue,
                height.toFloat())
            pathCut.lineTo(corners, height.toFloat())
            pathCut.lineTo(0f, height - corners)
            pathCut.lineTo(0f, upperCutValue)
            pathCut.lineTo(upperCutValue, 0F)
            pathCut.close()
            canvas?.drawPath(pathCut, paint)

        } else {

            val rActSmall1 = RectF(
                0f, 0f,
                width - (upperCutValue),
                height - (lowerCutValue)
            )

            canvas?.drawRoundRect(
                rActSmall1,
                corners,
                corners,
                paint
            )

            val rActSmall2 = RectF(width.toFloat(),
                height.toFloat(),
                (lowerCutValue),
                (upperCutValue)
            )

            canvas?.drawRoundRect(rActSmall2,
                corners,
                corners,
                paint
            )

            val pathCut = Path()  /// Upper Corner
            pathCut.lineTo(
                width - upperCutValue, 0f)
            pathCut.lineTo(
                corners, 0f
            )
            pathCut.lineTo(0f, corners)
            pathCut.lineTo(0f,
                height - lowerCutValue)
            pathCut.lineTo(lowerCutValue, height.toFloat())
            pathCut.lineTo(
                width - corners,
                height.toFloat())
            pathCut.lineTo(width.toFloat(), height - corners)
            pathCut.lineTo(
                width.toFloat(), upperCutValue)
            pathCut.lineTo(
                width - upperCutValue, 0f)
            pathCut.close()
            canvas?.drawPath(pathCut, paint)

        }
    }

}