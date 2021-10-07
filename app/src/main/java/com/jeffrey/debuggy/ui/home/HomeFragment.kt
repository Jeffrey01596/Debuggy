package com.jeffrey.debuggy.ui.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeffrey.debuggy.R
import com.jeffrey.debuggy.data.preference.PreferencesHelper
import com.jeffrey.debuggy.data.sectioned.CardSectionedAdapter
import com.jeffrey.debuggy.data.slot.informationHomeList
import com.jeffrey.debuggy.data.slot.instructionHomeList
import com.jeffrey.debuggy.databinding.FragmentHomeBinding
import com.jeffrey.debuggy.ui.base.BaseFragment
import com.jeffrey.debuggy.utils.NetworkUtil
import com.jeffrey.debuggy.utils.TransitionUtil
import com.jeffrey.debuggy.utils.extensions.addInsetPaddings
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    var container: MotionLayout? = null
    private val preference: PreferencesHelper by inject()

    override fun setUpViews() {
        returnTransition = TransitionUtil.getMaterialSharedAxis(requireActivity(), true)

        container = binding.mainContainer
        setHasOptionsMenu(true)

        NetworkUtil.listener(
            requireActivity(),
            ::hideBanner,
            ::showBanner
        )

        binding.recyclerView.addInsetPaddings(bottom = true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = ConcatAdapter(
            CardSectionedAdapter(
                informationHomeList(requireActivity(), preference.port),
                requireActivity().resources.getString(R.string.header_information),
                requireActivity()
            ),
            CardSectionedAdapter(
                instructionHomeList(requireActivity(), preference.port),
                requireActivity().resources.getString(R.string.header_instruction),
                requireActivity()
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.navigation_settings) {
            Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment
            ).navigate(R.id.navigation_settings)
            exitTransition = TransitionUtil.getMaterialSharedAxis(requireActivity(), false)
            reenterTransition = TransitionUtil.getMaterialSharedAxis(requireActivity(), true)
        }
        return false
    }

    private fun showBanner() {
        container?.transitionToEnd()
    }

    private fun hideBanner() {
        container?.transitionToStart()
    }
}