package com.example.antipodpiska.addition

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R
import java.lang.Math.round


class CreateCardFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_create_card, container, false)

        var context: Context? = getContext()

        var addCard: EditText = view.findViewById(R.id.add_card)

        val btnContinue: Button = view.findViewById(R.id.button_continue)
        val btnReady:Button = view.findViewById(R.id.button_ready)
        var lay : FrameLayout = view.findViewById(R.id.color)
        var layReady: ConstraintLayout = view.findViewById(R.id.added)
        var nameSub: TextView = view.findViewById(R.id.name_new_sub)
        val buttonBack: Button= view.findViewById(R.id.button_back)
        var pushEnabled : SwitchCompat= view.findViewById(R.id.switch_enabled)
        var banner: ImageView = view.findViewById(R.id.image_men)

        pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены  "
            else
                pushEnabled.text = "Выключены"
        }




    //    val push = pushEnabled.isChecked()

        communicator = activity as Communicator

        btnContinue.setOnClickListener {

//            val display: Display = activity!!.windowManager.defaultDisplay
//            val stageWidth = display.width
//            val stageHeight = display.height
//
//            Log.e("Display1", stageWidth.toString())
//            Log.e("Display", stageHeight.toString())
//
//            val parms: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(stageWidth - round(stageWidth.toDouble() / 15).toInt(), stageHeight)
//            banner.setLayoutParams(parms)
//
//            val set = ConstraintSet()
//            set.clone(layReady)
//
//            set.connect(banner.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
//            set.applyTo(layReady)


            layReady.isVisible = true
            lay.isVisible = false
            buttonBack.isVisible = false
            btnReady.isVisible = true



            nameSub.text = communicator.getNameNewSub()
        }

        btnReady.setOnClickListener {
            communicator.cardFragmentToListSub()
        }




        buttonBack.setOnClickListener {
            communicator.onBackPressedInFragms23()
        }

        return view
    }

}