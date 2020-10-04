package com.devhome.myevents.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.devhome.myevents.R
import kotlinx.android.synthetic.main.fragment_pager.view.*


class PagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pager, container, false)
        val onBoardingImg = arrayOf(
            R.drawable.icon_event,
            R.drawable.icon_date,
            R.drawable.icon_time
        )
        val onBoardingBack = arrayOf(
            R.drawable.back_one,
            R.drawable.back_two,
            R.drawable.back_three
        )
        val mainText =
            arrayOf("Create Event", "View Events", "Get Notified")
        val subText =
            arrayOf(
                "Create your event with name, date and time",
                "See your upcoming Events List with days",
                "Get Notified by receiving notification"
            )
        var fragPos = arguments?.getInt(ARG_SECTION_NUMBER)
        root.main_txt.text = mainText[fragPos!!]
        root.sub_txt.text = subText[fragPos]
        root.imageView.setImageDrawable(
            ContextCompat.getDrawable(
                activity?.applicationContext!!,
                onBoardingImg[fragPos]
            )
        )

        root.imgLin.setBackgroundResource(onBoardingBack[fragPos])
        root.imageView.setColorFilter(
            ContextCompat.getColor(
                requireActivity(),
                R.color.white
            ), android.graphics.PorterDuff.Mode.SRC_IN
        );

        return root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PagerFragment {
            return PagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)

                }
            }
        }
    }

}