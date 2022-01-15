package com.abukatech.rubbletrack;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuestionsSpreadsheetWebService {

    @POST("1FAIpQLSdL0VtR23KDfREY0yrcvpVZsczp1IYv8dH8qJFP5csaNG2XuQ/formResponse")
    @FormUrlEncoded
    Call<Void> completeQuestionnaire(
            @Field("entry.597920126") String name,
            @Field("entry.1836483922") String answerQuestionCat,
            @Field("entry.2004388355") String color
    );

}

