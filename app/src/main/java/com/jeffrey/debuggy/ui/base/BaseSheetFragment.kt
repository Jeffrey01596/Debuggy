package com.jeffrey.debuggy.ui.base

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jeffrey.debuggy.utils.aliases.FragmentInflate
import org.koin.core.component.KoinComponent

abstract class BaseSheetFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>
) : BottomSheetDialogFragment(), KoinComponent {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    open fun setUpViews() {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : BottomSheetDialog(requireContext(), theme) {
            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window?.let {
                        WindowCompat.setDecorFitsSystemWindows(it, false)
                    }

                    findViewById<View>(com.google.android.material.R.id.container)?.apply {
                        fitsSystemWindows = false
                        val topMargin = marginTop
                        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
                            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                                updateMargins(top = topMargin + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
                            }
                            insets
                        }
                    }

                    findViewById<View>(com.google.android.material.R.id.coordinator)?.fitsSystemWindows =
                        false
                }
            }
        }
        dialog.window?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                WindowCompat.setDecorFitsSystemWindows(it, false)
                it.navigationBarColor = Color.TRANSPARENT
                ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { view, insets ->
                    val navigationInsets =
                        insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                    view.updatePadding(left = navigationInsets.left, right = navigationInsets.right)
                    insets
                }
            }
        }
        return dialog
    }
}