package data.model

import com.google.gson.annotations.SerializedName

   data class GeoCodingModel(
    val results:ArrayList<GeoCodingDetails>
   )

   data class GeoCodingDetails(
       val components:GeoCodingComponent
   )

   data class GeoCodingComponent(
    @SerializedName("country_code")
    val countryCode:String
    )



