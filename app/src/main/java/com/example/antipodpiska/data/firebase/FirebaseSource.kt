package com.example.antipodpiska.data.firebase

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.User
import com.example.antipodpiska.data.subList
import com.example.antipodpiska.menu.Statistics.Currencies
import com.example.antipodpiska.ui.home.HomeActivity
import com.example.recyclersample.data.DataSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

//Completable - this observer
    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful){
                    emitter.onComplete()
                    Log.e("FIREBASELOGIN", firebaseAuth.currentUser?.uid.toString())
                }
                else
                    emitter.onError(it.exception!!)
            }
        }


    }

    fun register(email: String, password: String, nickname: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                    Log.e("FIREBASE", firebaseAuth.currentUser?.uid.toString())
                }
                else
                {
                    emitter.onError(it.exception!!)
                }
            }
        }


    }

    fun sendVerificationCode(phone: String)= Completable.create { emitter ->


    }


    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun addSubInFirebase(sub: Sub) = Completable.create { emitter ->

   /*     val subItem = HashMap<String, String>()


        subItem.put( "image", sub.image.toString())
        subItem.put("description", sub.description.toString())
        subItem.put("name", sub.id.toString())
        subItem.put("card", sub.card.toString())
        subItem.put("dateEnd", sub.typeSub.toString())
        subItem.put("datePay", sub.datePay.toString())
        subItem.put("periodFree", sub.periodPay.toString())
        subItem.put("costSub", sub.costSub.toString())
        subItem.put("costCurr", sub.costCurr.toString())
        subItem.put("periodPay", sub.periodPay.toString())
        subItem.put("periodTypeFree", sub.periodTypeFree.toString())
        subItem.put("periodTypePay", sub.periodTypePay.toString())
        subItem.put("id_user", FirebaseAuth.getInstance().currentUser?.uid.toString())
        subItem.put("date_add", java.util.Calendar.getInstance().toString())
*/

        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).set(sub)
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful)
                            emitter.onComplete()
                        else
                            emitter.onError(it.exception!!)
                    }
                }
    }


    fun addUserInFirebase(user: User) {
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(user)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {

                }
                else{

                }

            }
    }


    fun getCurrencyFromFirebase(context: Context) {

        val shared: SharedPrefSource = SharedPrefSource(context)

        var docRef = firebaseFirestore.collection("Currency").document("RUB").get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        shared.saveToSharedCurr(document.toObject(Currencies::class.java)!!, context)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("TAG", "Error getting documents: ", exception)
                }

    }


    //На создание пользователя нужно время. так, чуть позже после регистрации добавляем данные пользователя. при последующих нажатиях прежде чем записать проверяем есть ли уже в бд user
    fun addUserInFirebaseWithCheck(email: String, password: String, nickname: String) {

        val userItem = HashMap<String, Any>()
        val token =  FirebaseInstanceId.getInstance().token
        if (token != null) {
            userItem.put("token", token)
        }

        userItem.put("pushAll", true)
        userItem.put("beginPush", 2)
        userItem.put("periodPush", true)
        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)
        var checkAvailabilityId = 0

        while (checkAvailabilityId != 1) {
            if (FirebaseAuth.getInstance().currentUser?.uid.toString() != "null") {

                firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
                    .addOnSuccessListener { document ->
                    if (document.data != null )
                    {

                    }
                        else{
                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userItem)
                            .addOnCompleteListener {
                                if (it.isSuccessful)
                                {
                                }
                                else{

                                }
                            }
                    }
                    }

                checkAvailabilityId += 1
            }
        }
    }


    fun addUserInFirebase(email: String, password: String, nickname: String, phone: String) = Completable.create { emitter ->

        val userItem = HashMap<String, String>()

        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)
        userItem.put("phone", phone)

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set("iii")
//ДОБАВИТЬ
    }

    fun getUserFromFirebase(context: Context){

        val shared: SharedPrefSource = SharedPrefSource(context)

        val docRef = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
            .addOnSuccessListener { document ->
                if (document!= null) {
               //     Log.e("shared.saveToShared", "DocumentSnapshot data: ${document.data}" )
                   if(document.data != null)
                    shared.saveUserToShared(document.toObject(User::class.java)!!, context)

                }
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting user from bd: ", exception)
            }
        Log.e("docRef", docRef.toString() )


    }


    fun getFromFirebase(context: Context): ArrayList<Sub> {

        var subListFromPref: ArrayList<Sub> = ArrayList()
        val shared: SharedPrefSource = SharedPrefSource(context)

        var docRef = firebaseFirestore.collection("Subscriptions").whereEqualTo("id_user",  FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        shared.saveToShared(document.toObject(Sub::class.java))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("TAG", "Error getting documents: ", exception)
                }

        return subListFromPref
    }


    fun updateFieldNearDayPay(){

        val userItem = HashMap<String, String>()

        userItem.put("nearDay", "good")
        firebaseFirestore.collection("Subscriptions").document("6781629119882284938").set(userItem)
    }



    fun signInWithCredential(credential: PhoneAuthCredential) = Completable.create { emitter ->
        // inside this method we are checking if
        // the code entered is correct or not.
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            })
    }




    /*companion object {
        private var INSTANCE: FirebaseSource? = null

        fun getFirebaseSource(): FirebaseSource {
            return synchronized(FirebaseSource::class) {
                val newInstance = INSTANCE ?: FirebaseSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }*/


}