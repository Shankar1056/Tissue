package bigappcompany.com.tissu.activity

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 16 Sep 2017 at 4:11 PM
 */

class CustomLinearLayout : LinearLayout, Checkable {

    private var checked = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(checked: Boolean) {
        this.checked = checked

        refreshDrawableState()

        // Propagate to childs
        val count = childCount
        for (i in 0..count - 1) {
            val child = getChildAt(i)
            if (child is Checkable) {
                (child as Checkable).isChecked = checked
            }
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun toggle() {
        this.checked = !this.checked
    }

    companion object {


        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}
