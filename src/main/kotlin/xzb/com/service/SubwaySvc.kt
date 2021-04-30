package xzb.com.service


import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import io.ktor.application.log
import khttp.get
import org.json.JSONArray
import org.json.JSONObject
import xzb.com.model.CityMetroInfo
import xzb.com.model.Line
import xzb.com.model.Station
import java.io.File

class SubwaySvc {

   fun generateSubways(): List<CityMetroInfo>
    {
        val cities = readCityCSV()
        val total = mutableListOf<CityMetroInfo>()
        cities.forEach {
//           log().info("*************************************")
            println("handle = ${it["cityName"]}")
            val metroInfo = handleSubway(
                cityPy = it["cityName"] ?: error(""),
                citCode = it["code"] ?: error(""),
                cityName = it["cname"] ?: error("")
            )
            total.add(metroInfo)
//            println("*************************************")
        }

      return total
    }

    private fun readCityCSV(): List<Map<String, String>> {
        val result = mutableListOf<Map<String, String>>()
        val path   = this::class.java.classLoader.getResource("cityid.csv").toURI()
        val file = File(path)

        val csvReader = CsvReader()
        csvReader.open(file) {
            readAllWithHeaderAsSequence().forEach {
                val t = mutableMapOf<String, String>()
                it.entries.forEach { obj ->
                    t[obj.key.trim()] = obj.value.trim()
                }
                result.add(t)
            }

        }
        return result

    }


    fun handleSubway(citCode: String, cityPy: String, cityName: String): CityMetroInfo {
        val url =
            "http://map.amap.com/service/subway?_1611194874728&srhdata=${citCode}_drw_${cityPy}.json"
        val response = get(url)
        val linesInfo = mutableListOf<Line>()
        val linesJson = response.jsonObject.getJSONArray("l")
        for (j in 0 until linesJson.length()) {
            linesInfo += handleLine(linesJson.getJSONObject(j))
        }

        return CityMetroInfo(cityName = cityName, citCode = citCode, lines = linesInfo)


    }


    //  处理一条地铁信息
    fun handleLine(lineJson: JSONObject?): Line {
        val lineName = lineJson?.getString("ln") ?: ("unknown")
        val stations = handleStations(lineJson?.getJSONArray("st"))
        return Line(lineName = lineName, stations = stations)
    }

    //处理地铁中的站点信息
    fun handleStations(stations: JSONArray?): List<Station> {
        val stationsList = mutableListOf<Station>()
        for (s in 0 until (stations?.length() ?: 0)) {
            val stationData = handleStation(stations?.getJSONObject(s))
            if (stationData != null) {
                stationsList.add(stationData)
            }
        }

        return stationsList
    }

    fun handleStation(station: JSONObject?): Station? {
        return if (station != null) {
            val limit: Int = 2
            val name = station.getString("n")
            val (longitude, latitude) = station.getString("sl").split(",", limit = 2)
            Station(name = name, latitude = latitude, longitude = longitude)
        } else {
            null
        }
    }
}