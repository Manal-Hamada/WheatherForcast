package com.example.wheatherforcast.utils

class ConvertUnits {
        companion object{
            private  var approximateTemp= ReaadableTemp()


            fun convertTemp(temp:Double,tempUnit:String) :String{
                var result=""
                when(tempUnit){
                    Constants.Celsius -> result= approximateTemp(temp - 273.15) + "\u00B0"
                    Constants.Kelvin -> result=
                        approximateTemp(273.5 + ((temp - 32.0) * (5.0/9.0))) + "\u00B0"
                    Constants.Fahrenheit -> result= approximateTemp(temp) + "\u00B0"
                    else -> result= approximateTemp(temp) + "\u00B0"
                }
                return result
            }
        }
    }
