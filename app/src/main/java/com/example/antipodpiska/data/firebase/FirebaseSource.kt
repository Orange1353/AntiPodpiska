package com.example.antipodpiska.data.firebase

import com.example.antipodpiska.data.Sub
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

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
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }


    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser


    fun addSubInFirebase(sub: Sub) = Completable.create { emitter ->

        val subItem = HashMap<String, String>()


        subItem.put( "image", sub.image.toString())
        subItem.put("description", sub.description.toString())
        subItem.put("name", sub.id.toString())
        subItem.put("card", sub.card.toString())
        subItem.put("dateEnd", sub.dateEnd.toString())
        subItem.put("datePay", sub.datePay.toString())
        subItem.put("periodFree", sub.periodPay.toString())
        subItem.put("costSub", sub.costSub.toString())
        subItem.put("costCurr", sub.costCurr.toString())
        subItem.put("periodPay", sub.periodPay.toString())
        subItem.put("periodTypeFree", sub.periodTypeFree.toString())
        subItem.put("periodTypePay", sub.periodTypePay.toString())
        subItem.put("id_user", FirebaseAuth.getInstance().currentUser?.uid.toString())
        subItem.put("date_add", java.util.Calendar.getInstance().toString())



        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).set(subItem)
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful)
                            emitter.onComplete()
                        else
                            emitter.onError(it.exception!!)
                    }
                }
    }

    fun addUserInFirebase(email: String, password: String, nickname: String) = Completable.create { emitter ->

        val userItem = HashMap<String, String>()
        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)


        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userItem)
        .addOnCompleteListener {
              if (!emitter.isDisposed) {
                  if (it.isSuccessful)
                      emitter.onComplete()
                  else
                      emitter.onError(it.exception!!)
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


    }





}