package com.jeffrey.debuggy.ui.setting.security

import com.jeffrey.debuggy.R
import com.jeffrey.debuggy.data.preference.PreferencesHelper
import com.jeffrey.debuggy.databinding.DialogAdbAfterWhilePreferenceBinding
import com.jeffrey.debuggy.ui.base.fragment.BaseSheetFragment
import org.koin.core.component.inject

class ADBAfterWhilePreferenceSheet :
    BaseSheetFragment<DialogAdbAfterWhilePreferenceBinding>(DialogAdbAfterWhilePreferenceBinding::inflate) {

    private val preference: PreferencesHelper by inject()

    override fun setUpViews() {
        when (preference.adbAfterWhile) {
            0 -> {
                binding.radioNever.isChecked = true
            }
            1 -> {
                binding.radioOneHour.isChecked = true
            }
            2 -> {
                binding.radioTwoHours.isChecked = true
            }
            4 -> {
                binding.radioFourHours.isChecked = true
            }
            6 -> {
                binding.radioSixHours.isChecked = true
            }
            12 -> {
                binding.radioTwelveHours.isChecked = true
            }
        }

        binding.actionCancel.setOnClickListener { dismiss() }

        binding.adbAfterWhileGroup.setOnCheckedChangeListener { _, _ ->
            when (binding.adbAfterWhileGroup.checkedRadioButtonId) {
                R.id.radio_never -> {
                    preference.adbAfterWhile = 0
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
                R.id.radio_one_hour -> {
                    preference.adbAfterWhile = 1
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
                R.id.radio_two_hours -> {
                    preference.adbAfterWhile = 2
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
                R.id.radio_four_hours -> {
                    preference.adbAfterWhile = 4
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
                R.id.radio_six_hours -> {
                    preference.adbAfterWhile = 6
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
                R.id.radio_twelve_hours -> {
                    preference.adbAfterWhile = 12
                    (parentFragment as SecuritySettingsFragment?)?.callback
                }
            }
            dismiss()
        }
    }
}