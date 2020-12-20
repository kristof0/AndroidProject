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

package com.wannaeat.db

import androidx.paging.DataSource
import androidx.room.*
import com.wannaeat.model.Repo

/**
 * Room data access object for accessing the [Repo] table.
 */
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Repo>)

    // Do a similar query as the search API:
    // Look for repos that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM restaurant_table WHERE city LIKE :queryString  ")
    fun restaurantsByCity(queryString: String): DataSource.Factory<Int, Repo>


    //update if it`s added to favorites
    @Query("UPDATE restaurant_table SET favorite = CASE WHEN favorite = 1 THEN 0 ELSE 1 END WHERE id=:id")
    fun updateRestaurants(id: Int)

    //get the favorites
    @Query("SELECT * FROM restaurant_table WHERE favorite=1 ")
    fun getFavorites(): DataSource.Factory<Int, Repo>


}
