package com.example.antipodpiska.subList


import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.observe
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.AddSubActivityFragments
import com.example.antipodpiska.addition.DATE_ADD
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.User
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.example.antipodpiska.menu.ArchiveFragment
import com.example.antipodpiska.menu.CommunicatorMenu
import com.example.antipodpiska.menu.MenuFragment
import com.example.antipodpiska.menu.NavigationMenuFragment
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


const val SUB_ID = "sub id"
const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val TYPE = "typeSub"
const val CARD = "card"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"
const val PUSH = "push"

class SubListActivity : AppCompatActivity(), CommunicatorMenu {
    private val newSubActivityRequestCode = 1
    private val subsListViewModel by viewModels<SubListViewModel> {
        SubListViewModelFactory(this)
    }

    private val CHANNEL_ID = "channel"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }

        setUserDataToSharedForFutureProfile()

        this.supportFragmentManager.beginTransaction().replace(R.id.lay_container, MenuFragment())
            .addToBackStack(
                null
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.item_menu -> {
                    fab.isVisible = false
                    bottomNavigationView.isSelected = true

                    val fragment = NavigationMenuFragment()
                    this.supportFragmentManager.beginTransaction().replace(
                        R.id.lay_container,
                        fragment
                    )
                        .addToBackStack(
                            null
                        )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                }

                R.id.item_subs -> {
                    fab.isVisible = true
                    bottomNavigationView.isSelected = true

                    val fragment = MenuFragment()
                    this.supportFragmentManager.popBackStack()

                    this.supportFragmentManager.beginTransaction().replace(
                        R.id.lay_container,
                        fragment
                    )
                        .addToBackStack(
                            null
                        )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()


                    true
                }

                R.id.item_archive -> {
                    fab.isVisible = false
                    bottomNavigationView.isSelected = true
                    val fragment = ArchiveFragment()
                    this.supportFragmentManager.beginTransaction().replace(
                        R.id.lay_container,
                        fragment
                    )
                        .addToBackStack(
                            null
                        )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                }
                R.id.item_calendar -> {
                    fab.isVisible = false
                    bottomNavigationView.isSelected = true
                    val fragment = ArchiveFragment()
                    this.supportFragmentManager.beginTransaction().replace(
                        R.id.lay_container,
                        fragment
                    )
                        .addToBackStack(
                            null
                        )
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                }


                else->false
            }
        }



    //    val headerAdapter = HeaderAdapter()
        val subsAdapter = SubAdapter { sub -> adapterOnClick(sub) }
  //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

  //      val recyclerView: RecyclerView = findViewById(R.id.recycler_view_menu)
    //    recyclerView.adapter = subsAdapter


        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
                subsAdapter.submitList(it as MutableList<Sub>)
  //              headerAdapter.updateSubCount(it.size)
            }

        }


        createNotChannel()


    }

    override  fun sendSupportMessage(text: String, email: String, theme: String)
    {
        subsListViewModel.sendSupportInFirebase(text, email, theme)
    }

    private fun fabOnClick() {

      val intent = Intent(this, AddSubActivityFragments::class.java)
      startActivityForResult(intent, newSubActivityRequestCode)
    }

    private fun createNotChannel(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name = "Not title"
            val descript = "not decs"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descript
            }
            val notificationManager: NotificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    override fun editProfile(userNew: User){
        subsListViewModel.editUser(userNew, this)
    }

    private fun sendNot(){

        val intent = Intent(this, SubListActivity::class.java).apply {
           flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent= PendingIntent.getActivity(this, 0, intent, 0)

        val builder= NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.logo_mail)
            .setContentTitle("Время проверить подписку!")
            .setContentText("Завтра истекает N ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            with(NotificationManagerCompat.from(this)){
                notify(notificationId, builder.build())
            }
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {
        val intent = Intent(this, SubDetailActivity()::class.java)
        intent.putExtra(SUB_ID, sub.id)
        startActivity(intent)
        }

    override fun onBackPressed() {

    }
    override fun onBackPressedMenuItem() {
        val fragment = NavigationMenuFragment()
        this.supportFragmentManager.beginTransaction().replace(
            R.id.lay_container,
            fragment
        )
            .addToBackStack(
                null
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onBackPressedPopBackstack() {
        this.supportFragmentManager.popBackStack()
    }

   override fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.lay_container, fragment)
            .addToBackStack(
                null
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    //if login on new device
    fun setUserDataToSharedForFutureProfile(){
        var Shared: SharedPrefSource = SharedPrefSource(this)
        var listUser = Shared.getTempUser(this)
        if(listUser[0] == "")
        {
            var fireUser: FirebaseSource = FirebaseSource()
            fireUser.getUserFromFirebase(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newSubActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subName = data.getStringExtra(SUB_NAME)
                val subDescription = data.getStringExtra(SUB_DESCRIPTION)

                var typeSub = data.getStringExtra(TYPE)
                if (typeSub == null || typeSub == "Выберите тип подписки")
                    typeSub = "Иное"
                var dayPay = data.getStringExtra(DATE_PAY)
                if (dayPay == null)
                    dayPay = ""
                var periodFree = data.getStringExtra(FREE_PERIOD)
                if (periodFree == null)
                    periodFree =""
                var cost = data.getStringExtra(COST)
                if ( cost == null)
                    cost =""
                var currCost = data.getStringExtra(CURR_COST)
                if ( currCost == null)
                currCost = ""
                var periodPay = data.getStringExtra(PERIOD)
                if ( periodPay == null)
                    periodPay = ""
                var peroidTypeFree = data.getStringExtra(TYPE_FREE)
                if ( peroidTypeFree == null)
                    peroidTypeFree = ""
                var peroidTypePay = data.getStringExtra(TYPE_PERIOD)
                if ( peroidTypePay == null)
                    peroidTypePay = ""
                var card = data.getStringExtra(CARD)
                if ( card == null)
                    card = ""
                var push = data.getBooleanExtra(PUSH, false)
                val dateAdd = data.getStringExtra(DATE_ADD)


//image?
                subsListViewModel.insertSub(
                    subName,
                    subDescription,
                    typeSub,
                    dayPay,
                    periodFree,
                    cost,
                    currCost,
                    periodPay,
                    peroidTypeFree,
                    peroidTypePay,
                    card,
                    push,
                    dateAdd!!,
                    this
                )
            }
        }
    }


}


