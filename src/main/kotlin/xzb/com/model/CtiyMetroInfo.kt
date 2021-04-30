package xzb.com.model
data class CityMetroInfo(val cityName: String, val citCode: String, val lines: List<Line>) {
    val tableName = "house_cool.subway_table"
    public fun toSql(): MutableList<String> {

        val modifyCityCode = modifyCityCode()
        val sqls = mutableListOf<String>()

        for (line in lines) {
            for (station in line.stations.withIndex()) {
                val lineName = line.lineName;
                val stationName = station.value.name
                val longitude = station.value.longitude
                val latituede = station.value.latitude
                val sort = station.index + 1

                val template = """
                      INSERT INTO $tableName (city_code, city_name, line_name, station_name, longitude, latitude, sort)
                          VALUES ('$modifyCityCode','$cityName','$lineName','$stationName','$longitude','$latituede',$sort)"""
                sqls += template.trim()

            }
        }

        return sqls
    }

    private fun modifyCityCode(): String {
        // 四个直辖市 北京 天津 上海 重庆
        if (citCode in listOf("1100", "1200", "3100", "5000")) {
            return citCode.substring(0..1) + "0100"
        } else {
            return citCode + "00"
        }
    }
}