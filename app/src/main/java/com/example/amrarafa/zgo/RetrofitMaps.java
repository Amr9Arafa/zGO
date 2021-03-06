package com.example.amrarafa.zgo;

/**
 * Created by amr arafa on 3/9/2017.
 */

import com.example.amrarafa.zgo.POJO.Example;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by navneet on 17/7/16.
 */

public interface RetrofitMaps
{


    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */



    @GET("api/directions/json?key=AIzaSyDpMrsr3DtuuPQH-4xkb9jopLLPw_E2CZo")

    Call<Example> getDistanceDuration(@Query("units")
                                      String units, @Query("origin")
                                      String origin, @Query("destination")
                                      String destination, @Query("mode")
                                      String mode);

}

