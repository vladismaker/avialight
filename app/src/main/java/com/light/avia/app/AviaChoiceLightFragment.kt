package com.light.avia.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class AviaChoiceLightFragment : Fragment() {
    private var aviaCreaLight:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aviaCreaLight =1
        return inflater.inflate(R.layout.fragment_avia_choice_light, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aviaCreaLight = 2

        val aviaAirplaneOneChoiceLight: ImageView = view.findViewById(R.id.avia_airplane_one_choice_light)
        val aviaAirplaneTwoChoiceLight: ImageView = view.findViewById(R.id.avia_airplane_two_choice_light)

        aviaAirplaneOneChoiceLight.setOnClickListener {
            aviaCreaLight++
            AviaGameLightFragment.choiceAirplane = 1
            parentFragmentManager.beginTransaction().replace(R.id.avia_contai_light, AviaGameLightFragment()).commit()
        }

        aviaAirplaneTwoChoiceLight.setOnClickListener {
            aviaCreaLight--
            AviaGameLightFragment.choiceAirplane = 2
            parentFragmentManager.beginTransaction().replace(R.id.avia_contai_light, AviaGameLightFragment()).commit()
        }
    }
}