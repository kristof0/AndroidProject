/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.wannaeat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Immutable model class for an OpenTable repo that holds all the information about a repository.
 * Objects of this type are received from the OT api, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */
@Entity(tableName = "restaurant_table")
data class Repo (
        @PrimaryKey @field:SerializedName("id") val id:Int,
        @field:SerializedName("name") val name:String,
        @field:SerializedName("address") val address: String,
        @field:SerializedName("city") val city:String,
        @field:SerializedName("state") val state:String,
        @field:SerializedName("area") val area:String,
        @field:SerializedName("postal_code") val postal_code:String,
        @field:SerializedName("country")  val country:String,
        @field:SerializedName("phone")  val phone:String,
        @field:SerializedName("lat") val lat:Double,
        @field:SerializedName("lng") val lng:Double,
        @field:SerializedName("price") val price:Int,
        @field:SerializedName("reserve_url")  val reserve_url:String,
        @field:SerializedName("mobile_reserve_url") val mobile_reserve_url:String,
        @field:SerializedName("image_url") val image_url:String,
        @SerializedName("favorite") val favorite:Int=0


)