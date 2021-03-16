package com.example.antipodpiska.menu

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.User


class MenuProfileFragment : Fragment() {

    private lateinit var nameUser: EditText
    private lateinit var nickUser: EditText
    private lateinit var phoneUser: EditText
    private lateinit var emailUser: EditText
    private lateinit var buttonBack: Button
    private lateinit var buttonSave: Button
    private var user: User = User()
    private lateinit var communicator: CommunicatorMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View  = inflater.inflate(R.layout.fragment_menu_profile, container, false)

        var context: Context? = getContext()
        var Shared: SharedPrefSource = SharedPrefSource(context!!)

        var desired_string = ""
        val arguments = arguments
        if(arguments != null) {
            desired_string = arguments.getString("key_from").toString()
        }

        user = Shared.getUserFromShared(context)

        nameUser = view.findViewById(R.id.edit_text_profile_name)
        nickUser = view.findViewById(R.id.edit_text_profile_nick)
        phoneUser = view.findViewById(R.id.edit_text_profile_phone)
        emailUser = view.findViewById(R.id.edit_text_profile_email)

        emailUser.setText(user.email)
        nickUser.setText(user.nickname)
        nameUser.setText(user.name)
        phoneUser.setText(user.phone)

        buttonBack = view.findViewById(R.id.button_back)
        buttonSave = view.findViewById(R.id.button_save_profile)

        if (desired_string == "edit")
        {
            buttonSave.visibility = View.VISIBLE
            emailUser.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            nickUser.setInputType(InputType.TYPE_CLASS_TEXT)
            nameUser.setInputType(InputType.TYPE_CLASS_TEXT)
            phoneUser.setInputType(InputType.TYPE_CLASS_PHONE)
        }
        communicator = activity as CommunicatorMenu

        buttonSave.setOnClickListener {
            user = User(
                email = emailUser.text.toString(),
                nickname = nickUser.text.toString(),
                name = nameUser.text.toString(),
                phone = phoneUser.text.toString(),
                password = user.password
            )
            communicator.editProfile(user)
        }
        buttonBack.setOnClickListener {
            communicator.onBackPressedPopBackstack()
        }

        return view

    }



}