package xzb.com.service

import io.ktor.http.content.*
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import xzb.com.database.DaoFacade
import java.io.File
import java.io.FileInputStream
import kotlin.io.path.ExperimentalPathApi


class DataService {
    companion object {
        fun upload(part: PartData.FileItem) {
            val name = part.originalFileName!!
            val file = File("uploads/$name")

            // use InputStream from part to save file
            part.streamProvider().use { its ->
                // copy the stream to the file with buffering
                file.outputStream().buffered().use {
                    // note that this is blocking
                    its.copyTo(it)
                }
            }
            // make sure to dispose of the part after use to prevent leaks
            part.dispose()
        }

        fun downLoad(): File {
            return handleExcel()
        }

        @OptIn(ExperimentalPathApi::class)
        fun handleExcel(): File {
            // 取一份最新的模板文件
            var upload = File("uploads")
            val listFiles = upload.listFiles { it -> it.name.endsWith(".xls") }
            listFiles.sortByDescending { it.lastModified() }
            return listFiles.firstOrNull()?.processExcel() ?: File("empty.xls")
        }


    }

}


private fun File.processExcel(): File {
    val excelFile = FileInputStream(this)
    val wb = HSSFWorkbook(excelFile)
    val sheet: HSSFSheet = wb.getSheetAt(0)

    val first = sheet.getRow(sheet.firstRowNum)
    if (first.lastCellNum.equals(10) && first.getCell(6).stringCellValue == "PHONE") {
        throw RuntimeException("错误的excel模板文件")
    }
    val emptyPhone = mutableMapOf<Int, String>()

     sheet.rowIterator().withIndex().forEach {
         if(it.index != 0)
         {
             if (it.value.getCell(6) == null
                 ||  it.value.getCell(6) .stringCellValue.isBlank()) {
                 emptyPhone +=  it.index to it.value.getCell(5).stringCellValue
             }
         }

    }
    val mobiles = DaoFacade().getMobile(emptyPhone.values)
    val downloadZFile = File("download/new.txt")
    downloadZFile.writeText(mobiles.toString())
    return downloadZFile
}
