package com.example.antipodpiska.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.User
import kotlinx.android.synthetic.main.fragment_menu_push.*

class MenuPushFragment : Fragment() {

    private lateinit var buttonBack: Button
    private lateinit var buttonSave: Button
    private lateinit var seekBar: SeekBar
    private lateinit var textSeekBar: TextView
    private lateinit var communicator: CommunicatorMenu
    private lateinit var pushEnabled : SwitchCompat
    private lateinit var periodEnabled : SwitchCompat
    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_menu_push, container, false)

        buttonBack = view.findViewById(R.id.button_back)
        buttonSave = view.findViewById(R.id.button_save)
        seekBar = view.findViewById(R.id.seekBar_days)
        textSeekBar = view.findViewById(R.id.textView_seek_bar)
        pushEnabled  = view.findViewById(R.id.switch_enabled)
        periodEnabled  = view.findViewById(R.id.switch_everyday)
        var frame2: FrameLayout = view.findViewById(R.id.frameLayout3)
        var frame3: FrameLayout = view.findViewById(R.id.frameLayout4)


        var context: Context? = getContext()
        var Shared: SharedPrefSource = SharedPrefSource(context!!)
        user = Shared.getUserFromShared(context)

        seekBar.progress= user.beginPush
        pushEnabled.isChecked = user.pushAll
        periodEnabled.isChecked = user.periodPush
        val tmp = user.beginPush

        when {
            user.beginPush == 1 -> textSeekBar.text = " " + user.beginPush + " день"
            user.beginPush in 2..4 ->  textSeekBar.text = " " + user.beginPush + " дня"
            else ->  textSeekBar.text = " " + user.beginPush + " дней"
        }



        if (user.periodPush)
            periodEnabled.text = "Каждый день"
        else
            periodEnabled.text = "Однократно "


        periodEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if( periodEnabled.text == "Каждый день")
                periodEnabled.text = "Однократно "
            else
                periodEnabled.text = "Каждый день"
        }

        if(!user.pushAll) {
            pushEnabled.isChecked = false
            pushEnabled.isChecked = false
            frame2.isVisible = false
            frame3.isVisible = false
        }

            pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if(!isChecked) {
                pushEnabled.isChecked = false
                pushEnabled.isChecked = false
                frame2.isVisible = false
                frame3.isVisible = false
            }
                else{
                pushEnabled.isChecked = true
                pushEnabled.isChecked = true
                frame2.isVisible = true
                frame3.isVisible = true
                }

        }

        communicator = activity as CommunicatorMenu

        buttonBack.setOnClickListener {
            communicator.onBackPressedPopBackstack()
        }


        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                when {
                    i == 1 -> textSeekBar.text = " $i день"
                    i in 2..4 ->  textSeekBar.text = " $i дня"
                    else ->  textSeekBar.text = " $i дней"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        pushEnabled.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if( isChecked) {
                    frame2.isVisible = true
                    frame3.isVisible = true
                }
                else
                     {
                        frame2.isVisible = false
                        frame3.isVisible = false
                    }

            }
        })


        buttonSave.setOnClickListener {

            user.pushAll = pushEnabled.isChecked()
            user.beginPush = seekBar.progress
            user.periodPush = periodEnabled.isChecked()

            Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
            communicator.editProfile(user)
        }

        return view
    }



}