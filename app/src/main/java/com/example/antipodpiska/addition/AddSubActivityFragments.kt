package com.example.antipodpiska.addition

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.User
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.example.antipodpiska.menu.NavigationMenuFragment
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.subList.SubListViewModel
import com.example.antipodpiska.utils.startSubListActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import java.text.SimpleDateFormat
import java.util.*


class AddSubActivityFragments : AppCompatActivity(), Communicator {

    private var fragmentManager : FragmentManager = getSupportFragmentManager()
    //+1 оставить. чтобы было 2 дока shared
    private val PREFS_NAME = FirebaseAuth.getInstance().currentUser?.uid.toString()+"1"

    private val Shared: SharedPrefSource by lazy{ SharedPrefSource(this) }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub_fragments)

        fragmentManager.beginTransaction().add(R.id.container, CreateNameAndTypeFragment()).commit()
        checkUserCloud()
    }

    override fun nameFragmentToPeriodFragment(nameSub: String, descriptionSub: String, typeSub: String){

        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = preferences.edit()
        prefsEditor.putString("name", nameSub).apply();
        prefsEditor.putString("description", descriptionSub).apply();
        prefsEditor.putString("type", typeSub).apply();


        fragmentManager.beginTransaction().replace(R.id.container, CreatePediodFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun periodFragmentToCardFragment(freePeriod: String, periodTypeFree: String,costSub: String, costCurr: String, periodPay: String, periodTypePay: String, dateSub: String){
        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = preferences.edit()
        prefsEditor.putString("freePeriod", freePeriod).apply();
        prefsEditor.putString("costSub", costSub).apply();
        prefsEditor.putString("periodPay", periodPay).apply();
        prefsEditor.putString("dateSub", dateSub).apply();

        prefsEditor.putString("typeFree", periodTypeFree).apply();
        prefsEditor.putString("currCost", costCurr).apply();
        prefsEditor.putString("typePeriod", periodTypePay).apply();

        fragmentManager.beginTransaction().replace(R.id.container, CreateCardFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun cardFragmentToListSub(cardNumber:String, push:Boolean){
        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = preferences.edit()
        prefsEditor.putString("card", cardNumber).apply();
        prefsEditor.putBoolean("push", push).apply();
        //    val intent = Intent(this, SubListActivity()::class.java)
//        intent.putExtra(SUB_ID, sub.id)
        //   startActivity(intent)
        addSub()
 //       startSubListActivity()
    }

    override fun onBackPressedInFragms23() {
        this.fragmentManager.popBackStack()
    }

    override fun onBackPressedInFragm() {
        finish()
    }

    fun checkUserCloud(){

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
                .addOnSuccessListener { document ->
                    if (document.data != null )
                    {Log.d("!!!!!", "1 DocumentSnapshot data: ${document.data}")
                        val user = document.toObject(User::class.java)
                       /* if (user?.token != FirebaseInstanceId.getInstance().token.toString()) {
                            user?.token = FirebaseInstanceId.getInstance().token.toString()
                            val firebase = FirebaseSource()
                            if (user != null) {
                                //возможно токен сменился, это чтобы перезаписать с новым
                                firebase.addUserInFirebase(user)
                            }
                        }*/
                        Log.d("!!!!!", "1 DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("!!!!!", "2 DocumentSnapshot data: ${document.data}")
                        var listUser = Shared.getTempUser(this)

                        Log.d("!!!!!", "listUser: $listUser")
                        val firebase = FirebaseSource()
                        firebase.addUserInFirebaseWithCheck(listUser[0], listUser[1], listUser[2])
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("!!!!!", "get failed with ", exception)
                }
        var listUser = Shared.getTempUser(this)
        val firebase = FirebaseSource()
        firebase.addUserInFirebaseWithCheck(listUser[0], listUser[1], listUser[2])
    }

    private fun addSub() {

        //   checkUserCloud()

        val resultIntent = Intent()

        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


            val name = preferences.getString("name", "") ?: ""
            val description = preferences.getString("description", "") ?: ""
            val typeSub= preferences.getString("type", "") ?: ""
            val datePay = preferences.getString("dateSub", "") ?: ""
            val freePeriod = preferences.getString("freePeriod", "") ?: ""
            val cost = preferences.getString("costSub", "") ?: ""
            val period = preferences.getString("periodPay", "") ?: ""
            val typeFree = preferences.getString("typeFree", "") ?: ""
            val currCost = preferences.getString("currCost", "") ?: ""
            val typePeriod = preferences.getString("typePeriod", "") ?: ""
            val card = preferences.getString("card", "") ?: ""
            val push = preferences.getBoolean("push", false)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val cal: Calendar = GregorianCalendar()

            val dateAdd = dateFormat.format(cal.getTime()).toString()

            resultIntent.putExtra(SUB_NAME, name)
            resultIntent.putExtra(SUB_DESCRIPTION, description)
            resultIntent.putExtra(TYPE, typeSub)
            resultIntent.putExtra(DATE_PAY, datePay)
            resultIntent.putExtra(FREE_PERIOD, freePeriod)
            resultIntent.putExtra(COST, cost)
            resultIntent.putExtra(PERIOD, period)
            resultIntent.putExtra(TYPE_FREE, typeFree)
            resultIntent.putExtra(CURR_COST, currCost)
            resultIntent.putExtra(TYPE_PERIOD, typePeriod)
            resultIntent.putExtra(CARD, card)
            resultIntent.putExtra(PUSH, push)
            resultIntent.putExtra(DATE_ADD, dateAdd)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()


    }

}
