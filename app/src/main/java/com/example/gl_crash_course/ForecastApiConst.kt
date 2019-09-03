package com.example.gl_crash_course

object ForecastApiConst {
    const val LAT_SZCZECIN: String = "53.4"
    const val LON_SZCZECIN: String = "14.5"
    const val CNT: String = "10"
    const val API_PATH = "https://api.openweathermap.org/data/2.5/"
    const val IMG_URL_PREFIX = "http://openweathermap.org/img/wn/"
    const val IMG_URL_POSTFIX = "@2x.png"
    const val UNITS: String = "metric"
    const val UPDATE_TIME: Int = 2
    const val ADAPTER_LIST_SIZE: Int = 10
    const val ADAPTER_SIZE_DIVIDE: Int = 3

    val PL_CITIES_IDS = mapOf(
        "Szczecin" to 3083829,
        "Gorzów Wlkp." to 3098722,
        "Zielona Góra" to 7530991,
        "Gdańsk" to 7531002,
        "Bydgoszcz" to 3102014,
        "Poznań" to 7530858,
        "Wrocław" to 3081368,
        "Olsztyn" to 763166,
        "Warszawa" to 756135,
        "Łódź" to 3093133,
        "Katowice" to 3096472,
        "Suwałki" to 7530941,
        "Białystok" to 858789,
        "Lublin" to 765876,
        "Kraków" to 3094802,
        "Rzeszów" to 7530819
    )
}