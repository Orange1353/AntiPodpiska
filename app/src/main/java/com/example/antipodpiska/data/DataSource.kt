/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.recyclersample.data

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.antipodpiska.data.Sub

import com.example.antipodpiska.data.subList

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource(resources: Resources, context: Context) {
    private val initialSubList = subList(resources, context)
    private val subLiveData = MutableLiveData(initialSubList)

    /* Adds flower to liveData and posts value. */
    fun addSub(sub: Sub) {
        val currentList = subLiveData.value
        if (currentList == null) {
            subLiveData.postValue(listOf(sub))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, sub)
            subLiveData.postValue(updatedList)
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeSub(flower:Sub) {
        val currentList = subLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(flower)
            subLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getSubForId(id: Long): Sub? {
        subLiveData.value?.let { sub ->
            return sub.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getSubList(): LiveData<List<Sub>> {
        return subLiveData
    }

    /* Returns a random flower asset for flowers that are added. */
    fun getRandomSubImageAsset(): Int? {
        val randomNumber = (initialSubList.indices).random()
        return initialSubList[randomNumber].image
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources, context: Context): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}