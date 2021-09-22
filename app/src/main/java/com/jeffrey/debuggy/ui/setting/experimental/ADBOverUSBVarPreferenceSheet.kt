package com.jeffrey.debuggy.ui.setting.experimental

import android.widget.CompoundButton
import com.jeffrey.debuggy.R
import com.jeffrey.debuggy.databinding.DialogAdbOverUsbVarPreferenceBinding
import com.jeffrey.debuggy.ui.base.BaseSheetFragment
import com.jeffrey.debuggy.utils.RootUtil
import com.jeffrey.debuggy.utils.Utils


class ADBOverUSBVarPreferenceSheet : BaseSheetFragment<DialogAdbOverUsbVarPreferenceBinding>(
    DialogAdbOverUsbVarPreferenceBinding::inflate
) {

    override fun setUpViews() {
        binding.switchAdb.text = requireActivity().getString(
            R.string.system_status_adb_enabled,
            Utils.getADBOverUSBStatus(requireActivity()),
            if (Utils.getADBOverUSBStatus(requireActivity())) 1 else 0
        )
        binding.switchAdb.isChecked = Utils.getADBOverUSBStatus(requireActivity())
        binding.switchAdb.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            RootUtil.changeADBOverUSBVar(isChecked)
            dismiss()
        }

        binding.actionCancel.setOnClickListener { dismiss() }
    }
}