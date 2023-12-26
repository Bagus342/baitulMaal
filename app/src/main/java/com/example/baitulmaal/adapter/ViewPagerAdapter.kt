package com.example.baitulmaal.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baitulmaal.fragments.PemasukanFragment
import com.example.baitulmaal.fragments.PengeluaranFragment

class ViewPagerAdapter(fragment: Fragment):
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                PemasukanFragment()
            }
            1 -> {
                PengeluaranFragment()
            }
            else -> {
                PemasukanFragment()
            }
        }
    }

}