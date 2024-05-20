package com.light.avia.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import kotlin.random.Random

class AviaGameLightFragment : Fragment() {
    private var aviaCreaLight:Int = 1
    private var arrayMills:MutableList<Long> = mutableListOf(4000, 4500, 5000, 6000, 7000, 8000, 9000)
    private var winnerAirplane:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aviaCreaLight = 2
        return inflater.inflate(R.layout.fragment_avia_game_light, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aviaCreaLight = 2

        val aviaAirplaneOneLight: ImageView = view.findViewById(R.id.avia_airplane_one_light)
        val aviaAirplaneTwoLight: ImageView = view.findViewById(R.id.avia_airplane_two_light)
        val aviaAgainLight: TextView = view.findViewById(R.id.avia_again_light)
        val aviaResultLottieLight: LottieAnimationView = view.findViewById(R.id.avia_lottie_result_light)

        aviaAgainLight.setOnClickListener {
            winnerAirplane = 0

            parentFragmentManager.beginTransaction().replace(R.id.avia_contai_light, AviaChoiceLightFragment()).commit()
        }

        start(aviaAirplaneOneLight, aviaAirplaneTwoLight, aviaAgainLight, aviaResultLottieLight)
    }

    private fun start(aviaAirplaneOneLight:ImageView, aviaAirplaneTwoLight:ImageView, aviaAgainLight:TextView, aviaResultLottieLight:LottieAnimationView){
        aviaAirplaneOneLight.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                aviaAirplaneOneLight.viewTreeObserver.removeOnPreDrawListener(this)

                val ballBallHorsassnLocation = IntArray(2)
                aviaAirplaneOneLight.getLocationOnScreen(ballBallHorsassnLocation)

                val xOnScreen = ballBallHorsassnLocation[0].toFloat()
                val yOnScreen = ballBallHorsassnLocation[1].toFloat()

                val animBallHorsassn = TranslateAnimation(xOnScreen, xOnScreen, 0f, -yOnScreen)
                animBallHorsassn.duration = arrayMills[Random.nextInt(5)]
                animBallHorsassn.interpolator = AccelerateInterpolator()

                aviaCreaLight --

                aviaAirplaneOneLight.startAnimation(animBallHorsassn)

                animBallHorsassn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animBallHorsassn: Animation?) {}

                    override fun onAnimationEnd(animBallHorsassn: Animation?) {

                        aviaAirplaneOneLight.setImageResource(R.color.avia_transparent_light)

                        if(winnerAirplane == 0){
                            winnerAirplane =1
                            if(winnerAirplane == choiceAirplane){
                                aviaResultLottieLight.visibility = View.VISIBLE
                                aviaResultLottieLight.setAnimation(R.raw.avia_win_light)
                                aviaResultLottieLight.playAnimation()
                            }else{
                                aviaResultLottieLight.visibility = View.VISIBLE
                                aviaResultLottieLight.setAnimation(R.raw.avia_cross_light)
                                aviaResultLottieLight.playAnimation()
                            }
                            aviaAgainLight.visibility = View.VISIBLE
                        }
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })

                return true
            }
        })

        aviaAirplaneTwoLight.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                aviaAirplaneTwoLight.viewTreeObserver.removeOnPreDrawListener(this)

                val ballBallHorsassnLocation = IntArray(2)
                aviaAirplaneTwoLight.getLocationOnScreen(ballBallHorsassnLocation)

                aviaCreaLight++

                val yOnScreen = ballBallHorsassnLocation[1].toFloat()


                val animBallHorsassn = TranslateAnimation(0f, 0f, 0f, -yOnScreen)
                animBallHorsassn.duration = arrayMills[Random.nextInt(7)]
                animBallHorsassn.interpolator = AccelerateInterpolator()

                aviaAirplaneTwoLight.startAnimation(animBallHorsassn)

                animBallHorsassn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animBallHorsassn: Animation?) {}

                    override fun onAnimationEnd(animBallHorsassn: Animation?) {

                        aviaAirplaneTwoLight.setImageResource(R.color.avia_transparent_light)

                        if(winnerAirplane == 0){
                            winnerAirplane =2
                            if(winnerAirplane == choiceAirplane){
                                aviaResultLottieLight.visibility = View.VISIBLE
                                aviaResultLottieLight.setAnimation(R.raw.avia_win_light)
                                aviaResultLottieLight.playAnimation()
                            }else{
                                aviaResultLottieLight.visibility = View.VISIBLE
                                aviaResultLottieLight.setAnimation(R.raw.avia_cross_light)
                                aviaResultLottieLight.playAnimation()
                            }
                            aviaAgainLight.visibility = View.VISIBLE
                        }
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })

                return true
            }
        })
    }

    companion object{
        var choiceAirplane:Int = 0
    }
}